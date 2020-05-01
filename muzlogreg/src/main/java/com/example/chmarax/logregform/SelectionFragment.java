package com.example.chmarax.logregform;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class SelectionFragment extends Fragment {



    public SelectionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_selection, container, false);
        Button qrcodescanner = (Button) v.findViewById(R.id.btQrcodeScanner);

        qrcodescanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                QrCodeScannerFragment fragment1 = new QrCodeScannerFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment1);
                fragmentTransaction.commit();


            }
        });

        return v;
    }

}