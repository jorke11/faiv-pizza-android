package com.jorgepinedo.faivpizza.Contents;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jorgepinedo.faivpizza.MainActivity;
import com.jorgepinedo.faivpizza.R;

public class OneFragment extends Fragment{

    public OneFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_one, container, false);
        CardView card_init = view.findViewById(R.id.card_init);

        card_init.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).hideInformation(false);
                ((MainActivity)getActivity()).chageFragment(new MasaFragment());
                ((MainActivity)getActivity()).enableBtnsTwo();
            }
        });


        ((MainActivity)getActivity()).enableBtnsOne();

        ((MainActivity)getActivity()).hideInformation(true);

        return view;
    }


}
