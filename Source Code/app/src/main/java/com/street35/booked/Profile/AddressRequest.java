package com.street35.booked.Profile;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rashi on 26-08-2016.
 */
public class AddressRequest extends StringRequest {

    private static final String Roll_url = "http://booked.16mb.com/location.php";
    private Map<String, String > params;

    public AddressRequest(String email,String latitude, String longitude , Response.Listener<String> listener){
        super(Request.Method.POST, Roll_url , listener, null);
        params= new HashMap<>();
        params.put("email",email);
        params.put("latitude",latitude);
        params.put("longitude",longitude);



    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

