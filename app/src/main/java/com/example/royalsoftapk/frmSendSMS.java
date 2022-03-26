package com.example.royalsoftapk;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class frmSendSMS extends AppCompatActivity {
    private RequestQueue mQueue;
    Spinner spinnerEmp,spinnerStaionID;
    String IDUser_income,NameUser_income,IDStaion_income,IDID_income;
    Button bt_SendMessage;
    EditText ETextMessage;

    String MessageNowSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_send_sms);

        bt_SendMessage=findViewById(R.id.bt_SendMessage);
        ETextMessage=findViewById(R.id.ETextMessage);


        bt_SendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageNowSend = ETextMessage.getText().toString();

                SendMessageVoid(IDID_income,IDUser_income,MessageNowSend);


            }
        });

        //-------------------------------Spinner-----------------------------------------------------
        spinnerEmp=(Spinner)findViewById(R.id.sp_Emp_SMS);
        spinnerStaionID=(Spinner) findViewById(R.id.sp_Pos_SMS);

//-------------------------------Spinner-----------------------------------------------------



//----------------------------------Cls_User_Spinner--------------------------------------------------------------------------------------------------------------------------

        final List<Cls_User_Spinner> userList=new ArrayList<>();



        userList.add(new Cls_User_Spinner(getString(R.string.All_employees),"0"));


        conRoyal.UrlRoyal_Name_Employees(conRoyal.ConnectionString);

        mQueue = Volley.newRequestQueue(frmSendSMS.this);
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

        mQueue = Volley.newRequestQueue(frmSendSMS.this);
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

    private void displayUserData(Cls_User_Spinner user)
    {
        IDUser_income = user.getIDUserNameEmp();
        NameUser_income = user.getUserNameEmp();

        Toast.makeText(this,NameUser_income,Toast.LENGTH_SHORT).show();
    }

    public void SendMessageVoid(String IDID_income2,String IDUser_income2 ,String MessageNowSend2)
    {

        conRoyal.UrlRoyal_Send_Message(conRoyal.ConnectionString,IDID_income2,IDUser_income2,MessageNowSend2);

        mQueue = Volley.newRequestQueue(frmSendSMS.this);
        JsonArrayRequest request2 =new JsonArrayRequest(Request.Method.POST, conRoyal.Url_Send_Message, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response2) {
                Toast.makeText(frmSendSMS.this,"تم إرسال الرسالة بنجاح",Toast.LENGTH_SHORT).show();
                ETextMessage.setText("");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(frmSendSMS.this,"فشل إرسال الرسالة الرجاء المحاولة مرة اخرى",Toast.LENGTH_SHORT).show();

            }
        });

        mQueue.add(request2);
    }
}
