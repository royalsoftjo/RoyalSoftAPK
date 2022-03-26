package com.example.royalsoftapk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_Emp_Online extends BaseAdapter {
    Context context;
    ArrayList<Cls_Virable_Emp_Online> alistEmp;

    public Adapter_Emp_Online(Context context, ArrayList<Cls_Virable_Emp_Online> alistEmp) {
        this.context = context;
        this.alistEmp = alistEmp;
    }

    @Override
    public int getCount() {
        return alistEmp.size();
    }

    @Override
    public Object getItem(int position) {
        return alistEmp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Cls_Virable_Emp_Online cls_virable_emp_online=alistEmp.get(position);


        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_emp_online, null);
        }

        //----------------------------------------------------------------------------
        TextView AName = (TextView) convertView.findViewById(R.id.AName);
        AName.setText(cls_virable_emp_online.getAName());

        TextView OpenDate = (TextView) convertView.findViewById(R.id.OpenDate);
        OpenDate.setText(cls_virable_emp_online.getOpenDate());


        TextView OpeningBalance = (TextView) convertView.findViewById(R.id.OpeningBalance);
        OpeningBalance.setText(cls_virable_emp_online.getOpeningBalance());


        TextView CountInvoice = (TextView) convertView.findViewById(R.id.CountInvoice);
        CountInvoice.setText(cls_virable_emp_online.getCountInvoice());


        TextView SalesTotal = (TextView) convertView.findViewById(R.id.SalesTotal);
        SalesTotal.setText(cls_virable_emp_online.getSalesTotal());

        return convertView;
    }
}
