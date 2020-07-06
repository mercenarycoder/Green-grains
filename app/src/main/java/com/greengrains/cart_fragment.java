package com.greengrains;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class cart_fragment extends Fragment {
RecyclerView final_medicine;
cartAdapter adapter;
ArrayList<cartBaseClass> list,list2;
Button cod,oc,decline_order,confirm_order;
productCount productcount;
TextView no_item_order;
Context context;
DashBoard dashBoard;
totalCost varna;
ImageButton refresh;
public TextView total_cost_grand,total_cost;
String mode_payment,client_id;
ProgressDialog progressDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     productcount=new productCount();
    list=new ArrayList<>();
    context=getContext();
        varna=new totalCost();
        total_cost_grand=(TextView)getActivity().findViewById(R.id.total_cost_grand);
        total_cost=(TextView)getActivity().findViewById(R.id.total_cost);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.cart_layout, container, false);
     final_medicine=(RecyclerView)view.findViewById(R.id.final_medicines);
     dashBoard=new DashBoard();
     refresh=(ImageButton)view.findViewById(R.id.refresh);
     refresh.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             refreshChanges();
         }
     });
     cod=(Button)view.findViewById(R.id.cod);
     oc=(Button)view.findViewById(R.id.oc);
     decline_order=(Button)view.findViewById(R.id.decline_order);
     confirm_order=(Button)view.findViewById(R.id.confirm_order);
     no_item_order=(TextView)view.findViewById(R.id.no_items_order);

        total_cost_grand=(TextView)view.findViewById(R.id.total_cost_grand);
        total_cost=(TextView)view.findViewById(R.id.total_cost);
        total_cost_grand.setText("₹ "+String.valueOf(totalCost.getTotal()));
        total_cost.setText("₹ "+String.valueOf(totalCost.getTotal()));
     no_item_order.setVisibility(View.INVISIBLE);
     no_item_order.setEnabled(false);
     cod.setBackgroundResource(R.drawable.cart_button4);
     decline_order.setOnClickListener(new View.OnClickListener() {
         @SuppressLint("RestrictedApi")
         @Override
         public void onClick(View v) {
    if(no_item_order.isEnabled())
    {
Toast.makeText(context,"Your Must have atleast element to use this feature",Toast.LENGTH_SHORT).show();
    }
    else
    {
        //clearing the cart
       //productcount=new productCount("erer");
        new DashBoard().fab.setVisibility(View.VISIBLE);
        new DashBoard().fab.setEnabled(true);
        new DashBoard().item_count.setVisibility(View.INVISIBLE);
        new DashBoard().item_count.setEnabled(false);
        new DashBoard().location_spinner.setVisibility(View.VISIBLE);
        new DashBoard().location_spinner.setEnabled(true);
((FragmentActivity)context).getSupportFragmentManager().beginTransaction().
        replace(R.id.frame_base,new Home_Fragment())
        .commit();
    }
         }
     });
     confirm_order.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             if(no_item_order.isEnabled())
             {
                 Toast.makeText(context,"Your Must have atleast element to use this feature",Toast.LENGTH_SHORT).show();
             }
             else
             {
                 //clearing the cart
                 //productcount=new productCount("erer");
               /*  Iterator iterator=productcount.map2.entrySet().iterator();
                 int xz=0;
                 while(iterator.hasNext())
                 {
                     Map.Entry pair=(Map.Entry)iterator.next();
                     String key=String.valueOf(pair.getKey());

                     int quant=Integer.parseInt(String.valueOf(pair.getValue()));
                     if(quant>0)
                     {
                         cartBaseClass obj=productcount.map3.get(key);
                         list.add(obj);
                         xz++;
                     }
                 }
                 System.out.println("ITTE ELEMESTA JHAGGS    "+xz);
                 double price=0;
                 for(int i=0;i<xz;i++)
                 {
                     JSONObject object=new JSONObject();
                     try {
                         price+=(Double)(Double.parseDouble(list.get(i).price.substring(2,list.get(i).price.length())));
                     } catch (Exception e) {
                         e.printStackTrace();
                     }
                 }

                */
                 new confirmOrderApi().execute();
                /*
                 if(price>0.0)
                 {
                     price=price;
                 }
                 else if(price<300.0)
                 {
                     price=price+60.0;
                     seeAlert(price,60,mode_payment);
                 }
                 else if(price>300.0&&price<500.0)
                 {
                     price=price+40.0;
                     seeAlert(price,40,mode_payment);
                 }
                */
            //after this networking code
             }
         }
     });
     mode_payment="cash";
     cod.setText("COD");
     cod.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             cod.setBackgroundResource(R.drawable.cart_button4);
             oc.setBackgroundResource(R.drawable.cart_button2);
             if(no_item_order.isEnabled())
             {
                 Toast.makeText(context,"Your Must have atleast element to use this feature",Toast.LENGTH_SHORT).show();
             }
             else
             {
                 mode_payment="cash";
                 //clearing the cart
             //    productcount=new productCount("erer");
             }
         }
     });
     //oc.setEnabled(false);
     oc.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             oc.setBackgroundResource(R.drawable.cart_button4);
             cod.setBackgroundResource(R.drawable.cart_button2);
             if(no_item_order.isEnabled())
             {
                 Toast.makeText(context,"Your Must have atleast element to use this feature",Toast.LENGTH_SHORT).show();
             }
             else
             {
                 mode_payment="online";
               //  context.startActivity(new Intent(context,razorpay_activity.class));
            // new confirmOrderApi().execute();
             }
         }
     });
        cod.setVisibility(View.VISIBLE);
        oc.setVisibility(View.VISIBLE);
        confirm_order.setVisibility(View.VISIBLE);
        decline_order.setVisibility(View.VISIBLE);
     if(productcount.map2.size()==0)
     {
         Toast.makeText(getContext(),"No Items in the cart",Toast.LENGTH_SHORT).show();
         no_item_order.setVisibility(View.VISIBLE);
         no_item_order.setEnabled(true);
         confirm_order.setVisibility(View.INVISIBLE);
         decline_order.setVisibility(View.INVISIBLE);
         return view;
     }
     else
     {
         Iterator iterator=productcount.map2.entrySet().iterator();
         while(iterator.hasNext())
         {
             Map.Entry pair=(Map.Entry)iterator.next();
             String key=String.valueOf(pair.getKey());
             int quant=Integer.parseInt(String.valueOf(pair.getValue()));
             if(quant>0)
             {
             cartBaseClass obj=productcount.map3.get(key);
             //String price=
                 double pri=Double.parseDouble(obj.getBase_price());
                 //if(productCount.map2.get(obj.getId())>0) {
             cartBaseClass baseObj=new cartBaseClass(obj.getImg(),obj.getMedicine(),obj.getId(),obj.getQty()
                     ,String.valueOf(pri), obj.getPrice(),obj.getPrice_old());
             list.add(obj);
             //}
         }
         }
         if(list.size()>0)
         {
             adapter=new cartAdapter(list,getContext());
             final_medicine.setLayoutManager(new LinearLayoutManager(getContext()));
             final_medicine.setHasFixedSize(true);
             final_medicine.setAdapter(adapter);
         }
         else
         {
             cod.setVisibility(View.INVISIBLE);
             oc.setVisibility(View.INVISIBLE);
             confirm_order.setVisibility(View.INVISIBLE);
             decline_order.setVisibility(View.INVISIBLE);
             Toast.makeText(getContext(),"No Items in the cart",Toast.LENGTH_SHORT).show();
             no_item_order.setVisibility(View.VISIBLE);
             no_item_order.setEnabled(true);
         }
     }
     return view;
    }
