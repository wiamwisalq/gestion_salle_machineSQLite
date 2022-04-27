package com.example.sqlitesallesmachines.ui.SalleAjout;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sqlitesallesmachines.R;

public class salleAjou extends Fragment {

    private SalleAjouViewModel mViewModel;

    public static salleAjou newInstance() {
        return new salleAjou();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.salle_ajou_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SalleAjouViewModel.class);
        // TODO: Use the ViewModel
    }

}