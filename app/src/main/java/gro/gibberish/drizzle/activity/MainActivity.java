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

    import gro.gibberish.drizzle.R;
    import gro.gibberish.drizzle.http.WeatherApi;
    import gro.gibberish.drizzle.ui.LocationListFragment;
    import rx.android.schedulers.AndroidSchedulers;


public class MainActivity extends ActionBarActivity implements
        LocationListFragment.OnFragmentInteractionListener {

    private int lastRefresh;
    private static final int FIFTEEN_MINUTES_MS = 900000;
    private static final String SP_LAST_REFRESH = "SP_LAST_REFRESH";
    private String API_KEY;
    private static final String SERVICE_ENDPOINT ="http://api.openweathermap.org/data/2.5";

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
            LocationListFragment f = LocationListFragment.newInstance(null, null);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.location_list, f).commit();
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
        TextView city = (TextView) findViewById(R.id.city_name);

        WeatherApi.getWeatherService().getLocationDetailWeather("30319,us", API_KEY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        locationList -> Log.d("city =", locationList.getName()),
                        error -> Log.d("error", error.getMessage()),
                        () -> Log.d("complete", "yay"));
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
        sp.edit().putInt(SP_LAST_REFRESH, lastRefresh);

    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //asdf
    }
}
