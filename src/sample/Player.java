package sample;
import javafx.scene.image.Image;
import java.util.Arrays;


public class Player {
    private String username; // none
    private String playerColor;
    private Position position;
    private int playerScore = 0;
    private boolean winner = false;
    private boolean loser = false;
    private boolean turn = false;
    private Image playerImage;

    public Player(String username, String playerColor, Position playerPosition) {
        this.setUsername(username);
        this.setPlayerColor(playerColor);
        this.setPosition(playerPosition);
        this.setPlayerScore(playerScore); // == 0
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerColor='" + playerColor + '\'' +
                ", playerPosition=" + position +
                ", playerScore=" + playerScore +
                ", winner=" + winner +
                ", turn=" + turn +
                '}';
    }

    public void setPlayerColor(String playerColor) {
        String[] colors = {"red", "blue", "black", "yellow", "white", "orange", "purple"};
        if (Arrays.stream(colors).toList().contains(playerColor.toLowerCase()))
            this.playerColor = playerColor;
        else System.out.println("You can't set this color to player");
    }

    public String getPlayerColor() { return playerColor; }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Image getPlayerImage() {
        return playerImage;
    }

    public void setPlayerImage(Image playerImage) {
        this.playerImage = playerImage;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    // getter turn
    public boolean isTurn() {
        return turn;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    // getter winner
    public boolean isWinner() {
        return winner;
    }

    public void setLoser(boolean loser) {
        this.loser = loser;
    }

    // getter loser
    public boolean isLoser() {
        return loser;
    }

}
