package com.grades.roomdatabase.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "classes")
public class ClassModel implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int Id;


    public int getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(int studentNumber) {
        this.studentNumber = studentNumber;
    }

    @ColumnInfo(name = "studentNumber")
    private int studentNumber;

    @ColumnInfo(name = "gradebookNumberTerm")
    private String gradebookNumberTerm;

    @ColumnInfo(name = "gradebookNumber")
    private int gradebookNumber;

    @ColumnInfo(name = "term")
    private String term;

    @ColumnInfo(name = "code")
    private String code;

    @ColumnInfo(name = "period")
    private int period;

    @ColumnInfo(name = "mark")
    private String mark;

    @ColumnInfo(name = "className")
    private String className;

    public String getClassespreviousName() {
        return classespreviousName;
    }

    public void setClassespreviousName(String classespreviousName) {
        this.classespreviousName = classespreviousName;
    }

    @ColumnInfo(name = "classespreviousName")
    private String classespreviousName;


    @ColumnInfo(name = "missingAssignments")
    private int missingAssignments;

    @ColumnInfo(name = "updated")
    private String updated;

    public boolean isSelectAlpha() {
        return isSelectAlpha;
    }

    public void setSelectAlpha(boolean selectAlpha) {
        isSelectAlpha = selectAlpha;
    }

    private boolean isSelectAlpha;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getGradebookNumberTerm() {
        return gradebookNumberTerm;
    }

    public void setGradebookNumberTerm(String gradebookNumberTerm) {
        this.gradebookNumberTerm = gradebookNumberTerm;
    }

    public int getGradebookNumber() {
        return gradebookNumber;
    }

    public void setGradebookNumber(int gradebookNumber) {
        this.gradebookNumber = gradebookNumber;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getMissingAssignments() {
        return missingAssignments;
    }

    public void setMissingAssignments(int missingAssignments) {
        this.missingAssignments = missingAssignments;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getTrendDirection() {
        return trendDirection;
    }

    public void setTrendDirection(String trendDirection) {
        this.trendDirection = trendDirection;
    }

    public int getPercentGrade() {
        return percentGrade;
    }

    public void setPercentGrade(int percentGrade) {
        this.percentGrade = percentGrade;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isUsingCheckMarks() {
        return isUsingCheckMarks;
    }

    public void setUsingCheckMarks(boolean usingCheckMarks) {
        isUsingCheckMarks = usingCheckMarks;
    }

    public boolean isHideOverallScore() {
        return hideOverallScore;
    }

    public void setHideOverallScore(boolean hideOverallScore) {
        this.hideOverallScore = hideOverallScore;
    }

    public boolean isShowFinalMark() {
        return showFinalMark;
    }

    public void setShowFinalMark(boolean showFinalMark) {
        this.showFinalMark = showFinalMark;
    }

    public boolean isDoingRubric() {
        return doingRubric;
    }

    public void setDoingRubric(boolean doingRubric) {
        this.doingRubric = doingRubric;
    }

    @ColumnInfo(name = "trendDirection")
    private String trendDirection;

    @ColumnInfo(name = "percentGrade")
    private int percentGrade;

    @ColumnInfo(name ="comment")
    private String comment;

    @ColumnInfo(name = "isUsingCheckMarks")
    private boolean isUsingCheckMarks;

    @ColumnInfo(name = "hideOverallScore")
    private boolean hideOverallScore;

    @ColumnInfo(name = "showFinalMark")
    private boolean showFinalMark;

    @ColumnInfo(name = "doingRubric")
    private boolean doingRubric;
}
