package maze.generators;

import javafx.scene.canvas.GraphicsContext;
import maze.Maze;
import javafx.scene.paint.Color;
import utils.Cell;

public class DFSGenerator extends MazeGenerator{

    public DFSGenerator(Maze maze, GraphicsContext context) {
        super(maze, context);
    }

    @Override
    public void generate() {
        initMaze();

        this.current.setVisited(true);
        this.current.show();
        Cell next = this.current.checkNeighbors();

        while(next != null)
        {
            next.setVisited(true);
            next.show();
            this.current = next;
            next = this.current.checkNeighbors();
        }






    }
}
