package com.example.royalsoftapk;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class SettingsDevice extends Fragment {
CheckBox cbDailyupdate,cbBranchdataupdate,cbUpdatingwarehousedata,cbUpdate_material_data,cbUpdate_supplier_data,cbUpdate_customer_data,cbUpdate_employee_data,cbGroup_data_update,cbData_migrated_after_each_save;
Button btnSave,btnCancel,btnRest,btnsettings;
    DataBaseHelper db;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_settings_device, container, false);
        cbDailyupdate=view.findViewById(R.id.chDailyupdate);
        cbBranchdataupdate=view.findViewById(R.id.chBranchdataupdate);
        cbUpdatingwarehousedata=view.findViewById(R.id.chUpdatingwarehousedata);
        cbUpdate_material_data=view.findViewById(R.id.chUpdate_material_data);
        cbUpdate_supplier_data=view.findViewById(R.id.chUpdate_supplier_data);
        cbUpdate_customer_data=view.findViewById(R.id.chUpdate_customer_data);
        cbUpdate_employee_data=view.findViewById(R.id.chUpdate_employee_data);
        cbGroup_data_update=view.findViewById(R.id.chGroup_data_update);
        cbData_migrated_after_each_save=view.findViewById(R.id.chData_migrated_after_each_save);
        btnRest=view.findViewById(R.id.noerror);
        btnSave=view.findViewById(R.id.btnSaveSettingsDevice);
        btnCancel=view.findViewById(R.id.btnCancelSettingsDevice);
        btnsettings=view.findViewById(R.id.btnSettingPrinter);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ReportHome reportHome =new ReportHome();
                setFragment(reportHome);
            }
        });
        btnsettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), frmSettingDeviceBltooth.class);
                startActivity(intent);
            }
        });
        btnRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetPassword();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveData(getActivity());
            }
        });
        LoadData();
        return view;
    }
    public  void SaveData(Context c)
    {
        db=new DataBaseHelper(c);
       if(db.InsertSettingsDevice(1,
               Convert.ConvertToint(cbDailyupdate.isChecked())
                ,Convert.ConvertToint(cbBranchdataupdate.isChecked())
                ,Convert.ConvertToint(cbUpdate_supplier_data.isChecked())
                ,Convert.ConvertToint(cbUpdatingwarehousedata.isChecked())
                ,Convert.ConvertToint(cbUpdate_material_data.isChecked())
                ,Convert.ConvertToint(cbUpdate_customer_data.isChecked())
                ,Convert.ConvertToint(cbUpdate_employee_data.isChecked())
                ,Convert.ConvertToint(cbGroup_data_update.isChecked())
                ,Convert.ConvertToint(cbData_migrated_after_each_save.isChecked())))
       {
           Toast.makeText(getActivity(),"تم حفظ البيانات بنجاح",Toast.LENGTH_SHORT).show();
           final ReportHome reportHome =new ReportHome();
           setFragment(reportHome);

       }
       else
       {
           Toast.makeText(getActivity(),"لم تتم عملية الحفظ",Toast.LENGTH_SHORT).show();
       }
    }
    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction =getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame33,fragment);
        fragmentTransaction.commit();
    }

    private void LoadData()
    {
        db=new DataBaseHelper(getActivity());
        Cursor res=db.SelectSettingsDevice();
        if(res!=null) {
            while (res.moveToNext()) {

                cbDailyupdate.setChecked(Convert.ConvertTobool(res.getString(DataBaseHelper.colUpdateDay)));
                cbBranchdataupdate.setChecked(Convert.ConvertTobool(res.getString(DataBaseHelper.colUpdateBranch)));
                cbUpdate_supplier_data.setChecked(Convert.ConvertTobool(res.getString(DataBaseHelper.colUpdateSupplier)));
                cbUpdatingwarehousedata.setChecked(Convert.ConvertTobool(res.getString(DataBaseHelper.colUpdateStore)));
                cbUpdate_material_data.setChecked(Convert.ConvertTobool(res.getString(DataBaseHelper.colUpdateItems)));
                cbUpdate_customer_data.setChecked(Convert.ConvertTobool(res.getString(DataBaseHelper.colUpdateCustomer)));
                cbUpdate_employee_data.setChecked(Convert.ConvertTobool(res.getString(DataBaseHelper.colUpdateEmp)));
                cbGroup_data_update.setChecked(Convert.ConvertTobool(res.getString(DataBaseHelper.colUpdateCategory)));
                cbData_migrated_after_each_save.setChecked(Convert.ConvertTobool(res.getString(DataBaseHelper.colDatamigration)));

            }
        }
        else
        {
            Toast.makeText(getActivity(),"يرجى إعداد البيانات قبل الخروج",Toast.LENGTH_SHORT).show();
        }
    }

    private void GetPassword()
    {
        //----------------------------- Dailog ItemName_New--------------------------------------------------------


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("يرجى إدخال كلمة المرور");
        builder.setIcon(R.drawable.sms);
        builder.setMessage("يرجى إدخال كلمة المرور     ");

        final EditText ePassword = new EditText(getContext());
        ePassword.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(ePassword);
        builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              String  Password = ePassword.getText().toString();
                if (Password.equals("R0yal@yman")) {
                    Toast.makeText(getContext(), "سيتم بدء الترحيل الكامل ، يرجى الإنتظار ...", Toast.LENGTH_LONG).show();
                    db.rest(getActivity());
                    Toast.makeText(getActivity(), "تم الإنتهاء من الترحيل", Toast.LENGTH_LONG).show();
                    return;
                } else {

                    //---------------------------------------------------------------------------------------

                    Toast.makeText(getActivity(), "كلمة المرور غير صحيحة", Toast.LENGTH_LONG).show();

                }


            }
        });

        builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog add = builder.create();
        add.show();
        //--------------------------------------Button Dailog----------------------------------------------

    }
}
