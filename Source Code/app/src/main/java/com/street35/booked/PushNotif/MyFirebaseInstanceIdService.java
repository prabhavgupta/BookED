package com.street35.booked.PushNotif;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by Weirdo on 18-12-2016.
 */

public class MyFirebaseInstanceIdService extends com.google.firebase.iid.FirebaseInstanceIdService {


    private static final String REG_TOKEN = "REG_TOKEN";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String recent_token = FirebaseInstanceId.getInstance().getToken();
        Log.d(REG_TOKEN,recent_token);

        //sendRegistrationToServer(recent_token);

    }
}
