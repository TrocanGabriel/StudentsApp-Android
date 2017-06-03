package com.gabi.homeworkcourse2;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gabi.homeworkcourse2.model.Student;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import static android.R.attr.country;
import static android.R.attr.id;
import static android.R.color.black;
import static com.gabi.homeworkcourse2.R.id.add;
import static com.gabi.homeworkcourse2.R.id.radio;
import static com.gabi.homeworkcourse2.R.id.radioGroup;

public class FormActivity extends AppCompatActivity {
    EditText name, nickname, email, phone;
    RadioGroup radioGroup;
    Intent intent;
    CheckBox oop, java, c, cSharp ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);


        name = (EditText) findViewById(R.id.name);
        nickname = (EditText) findViewById(R.id.nickname);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        oop = (CheckBox) findViewById(R.id.oop);
        java = (CheckBox) findViewById(R.id.java);
        c = (CheckBox) findViewById(R.id.c);
        cSharp = (CheckBox) findViewById(R.id.cSharp);

        intent = getIntent();
        if (intent.getIntExtra("position", -1) != -1) {
            name.setText(intent.getStringExtra("name"));
            nickname.setText(intent.getStringExtra("nickname"));
            email.setText(intent.getStringExtra("email"));
            phone.setText(String.valueOf(intent.getIntExtra("phone", 0)));

            for (int i = 0; i < radioGroup.getChildCount(); i++) {
                RadioButton view = (RadioButton) radioGroup.getChildAt(i);
                if (view.getText().equals(intent.getStringExtra("university"))) {
                    view.setChecked(true);
                }
            }
            String[] knowledges = intent.getStringExtra("knowledge").split(",");
            if (knowledges[0].equals("1")) oop.setChecked(true);
            if (knowledges[1].equals("1")) java.setChecked(true);
            if (knowledges[2].equals("1")) c.setChecked(true);
            if (knowledges[3].equals("1")) cSharp.setChecked(true);

        }
//        else if (intent.getBooleanExtra("read_only", false)) {
//
//            name.setText(intent.getStringExtra("name"));
//            nickname.setText(intent.getStringExtra("nickname"));
//            email.setText(intent.getStringExtra("email"));
//            phone.setText(String.valueOf(intent.getIntExtra("phone", 0)));
//
//            name.setEnabled(false);
//
//            nickname.setEnabled(false);
//
//            email.setEnabled(false);
//
//            phone.setEnabled(false);
//
//            TextView uni = null;
//            for (int i = 0; i < radioGroup.getChildCount(); i++) {
//                RadioButton view = (RadioButton) radioGroup.getChildAt(i);
//                if (view.getText().equals(intent.getStringExtra("university"))) {
//                    uni.setText(view.getText());
//                }
//            }
//
//            radioGroup.setVisibility(View.GONE);
//            TextView universityTitle = (TextView) findViewById(R.id.univ_title);
//
//
//        }
    }
    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void saveStudent(View view) {
        DatabaseHandler db = DatabaseHandler.getInstance(this);

        String addUniv = "";

        if (radioGroup.getCheckedRadioButtonId() != -1) {
            int id = radioGroup.getCheckedRadioButtonId();
            View radioButton = radioGroup.findViewById(id);
            int radioId = radioGroup.indexOfChild(radioButton);
            RadioButton btn = (RadioButton) radioGroup.getChildAt(radioId);
            addUniv += (String) btn.getText();
        }

        String addName = name.getText().toString();
        String addNickName = nickname.getText().toString();
        String addEmail = email.getText().toString();
        int addPhone = Integer.parseInt(String.valueOf(phone.getText()));

        String addKnowledge = "";
        if(oop.isChecked()) addKnowledge += "1,";
                        else addKnowledge += "0,";
        if(java.isChecked()) addKnowledge += "1,";
                        else addKnowledge += "0,";
        if(c.isChecked()) addKnowledge += "1,";
                        else addKnowledge += "0,";
        if(cSharp.isChecked()) addKnowledge += "1,";
                        else addKnowledge += "0,";


        Log.d("Insert: ", "Inserting ..");

        if(isValidEmail(addEmail)) {


            if (intent.getIntExtra("position", -1) == -1) {

                Student addNew;
                db.addStudent(addNew = new Student(addName, addNickName, addEmail, addPhone, addUniv, addKnowledge));
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Student");

                String studentId = mDatabase.push().getKey();

                mDatabase.child(studentId).setValue(addNew);
            } else if (intent.getBooleanExtra("read_only",false)) {
                Intent intent = new Intent(this, StudentsListActivity.class);
                startActivity(intent);
            } else {
                db.updateStudent(new Student(intent.getIntExtra("position", 0), addName, addNickName,
                        addEmail, addPhone, addUniv, addKnowledge));
            }


            Intent intent = new Intent(this, StudentsListActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(this, "Invalid email address",
                    Toast.LENGTH_SHORT).show();
        }
    }
}

