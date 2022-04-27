package com.example.sqlitesallesmachines.ui.salle;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitesallesmachines.R;
import com.example.sqlitesallesmachines.adapter.SalleAdapter;
import com.example.sqlitesallesmachines.bean.Salle;
import com.example.sqlitesallesmachines.databinding.FragmentSalleBinding;
import com.example.sqlitesallesmachines.service.MachineService;
import com.example.sqlitesallesmachines.service.SalleService;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class SalleFragment extends Fragment {

    static public RecyclerView recyclerView;
    private EditText code;
    private EditText libelle;
    private Button add;
    static public SalleAdapter salleAdapter;
    static public SalleService salleService;
    MachineService ms;

    View v;
    private FragmentSalleBinding binding;
    static ViewGroup view;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

         view=(ViewGroup)inflater.inflate(R.layout.fragment_salle,container,false);
        ms=new MachineService(getContext());
        binding = FragmentSalleBinding.inflate(inflater, container, false);

        code=view.findViewById(R.id.code);
        libelle=view.findViewById(R.id.libelle);
        add=view.findViewById(R.id.button);
        recyclerView=view.findViewById(R.id.recycle_view);
        salleService=new SalleService(getContext());
        salleAdapter=new SalleAdapter(salleService.findAll(),getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(salleAdapter);
        salleAdapter.notifyDataSetChanged();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                View popup = LayoutInflater.from(view.getContext()).inflate(R.layout.ajouter_salle, null,
                        false);

                EditText code=popup.findViewById(R.id.ajouCode);
                EditText libelle=popup.findViewById(R.id.ajouLibelle);

                AlertDialog dialog = new AlertDialog.Builder(getContext())
                        .setTitle(" ")
                        .setMessage("Entrz les donnees")
                        .setView(popup)
                        .setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String c=code.getText().toString();
                                String l=libelle.getText().toString();
                                Salle s = new Salle(c,l);
                                salleService.add(s);
                               load();
                            }
                        })
                        .setNegativeButton("Annuler", null)
                        .create();
                dialog.show();
            }
        });
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static void load(){
        salleAdapter=new SalleAdapter(salleService.findAll(),view.getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(salleAdapter);
        salleAdapter.notifyDataSetChanged();
    }
    ItemTouchHelper.SimpleCallback simpleCallback= new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |
            ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        Salle salleSupprimer=null;
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position= viewHolder.getAbsoluteAdapterPosition();

            switch (direction){
                case ItemTouchHelper.LEFT:
                    View popup = LayoutInflater.from(view.getContext()).inflate(R.layout.update_salle, null,
                            false);

                    TextView id = popup.findViewById(R.id.id);
                    TextView codet = popup.findViewById(R.id.code);
                    TextView libellet = popup.findViewById(R.id.libelle);
                    EditText code=popup.findViewById(R.id.popCode);
                    EditText libelle=popup.findViewById(R.id.popLibelle);


                    id.setText(salleService.findById(position+1).getId()+"");
                    code.setText(salleService.findById(position+1).getCode());
                    libelle.setText(salleService.findById(position+1).getLibelle());
                    AlertDialog dialog = new AlertDialog.Builder(getContext())
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
                                    salleAdapter=new SalleAdapter(salleService.findAll(),getContext());
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                                    recyclerView.setAdapter(salleAdapter);
                                    salleAdapter.notifyDataSetChanged();

                                }
                            })
                            .setNegativeButton("Annuler", null)
                            .create();
                    dialog.show();
                    break;
                case ItemTouchHelper.RIGHT:

                    Toast.makeText(getContext(), position+"", Toast.LENGTH_SHORT).show();
                    salleSupprimer=salleService.findById(position);
                    Toast.makeText(getContext(), salleService.findById(position).getCode(), Toast.LENGTH_SHORT).show();
                    //salleService.delete(salleService.findById(position+1));
                    salleAdapter=new SalleAdapter(salleService.findAll(),getContext());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerView.setAdapter(salleAdapter);
                    salleAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

}