package com.example.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class GuestDetailsFragment extends Fragment {

    View view;
    String guestName;
    String guestAge;
    String checkinDate;
    String checkoutDate;
    String guestCount;
    Button guestSubmitButton;
    RadioButton guestGenderRadioButton;

    TextInputLayout guestNameLayout, guestAgeLayout;
    TextInputEditText guestNameEditText, guestAgeEditText;

    List<GuestListData> userListResponseData;
    GuestListAdapter guestListAdapter;

    ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.guest_details_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView hotelConfirmedTextView = view.findViewById(R.id.confirmed_hotel_text_view);
        Button guestSubmitButton = view.findViewById(R.id.guest_submit_button);
        progressBar = view.findViewById(R.id.guest_list_progress_bar);


        String hotelName = getArguments().getString("hotel name");
        String hotelCity = getArguments().getString("hotel city");
        String hotelEmail = getArguments().getString("hotel email");
        guestCount = getArguments().getString("guest count");
        checkinDate = getArguments().getString("checkin date");
        checkoutDate = getArguments().getString("checkout date");

        hotelConfirmedTextView.setText("Please enter guest information");

        guestSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ArrayList<GuestListData> guestsData = guestListAdapter.getGuestList();
                    ReservationData reservationData = new ReservationData(hotelName, checkinDate, checkoutDate, guestsData);
                    progressBar.setVisibility(View.VISIBLE);

                    Api.getClient().postGuestsLists(reservationData, new Callback<String>() {
                        @Override
                        public void success(String confirmation, Response response) {
                            Log.e("Confirmation", confirmation);

                            Bundle bundle = new Bundle();
                            bundle.putString("Confirmation", confirmation);

                            //set fragment class arguments
                            ConfirmationFragment confirmationFragment = new ConfirmationFragment();
                            confirmationFragment.setArguments(bundle);

                            FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.main_layout, confirmationFragment);
                            fragmentTransaction.remove(GuestDetailsFragment.this);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.e("Confirmation failure",error.toString());
                        }
                    });


            }
        });


        setupRecyclerView();
    }


    private void setupRecyclerView() {
        progressBar.setVisibility(View.GONE);
        RecyclerView recyclerView = view.findViewById(R.id.hotel_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        guestListAdapter = new GuestListAdapter(getActivity(), Integer.parseInt(guestCount));
        recyclerView.setAdapter(guestListAdapter);
    }

}