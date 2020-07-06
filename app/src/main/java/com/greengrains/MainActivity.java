package com.greengrains;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {
private EditText username,password;
private TextView customer_care,register,forgot_password,help_number;
private Button login;
ImageView main_name;
String username_getter=null,password_getter=null;
private ProgressDialog dialog;
RelativeLayout wait_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        username=(EditText)findViewById(R.id.username);
        wait_main=(RelativeLayout)findViewById(R.id.wait_main);
        password=(EditText)findViewById(R.id.password);
        customer_care=(TextView)findViewById(R.id.help_number);
        register=(TextView)findViewById(R.id.register);
        main_name=(ImageView)findViewById(R.id.main_name);
        help_number=(TextView)findViewById(R.id.help_number);
        new getBaseInfo().execute();
        help_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number_dial = help_number.getText().toString();
                Intent call_intent = new Intent(Intent.ACTION_DIAL);
                call_intent.setData(Uri.parse("tel:" + number_dial));
                call_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE)
                            == PackageManager.PERMISSION_GRANTED) {
                        startActivity(call_intent);
                        return;
                    }
                    else
                    {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.CALL_PHONE}, 1);
                    }
                }
                else
                {
                    startActivity(call_intent);
                }
            }
        });
        forgot_password=(TextView)findViewById(R.id.forgot_password);
        login=(Button)findViewById(R.id.login);
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(MainActivity.this,ForgotPassword.class);
              startActivity(intent);
            }
        });
        SharedPreferences login_pref = getApplicationContext().
                getSharedPreferences("login_details",getApplicationContext().MODE_PRIVATE);
        if(login_pref.contains("user_id")&&login_pref.contains("user_pass"))
        {
            String email_2=login_pref.getString("user_id",null);
            username.setText(email_2);
            String password_2=login_pref.getString("user_pass",null);
            password.setText(password_2);
            username_getter=username.getText().toString();
            password_getter=password.getText().toString();
            new AdminLogin().execute();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username_getter=username.getText().toString();
                password_getter=password.getText().toString();
                if(username_getter.length()>=1&&password_getter.length()>=8)
                {
                 new AdminLogin().execute();
                }
                else
                {
          Toast.makeText(MainActivity.this,"Please enter Username and password",Toast.LENGTH_SHORT)
          .show();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
Intent intent=new Intent(MainActivity.this,Register_Act.class);
startActivity(intent);
finish();
            }
        });
    }
private class getBaseInfo extends AsyncTask<String,String,String>
{
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog=new ProgressDialog(MainActivity.this);
        dialog.setMessage("Please wait");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
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
    dialog.dismiss();
    if(s!=null)
    {
        try {
            JSONObject object = new JSONObject(s);
            JSONObject object1=object.getJSONObject("data");
            help_number.setText(String.valueOf(object1.get("mobile")));
          //  main_name.setText(String.valueOf(object1.get("name")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    }
}
    private class AdminLogin extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Context context;
        dialog.show();
        }
        @Override
        protected String doInBackground(String... strings) {
            JsonParser jsonParser=new JsonParser();
            String url="https://express.accountantlalaji.com/newapp/webapi/greenandgrains/login";
            String json=jsonParser.userLoginFromJSON(url,username_getter,password_getter);
            //        Log.d("return",json);
            return json;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            if(s!=null)
            {
                JSONObject object = null;
                try {
                    object = new JSONObject(s);
                    JSONObject object1=object.getJSONObject("data");
                   //toDetails(object);
                    SharedPreferences login_pref=getApplicationContext().
                            getSharedPreferences("login_details", getApplicationContext().
                                    MODE_PRIVATE);
                    wait_main.setBackgroundColor(Color.parseColor("#85CFCACA"));
                    SharedPreferences.Editor editor=login_pref.edit();
                    //Toast.makeText(MainActivity.this,jsonObject1.getString("user_name"), Toast.LENGTH_SHORT).show();
                    editor.putString("user_pass",password_getter);
                    editor.putString("user_id",username_getter);
                    editor.putString("auth_key", String.valueOf(object1.get("id")));
                    editor.putString("user_point", String.valueOf(object1.get("point")));
                    editor.putString("user_city",String.valueOf(object1.get("city")));
                    editor.putString("user_address", String.valueOf(object1.get("address")));
                    editor.putString("first","first");
                    editor.putString("cmp_mobile",String.valueOf(object1.get("cmp_mobile")));
                    editor.putString("user_image",String.valueOf(object1.get("photo")));
                    editor.apply();
                    JSONArray array=object1.getJSONArray("products");
                    String elements="";
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject ele=array.getJSONObject(i);
                        elements+=String.valueOf(ele.get("product_name"))+",";
                    }
                    if(elements.endsWith(","))
                    {
                        elements=elements.substring(0,elements.length()-1);
                    }
                    Intent intent=new Intent(MainActivity.this, DashBoard.class);
                    intent.putExtra("json",s);
                    intent.putExtra("elements",elements);
                    startActivity(intent);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this,"please enter valid email and password",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(MainActivity.this,"please check details and try again",Toast.LENGTH_SHORT).show();
            }
        }
        public void toDetails(JSONObject jsonObject1) throws JSONException {
            SharedPreferences login_pref=getApplicationContext().getSharedPreferences("login_details",
                    getApplicationContext().MODE_PRIVATE);
            SharedPreferences.Editor editor=login_pref.edit();
            //Toast.makeText(MainActivity.this,jsonObject1.getString("user_name"), Toast.LENGTH_SHORT).show();
            editor.putString("user_pass",password_getter);
            editor.putString("user_id",username_getter);
            editor.putString("auth_key", String.valueOf(jsonObject1.get("id")));
            editor.putString("user_point", String.valueOf(jsonObject1.get("point")));
            editor.putString("user_city",String.valueOf(jsonObject1.get("city")));
            editor.putString("user_address", String.valueOf(jsonObject1.get("address")));
            editor.apply();
        }
    }
}
