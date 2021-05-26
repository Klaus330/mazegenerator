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
import java.util.Random;
import java.util.stream.Collectors;

public class WilsonGenerator extends MazeGenerator {
    private Random random;

    public WilsonGenerator(Maze maze, GraphicsContext graphicsContext) {
        super(maze, graphicsContext);
    }

    @Override
    public void generate() {
        //Used for stopping another animation that is running
        try {
            if (GraphicsController.timeline.getStatus().equals(Animation.Status.RUNNING)) {
                GraphicsController.timeline.stop();
            }
        } catch (NullPointerException ignored) {
        }

        //Initialize the maze
        initMaze();

        //Use a Random class in order to get a random starting Cell
        random = new Random();

        //Mark the first chosen Cell as visited
        this.current = grid.get(random.nextInt(grid.size()));
        this.current.setVisited(true);

        //Choose another random Cell
        this.current = grid.get(random.nextInt(grid.size()));

        //Initialize the animation ( timeline )
        GraphicsController.timeline = new Timeline();

        Duration timePoint = Duration.ZERO;
        Duration pause = Duration.seconds(drawPause);
        timePoint = timePoint.add(pause);

        //Add the initial stage to the animation
        Cell finalStart = this.current;
        KeyFrame keyFrame = new KeyFrame(timePoint, e -> finalStart.show());
        GraphicsController.timeline.getKeyFrames().add(keyFrame);

        //While there exists at least one unvisited cell
        while (!grid.parallelStream().allMatch(Cell::isVisited)) {
            System.out.println(this.current.getX() + " " + this.current.getY());
            carve();
            System.out.println(this.current.getX() + " " + this.current.getY());
            System.out.println("----------");
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

    public void carve() {
        if (this.current.isVisited()) {
            addPathToMaze();

            //Get all the cells not visited
            List<Cell> notInMaze = grid.parallelStream().filter(c -> !c.isVisited()).collect(Collectors.toList());

            if(!notInMaze.isEmpty())
            {
                //Pick a random cell not in the maze to be the this.current one
                this.current = notInMaze.get(random.nextInt(notInMaze.size()));
            }else{
                return;
            }
        }
        this.current.setInPath(true);
        Cell nextCell = this.current.getNotInPathNeighbour(grid);
        if(nextCell != null)
        {
            stack.push(this.current);
            this.current.removeWalls(nextCell);
            this.current = nextCell;
        }else if(!stack.isEmpty())
        {
            try{
                this.current = stack.pop();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private void addPathToMaze() {
        //Iterate through all the cells that are in path and mark them as visited
        this.grid.parallelStream().filter(Cell::isInPath).forEach(c -> {
            c.setVisited(true);
            c.setInPath(false);
        });
        stack.clear();
    }
}
