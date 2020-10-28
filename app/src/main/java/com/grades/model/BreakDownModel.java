package com.grades.model;

import java.util.List;

public class BreakDownModel {

    /**
     * d : {"results":[{"type":"0","category":"Formative","percentOfGrade":0,"numberOfPoints":15,"maxPoints":15,"percentEarned":100,"mark":"A+","isShowingFinalMark":true,"isHidingOverallScore":false,"isDoingWeight":false,"doingRubric":"False"},{"type":"1","category":"Summative","percentOfGrade":0,"numberOfPoints":0,"maxPoints":0,"percentEarned":0,"mark":"","isShowingFinalMark":true,"isHidingOverallScore":false,"isDoingWeight":false,"doingRubric":"False"},{"type":"TOTAL","category":"Total","percentOfGrade":0,"numberOfPoints":15,"maxPoints":15,"percentEarned":100,"mark":"A+","isShowingFinalMark":true,"isHidingOverallScore":false,"isDoingWeight":false,"doingRubric":"False"}]}
     */

    private DBean d;

    public DBean getD() {
        return d;
    }

    public void setD(DBean d) {
        this.d = d;
    }

    public static class DBean {
        private List<ResultsBean> results;

        public List<ResultsBean> getResults() {
            return results;
        }

        public void setResults(List<ResultsBean> results) {
            this.results = results;
        }

        public static class ResultsBean {
            /**
             * type : 0
             * category : Formative
             * percentOfGrade : 0
             * numberOfPoints : 15
             * maxPoints : 15
             * percentEarned : 100
             * mark : A+
             * isShowingFinalMark : true
             * isHidingOverallScore : false
             * isDoingWeight : false
             * doingRubric : False
             */

            private String type;
            private String category;
            private double percentOfGrade;
            private double numberOfPoints;
            private double maxPoints;
            private float percentEarned;
            private String mark;
            private boolean isShowingFinalMark;
            private boolean isHidingOverallScore;
            private boolean isDoingWeight;
            private String doingRubric;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getCategory() {
                return category;
            }

            public void setCategory(String category) {
                this.category = category;
            }

            public double getPercentOfGrade() {
                return percentOfGrade;
            }

            public void setPercentOfGrade(double percentOfGrade) {
                this.percentOfGrade = percentOfGrade;
            }

            public double getNumberOfPoints() {
                return numberOfPoints;
            }

            public void setNumberOfPoints(double numberOfPoints) {
                this.numberOfPoints = numberOfPoints;
            }

            public double getMaxPoints() {
                return maxPoints;
            }

            public void setMaxPoints(double maxPoints) {
                this.maxPoints = maxPoints;
            }

            public float getPercentEarned() {
                return percentEarned;
            }

            public void setPercentEarned(float percentEarned) {
                this.percentEarned = percentEarned;
            }

            public String getMark() {
                return mark;
            }

            public void setMark(String mark) {
                this.mark = mark;
            }

            public boolean isIsShowingFinalMark() {
                return isShowingFinalMark;
            }

            public void setIsShowingFinalMark(boolean isShowingFinalMark) {
                this.isShowingFinalMark = isShowingFinalMark;
            }

            public boolean isIsHidingOverallScore() {
                return isHidingOverallScore;
            }

            public void setIsHidingOverallScore(boolean isHidingOverallScore) {
                this.isHidingOverallScore = isHidingOverallScore;
            }

            public boolean isIsDoingWeight() {
                return isDoingWeight;
            }

            public void setIsDoingWeight(boolean isDoingWeight) {
                this.isDoingWeight = isDoingWeight;
            }

            public String getDoingRubric() {
                return doingRubric;
            }

            public void setDoingRubric(String doingRubric) {
                this.doingRubric = doingRubric;
            }
        }
    }
}
