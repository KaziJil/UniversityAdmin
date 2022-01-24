package com.example.universityofsouthasiaadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.universityofsouthasiaadmin.DeleteNotice.DeleteNotice;
import com.example.universityofsouthasiaadmin.DeleteNotice.NoticeActivity;
import com.example.universityofsouthasiaadmin.Faculty.UpdateFaculty;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout gotoNoticeActivity;
    private LinearLayout goto_gallery_activity;
    private LinearLayout goto_pdf_activity;
    private LinearLayout faculty_info;
    private LinearLayout delete_notice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gotoActivity();
    }
    private void gotoActivity() {
        gotoNoticeActivity=findViewById(R.id.noticeActivity);
        goto_gallery_activity=findViewById(R.id.gallery_view);
        goto_pdf_activity=findViewById(R.id.Pdf_cardView);
        faculty_info=findViewById(R.id.faculty_info_main_xml);
        delete_notice=findViewById(R.id.delete_notice_activity);

        gotoNoticeActivity.setOnClickListener(this);
        faculty_info.setOnClickListener(this);
        goto_pdf_activity.setOnClickListener(this);
        delete_notice.setOnClickListener(this);
        goto_gallery_activity.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.noticeActivity:
                startActivity(new Intent(this, NoticeActivity.class));
                break;
            case R.id.Pdf_cardView:
                startActivity(new Intent(this,UploadPDF.class));
                break;
            case R.id.faculty_info_main_xml:
                startActivity(new Intent(this, UpdateFaculty.class));
                break;
            case R.id.delete_notice_activity:
                startActivity(new Intent(this, DeleteNotice.class));
                break;
            case R.id.gallery_view:
                startActivity(new Intent(this, GalleryImage.class));
                break;

        }

    }
}