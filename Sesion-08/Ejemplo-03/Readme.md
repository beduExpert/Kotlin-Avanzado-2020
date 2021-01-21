[`Kotlin Avanzado`](../../Readme.md) > [`Sesión 08`](../Readme.md) > `Ejemplo 3`

## Ejemplo 3: Pruebas end-to-end

<div style="text-align: justify;">




### 1. Objetivos :dart:

* Realizar pruebas de flujo completas.

### 2. Requisitos :clipboard:



### 3. Desarrollo :computer:

Cuando se escribe una prueba unitaria para una parte de una clase (un método o una pequeña colección de métodos), su objetivo es probar solo el código en esa clase.

Probar solo el código en una clase o clases específicas puede ser complicado.  Abra la clase data.source.DefaultTaskRepository en el conjunto de fuentes principal. Este es el repositorio de la aplicación, y es la clase para la que escribirás pruebas unitarias a continuación.

Tu objetivo es probar solo el código de esa clase. Sin embargo, DefaultTaskRepository depende de otras clases, como LocalTaskDataSource y RemoteTaskDataSource, para funcionar. Otra forma de decir esto es que LocalTaskDataSource y RemoteTaskDataSource son dependencias de DefaultTaskRepository.



```kotlin
 suspend fun getTasks(forceUpdate: Boolean = false): Result<List<Task>> {
        if (forceUpdate) {
            try {
                updateTasksFromRemoteDataSource()
            } catch (ex: Exception) {
                return Result.Error(ex)
            }
        }
        return tasksLocalDataSource.getTasks()
    }
```



Entonces, cada método en DefaultTaskRepository llama a métodos en clases de fuente de datos, que a su vez llaman a métodos en otras clases para guardar información en una base de datos o comunicarse con la red.



```kotlin
class TasksLocalDataSource internal constructor(
    private val tasksDao: TasksDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TasksDataSource { ... }

object TasksRemoteDataSource : TasksDataSource { ... }
```



Para escribir una versión falsa de estos métodos:

Escribe getTasks: si las tareas no son nulas, devuelve un resultado de éxito. Si las tareas son nulas, devuelve un resultado de error.
Escribir deleteAllTasks: borra la lista de tareas mutables.
Escribir saveTask: agrega la tarea a la lista.
Esos métodos, implementados para FakeDataSource, se parecen al código siguiente.



```kotlin
companion object {
        @Volatile
        private var INSTANCE: DefaultTasksRepository? = null

        fun getRepository(app: Application): DefaultTasksRepository {
            return INSTANCE ?: synchronized(this) {
                val database = Room.databaseBuilder(app,
                    ToDoDatabase::class.java, "Tasks.db")
                    .build()
                DefaultTasksRepository(TasksRemoteDataSource, TasksLocalDataSource(database.taskDao())).also {
                    INSTANCE = it
                }
            }
        }
    }
```



Cree tres variables, dos variables miembro de FakeDataSource (una para cada fuente de datos de su repositorio) y una variable para DefaultTasksRepository que probará.



```kotlin
    private lateinit var tasksRemoteDataSource: FakeDataSource
    private lateinit var tasksLocalDataSource: FakeDataSource

    // Class under test
    private lateinit var tasksRepository: DefaultTasksRepository
```

[`Anterior`](../) | [`Siguiente`](../)      

</div>

