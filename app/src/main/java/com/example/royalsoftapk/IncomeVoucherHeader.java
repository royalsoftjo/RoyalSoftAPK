package com.example.royalsoftapk;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class IncomeVoucherHeader extends AppCompatActivity {
    private RequestQueue mQueue;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    TextView textdate;
    EditText txtTrecipient,txtNote;
    Button btSave;
    String dateWork;
    String ID_Branch,Name_Branch,ID_Store,Name_Store,ID_Supplier,Name_Supplier;
    String frmType;
    //-------------------------cmb-----------------------------------//

    Spinner spinnerBranch;
    Spinner spinnerInventory;
    Spinner spinnerSupplier;


    DataBaseHelper da=new DataBaseHelper(this);

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(IncomeVoucherHeader.this,DASHWORD.class);
        startActivity(intent);
    }




    //---------------------------------------------------------------//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_voucher_header);

//-----------------------------------sp--------------------------//
        spinnerBranch=(Spinner) findViewById(R.id.sp_Branch);
        spinnerInventory=(Spinner) findViewById(R.id.sp_Inventory);
        spinnerSupplier=(Spinner) findViewById(R.id.sp_Supplier);
        txtNote= findViewById(R.id.txtNote2);
        txtTrecipient= findViewById(R.id.txtrecipient2);
        btSave=(Button)findViewById(R.id.btnSave);

        btSave.setFocusable(true);
        btSave.setFocusableInTouchMode(true);///add this line
        btSave.requestFocus();

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Vald())
                {
                    SaveHeaders();
                }
            }
        });


        Bundle extras = getIntent().getExtras();
        frmType=extras.get("frmType").toString();

        //-------------------------------Cls_Supplier_Spinner-----------------------------------------------------//
        da.SetAdapterSupplier(this,spinnerSupplier);
        spinnerSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ID_Supplier=Global.ListID_Supplier.get(position);
                Name_Supplier=Global.ListName_Supplier.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//-------------------------------Cls_Supplier_Spinner-----------------------------------------------------//

        //-------------------------------Cls_Branch_Spinner-----------------------------------------------------//
            da.SetAdapterBranch(this,spinnerBranch);
        spinnerBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ID_Branch= Global.ListID_Branch.get(position);
                Name_Branch= Global.ListName_Branch.get(position);
                da.SetAdapterStore(IncomeVoucherHeader.this,spinnerInventory,Integer.valueOf(ID_Branch));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

//-------------------------------Cls_Branch_Spinner-----------------------------------------------------//
        spinnerInventory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ID_Store=Global.ListID_Store.get(position);
                Name_Store=Global.ListName_Store.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


//-----------------------------------sp--------------------------//


        textdate=findViewById(R.id.txtNewDateVoucher);

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.forLanguageTag("en")).format(new Date());
        dateWork=currentDate;
        textdate.setText(currentDate);

        textdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int Month = cal.get(Calendar.MONTH);
                int Day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(IncomeVoucherHeader.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,Month,Day);

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

    private  boolean Vald()
    {
        if(ID_Branch=="0")
        {
            Toast.makeText(this,"يرجى تحديد الفرع",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(ID_Supplier=="0")
        {
            Toast.makeText(this,"يرجى تحديد المورد",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(ID_Store=="0")
        {
            Toast.makeText(this,"يرجى تحديد المستودع",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }
    void SaveHeaders()
    {
        String Note=txtNote.getText().toString();
        String Trecipient=txtTrecipient.getText().toString();
        if(Note.equals(""))
        {
            Note="Null";
        }
        if(Trecipient.equals(""))
        {
            Trecipient="Null";
        }
        Intent intent=new Intent(IncomeVoucherHeader.this,IncomeVoucherDetails.class);
        intent.putExtra("frmType",frmType);
        intent.putExtra("ID_Branch",ID_Branch);
        intent.putExtra("ID_Store",ID_Store);
        intent.putExtra("ID_Supplier",ID_Supplier);
        intent.putExtra("Trecipient",Trecipient);
        intent.putExtra("Note",Note);
        intent.putExtra("VoucherNumber","0");
        intent.putExtra("dateWork",dateWork);
        intent.putExtra("TransactionID","0");
        startActivity(intent);
    }




  /*  void getact() throws ParseException {

        String Note=txtNote.getText().toString();
        String Trecipient=txtTrecipient.getText().toString();
        if(Note.equals(""))
        {
            Note="Null";
        }
        if(Trecipient.equals(""))
        {
            Trecipient="Null";
        }

        conRoyal.UrlRoyal_SavePDAIncomeVoucher(conRoyal.ConnectionString,ID_Branch,ID_Store, ID_Supplier,
                Trecipient,  Note,  "0",  dateWork,  frmType,  "0");


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,conRoyal.Url_SavePDAIncomeVoucher, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++)
                            {
                                JSONObject obj = response.getJSONObject(i);
                                conRoyal.IncomeVoucherHeaderID = obj.getString("HeaderID");

                            }

                            if(!conRoyal.IncomeVoucherHeaderID.equals("0"))
                            {
                                Intent myIntent = new Intent(IncomeVoucherHeader.this, IncomeVoucherDetails.class);
                                startActivity(myIntent);

                                Toast.makeText(IncomeVoucherHeader.this,"تم حفظ السند بنجاح",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(IncomeVoucherHeader.this,"فشل عملية الحفظ يرجى المحاولة مره اخرى Status false from DataBase",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(IncomeVoucherHeader.this,e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(IncomeVoucherHeader.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        mQueue.add(request);


    }*/
}
