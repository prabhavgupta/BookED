package com.street35.booked.Login;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by Rashi on 08-10-2016.
 */
public class Validation extends StringRequest {

    private static final String val_url = "http://apilayer.net/api/check?access_key=6fda277bf57f459172e4559706fde422&email=";
    //private Map<String, String > params;

    public Validation(String email, Response.Listener<String> listener) {
        super(Method.POST, val_url +email , listener, null);



    }
}
