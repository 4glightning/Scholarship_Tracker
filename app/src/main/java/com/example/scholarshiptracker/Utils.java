package com.example.scholarshiptracker;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {

    private static final String ALL_SCHOLARSHIP_KEY = "all_scholarship";
    private static final String ALREADY_APPLIED_SCHOLARSHIP = "already_applied_scholarship";

    private static Utils instance;
    private SharedPreferences sharedPreferences;

    private Utils(Context context) {

        sharedPreferences = context.getSharedPreferences("alternate_db", Context.MODE_PRIVATE);

        if (null == getAllScholarships()) {
            initData();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if (null == getAlreadyAppliedScholarships()) {
            editor.putString(ALREADY_APPLIED_SCHOLARSHIP, gson.toJson(new ArrayList<ScholarshipList>()));
            editor.commit();
        }
    }

    private void initData() {
        //TODO: ADD initial data

        ArrayList<ScholarshipList> scholarshipLists = new ArrayList<>();
        scholarshipLists.add(new ScholarshipList(1, "PIDN", "Program Ijazah Dalam Negara", "https://assets.bharian.com.my/images/articles/jpa_1548411763.jpg", "16/04/2021", "Degree","Program Ijazah Dalam Negara (PIDN) bagi tahun 2021 merupakan penajaan yang berfokus kepada pelajar peringkat ijazah pertama yang sedang mengikuti pengajian di Universiti Awam (UA),Politeknik Premier dan Government Linked Universities (GLU). Penajaan program ini adalah dalam bentuk Pinjaman Boleh Ubah PBU).", "https://esilav2.jpa.gov.my/"));
        scholarshipLists.add(new ScholarshipList(2, "PS", "Panasonic Scholarship",  "https://envirocarespain.com/wp-content/uploads/2020/04/panasonic-logo-1280x720-3.jpg", "15/05/2021", "Degree","Panasonic Scholarship 2021: For Engineering Students is a program which seeks to financially assist engineering students. Students who are currently studying and those who wishes to continue their tertiary education in the field of engineering are encouranged to apply.", "https://biasiswa.index.my/list/panasonic-scholarship-2016/"));
        scholarshipLists.add(new ScholarshipList(3, "BKNS", "Biasiswa Kerajaan Negeri Sabah", "http://3.bp.blogspot.com/-azC8E2srv5w/VpuImh4eJkI/AAAAAAAAA5E/ILvxmiWotek/s1600/BIASISWA-KERAJAAN-NEGERI-SABAH.png", "30/04/2021", "Degree","Warganegara Malaysia yang berasal dari Negeri Sabah dan Wilayah Persekutuan Labuan dan salah seorang ibu atau bapanya dilahirkan di Sabah atau Wilayah Persekutuan Labuan yang mendapat keputusan cemerlang di peringkat SPM / STPM atau yang setaraf / Matrikulasi, adalah dipelawa memohon Biasiswa Kerajaan Negeri Sabah untuk mengikuti kursus-kursus di peringkat Diploma / Ijazah Sarjana Muda di Institusi-institusi Pengajian Tinggi yang diiktiraf oleh Kerajaan.", "www.biasiswa.sabah.gov.my"));
        scholarshipLists.add(new ScholarshipList(4, "BYDS", "Biasiswa Yayasan DayaDiri Scholarhsip", "https://yddmy.files.wordpress.com/2014/08/apply.jpg?w=900", "30/04/2021", "Degree","We are looking to sponsor students intending to pursue their undergraduate or Master’s degrees. The scholarship covers full tuition and living expenses as determined by the institution.", "https://ydd.simplify.com.my/scholarship/"));
        scholarshipLists.add(new ScholarshipList(5, "BSM", "Biasiswa Shell Malaysia", "https://cdn.imgbin.com/7/3/19/imgbin-royal-dutch-shell-shell-oil-company-petroleum-logo-lubricant-others-if6ad1HbpDA8a8mbXd8bqnHyC.jpg", "Ongoing", "Degree","A scholarship with Shell Malaysia opens a world of possibilities including opportunity to pursue undergraduate studies in global top tier universities, participating in mentoring, internship programs, and upon graduation, embarking on a fulfilling career with us.Apply now and be part of the Shell story!", "https://www.shell.com.my/careers/students-and-graduates/scholarships.html"));
        scholarshipLists.add(new ScholarshipList(6, "YSDS", "Yayasan Sime Darby Scholarship", "https://www.arteri.com.my/wp-content/uploads/2016/09/SB-e1473061564765.jpg", "26/04/2021", "Degree","Yayasan Sime Darby invites qualified SPM 2020 leavers to apply for education sponsorship to pursue foundation studies locally. Recipients of this scholarship will be sponsored to pursue foundation studies locally to prepare themselves for undergraduate admission at top universities in the United Kingdom.Pre-University scholars would be given priority for Yayasan Sime Darby Undergraduate Scholarship (Overseas) upon meeting the qualifying criteria after completion of their foundation studies. The continuation of the degree programme is at the sole discretion of Yayasan Sime Darby.", "http://www.yayasansimedarby.com/page-view.aspx?website_page_template_menu_id=0D44D91D-0236-47C0-92D6-123B3DF3AD83&page_draft_id=224ead98-496b-4267-9e5e-781f4ea577f6"));
        scholarshipLists.add(new ScholarshipList(7, "BMUS", "Biasiswa Multimedia University Scholarship", "https://www.yayasantar.org.my/wp-content/uploads/yum.png", "24/04/2021", "Degree","Our option of various loans and scholarships are available for deserving students so that quality university education is accessible to all. Come join us and reward yourself!", "https://biasiswa.index.my/list/biasiswa-multimedia-university-scholarship-financial-aid/"));
        scholarshipLists.add(new ScholarshipList(8, "BTAR", "Biasiswa Tunku Abdul Rahman Scholarship", "https://www.yayasantar.org.my/wp-content/uploads/YTAR-logo.png", "12/04/2021", "Degree","The scholarship is open to all, focusing especially on high-potential students with a strong financial need to cover the expenses of the university’s tuition fees and living expenses during the course of the study. We encourage all Malaysians from all fields of study to apply as all applications will be reviewed individually and holistically by the YTAR team.", "https://www.yayasantar.org.my/bm/biasiswa-tunku-abdul-rahman/"));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(ALL_SCHOLARSHIP_KEY, gson.toJson(scholarshipLists));
        editor.commit();
    }

    public static Utils getInstance(Context context) {
        if (null != instance) {
            return instance;
        } else {
            instance = new Utils(context);
            return instance;
        }
    }

    public ArrayList<ScholarshipList> getAllScholarships() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ScholarshipList>>(){}.getType();
        ArrayList<ScholarshipList> scholarshipLists = gson.fromJson(sharedPreferences.getString(ALL_SCHOLARSHIP_KEY, null), type);
        return scholarshipLists;
    }

    public ArrayList<ScholarshipList> getAlreadyAppliedScholarships() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<ScholarshipList>>(){}.getType();
        ArrayList<ScholarshipList> scholarshipLists = gson.fromJson(sharedPreferences.getString(ALREADY_APPLIED_SCHOLARSHIP, null), type);
        return scholarshipLists;
    }

    public ScholarshipList getScholarshipById (int id){
        ArrayList<ScholarshipList> scholarshipLists = getAllScholarships();
        if (null != scholarshipLists){
            for(ScholarshipList s: scholarshipLists){
                if (s.getSid() == id) {
                    return s;
                }
            }
        }

        return null;
    }

    public boolean addToAlreadyAppliedScholarship(ScholarshipList scholarshipList){
        ArrayList<ScholarshipList> scholarshipLists = getAlreadyAppliedScholarships();
        if (null != scholarshipLists){
            if (scholarshipLists.add(scholarshipList)){
                Gson gson = new Gson();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(ALREADY_APPLIED_SCHOLARSHIP);
                editor.putString(ALREADY_APPLIED_SCHOLARSHIP, gson.toJson(scholarshipLists));
                editor.commit();
                return true;
            }
        }

        return false;
    }

    public boolean removeFromAlreadyApplied(ScholarshipList scholarshipList){
        ArrayList<ScholarshipList> scholarshipLists = getAlreadyAppliedScholarships();
        if (null != scholarshipLists){
            for(ScholarshipList s: scholarshipLists){
                if(s.getSid() == scholarshipList.getSid()){
                    if(scholarshipLists.remove(s)){
                        Gson gson = new Gson();
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(ALREADY_APPLIED_SCHOLARSHIP);
                        editor.putString(ALREADY_APPLIED_SCHOLARSHIP, gson.toJson(scholarshipLists));
                        editor.commit();
                        return true;
                    }
                }
            }
        }

        return false;
    }
}
