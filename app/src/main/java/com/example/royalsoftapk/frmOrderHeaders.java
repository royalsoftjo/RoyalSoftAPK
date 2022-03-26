package com.example.royalsoftapk;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.InputType;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class
frmOrderHeaders extends AppCompatActivity {
    TextView TableID,TOrderID,TotalOrder,SecationName,NameTable;
    LinearLayout LineCategory;
    LinearLayout LineItems;
    LinearLayout LineItemAdditions;
    private RequestQueue mQueue;
    ProgressDialog progressDialog;
    SwipeMenuListView listitems;
    Dialog epicDialog;
    ImageView closebox;
    Button btnCancel,btnSave;
    int OrderID=0;
    int ID=0;
    int SectionID=0;
    int IdTable=0;
    int finalSectionID=0;
    String Vprices;
    String NameTables;
     CountDownTimer aCounter;
    ArrayList<ClsOrderSalesSelected> listNew = new ArrayList<ClsOrderSalesSelected>();
    Adapter_OrderSales_Selected Adapter = new Adapter_OrderSales_Selected(frmOrderHeaders.this, listNew,this);

    @Override
    public void onBackPressed() {
        UpdateStatusTableByTableID(Integer.parseInt(TableID.getText().toString()),0);
    }

    @Override
    protected void onUserLeaveHint() {
        UpdateStatusTableByTableID(Integer.parseInt(TableID.getText().toString()),0);
        super.onUserLeaveHint();
    }

    void UpdateStatusTableByTableID(final int TableID, int TableStatusID)
    {
        conRoyal.UrlRoyal_UpdateStatusTableByTableID(conRoyal.ConnectionString,TableID,TableStatusID);
        mQueue = Volley.newRequestQueue(frmOrderHeaders.this);
        JsonArrayRequest request2 =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_UpdateStatusTableByTableID, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response2) {
                Intent intent = new Intent(frmOrderHeaders.this, frmTablesHome.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(frmOrderHeaders.this,"يرجى التأكد من إتصال الإنترنت",Toast.LENGTH_SHORT).show();
            }
        });

        mQueue.add(request2);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_order_headers);
        TOrderID=findViewById(R.id.idOrder);
        TableID=findViewById(R.id.idTable);
        TotalOrder=findViewById(R.id.txtTotalOrderSales);
        SecationName=findViewById(R.id.NameSecation);
        btnSave=findViewById(R.id.btnSaveOrder);
        listitems=findViewById(R.id.listOrderSales);
        NameTable=findViewById(R.id.nameTable);

        Bundle extras = getIntent().getExtras();
        IdTable=Integer.parseInt(extras.get("IDTable").toString());
        SectionID=Integer.parseInt(extras.get("SectionID").toString());

        NameTables=String.valueOf(extras.get("TableName").toString());
        NameTable.setText(NameTables);
        TableID.setText(String.valueOf( IdTable));
        SecationName.setText(extras.get("SectionName").toString());
        OrderID=Integer.parseInt(extras.get("OrderID").toString());
        ID=Integer.parseInt(extras.get("ID").toString());
        TOrderID.setText(String.valueOf(ID));

        AddCardViewCategory(ID,"المجموعات","","#979A9A");
        GetdataCategory();
        GetOrderDetails(Integer.parseInt(TableID.getText().toString()));

        epicDialog = new Dialog(this);

        listitems.setAdapter(Adapter);

        finalSectionID = SectionID;
        final int finalIdTable = IdTable;
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSave.setEnabled(false);
                SavedOrder(OrderID,finalSectionID, finalIdTable,conRoyal.IDUser,Global.WaiterID);
            }
        });




        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                SwipeMenuItem btNote = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                btNote.setBackground(new ColorDrawable(Color.YELLOW));
                // set item width
                btNote.setWidth(170);
                // set item title
                btNote.setTitle("الملاحظات");
                // set item title fontsize
                btNote.setTitleSize(18);
                // set item title font color
                btNote.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(btNote);



                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.GRAY));
                // set item width
                openItem.setWidth(170);
                // set item title
                openItem.setTitle("الخيارات");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.BLUE));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
