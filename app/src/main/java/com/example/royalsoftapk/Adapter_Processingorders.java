package com.example.royalsoftapk;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;

public class Adapter_Processingorders extends BaseAdapter {
    private EditText inputEditQty;
    private EditText inputEditQtylength;
    String Qtywidth;
    String Qtylength;
    Context context;
    ArrayList<Cls_Virable_Processingorders> alist_Processingorders;

    public Adapter_Processingorders(Context context, ArrayList<Cls_Virable_Processingorders> alist_Processingorders) {
        this.context = context;
        this.alist_Processingorders = alist_Processingorders;
    }

    @Override
    public int getCount() {
        return alist_Processingorders.size();
    }

    @Override
    public Object getItem(int i) {
        return alist_Processingorders.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final Cls_Virable_Processingorders cls_virable_Processingorders=alist_Processingorders.get(i);

        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_processingorders, null);
        }

        //----------------------------------------------------------------------------

        final CheckBox chkItem = (CheckBox) view.findViewById(R.id.chkItem);
        //chkItem.setText(cls_virable_Processingorders.getItemAName());
        chkItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean chk=chkItem.isChecked();
                cls_virable_Processingorders.setChecked(chk);
            }
        });

        TextView StartNumber = (TextView) view.findViewById(R.id.sStartNumber);
        StartNumber.setText(String.valueOf(cls_virable_Processingorders.getStartNumber()));



        TextView ItemAName = (TextView) view.findViewById(R.id.ItemAName);
        ItemAName.setText(cls_virable_Processingorders.getItemAName());

        TextView ItemSubAName = (TextView) view.findViewById(R.id.ItemSubAName);
        ItemSubAName.setText(cls_virable_Processingorders.getItemSubAName());


        TextView CustomerAName = (TextView) view.findViewById(R.id.CustomerAName);
        CustomerAName.setText(cls_virable_Processingorders.getCustomerName());


        TextView Date = (TextView) view.findViewById(R.id.Date);
        Date.setText(cls_virable_Processingorders.getDate());


        ImageView State = (ImageView) view.findViewById(R.id.send);
        if(cls_virable_Processingorders.getState()==3) {
            State.setImageResource(R.drawable.ic_check_circle_green_24dp);
        }
        else
        {
            State.setImageResource(R.drawable.ic_send_black_24dp);
        }

        State.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                final int ID = cls_virable_Processingorders.getID();
                if(cls_virable_Processingorders.getState()==2) {


                    boolean cc=false;
                    for(int i=0;i<alist_Processingorders.size();i++)
                    {
                        if(alist_Processingorders.get(i).isChecked())
                        {
                            cc=true;
                            break;
                        }
                    }

                    if(cc) {
                        if (cls_virable_Processingorders.getProperty() == 1)  // QTY
                        {
                            //----------------------------- Dailog ItemName_New--------------------------------------------------------
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("?????????? ????????????  ");
                            builder.setIcon(R.drawable.sms);
                            builder.setMessage(" ???????? ?????????? ????????????     ");

                            inputEditQty = new EditText(context);

                            inputEditQty.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                            builder.setView(inputEditQty);
                            builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Qtywidth = inputEditQty.getText().toString();
                                    if (Qtywidth.equals("")) {
                                        Toast.makeText(context, "???????? ?????????? ???????????? ", Toast.LENGTH_LONG).show();
                                        return;
                                    } else {
                                        saveall(context, ID, Double.valueOf(Qtywidth), Double.valueOf(0));
                                    }
                                }
                            });
                            builder.setNegativeButton("??????????", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            final AlertDialog add = builder.create();
                            add.show();
                            //--------------------------------------Button Dailog----------------------------------------------

                        }
                        if (cls_virable_Processingorders.getProperty() == 2)  // Two
                        {
                            //----------------------------- Dailog ItemName_New--------------------------------------------------------
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("?????????? ???????????? ?????????? ");
                            builder.setIcon(R.drawable.sms);
                            builder.setMessage(" ???????? ?????????? ????????????  ??????????   ");

                            inputEditQty = new EditText(context);

                            inputEditQty.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                            builder.setView(inputEditQty);
                            builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Qtywidth = inputEditQty.getText().toString();
                                    if (Qtywidth.equals("")) {
                                        Toast.makeText(context, "???????? ?????????? ???????????? ??????????", Toast.LENGTH_LONG).show();
                                        return;
                                    } else {


                                        //----------------------------- Dailog ItemName_New--------------------------------------------------------
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setTitle("?????????? ???????????? ?????????? ");
                                        builder.setIcon(R.drawable.sms);
                                        builder.setMessage(" ???????? ?????????? ????????????  ??????????   ");

                                        inputEditQtylength = new EditText(context);

                                        inputEditQtylength.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                                        builder.setView(inputEditQtylength);
                                        builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Qtylength = inputEditQtylength.getText().toString();
                                                if (Qtylength.equals("")) {
                                                    Toast.makeText(context, "???????? ?????????? ???????????? ??????????", Toast.LENGTH_LONG).show();
                                                    return;
                                                } else {
                                                    saveall(context, ID, Double.valueOf(Qtywidth), Double.valueOf(Qtylength));
                                                }
                                            }
                                        });
                                        builder.setNegativeButton("??????????", new DialogInterface.OnClickListener() {
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
                            builder.setNegativeButton("??????????", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            final AlertDialog add = builder.create();
                            add.show();
                            //--------------------------------------Button Dailog----------------------------------------------
                        }
                        if (cls_virable_Processingorders.getProperty() == 3)  // Height
                        {

                            //----------------------------- Dailog ItemName_New--------------------------------------------------------
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("?????????? ???????????? ?????????? ");
                            builder.setIcon(R.drawable.sms);
                            builder.setMessage(" ???????? ?????????? ????????????  ??????????   ");

                            inputEditQtylength = new EditText(context);

                            inputEditQtylength.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                            builder.setView(inputEditQtylength);
                            builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Qtylength = inputEditQtylength.getText().toString();
                                    if (Qtylength.equals("")) {
                                        Toast.makeText(context, "???????? ?????????? ???????????? ??????????", Toast.LENGTH_LONG).show();
                                        return;
                                    } else {
                                        saveall(context, ID, Double.valueOf(0), Double.valueOf(Qtylength));
                                    }
                                }
                            });
                            builder.setNegativeButton("??????????", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            final AlertDialog add = builder.create();
                            add.show();
                            //--------------------------------------Button Dailog----------------------------------------------
                        }
                        if (cls_virable_Processingorders.getProperty() == 4)  // Width
                        {
                            //----------------------------- Dailog ItemName_New--------------------------------------------------------
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("?????????? ???????????? ?????????? ");
                            builder.setIcon(R.drawable.sms);
                            builder.setMessage(" ???????? ?????????? ???????????? ??????????    ");

                            inputEditQty = new EditText(context);

                            inputEditQty.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

                            builder.setView(inputEditQty);
                            builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Qtywidth = inputEditQty.getText().toString();
                                    if (Qtywidth.equals("")) {
                                        Toast.makeText(context, "???????? ?????????? ???????????? ??????????", Toast.LENGTH_LONG).show();
                                        return;
                                    } else {
                                        saveall(context, ID, Double.valueOf(Qtywidth), Double.valueOf(0));
                                    }
                                }
                            });
                            builder.setNegativeButton("??????????", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            final AlertDialog add = builder.create();
                            add.show();
                            //--------------------------------------Button Dailog----------------------------------------------
                        }
                        if (cls_virable_Processingorders.getProperty() == 0)  // else
                        {
                            Toast.makeText(context, "???????????? ?????? ?????? ?????????? ?????? ?? ?????? ?? ????????", Toast.LENGTH_LONG).show();
                            return;
                        }

                    }
                    else
                    {
                        Toast.makeText(context, "???????? ?????????? ???????? ?????????? ?????? ??????????", Toast.LENGTH_LONG).show();
                    }

