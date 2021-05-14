package utils;

import java.util.List;

public class Cell {
    protected int x;
    protected int y;

    protected boolean[] walls = {true, true, true, true};
    protected boolean visited = false;

    protected Cell previous;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
