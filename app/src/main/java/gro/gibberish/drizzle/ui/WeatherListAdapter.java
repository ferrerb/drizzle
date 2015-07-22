package gro.gibberish.drizzle.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.util.NumberFormatting;
import gro.gibberish.drizzle.models.LocationModel;

public class WeatherListAdapter extends RecyclerView.Adapter<WeatherListAdapter.RowHolder> {
    List<LocationModel> mLocationList;
    OnItemTouchListener mOnItemTouchListener;

    public WeatherListAdapter(List<LocationModel> locationList, OnItemTouchListener listener) {
        // TODO check for null? throw exception if null?
        mLocationList = locationList;
        mOnItemTouchListener = listener;
    }

    @Override
    public RowHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.location_list_row, viewGroup, false);

        return new RowHolder(v, mOnItemTouchListener);
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

        public RowHolder(View row, OnItemTouchListener listener) {
            super(row);
            this.locationName = (TextView) row.findViewById(R.id.location_list_name);
            this.locationTemp = (TextView) row.findViewById(R.id.location_list_current_temp);
            row.setOnClickListener(v -> listener.onLocationClick(v, getAdapterPosition()));
        }


        void bindModel(LocationModel data) {
            locationName.setText(data.getName());
            locationTemp.setText(
                    NumberFormatting.doubleToStringNoDecimals(data.getMain().getTemp()) + "\u00b0");
        }
    }
}