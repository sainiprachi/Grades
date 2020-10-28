package com.grades.model;

import java.util.List;

public class ClassesDetailModel {


    /**
     * d : {"results":[{"gradebookNumber":6626405,"assignmentNumber":8,"description":"Sept 4 - p. 91 /Wksht5","type":"Homework","isGraded":true,"isScoreVisibleToParents":true,"isScoreValueACheckMark":false,"numberCorrect":2,"numberPossible":2,"mark":"2","score":2,"maxScore":2,"percent":100,"dateAssigned":"/Date(1599116400000)/","dateDue":"/Date(1599721200000)/","dateCompleted":"/Date(1599116400000)/","rubricAssignment":"False"},{"gradebookNumber":6626405,"assignmentNumber":7,"description":"Chapter P/1 Test","type":"Tests","isGraded":true,"isScoreVisibleToParents":true,"isScoreValueACheckMark":false,"numberCorrect":41,"numberPossible":50,"mark":"41","score":41,"maxScore":50,"percent":82,"dateAssigned":"/Date(1599807600000)/","dateDue":"/Date(1599807600000)/","dateCompleted":"/Date(1599807600000)/","rubricAssignment":"False"},{"gradebookNumber":6626405,"assignmentNumber":6,"description":"Calc AB Ch. P/1 Quiz","type":"Quizzes","isGraded":true,"isScoreVisibleToParents":true,"isScoreValueACheckMark":false,"numberCorrect":12,"numberPossible":14,"mark":"12","score":12,"maxScore":14,"percent":85.7143,"dateAssigned":"/Date(1599116400000)/","dateDue":"/Date(1599116400000)/","dateCompleted":"/Date(1599116400000)/","rubricAssignment":"False"},{"gradebookNumber":6626405,"assignmentNumber":5,"description":"Sept 3 - p. 88, wksht 4","type":"Homework","isGraded":true,"isScoreVisibleToParents":true,"isScoreValueACheckMark":false,"numberCorrect":1,"numberPossible":2,"mark":"1","score":1,"maxScore":2,"percent":50,"dateAssigned":"/Date(1599030000000)/","dateDue":"/Date(1599202800000)/","dateCompleted":"/Date(1599030000000)/","rubricAssignment":"False"},{"gradebookNumber":6626405,"assignmentNumber":4,"description":"Aug 28 - p. 78/wksht 2/3","type":"Homework","isGraded":true,"isScoreVisibleToParents":true,"isScoreValueACheckMark":false,"numberCorrect":2,"numberPossible":2,"mark":"2","score":2,"maxScore":2,"percent":100,"dateAssigned":"/Date(1598511600000)/","dateDue":"/Date(1599116400000)/","dateCompleted":"/Date(1598511600000)/","rubricAssignment":"False"},{"gradebookNumber":6626405,"assignmentNumber":3,"description":"Aug 21 - page 8/16","type":"Homework","isGraded":true,"isScoreVisibleToParents":true,"isScoreValueACheckMark":false,"numberCorrect":2,"numberPossible":2,"mark":"2","score":2,"maxScore":2,"percent":100,"dateAssigned":"/Date(1597906800000)/","dateDue":"/Date(1598511600000)/","dateCompleted":"/Date(1597906800000)/","rubricAssignment":"False"},{"gradebookNumber":6626405,"assignmentNumber":2,"description":"Aug 27 - p. 54/67/wksht 1","type":"Homework","isGraded":true,"isScoreVisibleToParents":true,"isScoreValueACheckMark":false,"numberCorrect":2,"numberPossible":2,"mark":"2","score":2,"maxScore":2,"percent":100,"dateAssigned":"/Date(1598425200000)/","dateDue":"/Date(1598598000000)/","dateCompleted":"/Date(1598425200000)/","rubricAssignment":"False"}],"total":7}
     */

    private DBean d;

    public DBean getD() {
        return d;
    }

    public void setD(DBean d) {
        this.d = d;
    }

