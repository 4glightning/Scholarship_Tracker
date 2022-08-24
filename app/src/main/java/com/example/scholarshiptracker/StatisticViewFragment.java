package com.example.scholarshiptracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class StatisticViewFragment extends Fragment {

    BarChart monthlyIncomeBarchart;
    ArrayList<BarEntry> barEntryArrayList;
    ArrayList<String> labelsName;
    ArrayList<WeeklyIncomeChart> monthlyIncomeChart = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_view_statistic, container, false);

        getActivity().setTitle("Statistic View");

        //Weekly Income Barchart
        monthlyIncomeBarchart = v.findViewById(R.id.monthlyIncomeBarchart);

        barEntryArrayList = new ArrayList<>();
        labelsName = new ArrayList<>();
        fillMonthlyIncome();
        for(int i = 0; i < monthlyIncomeChart.size(); i ++){
            String month = monthlyIncomeChart.get(i).getDays();
            int income = monthlyIncomeChart.get(i).getIncome();
            barEntryArrayList.add(new BarEntry(i, income));
            labelsName.add(month);
        }

        BarDataSet barDataSet = new BarDataSet(barEntryArrayList, "Month");
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        Description description = new Description();
        description.setText("Monthly Income");
        monthlyIncomeBarchart.setDescription(description);
        BarData barData = new BarData(barDataSet);
        monthlyIncomeBarchart.setData(barData);

        //set XAxis values formater
        XAxis xAxis = monthlyIncomeBarchart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labelsName));

        //set position of labels(Days name)
        xAxis.setPosition(XAxis.XAxisPosition.TOP);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(labelsName.size());
        xAxis.setLabelRotationAngle(270);
        monthlyIncomeBarchart.animateY(2000);
        monthlyIncomeBarchart.invalidate();

        return v;
    }

    private void fillMonthlyIncome(){
        monthlyIncomeChart.clear();
        monthlyIncomeChart.add(new WeeklyIncomeChart("Jan", 400));
        monthlyIncomeChart.add(new WeeklyIncomeChart("Feb", 450));
        monthlyIncomeChart.add(new WeeklyIncomeChart("Mar", 350));
        monthlyIncomeChart.add(new WeeklyIncomeChart("Apr", 300));
        monthlyIncomeChart.add(new WeeklyIncomeChart("May", 500));
        monthlyIncomeChart.add(new WeeklyIncomeChart("June", 150));
        monthlyIncomeChart.add(new WeeklyIncomeChart("July", 0));
        monthlyIncomeChart.add(new WeeklyIncomeChart("Aug", 0));
        monthlyIncomeChart.add(new WeeklyIncomeChart("Sep", 0));
        monthlyIncomeChart.add(new WeeklyIncomeChart("Oct", 0));
        monthlyIncomeChart.add(new WeeklyIncomeChart("Nov", 0));
        monthlyIncomeChart.add(new WeeklyIncomeChart("Dec", 0));
    }

}