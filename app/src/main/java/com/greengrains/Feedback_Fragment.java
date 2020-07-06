package com.greengrains;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Feedback_Fragment extends Fragment
{
    Context context;
    Button submit_feedback;
    LinearLayout sady_feed;
    ImageView emotion_feedback,picchange_feedback;
    TextView text_emotion_feedback,text_change_feedback;
    String msg="";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     context=getContext();
    }
  int c=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.feedback_fragment, container, false);
       submit_feedback=(Button)view.findViewById(R.id.submit_feedback);
       submit_feedback.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               showCustomDialog();
           }
       });
       sady_feed=(LinearLayout)view.findViewById(R.id.sady_feed);
       emotion_feedback=(ImageView)view.findViewById(R.id.emotion_feedback);
       picchange_feedback=(ImageView)view.findViewById(R.id.picchange_feedback);
       text_emotion_feedback=(TextView)view.findViewById(R.id.text_emotion_feedback);
       text_change_feedback=(TextView)view.findViewById(R.id.text_change_feedback);
       sady_feed.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(c%2==0) {
                   emotion_feedback.setImageResource(R.drawable.ic_sad);
                   picchange_feedback.setImageResource(R.drawable.ic_smile);
                   text_emotion_feedback.setText("Please Tell Us \n What we got wrong!");
                c++;
               }
               else
               {
                   emotion_feedback.setImageResource(R.drawable.ic_smile);
                   picchange_feedback.setImageResource(R.drawable.ic_sad);
                   text_emotion_feedback.setText("Tell Us \n What we got right!");
               c++;
               }
           }
       });
        return view;
    }
    private void showCustomDialog() {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
       final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_done);
        dialog.show();
        Button done_done=(Button)dialog.findViewById(R.id.done_done);
        done_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        TextView text_done=(TextView)dialog.findViewById(R.id.done_text);
    }
}
