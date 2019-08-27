package com.pos.countries;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pos.countries.Adapter.CountriesAdapter;
import com.pos.countries.Model.Country;
import com.pos.countries.Util.NetworkUtil;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final String TAG = "MainActivity";
    ListView listCrountries;
    TextView txTextoExibido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txTextoExibido = findViewById(R.id.tv_texto_exibido);
        listCrountries = (ListView) findViewById(R.id.lv_lista_countries);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_paises:
                callWebServiceCountry();
                break;
            case R.id.asia:
                callWebServiceCountry("Asia");
                break;
            case R.id.africa:
                callWebServiceCountry("Africa");
                break;
            case R.id.oceania:
                callWebServiceCountry("Oceania");
                break;
            case R.id.europe:
                callWebServiceCountry("Europe");
                break;
            case R.id.americas:
                callWebServiceCountry("Americas");
                break;
            case R.id.menu_favorites:
                listAllFavoritesCountries();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void callWebServiceCountry(){
        Log.d(TAG, "method call web service");
        URL url = NetworkUtil.buildUrl("stf");
        CountrieAsyncTask task = new CountrieAsyncTask();
        task.execute(url);
    }
    public void callWebServiceCountry(String continente){
        Log.d(TAG, "method call web service");
        URL url = NetworkUtil.buildUrl(continente);
        CountrieAsyncTask task = new CountrieAsyncTask();
        task.execute(url);
    }

    public void listAllFavoritesCountries(){
        GetAllFavoritesAsyncTask task = new GetAllFavoritesAsyncTask(this);
        task.execute();
    }
    public ListView listarCountries(final List<Country> a){

        CountriesAdapter adapter= new CountriesAdapter(a, this);
        listCrountries.setAdapter(adapter);

         listCrountries.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Details.class);
                intent.putExtra("country", a.get(position));
                startActivity(intent);
            }
        });

        return listCrountries;
    }



    class GetAllFavoritesAsyncTask extends AsyncTask<Void, Void, List<Country>>{
        Context context;

        GetAllFavoritesAsyncTask(Context context){
            this.context = context;
        }

        @Override
        protected List<Country> doInBackground(Void... voids) {
            return CountryDataBase.getInstance(context).getDao().getAllCountry();
        }

        @Override
        protected void onPostExecute(List<Country> countries) {
            if (countries.size() == 0) {
                //clearList();
                //txTextoExibido.setText(R.string.without_favorite);
            } else {
                txTextoExibido.setText(null);
                listarCountries(countries);
            }

            super.onPostExecute(countries);
        }
    }

    class CountrieAsyncTask extends AsyncTask<URL, Void, List<Country>> {

        @Override
        protected List<Country> doInBackground(URL... urls) {

            URL url = urls[0];
            Log.d(TAG, "url utilizada: " + url.toString());
            String json = null;
            try {
                json = NetworkUtil.getResponseFromHttpUrl(url);
                Log.d(TAG, "async task retorno: " + json);
            } catch (IOException e) {
                e.printStackTrace();
            }

            TypeToken<List<Country>> token = new TypeToken<List<Country>>() {};
            List<Country> countries = new Gson().fromJson(json.toString(), token.getType());

            return countries;
        }

        @Override
        protected void onPreExecute() {
           //mostrarLoading();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Country> s) {
            //esconderLoading();
            if(s == null) {
                txTextoExibido.setText("Houve um erro");
            }else{
                listarCountries(s);
                //txTextoExibido.setText(s.toString());
            }
        }
    }
}
