package com.example.royalsoftapk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_Custody_staff extends BaseAdapter {

    Context context;
    ArrayList<Cls_Virable_Custody_staff> alistCustody_staff;

    public Adapter_Custody_staff(Context context, ArrayList<Cls_Virable_Custody_staff> alistCustody_staff) {
        this.context = context;
        this.alistCustody_staff = alistCustody_staff;
    }

    @Override
    public int getCount() {
        return alistCustody_staff.size();
    }

    @Override
    public Object getItem(int position) {
        return alistCustody_staff.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Cls_Virable_Custody_staff cls_virable_custody_staff=alistCustody_staff.get(position);


        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_custody_staff, null);
        }

        //----------------------------------------------------------------------------
        TextView ItemName = (TextView) convertView.findViewById(R.id.ItemName);
        ItemName.setText(cls_virable_custody_staff.getItemName());

        TextView Qty = (TextView) convertView.findViewById(R.id.Qty);
        Qty.setText(cls_virable_custody_staff.getQty());




        return convertView;

    }
}
