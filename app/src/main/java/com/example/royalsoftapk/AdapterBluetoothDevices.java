package com.example.royalsoftapk;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdapterBluetoothDevices extends BaseAdapter {
    Context context;
    ArrayList<ClsBluetoothDevices> alist;
    Activity act;
    DataBaseHelper da;
    public AdapterBluetoothDevices(Context context, ArrayList<ClsBluetoothDevices> alist,Activity act) {
        this.context = context;
        this.alist = alist;
        this.act=act;
       da =new DataBaseHelper(context);
    }

    @Override
    public int getCount() {
         return alist.size();
    }

    @Override
    public Object getItem(int position) {
       return alist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ClsBluetoothDevices cls=alist.get(position);


        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_setting_device1, null);
        }

        //----------------------------------------------------------------------------
        final TextView ID = (TextView) convertView.findViewById(R.id.lDeviceNo);
        ID.setText(String.valueOf(cls.getID()));
        ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settext(cls);
            }
        });

        TextView AName = (TextView) convertView.findViewById(R.id.lDeviceName);
        AName.setText(cls.getDevicesName());
        AName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settext(cls);
            }
        });

        TextView TypeName = (TextView) convertView.findViewById(R.id.lTypeName);
        TypeName.setText(cls.getTypeName());
        TypeName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settext(cls);
            }
        });

        TextView Mac = (TextView) convertView.findViewById(R.id.lMac);
        Mac.setText(cls.getMacAddress());
        Mac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settext(cls);
            }
        });

        ImageView Delete=(ImageView) convertView.findViewById(R.id.ldelete);
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SQLiteDatabase db = da.getWritableDatabase();
                int ID1=Integer.parseInt(ID.getText().toString());
                db.delete("Tbl_SettingDeviceBltooth", "ID=?", new String[]{String.valueOf(ID1)});
                alist.remove(position);
                notifyDataSetChanged();
                GetMaxID();
            }
        });

        return convertView;
    }
    void settext(ClsBluetoothDevices cls){
        try{
            int ID=cls.getID();
            String AName=cls.getDevicesName();
            int TypeId=cls.getTypeId();
            String TypeName=cls.getTypeName();
            String Mac=cls.getMacAddress();

            if(ID==0)
            {
                GetMaxID();
            }
            else
            {
                EditText txtID=act.findViewById(R.id.idNo);
                txtID.setText(String.valueOf(ID));
            }

            EditText txtAname=act.findViewById(R.id.idAname);
            txtAname.setText(AName);

            Spinner spinner=act.findViewById(R.id.comboTypeDevices);
            spinner.setSelection(TypeId-1);

            EditText txtMac=act.findViewById(R.id.idMac);
            txtMac.setText(Mac);
        }
        catch (Exception ex)
        {

        }
    }
    private void GetMaxID()
    {
        try {
            EditText txtID=act.findViewById(R.id.idNo);
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
            Toast.makeText(context,ex.getMessage(),Toast.LENGTH_SHORT);
        }
    }
}
