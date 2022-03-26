package com.example.royalsoftapk;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class frmItemsNew extends AppCompatActivity {
Button btnSave,btnNew;
    ImageButton btnCamera;
EditText txtBarcode,txtItemName,txtSalesPrice;
    Spinner spinnerCategory,spinnerTax,spinnerSupplier;
    private RequestQueue mQueue;
    int ID_Category=0;
    String Name_Category="";
    private TextWatcher textWatcher=null;
    DataBaseHelper da=new DataBaseHelper(this);
    List<Cls_Tax> ListTax=new ArrayList<>();
    List<Cls_Tax> ListSupplier=new ArrayList<>();
    List<Cls_Tax> ListCategory=new ArrayList<>();
    List<Cls_Tax> ListItems=new ArrayList<>();
    int spinnerTaxId=0;
    String spinnerTaxName="";
    Spinner spinnerItemName;
    int spinnerSupplierId=0;
    String spinnerSupplierName="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frm_items_new);
        btnSave=findViewById(R.id.btnSave);
        btnNew=findViewById(R.id.btnNew);
        txtBarcode=findViewById(R.id.txtBarcode);
        txtItemName=findViewById(R.id.txtItemName);
        txtSalesPrice=findViewById(R.id.txtSalesPrice);
        spinnerCategory=(Spinner) findViewById(R.id.sp_Categoery);
        spinnerTax=(Spinner) findViewById(R.id.sp_Tax);
        spinnerSupplier=(Spinner) findViewById(R.id.sp_supplier2);
        btnCamera=findViewById(R.id.btnCamera3);
        spinnerItemName=(Spinner) findViewById(R.id.sp_ItemName);
        GetTax();
        GetSupplier();
        GetCategory();
        GetItemsCompo();
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtBarcode.setText("");
                txtItemName.setText("");
                txtSalesPrice.setText("");
                spinnerCategory.setSelection(GetIndexByCategory(0));
                spinnerTax.setSelection(GetIndexByTax(0));
                spinnerSupplier.setSelection(GetIndexBySupplier(0));
                spinnerSupplierId=0;
                spinnerSupplierName="";
                spinnerTaxId=0;
                spinnerTaxName="";
                ID_Category=0;
                Name_Category="";
                Toast.makeText(frmItemsNew.this,"تم تجديد البيانات",Toast.LENGTH_SHORT).show();
            }
        });
        spinnerTax.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerTaxId=ListTax.get(position).getID();
                spinnerTaxName=ListTax.get(position).getAName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //-------------------------------Cls_Supplier_Spinner-----------------------------------------------------//
        spinnerItemName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SelectItemPriceByID(ListItems.get(position).getID());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//-------------------------------Cls_Supplier_Spinner-----------------------------------------------------//

        spinnerSupplier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerSupplierId=ListSupplier.get(position).getID();
                spinnerSupplierName=ListSupplier.get(position).getAName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r();
            }
        });
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
                        Toast.makeText(frmItemsNew.this,"جاري تحميل البيانات",Toast.LENGTH_SHORT).show();
                        Barcode=Barcode.substring(0,Barcode.length()-1);
                        Toast.makeText(frmItemsNew.this,Barcode.toString(),Toast.LENGTH_SHORT).show();
                        SelectItemPrice(Barcode);
                        txtBarcode.setText(Barcode);
                    }
                }
            }


        };
        txtBarcode.addTextChangedListener(textWatcher);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }
        });





        //-------------------------------Cls_Branch_Spinner-----------------------------------------------------//
      //  da.SetAdapterCategory(this,spinnerCategory);
        spinnerCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ID_Category=ListCategory.get(position).getID();
                Name_Category= ListCategory.get(position).getAName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    int GetIndexBySupplier(int ID)
    {
        for (int i=0;i<ListSupplier.size();i++)
        {
            if(ListSupplier.get(i).getID()==ID)
            {
                return i;
            }
        }
        return 0;
    }
    int GetIndexByTax(int ID)
    {
        for (int i=0;i<ListTax.size();i++)
        {
            if(ListTax.get(i).getID()==ID)
            {
                return i;
            }
        }
        return 0;
    }
    int GetIndexByCategory(int ID)
    {
        for (int i=0;i<ListCategory.size();i++)
        {
            if(ListCategory.get(i).getID()==ID)
            {
                return i;
            }
        }
        return 0;
    }
    private void  GetTax()
    {
        conRoyal.UrlRoyal_Query(conRoyal.ConnectionString,"select ID,Aname from tax");

        mQueue = Volley.newRequestQueue(frmItemsNew.this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Query, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<String> aa=new ArrayList<String>();
                    aa.add("--إختر --");
                    ListTax.add(new Cls_Tax(0,""));
                    for(int i =0 ; i < response.length() ; i++)
                    {
                        JSONObject jsonArray = response.getJSONObject(i);
                        int ID = jsonArray.getInt("ID");
                        String AName = jsonArray.getString("Aname");

                        ListTax.add(new Cls_Tax(ID,AName));
                        aa.add(AName);
                    }

                    ArrayAdapter<String> adapter2=new ArrayAdapter<String>(frmItemsNew.this,android.R.layout.simple_spinner_item,aa);
                    //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerTax.setAdapter(adapter2);

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


    private void  GetCategory()
    {
        conRoyal.UrlRoyal_Query(conRoyal.ConnectionString,"select ID,Aname from Category");

        mQueue = Volley.newRequestQueue(frmItemsNew.this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Query, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<String> aa=new ArrayList<String>();
                    aa.add("--إختر --");
                    ListCategory.add(new Cls_Tax(0,""));
                    for(int i =0 ; i < response.length() ; i++)
                    {
                        JSONObject jsonArray = response.getJSONObject(i);
                        int ID = jsonArray.getInt("ID");
                        String AName = jsonArray.getString("Aname");

                        ListCategory.add(new Cls_Tax(ID,AName));
                        aa.add(AName);
                    }

                    ArrayAdapter<String> adapter2=new ArrayAdapter<String>(frmItemsNew.this,android.R.layout.simple_spinner_item,aa);
                    //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCategory.setAdapter(adapter2);

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

    private void  GetItemsCompo()
    {
        conRoyal.UrlRoyal_Query(conRoyal.ConnectionString,"select ID,Aname from Items");

        mQueue = Volley.newRequestQueue(frmItemsNew.this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Query, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<String> aa=new ArrayList<String>();
                    aa.add("--إختر --");
                    ListItems.add(new Cls_Tax(0,""));
                    for(int i =0 ; i < response.length() ; i++)
                    {
                        JSONObject jsonArray = response.getJSONObject(i);
                        int ID = jsonArray.getInt("ID");
                        String AName = jsonArray.getString("Aname");

                        ListItems.add(new Cls_Tax(ID,AName));
                        aa.add(AName);
                    }

                    ArrayAdapter<String> adapter2=new ArrayAdapter<String>(frmItemsNew.this,android.R.layout.simple_spinner_item,aa);
                    //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerItemName.setAdapter(adapter2);

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

    private void  GetSupplier()
    {
        conRoyal.UrlRoyal_Query(conRoyal.ConnectionString,"select ID,Aname from supplier");

        mQueue = Volley.newRequestQueue(frmItemsNew.this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Query, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<String> aa=new ArrayList<String>();
                    aa.add("--إختر --");
                    ListSupplier.add(new Cls_Tax(0,""));
                    for(int i =0 ; i < response.length() ; i++)
                    {
                        JSONObject jsonArray = response.getJSONObject(i);
                        int ID = jsonArray.getInt("ID");
                        String AName = jsonArray.getString("Aname");

                        ListSupplier.add(new Cls_Tax(ID,AName));
                        aa.add(AName);
                    }

                    ArrayAdapter<String> adapter2=new ArrayAdapter<String>(frmItemsNew.this,android.R.layout.simple_spinner_item,aa);
                    //adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSupplier.setAdapter(adapter2);

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
    void r()
    {

        IntentIntegrator intentIntegrator=new IntentIntegrator(frmItemsNew.this);
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
void  RefreashForm()
{
    txtBarcode.setText("");
    txtItemName.setText("");
    txtSalesPrice.setText("");
    spinnerCategory.setSelection(GetIndexByCategory(0));
    spinnerTax.setSelection(GetIndexByTax(0));
    spinnerSupplier.setSelection(GetIndexBySupplier(0));
    spinnerSupplierId=0;
    spinnerSupplierName="";
    spinnerTaxId=0;
    spinnerTaxName="";
    ID_Category=0;
    Name_Category="";
}
    public void Save()
    {

        conRoyal.UrlRoyal_InsertItems(conRoyal.ConnectionString,Global.convertToEnglish(String.valueOf(txtBarcode.getText())),String.valueOf(txtItemName.getText()),ID_Category,Double.parseDouble(Global.convertToEnglish(String.valueOf(txtSalesPrice.getText()))),spinnerTaxId,spinnerSupplierId);

        mQueue = Volley.newRequestQueue(frmItemsNew.this);
        JsonArrayRequest request2 =new JsonArrayRequest(Request.Method.POST, conRoyal.Url_InsertItems, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response2) {
                Toast.makeText(frmItemsNew.this,"تم الحفظ بنجاح",Toast.LENGTH_SHORT).show();
                RefreashForm();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(frmItemsNew.this,"فشل عملية الحفظ الرجاء المحاولة مرة اخرى",Toast.LENGTH_SHORT).show();

            }
        });

        mQueue.add(request2);
    }

    private void SelectItemPrice(String Barcode)
    {

      //  conRoyal.UrlRoyal_SelectPurchasePrice(conRoyal.ConnectionString,Barcode);

        conRoyal.UrlRoyal_Query(conRoyal.ConnectionString,"select Aname,SalesPrice,CategoryID,(select Aname from Category where id=Items.CategoryID) as CategoryName ,SalesTaxID,(select TaxRatio from tax where id= Items.SalesTaxID) as TaxName,Items.vendorid,(select Aname from supplier where ID=Items.vendorid) as VendorName from items where MainBarcode="+"'"+Barcode+"'");
        mQueue = Volley.newRequestQueue(this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Query, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {

                    JSONObject jsonArray = response.getJSONObject(0);

                    String AName = jsonArray.getString("Aname");
                    spinnerTaxId = jsonArray.getInt("SalesTaxID");
                    spinnerTaxName=jsonArray.getString("TaxName");
                    spinnerSupplierId= jsonArray.getInt("vendorid");
                    spinnerSupplierName=jsonArray.getString("VendorName");
                    ID_Category=jsonArray.getInt("CategoryID");
                    Name_Category=jsonArray.getString("CategoryName");
                    spinnerSupplier.setSelection(GetIndexBySupplier(spinnerSupplierId));
                    spinnerTax.setSelection(GetIndexByTax(spinnerTaxId));
                    spinnerCategory.setSelection(GetIndexByCategory(ID_Category));
                    String SalesPrice= jsonArray.getString("SalesPrice");
                    DecimalFormat DF = new DecimalFormat("######0.000");

                    txtItemName.setText(AName);
                    if(SalesPrice.equals("null")||SalesPrice.equals("Null"))
                    {
                        txtSalesPrice.setText(DF.format(0));
                    }
                    else
                    {
                        txtSalesPrice.setText(DF.format(Double.valueOf(SalesPrice)));
                    }

                } catch (JSONException e) {
                    Toast.makeText(frmItemsNew.this,"المادة غير معرفة",Toast.LENGTH_LONG).show();
                    txtItemName.setText("");
                    txtSalesPrice.setText("");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(frmItemsNew.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(request);

    }
    private void SelectItemPriceByID(int ID)
    {

        //  conRoyal.UrlRoyal_SelectPurchasePrice(conRoyal.ConnectionString,Barcode);

        conRoyal.UrlRoyal_Query(conRoyal.ConnectionString,"select MainBarcode,Aname,SalesPrice,CategoryID,(select Aname from Category where id=Items.CategoryID) as CategoryName ,SalesTaxID,(select TaxRatio from tax where id= Items.SalesTaxID) as TaxName,Items.vendorid,(select Aname from supplier where ID=Items.vendorid) as VendorName from items where ID="+""+ID+"");
        mQueue = Volley.newRequestQueue(this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Query, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {

                    JSONObject jsonArray = response.getJSONObject(0);

                    String AName = jsonArray.getString("Aname");
                    spinnerTaxId = jsonArray.getInt("SalesTaxID");
                    spinnerTaxName=jsonArray.getString("TaxName");
                   String Barcode =jsonArray.getString("MainBarcode");
                    txtBarcode.setText(Barcode);

                    spinnerSupplierId= jsonArray.getInt("vendorid");
                    spinnerSupplierName=jsonArray.getString("VendorName");
                    ID_Category=jsonArray.getInt("CategoryID");
                    Name_Category=jsonArray.getString("CategoryName");
                    spinnerSupplier.setSelection(GetIndexBySupplier(spinnerSupplierId));
                    spinnerTax.setSelection(GetIndexByTax(spinnerTaxId));
                    spinnerCategory.setSelection(GetIndexByCategory(ID_Category));
                    String SalesPrice= jsonArray.getString("SalesPrice");
                    DecimalFormat DF = new DecimalFormat("######0.000");

                    txtItemName.setText(AName);
                    if(SalesPrice.equals("null")||SalesPrice.equals("Null"))
                    {
                        txtSalesPrice.setText(DF.format(0));
                    }
                    else
                    {
                        txtSalesPrice.setText(DF.format(Double.valueOf(SalesPrice)));
                    }

                } catch (JSONException e) {
                    Toast.makeText(frmItemsNew.this,"المادة غير معرفة",Toast.LENGTH_LONG).show();
                    txtItemName.setText("");
                    txtSalesPrice.setText("");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(frmItemsNew.this,error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(request);

    }
}
