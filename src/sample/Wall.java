package sample;
import javafx.scene.shape.Rectangle;

public class Wall {
    private Rectangle rectangle;
    Position position;

    Wall() {}
    Wall (Position position) { this.setPosition(position); }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}
