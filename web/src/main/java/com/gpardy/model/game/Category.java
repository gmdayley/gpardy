package com.gpardy.model.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 4/5/11
 * Time: 8:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class Category {
    private String name;
    private List<QuestionAnswer> questionAnswers = new ArrayList<QuestionAnswer>();

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<QuestionAnswer> getQuestionAnswers() {
        return questionAnswers;
    }

    public void setQuestionAnswers(List<QuestionAnswer> questionAnswers) {
        this.questionAnswers = questionAnswers;
    }

    public void addQuestionAnswer(QuestionAnswer questionAnswer){
        questionAnswers.add(questionAnswer);
    }
}