//                    //----------------------------- Dailog ItemName_New--------------------------------------------------------
//                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                    builder.setTitle("?????????? ???????????? ?????????? ");
//                    builder.setIcon(R.drawable.sms);
//                    builder.setMessage(" ???????? ?????????? ????????????  ??????????   ");
//
//                    inputEditQty = new EditText(context);
//
//                        inputEditQty.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
//
//                    builder.setView(inputEditQty);
//                    builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Qtywidth = inputEditQty.getText().toString();
//                            if (Qtywidth.equals(""))
//                            {
//                                Toast.makeText(context, "???????? ?????????? ???????????? ??????????", Toast.LENGTH_LONG).show();
//                                return;
//                            } else {
//
//
//
//
//
//
//
//
//
//
//                                //----------------------------- Dailog ItemName_New--------------------------------------------------------
//                                AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                                builder.setTitle("?????????? ???????????? ?????????? ");
//                                builder.setIcon(R.drawable.sms);
//                                builder.setMessage(" ???????? ?????????? ????????????  ??????????   ");
//
//                                inputEditQtylength = new EditText(context);
//
//                                inputEditQtylength.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
//
//                                builder.setView(inputEditQtylength);
//                                builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        Qtylength = inputEditQtylength.getText().toString();
//                                        if (Qtylength.equals(""))
//                                        {
//                                            Toast.makeText(context, "???????? ?????????? ???????????? ??????????", Toast.LENGTH_LONG).show();
//                                            return;
//                                        } else {
//                                            Savedata(context,ID,Double.valueOf(Qtywidth),Double.valueOf(Qtylength));
//                                        }
//                                    }
//                                });
//                                builder.setNegativeButton("??????????", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        dialog.dismiss();
//                                    }
//                                });
//
//                                final AlertDialog add = builder.create();
//                                add.show();
//                                //--------------------------------------Button Dailog----------------------------------------------
//
//
//
//
//
//
//
//
//                            }
//                        }
//                    });
//                    builder.setNegativeButton("??????????", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//
//                    final AlertDialog add = builder.create();
//                    add.show();
//                    //--------------------------------------Button Dailog----------------------------------------------
                }
            }
        });




        return view;
    }

    private void saveall(Context c, int ID, Double width, Double length){
        try
        {
            for(int i=0;i<alist_Processingorders.size();i++)
            {
                if(alist_Processingorders.get(i).isChecked())
                {
                    Savedata(context,alist_Processingorders.get(i).getID(),Double.valueOf(width),Double.valueOf(length));
                }
            }
            Qtywidth="0";
            Qtylength="0";
            Intent intent=new Intent(context,frmProcessingorders.class);
            context.startActivity(intent);
        }
        catch (Exception ex)
        {

        }
    }
    private void Savedata(Context c, int ID, Double width, Double length) {
        try {
             RequestQueue mQueue;
            conRoyal.UrlRoyal_UpdateItemsToPrepareByEmployeeByID(ID,width,length);
            mQueue = Volley.newRequestQueue(c);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,conRoyal.Url_UpdateItemsToPrepareByEmployeeByID, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {


                            //for (int i = 0; i < response.length(); i++) {
                            // JSONObject obj = response.getJSONObject(i);

                            // String CustomerAName = obj.getString("CustomerAName");

                            //}
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
            Toast.makeText(c,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
}
