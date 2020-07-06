package com.greengrains;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class oderMainAdapter extends RecyclerView.Adapter<oderMainAdapter.viewholder1>{
    ArrayList<order_BaseClass> list;
    Context context;
    SharedPreferences medi_pref;
    SharedPreferences.Editor editor;
    public oderMainAdapter(ArrayList<order_BaseClass> list, Context context)
    {
        this.list=list;
        this.context=context;
    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.order_recycler, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
        return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder1 holder, final int position) {
        final order_BaseClass adapter=list.get(position);
        medi_pref=context.getApplicationContext().
                getSharedPreferences("service_id",context.getApplicationContext().MODE_PRIVATE);
        editor=medi_pref.edit();
       holder.amount_final.setText("₹ "+adapter.getTotal_amount());
       holder.order_date.setText(adapter.getDate());
       holder.status_report.setText(adapter.getStatus());
       holder.order_id.setText("#"+adapter.getNumber());
       holder.muje_dbao.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(context,Show_order.class);
               intent.putExtra("number",String.valueOf(position));
               context.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewholder1 extends RecyclerView.ViewHolder
    {
     TextView amount_final;
     TextView order_date,status_report,order_id;
     LinearLayout muje_dbao;
        public viewholder1(@NonNull View itemView) {
            super(itemView);
            amount_final=(TextView)itemView.findViewById(R.id.amount_final);
            order_date=(TextView)itemView.findViewById(R.id.order_date);
            status_report=(TextView)itemView.findViewById(R.id.status_report);
            order_id=(TextView)itemView.findViewById(R.id.order_id);
           muje_dbao=(LinearLayout)itemView.findViewById(R.id.muje_dbao);
        }
    }
}
