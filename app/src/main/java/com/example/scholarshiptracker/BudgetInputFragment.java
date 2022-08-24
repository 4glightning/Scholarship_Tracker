package com.example.scholarshiptracker;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class BudgetInputFragment extends Fragment {

    FirebaseFirestore db;
    FirebaseAuth fAuth;
    String userID;

    private DatePickerDialog.OnDateSetListener startDateSetListener, endDateSetListener;
    private Button startDate, endDate, btnSubmit;
    private TextInputEditText budgetAmount;
    private EditText foodAmount, medicalAmount, entertainmentAmount, electricAmount, housingAmount;
    private TextView totalAmount;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_input_budget, container, false);

        getActivity().setTitle("Budget Input");

        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        startDate = v.findViewById(R.id.startDate);
        endDate = v.findViewById(R.id.endDate);
        btnSubmit = v.findViewById(R.id.btnSubmit);

        budgetAmount = v.findViewById(R.id.budgetAmount);
        foodAmount = v.findViewById(R.id.foodAmount);
        medicalAmount = v.findViewById(R.id.medicalAmount);
        entertainmentAmount = v.findViewById(R.id.entertainmentAmount);
        electricAmount = v.findViewById(R.id.electricAmount);
        housingAmount = v.findViewById(R.id.housingAmount);
        totalAmount = v.findViewById(R.id.totalAmount);

        startDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_DeviceDefault_Dialog, startDateSetListener, year, month, day);
                dialog.show();
            }
        });


        endDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_DeviceDefault_Dialog, endDateSetListener, year, month, day);
                dialog.show();
            }
        });

        startDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDataSet: dd/mm/yyyy" + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                startDate.setText(date);
            }
        };
        endDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDataSet: dd/mm/yyyy" + day + "/" + month + "/" + year);

                String date = day + "/" + month + "/" + year;
                endDate.setText(date);
            }
        };

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!foodAmount.getText().toString().equals("") && !medicalAmount.getText().toString().equals("") && !entertainmentAmount.getText().toString().equals("") && !electricAmount.getText().toString().equals("") && !housingAmount.getText().toString().equals("")){
                    int food = Integer.parseInt(foodAmount.getText().toString());
                    int medical = Integer.parseInt(medicalAmount.getText().toString());
                    int entertainment = Integer.parseInt(entertainmentAmount.getText().toString());
                    int electric = Integer.parseInt(electricAmount.getText().toString());
                    int housing = Integer.parseInt(housingAmount.getText().toString());
                    totalAmount.setText(String.valueOf(food + medical + entertainment + electric + housing));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        foodAmount.addTextChangedListener(textWatcher);
        medicalAmount.addTextChangedListener(textWatcher);
        entertainmentAmount.addTextChangedListener(textWatcher);
        electricAmount.addTextChangedListener(textWatcher);
        housingAmount.addTextChangedListener(textWatcher);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String budgetInput = budgetAmount.getText().toString();
                String dateStart = startDate.getText().toString();
                String dateEnd = endDate.getText().toString();
                String food = foodAmount.getText().toString();
                String medical = medicalAmount.getText().toString();
                String entertainment = entertainmentAmount.getText().toString();
                String electric = electricAmount.getText().toString();
                String housing = housingAmount.getText().toString();

                Map<String, Object> budget = new HashMap<>();
                budget.put("Budget", budgetInput);
                budget.put("Start Date", dateStart);
                budget.put("End Date", dateEnd);
                budget.put("Food", food);
                budget.put("Medical", medical);
                budget.put("Entertainment", entertainment);
                budget.put("Electric", electric);
                budget.put("Housing", housing);

                userID = fAuth.getCurrentUser().getUid();

                db.collection("users").document(userID).collection("budget")
                        .add(budget)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        return v;
    }

}