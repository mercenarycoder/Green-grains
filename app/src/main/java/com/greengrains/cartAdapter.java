package com.greengrains;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class cartAdapter  extends RecyclerView.Adapter<cartAdapter.viewholder1>{
    ArrayList<cartBaseClass> list;
    SharedPreferences quantity_pref;
    SharedPreferences.Editor editor;
    productCount dashBoard;
    totalCost varna;

    Context context;
    public cartAdapter(ArrayList<cartBaseClass> list, Context context)
    {
        this.list=list;
        this.context=context;
        setProgress();
        varna=new totalCost();
        dashBoard=new productCount();

    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.cart_item, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder1 holder, final int position) {
        final cartBaseClass adapter=list.get(position);
        holder.medicine_cart.setText(adapter.getMedicine());
        holder.price_cart.setText(adapter.getPrice());
        //Toast.makeText(context,adapter.getId(),Toast.LENGTH_SHORT).show();
        holder.change_price.setText("₹ "+adapter.getBase_price());
        if(adapter.getPrice_old().length()<=3)
        {
         holder.price_old_cart.setVisibility(View.INVISIBLE);
         holder.cart_line.setVisibility(View.INVISIBLE);
        }
        else {
            holder.price_old_cart.setText(adapter.getPrice_old());
        }
        holder.medicine_quantity_cart.setText(adapter.getQty());
        if(!adapter.getImg().equals(""))
        {
            Picasso.with(context).load(adapter.getImg())
                    .placeholder(R.drawable.ic_groceries)
                    .into(holder.medicine_image_cart);
        }
        holder.medicine_add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quant=holder.medicine_quantity_cart.getText().toString();
                int quant2=Integer.parseInt(quant);
                quant2++;
                dashBoard.calculate++;
                String get_price=adapter.getBase_price();
                int price_1=Integer.parseInt(get_price);
                varna.total+=price_1;
                //new cart_fragment().total_cost_grand.setText("₹"+String.valueOf(varna.getTotal()));
                price_1=price_1*quant2;
                String price_change=String.valueOf(price_1);
                holder.price_cart.setText("₹"+price_change);
                dashBoard.map2.containsKey(adapter.getId());
                {
                    dashBoard.map2.remove(adapter.getId());
                    dashBoard.map3.remove(adapter.getId());
                    dashBoard.map2.put(adapter.getId(),quant2);
                    dashBoard.map3.put(adapter.id,new cartBaseClass(adapter.getImg(),adapter.getMedicine(),adapter.getId(),
                            String.valueOf(quant2),adapter.getBase_price(),price_change,adapter.getPrice_old()));
                }
                adapter.qty=String.valueOf(quant2);
                adapter.base_price=get_price;
                adapter.price=price_change;
                quant=String.valueOf(quant2);
                holder.medicine_quantity_cart.setText(quant);
            }
        });
        holder.remove_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quant2=Integer.parseInt(holder.medicine_quantity_cart.getText().toString());;
                String get_price=adapter.getBase_price();
                int price_1=Integer.parseInt(get_price);
                if(quant2>=0) {
                    price_1 = price_1 * quant2;
                }
                varna.total-=(price_1);
                String price_change=String.valueOf(price_1);
                holder.price_cart.setText("₹"+price_change);
                dashBoard.map2.containsKey(adapter.id);
                {
                    dashBoard.map2.remove(adapter.id);
                    dashBoard.map3.remove(adapter.id);
                    //dashBoard.map2.put(adapter.id,quant2);
                   // dashBoard.map3.put(adapter.id,new cartBaseClass(adapter.getImg(),adapter.getMedicine(),adapter.getId(),
                     //       String.valueOf(quant2),adapter.getBase_price(),price_change,adapter.getPrice_old()));
                }
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,list.size());
            }
        });
        holder.medicine_minus_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quant=holder.medicine_quantity_cart.getText().toString();
                int quant2=Integer.parseInt(quant);
                if(quant2==0)
                {
                    Toast.makeText(context,"Quantity Cannot Be Negative",Toast.LENGTH_SHORT).show();
                    return;
                }
                dashBoard.calculate--;
                quant2--;
                String get_price=adapter.getBase_price();
                int price_1=Integer.parseInt(get_price);
                varna.total-=price_1;
               if(quant2>0) {
                   price_1 = price_1 * quant2;
               }
                String price_change=String.valueOf(price_1);
                holder.price_cart.setText("₹"+price_change);
                dashBoard.map2.containsKey(adapter.id);
                {
                    dashBoard.map2.remove(adapter.id);
                    dashBoard.map3.remove(adapter.id);
                    dashBoard.map2.put(adapter.id,quant2);
                    dashBoard.map3.put(adapter.id,new cartBaseClass(adapter.getImg(),adapter.getMedicine(),adapter.getId(),
                            String.valueOf(quant2),adapter.getBase_price(),price_change,adapter.getPrice_old()));
                }
                adapter.qty=String.valueOf(quant2);
                adapter.base_price=get_price;
                adapter.price=price_change;
                quant=String.valueOf(quant2);
                holder.medicine_quantity_cart.setText(quant);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewholder1 extends RecyclerView.ViewHolder
    {
        TextView medicine_cart,qty_cart,price_cart,price_old_cart,medicine_quantity_cart,change_price;
        ImageView medicine_image_cart;
        View cart_line;
        ImageButton medicine_add_cart,medicine_minus_cart,remove_cart;

        TextView total_cost_grand,total_cost;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            medicine_cart=(TextView)itemView.findViewById(R.id.medicine_cart);
            cart_line=(View)itemView.findViewById(R.id.cart_line);
            change_price=(TextView)itemView.findViewById(R.id.change_price);
            price_cart=(TextView)itemView.findViewById(R.id.rate_cart);
            price_old_cart=(TextView)itemView.findViewById(R.id.rate_old_cart);
            remove_cart=(ImageButton)itemView.findViewById(R.id.remove_cart);
           medicine_quantity_cart=(TextView)itemView.findViewById(R.id.medicine_quantity_cart);
           medicine_add_cart=(ImageButton)itemView.findViewById(R.id.medicine_add_cart);
           medicine_minus_cart=(ImageButton)itemView.findViewById(R.id.medicine_minus_cart);
            medicine_image_cart=(ImageView)itemView.findViewById(R.id.medicine_image_cart);


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
