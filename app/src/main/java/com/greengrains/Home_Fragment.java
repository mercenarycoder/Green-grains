package com.greengrains;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class Home_Fragment extends Fragment {
    Context context;
    RecyclerView  base_recycler;
    SwipeRefreshLayout swipe_base;
    dashboardmainadapter2 adapter;
    ArrayList<medicineBaseClass2> list;
    ProgressDialog progressDialog;
    String ban_img[];
    String ban_tit[];
    int count=0;
    int limit=1;
    Button mov1_img,mov2_img;
    ViewFlipper ban_img_id;
    productCount2 time_reduce;
    ViewFlipper ban_tit_id;
    totalCost varna;
    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    context=getContext();
    time_reduce=new productCount2();
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.home_fragment, container, false);
    swipe_base=(SwipeRefreshLayout)view.findViewById(R.id.swipe_base);
    base_recycler=(RecyclerView)view.findViewById(R.id.base_recycler);
    ban_img_id=(ViewFlipper) view.findViewById(R.id.ban_img);
    ban_tit_id=(ViewFlipper)view.findViewById(R.id.ban_tit);
    TextView heading=getActivity().findViewById(R.id.heading_heading);
    heading.setText("Green & Grains");
    mov1_img=(Button)view.findViewById(R.id.mov1_img);
    mov2_img=(Button)view.findViewById(R.id.mov2_img);
    mov1_img.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(limit==count+1)
            {
                count=0;
            }
            count++;
           ban_img_id.showNext();
           ban_tit_id.showNext();
          // ban_tit_id.setText(ban_tit[count]);
        }
    });

    mov2_img.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
      ban_img_id.showPrevious();
      ban_tit_id.showPrevious();
      if(count-1<0)
      {
          count=0;
          //ban_tit_id.setText(ban_tit[count]);
      }
      else
      {
          count--;
          //ban_tit_id.setText(ban_tit[count]);
      }
        }
    });
    swipe_base.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            swipe_base.setRefreshing(false);
        new getBasey().execute();
        }
    });

    new getBasey().execute();
    return view;
    }
    private  class getBasey extends AsyncTask<String,String,String>
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
            String url="https://express.accountantlalaji.com/newapp/webapi/greenandgrains/category_list";

            String data=new JsonParser().baseGetRequest(url);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try{
                    JSONObject object=new JSONObject(s);
                    JSONObject object1 = object.getJSONObject("data");
                    JSONArray banner = object1.getJSONArray("banner");
                    JSONArray cats = object1.getJSONArray("category");
                    list=new ArrayList<>();
                    ban_img=new String[banner.length()];
                    ban_tit=new String[banner.length()];
                    for(int i=0;i<cats.length();i++)
                    {
                        JSONObject element = cats.getJSONObject(i);
                        String img=String.valueOf(element.get("category_image"));
                        String name=String.valueOf(element.get("category_name"));
                        String id=String.valueOf(element.get("category_id"));
                        String description="Please Buy Things from these category \n they are the Best ";
                        list.add(new medicineBaseClass2(id,img,name,description));
                    }
                    time_reduce.list=list;
                    for(int i=0;i<banner.length();i++)
                    {
                        JSONObject element = banner.getJSONObject(i);
                        ban_img[i]=String.valueOf(element.get("img"));
                     ImageView imageView=new ImageView(context);
                     imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                     Picasso.with(context).load(ban_img[i]).into(imageView);
                     ban_img_id.addView(imageView);
                     ban_tit[i]=String.valueOf(element.get("title"));
                     TextView textView=new TextView(context);
                     textView.setText(ban_tit[i]);
                     textView.setGravity(Gravity.CENTER);
                     textView.setTextColor(Color.WHITE);
                     textView.setTextSize(22);
                     ban_tit_id.addView(textView);
                    }
                    limit=banner.length();
                    adapter=new dashboardmainadapter2(list,context);
                    base_recycler.setLayoutManager(new LinearLayoutManager(context));
                    base_recycler.setHasFixedSize(true);
                    base_recycler.setAdapter(adapter);
                    count=0;
                    Animation in = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
                    Animation out = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right);
//                    Picasso.with(context).load(ban_img[count]).into(ban_img_id);
                    //ban_tit_id.setText(ban_tit[count]);
                    ban_img_id.setInAnimation(in);
                    ban_img_id.setOutAnimation(out);
                    ban_img_id.setFlipInterval(7000);
                    ban_img_id.startFlipping();
                    ban_tit_id.setInAnimation(in);
                    ban_tit_id.setOutAnimation(out);
                    ban_tit_id.setFlipInterval(7000);
                    ban_tit_id.startFlipping();
                }
                catch (Exception e)
                {
                    Toast.makeText(context,"Some Problem with Internet",Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(context,"Some Problem with Internet",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
