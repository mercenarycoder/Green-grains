package com.greengrains;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

public class Register_Act extends AppCompatActivity {
private EditText name_register,confirm_password,phone_register,password;
Button register_register;
TextView back_login;
String name,phone,password_getter,confirm_password_getter;
//for verifying otp
TextView forgot_otp;
EditText verify_otp;
Button verify_it;
LinearLayout after_otp,before_otp;
int age;
String otp_web="";
ProgressDialog progressDialog;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_);
    context=Register_Act.this;
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        name_register=(EditText)findViewById(R.id.name_register);
    confirm_password=(EditText)findViewById(R.id.age_register);
    phone_register=(EditText)findViewById(R.id.phone_register);
    password=(EditText)findViewById(R.id.address_register);
    register_register=(Button)findViewById(R.id.register_register);
    back_login=(TextView)findViewById(R.id.back_login);
    back_login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(Register_Act.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
    });
    register_register.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            name=name_register.getText().toString();
            phone=phone_register.getText().toString();
            password_getter=password.getText().toString();
            confirm_password_getter=confirm_password.getText().toString();

            if(name.equals("")||phone.length()>10||phone.length()<10)
            {
    Toast.makeText(Register_Act.this,"please check the phone number",Toast.LENGTH_SHORT).show();
            }
            else if(!(phone.startsWith("6")||phone.startsWith("7")||phone.startsWith("8")||phone.startsWith("9")))
            {
    Toast.makeText(Register_Act.this,"Phone Number Is Invalid",Toast.LENGTH_SHORT).show();
            }
           else if(!password_getter.equals(confirm_password_getter))
            {
                password.setError("Passwords Does Not Match");
                confirm_password.setError("Passwords Does Not Match");
                Toast.makeText(Register_Act.this,"Both Passwords do not match",Toast.LENGTH_SHORT).show();
            }
            else if(!checkPassword(password_getter))
            {
                Toast.makeText(Register_Act.this,"Password length to short",Toast.LENGTH_SHORT).show();
                //showCustomDialog();
                password.setError("Password Too Short");
                confirm_password.setError("Password Too Short");
                password.setText("");
                confirm_password.setText("");
            }
            else
            {
       new singUP().execute();
            }
        }
    });
    before_otp=(LinearLayout)findViewById(R.id.before_otp);
    after_otp=(LinearLayout)findViewById(R.id.after_otp);
    after_otp.setVisibility(View.INVISIBLE);
    after_otp.setEnabled(false);
    verify_it=(Button)findViewById(R.id.verify_it);
    verify_otp=(EditText)findViewById(R.id.verify_otp);
    forgot_otp=(TextView)findViewById(R.id.forgot_otp);
    verify_it.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String otp_msg=verify_otp.getText().toString();
            if(otp_web.equals(otp_msg))
            {
new verifyFinal().execute();
            }
            else
            {
                Toast.makeText(Register_Act.this,"Please Enter Correct OTP",Toast.LENGTH_SHORT).show();
            }
        }
    });
    }
    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_done);
        dialog.show();
        Button done_done=(Button)dialog.findViewById(R.id.done_done);
        ImageView warn_warn=dialog.findViewById(R.id.warn_warn);
        warn_warn.setImageResource(R.drawable.ic_caution);
        done_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView text_done=(TextView)dialog.findViewById(R.id.done_text);
        text_done.setText("1) Password must contain at least 8 characters\n" +
                "2) Password must contain at least 1 number\n" +
                "3) Password must contain at least 1 upper case letter\n" +
                "4) Password must contain at least 1 lower case letter\n" +
                "5) Password must contain at least 1 special character\n" +
                "6) Password must not contain any spaces");
    }
    public boolean checkPassword(String pass)
    {
        String strRegEx = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
        //remove first if to make it strong password checker
        if(pass.length()>7)
            return true;
        if(pass.matches( strRegEx ))
           return true;
        else
           return false;
    }
    private class verifyFinal extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String url="https://express.accountantlalaji.com/newapp/webapi/greenandgrains/verify_otp";
            String data=new JsonParser().verifyOTP(phone,otp_web,url);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        progressDialog.dismiss();
        if(s!=null) {
            JSONObject object = null;
            try {
                object = new JSONObject(s);
                JSONObject object1=object.getJSONObject("data");
                //toDetails(object);
                SharedPreferences login_pref=getApplicationContext().
                        getSharedPreferences("login_details", getApplicationContext().
                                MODE_PRIVATE);
                SharedPreferences.Editor editor=login_pref.edit();
                //Toast.makeText(MainActivity.this,jsonObject1.getString("user_name"), Toast.LENGTH_SHORT).show();
                editor.putString("user_pass",password_getter);
                editor.putString("user_id",phone);
                editor.putString("auth_key", String.valueOf(object1.get("id")));
                editor.putString("user_point", String.valueOf(object1.get("point")));
                editor.putString("user_city",String.valueOf(object1.get("city")));
                editor.putString("user_address", String.valueOf(object1.get("address")));
                editor.apply();
                final Intent intent=new Intent(Register_Act.this, DashBoard.class);
                intent.putExtra("json",s);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Alert !!")
                        .setMessage(String.valueOf(object.get("msg")))
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(intent);
                                finish();
                            }
                        });
                builder.create();
                builder.show();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(context,"please enter valid otp",Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(context,"Internet issue",Toast.LENGTH_SHORT).show();
        }
        }

    }
    private class singUP extends AsyncTask<String,String,String>
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
            String url="https://express.accountantlalaji.com/newapp/webapi/greenandgrains/createaccount";
            String data=new JsonParser().signUp(url,name,phone,password_getter,confirm_password_getter);
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
                    String status=String.valueOf(object.get("status"));
                    if(status.equals("1"))
                    {
                  JSONObject data=object.getJSONObject("data");
                  otp_web=String.valueOf(data.get("otp"));
                  before_otp.setVisibility(View.INVISIBLE);
                  before_otp.setEnabled(false);
                  after_otp.setVisibility(View.VISIBLE);
                  after_otp.setEnabled(true);
                 // new sendOTP().execute();
                    }
                    else
                    {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle("Alert !!")
                                .setMessage(String.valueOf(object.get("msg")))
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                    }
                                });
                        builder.create();
                        builder.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Register_Act.this,"Please Check JSON Request",Toast.LENGTH_SHORT).show();

                }
            }
            else
            {
      Toast.makeText(Register_Act.this,"Please Check Your Internet Connextion",Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class sendOTP extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
          String url="https://express.accountantlalaji.com/newapp/webapi/greenandgrains/get_otp";
          String data=new JsonParser().sendOTPFirst(url,phone);
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
                otp_web=String.valueOf(object.get("data"));
                Toast.makeText(context,String.valueOf(object.get("msg")),Toast.LENGTH_SHORT).show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
        {

        }
        }
    }
}
