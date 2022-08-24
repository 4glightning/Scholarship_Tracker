package com.example.scholarshiptracker;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.UUID;

import static com.example.scholarshiptracker.Register.TAG;

public class BudgetActivity extends AppCompatActivity {

    FirebaseFirestore db;
    FirebaseAuth fAuth;
    String userID;

    ProgressDialog progressDialog;

    public String getId() {
        return null;
    }

    private TextView txtBudget, txtStart, txtEnd, txtFood, txtMedical, txtEntertainment, txtElectric, txtHousing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);

        this.setTitle("My Budget");
        Toolbar my_toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);

        progressDialog = new ProgressDialog(this);

        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        txtBudget = findViewById(R.id.txtBudget);
        txtStart = findViewById(R.id.txtStart);
        txtEnd = findViewById(R.id.txtEnd);
        txtFood = findViewById(R.id.txtFood);
        txtMedical = findViewById(R.id.txtMedical);
        txtEntertainment = findViewById(R.id.txtEntertainment);
        txtElectric = findViewById(R.id.txtElectric);
        txtHousing = findViewById(R.id.txtHousing);

        showData();
    }

    private void showData() {
        String id = UUID.randomUUID().toString();
        //set title for progress bar
        progressDialog.setTitle("Retrieving Budget...");
        //show progress bar when user click save button
        progressDialog.show();
        db.collection("users").document(userID).collection("budget")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                            progressDialog.dismiss();
                            String budget = document.getString("Budget");
                            String start = document.getString("Start Date");
                            String end = document.getString("End Date");
                            String food = document.getString("Food");
                            String medical = document.getString("Medical");
                            String entertainment = document.getString("Entertainment");
                            String electric = document.getString("Electric");
                            String housing = document.getString("Housing");

                            txtBudget.setText(budget);
                            txtStart.setText(start);
                            txtEnd.setText(end);
                            txtFood.setText(food);
                            txtMedical.setText(medical);
                            txtEntertainment.setText(entertainment);
                            txtElectric.setText(electric);
                            txtHousing.setText(housing);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
//                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                    @Override
//                    public void onSuccess(DocumentSnapshot documentSnapshot) {
//                        if(documentSnapshot.exists())
//                        {
//                            //called when updated successfully
//                            progressDialog.dismiss();
//                            String budget = documentSnapshot.getString("Budget");
//                            String start = documentSnapshot.getString("Start Date");
//                            String end = documentSnapshot.getString("End Date");
//                            String food = documentSnapshot.getString("Food");
//                            String medical = documentSnapshot.getString("Medical");
//                            String entertainment = documentSnapshot.getString("Entertainment");
//                            String electric = documentSnapshot.getString("Electric");
//                            String housing = documentSnapshot.getString("Housing");
//
//                            txtBudget.setText(budget);
//                            txtStart.setText(start);
//                            txtEnd.setText(end);
//                            txtFood.setText(food);
//                            txtMedical.setText(medical);
//                            txtEntertainment.setText(entertainment);
//                            txtElectric.setText(electric);
//                            txtHousing.setText(housing);
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull @NotNull Exception e) {
//                //called when there is any error
//                progressDialog.dismiss();
//                Toast.makeText(BudgetActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}