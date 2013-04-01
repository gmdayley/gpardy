package com.gpardy.web.mvc.controller;

import com.google.appengine.api.channel.ChannelMessage;
import com.google.appengine.api.channel.ChannelService;
import com.google.appengine.api.channel.ChannelServiceFactory;
import com.google.appengine.api.datastore.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 8/1/11
 * Time: 11:09 PM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/channel")
public class ChannelController {

    //todo is this still being used? Yes. Yes it is.
    private String getChannelToken(String clientId) {
        ChannelService channelService = ChannelServiceFactory.getChannelService();
        return channelService.createChannel(clientId);
    }

    //todo is this still being used? Yes. Yes it is.
    @RequestMapping(value = "/token/{clientId}", method = RequestMethod.GET)
    public String getChannelToken(HttpServletRequest request, HttpServletResponse response, @PathVariable String clientId) throws IOException {
        String token = getChannelToken(clientId);

        response.setContentType("text/html");
        response.getWriter().write(token);
        response.getWriter().close();

        return null;
    }

    @RequestMapping(value = "/buzzin/{gameId}/{userName}", method = RequestMethod.GET)
    public void buzzIn(HttpServletRequest request, HttpServletResponse response, @PathVariable String gameId, @PathVariable String userName) throws IOException {
        String id = getUniqueId(request, response);

        System.out.println("BUZZIN");
        // Send response to caller
        response.setContentType("text/html");
//        if (checkUniqueName(id, gameId, userName)) {
            ChannelService channelService = ChannelServiceFactory.getChannelService();
            channelService.sendMessage(new ChannelMessage(gameId, "b" + id + ":" + userName));

            response.setStatus(200);
            response.getWriter().write("success");
//        } else {
//            response.setStatus(HttpServletResponse.SC_CONFLICT);
//            response.getWriter().write("conflict");
//        }
        response.getWriter().close();
    }

    @RequestMapping(value = "/selectq/{gameId}/{question}", method = RequestMethod.GET)
    public void selectQuestion(HttpServletRequest request, HttpServletResponse response, @PathVariable String gameId, @PathVariable String question) throws IOException {
        ChannelService channelService = ChannelServiceFactory.getChannelService();
        channelService.sendMessage(new ChannelMessage(gameId, "s" + question));

        // Send response to caller
        response.setContentType("text/html");
        response.setStatus(200);
        response.getWriter().write("success");
        response.getWriter().close();
    }

    @RequestMapping(value = "/timerstart/{gameId}", method = RequestMethod.GET)
    public void startTimer(HttpServletRequest request, HttpServletResponse response, @PathVariable String gameId) throws IOException {
        ChannelService channelService = ChannelServiceFactory.getChannelService();
        channelService.sendMessage(new ChannelMessage(gameId, "t"));

        // Send response to caller
        response.setContentType("text/html");
        response.setStatus(200);
        response.getWriter().write("success");
        response.getWriter().close();
    }


    @RequestMapping(value = "/join/{gameId}/{userName}", method = RequestMethod.GET)
    public void joinGame(HttpServletRequest request, HttpServletResponse response, @PathVariable String gameId, @PathVariable String userName) throws IOException {
        String id = getUniqueId(request, response);

        // Send response to caller
        response.setContentType("text/html");
//        if (checkUniqueName(id, gameId, userName)) {
            ChannelService channelService = ChannelServiceFactory.getChannelService();
            channelService.sendMessage(new ChannelMessage(gameId, "j" + id + ":" + userName));

            response.setStatus(200);
            response.getWriter().write("success");
//        } else {
//            response.setStatus(HttpServletResponse.SC_CONFLICT);
//            response.getWriter().write("conflict");
//        }
        response.getWriter().close();
    }

//    private boolean checkUniqueName(String id, String gameId, String userName) {
//        final DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();
//        Query q = new Query("names");
//        q.addFilter("gameId", Query.FilterOperator.EQUAL, gameId);
//        q.addFilter("userName", Query.FilterOperator.EQUAL, userName);
//        final PreparedQuery preparedQuery = datastoreService.prepare(q);
//        for (Entity e : preparedQuery.asIterable()) {
//            if (e.getProperty("id").equals(id)) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//        Entity entity = new Entity("names");
//        entity.setProperty("gameId", gameId);
//        entity.setProperty("userName", userName);
//        entity.setProperty("id",id);
//        datastoreService.put(entity);
//        return true;
//    }

    private static final String COOKIE_NAME="gpardy";

    private String getUniqueId(HttpServletRequest request, HttpServletResponse response) {
        Cookie idCookie = getCookie(request);
        if (idCookie == null) {
            idCookie = new Cookie(COOKIE_NAME, UUID.randomUUID().toString());
            idCookie.setMaxAge(24 * 60 * 60);
            response.addCookie(idCookie);
        }
        return idCookie.getValue();
    }

    private Cookie getCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (COOKIE_NAME.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }
}
