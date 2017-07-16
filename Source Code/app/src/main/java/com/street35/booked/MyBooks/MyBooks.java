package com.street35.booked.MyBooks;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.street35.booked.AllBooks.BooksData;
import com.street35.booked.AllBooks.UserBooksAdapter;
import com.street35.booked.NetworkServices.AllBooksViaEmail;
import com.street35.booked.NetworkServices.MyBooksViaEmail;
import com.street35.booked.NetworkServices.NotConnected;
import com.street35.booked.NetworkServices.VolleySingleton;
import com.street35.booked.R;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by Rashi on 20-08-2016.
 */
public class MyBooks extends android.app.Fragment implements GoogleApiClient.OnConnectionFailedListener,SwipeRefreshLayout.OnRefreshListener{


    String ecopy;
    private List<BooksData> itemList;
    private RecyclerView recyclerView;
    private UserBooksAdapter mAdapter;
    static MyBooks myBooks;
    private MaterialDialog dialog;
    SwipeRefreshLayout swipeLayout;


    public static MyBooks newInstance() {

        if(myBooks!=null) return myBooks;
        MyBooks myBooks= new MyBooks();
        Log.d("Hey","Sad");
        return myBooks;
    }






    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.my_books, container, false);
        //ecopy = "goel.rashi48@gmail.com";
        //ecopy = getArguments().getString("username");
        //Log.d("mybooks",ecopy);
        dialog = new MaterialDialog.Builder(view.getContext())
                .title("Fetching Data")
                .content("And Its Almost There")
                .progress(true, 0)
                .cancelable(false)
                .backgroundColor(Color.WHITE)
                .show();



        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipelayout);
       swipeLayout.setOnRefreshListener(this);



        boolean conn = isConnected(getActivity());

        if (!conn) {
            Intent i = new Intent(getActivity(), NotConnected.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            getFragmentManager().popBackStack();
            Log.d("Tag", "Yahan se gya tha bro");
            dialog.dismiss();
            startActivity(i);
            getActivity().finish();

        }

        final android.os.Handler handler = new android.os.Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                    Snackbar.make(view ,"Could Not Load Data! Server Error. Try later",Snackbar.LENGTH_LONG).show();
                }
            }
        };
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                handler.removeCallbacks(runnable);
            }
        });


        handler.postDelayed(runnable, 40000);



        itemList = new ArrayList<>();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view1);
        recyclerView.setNestedScrollingEnabled(false);
        getActivity().setTitle("My Shelf");
