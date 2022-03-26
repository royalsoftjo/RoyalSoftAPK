package com.example.royalsoftapk;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.InputType;
import android.text.format.DateFormat;
import android.util.Base64;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.print.sdk.PrinterConstants;
import com.android.print.sdk.PrinterInstance;
import com.android.print.sdk.bluetooth.BluetoothPort;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.royalsoftapk.bluetooth.BluetoothOperation;
import com.example.royalsoftapk.permission.EasyPermission;
import com.example.tscdll.TSCActivity;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.woosim.printer.WoosimCmd;
import com.woosim.printer.WoosimImage;
import com.woosim.printer.WoosimService;
import com.zebra.sdk.comm.BluetoothConnection;
import com.zebra.sdk.graphics.internal.ZebraImageAndroid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class frmCashVanPos extends AppCompatActivity  implements EasyPermission.PermissionCallback{
    private RequestQueue mQueue;
    LinearLayout LineCategory;
    ProgressDialog progressDialog;
    Dialog epicDialog;
    LinearLayout LineItems;
    SwipeMenuListView listitems;
    ImageView closebox, btnNotes, btnmenu;
    Button btnCancel, btnSaveOrder;
    LinearLayout LineTypeBasket;
    TextView TotalOrder, TotalQty, TotalTaxAmount, TotalBeforeTax,TotalBalance;
    ImageView btn_signaturepad,addcustomer;
    //------------ Values ----------------------------//
    int spinnerSettelmentWayID;
    String spinnerSettelmentWayName;
    int spinnerInvoiceTaxTypeID=1;
    String spinnerInvoiceTaxTypeName;
    int spinnerCashID;
    String spinnerCashName;
    int spinnerCustomerID;
    int spinnerCustomerSalesManID;
    String spinnerCustomerName;
    String Notes = "";
    Bitmap bitPohto = null;
    Bitmap bitsignature = null;
    String Logo = "";
    String InvoiceNo = "1";
    int routeDetailsHomeID;
    double MaxCreditLimit=0;
    private static boolean isConnected;                 //是否已经建立了连接
    protected static IPrinterOpertion myOpertion;
    private PrinterInstance mPrinter;

    public static final int CONNECT_DEVICE = 1;             //选择设备
    public static final int ENABLE_BT = 2;                  //启动蓝牙
    public static final int REQUEST_SELECT_FILE = 3;        //选择文件
    public static final int REQUEST_PERMISSION = 4;         //读写权限
    private ProgressDialog dialog;

    BluetoothAdapter mBluetoothAdapter = null;
    BluetoothPrintService mPrintService = null;
    private WoosimService mWoosim = null;
    //------------ Values ----------------------------//

    //--------------- List Spinner --------------------//
    List<Cls_SettelmentWay> ListSettelmentWay = new ArrayList<>();
    List<Cls_InvoiceTaxType> ListInvoiceTaxType = new ArrayList<>();
    List<Cls_Cash> ListCash = new ArrayList<>();
    List<ClsOrderSalesSelected> ListItems = new ArrayList<>();
    List<Cls_Customer> ListCustomer = new ArrayList<>();
    List<Cls_Employee> ListSalesMan = new ArrayList<>();
    //--------------- List Spinner --------------------//

    //------------ Spinner ------------------------- //
    Spinner spinnerSettelmentWay;
    Spinner spinnerInvoiceTaxType;
    Spinner spinnerSalesman;
    Spinner spinnerCustomer;
    Spinner spinnerCash;
    Spinner spinnerItem;
    //------------ Spinner ------------------------- //
    ArrayList<ClsOrderSalesSelected> listNew = new ArrayList<ClsOrderSalesSelected>();
    Adapter_CashVanPos_Selected Adapter;


    double x;
    double y;
    int TransType=33;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
           Toast.makeText(frmCashVanPos.this,"Dawnn",Toast.LENGTH_SHORT).show();
            spinnerInvoiceTaxTypeID=2;
        }
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            Toast.makeText(frmCashVanPos.this,"UPPP",Toast.LENGTH_SHORT).show();
            spinnerInvoiceTaxTypeID=1;
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        epicDialog.setContentView(R.layout.box_dialog_new_income);
        closebox = (ImageView) epicDialog.findViewById(R.id.bt_N_close23);
        btnCancel = (Button) epicDialog.findViewById(R.id.btn_exit_Now3);
        TextView txt =epicDialog.findViewById(R.id.txtexitshow);
        txt.setText(getString(R.string.Mexit));


        closebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });

        btnCancel.setText(getString(R.string.yes));
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TransType==15)
                {
                    Intent intent=new Intent(frmCashVanPos.this,frmCashVanPos.class);
                    intent.putExtra("TransType",15);
                    intent.putExtra("CustomerID",-2);
                    intent.putExtra("routeDetailsID",0);
                    startActivity(intent);
                }
                else {
                    startActivity(new Intent(frmCashVanPos.this, CashVanPosDashBoard.class));
                }
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_frm_cash_van_pos);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            spinnerSettelmentWay = (Spinner) findViewById(R.id.idcmbInvoiceSettelmentWay);
            spinnerInvoiceTaxType = (Spinner) findViewById(R.id.idCmbInvoiceTaxType);
            spinnerSalesman = (Spinner) findViewById(R.id.idcmbSalesman);
            spinnerCustomer = (Spinner) findViewById(R.id.idtxtCustomer);
            spinnerCash = (Spinner) findViewById(R.id.sp_Cash);
            spinnerItem=(Spinner) findViewById(R.id.spinnerItem);
            listitems = findViewById(R.id.listCashVanPos);
            TotalOrder = findViewById(R.id.txtTotalCashVan);
            btnNotes = findViewById(R.id.btnNotes);
            btnmenu = findViewById(R.id.btnmenu);
            TotalBeforeTax = findViewById(R.id.txtTotalBeforeTax);
            TotalTaxAmount = findViewById(R.id.txtTotalTaxAmount);
            TotalQty = findViewById(R.id.txtTotalQty);
            btnSaveOrder = findViewById(R.id.btnSaveOrder);
            TotalBalance = findViewById(R.id.totalBalance);
            addcustomer = findViewById(R.id.addcustomer);

            initDialog();
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mPrintService == null)  setupPrint();
            Bundle extras = getIntent().getExtras();
            spinnerCustomerID=Integer.parseInt(extras.get("CustomerID").toString());
            routeDetailsHomeID=Integer.parseInt(extras.get("routeDetailsID").toString());
            TransType=Integer.parseInt(extras.get("TransType").toString());
            Adapter = new Adapter_CashVanPos_Selected(frmCashVanPos.this, listNew, this,TransType);
            if(spinnerCustomerID==-2)
            {
                spinnerCustomer.setEnabled(true);
            }
            else
            {
                spinnerCustomer.setEnabled(false);
            }

            if(TransType==15)
            {
                GetdataCategory();
            }

            addcustomer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DilogAddCustomer(frmCashVanPos.this);

                }
            });


            if(Global.IsHasBasket)
            {
                TextView vItemName = findViewById(R.id.vItemName);
                vItemName.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams loparamsItemName = (LinearLayout.LayoutParams) vItemName.getLayoutParams();
                loparamsItemName.weight=3;
                vItemName.setLayoutParams(loparamsItemName);


                TextView vPrice = findViewById(R.id.vPrice);
                vPrice.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams loparamsPrice = (LinearLayout.LayoutParams) vPrice.getLayoutParams();
                loparamsPrice.weight=2;
                vPrice.setLayoutParams(loparamsPrice);


                TextView vTotal = findViewById(R.id.vTotal);
                vTotal.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams loparamsTotal = (LinearLayout.LayoutParams) vTotal.getLayoutParams();
                loparamsTotal.weight=2;
                vTotal.setLayoutParams(loparamsTotal);

                TextView vQty = findViewById(R.id.vQty);
                vQty.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams loparamsQty = (LinearLayout.LayoutParams) vQty.getLayoutParams();
                loparamsQty.weight=1;
                vQty.setLayoutParams(loparamsQty);


                TextView vBased_quantity = findViewById(R.id.vBased_quantity);
                vBased_quantity.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams loparamsBased_quantity = (LinearLayout.LayoutParams) vBased_quantity.getLayoutParams();
                loparamsBased_quantity.weight=2;
                vBased_quantity.setLayoutParams(loparamsBased_quantity);

                TextView vnumber_baskets = findViewById(R.id.vnumber_baskets);
                vnumber_baskets.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams loparamsnumber_baskets = (LinearLayout.LayoutParams) vnumber_baskets.getLayoutParams();
                loparamsnumber_baskets.weight=2;
                vnumber_baskets.setLayoutParams(loparamsnumber_baskets);


                TextView vType_panier = findViewById(R.id.vType_panier);
                vType_panier.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams loparamsType_panier = (LinearLayout.LayoutParams) vType_panier.getLayoutParams();
                loparamsType_panier.weight=2;
                vType_panier.setLayoutParams(loparamsType_panier);

                TextView vEmpty_quantity = findViewById(R.id.vEmpty_quantity);
                vEmpty_quantity.setVisibility(View.VISIBLE);
                LinearLayout.LayoutParams loparamsEmpty_quantity = (LinearLayout.LayoutParams) vEmpty_quantity.getLayoutParams();
                loparamsEmpty_quantity.weight=2;
                vEmpty_quantity.setLayoutParams(loparamsEmpty_quantity);
            }





            btnSaveOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Global.IsShowISPaid==true && spinnerSettelmentWayID!=1)
                    {
                        ShowYouPayment(frmCashVanPos.this,"تنبيه !!","هل يوجد دفعة من العميل ؟");
                    }
                    else
                    {
                        Save("0");
                    }
                }
            });

            btnmenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnmenu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            epicDialog.setContentView(R.layout.box_button);
                            closebox = (ImageView) epicDialog.findViewById(R.id.bt_N_closeCashVan);
                            LinearLayout btn_Pohto = (LinearLayout) epicDialog.findViewById(R.id.btn_Pohto);
                            LinearLayout btn_IncomingCashVouchers = (LinearLayout) epicDialog.findViewById(R.id.btn_IncomingCashVouchers);
                            LinearLayout btn_IncommingChequesCashVan = (LinearLayout) epicDialog.findViewById(R.id.idIncommingChequesCashVan);
                            LinearLayout btn_PrinterSetting = (LinearLayout) epicDialog.findViewById(R.id.btnPrintSettings);
                            LinearLayout btnExit = (LinearLayout) epicDialog.findViewById(R.id.btnExit);
                            LinearLayout Print_Previous_Invoice = (LinearLayout) epicDialog.findViewById(R.id.Print_Previous_Invoice);

                            btn_IncomingCashVouchers.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    epicDialog.dismiss();
                                    startActivity(new Intent(frmCashVanPos.this, frmIncomingCashVouchersCashVan.class));
                                }
                            });
                            btn_Pohto.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    SetPohto();
                                }
                            });

                            closebox.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    epicDialog.dismiss();
                                }
                            });

                            btn_IncommingChequesCashVan.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    epicDialog.dismiss();
                                    startActivity(new Intent(frmCashVanPos.this, frmIncommingChequesCashVan.class));
                                }
                            });
                            btn_PrinterSetting.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(frmCashVanPos.this, frmSettingDeviceBltooth.class);
                                    startActivity(intent);
                                    // Global.connectPrinter(frmCashVanPos.this, "frmCashVanPos");
                                }
                            });

                            btnExit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(new Intent(frmCashVanPos.this, Login.class));
                                }
                            });

                            Print_Previous_Invoice.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                   // if(!isConnected && mPrinter == null) {
                                        // Global.connectPrinter(frmCashVanPos.this, "frmCashVanPos");
                                //        Global.GetLastPrinter(frmCashVanPos.this);
                                //        if (!Global.addressBlutooth.equals("")) {
                                //            connClick();
                               //         }
                               //     }
                                //    else {
                               //         GetInviceBeforeAndPrint();
                                 //   }
                                    //connectDeviceWoosim();
                                    Global.GetLastPrinter(frmCashVanPos.this);
                                    if(Global.TypePrinterConnected==5){
                                        connectDeviceWoosim();
                                    }
                                    try {
                                        Thread.sleep(3000);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    GetInviceBeforeAndPrint();
                                }
                            });
                            epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            epicDialog.show();

                        }
                    });
                }
            });
            btn_signaturepad = findViewById(R.id.btn_signaturepad);
            btn_signaturepad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder alert = new AlertDialog.Builder(frmCashVanPos.this);
                    View mView = getLayoutInflater().inflate(R.layout.custom_signaturepad, null);
                    final TextView txtView = mView.findViewById(R.id.noteText1);
                    final SignaturePad signaturePad = mView.findViewById(R.id.signature_pad);
                    Button btn_Cancel = mView.findViewById(R.id.idCancel);
                    Button btn_Ok = mView.findViewById(R.id.idOk);
                    Button btn_New = mView.findViewById(R.id.idNew);
                    alert.setView(mView);
                    final AlertDialog alertDialog = alert.create();
                    txtView.setText(getResources().getString(R.string.txtNote));
                    if (bitsignature != null) {
                        signaturePad.setSignatureBitmap(bitsignature);
                    }
                    btn_New.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            signaturePad.clear();
                        }
                    });
                    btn_Cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                    btn_Ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bitmap bitmap = signaturePad.getSignatureBitmap();
                            bitsignature = bitmap;
                            alertDialog.dismiss();
                        }
                    });
                    alertDialog.setCanceledOnTouchOutside(false);

                    alertDialog.show();

                }
            });
            btnNotes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AlertDialog.Builder alert = new AlertDialog.Builder(frmCashVanPos.this);
                    View mView = getLayoutInflater().inflate(R.layout.custom_dailog_note, null);
                    final EditText txt = mView.findViewById(R.id.txtinputNote);
                    final TextView txtView = mView.findViewById(R.id.noteText1);
                    Button btn_Cancel = mView.findViewById(R.id.idCancel);
                    Button btn_Ok = mView.findViewById(R.id.idOk);
                    alert.setView(mView);
                    final AlertDialog alertDialog = alert.create();


                    txtView.setText(getResources().getString(R.string.txtNote));
                    txt.setHint(getResources().getString(R.string.txtNote22));
                    txt.setInputType(InputType.TYPE_CLASS_TEXT);
                    txt.setText(Notes);


                    btn_Cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });

                    btn_Ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String tt = txt.getText().toString().trim();
                            if (!tt.equals("")) {
                                Notes = tt;
                            }
                            alertDialog.dismiss();
                            //sumTotalOrder();
                        }
                    });
                    alertDialog.setCanceledOnTouchOutside(false);

                    alertDialog.show();
                }
            });
            spinnerCash.setEnabled(false);
            spinnerSalesman.setEnabled(false);
            GetAll();
            epicDialog = new Dialog(this);
            listitems.setAdapter(Adapter);
            //-------------------------------Cls_Supplier_Spinner-----------------------------------------------------//
            //da.SetAdapterSupplier(this,spinnerSupplier);
            spinnerSettelmentWay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        spinnerSettelmentWayID = ListSettelmentWay.get(position).getID();
                        spinnerSettelmentWayName = ListSettelmentWay.get(position).getAName();
                        if (spinnerSettelmentWayID == 1) {
                            spinnerCash.setEnabled(true);
                            spinnerCash.setSelection(1);
                        } else {
                            spinnerCash.setEnabled(false);
                            spinnerCash.setSelection(0);
                        }
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            spinnerInvoiceTaxType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        spinnerInvoiceTaxTypeID = ListInvoiceTaxType.get(position).getID();
                        spinnerInvoiceTaxTypeName = ListInvoiceTaxType.get(position).getAName();
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            spinnerCash.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {
                        spinnerCashID = ListCash.get(position).getID();
                        spinnerCashName = ListCash.get(position).getAName();
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            spinnerCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {

                        spinnerCustomerID = ListCustomer.get(position).getID();
                       // Toast.makeText(frmCashVanPos.this,String.valueOf(spinnerCustomerID) ,Toast.LENGTH_SHORT).show();
                        spinnerCustomerName = ListCustomer.get(position).getAName();
                        spinnerCustomerSalesManID = ListCustomer.get(position).getSalesManID();
                        if (spinnerCustomerID != 0) {
                            for (int i = 0; i < ListSalesMan.size(); i++) {
                                if (spinnerCustomerSalesManID == ListSalesMan.get(i).getID()) {
                                    spinnerSalesman.setSelection(i);

                                }
                            }
                            GetdataCategory();
                            selecttotalBalanceByCustomerID();
                        }
                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
                    }

                    //GetdataCategory();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });



            spinnerItem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    try {

                            if(ListItems.get(position).getID()!="0")
                            {
                                ClsOrderSalesSelected getdata = ListItems.get(position);
                                ShowBoxInputNote(getdata);
                                Adapter.notifyDataSetChanged();
                                sumTotalOrder();
                                spinnerItem.setSelection(0);
                            }


                    }
                    catch (Exception ex)
                    {
                        Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

//-------------------------------Cls_Supplier_Spinner-----------------------------------------------------//

            //CustomerSelect();
        }
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    private void  SaveCustomers(final Context C, String AName, String Tel1, final Dialog dialog)
    {
        final RequestQueue mQueue;
        final ProgressDialog  progressDialog = new ProgressDialog(C);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        conRoyal.UrlRoyal_InsertCustomerERP(conRoyal.ConnectionString,AName,Tel1,conRoyal.IDUser);

        mQueue = Volley.newRequestQueue(C);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_InsertCustomerERP, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonArray = response.getJSONObject(0);
                    boolean Status=jsonArray.optBoolean("IsSave",false);
                    if(Status)
                    {
                        Toast.makeText(C,"تم إضافة عميل بنجاح",Toast.LENGTH_SHORT).show();
                        dialog.dismiss();

                        GetCustomer();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        int socketTimeout = 30000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        mQueue.add(request);
    }
    private   void DilogAddCustomer(final Context c)
    {
        final Dialog epicDialog = new Dialog(c);
        ImageView closebox;
        Button btnCancel,btnOk;
        TextView textHeader,textDetails;
        final EditText namecustomer,phonecustomer;

        epicDialog.setContentView(R.layout.boxdailog_add_customer);
        epicDialog.setCancelable(false);
        textHeader=(TextView) epicDialog.findViewById(R.id.txtbox_yes_or_no1);
        textDetails=(TextView)epicDialog.findViewById(R.id.txtbox_yes_or_no2);
        closebox=(ImageView)epicDialog.findViewById(R.id.bt_box_yes_or_no);
        btnCancel=(Button)epicDialog.findViewById(R.id.btn_box_yes_or_no_close);
        btnOk=(Button)epicDialog.findViewById(R.id.btn_Ok);
        namecustomer=(EditText) epicDialog.findViewById(R.id.namecustomer);
        phonecustomer=(EditText)epicDialog.findViewById(R.id.phonecustomer);

        textHeader.setText("عميل جديد");
        textDetails.setText("يرجى تعبئة البيانات التالية !!");


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String AName=namecustomer.getText().toString();
                String Tel1=phonecustomer.getText().toString();
                SaveCustomers(c,AName,Tel1,epicDialog);

            }
        });
        closebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();

            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }
    String[] permisions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };
    private boolean hasSDcardPermissions() {
        //判断是否有权限
        if (EasyPermission.hasPermissions(frmCashVanPos.this, permisions)) {
            return true;
        } else {
            EasyPermission.with(this)
                    .rationale("选择文件需要SDCard读写权限")
                    .addRequestCode(REQUEST_PERMISSION)
                    .permissions(permisions)
                    .request();
        }
        return false;
    }
    private String bt_mac;
    private String bt_name;
    private String wifi_mac;
    private String wifi_name;
    private void openConn() {
        myOpertion = new BluetoothOperation(this, mHandler);
        myOpertion.btAutoConn(this,  mHandler);

    }

    @Override
    public void onPermissionGranted(int requestCode, List<String> perms) {

    }
    private void setupPrint() {


        // Initialize the BluetoothPrintService to perform bluetooth connections
        mPrintService = new BluetoothPrintService(mHandler);
        mWoosim = new WoosimService(mHandler);
    }
    @Override
    public void onPermissionDenied(int requestCode, List<String> perms) {

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


    private MyTask myTask;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(false==true)
            {
                switch (msg.what) {
                    case PrinterConstants.Connect.SUCCESS:
                        isConnected = true;
                        mPrinter = myOpertion.getPrinter();
                        java.util.Timer timer = new Timer();
                        myTask = new MyTask();
                        timer.schedule(myTask, 0, 2000);
                        Toast.makeText(frmCashVanPos.this, R.string.yesconn, Toast.LENGTH_SHORT).show();
                        break;
                    case PrinterConstants.Connect.FAILED:
                        if(myTask != null){
                            myTask.cancel();
                        }
                        isConnected = false;
                        Toast.makeText(frmCashVanPos.this, R.string.conn_failed, Toast.LENGTH_SHORT).show();
                        break;
                    case PrinterConstants.Connect.CLOSED:
                        if(myTask != null){
                            myTask.cancel();
                        }
                        isConnected = false;
                        Toast.makeText(frmCashVanPos.this, R.string.conn_closed, Toast.LENGTH_SHORT).show();
                        break;
                    case PrinterConstants.Connect.NODEVICE:
                        isConnected = false;
                        Toast.makeText(frmCashVanPos.this, R.string.conn_no, Toast.LENGTH_SHORT).show();
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

        }

    };


    private void initDialog(){
        try {
            if(dialog != null && dialog.isShowing()){
                dialog.dismiss();
            }

            dialog = new ProgressDialog(frmCashVanPos.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setTitle("Connecting");
            dialog.setMessage("Please Wait...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        }
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }



    private void connClick(){
        try {
            if(isConnected){
                myOpertion.close();
                myOpertion = null;
                mPrinter = null;
            }else {
                openConn();
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    private void printImage(final Bitmap bitmap){
        try {
            Global.GetLastPrinter(frmCashVanPos.this);
            switch(Global.TypePrinterConnected) {
                case 3:
                    Global.GetLastPrinter(frmCashVanPos.this);
                    Global.TscDll= new TSCActivity();
                    Global.TscDll.IsConnected=false;
                    if(Global.TscDll.IsConnected==false) {
                        Global.TscDll.openport(Global.addressBlutooth);
                    }
                    Toast.makeText(frmCashVanPos.this,"تم الاتصال",Toast.LENGTH_SHORT).show();
                   // Global.TscDll.barcode(100, 100, "128", 100, 1, 0, 3, 3, "123456789");
                    Toast.makeText(frmCashVanPos.this,"تم الطباعة",Toast.LENGTH_SHORT).show();
                    Global.TscDll.sendbitmap(0,0,bitmap);
                    Global.TscDll.printlabel(1, 1);
                    break;

                case 4:
                    if(!isConnected && mPrinter == null) {
                        // Global.connectPrinter(frmCashVanPos.this, "frmCashVanPos");
                        Global. GetLastPrinter(frmCashVanPos.this);
                        if(!Global.addressBlutooth.equals(""))
                        {
                            connClick();
                        }
                        //  PrintUtils.printImagea(bitmap, mPrinter);

                    }
                    else {
                        PrintUtils.printImagea(this.getResources(), bitmap, mPrinter);
                        isConnected=false;
                        myOpertion.close();
                        myOpertion = null;
                        mPrinter = null;
                    }
                    break;

                case 5:
                    connectDeviceWoosim();
                   // Thread.sleep(3000);
                    printImageWoosim(bitmap);
                    break;
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }



    }

    private  void displayLocationSettingsRequest(final Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        //  Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(frmCashVanPos.this, 0);
                        } catch (IntentSender.SendIntentException e) {
                            // Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        //  Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }

    private void SetPohto()
    {
        try {
            if(bitPohto==null)
            {
                if (ContextCompat.checkSelfPermission(frmCashVanPos.this, Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED)
                {
                    ActivityCompat.requestPermissions(frmCashVanPos.this, new String[] {Manifest.permission.CAMERA}, 0);


                }
                else
                {
                    Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intentCamera,0);
                }

            }
            else {
                final AlertDialog.Builder alert = new AlertDialog.Builder(frmCashVanPos.this);
                View mView = getLayoutInflater().inflate(R.layout.custom_camera, null);
                final TextView txtView = mView.findViewById(R.id.noteText1);
                final ImageView img = mView.findViewById(R.id.idCamera);
                Button btn_Cancel = mView.findViewById(R.id.idCancel);
                Button btn_Ok = mView.findViewById(R.id.idOk);
                alert.setView(mView);
                final AlertDialog alertDialog = alert.create();
                txtView.setText(getResources().getString(R.string.take_picture));
                img.setImageBitmap(bitPohto);

                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intentCamera,0);
                    }
                });
                btn_Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                btn_Ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();
                    }
                });
                //alertDialog.setCanceledOnTouchOutside(false);

                alertDialog.show();
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    private void  GetCash()
    {
        try {
            conRoyal.UrlRoyal_Query(conRoyal.ConnectionString,"select ID,AName from Cash");
            mQueue = Volley.newRequestQueue(frmCashVanPos.this);
            JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Query, null, new Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        ArrayList<String> aa=new ArrayList<String>();
                        aa.add("--إختر --");
                        ListCash.add(new Cls_Cash(0,"--إختر --"));
                        for(int i =0 ; i < response.length() ; i++)
                        {
                            JSONObject jsonArray = response.getJSONObject(i);
                            int ID = jsonArray.getInt("ID");
                            String AName = jsonArray.getString("AName");

                            ListCash.add(new Cls_Cash(ID,AName));
                            aa.add(AName);
                        }

                        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(frmCashVanPos.this,android.R.layout.simple_spinner_item,aa);
                        //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCash.setAdapter(adapter2);

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
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    private void connectDeviceWoosim() {
        if (mPrintService.getState() == BluetoothPrintService.STATE_CONNECTED) {
            Toast.makeText(this, "متصل", Toast.LENGTH_SHORT).show();

        }
        else
        {
            String address = null;
            // Get the device MAC address

            address = Global.addressBlutooth;
            // Get the BLuetoothDevice object
            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
            // Attempt to connect to the device

            mPrintService.connect(device, true);
            Toast.makeText(this, "تم الإتصال في الطابعة يرجى الضغط على نعم مره اخرى للطباعة", Toast.LENGTH_SHORT).show();
        }

    }
    private void sendData(byte[] data) {
        // Check that we're actually connected before trying printing
        if (mPrintService.getState() != BluetoothPrintService.STATE_CONNECTED) {
            Toast.makeText(this, "غير متصل", Toast.LENGTH_SHORT).show();
            return;
        }
        // Check that there's actually something to send
        if (data.length > 0)
            mPrintService.write(data);
    }

    public void printImageWoosim(Bitmap bmp) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
      //  Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.logo, options);
        if (bmp == null) {
         //   Log.e(Log.TAG, "resource decoding is failed");
            return;
        }
        byte[] data = WoosimImage.printBitmap(0, 0, 0, 0, bmp);
        bmp.recycle();

        sendData(WoosimCmd.setPageMode());
        sendData(data);
        sendData(WoosimCmd.PM_setStdMode());
    }


    private void  GetItems()
    {
        try {
                conRoyal.UrlRoyal_GetItemsByCategoryIDForCashVanForWholeSalePrice(conRoyal.ConnectionString, 0, Global.LoginBranchID, Global.LoginStoreID,false);

           // conRoyal.UrlRoyal_Query(conRoyal.ConnectionString,"select ID,AName from Items where Status=1");
            mQueue = Volley.newRequestQueue(frmCashVanPos.this);
            JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_GetSelectItemsByCategoryIDForCashVanForCustomerID, null, new Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONArray response) {
                    try {

                        ListItems.add(new ClsOrderSalesSelected("0","","--إختر --","0","0","0",0,0,false,0));
                        for(int i =0 ; i < response.length() ; i++)
                        {
                            JSONObject jsonArray = response.getJSONObject(i);
                            String ID = jsonArray.getString("ID");
                            String AName = jsonArray.getString("AName");
                            String MainBarcode = jsonArray.getString("MainBarcode");


                            double WholeSalePrice= jsonArray.optDouble("WholeSalePrice",0);
                            double MinWholeSalePrice= jsonArray.optDouble("MinWholeSalePrice",0);
                            double TaxRatio= jsonArray.optDouble("TaxRatio",0);
                            boolean IsNumber=jsonArray.optBoolean("IsNumber",false);



                            ListItems.add(new ClsOrderSalesSelected(ID,MainBarcode,AName,"1",String.valueOf(WholeSalePrice),String.valueOf(WholeSalePrice),WholeSalePrice,MinWholeSalePrice,IsNumber,TaxRatio));

                        }

                        ArrayAdapter<ClsOrderSalesSelected> adapter2=new ArrayAdapter<ClsOrderSalesSelected>(frmCashVanPos.this,android.R.layout.simple_spinner_item,ListItems);
                        //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerItem.setAdapter(adapter2);

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
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    private void  GetCustomer()
    {
        try {
            conRoyal.UrlRoyal_Query(conRoyal.ConnectionString,"select ID,AName,SalesManID from Customer");
            if(ListCustomer.size()>0)
            {
               // Thread.sleep(1000);
                ListCustomer.clear();
                //spinnerCustomer.removeAllViews();
            }
            mQueue = Volley.newRequestQueue(frmCashVanPos.this);
            JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Query, null, new Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONArray response) {
                    try {

                        ListCustomer.add(new Cls_Customer(0,"--إختر --",0));
                        for(int i =0 ; i < response.length() ; i++)
                        {
                            JSONObject jsonArray = response.getJSONObject(i);
                            int ID = jsonArray.optInt("ID",0);
                            int SalesManID = jsonArray.optInt("SalesManID",0);
                            String AName = jsonArray.optString("AName","");

                            ListCustomer.add(new Cls_Customer(ID,AName,SalesManID));

                        }

                        ArrayAdapter<Cls_Customer> adapter2=new ArrayAdapter<Cls_Customer>(frmCashVanPos.this,android.R.layout.simple_spinner_item,ListCustomer);
                        //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerCustomer.setAdapter(adapter2);

                        CustomerSelect();
                        spinnerCustomer.setSelection(0);

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
        }catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    private void  GetSalesMan()
    {
        try {
            conRoyal.UrlRoyal_Query(conRoyal.ConnectionString,"select ID,AName from Employees where PositionID=1");

            mQueue = Volley.newRequestQueue(frmCashVanPos.this);
            JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Query, null, new Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        ArrayList<String> aa=new ArrayList<String>();
                        aa.add("");
                        ListCustomer.add(new Cls_Customer(0,"",0));
                        for(int i =0 ; i < response.length() ; i++)
                        {
                            JSONObject jsonArray = response.getJSONObject(i);
                            int ID = jsonArray.getInt("ID");
                            String AName = jsonArray.getString("AName");

                            ListSalesMan.add(new Cls_Employee(ID,AName));
                            aa.add(AName);
                        }

                        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(frmCashVanPos.this,android.R.layout.simple_spinner_item,aa);
                        //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSalesman.setAdapter(adapter2);

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
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    private void RefreshForm()
    {
        try {
            spinnerCash.setEnabled(false);
            spinnerSalesman.setSelection(0);
            spinnerInvoiceTaxType.setSelection(0);
            spinnerSettelmentWay.setSelection(2);
        }
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void CustomerSelect()
    {
        try {
            for (int i = 0; i < ListCustomer.size(); i++) {
                if (spinnerCustomerID == ListCustomer.get(i).getID()) {
                    spinnerCustomer.setSelection(i-1);

                }
            }
            selectMAXCustomerID();

        }
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    private void sendTestLabel(String base) {
        try {

            Bitmap as=StringToBitMap(base);
            Global.printerZebra.printImage(new ZebraImageAndroid(as), 5, 5, as.getWidth()*2, as.getHeight()+2000, false);

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
            startActivity(new Intent(frmCashVanPos.this,CashVanPosDashBoard.class));
        } catch(Exception ex){
            //setStatus(e.getMessage(), Color.RED);
        }  finally {

        }
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
        try {
            if(base ==null)
            {
                //Toast.makeText(frmCashVanPos.this,base.toString(),Toast.LENGTH_LONG).show();
            }
            else
            {
                Bitmap as=StringToBitMap(base);

                printImage(as);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
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
  /*  private void SELECTITEM()
    {

        conRoyal.UrlRoyal_repshelftag2();
        mQueue = Volley.newRequestQueue(frmCashVanPos.this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_repshelftag2, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {


                try {
                    JSONObject jsonArray = response.getJSONObject(0);
                    //progressDialog.dismiss();
                    Logo = jsonArray.getString("Logo");
                    Bitmap as=StringToBitMap(Logo);
                    Global.printerZebra.printImage(new ZebraImageAndroid(as), 100, 0, as.getWidth()*2, as.getHeight()+1000, false);

s
                }
                catch (Exception ex)
                {
                    Toast.makeText(frmCashVanPos.this,"المادة غير معرفة",Toast.LENGTH_LONG).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(frmCashVanPos.this,error.getMessage(),Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
        mQueue.add(request);
    }*/
    public  Bitmap drawStringonBitmap() {
        Bitmap imageBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(imageBitmap);
        float scale = getResources().getDisplayMetrics().density;
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(24*scale);
        p.setStyle(Paint.Style.FILL);
        p.setShadowLayer(10f,10f,10f,Color.WHITE);
        canvas.drawText("Hello", 100/2, 100/2, p);
        bitPohto=imageBitmap;
        return imageBitmap;
    }
    private void GetAll()
    {
        try {
            GetSettelmentWay();
            GetInvoiceTaxType();
            //GetdataCategory();
            GetCash();
            if(TransType==15) // Type CashVanPos
            {
                GetItems();
                spinnerItem.setVisibility(View.VISIBLE);
            }
            else
            {
                spinnerItem.setVisibility(View.GONE);
            }
            GetCustomer();
            GetSalesMan();
        }
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void GetSettelmentWay()
    {
        try {
            ListSettelmentWay.add(new Cls_SettelmentWay(getString(R.string.radioAccountsreceivable),2));
            ListSettelmentWay.add(new Cls_SettelmentWay(getString(R.string.radioCash),1));

            ArrayList<String> aa=new ArrayList<String>();
            for (int i=0;i<ListSettelmentWay.size();i++)
            {
                aa.add(ListSettelmentWay.get(i).getAName());
            }

            ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,aa);
            //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSettelmentWay.setAdapter(adapter2);
        }
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    private void GetInvoiceTaxType()
    {
        try {

            ListInvoiceTaxType.add(new Cls_InvoiceTaxType(1,getString(R.string.txtTaxbill)));
            ListInvoiceTaxType.add(new Cls_InvoiceTaxType(2,getString(R.string.Anon_taxinvoice)));
            ListInvoiceTaxType.add(new Cls_InvoiceTaxType(3,getString(R.string.exempt_Invoice)));
            ListInvoiceTaxType.add(new Cls_InvoiceTaxType(4,getString(R.string.Zero_Invoice)));


            ArrayList<String> aa=new ArrayList<String>();
            for (int i=0;i<ListInvoiceTaxType.size();i++)
            {
                aa.add(ListInvoiceTaxType.get(i).getAName());
            }

            ArrayAdapter<String> adapter2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,aa);
            //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerInvoiceTaxType.setAdapter(adapter2);
        }
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    void AddCardViewCategory(final Integer ID, String Text1, String Text2,String color)
    {
        try {
            LinearLayout l1=new LinearLayout(frmCashVanPos.this);
            LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT );
            params0.gravity= Gravity.CENTER|Gravity.TOP;
            l1.setLayoutParams(params0);
            l1.setOrientation(LinearLayout.HORIZONTAL);
            l1.setWeightSum(2);

            final CardView cardView=new CardView(frmCashVanPos.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(5,0,5,0);
            params.weight=1;
            cardView.setLayoutParams(params);
            cardView.setRadius(0);
            cardView.setTag(ID);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer idCategory=Integer.valueOf(cardView.getTag().toString());
                    GetdataItemsByCategory(idCategory,spinnerCustomerID);
                }
            });


            LinearLayout l2=new LinearLayout(frmCashVanPos.this);
            l2.setOrientation(LinearLayout.VERTICAL);
            l2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
            if(!color.equals(""))
            {
                l2.setBackgroundColor(Color.parseColor(color));
            }
            else {
                l2.setBackgroundColor(Color.rgb(0, 204, 204));
            }

            LinearLayout l3=new LinearLayout(frmCashVanPos.this);
            l3.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT );
            params2.setMargins(30,10,10,0);
            params2.gravity=Gravity.RIGHT;
            l3.setLayoutParams(params2);


            TextView text1=new TextView(frmCashVanPos.this);
            LinearLayout.LayoutParams paramstext = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
            paramstext.gravity=Gravity.RIGHT;
            text1.setLayoutParams(paramstext);
            text1.setTextColor(Color.WHITE);
            text1.setTextSize(24);
            text1.setText(Text1);

            TextView text2=new TextView(frmCashVanPos.this);
            LinearLayout.LayoutParams paramstext2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
            paramstext2.gravity=Gravity.CENTER;
            paramstext2.setMargins(0,10,0,0);
            text2.setLayoutParams(paramstext2);
            text2.setTextColor(Color.WHITE);
            text2.setTextSize(20);
            text2.setText(Text2);

            l3.addView(text1);
            l3.addView(text2);

            l2.addView(l3);

            cardView.addView(l2);

            l1.addView(cardView);

            LineCategory=findViewById(R.id.listcategoryCashVanPos);
            LineCategory.addView(l1);
        }
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }




    private void GetdataCategory() {
        try {

            progressDialog= new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            conRoyal.UrlRoyal_Query(conRoyal.ConnectionString,"select * from Category");
            mQueue = Volley.newRequestQueue(this);

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,conRoyal.Url_Query, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                boolean flag=false;
                                if(LineCategory!=null) {
                                    LineCategory.removeAllViews();

                                }
                               // LineItems=findViewById(R.id.listitemswherecategoryCashVanPos);
                            //    SearchableSpinner spinnerItem=new SearchableSpinner(frmCashVanPos.this);
                             //   LinearLayout.LayoutParams paramsspinnerItem = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
                            //    spinnerItem.setLayoutParams(paramsspinnerItem);

                             //   LineItems.addView(spinnerItem);
                                for (int i = 0; i < response.length(); i++) {
                                    flag=true;
                                    JSONObject obj = response.getJSONObject(i);
                                    Integer ID = obj.getInt("ID");
                                    String Name = obj.getString("AName");
                                    String Color=obj.getString("Color");
                                    if(Color.equals("null"))
                                    {
                                        Color="";
                                    }
                                    AddCardViewCategory(ID,Name,"",Color);

                                }
                                if(flag) {
                                    progressDialog.dismiss();
                                    flag=false;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    progressDialog.dismiss();
                }
            });
            mQueue.add(request);
        }
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void GetdataItemsByCategory(final int CategoryID, final int CustomerID) {
        try {
            progressDialog= new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    if(TransType==15) // Type CashVanPos
                    {
                        conRoyal.UrlRoyal_GetItemsByCategoryIDForCashVanForWholeSalePrice(conRoyal.ConnectionString, CategoryID, Global.LoginBranchID, Global.LoginStoreID,false);
                    }
                    else
                    {
                      // conRoyal.UrlRoyal_GetItemsCashVanPOSByCategoryIDIsProdaction(conRoyal.ConnectionString, CategoryID);
                        conRoyal.UrlRoyal_GetItemsByCategoryIDForCashVanForCustomerID(conRoyal.ConnectionString, CategoryID, Global.LoginBranchID, Global.LoginStoreID,CustomerID);
                    }
                    mQueue = Volley.newRequestQueue(frmCashVanPos.this);

                    JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,conRoyal.Url_GetSelectItemsByCategoryIDForCashVanForCustomerID, null,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try {
                                        DecimalFormat DF = new DecimalFormat("######0.000");
                                        boolean flag=false;
                                        if(LineItems!=null) {
                                            LineItems.removeAllViews();
                                           // LineItems=findViewById(R.id.listitemswherecategoryCashVanPos);
                                          //  SearchableSpinner spinnerItem=new SearchableSpinner(frmCashVanPos.this);
                                          //  LinearLayout.LayoutParams paramsspinnerItem = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
                                          //  spinnerItem.setLayoutParams(paramsspinnerItem);

                                         //   LineItems.addView(spinnerItem);
                                        }

                                        if(response.length()==0)
                                        {
                                            progressDialog.dismiss();
                                        }
                                        for (int i = 0; i < response.length(); i++) {
                                            flag=true;
                                            JSONObject obj = response.getJSONObject(i);
                                            Integer ID = obj.getInt("ID");
                                            String Name = obj.getString("AName");
                                            String Color=obj.getString("Color");
                                            // String SalesPrice=obj.getString("SalesPrice");DF.format(Double.parseDouble(SalesPrice)))
                                            String Picture=obj.getString("Picture");
                                            Boolean IsHasBasket=obj.optBoolean("IsHasBasket",false);
                                            double SalesPriceBeforeTax= obj.optDouble("SalesPriceBeforeTax",0);
                                            double SalesPriceAfterTax= obj.optDouble("SalesPrice",0);
                                            boolean IsNumber=obj.optBoolean("IsNumber",false);
                                            double WholeSalePrice= obj.optDouble("WholeSalePrice",999);
                                            double MinWholeSalePrice= obj.optDouble("MinWholeSalePrice",0);

                                            double TaxAmount= obj.optDouble("TaxAmount",0);
                                            double TaxRatio= obj.optDouble("TaxRatio",0);
                                            TaxRatio=TaxRatio/100;
                                            int SalesTaxID= obj.optInt("SalesTaxID",0);

                                            if(Color.equals("null"))
                                            {
                                                Color="";
                                            }

                                            Bitmap bp = null;
                                            if (!Picture.equals("0")) {
                                                byte[] iimage = Base64.decode(Picture, Base64.DEFAULT);
                                                bp = BitmapFactory.decodeByteArray(iimage, 0, iimage.length);
                                            }

                                            AddCardViewItems(ID,Name,DF.format(SalesPriceAfterTax),Color,bp,IsHasBasket,SalesPriceBeforeTax,TaxAmount,SalesTaxID,WholeSalePrice,MinWholeSalePrice,IsNumber,TaxRatio);
                                        }
                                        if(flag) {
                                            progressDialog.dismiss();
                                            flag=false;
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        progressDialog.dismiss();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            progressDialog.dismiss();
                        }
                    });

                    mQueue.add(request);
                }
            });
        }
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }



    void AddCardViewItems(final Integer ID, String Text1, String SalesPrice, String color, Bitmap Picture, final Boolean IsHasBasket,double SalesPriceBeforeTax,double TaxAmount,int SalesTaxID,double WholeSalePrice,double MinWholeSalePrice,boolean IsNumber,double TaxRatio)
    {
        try {

            final DecimalFormat DF = new DecimalFormat("######0.000");
            String Text2="";
            if(TransType==15)
            {
                Text2 =" ↓ "+ String.valueOf(MinWholeSalePrice) + " ~ "+String.valueOf(WholeSalePrice)+" ↑ " ;
            }
            else {
                Text2 = SalesPrice;
            }
            LinearLayout l1=new LinearLayout(frmCashVanPos.this);
            LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT ,LinearLayout.LayoutParams.MATCH_PARENT );
            //params0.gravity=Gravity.CENTER|Gravity.TOP;
            l1.setLayoutParams(params0);
            l1.setOrientation(LinearLayout.HORIZONTAL);
            l1.setWeightSum(2);





            final CardView cardView=new CardView(frmCashVanPos.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,20,10,10);
            params.weight=1;
            cardView.setLayoutParams(params);
            cardView.setRadius(8);

            ClsOrderSalesSelected a = new ClsOrderSalesSelected("", "", "", "", "", "",0,0,false,0);
            a.setID(String.valueOf(ID));
            a.setMainBarcode("0");
            a.setItemName(Text1);
            a.setQty("1");
            a.setNumber(IsNumber);
            a.setTaxRatio(TaxRatio);
            a.setIsDelete(true);
            // a.setNumber(IsNumber);
            a.setQtyBefore(1);
            if(TransType==15)
            {
                a.setPrice(String.valueOf(WholeSalePrice));
                a.setTotal(String.valueOf(WholeSalePrice));
            }
            else
            {
                a.setPrice(SalesPrice);
                a.setTotal(SalesPrice);
            }

            //  a.setHasAdditions(IsHasAdditions);
            //  a.setHasOptions(IsHasOptions);
            a.setHasNotes(false);
            a.setUpdatedQty(false);
            //  a.setTax(IsTax);
            //  a.setTaxID(TaxID);
            a.setStatus(0);
            a.setItemAdditions(false);
            a.setItemNotes(false);
            a.setItemOptions(false);
            a.setNotes("");
            a.setSalesTaxID(SalesTaxID);

            a.setHasBasket(IsHasBasket);
            a.setSalesPriceBeforeTax(SalesPriceBeforeTax);
            a.setTaxAmount(TaxAmount);
            a.setWholeSalePrice(WholeSalePrice);
            a.setMinWholeSalePrice(MinWholeSalePrice);
            cardView.setTag(a);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClsOrderSalesSelected getdata= (ClsOrderSalesSelected) cardView.getTag();
                    int ItemID=Integer.parseInt(getdata.getID());

                    if(true==false) {
                        if (listNew.size() > 0) {
                            for (int i = 0; i < listNew.size(); i++) {
                                if (ItemID == Integer.parseInt(listNew.get(i).getID())) {
                                    listNew.get(i).setQty(String.valueOf(Double.parseDouble( listNew.get(i).getQty()) + 1));
                                    listNew.get(i).setStatus(0);
                                    getdata.setStatus(0);
                                    break;
                                } else {
                                    if (i == listNew.size()-1) {
                                        getdata.setQty("1");
                                        getdata.setStatus(0);
                                        getdata.setQtyBefore(1);
                                        listNew.add(getdata);
                                        break;
                                    }
                                }
                            }
                        } else {
                            getdata.setQty("1");
                            getdata.setQtyBefore(1);
                            getdata.setStatus(0);
                            listNew.add(getdata);
                        }
                    }
                    else
                    {
                        ShowBoxInputNote(getdata);
                    }
                    Adapter.notifyDataSetChanged();
                    sumTotalOrder();
                }
            });

            LinearLayout l2=new LinearLayout(frmCashVanPos.this);
            l2.setOrientation(LinearLayout.VERTICAL);
            l2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
            if(!color.equals(""))
            {
                l2.setBackgroundColor(Color.parseColor(color));
            }
            else {
                l2.setBackgroundColor(Color.rgb(0, 204, 204));
            }


            LinearLayout ln=new LinearLayout(frmCashVanPos.this);
            ln.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams paramsn = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT );
            ln.setLayoutParams(paramsn);
            //ln.setWeightSum(2);

            ImageView img=new ImageView(this);
            LinearLayout.LayoutParams paramsimg = new LinearLayout.LayoutParams(100,LinearLayout.LayoutParams.MATCH_PARENT  );
            img.setScaleType(ImageView.ScaleType.FIT_XY);
            paramsimg.gravity=Gravity.RIGHT;
            //paramsimg.weight=1;
            img.setLayoutParams(paramsimg);
            img.setBackgroundColor(Color.WHITE);
            if(Picture==null) {
                img.setImageResource(R.drawable.logoroyal);
            }
            else
            {
                img.setImageBitmap(Picture);
            }


            LinearLayout l3=new LinearLayout(frmCashVanPos.this);
            l3.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
            //params2.weight=1;
            params2.setMargins(30,10,10,0);
            l3.setLayoutParams(params2);



            TextView text1=new TextView(frmCashVanPos.this);
            LinearLayout.LayoutParams paramstext = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
            paramstext.gravity=Gravity.RIGHT;
            text1.setLayoutParams(paramstext);
            text1.setTextColor(Color.WHITE);
            text1.setTextSize(24);
            text1.setText(Text1);



            TextView text2=new TextView(frmCashVanPos.this);
            LinearLayout.LayoutParams paramstext2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
            paramstext2.gravity=Gravity.RIGHT;
            paramstext2.setMargins(0,10,0,0);
            text2.setLayoutParams(paramstext2);
            text2.setTextColor(Color.RED);
            text2.setTextSize(20);
            text2.setText(Text2);



            l3.addView(text1);
            l3.addView(text2);


            ln.addView(img);
            ln.addView(l3);

            l2.addView(ln);

            cardView.addView(l2);


            l1.addView(cardView);


            LineItems=findViewById(R.id.listitemswherecategoryCashVanPos);
            LineItems.addView(l1);

        }
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }


    }
    void AddCardViewBasketType(LinearLayout lii,ClsOrderSalesSelected getdata,int ID,String Name,double Weight)
    {
        try {
            LinearLayout l1=new LinearLayout(frmCashVanPos.this);
            LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT );
            params0.gravity= Gravity.CENTER|Gravity.TOP;
            l1.setLayoutParams(params0);
            l1.setOrientation(LinearLayout.HORIZONTAL);
            l1.setWeightSum(2);

            final CardView cardView=new CardView(frmCashVanPos.this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(10,10,10,10);
            params.weight=1;
            cardView.setLayoutParams(params);
            cardView.setRadius(0);


            ClsOrderSalesSelected a = new ClsOrderSalesSelected("", "", "", "", "", "",0,0,false,0);
            a.setID(String.valueOf(getdata.getID()));
            a.setMainBarcode("0");
            a.setItemName(getdata.getItemName());
            a.setQty("1");
            a.setIsDelete(true);
            // a.setNumber(IsNumber);
            a.setQtyBefore(1);
            a.setPrice(getdata.getPrice());
            a.setTotal(getdata.getTotal());

            //  a.setHasAdditions(IsHasAdditions);
            //  a.setHasOptions(IsHasOptions);
            a.setHasNotes(false);
            a.setUpdatedQty(false);
            //  a.setTax(IsTax);
            //  a.setTaxID(TaxID);
            a.setStatus(0);
            a.setItemAdditions(false);
            a.setItemNotes(false);
            a.setItemOptions(false);
            a.setNotes("");
            a.setBasketTypeID(ID);
            a.setBasketTypeName(Name);
            a.setBasketTypeWeight(Weight);
            a.setBasedQTY(getdata.getBasedQTY());
            a.setEmptyQTY(getdata.getEmptyQTY());
            a.setType_panier(Name);
            a.setHasBasket(getdata.getHasBasket());
            a.setNumberbaskets(getdata.getNumberbaskets());
            a.setTaxAmount(getdata.getTaxAmount());
            a.setSalesPriceBeforeTax(getdata.getSalesPriceBeforeTax());
            a.setSalesTaxID(getdata.getSalesTaxID());

            cardView.setTag(a);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClsOrderSalesSelected tt= (ClsOrderSalesSelected) cardView.getTag();
                    tt.setEmptyQTY(tt.getNumberbaskets() * tt.getBasketTypeWeight());
                    tt.setQty(String.valueOf(tt.getBasedQTY()-tt.getEmptyQTY()));
                    tt.setTotal(String.valueOf(Double.parseDouble(tt.getQty()) * Double.parseDouble(tt.getPrice())));
                    listNew.add(tt);
                    Adapter.notifyDataSetChanged();
                    epicDialog.dismiss();
                    Toast.makeText(frmCashVanPos.this," تم إضافة "+ tt.getItemName().toString(), Toast.LENGTH_LONG).show();
                    sumTotalOrder();
                }
            });


            LinearLayout l2=new LinearLayout(frmCashVanPos.this);
            l2.setOrientation(LinearLayout.VERTICAL);
            l2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

            l2.setBackgroundColor(Color.rgb(0, 204, 204));


            LinearLayout l3=new LinearLayout(frmCashVanPos.this);
            l3.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT );
            params2.setMargins(30,10,10,0);
            params2.gravity=Gravity.RIGHT;
            l3.setLayoutParams(params2);


            TextView text1=new TextView(frmCashVanPos.this);
            LinearLayout.LayoutParams paramstext = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
            paramstext.gravity=Gravity.RIGHT;
            text1.setLayoutParams(paramstext);
            text1.setTextColor(Color.WHITE);
            text1.setTextSize(24);
            text1.setText(getdata.getBasketTypeName());

            TextView text2=new TextView(frmCashVanPos.this);
            LinearLayout.LayoutParams paramstext2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
            paramstext2.gravity=Gravity.CENTER;
            paramstext2.setMargins(0,5,0,0);
            text2.setLayoutParams(paramstext2);
            text2.setTextColor(Color.RED);
            text2.setTextSize(20);
            text2.setText(String.valueOf(getdata.getBasketTypeWeight()));

            l3.addView(text1);
            l3.addView(text2);

            l2.addView(l3);

            cardView.addView(l2);

            l1.addView(cardView);

            lii.addView(l1);
        }
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
    }

}
    private void getdataBasket(final LinearLayout ln, final Dialog dd, final ClsOrderSalesSelected getdata) {
        try {
            progressDialog= new ProgressDialog(this);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            conRoyal.UrlRoyal_Query(conRoyal.ConnectionString,"select AName, ID, Weight from Basket");
            mQueue = Volley.newRequestQueue(this);

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,conRoyal.Url_Query, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                boolean flag=false;
                                for (int i = 0; i < response.length(); i++) {
                                    flag=true;
                                    JSONObject obj = response.getJSONObject(i);
                                    Integer ID = obj.getInt("ID");
                                    String Name = obj.getString("AName");
                                    double Weight = obj.getDouble("Weight");
                                    getdata.setBasketTypeID(ID);
                                    getdata.setBasketTypeName(Name);
                                    getdata.setBasketTypeWeight(Weight);
                                    AddCardViewBasketType(ln,getdata,ID,Name,Weight);

                                }
                                if(flag) {
                                    progressDialog.dismiss();
                                    flag=false;
                                    dd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    dd.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    progressDialog.dismiss();
                }
            });


            mQueue.add(request);
        }
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    void ShowBasketType (ClsOrderSalesSelected getdata)
    {
        try {
            epicDialog.setContentView(R.layout.box_dailog_item_additions);
            closebox=(ImageView)epicDialog.findViewById(R.id.bt_N_close_access);
            btnCancel=(Button)epicDialog.findViewById(R.id.btn_Cancel_Now);
            Button  btnOk=(Button)epicDialog.findViewById(R.id.btn_Ok_Now);
            closebox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    epicDialog.dismiss();
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    epicDialog.dismiss();
                }
            });

            LineTypeBasket=epicDialog.findViewById(R.id.listItemAdditions);
            getdataBasket(LineTypeBasket,epicDialog,getdata);
            //epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //epicDialog.show();

            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    epicDialog.dismiss();
                }
            });
        }
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    void  ShowBoxInputNote(final ClsOrderSalesSelected getdata)
    {
        try {
            final AlertDialog.Builder alert=new AlertDialog.Builder(frmCashVanPos.this);
            View mView=getLayoutInflater().inflate(R.layout.custom_dailog_note,null);
            final EditText txt=mView.findViewById(R.id.txtinputNote);
            final TextView txtView=mView.findViewById(R.id.noteText1);
            Button btn_Cancel=mView.findViewById(R.id.idCancel);
            Button btn_Ok=mView.findViewById(R.id.idOk);
            alert.setView(mView);
            final AlertDialog alertDialog=alert.create();
            final DecimalFormat DF = new DecimalFormat("######0.000");
            if(getdata.getHasBasket())
            {
                txtView.setText(getResources().getString(R.string.EnterQtyExisting));
                txt.setHint(getResources().getString(R.string.EnterQtyExisting));
                if(getdata.isNumber())
                {
                    txt.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
                else
                {
                    txt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                }


                btn_Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                btn_Ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String tt=txt.getText().toString().trim();
                        if(!tt.equals("")&& Double.parseDouble(tt)>0) {
                            alertDialog.dismiss();
                            getdata.setBasedQTY(Double.parseDouble(tt));
                            final AlertDialog.Builder alert=new AlertDialog.Builder(frmCashVanPos.this);
                            View mView=getLayoutInflater().inflate(R.layout.custom_dailog_note,null);
                            final EditText txtNumber=mView.findViewById(R.id.txtinputNote);
                            final TextView txtView=mView.findViewById(R.id.noteText1);
                            Button btn_Cancel=mView.findViewById(R.id.idCancel);
                            Button btn_Ok=mView.findViewById(R.id.idOk);
                            alert.setView(mView);
                            final AlertDialog alertDialog=alert.create();

                            txtView.setText(getResources().getString(R.string.EnterNumberBaskets));
                            txtNumber.setHint(getResources().getString(R.string.EnterNumberBaskets));
                            txtNumber.setInputType(InputType.TYPE_CLASS_NUMBER);

                            btn_Cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    alertDialog.dismiss();
                                }


                            });
                            btn_Ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String Number=txtNumber.getText().toString().trim();
                                    if(!Number.equals("")&& Integer.parseInt(Number)>0) {
                                        getdata.setNumberbaskets(Integer.parseInt(Number));
                                        getdata.setSalesPriceBeforeTax(getdata.getSalesPriceBeforeTax());
                                        getdata.setSalesTaxID(getdata.getSalesTaxID());
                                        getdata.setTaxAmount(getdata.getTaxAmount());
                                        getdata.setTotalBeforeTax(getdata.getSalesPriceBeforeTax()*Double.parseDouble(txt.getText().toString()));
                                        getdata.setTotalTaxAmount(getdata.getTaxAmount()*Double.parseDouble(txt.getText().toString()));

                                        ShowBasketType(getdata);
                                        alertDialog.dismiss();

                                    }
                                    else
                                    {
                                        Toast.makeText(frmCashVanPos.this,getResources().getString(R.string.EnterNumberBaskets),Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                            alertDialog.setCanceledOnTouchOutside(false);

                            alertDialog.show();
                        }
                        else
                        {
                            Toast.makeText(frmCashVanPos.this,getResources().getString(R.string.EnterQtyExisting),Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                alertDialog.setCanceledOnTouchOutside(false);

                alertDialog.show();
            }
            else
            {
                txtView.setText(getResources().getString(R.string.EnterQty));
                txt.setHint(getResources().getString(R.string.EnterQty));

                if(getdata.isNumber())
                {
                    txt.setInputType(InputType.TYPE_CLASS_NUMBER);
                }
                else
                {
                    txt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                }


                btn_Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                btn_Ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tt=txt.getText().toString().trim();
                        if(!tt.equals("")&& Double.parseDouble(tt)>0) {


                            if(Global.IsShowPriceChange) {
                                final double UpPrice=Double.valueOf(getdata.getWholeSalePrice());
                                final double MinPrice=Double.valueOf(getdata.getMinWholeSalePrice());




                                //----------------------------- Dailog ItemName_New--------------------------------------------------------
                                AlertDialog.Builder builder = new AlertDialog.Builder(frmCashVanPos.this);
                                builder.setTitle("تعديل السعر ");
                                builder.setIcon(R.drawable.sms);
                                builder.setMessage(" يرجى إدخال السعر  (تعديل)   ");

                                final EditText  inputEditPrice = new EditText(frmCashVanPos.this);

                                inputEditPrice.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                                builder.setView(inputEditPrice);
                                builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String   PriceNew = inputEditPrice.getText().toString();
                                        if (PriceNew.equals(""))
                                        {
                                            Toast.makeText(frmCashVanPos.this, "يرجى إدخال السعر", Toast.LENGTH_LONG).show();
                                            return ;
                                        } else {
                                            if ((Double.valueOf(PriceNew)) >= MinPrice && (Double.valueOf(PriceNew)) <= UpPrice)
                                            {
                                                //  Cls_ItemsInvoiceSales.setQty(QtyNew);
                                                if (Double.valueOf(PriceNew) > 0) {
                                                    Double dd = Double.valueOf(PriceNew);
                                                    DecimalFormat DF = new DecimalFormat("######0.000");


                                                    ClsOrderSalesSelected a = new ClsOrderSalesSelected("", "", "", "", "", "",0,0,false,0);
                                                    a.setID(String.valueOf(getdata.getID()));
                                                    a.setMainBarcode("0");
                                                    a.setSalesPriceBeforeTax(Double.parseDouble(String.valueOf(getdata.getSalesPriceBeforeTax())));
                                                    a.setTaxAmount(getdata.getTaxAmount());
                                                    a.setItemName(getdata.getItemName());
                                                    a.setQty(txt.getText().toString());
                                                    a.setPrice(DF.format(Double.parseDouble(String.valueOf(PriceNew))));
                                                    a.setTotal(DF.format(Double.parseDouble(txt.getText().toString())*Double.parseDouble(PriceNew)));
                                                    a.setTotalBeforeTax(Double.parseDouble(PriceNew)*Double.parseDouble(txt.getText().toString()));
                                                    a.setTotalTaxAmount(getdata.getTaxAmount()*Double.parseDouble(txt.getText().toString()));
                                                    a.setTaxID(0);
                                                    a.setTax(false);
                                                    a.setHasOptions(false);
                                                    a.setIsDelete(true);
                                                    a.setNumber(getdata.getisNumber());
                                                    a.setHasAdditions(false);
                                                    a.setHasNotes(false);
                                                    a.setUpdatedQty(false);
                                                    a.setItemAdditions(false);
                                                    a.setItemNotes(false);
                                                    a.setItemOptions(false);
                                                    a.setNotes("");
                                                    a.setType_panier("");
                                                    a.setBasketTypeName("");
                                                    a.setBasketTypeID(0);
                                                    a.setBasketTypeWeight(0);
                                                    a.setHasBasket(getdata.getHasBasket());

                                                    a.setWholeSalePrice(getdata.getWholeSalePrice());
                                                    a.setMinWholeSalePrice(getdata.getMinWholeSalePrice());
                                                    listNew.add(a);
                                                    Adapter.notifyDataSetChanged();
                                                    sumTotalOrder();
                                                }
                                                //Qty.setText(QtyNew);

                                                // Qty.setText(QtyNew);

                                            }
                                            else if((Double.valueOf(PriceNew)) < MinPrice)
                                            {
                                                Global.ShowAccessPopup2(frmCashVanPos.this,"عذرا"," يجب أن يكون السعر أعلى من  "+MinPrice,"#cc0000");
                                                /// Toast.makeText(context,MinPrice+"يجب أن يكون السعر أعلى من  ",Toast.LENGTH_SHORT).show();
                                                return ;
                                            }
                                            else if((Double.valueOf(PriceNew)) > UpPrice)
                                            {
                                                //Toast.makeText(context,UpPrice+"يجب أن يكون السعر أقل من  ",Toast.LENGTH_SHORT).show();
                                                Global.ShowAccessPopup2(frmCashVanPos.this,"عذرا"," يجب أن يكون السعر أقل من  "+UpPrice,"#D81B60");
                                                return ;
                                            }
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
                            else
                            {
                                ClsOrderSalesSelected a = new ClsOrderSalesSelected("", "", "", "", "", "",0,0,false,0);
                                a.setID(String.valueOf(getdata.getID()));
                                a.setMainBarcode("0");
                                a.setSalesPriceBeforeTax(Double.parseDouble(String.valueOf(getdata.getSalesPriceBeforeTax())));
                                a.setTaxAmount(getdata.getTaxAmount());
                                a.setItemName(getdata.getItemName());
                                a.setQty(txt.getText().toString());
                                a.setPrice(DF.format(Double.parseDouble(String.valueOf(getdata.getPrice()))));
                                a.setTotal(DF.format(Double.parseDouble(txt.getText().toString())*Double.parseDouble(getdata.getPrice())));
                                a.setTotalBeforeTax(getdata.getSalesPriceBeforeTax()*Double.parseDouble(txt.getText().toString()));
                                a.setTotalTaxAmount(getdata.getTaxAmount()*Double.parseDouble(txt.getText().toString()));
                                a.setTaxID(0);
                                a.setTax(false);
                                a.setHasOptions(false);
                                a.setIsDelete(true);
                                a.setNumber(getdata.getisNumber());
                                a.setHasAdditions(false);
                                a.setHasNotes(false);
                                a.setUpdatedQty(false);
                                a.setItemAdditions(false);
                                a.setItemNotes(false);
                                a.setItemOptions(false);
                                a.setNotes("");
                                a.setType_panier("");
                                a.setBasketTypeName("");
                                a.setBasketTypeID(0);
                                a.setBasketTypeWeight(0);
                                a.setHasBasket(getdata.getHasBasket());
                                a.setTaxRatio(getdata.getTaxRatio());
                                a.setWholeSalePrice(getdata.getWholeSalePrice());
                                a.setMinWholeSalePrice(getdata.getMinWholeSalePrice());
                                listNew.add(a);
                                Adapter.notifyDataSetChanged();
                                sumTotalOrder();
                            }
                        }
                        alertDialog.dismiss();
                        sumTotalOrder();
                    }
                });
                alertDialog.setCanceledOnTouchOutside(false);

                alertDialog.show();
            }

        }
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    Boolean Vald()
    {
        if(spinnerCustomerID==0 || spinnerCustomerID==-2)
        {
            Toast.makeText(frmCashVanPos.this,"يرجى تحديد العميل", Toast.LENGTH_LONG).show();
            return false;
        }
        if(Double.parseDouble(Global.convertToEnglish(TotalOrder.getText().toString()))<=0)
        {
            Toast.makeText(frmCashVanPos.this,"لا يمكنك حفظ فاتورة فارغة أو قيمتها تساوي صفر", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    private void InsertRound(int CustomerID,int routeDetailsID,int RouteStatus)
    {
        //Global.GetLocation(CashVanPosDashBoard.this);
      /*  progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);*/

        //   Bitmap signature =null;
        String txtsignature ="NoPic";
           /* if(bitsignature!=null)
            {
                signature =bitsignature;
                txtsignature = ImageUtil.convert(signature);
            }*/


        //  Bitmap imgg =null;
        String txtimgg ="NoPic";
          /*  if(bitPohto!=null)
            {
                imgg=bitPohto;
                txtimgg = ImageUtil.convert(imgg);

            }*/
        int EmpID=conRoyal.IDUser;
        int BatteryPercentage=Global.GetBatteryLevel(frmCashVanPos.this);

        String locationOnMap=Global.locaion;
        int CreationUserID=conRoyal.IDUser;
        String CreationDate="";

        String Notes="";


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(conRoyal.ConIpRoyal2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        ClsCashVanPosRound pp = new ClsCashVanPosRound(conRoyal.ConnectionString, CustomerID,EmpID,txtimgg, BatteryPercentage,txtsignature, locationOnMap,CreationUserID,CreationDate,routeDetailsID,RouteStatus,Notes);
        Call<ClsCashVanPosRound> call = jsonPlaceHolderApi.InsertCashVanPosRound(pp);
        call.enqueue(new Callback<ClsCashVanPosRound>() {
            @Override
            public void onResponse(Call<ClsCashVanPosRound> call, retrofit2.Response<ClsCashVanPosRound> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("True")) {
                        progressDialog.dismiss();
                        //ShowDataSavedRound(frmCashVanPos.this, getString(R.string.txtCorrectoperation), getString(R.string.txtSuccessfully_Print));
                    } else {
                        progressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(Call<ClsCashVanPosRound> call, Throwable t) {

                Global.ShowDataSavedToSqlLitePopup(frmCashVanPos.this);

            }


        });
    }

    private void timer()
    {
        Timer myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                conRoyal.UrlRoyal_Query(conRoyal.ConnectionString,"select case when RouteStatus = 7 then 1 when RouteStatus = 8 then 0 else -1 end as col,* from RoutesControl where RouteStatus in (7,8) and routeDetailsID = "+routeDetailsHomeID+"");

                mQueue = Volley.newRequestQueue(frmCashVanPos.this);
                JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Query, null, new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject jsonArray = response.getJSONObject(0);
                            int i = jsonArray.getInt("col");
                            if(i==1)
                            {
                                ShowDataSaved(frmCashVanPos.this, getString(R.string.txtCorrectoperation), getString(R.string.txtSuccessfully_Print));
                            }
                            else if(i==0)
                            {

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

        }, 0, 5000);
    }
    private void InsertRound2(int CustomerID,int routeDetailsID,int RouteStatus,String Note)
    {
        //Global.GetLocation(CashVanPosDashBoard.this);
      /*  progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);*/

        //   Bitmap signature =null;
        String txtsignature ="NoPic";
           /* if(bitsignature!=null)
            {
                signature =bitsignature;
                txtsignature = ImageUtil.convert(signature);
            }*/


        //  Bitmap imgg =null;
        String txtimgg ="NoPic";
          /*  if(bitPohto!=null)
            {
                imgg=bitPohto;
                txtimgg = ImageUtil.convert(imgg);

            }*/
        int EmpID=conRoyal.IDUser;
        int BatteryPercentage=Global.GetBatteryLevel(frmCashVanPos.this);

        String locationOnMap=Global.locaion;
        int CreationUserID=conRoyal.IDUser;
        String CreationDate="";

        String Notes=Note;


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(conRoyal.ConIpRoyal2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        ClsCashVanPosRound pp = new ClsCashVanPosRound(conRoyal.ConnectionString, CustomerID,EmpID,txtimgg, BatteryPercentage,txtsignature, locationOnMap,CreationUserID,CreationDate,routeDetailsID,RouteStatus,Notes);
        Call<ClsCashVanPosRound> call = jsonPlaceHolderApi.InsertCashVanPosRound(pp);
        call.enqueue(new Callback<ClsCashVanPosRound>() {
            @Override
            public void onResponse(Call<ClsCashVanPosRound> call, retrofit2.Response<ClsCashVanPosRound> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equals("True")) {
                        progressDialog= new ProgressDialog(frmCashVanPos.this);
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        progressDialog.setContentView(R.layout.progress_dialogandtext);
                        TextView textView=progressDialog.findViewById(R.id.idLoading2);
                        textView.setText(getString(R.string.Please_approval));
                        Button btnBack=progressDialog.findViewById(R.id.btnRetREAT);
                        timer();
                        btnBack.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                InsertRound(spinnerCustomerID,routeDetailsHomeID,9);
                                if(progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }
                        });
                        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    } else {
                        if(progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ClsCashVanPosRound> call, Throwable t) {

                Global.ShowDataSavedToSqlLitePopup(frmCashVanPos.this);

            }


        });
    }
    private void selectMAXCustomerID()
    {
        try {
            conRoyal.UrlRoyal_Query(conRoyal.ConnectionString,"select MaxCreditLimit from customer where ID="+spinnerCustomerID+"");

            mQueue = Volley.newRequestQueue(frmCashVanPos.this);
            JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Query, null, new Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONArray response) {
                    try {
                            JSONObject jsonArray = response.getJSONObject(0);
                             MaxCreditLimit = jsonArray.getDouble("MaxCreditLimit");
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
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }



    private void selecttotalBalanceByCustomerID()
    {
        try {
            conRoyal.UrlRoyal_Query(conRoyal.ConnectionString,"select isnull((sum(Debit) - sum(Credit)) ,0) as totalBalance from JournalEntryDetails where CustomerID="+spinnerCustomerID+"  and HeaderID in (select ID from JournalEntryHeaders where ISNULL(IsDelete,0)=0) ");

            mQueue = Volley.newRequestQueue(frmCashVanPos.this);
            JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Query, null, new Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        DecimalFormat DF = new DecimalFormat("######0.000");
                        JSONObject jsonArray = response.getJSONObject(0);
                     double   totalBalance = jsonArray.getDouble("totalBalance");
                        TotalBalance.setText(DF.format(totalBalance));
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
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }




    private void SavePayment(String Amountt)
    {


            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            String VoucherDate=String.valueOf(DateFormat.format("dd-MM-yyyy", new java.util.Date()));
            int BranchID=Global.LoginBranchID;
            int CostCenterID=0;
            int RevenuCenterID=0;
            int ProfitCenterID=0;
            double Amount=Double.parseDouble(Global.convertToEnglish(Amountt));
            int MemoID= Integer.parseInt(Global.convertToEnglish("0"));
            int LoginUserID = conRoyal.IDUser;
            int VoucherManualNumber=Integer.parseInt(Global.convertToEnglish("0"));
            String Receiver="دفعة من السيد "+spinnerCustomerName + " ب تاريخ "+VoucherDate;
            int CustomerId=spinnerCustomerID;
            int CashID=Global.LoginCashID;

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(conRoyal.ConIpRoyal2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
            post_CashVoucher pp = new post_CashVoucher(conRoyal.ConnectionString, VoucherDate,BranchID,CostCenterID,RevenuCenterID,ProfitCenterID, Amount,Notes, MemoID, LoginUserID, VoucherManualNumber, Receiver, CustomerId,CashID,Integer.parseInt(InvoiceNo));
            Call<post_CashVoucher> call = jsonPlaceHolderApi.InsertOrder(pp);
            call.enqueue(new Callback<post_CashVoucher>() {
                @Override
                public void onResponse(Call<post_CashVoucher> call, retrofit2.Response<post_CashVoucher> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("true")) {
                            progressDialog.dismiss();

                          //  ShowDataSaved(frmCashVanPos.this, getString(R.string.txtCorrectoperation), getString(R.string.txtmigratedsystem));
                        } else {
                            progressDialog.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<post_CashVoucher> call, Throwable t) {

                    Global.ShowDataSavedToSqlLitePopup(frmCashVanPos.this);

                }


            });

    }

    private void Save(final String amountpaid)
    {
        try {
        displayLocationSettingsRequest(this);
        sumTotalOrder();
        if(Vald()) {




            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            Bitmap signature =null;
            String txtsignature ="NoPic";
            if(bitsignature!=null)
            {
                signature =bitsignature;
                txtsignature = ImageUtil.convert(signature);
            }
            Bitmap imgg =null;
            String txtimgg ="NoPic";
            if(bitPohto!=null)
            {
                imgg=bitPohto;
                txtimgg = ImageUtil.convert(imgg);

            }
            int TaxID=spinnerInvoiceTaxTypeID;
            int StoreId=Global.LoginStoreID;
            int SettelmentWayId=spinnerSettelmentWayID;
            String SettelmentWayName=spinnerSettelmentWayName;
            int CustomerId=spinnerCustomerID;
            String CustomerName=spinnerCustomerName;
            int SalesManID=spinnerCustomerSalesManID;
            int CashId=spinnerCashID;
            String Note=Notes;
            double TotalQty=Double.parseDouble(Global.convertToEnglish(this.TotalQty.getText().toString()));
            int LoginBranchID= Global.LoginBranchID;
            String LoginBranchName= Global.LoginBranchName;
            String LoginStoreName= Global.LoginStoreName;
            String InvoiceTaxTypeName= spinnerInvoiceTaxTypeName;

            int LoginUserID=conRoyal.IDUser;
            String LoginUserName=conRoyal.UserName;
            double TotalBeforeTax=Double.parseDouble(Global.convertToEnglish(this.TotalBeforeTax.getText().toString()));
            double TotalTaxAmount=Double.parseDouble(Global.convertToEnglish(this.TotalTaxAmount.getText().toString()));
            double TotalAfterTax=Double.parseDouble(Global.convertToEnglish(this.TotalOrder.getText().toString()));
            double LocationX=x;
            double LocationY=y;


          //  if(TotalAfterTax > MaxCreditLimit)
        //    {
         //       if(progressDialog.isShowing())
          //      {
         //           progressDialog.dismiss();
          //      }
         //       ShowDataSaved2(this,getString(R.string.Warning)+"  "+CustomerName,getString(R.string.txtlimitCustomer),TotalAfterTax);
         //       return;
         //   }

            JSONArray ArrayItemSelected = new JSONArray();
            JSONObject object = null;
            for (int i = 0; i < listNew.size(); i++) {
                object = new JSONObject();
                try {

                    object.put("ItemID", listNew.get(i).getID());
                    object.put("ItemName", listNew.get(i).getItemName());
                    object.put("Qty", listNew.get(i).getQty());
                    if(TransType==15)
                    {
                        object.put("priceBeforeTax", listNew.get(i).getWholeSalePrice());
                        object.put("PriceAfterTax",  listNew.get(i).getWholeSalePrice());
                    }
                    else
                    {
                        object.put("priceBeforeTax", Double.parseDouble(listNew.get(i).getPrice())/(1+listNew.get(i).getTaxRatio()));
                        object.put("PriceAfterTax",  listNew.get(i).getPrice());
                    }

                    object.put("SalesTaxID", listNew.get(i).getSalesTaxID());
                    object.put("taxPercentage", listNew.get(i).getTaxRatio());
                    object.put("TaxAmountPerUnit", (Double.parseDouble(listNew.get(i).getPrice())/(1+listNew.get(i).getTaxRatio())) * (listNew.get(i).getTaxRatio()));

                    object.put("QTYexisting", listNew.get(i).getBasedQTY());
                    object.put("NumberBaskets", listNew.get(i).getNumberbaskets());
                    object.put("Baskettype", listNew.get(i).getBasketTypeID());
                    object.put("EmptyQty", listNew.get(i).getEmptyQTY());
                    //object.put("Status", listNew.get(i).getStatus());


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ArrayItemSelected.put(object);
            }
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(conRoyal.ConIpRoyal2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
            post_SaveCashVan pp = new post_SaveCashVan(conRoyal.ConnectionString, txtsignature,txtimgg,0, TaxID,StoreId, SettelmentWayId,SettelmentWayName,InvoiceTaxTypeName,CustomerId,CustomerName, SalesManID, CashId, Note, TotalQty, LoginBranchID,LoginBranchName,LoginStoreName,LoginUserID,LoginUserName,TotalBeforeTax,TotalTaxAmount,TotalBeforeTax+TotalTaxAmount,TransType, ArrayItemSelected.toString(),Global.DataSourceName);
            Call<post_SaveCashVan> call = jsonPlaceHolderApi.InsertOrder(pp);
            call.enqueue(new Callback<post_SaveCashVan>() {
                @Override
                public void onResponse(Call<post_SaveCashVan> call, retrofit2.Response<post_SaveCashVan> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("true")) {
                            InvoiceNo=response.body().getInvoiceNoBefore();
                            InsertRound(spinnerCustomerID,routeDetailsHomeID,1);
                            progressDialog.dismiss();
                            if(Global.IsShowISPaid==true && amountpaid!="0")
                            {
                                SavePayment(amountpaid);
                            }

                            ShowDataSaved(frmCashVanPos.this, getString(R.string.txtCorrectoperation), getString(R.string.txtSuccessfully_Print));
                        } else {
                            progressDialog.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<post_SaveCashVan> call, Throwable t) {

                    Global.ShowDataSavedToSqlLitePopup(frmCashVanPos.this);

                }


            });
        }
        }
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void PrintPeper()
    {
        try {
            if(Vald()) {
                progressDialog = new ProgressDialog(this);
                progressDialog.setCancelable(false);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_dialog);
                progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                Bitmap signature =null;
                String txtsignature ="NoPic";
                if(bitsignature!=null)
                {
                    signature =bitsignature;
                    txtsignature = ImageUtil.convert(signature);
                }


                Bitmap imgg =null;
                String txtimgg ="NoPic";
                if(bitPohto!=null)
                {
                    imgg=bitPohto;
                    txtimgg = ImageUtil.convert(imgg);

                }
                int TaxID=spinnerInvoiceTaxTypeID;
                int StoreId=Global.LoginStoreID;
                int SettelmentWayId=spinnerSettelmentWayID;
                String SettelmentWayName=spinnerSettelmentWayName;
                int CustomerId=spinnerCustomerID;
                String CustomerName=spinnerCustomerName;
                int SalesManID=spinnerCustomerSalesManID;
                int CashId=spinnerCashID;
                String Note=Notes;
                double TotalQty=Double.parseDouble(Global.convertToEnglish(this.TotalQty.getText().toString()));
                int LoginBranchID= Global.LoginBranchID;
                String LoginBranchName= Global.LoginBranchName;
                String LoginStoreName= Global.LoginStoreName;
                String InvoiceTaxTypeName= spinnerInvoiceTaxTypeName;

                int LoginUserID=conRoyal.IDUser;
                String LoginUserName=conRoyal.UserName;
                double TotalBeforeTax=Double.parseDouble(Global.convertToEnglish(this.TotalBeforeTax.getText().toString()));
                double TotalTaxAmount=Double.parseDouble(Global.convertToEnglish(this.TotalTaxAmount.getText().toString()));
                double TotalAfterTax=Double.parseDouble(Global.convertToEnglish(this.TotalOrder.getText().toString()));

                JSONArray ArrayItemSelected = new JSONArray();
                JSONObject object = null;
                for (int i = 0; i < listNew.size(); i++) {
                    object = new JSONObject();
                    try {

                        object.put("ItemID", listNew.get(i).getID());
                        object.put("ItemName", listNew.get(i).getItemName());
                        object.put("Qty", listNew.get(i).getQty());
                        object.put("priceBeforeTax", listNew.get(i).getSalesPriceBeforeTax());
                        object.put("SalesTaxID", listNew.get(i).getSalesTaxID());
                        object.put("taxPercentage", 0);
                        object.put("TaxAmountPerUnit", listNew.get(i).getTaxAmount());
                        object.put("PriceAfterTax", listNew.get(i).getPrice());
                        object.put("QTYexisting", listNew.get(i).getBasedQTY());
                        object.put("NumberBaskets", listNew.get(i).getNumberbaskets());
                        object.put("Baskettype", listNew.get(i).getBasketTypeID());
                        object.put("EmptyQty", listNew.get(i).getEmptyQTY());
                        //object.put("Status", listNew.get(i).getStatus());


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    ArrayItemSelected.put(object);
                }
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(conRoyal.ConIpRoyal2)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
                post_SaveCashVan pp = new post_SaveCashVan(conRoyal.ConnectionString, txtsignature,txtimgg,Integer.parseInt(InvoiceNo), TaxID,StoreId, SettelmentWayId,SettelmentWayName,InvoiceTaxTypeName,CustomerId,CustomerName, SalesManID, CashId, Note, TotalQty, LoginBranchID,LoginBranchName,LoginStoreName,LoginUserID,LoginUserName,TotalBeforeTax,TotalTaxAmount,TotalAfterTax, TransType,ArrayItemSelected.toString(),Global.DataSourceName);
                Call<post_SaveCashVan> call = jsonPlaceHolderApi.PrintSaveOrderCashVanPOS(pp);
                call.enqueue(new Callback<post_SaveCashVan>() {
                    @Override
                    public void onResponse(Call<post_SaveCashVan> call, retrofit2.Response<post_SaveCashVan> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus().equals("true")) {
                              //  Toast.makeText(frmCashVanPos.this,"dd1",Toast.LENGTH_LONG).show();
                                Print(response.body().getPohtoBase64());

                                progressDialog.dismiss();

                            } else {
                                progressDialog.dismiss();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<post_SaveCashVan> call, Throwable t) {
                        //Global.ShowDataSavedToSqlLitePopup(frmCashVanPos.this);
                        progressDialog.dismiss();
                        Toast.makeText(frmCashVanPos.this, "إتصال الإنترنت غير مستقر ، لم تتم عملية الطباعة يرجى المحاولة مره اخرى", Toast.LENGTH_SHORT).show();

                    }


                });
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(frmCashVanPos.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }
    private void  GetInviceBeforeAndPrint()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        conRoyal.UrlRoyal_PrintSaveOrderCashVanPOSBefore(conRoyal.ConnectionString,Global.LoginStoreID,conRoyal.IDUser,conRoyal.UserName,Global.LoginStoreName);

        mQueue = Volley.newRequestQueue(frmCashVanPos.this);

        JsonObjectRequest request =new JsonObjectRequest(Request.Method.POST, conRoyal.Url_GetPrintSaveOrderCashVanPOSBefore, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONObject response) {
                try {
                   // JSONObject jsonArray = response.getAsJsonArray(0);
                    JSONObject jObject = new JSONObject(String.valueOf(response));
                    Print(jObject.getString("PohtoBase64"));
                    progressDialog.dismiss();

                } catch (JSONException e) {
                    Toast.makeText(frmCashVanPos.this,e.getMessage(),Toast.LENGTH_SHORT);
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(frmCashVanPos.this,error.getMessage(),Toast.LENGTH_SHORT);
                progressDialog.dismiss();
            }
        });

        mQueue.add(request);
    }
    public void sumTotalOrder()
    {
        double TotalRows=0;
        double TotalQty=0;
        double TotalBeforeTax=0;
        double TotalTaxAmount=0;
        if(Adapter.alistItems.size()>0) {
            for (int i = 0; i < Adapter.alistItems.size(); i++) {
                TotalRows += Double.parseDouble(Adapter.alistItems.get(i).getPrice()) * Double.parseDouble(Adapter.alistItems.get(i).getQty());
                TotalQty += Double.parseDouble(Adapter.alistItems.get(i).getQty());
                TotalBeforeTax += (Double.parseDouble(Adapter.alistItems.get(i).getPrice()) /(1+Adapter.alistItems.get(i).getTaxRatio())) * Double.parseDouble(Adapter.alistItems.get(i).getQty());
                TotalTaxAmount += ((((Double.parseDouble(Adapter.alistItems.get(i).getPrice()) /(1+Adapter.alistItems.get(i).getTaxRatio()))) * Adapter.alistItems.get(i).getTaxRatio())* Double.parseDouble(Adapter.alistItems.get(i).getQty()));
            }
            DecimalFormat DF = new DecimalFormat("######0.000");
            TotalOrder.setText(DF.format(TotalRows));
            this.TotalBeforeTax.setText(DF.format(TotalBeforeTax));
            this.TotalTaxAmount.setText(DF.format(TotalTaxAmount));
            TotalOrder.setText(DF.format(TotalRows));
            this.TotalQty.setText(DF.format(TotalQty));
        }
        else
        {
            this.TotalOrder.setText("0.000");
            this.TotalBeforeTax.setText("0.000");
            this.TotalTaxAmount.setText("0.000");
            this.TotalOrder.setText("0.000");
            this.TotalQty.setText("0.000");
        }
    }



    public  void ShowYouPayment(Context c, String Header, String Details)
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
        ImageView   box_image_yes_or_no=(ImageView)epicDialog.findViewById(R.id.box_image_yes_or_no);
        box_image_yes_or_no.setImageResource(R.drawable.ic_description_black_24dp);
        btnCancel=(Button)epicDialog.findViewById(R.id.btn_box_yes_or_no_close);
        btnOk=(Button)epicDialog.findViewById(R.id.btn_Ok);

        textHeader.setText(Header);
        textDetails.setText(Details);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //----------------------------- Dailog ItemName_New--------------------------------------------------------
                AlertDialog.Builder builder = new AlertDialog.Builder(frmCashVanPos.this);
                builder.setTitle("إدخال الدفعة ");
                builder.setIcon(R.drawable.sms);
                builder.setMessage(" يرجى إدخال قيمة الدفعة   ");
                 final EditText inputEditQty;
                inputEditQty = new EditText(frmCashVanPos.this);
                inputEditQty.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                builder.setView(inputEditQty);
                builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String Amount = inputEditQty.getText().toString();
                        if (Amount.equals(""))
                        {
                            Toast.makeText(frmCashVanPos.this, "يرجى إدخال قيمة الدفعة", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            Save(Amount);
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
        });
        closebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save("0");
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save("0");
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }


    public  void ShowDataSaved(Context c, String Header, String Details)
    {
        final Dialog epicDialog = new Dialog(c);
        ImageView closebox,ico;
        Button btnCancel,btnOk;
        TextView textHeader,textDetails;

        epicDialog.setContentView(R.layout.boxdailog_yes_or_no);
        epicDialog.setCancelable(false);
        textHeader=(TextView) epicDialog.findViewById(R.id.txtbox_yes_or_no1);
        textDetails=(TextView)epicDialog.findViewById(R.id.txtbox_yes_or_no2);
        closebox=(ImageView)epicDialog.findViewById(R.id.bt_box_yes_or_no);
        btnCancel=(Button)epicDialog.findViewById(R.id.btn_box_yes_or_no_close);
        btnOk=(Button)epicDialog.findViewById(R.id.btn_Ok);
        ico=(ImageView) epicDialog.findViewById(R.id.box_image_yes_or_no);

        if(TransType==15)
        {
            textHeader.setText("تم الحفظ بنجاح");
            textDetails.setText("تم الحفظ بنجاح ، هل تريد إنشاء سند جديد");
            ico.setImageResource(R.drawable.ic_check_circle_green_24dp);
            btnCancel.setVisibility(View.GONE);
        }
        else
        {
            textHeader.setText(Header);
            textDetails.setText(Details);
        }


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Global.GetLastPrinter(frmCashVanPos.this);
                    if(TransType==15)
                    {
                        epicDialog.dismiss();
                        finish();
                        Intent intent=new Intent(frmCashVanPos.this,frmCashVanPos.class);
                        intent.putExtra("TransType",15);
                        intent.putExtra("CustomerID",-2);
                        intent.putExtra("routeDetailsID",0);
                        startActivity(intent);
                    }
                    else
                    {
                        if (Global.TypePrinterConnected == 4) {
                            if (!isConnected && mPrinter == null) {
                                // Global.connectPrinter(frmCashVanPos.this, "frmCashVanPos");
                                Global.GetLastPrinter(frmCashVanPos.this);
                                if (!Global.addressBlutooth.equals("")) {
                                    connClick();
                                }
                                //  PrintUtils.printImagea(bitmap, mPrinter);

                            } else {
                                PrintPeper();
                            }
                        } else if(Global.TypePrinterConnected==5){
                                connectDeviceWoosim();
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                                //connectDeviceWoosim();
                                PrintPeper();
                        }
                        else {
                            PrintPeper();
                        }

                    }


                }
                catch (Exception ex)
                {
                    Toast.makeText(frmCashVanPos.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
                }

            }
        });
        closebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TransType==15)
                {
                    epicDialog.dismiss();
                    finish();
                    Intent intent=new Intent(frmCashVanPos.this,frmCashVanPos.class);
                    intent.putExtra("TransType",15);
                    intent.putExtra("CustomerID",-2);
                    intent.putExtra("routeDetailsID",0);
                    startActivity(intent);
                }
                else {
                    epicDialog.dismiss();
                    startActivity(new Intent(frmCashVanPos.this, CashVanPosDashBoard.class));
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TransType==15)
                {
                    epicDialog.dismiss();
                    finish();
                    Intent intent=new Intent(frmCashVanPos.this,frmCashVanPos.class);
                    intent.putExtra("TransType",15);
                    intent.putExtra("CustomerID",-2);
                    intent.putExtra("routeDetailsID",0);
                    startActivity(intent);
                }
                else {
                    epicDialog.dismiss();
                    startActivity(new Intent(frmCashVanPos.this, CashVanPosDashBoard.class));
                }
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getExtras().get("data")!=null) {
            Bitmap bb = (Bitmap) data.getExtras().get("data");
            bitPohto = bb;
        }   //finish();

        if(resultCode==0)
        {
            displayLocationSettingsRequest(this);
        }

    }
}
