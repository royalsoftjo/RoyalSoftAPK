package com.example.royalsoftapk;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
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

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CashVanPosDashBoard extends AppCompatActivity {

    LinearLayout LineCard;
    List<clsGet> ListRounds=new ArrayList<clsGet>();
    private RequestQueue mQueue;
    Dialog epicDialog;
    ImageView closebox;
    ProgressDialog progressDialog;
    TextView txtHome,TotalCashHand,TotalAfterTaxCash,TotalAfterTaxAP;
    ImageView pic;
    boolean AllowRandom;
    @Override
    public void onBackPressed() {
        try {
            startActivity(new Intent(CashVanPosDashBoard.this, CashVanPosDashBoard.class));
        }
        catch (Exception ex)
        {
            Toast.makeText(CashVanPosDashBoard.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_van_pos_dash_board);
        LineCard=findViewById(R.id.listCardCashVanPos);
        pic=findViewById(R.id.pichome);
        txtHome=findViewById(R.id.txthome);
        TotalCashHand = findViewById(R.id.TotalCashHand);
        TotalAfterTaxCash = findViewById(R.id.cashd);
        TotalAfterTaxAP = findViewById(R.id.APd);
        epicDialog = new Dialog(this);
        GetAll();
        selectTotalCashHand();
        selectTotalSales(1);
        selectTotalSales(2);
        displayLocationSettingsRequest(this);
        Global.GetLocation(this,CashVanPosDashBoard.this);


        AddCardViewItemsAutoADD();
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
                            status.startResolutionForResult(CashVanPosDashBoard.this, 0);
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
    void AddCardViewItems()
    {
       // final DecimalFormat DF = new DecimalFormat("######0.000");
        LinearLayout l1 = null;
        int count=0;
        for (int i=0;i<ListRounds.size();i++)
        {

            if(i==0 || count==2)
            {

                l1=new LinearLayout(CashVanPosDashBoard.this);
                LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT ,200);
                //params0.gravity=Gravity.CENTER|Gravity.TOP;
                l1.setLayoutParams(params0);
                l1.setOrientation(LinearLayout.HORIZONTAL);
                l1.setWeightSum(150);

                clsGet cls=new clsGet(0,0,"","",false,false,0,"",0,null,true);
                cls.setID(ListRounds.get(i).getID());
                cls.setStatus(ListRounds.get(i).getStatus());
                cls.setAllowAlternate(ListRounds.get(i).isAllowAlternate());
                cls.setAllowRandom(ListRounds.get(i).isAllowRandom());
                cls.setCustomerId(ListRounds.get(i).getCustomerId());
                cls.setDayOfWeek(ListRounds.get(i).getDayOfWeek());
                cls.setRouteOrder(ListRounds.get(i).getRouteOrder());
                cls.setText1(ListRounds.get(i).getText1());
                cls.setText2(ListRounds.get(i).getText2());
                cls.setRandom(ListRounds.get(i).isRandom());

                final CardView cardView=new CardView(CashVanPosDashBoard.this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 200);
                params.setMargins(10,20,10,10);
                params.weight=50;
                cardView.setLayoutParams(params);
                cardView.setRadius(8);
                cardView.setTag(cls);

                final int finalI = i;
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clsGet getdata= (clsGet) cardView.getTag();

                            int Status = getdata.getStatus();
                            if (Status == 3) {
                                ShowDataSavedRound(CashVanPosDashBoard.this, getString(R.string.Request_retracted), getString(R.string.The_continue), getdata);
                            } else {
                                if(finalI ==0)
                                {
                                    Intent intent = new Intent(CashVanPosDashBoard.this, frmCashVanPos.class);
                                    intent.putExtra("CustomerID", getdata.getCustomerId());
                                    intent.putExtra("routeDetailsID", getdata.getID());
                                    intent.putExtra("TransType",33);
                                    startActivity(intent);
                                }
                                else
                                {
                                if(getdata.isRandom()) {
                                    Intent intent = new Intent(CashVanPosDashBoard.this, frmCashVanPos.class);
                                    intent.putExtra("CustomerID", getdata.getCustomerId());
                                    intent.putExtra("routeDetailsID", getdata.getID());
                                    intent.putExtra("TransType",33);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(CashVanPosDashBoard.this,"الترتيب غير عشوائي",Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });
                cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        clsGet cls=(clsGet)cardView.getTag();
                        int Status=cls.getStatus();
                        if(Status!=3) {
                            if(finalI ==0) {
                                Mune(cls);
                            }
                            else
                            {
                                if(cls.isRandom()) {
                                    Mune(cls);
                                }
                            }
                        }
                        return true;
                    }
                });

                LinearLayout l2=new LinearLayout(CashVanPosDashBoard.this);
                l2.setOrientation(LinearLayout.VERTICAL);
                l2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

       /* if(Status==1)
        {
            l2.setBackgroundColor(Color.parseColor("#000"));
        }
        else {
            l2.setBackgroundColor(Color.rgb(0, 204, 204));
        }*/

                l2.setBackgroundResource(R.drawable.bg9);
                LinearLayout ln=new LinearLayout(CashVanPosDashBoard.this);
                ln.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams paramsn = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT );
                ln.setLayoutParams(paramsn);
                //ln.setWeightSum(2);

                ImageView img=new ImageView(this);
                LinearLayout.LayoutParams paramsimg = new LinearLayout.LayoutParams(10,LinearLayout.LayoutParams.MATCH_PARENT  );
                img.setScaleType(ImageView.ScaleType.FIT_XY);
                paramsimg.gravity= Gravity.RIGHT;
                //paramsimg.weight=1;
                img.setLayoutParams(paramsimg);
               // img.setBackgroundColor(Color.WHITE);


                LinearLayout l3=new LinearLayout(CashVanPosDashBoard.this);
                l3.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
                //params2.weight=1;
                params2.setMargins(30,10,10,0);
                l3.setLayoutParams(params2);



                TextView text1=new TextView(CashVanPosDashBoard.this);
                LinearLayout.LayoutParams paramstext = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
                paramstext.gravity=Gravity.RIGHT;
                text1.setLayoutParams(paramstext);
                //text1.setTextColor(Color.WHITE);
                text1.setTextSize(20);
                text1.setText(ListRounds.get(i).getText1());



                TextView text2=new TextView(CashVanPosDashBoard.this);
                LinearLayout.LayoutParams paramstext2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
                paramstext2.gravity=Gravity.RIGHT;
                paramstext2.setMargins(0,10,0,0);
                text2.setLayoutParams(paramstext2);
                //text2.setTextColor(Color.RED);
                text2.setTextSize(20);
                text2.setText(ListRounds.get(i).getText2());

                switch (ListRounds.get(i).getStatus())
                {
                    case 0:
                        img.setBackgroundColor(Color.RED);
                        text1.setTextColor(Color.RED);
                        text2.setTextColor(Color.RED);
                        break;
                    case 1:
                        img.setBackgroundColor(Color.GREEN);
                        text1.setTextColor(Color.GREEN);
                        text2.setTextColor(Color.GREEN);
                        break;
                    case 2:
                        img.setBackgroundColor(Color.BLACK);
                        text1.setTextColor(Color.BLACK);
                        text2.setTextColor(Color.BLACK);
                        break;
                    case 3:
                        img.setBackgroundColor(Color.BLUE);
                        text1.setTextColor(Color.BLUE);
                        text2.setTextColor(Color.BLUE);
                        break;
                    case 4:
                        img.setBackgroundColor(Color.RED);
                        text1.setTextColor(Color.RED);
                        text2.setTextColor(Color.RED);
                        break;

                }

                l3.addView(text1);
                l3.addView(text2);


                ln.addView(img);
                ln.addView(l3);

                l2.addView(ln);

                cardView.addView(l2);


                l1.addView(cardView);

                LineCard.addView(l1);

              //  LineCard.addView(l1);
                count=0;

            }
            else
            {

                LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT ,200);
                //params0.gravity=Gravity.CENTER|Gravity.TOP;
                // l1.setLayoutParams(params0);
                //  l1.setOrientation(LinearLayout.HORIZONTAL);
                //   l1.setWeightSum(100);

                clsGet cls=new clsGet(0,0,"","",false,false,0,"",0,null,true);
                cls.setID(ListRounds.get(i).getID());
                cls.setStatus(ListRounds.get(i).getStatus());
                cls.setAllowAlternate(ListRounds.get(i).isAllowAlternate());
                cls.setAllowRandom(ListRounds.get(i).isAllowRandom());
                cls.setCustomerId(ListRounds.get(i).getCustomerId());
                cls.setDayOfWeek(ListRounds.get(i).getDayOfWeek());
                cls.setRouteOrder(ListRounds.get(i).getRouteOrder());
                cls.setText1(ListRounds.get(i).getText1());
                cls.setText2(ListRounds.get(i).getText2());
                cls.setRandom(ListRounds.get(i).isRandom());

                final CardView cardView=new CardView(CashVanPosDashBoard.this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, 200);
                params.setMargins(10,20,10,10);
                params.weight=50;
                cardView.setLayoutParams(params);
                cardView.setRadius(8);
                cardView.setTag(cls);
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clsGet getdata= (clsGet) cardView.getTag();
                        int Status=getdata.getStatus();
                        if(Status==3)
                        {
                            ShowDataSavedRound(CashVanPosDashBoard.this,getString(R.string.Request_retracted),getString(R.string.The_continue),getdata);
                        }
                        else {
                            if(getdata.isRandom()) {
                                Intent intent = new Intent(CashVanPosDashBoard.this, frmCashVanPos.class);
                                intent.putExtra("CustomerID", getdata.getCustomerId());
                                intent.putExtra("routeDetailsID", getdata.getID());
                                intent.putExtra("TransType",33);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(CashVanPosDashBoard.this,"الترتيب غير عشوائي",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

                cardView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        clsGet cls=(clsGet)cardView.getTag();
                        int Status=cls.getStatus();
                        if(Status!=3) {
                                if(cls.isRandom()) {
                                    Mune(cls);
                                }
                        }
                        return true;
                    }
                });

                LinearLayout l2=new LinearLayout(CashVanPosDashBoard.this);
                l2.setOrientation(LinearLayout.VERTICAL);
                l2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

       /* if(Status==1)
        {
            l2.setBackgroundColor(Color.parseColor("#000"));
        }
        else {
            l2.setBackgroundColor(Color.rgb(0, 204, 204));
        }*/
               // l2.setBackgroundColor(Color.rgb(0, 204, 204));
                l2.setBackgroundResource(R.drawable.bg9);
                LinearLayout ln=new LinearLayout(CashVanPosDashBoard.this);
                ln.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams paramsn = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT );
                ln.setLayoutParams(paramsn);
                //ln.setWeightSum(2);

                ImageView img=new ImageView(this);
                LinearLayout.LayoutParams paramsimg = new LinearLayout.LayoutParams(10,LinearLayout.LayoutParams.MATCH_PARENT  );
                img.setScaleType(ImageView.ScaleType.FIT_XY);
                paramsimg.gravity= Gravity.RIGHT;
                //paramsimg.weight=1;
                img.setLayoutParams(paramsimg);
               // img.setBackgroundColor(Color.WHITE);





                LinearLayout l3=new LinearLayout(CashVanPosDashBoard.this);
                l3.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
                //params2.weight=1;
                params2.setMargins(30,10,10,0);
                l3.setLayoutParams(params2);



                TextView text1=new TextView(CashVanPosDashBoard.this);
                LinearLayout.LayoutParams paramstext = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
                paramstext.gravity=Gravity.RIGHT;
                text1.setLayoutParams(paramstext);
                //text1.setTextColor(Color.WHITE);
                text1.setTextSize(20);
                text1.setText(ListRounds.get(i).getText1());



                TextView text2=new TextView(CashVanPosDashBoard.this);
                LinearLayout.LayoutParams paramstext2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
                paramstext2.gravity=Gravity.RIGHT;
                paramstext2.setMargins(0,10,0,0);
                text2.setLayoutParams(paramstext2);
                //text2.setTextColor(Color.RED);
                text2.setTextSize(20);
                text2.setText(ListRounds.get(i).getText2());


                switch (ListRounds.get(i).getStatus())
                {
                    case 0:
                        img.setBackgroundColor(Color.RED);
                        text1.setTextColor(Color.RED);
                        text2.setTextColor(Color.RED);
                        break;
                    case 1:
                        img.setBackgroundColor(Color.GREEN);
                        text2.setTextColor(Color.GREEN);
                        text2.setTextColor(Color.GREEN);
                        break;
                    case 2:
                        img.setBackgroundColor(Color.BLACK);
                        text2.setTextColor(Color.BLACK);
                        text2.setTextColor(Color.BLACK);
                        break;
                    case 3:
                        img.setBackgroundColor(Color.BLUE);
                        text2.setTextColor(Color.BLUE);
                        text2.setTextColor(Color.BLUE);
                        break;
                    case 4:
                        img.setBackgroundColor(Color.RED);
                        text1.setTextColor(Color.RED);
                        text2.setTextColor(Color.RED);
                        break;

                }

                l3.addView(text1);
                l3.addView(text2);


                ln.addView(img);
                ln.addView(l3);

                l2.addView(ln);

                cardView.addView(l2);


                l1.addView(cardView);

                count++;

            }
        }











     /*   ClsOrderSalesSelected a = new ClsOrderSalesSelected("", "", "", "", "", "");
        a.setID(String.valueOf(ID));
        a.setMainBarcode("0");
        a.setItemName(Text1);
        a.setQty("1");
        a.setIsDelete(true);
        // a.setNumber(IsNumber);
        a.setQtyBefore(1);
        a.setPrice(SalesPrice);
        a.setTotal(SalesPrice);
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
        cardView.setTag(a);*/

        /*cardView.setOnClickListener(new View.OnClickListener() {
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
        });*/



        if(ListRounds.size()==0)
        {
            pic.setVisibility(View.VISIBLE);
            txtHome.setVisibility(View.VISIBLE);
        }
        else
        {
            pic.setVisibility(View.GONE);
            txtHome.setVisibility(View.GONE);
        }

    }









    void AddCardViewItemsAutoADD()
    {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(c.getTime());
        // final DecimalFormat DF = new DecimalFormat("######0.000");
        LinearLayout l1 = null;
                l1=new LinearLayout(CashVanPosDashBoard.this);
                LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT ,LinearLayout.LayoutParams.MATCH_PARENT);
                //params0.gravity=Gravity.CENTER|Gravity.TOP;
                l1.setLayoutParams(params0);
                l1.setOrientation(LinearLayout.HORIZONTAL);
                l1.setWeightSum(150);

                clsGet cls=new clsGet(0,0,"","",false,false,0,"",0,null,true);
                cls.setID(-100);
                cls.setStatus(0);
                cls.setAllowAlternate(true);
                cls.setAllowRandom(true);
                cls.setCustomerId(0);
                cls.setDayOfWeek("");
                cls.setRouteOrder(0);
                cls.setText1("إضافة جولة جديدة");

                cls.setText2(strDate);
                cls.setRandom(true);

                final CardView cardView=new CardView(CashVanPosDashBoard.this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                params.setMargins(10,20,10,10);
                params.weight=50;
                cardView.setLayoutParams(params);
                cardView.setRadius(8);
                cardView.setTag(cls);


                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clsGet getdata= (clsGet) cardView.getTag();

                        int ID = getdata.getID();
                      if(ID==-100)
                      {
                          SaveCashVanRo(-2);
                      }
                    }
                });


                LinearLayout l2=new LinearLayout(CashVanPosDashBoard.this);
                l2.setOrientation(LinearLayout.VERTICAL);
                l2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));



                l2.setBackgroundResource(R.drawable.bg9);
                LinearLayout ln=new LinearLayout(CashVanPosDashBoard.this);
                ln.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams paramsn = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT );
                ln.setLayoutParams(paramsn);
                //ln.setWeightSum(2);

                ImageView img=new ImageView(this);
                LinearLayout.LayoutParams paramsimg = new LinearLayout.LayoutParams(10,LinearLayout.LayoutParams.MATCH_PARENT  );
                img.setScaleType(ImageView.ScaleType.FIT_XY);
                paramsimg.gravity= Gravity.RIGHT;
                //paramsimg.weight=1;
                img.setLayoutParams(paramsimg);
                // img.setBackgroundColor(Color.WHITE);


                LinearLayout l3=new LinearLayout(CashVanPosDashBoard.this);
                l3.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
                //params2.weight=1;
                params2.setMargins(30,10,10,0);
                l3.setLayoutParams(params2);



                TextView text1=new TextView(CashVanPosDashBoard.this);
                LinearLayout.LayoutParams paramstext = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
                paramstext.gravity=Gravity.RIGHT;
                text1.setLayoutParams(paramstext);
                //text1.setTextColor(Color.WHITE);
                text1.setTextSize(20);
                text1.setText("إضافة جولة جديدة");



                TextView text2=new TextView(CashVanPosDashBoard.this);
                LinearLayout.LayoutParams paramstext2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
                paramstext2.gravity=Gravity.RIGHT;
                paramstext2.setMargins(0,10,0,0);
                text2.setLayoutParams(paramstext2);
                //text2.setTextColor(Color.RED);
                text2.setTextSize(20);
                text2.setText(strDate);


                        img.setBackgroundColor(Color.DKGRAY);
                        text1.setTextColor(Color.DKGRAY);
                        text2.setTextColor(Color.DKGRAY);



                l3.addView(text1);
                l3.addView(text2);


                ln.addView(img);
                ln.addView(l3);

                l2.addView(ln);

                cardView.addView(l2);


                l1.addView(cardView);

                LineCard.addView(l1);

                //  LineCard.addView(l1);


    }
    public  void ShowDataSavedRound(Context c, String Header, String Details, final clsGet cls)
    {
        final Dialog epicDialog = new Dialog(c);
        ImageView closebox,Image;
        Button btnCancel,btnOk;
        TextView textHeader,textDetails;

        epicDialog.setContentView(R.layout.boxdailog_yes_or_no);
        epicDialog.setCancelable(false);
        textHeader=(TextView) epicDialog.findViewById(R.id.txtbox_yes_or_no1);
        textDetails=(TextView)epicDialog.findViewById(R.id.txtbox_yes_or_no2);
        closebox=(ImageView)epicDialog.findViewById(R.id.bt_box_yes_or_no);
        btnCancel=(Button)epicDialog.findViewById(R.id.btn_box_yes_or_no_close);
        btnOk=(Button)epicDialog.findViewById(R.id.btn_Ok);
        Image=epicDialog.findViewById(R.id.box_image_yes_or_no);

        Image.setImageResource(R.drawable.ic_error_outline_black_24dp);

        textHeader.setText(Header);
        textDetails.setText(Details);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InsertRound(cls.getCustomerId(),cls.getID(),4);
            }
        });
        closebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
                startActivity(new Intent(CashVanPosDashBoard.this,CashVanPosDashBoard.class));
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
                startActivity(new Intent(CashVanPosDashBoard.this,CashVanPosDashBoard.class));
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

    private void InsertRound(int CustomerID,int routeDetailsID,int RouteStatus)
    {
        //Global.GetLocation(CashVanPosDashBoard.this);
            progressDialog = new ProgressDialog(this);
            progressDialog.setCancelable(false);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

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
            int BatteryPercentage=Global.GetBatteryLevel(CashVanPosDashBoard.this);

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
                            ShowDataSaved(CashVanPosDashBoard.this, getString(R.string.txtCorrectoperation), getString(R.string.txtSuccessfully_Print));
                        } else {
                            progressDialog.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ClsCashVanPosRound> call, Throwable t) {

                    Global.ShowDataSavedToSqlLitePopup(CashVanPosDashBoard.this);

                }


            });
        }


    public  void ShowDataSaved(Context c, String Header, String Details)
    {
        ImageView closebox;
        Button btnCancel;
        TextView textHeader,textDetails;

        epicDialog.setContentView(R.layout.from_failog);
        textHeader=(TextView) epicDialog.findViewById(R.id.txtHeaderDailog);
        textDetails=(TextView)epicDialog.findViewById(R.id.txtBodyDailog);
        closebox=(ImageView)epicDialog.findViewById(R.id.bt_N_close_access);
        btnCancel=(Button)epicDialog.findViewById(R.id.btn_Cancel_Now);

        textHeader.setText(getString(R.string.txtCorrectoperation));
        textDetails.setText(getString(R.string.Saved_successfully));
        closebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
                startActivity(new Intent(CashVanPosDashBoard.this,CashVanPosDashBoard.class));
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
                startActivity(new Intent(CashVanPosDashBoard.this,CashVanPosDashBoard.class));
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }
    void Mune(final clsGet cls)
    {
        try {
            epicDialog.setContentView(R.layout.box_button_land);
            closebox = (ImageView) epicDialog.findViewById(R.id.bt_N_closeCashVan);
            LinearLayout btnCancel_Round = (LinearLayout) epicDialog.findViewById(R.id.btnCancel_Round);
            LinearLayout btn_replace_Round = (LinearLayout) epicDialog.findViewById(R.id.btn_replace_Round);

            btn_replace_Round.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    epicDialog.dismiss();
                }
            });
            btnCancel_Round.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InsertRound(cls.getCustomerId(),cls.getID(),3);
                    epicDialog.dismiss();
                }
            });

            closebox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    epicDialog.dismiss();
                }
            });

            epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            epicDialog.show();



        }
        catch (Exception ex)
        {
            Toast.makeText(CashVanPosDashBoard.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    private void  GetAll()
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        conRoyal.UrlRoyal_SelectCashVanPosDashBoard(conRoyal.ConnectionString);

        mQueue = Volley.newRequestQueue(CashVanPosDashBoard.this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_GetSelectCashVanPosDashBoard, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for(int i =0 ; i < response.length() ; i++)
                    {
                        JSONObject jsonArray = response.getJSONObject(i);
                        int ID = jsonArray.getInt("ID");
                        int RouteOrder = jsonArray.getInt("RouteOrder");
                        int Stutas = jsonArray.optInt("routeStatus",0);
                        int CustomerId = jsonArray.optInt("CustomerId",0);
                        AllowRandom = jsonArray.optBoolean("AllowRandom",false);
                        boolean AllowAlternate = jsonArray.optBoolean("AllowAlternate",false);
                        String AName = jsonArray.optString("AName","");
                        String Address=jsonArray.optString("Telephone3","");
                        String Pohne=jsonArray.optString("Telephone1","");
                        String RouteDate=jsonArray.optString("RouteDate","");
                        RouteDate=RouteDate.substring(0,10);
                        String DayOfWeek=jsonArray.optString("DayOfWeek","");
                        ListRounds.add(new clsGet(ID,Stutas, AName + "\n" + RouteDate,Address+"\n"+Pohne,AllowRandom,AllowAlternate,RouteOrder,DayOfWeek,CustomerId,null,AllowRandom));
                    }
                    AddCardViewItems();
                    progressDialog.dismiss();
                } catch (JSONException e) {

                    progressDialog.dismiss();
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

    private void selectTotalCashHand()
    {
        try {
            conRoyal.UrlRoyal_Query(conRoyal.ConnectionString,"select sum(Amount) as amount from CashVoucherHeader where Bu="+Global.LoginBranchID+" and IssueUser="+conRoyal.IDUser+" and cast(CreationDate as date)=cast(GETDATE() as date) and isnull( DeleteUserId ,0)=0 ");

            mQueue = Volley.newRequestQueue(CashVanPosDashBoard.this);
            JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Query, null, new Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        DecimalFormat DF = new DecimalFormat("######0.000");
                        JSONObject jsonArray = response.getJSONObject(0);
                        double   totalBalance = jsonArray.getDouble("amount");
                        TotalCashHand.setText(DF.format(totalBalance));
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
            Toast.makeText(CashVanPosDashBoard.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void selectTotalSales(final int type)
    {
        try {
            conRoyal.UrlRoyal_Query(conRoyal.ConnectionString,"select isnull(sum(TotalAfterTax),0) as TotalAfterTax from TransactionHeader where TranactionType=33 and SettelmentWayId="+type+" and cast(CreationDate as date)= cast(GETDATE() as date) and CreationUserId="+conRoyal.IDUser+" and bu="+Global.LoginBranchID+" and isnull(IsDelete,0)=0");

            mQueue = Volley.newRequestQueue(CashVanPosDashBoard.this);
            JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Query, null, new Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        DecimalFormat DF = new DecimalFormat("######0.000");
                        JSONObject jsonArray = response.getJSONObject(0);
                        double   totalBalance = jsonArray.getDouble("TotalAfterTax");
                        if(type==1)
                        {
                            TotalAfterTaxCash.setText(DF.format(totalBalance));
                        }
                        else if(type==2)
                        {
                            TotalAfterTaxAP.setText(DF.format(totalBalance));
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
        catch (Exception ex)
        {
            Toast.makeText(CashVanPosDashBoard.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void  SaveCashVanRo(int CustomerID)
    {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        conRoyal.UrlRoyal_GetSaveCashVanRoutesAuto(conRoyal.ConnectionString,conRoyal.IDUser,Global.LoginBranchID,Global.LoginStoreID,CustomerID);

        mQueue = Volley.newRequestQueue(CashVanPosDashBoard.this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_GetSaveCashVanRoutesAuto, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject jsonArray = response.getJSONObject(0);
                    boolean Status=jsonArray.optBoolean("Status",false);
                    if(Status)
                    {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
                GetAll();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //int r=requestCode;
        int i=resultCode;
        if(resultCode==0)
        {
            displayLocationSettingsRequest(this);
        }
    }
}

