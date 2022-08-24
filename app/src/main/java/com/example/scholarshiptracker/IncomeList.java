package com.example.scholarshiptracker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class IncomeList extends Fragment {

    public static final String TAG = "Firelog";
    FloatingActionButton fab;
    FirebaseAuth fAuth;
    FirebaseFirestore db;
    String userID;

    RecyclerView recyclerView;
    List<IncomeListItem> incomeListItems = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    IncomeListAdapter incomeListAdapter;

    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_income_list, container, false);

        getActivity().setTitle("Income List");

        progressDialog = new ProgressDialog(getActivity());

        incomeListAdapter = new IncomeListAdapter(IncomeList.this, incomeListItems);

        fAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        //initialize views
        recyclerView = v.findViewById(R.id.incomeRecyclerView);

        //set recycler view properties
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(incomeListAdapter);

        //show data in recyclerView
        showData();

        //fab
        fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IncomeInputActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    private void showData() {
        //set title of progress dialog
        progressDialog.setTitle("Loading Data....");
        //show progress dialog
        progressDialog.show();


        db.collection("users").document(userID).collection("income")
                .orderBy("Date", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable @org.jetbrains.annotations.Nullable QuerySnapshot value, @Nullable @org.jetbrains.annotations.Nullable FirebaseFirestoreException error) {

                        if (error != null) {

                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e(TAG, "Error: " + error.getMessage());
                            return;
                        }
                        for (DocumentChange doc : value.getDocumentChanges()) {

                            if (doc.getType() == DocumentChange.Type.ADDED) {

                                incomeListItems.add(doc.getDocument().toObject(IncomeListItem.class));

                            }

                            incomeListAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();

                        }

                    }
                });
    }

    public void deleteData(int index) {
        progressDialog.setTitle("Deleting Data...");
        progressDialog.show();

        db.collection("users").document(userID).collection("income").document(incomeListItems.get(index).getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        //called when deleted successfully
                        Toast.makeText(getActivity(), "Deleted...", Toast.LENGTH_SHORT).show();
                        //update data


                        showData();
                        incomeListAdapter.notifyDataSetChanged();
                        incomeListItems.clear();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                //called when there's error
                progressDialog.dismiss();
                //get and show error message
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    //menu


    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull @NotNull Menu menu, @NonNull @NotNull MenuInflater inflater) {
        //inflating search_menu.xml
        inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        //SearchView
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //called when pressed search button

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);

                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        //handle other menu item clicks here
        if (item.getItemId() == R.id.action_setting) {
            Toast.makeText(getActivity(), "Settings", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        List<IncomeListItem> incomeFilteredList = new ArrayList<>();

        // running a for loop to compare elements.
        for (IncomeListItem item : incomeListItems) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getCategory().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                incomeFilteredList.add(item);
            }
        }
        if (incomeFilteredList.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(getActivity(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            incomeListAdapter.filterList(incomeFilteredList);
        }
    }


}