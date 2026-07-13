package IO;

import logic.*;
import processing.core.PApplet;

import java.util.Timer;
import java.util.TimerTask;

public class Processing extends PApplet {
    enum modes {ENTRYGAME, GAME, ENTRYGAMEOVER, GAMEOVER, MAINMENU, temp, ENTRYLEADERBOARD, LEADERBOARD, LOGIN}
    static modes mode;

    enum games {ORIGINAL, CANNIBAL}
    static games currentGame;

    static boolean loginFailed = false;

    Snake.directions heading;
    Grid grid;
    Snake snake;
    DatabaseInterface dbi = new DatabaseInterface("localhost:3306", "root", "");
    Spieler player;

    Timer timer;
    TimerTask timerTask;

    Button buttonPlayAgain;
    Button buttonReturnMainMenu;
    Button buttonPlayOriginal;
    Button buttonPlayCannibal;
    Button buttonLeaderboardOriginal;
    Button buttonLeaderboardCannibal;
    Button buttonBack;

    Textfield textfieldUsername;
    Textfield textfieldPassword;


    public static void main(String[] args) {
        PApplet.main("IO.Processing");
    }

    @Override
    public void settings() {
        size(800, 840);
    }

    @Override
    public void setup() {
        mode = modes.temp;
    }

    @Override
    public void draw() {
        background(0);

        switch (mode) {
            case temp:
                dbi.establishConnection();
                initializeButtons();
                mode = modes.LOGIN;
                break;
            case LOGIN:
                textSize(100);
                fill(0, 255, 0);
                textAlign(CENTER, CENTER);
                text("Welcome to Snake", 400, 250);

                textSize(20);
                fill(255);
                textAlign(LEFT, CENTER);
                text("Username:", 200, 415);
                text("Password:", 200, 465);

                if (loginFailed) {
                    fill(255, 0, 0);
                    textAlign(CENTER, CENTER);
                    text("Wrong username or password, please try again", 400 , 370 );
                }

                textfieldUsername.execute(this);
                textfieldPassword.execute(this);
                break;
            case MAINMENU:
                textSize(150);
                fill(0, 255, 0);
                textAlign(CENTER, CENTER);
                text("Snake", 400, 120);

                textSize(25);
                fill(255);
                textAlign(LEFT, CENTER);
                text("Snake Orginal", 150, 325);
                text("Cannibal Snake", 150, 425);

                buttonPlayOriginal.execute(this);
                buttonLeaderboardOriginal.execute(this);
                buttonPlayCannibal.execute(this);
                buttonLeaderboardCannibal.execute(this);
                break;
            case ENTRYLEADERBOARD:
                System.out.println(currentGame);
                mode = modes.LEADERBOARD;
                break;
            case LEADERBOARD:

                buttonBack.execute(this);
                break;
            case ENTRYGAME:
                grid = new Grid(20, 20);
                switch (currentGame) {
                    case ORIGINAL:
                        snake = new Snake(grid);
                        break;
                    case CANNIBAL:
                        snake = new CannibalSnake(grid);
                }

                timer = new Timer();
                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        snake.move(heading, grid);
                        heading = null;
                        grid.updateGrid(snake);
                    }
                };
                timer.schedule(timerTask, 500, 500);

                grid.updateGrid(snake);
                grid.placeApple();
                grid.updateGrid(snake);

                noStroke();

                mode = modes.GAME;
                break;
            case GAME:
                textSize(30);
                fill(255);
                textAlign(LEFT, BASELINE);
                text("apples eaten: " + snake.getApplesEaten(), 20, 25);

