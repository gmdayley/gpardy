package com.gpardy.web.gwt.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.UListElement;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.gpardy.web.gwt.client.User;
import com.gpardy.web.gwt.client.jso.CategoryJso;
import com.gpardy.web.gwt.client.jso.GameJso;
import com.gpardy.web.gwt.client.jso.QuestionAnswerJso;
import com.gpardy.web.gwt.client.ui.QuestionTimer;
import com.gpardy.web.gwt.client.ui.TimerExpiredHandler;

import java.util.*;
import java.util.List;
import java.util.logging.Logger;


/**
 * Created by IntelliJ IDEA.
 * User: gmdayley
 * Date: 4/5/11
 * Time: 9:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class GameViewImpl extends Composite implements GameView, TimerExpiredHandler {
    Logger logger = Logger.getLogger("GameView");
    public static final int QUESTION_TIMER_DURATION = 30000;

    interface GameViewImplUiBinder extends UiBinder<HTMLPanel, GameViewImpl> {
    }

    private static GameViewImplUiBinder ourUiBinder = GWT.create(GameViewImplUiBinder.class);

    private Presenter presenter;

    // Ui Bindings
    @UiField FlowPanel board;
    @UiField FlowPanel questionPanel;
    @UiField QuestionTimer questionTimer;
    @UiField FlowPanel buzzinPanel;
    @UiField HTMLPanel scoresPanel;
    @UiField Button correctButton;
    @UiField Button incorrectButton;
    @UiField SimplePanel buzzinBoxText;
    @UiField UListElement scoreList;
    @UiField Anchor optionsAnchor;
    @UiField Anchor resetAnchor;
    @UiField FlowPanel gameOverPanel;
    @UiField Label winnerLabel;
    @UiField Label gameNameCode;
    @UiField FlowPanel questionContent;
    @UiField InlineLabel currentQuestionLabel;
    @UiField Button dismissButton;
    private OptionsPanel optionsPanel;

    //private JSONObject tScores = new JSONObject();
    private Map<User, Integer> scores = new HashMap<User, Integer>();

    boolean mute = false;
    private GameJso gameData;
    private int numQuestions = 0;
    private boolean canBuzzIn = false;
    private InlineLabel currentLevel;
    private QuestionAnswerJso currentQuestionAnswer;
    private int currentRound = 0;
    private User buzzedInUser = null;

    public GameViewImpl() {
        HTMLPanel rootElement = ourUiBinder.createAndBindUi(this);
        this.initWidget(rootElement);

        optionsPanel = new OptionsPanel();

        // Register handler for timer expired
        questionTimer.addTimerExpiredHandler(this);

        // Toggle score board style to show full or collapsed
        scoresPanel.addDomHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                if (scoresPanel.getStyleName().contains("scores-full")) {
                    scoresPanel.removeStyleName("scores-full");
                } else {
                    scoresPanel.addStyleName("scores-full");
                }
            }
        }, ClickEvent.getType());

        RootPanel.get().addDomHandler(new KeyUpHandler() {
            @Override
            public void onKeyUp(KeyUpEvent event) {
                switch (event.getNativeKeyCode()) {
                    case KeyCodes.KEY_ENTER:
                        //todo simplify, yuk :(
                        if (questionPanel.isVisible() && questionPanel.getElement().getAttribute("class").contains("show")) {
                            startTimer();
                        }
                        break;
                    case KeyCodes.KEY_ESCAPE:
                        if (questionPanel.isVisible()) {
                            hideQuestion(false);
                        }
                        break;
                }
            }
        }, KeyUpEvent.getType());


        // Register handlers for right/wrong buttons
        correctButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                updateScore(buzzedInUser, currentQuestionAnswer.getValue());
                hideQuestion(true);
                event.stopPropagation();
                playSound("correctanswer");
            }
        });

        incorrectButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hideBuzzIn();
                updateScore(buzzedInUser, -currentQuestionAnswer.getValue());
                startTimer();
                event.stopPropagation();
                playSound("wronganswer");
            }
        });

        dismissButton.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                hideBuzzIn();
                startTimer();
            }
        });
    }


    private native void playSound(String id) /*-{
        if(!this.@com.gpardy.web.gwt.client.view.GameViewImpl::isMuted()()){
            $wnd.soundManager.play(id);
        }
    }-*/;

    private boolean isMuted(){
        return !optionsPanel.playSounds();
    }

    private void hideQuestion(boolean disableQuestion) {
        canBuzzIn = false;
        hideBuzzIn();
        questionPanel.removeStyleName("show");
        questionContent.clear();
        questionTimer.stop();
        questionTimer.setVisible(false);
        if (disableQuestion) {
            disableQuestion();
        }

        //Check to see if there are any questions left, if not the game is over
        if (numQuestions == 0) {
            Timer t = new Timer() {
                @Override
                public void run() {
                    gameOver();
                }
            };
            t.schedule(2000);
        }
    }

    private void gameOver() {
        if (getSortedScoresKeys().size() > 0) {
            winnerLabel.setText(getSortedScoresKeys().get(0).name);
        }
        gameOverPanel.addStyleName("show");
        playSound("ending");
    }

    private void disableQuestion() {
        currentLevel.addStyleName("disabled");
        currentQuestionAnswer.disable();
        --numQuestions;

        JSONObject jsonObject = new JSONObject(gameData);
        Storage.getLocalStorageIfSupported().setItem(gameData.getName(), jsonObject.toString());
    }

    private void startTimer() {
        questionTimer.stop();
        canBuzzIn = true;
        questionTimer.setVisible(true);

        questionTimer.start(optionsPanel.getTimerDuration());
    }

    private void hideBuzzIn() {
        buzzinPanel.removeStyleName("show");
    }

    private void updateScore(User user, Integer delta) {
        //Find user's score and update
        Integer value = scores.get(user);
        if (value == null) {
            scores.put(user, Math.max(delta, 0));
        } else {
            int newValue = Math.max(value + delta, 0);
            scores.put(user, newValue);
        }

        //Cache scores into local storage
        //Storage.getLocalStorageIfSupported().setItem(gameData.getName() + "-scores", tScores.toString());

        //Update display
        displayScores();
    }


    private void displayScores() {
        List<User> keys = getSortedScoresKeys();

        scoreList.setInnerHTML("");

        for (User key : keys) {
            Element li = DOM.createElement("li");
            li.setInnerHTML(key.name + "<span>" + scores.get(key) + "</span>");
            scoreList.appendChild(li);
        }
    }

    private List<User> getSortedScoresKeys() {
        List<User> keys = new ArrayList<User>(scores.keySet());
        Collections.sort(keys, new Comparator<User>() {
            @Override
            public int compare(User s, User s1) {
                int v1 = scores.get(s1);
                int v2 = scores.get(s);
                int diff = v1 - v2;
                return diff == 0 ? s.name.compareTo(s1.name) : diff;
            }
        });
        return keys;
    }


    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }


    // Draw the gameboard
    public void drawBoard() {
        gameNameCode.setText("Game code: " + gameData.getCode());
        final Stack<InlineLabel> levels = new Stack<InlineLabel>();
        this.numQuestions = 0;

        JsArray<CategoryJso> categories = gameData.getRound(0).getCategories();

        //Dynamically calculate the width so that the board will be centered horizontally
        //191 comes from the calculation of styles:
        // .category span 165 (width) + 2*2 (margin) + 10*2 (padding)
        // .category 1*2(padding)  == 165 + 4 + 20 +2 = 191

//        DOM.setStyleAttribute(this.getElement(), "width", 191 * categories.length() + "px");
        DOM.setStyleAttribute(this.getElement(), "width", 246 * categories.length() + "px");

        // Create columns
        for (int i = 0; i < categories.length(); i++) {
            CategoryJso categoryJso = categories.get(i);

            FlowPanel categoryPanel = new FlowPanel();
            categoryPanel.addStyleName("category");

            InlineLabel name = new InlineLabel(categoryJso.getName());
            name.addStyleName("name");
            categoryPanel.add(name);

            JsArray<QuestionAnswerJso> questionAnswers = categoryJso.getQuestionAnswers();
            for (int j = 0; j < questionAnswers.length(); j++) {
                final QuestionAnswerJso qa = questionAnswers.get(j);

                final InlineLabel level = new InlineLabel(Integer.toString(qa.getValue()));
                level.addStyleName("level");

                final int categoryIdx = i;
                final int levelIdx = j;

                if (!qa.disabled()) {
                    numQuestions++;
                    level.addClickHandler(new ClickHandler() {
                        @Override
                        public void onClick(ClickEvent event) {
                            //Store current question and level
                            currentQuestionAnswer = qa;
                            currentLevel = level;

                            //Show question
                            showQuestion(categoryIdx, levelIdx);
                        }
                    });
                    levels.add(level);
                } else {
                    level.addStyleName("disabled");
                }
                categoryPanel.add(level);
            }
            board.add(categoryPanel);
        }

        Collections.sort(levels, new Comparator<InlineLabel>() {
            @Override
            public int compare(InlineLabel inlineLabel, InlineLabel inlineLabel1) {
                Random r = new Random();
                return r.nextInt(10) - r.nextInt(10);
            }
        });

        Timer timer = new Timer() {
            @Override
            public void run() {
                if (!levels.empty()) {
                    levels.pop().addStyleName("show");
                } else {
                    this.cancel();
                }
            }
        };

        timer.scheduleRepeating(110);
        playSound("fillboard");
    }


    public void clearBoard(){
        board.clear();
    }

    @Override
    public void setGameData(final GameJso gameJso, final JSONObject scores) {
        this.gameData = gameJso;
        //this.tScores = scores;

        drawBoard();
        displayScores();
    }

    @Override
    public void showBuzzIn(User user) {
        logger.fine("Buzz: " + user);
        fixNameInScoreHack(user);

        if (canBuzzIn) {
            playSound("buzzin");
            correctButton.setText("Right (+" + currentQuestionAnswer.getValue() + ")");
            incorrectButton.setText("Wrong (-" + currentQuestionAnswer.getValue() + ")");

            buzzedInUser = user;
            questionTimer.stop();
            buzzinBoxText.setWidget(new Label(user.name + " has buzzed in"));
            buzzinPanel.addStyleName("show");
            canBuzzIn = false;
        }
    }

    @Override
    public void joinGame(User user) {
        playSound("buzzin");
        fixNameInScoreHack(user);
        updateScore(user, 0);
    }

    private void fixNameInScoreHack(User user) {
        Integer score = scores.get(user);
        if (score != null) {
            scores.remove(user);
            scores.put(user, score);
        }
    }

    @Override
    public void showQuestion(int category, int level) {
        QuestionAnswerJso qa = gameData.getRound(currentRound)
                .getCategories().get(category)
                .getQuestionAnswers().get(level);

        if (!qa.disabled()) {
            playSound("woosh");

            this.questionContent.clear();

            if (qa.getQuestionText().startsWith("image:")) {
                this.questionContent.add(new Image(qa.getQuestionText().split("image:")[1]));
            }
            else if (qa.getQuestionText().startsWith("video:")) {
                this.questionContent.add(new Video(qa.getQuestionText().split("video:")[1]));
            }
            else if(qa.getQuestionText().startsWith("youtube:")){
                //    <iframe width="560" height="349" src="http://www.youtube.com/embed/aTDCdZoCOY4?rel=0" frameborder="0" allowfullscreen></iframe>
                Frame youtubeFrame = new Frame(qa.getQuestionText().split("youtube:")[1]);
                youtubeFrame.getElement().setAttribute("frameborder", "0");
                youtubeFrame.getElement().setAttribute("width", "853");
                youtubeFrame.getElement().setAttribute("height", "510");
                this.questionContent.add(youtubeFrame);
            }
            else if(qa.getQuestionText().startsWith("audio:")){
                this.questionContent.add(new Audio(qa.getQuestionText().split("audio:")[1]));
            }
            else if(qa.getQuestionText().startsWith("html:")){
                FlowPanel panel = new FlowPanel();
                panel.addStyleName("question-html");
                panel.getElement().setInnerHTML(qa.getQuestionText().split("html:")[1]);
                this.questionContent.add(panel);
            }
            else {
                this.questionContent.add(new InlineLabel(qa.getQuestionText()));
            }

            this.currentQuestionLabel.setText(gameData.getRound(currentRound).getCategories().get(category).getName() + " - " + qa.getValue());
            this.questionPanel.addStyleName("show");
        }
    }

    class Video extends Widget {
        Video(String source) {
            Element video = DOM.createElement("video");
            video.setAttribute("src", source);
            video.setAttribute("controls", "true");
            video.setAttribute("autoplay", "true");
            video.setAttribute("preload", "auto");
            video.setAttribute("width", "640");
            video.setAttribute("height", "480");
            setElement(video);
        }
    }

    class Audio extends Widget {
        Audio(String source) {
            Element audio = DOM.createElement("audio");
            audio.setAttribute("src", source);
            audio.setAttribute("controls", "true");
            audio.setAttribute("autoplay", "true");
            audio.setAttribute("preload", "auto");
            setElement(audio);
        }
    }


    @UiHandler("optionsAnchor")
    public void options(ClickEvent event) {
        DialogBox optionsDialog = new DialogBox(true, true);
        optionsDialog.addStyleName("options");

        optionsDialog.add(optionsPanel);

        optionsDialog.center();
    }

    @UiHandler("resetAnchor")
    public void reset(ClickEvent event) {
        presenter.resetGame();
    }

    @UiHandler("gameNameCode")
    public void reconnect(ClickEvent e){
        Window.alert("reconnect");
        presenter.reconnect();
    }

    @Override
    public void onCompleted() {
        playSound("timerexpired");
        canBuzzIn = false;

        //Show Answer
        questionContent.clear();
        questionContent.add(new InlineLabel(currentQuestionAnswer.getAnswerText()));
        questionTimer.setVisible(false);

        disableQuestion();
    }
}