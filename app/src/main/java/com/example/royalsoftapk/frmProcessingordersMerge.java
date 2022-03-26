package com.example.royalsoftapk;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

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

import java.util.ArrayList;

public class frmProcessingordersMerge extends AppCompatActivity {
    ListView listView;
    private RequestQueue mQueue;
    Adapter_ProcessingordersMerge Adapter;
    ArrayList<Cls_Virable_ProcessingordersMerge> alist;


    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,frmProcessingordersMerge.class);
        this.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_processingorders_merge);

        listView =findViewById(R.id.list_processingordersMerge);
        getdata(conRoyal.IDUser);
    }

    private void getdata(int EmployeeID) {
        try {
            alist=new ArrayList<>();
            conRoyal.UrlRoyal_SelectCompositeOrdermerge(EmployeeID);
            mQueue = Volley.newRequestQueue(this);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,conRoyal.Url_SelectCompositeOrdermerge, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject obj = response.getJSONObject(i);
                                    Cls_Virable_ProcessingordersMerge a=new Cls_Virable_ProcessingordersMerge();
                                    int orderid = obj.getInt("orderid");
                                    int customerid = obj.getInt("customerid");
                                    String CustomerName = obj.getString("CustomerName");

                                    String delDate = obj.getString("delDate");


                                    a.setOrderid(orderid);
                                    a.setCustomerName(CustomerName);
                                    a.setCustomerid(customerid);
                                    a.setDelDate(delDate.substring(0,10));


                                    alist.add(a);
                                }
                                Adapter = new Adapter_ProcessingordersMerge(frmProcessingordersMerge.this, alist);
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
        catch (Exception ex)
        {
            Toast.makeText(frmProcessingordersMerge.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
}
