package com.example.conrep.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.conrep.R;
import com.example.conrep.ui.actionBar.About;
import com.example.conrep.ui.actionBar.SettingsActivity;

import java.util.Locale;

public class BaseActivity extends AppCompatActivity{
    public static final String PREFS_NAME = "SharedPrefs";
    public static final String PREFS_USER = "LoggedIn";
    private static final String KEY_PREF_LANGUAGE = String.valueOf(R.string.pref_language);
    /**
     *  Frame layout: Which is going to be used as parent layout for child activity layout.
     *  This layout is protected so that child activity can access this
     */
    protected FrameLayout frameLayout;

    /**
     * Static variable for selected item position. Which can be used in child activity to know which item is selected from the list.
     */
    protected static int position;

    public String languagePref_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        switch (sharedPref.getString("pref_language", "EN")) {
            case "EN":
                Locale localeEN = new Locale("EN");
                setLocaleOnCreate(localeEN);
                break;
            case "DE":
                Locale localeDE = new Locale("DE");
                setLocaleOnCreate(localeDE);
                break;
        }
        if (sharedPref.getBoolean("key_darkmode", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        setContentView(R.layout.activity_base);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        frameLayout = findViewById(R.id.flContent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        BaseActivity.position = 0;
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }else
            if (item.getItemId() == R.id.about) {
            Intent intent = new Intent(this, About.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void setLocaleOnCreate(Locale locale) {
        Locale.setDefault(locale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
    }

}