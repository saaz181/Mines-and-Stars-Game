package sample;
import javafx.scene.image.ImageView;
import java.util.Random;

public class Star {
    private int score = 0;
    private Position position;
    private ImageView elementImage;

    Star () { }
    Star (int score, Position position) {
        this.setScore(score);
        this.setPosition(position);
    }

    public void removeElement() {
        this.position = new Position(-1, -1);
        this.score = 0;
    }

    public static int calcScore(int minScore, int maxScore) {
        Random r = new Random();
        return r.nextInt(maxScore - minScore) + minScore;
    }


    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public ImageView getElementImage() {
        return elementImage;
    }

    public void setElementImage(ImageView elementImage) {
        this.elementImage = elementImage;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "Star{" +
                "score=" + score +
                ", position=" + position +
                '}';
    }
}
