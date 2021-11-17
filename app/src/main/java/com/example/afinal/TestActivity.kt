package com.example.afinal

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.afinal.Common.Common
import com.example.afinal.Remote.IGoogleAPIService
import com.example.afinal.VO.MyPlaces

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.afinal.databinding.ActivityTestBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import retrofit2.Call
import retrofit2.Response
import java.lang.StringBuilder
import javax.security.auth.callback.Callback

class TestActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityTestBinding

    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()

    private lateinit var mLastLocation: Location
    private var mMarker: Marker? = null

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback

    companion object {

    private val MY_PERMISSION_CODE: Int = 1000
}


lateinit var mService:IGoogleAPIService
internal var currentPlaces: MyPlaces? =null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mService = Common.googleApiService


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            if(checkLocationPermission()) {
                buildLocationRequest()
                buildLocationCallback();


                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.myLooper()
                )
            }
        else{
                buildLocationRequest()
                buildLocationCallback();


                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.myLooper())
            }

        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            item ->
            when(item.itemId){

                R.id.action_hospital -> nearByPlace("hospital")
                R.id.action_tour -> nearByPlace("tour")
                R.id.action_restaurant -> nearByPlace("restaurant")

            }
            true
        }

    }

    private fun nearByPlace(typePlace: String) {
        mMap.clear()
        val url = getUrl(latitude,longitude,typePlace)

        mService.getNearbyPlaces(url)
            .enqueue(object : retrofit2.Callback<MyPlaces> {
                override fun onResponse(call: Call<MyPlaces>, response: Response<MyPlaces>) {

                    currentPlaces = response.body()!!
                    if (response.isSuccessful) {

                        for (i in 0 until response.body()!!.results!!.size)
                        {
                            val markerOptions = MarkerOptions()
                            val googlePlace = response.body()!!.results!![i]
                            val lat = googlePlace.geometry!!.location!!.lat
                            val lng = googlePlace.geometry!!.location!!.lng
                            val placeName = googlePlace.name
                            val latLng = LatLng(lat, lng)

                            markerOptions.position(latLng)
                            markerOptions.title(placeName)
                      /*      if (typePlace.equals("hospital"))
                                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_local))
                            if (typePlace.equals("tour"))
                                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_baseline_tour_24))
                            if (typePlace.equals("restaurant"))
                                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_baseline_restaurant_24))
                            else*/
                                markerOptions.icon(
                                    BitmapDescriptorFactory.defaultMarker(
                                        BitmapDescriptorFactory.HUE_BLUE
                                    )
                                )


                            markerOptions.snippet(i.toString())

                            //Add marker to map
                            mMap!!.addMarker(markerOptions)
                            //move camera
                            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                            mMap!!.animateCamera(CameraUpdateFactory.zoomTo(15f))

                        }
                    }
                }

                override fun onFailure(call: Call<MyPlaces>, t: Throwable) {
                    Toast.makeText(baseContext,""+t!!.message,Toast.LENGTH_SHORT).show()
                }

            })
    }

    private fun getUrl(latitude: Double, longitude: Double, typePlace: String): String {

        val googlePlaceUrl= StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
        googlePlaceUrl.append("?location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=10000")
        googlePlaceUrl.append("&type=$typePlace")
        googlePlaceUrl.append("&key=AIzaSyAEHJx5j_CUnp6jRgzLe3hoPiPVgLtxWrA")

        Log.d("URL_DEBUG",googlePlaceUrl.toString())
        return googlePlaceUrl.toString()

    }

    private fun buildLocationCallback() {
        locationCallback = object :LocationCallback(){
            override fun onLocationResult(p0: LocationResult?) {
                mLastLocation =p0!!.locations.get(p0!!.locations.size-1)

                if(mMarker !=null)
                {
                    mMarker!!.remove()
                }
                latitude= mLastLocation.latitude
                longitude=mLastLocation.longitude

                val latLng = LatLng(latitude,longitude)
                val markerOptions = MarkerOptions()
                    .position(latLng)
                    .title("Your position")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                mMarker = mMap!!.addMarker(markerOptions)

                //move Camera
                mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                mMap!!.animateCamera(CameraUpdateFactory.zoomTo(11f))
            }
        }
    }

    private fun buildLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 5000
        locationRequest.fastestInterval = 3000
        locationRequest.smallestDisplacement = 10f
    }

    private fun checkLocationPermission() :Boolean{

        if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.ACCESS_FINE_LOCATION))
                ActivityCompat.requestPermissions(this, arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ),MY_PERMISSION_CODE)
            else
                ActivityCompat.requestPermissions(this, arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),MY_PERMISSION_CODE)
            return false

        }
        else
            return true
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        when(requestCode){

            MY_PERMISSION_CODE->{
                if(grantResults.size > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
                        if(checkLocationPermission()) {
                            buildLocationRequest()
                            buildLocationCallback();


                            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
                            fusedLocationProviderClient.requestLocationUpdates(
                                locationRequest,
                                locationCallback,
                                Looper.myLooper()
                            )
                            mMap!!.isMyLocationEnabled = true
                        }
                }
                else{

                    Toast.makeText(this,"Permission Denied",Toast.LENGTH_LONG).show()
                }


            }
        }
    }

    override fun onStop() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
        super.onStop()
    }



    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap




        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                mMap!!.isMyLocationEnabled = true

            }
        }
        else
            mMap!!.isMyLocationEnabled = true


        mMap.uiSettings.isZoomControlsEnabled= false



        //MAke event click Marker
      mMap!!.setOnMarkerClickListener { marker ->

          Common.currentResult = currentPlaces!!.results!![Integer.parseInt(marker.snippet)]
          startActivity(Intent(this,ViewPlace::class.java))
          true
      }


    }
}