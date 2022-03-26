package com.example.royalsoftapk;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;

public class Adapter_ProcessingordersMerge  extends BaseAdapter {
    Context context;
    ArrayList<Cls_Virable_ProcessingordersMerge> alist_ProcessingordersMerge;

    public Adapter_ProcessingordersMerge(Context context, ArrayList<Cls_Virable_ProcessingordersMerge> alist_ProcessingordersMerge) {
        this.context = context;
        this.alist_ProcessingordersMerge = alist_ProcessingordersMerge;
    }

    @Override
    public int getCount() {
        return  alist_ProcessingordersMerge.size();
    }

    @Override
    public Object getItem(int i) {
        return alist_ProcessingordersMerge.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Cls_Virable_ProcessingordersMerge cls_virable_ProcessingordersMerge=alist_ProcessingordersMerge.get(i);

        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_processingordersmerge, null);
        }

        //----------------------------------------------------------------------------
        TextView Orderid = (TextView) view.findViewById(R.id.orderidMerge);
        Orderid.setText(String.valueOf( cls_virable_ProcessingordersMerge.getOrderid()));



        TextView customername = (TextView) view.findViewById(R.id.customernameMerge);
        customername.setText(cls_virable_ProcessingordersMerge.getCustomerName());


        TextView Date = (TextView) view.findViewById(R.id.DateMerge);
        Date.setText(cls_virable_ProcessingordersMerge.getDelDate());


        ImageView State = (ImageView) view.findViewById(R.id.sendMerge);
            State.setImageResource(R.drawable.ic_check_circle_green_24dp);


        State.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final int ID = cls_virable_ProcessingordersMerge.getOrderid();
                Savedata(context,ID);
            }
        });




        return view;
    }

    private void Savedata(Context c, int orderid) {
        try {
            RequestQueue mQueue;
            conRoyal.UrlRoyal_UpdateStateCompositeOrderMergeByID(orderid);
            mQueue = Volley.newRequestQueue(c);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,conRoyal.Url_UpdateStateCompositeOrderMergeByID, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            Intent intent=new Intent(context,frmProcessingordersMerge.class);
                            context.startActivity(intent);
                            //for (int i = 0; i < response.length(); i++) {
                            // JSONObject obj = response.getJSONObject(i);

                            // String CustomerAName = obj.getString("CustomerAName");

                            //}
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            mQueue.add(request);
        }
        catch (Exception ex)
        {
            Toast.makeText(c,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
}
