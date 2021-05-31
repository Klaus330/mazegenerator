package maze.generators;

import javafx.scene.canvas.GraphicsContext;
import maze.Maze;
import utils.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WilsonGenerator extends MazeGenerator {
    private Random randomChoice;
    private final List<Cell>cellsInPath = new ArrayList<>();
    
    public WilsonGenerator(Maze maze, GraphicsContext graphicsContext) {
        super(maze, graphicsContext);
    }

    @Override
    public void generate() {
        setup();
        //Use a Random class in order to get a random starting Cell
        randomChoice = new Random();

        //Mark the first chosen Cell as visited
        this.current = grid.get(randomChoice.nextInt(grid.size()));
        this.current.setVisited(true);

        //Choose another random Cell
        this.current = grid.get(randomChoice.nextInt(grid.size()));

        //Add the initial stage to the animation
        addKeyFrame(showKeyFrame(this.current));

        //While there exists at least one unvisited cell
        while (generationNotFinished()) {

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
            List<Cell> notSelectedYet = grid.parallelStream().filter(c -> !c.isVisited()).collect(Collectors.toList());

            if(!notSelectedYet.isEmpty())
            {
                //Pick a random cell not in the maze to be the this.current one
                this.current = notSelectedYet.get(randomChoice.nextInt(notSelectedYet.size()));
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
        }

        stack.clear();
        cellsInPath.clear();
    }
}
