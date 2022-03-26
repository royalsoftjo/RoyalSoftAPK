package com.example.royalsoftapk;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class Adapter_Customer extends BaseAdapter {
    ArrayList<Cls_Select_Customer> alistItems;
    Context context;

    public Adapter_Customer(Context context, ArrayList<Cls_Select_Customer> alistItems) {
        this.context = context;
        this.alistItems = alistItems;
    }
    @Override
    public int getCount() {
        return alistItems.size();
    }

    @Override
    public Object getItem(int position) {
        return alistItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
