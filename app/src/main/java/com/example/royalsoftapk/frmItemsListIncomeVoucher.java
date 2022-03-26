package com.example.royalsoftapk;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class frmItemsListIncomeVoucher extends AppCompatActivity {
    ListView listView;
    Adapter_Items_IncomeVoucherDetails Adapter;
    ArrayList<Cls_ItemsHistoryIncomeVoucher> alist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frm_items_list_income_voucher);
        listView =findViewById(R.id.list_ItemsIncomeVoucher);


        getdata();
    }
    private void getdata() {
        Adapter = new Adapter_Items_IncomeVoucherDetails(frmItemsListIncomeVoucher.this, conRoyal.alistIncome);

        listView.setAdapter(Adapter);

    }


}
