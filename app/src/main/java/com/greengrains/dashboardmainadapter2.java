package com.greengrains;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

public class dashboardmainadapter2 extends RecyclerView.Adapter<dashboardmainadapter2.viewholder1>{
    ArrayList<medicineBaseClass2> list;
    SharedPreferences.Editor editor;
    productCount dashBoard;
    SharedPreferences medi_pref;
    totalCost varna;
    Context context;
    public dashboardmainadapter2(ArrayList<medicineBaseClass2> list, Context context)
    {
        this.list=list;
        this.context=context;
        setProgress();

        dashBoard=new productCount();
    }
    @NonNull
    @Override
    public viewholder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        View inflator=LayoutInflater.from(context).inflate(R.layout.main_base, parent,
                false);
        viewholder1 viewhold=new viewholder1(inflator);
     return viewhold;
    }

    @Override
    public void onBindViewHolder(@NonNull final viewholder1 holder, final int position) {
        medi_pref=context.getApplicationContext().
                getSharedPreferences("service_id",context.getApplicationContext().MODE_PRIVATE);
        editor=medi_pref.edit();
        final medicineBaseClass2 adapter=list.get(position);
        if(!adapter.getImage_url().equals("")) {
            Picasso.with(context).load(adapter.getImage_url())
                    .placeholder(R.drawable.hailand_fruits)
                    .into(holder.base_image);
        }
        holder.base_head.setText(adapter.getMedicine());
        holder.base_description.setText(adapter.getDescription());
        holder.base_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().
                        replace(R.id.frame_base,new medicine_fragment()).commit();
                editor.putString("id_service",adapter.getId());
                editor.putString("name_service",adapter.getMedicine());
                editor.apply();
            }
        });
        }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class viewholder1 extends RecyclerView.ViewHolder
    {
        ImageView base_image;
        TextView base_head,base_description;
        RelativeLayout get_get;

        public viewholder1(@NonNull View itemView) {
            super(itemView);
          get_get=(RelativeLayout)itemView.findViewById(R.id.get_get);
         base_image=(ImageView)itemView.findViewById(R.id.img_img);
          base_head=(TextView)itemView.findViewById(R.id.heading_item);
          base_description=(TextView)itemView.findViewById(R.id.description_item);
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
