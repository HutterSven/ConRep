package com.example.conrep.ui.actionBar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;

import com.example.conrep.R;

import java.util.Locale;

public class SettingsFragment extends PreferenceFragmentCompat{

    private boolean darkModeActivated;
    private String setLang;
    private SharedPreferences prefs;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        SwitchPreferenceCompat sDarkMode = findPreference("key_darkmode");
        setDarkModeActivated(prefs.getBoolean("key_darkmode", false));
        sDarkMode.setChecked(darkModeActivated);
        sDarkMode.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                boolean switched = ((SwitchPreferenceCompat) preference)
                        .isChecked();
                boolean update = !switched;
                setDarkMode(switched);

                return true;
            }
        });

        ListPreference languages = findPreference("pref_language");
        setSetLang(prefs.getString("pref_language", "EN"));
        languages.setValue(setLang);
        languages.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                switch (languages.getValue()){
                    case "EN":
                        setLocale("EN");
                        break;
                    case "DE":
                        setLocale("DE");
                        break;
                    default:
                        break;
                }
                return true;
            }
        } );
    }

    public void setDarkModeActivated(boolean darkModeActivated) {
        this.darkModeActivated = darkModeActivated;
    }

    public void setSetLang(String setLang) {
        this.setLang = setLang;
    }

    public void setPrefs(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
        prefs.edit().putString("pref_language", lang);
        Intent refresh = new Intent(getActivity(), getActivity().getClass());
        getActivity().finish();
        startActivity(refresh);
    }

    public void setDarkMode(boolean mode){
        prefs.edit().putBoolean("key_darkmode", mode);
        Intent refresh = new Intent(getActivity(), getActivity().getClass());
        getActivity().finish();
        startActivity(refresh);
    }

}
