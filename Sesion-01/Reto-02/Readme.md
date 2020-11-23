[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 01`](../Readme.md) > `Reto 2`

## Reto 2: Reproducción de video

<div style="text-align: justify;">


### 1. Objetivos :dart:

- Implementar el conocimiento adquirido en el módulo de video de la sesión 1
- Utilizar nuevos métodos y otras utilidades para guardar estado de un video

### 2. Requisitos :clipboard:

1. Android Studio Instalado en nuestra computadora.

### 3. Desarrollo :computer:

Vamos a crear un reproductor de Video capaz de entrar en modo fullscreen al rotar el video, como en youtube.
Debido a que existen temas sin tocar, vamos a facilitar snippets para que sean fácilmente implementados.

1.- Crear un proyecto, tomando de base lo visto en el [Ejemplo 2](../Ejempo-02). 

2.- Determinaremos un layout con el VideoView ocupando toda la actividad, cuando la orientación del dispositivo sea Landscape. Crearemos en res el directorio **layout-land** que contendrá  el layout *activity_main.xml* en horizontal.

<img src="Images/image-01.png" width="30%">

3.- Crear el MediaController que nos da por defecto android y asignarlo a nuestro VideoView.

4.- Asignar el recurso multimedia al VideoView y reproducirlo.

5.- Reproducir el ejercicio ¿Qué sucede al cambiar de orientación? ¿Qué características aún no hemos cumplido? Discútanlo en conjunto y cuando lo hallan determinado, descubran la solución y continuen al paso 4.

<details>


<summary>Respuesta</summary>


Recordando el tema de ciclos de vida, cuando existe rotación, la actividad en curso se destruye y se crea una nueva, por lo que se necesita restaurar el estado del video. con esto tenemos que nos falta: 

* Entrar a modo fullscreen (ocultando las barras de sistema)
* Guardar el estado del video (si está pausado, el segundo en que estaba reproduciéndose)

</details>


6.- declarar estos métodos que se encargan de setear el modo inmersivo y el modo normal mediante banderas: 

```kotlin
    //Habilitando el modo inmersivo
    private fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                // El contenido aparecerá bajo las barras de sistema
                // para que no cambie de tamaño cuando estas aparezcan/desaparezcan
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Esconde el nav Bar y el Status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    // Removemos todas las banderas para que las barras de sistema vuelvan a mostrarse
    private fun showSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
    }
```

7.- ¿En qué callback de nuestro ciclo de vida debería ir la lógica para cambiar a fullscreen/normal? tomen su desición e implementen las funciones declaradas en el paso anterior.

> con *resources.configuration.orientation* se obtiene la orientación actual del dispositivo. *Configuration.ORIENTATION_LANDSCAPE* es el valor correspondiente a la posición landscape.



8.- Declarar estas banderas como *variables de clase*: 

```kotlin
   val PLAY_TIME = "PLAY_TIME"
    val IS_PLAYING = "IS_PLAYING"
```

9.- Guardar el estado del video, recordar que el VideoView contiene MediaPlayer, y este último tiene métodos para obtener parámetros de la sesión multimedia.

```kotlin
//Paso 9: Guardar el estado del video antes de que la Activity se destruya
    override fun onSaveInstanceState(out: Bundle) {
        super.onSaveInstanceState(out)
        //guardando la posición actual del video y si se está reproduciendo
        out.putInt(PLAY_TIME, <AQUÍ VA EL TIEMPO DE GUARDADO>)
        out.putBoolean(IS_PLAYING, <AQUÍ VA UN BOOLENAO DETERMINANDO SI EL VIDEO ESTA REPRODUCIÉNDOSE>)
    }
```

10.- Después de la recreación de la Actividad, se debe restaurar el estado anterior:

```kotlin
    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        // Recuperando el tiempo transcurrido de reproducción
        var seekValue = 0
        val playTime = savedInstanceState?.getInt(PLAY_TIME)
        if(playTime != null){
            seekValue = playTime
        }

        //AQUÍ EL CÓDIGO PARA REESTABLECER EL TIEMPO TRANSCURRIDO

        // Vamos a regresar al estado anterior reproduciendo/pausado
        var isPlaying = savedInstanceState?.getBoolean(IS_PLAYING)
	
	//AQUÍ EL CÓDIGO PARA MANTENER EN PAUSA/EN REPRODUCCIÓN EL VÍDEO
    }
```



[`Anterior`](../Ejemplo-02/Readme.md) | [`Siguiente`](../Ejemplo-03/Readme.md)

</div>




