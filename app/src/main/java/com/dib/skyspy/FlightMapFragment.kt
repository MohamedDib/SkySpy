package com.dib.skyspy

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import com.example.skyspy.R
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.model.CameraPosition

import com.google.android.gms.maps.model.MarkerOptions

import com.google.android.gms.maps.model.LatLng

import com.google.android.gms.maps.GoogleMap

import com.google.android.gms.maps.OnMapReadyCallback

import com.google.android.gms.maps.MapsInitializer
import java.lang.Exception


class FlightMapFragment : Fragment(),OnMapReadyCallback {


    companion object {
        fun newInstance() = FlightMapFragment()
    }

    private lateinit var viewModel: FlightMapViewModel
    private lateinit var sharedViewModel: SharedFlightViewModel
    private lateinit var mapView: MapView
    private var googleMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {

            viewModel = ViewModelProvider(this).get(FlightMapViewModel::class.java)
            sharedViewModel = ViewModelProvider(requireActivity()).get(SharedFlightViewModel::class.java)

            var view = inflater.inflate(R.layout.flight_map_fragment, container, false)

            mapView = view.findViewById<MapView>(R.id.flight_map)


            val textSpeed = view?.findViewById<TextView>(R.id.textview_speed)
            val textAltitude = view?.findViewById<TextView>(R.id.textview_altitude)

            try {
                MapsInitializer.initialize(requireActivity().applicationContext)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            mapView.onCreate(savedInstanceState);

            mapView.getMapAsync( this);


        mapView.getMapAsync(OnMapReadyCallback { mMap ->
            googleMap = mMap

            // For showing a move to my location button
            if (ActivityCompat.checkSelfPermission(
                    this.requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this.requireActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

            }
            //googleMap.setMyLocationEnabled(true)

            // GET AIRCAFT POSITION
            // Send request to get flight states after the user select a flight
            sharedViewModel.getSelectedFlightLiveData().observe(viewLifecycleOwner, Observer {
                Toast.makeText(this.requireActivity(), "Flight : "+it.icao24, Toast.LENGTH_SHORT).show()
                viewModel.getFlightStates(it.icao24,it.lastSeen)
            })

            // Check the request status
            viewModel.getRequestStatusLiveData().observe(this.requireActivity(), Observer {
                if (it == 400) {
                    Toast.makeText(this.requireActivity(), "Connexion error", Toast.LENGTH_SHORT).show()
                } else if (it == 200) {
                    //Open Activity
                    Toast.makeText(this.requireActivity(), "Getting last seen location", Toast.LENGTH_SHORT).show()
                }
            })

            // Get flight state
            viewModel.getFlightStatesLiveData().observe(this.requireActivity(), Observer {

                ("Speed m/s :" +it.velocity.toString()).also { textSpeed?.text = it }
                ("Altitude (m) :" + it.geo_altitude.toString()).also { textAltitude?.text = it }

                val lat: Double = it.latitude.toString().toDouble()
                val lng: Double = it.longitude.toString().toDouble()
                val pos = LatLng(lat, lng)
                googleMap?.addMarker(
                    MarkerOptions().position(pos).title("Airplane ").snippet("")
                )

                // For zooming automatically to the location of the marker
                val cameraPosition = CameraPosition.Builder().target(pos).zoom(12f).build()
                googleMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            })


        })
        // The required permissions...
        // The required permissions...
        val REQUIRED_PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.INTERNET
        )
        requestPermissions(REQUIRED_PERMISSIONS, 0)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val textView = view?.findViewById<TextView>(R.id.textview_callsign)

        viewModel = ViewModelProvider(this).get(FlightMapViewModel::class.java)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedFlightViewModel::class.java)


        sharedViewModel.getSelectedFlightLiveData().observe(viewLifecycleOwner, Observer {
            textView?.text = it.callsign
        })
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onMapReady(p0: GoogleMap?) {

    }

}