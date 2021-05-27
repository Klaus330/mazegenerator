package maze.generators;

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

public abstract class MazeGenerator{
    protected Maze maze;
    protected Cell current;
    protected GraphicsContext context;
    protected double drawPause;
    protected Stack<Cell> stack;
    protected List<Cell> grid;

    Duration timePoint;
    Duration pause;

    public MazeGenerator(Maze maze, GraphicsContext context) {
        this.maze = maze;
        this.context = context;
        maze.initCells();
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

    public void displayCells(){
        for (Cell cell: maze.getGrid()) {
            cell.show();
        }
    }

    public KeyFrame showKeyFrame(Cell cell)
    {
        timePoint = timePoint.add(pause);
        return new KeyFrame(timePoint, e -> cell.show());
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
        this.context.clearRect(0,0,800,800);
        this.context.setFill(Color.rgb(204,204,204));
        this.context.fillRect(0,0,800,800);

        GraphicsController.timeline.play();
    }

    public void setPause(double pause)
    {
        this.drawPause = pause;
    }

    public abstract void generate();
}
