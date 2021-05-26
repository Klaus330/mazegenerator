package maze.generators;

import controllers.GraphicsController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import utils.DisjointSet;
import maze.Maze;
import utils.Cell;

import java.util.Collections;
import java.util.List;

public class KruskalGenerator extends MazeGenerator{
    public KruskalGenerator(Maze maze, GraphicsContext graphicsContext)
    {
        super(maze, graphicsContext);
    }
    private final DisjointSet disjointSet = new DisjointSet();

    @Override
    public void generate() {
        //Used for stopping another animation that is running
        try {
            if (GraphicsController.timeline.getStatus().equals(Animation.Status.RUNNING)) {
                GraphicsController.timeline.stop();
            }
        }catch(NullPointerException ignored)
        {
        }

        //Initialize the maze
        initMaze();

        for (int i = 0; i < grid.size(); i++) {
            grid.get(i).setId(i);
            disjointSet.create_set(grid.get(i).getId());
        }

        stack.addAll(grid);

        //Initialize the animation ( timeline )
        GraphicsController.timeline = new Timeline();

        Duration timePoint = Duration.ZERO;
        Duration pause = Duration.seconds(drawPause);
        timePoint = timePoint.add(pause);

        //Add the initial stage to the animation
        Cell finalStart = this.current;
        KeyFrame keyFrame = new KeyFrame(timePoint, e -> finalStart.show());
        GraphicsController.timeline.getKeyFrames().add(keyFrame);

        while(!grid.parallelStream().allMatch(Cell::isVisited))
        {
            //Carve the walls
            carve();

            //Highlight the current cell
            Cell finalCurrent = this.current;
            timePoint = timePoint.add(pause);
            KeyFrame highlightFrame = new KeyFrame(timePoint, e -> finalCurrent.highlight());
            GraphicsController.timeline.getKeyFrames().add(highlightFrame);

            //Display the progress
            timePoint = timePoint.add(pause);
            KeyFrame showFrame = new KeyFrame(timePoint, e -> finalCurrent.show());
            GraphicsController.timeline.getKeyFrames().add(showFrame);
        }

        //Clear the display
        this.context.clearRect(0,0,800,800);
        this.context.setFill(Color.rgb(204,204,204));
        this.context.fillRect(0,0,800,800);

        //Start the animation
        GraphicsController.timeline.play();
    }

    private void carve() {
        this.current = stack.pop();
        this.current.setVisited(true);

        List<Cell> neighbours = this.current.getUnvisitedNeighbours();

        for (Cell next : neighbours) {
            if (disjointSet.find_set(this.current.getId()) != disjointSet.find_set(next.getId())) {
                this.current.removeWalls(next);

                disjointSet.union(this.current.getId(), next.getId());
            }
        }

        Collections.shuffle(stack);
    }
}
