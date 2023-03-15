package com.example.pipati;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class FragmentoAjustes extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String key) {
        setPreferencesFromResource(R.xml.preferences, key);
    }
}