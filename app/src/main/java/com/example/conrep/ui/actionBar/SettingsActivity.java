package com.example.conrep.ui.actionBar;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;

import android.content.Intent;
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

public class SettingsActivity extends BaseActivity {

    private static final String KEY_PREF_LANGUAGE = "pref_language";
    public String languagePref_ID;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_settings, frameLayout);

        SettingsFragment settings = new SettingsFragment();

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        settings.setPrefs(prefs);

        getSupportFragmentManager().beginTransaction().replace(R.id.idFrameLayout, settings).commit();
    }

}