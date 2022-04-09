package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HotelSearchFragment extends Fragment {

    View view;
    ConstraintLayout mainLayout;
    TextView titleTextView;
    EditText guestsCountEditText, nameEditText;
    TextInputLayout guestCountLayout, guestNameLayout;
    Button searchButton;
    DatePicker checkInDatePicker, checkOutDatePicker;
    String checkInDate, checkOutDate, numberOfGuests, guestName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.hotel_search_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mainLayout = view.findViewById(R.id.main_layout);
        titleTextView = view.findViewById(R.id.title_text_view);

        guestNameLayout = view.findViewById(R.id.name_input_layout);
        guestCountLayout = view.findViewById(R.id.guest_count_input_layout);

        guestsCountEditText = view.findViewById(R.id.guests_count_edit_text);
        nameEditText = view.findViewById(R.id.name_edit_text);
        searchButton = view.findViewById(R.id.search_button);

        checkInDatePicker = view.findViewById(R.id.checkin_date_picker);
        checkOutDatePicker = view.findViewById(R.id.checkout_date_picker);

        titleTextView.setText(R.string.welcome_text);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkInDate = getDateFromCalendar(checkInDatePicker);
                checkOutDate = getDateFromCalendar(checkOutDatePicker);
                numberOfGuests = guestsCountEditText.getText().toString();
                guestName = nameEditText.getText().toString();

                // Validations
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date checkInFormatted = Calendar.getInstance().getTime();
                Date checkOutFormatted = Calendar.getInstance().getTime();
                try {
                    checkInFormatted = simpleDateFormat.parse(checkInDate);
                    checkOutFormatted = simpleDateFormat.parse(checkOutDate);
                } catch (Exception e) {
                    e.printStackTrace();

                }
                if (checkOutFormatted.before(checkInFormatted)){
                    Toast.makeText(getActivity(), "CheckOut must be greater than CheckIn", Toast.LENGTH_LONG).show();
                    return;
                }

                boolean isValid = true;
                if (guestName.isEmpty()){
                    guestNameLayout.setError("This field is mandatory");
                    isValid = false;
                } else {
                    guestNameLayout.setErrorEnabled(false);
                }

                if (numberOfGuests.isEmpty()){
                    guestCountLayout.setError("This field is mandatory");
                    isValid = false;
                } else {
                    guestCountLayout.setErrorEnabled(false);
                }

                if (Integer.parseInt(numberOfGuests) > 7){
                    guestCountLayout.setError("Number of Guests cannot be more than 7");
                    isValid = false;
                } else {
                    guestCountLayout.setErrorEnabled(false);
                }

                if (isValid){
                    Bundle bundle = new Bundle();
                    bundle.putString("check in date", checkInDate);
                    bundle.putString("check out date", checkOutDate);
                    bundle.putString("number of guests", numberOfGuests);
                    bundle.putString("name of guest", guestName);

                    HotelListFragment hotelListFragment = new HotelListFragment();
                    hotelListFragment.setArguments(bundle);

                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.main_layout, hotelListFragment);
                    fragmentTransaction.remove(HotelSearchFragment.this);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }


            }
        });

    }


    private String getDateFromCalendar(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = simpleDateFormat.format(calendar.getTime());

        return formattedDate;
    }
}
