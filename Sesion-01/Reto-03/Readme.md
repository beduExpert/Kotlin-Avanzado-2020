

[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 01`](../Readme.md) > `Reto 2`

## Reto 3: Animaciones y Transiciones

<div style="text-align: justify;">


### 1. Objetivos :dart:

- Implementar el conocimiento adquirido en el módulo de video de la sesión 1
- Utilizar nuevos métodos y otras utilidades para guardar estado de un video

### 2. Requisitos :clipboard:

1. Android Studio Instalado en nuestra computadora.

### 3. Desarrollo :computer:

Reutilizando el [Ejemplo 03](../Ejemplo-03), se harán los siguientes cambios: 

a) Activity Transitions
 - Probar nuevamente el módulo de Actividades compartidas y ver el efecto al armarse la Actividad 2

 - Modificar *TransitionedActivity* para cambiar la transición de entrada y salida,
 ```kotlin
 title = "Receptor" //agregar este valor para distinguir entre las barras de las distintas actividades
 
window.enterTransition = Slide()
kotlin.existTransition = Explode()
 ```
¿De qué manera afecta el código modificado a la animación de entrada/salida de dicha actividad?


<details>

<summary>Respuesta</summary>

las barras de sistema también son afectados por el método, por eso hay que restringir su animación a dichos objetos. Hay que encontrar una forma para evitar esto.

</details>

b) Ahora, tanto en SharedTransitionActivity como en TransitionedActivity, implemenentar código para que las barras no se vean afectadas por la animación.

```kotlin
	val transition = Explode()
        val decor = window.decorView

	//con excludeTarget, los layouts aquí declarados, no son tomados en cuenta en la transición de entrada y salida
        transition.excludeTarget(decor.findViewById<View>(R.id.action_bar_container), true)
        transition.excludeTarget(android.R.id.statusBarBackground, true)
        transition.excludeTarget(android.R.id.navigationBarBackground, true)

        window.enterTransition = transition
        window.exitTransition = transition
```

Aplicar cambios y correr la aplicación. Qué sucedio a partir de este cambio? comenten en conjunto

c) En SharedTransitionActivity, crear un TextView que será borrado del layout.
```xml
    <TextView
        android:id="@+id/tvDeleteMe"
        android:text="ESTE ES UN TEXTO DE PRUEBA QUE SU OBJETIVO ES SER BORRADO"
        android:layout_height="wrap_content"
        android:layout_width="wrap_content"
        android:layout_centerInParent="true"/>
```

setear el onClickListener del ViewGroup padre para borrar al texto, poniendo la siguientes líneas:

```kotlin
shared_layout.setOnClickListener{
            val transition = Slide()
            TransitionManager.beginDelayedTransition(shared_layout, transition)
            shared_layout.removeView(tvDeleteMe)
        }
```
¿Qué sucedió? beginDelagedTransition permite generar transiciones sin necesidaad de escenas (captura el estado actual y al remover o agregar alguna View, estas se animan conforme a la transición seleccionada).

Ahora, experimenta con Fade() y con Explode() para ver lo que occurre.
	

d) Ahora toca el turno de la animación de la nave, ¿Qué sucederá al cambiar el LinearInterpolator en moveArwing por el de AccelerateInterpolator? comentar resultado
```kotlin
valueAnimator.interpolator = AccelerateInterpolator()
valueAnimator.duration = 1500
```

e) Ir ahora a la función barrelRoll ¿Qué sucede si cambiamos el interpolador lineal por uno de AccelerateDecelerate. Comenta los cambios en clase.



[`Anterior`](../Ejemplo-03/Readme.md) | [`Siguiente`](../Proyecto/Readme.md)

</div>