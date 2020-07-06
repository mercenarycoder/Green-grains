package com.greengrains;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.text.Line;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import de.hdodenhof.circleimageview.CircleImageView;

public class DashBoard extends AppCompatActivity
{
 static int count_back=0;
    private DrawerLayout drawerLayout;
    HashMap<String, Integer> map;
     NavigationView navigationView;
    LinearLayout appointments;
    LinearLayout my_dashboard,my_order,my_prescribtion,my_logout,my_offer,contact_hume,
            setting,slots_info_get,my_order_side,my_feedback,
            offer,my_profile;
    static LinearLayout location_spinner;
    Toolbar toolbar;
    static FloatingActionButton fab;
    static ImageButton backer_vias;
    TextView menu_version,blossom_link;
    ImageButton user_access,home_access,support_access,offer_access,location_access;
    TextView toolname,heading_heading;
    static TextView item_count;
    FrameLayout frameLayout;
    HashSet<String> order_ids;
     ActionBarDrawerToggle actionBarDrawerToggle;
    int count=0;
    static CircleImageView profile_image;
    SharedPreferences preferences;
    productCount count_product;
    private static DashBoard inst;
    productCount2 cc;
    productGroup prod;
   static String prods[];
   public static DashBoard instance()
   {
       return inst;
   }
   public void updateCount()
   {
       item_count.setVisibility(View.VISIBLE);
       item_count.setText(String.valueOf(count_product.calculate));
       if(count_product.calculate<=0)
       {
      item_count.setVisibility(View.INVISIBLE);
       }
   }
    public DashBoard()
    {
        count_back=0;
       // heading_heading=(TextView)findViewById(R.id.heading_heading);
    }
    @SuppressLint("RestrictedApi")
    @Override
    protected void onStart() {
        super.onStart();
        inst=this;
    SharedPreferences preferences=getApplicationContext().
                getSharedPreferences("login_details",
                        getApplicationContext().MODE_PRIVATE);
        String str=preferences.getString("first","second");
        fab=(FloatingActionButton)findViewById(R.id.fab);
        item_count=(TextView)findViewById(R.id.item_count);
        backer_vias=(ImageButton)findViewById(R.id.backer_vias);
        backer_vias.setVisibility(View.INVISIBLE);
        profile_image=(CircleImageView)findViewById(R.id.profile_image);
        item_count.setVisibility(View.INVISIBLE);
        item_count.setEnabled(false);
        if(count_product.calculate>0) {
            fab.setVisibility(View.VISIBLE);
            fab.setEnabled(true);
            item_count.setVisibility(View.VISIBLE);
            item_count.setText(String.valueOf(count_product.calculate));
        }
        if(str.equals("first")) {
            count_product = new productCount("rtrt");
            prod=new productGroup("efefef");
            cc=new productCount2("erf");
            //here the search elements for search options are being prepared
            String ele=getIntent().getStringExtra("elements");
            prods=ele.split(",");
            prod.products.addAll(Arrays.asList(prods));
            //
            Editor editor = preferences.edit();
            editor.putString("first","second");
            editor.apply();
            editor.commit();
            item_count.setText("0");
        }
     //fab=(FloatingActionButton)
    }

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        setUpToolbar();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        frameLayout=(FrameLayout)findViewById(R.id.frame_base);
getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new Home_Fragment()).commit();
location_spinner=(LinearLayout)findViewById(R.id.spinner_location);
location_spinner.setVisibility(View.VISIBLE);
location_spinner.setEnabled(true);
profile_image=(CircleImageView)findViewById(R.id.profile_image);
heading_heading=(TextView)findViewById(R.id.heading_heading);
heading_heading.setText("Green & Grains");
location_access=(ImageButton)findViewById(R.id.location_access);
backer_vias=(ImageButton)findViewById(R.id.backer_vias);

location_access.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        String city[]=new String[1];
        city[0]="Bhopal";
        boolean selected[]=new boolean[1];
        selected[0]=true;
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(DashBoard.this);
        mbuilder.setTitle("Select Location");
        mbuilder.setMultiChoiceItems(city, selected, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {

            }
        });
        mbuilder.setCancelable(false);
        mbuilder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = mbuilder.create();
        alertDialog.show();
//Toast.makeText(DashBoard.this,"Currently Only Operational In Bhopal",Toast.LENGTH_SHORT).show();
    }
});
      preferences=getApplicationContext().
        getSharedPreferences("login_details",
                getApplicationContext().MODE_PRIVATE);
        String user_image=preferences.getString("user_image","second");
       if(!user_image.equals("")) {
           Picasso.with(DashBoard.this)
                   .load(user_image)
                   .placeholder(R.drawable.user_55)
                   .into(profile_image);
       }
       my_prescribtion=(LinearLayout)findViewById(R.id.my_prescribtion);
        my_prescribtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences=getApplicationContext().
                        getSharedPreferences("login_details",
                                getApplicationContext().MODE_PRIVATE);
                String number=preferences.getString("cmp_mobile","7389438159");
                String masg_at="+91"+number;
                String url = "https://api.whatsapp.com/send?phone="+masg_at;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

