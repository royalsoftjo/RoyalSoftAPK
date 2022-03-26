package com.example.royalsoftapk;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.sdsmdg.harjot.vectormaster.VectorMasterView;
import com.sdsmdg.harjot.vectormaster.models.PathModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Login extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    CurvedBottomNavigationView bottomNavigationView;
    VectorMasterView fab, fab1, fab2 ,fab3;
    RelativeLayout lin_id;
    PathModel outline;
    Bitmap ss=null;
    Dialog epicDialog;
    ImageView closebox;
    Button btnAccept,btn_Update_Now;
    String TextTitle;
    String TextDesc;
    String Box_Image;
    String sss;
    //------------------------------------
    Button btnCancel;
    Animation topAnim, bottomAnim;

    //------------------------------------
    ViewPager viewPager;
    Adapter adapter;
    List<Model> models;
    Integer[] colors = null;
    ArgbEvaluator argbEvaluator = new ArgbEvaluator();



    //--------------------------------------------------

    private RequestQueue mQueue;

    ImageView imageView;


    @Override
    public void onBackPressed() {
        startActivity(new Intent(Login.this,Login.class));
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        epicDialog = new Dialog(this);




        bottomNavigationView = (CurvedBottomNavigationView) findViewById(R.id.bottom_nav);

        fab = (VectorMasterView) findViewById(R.id.fab);
        fab1 = (VectorMasterView) findViewById(R.id.fab1);
        fab2 = (VectorMasterView) findViewById(R.id.fab2);
        fab3 = (VectorMasterView) findViewById(R.id.fab3);

        lin_id = (RelativeLayout) findViewById(R.id.lin_id);
        bottomNavigationView.inflateMenu(R.menu.main_menu);

        //----------------------------------------------------------

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        //------------------------------------------------------------------------------------------------------------------------------


        Select_versionCode();







        getdata();




       /* Integer[] colors_temp = {
                getResources().getColor(R.color.color1),
                getResources().getColor(R.color.color2),
                getResources().getColor(R.color.color3),
                getResources().getColor(R.color.color4)
        };*/

        //colors = colors_temp;

       /* viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position < (adapter.getCount() -1) && position < (colors.length - 1)) {
                    viewPager.setBackgroundColor(

                            (Integer) argbEvaluator.evaluate(
                                    positionOffset,
                                    colors[position],
                                    colors[position + 1]
                            )
                    );
                }

                else {
                    viewPager.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/



        //-------------------------------------------------------------------------------------------------------------------------------

        //-------------------------------------------------------------

        bottomNavigationView.setOnNavigationItemSelectedListener(this);


        //recyclerView.setAnimation(topAnim);


        //---------------------------------------------------------






     }



   /* public void HH()
    {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter2(this,items,items2);
        recyclerView.setAdapter(adapter);
    }*/

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        AccLogin accLogin=new AccLogin();

        Contact contact = new Contact();
        switch (menuItem.getItemId())
        {


            case R.id.action_Home:
                Toast.makeText(this,"الرئيسية",Toast.LENGTH_SHORT).show();

                //Draw(2);
               // lin_id.setX(bottomNavigationView.mFirstCurveControlPoint1.x);
                fab.setVisibility(View.GONE);
                fab1.setVisibility(View.VISIBLE);
                fab2.setVisibility(View.GONE);
                fab3.setVisibility(View.GONE);
                //setFragment(homeData);
              //  drawAnimation(fab1);
                //Login login=new Login();
              //  login.sh
               // HH();

               Intent intent2=new Intent(Login.this,Login.class);
               startActivity(intent2);

                break;

            case R.id.action_location:
                Toast.makeText(this,"موقعنا",Toast.LENGTH_SHORT).show();

              //  Draw(6);
                lin_id.setX(bottomNavigationView.mFirstCurveControlPoint1.x);
                fab.setVisibility(View.GONE);
                fab1.setVisibility(View.GONE);
                fab2.setVisibility(View.VISIBLE);
                fab3.setVisibility(View.GONE);

                 Intent intent=new Intent(Login.this,Maps.class);
                 startActivity(intent);

            //    drawAnimation(fab2);
            break;



            case R.id.action_account:
                Toast.makeText(this,"حسابي",Toast.LENGTH_SHORT).show();

             //   Draw();
                lin_id.setX(bottomNavigationView.mFirstCurveControlPoint1.x);
                fab.setVisibility(View.VISIBLE);
                fab1.setVisibility(View.GONE);
                fab2.setVisibility(View.GONE);
                fab3.setVisibility(View.GONE);
               setFragment(accLogin);
               // drawAnimation(fab);



                break;


            case R.id.action_conte:
                Toast.makeText(this,"معلوماتنا",Toast.LENGTH_SHORT).show();

               // Draw();
                //lin_id.setX(bottomNavigationView.mFirstCurveControlPoint1.x);
                fab.setVisibility(View.GONE);
                fab1.setVisibility(View.GONE);
                fab2.setVisibility(View.GONE);
                fab3.setVisibility(View.VISIBLE);

                setFragment(contact);
                // drawAnimation(fab);



                break;

        }



        return true;
    }

    private void Draw() {
        bottomNavigationView.mFirstCurveStartPoint.set((bottomNavigationView.mNavigationBarWidth * 10/12)-(bottomNavigationView.CURVE_CIRCLE_RADIUS*2)-(bottomNavigationView.CURVE_CIRCLE_RADIUS/4),0);

        bottomNavigationView.mFirstCurveEndPoint.set(bottomNavigationView.mNavigationBarWidth* 10/12,bottomNavigationView.CURVE_CIRCLE_RADIUS+(bottomNavigationView.CURVE_CIRCLE_RADIUS/4));
        bottomNavigationView.mSecondCurveStartPoint = bottomNavigationView.mFirstCurveEndPoint;
        bottomNavigationView.mSecondCurveEndPoint.set((bottomNavigationView.mNavigationBarWidth*10/12)+(bottomNavigationView.CURVE_CIRCLE_RADIUS*2)+(bottomNavigationView.CURVE_CIRCLE_RADIUS/3),0);
        bottomNavigationView.mFirstCurveControlPoint1.set(bottomNavigationView.mFirstCurveStartPoint.x+bottomNavigationView.CURVE_CIRCLE_RADIUS + (bottomNavigationView.CURVE_CIRCLE_RADIUS/4),bottomNavigationView.mFirstCurveStartPoint.y);
        bottomNavigationView.mFirstCurveControlPoint2.set(bottomNavigationView.mFirstCurveEndPoint.x-(bottomNavigationView.CURVE_CIRCLE_RADIUS*2)+bottomNavigationView.CURVE_CIRCLE_RADIUS,bottomNavigationView.mFirstCurveEndPoint.y);

        bottomNavigationView.mSecondCurveControlPoint1.set(bottomNavigationView.mSecondCurveStartPoint.x+(bottomNavigationView.CURVE_CIRCLE_RADIUS*2)-bottomNavigationView.CURVE_CIRCLE_RADIUS,bottomNavigationView.mSecondCurveStartPoint.y);


        bottomNavigationView.mSecondCurveControlPoint2.set(bottomNavigationView.mSecondCurveEndPoint.x-(bottomNavigationView.CURVE_CIRCLE_RADIUS + (bottomNavigationView.CURVE_CIRCLE_RADIUS/4)),bottomNavigationView.mSecondCurveEndPoint.y);
    }

    private void drawAnimation(final VectorMasterView fab) {
        outline=fab.getPathModelByName("outline");
        outline.setStrokeColor(Color.WHITE);
        outline.setTrimPathEnd(0.0f);

        final ValueAnimator valueAnimator = ValueAnimator.ofFloat(0.0f,1.0f);
        valueAnimator.setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                outline.setTrimPathEnd((Float)valueAnimator.getAnimatedValue());
                fab.update();
            }
        });
        valueAnimator.start();
    }

    private void Draw(int i) {
        bottomNavigationView.mFirstCurveStartPoint.set(bottomNavigationView.mNavigationBarWidth/(i)-(bottomNavigationView.CURVE_CIRCLE_RADIUS*2)-(bottomNavigationView.CURVE_CIRCLE_RADIUS/2),2);

        bottomNavigationView.mFirstCurveEndPoint.set(bottomNavigationView.mNavigationBarWidth/i,bottomNavigationView.CURVE_CIRCLE_RADIUS+(bottomNavigationView.CURVE_CIRCLE_RADIUS/4));

        bottomNavigationView.mSecondCurveStartPoint = bottomNavigationView.mFirstCurveEndPoint;
        bottomNavigationView.mSecondCurveEndPoint.set((bottomNavigationView.mNavigationBarWidth/i)+(bottomNavigationView.CURVE_CIRCLE_RADIUS*2)+(bottomNavigationView.CURVE_CIRCLE_RADIUS/3),0);


        bottomNavigationView.mFirstCurveControlPoint1.set(bottomNavigationView.mFirstCurveStartPoint.x+bottomNavigationView.CURVE_CIRCLE_RADIUS + (bottomNavigationView.CURVE_CIRCLE_RADIUS/4),bottomNavigationView.mFirstCurveStartPoint.y);

        bottomNavigationView.mFirstCurveControlPoint2.set(bottomNavigationView.mFirstCurveEndPoint.x-(bottomNavigationView.CURVE_CIRCLE_RADIUS*2)+bottomNavigationView.CURVE_CIRCLE_RADIUS,bottomNavigationView.mFirstCurveEndPoint.y);

        bottomNavigationView.mSecondCurveControlPoint1.set(bottomNavigationView.mSecondCurveStartPoint.x+(bottomNavigationView.CURVE_CIRCLE_RADIUS*2)-bottomNavigationView.CURVE_CIRCLE_RADIUS,bottomNavigationView.mSecondCurveStartPoint.y);


        bottomNavigationView.mSecondCurveControlPoint2.set(bottomNavigationView.mSecondCurveEndPoint.x-(bottomNavigationView.CURVE_CIRCLE_RADIUS + (bottomNavigationView.CURVE_CIRCLE_RADIUS/3)),bottomNavigationView.mSecondCurveEndPoint.y);

    }

    private void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();
    }


    private void Select_versionCode()
    {

        conRoyal.UrlRoyal_VersionCode();

        mQueue = Volley.newRequestQueue(this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_VersionCode, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {

                    JSONObject jsonArray = response.getJSONObject(0);


                    conRoyal.A_versionCode = jsonArray.getString("versionCode");
                    conRoyal.Flag_compulsory = jsonArray.getString("Flag_compulsory");


                    if(!conRoyal.B_versionCode.equals(conRoyal.A_versionCode))
                    {
                        if(conRoyal.Flag_compulsory.equals("1"))
                        {
                            Show_Negative_Popup_Close();
                        }
                        else
                        {
                            Show_Negative_Popup();
                        }


                    }


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

//---------------- Box الصيانة ----------------------------------//
   /* public void ShowPostivePopup()
    {
        epicDialog.setContentView(R.layout.box_dialog_positive);
        closebox=(ImageView)epicDialog.findViewById(R.id.bt_P_close);
        btnAccept=(Button)epicDialog.findViewById(R.id.btn_yess);


        closebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });

        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();
    }
    //---------------- Box الصيانة ----------------------------------//*/

    //---------------- Box التحديثات ----------------------------------//
    public void Show_Negative_Popup()
    {
        epicDialog.setContentView(R.layout.box_dialog_negative);
        closebox=(ImageView)epicDialog.findViewById(R.id.bt_N_close);
        btn_Update_Now=(Button)epicDialog.findViewById(R.id.btn_Update_Now);


        closebox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                epicDialog.dismiss();
            }
        });


        btn_Update_Now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id="+"com.android.chrome")));
                }
                catch (ActivityNotFoundException e)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store/apps/details?id=1")));
                }
            }
        });


        epicDialog.setCancelable(false);
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();

    }
    //---------------- Box التحديثات ----------------------------------//


    //---------------- Box التحديثات ----------------------------------//
    public void Show_Negative_Popup_Close()
    {
        epicDialog.setContentView(R.layout.box_dialog_negative);
         closebox=(ImageView)epicDialog.findViewById(R.id.bt_N_close);
        btn_Update_Now=(Button)epicDialog.findViewById(R.id.btn_Update_Now);

        closebox.setVisibility(View.GONE);



        btn_Update_Now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id="+"com.android.chrome")));
                }
                catch (ActivityNotFoundException e)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store/apps/details?id=1")));
                }
            }
        });


        epicDialog.setCancelable(false);
        epicDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        epicDialog.show();

    }
    //---------------- Box التحديثات ----------------------------------//


  /*  @Override
    public void onBackPressed() {
        Toast.makeText(this,"hh",Toast.LENGTH_SHORT).show();

        super.onBackPressed();
    }

    @Override
    protected void onUserLeaveHint() {
        //finish();
        System.exit(1);
        super.onUserLeaveHint();
    }*/
















    private void getdata() {


        models = new ArrayList<>();


        conRoyal.UrlRoyal_Load_advertising();





        mQueue = Volley.newRequestQueue(this);
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, conRoyal.Url_Load_advertising, null, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(JSONArray response) {
                try {

                    for(int i=0 ; i<response.length();i++)
                    {
                        JSONObject jsonArray = response.getJSONObject(i);
                        TextTitle = jsonArray.getString("TextTitle");
                        TextDesc = jsonArray.getString("TextDesc");
                        Box_Image = conRoyal.ConIpImg+ jsonArray.getString("Box_Image");

                        models.add(new Model(Box_Image, TextTitle, TextDesc));
                    }




                    adapter = new Adapter(models, Login.this);
                    viewPager = findViewById(R.id.viewPager);
                    viewPager.setAdapter(adapter);
                    viewPager.setPadding(130, 0, 130, 0);


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
