package maze.generators;

import controllers.GraphicsController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
        try {
            if (GraphicsController.timeline.getStatus().equals(Animation.Status.RUNNING)) {
                GraphicsController.timeline.stop();
            }
        }catch(NullPointerException exception)
        { }
        initMaze();

        this.current.setVisited(true);
        Cell next = this.current.checkNeighbors();
        int index=0;

        GraphicsController.timeline = new Timeline();

        Duration timePoint = Duration.ZERO ;
        Duration pause = Duration.seconds(0.3);

        Cell finalCurrent = this.current;
        KeyFrame initial = new KeyFrame(timePoint, e -> finalCurrent.show());
        GraphicsController.timeline.getKeyFrames().add(initial);

        while(next != null)
        {

            // step 1
            next.setVisited(true);

            // step 3
            this.current.removeWalls(next);

            Cell finalNext = next;

            timePoint = timePoint.add(pause);
            KeyFrame keyFrame = new KeyFrame(timePoint, e -> finalNext.show());
            GraphicsController.timeline.getKeyFrames().add(keyFrame);

            // step 4
            this.current = next;
            next = this.current.checkNeighbors();

        }
        GraphicsController.timeline.play();
    }
}
