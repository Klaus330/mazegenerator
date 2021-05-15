package maze.generators;

import javafx.scene.canvas.GraphicsContext;
import maze.Maze;
import javafx.scene.paint.Color;
public class DFSGenerator extends MazeGenerator{

    public DFSGenerator(Maze maze, GraphicsContext context) {
        super(maze, context);
    }

    @Override
    public void generate() {
        initMaze();

        context.setFill(Color.BLACK);
        context.fillRect(0,0,(current.getX()+1)*maze.getCellSize(),(current.getY()+1)*maze.getCellSize());
        current = maze.getGrid().get(1);

        context.setFill(Color.GREEN);
        context.fillRect(current.getX()*maze.getCellSize(),current.getY()*maze.getCellSize(),(current.getX()+1)*maze.getCellSize(),(current.getY())*maze.getCellSize());


    }
}
