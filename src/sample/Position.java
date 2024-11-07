package sample;
import java.util.Objects;

/*
* Position@12121 != Position@23223
* (x = 1, y = 1)  ==  (x = 1 , y = 1)
* */

public class Position {
    private int X;
    private int Y;

    Position(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public void setPosition(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    // Position(1, 1) == Position(1, 1) -> Position@12121 == Positon@12121
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return X == position.X && Y == position.Y;
    }

    // Position(1, 1) -> Position@25778
    @Override
    public int hashCode() {
        return Objects.hash(X, Y);
    }


    @Override
    public String toString() {
        return "{" +
                "X=" + X +
                ", Y=" + Y +
                '}';
    }
}
