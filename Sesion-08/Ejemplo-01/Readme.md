[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 07`](../Readme.md) > `Ejemplo `

## Ejemplo 1: Pruebas unitarias

<div style="text-align: justify;">




### 1. Objetivos :dart:

* Realizar pruebas de clases y métodos ailsaldos  en específico.

### 2. Requisitos :clipboard:



### 3. Desarrollo :computer:

En el proyecto de Android Studio, se debe almacenar los archivos de origen para las pruebas unitarias locales en module-name / src / test / java /. Este directorio ya existe cuando crea un nuevo proyecto.

También se debe configurar las dependencias de prueba para que su proyecto utilice las API estándar proporcionadas por el marco JUnit 4. Si su prueba necesita interactuar con las dependencias de Android, incluir Robolectric o la biblioteca Mockito para simplificar sus pruebas de unidades locales.

```kotlin
dependencies {
    // Required -- JUnit 4 framework
    testImplementation 'junit:junit:4.12'
    // Optional -- Robolectric environment
    testImplementation 'androidx.test:core:1.0.0'
    // Optional -- Mockito framework
    testImplementation 'org.mockito:mockito-core:1.10.19'
}
```



La clase  debe escribirse como una clase de prueba JUnit 4. JUnit es el marco de pruebas unitarias más popular y ampliamente utilizado para Java. JUnit 4 le permite escribir pruebas de una manera más limpia y flexible que sus versiones predecesoras porque JUnit 4 no requiere que haga lo siguiente:

Amplíe la clase junit.framework.TestCase.
Prefije el nombre de su método de prueba con la palabra clave 'prueba'.
Utilice clases de los paquetes junit.framework o junit.extensions.
Para crear una clase de prueba básica de JUnit 4, cree una clase que contenga uno o más métodos de prueba. Un método de prueba comienza con la anotación @Test y contiene el código para ejercitar y verificar una única funcionalidad en el componente que desea probar.

El siguiente ejemplo muestra cómo podría implementar una clase de prueba de unidad local. El método de prueba emailValidator_CorrectEmailSimple_ReturnsTrue verifica que el método isValidEmail () en la aplicación bajo prueba devuelve el resultado correcto.

```kotlin
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class EmailValidatorTest {
    @Test
    fun emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertThat(EmailValidator.isValidEmail("name@email.com")).isTrue()
    }
}
```



[`Anterior`](../) | [`Siguiente`](../)      

</div>

