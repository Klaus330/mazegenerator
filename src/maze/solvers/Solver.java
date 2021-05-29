package maze.solvers;

import controllers.GraphicsController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import maze.Maze;
import utils.Cell;

import java.util.List;
import java.util.Stack;

public abstract class Solver {
    protected Maze maze;
    private GraphicsContext context;
    protected Cell current;
    protected List<Cell> grid;
    protected double drawPause;

    Stack<Cell>stack;

    Duration timePoint;
    Duration pause;

    public Solver(Maze maze, GraphicsContext context) {
        this.maze = GraphicsController.maze;
        this.context = context;
        this.current = maze.getGrid().get(0);
        this.grid = maze.getGrid();
    }

    public void setup()
    {
        //Used for stopping another animation that is running
        try {
            if (GraphicsController.timeline.getStatus().equals(Animation.Status.RUNNING)) {
                GraphicsController.timeline.stop();
            }
        }catch(NullPointerException exception)
        {
            throw new NullPointerException();
        }

        GraphicsController.timeline = new Timeline();
        timePoint = Duration.ZERO;
        pause = Duration.seconds(drawPause);

        stack = new Stack<>();
    }

    public KeyFrame deadEndFrame(Cell cell)
    {
        timePoint = timePoint.add(pause);
        return new KeyFrame(timePoint, e -> cell.deadEnd());
    }

    public KeyFrame highlightKeyFrame(Cell cell)
    {
        timePoint = timePoint.add(pause);
        return new KeyFrame(timePoint, e -> cell.highlight());
    }

    public KeyFrame inPathFrame(Cell cell)
    {
        timePoint = timePoint.add(pause);
        return new KeyFrame(timePoint, e -> cell.drawInPath());
    }

    public void addKeyFrame(KeyFrame frame)
    {
        GraphicsController.timeline.getKeyFrames().add(frame);
    }

    public void play()
    {
        GraphicsController.timeline.play();
    }

    public void setPause(double pause)
    {
        this.drawPause = pause;
    }

    public abstract void solve();
}
