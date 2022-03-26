package com.example.royalsoftapk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter2 extends RecyclerView.Adapter<Adapter2.ViewHolder> {

    private LayoutInflater layoutInflater;
    private List<String>data;
    private List<String>data2;
    private int lastPosition = -1;
    RecyclerView recyclerView;


    Adapter2(Context context, List<String>data, List<String>data2)
    {
            this.layoutInflater= LayoutInflater.from(context);
            this.data=data;
            this.data2=data2;


    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view=layoutInflater.inflate(R.layout.custom_view,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        setAnimation(holder.itemView, position);

        String titel=data.get(position);

        String Desc=data2.get(position);

        holder.textTitel.setText(titel);
        holder.textDescr.setText(Desc);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textTitel,textDescr;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitel= itemView.findViewById(R.id.textTitle);
            textDescr= itemView.findViewById(R.id.textDesc);
        }
    }


    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            //TranslateAnimation anim = new TranslateAnimation(0,-1000,0,-1000);
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.1f, Animation.RELATIVE_TO_SELF, 0.1f);
            //anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
            anim.setDuration(950);//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;

        }
    }

}
