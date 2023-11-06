package com.example.demapp


import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.widget.FrameLayout

import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.android.play.integrity.internal.e
import java.io.IOException
import java.util.Locale


class LocationActivity : FragmentActivity(),OnMapReadyCallback {
    private lateinit var gmap: GoogleMap
    private lateinit var location: FrameLayout
    private lateinit var fusedClient: FusedLocationProviderClient
    private val REQUEST_CODE = 101
    private lateinit var searchView: SearchView
    private lateinit var marker: Marker
    private lateinit var currentLocation: Location


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        //val locationFragment =
        //supportFragmentManager.findFragmentById(R.id.location) as SupportMapFragment
        // locationFragment.getMapAsync(this);

        location = findViewById(R.id.location)

        searchView = findViewById(R.id.search)
        searchView.clearFocus()
        fusedClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();


        //(object : SearchView.OnQueryTextListener{
        // override fun onQueryTextSubmit(query: String): Boolean {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                //TODO("Not yet implemented")
                val loc = searchView.query.toString()
                if (loc == null) {
                    Toast.makeText(this@LocationActivity, "Location Not Found", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val geocoder = Geocoder(this@LocationActivity, Locale.getDefault())
                    try {
                        val addressList = geocoder.getFromLocationName(loc, 1)
                        if (addressList?.size!! > 0) {
                            val latLng = LatLng(addressList[0].latitude, addressList[0].longitude)

                            if (::marker.isInitialized) {
                                marker.remove()

                                val markerOptions = MarkerOptions().position(latLng).title(loc);
                                markerOptions.icon(
                                    BitmapDescriptorFactory.defaultMarker(
                                        BitmapDescriptorFactory.HUE_AZURE
                                    )
                                );
                                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 5f);
                                gmap.animateCamera(cameraUpdate);
                              //  val marker = gmap.addMarker(markerOptions)
                                marker = gmap.addMarker(markerOptions)!!
                            }

                        }
                    } catch (e: IOException) {
                        e.printStackTrace();
                    }


                }
                return false;
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                //TODO("Not yet implemented")
                return false;
            }

        }

        )
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_CODE
            );
            return;
        }
        val task: Task<Location> = fusedClient.lastLocation
        task.addOnSuccessListener(OnSuccessListener<Location> { location ->
            if (location != null) {
                currentLocation = location
                val locationFragment =
                    supportFragmentManager.findFragmentById(R.id.location) as SupportMapFragment
                locationFragment.getMapAsync(this);
            }

        });

    }

    override fun onMapReady(p0: GoogleMap) {
        //TODO("Not yet implemented")


        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
        val markerOptions = MarkerOptions().position(latLng).title("My Current Location")
        p0.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        p0.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5f));
        p0.addMarker(markerOptions);
        this.gmap = p0;
        // val mapKenya = LatLng(0.0236, 37.9062);
        //  gmap.addMarker(MarkerOptions().position(mapKenya).title("Marker in Kenya"));
        // gmap.moveCamera(CameraUpdateFactory.newLatLng(mapKenya));


    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();


            }
        }
    }
}