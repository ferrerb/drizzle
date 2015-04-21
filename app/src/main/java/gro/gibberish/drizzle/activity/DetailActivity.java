package gro.gibberish.drizzle.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.ui.LocationDetailFragment;

/**
 * Hosts the location detail fragment
 */
public class DetailActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        if (savedInstanceState == null) {
            LocationDetailFragment frag = new LocationDetailFragment();
            frag.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.location_detail, frag).commit();
        }
    }
}
