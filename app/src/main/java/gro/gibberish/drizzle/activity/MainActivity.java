package gro.gibberish.drizzle.activity;

    import android.app.FragmentTransaction;
    import android.content.SharedPreferences;
    import android.net.Uri;
    import android.os.Bundle;
    import android.preference.PreferenceManager;
    import android.support.v7.app.ActionBarActivity;
    import android.support.v7.widget.Toolbar;
    import android.util.Log;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.widget.TextView;

    import java.util.List;

    import gro.gibberish.drizzle.R;
    import gro.gibberish.drizzle.http.WeatherApi;
    import gro.gibberish.drizzle.models.LocationModel;
    import gro.gibberish.drizzle.ui.LocationDetailFragment;
    import gro.gibberish.drizzle.ui.LocationListFragment;
    import rx.Observable;
    import rx.android.lifecycle.LifecycleObservable;
    import rx.android.schedulers.AndroidSchedulers;


public class MainActivity extends ActionBarActivity implements
        LocationDetailFragment.OnFragmentInteractionListener,
        LocationListFragment.OnFragmentInteractionListener {

    private static final int FIFTEEN_MINUTES_MS = 900000;
    private static final String SP_LAST_REFRESH = "SP_LAST_REFRESH";
    private static final String SERVICE_ENDPOINT ="http://api.openweathermap.org/data/2.5";
    private String API_KEY;
    private int lastRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        API_KEY = getApplicationContext().getResources().getString(R.string.api_key);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        if (savedInstanceState == null) {
            LocationListFragment f = LocationListFragment.newInstance("703448,2643743", API_KEY);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.weather_content, f).commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        lastRefresh = sp.getInt(SP_LAST_REFRESH, 0);
        // 900000 == 15 minutes in ms
        if (System.currentTimeMillis() - lastRefresh > FIFTEEN_MINUTES_MS) {
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
        sp.edit().putInt(SP_LAST_REFRESH, lastRefresh);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //asdf
    }

    @Override
    public void onLocationChosen(Uri uri) {
        // Interface from list fragment, launch detail fragment/activity
    }
}
