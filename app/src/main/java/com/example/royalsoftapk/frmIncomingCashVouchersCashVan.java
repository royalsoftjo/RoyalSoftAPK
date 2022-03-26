package com.example.royalsoftapk;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.royalsoftapk.bluetooth.BluetoothOperation;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.graphics.internal.ZebraImageAndroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class frmIncomingCashVouchersCashVan extends AppCompatActivity {
    private RequestQueue mQueue;
    EditText txtDate,txtMemoID,txtJVID,txtVoucherManualNumber,txtAmount,txtAmountTafkeet,txtReceiver,txtNotes,txtPrev,txtCurrent;
    Spinner cmbCustomer,cmbCash;
    Button btnNew,btnSave,btnExit,btnPrevPaidInCashVan;
    List<Cls_Customer> ListCustomer=new ArrayList<>();
    List<Cls_Cash> ListCash=new ArrayList<>();
    ProgressDialog progressDialog;
    Dialog epicDialog;
    int CashID1;
    String CashName1;
    //----------- Virable -------------------
    int CustomerID=0;
    String CustomerName="";
    int MemoID =0;
    int VoucherManualNumber =0;
    private static boolean isConnected;                 //是否已经建立了连接
    protected static IPrinterOpertion myOpertion;
    private PrinterInstance mPrinter;
    private ProgressDialog dialog;
    @Override
    public void onBackPressed() {
        startActivity(new Intent(frmIncomingCashVouchersCashVan.this,frmCashVanPos.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_incoming_cash_vouchers_cash_van);
        txtDate=findViewById(R.id.DateIncomingCashVouchers);
        txtMemoID=findViewById(R.id.txtMemoID);
        txtJVID=findViewById(R.id.txtJVID);
        txtVoucherManualNumber=findViewById(R.id.txtVoucherManualNumber);
        txtAmount=findViewById(R.id.txtAmount);
        txtAmountTafkeet=findViewById(R.id.txtAmountTafkeet);
        cmbCustomer=findViewById(R.id.txtCustomer);
        cmbCash=findViewById(R.id.cmbCash);
        txtReceiver=findViewById(R.id.txtReceiver);
        txtNotes=findViewById(R.id.txtNotes);
        txtPrev=findViewById(R.id.txtPrev);
        txtCurrent=findViewById(R.id.txtCurrent);
        btnNew=findViewById(R.id.btnNew);
        btnSave=findViewById(R.id.btnSave);
        btnExit=findViewById(R.id.btnExit);
        btnPrevPaidInCashVan=findViewById(R.id.btnPrevPaidInCashVan);
        //---------------------------------------------------------------------------------------
        cmbCash.setEnabled(false);
        txtDate.setText(DateFormat.format("dd-MM-yyyy", new java.util.Date()));
        txtDate.setEnabled(false);
        txtMemoID.setEnabled(false);
        txtReceiver.setText(conRoyal.UserName);

        GetALL();



        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(frmIncomingCashVouchersCashVan.this,frmIncomingCashVouchersCashVan.class));
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }
        });

        btnPrevPaidInCashVan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetInviceBeforeAndPrint();
            }
        });


        cmbCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CustomerID=ListCustomer.get(position).getID();
                CustomerName=ListCustomer.get(position).getAName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cmbCash.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                CustomerID=ListCustomer.get(position).getID();
                CustomerName=ListCustomer.get(position).getAName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void  GetInviceBeforeAndPrint()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        int CashID=CashID1;
        String CashName=CashName1;
        conRoyal.UrlRoyal_PrintCashVoucherCashVanPOS(conRoyal.ConnectionString,CashID,CashName);

        mQueue = Volley.newRequestQueue(frmIncomingCashVouchersCashVan.this);

        JsonObjectRequest request =new JsonObjectRequest(Request.Method.GET, conRoyal.Url_GetPrintCashVoucherCashVanPOS, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // JSONObject jsonArray = response.getAsJsonArray(0);
                    JSONObject jObject = new JSONObject(String.valueOf(response));
                    Print(jObject.getString("PohtoBase64"));
                    progressDialog.dismiss();

                } catch (JSONException e) {
                    Toast.makeText(frmIncomingCashVouchersCashVan.this,e.getMessage(),Toast.LENGTH_SHORT);
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(frmIncomingCashVouchersCashVan.this,error.getMessage(),Toast.LENGTH_SHORT);
                progressDialog.dismiss();
            }
        });

        mQueue.add(request);
    }
    private void GetALL()
    {
        GetLoadDataHeader();
        GetCustomer();
        GetCash();
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
    private String bt_mac;
    private String bt_name;
    private String wifi_mac;
    private String wifi_name;
    private frmIncomingCashVouchersCashVan.MyTask myTask;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PrinterConstants.Connect.SUCCESS:
                    isConnected = true;
                    mPrinter = myOpertion.getPrinter();
                    java.util.Timer timer = new Timer();
                    myTask = new frmIncomingCashVouchersCashVan.MyTask();
                    timer.schedule(myTask, 0, 2000);
                    Toast.makeText(frmIncomingCashVouchersCashVan.this, R.string.yesconn, Toast.LENGTH_SHORT).show();
                    break;
                case PrinterConstants.Connect.FAILED:
                    if(myTask != null){
                        myTask.cancel();
                    }
                    isConnected = false;
                    Toast.makeText(frmIncomingCashVouchersCashVan.this, R.string.conn_failed, Toast.LENGTH_SHORT).show();
                    break;
                case PrinterConstants.Connect.CLOSED:
                    if(myTask != null){
                        myTask.cancel();
                    }
                    isConnected = false;
                    Toast.makeText(frmIncomingCashVouchersCashVan.this, R.string.conn_closed, Toast.LENGTH_SHORT).show();
                    break;
                case PrinterConstants.Connect.NODEVICE:
                    isConnected = false;
                    Toast.makeText(frmIncomingCashVouchersCashVan.this, R.string.conn_no, Toast.LENGTH_SHORT).show();
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
    private void  GetCustomer()
    {
        conRoyal.UrlRoyal_Query(conRoyal.ConnectionString,"select ID,AName from Customer");

        mQueue = Volley.newRequestQueue(frmIncomingCashVouchersCashVan.this);
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

                    ArrayAdapter<String> adapter2=new ArrayAdapter<String>(frmIncomingCashVouchersCashVan.this,android.R.layout.simple_spinner_item,aa);
                    //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmbCustomer.setAdapter(adapter2);

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
    private void openConn() {
        myOpertion = new BluetoothOperation(this, mHandler);
        myOpertion.btAutoConn(this,  mHandler);

    }
    private void printImage(final Bitmap bitmap){
        if(!isConnected && mPrinter == null) {
            // Global.connectPrinter(frmCashVanPos.this, "frmCashVanPos");
            Global. GetLastPrinter(frmIncomingCashVouchersCashVan.this);
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
    private void connClick(){
        if(isConnected){        //如果已经连接了, 则断开
            myOpertion.close();
            myOpertion = null;
            mPrinter = null;
        }else {
            openConn();
        }
    }
    private void  GetCash()
    {
        conRoyal.UrlRoyal_Query(conRoyal.ConnectionString,"select ID,AName from Cash");

        mQueue = Volley.newRequestQueue(frmIncomingCashVouchersCashVan.this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Query, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<String> aa=new ArrayList<String>();
                    aa.add("--إختر --");
                    boolean flag=false;
                    ListCash.add(new Cls_Cash(0,"--إختر --"));
                    for(int i =0 ; i < response.length() ; i++)
                    {
                        flag=true;
                        JSONObject jsonArray = response.getJSONObject(i);
                        int ID = jsonArray.getInt("ID");
                        String AName = jsonArray.getString("AName");

                        ListCash.add(new Cls_Cash(ID,AName));
                        aa.add(AName);
                    }

                    ArrayAdapter<Cls_Customer> adapter2=new ArrayAdapter<Cls_Customer>(frmIncomingCashVouchersCashVan.this,android.R.layout.simple_spinner_item,ListCustomer);
                    //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    cmbCash.setAdapter(adapter2);

                    if(flag)
                    {
                        flag=false;
                        SelectedDefultCash();
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
    }

    private void SelectedDefultCash()
    {
        for (int i=0;i<ListCash.size();i++)
        {
            if(ListCash.get(i).getID()==Global.LoginCashID)
            {
                cmbCash.setSelection(i);
                CashID1=ListCash.get(i).getID();
                CashName1=ListCash.get(i).getAName();

            }
        }
    }

    private void RefreshForm()
    {
        txtDate.setText(DateFormat.format("dd-MM-yyyy", new java.util.Date()));
        cmbCustomer.setSelection(0);
        txtReceiver.setText(conRoyal.UserName);
        txtPrev.setText("");
        txtCurrent.setText("");
        txtAmount.setText("");
        GetALL();
    }

    private void  GetLoadDataHeader()
    {

        conRoyal.UrlRoyal_SelectMaxVoucherHeaderId(conRoyal.ConnectionString);

        mQueue = Volley.newRequestQueue(frmIncomingCashVouchersCashVan.this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_GetSelectMaxVoucherHeaderId, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {
                    boolean flag=false;
                    for(int i =0 ; i < response.length() ; i++)
                    {
                        flag=true;
                        JSONObject jsonArray = response.getJSONObject(i);
                         MemoID = jsonArray.getInt("MemoID");
                         VoucherManualNumber = jsonArray.getInt("VoucherManualNumber");
                    }
                    if(flag)
                    {
                        flag=false;
                        txtMemoID.setText(String.valueOf(MemoID));
                        txtVoucherManualNumber.setText(String.valueOf(VoucherManualNumber));
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
    }
    private void Print2(String base) {
        // Global.addressBlutooth="CC:78:AB:9D:FE:82";
        if (Global.printerZebra == null) {
            Global.connectPrinter(frmIncomingCashVouchersCashVan.this, "frmCashVanPos");
        }
        else {
            if (Global.connectionZebra.isConnected()) {
                sendTestLabel(base);
                startActivity(new Intent(frmIncomingCashVouchersCashVan.this,frmIncomingCashVouchersCashVan.class));
            }
        }

    }
    private void sendTestLabel(String base) {
        try {

            Bitmap as=Global.StringToBitMap(base);
            Global.printerZebra.printImage(new ZebraImageAndroid(as), 5, 5, as.getWidth()*2, as.getHeight()+1000, false);

            // String cpclConfigLabel = "! 0 200 200 406 1\r\n" + "ON-FEED IGNORE\r\n" + "BOX 20 20 710 380 8\r\n" + "ST TT0003M_.TTF 0 1 137 177 عطا\r\n" + "PRINT\r\n";
        /*    String att="^XA^LRN^CI0^XZ\n" +
                    "\n" +
                    "^XA^CWZ,E:TT0003M_.FNT^FS^XZ\n" +
                    "^XA\n" +
                    "\n" +
                    "^FO10,50^CI28^AZN,50,50^F16^FDZebra Technologies^FS\n" +
                    "^FO10,150^CI28^AZN,50,100^F16^FDUNICODE^FS\n" +
                    "^FO0220,220^CI28^AZN,70,30^F16^FDالإدارة : 0796004442^FS\n" +
                    "^PQ1\n" +
                    "^XZ";






            String tmpHeader =

                  /*  "^XA" +

                            "^POI^PW400^MNN^LL325^LH0,0" + "\r\n" +

                            "^FO50,50" + "\r\n" + "^A0,N,70,70" + "\r\n" + "^FD الإدارة^FS" + "\r\n" +

                            "^FO50,130" + "\r\n" + "^A0,N,35,35" + "\r\n" + "^FDPurchase Confirmation^FS" + "\r\n" +

                            "^FO50,180" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FDCustomer:^FS" + "\r\n" +

                            "^FO225,180" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FDAcme Industries^FS" + "\r\n" +

                            "^FO50,220" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FDDelivery Date:^FS" + "\r\n" +

                            "^FO225,220" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FD%s^FS" + "\r\n" +

                            "^FO50,273" + "\r\n" + "^A0,N,30,30" + "\r\n" + "^FDItem^FS" + "\r\n" +

                            "^FO280,273" + "\r\n" + "^A0,N,25,25" + "\r\n" + "^FDPrice^FS" + "\r\n" +

                            "^FO50,300" + "\r\n" + "^GB350,5,5,B,0^FS" + "^XZ";

            int headerHeight = 325;

            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String dateString = sdf.format(date);

            String header = String.format(tmpHeader, dateString);*/

            // Global.connectionZebra.write(att.getBytes());
            //  Global.connectionZebra.write(header.getBytes());




          /*  byte[] configLabel2 = att.getBytes();
            //PrinterLanguage printerLanguage = Global.printerZebra.getPrinterControlLanguage();

            SGD.SET("device.languages", "CPCL", Global.connectionZebra);

            //ZebraPrinter printer = ZebraPrinterFactory.getInstance(Global.connectionZebra);
            //helper.showLoadingDialog("Printer Ready \nProcessing to Print.");
            Global.connectionZebra.write(configLabel2);
            //Global.printerZebra.sendFileContents("tttttttttttt");*/

            //   SELECTITEM();


            //  Global.printerZebra.printImage("kkk",0, 0, 550, 412, false);

            //ZebraPrinterLinkOs linkOsPrinter = ZebraPrinterFactory.createLinkOsPrinter(printer);

            // PrinterStatus printerStatus = (linkOsPrinter != null) ? linkOsPrinter.getCurrentStatus() : printer.getCurrentStatus();

            // if (printerStatus.isReadyToPrint) {
            //     byte[] configLabel = getConfigLabel();
            //      connection.write(configLabel);
            // setStatus("Sending Data", Color.BLUE);
            //  } else if (printerStatus.isHeadOpen) {
            //    setStatus("Printer Head Open", Color.RED);
            //  } else if (printerStatus.isPaused) {
            //     setStatus("Printer is Paused", Color.RED);
            //   } else if (printerStatus.isPaperOut) {
            //      setStatus("Printer Media Out", Color.RED);
            // }
            DemoSleeper.sleep(1500);
            if (Global.connectionZebra instanceof BluetoothConnection) {
                String friendlyName = ((BluetoothConnection) Global.connectionZebra).getFriendlyName();
                //setStatus(friendlyName, Color.MAGENTA);
                DemoSleeper.sleep(500);
            }

            epicDialog.dismiss();
            startActivity(new Intent(frmIncomingCashVouchersCashVan.this,frmCashVanPos.class));
        } catch(Exception ex){
            //setStatus(e.getMessage(), Color.RED);
        }  finally {

        }
    }
    public  void ShowDataSaved(Context c, String Header, String Details)
    {
        final Dialog epicDialog = new Dialog(c);
        ImageView closebox;
        Button btnCancel,btnOk;
        TextView textHeader,textDetails;

        epicDialog.setContentView(R.layout.boxdailog_yes_or_no);
        epicDialog.setCancelable(false);
        textHeader=(TextView) epicDialog.findViewById(R.id.txtbox_yes_or_no1);
        textDetails=(TextView)epicDialog.findViewById(R.id.txtbox_yes_or_no2);
        closebox=(ImageView)epicDialog.findViewById(R.id.bt_box_yes_or_no);
        btnCancel=(Button)epicDialog.findViewById(R.id.btn_box_yes_or_no_close);
        btnOk=(Button)epicDialog.findViewById(R.id.btn_Ok);

        textHeader.setText(Header);
        textDetails.setText(Details);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetInviceBeforeAndPrint();


            }
        });
        closebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
                startActivity(new Intent(frmIncomingCashVouchersCashVan.this,frmIncomingCashVouchersCashVan.class));
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
                startActivity(new Intent(frmIncomingCashVouchersCashVan.this,frmIncomingCashVouchersCashVan.class));
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

    /*public  void ShowDataSaved(Context c, String Header, String Details)
    {
        final Dialog epicDialog = new Dialog(c);
        ImageView closebox;
        Button btnCancel;
        TextView textHeader,textDetails;

        epicDialog.setContentView(R.layout.from_failog);
        epicDialog.setCancelable(false);
        textHeader=(TextView) epicDialog.findViewById(R.id.txtHeaderDailog);
        textDetails=(TextView)epicDialog.findViewById(R.id.txtBodyDailog);
        closebox=(ImageView)epicDialog.findViewById(R.id.bt_N_close_access);
        btnCancel=(Button)epicDialog.findViewById(R.id.btn_Cancel_Now);

        textHeader.setText(Header);
        textDetails.setText(Details);
        closebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
                startActivity(new Intent(frmIncomingCashVouchersCashVan.this,frmCashVanPos.class));
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
                startActivity(new Intent(frmIncomingCashVouchersCashVan.this,frmCashVanPos.class));
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }*/

    boolean Vald()
    {
        if(Double.parseDouble(txtAmount.getText().toString())<=0)
        {
            Toast.makeText(frmIncomingCashVouchersCashVan.this, getString(R.string.Please_enter_amount), Toast.LENGTH_LONG).show();
            return false;
        }
        if(CustomerID<=0)
        {
            Toast.makeText(frmIncomingCashVouchersCashVan.this, getString(R.string.Please_Select_Customer), Toast.LENGTH_LONG).show();
            return false;
        }
        if(Global.LoginCashID<=0)
        {
            Toast.makeText(frmIncomingCashVouchersCashVan.this, getString(R.string.Please_Select_Cash), Toast.LENGTH_LONG).show();
            return false;
        }
        return  true;
    }
    private void Save()
    {

        if(Vald()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            String VoucherDate=txtDate.getText().toString();
            int BranchID=Global.LoginBranchID;
            int CostCenterID=0;
            int RevenuCenterID=0;
            int ProfitCenterID=0;
            double Amount=Double.parseDouble(Global.convertToEnglish(this.txtAmount.getText().toString()));
            String Notes=txtNotes.getText().toString();
            int MemoID= Integer.parseInt(Global.convertToEnglish(txtMemoID.getText().toString()));
            int LoginUserID = conRoyal.IDUser;
            int VoucherManualNumber=Integer.parseInt(Global.convertToEnglish(txtVoucherManualNumber.getText().toString()));
            String Receiver=txtReceiver.getText().toString();
            int CustomerId=CustomerID;
            int CashID=Global.LoginCashID;

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(conRoyal.ConIpRoyal2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
            post_CashVoucher pp = new post_CashVoucher(conRoyal.ConnectionString, VoucherDate,BranchID,CostCenterID,RevenuCenterID,ProfitCenterID, Amount,Notes, MemoID, LoginUserID, VoucherManualNumber, Receiver, CustomerId,CashID,-100);
            Call<post_CashVoucher> call = jsonPlaceHolderApi.InsertOrder(pp);
            call.enqueue(new Callback<post_CashVoucher>() {
                @Override
                public void onResponse(Call<post_CashVoucher> call, retrofit2.Response<post_CashVoucher> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("true")) {
                            progressDialog.dismiss();
                            ShowDataSaved(frmIncomingCashVouchersCashVan.this, getString(R.string.txtCorrectoperation), getString(R.string.txtmigratedsystem));
                        } else {
                            progressDialog.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<post_CashVoucher> call, Throwable t) {

                    Global.ShowDataSavedToSqlLitePopup(frmIncomingCashVouchersCashVan.this);

                }


            });
        }
    }
}
