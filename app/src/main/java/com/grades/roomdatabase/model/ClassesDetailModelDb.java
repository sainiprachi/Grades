package com.grades.roomdatabase.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "classesDetail")
public class ClassesDetailModelDb {

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    @PrimaryKey(autoGenerate = true)
    public int Id;


    public int getGradebookNumber() {
        return gradebookNumber;
    }

    public void setGradebookNumber(int gradebookNumber) {
        this.gradebookNumber = gradebookNumber;
    }

    public int getAssignmentNumber() {
        return assignmentNumber;
    }

    public void setAssignmentNumber(int assignmentNumber) {
        this.assignmentNumber = assignmentNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSeekBarVisible() {
        return isSeekBarVisible;
    }

    public void setSeekBarVisible(boolean seekBarVisible) {
        isSeekBarVisible = seekBarVisible;
    }

    public boolean isGraded() {
        return isGraded;
    }

    public void setGraded(boolean graded) {
        isGraded = graded;
    }

    public boolean isScoreVisibleToParents() {
        return isScoreVisibleToParents;
    }

    public void setScoreVisibleToParents(boolean scoreVisibleToParents) {
        isScoreVisibleToParents = scoreVisibleToParents;
    }

    public boolean isScoreValueACheckMark() {
        return isScoreValueACheckMark;
    }

    public void setScoreValueACheckMark(boolean scoreValueACheckMark) {
        isScoreValueACheckMark = scoreValueACheckMark;
    }

    public int getNumberCorrect() {
        return numberCorrect;
    }

    public void setNumberCorrect(int numberCorrect) {
        this.numberCorrect = numberCorrect;
    }

    public int getNumberPossible() {
        return numberPossible;
    }

    public void setNumberPossible(int numberPossible) {
        this.numberPossible = numberPossible;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public String getDateAssigned() {
        return dateAssigned;
    }

    public void setDateAssigned(String dateAssigned) {
        this.dateAssigned = dateAssigned;
    }

    public String getDateDue() {
        return dateDue;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }

    public String getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(String dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public String getRubricAssignment() {
        return rubricAssignment;
    }

    public void setRubricAssignment(String rubricAssignment) {
        this.rubricAssignment = rubricAssignment;
    }

    @ColumnInfo(name = "gradebookNumber")
    private int gradebookNumber;

    @ColumnInfo(name = "assignmentNumber")
    private int assignmentNumber;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "isSeekBarVisible")
    private boolean isSeekBarVisible;

    @ColumnInfo(name = "isGraded")
    private boolean isGraded;

    @ColumnInfo(name = "isScoreVisibleToParents")
    private boolean isScoreVisibleToParents;

    @ColumnInfo(name = "isScoreValueACheckMark")
    private boolean isScoreValueACheckMark;

    @ColumnInfo(name = "numberCorrect")
    private int numberCorrect;

    @ColumnInfo(name = "numberPossible")
    private int numberPossible;

    @ColumnInfo(name = "mark")
    private String mark;

    @ColumnInfo(name ="score")
    private int score;

    @ColumnInfo(name = "maxScore")
    private int maxScore;

    @ColumnInfo(name ="percent")
    private float percent;

    @ColumnInfo(name = "dateAssigned")
    private String dateAssigned;

    @ColumnInfo(name = "dateDue")
    private String dateDue;

    @ColumnInfo(name = "dateCompleted")
    private String dateCompleted;

    @ColumnInfo(name = "rubricAssignment")
    private String rubricAssignment;
}
