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

public class frm_Working_Hours extends AppCompatActivity {
    ListView listView;
    Adapter_Working_Hours Adapter;
    ArrayList<Cls_Virable_Working> alist;
    private RequestQueue mQueue;
    TextView textdate;
    String dateWork;
    Button bt_Search;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm__working__hours);

        textdate=findViewById(R.id.TextDateWorking);
        bt_Search=findViewById(R.id.bt_Search_Working);

        listView =findViewById(R.id.list_Working);
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        textdate.setText(currentDate);





        bt_Search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata(dateWork);
            }
        });





        textdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int Month = cal.get(Calendar.MONTH);
                int Day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(frm_Working_Hours.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,Month,Day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                dateWork= year + "-" + month + "-" + dayOfMonth;
                textdate.setText(dateWork);
            }
        };









        getdata(currentDate);
    }


    private void getdata(String FingerPrintDate1) {

        alist=new ArrayList<>();



        conRoyal.UrlRoyal_FingerPrintPost(conRoyal.ConnectionString,FingerPrintDate1);
        mQueue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,conRoyal.Url_FingerPrintPost, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {


                                JSONObject obj = response.getJSONObject(i);

                                Cls_Virable_Working a=new Cls_Virable_Working();

                                String EmployeeID = obj.getString("EmployeeID");
                                String AName = obj.getString("AName");


                                String FingerPrintDate = obj.getString("FingerPrintDate");


                                String CheckInTime = obj.getString("CheckInTime");


                                String CheckOutTime = obj.getString("CheckOutTime");


                                a.setEmployeeID(EmployeeID);
                                a.setFingerPrintDate(FingerPrintDate.substring(0,10));
                                a.setEmployee_AName(AName);
                                a.setCheckInTime(CheckInTime);
                                a.setCheckOutTime(CheckOutTime);


                                alist.add(a);
                            }
                            Adapter = new Adapter_Working_Hours(frm_Working_Hours.this, alist);
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
