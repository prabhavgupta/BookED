package com.street35.booked.AllBooks;

/**
 * Created by Rashi on 20-08-2016.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.street35.booked.NetworkServices.VolleySingleton;
import com.street35.booked.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserBooksAdapter extends RecyclerView.Adapter<UserBooksAdapter.MyViewHolder> implements RecyclerView.OnItemTouchListener {




    private List<BooksData> itemlist;
    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }





    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView book_name , book_author ,  book_exchange_donate, book_date_posted;
        ImageView book_image;

        public MyViewHolder(View view) {
            super(view);
            book_name = (TextView) view.findViewById(R.id.mbl_bookname);

            book_author = (TextView) view.findViewById(R.id.mbl_bookauthor);

            //book_description = (TextView) view.findViewById(R.id.mbl_bookdescription);

            book_image = (ImageView) view.findViewById(R.id.mbl_bookimage);

            book_exchange_donate = (TextView) view.findViewById(R.id.mbl_exchange_donate);

            book_date_posted = (TextView) view.findViewById(R.id.mbl_date_posted);

        }
    }



    public UserBooksAdapter(List<BooksData> itemList) {
        this.itemlist = itemList;
    }


    @Override
    public UserBooksAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.books_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final UserBooksAdapter.MyViewHolder holder, final int position) {

      /* final BooksModel booksModel = itemlist.get(position);
               holder.book_name.setText(booksModel.getBook_name());
               holder.book_author.setText(booksModel.getBook_author());
               holder.book_description.setText(booksModel.getBook_author());
               holder.book_date_posted.setText(booksModel.getBook_date_posted());
               holder.book_exchange_donate.setText(booksModel.getBook_exchange_donate());

*/


        final BooksData booksModel = itemlist.get(position);
        holder.book_name.setText(booksModel.book_name);
        holder.book_author.setText(booksModel.book_author);
        //holder.book_description.setText(booksModel.book_author);
        holder.book_date_posted.setText(booksModel.book_date_posted);
        holder.book_exchange_donate.setText(booksModel.book_exchange_donate);



        String image_url = booksModel.book_image;
         Context context = holder.book_image.getContext();

        if(image_url.equals("No Data")){
            image_url="http://dacssgranites.com/wp-content/uploads/2015/08/1429098017no-preview-available.jpg";
        }
        Log.d("url", image_url);

        Picasso.with(context).load(image_url)
                .resize(100,150)
                .onlyScaleDown()
                .centerCrop()
               .skipMemoryCache()
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(holder.book_image);




        /*Glide.with(context).load(image_url)

                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .crossFade()
                .into(holder.book_image);*/

      /*  final Uri imageUri = Uri.parse(image_url);
        holder.book_image.setImageURI(imageUri);
*/




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new MaterialDialog.Builder(v.getContext())
                        .title(booksModel.book_name)
                        .content("What would you like to do with it ?")
                        .positiveText("Exchange")
                        .negativeText("Donate")
                        .neutralText("Delete")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // TODO
                                String url ="http://ieeedtu.com/testbook/ExchangeBook.php?bid="+booksModel.bid;
                                Log.d("url",url);

                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                // process your response here
                                                Log.d("response",response);
                                                Toast.makeText(v.getContext(),"Book is now up for Exchange. Refresh to view changes.",Toast.LENGTH_SHORT).show();

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        //perform operation here after getting error
                                    }
                                } ) {

                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        Map<String, String>  params = new HashMap<String, String>();
                                        return params;
                                    }

                                };


                                VolleySingleton.getInstance(v.getContext()).addToRequestQueue(stringRequest);




                            }
                        })
                        .onNeutral(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // TODO

                                String url ="http://ieeedtu.com/testbook/DeleteBook.php?bid="+booksModel.bid;
                                Log.d("url",url);

                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                // process your response here
                                                Log.d("response",response);
                                                Toast.makeText(v.getContext(),"Book Deleted. Refresh to view changes.",Toast.LENGTH_SHORT).show();

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        //perform operation here after getting error
                                    }
                                }){

                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        Map<String, String>  params = new HashMap<String, String>();
                                        return params;
                                    }

                                };

                                VolleySingleton.getInstance(v.getContext()).addToRequestQueue(stringRequest);



                            }
                        })
                        .onNegative(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                // TODO

                                String url ="http://ieeedtu.com/testbook/DonateBook.php?bid="+booksModel.bid;
                                Log.d("url",url);

                                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {
                                                // process your response here
                                                Log.d("response",response);
                                                Toast.makeText(v.getContext(),"Book is now up for Donate. Refresh to view changes.",Toast.LENGTH_SHORT).show();

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        //perform operation here after getting error
                                    }
                                }){

                                    @Override
                                    public Map<String, String> getHeaders() throws AuthFailureError {
                                        Map<String, String>  params = new HashMap<String, String>();
                                        return params;
                                    }

                                };

                                VolleySingleton.getInstance(v.getContext()).addToRequestQueue(stringRequest);



                            }
                        }).show();

            }
        });








    }

    @Override
    public int getItemCount() {
        return itemlist.size();
    }
}