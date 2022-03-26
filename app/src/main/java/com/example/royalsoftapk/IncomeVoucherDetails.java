package com.example.royalsoftapk;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.royalsoftapk.conRoyal.alistIncome;

public class IncomeVoucherDetails extends AppCompatActivity {
    private RequestQueue mQueue;
    public EditText input,TBarcode,inputNameItem,inputQtyNew,inputPurchasePrice;
    public String QtyItem;
    private TextWatcher textWatcher=null;
    private Button bt_Items,bt_CancelOrder,bt_SaveOrder;
    private String itemId,item_Name,MainBarcode,PurchasePrice;
    String BarcodeNew,ItemNameNew,QtyNewItem,PurchasePriceNew;
    Dialog epicDialog;
    ImageView closebox;
    Button btnCancel;
    JSONArray ArrayItemSelected=new JSONArray();
    DataBaseHelper da=new DataBaseHelper(this);
    String frmType;
    ImageButton btnCamera;
    Spinner spinnerItemName;
    List<Cls_getItems> ListItems=new ArrayList<>();
    @Override
    public void onBackPressed() {

        epicDialog.setContentView(R.layout.box_dialog_new_income);
        closebox=(ImageView)epicDialog.findViewById(R.id.bt_N_close23);
        btnCancel=(Button)epicDialog.findViewById(R.id.btn_exit_Now3);


        closebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewOrder();
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_voucher_details);
        TBarcode=findViewById(R.id.textBarcode);
        bt_Items=findViewById(R.id.bt_EnterItems);
        bt_CancelOrder=findViewById(R.id.bt_cancelOrder);
        bt_SaveOrder=findViewById(R.id.bt_SaveOrder);
        //conRoyal.alistIncome=new ArrayList<>();
        epicDialog = new Dialog(this);

        Bundle extras = getIntent().getExtras();
        frmType=extras.get("frmType").toString();
        btnCamera=findViewById(R.id.btnCamera);

        spinnerItemName=(Spinner) findViewById(R.id.sp_ItemName);


        GetItemsCompo();
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r();
            }
        });
        bt_SaveOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateExcel();
                SavedOrder();
            }
        });

        bt_CancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                //Yes button clicked
                                //CancelOrder();
                                NewOrder();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
                builder.setMessage("هل أنت متاكد من عملية إلغاء السند").setPositiveButton("نعم", dialogClickListener)
                        .setNegativeButton("لا", dialogClickListener).show();


            }
        });


        bt_Items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(IncomeVoucherDetails.this, frmItemsListIncomeVoucher.class);
                startActivity(intent);
            }
        });


        //-------------------------------Cls_Supplier_Spinner-----------------------------------------------------//
        spinnerItemName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Barcode=ListItems.get(position).getMainBarcode();
                if(Barcode!="0") {
                    if (Integer.parseInt(frmType) == 4) {
                        SelectDataItemBondInventory(Barcode);
                    } else {
                        SelectDataItem(Barcode);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//-------------------------------Cls_Supplier_Spinner-----------------------------------------------------//

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
                String Barcode=String.valueOf(TBarcode.getText());

                if(!Barcode.equals("") && Barcode!=null)
                {

                   if(Barcode.charAt(Barcode.length()-1)=='\n') {
                       Barcode=Barcode.substring(0,Barcode.length()-1);
                       if (Integer.parseInt(frmType) == 4) {
                           SelectDataItemBondInventory(Barcode);
                       } else {
                           SelectDataItem(Barcode);
                       }
                   }

                }
            }
        };
       TBarcode.addTextChangedListener(textWatcher);
