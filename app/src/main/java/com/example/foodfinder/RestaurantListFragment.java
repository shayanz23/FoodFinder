package com.example.foodfinder;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class RestaurantListFragment extends ListFragment {

    private RecyclerView recyclerView;
    private String[] restaurantNames;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> restaurants;


    public RestaurantListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        restaurantNames = getResources().getStringArray(R.array.restaurants);
        if (getArguments() != null) {
            Bundle bundle = getArguments();
            restaurants = getArguments().getStringArrayList("restaurants");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        if (restaurants != null && !restaurants.isEmpty()) {
            adapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_list_item_1, restaurants);
        } else {
            adapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_list_item_1, restaurantNames);
        }

        setListAdapter(adapter);
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //Toast.makeText(getActivity().getBaseContext(), restaurantNames[position] + " position " + position + " id " + id, Toast.LENGTH_SHORT).show();
        RestaurantFragment restaurantFragment = new RestaurantFragment();
        Bundle bundle = new Bundle();
        bundle.putString("name", restaurantNames[position]);
        restaurantFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .addToBackStack(null)
                .replace(R.id.restaurantListFragment, restaurantFragment)
                .commit();
    }


}