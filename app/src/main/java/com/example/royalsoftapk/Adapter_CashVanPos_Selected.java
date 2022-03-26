package com.example.royalsoftapk;

import android.app.Activity;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_CashVanPos_Selected extends BaseAdapter {
    Context context;
    Activity act;
    ListView list;
    private EditText inputEditPrice;
    String PriceNew;
    ArrayList<ClsOrderSalesSelected> alistItems;
    int TransType;
    public Adapter_CashVanPos_Selected(Context context, ArrayList<ClsOrderSalesSelected> alistItems,Activity act,int TransType) {
        this.context = context;
        this.alistItems = alistItems;
        this.act=act;
        this.TransType=TransType;
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
        DecimalFormat DF = new DecimalFormat("######0.000");

        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_cashvanpos_selected, null);
        }

        //----------------------------------------------------------------------------


        final TextView Number = (TextView) convertView.findViewById(R.id.Number);
        Number.setText(String.valueOf(position+1));

        final TextView ItemName = (TextView) convertView.findViewById(R.id.ItemNameSelected);
        ItemName.setText(ClsOrderSalesSelected.getItemName());

        final TextView Based_quantity = (TextView) convertView.findViewById(R.id.Based_quantity);
        Based_quantity.setText(DF.format(Double.parseDouble(String.valueOf(ClsOrderSalesSelected.getBasedQTY()))));

        final TextView number_baskets = (TextView) convertView.findViewById(R.id.number_baskets);
        number_baskets.setText(String.valueOf(ClsOrderSalesSelected.getNumberbaskets()));

        final TextView Type_panier = (TextView) convertView.findViewById(R.id.Type_panier);
        Type_panier.setText(String.valueOf(ClsOrderSalesSelected.getBasketTypeName()));

        final TextView Empty_quantity = (TextView) convertView.findViewById(R.id.Empty_quantity);
        Empty_quantity.setText(DF.format(Double.parseDouble(String.valueOf(ClsOrderSalesSelected.getEmptyQTY()))));


        final TextView Price = (TextView) convertView.findViewById(R.id.PriceSelected);
        Price.setText(DF.format(Double.parseDouble(String.valueOf(ClsOrderSalesSelected.getPrice()))));

        final TextView Total = (TextView) convertView.findViewById(R.id.TotalSelected);
        Double total= Double.valueOf(ClsOrderSalesSelected.getPrice()) * Double.valueOf(ClsOrderSalesSelected.getQty());

        Total.setText(DF.format(total));

        final TextView Qty = (TextView) convertView.findViewById(R.id.QtySelected);
      /*  Qty.setOnClickListener(new View.OnClickListener() {
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
                                        ClsOrderSalesSelected.setQty(String.valueOf(dd));
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
        });*/
        Double Qtyf=Double.valueOf(ClsOrderSalesSelected.getQty());
        Qty.setText(DF.format(Qtyf));


        if(Global.IsHasBasket)
        {

            ItemName.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams loparamsItemName = (LinearLayout.LayoutParams) ItemName.getLayoutParams();
            loparamsItemName.weight=3;
            ItemName.setLayoutParams(loparamsItemName);



            Price.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams loparamsPrice = (LinearLayout.LayoutParams) Price.getLayoutParams();
            loparamsPrice.weight=2;
            Price.setLayoutParams(loparamsPrice);



            Total.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams loparamsTotal = (LinearLayout.LayoutParams) Total.getLayoutParams();
            loparamsTotal.weight=2;
            Total.setLayoutParams(loparamsTotal);


            Qty.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams loparamsQty = (LinearLayout.LayoutParams) Qty.getLayoutParams();
            loparamsQty.weight=1;
            Qty.setLayoutParams(loparamsQty);



            Based_quantity.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams loparamsBased_quantity = (LinearLayout.LayoutParams) Based_quantity.getLayoutParams();
            loparamsBased_quantity.weight=2;
            Based_quantity.setLayoutParams(loparamsBased_quantity);


            number_baskets.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams loparamsnumber_baskets = (LinearLayout.LayoutParams) number_baskets.getLayoutParams();
            loparamsnumber_baskets.weight=2;
            number_baskets.setLayoutParams(loparamsnumber_baskets);

            Type_panier.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams loparamsType_panier = (LinearLayout.LayoutParams) Type_panier.getLayoutParams();
            loparamsType_panier.weight=2;
            Type_panier.setLayoutParams(loparamsType_panier);

            Empty_quantity.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams loparamsEmpty_quantity = (LinearLayout.LayoutParams) Empty_quantity.getLayoutParams();
            loparamsEmpty_quantity.weight=2;
            Empty_quantity.setLayoutParams(loparamsEmpty_quantity);
        }



        Price.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TransType==15)
                {
                    final double UpPrice=Double.valueOf(ClsOrderSalesSelected.getWholeSalePrice());
                    final double MinPrice=Double.valueOf(ClsOrderSalesSelected.getMinWholeSalePrice());


                    //----------------------------- Dailog ItemName_New--------------------------------------------------------
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("تعديل السعر ");
                    builder.setIcon(R.drawable.sms);
                    builder.setMessage(" يرجى إدخال السعر  (تعديل)   ");

                    inputEditPrice = new EditText(context);

                    inputEditPrice.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                    builder.setView(inputEditPrice);
                    builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PriceNew = inputEditPrice.getText().toString();
                            if (PriceNew.equals(""))
                            {
                                Toast.makeText(context, "يرجى إدخال السعر", Toast.LENGTH_LONG).show();
                                return ;
                            } else {
                                if ((Double.valueOf(PriceNew)) >= MinPrice && (Double.valueOf(PriceNew)) <= UpPrice)
                                {
                                    //  Cls_ItemsInvoiceSales.setQty(QtyNew);
                                    if (Double.valueOf(PriceNew) > 0) {
                                        Double dd = Double.valueOf(PriceNew);
                                        DecimalFormat DF = new DecimalFormat("######0.000");
                                        Price.setText(Global.convertToEnglish(String.valueOf(dd)));
                                        double Salesprice = Double.valueOf(Price.getText().toString());
                                        double ATotal = Double.valueOf( alistItems.get(position).getQty()) * Salesprice;
                                        Total.setText(String.valueOf(ATotal));
                                        alistItems.get(position).setPrice(String.valueOf(dd));
                                        ClsOrderSalesSelected.setPrice(String.valueOf(dd));
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
                                else if((Double.valueOf(PriceNew)) < MinPrice)
                                {
                                    Global.ShowAccessPopup2(act,"عذرا"," يجب أن يكون السعر أعلى من  "+MinPrice,"#cc0000");
                                   /// Toast.makeText(context,MinPrice+"يجب أن يكون السعر أعلى من  ",Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                else if((Double.valueOf(PriceNew)) > UpPrice)
                                {
                                    //Toast.makeText(context,UpPrice+"يجب أن يكون السعر أقل من  ",Toast.LENGTH_SHORT).show();
                                    Global.ShowAccessPopup2(act,"عذرا"," يجب أن يكون السعر أقل من  "+UpPrice,"#D81B60");
                                    return;
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
                else
                {
                    //----------------------------- Dailog ItemName_New--------------------------------------------------------
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("تعديل السعر ");
                    builder.setIcon(R.drawable.sms);
                    builder.setMessage(" يرجى إدخال السعر  (تعديل)   ");

                    inputEditPrice = new EditText(context);

                    inputEditPrice.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                    builder.setView(inputEditPrice);
                    builder.setPositiveButton("حفظ", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            PriceNew = inputEditPrice.getText().toString();
                            if (PriceNew.equals(""))
                            {
                                Toast.makeText(context, "يرجى إدخال السعر", Toast.LENGTH_LONG).show();
                                return ;
                            } else {

                                    //  Cls_ItemsInvoiceSales.setQty(QtyNew);
                                    if (Double.valueOf(PriceNew) > 0) {
                                        Double dd = Double.valueOf(PriceNew);
                                        DecimalFormat DF = new DecimalFormat("######0.000");
                                        Price.setText(Global.convertToEnglish(String.valueOf(dd)));
                                        double Salesprice = Double.valueOf(Price.getText().toString());
                                        double ATotal = Double.valueOf( alistItems.get(position).getQty()) * Salesprice;
                                        Total.setText(String.valueOf(ATotal));
                                        alistItems.get(position).setPrice(String.valueOf(dd));
                                        ClsOrderSalesSelected.setPrice(String.valueOf(dd));
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


        ImageView ImageItem =(ImageView) convertView.findViewById(R.id.DeleteRowSelectedOrderSales);
        ImageItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean IsDelete=alistItems.get(position).getIsDelete();
                if(IsDelete)
                {
                    //int ItemID = Integer.valueOf(alistItems.get(position).getID());
                    alistItems.remove(position);
                    notifyDataSetChanged();
                    sumTotalOrder();
                }
                else
                {
                    Global.ShowAccessPopup(act,R.string.Sorry,R.string.txt_Access_delete,"#cc0000");
                }

            }
        });

        return convertView;
    }

    public void sumTotalOrder()
    {
        TextView  TotalOrder=act.findViewById(R.id.txtTotalCashVan);
        TextView  TotalQty=act.findViewById(R.id.txtTotalQty);
        TextView  TotalTaxAmount=act.findViewById(R.id.txtTotalTaxAmount);
        TextView  TotalBeforeTax=act.findViewById(R.id.txtTotalBeforeTax);
        double TotalRows=0;
        double TotalQtyRows=0;
        double dTotalBeforeTax=0;
        double dTotalTaxAmount=0;
        if(alistItems.size()>0) {
            for (int i = 0; i < alistItems.size(); i++) {
                TotalRows += Double.parseDouble(alistItems.get(i).getPrice()) * Double.parseDouble(alistItems.get(i).getQty());
                TotalQtyRows += Double.parseDouble(alistItems.get(i).getQty());
            }
            DecimalFormat DF = new DecimalFormat("######0.000");
            TotalOrder.setText(DF.format(TotalRows));
            TotalQty.setText(DF.format(TotalQtyRows));
            TotalBeforeTax.setText(DF.format(dTotalBeforeTax));
            TotalTaxAmount.setText(DF.format(dTotalTaxAmount));
        }
        else
        {
            TotalOrder.setText("0.000");
            TotalBeforeTax.setText("0.000");
            TotalTaxAmount.setText("0.000");
            TotalOrder.setText("0.000");
            TotalQty.setText("0.000");
        }
    }
}
