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

public class frmSelectItemQuantity extends AppCompatActivity {
    private RequestQueue mQueue;
    private TextWatcher textWatcher=null;
    private TextView txtItemName,txtItemQuantity,txtSalesPrice;
    private EditText txtBarcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_select_item_quantity);

        txtBarcode=findViewById(R.id.frmSelectItemQuantity_textBarcode);
        txtItemName=findViewById(R.id.frmSelectItemQuantity_ItemName);
        txtItemQuantity=findViewById(R.id.frmSelectItemQuantity_ItemQuantity);
        txtSalesPrice=findViewById(R.id.frmSelectItemQuantity_Price);
        txtBarcode.requestFocus();
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

                if (!Barcode.equals("")) {
                    //SelectItemPrice(Barcode);
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
                    String id = jsonArray.getString("id");
                    String SalesPrice= jsonArray.getString("salesPrice");
                    DecimalFormat DF = new DecimalFormat("######0.000");

                    txtItemName.setText(AName);
                    if(txtSalesPrice.equals("null")||txtSalesPrice.equals("Null"))
                    {
                        txtSalesPrice.setText(DF.format(0));
                    }
                    else
                    {
                        txtSalesPrice.setText(DF.format(Double.valueOf(SalesPrice)));
                    }

                    SelectItemQuantity(id);
                  //  txtBarcode.setText("");


                } catch (JSONException e) {
                    Toast.makeText(frmSelectItemQuantity.this,"المادة غير معرفة",Toast.LENGTH_LONG).show();

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


    private void SelectItemQuantity(String itemId)
    {

        conRoyal.UrlRoyal_ItemquantityPDA(conRoyal.ConnectionString,itemId);



        mQueue = Volley.newRequestQueue(this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_ItemquantityPDA, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {

                    JSONObject jsonArray = response.getJSONObject(0);
                    String QTY= jsonArray.getString("Balance");
                    DecimalFormat DF = new DecimalFormat("######0.000");

                        txtItemQuantity.setText(DF.format(Double.valueOf(QTY)));
                    txtBarcode.setText("");
                } catch (JSONException e) {
                    txtBarcode.setText("");
                    txtItemName.setText("");
                    txtSalesPrice.setText("");
                    txtItemQuantity.setText("--");
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
