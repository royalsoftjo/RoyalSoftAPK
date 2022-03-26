package com.example.royalsoftapk;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Looper;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tscdll.TSCActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.printer.ZebraPrinter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import jpos.POSPrinter;

import static android.content.Context.BATTERY_SERVICE;

public class Global {

    //-----------------------------------------------
    public static boolean IsShowSubCategory=false;
    public static boolean IsShowISPaid=true;
    public static int LoginBranchID;
    public static String LoginBranchName;
    public static int LoginStationID;
    public static int LoginStoreID;
    public static String LoginStoreName;
    public static int LoginCashID;
    public static int WorkDayID;
    public static String LoginWorkDayDate;
    public static Boolean IsUsePos;
    public static Boolean IsUseManagement;
    public static Boolean IsUser;
    public static Boolean IsAdmin;
    public static Boolean IsMergeOrder;
    public static int WaiterID;
    public static boolean IsWaiter;

    public static String locaion="";
    public static int TypePrinterConnected=4;
    //----------------------------------------------
    public static boolean IsHoldResturent=true;
    public static boolean IsCashVan=false;
    public static boolean IsProcessingorder=false;
    public static boolean IsCustomerOther=false;
    public static boolean IsHasBasket=false;
    public static boolean IsShowPriceChange=false;

    public static boolean Isfeedback=false;
    public static boolean IsOrderSales=false;
    public static String DataSourceName="Nadej";
    public static int IsStatic=1;
    //-----------------------------------------------
    public static boolean StatusBlutooth;
    public static String logicalName="SPP-R300";
    public static POSPrinter posPrinter;
    //--------- Zebra --------------------//
    public  static String addressBlutooth="a";
    public static ZebraPrinter printerZebra;
    public static Connection connectionZebra;
    //-------- Zaebra-------------------//


    //--------------Tsc-------------------//
   public static TSCActivity TscDll ;
    //--------------Tsc-------------------//

    public static boolean save=false;
    public static ArrayList<Cls_ItemsInvoiceSales> listItem=new ArrayList<Cls_ItemsInvoiceSales>();
    //----------- Array Branch ---------------------------------------------------//
    public static ArrayList<String> ListName_Branch = new ArrayList<String>();
    public static   ArrayList<String> ListID_Branch = new ArrayList<>();
    //----------- Array Branch ---------------------------------------------------//

    //----------- Array Store ---------------------------------------------------//
    public static ArrayList<String> ListName_Store = new ArrayList<String>();
    public static   ArrayList<String> ListID_Store = new ArrayList<>();
    public static   ArrayList<String> ListBranchid_Store = new ArrayList<>();
    //----------- Array Store ---------------------------------------------------//

    //----------- Array Supplier ---------------------------------------------------//
    public static ArrayList<String> ListName_Supplier = new ArrayList<String>();
    public static   ArrayList<String> ListID_Supplier = new ArrayList<>();
    //----------- Array Supplier ---------------------------------------------------//

    //----------- Array Customer ---------------------------------------------------//
    public static ArrayList<String> ListName_Customer = new ArrayList<String>();
    public static   ArrayList<String> ListID_Customer = new ArrayList<>();
    //----------- Array Customer ---------------------------------------------------//

    //----------- Array Category ---------------------------------------------------//
    public static ArrayList<String> ListName_Category = new ArrayList<String>();
    public static   ArrayList<String> ListID_Category = new ArrayList<>();
    //----------- Array Category ---------------------------------------------------//

    public static void ShowDataSavedToSqlLitePopup(final Context c)
    {
        final Dialog epicDialog = new Dialog(c);
        ImageView closebox;
        Button btnCancel;
        epicDialog.setContentView(R.layout.maseege_saved_to_sqllite);

        closebox=(ImageView)epicDialog.findViewById(R.id.bt_N_close_access);
        btnCancel=(Button)epicDialog.findViewById(R.id.btn_Cancel_Now);


        closebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(c, DASHWORD.class);
                c.startActivity(myIntent);
                epicDialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(c, DASHWORD.class);
                c.startActivity(myIntent);
                epicDialog.dismiss();
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }
    public static void displayLocationSettingsRequest(final Context context, final Activity act) {
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
                            status.startResolutionForResult(act, 10);
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
    private static void  SaveCustomers(final Context C, String AName, String Tel1, final Dialog dialog)
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
    private class myTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }


