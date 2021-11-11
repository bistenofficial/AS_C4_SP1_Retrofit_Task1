package com.example.as_c4_sp1_retrofit_task1;

import android.app.Application;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView,populationTextView;
        public ImageView imageView;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            super(itemView);
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            nameTextView = itemView.findViewById(R.id.textView_countryName);
            populationTextView = itemView.findViewById(R.id.textView_population);
            imageView = itemView.findViewById(R.id.imageView_flag);
        }
    }
    public Context context;
    private List<Country> mCountries;
    public CountryAdapter(List<Country> countries, Context context) {
        this.context = context;
        mCountries = countries;
    }
    // inflating a layout from XML and returning the holder

    public CountryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.recyclerview_item_layout, parent, false);
        // Return a new holder instance
        return new ViewHolder(contactView);
    }

    // В этом методе мы привязываем данные к ячейке
    @Override
    public void onBindViewHolder(CountryAdapter.ViewHolder holder, int position) {
        // Get the data model based on position
        Country country = mCountries.get(position);
        // Set item views based on your views and data model
        TextView textViewName = holder.nameTextView;
        textViewName.setText(country.getNameCountry());
        TextView textViewPopulation = holder.populationTextView;
        textViewPopulation.setText(country.getPopulationCountry());
        ImageView viewImage = holder.imageView;
        Picasso.with(this.context)
                .load(country.getFlags().get("png"))
                .error(R.drawable.ic_launcher_background)
                .placeholder(R.drawable.ic_launcher_background)
                .resize(250, 250)
                .into(viewImage);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mCountries.size();
    }
}