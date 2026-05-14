/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beleris.es.finalprimaryclasses;

/**
 *
 * @author edavid
 */
public class NTypeQuestions {

    /**
     *
     */
    public NTypeQuestions() {

    }

    NTypeQuestions(int tmpid_question_types, String tmpquestion_types_name) {
        id_question_types = tmpid_question_types;
        question_types_name = tmpquestion_types_name;
    }
    private int id_question_types = 0;
    private String question_types_name = "";

    /**
     * @return the id_question_types
     */
    public int getId_question_types() {
        return id_question_types;
    }

    /**
     * @param id_question_types the id_question_types to set
     */
    public void setId_question_types(int id_question_types) {
        this.id_question_types = id_question_types;
    }

    /**
     * @return the question_types_name
     */
    public String getQuestion_types_name() {
        return question_types_name;
    }

    /**
     * @param question_types_name the question_types_name to set
     */
    public void setQuestion_types_name(String question_types_name) {
        this.question_types_name = question_types_name;
    }

}
