package com.street35.booked.NetworkServices;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Weirdo on 23-12-2016.
 */

public class LoginDetailsCheck extends StringRequest {

    private static final String FirstEntry_url = "http://ieeedtu.com/testbook/FirstEntry.php";
    
    private Map<String, String > params;

    public LoginDetailsCheck(String email , String fname , String lname , Response.Listener<String> listener){
        super(Method.POST, FirstEntry_url , listener, null);
        params= new HashMap<>();
        params.put("email",email);
        params.put("fname",fname);
        params.put("lname",lname);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String>  params = new HashMap<String, String>();
      /*  params.put("Cookie","__test=44c3613f5fdf5542f710c31f6a68779a; expires=Thu, 31-Dec-37 23:55:55 GMT; path=/");
        params.put("Content-Type", "application/json; charset=utf-8");
        */
        return params;
    }

}
