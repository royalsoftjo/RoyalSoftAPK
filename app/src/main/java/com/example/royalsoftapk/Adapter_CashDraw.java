package com.example.royalsoftapk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_CashDraw extends BaseAdapter {
    Context context;
    ArrayList<Cls_Virable_CashDraw> alistCashDraw;

    public Adapter_CashDraw(Context context, ArrayList<Cls_Virable_CashDraw> alistCashDraw) {
        this.context = context;
        this.alistCashDraw = alistCashDraw;
    }

    @Override
    public int getCount() {
        return alistCashDraw.size();
    }

    @Override
    public Object getItem(int position) {
        return alistCashDraw.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        Cls_Virable_CashDraw cls_virable_cashDraw=alistCashDraw.get(position);


        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_cash_draw, null);
        }

        //----------------------------------------------------------------------------
        TextView StationIdDrow = (TextView) convertView.findViewById(R.id.StationIdDrow);
        StationIdDrow.setText(cls_virable_cashDraw.getSessionID());

        TextView CashBalanceDrow = (TextView) convertView.findViewById(R.id.CashBalanceDrow);
        CashBalanceDrow.setText(cls_virable_cashDraw.getCashBalanceDraw());




        return convertView;
    }
}
