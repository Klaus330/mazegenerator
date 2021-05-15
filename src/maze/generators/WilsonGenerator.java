package maze.generators;

import javafx.scene.canvas.GraphicsContext;
import maze.Maze;

public class WilsonGenerator extends MazeGenerator {
    public WilsonGenerator(Maze maze, GraphicsContext graphicsContext) {
        super(maze, graphicsContext);
    }
    @Override
    public void generate() {
        initMaze();

    }
}
