package util;

import java.util.Objects;

public class Pair {
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isNegativePair() {
        return x == -1 && y == -1;
    }

    public void setNegative() {
        x = -1;
        y = -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return x == pair.x && y == pair.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}