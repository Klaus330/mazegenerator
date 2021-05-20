package maze.generators;

import com.sun.webkit.Timer;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;
import maze.Maze;
import utils.Cell;

public class DFSGenerator extends MazeGenerator{

    public DFSGenerator(Maze maze, GraphicsContext context) {
        super(maze, context);
    }

    @Override
    public void generate() {
        initMaze();

        this.current.setVisited(true);
        Cell next = this.current.checkNeighbors();

        Timeline timeline = new Timeline();
        Duration timePoint = Duration.ZERO ;
        Duration pause = Duration.seconds(1);

        KeyFrame initial = new KeyFrame(timePoint, e -> this.current.show());
        timeline.getKeyFrames().add(initial);

        while(next != null)
        {
            next.setVisited(true);
            Cell finalNext = next;

            timePoint = timePoint.add(pause);
            KeyFrame keyFrame = new KeyFrame(timePoint, e -> {
                finalNext.show();
            });
            timeline.getKeyFrames().add(keyFrame);

            this.current = next;
            next = this.current.checkNeighbors();
        }
        timeline.play();
    }
}
