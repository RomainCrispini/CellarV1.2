package com.romain.cellarv1.vue;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.romain.cellarv1.R;
import com.romain.cellarv1.modele.AccesLocal;
import com.romain.cellarv1.modele.WineBottle;
import com.romain.cellarv1.outils.MyAdapterCellarListView;
import com.romain.cellarv1.outils.MyAdapterCellarRecyclerView;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CellarStatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CellarStatsFragment extends Fragment {

    private AccesLocal accesLocal;

    // Interpolator pour animation des menuBis
    private OvershootInterpolator interpolator = new OvershootInterpolator();

    // Tableaux
    private PieChart pieChart;

    // TextViews
    private TextView txtTotalNumber;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CellarStatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CellarStatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CellarStatsFragment newInstance(String param1, String param2) {
        CellarStatsFragment fragment = new CellarStatsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View cellarStatsFragment = inflater.inflate(R.layout.fragment_cellar_stats, container, false);
        //pieChart = (PieChart) cellarStatsFragment.findViewById(R.id.pieChart);

        //txtTotalNumber = (TextView) cellarStatsFragment.findViewById(R.id.txtTotalNumber);



        //loadTotalNumber();
        //loadWineColorPieChart();




        return cellarStatsFragment;

    }

    private void loadTotalNumber() {
        accesLocal = new AccesLocal(getContext());
        Integer nbTotalBottle = accesLocal.nbTotal();

        txtTotalNumber.setText(nbTotalBottle.toString());
    }

    private void loadWineColorPieChart() {

        pieChart.setUsePercentValues(false);

        // Caractéristiques du message s'il n'y a pas de données
        pieChart.setNoDataText("Il manque quelques bouteilles pour éditer des statistiques !");
        //pieChart.invalidate();
        Paint p = pieChart.getPaint(PieChart.PAINT_INFO);
        p.setTextSize(15f);
        p.setColor(Color.parseColor("#8DB3C5"));

        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setHoleRadius(70f);
        pieChart.setTransparentCircleRadius(0f);
        //pieChart.getLegend().setEnabled(false);

        accesLocal = new AccesLocal(getContext());
        //ArrayList<WineBottle> wineBottleArrayList = (ArrayList<WineBottle>) accesLocal.recoverWineBottleList();

        Integer nbRed = accesLocal.nbRed();
        Integer nbRose = accesLocal.nbRose();
        Integer nbWhite = accesLocal.nbWhite();
        Integer nbChamp = accesLocal.nbChamp();

        // Modifie le nombre de couleurs du pie suivant les celle des bouteilles
        ArrayList<Integer> COLORS = new ArrayList<>();
        ArrayList<PieEntry> values = new ArrayList<>();



        if(nbRed > 0) {
            values.add(new PieEntry(nbRed, "Rouge"));
            COLORS.add(Color.rgb(159, 6, 52)); // Rouge
        } else {
            values.add(new PieEntry(0, "Rouge"));
            COLORS.add(Color.rgb(159, 6, 52)); // Rouge
        }

        if(nbRose > 0) {
            values.add(new PieEntry(nbRose, "Rosé"));
            COLORS.add(Color.rgb(249, 175, 164)); // Rosé
        } else {
            values.add(new PieEntry(0, "Rosé"));
            COLORS.add(Color.rgb(249, 175, 164)); // Rosé
        }

        if(nbWhite > 0) {
            values.add(new PieEntry(nbWhite, "Blanc"));
            COLORS.add(Color.rgb(254, 207, 29)); // Blanc
        } else {
            values.add(new PieEntry(0, "Blanc"));
            COLORS.add(Color.rgb(254, 207, 29)); // Blanc
        }

        if(nbChamp > 0) {
            values.add(new PieEntry(nbChamp, "Effervescent"));
            COLORS.add(Color.rgb(222, 203, 135)); // Effervescent
        } else {
            values.add(new PieEntry(0, "Effervescent"));
            COLORS.add(Color.rgb(222, 203, 135)); // Effervescent
        }









        pieChart.setDrawEntryLabels(false);
        //pieChart.setEntryLabelTextSize(4f);
        //pieChart.setEntryLabelColor(Color.parseColor("#2F3B40"));

        Legend legend = pieChart.getLegend();
        legend.setTextColor(Color.parseColor("#8DB3C5"));
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        PieDataSet dataSet = new PieDataSet(values, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(COLORS);
        //dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        pieChart.animateXY(0, 2000);


        PieData data = new PieData(dataSet);

        data.setValueTextSize(15f);
        data.setValueFormatter(new MyPieChartValueFormatter());
        data.setValueTextColor(Color.parseColor("#2F3B40"));

        pieChart.setData(data);
        pieChart.notifyDataSetChanged();

    }

    public class MyPieChartValueFormatter extends ValueFormatter {

        private DecimalFormat mFormat;

        public MyPieChartValueFormatter() {
            mFormat = new DecimalFormat("#");
        }

        @Override
        public String getFormattedValue(float value) {
            return mFormat.format(value);
        }
    }

}
