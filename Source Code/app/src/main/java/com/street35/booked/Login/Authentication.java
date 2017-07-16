package com.street35.booked.Login;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;


import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.auth.api.Auth;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.street35.booked.Main.BottomNavigation;
import com.street35.booked.NetworkServices.EmailExist;
import com.street35.booked.NetworkServices.LoginDetailsCheck;
import com.street35.booked.NetworkServices.NotConnected;
import com.street35.booked.NetworkServices.RegisterUser;
import com.street35.booked.NetworkServices.VolleySingleton;
import com.street35.booked.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by Rashi on 06-10-2016.
 */
public class Authentication extends AppCompatActivity
        implements
        GoogleApiClient.OnConnectionFailedListener{


   // Login login = new Login();

    Button signup;
    TextView forgootpass;
    ProgressDialog progressDialog;
    String un,ps;
    String ema;
    int em_val=0;
    MaterialDialog materialDialog;

    ImageView done;
    android.app.FragmentManager fragmentManager = getFragmentManager();



    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        boolean conn = isConnected(Authentication.this);


        if (!conn) {
            Intent i = new Intent(Authentication.this, NotConnected.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Log.d("Tag", "Yahan se gya tha bro");
            startActivity(i);
            Authentication.this.finish();

        }



        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        final SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
             //   .requestScopes(new Scope(Scopes.PLUS_LOGIN))
             //   .requestScopes(new Scope(Scopes.PLUS_ME))
               .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();






            findViewById(R.id.sign_in_button).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
                            signIn();
                            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);

                    }
                });




        // Customizing G+ button
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());

/*
        setContentView(R.layout.uthentication);

        boolean conn = isConnected(getApplicationContext());
        Log.d("abs", String.valueOf(conn));
        if (!conn) {
            Intent i = new Intent(this, NotConnected.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(i);
            finish();

        } else {
            final SharedPreferences sharedPref = this.getSharedPreferences("Login", Context.MODE_PRIVATE);


            un = sharedPref.getString("username", "NoValue");
            ps = sharedPref.getString("password", "NoValue");
            // Get lat and long from sharedpref
            SharedPreferences sharedPreferences = this.getSharedPreferences("Location", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("latitude", "0");
            editor.putString("longitude", "0");
            editor.commit();


            Log.d("sssssssssssssssss", "ddddddddddddddddddddddddd");
            Log.d(un, ps);
            Log.d(un, ps);
            Log.d(un, ps);
            Log.d(un, ps);
            Log.d("sssssssssssssssss", "ddddddddddddddddddddddddd");


            if (!un.equalsIgnoreCase("NoValue") || !ps.equalsIgnoreCase("NoValue")) {
                // Snackbar.make( getApplication().onConfigurationChanged;, "Welcome buddy !", Snackbar.LENGTH_LONG).show();
                Intent i = new Intent(Authentication.this, BottomNavigation.class);
                i.putExtra("username", un);
                startActivity(i);
                finish();
            }else{

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                fragmentTransaction.add(R.id.upper, login);
                fragmentTransaction.commit();

                signup = (Button) findViewById(R.id.signup);
                forgootpass = (TextView) findViewById(R.id.forgotpass);
                done = (ImageView) findViewById(R.id.done);

                signup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (signup.getText() == "LOGIN") {
                            System.out.println(signup.getText());
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                            fragmentTransaction.replace(R.id.upper, login);
                            fragmentTransaction.commit();
                            signup.setText("SIGN UP");
                            forgootpass.setText("Forgot Password?");
                        } else {
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                            fragmentTransaction.replace(R.id.upper, register);
                            fragmentTransaction.commit();
                            signup.setText("LOGIN");
                            forgootpass.setText("");
                        }

                    }
                });
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        android.app.Fragment f = fragmentManager.findFragmentById(R.id.upper);
                        if (f instanceof Login) {

                            final EditText email_h, password_h;
                            email_h = (EditText) findViewById(R.id.login_email);
                            password_h = (EditText) findViewById(R.id.login_password);


                            final String email = email_h.getText().toString();
                            final String password = password_h.getText().toString();

                            final Response.Listener<String> listener = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        Log.d("hey", response);
                                        if (response.contains("1")) {


                                            SharedPreferences.Editor editor = sharedPref.edit();
                                            editor.putString("username", email);
                                            editor.putString("password", password);
                                            editor.commit();
                                            String s = sharedPref.getString("username", "NoValue");


                                            Snackbar.make(getCurrentFocus(), "Welcome buddy !", Snackbar.LENGTH_LONG).show();
                                            Intent i = new Intent(Authentication.this, BottomNavigation.class);
                                            i.putExtra("username", un);
                                            startActivity(i);


                                        } else {
                                            Log.d("bc", "ls" + response + "ls");
                                            Snackbar.make(getCurrentFocus(), "Wrong Email/password" + response, Snackbar.LENGTH_LONG).show();

                                            //t.setText(String.valueOf(sum));
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            };
                            EmailCheckRequest a = new EmailCheckRequest(email, password, listener);
        */
