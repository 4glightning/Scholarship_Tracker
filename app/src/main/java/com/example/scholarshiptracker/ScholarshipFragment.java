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

public class ScholarshipFragment extends Fragment {

    private RecyclerView scholarshipRecView;

//    RecyclerView recyclerView;
//    ArrayList<ScholarshipItem> scholarshipItems;
//    ScholarshipItemAdapter scholarshipItemAdapter;
//    FirebaseAuth fAuth;
//    FirebaseFirestore db;
//    String userID;
//
//    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scholarship, container, false);

        getActivity().setTitle("Scholarship List");

//        recyclerView = view.findViewById(R.id.scholarshipRecView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//
//        db = FirebaseFirestore.getInstance();
//        fAuth = FirebaseAuth.getInstance();
//        scholarshipItems = new ArrayList<ScholarshipItem>();
//        scholarshipItemAdapter = new ScholarshipItemAdapter(getActivity(), scholarshipItems);
//
//
//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setCancelable(true);
//        progressDialog.setMessage("Fetching Data...");
//        progressDialog.show();
//
//        recyclerView.setAdapter(scholarshipItemAdapter);
//
//        EventChangeListener();



        ScholarshipRecViewAdapter adapter = new ScholarshipRecViewAdapter(getActivity(), "scholarship_fragment");
        scholarshipRecView = view.findViewById(R.id.scholarshipRecView);

        scholarshipRecView.setAdapter(adapter);
        scholarshipRecView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter.setScholarshipLists(Utils.getInstance(getActivity()).getAllScholarships());
        return view;


    }

//    private void EventChangeListener() {
//        userID = fAuth.getCurrentUser().getUid();
//        db.collection("users").document(userID).collection("scholarship")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {
//
//                        if(error != null){
//
//                            if(progressDialog.isShowing())
//                                progressDialog.dismiss();
//
//                            Log.e("Firestore Error", error.getMessage());
//                            return;
//                        }
//
//                        for(DocumentChange dc : value.getDocumentChanges()){
//
//                            if(dc.getType() == DocumentChange.Type.ADDED){
//
//                                scholarshipItems.add(dc.getDocument().toObject(ScholarshipItem.class));
//
//                            }
//
//                            scholarshipItemAdapter.notifyDataSetChanged();
//                            if(progressDialog.isShowing())
//                                progressDialog.dismiss();
//                        }
//
//                    }
//                });
//    }


}
