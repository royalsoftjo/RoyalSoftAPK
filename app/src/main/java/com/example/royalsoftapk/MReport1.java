package com.example.royalsoftapk;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MReport1 extends Fragment {

CardView cardPaid,CardReceipts,Card_offer,Card_Cash_Count,Card_CloseCash,Custody_staff,Card_Sms;
    public static   EditText input;
    public static   String Srr;
    public MReport1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =inflater.inflate(R.layout.fragment_mreport1, container, false);

        cardPaid=view.findViewById(R.id.CardPaid);
        CardReceipts=view.findViewById(R.id.CardReceipts);
        Card_offer=view.findViewById(R.id.Card_offer);
        Card_Cash_Count=view.findViewById(R.id.Card_Cash_Count);
        Card_CloseCash=view.findViewById(R.id.Card_CloseCash);
        Custody_staff=view.findViewById(R.id.Custody_staff);
        Card_Sms=view.findViewById(R.id.Card_Send_SMS);



        cardPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),frm_OutgoingVoucher.class);
                startActivity(intent);
            }
        });

        /*CardReceipts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),incoming_voucher.class);
                startActivity(intent);
            }
        });*/


        Card_offer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),List_offer_headers.class);
                startActivity(intent);
            }
        });


        Card_Cash_Count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),Cash_Count.class);
                startActivity(intent);
            }
        });

        Card_CloseCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getActivity(),EnableCashDrawer.class);
                startActivity(intent);
            }
        });

        Custody_staff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),frmCustody_staff.class);
                startActivity(intent);
            }
        });














        //-----------------------------Button Dailog--------------------------------------------------------
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("إرسال رسالة");
        //builder.setIcon(R.drawable.r22);
        builder.setMessage("يرجى تحديد نص الرسالة");

        input=new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);
        builder.setPositiveButton("إرسال", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                Srr= input.getText().toString();



            }
        });

        builder.setNegativeButton("إلغاء", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog add = builder.create();

        Card_Sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // add.show();

                Intent intent=new Intent(getActivity(),frmSendSMS.class);
                startActivity(intent);
            }
        });

        //--------------------------------------Button Dailog----------------------------------------------






        return view;
    }

}
