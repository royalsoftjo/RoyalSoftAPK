package com.example.royalsoftapk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_Cash_Count extends BaseAdapter {

    Context context;
    ArrayList<Cls_Virable_Cash_Count> alistCash;

    public Adapter_Cash_Count(Context context, ArrayList<Cls_Virable_Cash_Count> alistCash) {
        this.context = context;
        this.alistCash = alistCash;
    }


    @Override
    public int getCount() {
        return alistCash.size();
    }

    @Override
    public Object getItem(int position) {
        return alistCash.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Cls_Virable_Cash_Count cls_virable_cash_count=alistCash.get(position);


        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.cash_count, null);
        }

        //----------------------------------------------------------------------------
        TextView ClosingBalance = (TextView) convertView.findViewById(R.id.ClosingBalance);
        ClosingBalance.setText(cls_virable_cash_count.getClosingBalance());

        TextView TotalCash = (TextView) convertView.findViewById(R.id.TotalCash);
        TotalCash.setText(cls_virable_cash_count.getTotalCash());


        TextView Diff = (TextView) convertView.findViewById(R.id.Diff);
        Diff.setText(cls_virable_cash_count.getDiff());


        TextView Users = (TextView) convertView.findViewById(R.id.Users);
        Users.setText(cls_virable_cash_count.getUsers());


        TextView WorkDayDate_Cashcount = (TextView) convertView.findViewById(R.id.WorkDayDate_Cashcount);
        WorkDayDate_Cashcount.setText(cls_virable_cash_count.getWorkDayDate_Cash_Count());

        return convertView;
    }
}
