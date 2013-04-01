package com.gpardy;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import java.net.URI;

public class BuzzIn extends Activity {
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.buzzin);

		Resources res = getResources();

		final Button buzzIn = (Button) findViewById(R.id.buzzIn);
		final Drawable defaultButtonDrawable = res.getDrawable(R.drawable.buzzinbutton);
		final Drawable pressedButtonDrawable = res.getDrawable(R.drawable.buzzinpressedbutton);
		buzzIn.setOnTouchListener(new View.OnTouchListener() {
			public boolean onTouch(View view, MotionEvent motionEvent) {
				Log.d("buzzin", "Button motion event is: " + motionEvent);
				if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
					buzzIn.setBackgroundDrawable(pressedButtonDrawable);
				} else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
					buzzIn.setBackgroundDrawable(defaultButtonDrawable);
				}
				return false;
			}
		});
		final Bundle extras = getIntent().getExtras();
		buzzIn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				new AsyncTask<String, Integer, String>() {
					@Override
					protected String doInBackground(String... strings) {
						try {
							String host = strings[0];
							String game = strings[1];
							String name = strings[2];
							String buzzInPath = "/r/channel/buzzin/" + game + "/" + name;
							URI uri = new URI("http", host, buzzInPath, null);
							Log.d("buzzin", "Buzz In URL: " + uri);
							uri.toURL().getContent();
							return null;
						} catch (Exception e) {
							Log.d("buzzin", "Error trying to Buzz In", e);
							return e.getMessage();
						}
					}

					@Override
					protected void onPreExecute() {
						setProgressBarIndeterminateVisibility(true);
						buzzIn.setEnabled(false);
					}

					@Override
					protected void onPostExecute(String result) {
						super.onPostExecute(result);
						setProgressBarIndeterminateVisibility(false);
						if (result == null) {
							Toast.makeText(BuzzIn.this, "Buzz In submitted", Toast.LENGTH_SHORT).show();
						} else {
							Toast.makeText(BuzzIn.this, "An error occurred trying to buzz in: " + result, Toast.LENGTH_LONG).show();
						}
						buzzIn.setEnabled(true);
					}
				}.execute(extras.getString("host"), extras.getString("game"), extras.getString("name"));
			}
		});
	}
}
