package utils;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import maze.Maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Cell {
    public int size;
    protected int x;
    protected int y;
    protected int id;
    protected GraphicsContext context;
    protected boolean[] walls = {true, true, true, true};
    protected boolean visited;
    protected boolean inPath;
    protected int distance;
    protected Cell parent;

    public static final int NOT_REACHED = -1;

    protected boolean isDeadEnd;

    protected Maze maze;

    public Cell(int x, int y, int size, GraphicsContext context, Maze maze) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.context = context;
        this.maze = maze;
        this.visited = false;
        this.inPath = false;
        this.isDeadEnd = false;
        this.distance = NOT_REACHED;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Cell getParent() {
        return parent;
    }

    public void setParent(Cell parent) {
        this.parent = parent;
    }

    public boolean isDeadEnd() {
        return isDeadEnd;
    }

    public void setDeadEnd(boolean deadEnd) {
        isDeadEnd = deadEnd;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public boolean isInPath() {
        return inPath;
    }

    public void setInPath(boolean inPath) {
        this.inPath = inPath;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void drawWalls() {
        int x0 = this.getX() * size;
        int y0 = this.getY() * size;

        if (walls[0]) {
            context.setStroke(Color.BLACK);
            context.strokeLine(x0, y0, x0 + size, y0); // top
        }

        if (walls[1]) {
            context.setStroke(Color.BLACK);
            context.strokeLine(x0 + size, y0, x0 + size, y0 + size); // right
        }

        if (walls[2]) {
            context.setStroke(Color.BLACK);
            context.strokeLine(x0 + size, y0 + size, x0, y0 + size); // bottom
        }

        if (walls[3]) {
            context.setStroke(Color.BLACK);
            context.strokeLine(x0, y0 + size, x0, y0); // left
        }
    }

    public void show() {
        int x0 = this.getX() * size;
        int y0 = this.getY() * size;

        if (this.visited) {
            context.setFill(Color.ORANGE);
            context.fillRect(x0, y0, size, size);
        }

        drawWalls();
    }

    public void highlight() {
        int x0 = this.getX() * size;
        int y0 = this.getY() * size;
        context.setFill(Color.GREEN);
        context.fillRect(x0, y0, size, size);
    }

    public void deadEnd() {
        int x0 = this.getX() * size;
        int y0 = this.getY() * size;
        context.setFill(Color.RED);
        context.fillRect(x0, y0, size, size);
        drawWalls();
    }

    public void drawInPath() {
        int x0 = this.getX() * size;
        int y0 = this.getY() * size;

        context.setFill(Color.PURPLE);
        context.fillRect(x0, y0, size, size);

        drawWalls();
    }

    public Cell getNotInPathNeighbour(List<Cell> grid) {
        List<Cell> neighbors = new ArrayList<>();
//        List<Pair<Integer,Integer>> coordinates = new ArrayList<>();

        Cell topNeighbor = getNeighbor(neighborIndex(x, y - 1));
        Cell rightNeighbor = getNeighbor(neighborIndex(x + 1, y));
        Cell bottomNeighbor = getNeighbor(neighborIndex(x, y + 1));
        Cell leftNeighbor = getNeighbor(neighborIndex(x - 1, y));

        if (topNeighbor != null && !topNeighbor.isInPath()) {
            neighbors.add(topNeighbor);
        }

        if (rightNeighbor != null && !rightNeighbor.isInPath()) {
            neighbors.add(rightNeighbor);
        }

        if (bottomNeighbor != null && !bottomNeighbor.isInPath()) {
            neighbors.add(bottomNeighbor);
        }

        if (leftNeighbor != null && !leftNeighbor.isInPath()) {
            neighbors.add(leftNeighbor);
        }

        return getRandomNeighbour(neighbors);
    }


    public Cell getRandomNeighbour(List<Cell> neighbours) {
        if (neighbours.size() > 0) {
            if (neighbours.size() == 1) {
                return neighbours.get(0);
            } else {
                Random random = new Random();
                int randomIndex = random.nextInt(neighbours.size());
                return neighbours.get(randomIndex);
            }
        } else {
            return null;
        }
    }

    public Cell checkNeighbors() {
        List<Cell> neighbours = getUnvisitedNeighbours();

        if (neighbours.size() > 0) {
            return getRandomNeighbour(neighbours);
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
        return i + (j * maze.getSize());
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


        if (x == 1) {
            this.walls[3] = false;
            neighbor.walls[1] = false;

        } else if (x == -1) {
            this.walls[1] = false;
            neighbor.walls[3] = false;

        }

        int y = this.y - neighbor.y;
        if (y == 1) {
            this.walls[0] = false;
            neighbor.walls[2] = false;
        } else if (y == -1) {
            this.walls[2] = false;
            neighbor.walls[0] = false;
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cell> getUnvisitedNeighbours() {
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

        Collections.shuffle(neighbors);

        return neighbors;
    }

    public List<Cell> getAllNeighbours() {
        List<Cell> neighbors = new ArrayList<>();

        Cell topNeighbor = getNeighbor(neighborIndex(x, y - 1));
        Cell rightNeighbor = getNeighbor(neighborIndex(x + 1, y));
        Cell bottomNeighbor = getNeighbor(neighborIndex(x, y + 1));
        Cell leftNeighbor = getNeighbor(neighborIndex(x - 1, y));

        if (topNeighbor != null) neighbors.add(topNeighbor);
        if (rightNeighbor != null) neighbors.add(rightNeighbor);
        if (bottomNeighbor != null) neighbors.add(bottomNeighbor);
        if (leftNeighbor != null) neighbors.add(leftNeighbor);

        return neighbors;
    }


    public List<Cell> getAllVisitedNeighbours() {
        List<Cell> allVisitedNeighbours = new ArrayList<>();

        Cell topNeighbor = getNeighbor(neighborIndex(x, y - 1));
        Cell rightNeighbor = getNeighbor(neighborIndex(x + 1, y));
        Cell bottomNeighbor = getNeighbor(neighborIndex(x, y + 1));
        Cell leftNeighbor = getNeighbor(neighborIndex(x - 1, y));

        if (topNeighbor != null && topNeighbor.visited) {
            allVisitedNeighbours.add(topNeighbor);
        }

        if (rightNeighbor != null && rightNeighbor.visited) {
            allVisitedNeighbours.add(rightNeighbor);
        }

        if (bottomNeighbor != null && bottomNeighbor.visited) {
            allVisitedNeighbours.add(bottomNeighbor);
        }

        if (leftNeighbor != null && leftNeighbor.visited) {
            allVisitedNeighbours.add(leftNeighbor);
        }


        return allVisitedNeighbours;
    }

    public Cell getPathNeighbour() {
        List<Cell> neighbors = new ArrayList<>();

        Cell topNeighbor = getNeighbor(neighborIndex(x, y - 1));
        Cell rightNeighbor = getNeighbor(neighborIndex(x + 1, y));
        Cell bottomNeighbor = getNeighbor(neighborIndex(x, y + 1));
        Cell leftNeighbor = getNeighbor(neighborIndex(x - 1, y));

        if (topNeighbor != null && !topNeighbor.isDeadEnd() && !this.walls[0]) {
            neighbors.add(topNeighbor);
        }
        if (rightNeighbor != null && !rightNeighbor.isDeadEnd() && !this.walls[1]) {
            neighbors.add(rightNeighbor);
        }
        if (bottomNeighbor != null && !bottomNeighbor.isDeadEnd() && !this.walls[2]) {
            neighbors.add(bottomNeighbor);
        }
        if (leftNeighbor != null && !leftNeighbor.isDeadEnd() && !this.walls[3]) {
            neighbors.add(leftNeighbor);
        }

        return getRandomNeighbour(neighbors);
    }

    public List<Cell> getAccessibleNeighbours() {
        List<Cell> neighbors = new ArrayList<>();

        Cell topNeighbor = getNeighbor(neighborIndex(x, y - 1));
        Cell rightNeighbor = getNeighbor(neighborIndex(x + 1, y));
        Cell bottomNeighbor = getNeighbor(neighborIndex(x, y + 1));
        Cell leftNeighbor = getNeighbor(neighborIndex(x - 1, y));

        if (topNeighbor != null &&!this.walls[0]) {
            neighbors.add(topNeighbor);
        }
        if (rightNeighbor != null && !this.walls[1]) {
            neighbors.add(rightNeighbor);
        }
        if (bottomNeighbor != null && !this.walls[2]) {
            neighbors.add(bottomNeighbor);
        }
        if (leftNeighbor != null && !this.walls[3]) {
            neighbors.add(leftNeighbor);
        }

        return neighbors;
    }
}
