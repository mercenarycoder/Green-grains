package com.greengrains;

public class medicineBaseClass2 {
String image_url,medicine,description;
String id;
    public medicineBaseClass2(String id, String image_url, String medicine,
                             String description) {
        this.image_url = image_url;
        this.medicine = medicine;
        this.id=id;
        this.description = description;
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

    public String getId() {
        return id;
    }
}
