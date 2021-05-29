package maze.solvers;

import javafx.scene.canvas.GraphicsContext;
import maze.Maze;

public class DijkstraSolver extends Solver{

    public DijkstraSolver(Maze maze, GraphicsContext context) {
        super(maze, context);
    }

    public void solve()
    {
        setup();
    }
}
