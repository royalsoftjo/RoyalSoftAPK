package com.example.royalsoftapk;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;


public class PDAHome extends Fragment {
    Button btnIncomeVoucher,bt_PurchasePrice,btn_purchaseOrder,btn_Itemquantity,btn_PrintBarcode,btn_BondInventory,btn_NewItem;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_pdahome, container, false);

        btnIncomeVoucher=(Button)view.findViewById(R.id.bt_IncomeVoucher);
        bt_PurchasePrice=view.findViewById(R.id.bt_PurchasePrice);
        btn_purchaseOrder=view.findViewById(R.id.bt_purchaseOrder);
        btn_Itemquantity=view.findViewById(R.id.bt_Itemquantity);
        btn_PrintBarcode=view.findViewById(R.id.bt_PrintBarcode);
        btn_BondInventory=view.findViewById(R.id.bt_BondInventory);
        btn_NewItem=view.findViewById(R.id.bt_itemsNew);


        btn_BondInventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),IncomeVoucherHeader.class);
                intent.putExtra("frmType",4);//---- سند الجرد ----------
                startActivity(intent);
            }
        });
        bt_PurchasePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),frmSelectPurchasePrice.class);
                startActivity(intent);
            }
        });
        btnIncomeVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),IncomeVoucherHeader.class);
                intent.putExtra("frmType",1);//---- سند استلام ----------
                startActivity(intent);
            }
        });
        btn_purchaseOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),IncomeVoucherHeader.class);
                intent.putExtra("frmType",2);//---- طلب شراء ----------
                startActivity(intent);
            }
        });
        btn_Itemquantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),frmSelectItemQuantity.class);
                startActivity(intent);
            }
        });
        btn_PrintBarcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),frmPrintBarcode.class);startActivity(intent);
            }
        });

        btn_NewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),frmItemsNew.class);startActivity(intent);
            }
        });
       // if(conRoyal.Access_Home_ID==1) {
      //  btn_NewItem.setVisibility(View.VISIBLE);
       // btn_Itemquantity.setVisibility(View.VISIBLE);
     //   }
      //  else
     //   {
      //      btn_NewItem.setVisibility(View.GONE);
      //      btn_Itemquantity.setVisibility(View.GONE);
     //   }
        return view;
    }
    }



