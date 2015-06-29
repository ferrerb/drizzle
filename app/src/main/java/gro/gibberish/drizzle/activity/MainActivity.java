package gro.gibberish.drizzle.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.ui.LocationListFragment;


public class MainActivity extends AppCompatActivity implements
        LocationListFragment.OnFragmentInteractionListener {

    private String API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Check for internet access
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        API_KEY = getResources().getString(R.string.api_key);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        // TODO Decide how to store locations
        if (savedInstanceState == null) {
            LocationListFragment f = LocationListFragment.newInstance(API_KEY);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.weather_content, f).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onLocationChosen(long id) {
        Intent i = new Intent();
        i.setClass(this, DetailActivity.class);
        i.putExtra("api_key", API_KEY);
        i.putExtra("id", Long.toString(id));
        startActivity(i);
    }
}
