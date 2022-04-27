package com.example.sqlitesallesmachines.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sqlitesallesmachines.R;
import com.example.sqlitesallesmachines.bean.ResultatStatique;
import com.example.sqlitesallesmachines.bean.Salle;
import com.example.sqlitesallesmachines.databinding.FragmentHomeBinding;
import com.example.sqlitesallesmachines.service.MachineService;
import com.example.sqlitesallesmachines.service.SalleService;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class HomeFragment extends Fragment {
    Button b;
    SalleService ms;
    private static final int MAX_X_VALUE = 7;
    private static final int MAX_Y_VALUE = 50;
    private static final int MIN_Y_VALUE = 5;
    private static final String SET_LABEL = "Machine par salle";
    private String[] DAYS;
    private FragmentHomeBinding binding;
    ViewGroup view;
    BarChart barChart;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        view=(ViewGroup)inflater.inflate(R.layout.fragment_home,container,false);
        ms=new SalleService(getContext());
        barChart=(BarChart) view.findViewById(R.id.bar_chart);




        DAYS=new String[ms.getStatiqueRes().size()];
        for(int i=0 ; i< ms.getStatiqueRes().size();i++){
            DAYS[i]=ms.findById(ms.getStatiqueRes().get(i).getSalle()).getCode();
        }
//        b=(Button) view.findViewById(R.id.button2);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Log.d("wiam",DAYS[0]+""+DAYS[1]);
//                Toast.makeText(getContext(), DAYS+"", Toast.LENGTH_SHORT).show();
//            }
//        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        BarData data = createChartData();
        configureChartAppearance();
        prepareChartData(data);
    }

    private void prepareChartData(BarData data) {
        data.setValueTextSize(12f);
        barChart.setData(data);
        barChart.invalidate();
    }
    private BarData createChartData() {
        Random r = new Random();
        ArrayList<BarEntry> values = new ArrayList<>();
        for (int i = 0; i < DAYS.length; i++) {
            float x = i;
            float y = ms.getStatiqueRes().get(i).getNbr(); //nbr machine
            values.add(new BarEntry(x, y));
        }

        BarDataSet set1 = new BarDataSet(values, SET_LABEL);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);

        return data;
    }
    private void configureChartAppearance() {
        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {

                return DAYS[(int) value];
            }
        });

        YAxis axisLeft = barChart.getAxisLeft();
        axisLeft.setGranularity(10f);
        axisLeft.setAxisMinimum(0);

        YAxis axisRight = barChart.getAxisRight();
        axisRight.setGranularity(10f);
        axisRight.setAxisMinimum(0);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}