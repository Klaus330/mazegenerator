package maze;

import utils.Cell;

import java.util.ArrayList;
import java.util.List;

public class Maze {
    protected List<Cell> grid;
    protected int size;
    protected int cellSize;

    public Maze(int size, int cellSize) {
        this.size = size;
        this.cellSize = cellSize;
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
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid.add(new Cell(i,j));
            }
        }
    }
}
