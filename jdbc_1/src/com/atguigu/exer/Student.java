package com.atguigu.exer;

import javax.sound.midi.Soundbank;

/**
 * Type:
 *     IDCard:
 *     ExamCard:
 *     StudentName:
 *     Location:
 *     Grade:
 * @author Cynicism
 * @Package_name
 * @since 2020/6/25 16:03
 */
public class Student {
    private int flowID;
    private int type;
    private String IDcard;
    private String examCard;
    private String name;
    private String location;
    private int grade;

    public Student() {
    }

    public Student(int flowID, int type, String IDcard, String examCard, String name, String location, int grade) {
        this.flowID = flowID;
        this.type = type;
        this.IDcard = IDcard;
        this.examCard = examCard;
        this.name = name;
        this.location = location;
        this.grade = grade;
    }

    public int getFlowID() {
        return flowID;
    }

    public void setFlowID(int flowID) {
        this.flowID = flowID;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIDcard() {
        return IDcard;
    }

    public void setIDcard(String IDcard) {
        this.IDcard = IDcard;
    }

    public String getExamCard() {
        return examCard;
    }

    public void setExamCard(String examCard) {
        this.examCard = examCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        System.out.println("==============查询结果================");

        return "流水号:" + flowID + "\n身份证号:" + IDcard+"\n准考证号：" + examCard+"\n学生姓名：" + name+"\n所在城市：" + location+"\n考试成绩：" + grade;
    }

}
