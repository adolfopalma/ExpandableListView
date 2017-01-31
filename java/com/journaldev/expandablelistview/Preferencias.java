package com.journaldev.expandablelistview;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by adolf on 28/01/2017.
 */

public class Preferencias extends PreferenceActivity {
    //  SharedPreferences prefe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
    }
}
