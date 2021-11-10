package com.example.as_c4_sp1_retrofit_task1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView textAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        textAPI = findViewById(R.id.data);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://restcountries.com/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonApi json = retrofit.create(JsonApi.class);
        Call<List<Country>> call = json.getPosts();
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if(!response.isSuccessful())
                {
                    textAPI.setText(response.code());
                    return;
                }
                List<Country> posts = response.body();
                for(Country api:posts)
                {
                    String content = "Страна";
                    content+=api.getNameCountry()+" ";
                    content+=" Население ";
                    content+=api.getPopulationCountry()+"\n";
                    textAPI.append(content);
                }

            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                textAPI.setText(t.getMessage());
            }
        });
    }

}