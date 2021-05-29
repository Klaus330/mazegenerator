package maze.generators;

import javafx.scene.canvas.GraphicsContext;
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

        //Initialize the maze
        setup();

        for (int i = 0; i < grid.size(); i++) {
            grid.get(i).setId(i);
            disjointSet.create_set(grid.get(i).getId());
        }

        stack.addAll(grid);

        //Add the initial stage to the animation
        Cell finalStart = this.current;
        addKeyFrame(showKeyFrame(finalStart));

        while(!grid.parallelStream().allMatch(Cell::isVisited))
        {
            //Carve the walls
            carve();

            Cell finalCurrent = this.current;
            showProgress(finalCurrent);
        }

       play();
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


    public void showProgress(Cell finalCurrent)
    {
        //Highlight the current cell
        addKeyFrame(showKeyFrame(finalCurrent));

        //Display the progress
        addKeyFrame(showKeyFrame(finalCurrent));
    }

}
