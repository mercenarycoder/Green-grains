package com.greengrains;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class order_adapter extends RecyclerView.Adapter<order_adapter.viewholder1>{
    ArrayList<order_view_flager> list;
    Context context;
    public order_adapter(ArrayList<order_view_flager> list, Context context)
    {
        this.list=list;
        this.context=context;
    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.internal_items, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder1 holder, int position) {
        order_view_flager adapter=list.get(position);
        holder.quantity.setText(adapter.getNumber());
        holder.amount.setText(adapter.getPrice());
        holder.product_name.setText(adapter.getName_product());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class viewholder1 extends RecyclerView.ViewHolder
    {
        TextView quantity,product_name,amount;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            quantity=(TextView)itemView.findViewById(R.id.medi_count);
            product_name=(TextView)itemView.findViewById(R.id.medi_name);
            amount=(TextView)itemView.findViewById(R.id.medi_price);
        }
    }
}

