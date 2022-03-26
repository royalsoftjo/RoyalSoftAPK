package com.example.royalsoftapk;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.print.sdk.PrinterConstants;
import com.android.print.sdk.PrinterInstance;
import com.android.print.sdk.bluetooth.BluetoothPort;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.royalsoftapk.bluetooth.BluetoothOperation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Timer;

public class frmIncommingChequesCashVan extends AppCompatActivity {
    private RequestQueue mQueue;
    List<Cls_Customer> ListCustomer=new ArrayList<>();
    List<Cls_Cash> ListBank=new ArrayList<>();
    TextView textdate;
    String dateWork;
    Spinner spinnerCustomer
            ,spinnerBank;
    EditText txtVoucherManualNumdber;
    private static boolean isConnected;                 //是否已经建立了连接
    protected static IPrinterOpertion myOpertion;
    private PrinterInstance mPrinter;
    private ProgressDialog dialog;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_incomming_cheques_cash_van);

        txtVoucherManualNumdber=findViewById(R.id.txtVoucherManualNumdber);
        spinnerCustomer=findViewById(R.id.txtCustomer);
        spinnerBank=findViewById(R.id.cmbBank);
        textdate=findViewById(R.id.dtpPaymentDate);

        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        textdate.setText(currentDate);


        textdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal=Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int Month = cal.get(Calendar.MONTH);
                int Day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog=new DatePickerDialog(frmIncommingChequesCashVan.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,Month,Day);

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
        GetALL();
    }
    private void Print(String base) {
        // Global.addressBlutooth="CC:78:AB:9D:FE:82";
        //  if (Global.printerZebra == null) {
        //    Global.connectPrinter(frmCashVanPos.this, "frmCashVanPos");
        // }
        //   else {
        //      if (Global.connectionZebra.isConnected()) {
        //           sendTestLabel(base);

        //      }
        //    }
        Bitmap as=StringToBitMap(base);
        printImage(as);


    }
    private class MyTask extends java.util.TimerTask{
        @Override
        public void run() {
            if(isConnected && mPrinter != null) {
                byte[] by = mPrinter.read();
                if (by != null) {
                    System.out.println(mPrinter.isConnected() + " read byte " + Arrays.toString(by));
                }
            }
        }
    }

    private frmIncommingChequesCashVan.MyTask myTask;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PrinterConstants.Connect.SUCCESS:
                    isConnected = true;
                    mPrinter = myOpertion.getPrinter();
                    java.util.Timer timer = new Timer();
                    myTask = new frmIncommingChequesCashVan.MyTask();
                    timer.schedule(myTask, 0, 2000);
                    Toast.makeText(frmIncommingChequesCashVan.this, R.string.yesconn, Toast.LENGTH_SHORT).show();
                    break;
                case PrinterConstants.Connect.FAILED:
                    if(myTask != null){
                        myTask.cancel();
                    }
                    isConnected = false;
                    Toast.makeText(frmIncommingChequesCashVan.this, R.string.conn_failed, Toast.LENGTH_SHORT).show();
                    break;
                case PrinterConstants.Connect.CLOSED:
                    if(myTask != null){
                        myTask.cancel();
                    }
                    isConnected = false;
                    Toast.makeText(frmIncommingChequesCashVan.this, R.string.conn_closed, Toast.LENGTH_SHORT).show();
                    break;
                case PrinterConstants.Connect.NODEVICE:
                    isConnected = false;
                    Toast.makeText(frmIncommingChequesCashVan.this, R.string.conn_no, Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }

            //   updateButtonState();
            if(bt_mac==null ) {
                bt_mac = BluetoothPort.getmDeviceAddress();
                bt_name = BluetoothPort.getmDeviceName();
            }
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }

    };
    private String bt_mac;
    private String bt_name;
    private String wifi_mac;
    private String wifi_name;
    private void openConn() {
        myOpertion = new BluetoothOperation(this, mHandler);
        myOpertion.btAutoConn(this,  mHandler);

    }
    private void connClick(){
        if(isConnected){        //如果已经连接了, 则断开
            myOpertion.close();
            myOpertion = null;
            mPrinter = null;
        }else {
            openConn();
        }
    }
    private void printImage(final Bitmap bitmap){
        if(!isConnected && mPrinter == null) {
            // Global.connectPrinter(frmCashVanPos.this, "frmCashVanPos");
            Global. GetLastPrinter(frmIncommingChequesCashVan.this);
            if(!Global.addressBlutooth.equals(""))
            {
                connClick();
            }
            //  PrintUtils.printImagea(bitmap, mPrinter);

        }
        else {
            PrintUtils.printImagea(this.getResources(), bitmap, mPrinter);
        }
    }
    public Bitmap StringToBitMap(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }
    private void GetALL()
    {
        try {
            GetCustomer();
            GetBank();
        }
        catch (Exception ex)
        {
            Toast.makeText(frmIncommingChequesCashVan.this,ex.getMessage(),Toast.LENGTH_SHORT);
        }
    }
    private void  GetBank()
    {
        conRoyal.UrlRoyal_Query(conRoyal.ConnectionString,"select ID,AName from Bank");

        mQueue = Volley.newRequestQueue(frmIncommingChequesCashVan.this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Query, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<String> aa=new ArrayList<String>();
                    aa.add("--إختر --");
                    ListBank.add(new Cls_Cash(0,"--إختر --"));
                    for(int i =0 ; i < response.length() ; i++)
                    {
                        JSONObject jsonArray = response.getJSONObject(i);
                        int ID = jsonArray.getInt("ID");
                        String AName = jsonArray.getString("AName");

                        ListBank.add(new Cls_Cash(ID,AName));
                        aa.add(AName);
                    }

                    ArrayAdapter<String> adapter2=new ArrayAdapter<String>(frmIncommingChequesCashVan.this,android.R.layout.simple_spinner_item,aa);
                    //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerBank.setAdapter(adapter2);

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
    }
    private void  GetCustomer()
    {
        conRoyal.UrlRoyal_Query(conRoyal.ConnectionString,"select ID,AName from Customer");

        mQueue = Volley.newRequestQueue(frmIncommingChequesCashVan.this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Query, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<String> aa=new ArrayList<String>();
                    aa.add("--إختر --");
                    ListCustomer.add(new Cls_Customer(0,"",0));
                    for(int i =0 ; i < response.length() ; i++)
                    {
                        JSONObject jsonArray = response.getJSONObject(i);
                        int ID = jsonArray.getInt("ID");
                        String AName = jsonArray.getString("AName");

                        ListCustomer.add(new Cls_Customer(ID,AName,0));
                        aa.add(AName);
                    }

                    ArrayAdapter<String> adapter2=new ArrayAdapter<String>(frmIncommingChequesCashVan.this,android.R.layout.simple_spinner_item,aa);
                    //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCustomer.setAdapter(adapter2);

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
    }
}
