[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 04`](../Readme.md) > `Ejemplo 2 `

## Ejemplo 2: Content Providers

<div style="text-align: justify;">


### 1. Objetivos :dart:

* Proveer contenido de la app a aplicaciones externas
* Acceder a información de una app externa

### 2. Requisitos :clipboard:



### 3. Desarrollo :computer:



Para este proyecto, hay qué utilizar el código [base](base).

Para los content providers, hay que entender los siguientes conceptos:

* ContentResolver: Es una clase que se comunica con el proveedor y proporcionan funciones CRUD.
* CursorLoader: hace una consulta al ContentResolver y regresa un cursor.
* Cursor: Interfaz que provee de acceso al resultado de una query a la DB.
* UriMatcher: Utilidad que nos ayuda a identificar una uri ingresada con una uri registrada a través de un identificador.



* Content URI: Es una URI que apunta a una tabla o dato específico de un proveedor de contenido de una aplicación específica. Su estructura es la siguiente:

  ```xml
  content://<Authority>/<tabla>
  ```

  

  si se quiere recuperar un valor específico, agregamos al final su id:

  ```xml
  content://<Authority>/<tabla>/<id>
  ```

  ​	

* Authority: Es una cadena de texto que identifica al proveedor de contenido dentro de tu aplicación. Generalmente se ocupa la siguiente nomenclatura: 

* ```xml
<dominio de primer nivel>.<dominio>.<nombre-paquete>.<provider>
  ```

  Por ejemplo:

  ​	org.bedu.roomvehicles.provider

* 





Paso 1: Modificar nuestro Dao (Hay qué aclarar por qué el id tiene qué ser _ID, también regresar el id en el inserto)

```kotlin
@Dao
interface VehicleDao {

    @Insert
    fun insertAll(vehicle: List<Vehicle>)

    @Insert
    fun insertVehicle(vehicle: Vehicle): Long

    @Update
    fun updateVehicle(vehicle: Vehicle): Int

    @Delete
    fun removeVehicle(vehicle: Vehicle)

    @Query("DELETE FROM ${Vehicle.TABLE_NAME} WHERE ${Vehicle.COLUMN_PK}=:id")
    fun removeVehicleById(id: Int): Int

    @Delete
    fun removeVehicles(vararg vehicles: Vehicle)

    @Query("SELECT * FROM ${Vehicle.TABLE_NAME}")
    fun getVehicles(): Cursor

    @Query("SELECT * FROM ${Vehicle.TABLE_NAME} WHERE ${Vehicle.COLUMN_PK} = :id")
    fun getVehicleById(id: Int): Cursor

    @Query("SELECT * FROM ${Vehicle.TABLE_NAME} WHERE plates_number = :platesNumber")
    fun getVehicleByPlates(platesNumber: String) : Cursor
}
```



Paso 2: Crear nuestro UriMatcher (definiendo las queries a efectura). Este se llamará ___VehicleMatcher.kt___.

```kotlin
const val VEHICLE_DIR = 1
const val VEHICLE_ITEM = 2


val vehicleMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
    addURI(VehicleProvider.AUTHORITY, "${Vehicle.TABLE_NAME}", VEHICLE_DIR)
    addURI(VehicleProvider.AUTHORITY, "${Vehicle.TABLE_NAME}/*", VEHICLE_ITEM)
}
```



Paso 3: Crear el Content Provider.

```kotlin

class VehicleProvider: ContentProvider() {


    // Acceso a la base de datos
    private lateinit var vehicleDb: BeduDb

    // Guarda la instancia de nuestro DAO
    private var vehicleDao: VehicleDao? = null

    companion object{
        const val AUTHORITY = "org.bedu.roomvehicles.provider"
        const val URI_STRING = "content://$AUTHORITY/"
        const val URI_VEHICLE = "$URI_STRING${Vehicle.TABLE_NAME}"
        const val MIME_CONTENT_TYPE = "vnd.android.cursor.dir/$URI_VEHICLE"
        const val MIME_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/$URI_VEHICLE"
    }

