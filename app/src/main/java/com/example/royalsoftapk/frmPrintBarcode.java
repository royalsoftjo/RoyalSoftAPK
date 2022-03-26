package com.example.royalsoftapk;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bxl.config.editor.BXLConfigLoader;
import com.example.tscdll.TSCActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.woosim.printer.WoosimCmd;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.comm.ConnectionException;
import com.zebra.sdk.graphics.internal.ZebraImageAndroid;
import com.zebra.sdk.printer.PrinterLanguage;
import com.zebra.sdk.printer.SGD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;

import jpos.JposException;
import jpos.POSPrinterConst;
public class frmPrintBarcode extends AppCompatActivity implements View.OnClickListener,
        AdapterView.OnItemClickListener, SeekBar.OnSeekBarChangeListener, RadioGroup.OnCheckedChangeListener {
    ProgressDialog progressDialog;
    private static final String TAG = "frmPrintBarcode";
    private static final int REQUEST_CODE_BLUETOOTH = 1;
    private static final int REQUEST_CODE_ACTION_PICK = 2;

    private static final String DEVICE_ADDRESS_START = " (";
    private static final String DEVICE_ADDRESS_END = ")";

    private final ArrayList<CharSequence> bondedDevices = new ArrayList<>();
    private ArrayAdapter<CharSequence> arrayAdapter;

    private TextView pathTextView;
    private TextView progressTextView;
    private RadioGroup openRadioGroup;
    private Button openFromDeviceStorageButton;
    private BXLConfigLoader bxlConfigLoader;

    private String logicalName;
    private int brightness = 50;
    private RequestQueue mQueue;
    String Logo;
    String AName;
    String SalesPrice;
    String MainBarcode;
    String LastPurchasePrice;
    TextView txtAName,txtPrice,txtStatus,txtPurchasePrice;
    EditText txtBarcode,txtBarcode2;
    private TextWatcher textWatcher=null;
    public EditText input;
    Button btnBlo;
    Button TSC;
    Button Zibra;
    ImageButton btnCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_print_barcode);
        WoosimCmd.PM_printData();
       // sendData(WoosimCmd.queryStatus());
        btnCamera=findViewById(R.id.btnCamera2);
        Zibra=findViewById(R.id.Zibra2);
        TSC=findViewById(R.id.buttonPrintTSC);
        txtAName = findViewById(R.id.frmPurchasePrice_ItemName);
        txtPrice = findViewById(R.id.frmPurchasePrice_Price);
        txtBarcode = findViewById(R.id.frmPurchasePrice_textBarcode);
        txtBarcode2 = findViewById(R.id.frmPrintBarcode_textBarcode2);
        txtStatus = findViewById(R.id.StatusBluetooth);
        txtPurchasePrice=findViewById(R.id.PurchasePrice);
        txtBarcode.requestFocus();
        btnBlo=findViewById(R.id.buttonBlutoothPrinter);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r();
            }
        });
        btnBlo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final Context context = frmPrintBarcode.this.getBaseContext();
                Global.connectPrinter(frmPrintBarcode.this, "frmPrintBarcode");
                /*Intent intent = new Intent(frmPrintBarcode.this , frmDeviceBlooth.class);
                intent.putExtra("frm","frmPrintBarcode");
                startActivity(intent);*/
                //frmPrintBarcode.startActivity(new Intent(frmPrintBarcode.this,ListBluooth.class));
            }
        });
        Zibra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doConnectionTest();
            }
        });


        TSC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    TSC.setEnabled(false);
                    if(Logo==null)
                    {
                        Toast.makeText(frmPrintBarcode.this,"يرجى تحديد الباركود",Toast.LENGTH_LONG).show();
                        return;
                    }
                    dd();
                    TSC.setEnabled(true);
                }
                catch (Exception ex)
                {
                    throw  ex;
                }

            }
        });
        onActivityAction();
        if(Global.StatusBlutooth)
        {
            txtStatus.setText("Connected");
            txtStatus.setTextColor(Color.GREEN);
        }
        else
        {
            txtStatus.setText("Not Connected");
            txtStatus.setTextColor(Color.RED);
        }


        //----------------------------------Event Text Barcode-------------------------------------------------------------
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String Barcode = txtBarcode.getText().toString();

                if (!Barcode.equals("")) {
                    //SelectItemPrice(Barcode);
                    if(Barcode.charAt(Barcode.length()-1)=='\n') {
                        Barcode=Barcode.substring(0,Barcode.length()-1);
                        SELECTITEM(Barcode);
                    }
                }
            }
        };
        txtBarcode.addTextChangedListener(textWatcher);

        //----------------------------------Event Text Barcode-------------------------------------------------------------


        txtPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(true==false) {
                   //-----------------------------Button Dailog--------------------------------------------------------
                   AlertDialog.Builder builder = new AlertDialog.Builder(frmPrintBarcode.this);
                   builder.setTitle("إدخال سعر المادة");
                   builder.setIcon(null);
                   builder.setMessage(" يرجى إدخال سعر الصنف الجديد   -->    ");

                   input = new EditText(frmPrintBarcode.this);
                   input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                   builder.setView(input);
                   builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           String SalesPrice = input.getText().toString();
                           if (SalesPrice.equals("")) {
                               Toast.makeText(frmPrintBarcode.this, "يرجى إدخال السعر الجديد للمادة", Toast.LENGTH_LONG).show();
                               return;
                           } else {
                               UpdateSalesPrice(SalesPrice, MainBarcode);
                               MainBarcode = "";
                               AName = "";
                               SalesPrice = "";
                               txtBarcode.setText("");
                               txtBarcode2.setText("");
                               txtAName.setText("");
                               txtPrice.setText("");
                               txtPurchasePrice.setText("");

                           }
                           //---------------------------------------------------------------------------------------


                       }
                   });

                   builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {
                           dialog.dismiss();
                           input.setText("");
                       }
                   });

                   final AlertDialog add = builder.create();
                   add.show();
                   //--------------------------------------Button Dailog----------------------------------------------
               }
            }

        });
    }
    void r()
    {

        IntentIntegrator intentIntegrator=new IntentIntegrator(frmPrintBarcode.this);
        intentIntegrator.setPrompt("For flash use volume up key");
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setCaptureActivity(Capture.class);
        intentIntegrator.initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult.getContents()!=null)
        {
            txtBarcode.setText(intentResult.getContents()+"\n");
           /* final android.app.AlertDialog.Builder builder =new android.app.AlertDialog.Builder(IncomeVoucherDetails.this);
            builder.setTitle("Result");
            builder.setMessage(intentResult.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();*/

        }
        else
        {
            Toast.makeText(getApplicationContext(),"OOPS",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {



            case R.id.buttonPrint:
                if(Logo==null)
                {
                    Toast.makeText(frmPrintBarcode.this,"يرجى تحديد الباركود",Toast.LENGTH_LONG).show();
                    return;
                }
                print();
                //dd();
                break;

            case R.id.btBarcodeSearch:
                String Barcode = txtBarcode2.getText().toString();

                if (Barcode != "") {
                    //SelectItemPrice(Barcode);
                    SELECTITEM(Barcode);
                }

                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }
  /*  @Override
    protected void onActivityResult(final int requestCode2, final int resultCode2, final Intent data2) {
        super.onActivityResult(requestCode2, resultCode2, data2);
        switch (requestCode2) {
            case REQUEST_CODE_BLUETOOTH:
                //   setBondedDevices();
                break;

            case REQUEST_CODE_ACTION_PICK:
                onPickSomeAction(data2);
                break;
        }
    }*/



    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void onActivityAction() {
        pathTextView = findViewById(R.id.textViewPath);
        progressTextView = findViewById(R.id.textViewProgress);

        openRadioGroup = findViewById(R.id.radioGroupOpen);
        openRadioGroup.setOnCheckedChangeListener(this);
        findViewById(R.id.buttonPrint).setOnClickListener(this);
        //findViewById(R.id.buttonBlutoothPrinter).setOnClickListener(frmPrintBarcode.this);
        findViewById(R.id.btBarcodeSearch).setOnClickListener(this);
        openFromDeviceStorageButton = findViewById(R.id.buttonOpenFromDeviceStorage);
        openFromDeviceStorageButton.setOnClickListener(this);


    }
    @SuppressLint("ResourceType")
    private void print()
    {
        Global.GetLastPrinter(frmPrintBarcode.this);
        switch(Global.TypePrinterConnected) {
            case 1:
                doConnectionTest();
                break;
            case 2:
                InputStream is = null;
                try {
                    final ByteBuffer buffer = buildBuffer();
                    //   switch (openRadioGroup.getCheckedRadioButtonId()) {
                    //     case R.id.radioDeviceStorage:
                    //    posPrinter.printBitmap(buffer.getInt(0), pathTextView.getText().toString(),
                    //  posPrinter.getRecLineWidth(), POSPrinterConst.PTR_BM_LEFT);
                    //  break;

                    //  case R.id.radioProjectResources:
                    is = getResources().openRawResource(R.drawable.bixolon_logo_black_500);
                    final Bitmap bitmap = BitmapFactory.decodeStream(is);
                    Global.posPrinter.printBitmap(buffer.getInt(0), StringToBitMap(Logo),
                            Global.posPrinter.getRecLineWidth(), POSPrinterConst.PTR_BM_LEFT);
                    //   ii.setImageBitmap(bb);

                    //   break;

                } catch (final JposException e) {
                    Log.e(TAG, "Error printing.", e);
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                } finally {
                    closeInputStream(is);
                }
                break;
            case 3:
                if(Global.TscDll==null) {
                    Global.TscDll = new TSCActivity();
                    Global.TscDll.openport(Global.addressBlutooth);
                }

                Global.TscDll.sendbitmap(0,0,StringToBitMap(Logo));
                //Toast.makeText(frmPrintBarcode.this, "جاري الطباعة 1", Toast.LENGTH_SHORT).show();
                Global.TscDll.printlabel(1, 1);
                //Global.TscDll.closeport();
                break;
        }
       /* if(Global.addressBlutooth.equals("C4:64:E3:11:BC:99")||Global.addressBlutooth.equals("00:81:F9:04:CD:3E")||Global.addressBlutooth.equals("58:93:D8:4D:BC:C0"))
        {

        }
        else {

        }*/
    }

    private ByteBuffer buildBuffer() {
        final ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.put((byte) POSPrinterConst.PTR_S_RECEIPT);
        buffer.put((byte) brightness);
        int compress = 1;
        buffer.put((byte) compress);
        buffer.put((byte) 0x00);
        return buffer;
    }

    private void closeInputStream(final InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (final IOException e) {
                Log.e(TAG, "Error closing input stream.", e);
            }
        }
    }
    private void  dd()
    {

        try {
            //Toast.makeText(frmPrintBarcode.this, "1", Toast.LENGTH_SHORT).show();

            Toast.makeText(frmPrintBarcode.this, "تم الإتصال بنجاح", Toast.LENGTH_SHORT).show();
            // TscDll.downloadpcx("UL.PCX");
            //TscDll.downloadbmp("Triangle.bmp");
            //TscDll.downloadttf("ARIAL.TTF");
            // TscDll.setup(70, 33, 4, 4, 0, 0, 0);
            // TscDll.clearbuffer();
            //TscDll.sendcommand("SET TEAR ON\n");
            //TscDll.sendcommand("SET COUNTER @1 1\n");
            // TscDll.sendcommand("@1 = \"0001\"\n");
            //  TscDll.sendcommand("TEXT 100,300,\"3\",0,1,1,@1\n");
            // TscDll.sendcommand("PUTPCX 100,300,\"UL.PCX\"\n");
            // TscDll.sendcommand("PUTBMP 100,520,\"Triangle.bmp\"\n");
            // TscDll.sendcommand("TEXT 100,760,\"ARIAL.TTF\",0,15,15,\"THIS IS ARIAL FONT\"\n");
            // TscDll.barcode(100, 100, "128", 100, 1, 0, 3, 3, "12346");
            //TscDll.printerfont(100, 250, "3", 0, 1, 1, "987654321");
            // String status = TscDll.printerstatus();
            Global.TscDll.sendbitmap(0,0,StringToBitMap(Logo));
            //Toast.makeText(frmPrintBarcode.this, "جاري الطباعة 1", Toast.LENGTH_SHORT).show();
            Global.TscDll.printlabel(1, 1);
            //Toast.makeText(frmPrintBarcode.this, "جاري الطباعة 2", Toast.LENGTH_SHORT).show();
            // TscDll.closeport(5000);
        }
        catch (Exception ex)
        {
            Toast.makeText(frmPrintBarcode.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    private byte[] getConfigLabel() {
        byte[] configLabel = null;
        try {
            PrinterLanguage printerLanguage = Global.printerZebra.getPrinterControlLanguage();
            SGD.SET("device.languages", "zpl", Global.connectionZebra);

            if (printerLanguage == PrinterLanguage.ZPL) {
                configLabel = "^XA^FO17,16^GB379,371,8^FS^FT65,255^A0N,135,134^FDTEST^FS^XZ".getBytes();
            } else if (printerLanguage == PrinterLanguage.CPCL) {
                String cpclConfigLabel = "! 0 200 200 406 1\r\n" + "ON-FEED IGNORE\r\n" + "BOX 20 20 380 380 8\r\n" + "T 0 6 137 177 TEST\r\n" + "PRINT\r\n";
                configLabel = cpclConfigLabel.getBytes();
            }
        } catch (ConnectionException e) {

        }
        return configLabel;
    }
    private void sendTestLabel() {
        try {
            //byte[] configLabel2 = getConfigLabel();
            //PrinterLanguage printerLanguage = Global.printerZebra.getPrinterControlLanguage();
            SGD.SET("device.languages", "CPCL", Global.connectionZebra);

            //ZebraPrinter printer = ZebraPrinterFactory.getInstance(Global.connectionZebra);
            //helper.showLoadingDialog("Printer Ready \nProcessing to Print.");
            //Global.connectionZebra.write(configLabel2);
            Bitmap as=StringToBitMap(Logo);
           Global.printerZebra.printImage(new ZebraImageAndroid(as), 0, 0, 700, 400, false);

            //ZebraPrinterLinkOs linkOsPrinter = ZebraPrinterFactory.createLinkOsPrinter(printer);

           // PrinterStatus printerStatus = (linkOsPrinter != null) ? linkOsPrinter.getCurrentStatus() : printer.getCurrentStatus();

           // if (printerStatus.isReadyToPrint) {
           //     byte[] configLabel = getConfigLabel();
          //      connection.write(configLabel);
                setStatus("Sending Data", Color.BLUE);
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
                setStatus(friendlyName, Color.MAGENTA);
                DemoSleeper.sleep(500);
            }
        } catch (ConnectionException e) {
            setStatus(e.getMessage(), Color.RED);
        } finally {

        }
    }
    private void doConnectionTest() {

        if (Global.connectionZebra != null) {
            sendTestLabel();
        } else {
           // disconnect();
        }
    }
    private void setStatus(final String statusMessage, final int color) {
        runOnUiThread(new Runnable() {
            public void run() {
                txtStatus.setBackgroundColor(color);
                txtStatus.setText(statusMessage);
            }
        });
        DemoSleeper.sleep(1000);
    }
    private void SELECTITEM(String Barcode)
    {
        progressDialog= new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

       conRoyal.UrlRoyal_repshelftag(conRoyal.ConnectionString,Barcode);
        mQueue = Volley.newRequestQueue(this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_repshelftag, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {


                try {
                    JSONObject jsonArray = response.getJSONObject(0);
                    progressDialog.dismiss();
                    Logo = jsonArray.getString("Logo");
                    AName=jsonArray.getString("AName");
                    MainBarcode = jsonArray.getString("MainBarcode");
                    SalesPrice = jsonArray.getString("salesPrice");
                    LastPurchasePrice = jsonArray.getString("LastPurchasePrice");

                    DecimalFormat DF = new DecimalFormat("######0.000");

                    txtAName.setText(AName);
                    txtPrice.setText(DF.format(Double.valueOf(SalesPrice)));
                    if(conRoyal.Access_Home_ID==1) {
                        txtPurchasePrice.setText(DF.format(Double.valueOf(LastPurchasePrice)));
                    }
                        txtBarcode.setText("");
                        txtBarcode2.setText("");
                    

                }
                catch (Exception ex)
                {
                    Toast.makeText(frmPrintBarcode.this,"المادة غير معرفة",Toast.LENGTH_LONG).show();
                    txtBarcode.setText("");
                    txtBarcode2.setText("");
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(frmPrintBarcode.this,error.getMessage(),Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });

        int socketTimeout = 60000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        mQueue.add(request);
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
    public byte[] StringTOByte(String encodedString){
        try{
            byte [] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
          //  Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return encodeByte;
        }
        catch(Exception e){
            e.getMessage();
            return null;
        }
    }
    private void onPickSomeAction(final Intent data) {
        if (data != null) {
            final Uri uri = data.getData();
            if (uri != null) {
                final ContentResolver cr = getContentResolver();
                final Cursor c = cr.query(uri, new String[]{ MediaStore.Images.Media.DATA },
                        null, null, null);
                if (c == null || c.getCount() == 0) {
                    return;
                }

                c.moveToFirst();
                final int columnIndex = c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                final String text = c.getString(columnIndex);
                c.close();

                pathTextView.setText(text);
            }
        }
    }


    private void UpdateSalesPrice(String SalesPrice,String MainBarcode)
    {
       conRoyal.UrlRoyal_UpdateSalesPrice(conRoyal.ConnectionString,SalesPrice,MainBarcode);
        mQueue = Volley.newRequestQueue(frmPrintBarcode.this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_UpdateSalesPrice, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {

                    JSONObject jsonArray = response.getJSONObject(0);

                    String  SalesPrice = jsonArray.getString("SalesPrice");
                    Toast.makeText(frmPrintBarcode.this,"تم حفظ السعر الجديد بنجاح",Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    Toast.makeText(frmPrintBarcode.this,"فشلة عملية الحفظ يرجى إعادة المحاولة",Toast.LENGTH_LONG).show();
                    txtBarcode.setText("");
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
