package com.greengrains;

import java.util.ArrayList;
import java.util.HashMap;

public class productGroup {
   static HashMap<String,String> group;
   static ArrayList<String> products;
    public productGroup()
    {

    }
    public productGroup(String str)
    {
        group=new HashMap<>();
        products=new ArrayList<>();
    }
}
