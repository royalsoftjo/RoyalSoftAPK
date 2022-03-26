package com.example.royalsoftapk;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class frmTablesHome extends AppCompatActivity {
    Adapter adapter;
    LinearLayout lOne;
    LinearLayout lTables;
    int SectionID=0;
    String SectionName;
    String TableName;
    private RequestQueue mQueue;
    Dialog epicDialog;
    ImageView closebox;
    Button btnCancel;
    ProgressDialog progressDialog;
    @Override
    public void onBackPressed() {
        finish();
        Intent intent = new Intent(frmTablesHome.this, frmTablesHome.class);
        startActivity(intent);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_tables_home);
        epicDialog = new Dialog(this);

        getdataSections();

    }
    private void getdataSections() {
        conRoyal.UrlRoyal_SelectSections(conRoyal.ConnectionString);
        mQueue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,conRoyal.Url_SelectSections, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                Integer ID = obj.getInt("ID");
                                String Name = obj.getString("AName");
                                String Count = obj.getString("TableCount");
                                AddCardViewSections(ID,Name,Count);
                                if(i==0)
                                {
                                    SectionID=ID;
                                    SectionName=Name;
                                    getdataTables(ID);
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
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

    private void getdataTables(int SectionID) {
        conRoyal.UrlRoyal_SelectTables(conRoyal.ConnectionString,SectionID);
        mQueue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,conRoyal.Url_SelectTables, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if(lTables!=null) {
                                lTables.removeAllViews();
                            }
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);
                                Integer ID = obj.getInt("ID");
                                String Name = obj.getString("AName");
                                String TableStatusIDS=obj.getString("TableStatusID");
                                Integer TableStatusID=1;
                                if(!TableStatusIDS.equals("null"))
                                {
                                    TableStatusID=Integer.valueOf(obj.getInt("TableStatusID")) ;
                                }


                                AddCardViewTables(ID,Name,TableStatusID);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
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

    void AddCardViewSections(final Integer ID, String Text1, String Text2)
    {
        LinearLayout l1=new LinearLayout(frmTablesHome.this);
        LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT );
        params0.gravity=Gravity.CENTER|Gravity.TOP;
        l1.setLayoutParams(params0);
        l1.setOrientation(LinearLayout.HORIZONTAL);
        l1.setWeightSum(2);

        final CardView cardView=new CardView(frmTablesHome.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 120);
        params.setMargins(5,0,5,0);
        params.weight=1;
        cardView.setLayoutParams(params);
        cardView.setRadius(8);
        cardView.setTag(ID);
        cardView.setTransitionName(Text1);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer id2=Integer.valueOf(cardView.getTag().toString());
                SectionID=id2;
                SectionName=String.valueOf(cardView.getTransitionName());
                getdataTables(id2);
            }
        });


        LinearLayout l2=new LinearLayout(frmTablesHome.this);
        l2.setOrientation(LinearLayout.VERTICAL);
        l2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        l2.setBackgroundColor(Color.GRAY);


        LinearLayout l3=new LinearLayout(frmTablesHome.this);
        l3.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
        params2.setMargins(30,10,10,0);
        params2.gravity=Gravity.RIGHT;
        l3.setLayoutParams(params2);


        TextView text1=new TextView(frmTablesHome.this);
        LinearLayout.LayoutParams paramstext = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
        paramstext.gravity=Gravity.RIGHT;
        text1.setLayoutParams(paramstext);
        text1.setTextColor(Color.WHITE);
        text1.setTextSize(24);
        text1.setText(Text1);



        TextView text2=new TextView(frmTablesHome.this);
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

        lOne=findViewById(R.id.testtt1);
        lOne.addView(l1);
    }

    void UpdateStatusTableByTableID(final int TableID, int TableStatusID, final String AName)
    {
            conRoyal.UrlRoyal_UpdateStatusTableByTableID(conRoyal.ConnectionString,TableID,TableStatusID);

            mQueue = Volley.newRequestQueue(frmTablesHome.this);
            JsonArrayRequest request2 =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_UpdateStatusTableByTableID, null, new Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONArray response2) {
                    //Toast.makeText(frmTablesHome.this,"تم إغلاق الكاش بنجاح",Toast.LENGTH_SHORT).show();
                    GetOrderHeaders(TableID,AName);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(frmTablesHome.this,"يرجى التأكد من إتصال الإنترنت",Toast.LENGTH_SHORT).show();
                }
            });

            mQueue.add(request2);
    }
    void UpdateStatusTableByTableID2(final int TableID, int TableStatusID)
    {
        conRoyal.UrlRoyal_UpdateStatusTableByTableID(conRoyal.ConnectionString,TableID,TableStatusID);

        mQueue = Volley.newRequestQueue(frmTablesHome.this);
        JsonArrayRequest request2 =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_UpdateStatusTableByTableID, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response2) {
                //Toast.makeText(frmTablesHome.this,"تم إغلاق الكاش بنجاح",Toast.LENGTH_SHORT).show();
               finish();
               startActivity(new Intent(frmTablesHome.this,frmTablesHome.class));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(frmTablesHome.this,"يرجى التأكد من إتصال الإنترنت",Toast.LENGTH_SHORT).show();
            }
        });

        mQueue.add(request2);
    }

    public void ShowAccessPopup()
    {
        epicDialog.setContentView(R.layout.boxdailogisused);
        closebox=(ImageView)epicDialog.findViewById(R.id.bt_N_close_accessOrder);
        btnCancel=(Button)epicDialog.findViewById(R.id.btn_Cancel_NowOreder);


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

    void  GetStatusTableAndOpen(final int TableID)
    {
        progressDialog= new ProgressDialog(frmTablesHome.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);



        conRoyal.UrlRoyal_Query(conRoyal.ConnectionString,"select AName,TableStatusID from Tables where ID="+TableID);

        mQueue = Volley.newRequestQueue(frmTablesHome.this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Query, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonArray = response.getJSONObject(0);
                    int getTableStatusID = jsonArray.getInt("TableStatusID");
                    String getTablename = jsonArray.getString("AName");
                    if(getTableStatusID==2) {
                        progressDialog.dismiss();
                        ShowAccessPopup();
                    }
                    else {
                        UpdateStatusTableByTableID(TableID,2,getTablename);
                        // Intent intent = new Intent(frmTablesHome.this, frmOrderHeaders.class);
                        //    intent.putExtra("IDTable", id2);
                        //  intent.putExtra("SectionID", SectionID);
                        //  startActivity(intent);
                    }

                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(frmTablesHome.this,"يرجى التأكد من إتصال الإنترنت ، والمحاولة مره اخرى",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(frmTablesHome.this,"يرجى التأكد من إتصال الإنترنت !",Toast.LENGTH_SHORT).show();
                //DataBaseHelper db=new DataBaseHelper(getActivity());
                // db.Users(getActivity(),User,Pass);

            }
        });

        mQueue.add(request);

    }
    void AddCardViewTables(final Integer ID, String Text1, int TableStatusID)
    {
        String Text2 = "";

        LinearLayout l1=new LinearLayout(frmTablesHome.this);
        LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT ,LinearLayout.LayoutParams.MATCH_PARENT );
        //params0.gravity=Gravity.CENTER|Gravity.TOP;
        l1.setLayoutParams(params0);
        l1.setOrientation(LinearLayout.VERTICAL);
        l1.setWeightSum(2);

        final CardView cardView=new CardView(frmTablesHome.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(10,0,10,50);
        params.weight=1;
        cardView.setLayoutParams(params);
        cardView.setRadius(8);
        cardView.setTag(ID);
        cardView.setTransitionName(String.valueOf(TableStatusID));
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer TableID=Integer.valueOf(cardView.getTag().toString());
                GetStatusTableAndOpen(TableID);
            }
        });

        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //Integer TableID=Integer.valueOf(cardView.getTag().toString());
                //UpdateStatusTableByTableID2(TableID,0);
                return true;
            }
        });

        LinearLayout l2=new LinearLayout(frmTablesHome.this);
        l2.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams paramsl2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        l2.setLayoutParams(paramsl2);
        if(TableStatusID==1) {
            Text2="فارغة";
            l2.setBackgroundColor(Color.rgb(17,104,46));
        }
        else if(TableStatusID==2)
        {
            Text2="تحت الطلب";
            l2.setBackgroundColor(Color.rgb(204,0,102));
        }
        else if(TableStatusID==3)
        {
            Text2="مشغولة";
            l2.setBackgroundColor(Color.rgb(204,0,0));
        }
        else if(TableStatusID==5)
        {
            Text2="كشف حساب مطبوع";
            l2.setBackgroundColor(Color.rgb(204,204,0));
        }
        else
        {
            Text2="غير ذلك";
            l2.setBackgroundColor(Color.rgb(51,51,0));
        }

        LinearLayout l3=new LinearLayout(frmTablesHome.this);
        l3.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
        params2.setMargins(30,0,10,0);
        params2.gravity=Gravity.RIGHT;
        l3.setLayoutParams(params2);




        TextView text1=new TextView(frmTablesHome.this);
        LinearLayout.LayoutParams paramstext = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
        paramstext.gravity=Gravity.RIGHT;
        text1.setLayoutParams(paramstext);
        text1.setTextColor(Color.WHITE);
        text1.setTextSize(24);
        text1.setText(Text1);



        TextView text2=new TextView(frmTablesHome.this);
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

        lTables=findViewById(R.id.testtt2);
        lTables.addView(l1);
    }



    void GetOrderHeaders(final int TableID, final String Aname)
    {

        progressDialog= new ProgressDialog(frmTablesHome.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);



        conRoyal.UrlRoyal_SelectOrderHeaderByTableID(conRoyal.ConnectionString,TableID,Global.LoginWorkDayDate);

        mQueue = Volley.newRequestQueue(frmTablesHome.this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_SelectOrderHeaderByTableID, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {
                    int OrderID=0;
                    int ID=0;
                    JSONObject jsonArray = response.getJSONObject(0);
                    Boolean IsMax = jsonArray.getBoolean("IsMax");
                    if(IsMax)
                    {
                        OrderID =0;//jsonArray.getInt("OrderMaxID");
                    }
                    else {
                        OrderID = jsonArray.getInt("AutoID");
                        ID = jsonArray.getInt("ID");
                    }
                    try {
                        //------------- تحميل المتغيرات في حالة نجاح الاتصال--------------------------------------

                        progressDialog.dismiss();
                        finish();
                        Intent intent = new Intent(frmTablesHome.this, frmOrderHeaders.class);
                        intent.putExtra("IDTable", TableID);
                        intent.putExtra("TableName", Aname);
                        intent.putExtra("SectionName", SectionName);
                        intent.putExtra("SectionID", SectionID);
                        intent.putExtra("OrderID", OrderID);
                        intent.putExtra("ID", ID);
                        startActivity(intent);

                        //------------- تحميل المتغيرات في حالة نجاح الاتصال---------------------------------------

                    }
                    catch (Exception e)
                    {

                    }


                } catch (JSONException e) {
                    progressDialog.dismiss();
                    Toast.makeText(frmTablesHome.this,"يرجى التأكد من إتصال الإنترنت ، والمحاولة مره اخرى",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(frmTablesHome.this,"يرجى التأكد من إتصال الإنترنت !",Toast.LENGTH_SHORT).show();
                //DataBaseHelper db=new DataBaseHelper(getActivity());
                // db.Users(getActivity(),User,Pass);

            }
        });

        mQueue.add(request);

    }

}
