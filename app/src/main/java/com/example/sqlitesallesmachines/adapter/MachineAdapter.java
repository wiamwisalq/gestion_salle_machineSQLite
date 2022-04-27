package com.example.sqlitesallesmachines.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitesallesmachines.R;
import com.example.sqlitesallesmachines.bean.Machine;
import com.example.sqlitesallesmachines.bean.Salle;
import com.example.sqlitesallesmachines.service.MachineService;
import com.example.sqlitesallesmachines.service.SalleService;
import com.example.sqlitesallesmachines.ui.machines.MachineFragment;
import com.example.sqlitesallesmachines.ui.salle.SalleFragment;

import java.util.ArrayList;
import java.util.List;




/**
 * Created by PC-PRO on 19/11/2016.
 */

public class MachineAdapter extends RecyclerView.Adapter<MachineAdapter.MyViewHolder>{

    List<Machine> data;
    Context context;

    public MachineAdapter(List<Machine> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public MachineAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.machine_item, parent, false);
        final MyViewHolder holder=new MyViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Veuillez choisir une option :");

                alertDialogBuilder.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MachineService machineService=new MachineService(context);
                        SalleService salleService=new SalleService(context);
                        AlertDialog.Builder alertD = new AlertDialog.Builder(context);
                        alertD.setMessage("Vous etes sur de le supprimer :");
                        alertD.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int idm=Integer.parseInt(((TextView)view.findViewById(R.id.idm)).getText().toString());
                                machineService.delete(machineService.findById(idm));
                                MachineFragment.load();
                            }
                        });
                        alertD.setNegativeButton("NON", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog alertDial = alertD.create();
                        alertDial.show();
                    }
                });
                alertDialogBuilder.setNegativeButton("Modifier", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        View popup = LayoutInflater.from(context).inflate(R.layout.update_machine, null,
                                false);

                        TextView idm = popup.findViewById(R.id.idm);
                        EditText ref=popup.findViewById(R.id.modRef);
                        EditText marque=popup.findViewById(R.id.modMarque);
                        Spinner salleSpinner=popup.findViewById(R.id.spinner);
                        int id=Integer.parseInt(((TextView)view.findViewById(R.id.idm)).getText().toString());
                        int ids=Integer.parseInt(((TextView)view.findViewById(R.id.ids)).getText().toString());
                        //Toast.makeText(context, ids+"", Toast.LENGTH_SHORT).show();
                        MachineService machineService=new MachineService(context);
                        SalleService salleService=new SalleService(context);
                        //Chargement de Spinner
                        ArrayAdapter<String> adapter;
                        List<String> liste= new ArrayList<String>();
                        for(Salle sal : salleService.findAll()) {
                            liste.add(sal.getCode());
                        }
                        adapter = new ArrayAdapter<String>(context, R.layout.spinner_item, liste);
                        adapter.setDropDownViewResource(R.layout.spinner_item);
                        salleSpinner.setAdapter(adapter);

                        idm.setText(((TextView)view.findViewById(R.id.idm)).getText());
                        ref.setText(machineService.findById(id).getRefernce());
                        marque.setText(machineService.findById(id).getMarque());
                        //Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                        salleSpinner.setSelection(getIndex(salleSpinner,salleService.findById(ids).getCode()));

                        AlertDialog dialog1 = new AlertDialog.Builder(context)
                                .setTitle(" ")
                                .setMessage("Modifier les donnees:")
                                .setView(popup)
                                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int idt = Integer.parseInt(idm.getText().toString());
                                        String refs= ref.getText().toString();
                                        String marques=marque.getText().toString();
                                        Salle s=salleService.findByCode(salleSpinner.getSelectedItem().toString());
                                        Machine m=machineService.findById(idt);
                                        m.setRefernce(refs);
                                        m.setSalle(s);
                                        m.setMarque(marques);
                                        machineService.update(m);
                                        MachineFragment.load();
                                    }
                                })
                                .setNegativeButton("Annuler", null)
                                .create();
                        dialog1.show();

                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        return new MachineAdapter.MyViewHolder(view);
    }

    private int getIndex(Spinner salleSpinner, String s) {
        for(int i=0;i<salleSpinner.getCount();i++){
            if(salleSpinner.getItemAtPosition(i).toString().equalsIgnoreCase(s)){
                return i;
            }
        }
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id.setText(data.get(position).getId()+"");
        holder.marque.setText("Marque: "+data.get(position).getMarque());
        holder.ref.setText("Reference: "+data.get(position).getRefernce());
        holder.salle.setText("Salle: "+data.get(position).getSalle().getCode()+"");
        holder.ids.setText(data.get(position).getSalle().getId()+"");
    }

    public void filterList(ArrayList<Machine> filterContact) {
        data = filterContact;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id, marque, ref,salle ,ids;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.id = (TextView) itemView.findViewById(R.id.idm);
            this.marque = (TextView) itemView.findViewById(R.id.marque);
            this.ref = (TextView) itemView.findViewById(R.id.reference);
            this.salle = (TextView) itemView.findViewById(R.id.salle);
            this.ids=(TextView) itemView.findViewById(R.id.ids);
        }
    }
}
