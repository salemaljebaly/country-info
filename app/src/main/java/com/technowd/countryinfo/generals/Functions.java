package com.technowd.countryinfo.generals;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Gravity;
import android.widget.Toast;

import com.technowd.countryinfo.MainActivity;

import java.io.IOException;
import java.io.InputStream;

public class Functions {
    private Context context;

    public Functions(Context context) {
        this.context = context;
    }
    // Check if there is internet connection
    public Boolean CheckInternet(){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return networkInfo != null && networkInfo.isConnected();
    }
}
