<%@ page import="com.google.appengine.api.datastore.*" %>
<%--
  Created by IntelliJ IDEA.
  User: gmdayley
  Date: 8/3/11
  Time: 11:32 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Gpardy - Admin</title></head>
<body>
    <form method="POST">
        <label for="oauth_consumer_secret">OAuth Consumer Secret:</label>
        <input type="text" name="oauth_consumer_secret" id="oauth_consumer_secret"/>

        <label for="oauth_consumer_key">OAuth Consumer Key:</label>
        <input type="text" name="oauth_consumer_key" id="oauth_consumer_key"/>


        <input id="save" type="submit" name="Save"/>
    </form>

    <%
        if(request.getParameter("Save") != null){
            DatastoreService  dataStoreService = DatastoreServiceFactory.getDatastoreService();

            Key key = KeyFactory.createKey("OAuth", "1");

            Entity oauthEntity = new Entity(key);
            oauthEntity.setProperty("consumerSecret", request.getParameter("oauth_consumer_secret"));
            oauthEntity.setProperty("consumerKey", request.getParameter("oauth_consumer_key"));

            dataStoreService.put(oauthEntity);
        }
    %>
</body>
</html>