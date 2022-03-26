package com.example.royalsoftapk;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class frmSelectPurchasePrice extends AppCompatActivity  {
    private RequestQueue mQueue;
    private TextWatcher textWatcher=null;
    private TextView txtItemName,txtPurchasePrice,txtSupplierName,txtSalesPrice;
    private EditText txtBarcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_select_purchase_price);
        txtBarcode=findViewById(R.id.frmPurchasePrice_textBarcode);
        txtItemName=findViewById(R.id.frmPurchasePrice_ItemName);
        txtPurchasePrice=findViewById(R.id.frmPurchasePrice_Price);
        txtSupplierName=findViewById(R.id.frmPurchasePrice_Supplier);
        txtSalesPrice=findViewById(R.id.frmSales_Price);



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
                String Barcode=txtBarcode.getText().toString();
                if(!Barcode.equals("") && Barcode!=null)
                {

                    if(Barcode.charAt(Barcode.length()-1)=='\n') {
                        Barcode=Barcode.substring(0,Barcode.length()-1);
                        SelectItemPrice(Barcode);
                    }

                }

            }
        };
        txtBarcode.addTextChangedListener(textWatcher);




        //----------------------------------Event Text Barcode-------------------------------------------------------------
    }




    private void SelectItemPrice(String Barcode)
    {

        conRoyal.UrlRoyal_SelectPurchasePrice(conRoyal.ConnectionString,Barcode);



        mQueue = Volley.newRequestQueue(this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_PurchasePrice, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {

                    JSONObject jsonArray = response.getJSONObject(0);

                    String AName = jsonArray.getString("AName");
                    String PurchasePrice = jsonArray.getString("LastPurchasePrice");
                    String SupplierName= jsonArray.getString("SupplierAName");
                    String SalesPrice= jsonArray.getString("salesPrice");
                    DecimalFormat DF = new DecimalFormat("######0.000");

                    txtItemName.setText(AName);
                    if(PurchasePrice.equals("null")||PurchasePrice.equals("Null"))
                    {
                        txtPurchasePrice.setText(DF.format(0));
                        txtSalesPrice.setText(DF.format(0));
                    }
                    else
                    {
                        txtPurchasePrice.setText(DF.format(Double.valueOf(PurchasePrice)));
                        txtSalesPrice.setText(DF.format(Double.valueOf(SalesPrice)));
                    }

                    txtSupplierName.setText(SupplierName);

                    txtBarcode.setText("");


                } catch (JSONException e) {
                    Toast.makeText(frmSelectPurchasePrice.this,"المادة غير معرفة",Toast.LENGTH_LONG).show();
                    txtBarcode.setText("");
                    txtPurchasePrice.setText("");
                    txtSalesPrice.setText("");
                    txtSupplierName.setText("");
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
