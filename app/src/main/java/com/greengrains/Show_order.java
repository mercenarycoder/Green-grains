package com.greengrains;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Show_order extends AppCompatActivity {
ImageButton go_back2;
TextView id_order_act,status_order_act,time_order_act,total_order_act,grand_order_act,date_order_act;
RecyclerView list_order_act;
order_adapter adapter;
Context context;
globalOrderStore store;
ArrayList<order_view_flager> list_items;
order_BaseClass  list_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);
        store = new globalOrderStore();
        String number = getIntent().getStringExtra("number");
        int i = Integer.parseInt(number);
        context = Show_order.this;
        list_main = store.list2.get(i);
        list_items = list_main.getList_sub();
        go_back2 = (ImageButton) findViewById(R.id.go_back2);
        go_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        id_order_act = (TextView) findViewById(R.id.id_order_act);
        status_order_act = (TextView) findViewById(R.id.status_order_act);
        time_order_act = (TextView) findViewById(R.id.time_order_act);
        total_order_act = (TextView) findViewById(R.id.total_order_act);
        grand_order_act = (TextView) findViewById(R.id.grand_order_act);
        list_order_act = (RecyclerView) findViewById(R.id.list_order_act);
        date_order_act=(TextView)findViewById(R.id.date_order_act);
        id_order_act.setText("#" + list_main.getNumber());
        status_order_act.setText(list_main.getStatus());
        time_order_act.setText(list_main.getTime());
        date_order_act.setText(list_main.getDate());
        total_order_act.setText("₹ " + list_main.getTotal_amount());
        grand_order_act.setText("₹ " + list_main.getTotal_amount());

        adapter = new order_adapter(list_items, context);
        list_order_act.setHasFixedSize(true);
        list_order_act.setLayoutManager(new LinearLayoutManager(context));
        list_order_act.setAdapter(adapter);
    }
}
