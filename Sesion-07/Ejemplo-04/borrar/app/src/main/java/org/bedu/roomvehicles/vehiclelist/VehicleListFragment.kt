package org.bedu.roomvehicles.vehiclelist

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.bedu.roomvehicles.R
import org.bedu.roomvehicles.VehiclesApplication
import org.bedu.roomvehicles.data.local.Vehicle
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


/**
 * A fragment representing a list of Items.
 */
class VehicleListFragment : Fragment(), ItemListener {


    private lateinit var addButton: FloatingActionButton
    private lateinit var recyclerVehicle: RecyclerView
    private lateinit var adapter: VehicleAdapter

    private lateinit var viewModel: VehicleListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    fun getListener(): ItemListener {
        return this
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_vehicle_list, container, false)

        viewModel = VehicleListViewModel(
                (requireContext().applicationContext as VehiclesApplication).vehicleRepository
        )

        addButton = view.findViewById(R.id.button_add)
        recyclerVehicle = view.findViewById(R.id.list)


        addButton.setOnClickListener {
            findNavController().navigate(
                    R.id.action_vehicleListFragment_to_addEditFragment
            )
        }




        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupVehicleList()

    }

    private fun setupVehicleList(){
        if(viewModel!=null){

            val executor: ExecutorService = Executors.newSingleThreadExecutor()

            executor.execute(Runnable {

                val vehicleArray =viewModel.getVehicleList()
                Log.d("Vehicle","Adapter, $vehicleArray")

                Handler(Looper.getMainLooper()).post(Runnable {
                    adapter = VehicleAdapter(vehicleArray?.toMutableList(), getListener())
                    recyclerVehicle.adapter = adapter
                })
            })




        }
    }

    override fun onEdit(vehicle: Vehicle) {


    }

    override fun onDelete(vehicle: Vehicle) {

    }

}