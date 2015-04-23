package gro.gibberish.drizzle.ui;

import android.app.Fragment;
import android.support.v7.widget.RecyclerView;

/**
 * Implements the boilerplate parts of a recyclerview so the actual fragment isn't filled with all
 * this.
 */
public class RecyclerViewFragment extends Fragment {
    private RecyclerView rv = null;

    public void setAdapter(RecyclerView.Adapter adapter) {
        getRecyclerView().setAdapter(adapter);
    }

    public RecyclerView.Adapter getAdapter() {
        return getRecyclerView().getAdapter();
    }

    public void setLayoutManager(RecyclerView.LayoutManager lm) {
        getRecyclerView().setLayoutManager(lm);
    }

    public RecyclerView getRecyclerView() {
        if (rv == null) {
            rv = new RecyclerView(getActivity().getBaseContext());
            rv.setHasFixedSize(true);
            getActivity().setContentView(rv);
        }

        return rv;
    }
}
