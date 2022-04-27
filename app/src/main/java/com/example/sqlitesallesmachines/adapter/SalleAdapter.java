package com.example.sqlitesallesmachines.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitesallesmachines.R;
import com.example.sqlitesallesmachines.bean.Salle;
import com.example.sqlitesallesmachines.service.SalleService;
import com.example.sqlitesallesmachines.ui.salle.SalleFragment;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by leblond on 16/11/2015.
 */
public class SalleAdapter  extends RecyclerView.Adapter<SalleAdapter.MyViewHolder>{

    List<Salle> data;
    Context context;
    private SalleService salleService;
    public SalleAdapter(List<Salle> data, Context context) {
        this.data = data;
        this.context = context;
        salleService=new SalleService(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.salle_item, parent, false);
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
                        AlertDialog.Builder alertD = new AlertDialog.Builder(context);
                        alertD.setMessage("Vous etes sur de le supprimer :");
                        alertD.setPositiveButton("OUI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int ids=Integer.parseInt(((TextView)view.findViewById(R.id.id)).getText().toString());
                                salleService.delete(salleService.findById(ids));
                                SalleFragment.load();
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
                        View popup = LayoutInflater.from(context).inflate(R.layout.update_salle, null,
                                false);

                        TextView id = popup.findViewById(R.id.id);
                        EditText code=popup.findViewById(R.id.popCode);
                        EditText libelle=popup.findViewById(R.id.popLibelle);
                        int ids=Integer.parseInt(((TextView)view.findViewById(R.id.id)).getText().toString());
                        //Toast.makeText(context, ((TextView)view.findViewById(R.id.code)).getText(), Toast.LENGTH_SHORT).show();
                        id.setText(((TextView)view.findViewById(R.id.id)).getText());
                        code.setText(salleService.findById(ids).getCode().toString());
                        libelle.setText(salleService.findById(ids).getLibelle().toString());
                        AlertDialog dialog1 = new AlertDialog.Builder(context)
                                .setTitle(" ")
                                .setMessage("Modifier les donnees:")
                                .setView(popup)
                                .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int ids = Integer.parseInt(id.getText().toString());
                                        String c=code.getText().toString();
                                        String l=libelle.getText().toString();
                                        Salle s = salleService.findById(ids);
                                        s.setCode(c);
                                        s.setLibelle(l);
                                        salleService.update(s);
                                        SalleFragment.load();
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
        return new MyViewHolder(view);
    }
    public void filterList(ArrayList<Salle> filterContact) {
        data = filterContact;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.id.setText(data.get(position).getId()+"");
        holder.code.setText("Code : "+data.get(position).getCode());
        holder.liblle.setText( "Libelle : "+data.get(position).getLibelle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id, code, liblle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            this.id = (TextView) itemView.findViewById(R.id.id);
            this.code = (TextView) itemView.findViewById(R.id.code);
            this.liblle = (TextView) itemView.findViewById(R.id.libelle);
        }
    }
}
