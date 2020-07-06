package com.greengrains;

import android.annotation.SuppressLint;
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
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class Order_Fragment extends Fragment {
    RecyclerView order_recycler;
    oderMainAdapter adapter;
    ArrayList<order_BaseClass> list;
    TextView no_history;
    SwipeRefreshLayout refresh_orders;
    Context context;
    String id;
    ProgressDialog progressDialog;
    globalOrderStore store;
    totalCost varna;
    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    context=getContext();
    store=new globalOrderStore("hfsf");
        varna=new totalCost();
        if(varna.total<=0)
        {
            new DashBoard().fab.setVisibility(View.INVISIBLE);
            new DashBoard().fab.setEnabled(false);
            new DashBoard().item_count.setVisibility(View.INVISIBLE);
            new DashBoard().item_count.setEnabled(false);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.order_fragment, container, false);
       refresh_orders=(SwipeRefreshLayout)view.findViewById(R.id.refresh_order);
       refresh_orders.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
           @Override
           public void onRefresh() {
               refresh_orders.setRefreshing(false);
       new getOrdersDynamic().execute();
           }
       });

       no_history=(TextView)view.findViewById(R.id.no_history);
        no_history.setVisibility(View.INVISIBLE);
        no_history.setEnabled(false);
       order_recycler=(RecyclerView)view.findViewById(R.id.order_recycler);
        new getOrdersDynamic().execute();
        return view;
    }
    private class getOrdersDynamic extends AsyncTask<String,String,String>
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
            String url="https://express.accountantlalaji.com/newapp/webapi/saipharma/appointment_history";
            SharedPreferences preferences=getActivity().getApplicationContext().
                    getSharedPreferences("login_details",getActivity().
                            getApplicationContext().MODE_PRIVATE);

            id=preferences.getString("auth_key","2");

            String data=new JsonParser().getHistory(url,id);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try{
                    list=new ArrayList<>();
                    JSONObject object = new JSONObject(s);
                    System.out.println(String.valueOf(object.get("data")));
                    JSONArray array = object.getJSONArray("data");
                    for(int i=0;i<array.length();i++)
                    {
                    JSONObject obj=array.getJSONObject(i);
                        String order_id=String.valueOf(obj.get("id"));
                        String order_num=String.valueOf(obj.get("order_no"));
                       System.out.println(order_id+"-------------------------------");
                        String amount=String.valueOf(obj.get("grand_total"));
                        String tax=String.valueOf(obj.get("tax"));
                        double rs_big=Double.parseDouble(amount)+Double.parseDouble(tax);
                        amount=String.valueOf(rs_big);
                        if(amount.length()>7) {
                            amount = amount.substring(0, 7);
                        }
                        String total_amount=String.valueOf(obj.get("grand_total"));
                        String mode=String.valueOf(obj.get("modeofpayment"));
                        String date=String.valueOf(obj.get("book_date"));
                        String time=String.valueOf(obj.get("book_time"));
                        String status=String.valueOf(obj.get("status"));
                     JSONArray arr_internal=obj.getJSONArray("product");
                     String medicines="",discount="",strip="";
                     ArrayList<order_view_flager> list_suc=new ArrayList<>();
                     for(int j=0;j<arr_internal.length();j++)
                     {
                         JSONObject obj_pro=arr_internal.getJSONObject(j);
                        String number=String.valueOf(obj_pro.get("product_qty"));
                        String name=String.valueOf(obj_pro.get("product_name"));
                        String base_price=String.valueOf(obj_pro.get("product_price"));
                        String price=String.valueOf(obj_pro.get("product_amount"));
                        String disco=String.valueOf(obj_pro.get("product_discount"));
                        String product_hsn_rate=String.valueOf(obj_pro.get("product_hsn_rate"));
                     list_suc.add(new order_view_flager(number,name,disco,base_price,price,product_hsn_rate));
                     }
  list.add(new order_BaseClass(order_id,order_num,discount,total_amount,mode,date,time,status,list_suc));
                    }
                    store.list2=list;
                    adapter=new oderMainAdapter(list,context);
                    order_recycler.setLayoutManager(new LinearLayoutManager(context));
                    order_recycler.setHasFixedSize(true);
                    order_recycler.setAdapter(adapter);

                } catch (Exception e) {
                    e.printStackTrace();
                    no_history.setVisibility(View.VISIBLE);
                    no_history.setEnabled(true);
                }
            }
            else
            {
                Toast.makeText(context,"Please Check your Internet Connection",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
