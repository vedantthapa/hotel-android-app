package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HotelListFragment extends Fragment {

    View view;
    TextView fragmentTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.hotel_list_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        String checkInDate = bundle.getString("check in date");
        String checkOutDate = bundle.getString("check out date");
        String numberOfGuests = bundle.getString("number of guests");
        String guestName = bundle.getString("name of guest");


        fragmentTextView = view.findViewById(R.id.hotel_fragment_text_view);
        fragmentTextView.setText(checkInDate + checkOutDate + numberOfGuests + guestName);
    }
}
