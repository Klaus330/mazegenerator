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

import java.util.ArrayList;
import java.util.List;

public class PrimGenerator extends MazeGenerator {
    private final List<Cell> frontier = new ArrayList<Cell>();

    public PrimGenerator(Maze maze, GraphicsContext graphicsContext) {
        super(maze, graphicsContext);
    }
    @Override
    public void generate() {
        initMaze();
        try {
            if (GraphicsController.timeline.getStatus().equals(Animation.Status.RUNNING)) {
                GraphicsController.timeline.stop();
            }
        }catch(NullPointerException exception)
        {
            throw new NullPointerException();
        }
        initMaze();

        GraphicsController.timeline = new Timeline();
        Duration timePoint = Duration.ZERO;
        Duration pause = Duration.seconds(drawPause);
        timePoint = timePoint.add(pause);

        Cell finalCurrent = this.current;
        KeyFrame keyFrame = new KeyFrame(timePoint, e -> {
            finalCurrent.show();
        });
        GraphicsController.timeline.getKeyFrames().add(keyFrame);

        while(!maze.getGrid().parallelStream().allMatch(c -> c.isVisited())) {
            this.current.setVisited(true);
            Cell current1 = this.current;
            List<Cell> neighbors = current.getUnvisitedNeighbours();
            frontier.addAll(neighbors);
            int randomNeighbourIndex = (int) (Math.random()*100) % frontier.size();

            this.current = frontier.get(randomNeighbourIndex);

            List<Cell> newNeighbors = current.getAllVisitedNeighbours();

            if (!newNeighbors.isEmpty()) {
                int randomIndex = (int) (Math.random()*100) % newNeighbors.size();
                Cell finalNext = newNeighbors.get(randomIndex);

                    current.removeWalls(finalNext);
                    finalNext.setVisited(true);
                    timePoint = timePoint.add(pause);
                    KeyFrame highlightFrame = new KeyFrame(timePoint, e -> {
                        current1.show();
                    });
                    GraphicsController.timeline.getKeyFrames().add(highlightFrame);

                    timePoint = timePoint.add(pause);
                    KeyFrame showFrame = new KeyFrame(timePoint, e -> {
                        finalNext.show();
                    });
                    GraphicsController.timeline.getKeyFrames().add(showFrame);
            }

            frontier.removeIf(Cell::isVisited);
//            this.current.setVisited(true);
        }
        this.context.clearRect(0,0,800,800);
        this.context.setFill(Color.rgb(204,204,204));
        this.context.fillRect(0,0,800,800);

        GraphicsController.timeline.play();
    }
}