my_logout=(LinearLayout)findViewById(R.id.my_logout);
my_dashboard=(LinearLayout)findViewById(R.id.my_dashboard);
my_dashboard.setOnClickListener(new View.OnClickListener() {
    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new Home_Fragment()).commit();
        heading_heading.setText("Green & Grains");
   drawerLayout.closeDrawers();
        if(count_product.calculate>0) {
            fab.setVisibility(View.VISIBLE);
            fab.setEnabled(true);
            item_count.setVisibility(View.VISIBLE);
            item_count.setText(String.valueOf(count_product.calculate));
        }
        item_count.setVisibility(View.INVISIBLE);
        item_count.setEnabled(false);
        backer_vias.setVisibility(View.INVISIBLE);
        location_spinner.setVisibility(View.VISIBLE);
        location_spinner.setEnabled(true);
   count_back=0;
    }
});

my_offer=(LinearLayout)findViewById(R.id.my_offer);
my_offer.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        //getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new OfferFragment()).commit();
        drawerLayout.closeDrawers();
        if(count_product.calculate>0) {
            fab.setVisibility(View.VISIBLE);
            fab.setEnabled(true);
            item_count.setVisibility(View.VISIBLE);
            item_count.setText(String.valueOf(count_product.calculate));
        }
        item_count.setVisibility(View.INVISIBLE);
        item_count.setEnabled(false);
        backer_vias.setVisibility(View.INVISIBLE);
        location_spinner.setVisibility(View.INVISIBLE);
        location_spinner.setEnabled(false);
        count_back=0;
    }
});
my_feedback=(LinearLayout)findViewById(R.id.my_feedback);
my_feedback.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new Feedback_Fragment()).commit();
        drawerLayout.closeDrawers();
        heading_heading.setText("Feedback");
        fab.setVisibility(View.INVISIBLE);
        fab.setEnabled(false);
        item_count.setVisibility(View.INVISIBLE);
        item_count.setEnabled(false);
        backer_vias.setVisibility(View.INVISIBLE);
        location_spinner.setVisibility(View.INVISIBLE);
        location_spinner.setEnabled(false);
        count_back=0;
    }
});
fab=(FloatingActionButton)findViewById(R.id.fab);
item_count=(TextView)findViewById(R.id.item_count);
item_count.setVisibility(View.INVISIBLE);
item_count.setEnabled(false);
        if(count_product.calculate>0) {
            fab.setVisibility(View.VISIBLE);
            fab.setEnabled(true);
        }
fab.setOnClickListener(new View.OnClickListener() {
    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new cart_fragment()).commit();
        drawerLayout.closeDrawers();
        heading_heading.setText("Cart");
        fab.setVisibility(View.INVISIBLE);
        fab.setEnabled(false);
        item_count.setVisibility(View.INVISIBLE);
        item_count.setEnabled(false);
        backer_vias.setVisibility(View.INVISIBLE);
        location_spinner.setVisibility(View.INVISIBLE);
        location_spinner.setEnabled(false);
        count_back=0;
    }
});
my_order=(LinearLayout)findViewById(R.id.my_order);
my_order.setOnClickListener(new View.OnClickListener() {
    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new Order_Fragment()).commit();
        heading_heading.setText("Order History");
        drawerLayout.closeDrawers();
        if(count_product.calculate>0) {
            fab.setVisibility(View.VISIBLE);
            fab.setEnabled(true);
            item_count.setVisibility(View.VISIBLE);
            item_count.setText(String.valueOf(count_product.calculate));
        }
        item_count.setVisibility(View.INVISIBLE);
        item_count.setEnabled(false);
        backer_vias.setVisibility(View.INVISIBLE);
        location_spinner.setVisibility(View.VISIBLE);
        location_spinner.setEnabled(true);
        count_back=0;
    }
});
offer_access=(ImageButton)findViewById(R.id.offer_access);
offer_access.setOnClickListener(new View.OnClickListener() {
    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new Order_Fragment()).commit();
        heading_heading.setText("Order History");
        drawerLayout.closeDrawers();
        if(count_product.calculate>0) {
            fab.setVisibility(View.VISIBLE);
            fab.setEnabled(true);
            item_count.setVisibility(View.VISIBLE);
            item_count.setText(String.valueOf(count_product.calculate));
        }
        item_count.setVisibility(View.INVISIBLE);
        item_count.setEnabled(false);
        backer_vias.setVisibility(View.INVISIBLE);
        location_spinner.setVisibility(View.VISIBLE);
        location_spinner.setEnabled(true);
        count_back=0;
    }
});
my_logout.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        SharedPreferences p=getApplicationContext().getSharedPreferences("login_details",
                getApplicationContext().MODE_PRIVATE);
        Editor editor=p.edit();
        editor.clear();
        editor.apply();
        startActivity(new Intent(DashBoard.this,MainActivity.class));
        finish();
    }
});
my_profile=(LinearLayout)findViewById(R.id.my_profile);
my_profile.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new Profile_Fragment()).commit();
                if(count_product.calculate>0) {
                    fab.setVisibility(View.VISIBLE);
                    fab.setEnabled(true);
                    item_count.setVisibility(View.VISIBLE);
                    item_count.setText(String.valueOf(count_product.calculate));
                }
                item_count.setVisibility(View.INVISIBLE);
                item_count.setEnabled(false);
                heading_heading.setText("My Profile");
                backer_vias.setVisibility(View.INVISIBLE);
                location_spinner.setVisibility(View.INVISIBLE);
                location_spinner.setEnabled(false);
                drawerLayout.closeDrawers();
              count_back=0;
            }
        });
