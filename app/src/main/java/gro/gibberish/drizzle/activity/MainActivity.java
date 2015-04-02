package gro.gibberish.drizzle.activity;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.ui.LocationListFragment;


public class MainActivity extends ActionBarActivity implements
        LocationListFragment.OnFragmentInteractionListener {

    private int lastRefresh;
    private final String LAST_REFRESH = "LAST_REFRESH";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        if (savedInstanceState == null) {
            LocationListFragment f = LocationListFragment.newInstance(null, null);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.location_list, f).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        lastRefresh = sp.getInt(LAST_REFRESH, 0);
        // 900000 == 15 minutes in ms
        if (System.currentTimeMillis() - lastRefresh > 900000) {
            // refresh weather data automatically, set lastrefresh to now
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        sp.edit().putInt(LAST_REFRESH, lastRefresh);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //asdf
    }
}
