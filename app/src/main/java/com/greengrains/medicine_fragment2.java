package com.greengrains;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class medicine_fragment2 extends Fragment
{
    ArrayList<medicineBaseClass>  list;
    RecyclerView recycler_medicine;
    SwipeRefreshLayout medicine_refresh;
    dashboardmainadapter adapter;
    TextView no_medicine;
    TextView highlight;
    Context context;
    ProgressDialog progressDialog;
    productCount count_pro;
    DashBoard dashBoard;
    productGroup group;
    Activity activity;
    getCategoryProducts products;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
        activity=getActivity();
        dashBoard=new DashBoard();
        count_pro=new productCount();
     group=new productGroup();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.medicines_show, container, false);
        recycler_medicine=(RecyclerView)view.findViewById(R.id.recycler_medicine);
        no_medicine=(TextView)view.findViewById(R.id.no_medicine);
        medicine_refresh=(SwipeRefreshLayout)view.findViewById(R.id.medicine_refresh);
        medicine_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                medicine_refresh.setRefreshing(false);
                new getCategoryProducts().execute();
            }
        });
        SharedPreferences preferences=getActivity().getApplicationContext().
                getSharedPreferences("group_info",getActivity().
                        getApplicationContext().MODE_PRIVATE);

        String service_name=preferences.getString("group_name","2");
        TextView heading_heading=(TextView)getActivity().findViewById(R.id.heading_heading);
        heading_heading.setText(service_name);
        new getCategoryProducts().execute();
        return view;
    }
    boolean check_it=false,checker=false;
    @Override
    public void onPause() {
        super.onPause();
        check_it=true;
        if(checker) {
            SharedPreferences preferences = context.getApplicationContext().
                    getSharedPreferences("group_info", context.getApplicationContext().MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            String group_all = preferences.getString("group_name", "2");
            if (group.group.containsKey(group_all)) {
           editor.putString("bool","false");
           editor.apply();
           editor.commit();
            }
        }
    }
    @Override
    public void onStop() {
        super.onStop();
    //    Toast.makeText(context,"I am called here",Toast.LENGTH_SHORT).show();
        SharedPreferences preferences = context.getApplicationContext().
                getSharedPreferences("group_info", context.getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        String group_all = preferences.getString("group_name", "2");
        if (group.group.containsKey(group_all)) {
            editor.putString("bool","false");
            editor.apply();
            editor.commit();
        }
    }
    @Override
    public void onResume() {
        if(check_it)
        {
            new getCategoryProducts().execute();
        }
        super.onResume();
//        new getCategoryProducts().execute();
    }

    private class getCategoryProducts extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Data Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/webapi/greenandgrains/listofallotproductgroups";
            SharedPreferences preferences=getActivity().getApplicationContext().
                    getSharedPreferences("group_info",getActivity().
                            getApplicationContext().MODE_PRIVATE);

            String id=preferences.getString("group_id","2");
            System.out.println("ID of the Group:"+id);
            String data=new JsonParser().getGroupProducts(url,id);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try {
                    dashBoard.map=new HashMap<>();
                    list=new ArrayList<>();
                    JSONObject object = new JSONObject(s);
                    JSONArray arr=object.getJSONArray("data");
                    if(arr.length()==0)
                    {
                        no_medicine.setVisibility(View.VISIBLE);
                        no_medicine.setEnabled(true);
                        return;
                    }
                    for(int i=0;i<arr.length();i++)
                    {
                        JSONObject obj=arr.getJSONObject(i);
                        String url=String.valueOf(obj.get("product_image"));
                        String name=String.valueOf(obj.get("product_name"));
                        String price=String.valueOf(obj.get("product_price"));
                        String price_old=String.valueOf(obj.get("product_price_old"));
                        String description=String.valueOf(obj.get("product_des"));
                        String id=String.valueOf(obj.get("product_id"));
                        String point=String.valueOf(obj.get("product_point"));
                        String allot=String.valueOf(obj.get("allot_product_name"));
                        if(productCount.map2.containsKey(id))
                        {
                            double pri=Double.parseDouble(price);
                            if(productCount.map2.get(id)>0) {
                                pri = (Double) pri * (productCount.map2.get(id));
                                price = String.valueOf(pri);
                                if (price.length() > 5) {
                                    price = price.substring(0, 5);
                                }
                            }
                            list.add(new medicineBaseClass(id,url,name,description,allot,
                                    price,"₹"+price
                                    ,"₹"+price_old,String.valueOf(productCount.map2.get(id))));
                        }
                        else {
                            productCount.map2.put(id, 0);
                            productCount.map3.put(id,new cartBaseClass("a","a","a","a","a","a","a"));
                            list.add(new medicineBaseClass(id,url,name,description,allot,
                                    price,"₹"+price
                                    ,"₹"+price_old,"0"));
                        }
                    }
                    Collections.sort(list, new Comparator<medicineBaseClass>() {
                        @Override
                        public int compare(medicineBaseClass o1, medicineBaseClass o2) {
                            return o1.medicine.compareTo(o2.medicine);
                        }
                    });
                    no_medicine.setVisibility(View.INVISIBLE);
                    no_medicine.setEnabled(false);
                    adapter=new dashboardmainadapter(list,context,activity);
                    recycler_medicine.setLayoutManager(new LinearLayoutManager(context));
                    recycler_medicine.setHasFixedSize(true);
                    recycler_medicine.setAdapter(adapter);
                 checker=true;
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context,"Some Networking Error",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(context,"Please Check Your Internet Connection",Toast.LENGTH_SHORT).show();
            }
        }
    }


}
