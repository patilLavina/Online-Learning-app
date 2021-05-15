package com.example.eedu.QuestionDatabase;

public class QuestionHelperClass {
    String question;
    String qa;
    String qb;
    String qc;
    String qd;
    String ans;

    public QuestionHelperClass(){

    }

    public QuestionHelperClass(String question, String qa, String qb, String qc, String qd, String ans) {
        this.question = question;
        this.qa = qa;
        this.qb = qb;
        this.qc = qc;
        this.qd = qd;
        this.ans = ans;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQa() {
        return qa;
    }

    public void setQa(String qa) {
        this.qa = qa;
    }

    public String getQb() {
        return qb;
    }

    public void setQb(String qb) {
        this.qb = qb;
    }

    public String getQc() {
        return qc;
    }

    public void setQc(String qc) {
        this.qc = qc;
    }

    public String getQd() {
        return qd;
    }

    public void setQd(String qd) {
        this.qd = qd;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }
}
