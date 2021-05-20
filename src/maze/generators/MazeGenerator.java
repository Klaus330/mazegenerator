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
        maze.initCells();
        this.current = maze.getGrid().get(0);
    }

    public void initMaze()
    {
        int cellSize = maze.getCellSize();

        context.setStroke(Color.WHITE);

        for (Cell cell: maze.getGrid()) {
            int x = cell.getX()*cellSize;
            int y = cell.getY()*cellSize;

            context.strokeLine(x,y,x+cellSize,y); // top
            context.strokeLine(x+cellSize,y,x+cellSize,y+cellSize); // right
            context.strokeLine(x+cellSize,y+cellSize,x,y+cellSize); // bottom
            context.strokeLine(x,y+cellSize,x,y); // left

        }
        Cell firstCell = maze.getGrid().get(0);
        Cell lastCell = maze.getGrid().get(maze.getGrid().size()-1);
        context.setFill(Color.GREEN);
        context.fillRect(0,0,(firstCell.getX()+1)*cellSize,(firstCell.getY()+1)*cellSize);

        context.setFill(Color.PINK);
        context.fillRect(lastCell.getX()*cellSize,lastCell.getY()*cellSize,(lastCell.getX()+1)*cellSize,(lastCell.getY()+1)*cellSize);

    }


    public abstract void generate();
}