/*RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(a);*//*

                            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(a);


                        } else {


                            final EditText email, password, fname, lname, location,contact;
                            email = (EditText) findViewById(R.id.register_email);
                            password = (EditText) findViewById(R.id.register_password);
                            fname = (EditText) findViewById(R.id.register_fname);
                            lname = (EditText) findViewById(R.id.register_lname);
                            location = (EditText) findViewById(R.id.register_location);
                            contact = (EditText) findViewById(R.id.register_contact);

                            final String em = email.getText().toString();
                            ema = em;
                            final String pass = password.getText().toString();
                            final String firstname = fname.getText().toString();
                            final String lastname = lname.getText().toString();
                            final String loc = location.getText().toString();
                            final String phone = contact.getText().toString();


                            Log.d("em", em);

                            final boolean[] valid = {false};


                            Response.Listener<String> listener1 = new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response2) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(response2);
                                        Boolean smtp = jsonObject.getBoolean("smtp_check");


                                        System.out.println(String.valueOf(smtp));

                                        if (smtp == false) {
                                            Snackbar.make(getCurrentFocus(), "Enter Valid Email address !", Snackbar.LENGTH_LONG).show();
                                        } else {
                                            valid[0] = true;
                                            System.out.println("Valid email");

                                                    */
/*SharedPreferences sharedPref = getParent().getPreferences(Context.MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPref.edit();
                                                    editor.putString("username" , String.valueOf(email));
                                                    editor.putString("password" , key);
                                                    editor.apply();*//*



                                            registerUser(em, pass, firstname, lastname, loc, email, password, fname, lname, location,phone);



                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            };
                            Validation a = new Validation(em, listener1);
                            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(a);


                        }

                    }
                });
            }

        }
*/



    }






    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }


    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false);
                    }
                });
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("Data", resultCode + "      " + requestCode);
        Log.d(TAG, "handleSignInResult:1212121212121212mbbnbn");
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            int statusCode = result.getStatus().getStatusCode();

            Log.e("Data", statusCode + " ;;;");
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());

        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            final String personName = acct.getGivenName();
            String personPhotoUrl = acct.getPhotoUrl().toString();
            final String email = acct.getEmail();
            final String lastName = acct.getFamilyName();


            Log.e(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);
            //Toast.makeText(getBaseContext(), personName ,Toast.LENGTH_LONG).show();



            final Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        materialDialog.dismiss();
                        Log.d(TAG, response);
                        JSONObject jsonObject = new JSONObject(response);

                        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("Login", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("email", email);
                        editor.putString("fname", personName);
                        editor.putString("lname",lastName);
                        editor.commit();
                        editor.apply();

                        if (jsonObject.getBoolean("success")) {


                            Log.d(TAG,"Json bro");



                            //Toast.makeText(getApplicationContext() , sharedPref.getString("email","") , Toast.LENGTH_LONG).show();
                            //Toast.makeText(getApplicationContext() , sharedPref.getString("fname","") , Toast.LENGTH_LONG).show();
                            //Toast.makeText(getApplicationContext() , sharedPref.getString("lname","") , Toast.LENGTH_LONG).show();


                            //String s = sharedPref.getString("username", "NoValue");


                            if(jsonObject.getString("university").equals("NULL") ||
                                 jsonObject.getString("contact").equals("NULL")||
                                    jsonObject.getString("address").equals("NULL") ||
                                    jsonObject.getString("university") == null ||
                                    jsonObject.getString("contact") == null ||
                                    jsonObject.getString("address")  == null ||
                                    jsonObject.isNull("university") ||
                                    jsonObject.isNull("contact") ||
                                    jsonObject.isNull("address")
                                    ){

                                Log.d(TAG,"Json in");
                             //   Snackbar.make(getCurrentFocus(), "Fill The Details To Continue !", Snackbar.LENGTH_LONG).show();
                                Intent i = new Intent(Authentication.this, UserInfo.class);
                                startActivity(i);
                                finish();


                            }else{

                                Log.d(TAG,"Json else");
                                editor.putString("address", jsonObject.getString("address"));
                                editor.putString("university", jsonObject.getString("university"));
                                editor.putString("contact", jsonObject.getString("contact"));
                                editor.putString("sex", "0");
                                editor.commit();
                              //  Snackbar.make(getCurrentFocus(), "Welcome buddy !", Snackbar.LENGTH_LONG).show();
                                Intent i = new Intent(Authentication.this, BottomNavigation.class);
                                startActivity(i);
                                finish();
                            }

                        } else {

                            Log.d(TAG,"Json elseeeee");
                            Log.d("bc", "ls" + response + "ls");
                           // Snackbar.make(getCurrentFocus(), "Email Not Registered" + response, Snackbar.LENGTH_LONG).show();

                           // Snackbar.make(getCurrentFocus(), "Fill The Details To Continue !", Snackbar.LENGTH_LONG).show();
                            Intent i = new Intent(Authentication.this, UserInfo.class);
                            startActivity(i);
                            finish();
                            //t.setText(String.valueOf(sum));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            };
            LoginDetailsCheck a = new LoginDetailsCheck(email,personName , lastName , listener);

            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(a);



//            updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            materialDialog.dismiss();
            updateUI(false);
        }
    }





    @Override
    public void onStart() {
        super.onStart();


        mAuth.addAuthStateListener(mAuthListener);

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            //GoogleSignInResult result = opr.get();
            //handleSignInResult(result);

            SharedPreferences sharedPref=getSharedPreferences("Login", Context.MODE_PRIVATE);
            Log.d(TAG, sharedPref.getString("address","a") + " : " + sharedPref.getString("contact","a") + ":" + sharedPref.getString("university","a"));
            if( sharedPref.getString("address","a")=="a" ||
                    sharedPref.getString("contact","a")=="a" ||
                    sharedPref.getString("university","a")=="a" )
            {
            Intent i = new Intent(Authentication.this, UserInfo.class);
            startActivity(i);
            finish();
            }
            else{
                Intent i = new Intent(Authentication.this, BottomNavigation.class);
                startActivity(i);
                finish();
            }
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            materialDialog = new MaterialDialog.Builder(this)
                    .title("Verifying")
                    .content("Signing You In")
                    .progress(true, 0)
                    .show();

            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {

                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }






    private void updateUI(boolean isSignedIn) {
        if (isSignedIn) {
            //Sign In True
            Intent i = new Intent(Authentication.this, BottomNavigation.class);
            startActivity(i);
            finish();
        } else {

        //    Toast.makeText( getApplicationContext(), "Some Internal Issue. Try after sometime" ,
          //          Toast.LENGTH_LONG).show();
            //Not Signed In
        }
    }

        private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
            Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "signInWithCredential", task.getException());
                                Toast.makeText(Authentication.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            // ...
                        }
                    });
        }



    public void registerUser(final String em, final String pass, final String firstname , final String lastname, final String loc, EditText email, EditText password, final EditText fname, final EditText lname, EditText location, final String phone){

        if(TextUtils.isEmpty(firstname) ||TextUtils.isEmpty(lastname) || TextUtils.isEmpty(loc)||TextUtils.isEmpty(pass)|| TextUtils.isEmpty(phone) ){
            Snackbar.make(getCurrentFocus(),"Fill All the Details ",Snackbar.LENGTH_SHORT).show();

        }

        else{
            final SharedPreferences sharedPref = this.getSharedPreferences("Login",Context.MODE_PRIVATE);


            // check if user is already registered
            final Response.Listener<String> listener3 = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        //   Log.d("hey" , response);
                        if (response.contains("0")) {

                            Log.d("Registering","register");
                          //  GetLoc(firstname,lastname,em,pass,loc,phone,sharedPref);

                        }
                        else {
                            //Log.d("bc", "ls"+response+"ls");
                            Snackbar.make(getCurrentFocus(), "User Already Registered ! " , Snackbar.LENGTH_LONG).show();

                            //t.setText(String.valueOf(sum));
                        }

                    }catch(Exception e){
                        e.printStackTrace();
                    }

                }
            };
            EmailExist a = new EmailExist(em,pass,listener3);
        /*RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(a);*/
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(a);






            //String s = sharedPref.getString("username","NoValue");
           /* Snackbar.make(getCurrentFocus(), "Welcome buddy !", Snackbar.LENGTH_LONG).show();
            Intent i = new Intent(Authentication.this , BottomNavigation.class);
            i.putExtra("username",em);
            startActivity(i);*/

        }


    }
    public static boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
    }







    public void GetLoc(final String firstname, final String lastname, final String em, final String pass, String loc,String phone, final SharedPreferences sharedPref)
    {

        Geocoder coder = new Geocoder(Authentication.this);
        List<Address> addresses;
        try {
            addresses = coder.getFromLocationName(loc, 5);
            if (addresses == null) {
            }
            Address location = addresses.get(0);
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            Log.i("Lat",""+lat);
            Log.i("Lng",""+lng);
            //SetData(firstname,lastname,em,pass,loc,lat,lng,phone,sharedPref);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void SetData(final String firstname, final String lastname, final String em, final String pass, String loc,double lat, double lon ,String phone, final SharedPreferences sharedPref){


        // Enter details of the user
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String resp) {
                try {

                    Log.d("heyyyyyyyyyyyy", resp);

                    JSONObject jsonObject = new JSONObject(resp);
                    boolean success = jsonObject.getBoolean("success");

                    if (success) {
                        Toast.makeText(getApplicationContext(), "s", Toast.LENGTH_SHORT).show();


                        // Add to shared preferences


                        Snackbar.make(getCurrentFocus(), "Welcome buddy !", Snackbar.LENGTH_LONG).show();

                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("username", em);
                        editor.putString("password", pass);
                        editor.putString("fname", firstname);
                        editor.putString("lname", lastname);
                        editor.apply();
                        Intent i = new Intent(Authentication.this , BottomNavigation.class);
                        i.putExtra("username",em);
                        startActivity(i);
                        finish();



                    } else {
                        Toast.makeText(getApplicationContext(), "n", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        RegisterUser registerUser = new RegisterUser(firstname, lastname, em, pass, loc,lat, lon ,phone, listener);
        VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(registerUser);


    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

/*
    private class GeocoderHandler1 extends Handler {
        @Override
        public void handleMessage(Message message) {
            ArrayList<String> locationAddress = new ArrayList<>();
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getStringArrayList("address");
                    break;
                default:
                    locationAddress = null;
            }
            // t1.setText(locationAddress.get(0));
            String latitude = locationAddress.get(0);
            String longitude = locationAddress.get(1);
            Float lat = Float.valueOf(latitude);
            Float log = Float.valueOf(longitude);
            Log.d("bhhhhhhhhhhhhhh",latitude);

            SharedPreferences sharedPreferences = getSharedPreferences("Location",Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("latitude",latitude);
            editor.putString("longitude",longitude);



            if(latitude.length()>0){

                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");

                            if(success) {
                                Toast.makeText(getApplicationContext(),"sucess1",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"abscd",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                AddressRequest addressRequest= new AddressRequest(ema,latitude,longitude,listener);
               */
/* RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(addressRequest);*//*


                VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(addressRequest);


            }


        }
    }
*/



}