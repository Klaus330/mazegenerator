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
import java.util.Random;
import java.util.stream.Collectors;

public class WilsonGenerator extends MazeGenerator {
    private Random random;
    private List<Cell>cellsInPath = new ArrayList<>();
    public WilsonGenerator(Maze maze, GraphicsContext graphicsContext) {
        super(maze, graphicsContext);
    }

    @Override
    public void generate() {
        setup();
        //Use a Random class in order to get a random starting Cell
        random = new Random();

        //Mark the first chosen Cell as visited
        this.current = grid.get(random.nextInt(grid.size()));
        this.current.setVisited(true);

        //Choose another random Cell
        this.current = grid.get(random.nextInt(grid.size()));

        //Add the initial stage to the animation
        Cell finalStart = this.current;
        addKeyFrame(showKeyFrame(finalStart));

        //While there exists at least one unvisited cell
        while (!grid.parallelStream().allMatch(Cell::isVisited)) {

            carve();
            addKeyFrame(highlightKeyFrame(this.current));
            addKeyFrame(inPathFrame(this.current));
        }

        //Start the animation
        addKeyFrame(showKeyFrame(this.current));
        play();
    }

    public void carve() {
        if (this.current.isVisited()) {
            addPathToMaze();

            addKeyFrame(showKeyFrame(this.current));

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
        cellsInPath.add(this.current);
        Cell nextCell = this.current.getNotInPathNeighbour(grid);
        if(nextCell != null)
        {
            stack.addElement(this.current);
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
        }else{
            this.current.setVisited(true);
        }
    }

    private void addPathToMaze() {
        //Iterate through all the cells that are in path and mark them as visited
        for(Cell c:cellsInPath){
            int index = this.grid.indexOf(c);

            addKeyFrame(showKeyFrame(this.grid.get(index)));
            this.grid.get(index).setVisited(true);
            this.grid.get(index).setInPath(false);
        };

        stack.clear();
        cellsInPath.clear();
    }
}
