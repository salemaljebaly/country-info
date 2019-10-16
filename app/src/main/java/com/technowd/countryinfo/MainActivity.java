package com.technowd.countryinfo;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.technowd.countryinfo.generals.Setting;
import com.technowd.countryinfo.model.CountryCode;
import com.technowd.countryinfo.ui.CountryDialog;
import com.technowd.countryinfo.ui.PopupDialog;
import com.technowd.countryinfo.ui.SettingActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private CountryInfoAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private List<CountryCode> countryCode;
    private SearchView searchView;
    private PopupDialog popupDialog ;
    private AdView mAdView;
    // public SharedPreferences sharedPreferences;// use to store last selected layout from user
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.main_toolbar);
        mRecyclerView = findViewById(R.id.recycler_view);
        // tool bar
        setSupportActionBar(toolbar);
        buildRecycler();
        loadAds(); // load ads
    }

    private void loadAds() {
        MobileAds.initialize(this, Setting.APP_ID);
        // admob
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                // .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }

    private void buildRecycler() {
        // take file path as input stream
        InputStream coutryinfoPath = null;
        BufferedReader reader = null;
        try {
            coutryinfoPath = this.getAssets().open("country_info.json");
            reader = new BufferedReader(new InputStreamReader(coutryinfoPath, StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new GsonBuilder().serializeNulls().create();
        Type cC = new TypeToken<ArrayList<CountryCode>>(){}.getType();
        countryCode = gson.fromJson(reader,cC);
        mLayoutManager = new LinearLayoutManager(this);
        // second param to select layout
        mAdapter = new CountryInfoAdapter(countryCode,Setting.userLayoutSelect);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CountryInfoAdapter.OnClickListener() {
            @Override
            public void onItemClick(int position) {
                CountryDialog countryDialog = new CountryDialog();
                Bundle args = new Bundle();
                args.putSerializable("countryCode", countryCode.get(position));
                countryDialog.setArguments(args);
                countryDialog.show(getSupportFragmentManager(),"countryDialog");
            }
        });
    }


    // Create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mAdapter.getFilter().filter(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return true;
            }
        });
        return true;
        // return super.onCreateOptionsMenu(menu);
    }

    // select menu item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.setting:
                Intent setting = new Intent(this, SettingActivity.class);
                startActivity(setting);
                finish();
            return true;
            case R.id.about:
                // show about dialog
                popupDialog = new PopupDialog(this,R.layout.popu_up_dialog);
                popupDialog.showDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // About methods

    public void shareApp(View view) {
        String shareLink = getString(R.string.google_play_app_url);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT,shareLink);
        startActivity(share);
    }

    public void googlePlay(View view) {
        Uri googlePlayAccount = Uri.parse(getString(R.string.publisher_url));
        Intent googlePlay = new Intent(Intent.ACTION_VIEW, googlePlayAccount);
        startActivity(googlePlay);
    }

    public void mailTo(View view) {
        String[] to = new String[]{getString(R.string.developer_email)};
        Intent sendMail = new Intent(Intent.ACTION_SEND);
        sendMail.setData(Uri.parse("mailto:"));
        sendMail.setType("text/plain");
        sendMail.putExtra(Intent.EXTRA_EMAIL,to);
        sendMail.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
        sendMail.putExtra(Intent.EXTRA_TITLE, R.string.app_name);
        sendMail.putExtra(Intent.EXTRA_TEXT, R.string.write_your_notes);
        startActivity(sendMail);
    }

    public void facebookAccount(View view) {
        Uri facebookLink = Uri.parse(getString(R.string.facebook_url));
        Intent facebook = new Intent(Intent.ACTION_VIEW, facebookLink);
        startActivity(facebook);
    }

    public void closeDialog(View view) {
        popupDialog.dismissDialog();
    }
}
