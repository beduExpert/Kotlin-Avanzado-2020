[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 04`](../Readme.md) > `Reto 2` 

## Reto 2: Content Providers

<div style="text-align: justify;">




### 1. Objetivos :dart:

* Obtener el historial de llamadas desde nuestra app.

### 2. Requisitos :clipboard:



### 3. Desarrollo :computer:

vamos a registrar varias llamadas en el emulador de Android, de forma que los resultados de la consulta al historial de llamadas contenga algunos registros. Haremos por ejemplo varias llamadas salientes desde el emulador y simularemos varias llamadas entrantes desde el DDMS. Las primeras son sencillas, simplemente ve al emulador, accede al teléfono,marca y descuelga igual que lo harías en un dispositivo físico. Y para emular llamadas entrantes podremos hacerlo una vez más desde Eclipse, accediendo a la vista del DDMS. En esta vista, si accedemos a la sección «*Emulator Control*» veremos un apartado llamado «*Telephony Actions*«. Desde éste, podemos introducir un número de teléfono origen cualquiera y pulsar el botón «*Call*» para conseguir que nuestro emulador reciba una llamada entrante. Sin aceptar la llamada en elemulador pulsaremos «Hang Up» para teminar la llamada simulando así una llamada perdida.



[`Anterior`](../Ejemplo-02) | [`Siguiente`](../Proyecto)      

</div>