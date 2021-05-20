package maze.generators;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;
import maze.Maze;
import javafx.scene.paint.Color;
import utils.Cell;

import java.util.concurrent.TimeUnit;

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
        int index=0;

        Timeline timeline = new Timeline();
        Duration timePoint = Duration.ZERO ;
        Duration pause = Duration.seconds(0.3);

        KeyFrame initial = new KeyFrame(timePoint, e -> this.current.show());
        timeline.getKeyFrames().add(initial);

        while(next != null)
        {

            // step 1
            next.setVisited(true);

            // step 3
            this.current.removeWalls(next);

            Cell finalNext = next;

            timePoint = timePoint.add(pause);
            KeyFrame keyFrame = new KeyFrame(timePoint, e -> {
                finalNext.show();
                this.current.show();
            });
            timeline.getKeyFrames().add(keyFrame);



            // step 4
            this.current = next;
            next = this.current.checkNeighbors();

        }
        timeline.play();
    }
}
