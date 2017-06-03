package com.gabi.homeworkcourse2;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gabi.homeworkcourse2.adapter.StudentAdapter;
import com.gabi.homeworkcourse2.model.Student;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.empty;
import static com.gabi.homeworkcourse2.R.id.emptyList;

public class StudentsListActivity extends AppCompatActivity {

    private static final int REQUEST_CALL_PHONE = 1 ;
    private ListView mStudentsListView;
    private List<Student> mStudentsList;
    private TextView mEmptyList;
    public static final String EmptyCase = "Lista goala! ";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        DatabaseHandler db = DatabaseHandler.getInstance(this);

        mEmptyList = (TextView) findViewById(emptyList);
        mStudentsListView = (ListView) findViewById(R.id.stud_list_view);
        LinearLayout list = (LinearLayout) findViewById(R.id.students_list);


        if(db.getStudentsCount() > 0) {
          //  Toast.makeText(this, db.getStudentsCount(), Toast.LENGTH_SHORT).show();
            mEmptyList.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
            mStudentsList = new ArrayList<>();
            mStudentsList = db.getAllStudents();
            setCustomAdapterForListView();
        }
        else if(db.getStudentsCount() <= 1){
           list.setVisibility(View.GONE);
            mEmptyList.setVisibility(View.VISIBLE);
           // mEmptyList.setText(EmptyCase);
        }
    }

    private void setCustomAdapterForListView() {
        //build the adapter for the list view
        StudentAdapter studentAdapter = new StudentAdapter(mStudentsList, StudentsListActivity.this);

        //set the adapter to the list view
        mStudentsListView.setAdapter(studentAdapter);
//        mStudentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                DatabaseHandler db = DatabaseHandler.getInstance(getApplicationContext());
//
//                Student studentToCall = db.getStudent(position+1);
//                String phoneNr = String.valueOf(studentToCall.getPhone());
//
//                int checkPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CALL_PHONE);
//                if (checkPermission != PackageManager.PERMISSION_GRANTED) {
//                    ActivityCompat.requestPermissions(
//                            getParent(),
//                            new String[]{Manifest.permission.CALL_PHONE},
//                            REQUEST_CALL_PHONE);
//                } else {
//                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNr));
//                    startActivity(intent);
//                }
//            }
//        });
                mStudentsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseHandler db = DatabaseHandler.getInstance(getApplicationContext());

                Intent intent = new Intent (StudentsListActivity.this, ReadOnlyActivity.class);
                intent.putExtra("read_only",position+1);
                intent.putExtra("name",db.getStudent(position+1).getName());
                intent.putExtra("nickname",db.getStudent(position+1).getNickname());
                intent.putExtra("email",db.getStudent(position+1).getEmail());
                intent.putExtra("phone",db.getStudent(position+1).getPhone());
                intent.putExtra("university",db.getStudent(position+1).getUniversity());
                intent.putExtra("knowledge",db.getStudent(position+1).getKnowledge());
                startActivity(intent);
            }
        });


        mStudentsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseHandler db = DatabaseHandler.getInstance(getApplicationContext());

                Intent intent = new Intent (StudentsListActivity.this, FormActivity.class);
                intent.putExtra("position",position+1);
                intent.putExtra("name",db.getStudent(position+1).getName());
                intent.putExtra("nickname",db.getStudent(position+1).getNickname());
                intent.putExtra("email",db.getStudent(position+1).getEmail());
                intent.putExtra("phone",db.getStudent(position+1).getPhone());
                intent.putExtra("university",db.getStudent(position+1).getUniversity());
                intent.putExtra("knowledge",db.getStudent(position+1).getKnowledge());
                startActivity(intent);
                return true;
            }
        });
//        mStudentsListView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int position = mStudentsListView.getPositionForView(v);
//
//            }
//        });
    }

    public void addStudent(View view) {
        Intent intent = new Intent(this, FormActivity.class);
        startActivity(intent);
    }

    //        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String phone = "+34666777888";
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
//                mContext.startActivity(intent);
//            }
//        });


}
