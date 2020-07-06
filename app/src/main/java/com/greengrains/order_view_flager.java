package com.greengrains;

public class order_view_flager
{
    String number;
    String name_product;
    String discount;
    String base_price,price;
    String product_point;

    public order_view_flager(String number, String name_product, String discount, String base_price,
                             String price, String product_point) {
        this.number = number;
        this.name_product = name_product;
        this.discount = discount;
        this.base_price = base_price;
        this.price = price;
        this.product_point = product_point;
    }

    public String getNumber() {
        return number;
    }

    public String getName_product() {
        return name_product;
    }
    public String getDiscount() {
        return discount;
    }

    public String getBase_price() {
        return base_price;
    }

    public String getPrice() {
        return price;
    }

    public String getProduct_point() {
        return product_point;
    }
}
