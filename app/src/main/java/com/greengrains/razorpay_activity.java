package com.greengrains;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class razorpay_activity extends AppCompatActivity implements PaymentResultListener {
Context context;
String price;
String name;
double price2;
ProgressDialog progressDialog;
Checkout checkout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_razorpay_activity);
        Checkout.preload(getApplicationContext());
        context=razorpay_activity.this;
        new getSetting().execute();

    }
    public void startPayment() {
        //Checkout checkout;
        Checkout checkout = new Checkout();
        //checkout.setKeyID("rzp_test_X7bg1JPXBcTbNs");

        final Activity activity =this;
        try {
            JSONObject options = new JSONObject();
            options.put("name", name);
        //    options.put("key","rzp_test_X7bg1JPXBcTbNs");
            options.put("description", "payment for order");
            //options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
           // options.put("order_id", "ORD102");
            options.put("currency", "INR");
            options.put("amount", String.valueOf(price2));

            checkout.open(activity, options);
        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }
    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(context,"Payment Successfull",Toast.LENGTH_SHORT).show();
        System.out.println(s+"-------------------------------------------------SUCCESS-------------");
        SharedPreferences login_pref=getApplicationContext().
                getSharedPreferences("payment_details", getApplicationContext().
                        MODE_PRIVATE);
        SharedPreferences.Editor editor=login_pref.edit();
        editor.putString("payment","payed");
        editor.putString("id",s);
        editor.apply();
        editor.commit();
        finish();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(context,"Error "+s,Toast.LENGTH_SHORT).show();
        SharedPreferences login_pref=getApplicationContext().
                getSharedPreferences("payment_details", getApplicationContext().
                        MODE_PRIVATE);
        SharedPreferences.Editor editor=login_pref.edit();
        editor.putString("payment","not-payed");
        editor.putString("id",s);
        editor.apply();
        editor.commit();
        System.out.println(s);
        finish();
    }
    private class getSetting extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            progressDialog=new ProgressDialog(razorpay_activity.this);
            progressDialog.setMessage("Data Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();

            super.onPreExecute();
            //progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/webapi/saipharma/getprofile";
            SharedPreferences preferences=getApplicationContext().
                    getSharedPreferences("login_details",
                            getApplicationContext().MODE_PRIVATE);

           String id=preferences.getString("auth_key","2");
            String data=new JsonParser().getProfile(url,id);
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
                    String customer_name=String.valueOf(data.get("customer_name"));
                    String customer_mobile=String.valueOf(data.get("customer_mobile"));
                    //id=String.valueOf(data.get("id"));
                    String address=String.valueOf(data.get("address"));
                    String city=String.valueOf(data.get("city"));
                    String landmark=String.valueOf(data.get("landmark"));
                    String image_url=String.valueOf(data.get("photo"));
                    name=customer_name;
                    price=getIntent().getStringExtra("price");
                    price2=Double.parseDouble(price)*100;
                    startPayment();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(razorpay_activity.this,"JSON Problem",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(razorpay_activity.this,"Internet Problem",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
