package com.example.scholarshiptracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ScholarshipActivity extends AppCompatActivity {

    public static final String SCHOLARSHIP_ID_KEY = "scholarship_id";

    private TextView scholarshipName, scholarshipDate, studyLevel, shortDescription, longDescription;
    private Button btnApply, btnAppliedScholarship;
    private ImageView scholarshipImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship);

        this.setTitle("Scholarship Details");
        Toolbar my_toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);

        initViews();

        //TODO: Get the data from the recycler view in here

        Intent intent = getIntent();
        if(null != intent){
            int scholarship_id = intent.getIntExtra(SCHOLARSHIP_ID_KEY, -1);
            if(scholarship_id != -1){
                ScholarshipList incomingScholarship = Utils.getInstance(this).getScholarshipById(scholarship_id);
                if (null != incomingScholarship) {
                    setData(incomingScholarship);

                    handleAlreadyAppliedScholarship(incomingScholarship);
                }
            }
        }

        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScholarshipActivity.this, WebsiteActivity.class);
                intent.putExtra("url", "https://biasiswa.index.my/");
                startActivity(intent);
            }
        });


    }

    /**
     *Enable and Disable button
     * Add the scholarship that already been applied to Already Applied Scholarship Arraylist
     * @param scholarshipList
     */

    private void handleAlreadyAppliedScholarship(final ScholarshipList scholarshipList){
        ArrayList<ScholarshipList> alreadyAppliedScholarship = Utils.getInstance(this).getAlreadyAppliedScholarships();

        boolean existInAlreadyAppliedScholarship = false;

        for (ScholarshipList s: alreadyAppliedScholarship){
            if(s.getSid() == scholarshipList.getSid()){
                existInAlreadyAppliedScholarship = true;
            }
        }

        if (existInAlreadyAppliedScholarship){
            btnAppliedScholarship.setEnabled(false);
        }else{
            btnAppliedScholarship.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Utils.getInstance(ScholarshipActivity.this).addToAlreadyAppliedScholarship(scholarshipList)){
                        Toast.makeText(ScholarshipActivity.this, "Scholarship Added", Toast.LENGTH_SHORT).show();

                    }else{
                        Toast.makeText(ScholarshipActivity.this, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void setData(ScholarshipList scholarshipList) {
        scholarshipName.setText(scholarshipList.getSname());
        scholarshipDate.setText(scholarshipList.getStartDate());
        studyLevel.setText(scholarshipList.getLevelOfStudy());
        shortDescription.setText(scholarshipList.getSshortDesc());
        longDescription.setText(scholarshipList.getSlongDesc());
        Glide.with(this)
                .asBitmap().load(scholarshipList.getImageUrl())
                .into(scholarshipImage);
        btnApply.getUrls();
    }

    private void initViews() {
        scholarshipName = findViewById(R.id.scholarshipName);
        scholarshipDate = findViewById(R.id.scholarshipDate);
        studyLevel = findViewById(R.id.studyLevel);
        shortDescription = findViewById(R.id.shortDescription);
        longDescription = findViewById(R.id.longDescription);

        btnApply = findViewById(R.id.btnApply);
        btnAppliedScholarship = findViewById(R.id.btnAppliedScholarship);

        scholarshipImage = findViewById(R.id.scholarshipImage);
    }
}