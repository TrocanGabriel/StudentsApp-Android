package com.gabi.homeworkcourse2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.util.Log;


import com.gabi.homeworkcourse2.model.Student;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.provider.Contacts.SettingsColumns.KEY;


//import static android.R.attr.country;
//import static android.R.attr.version;
//import static android.R.attr.y;

/**
 * Created by gabi on 5/27/2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");


    // Database Name
    private static final String DATABASE_NAME = "StudentsManager";

    // Students table name
    private static final String TABLE_STUDENTS = "Student";

    // Students Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_NICKNAME = "nickname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_UNIVERSITY ="university";
    private static final String KEY_KNOWLEDGE = "knowledge";

    private static final String TAG = "DBHelper";

    private static DatabaseHandler sInstance;

//    //Students knowledge table name
//    private static final String TABLE_KNOWLEDGE ="Knowledge";
//
//    // Students Knowledge
//    private static final String KEY_KL_ID ="id";
//    private static final String KEY_KNOWLEDGE = "knowledge";


    public static synchronized DatabaseHandler getInstance(Context context) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        if (sInstance == null) {
            sInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_Students_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_STUDENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME + " TEXT," + KEY_NICKNAME + " TEXT,"
                 + KEY_EMAIL + " TEXT," + KEY_PHONE + " INTEGER,"
                    + KEY_UNIVERSITY + " TEXT," + KEY_KNOWLEDGE + " TEXT" +")";
        db.execSQL(CREATE_Students_TABLE);

//        String CREATE_Knowledge_TABLE = "CREATE TABLE " + TABLE_KNOWLEDGE + "("
//                + KEY_KL_ID + " INTEGER ," + KEY_KNOWLEDGE + " TEXT, " +
//                "PRIMARY KEY(" + KEY_KL_ID + ", " + KEY_KNOWLEDGE   + ")"
//                 + ")";
//        db.execSQL(CREATE_Knowledge_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table students if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);

        // Drop older table knowledge if existed
//        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KNOWLEDGE);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    //Adding new Student
    public void addStudent (Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, student.getName()); // Student Name
            values.put(KEY_NICKNAME, student.getNickname()); // Student NickName
            values.put(KEY_EMAIL, student.getEmail()); // Student Email
            values.put(KEY_PHONE, student.getPhone()); // Student Phone
            values.put(KEY_UNIVERSITY, student.getUniversity()); // Student University
            values.put(KEY_KNOWLEDGE, student.getKnowledge()); // Student Knowledge


            // Inserting Row
            db.insert(TABLE_STUDENTS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add country to database");
        } finally {
            db.endTransaction();
        }
    }

//    public void addKnowledge (Knowledge knowledge){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        db.beginTransaction();
//        try {
//            ContentValues values = new ContentValues();
////            values.put(KEY_KL_ID, knowledge.getId()); // Student ID
//            values.put(KEY_KNOWLEDGE, knowledge.getKnowledge()); // Student Knowledge
//
//            // Inserting Row
//            db.insert(TABLE_KNOWLEDGE, null, values);
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            Log.d(TAG, "Error while trying to add country to database");
//        } finally {
//            db.endTransaction();
//        }
//    }

    // Getting single Student by Id
   public Student getStudent(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Student student = null;

        Cursor cursor = db.query(TABLE_STUDENTS, new String[]{KEY_ID,
                        KEY_NAME,KEY_NICKNAME, KEY_EMAIL,KEY_PHONE,KEY_UNIVERSITY,KEY_KNOWLEDGE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        try {
            if (cursor != null) {
                cursor.moveToFirst();
                student = new Student(Integer.parseInt(cursor.getString(0)),cursor.getString(1),
                        cursor.getString(2),cursor.getString(3),Integer.parseInt(cursor.getString(4)),
                        cursor.getString(5),cursor.getString(6));
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get students from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // return Student
        return student;
    }

//        Knowledge getKnowledge(int id) {
//            SQLiteDatabase db = this.getReadableDatabase();
//            Knowledge knowledge = null;
//
//            Cursor cursor = db.query(TABLE_KNOWLEDGE, new String[]{KEY_KL_ID, KEY_KNOWLEDGE},
//                    KEY_KL_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
//
//            try {
//                if (cursor != null) {
//                    cursor.moveToFirst();
//                    knowledge = new Knowledge(Integer.parseInt(cursor.getString(0)), cursor.getString(1));
//                }
//            } catch (Exception e) {
//                Log.d(TAG, "Error while trying to get the knowledge of certain student: " + id);
//            } finally {
//                if (cursor != null && !cursor.isClosed()) {
//                    cursor.close();
//                }
//            }
//                return knowledge;
//        }

    // Getting All Students
    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<Student>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_STUDENTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        Student student = null;
        try {
            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    student = new Student();
                    student.setId(Integer.parseInt(cursor.getString(0)));
                    student.setName(cursor.getString(1));
                    student.setNickname(cursor.getString(2));
                    student.setEmail(cursor.getString(3));
                    student.setPhone(Integer.parseInt(cursor.getString(4)));
                    student.setUniversity(cursor.getString(5));
                    student.setKnowledge(cursor.getString(6));

                    // Adding Country to list
                    studentList.add(student);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get students from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // return Student list
        return studentList;
    }

    // Updating single Student
    public void updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_NAME, student.getName());
            values.put(KEY_NICKNAME, student.getNickname());
            values.put(KEY_EMAIL, student.getEmail());
            values.put(KEY_PHONE, student.getPhone());
            values.put(KEY_UNIVERSITY, student.getUniversity());
            values.put(KEY_KNOWLEDGE, student.getKnowledge());


            // updating row
            db.update(TABLE_STUDENTS, values, KEY_ID + " = ?",
                    new String[]{String.valueOf(student.getId())});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to update student");
        } finally {
            db.endTransaction();
        }
    }

    // Updating single Knowledge
//    public void updateKnowledge(Knowledge knowledge) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.beginTransaction();
//        try {
//            ContentValues values = new ContentValues();
//            values.put(KEY_KNOWLEDGE, knowledge.getKnowledge());
//
//
//            // updating row
//            db.update(TABLE_KNOWLEDGE, values, KEY_KL_ID + " = ?",
//                    new String[]{String.valueOf(knowledge.getId())});
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            Log.d(TAG, "Error while trying to update knowledge");
//        } finally {
//            db.endTransaction();
//        }
//    }

    // Deleting single Student
    public void deleteStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_STUDENTS, KEY_ID + " = ?",
                    new String[]{String.valueOf(student.getId())});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to delete student");
        } finally {
            db.endTransaction();
        }
    }

    // Deleting single Knowledge
//    public void deleteKnowledge(Knowledge knowledge) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.beginTransaction();
//        try {
//            db.delete(TABLE_KNOWLEDGE, KEY_KL_ID + " = ?",
//                    new String[]{String.valueOf(knowledge.getId())});
//            db.setTransactionSuccessful();
//        } catch (Exception e) {
//            Log.d(TAG, "Error while trying to delete knowledge");
//        } finally {
//            db.endTransaction();
//        }
//    }


    // Getting Students Count
    public int getStudentsCount() {
        int count = 0;
        String countQuery = "SELECT * FROM " + TABLE_STUDENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        try {
            count = cursor.getCount();
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get students from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return count;
    }

}
