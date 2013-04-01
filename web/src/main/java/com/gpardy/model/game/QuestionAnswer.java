package com.gpardy.model.game;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 4/5/11
 * Time: 8:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class QuestionAnswer {
    private String questionText;
    private String answerText;
    private int value;

    public QuestionAnswer(String questionText, String answerText, int value) {
        this.questionText = questionText;
        this.answerText = answerText;
        this.value = value;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
