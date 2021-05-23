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
        while(!maze.getGrid().parallelStream().allMatch(c -> c.isVisited())){
            carve();
            timeline.play();
        }


    }


    private void carve()
    {
        this.current.setVisited(true);
        this.current.show();

        Duration timePoint = Duration.ZERO ;
        Duration pause = Duration.seconds(1);
        Cell next = this.current.checkNeighbors();
        if(next != null)
        {
            // step 1
            stack.push(current);

            // step 3
            this.current.removeWalls(next);
            Cell finalNext = next;

            timePoint = timePoint.add(pause);
            KeyFrame keyFrame = new KeyFrame(timePoint, e -> {
                finalNext.show();

            });
            timeline.getKeyFrames().add(keyFrame);
            next.setVisited(true);

            // step 4
            this.current = next;
        }else if(!stack.isEmpty()){
            current = stack.pop();
        }
    }
}
