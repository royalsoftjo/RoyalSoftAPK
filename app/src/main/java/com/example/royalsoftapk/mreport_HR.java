package com.example.royalsoftapk;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class mreport_HR extends Fragment {
CardView Card_Working;

    public mreport_HR() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =inflater.inflate(R.layout.fragment_mreport__hr, container, false);


        Card_Working=view.findViewById(R.id.Card_Working);

        Card_Working.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),frm_Working_Hours.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
