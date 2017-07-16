package com.street35.booked.NetworkServices;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rashi on 01-11-2016.
 */

public class EnterProfileDetails extends StringRequest {

    private static final String Reg_req_url = "http://ieeedtu.com/testbook/profileDetailsOnReg.php?first_name=";
    private Map<String, String > params;

    public EnterProfileDetails(String first_name, String last_name, String address, String email , Response.Listener<String> listener){
        super(Request.Method.GET, Reg_req_url+first_name+"&last_name="+last_name+"&address="+address+"&email="+email , listener , null);
        /*params= new HashMap<>();
        params.put("fname",first_name);
        params.put("lname",last_name);
        params.put("address",address);

        params.put("email",email);*/



    }

 /*   @Override
    public Map<String, String> getParams() {
        return params;
    }
*/
 @Override
 public Map<String, String> getHeaders() throws AuthFailureError {
     Map<String, String>  params = new HashMap<String, String>();

     return params;
 }
}

