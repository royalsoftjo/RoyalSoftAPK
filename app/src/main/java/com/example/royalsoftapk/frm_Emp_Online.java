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

public class frm_Emp_Online extends AppCompatActivity {
    ListView listView;
    Adapter_Emp_Online Adapter;
    ArrayList<Cls_Virable_Emp_Online> alist;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm__emp__online);

        listView =findViewById(R.id.list_Emp_Online);

        getdata();
    }


    private void getdata() {

        alist=new ArrayList<>();



        conRoyal.UrlRoyal_SessionOpen(conRoyal.ConnectionString);
        mQueue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,conRoyal.Url_SessionOpen, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {


                                JSONObject obj = response.getJSONObject(i);

                                Cls_Virable_Emp_Online a=new Cls_Virable_Emp_Online();

                                String AName = obj.getString("AName");
                                String OpenDate = obj.getString("OpenDate");


                                String OpeningBalance = obj.getString("OpeningBalance");
                                Double DOpeningBalance=Double.valueOf(OpeningBalance);


                                String CountInvoice = obj.getString("CountInvoice");
                                Double DCountInvoice = Double.valueOf(CountInvoice);

                                String SalesTotal = obj.getString("SalesTotal");
                                Double DSalesTotal = Double.valueOf(SalesTotal);


                                DecimalFormat DF = new DecimalFormat("######0.000");
                                DecimalFormat DF2 = new DecimalFormat("######0");

                                a.setAName(AName);
                                a.setOpenDate(OpenDate.substring(0,10));
                                a.setOpeningBalance(DF.format(DOpeningBalance));
                                a.setCountInvoice( DF2.format(DCountInvoice));
                                a.setSalesTotal( DF.format(DSalesTotal));


                                alist.add(a);
                            }
                            Adapter = new Adapter_Emp_Online(frm_Emp_Online.this, alist);
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