    public static class DBean {
        /**
         * results : [{"gradebookNumber":6626405,"assignmentNumber":8,"description":"Sept 4 - p. 91 /Wksht5","type":"Homework","isGraded":true,"isScoreVisibleToParents":true,"isScoreValueACheckMark":false,"numberCorrect":2,"numberPossible":2,"mark":"2","score":2,"maxScore":2,"percent":100,"dateAssigned":"/Date(1599116400000)/","dateDue":"/Date(1599721200000)/","dateCompleted":"/Date(1599116400000)/","rubricAssignment":"False"},{"gradebookNumber":6626405,"assignmentNumber":7,"description":"Chapter P/1 Test","type":"Tests","isGraded":true,"isScoreVisibleToParents":true,"isScoreValueACheckMark":false,"numberCorrect":41,"numberPossible":50,"mark":"41","score":41,"maxScore":50,"percent":82,"dateAssigned":"/Date(1599807600000)/","dateDue":"/Date(1599807600000)/","dateCompleted":"/Date(1599807600000)/","rubricAssignment":"False"},{"gradebookNumber":6626405,"assignmentNumber":6,"description":"Calc AB Ch. P/1 Quiz","type":"Quizzes","isGraded":true,"isScoreVisibleToParents":true,"isScoreValueACheckMark":false,"numberCorrect":12,"numberPossible":14,"mark":"12","score":12,"maxScore":14,"percent":85.7143,"dateAssigned":"/Date(1599116400000)/","dateDue":"/Date(1599116400000)/","dateCompleted":"/Date(1599116400000)/","rubricAssignment":"False"},{"gradebookNumber":6626405,"assignmentNumber":5,"description":"Sept 3 - p. 88, wksht 4","type":"Homework","isGraded":true,"isScoreVisibleToParents":true,"isScoreValueACheckMark":false,"numberCorrect":1,"numberPossible":2,"mark":"1","score":1,"maxScore":2,"percent":50,"dateAssigned":"/Date(1599030000000)/","dateDue":"/Date(1599202800000)/","dateCompleted":"/Date(1599030000000)/","rubricAssignment":"False"},{"gradebookNumber":6626405,"assignmentNumber":4,"description":"Aug 28 - p. 78/wksht 2/3","type":"Homework","isGraded":true,"isScoreVisibleToParents":true,"isScoreValueACheckMark":false,"numberCorrect":2,"numberPossible":2,"mark":"2","score":2,"maxScore":2,"percent":100,"dateAssigned":"/Date(1598511600000)/","dateDue":"/Date(1599116400000)/","dateCompleted":"/Date(1598511600000)/","rubricAssignment":"False"},{"gradebookNumber":6626405,"assignmentNumber":3,"description":"Aug 21 - page 8/16","type":"Homework","isGraded":true,"isScoreVisibleToParents":true,"isScoreValueACheckMark":false,"numberCorrect":2,"numberPossible":2,"mark":"2","score":2,"maxScore":2,"percent":100,"dateAssigned":"/Date(1597906800000)/","dateDue":"/Date(1598511600000)/","dateCompleted":"/Date(1597906800000)/","rubricAssignment":"False"},{"gradebookNumber":6626405,"assignmentNumber":2,"description":"Aug 27 - p. 54/67/wksht 1","type":"Homework","isGraded":true,"isScoreVisibleToParents":true,"isScoreValueACheckMark":false,"numberCorrect":2,"numberPossible":2,"mark":"2","score":2,"maxScore":2,"percent":100,"dateAssigned":"/Date(1598425200000)/","dateDue":"/Date(1598598000000)/","dateCompleted":"/Date(1598425200000)/","rubricAssignment":"False"}]
         * total : 7
         */

        private int total;
        private List<ResultsBean> results;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ResultsBean> getResults() {
            return results;
        }

        public void setResults(List<ResultsBean> results) {
            this.results = results;
        }

        public static class ResultsBean {
            /**
             * gradebookNumber : 6626405
             * assignmentNumber : 8
             * description : Sept 4 - p. 91 /Wksht5
             * type : Homework
             * isGraded : true
             * isScoreVisibleToParents : true
             * isScoreValueACheckMark : false
             * numberCorrect : 2
             * numberPossible : 2
             * mark : 2
             * score : 2
             * maxScore : 2
             * percent : 100
             * dateAssigned : /Date(1599116400000)/
             * dateDue : /Date(1599721200000)/
             * dateCompleted : /Date(1599116400000)/
             * rubricAssignment : False
             */

            private int gradebookNumber;
            private int assignmentNumber;
            private String description;
            private String type;

            public boolean isSeekBarVisible() {
                return isSeekBarVisible;
            }

            public void setSeekBarVisible(boolean seekBarVisible) {
                isSeekBarVisible = seekBarVisible;
            }

            private boolean isSeekBarVisible;
            private boolean isGraded;
            private boolean isScoreVisibleToParents;
            private boolean isScoreValueACheckMark;
            private int numberCorrect;

            public boolean isAddedMock() {
                return isAddedMock;
            }

            public void setAddedMock(boolean addedMock) {
                isAddedMock = addedMock;
            }

            private boolean isAddedMock;
            private int numberPossible;
            private String mark;
            private double score;

            public boolean isShowImpact() {
                return isShowImpact;
            }

            public void setShowImpact(boolean showImpact) {
                isShowImpact = showImpact;
            }

            private boolean isShowImpact;
            private double maxScore;
            private double percent;
            private String dateAssigned;
            private String dateDue;
            private String dateCompleted;
            private String rubricAssignment;

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

            public boolean isIsGraded() {
                return isGraded;
            }

            public void setIsGraded(boolean isGraded) {
                this.isGraded = isGraded;
            }

            public boolean isIsScoreVisibleToParents() {
                return isScoreVisibleToParents;
            }

            public void setIsScoreVisibleToParents(boolean isScoreVisibleToParents) {
                this.isScoreVisibleToParents = isScoreVisibleToParents;
            }

            public boolean isIsScoreValueACheckMark() {
                return isScoreValueACheckMark;
            }

            public void setIsScoreValueACheckMark(boolean isScoreValueACheckMark) {
                this.isScoreValueACheckMark = isScoreValueACheckMark;
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

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public double getMaxScore() {
                return maxScore;
            }

            public void setMaxScore(double maxScore) {
                this.maxScore = maxScore;
            }

            public double getPercent() {
                return percent;
            }

            public void setPercent(double percent) {
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
        }
    }
}
