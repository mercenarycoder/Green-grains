package com.greengrains;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class Medicine_Show_Activity extends AppCompatActivity {
ImageView medicine_image2;
TextView medicine_name2,medicine_strip2,medicine_description2,medicine_count2,medicine_price2
        ,medicine_price_old2;
ImageButton medicine_add2,medicine_minus2,go_back2,goback_medicine;
String product_id="";
ProgressDialog progressDialog;
Context context;
Button proceed_to_confirm;
productGroup group;
productCount productcount;
totalCost varna;
View ye_line;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicineshower_box);
        productcount=new productCount();
        varna=new totalCost();
        group=new productGroup();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        context=Medicine_Show_Activity.this;
        product_id=getIntent().getStringExtra("product_id");
        medicine_image2=(ImageView)findViewById(R.id.medicine_image2);
        medicine_name2=(TextView)findViewById(R.id.medicine_name2);
        ye_line=(View)findViewById(R.id.ye_line);
        ye_line.setVisibility(View.VISIBLE);
        proceed_to_confirm=(Button)findViewById(R.id.proceed_to_confirm);
        proceed_to_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences=getApplicationContext().getSharedPreferences("direct_buy",
                        getApplicationContext().MODE_PRIVATE);
                SharedPreferences.Editor editor=preferences.edit();
                editor.putString("direct","doit");
                editor.apply();
                editor.commit();
                finish();
            }
        });
        medicine_strip2=(TextView)findViewById(R.id.medicine_strip2);
      /*
        medicine_strip2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String group_all=medicine_strip2.getText().toString();
               if(!group_all.equals(""))
               {
                   SharedPreferences preferences=getApplicationContext().
                           getSharedPreferences("group_info",getApplicationContext().MODE_PRIVATE);
                   SharedPreferences.Editor editor=preferences.edit();
                   if(group.group.containsKey(group_all))
                   {
                       editor.putString("group_id",group.group.get(group_all));
                       editor.putString("group_name",group_all);
                       editor.putString("bool","true");
                       editor.apply();
                       editor.commit();
                       finish();
                   }
               }
               else
               {
                   finish();
               }
            }
        });

       */
        medicine_description2=(TextView)findViewById(R.id.medicine_decription2);
        medicine_count2=(TextView)findViewById(R.id.medicine_quantity2);
        medicine_price2=(TextView)findViewById(R.id.medicine_price2);
        medicine_price_old2=(TextView)findViewById(R.id.medicine_price_old2);
        medicine_add2=(ImageButton)findViewById(R.id.medicine_add2);
        medicine_minus2=(ImageButton)findViewById(R.id.medicine_minus2);
        go_back2=(ImageButton)findViewById(R.id.go_back2);
        goback_medicine=(ImageButton)findViewById(R.id.goback_medicine);
        go_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        goback_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        new getSetting().execute();
  medicine_add2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String quant=medicine_count2.getText().toString();
            int quant2=Integer.parseInt(quant);
            quant2++;
            productcount.calculate++;
            DashBoard inst=DashBoard.instance();
            inst.updateCount();
            String get_price=price;
            if(get_price.startsWith("₹"))
            {
                get_price=get_price.substring(3,get_price.length());
            }
            int price_1=Integer.parseInt(get_price);
            varna.total+=price_1;
            price_1=price_1*quant2;
            String price_change=String.valueOf(price_1);
            medicine_price2.setText("₹"+price_change);
            if(productcount.map2.containsKey(product_id));
            {
                productcount.map2.remove(product_id);
                productcount.map3.remove(product_id);
                productcount.map2.put(product_id,quant2);
                productcount.map3.put(product_id,new cartBaseClass(image_url,name,
                        product_id,String.valueOf(quant2),price,"₹"+price_change,"₹"+price2));
            }
            quant=String.valueOf(quant2);
            medicine_count2.setText(quant);
        }
    });
   medicine_minus2.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String quant=medicine_count2.getText().toString();
            int quant2=Integer.parseInt(quant);
            if(quant2==0)
            {
                Toast.makeText(context,"Quantity Cannot Be Negative",Toast.LENGTH_SHORT).show();
                return;
            }
            productcount.calculate--;
            DashBoard inst=DashBoard.instance();
            inst.updateCount();
            quant2--;
            String get_price=price;
            if(get_price.startsWith("₹"))
            {
                get_price=get_price.substring(3,get_price.length());
            }
            int price_1=Integer.parseInt(get_price);
            varna.total-=price_1;
            price_1=price_1*quant2;
            String price_change=String.valueOf(price_1);
            medicine_price2.setText("₹"+price_change);
            if(productcount.map2.containsKey(product_id));
            {
                productcount.map2.remove(product_id);
                productcount.map3.remove(product_id);
                productcount.map2.put(product_id,quant2);
                productcount.map3.put(product_id,new cartBaseClass(image_url,name,
                        product_id,String.valueOf(quant2),price,"₹"+price_change,"₹"+price2));
            }
            quant=String.valueOf(quant2);
            medicine_count2.setText(quant);
        }
    });
    if(productcount.map2.containsKey(product_id))
    {

        medicine_count2.setText(String.valueOf(productCount.map2.get(product_id)));
    }
}
    String image_url,price,price2,descripton,name;
    private class getSetting extends AsyncTask<String,String,String>
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
            String url="https://express.accountantlalaji.com/newapp/webapi/greenandgrains/product_detail2";

            String data=new JsonParser().getProductDetails(url,product_id);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {
                try {
                    JSONObject object=new JSONObject(s);
                    JSONObject data =object.getJSONObject("data");
                    name=String.valueOf(data.get("product_name"));
                    price=String.valueOf(data.get("product_price"));
                    //use this price2 for old price
                    price2=String.valueOf(data.get("product_price_old"));
                    descripton=String.valueOf(data.get("product_des"));
                    image_url=String.valueOf(data.get("product_image"));
                    String point=String.valueOf(data.get("allot_product_name"));
                    if(!image_url.equals(""))
                    {
              Picasso.with(context).load(image_url)
              .error(R.drawable.ic_groceries)
              .into(medicine_image2);
                    }
                    //String price2[]=price.split(".");
                   // int
                    medicine_strip2.setText("₹ "+String.valueOf(price));
                    medicine_name2.setText(name.toUpperCase());
                    medicine_description2.setText(descripton);
if(Integer.parseInt(medicine_count2.getText().toString())>0) {
    medicine_price2.setText("₹ " + String.valueOf(Integer.parseInt(price) *
            Integer.parseInt(medicine_count2.getText().toString())));
}
else
{
    medicine_price2.setText("₹ "+String.valueOf(price));
}
                    if(price2.length()>0) {
                        medicine_price_old2.setText("₹ " + price2);
                    }
                    else
                    {
                        medicine_price_old2.setVisibility(View.INVISIBLE);
                        ye_line.setVisibility(View.INVISIBLE);
                    }
                    } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context,"JSON Problem",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(context,"Internet Problem",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
