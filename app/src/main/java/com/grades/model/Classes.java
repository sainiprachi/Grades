package com.grades.model;


import java.util.List;

public class Classes {


    /**
     * d : {"results":[{"gradebookNumberTerm":"6859331_F","gradebookNumber":6859331,"term":"F","code":"","period":1,"mark":"","className":"Latin 3 per. 1 - Fall semester","missingAssignments":0,"updated":"/Date(-62135568000000)/","trendDirection":null,"percentGrade":0,"comment":"","isUsingCheckMarks":false,"hideOverallScore":false,"showFinalMark":true,"doingRubric":false},{"gradebookNumberTerm":"6626405_F","gradebookNumber":6626405,"term":"F","code":"","period":2,"mark":"B","className":"AP Calculus AB - Fall semester","missingAssignments":0,"updated":"/Date(1600051521780)/","trendDirection":null,"percentGrade":83,"comment":"","isUsingCheckMarks":false,"hideOverallScore":false,"showFinalMark":true,"doingRubric":false},{"gradebookNumberTerm":"5214286_F","gradebookNumber":5214286,"term":"F","code":"","period":3,"mark":"A","className":"AP Physics 1/2A - Fall semester","missingAssignments":0,"updated":"/Date(1600119190310)/","trendDirection":"SAME","percentGrade":93,"comment":"","isUsingCheckMarks":false,"hideOverallScore":false,"showFinalMark":true,"doingRubric":false},{"gradebookNumberTerm":"7111649_F","gradebookNumber":7111649,"term":"F","code":"","period":5,"mark":"A+","className":"AP Eng Lang A - Fall semester","missingAssignments":0,"updated":"/Date(1599856851787)/","trendDirection":null,"percentGrade":100,"comment":"","isUsingCheckMarks":false,"hideOverallScore":false,"showFinalMark":true,"doingRubric":false},{"gradebookNumberTerm":"1587559_F","gradebookNumber":1587559,"term":"F","code":"","period":6,"mark":"","className":"GraphicDesign A - Fall semester","missingAssignments":0,"updated":"/Date(1600113794133)/","trendDirection":null,"percentGrade":0,"comment":"","isUsingCheckMarks":false,"hideOverallScore":false,"showFinalMark":true,"doingRubric":false},{"gradebookNumberTerm":"4263318_F","gradebookNumber":4263318,"term":"F","code":"","period":7,"mark":"A-","className":"AP Psychology A - Fall semester","missingAssignments":0,"updated":"/Date(1599754654533)/","trendDirection":null,"percentGrade":90,"comment":"","isUsingCheckMarks":false,"hideOverallScore":false,"showFinalMark":true,"doingRubric":false}]}
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
             * gradebookNumberTerm : 6859331_F
             * gradebookNumber : 6859331
             * term : F
             * code :
             * period : 1
             * mark :
             * className : Latin 3 per. 1 - Fall semester
             * missingAssignments : 0
             * updated : /Date(-62135568000000)/
             * trendDirection : null
             * percentGrade : 0
             * comment :
             * isUsingCheckMarks : false
             * hideOverallScore : false
             * showFinalMark : true
             * doingRubric : false
             */

            private String gradebookNumberTerm;
            private int gradebookNumber;
            private String term;
            private String code;
            private int period;
            private String mark;
            private String className;
            private int missingAssignments;
            private String updated;
            private Object trendDirection;
            private int percentGrade;
            private String comment;
            private boolean isUsingCheckMarks;
            private boolean hideOverallScore;
            private boolean showFinalMark;
            private boolean doingRubric;

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

            public Object getTrendDirection() {
                return trendDirection;
            }

            public void setTrendDirection(Object trendDirection) {
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

            public boolean isIsUsingCheckMarks() {
                return isUsingCheckMarks;
            }

            public void setIsUsingCheckMarks(boolean isUsingCheckMarks) {
                this.isUsingCheckMarks = isUsingCheckMarks;
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
        }
    }
}



