package com.gabi.homeworkcourse2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.R.attr.gravity;
import static android.R.attr.layout_gravity;
import static android.R.attr.left;
import static android.view.Gravity.CENTER;
import static com.gabi.homeworkcourse2.R.id.cSharp;

public class ReadOnlyActivity extends AppCompatActivity {

    TextView name, nickname, email, phone, univ, knowledgesList, answer;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_only);

        name = (TextView) findViewById(R.id.name);
        nickname = (TextView) findViewById(R.id.nickname);
        email = (TextView) findViewById(R.id.email);
        phone = (TextView) findViewById(R.id.phone);
        univ = (TextView) findViewById(R.id.univ);
        knowledgesList = (TextView) findViewById(R.id.knowledges);

            intent =getIntent();
        if (intent.getIntExtra("read_only", -1) != -1) {

            name.setText(intent.getStringExtra("name"));
            nickname.setText(intent.getStringExtra("nickname"));
            email.setText(intent.getStringExtra("email"));
            phone.setText(String.valueOf(intent.getIntExtra("phone", 0)));
            univ.setText(intent.getStringExtra("university"));

            String knowledge  = "";
            String[] knowledges = intent.getStringExtra("knowledge").split(",");
            if (knowledges[0].equals("1")) knowledge += "OOP,";
            if (knowledges[1].equals("1")) knowledge += "Java,";
            if (knowledges[2].equals("1")) knowledge += "C/C++,";
            if (knowledges[3].equals("1")) knowledge += "C#";

            knowledgesList.setText(knowledge);

            answer = (TextView) findViewById(R.id.answer);
            answer.setText("DA");
            answer.setGravity(Gravity.LEFT);



        }
    }
}
