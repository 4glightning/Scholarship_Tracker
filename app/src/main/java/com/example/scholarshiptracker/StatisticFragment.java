package com.example.scholarshiptracker;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class StatisticFragment extends Fragment {

    FirebaseAuth fAuth;
    FirebaseFirestore db;
    String userID;

    LineChart lineChart;
    LineData lineData;
    LineDataSet lineDataSet;
    ArrayList lineEntries;

    BarChart weeklyIncomeBarchart;
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<String> labelsName;
    ArrayList<WeeklyIncomeChart> weeklyIncomeCharts = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_statistic, container, false);

        getActivity().setTitle("Statistic");

        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        lineChart = v.findViewById(R.id.lineChart);
        getEntries();
        lineDataSet = new LineDataSet(lineEntries, "Daily Spending");
        lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);
        lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        lineDataSet.setValueTextColor(Color.BLACK);
        lineDataSet.setValueTextSize(18f);
        lineDataSet.setLineWidth(5);

        //Weekly Income Barchart
        weeklyIncomeBarchart = v.findViewById(R.id.weeklyIncomeBarchart);

        barEntryArrayList = new ArrayList<>();
        labelsName = new ArrayList<>();
        fillWeeklyIncome();
        for(int i = 0; i < weeklyIncomeCharts.size(); i ++){
            String days = weeklyIncomeCharts.get(i).getDays();
            int income = weeklyIncomeCharts.get(i).getIncome();
            barEntryArrayList.add(new BarEntry(i, income));
            labelsName.add(days);
        }

        BarDataSet barDataSet = new BarDataSet(barEntryArrayList, "Days");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        Description description = new Description();
        description.setText("Weekly Income");
        weeklyIncomeBarchart.setDescription(description);
        BarData barData = new BarData(barDataSet);
        weeklyIncomeBarchart.setData(barData);

        //set XAxis values formater
        XAxis xAxis = weeklyIncomeBarchart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsName));

        //set position of labels(Days name)
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelsName.size());
        xAxis.setLabelRotationAngle(270);
        weeklyIncomeBarchart.animateY(2000);
        weeklyIncomeBarchart.invalidate();

        return v;
    }

    private void getEntries() {
        lineEntries = new ArrayList<>();
        lineEntries.add(new Entry(1f, 0));
        lineEntries.add(new Entry(2f, 10));
        lineEntries.add(new Entry(3f, 5));
        lineEntries.add(new Entry(4f, 13));
        lineEntries.add(new Entry(5f, 10));
        lineEntries.add(new Entry(6f, 15));
    }

    private void fillWeeklyIncome(){
        weeklyIncomeCharts.clear();
        weeklyIncomeCharts.add(new WeeklyIncomeChart("Sunday", 53));
        weeklyIncomeCharts.add(new WeeklyIncomeChart("Monday", 30));
        weeklyIncomeCharts.add(new WeeklyIncomeChart("Tuesday", 15));
        weeklyIncomeCharts.add(new WeeklyIncomeChart("Wednesday", 30));
        weeklyIncomeCharts.add(new WeeklyIncomeChart("Thursday", 16));
        weeklyIncomeCharts.add(new WeeklyIncomeChart("Friday", 35));
        weeklyIncomeCharts.add(new WeeklyIncomeChart("Saturday", 45));
    }

}
