package com.gpardy.web.gwt.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.ui.RootPanel;
import com.gpardy.web.gwt.client.place.StartPlace;
import com.gpardy.web.gwt.client.view.Application;

import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 4/5/11
 * Time: 9:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class Gpardy implements EntryPoint {
    Logger logger = Logger.getLogger("Gpardy");


//    private Place defaultPlace = new NewGamePlace();
//    private Place defaultPlace = new GamePlace("GTUG", "0AiBN5S33ER-LdDVTZnZyVmppcnNiX094dzVMekxnQnc");
    private Place defaultPlace = new StartPlace();

    private Application application = new Application();

    public void onModuleLoad() {
        logger.fine("Starting application");
        ClientFactory clientFactory = GWT.create(ClientFactory.class);
        EventBus eventBus = clientFactory.getEventBus();
        PlaceController placeController = clientFactory.getPlaceController();

        // Start ActivityManager for the main widget with our ActivityMapper
        ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
        ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
        activityManager.setDisplay(application.getDisplay());

        // Start PlaceHistoryHandler with our PlaceHistoryMapper
        AppPlaceHistoryMapper historyMapper= GWT.create(AppPlaceHistoryMapper.class);
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        historyHandler.register(placeController, eventBus, defaultPlace);

        RootPanel.get().add(application);
        // Goes to the place represented on URL else default place
        historyHandler.handleCurrentHistory();
    }
}
