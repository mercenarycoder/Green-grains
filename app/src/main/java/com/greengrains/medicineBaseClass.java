package com.greengrains;

public class medicineBaseClass {
String image_url,medicine,description,strip,new_rate,old_rate,quantity;
String id,price_2;

    public medicineBaseClass(String id, String image_url, String medicine,
                             String description, String strip, String price_2, String new_rate,
                             String old_rate, String quantity) {
        this.image_url = image_url;
        this.medicine = medicine;
        this.id=id;
        this.price_2=price_2;
        this.description = description;
        this.strip = strip;
        this.new_rate = new_rate;
        this.old_rate = old_rate;
        this.quantity = quantity;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getMedicine() {
        return medicine;
    }

    public String getDescription() {
        return description;
    }

    public String getStrip() {
        return strip;
    }

    public String getNew_rate() {
        return new_rate;
    }

    public String getOld_rate() {
        return old_rate;
    }

    public String getQuantity() {
        return quantity;
    }
}