    override fun onCreate(): Boolean {
        vehicleDb = BeduDb.getInstance(context!!)!!
        vehicleDao = vehicleDb.vehicleDao()
        return true
    }

    // Select
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when(vehicleMatcher.match(uri)){
            VEHICLE_ITEM,VEHICLE_DIR -> {
                val code = vehicleMatcher.match(uri)
                Log.d("Vehicles","code: ${code}")
                val dao = BeduDb.getInstance(context!!)!!.vehicleDao()
                val cursor=
                        if (code== VEHICLE_DIR) dao.getVehicles()
                        else dao.getVehicleById(ContentUris.parseId(uri).toInt())
                Log.d("Vehicles","cursosr: ${cursor.count}")
                cursor.setNotificationUri(context?.contentResolver, uri)
                return cursor
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    // muestra el MIME correspondiente al URI
    override fun getType(uri: Uri): String? {
        return when(vehicleMatcher.match(uri)){
            VEHICLE_DIR -> MIME_CONTENT_TYPE
            VEHICLE_ITEM -> MIME_CONTENT_ITEM_TYPE
            else -> throw IllegalArgumentException("Unknown Uri: $uri")
        }
    }


    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return when(vehicleMatcher.match(uri)){
            VEHICLE_DIR -> {
                val vehicle = Vehicle(
                    model = values?.getAsString(Vehicle.COLUMN_MODEL),
                    brand = values?.getAsString(Vehicle.COLUMN_BRAND),
                    platesNumber = values?.getAsString(Vehicle.COLUMN_PLATES)
                )
                val id = context?.let { BeduDb.getInstance(it)?.vehicleDao()?.insertVehicle(vehicle) }
                context?.contentResolver?.notifyChange(uri,null)
                return ContentUris.withAppendedId(uri, id!!.toLong())
            }

            VEHICLE_ITEM -> throw IllegalArgumentException("Invalid URI: id should not be provided")

            else -> throw IllegalArgumentException("Unknown Uri: $uri")
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return when(vehicleMatcher.match(uri)){
            VEHICLE_DIR -> throw IllegalArgumentException("Invalid URI: id should be provided")

            VEHICLE_ITEM -> {

                val id = ContentUris.parseId(uri)
                val count = BeduDb.getInstance(context!!)?.vehicleDao()?.removeVehicleById(id.toInt())
                return count!!
            }

            else -> throw IllegalArgumentException("Unknown Uri: $uri")
        }
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return when(vehicleMatcher.match(uri)){
            VEHICLE_DIR -> throw IllegalArgumentException("Invalid URI: id should be provided")

            VEHICLE_ITEM -> {

                val vehicle = Vehicle(
                        id = ContentUris.parseId(uri).toInt(),
                        model = values?.getAsString(Vehicle.COLUMN_MODEL),
                        brand = values?.getAsString(Vehicle.COLUMN_BRAND),
                        platesNumber = values?.getAsString(Vehicle.COLUMN_PLATES)
                )

                return BeduDb.getInstance(context!!)?.vehicleDao()?.updateVehicle(vehicle)!!

            }

            else -> throw IllegalArgumentException("Unknown Uri: $uri")
        }
    }
}
```



Hay qué declarar nuestro provider en el ___AndroidManifest.xml___:

```xml
<provider
    android:name=".provider.VehicleProvider"
    android:authorities="org.bedu.roomvehicles.provider"
    android:exported="true"
    android:permission="org.bedu.roomvehicles.provider.READ_WRITE"/>
```

Y agregar el siguiente permiso:

```xml
<permission android:name="com.example.android.contentprovidersample.provider.READ_WRITE"/>
```



Paso 4: sustituir la llamada a getVehicles en el ListFragment. Para el botón eliminar:

```kotlin
    override fun onDelete(vehicle: Vehicle) {

        val executor: ExecutorService = Executors.newSingleThreadExecutor()

        executor.execute(Runnable {
            val selectionClause = "${Vehicle.COLUMN_PK} LIKE ?"
            val selectionArgs = arrayOf(vehicle.id.toString())


            context?.applicationContext?.contentResolver?.delete(
                    Uri.parse("${VehicleProvider.URI_VEHICLE}/${vehicle.id}"),
                    selectionClause,
                    selectionArgs

            )



            Handler(Looper.getMainLooper()).post(Runnable {
                adapter.removeItem(vehicle)
                Toast.makeText(context,"Elemento eliminado!",Toast.LENGTH_SHORT).show()
            })
        })
    }
```



Para recuperar los vehículos, hay qué declarar el siguiente callback para el cursor:

```kotlin
 private val loaderCallbacks: LoaderManager.LoaderCallbacks<Cursor> = object : LoaderManager.LoaderCallbacks<Cursor> {
        override fun onCreateLoader(id: Int, @Nullable args: Bundle?): Loader<Cursor?> {
            Log.d("Vehicles","holamei")
            Log.d("Vehicles","${VehicleProvider.URI_VEHICLE}")
            return CursorLoader(requireContext().applicationContext,
                    Uri.parse("${VehicleProvider.URI_VEHICLE}"),
                    arrayOf(
                            Vehicle.COLUMN_PK,
                            Vehicle.COLUMN_BRAND,
                            Vehicle.COLUMN_MODEL,
                            Vehicle.COLUMN_PLATES,
                            Vehicle.COLUMN_WORKING
                    ),
                    null,
                    null,
                    null
            )
        }

        override fun onLoadFinished(loader: Loader<Cursor?>, data: Cursor?) {

            Log.d("Vehicles","termina, ${data?.count}")

            data?.apply {
                // Determine the column index of the column named "word"
                val pkIndex: Int = getColumnIndex(Vehicle.COLUMN_PK)
                val brandIndex: Int = getColumnIndex(Vehicle.COLUMN_BRAND)
                val modelIndex: Int = getColumnIndex(Vehicle.COLUMN_MODEL)
                val platesIndex: Int = getColumnIndex(Vehicle.COLUMN_PLATES)
                val workingIndex: Int = getColumnIndex(Vehicle.COLUMN_WORKING)

                /*
                 * Moves to the next row in the cursor. Before the first movement in the cursor, the
                 * "row pointer" is -1, and if you try to retrieve data at that position you will get an
                 * exception.
                 */
                while (moveToNext()) {
                    // Gets the value from the column.
                    val pk = getInt(pkIndex)
                    val brand = getString(brandIndex)
                    val model = getString(modelIndex)
                    val plates = getString(platesIndex)

                    val vehicle = Vehicle(
                            id = pk,
                            brand = brand,
                            model = model,
                            platesNumber = plates,
                            isWorking = true
                    )


                    vehicleArray.add(vehicle)
                }
            }




            adapter = VehicleAdapter(vehicleArray, getListener())
            recyclerVehicle.adapter = adapter
        }

        override fun onLoaderReset(loader: Loader<Cursor?>) {
            recyclerVehicle.adapter = null
        }
    }
```



y llamarlo en ___onCreateView___:

```kotlin
LoaderManager.getInstance(this).initLoader(1,null,loaderCallbacks)
```



Para prepoblar nuestra base de datos, podemos recurrir al método prepopulate() (usarlo una sola vez en onCreateView).

Paso 5: para gregar un nuevo vehículo, usaríamos esto.

```kotlin
addButton.setOnClickListener{

            val executor: ExecutorService = Executors.newSingleThreadExecutor()

            executor.execute(Runnable {
                val values = ContentValues().apply {
                    put(Vehicle.COLUMN_BRAND, brandEdit.text.toString())
                    put(Vehicle.COLUMN_MODEL, modelEdit.text.toString())
                    put(Vehicle.COLUMN_PLATES, platesEdit.text.toString())
                    put(Vehicle.COLUMN_WORKING, workingSwitch.isEnabled)
                }


                context?.applicationContext?.contentResolver?.insert(
                        Uri.parse("${VehicleProvider.URI_VEHICLE}"),
                        values
                )

                Handler(Looper.getMainLooper()).post(Runnable {
                    findNavController().navigate(
                            R.id.action_addEditFragment_to_vehicleListFragment
                    )
                })
            })

        }
```









[`Anterior`](../Reto-01) | [`Siguiente`](../Reto-02)      

</div>

