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

public class frmDailySalesDetails extends AppCompatActivity {

    ListView listView;
    Adapter_DailySalesDetails Adapter;
    ArrayList<Cls_Virable_DailySalesDetails> alist;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_daily_sales_details);

        listView =findViewById(R.id.lv);

        getdata();
    }

    private void getdata() {

        alist=new ArrayList<>();



        String url = "http://192.168.1.54/api/Inventory/DailySalesDetails";
        mQueue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {


                                JSONObject obj = response.getJSONObject(i);

                                Cls_Virable_DailySalesDetails a=new Cls_Virable_DailySalesDetails();

                                String BranchAName = obj.getString("BranchAName");
                                String WorkDayDate = obj.getString("WorkDayDate");


                                String NetSalesInvoiceAmount = obj.getString("NetSalesInvoiceAmount");
                                Double DNetSalesInvoiceAmount=Double.valueOf(NetSalesInvoiceAmount);


                                String TaxAmount16 = obj.getString("TaxAmount16");
                                Double DTaxAmount16 = Double.valueOf(TaxAmount16);


                                String TaxAmount10 = obj.getString("TaxAmount10");
                                Double DTaxAmount10 = Double.valueOf(TaxAmount10);


                                String TaxAmount8 = obj.getString("TaxAmount8");
                                Double DTaxAmount8 = Double.valueOf(TaxAmount8);

                                String TaxAmount5;
                                Double DTaxAmount5=0.0;
                                try {
                                    TaxAmount5 = obj.getString("TaxAmount5");
                                    DTaxAmount5 = Double.valueOf(TaxAmount5);
                                }
                                catch (Exception e)
                                {
                                    TaxAmount5="";
                                }

                                String TaxAmount4 = obj.getString("TaxAmount4");
                                Double DTaxAmount4 = Double.valueOf(TaxAmount4);


                                String TaxAmount0 = obj.getString("TaxAmount0");
                                Double DTaxAmount0 = Double.valueOf(TaxAmount0);

                                DecimalFormat  DF = new DecimalFormat("######0.000");


                                a.setBranchAName(BranchAName);
                                a.setWorkDayDate(WorkDayDate.substring(0,10));
                                a.setNetSalesInvoiceAmount(DF.format(DNetSalesInvoiceAmount));

                                a.setTaxAmount16( DF.format(DTaxAmount16));
                                a.setTaxAmount10(DF.format(DTaxAmount10));
                                a.setTaxAmount8(DF.format(DTaxAmount8));
                                a.setTaxAmount5(DF.format(DTaxAmount5));
                                a.setTaxAmount4(DF.format(DTaxAmount4));
                                a.setTaxAmount0(DF.format(DTaxAmount0));

                                alist.add(a);
                            }
                            Adapter = new Adapter_DailySalesDetails(frmDailySalesDetails.this, alist);
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
