package maze.generators;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import maze.Maze;

public class PrimGenerator extends MazeGenerator {
    public PrimGenerator(Maze maze, GraphicsContext graphicsContext) {
        super(maze, graphicsContext);
    }
    @Override
    public void generate() {
        initMaze();

    }
}
