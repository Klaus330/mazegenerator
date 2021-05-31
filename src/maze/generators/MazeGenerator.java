package maze.generators;

import controllers.GraphicsController;
import javafx.animation.KeyFrame;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import maze.Maze;
import utils.Cell;
import utils.Drawable;

import java.util.List;
import java.util.Stack;

public abstract class MazeGenerator extends Drawable {
    protected Maze maze;
    protected Cell current;
    protected Stack<Cell> stack;
    protected List<Cell> grid;

    public MazeGenerator(Maze maze) {
        this.maze = maze;
        maze.initCells();
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
        GraphicsController.graphicsContext.clearRect(0,0,800,800);
        GraphicsController.graphicsContext.setFill(Color.rgb(204,204,204));
        GraphicsController.graphicsContext.fillRect(0,0,800,800);

        timePoint = timePoint.add(Duration.ZERO);
        GraphicsController.timeline.getKeyFrames().add(new KeyFrame(timePoint, e -> GraphicsController.isGenerated = true));
        GraphicsController.timeline.getKeyFrames().add(new KeyFrame(timePoint, e -> GraphicsController.isGenerating = false));
        GraphicsController.timeline.getKeyFrames().add(new KeyFrame(timePoint, e -> GraphicsController.isSolved = false));

        GraphicsController.timeline.play();
        GraphicsController.maze = maze;
    }

    public abstract void generate();

    public boolean generationNotFinished() {
        return (!maze.getGrid().parallelStream().allMatch(Cell::isVisited));
    }
}
