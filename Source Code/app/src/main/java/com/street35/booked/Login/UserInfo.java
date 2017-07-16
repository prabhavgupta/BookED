package com.street35.booked.Login;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.street35.booked.Main.BottomNavigation;
import com.street35.booked.NetworkServices.AddressSaveRequest;
import com.street35.booked.NetworkServices.EditProfileRequest;
import com.street35.booked.NetworkServices.VolleySingleton;
import com.street35.booked.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;

/**
 * Created by Rashi on 06-10-2016.
 */
public class UserInfo extends AppCompatActivity {
    EditText address,contact,university;
    String fname,lname,email;
    String add,con,uni;
    SharedPreferences sharedPref;
    SharedPreferences sharedPreferences;

    private static final String TAG = "SignInActivity";

    @Override
    public void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);

        sharedPref=getSharedPreferences("Login", Context.MODE_PRIVATE);
        sharedPreferences = getSharedPreferences("Location", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor1 = sharedPreferences.edit();
        final SharedPreferences.Editor editor = sharedPref.edit();

        address = (EditText)findViewById(R.id.ui_address);
        university = (EditText)findViewById(R.id.ui_university);
        contact = (EditText)findViewById(R.id.ui_contact);

        final Button enter = (Button)findViewById(R.id.done);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                enter.setVisibility(View.GONE);

                String MobilePattern = "[0-9]{10}";

                 if(address.getText().toString().isEmpty()){

                    Snackbar.make(view, "Enter your Home Address", Snackbar.LENGTH_LONG).show();
                }else if(university.getText().toString().isEmpty()){

                    Snackbar.make(view, "Enter your University Name", Snackbar.LENGTH_LONG).show();
                }else if (!contact.getText().toString().matches(MobilePattern)||contact.getText().toString().length()>13||contact.getText().toString().length()>10 ) {

                    Snackbar.make(view, "Enter a valid number", Snackbar.LENGTH_LONG).show();
                }else {
                    add = address.getText().toString();
                    uni = university.getText().toString();
                    con = contact.getText().toString();
                    if (TextUtils.isEmpty(add) && TextUtils.isEmpty(con)) {
                        Snackbar.make(view , "Enter all the details", Snackbar.LENGTH_LONG).show();
                    } else {

                        fname = sharedPref.getString("fname", "");
                        lname = sharedPref.getString("lname", "");
                        email = sharedPref.getString("email", "");
                        //email = "goel.rashi48@gmail.com";

                        Log.d(TAG, email);
                        Log.d("abbbbbbbbbbbbbb","abbbbbbbbbbbbbbbbb"+fname+lname);


                        Geocoder coder = new Geocoder(getApplicationContext());
                        List<Address> addresses;
                        try {
                            addresses = coder.getFromLocationName(add, 5);

                            Address location = addresses.get(0);
                            double lat = location.getLatitude();
                            double lng = location.getLongitude();
                            Log.i(TAG + "Lat", "" + lat);
                            Log.i(TAG + "Lng", "" + lng);
                            editor1.putString("latitude", String.valueOf(lat));
                            editor1.putString("longitude", String.valueOf(lng));
                            editor1.apply();


                            Response.Listener<String> listener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        Log.d("abbbbbbbbbbbbbbbbb", "bbbbbbbbbbbbbbbbbbb");


                                        JSONObject jsonObject = new JSONObject(response);
                                        boolean success = jsonObject.getBoolean("success");
                                        Toast.makeText(view.getContext(),response+"" , Toast.LENGTH_LONG).show();
                                        Log.d(response, response);
                                        if (success) {
                                            Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();
                                            editor.putString("address", add);
                                            editor.putString("university", uni);
                                            editor.putString("contact", con);
                                            editor.putString("sex", "0");
                                            editor.commit();
                                            editor.apply();
                                            Log.d("ffffffffffffffffff", fname + lname + uni + con + add + "2" + email);

                                            Intent i = new Intent(UserInfo.this, BottomNavigation.class);
                                            startActivity(i);
                                            finish();

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            };

                            Log.d("ttttttttttttttttttttttttttttttt",email+uni+"bxh");
                            AddressSaveRequest addressSaveRequest = new AddressSaveRequest( uni, con, add,
                                    email, String.valueOf(lat), String.valueOf(lng), listener);

                            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(addressSaveRequest);


                        } catch (IOException e) {
                            e.printStackTrace();
                            Snackbar.make(view,"Invalid address",Snackbar.LENGTH_SHORT).show();
                        }


                    }
                }
                enter.setVisibility(View.VISIBLE);
            }
         });

        }

}