                for (int i = 0; grid.getHeight() > i; i++) {
                    for (int j = 0; grid.getWidth() > j; j++) {
                        switch (grid.grid[j][i].getContent()) {
                            case HEAD:
                                fill(38, 168, 5);
                                square(j * 40, i * 40 + 40, 40);
                                break;
                            case BODY:
                                fill(19, 82, 3);
                                square(j * 40, i * 40 + 40, 40);
                                break;
                            case APPLE:
                                fill(255, 0, 0);
                                square(j * 40, i * 40 + 40, 40);
                        }
                    }
                }
                break;
            case ENTRYGAMEOVER:
                timer.cancel();
                mode = modes.GAMEOVER;
                break;
            case GAMEOVER:
                textSize(100);
                fill(255, 0, 0);
                textAlign(CENTER, CENTER);
                text("GAME OVER", 420, 250);
                buttonPlayAgain.execute(this);
                buttonReturnMainMenu.execute(this);
                break;
        }
    }

    @Override
    public void keyPressed() {
        switch (mode) {
            case LOGIN:
                try {
                    if (key == ENTER || key == RETURN) Textfield.active.selectNext();
                    if (key == BACKSPACE && !Textfield.active.writtenInput.isEmpty())
                        Textfield.active.writtenInput.remove(Textfield.active.writtenInput.size() - 1);
                } catch (NullPointerException e) {
                    System.out.println("no Textfield selected");
                }
                break;
            case GAME:
                if (key == 'w') heading = Snake.directions.UP;
                if (key == 'a') heading = Snake.directions.LEFT;
                if (key == 's') heading = Snake.directions.DOWN;
                if (key == 'd') heading = Snake.directions.RIGHT;

        }
    }

    @Override
    public void keyTyped() {
        try {
            if (mode == modes.LOGIN && key != BACKSPACE && key != ENTER) {
                Textfield.active.writtenInput.add(key);
            }
        } catch (NullPointerException e) {
            System.out.println("no Textfield selected");
        }
    }


    public static void gameOver() {
        //System.exit(0);
        mode = modes.ENTRYGAMEOVER;
    }

    private void initializeButtons() {
        buttonPlayAgain = new Button(300, 500, 200, 50, "Play Again") {
            @Override
            void executeFunction() {
                Processing.mode = modes.ENTRYGAME;
            }
        };

        buttonReturnMainMenu = new Button(300, 600, 200, 50, "Return to Menu") {
            @Override
            void executeFunction() {
                Processing.mode = modes.MAINMENU;
            }
        };

        buttonPlayOriginal = new Button(470, 300, 140, 50, "Play") {
            @Override
            void executeFunction() {
                currentGame = games.ORIGINAL;
                Processing.mode = modes.ENTRYGAME;
            }
        };

        buttonPlayCannibal = new Button(470, 400, 140, 50, "Play") {
            @Override
            void executeFunction() {
                currentGame = games.CANNIBAL;
                Processing.mode = modes.ENTRYGAME;
            }
        };

        buttonLeaderboardOriginal = new Button(630, 300, 140, 50, "Leaderboard") {
            @Override
            void executeFunction() {
                currentGame = games.ORIGINAL;
                Processing.mode = modes.ENTRYLEADERBOARD;
            }
        };

        buttonLeaderboardCannibal = new Button(630, 400, 140, 50, "Leaderboard") {
            @Override
            void executeFunction() {
                currentGame = games.CANNIBAL;
                Processing.mode = modes.ENTRYLEADERBOARD;
            }
        };

        buttonBack = new Button(50, 50, 70, 50, "Back") {
            @Override
            void executeFunction() {
                currentGame = null;
                Processing.mode = modes.MAINMENU;
            }
        };

        textfieldUsername = new Textfield(300, 400, 200, 30) {
            @Override
            void selectNext() {
                Textfield.active = textfieldPassword;
            }
        };

        textfieldPassword = new Textfield(300, 450, 200, 30) {
            @Override
            void selectNext() {
                try {
                    if (Spieler.login(dbi, textfieldUsername.writtenInputToString(), textfieldPassword.writtenInputToString())) {
                        player = new Spieler(dbi, textfieldUsername.writtenInputToString());
                    } else {
                        Processing.loginFailed = true;
                    }
                } catch (java.sql.SQLException e) {
                    System.out.println("how?");
                }
            }
        };
    }
}