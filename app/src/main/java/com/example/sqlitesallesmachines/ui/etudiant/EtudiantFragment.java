package com.example.sqlitesallesmachines.ui.etudiant;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sqlitesallesmachines.R;

public class EtudiantFragment extends Fragment {

    private EtudiantViewModel mViewModel;
    ViewGroup view;
    public static EtudiantFragment newInstance() {
        return new EtudiantFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view=(ViewGroup)inflater.inflate(R.layout.etudiant_fragment,container,false);
        return inflater.inflate(R.layout.etudiant_fragment, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}