package com.example.royalsoftapk;

import android.content.Intent;
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
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectVendor extends AppCompatActivity {
    ArrayList<String> PartName=new ArrayList<String>();
    ArrayList<String>PartId=new ArrayList<>();
    private RequestQueue mQueue;
    Spinner cmbVendor;
    public  int  VendorID;
    Button BtSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vendor);
        cmbVendor=(Spinner)findViewById(R.id.searchVendor);
        BtSave=findViewById(R.id.btSaveVendorID);
        GetVendor();
        BtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveData();
            }
        });
       cmbVendor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               VendorID = Integer.valueOf(PartId.get(position));
           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });
       // GetVendor();
    }

    public  void GetVendor()
    {
        PartId.add("0");
        PartName.add("--إختر مندوب");
        conRoyal.UrlRoyal_GetVendor(conRoyal.ConnectionString,"0");
        mQueue = Volley.newRequestQueue(this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_GetVendor, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                //  List<String> aa=new ArrayList<String>();
                //  List<String> aa2=new ArrayList<String>();
                try {
                    for (int i = 0; i < response.length(); i++)
                    {
                        JSONObject jsonArray = response.getJSONObject(i);
                        String ID = jsonArray.getString("ID");
                        String AName = jsonArray.getString("aname");
                        PartName.add(AName);
                        PartId.add(ID);

                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SelectVendor.this, android.R.layout.simple_spinner_dropdown_item, PartName);
                    cmbVendor.setAdapter(adapter);


                }
                catch (Exception ex)
                {
                    Toast.makeText(SelectVendor.this,"حدث خطأ ما يرجى إعادة المحاولة",Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(SelectVendor.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(request);
    }


    private void SaveData()
    {

        conRoyal.UrlRoyal_SaveVendorLogin(String.valueOf(conRoyal.IDUser),String.valueOf(VendorID));

        mQueue = Volley.newRequestQueue(this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_SaveVendorLogin, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                conRoyal.VendorID=String.valueOf(VendorID);
                Toast.makeText(SelectVendor.this,"تم حفظ الإعدادات",Toast.LENGTH_LONG).show();
                Intent intent =new Intent(SelectVendor.this,DASHWORD.class);
                startActivity(intent);
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
