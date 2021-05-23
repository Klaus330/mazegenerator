package maze.generators;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import maze.Maze;
import utils.Cell;

import java.util.Arrays;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

public class DFSGenerator extends MazeGenerator{

    protected Stack<Cell> stack = new Stack<>();



    public DFSGenerator(Maze maze, GraphicsContext context) {
        super(maze, context);
    }

    @Override
    public void generate() {
        if(timeline != null){
            timeline.stop();
        }
        initMaze();

        timeline = new Timeline();
        Duration timePoint = Duration.ZERO ;
        Duration pause = Duration.seconds(0.2);
        timePoint = timePoint.add(pause);
        Cell  finalCurrent = this.current;
        KeyFrame keyFrame = new KeyFrame(timePoint, e -> {
            finalCurrent.show();
        });
        timeline.getKeyFrames().add(keyFrame);

        while(!maze.getGrid().parallelStream().allMatch(c -> c.isVisited())){
            this.current.setVisited(true);
            this.current.show();


            Cell next = this.current.checkNeighbors();
            if(next != null)
            {
                // step 1
                stack.push(current);

                // step 3
                this.current.removeWalls(next);
                Cell finalNext = next;
                timePoint = timePoint.add(pause);
                KeyFrame highlightFrame = new KeyFrame(timePoint, e -> {

                    finalNext.highlight();
                });
                timeline.getKeyFrames().add(highlightFrame);
                timePoint = timePoint.add(pause);
                KeyFrame showFrame = new KeyFrame(timePoint, e -> {

                    finalNext.show();
                });
                timeline.getKeyFrames().add(showFrame);
                next.setVisited(true);
                // step 4
                this.current = next;
            }else if(!stack.isEmpty()) {
                current = stack.pop();
            }
        }
        this.context.clearRect(0,0,800,800);
        this.context.setFill(Color.BLACK);
        this.context.fillRect(0,0,800,800);

        timeline.play();


    }
}
