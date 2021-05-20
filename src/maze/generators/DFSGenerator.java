package maze.generators;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;
import maze.Maze;
import javafx.scene.paint.Color;
import utils.Cell;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class DFSGenerator extends MazeGenerator{




    public DFSGenerator(Maze maze, GraphicsContext context) {
        super(maze, context);
    }

    @Override
    public void generate() {
        if(timeline != null){
            timeline.stop();
        }

        initMaze();

        this.current.setVisited(true);
        Cell next = this.current.checkNeighbors();
        int index=0;

        timeline = new Timeline();
        Duration timePoint = Duration.ZERO ;
        Duration pause = Duration.seconds(0.3);

//        KeyFrame initial = new KeyFrame(timePoint, e -> this.current.show());
//        timeline.getKeyFrames().add(initial);

        while(next != null)
        {

            // step 1
            next.setVisited(true);

            // step 3
            this.current.removeWalls(next);

            System.out.println(Arrays.toString(this.current.getWalls()));
            Cell finalNext = next;

            timePoint = timePoint.add(pause);
            KeyFrame keyFrame = new KeyFrame(timePoint, e -> {
                finalNext.show();
            });
            timeline.getKeyFrames().add(keyFrame);



            // step 4
            this.current = next;
            next = this.current.checkNeighbors();

        }
        timeline.play();
    }
}
