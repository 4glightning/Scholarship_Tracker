//package com.example.scholarshiptracker;
//
//import android.app.ProgressDialog;
//import android.os.Bundle;
//import android.widget.TextView;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.firestore.FirebaseFirestore;
//
//public class WalletActivity extends AppCompatActivity {
//
//    TextView wallet_amount;
//
//    public static final String TAG = "Firelog";
//    FirebaseAuth fAuth;
//    FirebaseFirestore db;
//    String userID;
//
//    ProgressDialog progressDialog;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_wallet);
//
//        this.setTitle("My Wallet");
//        Toolbar my_toolbar = findViewById(R.id.my_toolbar);
//        setSupportActionBar(my_toolbar);
//
//        progressDialog = new ProgressDialog(this);
//
//        wallet_amount = findViewById(R.id.wallet_amount);
//
//        fAuth = FirebaseAuth.getInstance();
//        db = FirebaseFirestore.getInstance();
//        userID = fAuth.getCurrentUser().getUid();
//
////        Intent intent = getIntent();
////        String amount = intent.getStringExtra("message");
////        wallet_amount.setText(amount);
//
////        showData();
//    }
//
////    private int showData() {
////        //set title of progress dialog
////        progressDialog.setTitle("Loading Data....");
////        //show progress dialog
////        progressDialog.show();
////
////
////        db.collection("users").document(userID).collection("income").document()
////                .get()
////                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
////            @Override
////            public void onSuccess(DocumentSnapshot documentSnapshot) {
////                if(documentSnapshot.exists()){
////                    wallet_amount.setText(documentSnapshot.getString("Amount"));
////                }else{
////                    Toast.makeText(WalletActivity.this, "Failed to load data", Toast.LENGTH_SHORT).show();
////                }
////            }
////        }).addOnFailureListener(new OnFailureListener() {
////            @Override
////            public void onFailure(@NonNull @NotNull Exception e) {
////                Toast.makeText(WalletActivity.this, "Failed to fetch data", Toast.LENGTH_SHORT).show();
////            }
////        });
////
////
////        return 0;
////    }
//
//}