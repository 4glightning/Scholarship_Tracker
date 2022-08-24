package com.example.scholarshiptracker;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IncomeInputActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    FirebaseFirestore db;
    FirebaseAuth fAuth;
    String userID;

    private TextInputEditText inputAmount;
    private EditText income_comment;
    private Button btnDatePicker, btnSave, btnClear;

    private AutoCompleteTextView autoCompleteCategory, autoCompleteTransctionType;

    ProgressDialog progressDialog;

    String pId, pAmount, pCategory, pDate, pType, pComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_input);

        this.setTitle("Income Input");
        Toolbar my_toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);

        progressDialog = new ProgressDialog(this);

        db = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        userID = fAuth.getCurrentUser().getUid();

        inputAmount = findViewById(R.id.inputAmount);
        income_comment = findViewById(R.id.income_comment);

        autoCompleteCategory = findViewById(R.id.autocompleteCategory);
        autoCompleteTransctionType = findViewById(R.id.autoCompleteTransactionType);

        String[] category = {"Scholarship", "Allowance", "Part Time", "Others"};
        ArrayAdapter arrayCategory = new ArrayAdapter(this, R.layout.input_category, category);

        String[] transaction = {"Cash", "Online Banking", "Credit Card"};
        ArrayAdapter arrayTransaction = new ArrayAdapter(this, R.layout.transaction_type, transaction);

        //to make default value
        //autoCompleteCategory.setText(arrayAdapter.getItem(0).toString(), false);
        autoCompleteCategory.setAdapter(arrayCategory);
        autoCompleteTransctionType.setAdapter(arrayTransaction);

        btnDatePicker = findViewById(R.id.btnDatePicker);
        btnSave = findViewById(R.id.btnSave);
        btnClear = findViewById(R.id.btnClear);

        //after clicking update
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            //Update data
            this.setTitle("Update Income Input");
            btnSave.setText("UPDATE");
            //get data
            pId = bundle.getString("pId");
            pAmount = bundle.getString("pAmount");
            pCategory = bundle.getString("pCategory");
            pDate = bundle.getString("pDate");
            pType = bundle.getString("pType");
            pComment = bundle.getString("pComment");
            //set data
            inputAmount.setText(pAmount);
            autoCompleteCategory.setText(pCategory);
            btnDatePicker.setText(pDate);
            autoCompleteTransctionType.setText(pType);
            income_comment.setText(pComment);

        }else{
            //New data
            btnSave.setText("SAVE");
        }


        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = getIntent().getExtras();
                if(bundle != null){
                    //updating
                    //adding new
                    String id = pId;
                    String Amount = inputAmount.getText().toString().trim();
                    String Category = autoCompleteCategory.getText().toString();
                    String Date = btnDatePicker.getText().toString();
                    String Type = autoCompleteTransctionType.getText().toString();
                    String Comment = income_comment.getText().toString().trim();
                    //security to avoid empty fields
                    if (TextUtils.isEmpty(Amount)) {
                        inputAmount.setError("Amount is required");
                        return;
                    }

                    if (TextUtils.isEmpty(Category)) {
                        autoCompleteCategory.setError("Category is required");
                        return;
                    }

                    if (TextUtils.isEmpty(Date)) {
                        btnDatePicker.setError("Date is required");
                        return;
                    }

                    if (TextUtils.isEmpty(Type)) {
                        autoCompleteTransctionType.setError("Transaction type is required");
                        return;
                    }
                    //function call to update data
                    updateData(id, Amount, Category, Date,Type, Comment);


                }else{
                    //adding new
                    String Amount = inputAmount.getText().toString().trim();
                    String Category = autoCompleteCategory.getText().toString();
                    String Date = btnDatePicker.getText().toString();
                    String Type = autoCompleteTransctionType.getText().toString();
                    String Comment = income_comment.getText().toString().trim();

                    if (TextUtils.isEmpty(Amount)) {
                        inputAmount.setError("Amount is required");
                        return;
                    }

                    if (TextUtils.isEmpty(Category)) {
                        autoCompleteCategory.setError("Category is required");
                        return;
                    }

                    if (TextUtils.isEmpty(Date)) {
                        btnDatePicker.setError("Date is required");
                        return;
                    }

                    if (TextUtils.isEmpty(Type)) {
                        autoCompleteTransctionType.setError("Transaction type is required");
                        return;
                    }

                    uploadData(Amount, Category, Date, Type, Comment);
                }

            }
        });

                btnClear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        inputAmount.getText().clear();
                        autoCompleteCategory.getText().clear();
                        btnDatePicker.setText("Select Date");
                        autoCompleteTransctionType.getText().clear();
                        income_comment.getText().clear();
                    }
                });
            }

    private void updateData(String id, String amount, String category, String date, String type, String comment) {
        //set title for progress bar
        progressDialog.setTitle("Updating Data to the Firestore");
        //show progress bar when user click save button
        progressDialog.show();

        db.collection("users").document(userID).collection("income").document(id)
                .update("Amount", amount, "Category", category, "search", category.toLowerCase(), "Date", date, "Type", type, "Comment", comment)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        //called when updated successfully
                        progressDialog.dismiss();
                        Toast.makeText(IncomeInputActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                //called when there is any error
                progressDialog.dismiss();
                //get and show error message
                Toast.makeText(IncomeInputActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadData(String amount, String category, String date, String type, String comment) {
        //set title for progress bar
        progressDialog.setTitle("Adding Data to the Firestore");
        //show progress bar when user click save button
        progressDialog.show();
        String id = UUID.randomUUID().toString();

        Map<String, Object> income = new HashMap<>();
        income.put("id", id);
        income.put("Amount", amount);
        income.put("Category", category);
        income.put("search", category.toLowerCase());
        income.put("Date", date);
        income.put("Type", type);
        income.put("Comment", comment);

        //add this data
        db.collection("users").document(userID).collection("income").document(id)
                .set(income)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                        //this will be called when data is added successfully
                        progressDialog.dismiss();
                        Toast.makeText(IncomeInputActivity.this, "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                //this will be called when there is error during uploading
                progressDialog.dismiss();
                //get and show error message
                Toast.makeText(IncomeInputActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    //menu

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        btnDatePicker.setText(currentDateString);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }
}