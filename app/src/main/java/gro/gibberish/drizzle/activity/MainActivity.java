package gro.gibberish.drizzle.activity;

    import android.app.FragmentTransaction;
    import android.content.Intent;
    import android.content.SharedPreferences;
    import android.net.Uri;
    import android.os.Bundle;
    import android.preference.PreferenceManager;
    import android.support.v7.app.ActionBarActivity;
    import android.support.v7.widget.Toolbar;
    import android.view.Menu;
    import android.view.MenuItem;

    import gro.gibberish.drizzle.R;
    import gro.gibberish.drizzle.ui.LocationDetailFragment;
    import gro.gibberish.drizzle.ui.LocationListFragment;


public class MainActivity extends ActionBarActivity implements
        LocationListFragment.OnFragmentInteractionListener {

    private static final int ONE_HOUR_MS = 3600000;
    private static final String SP_LAST_REFRESH = "SP_LAST_REFRESH";
    private static final String LOCATIONS = "locations";
    private boolean needsRefresh = false;
    private String API_KEY;
    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        API_KEY = getApplicationContext().getResources().getString(R.string.api_key);
        sp = PreferenceManager.getDefaultSharedPreferences(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        long lastRefresh = sp.getLong(SP_LAST_REFRESH, 0L);
        if (System.currentTimeMillis() - lastRefresh > ONE_HOUR_MS) {
            // refresh weather data automatically, set lastrefresh to now
            needsRefresh = true;
        }
        String mLocations = sp.getString(LOCATIONS, null);
        if (savedInstanceState == null) {
            LocationListFragment f = LocationListFragment.newInstance("4180439,5660340", API_KEY, needsRefresh);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // Actually have some settings, like units (imperial, metric)
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
    public void onListWeatherRefreshed(long l) {
        // TODO Think about when the last refresh is saved, should each location have its own SP for this just for forecast data?
        sp.edit().putLong(SP_LAST_REFRESH, l).apply();
    }

    @Override
    public void onLocationChosen(long id) {
        Intent i = new Intent();
        i.setClass(this, DetailActivity.class);
        i.putExtra("api_key", API_KEY);
        i.putExtra("id", Long.toString(id));
        startActivity(i);
    }

    @Override
    public void onLocationAdded(String locations) {
        sp.edit().putString(LOCATIONS, locations).apply();
    }

}
