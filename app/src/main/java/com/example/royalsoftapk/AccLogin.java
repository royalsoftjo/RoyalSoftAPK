package com.example.royalsoftapk;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccLogin extends Fragment {

    private RequestQueue mQueue;
    Button Btn_Login;
    ProgressDialog progressDialog;
    TextView textUser,textPass;

    //--------------- Profile Login ------------------------------------------------//
    String UserName,Password,ipStatic,DataBase_Name,User_DB,Password_DB,StartDate,EndDate,Activate_User,NowDate,SalesManID;
    int IDUser;
    //--------------- Profile Login ------------------------------------------------//

    DataBaseHelper db=new DataBaseHelper(getActivity());


    SpeechRecognizer speechRecognizer;
    Intent spechRecognizerIntent;
    String keeper="";
    public AccLogin() {
        // Required empty public constructor
    }
    void checkVoiceCommandPermission()
    {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.RECORD_AUDIO},
                    10);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment


        final View view = inflater.inflate(R.layout.fragment_acc_login, container, false);
        Btn_Login = view.findViewById(R.id.btnLogin2);
        textUser = view.findViewById(R.id.inputUser);
        textPass =(TextView) view.findViewById(R.id.inputPassword);

     /*   textUser.setText("محمد زهير");
       textPass.setText("1800");*/



     Btn_Login.setOnLongClickListener(new View.OnLongClickListener() {
         @Override
         public boolean onLongClick(View v) {
             Toast.makeText(getActivity(),getString(R.string.connection),Toast.LENGTH_SHORT).show();
           //  speechRecognizer.startListening(spechRecognizerIntent);
          //   keeper="";
             return true;
         }
     });

        Btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Global.GetLocation(getActivity(),getContext());
               String User = textUser.getText().toString();
               String Pass = textPass.getText().toString();
                CheckIP();
                    if (Global.IsHoldResturent) {
                        Successful_LoginIsHoldResturent(User, Pass);
                    } else if (Global.IsCashVan) {
                        Successful_LoginIsCashVan(User, Pass);
                    }
                    else if (Global.IsOrderSales) {
                        Successful_LoginIsCashVan(User, Pass);
                    }
                    else if(Global.Isfeedback)
                    {
                        Successful_LoginIsFeed();
                    }
                    else if(Global.IsProcessingorder)
                    {
                        Successful_LoginIsCashVan(User, Pass);
                    }
                    else {
                        Successful_Login(User, Pass);
                    }
                }
        });


        checkVoiceCommandPermission();
        speechRecognizer= SpeechRecognizer.createSpeechRecognizer(getActivity());
        spechRecognizerIntent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        spechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        spechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {

                ArrayList<String> match= results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if(match!=null)
                {
                    keeper=match.get(0);
                    if(keeper.equals(getString(R.string.connection))) {
                        startActivity(new Intent(getActivity(),Connected_IP.class));
                    }

                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {

            }
        });
        return view;
    }

    private void CheckIP()
    {
        try {
            DataBaseHelper dd=new DataBaseHelper(getContext());
            SQLiteDatabase db=dd.getWritableDatabase();
            Cursor res =db.rawQuery("select * from Tbl_Connected",null);
            if(res!=null && res.getCount()>0) {
                res.moveToNext();
                Global.IsStatic=Integer.valueOf(res.getString(3));
                if(Global.IsStatic==0)
                {
                    String cons=String.valueOf(res.getString(1));
                    String con="http://"+cons+"/api/Inventory";
                    conRoyal.ConIpRoyal=con;
                    conRoyal.ConIpRoyal2=con+"/";
                    conRoyal.ConIpImg="http://"+cons+"/Img/";
                }

                Integer TypeID=res.getInt(2);
                if(TypeID==0)
                {
                    Global.IsHoldResturent=false;
                    Global.IsCashVan=false;
                    Global.Isfeedback=false;
                    Global.IsOrderSales=false;
                    Global.IsProcessingorder=false;
                }
                else if(TypeID==1)
                {
                    Global.IsHoldResturent=false;
                    Global.IsCashVan=false;
                    Global.Isfeedback=false;
                    Global.IsOrderSales=false;
                    Global.IsProcessingorder=false;
                }
                else if(TypeID==2)
                {
                    Global.IsHoldResturent=true;
                    Global.IsCashVan=false;
                    Global.Isfeedback=false;
                    Global.IsOrderSales=false;
                    Global.IsProcessingorder=false;
                }
                else if(TypeID==3)
                {
                    Global.IsHoldResturent=false;
                    Global.IsCashVan=true;
                    Global.DataSourceName=res.getString(4);
                    Global.Isfeedback=false;
                    Global.IsOrderSales=false;
                    Global.IsProcessingorder=false;
                }
                else if(TypeID==4)
                {
                    Global.IsHoldResturent=false;
                    Global.IsCashVan=false;
                    Global.Isfeedback=true;
                    Global.DataSourceName=res.getString(4);
                    Global.IsOrderSales=false;
                    Global.IsProcessingorder=false;
                }
                else if(TypeID==5)
                {
                    Global.IsHoldResturent=false;
                    Global.IsCashVan=false;
                    Global.DataSourceName=res.getString(4);
                    Global.Isfeedback=false;
                    Global.IsProcessingorder=true;
                    Global.IsOrderSales=false;
                }
                else if(TypeID==6)
                {
                    Global.IsHoldResturent=false;
                    Global.IsCashVan=false;
                    Global.DataSourceName=res.getString(4);
                    Global.Isfeedback=false;
                    Global.IsProcessingorder=false;
                    Global.IsOrderSales=true;
                }

            }
            else
            {
                startActivity(new Intent(getActivity(),Connected_IP.class));
            }

        }
        catch (Exception ex)
        {

        }
    }
    private void Successful_Login(final String User , final String Pass)
    {

        progressDialog= new ProgressDialog(getActivity());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);



        conRoyal.UrlRoyalLogin(User,Pass);

        mQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.POST, conRoyal.Url_Login, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {

                    JSONObject jsonArray = response.getJSONObject(0);
                    IDUser = jsonArray.getInt("ID");
                    UserName = jsonArray.getString("User_Name");
                    Password = jsonArray.getString("Password");
                    ipStatic = jsonArray.getString("IP_Static");
                    DataBase_Name = jsonArray.getString("DataBase_Name");
                    User_DB= jsonArray.getString("User_DB");
                    Password_DB=jsonArray.getString("Password_DB");
                    StartDate = jsonArray.getString("StartDate");
                    EndDate = jsonArray.getString("EndDate");
                    EndDate=EndDate.substring(0,10);
                    Activate_User = jsonArray.getString("Activate_User");
                    NowDate =jsonArray.getString("NowDate") ;
                    NowDate=NowDate.substring(0,10);
                  //  SalesManID=jsonArray.getString("SalesManID");
                  /*  if(SalesManID.equals("null"))
                    {
                        SalesManID="0";
                    }*/
                  //  conRoyal.VendorID=SalesManID;
                    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");


                    try {
                        Date DDNow = sdf1.parse(NowDate);
                        Date DDNow2 = sdf1.parse(EndDate);

                        if(DDNow2.after(DDNow))
                        {
                            //------------- تحميل المتغيرات في حالة نجاح الاتصال--------------------------------------
                            SalesManID="1";

                            DataBaseHelper db=new DataBaseHelper(getActivity());
                            LoadVirable();
                            db.CteateTable(getActivity());
                            conRoyal.UrlRoyal_ConnectionString();
                            db.InsertToSqlLiteLogin(IDUser,"",UserName,Password,ipStatic,DataBase_Name,User_DB,Password_DB,StartDate,EndDate,Activate_User,SalesManID);


                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), getString(R.string.Welcome) +conRoyal.UserName, Toast.LENGTH_SHORT).show();

                            if(Integer.valueOf(SalesManID)>=0)
                            {
                                Intent intent=new Intent(getActivity(),DASHWORD.class);
                               startActivity(intent);
                            }
                           else
                           {
                               Intent intent=new Intent(getActivity(),SelectVendor.class);
                               startActivity(intent);
                            }

                            //------------- تحميل المتغيرات في حالة نجاح الاتصال---------------------------------------
                        }
                          else {

                            progressDialog.dismiss();
                            Toast.makeText(getActivity(), "عذرا ، الحساب غير فعال يرجى مراجعة شركة رويال سوفت لتفعيله", Toast.LENGTH_LONG).show();
                        }
                    }
                    catch (Exception e)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();

                if(Global.IsCustomerOther)
                {
                    conRoyal.Sccess_PDA=7;
                    conRoyal.Access_PDA=1;
                    Toast.makeText(getActivity(), getString(R.string.Welcome) +conRoyal.UserName + "(offline)", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getActivity(),DASHWORD.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getActivity(),error.getMessage() + "  "+"يرجى التأكد من إتصال الإنترنت !",Toast.LENGTH_SHORT).show();
                }
                //DataBaseHelper db=new DataBaseHelper(getActivity());
                //db.Users(getActivity(),User,Pass);

            }
        });
        int socketTimeout = 60000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        mQueue.add(request);
    }


    private void Successful_LoginIsHoldResturent(final String User , final String Pass)
    {

        progressDialog= new ProgressDialog(getActivity());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);



        conRoyal.UrlRoyal_LoginIsHoldResturent(User,Pass);

        mQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.POST, conRoyal.Url_LoginIsHoldResturent, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {

                    JSONObject jsonArray = response.getJSONObject(0);
                    Global.LoginBranchID = jsonArray.optInt("LoginBranchID",0);
                    Global.LoginStationID = jsonArray.optInt("LoginStationID",0);
                    Global.LoginStoreID = jsonArray.optInt("LoginStoreID",0);
                    Global.WorkDayID = jsonArray.optInt("WorkDayID",0);
                    Global.LoginWorkDayDate = jsonArray.optString("LoginWorkDayDate","ex");
                  //  if(jsonArray.optBoolean("IsUsePos",false))
                    Global.IsUsePos = jsonArray.optBoolean("IsUsePos",false);
                    Global.IsUseManagement = jsonArray.optBoolean("IsUseManagement",false);
                    Global.IsUser = jsonArray.optBoolean("IsUser",false);
                    Global.IsAdmin = jsonArray.optBoolean("IsAdmin",false);
                    Global.IsWaiter = jsonArray.optBoolean("IsWaiter",false);
                    Global.WaiterID = jsonArray.optInt("WaiterID",0);
                    conRoyal.IDUser = jsonArray.optInt("ID",0);

                    UserName = User;
                    conRoyal.UserName=User;
                    Password = Pass;
                   String ConStr = jsonArray.getString("ConStr");

                    try {
                            //------------- تحميل المتغيرات في حالة نجاح الاتصال--------------------------------------
                           // LoadVirable();
                            if(Global.WorkDayID==0)
                            {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(),"يرجى التأكد من تاريخ الكاش ، والمحاولة مره اخرى",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                if(Global.IsUsePos)
                                {
                                    conRoyal.UrlRoyal_ConnectionString2(ConStr);
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), getString(R.string.Welcome) +conRoyal.UserName, Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getActivity(),frmTablesHome.class);
                                    startActivity(intent);

                                }
                                else
                                {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(),"IsUsePos يرجى التأكد من إسم المستخدم وكلمة المرور ، والمحاولة مره اخرى",Toast.LENGTH_SHORT).show();
                                }

                                //------------- تحميل المتغيرات في حالة نجاح الاتصال---------------------------------------

                            }

                    }
                    catch (Exception e)
                    {

                    }


                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),"يرجى التأكد من إسم المستخدم وكلمة المرور ، والمحاولة مره اخرى",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(getActivity(),"يرجى التأكد من إتصال الإنترنت !"+error.getMessage(),Toast.LENGTH_SHORT).show();
                //DataBaseHelper db=new DataBaseHelper(getActivity());
               // db.Users(getActivity(),User,Pass);

            }
        });
        int socketTimeout = 60000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        mQueue.add(request);
    }



    private void Successful_LoginIsFeed()
    {
        progressDialog= new ProgressDialog(getActivity());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        conRoyal.UrlRoyal_LoginIsFeedBack(Global.DataSourceName);

        mQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.POST, conRoyal.Url_GetLoginIsFeedBack, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {

                        JSONObject jsonArray = response.getJSONObject(0);
                    String ConStr = jsonArray.getString("ConStr");

                    try {
                        //------------- تحميل المتغيرات في حالة نجاح الاتصال--------------------------------------
                                    conRoyal.UrlRoyal_ConnectionString2(ConStr);
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), getString(R.string.Welcome) , Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), HomeFeedBack.class);
                                    startActivity(intent);

                            //------------- تحميل المتغيرات في حالة نجاح الاتصال---------------------------------------
                    }
                    catch (Exception e)
                    { progressDialog.dismiss();

                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),"يرجى التأكد من إسم المستخدم وكلمة المرور ، والمحاولة مره اخرى",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(getActivity(),"يرجى التأكد من إتصال الإنترنت !",Toast.LENGTH_SHORT).show();
            }
        });

        mQueue.add(request);
    }


    private void Successful_LoginIsCashVan(final String User , final String Pass)
    {

        progressDialog= new ProgressDialog(getActivity());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);



        conRoyal.UrlRoyal_LoginIsCashVan(User,Pass,Global.DataSourceName);

        mQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.POST, conRoyal.Url_LoginIsCashVan, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {

                    JSONObject jsonArray = response.getJSONObject(0);
                    Global.LoginBranchID = jsonArray.optInt("Bu",0);
                    Global.LoginBranchName = jsonArray.optString("LoginBranchName");
                    Global.LoginStoreName = jsonArray.optString("LoginStoreName");
                    Global.LoginBranchID = jsonArray.optInt("Bu",0);
                    Global.LoginCashID = jsonArray.optInt("LoginCashID",0);
                    Global.LoginStoreID = jsonArray.optInt("RelatedStore",0);
                    Global.IsUsePos = jsonArray.optBoolean("IsUsePos",false);
                    Global.IsUser = jsonArray.optBoolean("IsUser",false);
                    Global.IsAdmin = jsonArray.optBoolean("IsAdmin",false);
                    Global.IsMergeOrder = jsonArray.optBoolean("IsMergeOrder",false);
                    conRoyal.IDUser = jsonArray.optInt("ID",0);

                    UserName = User;
                    conRoyal.UserName=User;
                    Password = Pass;
                    String ConStr = jsonArray.getString("ConStr");

                    try {
                        //------------- تحميل المتغيرات في حالة نجاح الاتصال--------------------------------------
                        if(Global.IsOrderSales)
                        {
                            if (Global.IsUsePos) {
                                if (Global.LoginBranchID == 0) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "لا يوجد فرع مربوط في هذا المندوب ، يرجى ربط الفرع ومحاولة الدخول مرة اخرى", Toast.LENGTH_SHORT).show();
                                } else if (Global.LoginStoreID == 0) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "لا يوجد مستودع مربوط في هذا المندوب ، يرجى ربط المستودع ومحاولة الدخول مرة اخرى", Toast.LENGTH_SHORT).show();
                                } else {
                                    conRoyal.UrlRoyal_ConnectionString2(ConStr);
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), getString(R.string.Welcome) + conRoyal.UserName, Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(getActivity(),frmCashVanPos.class);
                                    intent.putExtra("TransType",15);
                                    intent.putExtra("CustomerID",-2);
                                    intent.putExtra("routeDetailsID",0);
                                    startActivity(intent);
                                }

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "IsUsePos عذرا لا يوجد لديك صلاحية للدخول الى نظام نقاط البيع", Toast.LENGTH_SHORT).show();
                            }


                        }
                        else if(Global.IsProcessingorder)
                        {
                          //  conRoyal.UrlRoyal_ConnectionString2(ConStr);
                          //  progressDialog.dismiss();
                           // Toast.makeText(getActivity(), getString(R.string.Welcome) + conRoyal.UserName, Toast.LENGTH_SHORT).show();
                            if(Global.IsMergeOrder)
                            {
                                Intent intent=new Intent(getActivity(),frmProcessingordersMerge.class);
                                startActivity(intent);
                            }
                            else {
                                Intent intent = new Intent(getActivity(), frmProcessingorders.class);
                                startActivity(intent);
                            }
                        }
                        else {
                            if (Global.IsUsePos) {
                                if (Global.LoginBranchID == 0) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "لا يوجد فرع مربوط في هذا المندوب ، يرجى ربط الفرع ومحاولة الدخول مرة اخرى", Toast.LENGTH_SHORT).show();
                                } else if (Global.LoginStoreID == 0) {
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), "لا يوجد مستودع مربوط في هذا المندوب ، يرجى ربط المستودع ومحاولة الدخول مرة اخرى", Toast.LENGTH_SHORT).show();
                                } else {
                                    conRoyal.UrlRoyal_ConnectionString2(ConStr);
                                    progressDialog.dismiss();
                                    Toast.makeText(getActivity(), getString(R.string.Welcome) + conRoyal.UserName, Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getActivity(), CashVanPosDashBoard.class);
                                    startActivity(intent);
                                }

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "IsUsePos عذرا لا يوجد لديك صلاحية للدخول الى نظام نقاط البيع", Toast.LENGTH_SHORT).show();
                            }
                            //------------- تحميل المتغيرات في حالة نجاح الاتصال---------------------------------------
                        }
                    }
                    catch (Exception e)
                    {

                    }
                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),"يرجى التأكد من إسم المستخدم وكلمة المرور ، والمحاولة مره اخرى",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(getActivity(),"يرجى التأكد من إتصال الإنترنت !",Toast.LENGTH_SHORT).show();
            }
        });
        int socketTimeout = 60000;//30 seconds - change to what you want
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
        mQueue.add(request);
    }
    private void LoadVirable()
    {
        conRoyal.IDUser=IDUser;
        conRoyal.UserName=UserName;
        conRoyal.Password=Password;
        conRoyal.ipStatic=ipStatic;
        conRoyal.DataBase_Name=DataBase_Name;
        conRoyal.User_DB=User_DB;
        conRoyal.Password_DB=Password_DB;
        conRoyal.StartDate=StartDate;
        conRoyal.EndDate=EndDate;
        conRoyal.Activate_User=Activate_User;
        DataBaseHelper db=new DataBaseHelper(getActivity());
        db.Delete_LoginAccess_Screen();
        Access_User();
    }


    private void Access_User()
    {

        conRoyal.UrlRoyal_Access_Screen(conRoyal.IDUser);

        mQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Access_Screen, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for(int i = 0 ; i<response.length() ; i++)
                    {
                        JSONObject jsonArray = response.getJSONObject(i);

                        int Screen_id = jsonArray.getInt("Screen_id") ;
                        int Access_User = jsonArray.getInt("Access_User");

                        DataBaseHelper db=new DataBaseHelper(getActivity());


                        db.InsertToSqlLiteLoginAccess_Screen(conRoyal.IDUser,Screen_id,Access_User);
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
