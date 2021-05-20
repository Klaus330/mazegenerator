package utils;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import maze.Maze;

import java.util.ArrayList;
import java.util.List;

public class Cell {
    protected int x;
    protected int y;
    public int size;
    protected GraphicsContext context;
    protected boolean[] walls = {true, true, true, true};
    protected boolean visited = false;

    protected Maze maze;

    public Cell(int x, int y, int size, GraphicsContext context, Maze maze) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.context = context;
        this.maze = maze;
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


    public void show() {
        int x0 = this.x * size;
        int y0 = this.y * size;
        if (this.visited) {
            context.setFill(Color.ORANGE);
            context.clearRect(x0, y0, size, size);
            context.fillRect(x0, y0, size, size);
        }

        if (walls[0]) {
            context.strokeLine(x0, y0, x0 + size, y0); // top
        }

        if (walls[1]) {
            context.strokeLine(x0 + size, y0, x0 + size, y0 + size); // right
        }

        if (walls[2]) {
            context.strokeLine(x0 + size, y0 + size, x0, y0 + size); // bottom
        }

        if (walls[3]) {
            context.strokeLine(x0, y0 + size, x0, y0); // left
        }
    }
    public void highlight()
    {
        int x0 = this.getX() * size;
        int y0 = this.getY() * size;
        context.setFill(Color.GREEN);
        context.clearRect(x0, y0, size, size);
        context.fillRect(x0, y0, size, size);
    }


    public Cell checkNeighbors() {
        List<Cell> neighbors = new ArrayList<>();

        Cell topNeighbor = getNeighbor(neighborIndex(x, y - 1));
        Cell rightNeighbor = getNeighbor(neighborIndex(x + 1, y));
        Cell bottomNeighbor = getNeighbor(neighborIndex(x, y + 1));
        Cell leftNeighbor = getNeighbor(neighborIndex(x - 1, y));

        if (topNeighbor != null && !topNeighbor.visited) {
            neighbors.add(topNeighbor);
        }

        if (rightNeighbor != null && !rightNeighbor.visited) {
            neighbors.add(rightNeighbor);
        }

        if (bottomNeighbor != null && !bottomNeighbor.visited) {
            neighbors.add(bottomNeighbor);
        }

        if (leftNeighbor != null && !leftNeighbor.visited) {
            neighbors.add(leftNeighbor);
        }


        if (neighbors.size() > 0) {
            int randomIndex = (int) (Math.random()*100) % neighbors.size();
            return neighbors.get(randomIndex);
        } else {
            return null;
        }
    }


    public Cell getNeighbor(int index) {
        if (index == -1) {
            return null;
        }

        return maze.getGrid().get(index);
    }


    public int neighborIndex(int i, int j) {
        if (i < 0 || j < 0 || i > maze.getSize() - 1 || j > maze.getSize() - 1) {
            return -1;
        }
        int index = i + (j * maze.getSize());
        return index;
    }


    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean[] getWalls() {
        return walls;
    }

    public void setWalls(boolean[] walls) {
        this.walls = walls;
    }


    public void removeWalls(Cell neighbor) {
        int x = this.x - neighbor.x;


        if (x ==  1) {
            this.walls[3] = false;
            neighbor.walls[1] = false;
        } else if (x ==  -1) {
            this.walls[1] = false;
            neighbor.walls[3] = false;
        }

        int y = this.y - neighbor.y;
        if (y ==  1) {
            this.walls[0] = false;
            neighbor.walls[2] = false;
        } else if (y ==  -1) {
            this.walls[2] = false;
            neighbor.walls[0] = false;
        }
    }

}
