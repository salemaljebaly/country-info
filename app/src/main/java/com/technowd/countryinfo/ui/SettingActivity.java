package com.technowd.countryinfo.ui;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.technowd.countryinfo.MainActivity;
import com.technowd.countryinfo.R;
import com.technowd.countryinfo.generals.Setting;

public class SettingActivity extends AppCompatActivity {
    private RadioButton radio_center_flag, radio_side_flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        // set toolbar
        setSupportActionBar((Toolbar) findViewById(R.id.setting_toolbar));
        // init radios
        radio_center_flag = findViewById(R.id.radiio_center_flag);
        radio_side_flag = findViewById(R.id.radiio_side_flag);
        // check any last layout users selected
        checkAnyRadioSelected();
    }

    // check any last layout users selected
    private void checkAnyRadioSelected() {
        if(Setting.userLayoutSelect == Setting.centerFlagRow){
            radio_center_flag.setChecked(true);
        } else if(Setting.userLayoutSelect == Setting.sideFlagRow){
            radio_side_flag.setChecked(true);
        } else {
            radio_side_flag.setChecked(true);
        }
    }


    // go to main activity
    public void goToMain(){
        Intent  intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.back_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.back) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return true;
    }
    // save setting of layout from user
    public void saveSetting(View view) {

        // change layout depends on user selected
        if(radio_center_flag.isChecked()){
            Setting.userLayoutSelect = Setting.centerFlagRow;
            goToMain();
        } else if(radio_side_flag.isChecked()){
            Setting.userLayoutSelect = Setting.sideFlagRow;
             goToMain();
        } else {
            Setting.userLayoutSelect = Setting.sideFlagRow;
             goToMain();
        }
    }
}
