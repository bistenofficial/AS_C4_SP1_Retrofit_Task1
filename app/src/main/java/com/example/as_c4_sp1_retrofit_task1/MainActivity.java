package com.example.as_c4_sp1_retrofit_task1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView countryName;
    TextView textAPI;
    ArrayList<Country> countries = new ArrayList<Country>();
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Context context;
        countryName = findViewById(R.id.textView_countryName);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://restcountries.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        JsonApi json = retrofit.create(JsonApi.class);
        Call<List<Country>> call = json.getPosts();
        context = this;
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if(!response.isSuccessful())
                {
                    countryName.setText(response.code());
                    return;
                }
                List<Country> posts = response.body();
                countries.addAll(posts);
                RecyclerView rvCountries = findViewById(R.id.RecyclerViewMain);
                // Create adapter passing in the sample user data
                CountryAdapter adapter = new CountryAdapter(countries, context);
                // Attach the adapter to the recyclerview to populate items
                rvCountries.setAdapter(adapter);
                // Set layout manager to position the items
                rvCountries.setLayoutManager(new LinearLayoutManager(context));
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                countryName.setText(t.getMessage());
            }
        });
    }

}