package com.example.royalsoftapk;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class frmProcessingorders extends AppCompatActivity {

    ListView listView;
    TextView textdate;
    private RequestQueue mQueue;
    Button bt_Search_USER;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Adapter_Processingorders Adapter;
    ArrayList<Cls_Virable_Processingorders> alist;
    String dateWork;


    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this,frmProcessingorders.class);
        this.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_processingorders);

        listView =findViewById(R.id.list_processingorders);
        textdate=findViewById(R.id.TextDate_processingorders);
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        textdate.setText(currentDate);
        getdata(conRoyal.IDUser);
        bt_Search_USER=findViewById(R.id.bt_Search_USER);

        bt_Search_USER.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getdata(conRoyal.IDUser);
            }
        });



        textdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int Month = cal.get(Calendar.MONTH);
                int Day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(frmProcessingorders.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,Month,Day);

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
    }

    private void getdata(int EmployeeID) {
        try {
            alist=new ArrayList<>();
            conRoyal.UrlRoyal_SelectItemsToPrepareByEmployee(EmployeeID);
            mQueue = Volley.newRequestQueue(this);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,conRoyal.Url_SelectItemsToPrepareByEmployee, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject obj = response.getJSONObject(i);
                                    Cls_Virable_Processingorders a=new Cls_Virable_Processingorders();
                                    int MainItemID = obj.getInt("MainItemID");
                                    int ID = obj.getInt("ID");
                                    String MainItemAName = obj.getString("MainItemAName");
                                    int SubItemID = obj.getInt("SubItemID");
                                    String SubItemAName = obj.getString("SubItemAName");
                                    int ItemState = obj.optInt("ItemState",2);
                                    String DeliveryDate = obj.getString("DeliveryDate");
                                    int CustomerID = obj.getInt("CustomerID");
                                    String CustomerAName = obj.getString("CustomerAName");

                                    a.setID(ID);
                                    a.setItemID(MainItemID);
                                    a.setItemAName(MainItemAName);
                                    a.setItemSubID(SubItemID);
                                    a.setItemSubAName(SubItemAName);
                                    a.setState(ItemState);
                                    a.setDate(DeliveryDate);
                                    a.setCustomerID(CustomerID);
                                    a.setCustomerName(CustomerAName);

                                    alist.add(a);
                                }
                                Adapter = new Adapter_Processingorders(frmProcessingorders.this, alist);
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
            Toast.makeText(frmProcessingorders.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
}
