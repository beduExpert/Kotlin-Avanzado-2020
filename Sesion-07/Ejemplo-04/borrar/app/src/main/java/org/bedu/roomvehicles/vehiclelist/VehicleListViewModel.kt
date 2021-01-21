package org.bedu.roomvehicles.vehiclelist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.bedu.roomvehicles.data.VehicleRepository
import org.bedu.roomvehicles.data.local.Vehicle

class VehicleListViewModel(
    private val vehicleRepository: VehicleRepository): ViewModel(){

    private var _vehicles: List<Vehicle> = listOf()

    init{

        //prepopulate()
    }

    fun getVehicleList(): List<Vehicle>{
        _vehicles = vehicleRepository.getVehicles()
        return _vehicles
    }

    fun prepopulate(){
        val vehicles = listOf(
            Vehicle(model = "Vento",brand = "Volkswagen",platesNumber = "STF0321",isWorking = true),
            Vehicle(model = "Jetta",brand = "Volkswagen",platesNumber = "FBN6745",isWorking = true)
        )

        viewModelScope.launch {
            vehicleRepository.populateVehicles(vehicles)
        }

    }

}