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

public class List_offer_headers extends AppCompatActivity {
    ListView listView;
    Adapter_OfferHeaders Adapter;
    ArrayList<Cls_Virable_OfferHeaders> alist;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_offer_headers);


        listView =findViewById(R.id.list_OfferHeaders);



        getdata();
    }

    private void getdata() {

        alist=new ArrayList<>();



        conRoyal.UrlRoyal_OfferHeaders(conRoyal.ConnectionString);
        mQueue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,conRoyal.Url_OfferHeaders, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {


                                JSONObject obj = response.getJSONObject(i);

                                Cls_Virable_OfferHeaders a=new Cls_Virable_OfferHeaders();

                                String ID = obj.getString("ID");
                                String AName = obj.getString("AName");


                                String DateFrom = obj.getString("DateFrom");



                                String DateTo = obj.getString("DateTo");


                                String Status = obj.getString("Status");


                                a.setID_Offer(ID);
                                a.setName_Offer(AName);
                                a.setDateFrom_Offer(DateFrom.substring(0,10));
                                a.setDateTo_Offer( DateTo.substring(0,10));
                                if(Status.equals("true"))
                                {

                                    a.setStatus_Offer("فعال");
                                }
                                else
                                {

                                    a.setStatus_Offer("غير فعال");
                                }



                                alist.add(a);
                            }
                            Adapter = new Adapter_OfferHeaders(List_offer_headers.this, alist);
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
