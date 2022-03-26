package com.example.royalsoftapk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_OutgoingVoucher extends BaseAdapter {

    Context context;
    ArrayList<Cls_Virable_OutgoingVoucher> alist_OutgoingVoucher;

    public Adapter_OutgoingVoucher(Context context, ArrayList<Cls_Virable_OutgoingVoucher> alist_OutgoingVoucher) {
        this.context = context;
        this.alist_OutgoingVoucher = alist_OutgoingVoucher;
    }

    @Override
    public int getCount() {
        return alist_OutgoingVoucher.size();
    }

    @Override
    public Object getItem(int position) {
        return alist_OutgoingVoucher.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Cls_Virable_OutgoingVoucher cls_virable_outgoingVoucher=alist_OutgoingVoucher.get(position);

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_outgoingvoucher, null);
        }

        //----------------------------------------------------------------------------
        TextView StationID = (TextView) convertView.findViewById(R.id.StationID);
        StationID.setText(cls_virable_outgoingVoucher.getStationID());

        TextView WorkDayDate = (TextView) convertView.findViewById(R.id.WorkDayDate);
        WorkDayDate.setText(cls_virable_outgoingVoucher.getWorkDayDate());


        TextView Amount = (TextView) convertView.findViewById(R.id.Amount);
        Amount.setText(cls_virable_outgoingVoucher.getAmount());


        TextView SourceName = (TextView) convertView.findViewById(R.id.SourceName);
        SourceName.setText(cls_virable_outgoingVoucher.getSourceName());



        return convertView;
    }
}
