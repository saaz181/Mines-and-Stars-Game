package sample;

public class Mine extends Star {

    @Override
    public String toString() {
        return "Mine{" +
                "score=" + this.getScore() +
                ", position=" + this.getPosition() +
                '}';
    }
}
