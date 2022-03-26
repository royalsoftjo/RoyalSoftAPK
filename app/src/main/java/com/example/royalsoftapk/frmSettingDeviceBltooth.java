package com.example.royalsoftapk;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class frmSettingDeviceBltooth extends AppCompatActivity {
     List<ClsTypeDevices> ListType=new ArrayList<ClsTypeDevices>();
    ArrayList<ClsBluetoothDevices> ListDevice=new ArrayList<ClsBluetoothDevices>();
    Spinner spinnerTypeDevices;
    Button bt_cancel,btn_Save,bt_NewDevices,bt_BeforeDevice;
    EditText txtID,txtAname,txtMac;
    DataBaseHelper da=new DataBaseHelper(frmSettingDeviceBltooth.this);
    int IDType=1;
    String TypeName="Zebra ZQ520 Printer";
    ListView list1;
    AdapterBluetoothDevices Adapter;
    private final ArrayList<CharSequence> bondedDevices = new ArrayList<>();
    private ArrayAdapter<CharSequence> arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_setting_device_bltooth);
        spinnerTypeDevices=findViewById(R.id.comboTypeDevices);
        bt_cancel=findViewById(R.id.bt_cancel);
        btn_Save=findViewById(R.id.btn_Save);
        txtID=findViewById(R.id.idNo);
        txtID.setEnabled(false);
        txtAname=findViewById(R.id.idAname);
        txtMac=findViewById(R.id.idMac);
        list1=findViewById(R.id.list_setting1);
        bt_NewDevices=findViewById(R.id.btnNewDevice);
        bt_BeforeDevice=findViewById(R.id.btnBefore_Device);




        FillCombo();
        Load();
        GetMaxID();
        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }
        });



        bt_NewDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBondedDevices();
                Toast.makeText(frmSettingDeviceBltooth.this,"الأجهزة المتصلة جديدة",Toast.LENGTH_SHORT);
            }
        });

        bt_BeforeDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ListDevice!=null)
                {
                    ListDevice.clear();
                }
                Load();
                Toast.makeText(frmSettingDeviceBltooth.this,"الأجهزة المعرفة مسبقا",Toast.LENGTH_SHORT);
            }
        });

     /*   spinnerTypeDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClsTypeDevices TypeDevices =(ClsTypeDevices) parent.getSelectedItem();
                IDType=TypeDevices.getID();
            }
        });*/
        spinnerTypeDevices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ClsTypeDevices TypeDevices =(ClsTypeDevices) parent.getSelectedItem();
                IDType=TypeDevices.getID();
                TypeName=TypeDevices.getTypeName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    private void setBondedDevices() {

        bondedDevices.clear();
        final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!bluetoothAdapter.isEnabled()) {
            Intent bluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(bluetoothIntent, 1);
        }
        if (bluetoothAdapter != null) {
            final Set<BluetoothDevice> bondedDeviceSet = bluetoothAdapter.getBondedDevices();
            if(ListDevice!=null)
            {
                ListDevice.clear();
            }
            for (final BluetoothDevice device : bondedDeviceSet) {

                ListDevice.add(new ClsBluetoothDevices(0,device.getName(),0,"",device.getAddress(),""));
            }
            Adapter.notifyDataSetChanged();
            if (arrayAdapter != null) {
                arrayAdapter.notifyDataSetChanged();
            }
        }
    }
    private void FillCombo()
    {
        try {
            ListType.add(new ClsTypeDevices("Zebra ZQ520 Printer",1));
            ListType.add(new ClsTypeDevices("Bixlon Printer",2));
            ListType.add(new ClsTypeDevices("TSC Printer",3));
            ListType.add(new ClsTypeDevices("MTP II Printer",4));
            ListType.add(new ClsTypeDevices("Woosim WSP-i350",5));

            ArrayAdapter<ClsTypeDevices> adapter2=new ArrayAdapter<ClsTypeDevices>(frmSettingDeviceBltooth.this,android.R.layout.simple_spinner_item,ListType);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerTypeDevices.setAdapter(adapter2);
        }
        catch (Exception ex)
        {
            Toast.makeText(frmSettingDeviceBltooth.this,ex.getMessage(),Toast.LENGTH_SHORT);
        }
    }
    boolean vald()
    {
        try {
            if(txtID.getText().toString().equals(""))
            {
                Toast.makeText(frmSettingDeviceBltooth.this,"يرجى إدخال رقم الجهاز",Toast.LENGTH_SHORT).show();
                return  false;
            }
            if(txtAname.getText().toString().equals(""))
            {
                Toast.makeText(frmSettingDeviceBltooth.this,"يرجى إدخال اسم الجهاز",Toast.LENGTH_SHORT).show();
                return  false;
            }
            if(txtMac.getText().toString().equals(""))
            {
                Toast.makeText(frmSettingDeviceBltooth.this,"يرجى إدخال Mac",Toast.LENGTH_SHORT).show();
                return  false;
            }
            return true;
        }
        catch (Exception ex)
        {
            Toast.makeText(frmSettingDeviceBltooth.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    private void GetMaxID()
    {
        try {
            //--------------- Profile Login ------------------------------------------------//
            int ID;
            //--------------- Profile Login ------------------------------------------------//
            SQLiteDatabase db2 = da.getWritableDatabase();
            Cursor res = db2.rawQuery("select max(ID) from Tbl_SettingDeviceBltooth",null);
            while (res.moveToNext())
            {
                ID = res.getInt(0);
                txtID.setText(String.valueOf(ID+1));
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(frmSettingDeviceBltooth.this,ex.getMessage(),Toast.LENGTH_SHORT);
        }
    }
    private void Load()
    {
        try {
            //--------------- Profile Login ------------------------------------------------//
            int ID,TypeID;
            String AName,Mac,TypeName,CreationDate;
            //--------------- Profile Login ------------------------------------------------//
            SQLiteDatabase db2 = da.getWritableDatabase();
            Cursor res = db2.rawQuery("select * from Tbl_SettingDeviceBltooth",null);
            while (res.moveToNext())
            {
                ID = res.getInt(0);
                AName = res.getString(1);
                TypeID = res.getInt(2);
                TypeName = res.getString(3);
                Mac = res.getString(4);
                CreationDate = res.getString(5);
                ListDevice.add(new ClsBluetoothDevices(ID,AName,TypeID,TypeName,Mac,CreationDate));
            }
            Adapter = new AdapterBluetoothDevices(frmSettingDeviceBltooth.this, ListDevice,frmSettingDeviceBltooth.this);
            list1.setAdapter(Adapter);
        }
        catch (Exception ex)
        {
            Toast.makeText(frmSettingDeviceBltooth.this,ex.getMessage(),Toast.LENGTH_SHORT);
        }
    }
    void ClearForm()
    {
        try {
            GetMaxID();
            txtAname.setText("");
            txtMac.setText("");
            spinnerTypeDevices.setSelection(0);
        }
        catch (Exception ex)
        {
            Toast.makeText(frmSettingDeviceBltooth.this,ex.getMessage(),Toast.LENGTH_SHORT);
        }
    }
    private void Save()
    {
        try {
            if(vald())
            {

                int ID=Integer.parseInt(txtID.getText().toString());
                String AName=txtAname.getText().toString();
                String Mac=txtMac.getText().toString();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                String CreationDate=dateFormat.format(new Date());

                int IId=0;
                SQLiteDatabase db = da.getWritableDatabase();
                final Cursor res = db.rawQuery("select * from PDAIncomeVoucherHeader where ID=" + ID + " ", null);
                while (res.moveToNext()) {
                    IId=res.getInt(0);
                }
                if(IId==0)
                {
                    if(da.SavefrmSettingDeviceBltooth(ID, AName, IDType,TypeName,Mac,Global.convertToEnglish(CreationDate))) {
                        Global.ShowDataSaved(frmSettingDeviceBltooth.this, getString(R.string.txtCorrectoperation), getString(R.string.Saved_successfully));
                        ClearForm();
                        if(ListDevice!=null)
                        {
                            ListDevice.clear();
                        }

                        Load();
                    }
                }
                else
                {
                    Toast.makeText(frmSettingDeviceBltooth.this,"لم يتم الحفظ يرجى حذف الطابعة و إضافتها من جديد لعملية التعديل",Toast.LENGTH_SHORT);
                }

            }
        }
        catch (Exception ex)
        {
            Toast.makeText(frmSettingDeviceBltooth.this,ex.getMessage(),Toast.LENGTH_SHORT);
        }
    }
}
