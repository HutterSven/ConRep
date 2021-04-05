package com.example.conrep.ui.actionBar;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.conrep.R;
import com.example.conrep.ui.BaseActivity;

import java.util.Locale;

public class SettingsActivity extends BaseActivity implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {

    private static final String KEY_PREF_LANGUAGE = "pref_language";
    public String languagePref_ID;
    private boolean darkModeActivated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_settings, frameLayout);

        // below line is to change
        // the title of our action bar.
        getSupportActionBar().setTitle("Settings");

        // below line is used to check if
        // frame layout is empty or not.
        if (findViewById(R.id.idFrameLayout) != null) {
            if (savedInstanceState != null) {
                return;
            }
            // below line is to inflate our fragment.
            getFragmentManager().beginTransaction().add(R.id.idFrameLayout, new SettingsFragment()).commit();
        }

        Switch sDarkMode = findViewById(R.id.sDarkMode);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        darkModeActivated = prefs.getBoolean("darkmode", false);
        sDarkMode = (Switch) findViewById(R.id.sDarkMode);
        sDarkMode.setChecked(darkModeActivated);
        sDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    darkModeActivated = prefs.edit().putBoolean("darkmode", true).commit();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    darkModeActivated = prefs.edit().putBoolean("darkmode", false).commit();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value)
    {
        String textValue = value.toString();

        ListPreference listPreference = (ListPreference) preference;
        int index = listPreference.findIndexOfValue(textValue);

        CharSequence[] entries = listPreference.getEntries();

        if(index >= 0)
            Toast.makeText(preference.getContext(), entries[index], Toast.LENGTH_LONG);

        return true;
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(KEY_PREF_LANGUAGE)) {
            languagePref_ID = sharedPreferences.getString(SettingsActivity.KEY_PREF_LANGUAGE, "2");
            switch (languagePref_ID) {
                case "1":
                    Locale localeEN = new Locale("en_US");
                    setLocale(localeEN);
                    break;
                case "2":
                    Locale localeHU = new Locale("hu_HU");
                    setLocale(localeHU);
                    break;
            }
        }
    }

    public void setLocale(Locale locale) {
        Locale.setDefault(locale);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = locale;
        res.updateConfiguration(conf, dm);
        recreate();
    }
}