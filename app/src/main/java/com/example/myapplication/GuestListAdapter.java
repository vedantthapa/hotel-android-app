package com.example.myapplication;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.ViewHolder> {


    private LayoutInflater layoutInflater;
    private ItemClickListener clickListener;
    private Integer guestCount;
    ArrayList<GuestListData> guestListData;


    //Data gets passed in the constructor
    GuestListAdapter(Context context, Integer guestCount) {
        this.layoutInflater = LayoutInflater.from(context);
        this.guestCount = guestCount;
    }

    public ArrayList<GuestListData> getGuestList(){
        return guestListData;
    }

    @NonNull
    @Override
    public GuestListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.guest_list_layout, parent, false);
        if (guestListData == null) {
            guestListData = new ArrayList<>();
            int count = 0;
            while (count < guestCount) {
                GuestListData guests = new GuestListData();
                guestListData.add(guests);
                count = count + 1;
            }
        }

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull GuestListAdapter.ViewHolder holder, int position) {

        holder.name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                guestListData.get(position).setName(s.toString());

            }
        });

        holder.age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                guestListData.get(position).setAge(Integer.parseInt(s.toString()));
            }
        });

        holder.gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==0)
                    guestListData.get(position).setGender("Female");
                else
                    guestListData.get(position).setGender("Male");
            }
        });
    }

    @Override
    public int getItemCount() {
        if (guestCount != null) {
            return guestCount;
        } else {
            return 0;
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        EditText name, age;
        RadioGroup gender;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.guest_name_edit_text);
            age = itemView.findViewById(R.id.guest_age_edit_text);
            gender = itemView.findViewById(R.id.guest_radio_group);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (clickListener != null)
                clickListener.onClick(view, getAbsoluteAdapterPosition());
        }
    }
}