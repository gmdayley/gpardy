package com.gpardy;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * @author Mike Heath <heathma@ldschurch.org>
 */
public class GpardyPreferencesActivity extends PreferenceActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences);
	}
}