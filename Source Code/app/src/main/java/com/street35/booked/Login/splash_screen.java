package com.street35.booked.Login;

        import android.content.Intent;
        import android.os.Bundle;
        import android.os.Handler;
        import android.support.annotation.Nullable;
        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.ImageView;
        import com.street35.booked.R;

/**
 * Created by goelr on 15-07-2017.
 */

public class splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        final ImageView imageView = (ImageView)findViewById(R.id.imageView);

        imageView.postDelayed(new Runnable() {
            public void run() {
                imageView.setVisibility(View.VISIBLE);
            }
        }, 1000);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(getApplicationContext(), Authentication.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, 5000);
    }
}