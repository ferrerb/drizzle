package gro.gibberish.drizzle.ui;

import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gro.gibberish.drizzle.R;
import gro.gibberish.drizzle.util.NumberFormatting;
import gro.gibberish.drizzle.weather_beans.LocationForecastModel;
import gro.gibberish.drizzle.weather_beans.WeatherList;

/**
 * Change this
 */
public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.RowHolder> {
    List<WeatherList> mWeatherList = new ArrayList<WeatherList>();

    public WeatherForecastAdapter(LocationForecastModel data) {
        mWeatherList = data.getList();
    }

    @Override
    public RowHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.forecast_list_row, viewGroup, false);
        // TODO create a header that helps specify what each column is (Day, High, Low, Pressure)

        return new RowHolder(v);
    }

    @Override
    public void onBindViewHolder(RowHolder holder, final int position) {
        holder.bindModel(mWeatherList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mWeatherList == null) {
            return 0;
        }
        return mWeatherList.size();

    }

    public static class RowHolder extends RecyclerView.ViewHolder {
        private final TextView dayName;
        private final TextView dayHiTemp;
        private final TextView dayLoTemp;
        private final TextView dayPressure;

        public RowHolder(View row) {
            super(row);
            this.dayName = (TextView) row.findViewById(R.id.forecast_row_day);
            this.dayHiTemp = (TextView) row.findViewById(R.id.forecast_row_hi);
            this.dayLoTemp = (TextView) row.findViewById(R.id.forecast_row_lo);
            this.dayPressure = (TextView) row.findViewById(R.id.forecast_row_pressure);
        }


        void bindModel(WeatherList data) {
            // The received time is multiplied by 1000 to convert to milliseconds
            long forecastDayUnixTime = data.getDt() * 1000L;
            String forecastDayAsString =
                    DateFormat.format("EEE", new Date(forecastDayUnixTime)).toString();
            dayName.setText(forecastDayAsString);
            dayHiTemp.setText(
                    NumberFormatting.doubleToStringNoDecimals(data.getTemp().getMax()) + "\u00b0");
            dayLoTemp.setText(
                    NumberFormatting.doubleToStringNoDecimals(data.getTemp().getMin()) + "\u00b0");
            dayPressure.setText(
                    NumberFormatting.doubleToStringOneDecimal(data.getPressure()) + "\u0025");
        }
    }
}