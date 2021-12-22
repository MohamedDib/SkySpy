package com.dib.skyspy

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.skyspy.R

/**
 * Created by sergio on 11/10/21
 * All rights reserved GoodBarber
 */
class FlightCell : RelativeLayout {

    var callSignTextView: TextView? = null
    var dateTextView: TextView? = null
    var departureDateTextView: TextView? = null
    var arrivalDateTextView: TextView? = null
    var departureAirportTextView: TextView? = null
    var arrivalAirportTextView: TextView? = null
    var durationTextView: TextView? = null


    constructor(context: Context?) : super(context) {
        initLayout()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initLayout()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initLayout()
    }

    private fun bindViews() {
        // make the find view by ids for your view
        callSignTextView = findViewById(R.id.textview_callsign)
        dateTextView = findViewById(R.id.textview_date)
        departureDateTextView = findViewById(R.id.textview_departure_time)
        arrivalDateTextView = findViewById(R.id.textview_arrival_time)
        departureAirportTextView = findViewById(R.id.textview_departure_airport)
        arrivalAirportTextView = findViewById(R.id.textview_arrival_airport)
        durationTextView = findViewById(R.id.textview_duration)
    }

    //CACA: c'est pas bien de traiter de la donnée dans une vue
    fun bindData(flight: FlightModel) {
        //fill your views
        dateTextView?.text = Utils.timestampToString(flight.lastSeen, true)
        callSignTextView?.text = flight.callsign
        departureAirportTextView?.text = flight.estDepartureAirport
        arrivalAirportTextView?.text = flight.estArrivalAirport
        departureDateTextView?.text = Utils.timestampToHourMinute(flight.firstSeen)
        arrivalDateTextView?.text = Utils.timestampToHourMinute(flight.lastSeen)
        durationTextView?.text = Utils.formatFlightDuration(flight.firstSeen)


    }

    private fun initLayout() {
        LayoutInflater.from(context).inflate(R.layout.flightcell_view, this, true)
        bindViews()
    }
}