//        ecopy = getArguments().getString("email");

       SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Login", Context.MODE_PRIVATE);
        ecopy =  sharedPreferences.getString("email","goel.rashi48@gmail.com");


        System.out.println(ecopy);
        Log.d("ecopy",ecopy);





       mAdapter = new UserBooksAdapter(itemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.setVerticalScrollBarEnabled(true);
        //recyclerView.setItemAnimator(new SlideInLeftAnimator());


        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    Log.d("response",response);

                    JSONArray jsonArray = new JSONArray(response);


                    for(int i = 0; i < jsonArray.length(); i++)
                    {
/*


                        String image = j.getString(3);
                        String name = j.getString(4);
                        String author = j.getString(5);
                        String description = j.getString(6);
                        String exchange_donate = j.getString(7);
                        String date_posted = j.getString(8);
                        BooksModel item = new BooksModel(image,name,author,description,exchange_donate,date_posted);
                        itemList.add(item);

*/

                        JSONArray j = jsonArray.getJSONArray(i);

                        BooksData item = new BooksData();
                        item.book_image = j.getString(3);
                        item.book_name = j.getString(4);
                        item.book_author = j.getString(5);
                        item.book_description = j.getString(6);
                        String ed = j.getString(7);
                        if(ed.equals("E")){
                            item.book_exchange_donate = "Exchange";
                        }
                        else{
                            item.book_exchange_donate = "Donate";

                        }
                        item.book_date_posted = j.getString(8);
                        item.bid = j.getInt(0);
                        itemList.add(item);

                    }


                    //t.setText(String.valueOf(sum));


                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    Log.d("Data Set Chanhed","Data Set Cganhed");
                    mAdapter.notifyDataSetChanged();
                    dialog.dismiss();

                }
            }
        };

      //  mAdapter.notifyDataSetChanged();

        Log.e("Emailllllll.",ecopy);
        Log.e("Emailllllll.",ecopy);
        Log.e("Emailllllll.",ecopy);
        Log.e("Emailllllll.",ecopy);
        Log.e("Emailllllll.",ecopy);
        Log.e("Emailllllll.",ecopy);
        MyBooksViaEmail a = new MyBooksViaEmail(ecopy,listener);
        /*RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(a);*/
        a.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(a);





        return view;
    }



    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

       /* inflater.inflate(R.menu.menu_search,menu);
        MenuItem searchItem = menu.findItem(R.id.search123);
        SearchManager searchManager = (SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        if (null != searchView) {
            searchView.setSearchableInfo(searchManager
                    .getSearchableInfo(getActivity().getComponentName()));
            searchView.setIconifiedByDefault(false);
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String keyword) {
                *//*Intent searchIntent = new Intent(FirstPage.this,Search.class);
                //send the keyword to the next screen
                searchIntent.putExtra("key",keyword);
                startActivity(searchIntent);*//*
                Bundle bundle = new Bundle();
                bundle.putString("key", keyword);
// set Fragmentclass Arguments
                Search search = new Search();
                search.setArguments(bundle);
                android.app.FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.firstpage, search);
                transaction.commit();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String keyword) {
                Bundle bundle = new Bundle();
                bundle.putString("key", keyword);
// set Fragmentclass Arguments
                Search search = new Search();
                search.setArguments(bundle);
                android.app.FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.firstpage, search);
                transaction.commit();
                return true;
            }
        });*/

        super.onCreateOptionsMenu(menu, inflater);

    }



    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Intent i = new Intent(getActivity(), NotConnected.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        dialog.dismiss();
        startActivity(i);


    }

    public static boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork!=null && activeNetwork.isConnectedOrConnecting();
    }


    @Override
    public void onRefresh() {
        fetchData();
    }

    public void fetchData(){

        //itemList = null;
        swipeLayout.setRefreshing(true);

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    Log.d("abcde",response);

                    JSONArray jsonArray = new JSONArray(response);
                    itemList.clear();

                    for(int i = 0; i < jsonArray.length(); i++)
                    {

                        JSONArray j = jsonArray.getJSONArray(i);


                      /*  String image = j.getString(3);
                        String name = j.getString(4);
                        String author = j.getString(5);
                        String description = j.getString(6);
                        String exchange_donate = j.getString(7);
                        String date_posted = j.getString(8);
                        BooksModel item = new BooksModel(image,name,author,description,exchange_donate,date_posted);
                        itemList.add(item);

*/


                        BooksData item = new BooksData();
                        item.book_image = j.getString(3);
                        item.book_name = j.getString(4);
                        item.book_author = j.getString(5);
                        item.book_description = j.getString(6);
                        String ed = j.getString(7);
                        if(ed.equals("E")){
                            item.book_exchange_donate = "Exchange";
                        }
                        else{
                            item.book_exchange_donate = "Donate";

                        }
                        item.book_date_posted = j.getString(8);
                        item.bid = j.getInt(0);
                        itemList.add(item);
                        Log.d("abcde",ed);

                    }


                    //t.setText(String.valueOf(sum));
                    swipeLayout.setRefreshing(false);



                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    Log.d("Data Set Chanhed","Data Set Cganhed");
                    mAdapter.notifyDataSetChanged();
                    swipeLayout.setRefreshing(false);


                }
            }
        };

        mAdapter.notifyDataSetChanged();
        MyBooksViaEmail a = new MyBooksViaEmail(ecopy,listener);
        /*RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(a);*/
        a.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getActivity()).addToRequestQueue(a);
        swipeLayout.setRefreshing(false);



    }


}
