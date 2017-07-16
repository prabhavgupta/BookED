package com.street35.booked.NearbyBooks;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.street35.booked.Main.BottomNavigation;
import com.street35.booked.NetworkServices.NotConnected;
import com.street35.booked.R;

public class NearbyBooks extends android.app.Fragment implements
        GoogleApiClient.OnConnectionFailedListener {

    int PERMISSION_ACCESS_COARSE_LOCATION = 1;
    static NearbyBooks nearbyBooks =null;
    LocationManager locationManager;
    public static NearbyBooks newInstance() {

        if(nearbyBooks!=null) return nearbyBooks;

        nearbyBooks = new NearbyBooks();

        return nearbyBooks;
    }

    InterstitialAd mInterstitialAd;



    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("19958886412D28A5FFC7BA20F66C6FF5")
                .build();
        mInterstitialAd.loadAd(adRequest);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);







    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.main_map_fragment , container , false);
        boolean conn = isConnected(getActivity());


        if (!conn) {
            Intent i = new Intent(getActivity(), NotConnected.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(i);
            getActivity().finish();


        }


        //Ads
        mInterstitialAd = new InterstitialAd(this.getActivity());
        mInterstitialAd.setAdUnitId("ca-app-pub-7027418412989558/5138339023");
        requestNewInterstitial();
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
            }
        });

        getActivity().setTitle("Nearby Books");
        Button b = (Button) v.findViewById(R.id.faltuButton);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
                if (!statusCheck()) {
                    // Snackbar.make(v, "Enable Location Services", Snackbar.LENGTH_LONG).show();
                    Toast.makeText(getActivity(),"Enable Location Services ",Toast.LENGTH_LONG).show();
                } else {
                    Intent i = new Intent(getActivity(), MapsActivity.class);
                    startActivity(i);
                }
            }
        });
        return v;
    }

    boolean statusCheck() {
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
            return false;
        }

        /*if(ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(getActivity(),new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSION_ACCESS_COARSE_LOCATION );
        }*/

        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_ACCESS_COARSE_LOCATION) {
            if (permissions.length == 1 &&
                    permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                // Permission was denied. Display an error message.
                Log.d("a","Permission denied");
                Toast.makeText(getActivity(),"Permission Denied",Toast.LENGTH_LONG).show();
            }
        }
    }
    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.dismiss();
                        dialog.cancel();
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();

        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            }
        });
        alert.show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Intent i = new Intent(getActivity(), NotConnected.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(i);


    }

    public static boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        getActivity().setTitle("Nearby Books");
        super.onAttachFragment(childFragment);
    }
}
