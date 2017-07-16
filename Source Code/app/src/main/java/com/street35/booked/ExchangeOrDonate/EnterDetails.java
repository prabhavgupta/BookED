package com.street35.booked.ExchangeOrDonate;

/**
 * Created by Rashi on 31-08-2016.
 */
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.street35.booked.Login.Authentication;
import com.street35.booked.Main.BottomNavigation;
import com.street35.booked.NetworkServices.EnterDetailsRequest;
import com.street35.booked.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class EnterDetails extends AppCompatActivity {

    String isbncode , url , p=null;

    private static int RESULT_LOAD_IMAGE = 1;
    Button resultset;
    EnterDetailsRequest set;
    private EditText bookd;

    private TextView authorText, titleText, descriptionText, bookp ;
    private LinearLayout starLayout;
    private ImageButton thumbView;
    String value;
    Toolbar toolbar;
    String username;
    int flag =0;
    MaterialDialog dialog;


    void getImage(String ur){
        Log.d("asasasa", ur);
        ImageRequest imgRequest = new ImageRequest(ur,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        thumbView.setImageBitmap(response);
                    }
                }, 0, 0, null , null);

        Volley.newRequestQueue(this).add(imgRequest);

       /* Picasso.with(thumbView.getContext()).load(url)
                //.onlyScaleDown()
                // .centerCrop()
                // .skipMemoryCache()
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(thumbView);*/
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_details);
        Bundle extras = getIntent().getExtras();
        value = extras.getString("ED");

        Log.d("ED",value);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Book Share");

        SharedPreferences sharedPreferences = this.getSharedPreferences("Login",Context.MODE_PRIVATE);
        username = sharedPreferences.getString("email","goel.rashi48@gmail.com");
        Log.d("emailuuuuuuuu",username);



        resultset= (Button) findViewById(R.id.resultSubmit);
        authorText = (TextView)findViewById(R.id.book_author);
        titleText = (TextView)findViewById(R.id.book_title);
        thumbView = (ImageButton) findViewById(R.id.thumb);


        resultset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                //  Toast.makeText(getApplication(),"Result Button",Toast.LENGTH_LONG).show();


                if(titleText.getText().toString().trim().equals("")){

                    Snackbar.make(v , "Book name can not be empty" , Snackbar.LENGTH_LONG).show();
                }
                else if(authorText.getText().toString().trim().equals("")){

                    Snackbar.make(v , "Book author's name can not be empty" , Snackbar.LENGTH_LONG).show();
                }
                else{
                    buildAlertMessage();
                }



            }
        });

        Intent iin= getIntent();
        final Bundle[] b = {iin.getExtras()};
        Log.d("ABC","y1");
        if(b[0] !=null)
        {

            dialog = new MaterialDialog.Builder(EnterDetails.this)
                .title("Fetching Data")
                .content("And Its Almost There")
                .progress(true, 0)
                .show();

            Log.d("ABC","y2");
            isbncode = b[0].getString("ABCD");
            Log.d("ABC",isbncode + "isbn ends");
            url = "https://www.googleapis.com/books/v1/volumes?q=isbn:"+isbncode;
            Log.e("uel",url);
            getInfo(url);
        //    dialog.dismiss();

        }





    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            thumbView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }


    }



    //Getting Info : Title, Name, Author

    void getInfo(String url){


        // Request a string response
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Result handling
                        String b=response;
                        try {

                            Log.d("aaaaaa", b);
                            JSONObject obj = new JSONObject(b);

                            String check = obj.getString("totalItems");
                            if(check.endsWith("0")){

                                Log.d("TwoAdd" , "No Data");
                                dialog.dismiss();
                                dialog.cancel();
                                new MaterialDialog.Builder(EnterDetails.this)
                                        .title("No Information")
                                        .content("No data for this ISBN. You can enter manually.")
                                        .positiveText("Okay")
                                        .positiveColor(Color.BLACK)
                                        .cancelable(false)
                                        .showListener(new DialogInterface.OnShowListener() {
                                            @Override
                                            public void onShow(DialogInterface dialog) {

                                            }
                                        })
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                                                //Intent i = new Intent(EnterDetails.this,TwoAddOptions.class);
                                                //i.putExtra("E/D", "E");
                                                //startActivity(i);
                                                //finish();
                                            }
                                        })
                                        .show();
                            }else {


                                Log.d("TwoAdd" , "Yes Data");
                                JSONArray items = obj.getJSONArray("items");
                                JSONObject info = items.getJSONObject(0);
                                JSONObject abc = info.getJSONObject("volumeInfo");
                                JSONArray author = abc.getJSONArray("authors");
                                JSONObject imagelink = abc.getJSONObject("imageLinks");
                                String ur = imagelink.getString("thumbnail");
                                p = ur;
                                StringBuilder aut = new StringBuilder();
                                for (int i = 0; i < author.length(); i++) {
                                    aut.append(author.get(i));
                                }


                                titleText.setText(abc.getString("title"));
//                            bookp.setText( abc.getString("publisher"));
                                authorText.setText(aut.toString());
                                getImage(ur);
                                dialog.dismiss();
                                dialog.cancel();
                            }


                        } catch (JSONException e) {
                            Log.d("TwoAdd" , e.getMessage() + "<--");
                            dialog.dismiss();
                            dialog.cancel();
                            /*Intent i = new Intent(EnterDetails.this,BottomNavigation.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            i.putExtra("E/D", "E");
                            startActivity(i);
                            finish();
                            new MaterialDialog.Builder(EnterDetails.this)
                                    .title("Some Information is missing")
                                    .content("Try to search for other ISBN for the same book")
                                    .positiveText("Okay")
                                    .positiveColor(Color.BLACK)
                                    .cancelable(false)
                                    .showListener(new DialogInterface.OnShowListener() {
                                        @Override
                                        public void onShow(DialogInterface dialog) {

                                        }
                                    })
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                                            newActivity(1);
                                        }
                                    })
                                    .show();
                            */


                            new MaterialDialog.Builder(EnterDetails.this)
                                    .title("No Information")
                                    .content("No data for this ISBN. You can enter manually.")
                                    .positiveText("Okay")
                                    .positiveColor(Color.BLACK)
                                    .cancelable(false)
                                    .showListener(new DialogInterface.OnShowListener() {
                                        @Override
                                        public void onShow(DialogInterface dialog) {

                                        }
                                    })
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {


                                            //Intent i = new Intent(EnterDetails.this,TwoAddOptions.class);
                                            //i.putExtra("E/D", "E");
                                            //startActivity(i);
                                            //finish();
                                        }
                                    })
                                    .show();



                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                // Error handling
                System.out.println("Something went wrong!");
                error.printStackTrace();

            }
        });


        // Add the request to the queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


    public void newActivity(int flag){
        if(flag==1){

            FragmentManager fragmentManager = getFragmentManager();
            Fragment fragment = fragmentManager.findFragmentByTag("0");
            if(fragment != null)
                fragmentManager.beginTransaction().remove(fragment).commit();

            fragment = fragmentManager.findFragmentByTag("1");
            if(fragment != null)
                fragmentManager.beginTransaction().remove(fragment).commit();

            fragment = fragmentManager.findFragmentByTag("2");
            if(fragment != null)
                fragmentManager.beginTransaction().remove(fragment).commit();

            fragment = fragmentManager.findFragmentByTag("3");
            if(fragment != null)
                fragmentManager.beginTransaction().remove(fragment).commit();

            fragment = fragmentManager.findFragmentByTag("4");
            if(fragment != null)
                fragmentManager.beginTransaction().remove(fragment).commit();


            Intent i = new Intent(this,BottomNavigation.class);

            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(i);
            finish();
        }
    }



    private void buildAlertMessage() {
        String x;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(value.equals("E")){
            x = "Exchange";
        }
        else{
            x = "Donate";
        }
        builder.setMessage("Would you like to let it for "+x+ "?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {


                        Response.Listener<String> listener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {

                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    Log.d("response3",response);

                                    if (success) {
                                        Snackbar.make(getCurrentFocus(),"Succesfully added!",Snackbar.LENGTH_LONG).show();


                                        flag =1;
                                        newActivity(flag);



                                    } else {
                                        Toast.makeText(getApplication(), "Error Occurred! Report or try again later.", Toast.LENGTH_SHORT).show();

                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };





                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date date = new Date(); //2014/08/06 15:59:48

                        if(username == null)
                            username = "No Data";
                        if(p == null)
                            p = "No Data";
                        if(value == null)
                            value = "No Data";

                        Log.d(username , p +" :: " + value);
                        set = new EnterDetailsRequest(username, p,titleText.getText().toString(),
                                authorText.getText().toString()  ,value, String.valueOf(dateFormat.format(date)), listener);

                        RequestQueue queue = Volley.newRequestQueue(getApplication());
                        queue.add(set);


                        dialog.dismiss();
                        dialog.cancel();


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
                alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
                alert.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
            }
        });
        alert.show();
    }



}








