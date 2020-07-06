package com.greengrains;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class Support_Fragment extends Fragment {
   TextView support_name,support_phone,support_email,support_city;
   Context context;
   CircleImageView imageView;
   ProgressDialog progressDialog;
   LinearLayout open_watsapp;
   totalCost varna;
   @SuppressLint("RestrictedApi")
   @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=getContext();
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.support_fragment, container, false);
        support_name=(TextView)view.findViewById(R.id.support_name);
        imageView=(CircleImageView)view.findViewById(R.id.sup_image);
        support_email=(TextView)view.findViewById(R.id.support_email);
        support_phone=(TextView)view.findViewById(R.id.support_phone);
        open_watsapp=(LinearLayout) view.findViewById(R.id.open_watsapp);
        open_watsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences=context.getApplicationContext().
                        getSharedPreferences("login_details",
                                context.getApplicationContext().MODE_PRIVATE);
                String number=preferences.getString("cmp_mobile","7389438159");
                String masg_at="+91"+number;
                String url = "https://api.whatsapp.com/send?phone="+masg_at;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        support_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number_dial = support_phone.getText().toString();
                Intent call_intent = new Intent(Intent.ACTION_DIAL);
                call_intent.setData(Uri.parse("tel:" + number_dial));
                call_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
                            == PackageManager.PERMISSION_GRANTED) {
                        startActivity(call_intent);
                        return;
                    }
                    else
                    {
                        ActivityCompat.requestPermissions((Activity)context,
                                new String[]{Manifest.permission.CALL_PHONE}, 1);
                    }
                }
                else
                {
                    startActivity(call_intent);
                }
            }});
        support_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[] {support_email.getText().toString() });
                intent.putExtra(Intent.EXTRA_SUBJECT, "Enter Your Subject");
                intent.putExtra(Intent.EXTRA_TEXT, "Enter your body");
                startActivity(Intent.createChooser(intent, "Choose the app to complete action!"));
            }
        });
        support_city=(TextView)view.findViewById(R.id.support_city);
      new getSupportInfo().execute();
       return view;
    }
    public class getSupportInfo extends AsyncTask<String,String,String>
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
            String url="https://express.accountantlalaji.com/newapp/webapi/greenandgrains/contactDetail";
            String data=new JsonParser().baseGetRequest(url);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try {
                    JSONObject object = new JSONObject(s);
                JSONObject data=object.getJSONObject("data");
                support_name.setText(String.valueOf(data.get("name")).toUpperCase());
                support_phone.setText(String.valueOf(data.get("mobile")));
                support_email.setText(String.valueOf(data.get("email")));
                support_city.setText(String.valueOf(data.get("address"))+" "+
                        String.valueOf(data.get("city")));
                String image=String.valueOf(data.get("photo"));
                    Picasso.with(context)
                            .load(image)
                            .placeholder(R.drawable.ic_noimage)
                            .error(R.drawable.ic_noimage)
                            .into(imageView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Toast.makeText(context,"Please Check your Internet Connection",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
