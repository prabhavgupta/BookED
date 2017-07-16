package com.street35.booked.ExchangeOrDonate;

/**
 * Created by Rashi on 31-08-2016.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.street35.booked.R;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.google.android.gms.vision.barcode.Barcode;


public class TwoAddOptions extends AppCompatActivity {
    ImageButton manuald , barcoded ;
    String scanResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.two_add_options);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Book Share");

        manuald= (ImageButton) findViewById(R.id.manualdetailsbt);
        barcoded= (ImageButton) findViewById(R.id.barcodebt);

        Bundle extras = getIntent().getExtras();
        final String value = extras.getString("E/D");

        Log.e("Tololotot" , value);

        Log.e("Tololotot" , value);
        Log.e("Tololotot" , value);
        Log.e("Tololotot" , value);



        manuald.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Context context = getApplication();
                ConnectivityManager cm =
                        (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if(isConnected){
                new MaterialDialog.Builder(v.getContext())
                        .title("Enter ISBN manually")
                        .inputRangeRes(10, 13, R.color.colorAccent)
                        .input(null, null, new MaterialDialog.InputCallback() {
                            @Override
                            public void onInput(MaterialDialog dialog, CharSequence input) {
                                Intent intent = new Intent(getBaseContext(), EnterDetails.class);

                                intent.putExtra("ABCD", input.toString());
                                Intent i =  getIntent();
                                String ed = i.getStringExtra("E/D");
                                Log.e("Tololotot" , value + "  ddd   "+ ed);
                                intent.putExtra( "ED" , ed );
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                startActivity(intent);
                                //finish();
                            }
                        }).show();
                }
                else{
                    Toast.makeText(getApplication(), "Connect To Internet", Toast.LENGTH_LONG).show();
                }


            }
        });


        barcoded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Context context = getApplication();
                ConnectivityManager cm =
                        (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if(isConnected){

                    /*
                    Intent intent = new Intent(v.getContext(), BarcodeScanner.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("E/D", value);
                    startActivity(intent);
               */

                    final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                            .withActivity(TwoAddOptions.this)
                            .withEnableAutoFocus(true)
                            .withBleepEnabled(true)
                            .withBackfacingCamera()
                            .withBarcodeFormats(Barcode.EAN_13)
                            .withText("Scanning...")
                            .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                                @Override
                                public void onResult(Barcode barcode) {

                                    Log.d("Scan","Scann");
                                    scanResult=barcode.rawValue;
                                    Log.d("ISBN",scanResult);


                                    new MaterialDialog.Builder(v.getContext())
                                            .title("Is this ISBN correct?")
                                            .content(scanResult)
                                            .positiveText("Agree")
                                            .negativeText("Disagree")
                                            .positiveColor(Color.BLACK)
                                            .negativeColor(Color.BLACK)
                                            .cancelable(false)
                                            .showListener(new DialogInterface.OnShowListener() {
                                                @Override
                                                public void onShow(DialogInterface dialog) {




                                                }
                                            })
                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                                    Intent intent = new Intent(getBaseContext(), EnterDetails.class);
                                                    intent.putExtra("ABCD", scanResult);
                                                    intent.putExtra("ED", value);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                                    startActivity(intent);

                                                    //finish();
                                                }
                                            })
                                            .show();



                                }
                            })
                            .withCenterTracker()
                            .build();

                    materialBarcodeScanner.startScan();
                }
                else{
                    Toast.makeText(getApplication(), "Connect To Internet", Toast.LENGTH_LONG).show();
                }

            }
        });



    }
}

