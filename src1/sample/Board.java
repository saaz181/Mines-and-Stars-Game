package sample;
import java.util.ArrayList;
import java.util.Random;

record PlayerMove(int begin, int finish, int constant, String direction) {

    public int getBegin() {
        return begin;
    }

    public int getFinish() {
        return finish;
    }

    public int getConstant() {
        return constant;
    }

    public String getDirection() {
        return direction;
    }
}

public class Board {
    private final ArrayList<Mine> mines = new ArrayList<>();
    private final ArrayList<Star> stars = new ArrayList<>();
    private final ArrayList<Wall> walls = new ArrayList<>();
    private final ArrayList<Player> players = new ArrayList<>();
    private int playersOnBoard = 0;
    private int size;
    private final ArrayList<Position> boardPositions = new ArrayList<>();
    private final int minNumberOfElement = 4;

    public ArrayList<Mine> getMines() { return mines; }

    public ArrayList<Star> getStars() { return stars; }

    public ArrayList<Wall> getWalls() { return walls; }

    public ArrayList<Position> getBoardPositions() { return boardPositions; }

    public Board(int size) { this.setSize(size); }

    public boolean positionIsEmpty (Position position) {
        return !this.boardPositions.contains(position);
    }

    private int generateRandomNumber (int maxSize, int minSize) {
        Random r = new Random();
        return r.nextInt(maxSize) + minSize;
    }

    private Position findRandomEmptyPosition () {
        Random r = new Random();
        while (true) {
            int X = r.nextInt(this.size);
            int Y = r.nextInt(this.size);
            Position position = new Position(X, Y);
            if (this.positionIsEmpty(position)) return position;
        }
    }

    public void addPlayerToBoard(Player player) {
        int maxNumberOfPlayers = 2;
        if (this.playersOnBoard < maxNumberOfPlayers) {
            this.players.add(player);
            this.boardPositions.add(player.getPosition());
            this.playersOnBoard++;
        }
    }

    public void generateWalls () {
        int numberOfElements =  this.generateRandomNumber(this.size, 2*minNumberOfElement);
        for (int i = 0; i < numberOfElements; i++) {
            Wall wall = new Wall();
            Position position = this.findRandomEmptyPosition();
            wall.setPosition(position);
            this.walls.add(wall);
            this.boardPositions.add(position);
        }
    }

    public void generateStars () {
        int numberOfElements = this.generateRandomNumber(this.size, 2*minNumberOfElement);
        for (int i = 0; i < numberOfElements; i++) {
            int score = Star.calcScore(1, 5);
            Star star = new Star();
            star.setScore(score);
            Position position = this.findRandomEmptyPosition();
            star.setPosition(position);
            this.stars.add(star);
            this.boardPositions.add(position);
        }
    }

    public void generateMines () {
        int numberOfElements =
                this.generateRandomNumber(this.size - 2 - minNumberOfElement, minNumberOfElement);

        for (int i = 0; i < numberOfElements; i++) {
            int score = 5 * Mine.calcScore(0, 5);
            if (score == 0) score = 1;
            Mine mine = new Mine();
            mine.setScore(score);
            Position position = this.findRandomEmptyPosition();
            mine.setPosition(position);
            this.mines.add(mine);
            this.boardPositions.add(position);
            }
    }

    private boolean pathNotBlockOn_X_axis (Position start, Position target) {
        boolean theresNoWall = true;
        PlayerMove info = this.playerInfo(start, target);

        for (Wall wall : this.walls)
            for (int i = info.getBegin(); i <= info.getFinish() ; i++)
                if (wall.getPosition().getX() == info.getConstant() && wall.getPosition().getY() == i) {
                    theresNoWall = false;
                    break;
                }

        return theresNoWall;
    }

    private boolean pathNotBlockedOn_Y_axis (Position start, Position target) {
        boolean theresNoWall = true;
        PlayerMove info = this.playerInfo(start, target);

        for (Wall wall : this.walls)
            for (int i = info.getBegin(); i <= info.getFinish() ; i++)
                if (wall.getPosition().getY() == info.getConstant() && wall.getPosition().getX() == i) {
                    theresNoWall = false;
                    break;
                }
        return theresNoWall;
    }

