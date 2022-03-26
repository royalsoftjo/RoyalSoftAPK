package com.example.royalsoftapk;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;
import com.google.android.material.navigation.NavigationView;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class DASHWORD extends AppCompatActivity {
        private DrawerLayout mDrawerLayout;
        private ActionBarDrawerToggle mToggle;
        private RequestQueue mQueue;
              Dialog epicDialog;
              ImageView closebox;
    ProgressDialog progressDialog;
              Button btnCancel;
    DataBaseHelper da=new DataBaseHelper(DASHWORD.this);
    @Override
    public void onBackPressed() {

        epicDialog.setContentView(R.layout.box_dialog_exit);
        closebox=(ImageView)epicDialog.findViewById(R.id.bt_N_close2);
        btnCancel=(Button)epicDialog.findViewById(R.id.btn_exit_Now);


        closebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DASHWORD.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashword);


        epicDialog = new Dialog(this);





       mDrawerLayout=findViewById(R.id.DASHWORD);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView myNavigationView = findViewById(R.id.sqa);


//-----------------------------------------------------------------------------

        final ReportHome reportHome =new ReportHome();
        final MReport1 mReport1=new MReport1();
        final AccLogin accLogin=new AccLogin();
        final mreport_HR mreport_hr=new mreport_HR();
        final PDAHome mreport_PDA=new PDAHome();
       //final TabletHome1 mreport_Tablet=new TabletHome1();
        final SettingsDevice Settings=new SettingsDevice();
        setFragment(reportHome);
//------------------------------------------------------------------------------






        myNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    menuItem.setChecked(true);
                    int menu_id=menuItem.getItemId();

                    switch (menu_id)
                    {

                        case R.id.db1:
                            if(conRoyal.Access_Home_ID==1)
                            {

                                setFragment(reportHome);
                                break;
                            }
                            else
                            {
                                ShowAccessPopup();
                                break;
                            }
                        case R.id.db2:

                            if(conRoyal.Access_Pos_ID==1)
                            {
                                setFragment(mReport1);
                                break;
                            }
                            else
                            {
                                ShowAccessPopup();
                                break;
                            }

                        case R.id.db3:

                            if(conRoyal.Access_Inventory_ID==1)
                            {
                                setFragment(mReport1);
                                break;
                            }
                            else
                            {
                                ShowAccessPopup();
                                break;
                            }


                        case R.id.db4:

                            if(conRoyal.Access_Account_ID==1)
                            {
                                setFragment(mReport1);
                                break;
                            }
                            else
                            {
                                ShowAccessPopup();
                                break;
                            }


                        case R.id.db5:

                            if(conRoyal.Access_Emp_ID==1)
                            {
                                setFragment(mreport_hr);
                                break;
                            }
                            else
                            {
                                ShowAccessPopup();
                                break;
                            }

                        case R.id.db6:

                            ShowExitPopup();
                            break;
                        case R.id.db7:

                            if(conRoyal.Access_PDA==1)
                            {
                                setFragment(mreport_PDA);
                                break;
                            }
                            else
                            {
                                ShowAccessPopup();
                                break;
                            }
                        /*case R.id.db8:

                        if(conRoyal.Access_Tablet==1)
                        {
                            setFragment(mreport_Tablet);
                            break;
                        }
                        else
                        {
                            ShowAccessPopup();
                            break;
                        }*/
                        case R.id.db9:

                            if(conRoyal.Access_SettingsDevice==1)
                            {
                                setFragment(Settings);
                                break;
                            }
                            else
                            {
                                ShowAccessPopup();
                                break;
                            }
                        case R.id.db10:

                            if(conRoyal.Access_Data_migration==1)
                            {
                               // setFragment(Settings);
                                da.TransferDataToSqlServer(DASHWORD.this);
                                break;
                            }
                            else
                            {
                                ShowAccessPopup();
                                break;
                            }
                        case R.id.db11:

                            if(conRoyal.Access_Updatingdata==1)
                            {


                                da.UpdateAll(DASHWORD.this);
                               // setFragment(Settings);

                                break;
                            }
                            else
                            {
                                ShowAccessPopup();
                                break;
                            }
                    }
                    mDrawerLayout.closeDrawers();
                return true;
            }
        });

    };



    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame33,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(mToggle.onOptionsItemSelected(item))
        {

            return true;

        }

        return super.onOptionsItemSelected(item);
    }

//---------------- Box الصلاحية ----------------------------------//
    public void ShowAccessPopup()
    {
        epicDialog.setContentView(R.layout.box_dialog_access);
        closebox=(ImageView)epicDialog.findViewById(R.id.bt_N_close_access);
        btnCancel=(Button)epicDialog.findViewById(R.id.btn_Cancel_Now);


        closebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }
    //---------------- Box الصلاحية ----------------------------------//


    //---------------- Box تسجيل الخروج ----------------------------------//
    public void ShowExitPopup()
    {
        epicDialog.setContentView(R.layout.box_dialog_exit);
        closebox=(ImageView)epicDialog.findViewById(R.id.bt_N_close2);
        btnCancel=(Button)epicDialog.findViewById(R.id.btn_exit_Now);


        closebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DASHWORD.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }
    //---------------- Box تسجيل الخروج ----------------------------------//

    void r()
    {

        IntentIntegrator intentIntegrator=new IntentIntegrator(DASHWORD.this);
        intentIntegrator.setPrompt("For flash use volume up key");
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.setOrientationLocked(true);
        intentIntegrator.setCaptureActivity(Capture.class);
        intentIntegrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult.getContents()!=null)
        {
            final AlertDialog.Builder builder =new AlertDialog.Builder(DASHWORD.this);
            builder.setTitle("Result");
            builder.setMessage(intentResult.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();

        }
        else
        {
            Toast.makeText(getApplicationContext(),"OOPS",Toast.LENGTH_SHORT).show();
        }
    }
}
