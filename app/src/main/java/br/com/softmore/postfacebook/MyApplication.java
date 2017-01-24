package br.com.softmore.postfacebook;

import android.app.Application;

/**
 * Created by rapha on 23/01/2017.
 */
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate();
        AppEventsLogger.activateApp(this);
    }
}
