package gro.gibberish.drizzle.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.models.LocationForecastModel;
import gro.gibberish.drizzle.models.LocationModel;

/**
 * Change this
 */
public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.RowHolder> {
    LocationForecastModel mData;
    //TODO Move weatherList and temp to own class? to create a list of them here

    public WeatherForecastAdapter(LocationForecastModel data) {
        // TODO check for null? throw exception if null?
        mData = data;
    }

    @Override
    public RowHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.forecast_list_row, viewGroup, false);

        return new RowHolder(v);
    }

    @Override
    public void onBindViewHolder(RowHolder holder, final int position) {
        holder.bindModel(mLocationList.get(position));
    }

    @Override
    public int getItemCount() {
        if ( == null) {
            return 0;
        }
        return mLocationList.size();

    }

    public static class RowHolder extends RecyclerView.ViewHolder {
        private final TextView dayName;
        private final TextView dayHiTemp;
        private final TextView dayLoTemp;
        private final TextView dayPrecipChance;

        public RowHolder(View row) {
            super(row);
            this.dayName = (TextView) row.findViewById(R.id.forecast_row_day);
            this.dayHiTemp = (TextView) row.findViewById(R.id.forecast_row_hi);
            this.dayLoTemp = (TextView) row.findViewById(R.id.forecast_row_lo);
            this.dayPrecipChance = (TextView) row.findViewById(R.id.forecast_row_precip_chance);
        }


        void bindModel(LocationModel data) {

        }
    }
}