package maze.generators;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import maze.Maze;
import utils.Cell;

public abstract class MazeGenerator{
    protected Maze maze;
    protected Cell current;
    protected GraphicsContext context;
    public MazeGenerator(Maze maze, GraphicsContext context) {
        this.maze = maze;
        this.context = context;
    }


    public void initMaze()
    {
        maze.initCells();
        int cellSize = maze.getCellSize();

        context.setStroke(Color.RED);

        for (Cell cell: maze.getGrid()) {
            context.strokeRect(cell.getX()*cellSize,cell.getY()*cellSize,(cell.getX()+1)*cellSize,(cell.getY()+1)*cellSize);
        }

    }


    public abstract void generate();
}