boolean check=true;
    public void refreshChanges()
    {
        total_cost_grand.setText("₹ "+String.valueOf(totalCost.getTotal()));
        total_cost.setText("₹ "+String.valueOf(totalCost.getTotal()));
    }
    @SuppressLint("RestrictedApi")
    @Override
    public void onResume() {
    if(mode_payment.equals("online"))
    {
        SharedPreferences login_pref=context.getApplicationContext().
                getSharedPreferences("payment_details",context.getApplicationContext().
                        MODE_PRIVATE);
       if(login_pref.getString("payment","not-payed").equals("payed"))
       {
        check=false;
        SharedPreferences.Editor editor=login_pref.edit();
        editor.putString("done","done");
        editor.apply();
        editor.commit();
        new confirmOrderApi().execute();
       }
    }
        new DashBoard().fab.setVisibility(View.INVISIBLE);
        new DashBoard().fab.setEnabled(false);
        new DashBoard().item_count.setVisibility(View.INVISIBLE);
        new DashBoard().item_count.setEnabled(false);
        super.onResume();
    }

    private class confirmOrderApi extends AsyncTask<String,String,String>
{
    private productCount productCount;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Data Loading");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
    }

    @SuppressLint("WrongThread")
    @Override
    protected String doInBackground(String... strings) {
        String url="https://express.accountantlalaji.com/newapp/webapi/greenandgrains/confirmbooking";
        productCount=new productCount();
        Iterator iterator=productcount.map2.entrySet().iterator();
         int xz=0;
         list2=new ArrayList<>();
        // list=new ArrayList<>();
        String str_product="";
        String str_quantity="";
        double price=0;
      /*  for(int i=0;i<list.size();i++)
        {
            str_product+=list.get(i).id+",";
            str_quantity+=list.get(i).qty+",";
            price+=(Double.parseDouble(list.get(i).qty)*Double.parseDouble(list.get(i).base_price));
        }
       */
        while(iterator.hasNext())
        {
            Map.Entry pair=(Map.Entry)iterator.next();
            String key=String.valueOf(pair.getKey());

            int quant=Integer.parseInt(String.valueOf(pair.getValue()));
            if(quant>0)
            {
                cartBaseClass obj=productcount.map3.get(key);
                list2.add(obj);
            xz++;
            }
        }

        System.out.println("ITTE ELEMESTA JHAGGS    "+xz);
        //double price=0;
        for(int i=0;i<xz;i++)
        {
           JSONObject object=new JSONObject();
            try {
                str_product+=list2.get(i).id+",";
                str_quantity+=list2.get(i).getQty()+",";
                price+=(Double)(Double.parseDouble(list2.get(i).price.substring(1,list2.get(i).price.length()))
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //*/
        System.out.println(price+"--------------------------------------PRICE FINALLY------------------");
       if(str_product.endsWith(","))
       {
       str_product=str_product.substring(0,str_product.length()-1);
       str_quantity=str_quantity.substring(0,str_quantity.length()-1);
       }

        SharedPreferences preferences=getActivity().getApplicationContext().
                getSharedPreferences("login_details",getActivity().
                        getApplicationContext().MODE_PRIVATE);

        client_id=preferences.getString("auth_key","2");
        System.out.println(mode_payment);
        if(list2.size()<=0)
        {
            return "empty_hai";
        }
        if(mode_payment.equals("online")&&check)
        {
            SharedPreferences login_pref=context.getApplicationContext().
                    getSharedPreferences("payment_details",context.getApplicationContext().
                            MODE_PRIVATE);
            SharedPreferences.Editor editor=login_pref.edit();
            editor.putString("payment","not-payed");
            editor.putString("id","this");
            editor.apply();
            editor.commit();
            final Intent intent=new Intent(context,razorpay_activity.class);
            if(price>00.0)
            {
                price=price;
            }
            /*else if(price<300.0)
            {
                price=price+60.0;
                //seeAlert(price,60);
            }
            else if(price>300.0&&price<500.0)
            {
                price=price+40.0;
                //seeAlert(price,40);
            }
             */
            intent.putExtra("price",String.valueOf(varna.getTotal()));
            context.startActivity(intent);
            return null;
        }
        else if(mode_payment.equals("online"))
        {
            SharedPreferences login_pref=context.getApplicationContext().
                    getSharedPreferences("payment_details",context.getApplicationContext().
                            MODE_PRIVATE);
            String pay_id=login_pref.getString("id","this");
            System.out.println(pay_id+"------------------------------------REAVHED HERE--------------------");
            String data=new JsonParser().confirmbooking2(url,client_id,mode_payment,"0","0",
                    "0","0","0",
                    "0","0","0",
                    "",pay_id,
                    str_product
                    ,str_quantity);
            return data;
        }
        else {
            String data = new JsonParser().confirmbooking2(url, client_id, mode_payment, "0", "0",
                    "0", "0", "0",
                    "0", "0", "0", "0", "0", str_product
                    , str_quantity);
            return data;
        }
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progressDialog.dismiss();
        if(s!=null&& !s.equals("empty_hai"))
        {
            try{
            JSONObject object = new JSONObject(s);
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_done);
                dialog.show();
                Button done_done=(Button)dialog.findViewById(R.id.done_done);
                done_done.setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("RestrictedApi")
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        productcount=new productCount("erer");
                        new DashBoard().fab.setVisibility(View.INVISIBLE);
                        new DashBoard().fab.setEnabled(false);
                        new DashBoard().item_count.setVisibility(View.INVISIBLE);
                        new DashBoard().item_count.setEnabled(false);
                        new DashBoard().location_spinner.setVisibility(View.VISIBLE);
                        new DashBoard().location_spinner.setEnabled(true);
                        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction().
                                replace(R.id.frame_base,new Home_Fragment())
                                .commit();
                        varna.total=0;
                    }
                });
                TextView text_done=(TextView)dialog.findViewById(R.id.done_text);
                text_done.setText(String.valueOf(object.get("msg")));
            } catch (JSONException e) {
                e.printStackTrace();
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.dialog_done);
                dialog.show();
                ImageView logo=(ImageView)dialog.findViewById(R.id.warn_warn);
                logo.setImageResource(R.drawable.ic_delivery_man);
                Button done_done=(Button)dialog.findViewById(R.id.done_done);
                done_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                TextView text_done=(TextView)dialog.findViewById(R.id.done_text);
               text_done.setText("Please Try Again in 2 Minutes");
            }

        }
        else if(s.equals("empty_hai"))
        {
            varna.total=0;
            Toast.makeText(context,"No items So No Order can be made ",Toast.LENGTH_SHORT).show();
            cod.setVisibility(View.INVISIBLE);
            cod.setEnabled(false);
            oc.setVisibility(View.INVISIBLE);
            oc.setEnabled(false);
            refresh.setVisibility(View.INVISIBLE);
            total_cost_grand.setVisibility(View.INVISIBLE);
            total_cost.setVisibility(View.INVISIBLE);
            confirm_order.setVisibility(View.INVISIBLE);
            confirm_order.setEnabled(false);
        }
        else
        {
           // Toast.makeText(context,"Please Check your Internet",Toast.LENGTH_SHORT).show();
        }
    }
}
private void seeAlert(final double price, double rate, final String mode_payment)
{
    final Intent intent=new Intent(context,razorpay_activity.class);
    final Dialog dialog = new Dialog(context);
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    dialog.setCancelable(false);
    dialog.setContentView(R.layout.dialog_done);
    dialog.show();
    Button done_done=(Button)dialog.findViewById(R.id.done_done);
    done_done.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(mode_payment.equals("online")) {
                intent.putExtra("price", String.valueOf(price));
                context.startActivity(intent);
            }
            else
            {
                new confirmOrderApi().execute();
            }
            dialog.dismiss();
        }
    });
    TextView text_done=(TextView)dialog.findViewById(R.id.done_text);
    text_done.setText("As your order total cost is less then ₹."+price+"" +
            ".Therefore a delivery charge of ₹."+rate+" will be added in the total bill.");
}
}
