package com.dib.skyspy

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FlightMapViewModel : ViewModel() {

    private val flightStatesLiveData = MutableLiveData<FlightStatesModel>()
    private val requestStatusLiveData = MutableLiveData<Int>()

/*    init {
        flightStatesLiveData.value = DataHolder.flightStates
    }*/

    fun getRequestStatusLiveData(): LiveData<Int> {
        return requestStatusLiveData
    }

    fun getFlightStatesLiveData(): LiveData<FlightStatesModel> {
        return flightStatesLiveData
    }

    fun getFlightStates(icao24: String, time : Long){

        //Créer l'URL
        var url ="https://opensky-network.org/api/states/all"
        url += "?time=${time}&icao24=${icao24}"

        viewModelScope.launch {
            requestStatusLiveData.value = 1 // 1 means pending
            withContext(Dispatchers.IO) {
                val result = RequestManager.getSuspended(url, HashMap())
                //CACA: requestStatusLiveData to trigger navigation is limit limit.
                if (result != null) {
                    Log.i("RESULT", result)
                    val flightStates = Utils.convertFlightStatesDataIntoObject(result)
                    flightStatesLiveData.postValue(flightStates)
                    requestStatusLiveData.postValue(200)
                } else {
                    requestStatusLiveData.postValue(400)
                }
            }


        }
        // stocker le résultat dans un singleton
    }

}