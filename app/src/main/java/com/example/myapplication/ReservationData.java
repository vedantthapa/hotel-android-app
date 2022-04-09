package com.example.myapplication;

import java.util.ArrayList;

public class ReservationData {

    String hotel, check_in, check_out;
    ArrayList<GuestListData> guest;

    public ReservationData(String hotel, String check_in, String check_out, ArrayList<GuestListData> guest) {
        this.hotel = hotel;
        this.check_in = check_in;
        this.check_out = check_out;
        this.guest = guest;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public String getCheck_in() {
        return check_in;
    }

    public void setCheck_in(String check_in) {
        this.check_in = check_in;
    }

    public String getCheck_out() {
        return check_out;
    }

    public void setCheck_out(String check_out) {
        this.check_out = check_out;
    }

    public ArrayList<GuestListData> getGuest() {
        return guest;
    }

    public void setGuest(ArrayList<GuestListData> guest) {
        this.guest = guest;
    }
}
