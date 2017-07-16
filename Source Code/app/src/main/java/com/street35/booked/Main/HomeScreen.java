package com.street35.booked.Main;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.street35.booked.ExchangeOrDonate.TwoAddOptions;
import com.street35.booked.R;

/**
 * Created by Weirdo on 03-08-2016.
 */
public class HomeScreen extends android.app.Fragment implements View.OnClickListener {
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    TextView donate,exchange;

    static HomeScreen homeScreen=null;

    public static HomeScreen newInstance() {

        if(homeScreen!=null) return homeScreen;

        homeScreen = new HomeScreen();

        return homeScreen;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_screen,container,false);

        getActivity().setTitle("Home");

        /*SharedPreferences sharedPreferences = this.getActivity().getPreferences(Context.MODE_PRIVATE);
        String ecopy =  sharedPreferences.getString("username","");

        System.out.println(ecopy);
        Log.d("eeeeeeeeeeeeeee",ecopy);
        Log.d("abababababab","abababababba");*/

        getActivity().setTitle("Home");

        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        String ecopy =  sharedPreferences.getString("email","");

        System.out.println(ecopy);
        Log.d("eeeeeeeeeeeeeee",ecopy);

        donate= (TextView)v.findViewById(R.id.Donate);
        exchange = (TextView)v.findViewById(R.id.Exchange);

        fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab1 = (FloatingActionButton)v.findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) v.findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_backward);
        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        donate.setVisibility(View.GONE);
        exchange.setVisibility(View.GONE);

        return v;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.fab:

                animateFAB();
                break;
            case R.id.fab1:

                fab.startAnimation(rotate_backward);
                fab1.startAnimation(fab_close);
                fab2.startAnimation(fab_close);
                fab1.setClickable(false);
                fab2.setClickable(false);
                isFabOpen = false;
                Intent intent2 = new Intent(getActivity(),TwoAddOptions.class);
                intent2.putExtra("E/D","D");
                getActivity().startActivity(intent2);
                //getActivity().finish();
                break;

            case R.id.fab2:



                fab.startAnimation(rotate_backward);
                fab1.startAnimation(fab_close);
                fab2.startAnimation(fab_close);
                fab1.setClickable(false);
                fab2.setClickable(false);
                isFabOpen = false;
                Intent intent = new Intent(getActivity(),TwoAddOptions.class);
                intent.putExtra("E/D","E");
                getActivity().startActivity(intent);
                //getActivity().finish();
                break;
        }
    }

    public void animateFAB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            donate.setVisibility(View.GONE);
            exchange.setVisibility(View.GONE);

            isFabOpen = false;
            Log.d("Raj", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            donate.setVisibility(View.VISIBLE);
            exchange.setVisibility(View.VISIBLE);

            isFabOpen = true;

            Log.d("Raj","open");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        donate.setVisibility(View.GONE);
        exchange.setVisibility(View.GONE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_search,menu);
    }
}