//                deleteItem.setIcon(R.drawable.ic_desktop_windows_black_24dp);
                deleteItem.setTitle("الإضافات");
                // set item title fontsize
                deleteItem.setTitleSize(18);
                // set item title font color
                deleteItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        listitems.setMenuCreator(creator);

        listitems.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final int position, SwipeMenu menu, int index)
            {
                switch (index) {
                    case 0://Note
                        boolean IsNotes=Adapter.alistItems.get(position).getHasNotes();
                        if(IsNotes)
                        {
                            ShowBoxInputNote(position,R.string.txtNote,R.string.txtNote22, InputType.TYPE_CLASS_TEXT);
                        }
                        break;
                    case 1://الخيارات
                        boolean IsHasOptions =Adapter.alistItems.get(position).getHasOptions();
                        if(IsHasOptions)
                        {
                            ShowItemOps(position);
                        }
                        else
                        {
                           Global.ShowAccessPopup(frmOrderHeaders.this,R.string.Sorry,R.string.txtIsHasOptions,"#b3b9ac");
                        }
                        break;
                    case 2://الإضافات
                        boolean IsHasAdditions =Adapter.alistItems.get(position).getHasAdditions();
                        if(IsHasAdditions)
                        {
                            ShowItemAdditions(position);
                        }
                        else
                        {
                            Global.ShowAccessPopup(frmOrderHeaders.this,R.string.Sorry,R.string.txtIsHasAdditions,"");
                        }
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }
    void  ShowBoxInputNote(final int RowIndex, int Text1 , int Text2,int Type)
    {
        final AlertDialog.Builder alert=new AlertDialog.Builder(frmOrderHeaders.this);
        View mView=getLayoutInflater().inflate(R.layout.custom_dailog_note,null);
        final EditText txt=mView.findViewById(R.id.txtinputNote);
        final TextView txtView=mView.findViewById(R.id.noteText1);
        txtView.setText(getResources().getString(Text1));
        txt.setHint(getResources().getString(Text2));
        txt.setInputType(Type);
        Button btn_Cancel=mView.findViewById(R.id.idCancel);
        Button btn_Ok=mView.findViewById(R.id.idOk);
        alert.setView(mView);
        final AlertDialog alertDialog=alert.create();
        alertDialog.setCanceledOnTouchOutside(false);
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
                if(!tt.equals("")) {
                    ClsOrderSalesSelected a = new ClsOrderSalesSelected("", "", "", "", "", "",0,0,false,0);
                    a.setID(String.valueOf(Adapter.alistItems.get(RowIndex).getID()));
                    a.setMainBarcode("0");
                    a.setItemName(txt.getText().toString());
                    a.setQty("0");
                    a.setPrice("0");
                    a.setTotal("0");
                    a.setTaxID(0);
                    a.setTax(false);
                    a.setHasOptions(false);
                    a.setIsDelete(true);
                    a.setNumber(false);
                    a.setHasAdditions(false);
                    a.setHasNotes(false);
                    a.setUpdatedQty(false);
                    a.setItemAdditions(false);
                    a.setItemNotes(true);
                    a.setItemOptions(false);
                    a.setNotes(txt.getText().toString());
                    listNew.add(RowIndex + 1, a);
                    Adapter.notifyDataSetChanged();
                }
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    void AddCardViewCategory(final Integer ID, String Text1, String Text2,String color)
    {
        LinearLayout l1=new LinearLayout(frmOrderHeaders.this);
        LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT );
        params0.gravity= Gravity.CENTER|Gravity.TOP;
        l1.setLayoutParams(params0);
        l1.setOrientation(LinearLayout.HORIZONTAL);
        l1.setWeightSum(2);

        final CardView cardView=new CardView(frmOrderHeaders.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(5,0,5,0);
        params.weight=1;
        cardView.setLayoutParams(params);
        cardView.setRadius(0);
        cardView.setTag(ID);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Integer idCategory=Integer.valueOf(cardView.getTag().toString());
                GetdataCategory();
            }
        });


        LinearLayout l2=new LinearLayout(frmOrderHeaders.this);
        l2.setOrientation(LinearLayout.VERTICAL);
        l2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        if(!color.equals(""))
        {
            l2.setBackgroundColor(Color.parseColor(color));
        }
        else {
            l2.setBackgroundColor(Color.rgb(0, 204, 204));
        }

        LinearLayout l3=new LinearLayout(frmOrderHeaders.this);
        l3.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT );
        params2.setMargins(30,10,10,0);
        params2.gravity=Gravity.RIGHT;
        l3.setLayoutParams(params2);


        TextView text1=new TextView(frmOrderHeaders.this);
        LinearLayout.LayoutParams paramstext = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
        paramstext.gravity=Gravity.RIGHT;
        text1.setLayoutParams(paramstext);
        text1.setTextColor(Color.BLACK);
        text1.setTextSize(24);
        text1.setText(Text1);

        TextView text2=new TextView(frmOrderHeaders.this);
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

        LineCategory=findViewById(R.id.listcategory);
        LineCategory.addView(l1);
    }

    void AddCardViewItems(final Integer ID, String Text1,String SalesPrice,String color,Bitmap Picture,boolean IsHasAdditions,boolean IsHasOptions,boolean IsTax,int TaxID,boolean IsNumber,boolean IsVolatilePrices)
    {
        String Text2 = SalesPrice;

        LinearLayout l1=new LinearLayout(frmOrderHeaders.this);
        LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT ,LinearLayout.LayoutParams.MATCH_PARENT );
        //params0.gravity=Gravity.CENTER|Gravity.TOP;
        l1.setLayoutParams(params0);
        l1.setOrientation(LinearLayout.HORIZONTAL);
        l1.setWeightSum(2);





        final CardView cardView=new CardView(frmOrderHeaders.this);
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
        a.setIsDelete(true);
        a.setNumber(IsNumber);
        a.setQtyBefore(1);
        a.setPrice(SalesPrice);
        a.setTotal(SalesPrice);
        a.setHasAdditions(IsHasAdditions);
        a.setHasOptions(IsHasOptions);
        a.setHasNotes(true);
        a.setUpdatedQty(true);
        a.setTax(IsTax);
        a.setTaxID(TaxID);
        a.setStatus(0);
        a.setItemAdditions(false);
        a.setItemNotes(false);
        a.setItemOptions(false);
        a.setNotes("");
        a.setVolatilePrices(IsVolatilePrices);
        cardView.setTag(a);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ClsOrderSalesSelected getdata= (ClsOrderSalesSelected) cardView.getTag();
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
                    final ClsOrderSalesSelected a = new ClsOrderSalesSelected("", "", "", "", "", "",0,0,false,0);
                    a.setID(String.valueOf(getdata.getID()));
                    a.setMainBarcode("0");
                    a.setItemName(getdata.getItemName());
                    a.setQty("1");
                    a.setIsDelete(true);
                    a.setNumber(getdata.getisNumber());
                    a.setQtyBefore(1);
                    a.setPrice(getdata.getPrice());
                    a.setTotal(getdata.getPrice());
                    a.setHasAdditions(getdata.getHasAdditions());
                    a.setHasOptions(getdata.getHasOptions());
                    a.setHasNotes(getdata.getHasNotes());
                    a.setUpdatedQty(getdata.getUpdatedQty());
                    a.setTax(getdata.getTax());
                    a.setTaxID(getdata.getTaxID());
                    a.setStatus(0);
                    a.setItemAdditions(getdata.getItemAdditions());
                    a.setItemNotes(getdata.getItemNotes());
                    a.setItemOptions(getdata.getItemOptions());
                    a.setNotes("");
                    a.setVolatilePrices(getdata.isVolatilePrices());

                    if(getdata.isVolatilePrices())
                    {
                        //----------------------------- Dailog ItemName_New--------------------------------------------------------
                        final EditText inputtxt;
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(frmOrderHeaders.this);
                        builder.setTitle("إدخال السعر");
                        builder.setIcon(R.drawable.sms);
                        builder.setMessage(" يرجى إدخال السعر     ");

                        inputtxt = new EditText(frmOrderHeaders.this);
                        inputtxt.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                        builder.setView(inputtxt);
                        builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Vprices =inputtxt.getText().toString();
                                if (Vprices.equals("") || Vprices.equals("0")) {
                                    Toast.makeText(frmOrderHeaders.this, "يرجى إدخال السعر", Toast.LENGTH_LONG).show();
                                    return;
                                } else {
                                    a.setPrice(Vprices);
                                    listNew.add(a);
                                    Adapter.notifyDataSetChanged();
                                    sumTotalOrder();
                                    listitems.setSelection(listNew.size());
                                    Toast.makeText(frmOrderHeaders.this,"تم إضافة "+getdata.getItemName(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        final androidx.appcompat.app.AlertDialog add = builder.create();
                        add.show();
                        //--------------------------------------Button Dailog----------------------------------------------
                    }
                    else {
                        listNew.add(a);
                        Adapter.notifyDataSetChanged();
                        sumTotalOrder();
                        listitems.setSelection(listNew.size());
                        Toast.makeText(frmOrderHeaders.this,"تم إضافة "+getdata.getItemName(),Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });

        LinearLayout l2=new LinearLayout(frmOrderHeaders.this);
        l2.setOrientation(LinearLayout.VERTICAL);
        l2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        if(!color.equals(""))
        {
            l2.setBackgroundColor(Color.parseColor(color));
        }
        else {
            l2.setBackgroundColor(Color.rgb(0, 204, 204));
        }


        LinearLayout ln=new LinearLayout(frmOrderHeaders.this);
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


        LinearLayout l3=new LinearLayout(frmOrderHeaders.this);
        l3.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
        //params2.weight=1;
        params2.setMargins(30,10,10,0);
        l3.setLayoutParams(params2);



        TextView text1=new TextView(frmOrderHeaders.this);
        LinearLayout.LayoutParams paramstext = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
        paramstext.gravity=Gravity.RIGHT;
        text1.setLayoutParams(paramstext);
        text1.setTextColor(Color.BLACK);
        text1.setTextSize(24);
        text1.setText(Text1);



        TextView text2=new TextView(frmOrderHeaders.this);
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


        LineItems=findViewById(R.id.listitemswherecategory);
        LineItems.addView(l1);

    }



    void AddCardViewItems2(final Integer ID, String Text1,String color)
    {
        String Text2 = "";

        LinearLayout l1=new LinearLayout(frmOrderHeaders.this);
        LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT ,LinearLayout.LayoutParams.MATCH_PARENT );
        //params0.gravity=Gravity.CENTER|Gravity.TOP;
        l1.setLayoutParams(params0);
        l1.setOrientation(LinearLayout.HORIZONTAL);
        l1.setWeightSum(2);





        final CardView cardView=new CardView(frmOrderHeaders.this);
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
        a.setIsDelete(false);
        a.setNumber(false);
        a.setQtyBefore(1);
        a.setPrice("0");
        a.setTotal("0");
        a.setHasAdditions(false);
        a.setHasOptions(false);
        a.setHasNotes(false);
        a.setUpdatedQty(true);
        a.setTax(false);
        a.setTaxID(0);
        a.setStatus(0);
        a.setItemAdditions(false);
        a.setItemNotes(false);
        a.setItemOptions(false);
        a.setNotes("");
        cardView.setTag(a);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClsOrderSalesSelected getdata= (ClsOrderSalesSelected) cardView.getTag();
                int CategoryID=Integer.parseInt(getdata.getID());
                if(Global.IsShowSubCategory)
                {
                    getdataCategorySubCount(CategoryID);
                    progressDialog.dismiss();
                }
                else
                {
                    GetdataItemsByCategory(CategoryID);
                }

            }
        });

        LinearLayout l2=new LinearLayout(frmOrderHeaders.this);
        l2.setOrientation(LinearLayout.VERTICAL);
        l2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
        if(!color.equals(""))
        {
            l2.setBackgroundColor(Color.parseColor(color));
        }
        else {
            l2.setBackgroundColor(Color.rgb(0, 204, 204));
        }


        LinearLayout ln=new LinearLayout(frmOrderHeaders.this);
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
        Bitmap Picture=null;
        if(Picture==null) {
            img.setImageResource(R.drawable.logoroyal);
        }
        else
        {
            img.setImageBitmap(Picture);
        }


        LinearLayout l3=new LinearLayout(frmOrderHeaders.this);
        l3.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
        //params2.weight=1;
        params2.setMargins(30,10,10,0);
        l3.setLayoutParams(params2);



        TextView text1=new TextView(frmOrderHeaders.this);
        LinearLayout.LayoutParams paramstext = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
        paramstext.gravity=Gravity.RIGHT;
        text1.setLayoutParams(paramstext);
        text1.setTextColor(Color.BLACK);
        text1.setTextSize(24);
        text1.setText(Text1);



        TextView text2=new TextView(frmOrderHeaders.this);
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


        LineItems=findViewById(R.id.listitemswherecategory);
        LineItems.addView(l1);

    }
    private void GetdataCategory() {
        progressDialog= new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        conRoyal.UrlRoyal_GetCategory(conRoyal.ConnectionString);
        mQueue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,conRoyal.Url_GetCategory, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            boolean flag=false;
                            if(LineItems!=null) {
                                LineItems.removeAllViews();
                            }
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

                                AddCardViewItems2(ID,Name,Color);
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

    private void GetdataItemsByCategory(final int CategoryID) {
        progressDialog= new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                conRoyal.UrlRoyal_ListItemsALL(conRoyal.ConnectionString,CategoryID);
                mQueue = Volley.newRequestQueue(frmOrderHeaders.this);

                JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,conRoyal.Url_SelectListItemsALL, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    boolean flag=false;
                                    if(LineItems!=null) {
                                        LineItems.removeAllViews();
                                    }
                                    for (int i = 0; i < response.length(); i++) {
                                        flag=true;
                                        JSONObject obj = response.getJSONObject(i);
                                        Integer ID = obj.getInt("id");
                                        String Name = obj.getString("AName");
                                        String Color=obj.getString("color");
                                        String SalesPrice=obj.getString("SalesPrice");
                                        String Picture=obj.getString("picture");
                                        boolean IsHasAdditions=obj.getBoolean("IsHasAdditions");
                                        boolean IsHasOptions=obj.getBoolean("IsHasOptions");
                                        boolean IsTax=obj.getBoolean("IsTax");
                                        boolean IsNumber=obj.getBoolean("IsNumber");
                                        boolean IsVolatilePrices=obj.getBoolean("IsVolatilePrices");
                                        int TaxID=obj.getInt("SalesTaxID");

                                        if(Color.equals("null"))
                                        {
                                            Color="";
                                        }

                                        Bitmap bp = null;
                                        if (!Picture.equals("0")) {
                                            byte[] iimage = Base64.decode(Picture, Base64.DEFAULT);
                                            bp = BitmapFactory.decodeByteArray(iimage, 0, iimage.length);
                                        }

                                        AddCardViewItems(ID,Name,SalesPrice,Color,bp,IsHasAdditions,IsHasOptions,IsTax,TaxID,IsNumber,IsVolatilePrices);
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

    void AddCardViewItemops(final String Notes, int ItemID,String Text1, double SalesPrice ,LinearLayout lii,int Row )
    {
        LinearLayout l1=new LinearLayout(frmOrderHeaders.this);
        LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT );
        params0.gravity= Gravity.CENTER|Gravity.TOP;
        l1.setLayoutParams(params0);
        l1.setOrientation(LinearLayout.HORIZONTAL);
        l1.setWeightSum(2);

        final CardView cardView=new CardView(frmOrderHeaders.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(10,10,10,10);
        params.weight=1;
        cardView.setLayoutParams(params);
        cardView.setRadius(0);



        ClsOrderSalesSelected a = new ClsOrderSalesSelected("", "", "", "", "", "",0,0,false,0);
        a.setRowIndex(Row);
        a.setID(String.valueOf(ItemID));
        a.setAdditionID(0);
        a.setMainBarcode("0");
        a.setItemName(Text1);
        a.setQty("0");
        a.setQtyBefore(1);
        a.setIsDelete(true);
        a.setPrice(String.valueOf(SalesPrice));
        a.setTotal(String.valueOf(SalesPrice));
        a.setHasAdditions(false);
        a.setHasOptions(false);
        a.setHasNotes(true);
        a.setUpdatedQty(true);
        a.setNumber(false);
        a.setTax(false);
        a.setTaxID(0);
        a.setItemAdditions(false);
        a.setItemNotes(true);
        a.setItemOptions(false);
        a.setNotes(Notes);
        cardView.setTag(a);


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClsOrderSalesSelected getdata= (ClsOrderSalesSelected) cardView.getTag();
                int Rows=getdata.getRowIndex();
                listNew.add(Rows+1,getdata);
                Adapter.notifyDataSetChanged();
                Toast.makeText(frmOrderHeaders.this," تم إضافة "+ getdata.getItemName().toString(), Toast.LENGTH_LONG).show();
                sumTotalOrder();
            }
        });


        LinearLayout l2=new LinearLayout(frmOrderHeaders.this);
        l2.setOrientation(LinearLayout.VERTICAL);
        l2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

        l2.setBackgroundColor(Color.rgb(0, 204, 204));


        LinearLayout l3=new LinearLayout(frmOrderHeaders.this);
        l3.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT );
        params2.setMargins(30,10,10,0);
        params2.gravity=Gravity.RIGHT;
        l3.setLayoutParams(params2);


        TextView text1=new TextView(frmOrderHeaders.this);
        LinearLayout.LayoutParams paramstext = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
        paramstext.gravity=Gravity.RIGHT;
        text1.setLayoutParams(paramstext);
        text1.setTextColor(Color.WHITE);
        text1.setTextSize(24);
        text1.setText(Text1);

        TextView text2=new TextView(frmOrderHeaders.this);
        LinearLayout.LayoutParams paramstext2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
        paramstext2.gravity=Gravity.CENTER;
        paramstext2.setMargins(0,5,0,0);
        text2.setLayoutParams(paramstext2);
        text2.setTextColor(Color.RED);
        text2.setTextSize(20);
        text2.setText(String.valueOf(SalesPrice));

        l3.addView(text1);
        l3.addView(text2);

        l2.addView(l3);

        cardView.addView(l2);

        l1.addView(cardView);

        lii.addView(l1);
    }


    void AddCardViewSubCategory( int ID,String Text1 ,LinearLayout lii )
    {
        LinearLayout l1=new LinearLayout(frmOrderHeaders.this);
        LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT );
        params0.gravity= Gravity.CENTER|Gravity.TOP;
        l1.setLayoutParams(params0);
        l1.setOrientation(LinearLayout.HORIZONTAL);
        l1.setWeightSum(2);

        final CardView cardView=new CardView(frmOrderHeaders.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(10,10,10,10);
        params.weight=1;
        cardView.setLayoutParams(params);
        cardView.setRadius(0);



      ClsOrderSalesSelected a = new ClsOrderSalesSelected("", "", "", "", "", "",0,0,false,0);
        a.setRowIndex(0);
        a.setID(String.valueOf(ID));
        a.setAdditionID(0);
        a.setMainBarcode("0");
        a.setItemName(Text1);
        a.setQty("0");
        a.setQtyBefore(1);
        a.setIsDelete(true);
        a.setPrice(String.valueOf(""));
        a.setTotal(String.valueOf(""));
        a.setHasAdditions(false);
        a.setHasOptions(false);
        a.setHasNotes(true);
        a.setUpdatedQty(true);
        a.setNumber(false);
        a.setTax(false);
        a.setTaxID(0);
        a.setItemAdditions(false);
        a.setItemNotes(true);
        a.setItemOptions(false);
        a.setNotes("");
        cardView.setTag(a);


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClsOrderSalesSelected getdata= (ClsOrderSalesSelected) cardView.getTag();
                int CategoryID=Integer.parseInt(getdata.getID());
                GetdataItemsByCategory(CategoryID);
                epicDialog.dismiss();
               // int Rows=getdata.getRowIndex();
               // listNew.add(Rows+1,getdata);
               // Adapter.notifyDataSetChanged();
               // Toast.makeText(frmOrderHeaders.this," تم إضافة "+ getdata.getItemName().toString(), Toast.LENGTH_LONG).show();
              //  sumTotalOrder();
            }
        });


        LinearLayout l2=new LinearLayout(frmOrderHeaders.this);
        l2.setOrientation(LinearLayout.VERTICAL);
        l2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

        l2.setBackgroundColor(Color.rgb(0, 204, 204));


        LinearLayout l3=new LinearLayout(frmOrderHeaders.this);
        l3.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT );
        params2.setMargins(30,10,10,0);
        params2.gravity=Gravity.RIGHT;
        l3.setLayoutParams(params2);


        TextView text1=new TextView(frmOrderHeaders.this);
        LinearLayout.LayoutParams paramstext = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
        paramstext.gravity=Gravity.RIGHT;
        text1.setLayoutParams(paramstext);
        text1.setTextColor(Color.WHITE);
        text1.setTextSize(24);
        text1.setText(Text1);

        TextView text2=new TextView(frmOrderHeaders.this);
        LinearLayout.LayoutParams paramstext2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
        paramstext2.gravity=Gravity.CENTER;
        paramstext2.setMargins(0,5,0,0);
        text2.setLayoutParams(paramstext2);
        text2.setTextColor(Color.RED);
        text2.setTextSize(20);
        text2.setText(String.valueOf(""));

        l3.addView(text1);
        l3.addView(text2);

        l2.addView(l3);

        cardView.addView(l2);

        l1.addView(cardView);

        lii.addView(l1);
    }


    void AddCardViewItemAdditions(final Integer AdditionID, int ItemID,String Text1, double SalesPrice ,LinearLayout lii,int Row,boolean IsTax,int TaxID,boolean IsNumber)
    {
        LinearLayout l1=new LinearLayout(frmOrderHeaders.this);
        LinearLayout.LayoutParams params0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT );
        params0.gravity= Gravity.CENTER|Gravity.TOP;
        l1.setLayoutParams(params0);
        l1.setOrientation(LinearLayout.HORIZONTAL);
        l1.setWeightSum(2);

        final CardView cardView=new CardView(frmOrderHeaders.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        params.setMargins(10,10,10,10);
        params.weight=1;
        cardView.setLayoutParams(params);
        cardView.setRadius(0);



        ClsOrderSalesSelected a = new ClsOrderSalesSelected("", "", "", "", "", "",0,0,false,0);
        a.setRowIndex(Row);
        a.setID(String.valueOf(ItemID));
        a.setAdditionID(AdditionID);
        a.setMainBarcode("0");
        a.setItemName(Text1);
        a.setQty("1");
        a.setQtyBefore(1);
        a.setIsDelete(true);
        a.setPrice(String.valueOf(SalesPrice));
        a.setTotal(String.valueOf(SalesPrice));
        a.setHasAdditions(false);
        a.setHasOptions(false);
        a.setHasNotes(false);
        a.setUpdatedQty(true);
        a.setNumber(IsNumber);
        a.setTax(IsTax);
        a.setTaxID(TaxID);
        a.setItemAdditions(true);
        a.setItemNotes(false);
        a.setItemOptions(false);
        a.setNotes("");
        cardView.setTag(a);


        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClsOrderSalesSelected getdata= (ClsOrderSalesSelected) cardView.getTag();
                int Rows=getdata.getRowIndex();
                listNew.add(Rows+1,getdata);
                Adapter.notifyDataSetChanged();
                Toast.makeText(frmOrderHeaders.this," تم إضافة "+ getdata.getItemName().toString(), Toast.LENGTH_LONG).show();
                sumTotalOrder();
            }
        });


        LinearLayout l2=new LinearLayout(frmOrderHeaders.this);
        l2.setOrientation(LinearLayout.VERTICAL);
        l2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));

            l2.setBackgroundColor(Color.rgb(0, 204, 204));


        LinearLayout l3=new LinearLayout(frmOrderHeaders.this);
        l3.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.MATCH_PARENT );
        params2.setMargins(30,10,10,0);
        params2.gravity=Gravity.RIGHT;
        l3.setLayoutParams(params2);


        TextView text1=new TextView(frmOrderHeaders.this);
        LinearLayout.LayoutParams paramstext = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
        paramstext.gravity=Gravity.RIGHT;
        text1.setLayoutParams(paramstext);
        text1.setTextColor(Color.WHITE);
        text1.setTextSize(24);
        text1.setText(Text1);

        TextView text2=new TextView(frmOrderHeaders.this);
        LinearLayout.LayoutParams paramstext2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT );
        paramstext2.gravity=Gravity.CENTER;
        paramstext2.setMargins(0,5,0,0);
        text2.setLayoutParams(paramstext2);
        text2.setTextColor(Color.RED);
        text2.setTextSize(20);
        text2.setText(String.valueOf(SalesPrice));

        l3.addView(text1);
        l3.addView(text2);

        l2.addView(l3);

        cardView.addView(l2);

        l1.addView(cardView);

        lii.addView(l1);
    }

    private void getdataItemops(final int ItemID, final LinearLayout ln, final int Row, final Dialog dd) {
        progressDialog= new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        conRoyal.UrlRoyal_SelectItemopsByItemID(conRoyal.ConnectionString,ItemID);
        mQueue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,conRoyal.Url_GetSelectItemopsByItemID, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            boolean flag=false;
                            for (int i = 0; i < response.length(); i++) {
                                flag=true;
                                JSONObject obj = response.getJSONObject(i);

                                String Name = obj.getString("OptionName");
                                AddCardViewItemops(Name,ItemID,Name,0,ln,Row);

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

      private void getdataCategorySub(final int CategoryID, final LinearLayout ln, final Dialog dd) {
        progressDialog= new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        conRoyal.UrlRoyal_GetSubCategory(conRoyal.ConnectionString,CategoryID);
        mQueue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,conRoyal.Url_GetSubCategory, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if(response.length()==0)
                            {
                                GetdataItemsByCategory(CategoryID);
                                progressDialog.dismiss();
                                dd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                dd.show();
                                return;
                            }
                            boolean flag=false;
                            for (int i = 0; i < response.length(); i++) {
                                flag=true;
                                JSONObject obj = response.getJSONObject(i);
                                int ID=obj.getInt("ID");
                                String Name = obj.getString("AName");
                                AddCardViewSubCategory(ID,Name,ln);

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

    private void getdataCategorySubCount(final int CategoryID) {
        progressDialog= new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        conRoyal.UrlRoyal_GetSubCategory(conRoyal.ConnectionString,CategoryID);
        mQueue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,conRoyal.Url_GetSubCategory, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                            if(response.length()==0)
                            {
                                GetdataItemsByCategory(CategoryID);
                                progressDialog.dismiss();

                                return;
                            }
                            else
                            {
                                ShowSubCategory(CategoryID);
                                return;
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

    private void getdataItemAdditions(int ItemID, final LinearLayout ln, final int Row, final Dialog dd) {
        progressDialog= new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        conRoyal.UrlRoyal_SelectItemAdditionsByItemID(conRoyal.ConnectionString,ItemID);
        mQueue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,conRoyal.Url_SelectItemAdditionsByItemID, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            boolean flag=false;
                            for (int i = 0; i < response.length(); i++) {
                                flag=true;
                                JSONObject obj = response.getJSONObject(i);
                                Integer ID = obj.getInt("ItemID");
                                String Name = obj.getString("AName");
                                int AdditionID = obj.getInt("AdditionID");
                                double Price = obj.getDouble("Price");
                                boolean IsTax = obj.getBoolean("IsTax");
                                int TaxID = obj.getInt("SalesTaxID");
                                boolean IsNumber = obj.getBoolean("IsNumber");
                                AddCardViewItemAdditions(AdditionID,ID,Name,Price,ln,Row,IsTax,TaxID,IsNumber);

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

    void ShowItemAdditions (int row)
    {
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

        LineItemAdditions=epicDialog.findViewById(R.id.listItemAdditions);
        int ItemID=Integer.parseInt(Adapter.alistItems.get(row).getID());
        getdataItemAdditions(ItemID,LineItemAdditions,row,epicDialog);
        //epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //epicDialog.show();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });
    }

    void ShowItemOps (int row)
    {
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

        LineItemAdditions=epicDialog.findViewById(R.id.listItemAdditions);
        int ItemID=Integer.parseInt(Adapter.alistItems.get(row).getID());
        getdataItemops(ItemID,LineItemAdditions,row,epicDialog);
        //epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //epicDialog.show();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });
    }

    void ShowSubCategory (int CategoryID)
    {
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

        LineItemAdditions=epicDialog.findViewById(R.id.listItemAdditions);
        getdataCategorySub(CategoryID,LineItemAdditions,epicDialog);
        //epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //epicDialog.show();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });
    }

   public void sumTotalOrder()
    {
        double TotalRows=0;
        for (int i=0;i<Adapter.alistItems.size();i++) {
            TotalRows +=Double.parseDouble(Adapter.alistItems.get(i).getPrice())*Double.parseDouble(Adapter.alistItems.get(i).getQty());
        }
        DecimalFormat DF = new DecimalFormat("######0.000");
        TotalOrder.setText(DF.format(TotalRows));
    }

    void GetOrderDetails(final int TableID)
    {

      //  progressDialog= new ProgressDialog(frmOrderHeaders.this);
       // progressDialog.show();
       // progressDialog.setContentView(R.layout.progress_dialog);
      //  progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        if(listNew!=null)
        {
            listNew.clear();
        }
        conRoyal.UrlRoyal_SelectOrderDetailsByTableID(conRoyal.ConnectionString,TableID);

        mQueue = Volley.newRequestQueue(frmOrderHeaders.this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_SelectOrderDetailsByTableID, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {
                    boolean flag=false;
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = response.getJSONObject(i);
                        flag=true;
                        int ItemID = obj.getInt("ItemID");
                        String ItemName = obj.getString("ItemAName");
                        String CreationDate = obj.getString("CreationDate");
                        Double qty = obj.getDouble("QTY");
                        Double Price = obj.getDouble("Price");
                        final Double Total = obj.getDouble("Total");
                        Boolean IsItemModifireAddition=obj.getBoolean("IsItemModifireAddition");
                        Boolean IsItemNotes=obj.getBoolean("IsItemNotes");
                        int AdditionID = obj.getInt("AdditionID");
                        int Status = obj.getInt("Status");

                        if(Status==0)
                        {

                            progressDialog= new ProgressDialog(frmOrderHeaders.this);
                            progressDialog.show();
                            progressDialog.setContentView(R.layout.progress_dialog);
                            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            progressDialog.setCancelable(false);


                            new CountDownTimer(10000, 1000) {

                                public void onTick(long millisUntilFinished) {
                                }

                                public void onFinish() {
                                    progressDialog.dismiss();
                                    GetOrderDetails(Integer.parseInt(String.valueOf(TableID)));
                                }
                            }.start();

                            break;
                        }

                        ClsOrderSalesSelected a =new ClsOrderSalesSelected("","","","","","",0,0,false,0);

                        a.setID(String.valueOf(ItemID));
                        a.setItemName(String.valueOf(ItemName));
                        a.setCreationDate(CreationDate);
                        a.setQty(String.valueOf(qty));
                        a.setQtyBefore(qty);
                        a.setStatus(Status);
                        a.setIsDelete(false);
                        a.setPrice(String.valueOf(Price));
                        a.setTotal(String.valueOf(Total));
                        a.setItemNotes(IsItemNotes);
                       // if(Global.IsAdmin) {
                        if(true==false){
                            a.setUpdatedQty(true);
                        }
                        else
                        {
                            a.setUpdatedQty(false);
                        }

                        if(IsItemNotes) {
                            a.setHasNotes(false);
                            a.setHasAdditions(false);
                            a.setHasOptions(false);
                            a.setAdditionID(0);
                            a.setNotes(ItemName);

                        }
                        else
                        {
                             if(IsItemModifireAddition)
                             {
                                 a.setHasNotes(false);
                                 a.setHasAdditions(true);
                                // listitems.getChildAt(i).setBackgroundColor(Color.BLACK);
                                 a.setAdditionID(AdditionID);
                                 a.setHasOptions(false);
                                 a.setItemAdditions(true);

                             }
                             else
                             {
                                 //listitems.getChildAt(i).setBackgroundColor(Color.GREEN);
                                 a.setHasNotes(true);
                                 a.setHasAdditions(false);
                                 a.setItemAdditions(false);
                                 a.setAdditionID(0);
                                 a.setHasOptions(false);
                             }
                         }
                         listNew.add(a);
                         Adapter.notifyDataSetChanged();
                         //Adapter.list.setBackgroundColor(Color.GREEN);
                       // listitems.getChildAt(i).setBackgroundColor(Color.BLACK);

                    }
                    if(flag) {
                        flag=false;
                       // progressDialog.dismiss();
                        sumTotalOrder();
                    }


                } catch (JSONException e) {
               //     progressDialog.dismiss();
                    Toast.makeText(frmOrderHeaders.this,"يرجى التأكد من إتصال الإنترنت ، والمحاولة مره اخرى",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
             //   progressDialog.dismiss();
                Toast.makeText(frmOrderHeaders.this,"يرجى التأكد من إتصال الإنترنت !",Toast.LENGTH_SHORT).show();
                //DataBaseHelper db=new DataBaseHelper(getActivity());
                // db.Users(getActivity(),User,Pass);

            }
        });

        mQueue.add(request);

    }

    public  void ShowDataSaved(Context c, String Header, String Details)
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
                NewOrder();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
                NewOrder();
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }

    void NewOrder()
    {
        UpdateStatusTableByTableID(Integer.parseInt(TableID.getText().toString()),0);
    }

    void SavedOrder( int OrderID1,  int SectionID, final int TableID,int UserID, int WaiterID)  {
        progressDialog= new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JSONArray ArrayItemSelected=new JSONArray();
        JSONObject object = null;
        for (int i=0;i<listNew.size();i++)
        {
            object=new JSONObject();
            try {
                object.put("ItemID",listNew.get(i).getID());
                object.put("Price",listNew.get(i).getPrice());
                object.put("NewQty", Double.parseDouble(listNew.get(i).getQty()) - listNew.get(i).getQtyBefore());
                object.put("QTY", Double.parseDouble(listNew.get(i).getQty()));
                object.put("ItemName",listNew.get(i).getItemName());
                object.put("CreationDate",listNew.get(i).getCreationDate());
                object.put("IsTax",listNew.get(i).getTax());
                object.put("TaxID",listNew.get(i).getTaxID());
                object.put("AdditionID",listNew.get(i).getAdditionID());
                object.put("IsItemNotes",listNew.get(i).getItemNotes());
                object.put("IsItemModifireAdditions",listNew.get(i).getItemAdditions());
                object.put("IsItemOptions",listNew.get(i).getItemOptions());
                object.put("OptionID",0);
                object.put("OptionGroupID",0);
                object.put("Notes",listNew.get(i).getNotes());
                object.put("Status",listNew.get(i).getStatus());


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
            Post_OrderDetails pp = new Post_OrderDetails(conRoyal.ConnectionString,OrderID1, Global.LoginBranchID, Global.LoginStoreID, Global.LoginStationID, Global.LoginWorkDayDate, Global.WorkDayID, 0, SectionID, TableID, UserID,WaiterID, ArrayItemSelected.toString());
            Call<Post_OrderDetails> call = jsonPlaceHolderApi.InsertOrder(pp);
            call.enqueue(new Callback<Post_OrderDetails>() {
                @Override
                public void onResponse(Call<Post_OrderDetails> call, retrofit2.Response<Post_OrderDetails> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("true"))
                        {
                            progressDialog.dismiss();
                            ShowDataSaved(frmOrderHeaders.this, getString(R.string.txtCorrectoperation), getString(R.string.txtmigratedsystem));
                        }
                        else
                        {
                            progressDialog.dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Post_OrderDetails> call, Throwable t) {


                    progressDialog= new ProgressDialog(frmOrderHeaders.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    progressDialog.setCancelable(false);


                    new CountDownTimer(10000, 1000) {

                        public void onTick(long millisUntilFinished) {
                        }

                        public void onFinish() {
                            progressDialog.dismiss();
                            SavedOrder(OrderID,finalSectionID, IdTable,conRoyal.IDUser,Global.WaiterID);
                        }
                    }.start();
                }


            });

    }
}
