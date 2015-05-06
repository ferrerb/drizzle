package gro.gibberish.drizzle.ui;

import android.view.View;

/**
 * An interface to pass to a recyclerview adapter, to deal with click events
 */
public interface OnItemTouchListener {
    void onLocationClick(View view, int position);
}
