package com.pos.countries.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pos.countries.Model.Country;
import com.pos.countries.R;

import java.util.List;

public class CountriesAdapter extends BaseAdapter {

    List<Country> countryList;
    Activity activity;

    public CountriesAdapter(List<Country> countryList, Activity activity) {
        this.countryList = countryList;
        this.activity = activity;
    }

    public int getCount(){
        return countryList.size();
    }

    public Country getItem(int position){
        return countryList.get(position);
    }

    public long getItemId(int position){
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View view = activity.getLayoutInflater().inflate(R.layout.list_countries, parent, false);

        Country country = getItem(position);
        TextView nameCountry = (TextView) view.findViewById(R.id.tv_name_country);

        nameCountry.setText(country.getName());

        return view;
    }
}
