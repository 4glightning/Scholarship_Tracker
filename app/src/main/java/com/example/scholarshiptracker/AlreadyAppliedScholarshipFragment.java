package com.example.scholarshiptracker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AlreadyAppliedScholarshipFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_already_applied_scholarship, container, false);

        getActivity().setTitle("My Scholarship");

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.appliedscholarshipRecView);
        ScholarshipRecViewAdapter adapter = new ScholarshipRecViewAdapter(getActivity(), "fragment_already_applied_scholarship");
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setScholarshipLists(Utils.getInstance(getActivity()).getAlreadyAppliedScholarships() );
        return v;
    }



}