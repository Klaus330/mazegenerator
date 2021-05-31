package utils;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import maze.Maze;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Cell {
    public static final int NOT_REACHED = -1;
    public static final int TOP_WALL = 0;
    public static final int RIGHT_WALL = 1;
    public static final int BOTTOM_WALL = 2;
    public static final int LEFT_WALL = 3;
    private final GraphicsContext context;
    protected Maze maze;
    private int size;
    private int x;
    private int y;
    private int id;
    private boolean[] walls = {true, true, true, true};
    private boolean visited;
    private boolean inPath;
    private int distance;
    private boolean isDeadEnd;
    private Cell parent;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void drawWalls() {
        int x0 = this.getX() * size;
        int y0 = this.getY() * size;

        if (walls[TOP_WALL]) {
            context.setStroke(Color.BLACK);
            context.strokeLine(x0, y0, x0 + size, y0);
        }

        if (walls[RIGHT_WALL]) {
            context.setStroke(Color.BLACK);
            context.strokeLine(x0 + size, y0, x0 + size, y0 + size);
        }

        if (walls[BOTTOM_WALL]) {
            context.setStroke(Color.BLACK);
            context.strokeLine(x0 + size, y0 + size, x0, y0 + size);
        }

        if (walls[LEFT_WALL]) {
            context.setStroke(Color.BLACK);
            context.strokeLine(x0, y0 + size, x0, y0);
        }
    }

    public void show() {
        if (this.visited) {
            drawCell(Color.ORANGE);
        }
    }

    public void highlight() {
        drawCell(Color.GREEN);
    }

    public void deadEnd() {
        drawCell(Color.RED);
    }

    public void drawInPath() {
        drawCell(Color.PURPLE);
    }

    public void drawCell(Color color) {
        int x0 = this.getX() * size;
        int y0 = this.getY() * size;

        context.setFill(color);
        context.fillRect(x0, y0, size, size);

        drawWalls();
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

    public int neighborIndex(int i, int j) {
        if (i < 0 || j < 0 || i > maze.getSize() - 1 || j > maze.getSize() - 1) {
            return -1;
        }
        return i + (j * maze.getSize());
    }

    public Cell getNeighbor(int index) {
        if (index == -1) {
            return null;
        }
        return maze.getGrid().get(index);
    }

    public Cell getNotInPathNeighbour(List<Cell> grid) {
        List<Cell> neighbours = getAllNeighbours();
        neighbours = neighbours.stream().filter(Predicate.not(Cell::isInPath)).collect(Collectors.toList());

        return getRandomNeighbour(neighbours);
    }

    public Cell checkNeighbors() {
        List<Cell> neighbours = getUnvisitedNeighbours();

        if (neighbours.size() > 0) {
            return getRandomNeighbour(neighbours);
        } else {
            return null;
        }
    }

    public void removeWalls(Cell neighbour) {
        int x = this.x - neighbour.getX();

        if (x == 1) {
            this.walls[LEFT_WALL] = false;
            neighbour.walls[RIGHT_WALL] = false;

        } else if (x == -1) {
            this.walls[RIGHT_WALL] = false;
            neighbour.walls[LEFT_WALL] = false;
        }

        int y = this.y - neighbour.getY();
        if (y == 1) {
            this.walls[TOP_WALL] = false;
            neighbour.walls[BOTTOM_WALL] = false;
        } else if (y == -1) {
            this.walls[BOTTOM_WALL] = false;
            neighbour.walls[TOP_WALL] = false;
        }
    }

    public List<Cell> getUnvisitedNeighbours() {
        List<Cell> neighbours = getAllNeighbours();
        neighbours.removeAll(getVisitedNeighbours());

        Collections.shuffle(neighbours);

        return neighbours;
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

    public List<Cell> getVisitedNeighbours() {
        List<Cell> neighbours = getAllNeighbours();
        neighbours = neighbours.stream().filter(Cell::isVisited).collect(Collectors.toList());

        return neighbours;
    }

    public Cell getPathNeighbour() {
        List<Cell> neighbours = getAccessibleNeighbours();
        neighbours = neighbours.stream().filter(Predicate.not(Cell::isDeadEnd)).collect(Collectors.toList());

        return getRandomNeighbour(neighbours);
    }

    public List<Cell> getAccessibleNeighbours() {
        List<Cell> neighbors = new ArrayList<>();

        Cell topNeighbor = getNeighbor(neighborIndex(x, y - 1));
        Cell rightNeighbor = getNeighbor(neighborIndex(x + 1, y));
        Cell bottomNeighbor = getNeighbor(neighborIndex(x, y + 1));
        Cell leftNeighbor = getNeighbor(neighborIndex(x - 1, y));

        if (topNeighbor != null && !this.walls[TOP_WALL]) {
            neighbors.add(topNeighbor);
        }
        if (rightNeighbor != null && !this.walls[RIGHT_WALL]) {
            neighbors.add(rightNeighbor);
        }
        if (bottomNeighbor != null && !this.walls[BOTTOM_WALL]) {
            neighbors.add(bottomNeighbor);
        }
        if (leftNeighbor != null && !this.walls[LEFT_WALL]) {
            neighbors.add(leftNeighbor);
        }

        return neighbors;
    }
}
