package com.pos.countries;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.pos.countries.Model.Country;

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

        TextView tv_capital = findViewById(R.id.tv_details_capital);
        TextView tv_region = findViewById(R.id.tv_details_region);

        tv_capital.setText(country.getCapital());
        tv_region.setText(country.getRegion());
    }
}
