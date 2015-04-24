package gro.gibberish.drizzle.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.models.LocationModel;

/**
 * Change this
 */
public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.RowHolder> {
    List<LocationModel> mLocationList;

    public WeatherListAdapter(List<LocationModel> locationList) {
        mLocationList = locationList;
    }

    @Override
    public RowHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.location_list_row, viewGroup, false);

        return new RowHolder(v);
    }

    @Override
    public void onBindViewHolder(RowHolder holder, final int position) {
        holder.bindModel(mLocationList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mLocationList == null) {
            return 0;
        }
        return mLocationList.size();

    }

    public static class RowHolder extends RecyclerView.ViewHolder {
        private final TextView locationName;
        private final TextView locationTemp;
        public RowHolder(View row) {
            super(row);
            this.locationName = (TextView) row.findViewById(R.id.location_list_name);
            this.locationTemp = (TextView) row.findViewById(R.id.location_list_current_temp);
        }

        void bindModel(LocationModel data) {
            locationName.setText(data.getName());
            locationTemp.setText("2343");
            //locationTemp.setText(Double.toString(data.getMain().getTemp()));
        }
    }
}