package gro.gibberish.drizzle.mainlist;

import android.os.Bundle;

import java.util.Arrays;
import java.util.List;

import gro.gibberish.drizzle.common.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected List<Object> getModules() {
        return Arrays.<Object>asList(MainModule.class);
    }
}
