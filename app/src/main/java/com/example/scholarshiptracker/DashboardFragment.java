package com.example.scholarshiptracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


public class DashboardFragment extends Fragment implements View.OnClickListener {

    private View mView;
    private CardView applyScholarship, appliedScholarship, income, expenditure, budget, statistic;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Scholarship Tracker");

        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        applyScholarship = mView.findViewById(R.id.applyScholarship);
        appliedScholarship = mView.findViewById(R.id.appliedScholarship);
        income = mView.findViewById(R.id.income);
        expenditure = mView.findViewById(R.id.expenditure);
        budget = mView.findViewById(R.id.budget);
        statistic = mView.findViewById(R.id.statistic);

        applyScholarship.setOnClickListener(this);
        appliedScholarship.setOnClickListener(this);
        income.setOnClickListener(this);
        expenditure.setOnClickListener(this);
        budget.setOnClickListener(this);
        statistic.setOnClickListener(this);

        return mView;
    }

    //method to open fragment and activity upon clicking on card view

        @Override
        public void onClick (View v){

            switch (v.getId()) {
                case R.id.applyScholarship:
                    ScholarshipFragment scholarshipFragment = new ScholarshipFragment();
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
                    transaction.replace(R.id.fragment_container, scholarshipFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;
                case R.id.appliedScholarship:
                    AlreadyAppliedScholarshipFragment alreadyAppliedScholarshipFragment = new AlreadyAppliedScholarshipFragment();
                    transaction = getParentFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
                    transaction.replace(R.id.fragment_container, alreadyAppliedScholarshipFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;
                case R.id.income:
                    IncomeList incomeList = new IncomeList();
                    transaction = getParentFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
                    transaction.replace(R.id.fragment_container, incomeList);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;
                case R.id.expenditure:
                    ExpendList expendList = new ExpendList();
                    transaction = getParentFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
                    transaction.replace(R.id.fragment_container, expendList);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;
                case R.id.budget:
                    BudgetInputFragment budgetInputFragment = new BudgetInputFragment();
                    transaction = getParentFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
                    transaction.replace(R.id.fragment_container, budgetInputFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;
                case R.id.statistic:
                    StatisticViewFragment statisticViewFragment = new StatisticViewFragment();
                    transaction = getParentFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
                    transaction.replace(R.id.fragment_container, statisticViewFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    break;
                default:
                    break;
            }
        }

    }