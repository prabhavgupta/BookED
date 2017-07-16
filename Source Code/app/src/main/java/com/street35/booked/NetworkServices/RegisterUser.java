package com.street35.booked.NetworkServices;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rashi on 01-11-2016.
 */

public class RegisterUser extends StringRequest {

    private static final String Reg_url = "http://ieeedtu.com/testbook/register.php";
    private Map<String, String > params;

    public RegisterUser(String fname, String lname , String email, String password ,
                        String loc , double lat, double lon, String phone , Response.Listener<String> listener){
        super(Method.GET, Reg_url, listener, null);
        params= new HashMap<>();
        params.put("first_name",fname);
        params.put("last_name",lname);
        params.put("address",loc);
        params.put("email",email);
        params.put("password",password);
        params.put("latitude",String.valueOf(lat));
        params.put("longitude",String.valueOf(lon));
        params.put("phone",phone);


    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String>  params = new HashMap<String, String>();
        params.put("Cookie","__test=44c3613f5fdf5542f710c31f6a68779a; expires=Thu, 31-Dec-37 23:55:55 GMT; path=/");

        return params;
    }
}