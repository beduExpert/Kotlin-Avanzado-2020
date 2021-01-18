 [`Kotlin Avanzado`](../../Readme.md) > [`Sesión 07`](../Readme.md) > ` `

## Reto 2

<div style="text-align: justify;">




### 1. Objetivos :dart:

- Enriquecer los conocimientos del [Ejemplo 3](https://github.com/beduExpert/C1-Kotlin-Avanzado/blob/master/Sesion-07/Ejemplo-03)

### 2. Requisitos :clipboard:

1. Haber cursado el ejemplo anterior

### 3. Desarrollo :computer:

Usaremos de base el ejercicio anterior. Con la siguiente lista de operators, cumplir las siguientes condiciones:

1. No repetir ningún nombre
2. Eliminar el primer y el último elemento
3. Agregar el prefijo Dr. a todos los nombres
4. Mostrar sólo el tercer nombre de la lista
5. Eliminar el tercer nombre de la lista


<details>
	<summary>Solucion</summary>

1. 
 > .distinct()

2. 
> .filter{it!=nameList.get(0) && it!=nameList.get(nameList.size-1) }

3.
> .map { name -> "Dr. $name" }

4.
> .filter{it==nameList.get(1)}

5. 
>.filter{it!=nameList.get(2)}

</details>

[`Anterior`](../Ret) | [`Siguiente`](../)      

</div>

