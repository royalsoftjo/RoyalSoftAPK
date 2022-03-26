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
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Adapter_Items_InvoiceSales extends BaseAdapter {
    Context context;
    ArrayList<Cls_ItemsInvoiceSales> alistItems;
    ArrayList<Cls_ItemsInvoiceSales> alistItems2;
    ImageView imagedailog;
    private FriendFilter friendFilter;
    private EditText inputEditQty;
    String QtyNew;
    public Adapter_Items_InvoiceSales(Context context, ArrayList<Cls_ItemsInvoiceSales> alistItems) {
        this.context = context;
        this.alistItems = alistItems;
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
         final Cls_ItemsInvoiceSales Cls_ItemsInvoiceSales=alistItems.get(position);


        if (convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_items_invoice_sales, null);
        }

        //----------------------------------------------------------------------------
        final TextView ItemName = (TextView) convertView.findViewById(R.id.ItemNameInvoiceSales);
        ItemName.setText(Cls_ItemsInvoiceSales.getItemName());

        final TextView Qty = (TextView) convertView.findViewById(R.id.QtyInvoiceSales);
        Qty.setText(Cls_ItemsInvoiceSales.getQty());
        Qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //----------------------------- Dailog ItemName_New--------------------------------------------------------
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("تعديل الكمية ");
                builder.setIcon(R.drawable.sms);
                builder.setMessage(" يرجى إدخال الكمية  (تعديل)   ");

                inputEditQty = new EditText(context);
                inputEditQty.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
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
                          //  Cls_ItemsInvoiceSales.setQty(QtyNew);
                            if(Double.valueOf(QtyNew)>=0) {
                                Double dd = Double.valueOf(QtyNew);
                                DecimalFormat DF = new DecimalFormat("######0.000");
                                Cls_ItemsInvoiceSales.setQty(Global.convertToEnglish(DF.format(dd)));
                                Qty.setText(Cls_ItemsInvoiceSales.getQty());
                                if (conRoyal.listInvoiceSales.size() == 0) {
                                    conRoyal.listInvoiceSales.add(Cls_ItemsInvoiceSales);
                                } else {
                                    boolean IsNow=false;
                                    for (int i = 0; i < conRoyal.listInvoiceSales.size(); i++) {
                                        if (Cls_ItemsInvoiceSales.getID() == conRoyal.listInvoiceSales.get(i).getID()) {
                                            conRoyal.listInvoiceSales.get(i).setQty(Global.convertToEnglish(Cls_ItemsInvoiceSales.getQty()));
                                            IsNow=true;
                                            break;
                                        }
                                        //something here
                                    }
                                    if(IsNow!=true)
                                    {
                                        conRoyal.listInvoiceSales.add(Cls_ItemsInvoiceSales);
                                    }
                                }

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

        final TextView Price = (TextView) convertView.findViewById(R.id.PriceInvoiceSales);
        Price.setText(Cls_ItemsInvoiceSales.getPrice());

        final ImageView ImageItem =(ImageView) convertView.findViewById(R.id.ImageInvoiceSales);

        ImageItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertadd = new AlertDialog.Builder(context);
                LayoutInflater factory = LayoutInflater.from(context);
                final View view = factory.inflate(R.layout.dailogimageview, null);
                imagedailog=view.findViewById(R.id.dialog_imageview);
                if(Cls_ItemsInvoiceSales.getBase64Image()!=null)
                {
                    imagedailog.setImageBitmap(Cls_ItemsInvoiceSales.getBase64Image());
                }
                else
                {
                    imagedailog.setImageResource(R.drawable.logoroyal);
                }
                alertadd.setView(view);
                alertadd.setNeutralButton(R.string.close_printer, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dlg, int sumthin) {

                    }
                });

                alertadd.show();
            }
        });
        if(Cls_ItemsInvoiceSales.getBase64Image()!=null)
        {
            ImageItem.setImageBitmap(Cls_ItemsInvoiceSales.getBase64Image());
        }
        else
        {
            ImageItem.setImageResource(R.drawable.logoroyal);
        }


        ImageView ImageBtmin =(ImageView) convertView.findViewById(R.id.Btmin);
        ImageBtmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Double.valueOf(Global.convertToEnglish(Cls_ItemsInvoiceSales.getQty()))>0)
                {
                    Double dd=Double.valueOf(Global.convertToEnglish(Cls_ItemsInvoiceSales.getQty()))-1;
                    DecimalFormat DF = new DecimalFormat("######0.000");
                    Cls_ItemsInvoiceSales.setQty(Global.convertToEnglish(DF.format(dd)));
                    Qty.setText(Cls_ItemsInvoiceSales.getQty());
                    //listInvoiceSales.clear();




                        for (int i = 0; i < conRoyal.listInvoiceSales.size(); i++) {
                            if (Cls_ItemsInvoiceSales.getID() == conRoyal.listInvoiceSales.get(i).getID()) {
                                if(Double.valueOf(Global.convertToEnglish(Cls_ItemsInvoiceSales.getQty()))==0)
                                {
                                    conRoyal.listInvoiceSales.remove(i);
                                }
                                else {
                                    conRoyal.listInvoiceSales.get(i).setQty(Global.convertToEnglish(Cls_ItemsInvoiceSales.getQty()));
                                }
                                break;
                            }
                            //something here
                        }



                   // listInvoiceSales.add(Cls_ItemsInvoiceSales);
                    //if(Double.valueOf(Cls_ItemsInvoiceSales.getQty())>0)
                    //{
                     //   ItemName.setBackgroundColor(Color.rgb(0, 199, 100));
                    //    ImageItem.setBackgroundColor(Color.rgb(0, 199, 100));
                   //     Price.setBackgroundColor(Color.rgb(0, 199, 100));
                   // }
                   // else
                    //{
                      //  ItemName.setBackgroundColor(Color.WHITE);
                     //   ImageItem.setBackgroundColor(Color.WHITE);
                    //    Price.setBackgroundColor(Color.WHITE);
                   // }
                }
            }
        });

        ImageView ImageBtPlus =(ImageView) convertView.findViewById(R.id.BtPlus);
        ImageBtPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(Double.parseDouble(Cls_ItemsInvoiceSales.getQty())>=0) {
                    Double dd = Double.valueOf(Global.convertToEnglish(Cls_ItemsInvoiceSales.getQty())) + 1;
                    DecimalFormat DF = new DecimalFormat("######0.000");
                    Cls_ItemsInvoiceSales.setQty(Global.convertToEnglish(DF.format(dd)));
                    Qty.setText(Cls_ItemsInvoiceSales.getQty());
                    //    if(Cls_ItemsInvoiceSales.getID()==conRoyal.listInvoiceSales.indexOf(Cls_ItemsInvoiceSales.getItemName()))
                    //    {
                    //         conRoyal.listInvoiceSales.get(position).setQty(Cls_ItemsInvoiceSales.getQty());
                    //    }
                    //   else
                    //   {
                    //      conRoyal.listInvoiceSales.add(Cls_ItemsInvoiceSales);
                    // }
                    if (conRoyal.listInvoiceSales.size() == 0) {
                        conRoyal.listInvoiceSales.add(Cls_ItemsInvoiceSales);
                    } else {
                        boolean IsNow=false;
                        for (int i = 0; i < conRoyal.listInvoiceSales.size(); i++) {
                            if (Cls_ItemsInvoiceSales.getID() == conRoyal.listInvoiceSales.get(i).getID()) {
                                conRoyal.listInvoiceSales.get(i).setQty(Global.convertToEnglish(Cls_ItemsInvoiceSales.getQty()));
                                IsNow=true;
                                break;
                            }
                            //something here
                        }
                        if(IsNow!=true)
                        {
                            conRoyal.listInvoiceSales.add(Cls_ItemsInvoiceSales);
                        }
                    }

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
            }
        });

        //String Base641=Cls_ItemsInvoiceSales.getBase64Image();
        //if(!Base641.equals("0"))
        //{
           // byte[] iimage = Base64.decode(Base641, Base64.DEFAULT);
           // Bitmap bp = BitmapFactory.decodeByteArray(iimage, 0, iimage.length);
           // ImageItem.setImageBitmap(bp);
        //   Base641="0";
        //}
        return convertView;
    }

    public Filter getFilter() {
        if (friendFilter == null) {
            alistItems2=alistItems;
              friendFilter = new FriendFilter();
        }

        return friendFilter;
    }

    private class FriendFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            alistItems=alistItems2;
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<Cls_ItemsInvoiceSales> tempList = new ArrayList<Cls_ItemsInvoiceSales>();

                // search content in friend list
                for (Cls_ItemsInvoiceSales user : alistItems) {
                    if (user.getItemName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(user);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = alistItems.size();
                filterResults.values = alistItems;
            }

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            alistItems = (ArrayList<Cls_ItemsInvoiceSales>) results.values;
            notifyDataSetChanged();
        }
    }}
