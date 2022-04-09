package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import retrofit.Callback;

import java.util.List;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HotelListFragment extends Fragment implements ItemClickListener{

    View view;
    TextView fragmentGreetTextView, fragmentTextView;
    ProgressBar progressBar;
    List<HotelListData> userListResponseData;

    String numberOfGuests, checkin_Date, checkout_Date;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.hotel_list_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        checkin_Date = bundle.getString("check in date");
        checkout_Date = bundle.getString("check out date");
        numberOfGuests = bundle.getString("number of guests");

        String guestName = bundle.getString("name of guest");

        fragmentGreetTextView = view.findViewById(R.id.hotel_fragment_greet_text_view);
        fragmentGreetTextView.setText("Hi " + guestName + " 👀");

        fragmentTextView = view.findViewById(R.id.hotel_fragment_text_view);
        fragmentTextView.setText("Here are some top picks for you");

        progressBar = view.findViewById(R.id.progress_bar);

        getHotelsListsData();

    }

    private void getHotelsListsData() {
        progressBar.setVisibility(View.VISIBLE);
        Api.getClient().getHotelsLists(checkin_Date, checkout_Date, new Callback<List<HotelListData>>() {

            @Override
            public void success(List<HotelListData> userListResponses, Response response) {
                // In this method we will get the response from API
                userListResponseData = userListResponses;

                // Set up the RecyclerView
                setupRecyclerView();
            }

            @Override
            public void failure(RetrofitError error) {
                // if error occurs in network transaction then we can get the error in this method.
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();

            }
        });
    }
    private void setupRecyclerView() {
        progressBar.setVisibility(View.GONE);
        RecyclerView recyclerView = view.findViewById(R.id.hotel_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        HotelListAdapter hotelListAdapter = new HotelListAdapter(getActivity(), userListResponseData);
        recyclerView.setAdapter(hotelListAdapter);

        //Bind the click listener
        hotelListAdapter.setClickListener(this::onClick);

    }
    @Override
    public void onClick(View view, int position) {
        HotelListData hotelListData = userListResponseData.get(position);

        String hotelName = hotelListData.getName();
        String hotelCity = hotelListData.getCity();
        String hotelEmail = hotelListData.getEmail();

        Bundle bundle = new Bundle();
        bundle.putString("hotel name", hotelName);
        bundle.putString("hotel city", hotelCity);
        bundle.putString("hotel email", hotelEmail);
        bundle.putString("guest count", numberOfGuests);
        bundle.putString("checkin date", checkin_Date);
        bundle.putString("checkout date", checkout_Date);

        GuestDetailsFragment guestDetailsFragment = new GuestDetailsFragment();
        guestDetailsFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.remove(HotelListFragment.this);
        fragmentTransaction.replace(R.id.main_layout, guestDetailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();

    }


}