package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;


public class Controller {

    @FXML
    private AnchorPane chessboard, container;
    @FXML
    private Button makeMine, makeWall, makeStar, generateRandomElement, startBtn;
    @FXML
    private Label blueScore, RedScore, turn, error;

    Label wonLabel;
    Button wonRestartButton;

    private String putElement = "";
    private Board board;
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Star> stars = new ArrayList<>();
    private final ArrayList<Mine> mines = new ArrayList<>();
    private final ArrayList<Wall> walls = new ArrayList<>();
    private final ArrayList<ImageView> playersImages = new ArrayList<>();
    private boolean isGameFinished = false;

    public double widthSize = 500.0;
    public double heightSize = 500.0;
    private int boardSize = 8;


    private void clearError () {
        if (!error.getText().isEmpty()) error.setText("");
    }

    // show the board after specifying the board size
    public void setScene (int size) {

        Board gameBoard = new Board(size);

        Image player1Icon = new Image("player1.png");
        Image player2Icon = new Image("player2.png");

        Player player1 = new Player("Ali", "BLUE", new Position(0, size - 1));
        Player player2 = new Player("Jack", "RED", new Position(size - 1, size - 1));

        player1.setPlayerImage(player1Icon);
        player2.setPlayerImage(player2Icon);
        player1.setTurn(true);

        gameBoard.addPlayerToBoard(player1);
        gameBoard.addPlayerToBoard(player2);

        this.setBoardSize(size);
        this.setBoard(gameBoard);
        this.drawBoard(size);

        this.setPlayer(player1, player1Icon, size);
        this.setPlayer(player2, player2Icon, size);
    }

    // does player did its move (sometimes player click on wall and move didn't complete)
    public boolean isMoveDone (Player player, Position target) {
        return player.getPosition().getX() == target.getX() &&
                player.getPosition().getY() == target.getY();
    }

    // player doesn't move to the same place that it is on it
    private boolean isNotTheSame (Position prePlayerPos, Position target) {
        return prePlayerPos.getX() != target.getX() || prePlayerPos.getY() != target.getY();
    }

