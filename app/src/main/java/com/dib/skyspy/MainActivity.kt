package com.dib.skyspy

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.skyspy.R
import java.util.*


lateinit var viewModel: MainViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Hide action bar
        getSupportActionBar()?.hide();
        /**
         * Show airports list on a spinner
         */
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val airportSpinner = findViewById<Spinner>(R.id.spinner_airport)

        viewModel.getAirportListNamesLiveData().observe(this, Observer {
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,
                it
            ) //selected item will look like a spinner set from XML

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            airportSpinner.adapter = adapter
        })

        val fromDateTextView = findViewById<TextView>(R.id.textview_from_date)
        val toDateTextView = findViewById<TextView>(R.id.textview_to_date)
        val fromDateImageView = findViewById<ImageView>(R.id.imageViewFromDate)
        val toDateImageView = findViewById<ImageView>(R.id.imageViewToDate)

        viewModel.getFromCalendarLiveData().observe(this, Observer {
            fromDateTextView.text = Utils.dateToString(it.time)
        })

        viewModel.getToCalendarLiveData().observe(this, Observer {
            toDateTextView.text = Utils.dateToString(it.time)
        })

        fromDateImageView.setOnClickListener {
            showDatePicker(R.id.textview_from_date)
        }

        toDateImageView.setOnClickListener {
            showDatePicker(R.id.textview_to_date)
        }

        findViewById<Button>(R.id.button_search).setOnClickListener {
            // Récupérer info aeroroport selectionné
            val airportSelectedIndex = airportSpinner.selectedItemPosition
            // Départ ou arrivée
            val isArrival = findViewById<Switch>(R.id.switch_dep_arr).isChecked

            //Notifier le viewmodel
            viewModel.doSearch(airportSelectedIndex, isArrival)
        }

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val progressBar2 = findViewById<ProgressBar>(R.id.progressBar2)

        viewModel.getRequestStatusLiveData().observe(this, Observer {
            if (it == 1) { // pending
                progressBar.visibility = View.VISIBLE
                progressBar2.visibility = View.VISIBLE
            } else {
                progressBar.visibility = View.INVISIBLE
                progressBar2.visibility = View.INVISIBLE
            }

            if (it == 400) {
                Toast.makeText(this, "Connexion error", Toast.LENGTH_SHORT).show()
            } else if (it == 200) {
                //Open Activity
                startActivity(Intent(this, FlightListActivity::class.java))
            }
        })
    }

    private fun showDatePicker(clickedViewId: Int) {
        val calendar: Calendar =
            if (clickedViewId == R.id.textview_from_date) viewModel.getFromCalendarLiveData().value!! else viewModel.getToCalendarLiveData().value!!
        val datePickerDialog = DatePickerDialog(
            this,
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                viewModel.updateCalendar(
                    year,
                    monthOfYear,
                    dayOfMonth,
                    clickedViewId == R.id.textview_from_date
                )
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.datePicker.maxDate = Calendar.getInstance().timeInMillis
        datePickerDialog.show()
    }
}