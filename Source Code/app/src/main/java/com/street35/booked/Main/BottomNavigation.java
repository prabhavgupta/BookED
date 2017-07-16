package com.street35.booked.Main;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.street35.booked.AllBooks.AllBooks;
import com.street35.booked.MyBooks.MyBooks;
import com.street35.booked.NearbyBooks.NearbyBooks;
import com.street35.booked.Profile.ProfileFragment;
import com.street35.booked.R;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by Weirdo on 03-08-2016.
 */
public class BottomNavigation extends AppCompatActivity {


    InterstitialAd mInterstitialAd;
    private InterstitialAd interstitial;
    int last_position = 2;

    String email ;

    Toolbar toolbar;
    HomeScreen homescreen ;
    MyBooks mybooks ;
    NearbyBooks nearbybooks ;
    AllBooks allbooks ;
    ProfileFragment profilefragment ;
    android.app.FragmentManager fragmentManager;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Book Share");



        SharedPreferences sharedPref = this.getSharedPreferences("Login",Context.MODE_PRIVATE);
        final String fname = sharedPref.getString("fname","");
        final String email = sharedPref.getString("email","");
        Log.d("emailllllllllllll",email);
        Log.d("fnameeeeeeeeeee",fname);

        /*SharedPreferences sharedPref = this.getSharedPreferences("Login",Context.MODE_PRIVATE);
        final String fname = sharedPref.getString("fname","A");



        SharedPreferences sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        String ecopy =  sharedPreferences.getString("username","NoValue");


        System.out.println(ecopy);*/
//        Log.d("eeeeeeeeeeeeeee",username);
        Log.d("gvcsdgcvs","dggvcgdvcgdvcgvdgcvdgc");

        fragmentManager = getFragmentManager();


        FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();

        HomeScreen hs = HomeScreen.newInstance();
        fragmentTransaction.add(R.id.fragmentContainer ,hs,"2" );
        //fragmentTransaction.add(R.id.fragmentContainer,HomeScreen.newInstance());
        fragmentTransaction.commit();




        AHBottomNavigation bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("Nearby Books", R.drawable.ic_location_on_black_24dp, R.color.tabcolor);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("All Books",R.drawable.ic_library_books_black_24dp, R.color.tabcolor);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("Home", R.drawable.ic_home_black_24dp, R.color.tabcolor);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("My Shelf", R.drawable.ic_collections_bookmark_black_24dp, R.color.tabcolor);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("Profile",  R.drawable.ic_face_black_24dp, R.color.tabcolor);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);
        bottomNavigation.addItem(item5);

        bottomNavigation.setBehaviorTranslationEnabled(false);
        bottomNavigation.setCurrentItem(2);
        bottomNavigation.setAccentColor(Color.parseColor("#3B494C"));
        bottomNavigation.setInactiveColor(Color.parseColor("#90F63D2B"));




        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...
                FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();

                switch (position){
                    case 0 :

                        toolbar.setTitle("Nearby Books");
                        if(position == last_position) break;

                        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag(last_position+"")).commit();
                        last_position = position;


                        if(fragmentManager.findFragmentByTag("0") != null) {
                            //if the fragment exists, show it.
                            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("0")).commit();
                        } else {
                            //if the fragment does not exist, add it to fragment manager.
                            toolbar.setTitle("Nearby Books");

                            NearbyBooks n = NearbyBooks.newInstance();
                            fragmentManager.beginTransaction().add(R.id.fragmentContainer, n, "0").commit();
                        }

                        break;

                    case 1 :
                        toolbar.setTitle("All Books");
                        if(position == last_position) break;


                        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag(last_position+"")).commit();
                        last_position = position;


                        if(fragmentManager.findFragmentByTag("1") != null) {
                            //if the fragment exists, show it.
                            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("1")).commit();
                        } else {
                            //if the fragment does not exist, add it to fragment manager.


                            AllBooks a = AllBooks.newInstance() ;
                            toolbar.setTitle("All Books");

                            fragmentManager.beginTransaction().add(R.id.fragmentContainer, a, "1").commit();
                        }

                        break;

                    case 2 :
                        toolbar.setTitle("Home");
                        if(position == last_position) break;

                        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag(last_position+"")).commit();
                        last_position = position;


                        if(fragmentManager.findFragmentByTag("2") != null) {
                            //if the fragment exists, show it.
                            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("2")).commit();
                        } else {
                            //if the fragment does not exist, add it to fragment manager.



                            HomeScreen hs = HomeScreen.newInstance();
                            toolbar.setTitle("Home");

                            fragmentManager.beginTransaction().add(R.id.fragmentContainer, hs, "2").commit();
                        }

                        break;

                    case 3 :
                        toolbar.setTitle("My Shelf");
                        if(position == last_position) break;
                        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag(last_position+"")).commit();
                        last_position = position;


                        if(fragmentManager.findFragmentByTag("3") != null) {
                            //if the fragment exists, show it.
                            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("3")).commit();
                        } else {
                            //if the fragment does not exist, add it to fragment manager.



                            MyBooks mb = MyBooks.newInstance();
                            toolbar.setTitle("My Shelf");

                            fragmentManager.beginTransaction().add(R.id.fragmentContainer, mb, "3").commit();
                        }

                        break;

                    case 4 :
                        toolbar.setTitle("Profile");
                        if(position == last_position) break;
                        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag(last_position+"")).commit();
                        last_position = position;


                        if(fragmentManager.findFragmentByTag("4") != null) {
                            //if the fragment exists, show it.
                            fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("4")).commit();
                        } else {
                            //if the fragment does not exist, add it to fragment manager.



                            ProfileFragment pf = ProfileFragment.newInstance();
                            toolbar.setTitle("Profile");
                            fragmentManager.beginTransaction().add(R.id.fragmentContainer, pf, "4").commit();
                        }

                        break;


                }
                return true;
            }
        });


    }





    public static boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
    }









    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        final View view = findViewById(R.id.fr);

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        //Checking for fragment count on backstack
        while (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();
        }
        if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this,"Please click BACK again to exit.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            finish();
            return;
        }
    }




}
