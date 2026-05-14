/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beleris.es.finalprimaryclasses;

/**
 *
 * @author edavid
 */
public class NQuestions {

    NQuestions(int tmpid_questions, String tmpquestion_name, String tmpquestion_recomendation, int tmpid_questiontype) {
        id_questions = tmpid_questions;
        question_name = tmpquestion_name;
        question_recomendation = tmpquestion_recomendation;
        id_questiontype = tmpid_questiontype;
    }
    private int id_questions = 0;
    private String question_name = "";
    private String question_recomendation = "";
    private int id_questiontype = 0;

    /**
     *
     */
    public NQuestions() {

    }

    /**
     * @return the id_questions
     */
    public int getId_questions() {
        return id_questions;
    }

    /**
     * @param id_questions the id_questions to set
     */
    public void setId_questions(int id_questions) {
        this.id_questions = id_questions;
    }

    /**
     * @return the question_name
     */
    public String getQuestion_string() {
        return question_name;
    }

    /**
     * @param question_name the question_name to set
     */
    public void setQuestion_string(String question_name) {
        this.question_name = question_name;
    }

    /**
     * @return the question_recomendation
     */
    public String getQuestion_recomendation() {
        return question_recomendation;
    }

    /**
     * @param question_recomendation the question_recomendation to set
     */
    public void setQuestion_recomendation(String question_recomendation) {
        this.question_recomendation = question_recomendation;
    }

    /**
     * @return the id_questiontype
     */
    public int getId_questiontype() {
        return id_questiontype;
    }

    /**
     * @param id_questiontype the id_questiontype to set
     */
    public void setId_questiontype(int id_questiontype) {
        this.id_questiontype = id_questiontype;
    }

}