user_access=(ImageButton)findViewById(R.id.user_access);
user_access.setOnClickListener(new View.OnClickListener() {
    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new Profile_Fragment()).commit();
        if(count_product.calculate>0) {
            fab.setVisibility(View.VISIBLE);
            fab.setEnabled(true);
            item_count.setVisibility(View.VISIBLE);
            item_count.setText(String.valueOf(count_product.calculate));

        }
        heading_heading.setText("My Profile");
        item_count.setVisibility(View.INVISIBLE);
        item_count.setEnabled(false);
        backer_vias.setVisibility(View.INVISIBLE);
        location_spinner.setVisibility(View.INVISIBLE);
        location_spinner.setEnabled(false);
        count_back=0;
    }
});
home_access=(ImageButton)findViewById(R.id.home_access);
home_access.setOnClickListener(new View.OnClickListener() {
    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new Home_Fragment()).commit();
count_back=0;
        heading_heading.setText("Green & Grains");
        if(count_product.calculate>0) {
            fab.setVisibility(View.VISIBLE);
            fab.setEnabled(true);
            item_count.setVisibility(View.VISIBLE);
            item_count.setText(String.valueOf(count_product.calculate));

        }
        item_count.setVisibility(View.INVISIBLE);
        item_count.setEnabled(false);
        backer_vias.setVisibility(View.INVISIBLE);
        location_spinner.setVisibility(View.VISIBLE);
        location_spinner.setEnabled(true);
    }
});
contact_hume=(LinearLayout)findViewById(R.id.contact_hume);
contact_hume.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new Support_Fragment()).commit();
        count_back=0;
        heading_heading.setText("Contact Us");
        if(count_product.calculate>0) {
            fab.setVisibility(View.VISIBLE);
            fab.setEnabled(true);
            item_count.setVisibility(View.VISIBLE);
            item_count.setText(String.valueOf(count_product.calculate));

        }
        item_count.setVisibility(View.INVISIBLE);
        item_count.setEnabled(false);
        backer_vias.setVisibility(View.INVISIBLE);
        location_spinner.setVisibility(View.INVISIBLE);
        location_spinner.setEnabled(false);
        drawerLayout.closeDrawers();
    }
});
support_access=(ImageButton)findViewById(R.id.support_access);
support_access.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
 getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new Support_Fragment()).commit();
       count_back=0;
                heading_heading.setText("Contact Us");
                if(count_product.calculate>0) {
                    fab.setVisibility(View.VISIBLE);
                    fab.setEnabled(true);
                    item_count.setVisibility(View.VISIBLE);
                    item_count.setText(String.valueOf(count_product.calculate));
                }
                item_count.setVisibility(View.INVISIBLE);
                item_count.setEnabled(false);
                backer_vias.setVisibility(View.INVISIBLE);
                location_spinner.setVisibility(View.INVISIBLE);
                location_spinner.setEnabled(false);
            }
        });

    }

    private void setUpToolbar() {
        drawerLayout=(DrawerLayout)findViewById(R.id.draw_layout_main);
        toolbar=findViewById(R.id.toolbarmain);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle=new ActionBarDrawerToggle
                (this,drawerLayout,toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().
                setColor(getResources().getColor(R.color.colorPrimary));
        actionBarDrawerToggle.syncState();

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBackPressed() {
        if(count_back==0)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_base,new Home_Fragment()).commit();
            heading_heading.setText("Green & Grains");
            Toast.makeText(DashBoard.this, "click again to exit", Toast.LENGTH_SHORT).show();
            if(count_product.calculate>0) {
                fab.setVisibility(View.VISIBLE);
                fab.setEnabled(true);
                item_count.setVisibility(View.VISIBLE);
                item_count.setText(String.valueOf(count_product.calculate));
            }
            item_count.setVisibility(View.INVISIBLE);
            item_count.setEnabled(false);
            backer_vias.setVisibility(View.INVISIBLE);
            location_spinner.setVisibility(View.VISIBLE);
            location_spinner.setEnabled(true);
            count_back++;
            return;
        }
        super.onBackPressed();
    }
}
