package com.street35.booked.Profile;

import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.street35.booked.Login.Authentication;
import com.street35.booked.R;

/**
 * Created by Weirdo on 11-08-2016.
 */
public class ProfileFragment extends android.app.Fragment{
    TextView detail , text1, profile_name , contactus , text4;
    ImageView detailimg , contactusimg;
    android.app.FragmentManager fragmentManager;
    EditDetails editDetails;
    Toolbar toolbar;
    static ProfileFragment profileFragment=null;
    GoogleApiClient mGoogleApiClient;

    public static ProfileFragment newInstance() {

        if(profileFragment!=null) return profileFragment;
        profileFragment = new ProfileFragment();
        return profileFragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Profile");


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        View v = inflater.inflate(R.layout.profile  ,container ,false);

        detail = (TextView)v.findViewById(R.id.detail);
        text1 = (TextView)v.findViewById(R.id.text1);
        detailimg = (ImageView)v.findViewById(R.id.detailimg);

        contactus = (TextView)v.findViewById(R.id.contactus);
        text4 = (TextView)v.findViewById(R.id.text4);
        contactusimg = (ImageView)v.findViewById(R.id.contactusimg);
        fragmentManager = getFragmentManager();
        Log.d("Hey","Hmmm");
        getActivity().setTitle("Profile");


        contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              gPlus();
            }
        });
        text4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gPlus();
            }
        });
        contactusimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gPlus();
            }
        });



        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDetails();
            }
        });
        detailimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDetails();
            }
        });
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDetails();
            }
        });




       /* Bundle bundle = this.getArguments();
        String ecopy = bundle.getString("username");
        System.out.println(ecopy);
        Log.d("eeeeeeeeeeeeeee",ecopy);*/

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Login",Context.MODE_PRIVATE);
        String fname=sharedPreferences.getString("fname","U");
        String lname = sharedPreferences.getString("lname","");

        Log.e(fname,lname);
        char p = fname.toUpperCase().charAt(0);

        Log.d("nxbchdcv", String.valueOf(p));

        //s= sharedPreferences.getString("email","abc");
        profile_name = (TextView) v.findViewById(R.id.profile_name);
        if(lname.isEmpty()){
            profile_name.setText(String.valueOf(p));
        }
        else{
            char s = lname.toUpperCase().charAt(0);
            String y = String.valueOf(p) + String.valueOf(s);
            profile_name.setText(y);
            Log.d("hhhhhhhhhhhhhhhhhhh", p+s + " lllllllllllllll");
        }





        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profilemenu,menu);

        MenuItem logout = menu.findItem(R.id.logout);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== R.id.logout){
           buildAlertMessage();

        }
        return super.onOptionsItemSelected(item);
    }




    private void buildAlertMessage() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Would You Like to logout ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Login",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", "NoValue");
                        editor.putString("password", "NoValue");
                        editor.putString("fname","NoValue");
                        editor.putString("lname","NoValue");
                        editor.apply();

                        SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("Location",Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                        editor1.putString("latitude","0");
                        editor1.putString("longitude","0");
                        editor1.apply();
                        dialog.dismiss();
                        dialog.cancel();
                        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                                new ResultCallback<Status>() {
                                    @Override
                                    public void onResult(Status status) {
                                        // ...
                                        Toast.makeText(getActivity(),"Logged Out",Toast.LENGTH_SHORT).show();
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



                                        Intent i = new Intent(getActivity(), Authentication.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                                        startActivity(i);
                                        


                                    }
                                });
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
                alert.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                alert.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            }
        });
        alert.show();
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        getActivity().setTitle("Profile");
        super.onAttachFragment(childFragment);
    }





    private void gPlus(){
        String url = "https://plus.google.com/communities/107007444706625817079";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void pDetails(){
        Intent i = new Intent(getActivity(), EditDetails.class);
        startActivity(i);
    }


}
