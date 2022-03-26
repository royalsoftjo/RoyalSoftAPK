package com.example.royalsoftapk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_DailySalesDetails extends BaseAdapter {
    Context context;
    ArrayList<Cls_Virable_DailySalesDetails> alist;

    public Adapter_DailySalesDetails(Context context, ArrayList<Cls_Virable_DailySalesDetails> alist) {
        this.context = context;
        this.alist = alist;
    }

    @Override
    public int getCount() {
        return alist.size();
    }

    @Override
    public Object getItem(int position) {
        return alist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Cls_Virable_DailySalesDetails cls_virable_dailySalesDetails_Now=alist.get(position);

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listviewlay, null);
        }

        //----------------------------------------------------------------------------
        TextView BranchAName = (TextView) convertView.findViewById(R.id.BranchAName);
        BranchAName.setText(cls_virable_dailySalesDetails_Now.getBranchAName());

        TextView WorkDayDate = (TextView) convertView.findViewById(R.id.WorkDayDate);
        WorkDayDate.setText(cls_virable_dailySalesDetails_Now.getWorkDayDate());


        TextView NetSalesInvoiceAmount = (TextView) convertView.findViewById(R.id.NetSalesInvoiceAmount);
        NetSalesInvoiceAmount.setText(cls_virable_dailySalesDetails_Now.getNetSalesInvoiceAmount());


        TextView TaxAmount16 = (TextView) convertView.findViewById(R.id.TaxAmount16);
        TaxAmount16.setText(cls_virable_dailySalesDetails_Now.getTaxAmount16());


        TextView TaxAmount10 = (TextView) convertView.findViewById(R.id.TaxAmount10);
        TaxAmount10.setText(cls_virable_dailySalesDetails_Now.getTaxAmount10());


        TextView TaxAmount8 = (TextView) convertView.findViewById(R.id.TaxAmount8);
        TaxAmount8.setText(cls_virable_dailySalesDetails_Now.getTaxAmount8());


        TextView TaxAmount5 = (TextView) convertView.findViewById(R.id.TaxAmount5);
        TaxAmount5.setText(cls_virable_dailySalesDetails_Now.getTaxAmount5());


        TextView TaxAmount4 = (TextView) convertView.findViewById(R.id.TaxAmount4);
        TaxAmount4.setText(cls_virable_dailySalesDetails_Now.getTaxAmount4());


        TextView TaxAmount0 = (TextView) convertView.findViewById(R.id.TaxAmount0);
        TaxAmount0.setText(cls_virable_dailySalesDetails_Now.getTaxAmount0());

        return convertView;
    }
}
