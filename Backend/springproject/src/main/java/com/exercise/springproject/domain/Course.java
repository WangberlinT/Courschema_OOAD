package com.exercise.springproject.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Course", schema = "mydb", catalog = "")
public class Course {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int idCourse;
    private String BianHao;
    private String chineseName;
    private String intro;
    private double credit;
    private byte spring;
    private byte autumn;
    private byte summer;
    private String englishName;
    private int nian;
    private int department;
    private String yuyan;
    private String department_name;
    private int experiment;
    private int weektime;
    private String xianxiu;

    public int getIdCourse() {
        return idCourse;
    }


    @Basic
    @Column(name = "YuYan")
    public void setYuyan(String y){
        this.yuyan = y;
    }
    public String getYuyan(){
        return this.yuyan;
    }

    @Basic
    @Column(name = "bian_hao")
    public String getBianHao() {
        return BianHao;
    }

    public void setBianHao(String bianHao) {
        this.BianHao = bianHao;
    }

    @Basic
    @Column(name = "ChineseName")
    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    @Basic
    @Column(name = "Intro")
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Basic
    @Column(name = "Credit")
    public double getCredit() {
        return credit;
    }

    public void setCredit(double credit) {
        this.credit = credit;
    }

    @Basic
    @Column(name = "Spring")
    public byte getSpring() {
        return spring;
    }

    public void setSpring(byte spring) {
        this.spring = spring;
    }

    @Basic
    @Column(name = "Autumn")
    public byte getAutumn() {
        return autumn;
    }

    public void setAutumn(byte autumn) {
        this.autumn = autumn;
    }

    @Basic
    @Column(name = "Summer")
    public byte getSummer() {
        return summer;
    }

    public void setSummer(byte summer) {
        this.summer = summer;
    }

    @Basic
    @Column(name = "EnglishName")
    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    @Basic
    @Column(name = "nian")
    public int getNian() {
        return nian;
    }

    public void setNian(int n) {
        this.nian = n;
    }

    @Basic
    @Column(name = "Department")
    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    @Basic
    @Column(name = "experiment")
    public int getExperiment() {
        return experiment;
    }

    public void setExperiment(int e) {
        this.experiment = e;
    }

    @Basic
    @Column(name = "week_time")
    public int getWeektime() {
        return weektime;
    }

    public void setWeektime(int w) {
        this.weektime = w;
    }

    @Basic
    @Column(name = "Department_name")
    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }

    @Basic
    @Column(name = "xianxiu")
    public String getXianxiu() {
        return xianxiu;
    }

    public void setXianxiu(String xianxiu) {
        this.xianxiu = xianxiu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course that = (Course) o;
        return idCourse == that.idCourse &&
                spring == that.spring &&
                autumn == that.autumn &&
                summer == that.summer &&
                Objects.equals(BianHao, that.BianHao) &&
                Objects.equals(chineseName, that.chineseName) &&
                Objects.equals(intro, that.intro) &&
                Objects.equals(credit, that.credit) &&
                Objects.equals(englishName, that.englishName) &&
                Objects.equals(nian, that.nian);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCourse, BianHao, chineseName, intro, credit, spring, autumn, summer, englishName, nian);

    }
}
