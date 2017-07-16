package com.street35.booked.NetworkServices;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rashi on 31-08-2016.
 */
public class EnterDetailsRequest extends StringRequest
{



        private static final String Log_req_url = "http://ieeedtu.com/testbook/EnterBooks.php";
        private Map<String, String > params;

        public EnterDetailsRequest( String username , String imageurl ,String bookname , String bookauthor,
                                    String ed, String date,
                                    Response.Listener<String> listener){
            super(Method.POST, Log_req_url , listener, null);
            params= new HashMap<>();
            params.put("username",username);
            params.put("imageurl",imageurl);
            params.put("bookname",bookname);
            params.put("bookauthor",bookauthor);
            params.put("ed",ed);
            params.put("date_posted",date);

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
