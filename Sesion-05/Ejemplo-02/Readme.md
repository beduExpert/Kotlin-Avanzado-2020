[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 04`](../Readme.md) > `Ejemplo 2 `

## Ejemplo 2: Content Providers

<div style="text-align: justify;">


### 1. Objetivos :dart:

* Proveer contenido de la app a aplicaciones externas
* Acceder a información de una app externa

### 2. Requisitos :clipboard:



### 3. Desarrollo :computer:

Vamos a comenzar explicando cómo podemos utilizar el content provider que implementamos en el artículo anterior para acceder a los datos de los clientes. Para no complicar mucho el ejemplo ni hacer más dificil las pruebas y la depuración en el emulador de Android vamos a hacer uso el content provider desde la propia aplicación de ejemplo que hemos creado. De cualquier forma, el código necesario sería exactamente igual si lo hiciéramos desde otra aplicación distinta.

Utilizar un content provider ya existente es muy sencillo, sobre todo comparado con el laborioso proceso de construcción de uno nuevo. Para comenzar, debemos obtener una referencia a un *Content Resolver*, objeto a través del que realizaremos todas las acciones necesarias sobre el content provider. Esto es tan fácil como utilizar el método getContentResolver() desde nuestra actividad para obtener la referencia indicada. Una vez obtenida la referencia al *content resolver*, podremos utilizar sus métodos query(), update(), insert() y delete() para realizar las acciones equivalentes sobre el *content provider*. Por ver varios ejemplos de la utilización de estos métodos añadiremos a nuestra aplicación de ejemplo tres botones en la pantalla principal, uno para hacer una consulta de todos los clientes, otro para insertar registros nuevos, y el último para eliminar todos los registros nuevos insertados con el segundo botón.

Empecemos por la consulta de clientes. Comenzaremos por definir un array con los nombres de las columnas de la tabla que queremos recuperar en los resultados de la consulta, que en nuestro caso serán el ID, el nombre, el teléfono y el email. Tras esto, obtendremos como dijimos antes una referencia al *content resolver* y utilizaremos su método query() para obtener los resultados en forma de *cursor*.  la Uri del content provider al que queremos acceder, el array de columnas que queremos recuperar, el criterio de selección, los argumentos variables, y el criterio de ordenación de los resultados. En nuestro caso, para no complicarnos utilizaremos tan sólo los dos primeros, pasándole el CONTENT_URI de nuestro provider y el array de columnas que acabamos de definir.

```java
//Columnas de la tabla a recuperar
String[] projection = new String[] {  Clientes._ID, Clientes.COL_NOMBRE,  Clientes.COL_TELEFONO,  Clientes.COL_EMAIL }; 
Uri clientesUri = ClientesProvider.CONTENT_URI; 
ContentResolver cr = getContentResolver(); 
//Hacemos la consulta
Cursor cur = cr.query(clientesUri,    projection, 
                      //Columnas a devolver
                     null,    //Condición de la query   
                      null,    //Argumentos variables de la query
                      null);   //Orden de los resultados
```

Hecho esto, tendremos que recorrer el cursor para procesar los resultados. Para nuestro ejemplo, simplemente los escribiremos en un cuadro de texto (txtResultados) colocado bajo los tres botones de ejemplo. Veamos cómo quedaría el código:

```java
if (cur.moveToFirst()){
  String nombre; 
  String telefono;  
  String email;  
  int colNombre = cur.getColumnIndex(Clientes.COL_NOMBRE);
  int colTelefono = cur.getColumnIndex(Clientes.COL_TELEFONO);
  int colEmail = cur.getColumnIndex(Clientes.COL_EMAIL);  txtResultados.setText("");
    do{    
      nombre = cur.getString(colNombre);
      telefono = cur.getString(colTelefono);    
      email = cur.getString(colEmail);    
      txtResultados.append(nombre + " - "+ telefono + " - "+ email + "\n");} 
    while(cur.moveToNext());}
```

Para insertar nuevos registros, el trabajo será también exactamente igual al que se hace al tratar directamente con bases de datos SQLite. Rellenaremos en primer lugar un objeto ContentValues con los datos del nuevo cliente y posteriormente utilizamos el método insert() pasándole la URI del content provider en primer lugar, y los datos del nuevo registro como segundo parámetro.

```java
ContentValues values = new ContentValues();
values.put(Clientes.COL_NOMBRE, "ClienteN");
values.put(Clientes.COL_TELEFONO, "999111222");
values.put(Clientes.COL_EMAIL, "nuevo@email.com");
ContentResolver cr = getContentResolver();
cr.insert(ClientesProvider.CONTENT_URI, values);
```

Por último, y más sencillo todavía, la eliminación de registros la haremos directamente utilizando el método delete() del *content resolver*, indicando como segundo parámetro el criterio de localización de los registros que queremos eliminar, que en este caso serán los que hayamos insertado nuevos con el segundo botón de ejemplo (aquellos con nombre = ‘ClienteN’).

```java
ContentResolver cr = getContentResolver();
cr.delete(ClientesProvider.CONTENT_URI,    Clientes.COL_NOMBRE + " = 'ClienteN'", null);
```



[`Anterior`](../Reto-01) | [`Siguiente`](../Reto-02)      

</div>

