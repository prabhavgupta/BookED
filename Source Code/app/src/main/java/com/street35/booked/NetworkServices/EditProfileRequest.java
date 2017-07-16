package com.street35.booked.NetworkServices;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rashi on 21-08-2016.
 */
public class EditProfileRequest extends StringRequest{

    private static final String Reg_req_url = "http://ieeedtu.com/testbook/profile.php";
    private Map<String, String > params;

    public EditProfileRequest(String first_name, String last_name, String university,
                              String contact, String address, String sex , String email ,String lt,
                              String ln, Response.Listener<String> listener){

        super(Method.POST, Reg_req_url , listener , null);


        params= new HashMap<>();
        params.put("fname",first_name);
        params.put("lname",last_name);
        params.put("university",university);
        params.put("contact",contact);
        params.put("address",address);
        params.put("sex",String.valueOf(sex));
        params.put("email",email);
        params.put("latitude",lt);
        params.put("longitude",ln);



    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String>  params = new HashMap<String, String>();

        return params;
    }

}