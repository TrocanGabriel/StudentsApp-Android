package com.gabi.homeworkcourse2.model;

/**
 * Created by gabi on 5/23/2017.
 */

public class Student {

    private int id;
    private String name;
    private String nickname;
    private String email;
    private int phone;
    private String university;
    private String knowledge;

    public Student(String name,String nickname, String email, int phone, String university, String knowledge) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.university = university;
        this.knowledge = knowledge;
    }
    public Student(int id, String name,String nickname, String email, int phone, String university, String knowledge) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.phone = phone;
        this.university = university;
        this.knowledge = knowledge;
    }

    public Student() {

    }

    public String getKnowledge() {
        return knowledge;
    }

    public void setKnowledge(String knowledge) {
        this.knowledge = knowledge;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", university='" + university + '\'' +
                ", knowledges='" + knowledge + '\'' +
                '}';
    }


}
