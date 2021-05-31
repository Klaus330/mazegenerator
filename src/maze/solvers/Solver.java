package maze.solvers;

import controllers.GraphicsController;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import maze.Maze;
import utils.Cell;
import utils.Drawable;

import java.util.List;
import java.util.Stack;

public abstract class Solver extends Drawable {
    protected Maze maze;
    protected Cell current;
    protected List<Cell> grid;

    Stack<Cell>stack;

    public Solver(Maze maze) {
        this.maze = GraphicsController.maze;
        this.current = maze.getGrid().get(0);
        this.grid = maze.getGrid();
    }

    public void setup()
    {
        super.setup();
        stack = new Stack<>();
    }

    public void play()
    {
        timePoint = timePoint.add(Duration.ZERO);
        GraphicsController.timeline.getKeyFrames().add(new KeyFrame(timePoint, e -> GraphicsController.isSolved = true));
        GraphicsController.timeline.getKeyFrames().add(new KeyFrame(timePoint, e -> GraphicsController.isSolving = false));

        GraphicsController.timeline.play();
    }

    protected boolean solutionNotFound() {
        return !this.current.equals(grid.get(grid.size() - 1));
    }

    public abstract void solve();

    protected abstract void findPath();

    protected abstract void getSolution();
}
