package com.example.royalsoftapk;


import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class frm_Cash_drow extends AppCompatActivity {

    ListView listView;
    Adapter_CashDraw Adapter;
    ArrayList<Cls_Virable_CashDraw> alist;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm__cash_drow);



        listView =findViewById(R.id.list_Cash_Draw);

        getdata();

    }


    private void getdata() {

        alist=new ArrayList<>();



        conRoyal.UrlRoyal_Cash_Dolar(conRoyal.ConnectionString);
        mQueue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,conRoyal.Url_Cash_Dolar, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {


                                JSONObject obj = response.getJSONObject(i);

                                Cls_Virable_CashDraw a=new Cls_Virable_CashDraw();

                                String SessionID = obj.getString("StationID");

                                String CashBalance = obj.getString("CashBalance");
                                Double DCashBalance = Double.valueOf(CashBalance);



                                DecimalFormat DF = new DecimalFormat("######0.000");

                                a.setSessionID(SessionID);
                                a.setCashBalanceDraw(DF.format(DCashBalance));



                                alist.add(a);
                            }
                            Adapter = new Adapter_CashDraw(frm_Cash_drow.this, alist);
                            listView.setAdapter(Adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });


        mQueue.add(request);



    }
}
