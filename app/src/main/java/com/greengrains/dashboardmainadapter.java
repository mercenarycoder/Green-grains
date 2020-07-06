package com.greengrains;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

public class dashboardmainadapter extends RecyclerView.Adapter<dashboardmainadapter.viewholder1>{
    ArrayList<medicineBaseClass> list;
    SharedPreferences quantity_pref;
    SharedPreferences.Editor editor;
    productCount dashBoard;
    Activity activity;
    Context context;
    totalCost varna;
    TextView item_count;
    public dashboardmainadapter(ArrayList<medicineBaseClass> list, Context context,Activity activity)
    {
        this.list=list;
        this.activity=activity;
        this.context=context;
        setProgress();
        varna=new totalCost();
        dashBoard=new productCount();
    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.recycler_medicine, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
     return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder1 holder, final int position) {
        quantity_pref=context.getApplicationContext().
                getSharedPreferences("quantity_details",context.getApplicationContext().MODE_PRIVATE);
        editor=quantity_pref.edit();
        final medicineBaseClass adapter=list.get(position);
        holder.medicine_price.setText(adapter.getNew_rate());
        String get_price=adapter.price_2;
        int price_1=Integer.parseInt(get_price);
        holder.medicine_price_base.setText("₹ "+String.valueOf(price_1));
        if(adapter.getOld_rate().length()>4) {
            holder.medicine_price_old.setText(adapter.getOld_rate());
        }
        else
        {
            holder.modify_1.setVisibility(View.INVISIBLE);
        }
        holder.medicine_name.setText(adapter.getMedicine());
        holder.medicine_quantity.setText(adapter.getQuantity());
        holder.medicine_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           Intent intent=new Intent(context,Medicine_Show_Activity.class);
           intent.putExtra("product_id",adapter.id);
           context.startActivity(intent);
            }
        });
        if(!adapter.getImage_url().equals("")) {
            Picasso.with(context).load(adapter.getImage_url())
                    .error(R.drawable.ic_groceries)
                    .into(holder.medicine_url);
        }
        holder.medicine_add.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "RestrictedApi"})
            @Override
            public void onClick(View v) {
          String quant=holder.medicine_quantity.getText().toString();
          int quant2=Integer.parseInt(quant);
          quant2++;
          dashBoard.calculate++;
          new DashBoard().fab.setVisibility(View.VISIBLE);
          new DashBoard().fab.setEnabled(true);
          DashBoard inst=DashBoard.instance();
          inst.updateCount();
                String get_price=adapter.price_2;
              int price_1=Integer.parseInt(get_price);

                varna.total+=price_1;
                price_1=price_1*quant2;
                String price_change=String.valueOf(price_1);
                holder.medicine_price.setText("₹"+price_change);

                dashBoard.map2.containsKey(adapter.id);
                {
                    dashBoard.map2.remove(adapter.id);
                    dashBoard.map3.remove(adapter.id);
                    dashBoard.map2.put(adapter.id,quant2);
                    dashBoard.map3.put(adapter.id,new cartBaseClass(adapter.getImage_url(),
                            adapter.getMedicine(),
                            adapter.id,String.valueOf(quant2),adapter.price_2,"₹"+price_change,adapter.getOld_rate()));
                }
          quant=String.valueOf(quant2);
          holder.medicine_quantity.setText(quant);
            }
        });
        holder.medicine_minus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "RestrictedApi"})
            @Override
            public void onClick(View v) {
                String quant=holder.medicine_quantity.getText().toString();
                String price_change;
                int quant2=Integer.parseInt(quant);
                if(quant2==0)
                {
                    Toast.makeText(context,"Quantity Cannot Be Negative",Toast.LENGTH_SHORT).show();
                    return;
                }
                quant2--;
                dashBoard.calculate--;
                if(dashBoard.calculate==0)
                {
                    new DashBoard().fab.setVisibility(View.INVISIBLE);
                    new DashBoard().fab.setEnabled(false);
                }
                DashBoard inst=DashBoard.instance();
                inst.updateCount();
                String get_price2 = adapter.price_2;
                int price_12 = Integer.parseInt(get_price2);
                varna.total-=price_12;
                if(quant2>0) {
                    String get_price = adapter.price_2;
                    int price_1 = Integer.parseInt(get_price);
                    //varna.total-=price_1;
                    price_1 = price_1 * quant2;
                    price_change = String.valueOf(price_1);
                }
                else
                {
                    price_change=adapter.price_2;
                }
                holder.medicine_price.setText("₹"+price_change);
                dashBoard.map2.containsKey(adapter.id);
                {
                    dashBoard.map2.remove(adapter.id);
                    dashBoard.map3.remove(adapter.id);
                    dashBoard.map2.put(adapter.id,quant2);
                    dashBoard.map3.put(adapter.id,new cartBaseClass(adapter.getImage_url(),adapter.getMedicine(),
                            adapter.id,String.valueOf(quant2),adapter.price_2,"₹"+price_change,adapter.getOld_rate()));
                }
                quant=String.valueOf(quant2);
                holder.medicine_quantity.setText(quant);
            }
        });
        }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewholder1 extends RecyclerView.ViewHolder
    {
        LinearLayout medicine_info;
        ImageView medicine_url;
        TextView medicine_name,medicine_price_base;
        TextView medicine_quantity,medicine_price,medicine_price_old;
        RelativeLayout modify_1;
        ImageButton medicine_add,medicine_minus;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            medicine_info=(LinearLayout)itemView.findViewById(R.id.medicine_info);
            medicine_url=(ImageView)itemView.findViewById(R.id.medicine_image);
            medicine_name=(TextView)itemView.findViewById(R.id.medicine_name);
            medicine_quantity=(TextView) itemView.findViewById(R.id.medicine_quantity);
            medicine_price=(TextView)itemView.findViewById(R.id.medicine_price);
            modify_1=(RelativeLayout)itemView.findViewById(R.id.modify_1);
            medicine_price_old=(TextView)itemView.findViewById(R.id.medicine_price_old);
            medicine_add=(ImageButton)itemView.findViewById(R.id.medicine_add);
            medicine_minus=(ImageButton)itemView.findViewById(R.id.medicine_minus);
            medicine_price_base=(TextView)itemView.findViewById(R.id.medicine_price_base);
        }
    }
    public void setProgress()
    {
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Data Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
    ProgressDialog progressDialog;

}
