package com.example.sqlitesallesmachines.ui.machines;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitesallesmachines.R;
import com.example.sqlitesallesmachines.adapter.MachineAdapter;
import com.example.sqlitesallesmachines.adapter.SalleAdapter;
import com.example.sqlitesallesmachines.bean.Machine;
import com.example.sqlitesallesmachines.bean.Salle;
import com.example.sqlitesallesmachines.databinding.FragmentMachineBinding;
import com.example.sqlitesallesmachines.databinding.FragmentSalleBinding;
import com.example.sqlitesallesmachines.service.MachineService;
import com.example.sqlitesallesmachines.service.SalleService;

import java.util.ArrayList;
import java.util.List;


public class MachineFragment extends Fragment {

    static public RecyclerView recyclerView;
    private Button add;
    static public MachineAdapter machineAdapter;
    static public MachineService machineService;
    static public SalleService salleService;
    SalleService db = null;
    View v;
    private FragmentSalleBinding binding;
    static ViewGroup view;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        view=(ViewGroup)inflater.inflate(R.layout.fragment_machine,container,false);
        machineService=new MachineService(getContext());
        salleService=new SalleService(getContext());
        add=view.findViewById(R.id.button1);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        machineAdapter = new MachineAdapter(machineService.findAll(), getContext());
        recyclerView.setAdapter(machineAdapter);
        machineAdapter.notifyDataSetChanged();
        machineService.findAll();


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View popup = LayoutInflater.from(view.getContext()).inflate(R.layout.ajout_machine, null,
                        false);

                EditText ref=popup.findViewById(R.id.ajouRef);
                EditText marque=popup.findViewById(R.id.ajouMarque);
                Spinner salleSpinner=popup.findViewById(R.id.spinner);
                //Chargement de Spinner
                ArrayAdapter<String> adapter;
                List<String> liste= new ArrayList<String>();
                for(Salle sal : salleService.findAll()) {
                    liste.add(sal.getCode());
                }
                adapter = new ArrayAdapter<String>(getContext(), R.layout.spinner_item, liste);
                adapter.setDropDownViewResource(R.layout.spinner_item);
                salleSpinner.setAdapter(adapter);
                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle(" ")
                        .setMessage("Entrz les donnees")
                        .setView(popup)
                        .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MachineService machineService = new MachineService(getContext());

                                Salle salle = salleService.findByCode(salleSpinner.getSelectedItem().toString());
                                machineService.add(new Machine(marque.getText().toString(), ref.getText().toString(), salle ));
                                Toast.makeText(getContext() ," Machine crée avec succès :) ", Toast.LENGTH_LONG).show();
                                marque.setText("");
                                ref.setText("");
                                load();
                            }
                        })
                        .setNegativeButton("Annuler", null)
                        .create();
                dialog.show();
            }
        });


        return view;
    }
    public static void load(){
        machineAdapter=new MachineAdapter(machineService.findAll(),view.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(machineAdapter);
        machineAdapter.notifyDataSetChanged();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}