package com.example.as_c4_sp1_retrofit_task1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityStatistic extends AppCompatActivity {
    ArrayList<Country> countries = new ArrayList<>();
    TextView text;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_statistic);
        radioGroup = (RadioGroup) findViewById(R.id.radio);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                switch (checkedId) //При изменении выбора
                {
                    case R.id.diagram_first:
                        makeDiagram(true);//Вызываем функцию при условии что список нужно отсортировать
                        break;
                    case R.id.diagram_second:
                        makeDiagram(false);
                        break;
                }
            }
        });
    }

    private void makeDiagram(boolean isSorted)
    {
        getSupportActionBar().hide();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://restcountries.com/v2/").addConverterFactory(GsonConverterFactory.create()).build();

        JsonApi json = retrofit.create(JsonApi.class);
        Call<List<Country>> call = json.getPosts();

        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (!response.isSuccessful())
                {
                    return;
                }

                List<Country> responseCountry = response.body();
                countries = new ArrayList<Country>(responseCountry);
                Collections.shuffle(countries);
                if (isSorted)//Если остортирован
                {
                    Collections.sort(countries, new Comparator<Country>()
                    {
                        public int compare(Country o1, Country o2)
                        {
                            return (int) (Long.parseLong(o2.getPopulationCountry()) - Long.parseLong(o1.getPopulationCountry()));
                        }
                    });
                }
                setDiagram(countries);
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                return;
            }
        });
    }

    private void setDiagram(List<Country> countries)
    {
        text = (TextView) findViewById(R.id.text);
        text.setText("");

        ArrayList<BarEntry> entries = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();

        for (int i = 0; i < 5; i++) {
            Country current = countries.get(i);
            String content = (i + 1) + " - ";
            content += current.getNameCountry() + "\n";
            text.append(content);

            entries.add(new BarEntry(Long.parseLong(current.getPopulationCountry()), i));
            labels.add(current.getNameCountry());
        }

        BarDataSet dataSet = new BarDataSet(entries, "");
        BarChart chart = (BarChart) findViewById(R.id.barChart);
        dataSet.setColor(Color.YELLOW);
        BarData data = new BarData(labels, dataSet);
        chart.setData(data);

    }

    public void backToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}