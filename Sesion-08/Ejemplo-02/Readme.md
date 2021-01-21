[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 08`](../Readme.md) > `Ejemplo 2`

## Ejemplo 2: Pruebas de integración

<div style="text-align: justify;">




### 1. Objetivos :dart:

* Realizar pruebas entre clases interactuando entre sí. 
* Ejecutar pruebas en la interfaz gráfica de la app.

### 2. Requisitos :clipboard:



### 3. Desarrollo :computer:

Puedes ejecutar pruebas de unidades instrumentadas en un dispositivo físico o en un emulador. Sin embargo, esta forma de prueba implica tiempos de ejecución mucho más lentos que los correspondientes a las pruebas de unidades locales, por lo que se recomienda confiar en este método solo cuando es esencial para evaluar el comportamiento de la app frente al hardware real del dispositivo.

Al ejecutar pruebas instrumentadas, AndroidX Test utiliza los siguientes subprocesos:

- El *subproceso principal*, también conocido como "subproceso de la IU" o "subproceso de la actividad", es el lugar donde se producen eventos de interacciones con la IU y el ciclo de vida de la actividad.
- El *subproceso de instrumentación* es el lugar donde se ejecutan la mayoría de las pruebas. Cuando comienza el conjunto de pruebas, la clase `AndroidJUnitTest` inicia este subproceso.



### Cómo escribir pruebas de nivel intermedio

Además de probar cada unidad de tu app mediante la ejecución de pruebas de nivel inferior, debes validar el comportamiento de tu app desde nivel del módulo. Para ello, escribe pruebas de nivel intermedio, que son pruebas de integración que validan la colaboración y la interacción de un grupo de unidades.

para definir la mejor manera de representar grupos de unidades en la app:

1. Interacciones entre una vista y un modelo de vista, como probar un objeto [`Fragment`](https://developer.android.com/reference/androidx/fragment/app/Fragment), validar el XML del diseño o evaluar la lógica de vinculación de datos de un objeto [`ViewModel`](https://developer.android.com/reference/androidx/lifecycle/ViewModel)
2. Pruebas en la capa de repositorio de tu app, que verifican que las diferentes fuentes de datos y los objetos de acceso a datos (DAO) interactúan de la forma esperada
3. Porciones verticales de la app, que prueban las interacciones en una pantalla determinada. (esa prueba verifica las interacciones en todas las capas de la pila de tu app)
4. Pruebas de varios fragmentos que evalúan un área específica de la app (a diferencia de los otros tipos de pruebas de nivel intermedio mencionados en la lista, este tipo de prueba en general requiere un dispositivo real porque la interacción durante las pruebas involucra varios elementos de la IU)



Para usar Espresso, agrega la siguiente dependencia al archivo de compilación de Gradle de su aplicación.

```kotlin
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])

    testImplementation 'junit:junit:4.12'

    // Android runner and rules support
    androidtestImplementation 'com.android.support.test:runner:0.5'
    androidtestImplementation 'com.android.support.test:rules:0.5'

    // Espresso support
    androidtestImplementation('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })

    // add this for intent mocking support
    androidtestImplementation 'com.android.support.test.espresso:espresso-intents:2.2.2'

    // add this for webview testing support
    androidtestImplementation 'com.android.support.test.espresso:espresso-web:2.2.2'

}
```

Al crear nuestro Test, el código debe quedar la siguiente forma:

```kotlin
package com.vogella.android.espressofirst;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


@RunWith(AndroidJUnit4.class)
public class MainActivityEspressoTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
        new ActivityTestRule<>(MainActivity.class);

    @Test
    public void ensureTextChangesWork() {
        // Type text and then press the button.
        onView(withId(R.id.inputField))
                .perform(typeText("HELLO"), closeSoftKeyboard());
        onView(withId(R.id.changeText)).perform(click());

        // Check that the text was changed.
        onView(withId(R.id.inputField)).check(matches(withText("Lalala")));
    }

    @Test
    public void changeText_newActivity() {
        // Type text and then press the button.
        onView(withId(R.id.inputField)).perform(typeText("NewText"),
                closeSoftKeyboard());
        onView(withId(R.id.switchActivity)).perform(click());

        // This view is in a different Activity, no need to tell Espresso.
        onView(withId(R.id.resultView)).check(matches(withText("NewText")));
    }
}
```



Las acciones a lanzarse en la interfaz gráfica se pueden resumir en estas funciones:



```kotlin
onView(withText(startsWith("ABC"))).perform(click()); 

onView(withText(endsWith("YYZZ"))).perform(click()); 

onView(withId(R.id.viewId)).check(matches(withContentDescription(containsString("YYZZ")))); 

onView(withText(equalToIgnoringCase("xxYY"))).perform(click()); 
 -
onView(withText(equalToIgnoringWhiteSpace("XX YY ZZ"))).perform(click()); 

onView(withId(R.id.viewId)).check(matches(withText(not(containsString("YYZZ"))))); 
```

A través de InstrumentationRegistry.getTargetContext (), uno tiene acceso al contexto de destino de su aplicación. Por ejemplo, si se requiere usar el id sin usar R.id, se puede usar el siguiente método auxiliar para determinarlo.

[`Anterior`](../) | [`Siguiente`](../)      

</div>

