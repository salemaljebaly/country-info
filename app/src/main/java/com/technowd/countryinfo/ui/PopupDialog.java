package com.technowd.countryinfo.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;

import android.view.Window;
import android.widget.ImageView;

import java.util.Objects;

public class PopupDialog{
    private Context context; // context ( current activity )
    private int themeResId; // resource file it return int
    private Dialog dialog; // Dialog

    public PopupDialog(@NonNull Context context, int themeResId) {
        this.context = context;
        this.themeResId = themeResId;
    }

    // this function show dialog
    public void showDialog(){
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        try {
            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        } catch (NullPointerException ex){
            ex.printStackTrace();
        }
        dialog.setContentView(themeResId);
        dialog.show();
    }

    public void dismissDialog(){dialog.dismiss();}


}
