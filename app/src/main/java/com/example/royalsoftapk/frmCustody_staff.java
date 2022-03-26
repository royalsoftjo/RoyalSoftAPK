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

import java.util.ArrayList;

public class frmCustody_staff extends AppCompatActivity {
    ListView listView;
    Adapter_Custody_staff Adapter;
    ArrayList<Cls_Virable_Custody_staff> alist;
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_custody_staff);




        listView =findViewById(R.id.list_Custody_staff);

        getdata();
    }


    private void getdata() {

        alist=new ArrayList<>();



        conRoyal.UrlRoyal_Custody_staff(conRoyal.ConnectionString);
        mQueue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,conRoyal.Url_Custody_staff, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {


                                JSONObject obj = response.getJSONObject(i);

                                Cls_Virable_Custody_staff a=new Cls_Virable_Custody_staff();

                                String ItemName = obj.getString("ItemName");

                                String Qty = obj.getString("Qty");






                                a.setItemName(ItemName);
                                a.setQty(Qty);



                                alist.add(a);
                            }
                            Adapter = new Adapter_Custody_staff(frmCustody_staff.this, alist);
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
