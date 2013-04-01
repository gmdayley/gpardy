package com.gpardy;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.URI;

/**
 * @author Mike Heath <heathma@ldschurch.org>
 */
public class SignIn extends Activity {
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.itemPrefs:
				Intent intent = new Intent(getApplicationContext(), GpardyPreferencesActivity.class);
				startActivity(intent);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

		final EditText game = (EditText) findViewById(R.id.game);
		final EditText name = (EditText) findViewById(R.id.name);

		Button signIn = (Button) findViewById(R.id.signIn);
		signIn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				new AsyncTask<String, Integer, Object>() {
					@Override
					protected Object doInBackground(String... strings) {
						try {
							String host = preferences.getString("host", "g-pardy.appspot.com");
							String gameValue = game.getText().toString();
							String nameValue = name.getText().toString();

							URI uri = new URI("http", host, "/r/channel/join/" + gameValue + "/" + nameValue, null);
							Log.d("signin", "Signing in to URL:" + uri);
							uri.toURL().getContent();
						} catch (Exception e) {
							Log.e("signin", "Error signing in", e);
						}

						return null;
					}

					@Override
					protected void onPostExecute(Object o) {
						super.onPostExecute(o);

						String host = preferences.getString("host", "g-pardy.appspot.com");
						String gameValue = game.getText().toString();
						String nameValue = name.getText().toString();

						Bundle bundle = new Bundle();
						bundle.putString("host", host);
						bundle.putString("game", gameValue);
						bundle.putString("name", nameValue);

						Intent intent = new Intent(getApplicationContext(), BuzzIn.class);
						intent.putExtras(bundle);
						startActivityForResult(intent, 0);
					}
				}.execute();
			}
		});



	}
}