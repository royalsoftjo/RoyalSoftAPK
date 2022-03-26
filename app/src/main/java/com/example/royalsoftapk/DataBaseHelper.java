package com.example.royalsoftapk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Base64;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DataBase_Name="RoyalLite";
    Date date2 = new Date();
    //--------------- Coulmn SettingsDevice --------------------------------------//
    public static final int colID=0;
    public static final int colUpdateDay=1;
    public static final int colUpdateBranch=2;
    public static final int colUpdateSupplier=3;
    public static final int colUpdateStore=4;
    public static final int colUpdateItems=5;
    public static final int colUpdateCustomer=6;
    public static final int colUpdateEmp=7;
    public static final int colUpdateCategory=8;
    public static final int colDatamigration=9;
    public ProgressBar textlodaing;
    ProgressDialog progressDialog;
    //--------------- Coulmn SettingsDevice --------------------------------------//


    public DataBaseHelper(Context context) {
        super(context, DataBase_Name, null, 1);
    }


    public void CteateTable(Activity c)
    {


        LoadData(c);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
      //  db.execSQL("create table if not exists SettingsDevice (ID INTEGER,UpdateDay INTEGER,UpdateBranch INTEGER,UpdateSupplier INTEGER,UpdateStore INTEGER,UpdateItems INTEGER,UpdateCustomer INTEGER,UpdateEmp INTEGER,UpdateCategory INTEGER,Datamigration INTEGER)");
     //   SQLiteDatabase db=this.getWritableDatabase();
        try {
            db.execSQL("create table if not exists SettingsDevice (ID INTEGER PRIMARY KEY,UpdateDay INTEGER,UpdateBranch INTEGER,UpdateSupplier INTEGER,UpdateStore INTEGER,UpdateItems INTEGER,UpdateCustomer INTEGER,UpdateEmp INTEGER,UpdateCategory INTEGER,Datamigration INTEGER)");
            db.execSQL("create table if not exists Updeted (ID INTEGER PRIMARY KEY AUTOINCREMENT,DateUpdated TEXT)");
            db.execSQL("create table if not exists Branch (ID INTEGER ,AName TEXT)");
            db.execSQL("create table if not exists Store (ID INTEGER ,AName TEXT,Branchid INTEGER)");
            db.execSQL("create table if not exists Supplier (ID INTEGER ,AName TEXT)");
            db.execSQL("create table if not exists Customer (ID INTEGER ,AName TEXT)");
            db.execSQL("create table if not exists Category (ID INTEGER ,AName TEXT)");
            db.execSQL("create table if not exists Items (ID INTEGER ,ItemName TEXT,MainBarcode TEXT,SalesPrice TEXT ,picture TEXT,CategoryID INTEGER)");
            db.execSQL("create table if not exists PDAIncomeVoucherHeader (ID INTEGER PRIMARY KEY AUTOINCREMENT ,BranchId INTEGER,StoreId INTEGER,VendorId INTEGER ,ReceiverName TEXT,Note TEXT,VoucherNumber INTEGER,VoucherDate TEXT,Reference TEXT,TransactionID INTEGER,CustomerId INTEGER,PaymentId INTEGER,SalesManID INTEGER,IsTransaction INTEGER)");
            db.execSQL("create table if not exists PDAIncomeVoucherDetails (ID INTEGER PRIMARY KEY AUTOINCREMENT ,HeaderId INTEGER,ItemId INTEGER,MainBarcode TEXT ,ItemName TEXT,QTY TEXT,rowindex INTEGER,PurchasePrice TEXT)");
            db.execSQL("create table if not exists Tbl_User (ID INTEGER PRIMARY KEY ,Comapny_Name TEXT,UserName TEXT,Password TEXT ,IP_Static TEXT,DataBase_Name TEXT,User_DB TEXT,Password_DB TEXT,StartDate TEXT,EndDate TEXT,Activate_User INTEGER,SalesManID INTEGER)");
            db.execSQL("create table if not exists Tbl_Access_Screen (ID INTEGER PRIMARY KEY AUTOINCREMENT ,User_Id INTEGER,Screen_id INTEGER,Access_User INTEGER)");
            db.execSQL("create table if not exists Tbl_SettingDeviceBltooth (ID INTEGER PRIMARY KEY AUTOINCREMENT ,Aname TEXT,TypeID INTEGER,TypeName TEXT,Mac TEXT,CreationDate TEXT)");
            db.execSQL("create table if not exists Tbl_Connected (ID INTEGER PRIMARY KEY AUTOINCREMENT ,IP TEXT,TypeID INTEGER,TypeCon INTEGER,DataSorce TEXT)");
        }
        catch(Exception ex)
        {
        }
    }
    public static enum Transaction {
        Insert_Not_transferred(1), Insert_transferred(0), Update_transferred(2),Update_Not_transferred(3), Delete_transferred(4),Delete_Not_transferred(5);
        private final int value;
        Transaction(int value) {
            this.value=value;
        }
        public int getValue()
        {
            return value;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public  boolean InsertSettingsDevice(int ID,int UpdateDay,int UpdateBranch,int UpdateSupplier,int UpdateStore,int UpdateItems,int UpdateCustomer,int UpdateEmp,int UpdateCategory,int Datamigration)
    {
        SQLiteDatabase db=this.getWritableDatabase();

        db.delete("SettingsDevice",null,null);
            ContentValues contentValues = new ContentValues();
            contentValues.put("ID", ID);
            contentValues.put("UpdateDay", UpdateDay);
            contentValues.put("UpdateBranch", UpdateBranch);
            contentValues.put("UpdateSupplier", UpdateSupplier);
            contentValues.put("UpdateStore", UpdateStore);
            contentValues.put("UpdateItems", UpdateItems);
            contentValues.put("UpdateCustomer", UpdateCustomer);
            contentValues.put("UpdateEmp", UpdateEmp);
            contentValues.put("UpdateCategory", UpdateCategory);
            contentValues.put("Datamigration", Datamigration);
            long result = db.insert("SettingsDevice", null, contentValues);
            if (result == -1) {
                return false;
            } else {
                ParmsSettingsDevice.UpdateDay=Convert.ConvertTobool(String.valueOf(UpdateDay));
                ParmsSettingsDevice.UpdateBranch=Convert.ConvertTobool(String.valueOf(UpdateBranch));
                ParmsSettingsDevice.UpdateSupplier=Convert.ConvertTobool(String.valueOf(UpdateSupplier));
                ParmsSettingsDevice.UpdateStore=Convert.ConvertTobool(String.valueOf(UpdateStore));
                ParmsSettingsDevice.UpdateItems=Convert.ConvertTobool(String.valueOf(UpdateItems));
                ParmsSettingsDevice.UpdateCustomer=Convert.ConvertTobool(String.valueOf(UpdateCustomer));
                ParmsSettingsDevice.UpdateEmp=Convert.ConvertTobool(String.valueOf(UpdateEmp));
                ParmsSettingsDevice.UpdateCategory=Convert.ConvertTobool(String.valueOf(UpdateCategory));
                ParmsSettingsDevice.Datamigration=Convert.ConvertTobool(String.valueOf(Datamigration));
                return true;
            }

    }
    public  boolean InsertUpdated()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Date date2 = new Date();
        String DateNow1 = DateFormat.format("yyyy-MM-dd", date2).toString();
        ContentValues contentValues = new ContentValues();
        contentValues.put("DateUpdated", DateNow1);
        long result = db.insert("Updeted", null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }

    }
    public Cursor SelectSettingsDevice()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res =db.rawQuery("select * from SettingsDevice",null);

        return  res;
    }
    public boolean IsUpdated() {
        Date date2 = new Date();
        String DateNow1 = DateFormat.format("yyyy-MM-dd", date2).toString();
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res =db.rawQuery("select  DateUpdated from Updeted  where DateUpdated='"+DateNow1+"' LIMIT 1",null);

        if(res!=null && res.getCount()>0) {
            Date date = new Date();
            String DateNow = DateFormat.format("yyyy-MM-dd", date).toString();
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date DDNow = sdf1.parse(DateNow);
                res.moveToNext();
                Date  DDNow2 = sdf1.parse(res.getString(0));
                if(DDNow2.equals(DDNow))
                {
                    return  false;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return true;
        }
        else
        {
            return true;
        }
    }


    public static class ParmsSettingsDevice
    {
        public static boolean UpdateDay=false;
        public static boolean UpdateBranch=false;
        public static boolean UpdateSupplier=false;
        public static boolean UpdateStore=false;
        public static boolean UpdateItems=false;
        public static boolean UpdateCustomer=false;
        public static boolean UpdateEmp=false;
        public static boolean UpdateCategory=false;
        public static boolean Datamigration=false;
    }




    private void LoadData(Activity c)
    {
        Cursor res=SelectSettingsDevice();
        if(res!=null) {
            while (res.moveToNext()) {

                ParmsSettingsDevice.UpdateDay=Convert.ConvertTobool(res.getString(DataBaseHelper.colUpdateDay));
                ParmsSettingsDevice.UpdateBranch=Convert.ConvertTobool(res.getString(DataBaseHelper.colUpdateBranch));
                ParmsSettingsDevice.UpdateSupplier=Convert.ConvertTobool(res.getString(DataBaseHelper.colUpdateSupplier));
                ParmsSettingsDevice.UpdateStore=Convert.ConvertTobool(res.getString(DataBaseHelper.colUpdateStore));
                ParmsSettingsDevice.UpdateItems=Convert.ConvertTobool(res.getString(DataBaseHelper.colUpdateItems));
                ParmsSettingsDevice.UpdateCustomer=Convert.ConvertTobool(res.getString(DataBaseHelper.colUpdateCustomer));
                ParmsSettingsDevice.UpdateEmp=Convert.ConvertTobool(res.getString(DataBaseHelper.colUpdateEmp));
                ParmsSettingsDevice.UpdateCategory=Convert.ConvertTobool(res.getString(DataBaseHelper.colUpdateCategory));
                ParmsSettingsDevice.Datamigration=Convert.ConvertTobool(res.getString(DataBaseHelper.colDatamigration));
            }
        }

        if(ParmsSettingsDevice.UpdateDay)
        {
            if(IsUpdated())
            {
                Sql_Branch_Update(c);
                Sql_Store_Update(c);
                Sql_Supplier_Update(c);
                Sql_Customer_Update(c);
                Sql_Category_Update(c);
                Sql_ITems_Update(c);
                if(InsertUpdated())
                {
                    Toast.makeText(c, "تم تحديث البيانات بنجاح"+DateFormat.format("yyyy-MM-dd", date2).toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }

    }


    public void UpdateAll(Activity c)
    {
       Sql_Branch_Update(c);
       Sql_Store_Update(c);
       Sql_Supplier_Update(c);
       Sql_Customer_Update(c);
       Sql_Category_Update(c);
       Sql_ITems_Update(c);

        if(InsertUpdated())
        {
            Toast.makeText(c, "تم تحديث البيانات بنجاح"+DateFormat.format("yyyy-MM-dd", date2).toString(), Toast.LENGTH_SHORT).show();
        }
    }


    //----------------- Cls SQl Server Is Select and Updated SqlLite --------------------------------------//
        private void Sql_Branch_Update(final Context context)
        {

            if(ParmsSettingsDevice.UpdateBranch)
            {
                Global.ListName_Branch.clear();
                Global.ListID_Branch.clear();
                //---------------------------
                final SQLiteDatabase db = this.getWritableDatabase();
                db.delete("Branch", null, null);
                final ContentValues contentValues = new ContentValues();
                //-------------------------------Cls_Branch_Spinner-----------------------------------------------------//
                RequestQueue mQueue;
                conRoyal.UrlRoyal_cmb_Fill(conRoyal.ConnectionString, "Branch");
                mQueue = Volley.newRequestQueue(context);
                JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, conRoyal.Url_DefultCmb, null, new Response.Listener<JSONArray>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(JSONArray response2) {
                        try {
                            Global.ListID_Branch.add(String.valueOf(0));
                            Global.ListName_Branch.add(context.getString(R.string.txtSelect));

                            for (int i = 0; i < response2.length(); i++) {
                                JSONObject jsonArray = response2.getJSONObject(i);

                                String Name = jsonArray.getString("AName");
                                String ID = jsonArray.getString("ID");


                                contentValues.put("ID", ID);
                                contentValues.put("AName", Name);
                                if (db.insert("Branch", null, contentValues) == -1) {
                                    return;
                                } else {

                                    Global.ListName_Branch.add(Name);
                                    Global.ListID_Branch.add(ID);
                                }
                            }
                            Toast.makeText(context, "تم تحديث بيانات الفروع", Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {

                            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

                mQueue.add(request2);
            }
        }



    private void Sql_Store_Update(final Context context)
    {
        if(ParmsSettingsDevice.UpdateStore)
        {
            Global.ListName_Store.clear();
            Global.ListID_Store.clear();
            Global.ListBranchid_Store.clear();
            //---------------------------
            final SQLiteDatabase db = this.getWritableDatabase();
            db.delete("Store", null, null);
            final ContentValues contentValues = new ContentValues();
            //-------------------------------Cls_Branch_Spinner-----------------------------------------------------//
            RequestQueue mQueue;
            conRoyal.UrlRoyal_cmb_FillStore(conRoyal.ConnectionString,"Store","0");
            mQueue = Volley.newRequestQueue(context);
            JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, conRoyal.Url_StoreCmb, null, new Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONArray response2) {
                    try {
                        Global.ListID_Store.add(String.valueOf(0));
                        Global.ListBranchid_Store.add(String.valueOf(0));
                        Global.ListName_Store.add(context.getString(R.string.txtSelect));
                        for (int i = 0; i < response2.length(); i++) {
                            JSONObject jsonArray = response2.getJSONObject(i);

                            String Name = jsonArray.getString("AName");
                            String ID = jsonArray.getString("ID");
                            String Branchid = jsonArray.getString("Branchid");

                            contentValues.put("ID", ID);
                            contentValues.put("AName", Name);
                            contentValues.put("Branchid",Branchid);
                            if (db.insert("Store", null, contentValues) == -1) {
                                return;
                            } else {

                                Global.ListName_Store.add(Name);
                                Global.ListID_Store.add(ID);
                                Global.ListBranchid_Store.add(Branchid);
                            }
                        }
                        Toast.makeText(context, "تم تحديث بيانات المستودع", Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {

                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

            mQueue.add(request2);
        }
    }


    private void Sql_Supplier_Update(final Context context)
    {

        if(ParmsSettingsDevice.UpdateSupplier)
        {
            Global.ListID_Supplier.clear();
            Global.ListName_Supplier.clear();
            //---------------------------
            final SQLiteDatabase db = this.getWritableDatabase();
            db.delete("Supplier", null, null);
            final ContentValues contentValues = new ContentValues();
            //-------------------------------Cls_Branch_Spinner-----------------------------------------------------//
            RequestQueue mQueue;
            conRoyal.UrlRoyal_cmb_FillSupplier(conRoyal.ConnectionString);
            mQueue = Volley.newRequestQueue(context);
            JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, conRoyal.Url_SupplierCmb, null, new Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONArray response2) {
                    try {
                        Global.ListID_Supplier.add(String.valueOf(0));
                        Global.ListName_Supplier.add(context.getString(R.string.txtSelect));
                        for (int i = 0; i < response2.length(); i++) {
                            JSONObject jsonArray = response2.getJSONObject(i);

                            String Name = jsonArray.getString("AName");
                            String ID = jsonArray.getString("ID");


                            contentValues.put("ID", ID);
                            contentValues.put("AName", Name);
                            if (db.insert("Supplier", null, contentValues) == -1) {
                                return;
                            } else {

                                Global.ListName_Supplier.add(Name);
                                Global.ListID_Supplier.add(ID);
                            }
                        }
                        Toast.makeText(context, "تم تحديث بيانات الموردين", Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {

                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

            mQueue.add(request2);
        }
    }



    private void Sql_Customer_Update(final Context context)
    {

        if(ParmsSettingsDevice.UpdateCustomer)
        {
            Global.ListID_Customer.clear();
            Global.ListName_Customer.clear();
            //---------------------------
            final SQLiteDatabase db = this.getWritableDatabase();
            db.delete("Customer", null, null);
            final ContentValues contentValues = new ContentValues();
            //-------------------------------Cls_Branch_Spinner-----------------------------------------------------//
            RequestQueue mQueue;
            conRoyal.UrlRoyal_GetCustomer(conRoyal.ConnectionString,conRoyal.VendorID);
            mQueue = Volley.newRequestQueue(context);
            JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, conRoyal.Url_GetCustomer, null, new Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONArray response2) {
                    try {
                        Global.ListID_Customer.add(String.valueOf(0));
                        Global.ListName_Customer.add(context.getString(R.string.txtSelect));
                        for (int i = 0; i < response2.length(); i++) {
                            JSONObject jsonArray = response2.getJSONObject(i);

                            String Name = jsonArray.getString("AName");
                            String ID = jsonArray.getString("ID");


                            contentValues.put("ID", ID);
                            contentValues.put("AName", Name);
                            if (db.insert("Customer", null, contentValues) == -1) {
                                return;
                            } else {

                                Global.ListName_Customer.add(Name);
                                Global.ListID_Customer.add(ID);
                            }
                        }
                        Toast.makeText(context, "تم تحديث بيانات العملاء", Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {

                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

            mQueue.add(request2);
        }
    }

    private void Sql_Category_Update(final Context context)
    {

        if(ParmsSettingsDevice.UpdateCategory)
        {
            Global.ListID_Category.clear();
            Global.ListName_Category.clear();
            //---------------------------
            final SQLiteDatabase db = this.getWritableDatabase();
            db.delete("Category", null, null);
            final ContentValues contentValues = new ContentValues();
            //-------------------------------Cls_Branch_Spinner-----------------------------------------------------//
            RequestQueue mQueue;
            conRoyal.UrlRoyal_GetCategory(conRoyal.ConnectionString);
            mQueue = Volley.newRequestQueue(context);
            JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, conRoyal.Url_GetCategory, null, new Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONArray response2) {
                    try {
                        Global.ListID_Category.add(String.valueOf(0));
                        Global.ListName_Category.add(context.getString(R.string.txtSelect));
                        for (int i = 0; i < response2.length(); i++) {
                            JSONObject jsonArray = response2.getJSONObject(i);

                            String Name = jsonArray.getString("AName");
                            String ID = jsonArray.getString("ID");


                            contentValues.put("ID", ID);
                            contentValues.put("AName", Name);
                            if (db.insert("Category", null, contentValues) == -1) {
                                return;
                            } else {

                                Global.ListName_Category.add(Name);
                                Global.ListID_Category.add(ID);
                            }
                        }
                        Toast.makeText(context, "تم تحديث بيانات المجموعات", Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {

                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

            mQueue.add(request2);
        }
    }

    private void Sql_ITems_Update(final Activity context)
    {


        //progressDialog.setContentView(R.layout.progress_dialog);
       // progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        //textlodaing=progressDialog.getWindow().findViewById(R.id.prsentlodaing);
try{



        if(ParmsSettingsDevice.UpdateItems)
        {
            if(Global.listItem!=null) {
                Global.listItem.clear();
            }
            //---------------------------


            //-------------------------------Cls_Branch_Spinner-----------------------------------------------------//
            RequestQueue mQueue;
            conRoyal.UrlRoyal_ListItemsALL(conRoyal.ConnectionString,0);
            mQueue = Volley.newRequestQueue(context);
            JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, conRoyal.Url_SelectListItemsALL, null, new Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONArray response2) {
                    LoadingItems(response2,context);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            int socketTimeout = 60000;//30 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            request2.setRetryPolicy(policy);
            mQueue.add(request2);
        }
        else
        {
          //  progressDialog.dismiss();
        }
}
catch (Exception ex)
{
    Toast.makeText(context,ex.getMessage(),Toast.LENGTH_SHORT).show();
}

    }
void LoadingItems(final JSONArray response2, final Activity context)
{
    progressDialog=new ProgressDialog(context);
    progressDialog.setMessage("يرجى عدم إغلاق النظام!!");
    progressDialog.setTitle("جاري تحميل المواد ...");
    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    progressDialog.show();
    progressDialog.setCancelable(false);
    final ContentValues contentValues = new ContentValues();
    final SQLiteDatabase db = this.getWritableDatabase();
    db.delete("Items", null, null);
    new Thread(new Runnable() {
        @Override
        public void run() {
                try {
                    progressDialog.setMax(response2.length());
                    for (int i = 0; i < response2.length(); i++) {
                        JSONObject jsonArray = response2.getJSONObject(i);
                        String Logo = "0";

                        if (jsonArray.getString("picture") != null) {

                            Logo = jsonArray.getString("picture");
                        }
                        Cls_ItemsInvoiceSales a = new Cls_ItemsInvoiceSales(null, "", "", "", "1", "");
                        try {

                            String ID = jsonArray.getString("id");
                            String MainBarcode = jsonArray.getString("MainBarcode");
                            String ItemName = jsonArray.getString("AName");
                            String Price = jsonArray.getString("SalesPrice");
                            String CategoryID = jsonArray.getString("CategoryID");
                            DecimalFormat DF = new DecimalFormat("######0.000");

                            a.setID(ID);
                            Bitmap bp = null;
                            if (!Logo.equals("0")) {
                                byte[] iimage = Base64.decode(Logo, Base64.DEFAULT);
                                bp = BitmapFactory.decodeByteArray(iimage, 0, iimage.length);
                            }
                            a.setBase64Image(bp);
                            a.setItemName(ItemName);
                            a.setMainBarcode(MainBarcode);
                            a.setQty(DF.format(Double.valueOf("0")));
                            a.setPrice(DF.format(Double.valueOf("1.5")));
                            a.setCategoryID(CategoryID);

                            contentValues.put("ID", ID);
                            contentValues.put("ItemName", ItemName);
                            contentValues.put("MainBarcode", MainBarcode);
                            contentValues.put("SalesPrice", Price);
                            contentValues.put("picture", Logo);
                            contentValues.put("CategoryID", CategoryID);
                            // textlodaing.setText(i+"From"+response2.length());
                            // int total=((i/response2.length())*100);
                            // textlodaing.setProgress(50);
                            //textlodaing.setIndeterminate(true);
                        }
                        catch (Exception ex)
                        {
                            String asd=ex.getMessage();
                        }
                        //Thread.sleep(50);
                        if(i==response2.length()-1)
                        {
                            if(progressDialog.isShowing())
                            {
                                progressDialog.dismiss();
                            }
                        }
                        else {
                            progressDialog.incrementProgressBy(1);
                        }
                        //Thread.sleep(50);
                        if (db.insert("Items", null, contentValues) == -1) {
                            return;
                        } else {
                            Global.listItem.add(a);
                        }
                    }

                    //progressDialog.dismiss();
                } catch (JSONException e) {

                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
                }
        }
    }).start();

    //Toast.makeText(context, "تم تحديث بيانات المواد", Toast.LENGTH_SHORT).show();

}




    Handler handle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDialog.incrementProgressBy(1);
            //textlodaing.incrementProgressBy(1);
        }
    };

    //----------------- Cls SQl Server Is Select and Updated SqlLite --------------------------------------//

    //------------------ Select From SqlLite ------------------------------------------------------------------//
    public void SetAdapterBranch(Context c, Spinner s)
    {
        if(Global.ListID_Branch.size() <=0) {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("select * from Branch", null);
            Global.ListID_Branch.add(String.valueOf(0));
            Global.ListName_Branch.add(c.getString(R.string.txtSelect));
            while (res.moveToNext()) {
                Global.ListID_Branch.add(String.valueOf(res.getString(0)));
                Global.ListName_Branch.add(String.valueOf(res.getString(1)));
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_dropdown_item, Global.ListName_Branch);
        s.setAdapter(adapter);

    }

    public void SetAdapterStore(Context c, Spinner s,int IDBranch)
    {
        if(Global.ListID_Store.size() <=0) {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("select * from Store where Branchid=" + IDBranch + " OR " + IDBranch + "=0", null);
            Global.ListID_Store.add(String.valueOf(0));
            Global.ListName_Store.add(c.getString(R.string.txtSelect));
            while (res.moveToNext()) {
                Global.ListID_Store.add(String.valueOf(res.getString(0)));
                Global.ListName_Store.add(String.valueOf(res.getString(1)));
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_dropdown_item, Global.ListName_Store);
        s.setAdapter(adapter);
    }

    public void SetAdapterSupplier(Context c, Spinner s)
    {
        if(Global.ListID_Supplier.size() <=0) {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("select * from Supplier ", null);
            Global.ListID_Supplier.add(String.valueOf(0));
            Global.ListName_Supplier.add(c.getString(R.string.txtSelect));
            while (res.moveToNext()) {
                Global.ListID_Supplier.add(String.valueOf(res.getString(0)));
                Global.ListName_Supplier.add(String.valueOf(res.getString(1)));
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_dropdown_item, Global.ListName_Supplier);
        s.setAdapter(adapter);
    }
    public void SetAdapterCategory(Context c, Spinner s)
    {
        if(Global.ListID_Category.size() <=0) {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("select * from Category ", null);
            Global.ListID_Category.add(String.valueOf(0));
            Global.ListName_Category.add(c.getString(R.string.txtSelect));
            while (res.moveToNext()) {
                Global.ListID_Category.add(String.valueOf(res.getString(0)));
                Global.ListName_Category.add(String.valueOf(res.getString(1)));
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_dropdown_item, Global.ListName_Category);
        s.setAdapter(adapter);
    }
    public void SetAdapterCustomer(Context c, Spinner s)
    {
        if(Global.ListID_Customer.size() <=0) {
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("select * from Customer ", null);
            Global.ListID_Customer.add(String.valueOf(0));
            Global.ListName_Customer.add(c.getString(R.string.txtSelect));
            while (res.moveToNext()) {
                Global.ListID_Customer.add(String.valueOf(res.getString(0)));
                Global.ListName_Customer.add(String.valueOf(res.getString(1)));
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_dropdown_item, Global.ListName_Customer);
        s.setAdapter(adapter);
    }
    public void SetAdapterItems(Context c, ListView s,int CategoryID)
    {
        ProgressDialog progressDialog=new ProgressDialog(c);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);

        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.show();
        if(Global.listItem!=null)
        {
            Global.listItem.clear();
        }
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor res = db.rawQuery("select * from Items where CategoryID="+CategoryID+" OR "+CategoryID+"=0", null);
            while (res.moveToNext()) {
                String Logo = "0";
                if (res.getString(4) != null) {

                    Logo = res.getString(4);
                }
                Cls_ItemsInvoiceSales a = new Cls_ItemsInvoiceSales(null, "", "", "", "1", "");
                a.setID(res.getString(0));
                Bitmap bp=null;
                if(!Logo.equals("0"))
                {
                    byte[] iimage = Base64.decode(Logo, Base64.DEFAULT);
                    bp = BitmapFactory.decodeByteArray(iimage, 0, iimage.length);
                }

                DecimalFormat DF = new DecimalFormat("######0.000");
                a.setBase64Image(bp);
                a.setItemName(res.getString(1));
                a.setMainBarcode(res.getString(2));
                a.setQty(Global.convertToEnglish(DF.format(Double.valueOf("0"))));
                a.setPrice(Global.convertToEnglish(DF.format(Double.valueOf(res.getString(3)))));

                Global.listItem.add(a);
            }

        if(conRoyal.listInvoiceSales.size()>0)
        {
            for (int ii1=0;ii1<Global.listItem.size();ii1++)
            {
                for (int ii2=0;ii2<conRoyal.listInvoiceSales.size();ii2++)
                {
                    if(Global.listItem.get(ii1).getID().equals(conRoyal.listInvoiceSales.get(ii2).getID()))
                    {
                        Global.listItem.get(ii1).setQty(conRoyal.listInvoiceSales.get(ii2).getQty());
                    }
                }
            }
        }
        if(s!=null) {
            Adapter_Items_InvoiceSales Adapter = new Adapter_Items_InvoiceSales(c, Global.listItem);

            s.setAdapter(Adapter);
        }
        progressDialog.dismiss();
    }
    //------------------ Select From SqlLite ------------------------------------------------------------------//
    public boolean Users(Activity c , String UserName, String Password2)
    {
        //--------------- Profile Login ------------------------------------------------//
        int ID,TypeID;
        String AName,Mac,CreationDate;
        //--------------- Profile Login ------------------------------------------------//

        SQLiteDatabase db2 = this.getWritableDatabase();
        Cursor res = db2.rawQuery("select * from Tbl_User where UserName="+UserName+" and Password="+Password2+"",null);
        while (res.moveToNext())
        {
            ID = res.getInt(0);
            AName = res.getString(1);
            TypeID = res.getInt(2);
            Mac = res.getString(3);
            CreationDate = res.getString(4);
        }
        return  true;
    }

    private void Access_User(int User_Id)
    {

        SQLiteDatabase dbq = this.getWritableDatabase();
        Cursor res = dbq.rawQuery("select * from Tbl_Access_Screen where User_Id="+User_Id+"", null);
        while (res.moveToNext()) {
            int Screen_id = res.getInt(2) ;
            int Access_User = res.getInt(3);

            if(Screen_id==1)
            {
                conRoyal.Sccess_Home_ID=Screen_id;
                conRoyal.Access_Home_ID=Access_User;
            }
            else if(Screen_id==2)
            {
                conRoyal.Sccess_Pos_ID=Screen_id;
                conRoyal.Access_Pos_ID=Access_User;
            }
            else if(Screen_id==3)
            {
                conRoyal.Sccess_Inventory_ID=Screen_id;
                conRoyal.Access_Inventory_ID=Access_User;
            }
            else if(Screen_id==4)
            {
                conRoyal.Sccess_Account_ID=Screen_id;
                conRoyal.Access_Account_ID=Access_User;
            }
            else if(Screen_id==5)
            {
                conRoyal.Sccess_Emp_ID=Screen_id;
                conRoyal.Access_Emp_ID=Access_User;
            }
            else if(Screen_id==7)
            {
                conRoyal.Sccess_PDA=Screen_id;
                conRoyal.Access_PDA=Access_User;
            }
            else if(Screen_id==8)
            {
                conRoyal.Sccess_Tablet=Screen_id;
                conRoyal.Access_Tablet=Access_User;
            }
            else if(Screen_id==9)
            {
                conRoyal.Sccess_SettingsDevice=Screen_id;
                conRoyal.Access_SettingsDevice=Access_User;
            }
            else if(Screen_id==10)
            {
                conRoyal.Sccess_Data_migration=Screen_id;
                conRoyal.Access_Data_migration=Access_User;
            }
            else if(Screen_id==11)
            {
                conRoyal.Sccess_Updatingdata=Screen_id;
                conRoyal.Access_Updatingdata=Access_User;
            }
        }
    }
    //------------------------ Save To SqlLite -------------------------------------------------------------//
    public boolean SavefrmInvoiceSales(String branchId, String storeId, String vendorId, String receiverName, String note, String voucherNumber, String voucherDate, String reference, String transactionID, String customerId, String paymentId, String salesManID, JSONArray details,Transaction transaction) throws JSONException {
        boolean Saved=false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("BranchId",Integer.valueOf(branchId));
        contentValues.put("StoreId", Integer.valueOf(storeId));
        contentValues.put("VendorId", Integer.valueOf(vendorId));
        contentValues.put("ReceiverName", receiverName);
        contentValues.put("Note", note);
        contentValues.put("VoucherNumber", Integer.valueOf(voucherNumber));
        contentValues.put("VoucherDate", voucherDate);
        contentValues.put("Reference", reference);
        contentValues.put("TransactionID", Integer.valueOf(transactionID));
        contentValues.put("CustomerId", Integer.valueOf(customerId));
        contentValues.put("PaymentId", Integer.valueOf(paymentId));
        contentValues.put("SalesManID", Integer.valueOf(salesManID));
        contentValues.put("IsTransaction", Integer.valueOf(transaction.getValue()));
        long HeaderId=db.insert("PDAIncomeVoucherHeader", null, contentValues);
        if (HeaderId == -1) {
            Saved=false;
        } else {
            for (int i=0;i<details.length();i++)
            {
                JSONObject object=new JSONObject();
                object=details.getJSONObject(i);

                String ItemId=object.getString("ID");
                String ItemName=object.getString("Name");
                String Qty=object.getString("Qty");
                String MainBarcode=object.getString("MainBarcode");
                String rowindex=object.getString("rowindex");
                String PurchasePrice=object.getString("PurchasePrice");

                ContentValues contentValues2 = new ContentValues();
                contentValues2.put("HeaderId",Integer.valueOf((int) HeaderId));
                contentValues2.put("ItemId", Integer.valueOf(ItemId));
                contentValues2.put("MainBarcode", MainBarcode);
                contentValues2.put("ItemName", ItemName);
                contentValues2.put("QTY", Qty);
                contentValues2.put("rowindex", Integer.valueOf(rowindex));
                contentValues2.put("PurchasePrice", PurchasePrice);
                if (db.insert("PDAIncomeVoucherDetails", null, contentValues2) == -1) {
                    Saved = false;
                    return Saved;
                }
                else
                {
                    Saved=true;
                    continue;
                }
            }

        }
        return Saved;
    }


    public boolean SavefrmSettingDeviceBltooth(int ID, String AName, int TypeID,String TypeName, String Mac, String CreationDate) throws JSONException {
        boolean Saved=false;
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Tbl_SettingDeviceBltooth", "ID=?", new String[]{String.valueOf(ID)});

           ContentValues contentValues = new ContentValues();
           contentValues.put("ID", Integer.valueOf(ID));
           contentValues.put("AName", String.valueOf(AName));
           contentValues.put("TypeID", Integer.valueOf(TypeID));
           contentValues.put("TypeName", TypeName);
           contentValues.put("Mac", Mac);
           contentValues.put("CreationDate", CreationDate);
           long HeaderId = db.insert("Tbl_SettingDeviceBltooth", null, contentValues);
           if (HeaderId == -1) {
               Saved = false;
           } else {
               Saved = true;
           }
        return Saved;
    }


    //------------------------ Save To SqlLite -------------------------------------------------------------//


        public void TransferDataToSqlServer(final Context c) {
            progressDialog = new ProgressDialog(c);
            progressDialog.setMessage("يرجى عدم إغلاق النظام!!");
            progressDialog.setTitle("جاري الرفع إلى النظام ...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.show();
            progressDialog.setCancelable(false);

            Transaction transaction_Insert_Not_transferred = Transaction.Insert_Not_transferred;
            final int[] i = {0};
            final int[] Saved = {0};
            JSONArray d;
            final SQLiteDatabase db = this.getWritableDatabase();
            final Cursor res = db.rawQuery("select * from PDAIncomeVoucherHeader where IsTransaction=" + transaction_Insert_Not_transferred.getValue() + " ", null);
            progressDialog.setMax(res.getCount());
            while (res.moveToNext())
            {
                int HeaderId = res.getInt(0);
                int BranchId = res.getInt(1);
                int StoreId = res.getInt(2);
                int VendorId = res.getInt(3);
                String ReceiverName = res.getString(4);
                String Note = res.getString(5);
                int VoucherNumber = res.getInt(6);
                String VoucherDate = res.getString(7);
                String Reference = res.getString(8);
                int TransactionID = res.getInt(9);
                int CustomerId = res.getInt(10);
                int PaymentId = res.getInt(11);
                int SalesManID = res.getInt(12);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            progressDialog.incrementProgressBy(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();
                d = new JSONArray();
                Cursor resDetails = db.rawQuery("select * from PDAIncomeVoucherDetails where HeaderId=" + HeaderId + "", null);
                while (resDetails.moveToNext())
                {
                    int ItemId = resDetails.getInt(2);
                    String MainBarcode = resDetails.getString(3);
                    String ItemName = resDetails.getString(4);
                    String QTY = resDetails.getString(5);
                    int rowindex = resDetails.getInt(6);
                    String PurchasePrice = resDetails.getString(7);
                    JSONObject object = null;
                    try {
                        object = new JSONObject();
                        object.put("ID", ItemId);
                        object.put("Qty", QTY);
                        object.put("MainBarcode", MainBarcode);
                        object.put("Name", ItemName);
                        object.put("rowindex", rowindex);
                        object.put("PurchasePrice", PurchasePrice);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    d.put(object);
                }

                    Saved[0] = SendDataTosqlServer(c, BranchId, StoreId, VendorId, ReceiverName, Note, VoucherNumber, VoucherDate, Reference, TransactionID, CustomerId, PaymentId, SalesManID, d);
                    if (Saved[0] == 1) {
                        Transaction transaction_Insert_transferred = Transaction.Insert_transferred;
                        ContentValues values = new ContentValues();
                        values.put("IsTransaction", String.valueOf(transaction_Insert_transferred.getValue()));
                        db.update("PDAIncomeVoucherHeader", values, "ID=?", new String[]{String.valueOf(HeaderId)});
                        i[0]++;

                        continue;
                    } else if (Saved[0] == 2) {
                        return;
                    }

            }
            if(progressDialog.isShowing())
            {
                progressDialog.dismiss();
            }
          /*  if (Saved[0] == 1) {
                Global.msgBox(c, c.getString(R.string.txtCorrectoperation), c.getString(R.string.txtmigratedsystem) + "   (" + i[0] + ")   ", Color.BLUE, R.drawable.ic_check_circle_black_24dp);
            } else if (Saved[0] == 2) {
                Global.msgBox(c, c.getString(R.string.txtWrongoperation), c.getString(R.string.txtFaild), Color.RED, R.drawable.ic_error_outline_black_24dp);
            }*/
            //Global.msgBox(c, c.getString(R.string.txtCorrectoperation), c.getString(R.string.txtmigratedsystem) + "   (" + i[0] + ")   ", Color.BLUE, R.drawable.ic_check_circle_black_24dp);

        }

public  void  rest(Activity activity)
{

    final SQLiteDatabase db = this.getWritableDatabase();
    final Cursor res = db.rawQuery("select * from PDAIncomeVoucherHeader  ", null);
    while (res.moveToNext()) {
        int HeaderId = res.getInt(0);

        ContentValues values = new ContentValues();
        Transaction transaction_Insert_transferred = Transaction.Insert_Not_transferred;
        values.put("IsTransaction", String.valueOf(transaction_Insert_transferred.getValue()));
        db.update("PDAIncomeVoucherHeader", values, "ID=?", new String[]{String.valueOf(HeaderId)});
        Toast.makeText(activity, "تم اعادة سند رقم"+ HeaderId, Toast.LENGTH_LONG).show();
    }
    Toast.makeText(activity, "تم اعادة الترحيل الكامل", Toast.LENGTH_LONG).show();


}

    //------------------------ Save To SQLSERVER -------------------------------------------------------------//


private int SendDataTosqlServer(Context c,int BranchId,int StoreId,int VendorId,String ReceiverName,String Note,int VoucherNumber,String VoucherDate,String Reference,int TransactionID,int CustomerId,int PaymentId,int SalesManID,JSONArray d)
{

    final int[] IsSaved = {1};
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(conRoyal.ConIpRoyal2)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
    post pp = new post(conRoyal.ConnectionString, String.valueOf(BranchId), String.valueOf(StoreId), String.valueOf(VendorId), String.valueOf(ReceiverName), String.valueOf(Note), String.valueOf(VoucherNumber), String.valueOf(VoucherDate), String.valueOf(Reference), String.valueOf(TransactionID), String.valueOf(CustomerId), String.valueOf(PaymentId), String.valueOf(SalesManID), d.toString());
    Call<post> call = jsonPlaceHolderApi.createPost(pp);
    call.enqueue(new Callback<post>() {
        @Override
        public void onResponse(Call<post> call, retrofit2.Response<post> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus().equals("true"))
                {
                     IsSaved[0] =1;
                    Global.save=true;
                }
            }
        }

        @Override
        public void onFailure(Call<post> call, Throwable t) {
            IsSaved[0] =2;
            Global.save=false;
        }


    });
    return IsSaved[0];
}


    //------------------------ Save To SQLSERVER -------------------------------------------------------------//


  public void InsertToSqlLiteLogin(int ID, String Comapny_Name, String User_Name, String Password, String IP_Static, String DataBase_Name, String User_DB, String Password_DB, String StartDate, String EndDate, String Activate_User, String SalesManID)
    {
        final SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Tbl_User", null, null);
        final ContentValues contentValues = new ContentValues();
        contentValues.put("ID", ID);
        contentValues.put("Comapny_Name", Comapny_Name);
        contentValues.put("UserName", User_Name);
        contentValues.put("Password", Password);
        contentValues.put("IP_Static", IP_Static);
        contentValues.put("DataBase_Name", DataBase_Name);
        contentValues.put("User_DB", User_DB);
        contentValues.put("Password_DB", Password_DB);
        contentValues.put("StartDate", StartDate);
        contentValues.put("EndDate", EndDate);
        contentValues.put("Activate_User", Activate_User);
        contentValues.put("SalesManID", SalesManID);


        if (db.insert("Tbl_User", null, contentValues) == -1) {
            return;
        } else {

        }
    }
    public void  Delete_LoginAccess_Screen()
    {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Tbl_Access_Screen", null, null);
    }

    public void InsertToSqlLiteLoginAccess_Screen(int User_Id, int Screen_id, int Access_User)
    {
         SQLiteDatabase db = this.getWritableDatabase();
         ContentValues contentValues = new ContentValues();
        contentValues.put("User_Id", User_Id);
        contentValues.put("Screen_id", Screen_id);
        contentValues.put("Access_User", Access_User);

        if (db.insert("Tbl_Access_Screen", null, contentValues) == -1) {
            return;
        } else {

        }
    }

}
