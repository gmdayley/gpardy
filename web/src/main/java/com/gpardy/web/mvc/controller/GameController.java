package com.gpardy.web.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 4/1/11
 * Time: 9:10 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class GameController {

    @RequestMapping(value = "/game")
    public String game(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "game";
    }

    @RequestMapping(value = "/admin")
    public String admin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return "admin";
    }


}
