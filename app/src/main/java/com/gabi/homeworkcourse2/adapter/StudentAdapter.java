package com.gabi.homeworkcourse2.adapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gabi.homeworkcourse2.DatabaseHandler;
import com.gabi.homeworkcourse2.R;
import com.gabi.homeworkcourse2.StudentsListActivity;
import com.gabi.homeworkcourse2.model.Student;

import java.util.List;

/**
 * Created by gabi on 5/23/2017.
 */

public class StudentAdapter extends BaseAdapter {
    private static final int REQUEST_CALL_PHONE = 1;
    private List<Student> mStudentsList;
    private Context mContext;
    public StudentAdapter(List<Student> mStudentsList, Context mContext) {
        this.mStudentsList = mStudentsList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        if (mStudentsList == null)
            return 0;
        else
            return mStudentsList.size();
    }

    @Override
    public Object getItem(int position) {
        if (mStudentsList == null)
            return null;
        else
            return mStudentsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        View view = null;
        final int currentPosition = position;
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            int layoutId = R.layout.student_item;
            view = layoutInflater.inflate(layoutId, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.mPhoneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view) {
                DatabaseHandler db = DatabaseHandler.getInstance(mContext);

                Student studentToCall = db.getStudent(position+1);
                String phoneNr = String.valueOf(studentToCall.getPhone());

                int checkPermission = ContextCompat.checkSelfPermission(mContext, Manifest.permission.CALL_PHONE);
                if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            (StudentsListActivity)mContext,
                            new String[]{Manifest.permission.CALL_PHONE},
                            REQUEST_CALL_PHONE);
                } else {
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNr));
                    mContext.startActivity(intent);
                }
            }
        });



        Student student = (Student) getItem(currentPosition);

        viewHolder.mNameTextView.setText(student.getName());
        viewHolder.mNickNameTextView.setText(student.getNickname());
        viewHolder.mUnivTextView.setText(student.getUniversity());

        return view;
    }

    class ViewHolder {
        protected TextView mNameTextView;
        protected TextView mNickNameTextView;
        protected TextView mUnivTextView;
        protected ImageView mPicImageView;
        protected ImageView mPhoneView;

        public ViewHolder(View view){
            mNameTextView = (TextView) view.findViewById(R.id.student_name);
            mNickNameTextView = (TextView) view.findViewById(R.id.student_nickname);
            mUnivTextView = (TextView) view.findViewById(R.id.university);
            mPicImageView = (ImageView) view.findViewById(R.id.profile);
            mPhoneView = (ImageView) view.findViewById(R.id.phone);

        }
    }
}
