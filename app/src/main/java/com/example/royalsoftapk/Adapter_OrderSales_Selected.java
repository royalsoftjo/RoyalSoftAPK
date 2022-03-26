package com.example.royalsoftapk;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_OrderSales_Selected extends BaseAdapter {
    Context context;
    Activity act;
    ListView list;
    private EditText inputEditQty;
    String QtyNew;
    ArrayList<ClsOrderSalesSelected> alistItems;
    public Adapter_OrderSales_Selected(Context context, ArrayList<ClsOrderSalesSelected> alistItems,Activity act) {
        this.context = context;
        this.alistItems = alistItems;
        this.act=act;
    }
    @Override
    public int getCount() {
        return alistItems.size();
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

        final ClsOrderSalesSelected ClsOrderSalesSelected=alistItems.get(position);


        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_order_sales_selected, null);
        }

        //----------------------------------------------------------------------------
         final TextView Number = (TextView) convertView.findViewById(R.id.Number);
        Number.setText(String.valueOf(position+1));

         final TextView ItemName = (TextView) convertView.findViewById(R.id.ItemNameSelected);
        ItemName.setText(ClsOrderSalesSelected.getItemName());




        final TextView Price = (TextView) convertView.findViewById(R.id.PriceSelected);
        Price.setText(ClsOrderSalesSelected.getPrice());

        final TextView Total = (TextView) convertView.findViewById(R.id.TotalSelected);
        Double total=Double.valueOf(ClsOrderSalesSelected.getPrice()) * Double.valueOf(ClsOrderSalesSelected.getQty());
        DecimalFormat DF = new DecimalFormat("######0.000");
        Total.setText(DF.format(total));

         final TextView Qty = (TextView) convertView.findViewById(R.id.QtySelected);
         Qty.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(final View v) {
                 boolean IsUpdated=alistItems.get(position).getUpdatedQty();

                 if(IsUpdated) {
                     //----------------------------- Dailog ItemName_New--------------------------------------------------------
                     AlertDialog.Builder builder = new AlertDialog.Builder(context);
                     builder.setTitle("تعديل الكمية ");
                     builder.setIcon(R.drawable.sms);
                     builder.setMessage(" يرجى إدخال الكمية  (تعديل)   ");

                     inputEditQty = new EditText(context);
                     if(alistItems.get(position).getisNumber())
                     {
                         inputEditQty.setInputType(InputType.TYPE_CLASS_NUMBER);
                     }
                     else {
                         inputEditQty.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
                     }
                     builder.setView(inputEditQty);
                     builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {
                             QtyNew = inputEditQty.getText().toString();
                             if (QtyNew.equals(""))
                             {
                                 Toast.makeText(context, "يرجى إدخال الكمية", Toast.LENGTH_LONG).show();
                                 return;
                             } else {
                                 double QtyBefore = alistItems.get(position).getQtyBefore();
                                 if ((Double.valueOf(QtyNew)) >= QtyBefore)
                                 {
                                     //  Cls_ItemsInvoiceSales.setQty(QtyNew);
                                     if (Double.valueOf(QtyNew) > 0) {
                                         Double dd = Double.valueOf(QtyNew);
                                         DecimalFormat DF = new DecimalFormat("######0.000");
                                         Qty.setText(Global.convertToEnglish(String.valueOf(dd)));
                                         alistItems.get(position).setStatus(0);
                                         double Salesprice = Double.valueOf(Price.getText().toString());
                                         double ATotal = dd * Salesprice;
                                         Total.setText(String.valueOf(ATotal));
                                         alistItems.get(position).setQty(String.valueOf(dd));
                                         //ClsOrderSalesSelected.setQty(String.valueOf(dd));
                                         alistItems.get(position).setTotal(String.valueOf(ATotal));

                                         //listInvoiceSales.clear();
                                         //listInvoiceSales.add(Cls_ItemsInvoiceSales);
                                         // if(Double.valueOf(Cls_ItemsInvoiceSales.getQty())>0)
                                         //  {
                                         //      ItemName.setBackgroundColor(Color.rgb(0, 199, 100));
                                         //      ImageItem.setBackgroundColor(Color.rgb(0, 199, 100));
                                         //      Price.setBackgroundColor(Color.rgb(0, 199, 100));
                                         //  }
                                         //   else
                                         //    {
                                         //      ItemName.setBackgroundColor(Color.WHITE);
                                         //      ImageItem.setBackgroundColor(Color.WHITE);
                                         //      Price.setBackgroundColor(Color.WHITE);
                                         //  }
                                     }
                                     //Qty.setText(QtyNew);

                                     // Qty.setText(QtyNew);

                                 }
                                 sumTotalOrder();
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
             }
         });

             Qty.setText(ClsOrderSalesSelected.getQty());


        if(ClsOrderSalesSelected.getAdditionID()!=0)
        {
            Number.setBackgroundColor(Color.BLUE);
            ItemName.setBackgroundColor(Color.BLUE);
            Price.setBackgroundColor(Color.BLUE);
            Qty.setBackgroundColor(Color.BLUE);
            Total.setBackgroundColor(Color.BLUE);
        }
        else if(ClsOrderSalesSelected.getItemNotes()==true)
        {
            Number.setBackgroundColor(Color.YELLOW);
            ItemName.setBackgroundColor(Color.YELLOW);
            Price.setBackgroundColor(Color.YELLOW);
            Qty.setBackgroundColor(Color.YELLOW);
            Total.setBackgroundColor(Color.YELLOW);
        }
        else
        {
            Number.setBackgroundColor(Color.WHITE);
            ItemName.setBackgroundColor(Color.WHITE);
            Price.setBackgroundColor(Color.WHITE);
            Qty.setBackgroundColor(Color.WHITE);
            Total.setBackgroundColor(Color.WHITE);
        }

         ImageView ImageItem =(ImageView) convertView.findViewById(R.id.DeleteRowSelectedOrderSales);

        ImageItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean IsDelete=alistItems.get(position).getIsDelete();
                if(IsDelete)
                {
                    if (conRoyal.listInvoiceSales.size() != 0) {
                        conRoyal.listInvoiceSales.remove(position);
                    }
                    int ItemID = Integer.valueOf(alistItems.get(position).getID());
                    int ItemAddions2 = Integer.valueOf(alistItems.get(position).getAdditionID());
                    Boolean IsNotes = alistItems.get(position).getHasNotes();
                    alistItems.remove(position);
                    if (IsNotes) {
                        for (int i = alistItems.size() - 1; i >= 0; i--) {
                            if (ItemID == Integer.valueOf(alistItems.get(i).getID())) {
                                int ItemAddions = Integer.valueOf(alistItems.get(i).getAdditionID());
                                if (ItemAddions != 0 || Double.valueOf(alistItems.get(i).getQty()) == 0) {
                                    alistItems.remove(i);
                                }
                            }
                        }
                    }


                    notifyDataSetChanged();
                    sumTotalOrder();
                }
                else
                {
                    Global.ShowAccessPopup(act,R.string.Sorry,R.string.txt_Access_delete,"#cc0000");
                }
            }
        });

        if(ClsOrderSalesSelected.getAdditionID()!=0)
        {
            Number.setBackgroundColor(Color.BLUE);
            ItemName.setBackgroundColor(Color.BLUE);
            Price.setBackgroundColor(Color.BLUE);
            Qty.setBackgroundColor(Color.BLUE);
            Total.setBackgroundColor(Color.BLUE);

        }
        else if(ClsOrderSalesSelected.getItemNotes()==true)
        {
            Number.setBackgroundColor(Color.YELLOW);
            ItemName.setBackgroundColor(Color.YELLOW);
            Price.setBackgroundColor(Color.YELLOW);
            Qty.setBackgroundColor(Color.YELLOW);
            Total.setBackgroundColor(Color.YELLOW);

        }
        else
        {
            Number.setBackgroundColor(Color.WHITE);
            ItemName.setBackgroundColor(Color.WHITE);
            Price.setBackgroundColor(Color.WHITE);
            Qty.setBackgroundColor(Color.WHITE);
            Total.setBackgroundColor(Color.WHITE);

        }

        return convertView;
    }
    public void sumTotalOrder()
    {
      TextView  TotalOrder=act.findViewById(R.id.txtTotalOrderSales);
        double TotalRows=0;
        for (int i=0;i<alistItems.size();i++) {
            TotalRows +=Double.parseDouble(alistItems.get(i).getPrice())*Double.parseDouble(alistItems.get(i).getQty());
        }
        DecimalFormat DF = new DecimalFormat("######0.000");
        TotalOrder.setText(DF.format(TotalRows));
    }

}