/*TBarcode.setOnKeyListener(new View.OnKeyListener() {
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event)
    {
        if(event.getAction()== KeyEvent.ACTION_DOWN&& keyCode==KeyEvent.KEYCODE_ENTER)
        {
            String Barcode=String.valueOf(TBarcode.getText());
            if(Barcode!="")
            {
                SelectDataItem(Barcode);
            }
            return  true;
        }
        return false;
    }
});*/

        //----------------------------------Event Text Barcode-------------------------------------------------------------
    }


    private void  GetItemsCompo()
    {
        conRoyal.UrlRoyal_Query(conRoyal.ConnectionString,"select ID,Aname,MainBarcode from Items");

        mQueue = Volley.newRequestQueue(IncomeVoucherDetails.this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Query, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {
                    ArrayList<String> aa=new ArrayList<String>();
                    aa.add("--إختر --");
                    ListItems.add(new Cls_getItems(0,"--إختر --","0"));
                    for(int i =0 ; i < response.length() ; i++)
                    {
                        JSONObject jsonArray = response.getJSONObject(i);
                        int ID = jsonArray.getInt("ID");
                        String AName = jsonArray.getString("Aname");
                        String MainBarcode = jsonArray.getString("MainBarcode");

                        ListItems.add(new Cls_getItems(ID,AName,MainBarcode));
                        aa.add(AName);
                    }

                    ArrayAdapter<Cls_getItems> adapter2=new ArrayAdapter<Cls_getItems>(IncomeVoucherDetails.this,android.R.layout.simple_spinner_item,ListItems);
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
    public static int indexOf(final ArrayList<Cls_ItemsInvoiceSales> menuItems, final String what) {
        final int size = menuItems.size();
        for (int i = 0; i < size; i++) {
            final String label = menuItems.get(i).getMainBarcode();
            if (label != null && label.equals(what)) {
                return i;
            }
        }
        return -1;
    }
    void r()
    {

        IntentIntegrator intentIntegrator=new IntentIntegrator(IncomeVoucherDetails.this);
        intentIntegrator.setPrompt("For flash use volume up key");
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setCaptureActivity(Capture.class);
        intentIntegrator.initiateScan();
    }

    public static void verifyStoragePermissions(Activity activity) {
          final int REQUEST_EXTERNAL_STORAGE = 1;
          String[] PERMISSIONS_STORAGE = {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    void  CreateExcel()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date=new Date();
        String s=sdf.format(date.getTime())+".xls";

        verifyStoragePermissions(IncomeVoucherDetails.this);
        HSSFWorkbook hssfWorkbook=new HSSFWorkbook();
        HSSFSheet hssfSheet=hssfWorkbook.createSheet();

        HSSFRow RowHeader=hssfSheet.createRow(0);
        HSSFCell cellIDItem=RowHeader.createCell(0);
        cellIDItem.setCellValue("ItemID");

        HSSFCell cellMainBarcode=RowHeader.createCell(1);
        cellMainBarcode.setCellValue("MainBarcode");

        HSSFCell cellItemName=RowHeader.createCell(2);
        cellItemName.setCellValue("ItemName");


        HSSFCell cellQTY=RowHeader.createCell(3);
        cellQTY.setCellValue("QTY");


        HSSFCell cellPurchasePrice=RowHeader.createCell(4);
        cellPurchasePrice.setCellValue("PurchasePrice");

        for(int i=0;i<alistIncome.size();i++)
        {
            HSSFRow RowRecord=hssfSheet.createRow(i+1);

            HSSFCell cellIDItemR=RowRecord.createCell(0);
            cellIDItemR.setCellValue(alistIncome.get(i).getID().toString());

            HSSFCell cellMainBarcodeR=RowRecord.createCell(1);
            cellMainBarcodeR.setCellValue(alistIncome.get(i).getMainBarcode().toString());

            HSSFCell cellItemNameR=RowRecord.createCell(2);
            cellItemNameR.setCellValue(alistIncome.get(i).getName().toString());


            HSSFCell cellQTYR=RowRecord.createCell(3);
            cellQTYR.setCellValue(alistIncome.get(i).getQty().toString());


            HSSFCell cellPurchasePriceR=RowRecord.createCell(4);
            cellPurchasePriceR.setCellValue(alistIncome.get(i).getPurchasePrice().toString());

        }

        File filePath=new File(Environment.getExternalStorageDirectory() +"/"+s.toString());
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},PackageManager.PERMISSION_GRANTED);
        try {
            if(!filePath.exists())
            {
                filePath.createNewFile();
            }

            FileOutputStream fileOutputStream =new FileOutputStream(filePath);
            hssfWorkbook.write(fileOutputStream);

            if(fileOutputStream!=null)
            {
                fileOutputStream.flush();
                fileOutputStream.close();
                Toast.makeText(IncomeVoucherDetails.this,"تم الحفظ في excel",Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(IncomeVoucherDetails.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult.getContents()!=null)
        {
            TBarcode.setText(intentResult.getContents()+"\n");
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
    private void SelectDataItemBondInventory(final String Barcode)
    {
        if(DataBaseHelper.ParmsSettingsDevice.Datamigration)
        {
            conRoyal.UrlRoyal_SelectDataItemBarcode(conRoyal.ConnectionString,Barcode);
            mQueue = Volley.newRequestQueue(this);
            JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_SelectDataItemBarcode, null, new Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        String json=response.toString();
                        if(json.equals("[]"))
                        {

                            if(alistIncome.size() > 0)
                            {
                                boolean IsBreak=false;
                                for (int i=0;i<alistIncome.size();i++)
                                {
                                    final Cls_ItemsHistoryIncomeVoucher a = alistIncome.get(i);
                                    String bb = a.getMainBarcode();
                                    if (bb.equals(Barcode)) {
                                        ItemNameNew=a.getName();
                                        NewQtyForm();
                                        return;
                                    }
                                }
                                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case DialogInterface.BUTTON_POSITIVE:
                                                //-----------------------------------------------------Yes button clicked
                                                BarcodeNew = TBarcode.getText().toString();
                                                TBarcode.setText("");


                                                //----------------------------- Dailog ItemName_New--------------------------------------------------------


                                                AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
                                                builder.setTitle("إدخال اسم المادة الجديدة");
                                                builder.setIcon(R.drawable.sms);
                                                builder.setMessage(" يرجى إدخال اسم الصنف الجديد     ");

                                                inputNameItem = new EditText(IncomeVoucherDetails.this);
                                                inputNameItem.setInputType(InputType.TYPE_CLASS_TEXT);
                                                builder.setView(inputNameItem);
                                                builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        ItemNameNew = inputNameItem.getText().toString();
                                                        if (ItemNameNew.equals("")) {
                                                            Toast.makeText(IncomeVoucherDetails.this, "يرجى إدخال اسم المادة", Toast.LENGTH_LONG).show();
                                                            return;
                                                        } else {

                                                            //---------------------------------------------------------------------------------------

                                                            NewQtyForm();

                                                        }


                                                    }
                                                });

                                                builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        TBarcode.setText("");
                                                    }
                                                });

                                                final AlertDialog add = builder.create();
                                                add.show();
                                                //--------------------------------------Button Dailog----------------------------------------------

                                                break;

                                            case DialogInterface.BUTTON_NEGATIVE:
                                                //No button clicked
                                                TBarcode.setText("");
                                                break;
                                        }
                                    }
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
                                builder.setMessage("هذه المادة غير معرفة ، هل تريد تعريفها ؟").setPositiveButton("نعم", dialogClickListener)
                                        .setNegativeButton("لا", dialogClickListener).show();

                            }
                            else {


                                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case DialogInterface.BUTTON_POSITIVE:
                                                //-----------------------------------------------------Yes button clicked
                                                BarcodeNew = TBarcode.getText().toString();
                                                TBarcode.setText("");


                                                //----------------------------- Dailog ItemName_New--------------------------------------------------------


                                                AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
                                                builder.setTitle("إدخال اسم المادة الجديدة");
                                                builder.setIcon(R.drawable.sms);
                                                builder.setMessage(" يرجى إدخال اسم الصنف الجديد     ");

                                                inputNameItem = new EditText(IncomeVoucherDetails.this);
                                                inputNameItem.setInputType(InputType.TYPE_CLASS_TEXT);
                                                builder.setView(inputNameItem);
                                                builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        ItemNameNew = inputNameItem.getText().toString();
                                                        if (ItemNameNew.equals("")) {
                                                            Toast.makeText(IncomeVoucherDetails.this, "يرجى إدخال اسم المادة", Toast.LENGTH_LONG).show();
                                                            return;
                                                        } else {

                                                            //---------------------------------------------------------------------------------------

                                                            NewQtyForm();

                                                        }


                                                    }
                                                });

                                                builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        TBarcode.setText("");
                                                    }
                                                });

                                                final AlertDialog add = builder.create();
                                                add.show();
                                                //--------------------------------------Button Dailog----------------------------------------------

                                                break;

                                            case DialogInterface.BUTTON_NEGATIVE:
                                                //No button clicked
                                                TBarcode.setText("");
                                                break;
                                        }
                                    }
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
                                builder.setMessage("هذه المادة غير معرفة ، هل تريد تعريفها ؟").setPositiveButton("نعم", dialogClickListener)
                                        .setNegativeButton("لا", dialogClickListener).show();
                            }
                        }
                        else {

                            JSONObject jsonArray = response.getJSONObject(0);

                            itemId = jsonArray.getString("ID");
                            item_Name = jsonArray.getString("AName");
                            MainBarcode = jsonArray.getString("MainBarcode");
                            PurchasePrice=jsonArray.getString("PurchasePrice");
                            if (itemId != null || itemId != "0") {
                                //-----------------------------Button Dailog--------------------------------------------------------
                                AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
                                builder.setTitle("إدخال كمية المادة");
                                builder.setIcon(R.drawable.sms);
                                builder.setMessage(" يرجى إدخال كمية الصنف    -->    " + item_Name);

                                input = new EditText(IncomeVoucherDetails.this);
                                input.setInputType(InputType.TYPE_CLASS_TEXT);
                                builder.setView(input);


                                builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        QtyItem = input.getText().toString();
                                        if (QtyItem.equals("")) {
                                            Toast.makeText(IncomeVoucherDetails.this, "يرجى إدخال الكمية", Toast.LENGTH_LONG).show();
                                            TBarcode.setText("");
                                            return;
                                        }
                                        //---------------------------------------------------------------------------------------

                                            if(Integer.parseInt(frmType)==4)
                                            {
                                                Cls_ItemsHistoryIncomeVoucher a = new Cls_ItemsHistoryIncomeVoucher();
                                                a.setID(itemId);
                                                a.setName(item_Name);
                                                a.setMainBarcode(MainBarcode);
                                                a.setQty(QtyItem);
                                                a.setrowindex(alistIncome.size()+1);
                                                a.setPurchasePrice("0");
                                                alistIncome.add(a);
                                                Toast.makeText(IncomeVoucherDetails.this, "تم إضافة المادة بنجاح", Toast.LENGTH_LONG).show();
                                                TBarcode.setText("");
                                            }
                                            else
                                            {
                                                NewPurchasePrice2(itemId,QtyItem,item_Name,MainBarcode);
                                            }


                                        ///-------------------------------------------------------------------------------------


                                        input.setText("");
                                        Toast.makeText(IncomeVoucherDetails.this, QtyItem, Toast.LENGTH_LONG).show();
                                        TBarcode.setText("");
                                    }
                                });

                                builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        TBarcode.setText("");
                                    }
                                });

                                final AlertDialog add = builder.create();
                                add.show();
                                //--------------------------------------Button Dailog----------------------------------------------

                            }
                        }
                    } catch (JSONException e) {
                        Toast.makeText(IncomeVoucherDetails.this,"المادة غير معرفة",Toast.LENGTH_LONG).show();
                        TBarcode.setText("");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    offlineBarcodeKeyDownInventory(Barcode);
                }
            });
            mQueue.add(request);
        }
        else
        {
            offlineBarcodeKeyDownInventory(Barcode);
        }



    }
    private void offlineBarcodeKeyDown(String Barcode)
    {
        if(Global.listItem.size()==0)
        {
            da.SetAdapterItems(IncomeVoucherDetails.this,null,0);
        }
        int rowcount= indexOf(Global.listItem,Barcode);
        if(rowcount==-1)
        {

            if(alistIncome.size() > 0)
            {
                boolean IsBreak=false;
                for (int i=0;i<alistIncome.size();i++)
                {
                    final Cls_ItemsHistoryIncomeVoucher a = alistIncome.get(i);
                    String bb = a.getMainBarcode();
                    if (bb.equals(Barcode)) {
                        ItemNameNew=a.getName();
                        NewQtyForm();
                        return;
                    }
                }
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //-----------------------------------------------------Yes button clicked
                                BarcodeNew = TBarcode.getText().toString();
                                TBarcode.setText("");


                                //----------------------------- Dailog ItemName_New--------------------------------------------------------


                                AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
                                builder.setTitle("إدخال اسم المادة الجديدة");
                                builder.setIcon(R.drawable.sms);
                                builder.setMessage(" يرجى إدخال اسم الصنف الجديد     ");

                                inputNameItem = new EditText(IncomeVoucherDetails.this);
                                inputNameItem.setInputType(InputType.TYPE_CLASS_TEXT);
                                builder.setView(inputNameItem);
                                builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ItemNameNew = inputNameItem.getText().toString();
                                        if (ItemNameNew.equals("")) {
                                            Toast.makeText(IncomeVoucherDetails.this, "يرجى إدخال اسم المادة", Toast.LENGTH_LONG).show();
                                            return;
                                        } else {

                                            //---------------------------------------------------------------------------------------

                                            NewQtyForm();

                                        }


                                    }
                                });

                                builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        TBarcode.setText("");
                                    }
                                });

                                final AlertDialog add = builder.create();
                                add.show();
                                //--------------------------------------Button Dailog----------------------------------------------

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                TBarcode.setText("");
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
                builder.setMessage("هذه المادة غير معرفة ، هل تريد تعريفها ؟").setPositiveButton("نعم", dialogClickListener)
                        .setNegativeButton("لا", dialogClickListener).show();

            }
            else {


                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                //-----------------------------------------------------Yes button clicked
                                BarcodeNew = TBarcode.getText().toString();
                                TBarcode.setText("");


                                //----------------------------- Dailog ItemName_New--------------------------------------------------------


                                AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
                                builder.setTitle("إدخال اسم المادة الجديدة");
                                builder.setIcon(R.drawable.sms);
                                builder.setMessage(" يرجى إدخال اسم الصنف الجديد     ");

                                inputNameItem = new EditText(IncomeVoucherDetails.this);
                                inputNameItem.setInputType(InputType.TYPE_CLASS_TEXT);
                                builder.setView(inputNameItem);
                                builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        ItemNameNew = inputNameItem.getText().toString();
                                        if (ItemNameNew.equals("")) {
                                            Toast.makeText(IncomeVoucherDetails.this, "يرجى إدخال اسم المادة", Toast.LENGTH_LONG).show();
                                            return;
                                        } else {

                                            //---------------------------------------------------------------------------------------

                                            NewQtyForm();

                                        }


                                    }
                                });

                                builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        TBarcode.setText("");
                                    }
                                });

                                final AlertDialog add = builder.create();
                                add.show();
                                //--------------------------------------Button Dailog----------------------------------------------

                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                TBarcode.setText("");
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
                builder.setMessage("هذه المادة غير معرفة ، هل تريد تعريفها ؟").setPositiveButton("نعم", dialogClickListener)
                        .setNegativeButton("لا", dialogClickListener).show();
            }
        }
        else
        {

            itemId = Global.listItem.get(rowcount).getID();
            item_Name = Global.listItem.get(rowcount).getItemName();
            MainBarcode = Global.listItem.get(rowcount).getMainBarcode();
            PurchasePrice=Global.listItem.get(rowcount).getPrice();
            if (itemId != null || itemId != "0") {
                //-----------------------------Button Dailog--------------------------------------------------------
                AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
                builder.setTitle("إدخال كمية المادة");
                builder.setIcon(R.drawable.sms);
                builder.setMessage(" يرجى إدخال كمية الصنف    -->    " + item_Name);

                input = new EditText(IncomeVoucherDetails.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);


                builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        QtyItem = input.getText().toString();
                        if (QtyItem.equals("")) {
                            Toast.makeText(IncomeVoucherDetails.this, "يرجى إدخال الكمية", Toast.LENGTH_LONG).show();
                            TBarcode.setText("");
                            return;
                        }
                        //---------------------------------------------------------------------------------------


                        NewPurchasePrice2(itemId,QtyItem,item_Name,MainBarcode);

                        ///-------------------------------------------------------------------------------------


                        input.setText("");
                        Toast.makeText(IncomeVoucherDetails.this, QtyItem, Toast.LENGTH_LONG).show();
                        TBarcode.setText("");
                    }
                });

                builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        TBarcode.setText("");
                    }
                });

                final AlertDialog add = builder.create();
                add.show();
                //--------------------------------------Button Dailog----------------------------------------------

            }
        }

    }








    private void offlineBarcodeKeyDownInventory(String Barcode)
    {
        if(Global.IsCustomerOther==false) {
            if (Global.listItem.size() == 0) {
                da.SetAdapterItems(IncomeVoucherDetails.this, null, 0);
            }
        }
        int rowcount= indexOf(Global.listItem,Barcode);
        if(rowcount==-1)
        {

            if(alistIncome.size() > 0)
            {
                boolean IsBreak=false;
                for (int i=0;i<alistIncome.size();i++)
                {
                    final Cls_ItemsHistoryIncomeVoucher a = alistIncome.get(i);
                    String bb = a.getMainBarcode();
                    if (bb.equals(Barcode)) {
                        ItemNameNew=a.getName();
                        NewQtyForm();
                        return;
                    }
                }
                if(Global.IsCustomerOther)
                {
                    BarcodeNew = TBarcode.getText().toString();
                    ItemNameNew=BarcodeNew;
                    TBarcode.setText("");

                    NewQtyForm();
                }
                else
                {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    //-----------------------------------------------------Yes button clicked
                                    BarcodeNew = TBarcode.getText().toString();
                                    TBarcode.setText("");


                                    //----------------------------- Dailog ItemName_New--------------------------------------------------------


                                    AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
                                    builder.setTitle("إدخال اسم المادة الجديدة");
                                    builder.setIcon(R.drawable.sms);
                                    builder.setMessage(" يرجى إدخال اسم الصنف الجديد     ");

                                    inputNameItem = new EditText(IncomeVoucherDetails.this);
                                    inputNameItem.setInputType(InputType.TYPE_CLASS_TEXT);
                                    builder.setView(inputNameItem);
                                    builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ItemNameNew = inputNameItem.getText().toString();
                                            if (ItemNameNew.equals("")) {
                                                Toast.makeText(IncomeVoucherDetails.this, "يرجى إدخال اسم المادة", Toast.LENGTH_LONG).show();
                                                return;
                                            } else {

                                                //---------------------------------------------------------------------------------------

                                                NewQtyForm();

                                            }


                                        }
                                    });

                                    builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            TBarcode.setText("");
                                        }
                                    });

                                    final AlertDialog add = builder.create();
                                    add.show();
                                    //--------------------------------------Button Dailog----------------------------------------------

                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    TBarcode.setText("");
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
                    builder.setMessage("هذه المادة غير معرفة ، هل تريد تعريفها ؟").setPositiveButton("نعم", dialogClickListener)
                            .setNegativeButton("لا", dialogClickListener).show();

                }

            }
            else {

                if(Global.IsCustomerOther)
                {
                    BarcodeNew = TBarcode.getText().toString();
                    ItemNameNew=BarcodeNew;
                    TBarcode.setText("");

                    NewQtyForm();
                }
                else {
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    //-----------------------------------------------------Yes button clicked
                                    BarcodeNew = TBarcode.getText().toString();
                                    TBarcode.setText("");


                                    //----------------------------- Dailog ItemName_New--------------------------------------------------------


                                    AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
                                    builder.setTitle("إدخال اسم المادة الجديدة");
                                    builder.setIcon(R.drawable.sms);
                                    builder.setMessage(" يرجى إدخال اسم الصنف الجديد     ");

                                    inputNameItem = new EditText(IncomeVoucherDetails.this);
                                    inputNameItem.setInputType(InputType.TYPE_CLASS_TEXT);
                                    builder.setView(inputNameItem);
                                    builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ItemNameNew = inputNameItem.getText().toString();
                                            if (ItemNameNew.equals("")) {
                                                Toast.makeText(IncomeVoucherDetails.this, "يرجى إدخال اسم المادة", Toast.LENGTH_LONG).show();
                                                return;
                                            } else {

                                                //---------------------------------------------------------------------------------------

                                                NewQtyForm();

                                            }


                                        }
                                    });

                                    builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            TBarcode.setText("");
                                        }
                                    });

                                    final AlertDialog add = builder.create();
                                    add.show();
                                    //--------------------------------------Button Dailog----------------------------------------------

                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    TBarcode.setText("");
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
                    builder.setMessage("هذه المادة غير معرفة ، هل تريد تعريفها ؟").setPositiveButton("نعم", dialogClickListener)
                            .setNegativeButton("لا", dialogClickListener).show();
                }
            }
        }
        else
        {

            itemId = Global.listItem.get(rowcount).getID();
            item_Name = Global.listItem.get(rowcount).getItemName();
            MainBarcode = Global.listItem.get(rowcount).getMainBarcode();
            PurchasePrice=Global.listItem.get(rowcount).getPrice();
            if (itemId != null || itemId != "0") {
                //-----------------------------Button Dailog--------------------------------------------------------
                AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
                builder.setTitle("إدخال كمية المادة");
                builder.setIcon(R.drawable.sms);
                builder.setMessage(" يرجى إدخال كمية الصنف    -->    " + item_Name);

                input = new EditText(IncomeVoucherDetails.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                builder.setView(input);


                builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        QtyItem = input.getText().toString();
                        if (QtyItem.equals("")) {
                            Toast.makeText(IncomeVoucherDetails.this, "يرجى إدخال الكمية", Toast.LENGTH_LONG).show();
                            TBarcode.setText("");
                            return;
                        }
                        //---------------------------------------------------------------------------------------

                        if(Integer.parseInt(frmType)==4)
                        {
                            Cls_ItemsHistoryIncomeVoucher a = new Cls_ItemsHistoryIncomeVoucher();
                            a.setID(itemId);
                            a.setName(item_Name);
                            a.setMainBarcode(MainBarcode);
                            a.setQty(QtyItem);
                            a.setrowindex(alistIncome.size()+1);
                            a.setPurchasePrice("0");
                            alistIncome.add(a);
                            Toast.makeText(IncomeVoucherDetails.this, "تم إضافة المادة بنجاح", Toast.LENGTH_LONG).show();
                            TBarcode.setText("");
                        }
                        else
                        {
                            NewPurchasePrice2(itemId,QtyItem,item_Name,MainBarcode);
                        }
                        ///-------------------------------------------------------------------------------------


                        input.setText("");
                        Toast.makeText(IncomeVoucherDetails.this, QtyItem, Toast.LENGTH_LONG).show();
                        TBarcode.setText("");
                    }
                });

                builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        TBarcode.setText("");
                    }
                });

                final AlertDialog add = builder.create();
                add.show();
                //--------------------------------------Button Dailog----------------------------------------------

            }
        }

    }














    private  void NewQtyForm()
    {
        //-----------------------------Button Dailog--------------------------------------------------------
        AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
        builder.setTitle("إدخال كمية المادة الجديدة");
        builder.setIcon(R.drawable.sms);
        builder.setMessage(" يرجى إدخال كمية الصنف الجديد   -->    " + ItemNameNew);

        inputQtyNew = new EditText(IncomeVoucherDetails.this);
        inputQtyNew.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

        builder.setView(inputQtyNew);

        builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                QtyNewItem = inputQtyNew.getText().toString();
                if (QtyNewItem.equals("")) {
                    Toast.makeText(IncomeVoucherDetails.this, "يرجى إدخال كمية المادة", Toast.LENGTH_LONG).show();
                    TBarcode.setText("");
                    return;
                }
                else
                {

                    if(!ItemNameNew.equals("") && !QtyNewItem.equals("") && !BarcodeNew.equals("")) {
                        if (Integer.parseInt(frmType) == 4) {

                            itemId = "0";
                            item_Name = ItemNameNew;
                            MainBarcode =BarcodeNew;
                            PurchasePrice="0";

                            Cls_ItemsHistoryIncomeVoucher a = new Cls_ItemsHistoryIncomeVoucher();
                            a.setID(itemId);
                            a.setName(item_Name);
                            a.setMainBarcode(MainBarcode);
                            a.setQty(QtyNewItem);
                            a.setrowindex(alistIncome.size()+1);
                            a.setPurchasePrice("0");
                            alistIncome.add(a);
                            Toast.makeText(IncomeVoucherDetails.this, "تم إضافة المادة بنجاح", Toast.LENGTH_LONG).show();
                            TBarcode.setText("");


                        }
                        else
                        {
                            NewPurchasePrice(QtyNewItem, ItemNameNew, BarcodeNew);
                        }

                    }
                    else
                    {
                        Toast.makeText(IncomeVoucherDetails.this, "حدث خلل بسيط ، يرجى إعادة المحاولة", Toast.LENGTH_LONG).show();
                        TBarcode.setText("");
                        return;
                    }

                    ///-------------------------------------------------------------------------------------
                }
            }
        });

        builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                TBarcode.setText("");
            }
        });

        final AlertDialog add = builder.create();
        add.show();

        //--------------------------------------Button Dailog----------------------------------------------

    }
    private void NewOrder()
    {
        TBarcode.setText("");
        //conRoyal.IncomeVoucherHeaderID="0";
        alistIncome=new ArrayList<Cls_ItemsHistoryIncomeVoucher>();
        Intent intent = new Intent(IncomeVoucherDetails.this, DASHWORD.class);
        startActivity(intent);

    }

    private  void  SelectDataItem(final String Barcode)
    {
        try {

        if(DataBaseHelper.ParmsSettingsDevice.Datamigration)
        {
            conRoyal.UrlRoyal_SelectDataItemBarcode(conRoyal.ConnectionString,Barcode);
            mQueue = Volley.newRequestQueue(this);
            JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_SelectDataItemBarcode, null, new Response.Listener<JSONArray>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onResponse(JSONArray response) {
                    try {
                        String json=response.toString();
                        if(json.equals("[]"))
                        {

                            if(alistIncome.size() > 0)
                            {
                                boolean IsBreak=false;
                                for (int i=0;i<alistIncome.size();i++)
                                {
                                    final Cls_ItemsHistoryIncomeVoucher a = alistIncome.get(i);
                                    String bb = a.getMainBarcode();
                                    if (bb.equals(Barcode)) {
                                        ItemNameNew=a.getName();
                                        NewQtyForm();
                                        return;
                                    }
                                }
                                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case DialogInterface.BUTTON_POSITIVE:
                                                //-----------------------------------------------------Yes button clicked
                                                BarcodeNew = TBarcode.getText().toString();
                                                TBarcode.setText("");


                                                //----------------------------- Dailog ItemName_New--------------------------------------------------------


                                                AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
                                                builder.setTitle("إدخال اسم المادة الجديدة");
                                                builder.setIcon(R.drawable.sms);
                                                builder.setMessage(" يرجى إدخال اسم الصنف الجديد     ");

                                                inputNameItem = new EditText(IncomeVoucherDetails.this);
                                                inputNameItem.setInputType(InputType.TYPE_CLASS_TEXT);
                                                builder.setView(inputNameItem);
                                                builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        ItemNameNew = inputNameItem.getText().toString();
                                                        if (ItemNameNew.equals("")) {
                                                            Toast.makeText(IncomeVoucherDetails.this, "يرجى إدخال اسم المادة", Toast.LENGTH_LONG).show();
                                                            return;
                                                        } else {

                                                            //---------------------------------------------------------------------------------------

                                                            NewQtyForm();

                                                        }


                                                    }
                                                });

                                                builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        TBarcode.setText("");
                                                    }
                                                });

                                                final AlertDialog add = builder.create();
                                                add.show();
                                                //--------------------------------------Button Dailog----------------------------------------------

                                                break;

                                            case DialogInterface.BUTTON_NEGATIVE:
                                                //No button clicked
                                                TBarcode.setText("");
                                                break;
                                        }
                                    }
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
                                builder.setMessage("هذه المادة غير معرفة ، هل تريد تعريفها ؟").setPositiveButton("نعم", dialogClickListener)
                                        .setNegativeButton("لا", dialogClickListener).show();

                            }
                            else {


                                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        switch (which) {
                                            case DialogInterface.BUTTON_POSITIVE:
                                                //-----------------------------------------------------Yes button clicked
                                                BarcodeNew = TBarcode.getText().toString();
                                                TBarcode.setText("");


                                                //----------------------------- Dailog ItemName_New--------------------------------------------------------


                                                AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
                                                builder.setTitle("إدخال اسم المادة الجديدة");
                                                builder.setIcon(R.drawable.sms);
                                                builder.setMessage(" يرجى إدخال اسم الصنف الجديد     ");

                                                inputNameItem = new EditText(IncomeVoucherDetails.this);
                                                inputNameItem.setInputType(InputType.TYPE_CLASS_TEXT);
                                                builder.setView(inputNameItem);
                                                builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        ItemNameNew = inputNameItem.getText().toString();
                                                        if (ItemNameNew.equals("")) {
                                                            Toast.makeText(IncomeVoucherDetails.this, "يرجى إدخال اسم المادة", Toast.LENGTH_LONG).show();
                                                            return;
                                                        } else {

                                                            //---------------------------------------------------------------------------------------

                                                            NewQtyForm();

                                                        }


                                                    }
                                                });

                                                builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        TBarcode.setText("");
                                                    }
                                                });

                                                final AlertDialog add = builder.create();
                                                add.show();
                                                //--------------------------------------Button Dailog----------------------------------------------

                                                break;

                                            case DialogInterface.BUTTON_NEGATIVE:
                                                //No button clicked
                                                TBarcode.setText("");
                                                break;
                                        }
                                    }
                                };

                                AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
                                builder.setMessage("هذه المادة غير معرفة ، هل تريد تعريفها ؟").setPositiveButton("نعم", dialogClickListener)
                                        .setNegativeButton("لا", dialogClickListener).show();
                            }
                        }
                        else {

                            JSONObject jsonArray = response.getJSONObject(0);

                            itemId = jsonArray.getString("ID");
                            item_Name = jsonArray.getString("AName");
                            MainBarcode = jsonArray.getString("MainBarcode");
                            PurchasePrice=jsonArray.getString("PurchasePrice");
                            if (itemId != null || itemId != "0") {
                                //-----------------------------Button Dailog--------------------------------------------------------
                                AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
                                builder.setTitle("إدخال كمية المادة");
                                builder.setIcon(R.drawable.sms);
                                builder.setMessage(" يرجى إدخال كمية الصنف    -->    " + item_Name);

                                input = new EditText(IncomeVoucherDetails.this);
                                input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);

                                builder.setView(input);
                                builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        QtyItem = input.getText().toString();
                                        if (QtyItem.equals("")) {
                                            Toast.makeText(IncomeVoucherDetails.this, "يرجى إدخال الكمية", Toast.LENGTH_LONG).show();
                                            TBarcode.setText("");
                                            return;
                                        }
                                        //---------------------------------------------------------------------------------------


                                        NewPurchasePrice2(itemId,QtyItem,item_Name,MainBarcode);

                                        ///-------------------------------------------------------------------------------------


                                        input.setText("");
                                        Toast.makeText(IncomeVoucherDetails.this, QtyItem, Toast.LENGTH_LONG).show();
                                        TBarcode.setText("");
                                    }
                                });

                                builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        TBarcode.setText("");
                                    }
                                });

                                final AlertDialog add = builder.create();
                                add.show();
                                //--------------------------------------Button Dailog----------------------------------------------

                            }
                        }
                    } catch (JSONException e) {
                        Toast.makeText(IncomeVoucherDetails.this,"المادة غير معرفة",Toast.LENGTH_LONG).show();
                        TBarcode.setText("");
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    offlineBarcodeKeyDown(Barcode);
                }
            });
            mQueue.add(request);
        }
        else
        {
            offlineBarcodeKeyDown(Barcode);
        }

        }
        catch (Exception ex)
        {
            Toast.makeText(IncomeVoucherDetails.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }

    private boolean SaveOrder( String Details)
    {
      /*  final boolean[] Status2 = {true};
        conRoyal.UrlRoyal_SavePDAIncomeVoucherDetails(conRoyal.ConnectionString,conRoyal.IncomeVoucherHeaderID,Details);

        mQueue = Volley.newRequestQueue(IncomeVoucherDetails.this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,conRoyal.Url_SavePDAIncomeVoucherDetails, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            boolean Status=false;
                            for (int i = 0; i < response.length(); i++)
                            {
                                JSONObject obj = response.getJSONObject(i);
                                Status = obj.getBoolean("Status");

                            }

                            if(Status)
                            {
                                Toast.makeText(IncomeVoucherDetails.this,"تم حفظ المادة بنجاح",Toast.LENGTH_SHORT).show();
                                Status2[0] =true;
                                //NewOrder();
                            }
                            else
                            {
                                Toast.makeText(IncomeVoucherDetails.this,"فشل عملية الحفظ يرجى المحاولة مره اخرى Status false from DataBase",Toast.LENGTH_SHORT).show();
                                Status2[0] =false;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(IncomeVoucherDetails.this,e.toString(),Toast.LENGTH_SHORT).show();
                            Status2[0] =false;
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(IncomeVoucherDetails.this,error.toString(),Toast.LENGTH_SHORT).show();
                Status2[0] =false;
            }

        });

        mQueue.add(request);


        return Status2[0];*/
      return false;
    }

    /*private void CancelOrder()
    {


        conRoyal.UrlRoyal_DeletePDAIncomeVoucherCancel(conRoyal.ConnectionString,conRoyal.IncomeVoucherHeaderID);

        mQueue = Volley.newRequestQueue(IncomeVoucherDetails.this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,conRoyal.Url_DeletePDAIncomeVoucherCancel, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            boolean Status=false;
                            for (int i = 0; i < response.length(); i++)
                            {
                                JSONObject obj = response.getJSONObject(i);
                                Status = obj.getBoolean("Status");

                            }

                            if(Status)
                            {
                                Toast.makeText(IncomeVoucherDetails.this,"تم إلغاء السند بنجاح",Toast.LENGTH_SHORT).show();
                                NewOrder();
                            }
                            else
                            {
                                Toast.makeText(IncomeVoucherDetails.this,"فشل عملية الغاء السند ، يرجى المحاولة مره اخرى",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(IncomeVoucherDetails.this,e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(IncomeVoucherDetails.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        mQueue.add(request);

    }*/


    private  void NewPurchasePrice(final String QtyNewItem2, final String ItemNameNew2, final String BarcodeNew2)
    {
        //-----------------------------Button Dailog--------------------------------------------------------
        AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
        builder.setTitle("إدخال سعر الشراء للمادة الجديدة");
        builder.setIcon(R.drawable.sms);
        builder.setMessage(" يرجى إدخال سعر شراء الصنف الجديد   -->    " + ItemNameNew2);

        inputPurchasePrice = new EditText(IncomeVoucherDetails.this);
        inputPurchasePrice.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        builder.setView(inputPurchasePrice);
        builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PurchasePriceNew = inputPurchasePrice.getText().toString();
                if (PurchasePriceNew.equals("")) {
                    Toast.makeText(IncomeVoucherDetails.this, "يرجى إدخال سعر الشراء للمادة", Toast.LENGTH_LONG).show();
                    TBarcode.setText("");
                    return;
                }
                else
                {

                    if(!ItemNameNew2.equals("") && !PurchasePriceNew.equals("") && !BarcodeNew2.equals(""))
                    {

                            Cls_ItemsHistoryIncomeVoucher a = new Cls_ItemsHistoryIncomeVoucher();
                            a.setID("0");
                            a.setName(ItemNameNew2);
                            a.setMainBarcode(BarcodeNew2);
                            a.setQty(QtyNewItem2);
                            a.setrowindex(alistIncome.size()+1);
                            a.setPurchasePrice(PurchasePriceNew);
                            alistIncome.add(a);
                            Toast.makeText(IncomeVoucherDetails.this, "تم إضافة المادة بنجاح", Toast.LENGTH_LONG).show();
                            TBarcode.setText("");
                    }
                    else
                    {
                        Toast.makeText(IncomeVoucherDetails.this, "حدث خلل بسيط ، يرجى إعادة المحاولة", Toast.LENGTH_LONG).show();
                        TBarcode.setText("");
                        return;
                    }

                    ///-------------------------------------------------------------------------------------
                }
            }
        });

        builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                TBarcode.setText("");
            }
        });

        final AlertDialog add = builder.create();
        add.show();

        //--------------------------------------Button Dailog----------------------------------------------

    }


    private  void NewPurchasePrice2(final String ID2, final String QtyNewItem2, final String ItemNameNew2, final String BarcodeNew2)
    {
        //-----------------------------Button Dailog--------------------------------------------------------
        AlertDialog.Builder builder = new AlertDialog.Builder(IncomeVoucherDetails.this);
        builder.setTitle("إدخال سعر الشراء للمادة ");
        builder.setIcon(R.drawable.sms);
        builder.setMessage(" يرجى إدخال سعر شراء الصنف    -->    " + ItemNameNew2);

        inputPurchasePrice = new EditText(IncomeVoucherDetails.this);
        inputPurchasePrice.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
        builder.setView(inputPurchasePrice);
        builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PurchasePriceNew = inputPurchasePrice.getText().toString();
                if (PurchasePriceNew.equals("")) {
                    Toast.makeText(IncomeVoucherDetails.this, "يرجى إدخال سعر الشراء للمادة", Toast.LENGTH_LONG).show();
                    TBarcode.setText("");
                    return;
                }
                else
                {

                    if(!ItemNameNew2.equals("") && !PurchasePriceNew.equals("") && !BarcodeNew2.equals(""))
                    {


                           Cls_ItemsHistoryIncomeVoucher a = new Cls_ItemsHistoryIncomeVoucher();
                           a.setID(ID2);
                           a.setName(ItemNameNew2);
                           a.setMainBarcode(BarcodeNew2);
                           a.setQty(QtyNewItem2);
                           a.setrowindex(alistIncome.size()+1);
                           a.setPurchasePrice(PurchasePriceNew);
                           alistIncome.add(a);
                           Toast.makeText(IncomeVoucherDetails.this, "تم إضافة المادة بنجاح", Toast.LENGTH_LONG).show();
                           TBarcode.setText("");

                    }
                    else
                    {
                        Toast.makeText(IncomeVoucherDetails.this, "حدث خلل بسيط ، يرجى إعادة المحاولة", Toast.LENGTH_LONG).show();
                        TBarcode.setText("");
                        return;
                    }

                    ///-------------------------------------------------------------------------------------
                }
            }
        });

        builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                TBarcode.setText("");
            }
        });

        final AlertDialog add = builder.create();
        add.show();

        //--------------------------------------Button Dailog----------------------------------------------

    }


    void SavedOrder()  {
        try {


        Bundle extras = getIntent().getExtras();
        final String ID_Branch=extras.get("ID_Branch").toString();
        final String ID_Store=extras.get("ID_Store").toString();
        final String ID_Supplier=extras.get("ID_Supplier").toString();
        final String Trecipient=extras.get("Trecipient").toString();
        final String Note=extras.get("Note").toString();
        final String VoucherNumber=extras.get("VoucherNumber").toString();
        final String dateWork=extras.get("dateWork").toString();
        final String TransactionID=extras.get("TransactionID").toString();

        JSONObject object = null;
          for (int i=0;i<alistIncome.size();i++)
          {
              object=new JSONObject();
              try {
                  object.put("ID",alistIncome.get(i).getID());
                  object.put("Qty",alistIncome.get(i).getQty());
                  object.put("MainBarcode",alistIncome.get(i).getMainBarcode());
                  object.put("Name",alistIncome.get(i).getName());
                  object.put("rowindex",i);
                  object.put("PurchasePrice",alistIncome.get(i).getPurchasePrice());
              } catch (JSONException e) {
                  e.printStackTrace();
              }
              ArrayItemSelected.put(object);
          }


        if(DataBaseHelper.ParmsSettingsDevice.Datamigration) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(conRoyal.ConIpRoyal2)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
            post pp = new post(conRoyal.ConnectionString, ID_Branch, ID_Store, ID_Supplier, Trecipient, Note, VoucherNumber, dateWork, frmType, TransactionID, String.valueOf(0), String.valueOf(1), conRoyal.VendorID, ArrayItemSelected.toString());
            Call<post> call = jsonPlaceHolderApi.createPost(pp);
            call.enqueue(new Callback<post>() {
                @Override
                public void onResponse(Call<post> call, retrofit2.Response<post> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getStatus().equals("true"))
                        {
                            try {
                                if(da.SavefrmInvoiceSales(ID_Branch, ID_Store, ID_Supplier, Trecipient, Note, VoucherNumber, dateWork, frmType, TransactionID, String.valueOf(0), String.valueOf(1), conRoyal.VendorID, ArrayItemSelected, DataBaseHelper.Transaction.Insert_transferred)) {
                                    NewOrder();
                                    Global.ShowDataSaved(IncomeVoucherDetails.this, getString(R.string.txtCorrectoperation), getString(R.string.txtmigratedsystem));

                                }
                            } catch (JSONException e) {
                                Toast.makeText(IncomeVoucherDetails.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<post> call, Throwable t) {
                    try {
                        if(da.SavefrmInvoiceSales(ID_Branch, ID_Store, ID_Supplier, Trecipient, Note, VoucherNumber, dateWork, frmType, TransactionID, String.valueOf(0), String.valueOf(1), conRoyal.VendorID, ArrayItemSelected, DataBaseHelper.Transaction.Insert_Not_transferred))
                        {
                            NewOrder();
                            Global.ShowDataSavedToSqlLitePopup(IncomeVoucherDetails.this);

                        }
                        else
                        {
                            Toast.makeText(IncomeVoucherDetails.this, " فشلة عملية الحفظ ", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        Toast.makeText(IncomeVoucherDetails.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }


            });
        }
        else
        {
            try {
                if(da.SavefrmInvoiceSales(ID_Branch, ID_Store, ID_Supplier, Trecipient, Note, VoucherNumber, dateWork, frmType, TransactionID, String.valueOf(0), String.valueOf(1), conRoyal.VendorID, ArrayItemSelected, DataBaseHelper.Transaction.Insert_Not_transferred))
                {

                    NewOrder();
                    Global.ShowDataSavedToSqlLitePopup(IncomeVoucherDetails.this);



                }
                else
                {
                    Toast.makeText(IncomeVoucherDetails.this, " فشلة عملية الحفظ ", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(IncomeVoucherDetails.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }
        }
        catch (Exception ex)
        {
            Toast.makeText(IncomeVoucherDetails.this,ex.getMessage(),Toast.LENGTH_SHORT).show();
            SavedOrder();
        }
    }
}
