package com.example.royalsoftapk;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReportHome extends Fragment {

    PieChart pieChart;
    BarChart barChart;
    private TextView UserLogin,text_CashDolar,textCountEmp;
    private RequestQueue mQueue;
    private CardView cardEmp_online,CardOpen;
    Double TCashBalance=0.0;
    TextView TComapny_Name,TComapny_Phone;
    LinearLayout linearLayout;


    public ReportHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_report_home, container, false);

        text_CashDolar=view.findViewById(R.id.CashDolar);
        textCountEmp=view.findViewById(R.id.Count_Emp);
        cardEmp_online=view.findViewById(R.id.Emp_Online);
        CardOpen=view.findViewById(R.id.CashOpen);
        TComapny_Name=view.findViewById(R.id.Comapny_Name);
        TComapny_Phone=view.findViewById(R.id.Comapny_Phone);
        linearLayout=view.findViewById(R.id.idhomedash);

        CardOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), frm_Cash_drow.class);
                startActivity(intent);
            }
        });

        cardEmp_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),frm_Emp_Online.class);
                startActivity(intent);
            }
        });

        if(conRoyal.Access_Home_ID==1) {
            linearLayout.setVisibility(View.VISIBLE);
        }
        else
        {
            linearLayout.setVisibility(View.GONE);
        }



        UserLogin = view.findViewById(R.id.UserLogin);
        UserLogin.setText(conRoyal.UserName);


        pieChart=(PieChart) view.findViewById(R.id.piechart);



       // barChart=(BarChart) view.findViewById(R.id.Barchart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.99f);
        pieChart.setDrawHoleEnabled(false);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        ArrayList<PieEntry> yValues= new ArrayList<>();
        yValues.add(new PieEntry(30,getString(R.string.Sales)));
        yValues.add(new PieEntry(30,getString(R.string.Returns)));
        yValues.add(new PieEntry(30,getString(R.string.Purchases)));
        yValues.add(new PieEntry(30,getString(R.string.Daily)));
        yValues.add(new PieEntry(30,getString(R.string.monthly)));


        pieChart.animateY(1000, Easing.EaseInElastic);
       // pieChart.animateXY(1000,2000);

        PieDataSet dataSet=new PieDataSet(yValues,getString(R.string.My_stats));
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData data =new PieData((dataSet));
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);
        pieChart.setData(data);



   /* //------------------------- BarChart ---------------------------------------------------------//

        barChart.getDescription().setEnabled(false);


        ArrayList<BarEntry> yValues2= new ArrayList<>();

        yValues2.add(new BarEntry(15,14));
        yValues2.add(new BarEntry(10,12));
        yValues2.add(new BarEntry(30,30));
        yValues2.add(new BarEntry(30,13));
        yValues2.add(new BarEntry(10,70));
        yValues2.add(new BarEntry(10,100));



        BarDataSet set =new BarDataSet(yValues2,"Data set");
        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setDrawValues(true);

        BarData data1=new BarData(set);
        barChart.setData(data1);
        barChart.invalidate();
        barChart.animateY(500);





        barChart.setFitBars(true);

//------------------------- BarChart ---------------------------------------------------------//*/



        //------------GET API --------------------------------------------//
              Get_Cash_Dolar();
              Get_Count_EMP();
              Get_info_company();

        //------------GET API --------------------------------------------//







        return view;
    }


    public void Get_Cash_Dolar()
    {

        conRoyal.UrlRoyal_Cash_Dolar(conRoyal.ConnectionString);

        mQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Cash_Dolar, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for(int i =0 ; i < response.length() ; i++)
                    {
                        JSONObject jsonArray = response.getJSONObject(i);



                        Double CashBalance = jsonArray.getDouble("CashBalance");


                        TCashBalance += CashBalance;
                    }
                    DecimalFormat DF = new DecimalFormat("######0.000");
                    text_CashDolar.setText(DF.format(TCashBalance));
                    TCashBalance=0.0;


                } catch (JSONException e) {


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

    public void Get_Count_EMP()
    {

        conRoyal.UrlRoyal_SessionOpen(conRoyal.ConnectionString);

        mQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_SessionOpen, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {

                    JSONObject jsonArray = response.getJSONObject(0);



                    String SessionCount = jsonArray.getString("SessionCount");
                    textCountEmp.setText(SessionCount);



                } catch (JSONException e) {


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


    public void Get_info_company()
    {

        conRoyal.UrlRoyal_info_company(conRoyal.ConnectionString);

        mQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_info_company, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {


                        JSONObject jsonArray = response.getJSONObject(0);



                        String AName = jsonArray.getString("AName");
                        String Telephone1 = jsonArray.getString("Telephone1");

                    TComapny_Name.setText(AName);
                    TComapny_Phone.setText(Telephone1);



                } catch (JSONException e) {


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
