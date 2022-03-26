package com.example.royalsoftapk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_IncomingVoucher extends  BaseAdapter {

    Context context;
    ArrayList<cls_Virable_IncomingVoucher> alist_IncomingVoucher;

    public Adapter_IncomingVoucher(Context context, ArrayList<cls_Virable_IncomingVoucher> alist_IncomingVoucher) {
        this.context = context;
        this.alist_IncomingVoucher = alist_IncomingVoucher;
    }




    @Override
    public int getCount() {
        return alist_IncomingVoucher.size();
    }

    @Override
    public Object getItem(int position) {
        return alist_IncomingVoucher.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        cls_Virable_IncomingVoucher cls_virable_incomingVoucher=alist_IncomingVoucher.get(position);

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_incomingvoucher, null);
        }

        //----------------------------------------------------------------------------
        TextView StationID = (TextView) convertView.findViewById(R.id.StationID);
        StationID.setText(cls_virable_incomingVoucher.getStationID());

        TextView WorkDayDate = (TextView) convertView.findViewById(R.id.WorkDayDate);
        WorkDayDate.setText(cls_virable_incomingVoucher.getWorkDayDate());


        TextView Amount = (TextView) convertView.findViewById(R.id.Amount);
        Amount.setText(cls_virable_incomingVoucher.getAmount());


        TextView SourceName = (TextView) convertView.findViewById(R.id.SourceName);
        SourceName.setText(cls_virable_incomingVoucher.getSourceName());



        return convertView;
    }
}
