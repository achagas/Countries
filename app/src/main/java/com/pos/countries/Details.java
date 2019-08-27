package com.pos.countries;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.pos.countries.Model.Country;

import java.util.List;

import static com.pos.countries.R.drawable.ic_action_favorite_on;
import static com.pos.countries.R.drawable.ic_action_favorite_off;

public class Details extends AppCompatActivity {
    Country country;
    final String TAG = "Details";
    int value = 0;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        context = this.getApplicationContext();
        Intent intent = getIntent();
        country = intent.getParcelableExtra("country");

        TextView tv_pais = findViewById(R.id.tv_details_pais);
        TextView tv_capital = findViewById(R.id.tv_details_capital);
        TextView tv_region = findViewById(R.id.tv_details_region);
        TextView tv_population = findViewById(R.id.tv_details_population);
        TextView tv_latitude = findViewById(R.id.tv_details_latitude);
        //TextView tv_longitude = findViewById(R.id.tv_details_longitude);
        TextView tv_currency = findViewById(R.id.tv_details_currency);


        tv_pais.setText(country.getName());
        tv_capital.setText(country.getCapital());
        tv_region.setText(country.getRegion());
        tv_population.setText(country.getPopulation().toString());
        //tv_latitude.setText(country.getLatlng().get(position));
        //tv_currency.setText(country.getCurrencies().toString());

        final FloatingActionButton favoriteButton = findViewById(R.id.favButton);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (value == 0){
                    value = 1;
                    favoriteButton.setImageResource(ic_action_favorite_on);
                    new setFavorite(context).execute();

                    Toast.makeText(Details.this, "Favoritado!", Toast.LENGTH_SHORT).show();
                }else {
                    value = 0;
                    favoriteButton.setImageResource(ic_action_favorite_off);
                    Toast.makeText(Details.this, "Desfavoritado!", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


    class setFavorite extends AsyncTask<Void, Void, Void> {

        setFavorite(Context context){
            context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            country.setName(country.getName());
            country.setCapital(country.getCapital());
            country.setRegion(country.getRegion());
            country.setPopulation(country.getPopulation());

            CountryDataBase.getInstance(context).getDao().insert(country);
            Log.d(TAG, "inseriu (teoricamente)");

            List<Country> countries = CountryDataBase.getInstance(context).getDao().getAllCountry();

            Log.d(TAG, "Mostrar Lista");
            for(Country c : countries){
                Log.d(TAG, "-->" + c.toString());
            }
            return null;
        }
    }
}
