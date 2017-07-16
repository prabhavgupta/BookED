package com.street35.booked.NearbyBooks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.maps.model.Marker;
import com.street35.booked.NetworkServices.LatLongViaEmail;
import com.street35.booked.NetworkServices.VolleySingleton;
import com.street35.booked.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener, GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {

    private GoogleMap mMap;
    FloatingActionButton booksnearby, mylocation, navigation;
    String ecopy;
    String latitude, longitude;
    public GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    Map container = new HashMap<String, ArrayList<String>>();
    ArrayList<String> list = new ArrayList<>();
    static Double destlatitude=0.0, destlongitude=0.0;

    LocationManager locationManager = null;
    Location location = null;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.location_fragment);

        Log.d("Adsdsdsdsds", "Sdsdsdsdsdsdsdsdsdsdsdsdsdssdss");
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Maps");

        SharedPreferences sharedPreferences = getSharedPreferences("Login",Context.MODE_PRIVATE);
        ecopy = sharedPreferences.getString("email","");













            mylocation = (FloatingActionButton) findViewById(R.id.fab12);
        navigation = (FloatingActionButton) findViewById(R.id.fab123);

        // mapView= (MapView)v.findViewById(R.id.mapview);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
        if (googleApiClient == null) {
            Log.d("NULL", "Lallu");
        }




          /*  mapView.onCreate(savedInstanceState);
           mapView.getMapAsync(this);*/


        /*destlatitude = Double.valueOf(latitude);
        destlongitude = Double.valueOf(longitude);*/


        // go to position of nearest book location

        // my location
        mylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Toast.makeText(getApplication(), "You are here", Toast.LENGTH_LONG).show();
                    // Location location = null;

                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    LatLng cl = null;
                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 200);

                            return;
                        }

                        if (googleApiClient == null) Log.d("sdsds", "Google Client API nullllllll");
                        else Log.d("sdsdsd", "khghjhhjhjkhkjjhkjhkhkhkjhkjhkjhkjhhkjhkjh");

                        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                        System.out.println(location);


                        // Log.d("gegege" , location.getLatitude() + " +++++++ " + location.getLongitude() );
                    } catch (IllegalArgumentException e) {
                        Log.d("dsds", "Error no sqe");
                    }
                    if (location == null) {
                        Log.d("dsds", "Location null of fab clicked");
                    } else {
                        cl = new LatLng(location.getLatitude(), location.getLongitude());
                        if (cl != null)
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cl, 15));
                    }

                }


        });


        // navigate from my location to the marker selected
        navigation.setOnClickListener(new View.OnClickListener() {
            Location location;

            @Override
            public void onClick(View v) {


                LatLng cl = null;
                try {

                    location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                } catch (SecurityException e) {
                    System.out.print("Error no sqe");
                }
                if (location == null) {
                    if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        ActivityCompat.requestPermissions((Activity) getApplicationContext(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 200);

                        return;
                    }
                    LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, (LocationListener) getApplicationContext());
                    Log.d("No loc on fab2", "No loc on fab2");
                } else {
                    cl = new LatLng(location.getLatitude(), location.getLongitude());
                    if (cl != null) {
                        System.out.println("http://maps.google.com/maps?saddr=" + cl.latitude + "," + cl.longitude + "&daddr=" + destlatitude + "," + destlongitude);
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + cl.latitude + "," + cl.longitude + "&daddr=" + destlatitude + "," + destlongitude));
                        startActivity(intent);
                    }

                }

            }


        });


    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //  ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        // mMap.setMyLocationEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 200);

            Log.d("permission denied", "Permission denied");
            return;
        }

        googleMap.setMyLocationEnabled(true);
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds

        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);



        LatLng latLng = new LatLng(20.5937,78.9629);
       /* mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("My Home")

                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_directions_car_black_24dp))
        );
*/
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,5));
        addMarker();

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker != null) {
                    LatLng latLng = marker.getPosition();
                    destlongitude = latLng.longitude;
                    destlatitude = latLng.latitude;
                    LatLng dest = new LatLng(destlatitude, destlongitude);
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dest,15));
                    marker.showInfoWindow();

                    return true;
                }
                return false;
            }
        });

        mMap = googleMap;






/*
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                if (marker != null) {
                    LatLng latLng = marker.getPosition();
                    destlongitude = latLng.longitude;
                    destlatitude = latLng.latitude;
                    LatLng dest = new LatLng(destlatitude, destlongitude);
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(dest,10));
                    marker.showInfoWindow();

                    *//*Location location = null;
                    LatLng cl = null;
                    try {

                        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    } catch (SecurityException e) {
                        System.out.print("Error no sqe");
                    }
                    if (location == null) {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                        }
                        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, (LocationListener) getApplicationContext());
                        Log.d("No loc on fab2","No loc on fab2");
                    }
                    else{
                        cl = new LatLng(location.getLatitude(), location.getLongitude());
                        if(cl!=null){
                            System.out.println("http://maps.google.com/maps?saddr="+cl.latitude+","+cl.longitude+"&daddr="+destlatitude+","+destlongitude);
                            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr="+cl.latitude+","+cl.longitude+"&daddr="+destlatitude+","+destlongitude));
                            startActivity(intent);
                        }

                    }*//*


                    return true;
                }
                return false;
            }
        });*/



    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 200: {

                mMap.setMyLocationEnabled(true);
                locationRequest = LocationRequest.create()
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                        .setFastestInterval(1 * 1000); // 1 second, in milliseconds

                locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

                location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

                return;
                }
            }
        }
*/

    @Override
    public void onConnected(@Nullable Bundle bundle) {




        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        if(location==null){
            System.out.println("location not found");
            //Snackbar.make(getApplicationContext(),"Could not get the location ! Try Again",Snackbar.LENGTH_LONG).show();
        }
        else{
            //handleNewLocation(location);
        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, 9000);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("a", "Location services connection failed with code " + connectionResult.getErrorCode());
        }

    }

    @Override
    public void onLocationChanged(Location location) {

        System.out.println("location changed");
        //handleNewLocation(location);
    }



    private void handleNewLocation(Location location) {
        Log.d("a", location.toString());

        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude, currentLongitude);

        MarkerOptions options = new MarkerOptions()
                .position(latLng)
                .title("I am here!");
        mMap.addMarker(options);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
    }


    @Override
    public void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (googleApiClient.isConnected()) {
            googleApiClient.disconnect();
        }
    }





    public void addMarker(){
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("respponse",response);
                    JSONArray jsonArray = new JSONArray(response);

                    for(int i = 0; i < jsonArray.length(); i++)
                    {

                        JSONArray j = jsonArray.getJSONArray(i);

                        String email = j.getString(0);
                        String name = j.getString(1);
                        String lat = j.getString(2);
                        String log = j.getString(3);
                        Log.d(email,name);
                        LatLng latLng = new LatLng(Double.valueOf(lat),Double.valueOf(log));
                        mMap.addMarker(new MarkerOptions()
                                        .position(latLng)
                                        .title(name)
                                        .snippet(email)
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))

                                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_directions_car_black_24dp))
                        );


                    }


                    //t.setText(String.valueOf(sum));


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        LatLongViaEmail a = new LatLongViaEmail(ecopy,listener);
        /*RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(a);*/
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(a);




    }



}