    private boolean pathNotBlocked (Position start, Position target) {
        if (this.getPlayerDirection(start, target).equals("X")) {
            return this.pathNotBlockOn_X_axis(start, target);
        }
        return this.pathNotBlockedOn_Y_axis(start, target);
    }

    private String getPlayerDirection (Position start, Position target) {
        return start.getX() - target.getX() == 0 ? "X" : "Y";
    }

    private PlayerMove playerInfo (Position start, Position target) {
        int begin, finish, constant;
        String direction = this.getPlayerDirection(start, target);
        if (direction.equals("X")) {
            constant = start.getX();
            begin = Math.min(start.getY(), target.getY());
            finish = Math.max(start.getY(), target.getY());
        } else {
            constant = start.getY();
            begin = Math.min(start.getX(), target.getX());
            finish = Math.max(start.getX(), target.getX());
        }

        return new PlayerMove(begin, finish, constant, direction);
    }

    private void getBoardElementByPlayer (Player player, Position target) {
        Position start = player.getPosition();
        PlayerMove info = this.playerInfo(start, target);


        for (int i = info.getBegin(); i <= info.getFinish() ; i++) {
            for (int j = 0; j < this.mines.size(); j++) {
                Position minePos = this.mines.get(j).getPosition();

                boolean condition = info.getDirection().equals("X") ?
                        minePos.getX() == info.getConstant() && minePos.getY() == i
                        :
                        minePos.getY() == info.getConstant() && minePos.getX() == i;

                    if (condition) {
                        int lostScore = this.mines.get(j).getScore();
                        player.setPlayerScore(player.getPlayerScore() - lostScore);

                        this.mines.get(j).removeElement();
                        this.mines.remove(this.mines.get(j));

                        if (player.getPlayerScore() < 0 ) player.setLoser(true);
                    }
                }
            }

            for (int i = info.getBegin(); i <= info.getFinish() ; i++) {
                for (int j = 0; j < this.stars.size(); j++) {
                    Position starPos = this.stars.get(j).getPosition();

                    boolean condition = info.getDirection().equals("X") ?
                            starPos.getX() == info.getConstant() && starPos.getY() == i
                            :
                            starPos.getY() == info.getConstant() && starPos.getX() == i;

                    if (condition) {
                        int addedScore = this.stars.get(j).getScore();
                        player.setPlayerScore(player.getPlayerScore() + addedScore);
                        this.stars.get(j).removeElement();

                        this.stars.remove(this.stars.get(j));
                    }
                }
            }
    }

    public boolean isMoveOnBoard(Position position) {
        return position.getX() <= size && position.getX() >= 0 &&
                position.getY() <= size && position.getY() >= 0;
    }

    private boolean checkForValidDirectionMove (Position currentPosition, Position targetPosition) {
        return targetPosition.getX() - currentPosition.getX() == 0 ||
                targetPosition.getY() - currentPosition.getY() == 0;
    }

    public boolean isGameFinished () {
        if (this.stars.toArray().length == 0) return true;

        for (Player player: this.players) {
            if (player.isWinner() || player.isLoser()) return true;
            if (player.getPlayerScore() < 0) return true;
        }

        return false;
    }

    public Player winner () {
        int highestScore = 0;
        int index = 0;
        for (int i = 0; i < this.players.size(); i++) {
            if (this.players.get(i).getPlayerScore() >= highestScore) {
                highestScore = this.players.get(i).getPlayerScore();
                index = i;
            }
        }
        return this.players.get(index);
    }

    // the implementation of this function is static
    // if players are more than 2 this function CANNOT check for draw
    public boolean arePlayerDraw () {
        return this.players.get(0).getPlayerScore() == this.players.get(1).getPlayerScore();
    }

    private boolean isNotTheSame (Position player, Position target) {
        return player.getX() != target.getX() || player.getY() != target.getY();
    }

    public void move(Player player, Position position) {
        if (
                this.isMoveOnBoard(position) &&
                this.checkForValidDirectionMove(player.getPosition(), position) &&
                this.isNotTheSame(player.getPosition(), position)
        ) {

            if (this.pathNotBlocked(player.getPosition(), position)) {

                this.getBoardElementByPlayer(player, position);
                this.boardPositions.remove(player.getPosition());
                player.setPosition(position);
                this.boardPositions.add(position);
            } // second if statement
        } // first if statement
    } // method

    public void setSize(int size) { this.size = size; }
}
