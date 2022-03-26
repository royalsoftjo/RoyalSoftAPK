package com.example.royalsoftapk;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFeedBack extends AppCompatActivity {

    Button btnsavefeed;
    EditText txtAname,txtPhone,txtNote;
    RatingBar rt1,rt2,rt3;
    RadioButton rdyes,rdno;
    private RequestQueue mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_feed_back);
        btnsavefeed=findViewById(R.id.btnsavefeed);
        txtAname=findViewById(R.id.txtAname);
        txtPhone=findViewById(R.id.txtPhone);
        rt1=findViewById(R.id.rtstar1);
        rt2=findViewById(R.id.rtstar2);
        rt3=findViewById(R.id.rtstar3);
        rdyes=findViewById(R.id.rdyes);
        rdno=findViewById(R.id.rdno);
        txtNote=findViewById(R.id.txtNott);
        btnsavefeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }
        });
    }
    void Refresh()
    {
        txtAname.setText("");
        txtNote.setText("");
        txtPhone.setText("");
        rdyes.setChecked(true);
        rdno.setChecked(false);
        rt1.setRating(0);
        rt2.setRating(0);
        rt3.setRating(0);
    }
    boolean Vald()
    {
        if(txtAname.getText().toString().equals(""))
        {
            Toast.makeText(HomeFeedBack.this,"يرجى اختيار الاسم",Toast.LENGTH_SHORT).show();
            return false;
        }
        if(txtPhone.getText().toString().equals(""))
        {
            Toast.makeText(HomeFeedBack.this,"يرجى اختيار رقم الهاتف",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    void Save()
    {
        if(Vald())
        {
            getdata();
        }
    }
    public  void ShowDataSaved(Context c, String Header, String Details)
    {
        final Dialog epicDialog = new Dialog(c);
        ImageView closebox;
        Button btnCancel;
        TextView textHeader,textDetails;

        epicDialog.setContentView(R.layout.from_failog);
        epicDialog.setCancelable(false);
        textHeader=(TextView) epicDialog.findViewById(R.id.txtHeaderDailog);
        textDetails=(TextView)epicDialog.findViewById(R.id.txtBodyDailog);
        closebox=(ImageView)epicDialog.findViewById(R.id.bt_N_close_access);
        btnCancel=(Button)epicDialog.findViewById(R.id.btn_Cancel_Now);

        textHeader.setText(Header);
        textDetails.setText(Details);
        closebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
                Refresh();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
                Refresh();
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }
    private void getdata() {

        String Aname=txtAname.getText().toString();
        String Number=txtPhone.getText().toString();
        String Note=txtNote.getText().toString();

        boolean way=false;
        if(rdyes.isChecked())
        {
            way=true;
        }
        else
        {
            way=false;
        }
        conRoyal.UrlRoyal_feedBackSave(conRoyal.ConnectionString,Aname,Number,(int)rt1.getRating(),(int)rt2.getRating(),(int)rt3.getRating(),way,Note);
        mQueue = Volley.newRequestQueue(this);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,conRoyal.Url_feedBackSave, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(0);
                                Boolean Status = obj.getBoolean("Status");
                                if(Status)
                                {
                                    ShowDataSaved(HomeFeedBack.this,getString(R.string.txtCorrectoperation),getString(R.string.txt_feedback_sent));
                                }
                                else
                                {
                                    Toast.makeText(HomeFeedBack.this,"لم يتم الحفظ ، يرجى إعادة المحاولة",Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
}
