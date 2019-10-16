package com.technowd.countryinfo.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.technowd.countryinfo.R;
import com.technowd.countryinfo.generals.Functions;
import com.technowd.countryinfo.generals.Setting;
import com.technowd.countryinfo.model.CountryCode;

public class CountryDialog extends DialogFragment {
    private TextView dialog_country_name, dialog_country_call, dialog_country_capital, dialog_country_region, dialog_country_population, dialog_country_time, dialog_country_iso_code,
            dialog_country_area, dialog_country_iso_native_name, dialog_country_currencies_name,
            dialog_country_currencies_symbol, dialog_country_borders;
    private LinearLayout border_container;
    private ImageView dialog_country_flag, close_dialog, show_country_map;
    private View view;
    private CountryCode code;
    private Context context =  getActivity();
    private Functions functions = new Functions(getActivity());
    private AdView mAdView;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.country_dialog,null);
        // Set transparent background and no title
        if (getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }
        loadAds();
        Bundle mArgs = getArguments();
        assert mArgs != null;
        code = (CountryCode) mArgs.getSerializable("countryCode");
        initVariables();
        setTextViews();
        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        builder.setView(view);
        return builder.create();
    }

    private void loadAds() {
        MobileAds.initialize(getActivity(), Setting.APP_ID);
        // admob
        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                //.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }


    private void initVariables(){
        dialog_country_name = view.findViewById(R.id.dialog_country_name);
        dialog_country_call = view.findViewById(R.id.dialog_country_call);
        dialog_country_capital = view.findViewById(R.id.dialog_country_capital);
        dialog_country_region = view.findViewById(R.id.dialog_country_region);
        dialog_country_population = view.findViewById(R.id.dialog_country_population);
        dialog_country_time = view.findViewById(R.id.dialog_country_time);
        dialog_country_iso_code = view.findViewById(R.id.dialog_country_iso_code);
        dialog_country_area = view.findViewById(R.id.dialog_country_area);
        dialog_country_iso_native_name = view.findViewById(R.id.dialog_country_iso_native_name);
        dialog_country_currencies_name = view.findViewById(R.id.dialog_country_currencies_name);
        dialog_country_currencies_symbol = view.findViewById(R.id.dialog_country_currencies_symbol);
        dialog_country_borders = view.findViewById(R.id.dialog_country_borders);
        border_container = view.findViewById(R.id.border_container);
        // flag image
        dialog_country_flag = view.findViewById(R.id.dialog_country_flag);
        close_dialog = view.findViewById(R.id.close_dialog);
        show_country_map = view.findViewById(R.id.show_country_map);
    }

    private void setTextViews() {
        // get image from country name
        String countryFlagImage = code.getName().replace(" ", "_").toLowerCase();
        dialog_country_name.setText(code.getName());
        // check if there is calling code
        if(code.getCallingCodes().get(0).isEmpty()){
            Log.e("call",  "no call code");
            dialog_country_call.setText("Phone code :");
        } else {
            dialog_country_call.setText(String.format("Phone code : +%s", code.getCallingCodes().get(0)));
        }

        // check if there is capital
        if(code.getCapital().isEmpty()){
            Log.e("capital",  "no capital");
            dialog_country_capital.setText("Capital code : ");
        } else {
            dialog_country_capital.setText(String.format("Capital : %s",code.getCapital()));
        }
        dialog_country_region.setText(String.format("Region : %s",code.getRegion()));

        // set in try and catch to
        try {
            dialog_country_population.setText(String.format("Population : %s",code.getPopulation()));
            dialog_country_flag.setImageURI(Uri.parse("android.resource://com.technowd.countryinfo/drawable/" + countryFlagImage));
        } catch (NullPointerException ex){
            ex.printStackTrace();
        }
        dialog_country_time.setText(String.format("Time Zone : \n%s", code.getTimezones()));
        dialog_country_iso_code.setText(String.format("Iso Code : %s", code.getAlpha2Code()));

        // check if area not null
        if(code.getArea() == null){
            dialog_country_area.setText("");
        }else {dialog_country_area.setText(String.format("Area : %s", code.getArea()));}

        dialog_country_iso_native_name.setText(String.format("Native name : %s", code.getNativeName()));
        dialog_country_currencies_name.setText(String.format("Name : %s", code.getCurrencies().get(0).getName()));
        dialog_country_currencies_symbol.setText(String.format("Symbol : %s", code.getCurrencies().get(0).getSymbol()));
        StringBuilder allBorders = new StringBuilder();
        for (String countryCode : code.getBorders()){
            allBorders.append(countryCode).append(" ");
        }
        // hide if country does not have borders
        if (code.getBorders().isEmpty()){
            border_container.setVisibility(View.GONE);
        } else {
            dialog_country_borders.setText(allBorders.toString());
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        show_country_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    try {
                        String countryName = code.getName();
                        Intent googleMap = new Intent(Intent.ACTION_VIEW);
                        // get user location on map and make marker on it
                        googleMap.setData(Uri.parse("geo:0,0?q=" + countryName));
                        googleMap.setPackage("com.google.android.apps.maps");
                        if (googleMap.resolveActivity(getActivity().getPackageManager()) != null) {
                            startActivity(googleMap);
                        }
                        dismiss();
                        // set code object to null to prevent ActivityNotFoundException
                        code = null;
                    } catch(NullPointerException e){
                        e.printStackTrace();
                    }catch(IndexOutOfBoundsException e){
                        e.printStackTrace();
                    }
                }
        });
    }
}
