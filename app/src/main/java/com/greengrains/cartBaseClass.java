package com.greengrains;

public class cartBaseClass {
    String img,medicine,id,qty,price,price_old;
    String base_price;
    public cartBaseClass(String img, String medicine, String id, String qty,String base_price,
                         String price, String price_old) {
        this.img = img;
        this.medicine = medicine;
        this.id = id;
        this.base_price=base_price;
        this.qty = qty;
        this.price = price;
        this.price_old = price_old;
    }

    public String getBase_price() {
        return base_price;
    }

    public String getImg() {
        return img;
    }

    public String getMedicine() {
        return medicine;
    }

    public String getId() {
        return id;
    }

    public String getQty() {
        return qty;
    }

    public String getPrice() {
        return price;
    }

    public String getPrice_old() {
        return price_old;
    }
}
