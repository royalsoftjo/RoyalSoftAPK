package com.example.royalsoftapk;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Connected_IP extends AppCompatActivity {

    Button btnSaveIP;
    EditText edittextip,txtDataSource;
    RadioButton rdpos,rdcashvan,rdrest,rdpda,rdlocal,rdStatic,rdfeedback,rdProcessingorder,rdIsOrderSales;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connected__ip);
        btnSaveIP=findViewById(R.id.btnSaveIP);
        edittextip=findViewById(R.id.edittextip);
        rdcashvan=findViewById(R.id.rdcashvan);
        rdpda=findViewById(R.id.rdpda);
        rdpos=findViewById(R.id.rdpos);
        rdrest=findViewById(R.id.rdrest);
        rdlocal=findViewById(R.id.rdlocal);
        rdStatic=findViewById(R.id.rdStatic);
        rdIsOrderSales=findViewById(R.id.IsOrderSales);
        rdfeedback=findViewById(R.id.rdfeedback);
        rdProcessingorder=findViewById(R.id.Processingorder);
        txtDataSource=findViewById(R.id.txtDataSource);
        btnSaveIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });


        rdlocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edittextip.setEnabled(true);
                rdStatic.setChecked(false);
            }
        });

        rdStatic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edittextip.setEnabled(false);
                rdlocal.setChecked(false);
            }
        });

        txtDataSource.setVisibility(View.GONE);

        //-----------------------------
        rdcashvan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDataSource.setVisibility(View.VISIBLE);
                rdpda.setChecked(false);
                rdpos.setChecked(false);
                rdrest.setChecked(false);
                rdProcessingorder.setChecked(false);
                rdfeedback.setChecked(false);
                txtDataSource.setEnabled(true);
                rdIsOrderSales.setChecked(false);
            }
        });

        rdpda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdcashvan.setChecked(false);
                rdpos.setChecked(false);
                rdrest.setChecked(false);
                txtDataSource.setVisibility(View.GONE);
                rdProcessingorder.setChecked(false);
                rdfeedback.setChecked(false);
                rdIsOrderSales.setChecked(false);
            }
        });

        rdrest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdpda.setChecked(false);
                rdpos.setChecked(false);
                rdcashvan.setChecked(false);
                txtDataSource.setVisibility(View.GONE);
                rdfeedback.setChecked(false);
                rdProcessingorder.setChecked(false);
                rdIsOrderSales.setChecked(false);
            }
        });
        rdpos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdpda.setChecked(true);
                rdpos.setChecked(false);
                rdcashvan.setChecked(false);
                rdrest.setChecked(false);
                txtDataSource.setVisibility(View.GONE);
                rdfeedback.setChecked(false);
                rdProcessingorder.setChecked(false);
                rdIsOrderSales.setChecked(false);
            }
        });
        rdfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdpda.setChecked(false);
                rdpos.setChecked(false);
                rdcashvan.setChecked(false);
                rdrest.setChecked(false);
                txtDataSource.setVisibility(View.VISIBLE);
                txtDataSource.setEnabled(true);
                rdfeedback.setChecked(true);
                rdProcessingorder.setChecked(false);
                rdIsOrderSales.setChecked(false);
            }
        });

        rdProcessingorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdpda.setChecked(false);
                rdpos.setChecked(false);
                rdcashvan.setChecked(false);
                rdrest.setChecked(false);
                txtDataSource.setVisibility(View.VISIBLE);
                txtDataSource.setEnabled(true);
                rdfeedback.setChecked(false);
                rdProcessingorder.setChecked(true);
                rdIsOrderSales.setChecked(false);
            }
        });


        rdIsOrderSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdpda.setChecked(false);
                rdpos.setChecked(false);
                rdcashvan.setChecked(false);
                rdrest.setChecked(false);
                txtDataSource.setVisibility(View.VISIBLE);
                txtDataSource.setEnabled(true);
                rdfeedback.setChecked(false);
                rdProcessingorder.setChecked(false);
                rdIsOrderSales.setChecked(true);
            }
        });
    }
    private  void save()
    {
        try {
            if(!rdStatic.isChecked()) {
                if (edittextip.getText().toString().equals("")) {
                    Toast.makeText(Connected_IP.this, "يرجى إدخال رقم الإتصال", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            if(rdcashvan.isChecked() || rdIsOrderSales.isChecked() || rdfeedback.isChecked() )
            {
                if (txtDataSource.getText().toString().equals("")) {
                    Toast.makeText(Connected_IP.this, "يرجى إدخال اسم ملف الإتصال DataSource Name", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            String ip=edittextip.getText().toString();
            DataBaseHelper dd=new DataBaseHelper(this);
            SQLiteDatabase db = dd.getWritableDatabase();
            int TypeID=0;
            int TypeCon=0;
            if(rdlocal.isChecked())
            {
                TypeCon=0;
            }
            if(rdStatic.isChecked())
            {
                TypeCon=1;
            }

            if(rdpda.isChecked())
            {
                TypeID=0;
            }
            else if(rdpos.isChecked())
            {
                TypeID=1;
            }
            else if(rdrest.isChecked())
            {
                TypeID=2;
            }
            else if(rdcashvan.isChecked())
            {
                TypeID=3;
            }
            else if(rdfeedback.isChecked())
            {
                TypeID=4;
            }
            else if(rdProcessingorder.isChecked())
            {
                TypeID=5;
            }
            else if(rdIsOrderSales.isChecked())
            {
                TypeID=6;
            }
            db.delete("Tbl_Connected", null, null);
            ContentValues contentValues = new ContentValues();
            contentValues.put("IP",ip);
            contentValues.put("TypeID", TypeID);
            contentValues.put("TypeCon", TypeCon);
            contentValues.put("DataSorce", txtDataSource.getText().toString());
            long HeaderId=db.insert("Tbl_Connected", null, contentValues);
            if (HeaderId != -1) {
                Toast.makeText(Connected_IP.this,getString(R.string.Saved_successfully),Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Connected_IP.this,Login.class));
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(Connected_IP.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
