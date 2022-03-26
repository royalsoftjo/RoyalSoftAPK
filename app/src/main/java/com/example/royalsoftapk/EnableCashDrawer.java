package com.example.royalsoftapk;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
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
import java.util.List;

public class EnableCashDrawer extends AppCompatActivity {
    private RequestQueue mQueue;
    Spinner spinnerStaionID;
    Button bt_Close,bt_Open;
    String IDStaion_income,IDID_income;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enable_cash_drawer);

        bt_Close=findViewById(R.id.bt_CloseCashDdraw);
        bt_Open=findViewById(R.id.bt_OpenCashDdraw);
        spinnerStaionID=(Spinner) findViewById(R.id.sp_Pos_CloseCashDdraw);



        bt_Close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloseCashVoid(IDID_income);
            }
        });

        bt_Open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCashVoid(IDID_income);
            }
        });

        //-------------------------------Cls_StaionID_Spinner-----------------------------------------------------//

        final List<Cls_StaionID_Spinner> StaionList=new ArrayList<>();

        StaionList.add(new Cls_StaionID_Spinner(getString(R.string.All_POS),"0"));


        conRoyal.UrlRoyal_Name_Stations(conRoyal.ConnectionString);

        mQueue = Volley.newRequestQueue(EnableCashDrawer.this);
        JsonArrayRequest request2 =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Name_Stations, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response2) {
                try {

                    for(int i =0 ; i < response2.length() ; i++)
                    {
                        JSONObject jsonArray = response2.getJSONObject(i);



                        String UserNameStation = jsonArray.getString("AName");
                        String IDNameStation = jsonArray.getString("ID");



                        StaionList.add(new Cls_StaionID_Spinner(UserNameStation,IDNameStation));


                    }



                } catch (JSONException e) {


                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();


            }
        });

        mQueue.add(request2);




        ArrayAdapter<Cls_StaionID_Spinner> adapter3=new ArrayAdapter<Cls_StaionID_Spinner>(this,android.R.layout.simple_spinner_item,StaionList);

        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerStaionID.setAdapter(adapter3);

//-------------------------------Cls_StaionID_Spinner-----------------------------------------------------//

        spinnerStaionID.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cls_StaionID_Spinner staionID_spinner =(Cls_StaionID_Spinner) parent.getSelectedItem();
                displayStaionData(staionID_spinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void displayStaionData(Cls_StaionID_Spinner StaionID)
    {
        IDStaion_income = StaionID.getStaionID();
        IDID_income = StaionID.getIDStaionID();

        Toast.makeText(this,IDStaion_income,Toast.LENGTH_SHORT).show();
    }

    public void CloseCashVoid(String  IDID_income2)
    {

        conRoyal.UrlRoyal_Close_Cash(conRoyal.ConnectionString,IDID_income2,"False");

        mQueue = Volley.newRequestQueue(EnableCashDrawer.this);
        JsonArrayRequest request2 =new JsonArrayRequest(Request.Method.POST, conRoyal.Url_Close_Cash, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response2) {
                Toast.makeText(EnableCashDrawer.this,"تم إغلاق الكاش بنجاح",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(EnableCashDrawer.this,"فشل إغلاق الكاش الرجاء المحاولة مرة اخرى",Toast.LENGTH_SHORT).show();

            }
        });

        mQueue.add(request2);
    }

    public void OpenCashVoid(String IDID_income2)
    {

        conRoyal.UrlRoyal_Close_Cash(conRoyal.ConnectionString,IDID_income2,"True");

        mQueue = Volley.newRequestQueue(EnableCashDrawer.this);
        JsonArrayRequest request2 =new JsonArrayRequest(Request.Method.POST, conRoyal.Url_Close_Cash, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response2) {
                Toast.makeText(EnableCashDrawer.this,"تم فك إغلاق الكاش بنجاح",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(EnableCashDrawer.this,"فشل فك إغلاق الكاش الرجاء المحاولة مرة اخرى",Toast.LENGTH_SHORT).show();

            }
        });

        mQueue.add(request2);
    }
}
