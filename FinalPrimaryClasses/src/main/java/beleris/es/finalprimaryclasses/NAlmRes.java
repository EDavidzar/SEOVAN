/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beleris.es.finalprimaryclasses;

import java.math.BigInteger;

/**
 *
 * @author edavid
 */
public class NAlmRes {

    /**
     *
     */
    public NAlmRes() {

    }

    /**
     *
     * @param tmpid_answers
     * @param tmnpexcluded
     * @param tmpid_client
     * @param tmpid_question
     * @param tmptheanswer
     */
    public NAlmRes(int tmpid_answers, BigInteger tmnpexcluded, int tmpid_client, int tmpid_question, BigInteger tmptheanswer) {
        id_answers = tmpid_answers;
        excluded = tmnpexcluded;
        id_client = tmpid_client;
        id_question = tmpid_question;
        theanswer = tmptheanswer;
    }
    private int id_answers = 0;
    private BigInteger excluded;
    private int id_client = 0;
    private int id_question = 0;
    private BigInteger theanswer;

    /**
     * @return the id_answers
     */
    public int getId_answers() {
        return id_answers;
    }

    /**
     * @param id_answers the id_answers to set
     */
    public void setId_answers(int id_answers) {
        this.id_answers = id_answers;
    }

    /**
     * @return the excluded
     */
    public BigInteger getExcluded() {
        return excluded;
    }

    /**
     * @param excluded the excluded to set
     */
    public void setExcluded(BigInteger excluded) {
        this.excluded = excluded;
    }

    /**
     * @return the id_client
     */
    public int getId_client() {
        return id_client;
    }

    /**
     * @param id_client the id_client to set
     */
    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    /**
     * @return the id_question
     */
    public int getId_question() {
        return id_question;
    }

    /**
     * @param id_question the id_question to set
     */
    public void setId_question(int id_question) {
        this.id_question = id_question;
    }

    /**
     * @return the theanswer
     */
    public BigInteger getTheanswer() {
        return theanswer;
    }

    /**
     * @param theanswer the theanswer to set
     */
    public void setTheanswer(BigInteger theanswer) {
        this.theanswer = theanswer;
    }

}
