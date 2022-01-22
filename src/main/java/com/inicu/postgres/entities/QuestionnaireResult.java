package com.inicu.postgres.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
@Table(name = "questionnaire_result")
@NamedQuery(name = "QuestionnaireResult.findAll",query = "SELECT a FROM QuestionnaireResult a")
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionnaireResult {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name ="questionnaire_id")
    private Long questionnaireId;

    private String uhid;
    private String submit_date;

    private String answer_1;
    private String answer_2;
    private String answer_3;
    private String answer_4;

    public QuestionnaireResult() {
    }

    public String getUhid() {
        return uhid;
    }

    public void setUhid(String uhid) {
        this.uhid = uhid;
    }

    public String getSubmit_date() {
        return submit_date;
    }

    public void setSubmit_date(String submit_date) {
        this.submit_date = submit_date;
    }

    public Long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public String getAnswer_1() {
        return answer_1;
    }

    public void setAnswer_1(String answer_1) {
        this.answer_1 = answer_1;
    }

    public String getAnswer_2() {
        return answer_2;
    }

    public void setAnswer_2(String answer_2) {
        this.answer_2 = answer_2;
    }

    public String getAnswer_3() {
        return answer_3;
    }

    public void setAnswer_3(String answer_3) {
        this.answer_3 = answer_3;
    }

    public String getAnswer_4() {
        return answer_4;
    }

    public void setAnswer_4(String answer_4) {
        this.answer_4 = answer_4;
    }
}
