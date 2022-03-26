package com.example.royalsoftapk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_OfferHeaders extends BaseAdapter {

    Context context;
    ArrayList<Cls_Virable_OfferHeaders> alist_OfferHeaders;

    public Adapter_OfferHeaders(Context context, ArrayList<Cls_Virable_OfferHeaders> alist_OfferHeaders) {
        this.context = context;
        this.alist_OfferHeaders = alist_OfferHeaders;
    }


    @Override
    public int getCount() {
        return alist_OfferHeaders.size();
    }

    @Override
    public Object getItem(int position) {
        return alist_OfferHeaders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Cls_Virable_OfferHeaders cls_virable_offerHeaders=alist_OfferHeaders.get(position);

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_offer_headers, null);
        }

        //----------------------------------------------------------------------------

        TextView ID_Offer = (TextView) convertView.findViewById(R.id.ID_Offer);
        ID_Offer.setText(cls_virable_offerHeaders.getID_Offer());

        TextView Name_Offer = (TextView) convertView.findViewById(R.id.Name_Offer);
        Name_Offer.setText(cls_virable_offerHeaders.getName_Offer());

        TextView DateFrom_Offer = (TextView) convertView.findViewById(R.id.DateFrom_Offer);
        DateFrom_Offer.setText(cls_virable_offerHeaders.getDateFrom_Offer());

        TextView DateTo_Offer = (TextView) convertView.findViewById(R.id.DateTo_Offer);
        DateTo_Offer.setText(cls_virable_offerHeaders.getDateTo_Offer());

        TextView Status_Offer = (TextView) convertView.findViewById(R.id.Status_Offer);
        Status_Offer.setText(cls_virable_offerHeaders.getStatus_Offer());

        return convertView;
    }
}
