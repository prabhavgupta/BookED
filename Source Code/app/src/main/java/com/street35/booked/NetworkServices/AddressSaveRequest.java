package com.street35.booked.NetworkServices;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Weirdo on 30-12-2016.
 */

public class AddressSaveRequest extends StringRequest {

    private static final String Reg_req_url = "http://ieeedtu.com/testbook/AddressSaveRequest.php";
    private Map<String, String > params;

    public AddressSaveRequest( String university,String contact, String address, String email ,String lt,
                              String ln, Response.Listener<String> listener){

        super(Method.POST, Reg_req_url , listener , null);


        params= new HashMap<>();

        Log.d("pppppppppppppppppppp",email+university);
        params.put("university",university);
        params.put("contact",contact);
        params.put("address",address);
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