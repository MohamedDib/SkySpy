package com.dib.skyspy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toolbar
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.skyspy.R

class FlightListActivity : AppCompatActivity() {

    private lateinit var sharedViewModel: SharedFlightViewModel
    private lateinit var toolBar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_list)

        //toolBar=findViewById(R.id.myActionBar)
        //setActionBar(toolBar)
        val isTablet = findViewById<View>(R.id.fragment_map_container) != null
        Log.i("Tablet", "isTablet $isTablet")

        sharedViewModel = ViewModelProvider(this).get(SharedFlightViewModel::class.java)
        sharedViewModel.getSelectedFlightLiveData().observe(this, Observer {
            if (!isTablet) {
                // Remplacer le fragment
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<FlightMapFragment>(R.id.fragment_list_container)
                    addToBackStack(null)
                }
            }
        })


        val listFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_list_container) as FlightListFragment
    }
}
