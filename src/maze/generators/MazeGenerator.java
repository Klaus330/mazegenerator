package maze.generators;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import maze.Maze;
import utils.Cell;

import java.util.List;
import java.util.Stack;

public abstract class MazeGenerator{
    protected Maze maze;
    protected Cell current;
    protected GraphicsContext context;
    protected double drawPause;
    protected Stack<Cell> stack = new Stack<>();
    protected List<Cell> grid;

    public MazeGenerator(Maze maze, GraphicsContext context) {
        this.maze = maze;
        this.context = context;
        maze.initCells();
        this.current = maze.getGrid().get(0);
        this.grid = maze.getGrid();
    }

    public void initMaze()
    {
        int cellSize = maze.getCellSize();

        context.setStroke(Color.WHITE);

        this.displayCells();

        Cell firstCell = maze.getGrid().get(0);
        Cell lastCell = maze.getGrid().get(maze.getGrid().size()-1);
        context.setFill(Color.GREEN);
        context.fillRect(0+1,0+1,(firstCell.getX()+1)*cellSize-1,(firstCell.getY()+1)*cellSize-1);

        context.setFill(Color.PINK);
        context.fillRect(lastCell.getX()*cellSize+1,lastCell.getY()*cellSize+1,(lastCell.getX()+1)*cellSize-1,(lastCell.getY()+1)*cellSize-1);

    }
    public void displayCells(){
        for (Cell cell: maze.getGrid()) {
            cell.show();
        }
    }

    public void setPause(double pause)
    {
        this.drawPause = pause;
    }

    public abstract void generate();
}
