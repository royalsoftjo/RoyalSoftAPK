package com.example.royalsoftapk;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class incoming_voucher extends AppCompatActivity {

    ListView listView;
    Adapter_IncomingVoucher Adapter;
    ArrayList<cls_Virable_IncomingVoucher> alist;
    private RequestQueue mQueue;
    TextView textdate;
    //-------------------------------------------------
    Spinner spinnerEmp,spinnerStaionID;
    String IDUser_income,NameUser_income,IDStaion_income,IDID_income,dateWork_income;
    Button bt_Search_USER;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_voucher);

        listView =findViewById(R.id.list_IncomingVoucher);
        textdate=findViewById(R.id.TextDate_income);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());

        textdate.setText(currentDate);

        bt_Search_USER=findViewById(R.id.bt_Search_USER_income);

        bt_Search_USER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata(Integer.valueOf(IDID_income),dateWork_income,Integer.valueOf(IDUser_income));
            }
        });


        //-------------------------------Spinner-----------------------------------------------------
        spinnerEmp=(Spinner)findViewById(R.id.Spinner_Name_Emp_income);
        spinnerStaionID=(Spinner) findViewById(R.id.spinner_StaionID_income);

//-------------------------------Spinner-----------------------------------------------------

//----------------------------------Cls_User_Spinner--------------------------------------------------------------------------------------------------------------------------

        final List<Cls_User_Spinner> userList=new ArrayList<>();



        userList.add(new Cls_User_Spinner(getString(R.string.All_employees),"0"));


        conRoyal.UrlRoyal_Name_Employees(conRoyal.ConnectionString);

        mQueue = Volley.newRequestQueue(incoming_voucher.this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Name_Employees, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for(int i =0 ; i < response.length() ; i++)
                    {
                        JSONObject jsonArray = response.getJSONObject(i);



                        String UserNameEmp = jsonArray.getString("AName");
                        String IDNameEmp = jsonArray.getString("ID");



                        userList.add(new Cls_User_Spinner(UserNameEmp,IDNameEmp));


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

        mQueue.add(request);




        ArrayAdapter<Cls_User_Spinner> adapter2=new ArrayAdapter<Cls_User_Spinner>(this,android.R.layout.simple_spinner_item,userList);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEmp.setAdapter(adapter2);

//----------------------------------Cls_User_Spinner--------------------------------------------------------------------------------------------------------------------------

//-------------------------------Cls_StaionID_Spinner-----------------------------------------------------//

        final List<Cls_StaionID_Spinner> StaionList=new ArrayList<>();

        StaionList.add(new Cls_StaionID_Spinner(getString(R.string.All_POS),"0"));


        conRoyal.UrlRoyal_Name_Stations(conRoyal.ConnectionString);

        mQueue = Volley.newRequestQueue(incoming_voucher.this);
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


        spinnerEmp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Cls_User_Spinner user_spinner =(Cls_User_Spinner) parent.getSelectedItem();
                displayUserData(user_spinner);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//--------------------------------------------------------------------------------------------------------------//

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









        textdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int Month = cal.get(Calendar.MONTH);
                int Day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(incoming_voucher.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,Month,Day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month=month+1;
                dateWork_income= year + "-" + month + "-" + dayOfMonth;
                textdate.setText(dateWork_income);
            }
        };



    }

    private void getdata(int StationID,String WorkDayDate,int CreationUserID) {

        alist=new ArrayList<>();



        conRoyal.UrlRoyal_IncomingVoucher(conRoyal.ConnectionString,StationID,WorkDayDate,CreationUserID);
        mQueue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,conRoyal.Url_IncomingVoucher, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {


                                JSONObject obj = response.getJSONObject(i);

                                cls_Virable_IncomingVoucher a=new cls_Virable_IncomingVoucher();

                                String StationID = obj.getString("StationID");
                                String WorkDayDate = obj.getString("time");

                                String Amount = obj.getString("Amount");
                                Double DAmount=Double.valueOf(Amount);

                                String SourceName = obj.getString("SourceName");


                                DecimalFormat DF = new DecimalFormat("######0.000");


                                a.setStationID(StationID);
                                a.setWorkDayDate(WorkDayDate);
                                a.setAmount(DF.format(DAmount));
                                a.setSourceName(SourceName);


                                alist.add(a);
                            }
                            Adapter = new Adapter_IncomingVoucher(incoming_voucher.this, alist);
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


    public void getSelectUser(View v)
    {
        Cls_User_Spinner user_spinner=(Cls_User_Spinner) spinnerEmp.getSelectedItem();
        displayUserData(user_spinner);
    }

    public void getSelectUserStaion(View v)
    {
        Cls_StaionID_Spinner cls_staionID_spinner=(Cls_StaionID_Spinner) spinnerEmp.getSelectedItem();
        displayStaionData(cls_staionID_spinner);
    }

    private void displayUserData(Cls_User_Spinner user)
    {
        IDUser_income = user.getIDUserNameEmp();
        NameUser_income = user.getUserNameEmp();

        Toast.makeText(this,NameUser_income,Toast.LENGTH_SHORT).show();
    }

    private void displayStaionData(Cls_StaionID_Spinner StaionID)
    {
        IDStaion_income = StaionID.getStaionID();
        IDID_income = StaionID.getIDStaionID();

        Toast.makeText(this,IDStaion_income,Toast.LENGTH_SHORT).show();
    }


}
