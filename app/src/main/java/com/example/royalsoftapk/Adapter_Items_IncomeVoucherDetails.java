package com.example.royalsoftapk;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;

import java.util.ArrayList;

public class Adapter_Items_IncomeVoucherDetails extends BaseAdapter {
    Context context;
    ArrayList<Cls_ItemsHistoryIncomeVoucher> alistItems;
    private EditText inputEditQty;
    private RequestQueue mQueue;
    String QtyNew;
    public Adapter_Items_IncomeVoucherDetails(Context context, ArrayList<Cls_ItemsHistoryIncomeVoucher> alistItems) {
        this.context = context;
        this.alistItems = alistItems;
    }

    @Override
    public int getCount() {
        return alistItems.size();
    }


    public int removeItem(int position)
    {
        alistItems.remove(position);
        return 0;
    }
    public int getrowindex(int pos)
    {
        int a=alistItems.get(pos).getrowindex();
        return  a;
    }



    @Override
    public Object getItem(int position) {
        return alistItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Cls_ItemsHistoryIncomeVoucher Cls_ItemsHistoryIncomeVoucher=alistItems.get(position);


        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_items_income_voucher_detils, null);
        }

        //----------------------------------------------------------------------------
         TextView ItemName = (TextView) convertView.findViewById(R.id.ItemName_Items);
        ItemName.setText(Cls_ItemsHistoryIncomeVoucher.getName());

        final TextView Qty = (TextView) convertView.findViewById(R.id.Qty_Items);
        Qty.setText(Cls_ItemsHistoryIncomeVoucher.getQty());
        Qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //----------------------------- Dailog ItemName_New--------------------------------------------------------
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("تعديل الكمية ");
                builder.setIcon(R.drawable.sms);
                builder.setMessage(" يرجى إدخال الكمية  (تعديل)   ");

                inputEditQty = new EditText(context);
                inputEditQty.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(inputEditQty);
                builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        QtyNew = inputEditQty.getText().toString();
                        if (QtyNew.equals("")) {
                            Toast.makeText(context, "يرجى إدخال الكمية", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else
                        {
                            Qty.setText(QtyNew);
                           Cls_ItemsHistoryIncomeVoucher.setQty(QtyNew);
                            Toast.makeText(context,"تم تعديل كمية المادة بنجاح",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                final AlertDialog add = builder.create();
                add.show();
                //--------------------------------------Button Dailog----------------------------------------------
            }
        });


        TextView MainBarcode = (TextView) convertView.findViewById(R.id.MainBarcode_Items);
        MainBarcode.setText(Cls_ItemsHistoryIncomeVoucher.getMainBarcode());

        ImageView Delete =(ImageView) convertView.findViewById(R.id.bt_DeleteList);
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               conRoyal.alistIncome.remove(position);
               notifyDataSetChanged();
                Toast.makeText(context,"تم حذف الصنف بنجاح",Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;

    }

    //-------------------------------//
}
