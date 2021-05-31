package maze;

import javafx.scene.canvas.GraphicsContext;
import utils.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Maze {
    private List<Cell> grid;
    private int size;
    private int cellSize;
    private final GraphicsContext context;

    public Maze(int size, int cellSize, GraphicsContext context) {
        this.size = size;
        this.cellSize = cellSize;
        this.context = context;
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Cell> getGrid() {
        return grid;
    }

    public void setGrid(List<Cell> grid) {
        this.grid = grid;
    }

    public void initCells() {
        grid = new ArrayList<>();
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                grid.add(new Cell(i, j, cellSize, context, this));
            }
        }
    }

    public void unSolve()
    {
        grid = grid.stream().
                peek(cell -> {cell.setDistance(Cell.NOT_REACHED);
                    cell.setDeadEnd(false);
                    cell.setInPath(false);
                }).
                collect(Collectors.toList());
    }
}
