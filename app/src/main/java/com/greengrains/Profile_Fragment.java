package com.greengrains;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_Fragment extends Fragment {
  TextView profile_name,profile_mobile,profile_address,profile_city;
  Button profile_edit;
  ProgressDialog progressDialog;
  Context context;
  CircleImageView profile_image_show;
  TextView profile_update_password;
  String old,new1,new2,id;
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
boolean check=false,upto_profile=false;
    @Override
    public void onPause() {
        super.onPause();
    check=true;
    }

    @Override
    public void onResume() {
        if(check)
        {
            upto_profile=true;
            new getSetting().execute();
        }
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.profile_fragment, container, false);
    profile_name=(TextView)view.findViewById(R.id.profile_name);
    profile_address=(TextView)view.findViewById(R.id.profile_address);
    profile_city=(TextView)view.findViewById(R.id.profile_city);
    profile_mobile=(TextView)view.findViewById(R.id.profile_mobile);
    profile_image_show=(CircleImageView)view.findViewById(R.id.profile_image_show);
    profile_edit=(Button)view.findViewById(R.id.profile_edit);
    profile_edit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
     Intent intent=new Intent(context,Edit_Profile.class);
     context.startActivity(intent);
        }
    });
    profile_update_password=(TextView)view.findViewById(R.id.profile_update_password);
    profile_update_password.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Dialog dialog=new Dialog(context, 0);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.change_password);
            Button cancel_change=(Button)dialog.findViewById(R.id.cancel_change);
            Button confirm_change=(Button)dialog.findViewById(R.id.confirm_change);
            final EditText old_password=(EditText)dialog.findViewById(R.id.old_password);
            final EditText new_password=(EditText)dialog.findViewById(R.id.new_password);
            final EditText confirm_password=(EditText)dialog.findViewById(R.id.confirm_password);
            cancel_change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            confirm_change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences preferences=getActivity().getApplicationContext().
                            getSharedPreferences("login_details",getActivity().
                                    getApplicationContext().MODE_PRIVATE);

                    String def_password=preferences.getString("user_pass","2");
                    String old_pass=old_password.getText().toString();
                    String new_pass=new_password.getText().toString();
                    String confirm_pss = confirm_password.getText().toString();
                    if(old_pass.equals(def_password)&&new_pass.equals(confirm_pss))
                    {
                   old=old_pass;
                   new1=new_pass;
                   new2=confirm_pss;
                   new changePassword().execute();
                   dialog.dismiss();
                    }
                    else if(!old_pass.equals(def_password))
                    {
                     Toast.makeText(context,"Old Password in Not Correct",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
    Toast.makeText(context,"New Password Does Not Match Confirm Password",Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialog.show();
        }
    });
    new getSetting().execute();
    return view;
    }



    private class changePassword extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
           String url="https://express.accountantlalaji.com/newapp/webapi/saipharma/change_password";
           String data=new JsonParser().updatePassword(old,new1,new2,url,id);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s!=null)
            {try {
                JSONObject object = new JSONObject(s);
                String object1 = String.valueOf(object.get("msg"));
                Toast.makeText(context, object1, Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Update")
                        .setMessage(object1)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                builder.create();
                builder.show();
            }
            catch (Exception e)
            {
       Toast.makeText(context,"Please Check your Internet Connection",Toast.LENGTH_SHORT).show();
            }
            }
            else
            {
   Toast.makeText(context,"Some Networking Issues Please Try Again in Sometime",Toast.LENGTH_SHORT).show();
            }
        }
    }
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
            String url="https://express.accountantlalaji.com/newapp/webapi/greenandgrains/getprofile";
            SharedPreferences preferences=getActivity().getApplicationContext().
                    getSharedPreferences("login_details",getActivity().
                            getApplicationContext().MODE_PRIVATE);

            id=preferences.getString("auth_key","2");
            //id="34604";
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
                    String address=String.valueOf(data.get("address"));
                    String city=String.valueOf(data.get("city"));
                    String landmark=String.valueOf(data.get("landmark"));
                    String image_url=String.valueOf(data.get("photo"));
                    if(customer_name.isEmpty())
                    {
                        profile_name.setText("");
                    }
                    else
                    {
                        profile_name.setText(customer_name.toUpperCase());
                    }
                    if(address.isEmpty())
                    {
                        profile_address.setText("");
                    }
                    else
                    {
                        profile_address.setText(address);
                    }
                    if(city.isEmpty())
                    {
                        profile_city.setText("");
                    }
                    else
                    {
                        profile_city.setText(city);
                    }
                    profile_mobile.setText(customer_mobile);
                    if(!image_url.equals(""))
                    {
                        Picasso.with(context).load(image_url)
                                .placeholder(R.drawable.user_55)
                                .error(R.drawable.user_55)
                                .into(profile_image_show);
                        Picasso.with(context).load(image_url)
                                .placeholder(R.drawable.user_55)
                                .error(R.drawable.user_55)
                                .into(new DashBoard().profile_image);
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
