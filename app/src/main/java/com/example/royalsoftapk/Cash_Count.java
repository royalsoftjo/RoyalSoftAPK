package com.example.royalsoftapk;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Cash_Count extends AppCompatActivity {

    ListView listView;
    Adapter_Cash_Count Adapter;
    ArrayList<Cls_Virable_Cash_Count> alist;
    private RequestQueue mQueue;

    //-----------------------------
    TextView textdate1,textdate2;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;
    String dateWork1,dateWork2;
    Button bt_Search_USER_Cash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash__count);


        listView =findViewById(R.id.list_Cash_Count);
        bt_Search_USER_Cash=findViewById(R.id.bt_Search_USER_Cash);
        textdate1=findViewById(R.id.TextDate_Cash1);
        textdate2=findViewById(R.id.TextDate_Cash2);

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());


        dateWork1=currentDate;
        dateWork2=currentDate;


        textdate1.setText(currentDate);
        textdate2.setText(currentDate);


        //getdata("2019-07-01","2019-09-04");















        textdate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int Month = cal.get(Calendar.MONTH);
                int Day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(Cash_Count.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,Month,Day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                dateWork1= year + "-" + month + "-" + dayOfMonth;
                textdate1.setText(dateWork1);
            }
        };

//-------------------------------------------------------------------------------------------------------------------------------
        textdate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int Month = cal.get(Calendar.MONTH);
                int Day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(Cash_Count.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener2,year,Month,Day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                dateWork2= year + "-" + month + "-" + dayOfMonth;
                textdate2.setText(dateWork2);
            }
        };

//-------------------------------------------------------------------------------------------------------------------------------


        bt_Search_USER_Cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata(dateWork1,dateWork2);
            }
        });









    }

    private void getdata(String WorkDayDate1 ,String WorkDayDate2 ) {

        alist=new ArrayList<>();



        conRoyal.UrlRoyal_Cash_Count(conRoyal.ConnectionString,WorkDayDate1,WorkDayDate2);
        mQueue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,conRoyal.Url_Cash_Count, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {


                                JSONObject obj = response.getJSONObject(i);

                                Cls_Virable_Cash_Count a=new Cls_Virable_Cash_Count();

                                String ClosingBalance = obj.getString("ClosingBalance");

                                String TotalCash = obj.getString("TotalCash");


                                String Diff = obj.getString("Diff");



                                String Users = obj.getString("Users");


                                String WorkDayDate = obj.getString("WorkDayDate");


                                //Double DSalesTotal = Double.valueOf(SalesTotal);


                               // DecimalFormat DF = new DecimalFormat("######0.000");


                                a.setClosingBalance(ClosingBalance);
                                a.setDiff(Diff);
                                a.setTotalCash(TotalCash);
                                a.setUsers(Users);
                                a.setWorkDayDate_Cash_Count(WorkDayDate.substring(0,10));


                                alist.add(a);
                            }
                            Adapter = new Adapter_Cash_Count(Cash_Count.this, alist);
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
