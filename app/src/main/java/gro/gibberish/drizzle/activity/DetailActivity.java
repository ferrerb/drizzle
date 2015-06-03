package gro.gibberish.drizzle.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.ui.LocationDetailFragment;

/**
 * Hosts the location detail fragment
 */
public class DetailActivity extends ActionBarActivity implements LocationDetailFragment.OnLocationDetailCallbacks{
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

    @Override
    public void onDeleteLocation(String location) {
        // TODO Open the mainactivity with an intent containing the location string?
        // TODO Could this be some eventbus/observable thing?
    }
}
