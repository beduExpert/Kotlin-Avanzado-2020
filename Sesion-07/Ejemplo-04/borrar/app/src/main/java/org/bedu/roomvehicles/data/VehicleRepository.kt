package org.bedu.roomvehicles.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.bedu.roomvehicles.data.local.Vehicle
import org.bedu.roomvehicles.data.local.VehicleDao

class VehicleRepository(
    private val vehicleDao: VehicleDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    fun getVehicles(): List<Vehicle> {
        return vehicleDao.getVehicles()
    }

    suspend fun populateVehicles(vehicles: List<Vehicle>) = withContext(ioDispatcher){
        return@withContext vehicleDao.insertAll(vehicles)
    }


}