[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 03`](../Readme.md) > `Reto 1`

## Reto 1: Localización y GPS

<div style="text-align: justify;">




### 1. Objetivos :dart:

Completar el código del ejemplo anterior para tener una experiencia de localización más integral

### 2. Requisitos :clipboard:

Haber finalizado el [Ejemplo 1](../Ejemplo-01)

### 3. Desarrollo :computer:

nvestigar para poder hacer las siguientes tareas:

1. Abrir el menú de prender localización cuando este no esté prendido

2. Localizar automáticamente después de obtener el permiso de localización



<details>
	<summary>Solucion</summary>


1. crear este método para ir a la pantalla de prender GPS

```kotlin
private fun goToTurnLocation(){
        Toast.makeText(this, "Debes prender el servicio de GPS", Toast.LENGTH_LONG).show()
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }
```

 e implementarlo en *getLocation*

```kotlin
if (isLocationEnabled()) { //localizamos sólo si el GPS está encendido
   ...
} else{
	goToTurnLocation()
}
```


2. Implementar este código

```kotlin
override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        //Esta condicionante implica que se respondió una petición de permisos GPS
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLocation()
            }
        }
    }
```

</details>

[`Anterior`](../Ejemplo-01) | [`Siguiente`](../Ejemplo-02)      

</div>

