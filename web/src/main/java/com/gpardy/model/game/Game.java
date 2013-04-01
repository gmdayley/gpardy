package com.gpardy.model.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 4/1/11
 * Time: 9:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class Game {
    private String gameName;
    private String code;

    private List<Round> rounds = new ArrayList<Round>();

    public Game(String gameName) {
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public void addRound(Round round){
        rounds.add(round);
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
