package com.greengrains;

import java.util.ArrayList;

public class order_BaseClass {
    String discount,total_amount,mode,order_id;
    String date,time,status,number;
    ArrayList<order_view_flager> list_sub;
    //String order_id;
    public order_BaseClass(String order_id, String number, String discount,
                           String total_amount, String mode, String date, String time, String status, ArrayList<order_view_flager> list_sub) {
        this.order_id=order_id;
        this.discount = discount;
        this.list_sub=list_sub;
        this.number=number;
        this.total_amount = total_amount;
        this.mode = mode;
        this.time=time;
        this.date=date;
        this.status=status;
    }

    public ArrayList<order_view_flager> getList_sub() {
        return list_sub;
    }

    public String getNumber() {
        return number;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public String getTime() {
        return time;
    }

    public String getOrder_id() {
        return order_id;
    }


    public String getDiscount() {
        return discount;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public String getMode() {
        return mode;
    }
}
