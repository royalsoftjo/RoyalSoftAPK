package com.example.royalsoftapk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_Working_Hours extends BaseAdapter {

    Context context;
    ArrayList<Cls_Virable_Working> alistWorking;

    public Adapter_Working_Hours(Context context, ArrayList<Cls_Virable_Working> alistWorking) {
        this.context = context;
        this.alistWorking = alistWorking;
    }

    @Override
    public int getCount() {
        return alistWorking.size();
    }

    @Override
    public Object getItem(int position) {
        return alistWorking.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Cls_Virable_Working cls_virable_working=alistWorking.get(position);


        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_working_hours, null);
        }

        //----------------------------------------------------------------------------
        TextView AName = (TextView) convertView.findViewById(R.id.Employee_Name);
        AName.setText(cls_virable_working.getEmployee_AName());

        TextView FingerPrintDate = (TextView) convertView.findViewById(R.id.FingerPrintDate);
        FingerPrintDate.setText(cls_virable_working.getFingerPrintDate());


        TextView CheckInTime = (TextView) convertView.findViewById(R.id.CheckInTime);
        CheckInTime.setText(cls_virable_working.getCheckInTime());


        TextView CheckOutTime = (TextView) convertView.findViewById(R.id.CheckOutTime);
        CheckOutTime.setText(cls_virable_working.getCheckOutTime());




        return convertView;
    }
}