    // check for player not in each other ways
    private boolean notInOtherPlayerPos (Position target, int otherPlayerIndex) {
        Position otherPlayer = this.players.get(otherPlayerIndex).getPosition();

        return target.getX() != otherPlayer.getX() || target.getY() != otherPlayer.getY();
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    // calculate X & Y coordinate of screen
    public int[] calculatePositionOfElement (double x, double y, int boardSize) {

        // width and size of board are the same
        double widthSize = chessboard.getWidth();
        double heightSize = chessboard.getHeight();

        double stepWidth = widthSize / boardSize;
        double stepHeight = heightSize / boardSize;

        return new int[] {(int)(x / stepWidth), (int) (y / stepHeight)};
    }

    private boolean isBoardEmpty () {
        return !this.stars.isEmpty() && !this.mines.isEmpty();
    }

    public void movement (MouseEvent event) {
        double x_pos = event.getX();
        double y_pos = event.getY();

        double stepWidth = widthSize / boardSize;
        double stepHeight = heightSize / boardSize;

        int[] posToMove = calculatePositionOfElement(x_pos, y_pos, boardSize);
        int x = posToMove[0];
        int y = posToMove[1];

        if (!putElement.isEmpty()) {
            // when user wants to draw wall, mine or star on screen
            switch (putElement) {
                case "wall" -> addToWalls(x, y);
                case "mine" -> addToMines(x, y);
                case "star" -> addToStars(x, y);
            }
        }

        else {
            boolean blueTurn = this.players.get(0).isTurn();
            int playerIndex = blueTurn ? 0 : 1;
            int otherPlayerIndex = blueTurn ? 1: 0;
            String ScoreColor = blueTurn? "Blue: ": "Red: ";
            String turnText = blueTurn ? "RED": "BLUE";
            Color turnColor = !blueTurn ? Color.BLUE: Color.RED;

            if (isBoardEmpty() && !isGameFinished) {

                if (notInOtherPlayerPos(new Position(x, y), otherPlayerIndex))
                {
                    Player player = this.players.get(playerIndex);

                    Position prePlayerPosition = player.getPosition(); // store current player position
                    Position targetPlayerPosition = new Position(x, y); // store target to move

                    // image node that is on board
                    ImageView lastImage = this.playersImages.get(playerIndex);

                    // image we want to add as new node on board
                    ImageView image = new ImageView(player.getPlayerImage());

                    this.getBoard().move(player, new Position(x, y));

                    if (this.isMoveDone(player, new Position(x, y)) &&
                            isNotTheSame(prePlayerPosition, targetPlayerPosition)) {

                        // move player on screen
                        chessboard.getChildren().remove(lastImage);
                        image.setX(x * stepWidth);
                        image.setY(y * stepHeight);
                        image.setFitWidth(stepWidth);
                        image.setFitHeight(stepHeight);
                        chessboard.getChildren().add(image);

                        this.playersImages.set(playerIndex, image);

                        // set score of player
                        if (blueTurn) blueScore.setText(ScoreColor + player.getPlayerScore());
                        else RedScore.setText(ScoreColor + player.getPlayerScore());

                        // change turn of player
                        this.players.get(playerIndex).setTurn(false);
                        this.players.get(otherPlayerIndex).setTurn(true);
                        turn.setText(turnText);
                        turn.setTextFill(turnColor);

                        clearError();
                    } else error.setText("invalid move");

                } else error.setText("Path is Blocked by Other Player!");
            }
            else {
                if (isGameFinished) error.setText("Restart the Game");
                else error.setText("Insert Mines and Stars to the field");
            }

            removeElementsFromBoard();
            if (this.getBoard().isGameFinished() && isBoardEmpty()) {

                if (!this.getBoard().arePlayerDraw()) {
                    Player winnerPlayer = this.getBoard().winner();

                    if (winnerPlayer.getPlayerColor().equals("BLUE")) {
                        drawGameOver("Blue won", Color.BLUE);
                    }
                    else {
                        drawGameOver("Red won", Color.RED);
                    }

                } // second if

                else {
                    drawGameOver("Draw", Color.BLACK);
                }
                this.isGameFinished = true;
            } // first if
        }

    }

    //  draw the board cell
    public void drawBoard (int boardSize) {

        double stepWidth = widthSize / boardSize;
        double stepHeight = heightSize / boardSize;

        for (int i = 0; i < boardSize ; i++) {
            for (int j = 0; j < boardSize; j++) {
                Rectangle rectangle = new Rectangle();
                rectangle.setX(i * stepWidth);
                rectangle.setY(j * stepHeight);
                rectangle.setWidth(stepWidth);
                rectangle.setHeight(stepHeight);
                rectangle.setFill(Color.WHITE);
                rectangle.setStroke(Color.ORANGE);
                chessboard.getChildren().add(rectangle);
            }
        }
    }

    // draw the wall on board on custom mode
    public void addToWalls (int x, int y) {
        if (this.getBoard().positionIsEmpty(new Position(x, y))) {
            clearError();
            double stepWidth = widthSize / boardSize;
            double stepHeight = heightSize / boardSize;

            Wall wall = new Wall();
            Position position = new Position(x, y);
            wall.setPosition(position);

            Rectangle rectangle = new Rectangle();

            rectangle.setX(x * stepWidth);
            rectangle.setY(y * stepHeight);
            rectangle.setWidth(stepWidth);
            rectangle.setHeight(stepHeight);
            rectangle.setFill(Color.BLACK);
            rectangle.setStroke(Color.ORANGE);
            wall.setRectangle(rectangle);
            chessboard.getChildren().add(rectangle);

            this.walls.add(wall);
            this.getBoard().getWalls().add(wall);
            this.getBoard().getBoardPositions().add(position);
        } else error.setText("Choose an empty cell for wall");
    }

    // draw the mine on board on custom mode
    public void addToMines (int x, int y) {
        if (this.getBoard().positionIsEmpty(new Position(x, y))) {
            clearError();
            double stepWidth = widthSize / boardSize;
            double stepHeight = heightSize / boardSize;

            int score = 5 * Mine.calcScore(0, 5);
            if (score == 0) score = 1;

            Mine mine = new Mine();
            mine.setScore(score);
            Position position = new Position(x, y);
            mine.setPosition(position);
            this.mines.add(mine);
            this.getBoard().getMines().add(mine);
            this.getBoard().getBoardPositions().add(position);

            Image mineIcon = new Image("mine" + score + ".png");
            ImageView image = new ImageView(mineIcon);

            image.setX(x * stepWidth);
            image.setY(y * stepHeight);
            image.setFitWidth(stepWidth);
            image.setFitHeight(stepHeight);

            mine.setElementImage(image);
            chessboard.getChildren().add(image);
        } else error.setText("Choose an empty cell for mine");

    }

    // draw the star on board on custom mode
    public void addToStars (int x, int y) {

        if (this.getBoard().positionIsEmpty(new Position(x, y))) {
            clearError();
            double stepWidth = widthSize / boardSize;
            double stepHeight = heightSize / boardSize;

            int score = Star.calcScore(1, 5);
            Star star = new Star();
            star.setScore(score);
            Position position = new Position(x, y);
            star.setPosition(position);
            this.stars.add(star);

            this.getBoard().getStars().add(star);
            this.getBoard().getBoardPositions().add(position);
            Image starIcon = new Image("star" + score + ".png");
            ImageView image = new ImageView(starIcon);

            image.setX(x * stepWidth);
            image.setY(y * stepHeight);
            image.setFitWidth(stepWidth);
            image.setFitHeight(stepHeight);

            star.setElementImage(image);
            chessboard.getChildren().add(image);
        } else error.setText("Choose an empty cell for star");

    }

    // puts wall on board by clicking on board cell
    public void makeWall (ActionEvent event) {
        startBtn.setDisable(false);
        putElement = "wall";
        generateRandomElement.setDisable(true);
    }

    // puts mine on board by clicking on board cell
    public void makeMine (ActionEvent event) {
        startBtn.setDisable(false);
        putElement = "mine";
        generateRandomElement.setDisable(true);

    }

    // puts star on board by clicking on board cell
    public void makeStar (ActionEvent event) {
        startBtn.setDisable(false);
        putElement = "star";
        generateRandomElement.setDisable(true);

    }

    // put random element on screen
    public void putRandomElementOnScreen () {

        double stepWidth = widthSize / boardSize;
        double stepHeight = heightSize / boardSize;

        // generate element in board class
        this.getBoard().generateWalls();
        this.getBoard().generateStars();
        this.getBoard().generateMines();

        // add generated element into controller elements
        this.walls.addAll(this.getBoard().getWalls());
        this.stars.addAll(this.getBoard().getStars());
        this.mines.addAll(this.getBoard().getMines());

        // draw walls
        for (Wall wall : this.getBoard().getWalls()) {
            Rectangle rectangle = new Rectangle();

            double x_axis = wall.getPosition().getX();
            double y_axis = wall.getPosition().getY();
            
            rectangle.setX(x_axis * stepWidth);
            rectangle.setY(y_axis * stepHeight);
            rectangle.setWidth(stepWidth);
            rectangle.setHeight(stepHeight);
            rectangle.setFill(Color.BLACK);
            rectangle.setStroke(Color.ORANGE);
            wall.setRectangle(rectangle);
            chessboard.getChildren().add(rectangle);
        }

        // draw mines
        for (Mine mine : this.getBoard().getMines()) {
            Image mineIcon = new Image("mine"+mine.getScore()+".png");
            ImageView image = new ImageView(mineIcon);
            mine.setElementImage(image);
            image.setX(mine.getPosition().getX() * stepWidth);
            image.setY(mine.getPosition().getY() * stepHeight);
            image.setFitWidth(stepWidth);
            image.setFitHeight(stepHeight);
            chessboard.getChildren().add(image);

        }

        // draw stars
        for (Star star : this.getBoard().getStars()) {
            Image starIcon = new Image("star"+star.getScore()+".png");
            ImageView image = new ImageView(starIcon);
            star.setElementImage(image);
            image.setX(star.getPosition().getX() * stepWidth);
            image.setY(star.getPosition().getY() * stepHeight);
            image.setFitWidth(stepWidth);
            image.setFitHeight(stepHeight);
            chessboard.getChildren().add(image);
        }

        startBtn.setDisable(false);
        generateRandomElement.setDisable(true);

    }

    // restart game
    public void restartGame (ActionEvent event) {
        // reset element on board
        // restart the whole board with the same size
        Board board = new Board(boardSize);
        this.setBoard(board);

        for (Wall wall: this.walls) chessboard.getChildren().remove(wall.getRectangle());
        for (Star star: this.stars) chessboard.getChildren().remove(star.getElementImage());
        for (Mine mine: this.mines) chessboard.getChildren().remove(mine.getElementImage());
        for (ImageView image: this.playersImages) chessboard.getChildren().remove(image);

        this.walls.clear();
        this.mines.clear();
        this.stars.clear();
        this.playersImages.clear();

        // reset buttons
        generateRandomElement.setDisable(false);
        makeMine.setDisable(false);
        makeStar.setDisable(false);
        makeWall.setDisable(false);

        // reset text labels
        RedScore.setText("Red: 0");
        blueScore.setText("Blue: 0");

        turn.setText("BLUE");
        turn.setTextFill(Color.BLUE);
        clearError();

        Image player1Icon = new Image("player1.png");
        Image player2Icon = new Image("player2.png");

        Player player1 = new Player("Ali", "BLUE", new Position(0, boardSize - 1));
        Player player2 = new Player("Jack", "RED", new Position(boardSize - 1, boardSize - 1));

        player1.setPlayerImage(player1Icon);
        player2.setPlayerImage(player2Icon);
        player1.setTurn(true);
        this.players.clear();

        this.getBoard().addPlayerToBoard(player1);
        this.getBoard().addPlayerToBoard(player2);

        this.setPlayer(player1, player1.getPlayerImage(), boardSize);
        this.setPlayer(player2, player2.getPlayerImage(), boardSize);

        this.isGameFinished = false;

        chessboard.setOpacity(1);
        container.getChildren().removeAll(wonLabel, wonRestartButton);

    }

    // set player on board
    public void setPlayer (Player player, Image playerImage, int boardSize) {
        double stepWidth = widthSize / boardSize;
        double stepHeight = heightSize / boardSize;

        this.players.add(player);
        ImageView image = new ImageView(playerImage);
        image.setX(player.getPosition().getX() * stepWidth);
        image.setY(player.getPosition().getY() * stepHeight);
        image.setFitWidth(stepWidth);
        image.setFitHeight(stepHeight);

        this.playersImages.add(image);
        chessboard.getChildren().add(image);
    }

    // removes the stars and mines that players pass from them
    public void removeElementsFromBoard () {
        for (Star star: this.stars)
            if (star.getScore() == 0) chessboard.getChildren().remove(star.getElementImage());

        for (Mine mine: this.mines)
            if (mine.getScore() == 0) chessboard.getChildren().remove(mine.getElementImage());

    }

    // when click on start button
    public void start (ActionEvent event) {
        if (isBoardEmpty()) {
            clearError();
            putElement = "";
            makeMine.setDisable(true);
            makeWall.setDisable(true);
            makeStar.setDisable(true);
            generateRandomElement.setDisable(true);
            startBtn.setDisable(true);
        }
        else error.setText("Add Mines & Stars to the field");
    }

    // write on screen which player won the match
    public void drawGameOver (String text, Color color) {
        Label label = new Label();
        label.setTextFill(Color.BLACK);
        label.setStyle("-fx-alignment: center; -fx-background: black; -fx-z-index: 2");
        chessboard.setOpacity(0.4);
        label.setText(text);
        label.setTextFill(color);
        label.setFont(Font.font("Helvetica", 100));
        label.setPrefWidth(500);
        label.setPrefHeight(500);
        wonLabel = label;

        Button restart = new Button();
        restart.setText("Restart");
        restart.setOnAction(this::restartGame);
        restart.setStyle("-fx-alignment: center; -fx-background-color: black");
        restart.setTextFill(Color.WHITE);
        restart.setFont(Font.font("Helvetica", 50));
        restart.setLayoutX(150);
        restart.setLayoutY(330);

        wonRestartButton = restart;
        container.getChildren().addAll(label, restart);

    }

}
