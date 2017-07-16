package com.street35.booked.NetworkServices;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rashi on 01-11-2016.
 */

public class EmailExist extends StringRequest {
    private static final String emailcheck_url = "http://ieeedtu.com/testbook/emailexistence.php?email=";
    // private Map<String, String > params;


    public EmailExist(String email , String password, Response.Listener<String> listener){
        super(Method.GET, emailcheck_url+email+"&password="+password , listener, null);
/*
        params= new HashMap<>();
        params.put("email",email);
        params.put("password",password);
*/
    }

   /* @Override
    public Map<String, String> getParams() {
        return params;
    }*/
   @Override
   public Map<String, String> getHeaders() throws AuthFailureError {
       Map<String, String>  params = new HashMap<String, String>();

       return params;
   }
}
