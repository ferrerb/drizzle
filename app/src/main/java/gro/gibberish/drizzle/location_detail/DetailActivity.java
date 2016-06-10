package gro.gibberish.drizzle.location_detail;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.common.BaseActivity;

public class DetailActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.detail_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        if (savedInstanceState == null) {
            DetailFragment frag = new DetailFragment();
            frag.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.location_detail, frag).commit();
        }
    }
}