    public static int GetBatteryLevel(Activity act)
    {
        BatteryManager bm = (BatteryManager) act.getSystemService(BATTERY_SERVICE);
        int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        return batLevel;
    }
    public static void  GetLocation(Activity act,Context context) {
        try {
            if (ActivityCompat.checkSelfPermission(act, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(act, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                getLocation2(act);
            } else {
                //displayLocationSettingsRequest(context,act);
                getLocation2(act);
            }
        } catch (Exception ex) {

        }
    }

    public static void   getLocation2(final Activity act) {

        try {
            final LocationRequest locationRequest=new LocationRequest();
            locationRequest.setInterval(100);
            locationRequest.setFastestInterval(300);
            locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

            LocationServices.getFusedLocationProviderClient(act).requestLocationUpdates(locationRequest,new LocationCallback(){
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    super.onLocationResult(locationResult);
                    LocationServices.getFusedLocationProviderClient(act).removeLocationUpdates(this);
                    if(locationRequest!=null && locationResult.getLocations().size()>0)
                    {
                       double x=locationResult.getLocations().get(locationResult.getLocations().size()-1).getLatitude();
                       double y=locationResult.getLocations().get(locationResult.getLocations().size()-1).getLongitude();
                       Global.locaion=x+","+y;

                    }
                    else
                    {
                        Global.locaion=null;
                    }
                }
            }, Looper.getMainLooper());
        }
        catch (Exception ex)
        {
            Toast.makeText(act,ex.getMessage(),Toast.LENGTH_SHORT);
            Global.locaion=null;
        }
    }

    public static void ShowAccessPopup(Activity act, int Text1, int Text2, String Colors)
    {
        final Dialog epicDialog;
        ImageView closebox;
        Button btnCancel,btnSave;
        epicDialog = new Dialog(act);
        epicDialog.setContentView(R.layout.box_custum_auto);
        closebox=(ImageView)epicDialog.findViewById(R.id.bt_N_close_access);
        btnCancel=(Button)epicDialog.findViewById(R.id.btn_Cancel_Now);
        CardView card=epicDialog.findViewById(R.id.CardViewAutoCustum);
        TextView text1=epicDialog.findViewById(R.id.boxtxt1);
        text1.setText(act.getResources().getString(Text1));
        TextView text2=epicDialog.findViewById(R.id.boxtxt2);
        text2.setText(act.getResources().getString(Text2));
        if(!Colors.equals("")) {
            card.setBackgroundColor(Color.parseColor(Colors));
        }
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
    public static void ShowAccessPopup2(Activity act, String Text1, String Text2, String Colors)
    {
        final Dialog epicDialog;
        ImageView closebox;
        Button btnCancel,btnSave;
        epicDialog = new Dialog(act);
        epicDialog.setContentView(R.layout.box_custum_auto);
        closebox=(ImageView)epicDialog.findViewById(R.id.bt_N_close_access);
        btnCancel=(Button)epicDialog.findViewById(R.id.btn_Cancel_Now);
        CardView card=epicDialog.findViewById(R.id.CardViewAutoCustum);
        TextView text1=epicDialog.findViewById(R.id.boxtxt1);
        text1.setText(Text1);
        TextView text2=epicDialog.findViewById(R.id.boxtxt2);
        text2.setText(Text2);
        if(!Colors.equals("")) {
            card.setBackgroundColor(Color.parseColor(Colors));
        }
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
    public static void ShowDataSaved(Context c,String Header,String Details)
    {
        final Dialog epicDialog = new Dialog(c);
        ImageView closebox;
        Button btnCancel;
        TextView textHeader,textDetails;

        epicDialog.setContentView(R.layout.from_failog);
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
    public String convertToArabic(int value)
    {
        String newValue = (((((((((((value+"")
                .replaceAll("1", "١")).replaceAll("2", "٢"))
                .replaceAll("3", "٣")).replaceAll("4", "٤"))
                .replaceAll("5", "٥")).replaceAll("6", "٦"))
                .replaceAll("7", "٧")).replaceAll("8", "٨"))
                .replaceAll("9", "٩")).replaceAll("0", "٠"));
        return newValue;
    }
    public static String convertToEnglish(String value)
    {
        String newValue = (((((((((((value+"")
                .replaceAll("١", "1")).replaceAll("٢", "2"))
                .replaceAll("٣", "3")).replaceAll("٤", "4"))
                .replaceAll("٥", "5")).replaceAll("٦", "6"))
                .replaceAll("٧", "7")).replaceAll("٨", "8"))
                .replaceAll("٩", "9")).replaceAll("٠", "0")).replaceAll("٫",".");
        return newValue;
    }
    public static void updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());
        config.locale = locale;
        res.updateConfiguration(config, res.getDisplayMetrics());
    }
    public static void msgBox(Context c, String Header, String Details, int color , int img)
    {
        final Dialog epicDialog = new Dialog(c);
        ImageView closebox,image;
        Button btnCancel;
        TextView textHeader,textDetails;
        CardView card;
        epicDialog.setContentView(R.layout.from_failog);
        textHeader=(TextView) epicDialog.findViewById(R.id.txtHeaderDailog);
        textDetails=(TextView)epicDialog.findViewById(R.id.txtBodyDailog);
        closebox=(ImageView)epicDialog.findViewById(R.id.bt_N_close_access);
        btnCancel=(Button)epicDialog.findViewById(R.id.btn_Cancel_Now);
        card=(CardView)epicDialog.findViewById(R.id.CardDaliog);
        image=(ImageView)epicDialog.findViewById(R.id.Imagev);

        card.setCardBackgroundColor(color);
        image.setImageResource(img);
        textHeader.setText(Header);
        textDetails.setText(Details);
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



    public static Bitmap StringToBitMap(String encodedString){
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
    public static void connectPrinter(Activity activity,String frm) {
            Intent intent = new Intent(activity , frmDeviceBlooth.class);
            intent.putExtra("frm",frm);
            activity.startActivity(intent);

    }
    public static  void GetLastPrinter(Context c)
    {

        try {
            DataBaseHelper da=new DataBaseHelper(c);
            //--------------- Profile Login ------------------------------------------------//
            int ID,TypeID;
            String AName,Mac,TypeName,CreationDate;
            //--------------- Profile Login ------------------------------------------------//
            SQLiteDatabase db2 = da.getWritableDatabase();
            Cursor res = db2.rawQuery("select * from Tbl_SettingDeviceBltooth",null);
            if(res.getCount()>0) {
                while (res.moveToNext()) {
                    ID = res.getInt(0);
                    AName = res.getString(1);
                    TypeID = res.getInt(2);
                    Global.TypePrinterConnected= TypeID;
                    TypeName = res.getString(3);
                    Mac = res.getString(4);
                    CreationDate = res.getString(5);
                    Global.addressBlutooth=Mac;

                }

                /*Adapter = new AdapterBluetoothDevices(frmDeviceBlooth.this, ListDeviceAvilable, frmDeviceBlooth.this);
                listView.setAdapter(Adapter);
                listView.setOnItemClickListener(frmDeviceBlooth.this);*/

            }
            else
            {
                Global.msgBox(c,"عملية غير صحيحة","لم تتم عملية الاتصال", Color.RED,R.drawable.ic_error_outline_black_24dp);
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(c,ex.getMessage(),Toast.LENGTH_SHORT);
        }
    }

}
