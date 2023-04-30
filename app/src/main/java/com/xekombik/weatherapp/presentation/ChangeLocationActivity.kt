package com.xekombik.weatherapp.presentation

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource


import com.xekombik.weatherapp.databinding.ActivityChangeLocationBinding

import com.xekombik.weatherapp.databinding.LocationItemCardViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ChangeLocationActivity : AppCompatActivity() {

    private val vm by viewModel<MainViewModel>()
    private lateinit var binding: ActivityChangeLocationBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)

        binding.searchView.setOnEditorActionListener { _, _, _ ->
            if (binding.searchView.text.length <= 2) {
                Toast.makeText(this, "Please, input more letter!", Toast.LENGTH_SHORT).show()
            } else {
                startSearch(binding.searchView.text.toString())
                closeKeyboard()
            }
            true
        }

        binding.currentLocation.setOnClickListener {
            setLocation()
        }
    }


    private fun isLocationEnabled(): Boolean {
        val lm = this.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }



    private fun setLocation() {

        if (!isLocationEnabled()) {
            Toast.makeText(this, "Turn on location!", Toast.LENGTH_SHORT).show()
        } else {



            val ct = CancellationTokenSource()
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    1
                )
            }
            else{
                fusedLocationProviderClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    ct.token
                )
                    .addOnCompleteListener {
                        val completeLocation = "${it.result.latitude}, ${it.result.longitude}"
                        sharedPreferences.edit().putString("location", completeLocation).apply()
                        sharedPreferences.edit().putBoolean("isChosenLocationMode", true).apply()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Please, allow get device's location", Toast.LENGTH_SHORT)
                            .show()
                    }
            }

        }
    }


    private fun closeKeyboard() {
        val keyboard = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.hideSoftInputFromWindow(binding.searchView.windowToken, 0)
        binding.searchView.clearFocus()
    }

    private fun startSearch(place: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val placeList = vm.getPlace(place)

            runOnUiThread {
                binding.countryList.removeAllViews()
                if (placeList.isEmpty()) {
                    binding.searchIconIV.visibility = VISIBLE
                    binding.showErrorTextView.visibility = VISIBLE
                } else {
                    binding.searchIconIV.visibility = GONE
                    binding.showErrorTextView.visibility = GONE
                }
                for (placeItem in placeList) {
                    val vB = LocationItemCardViewBinding.inflate(
                        LayoutInflater.from(this@ChangeLocationActivity),
                        binding.countryList,
                        false
                    )
                    vB.placeTextView.text = placeItem
                    vB.placeCardView.setOnClickListener {
                        sharedPreferences.edit().putString("location", placeItem).apply()
                        sharedPreferences.edit().putBoolean("isChosenLocationMode", false).apply()
                        finish()
                    }
                    binding.countryList.addView(vB.root)
                }
            }
        }
    }
}

