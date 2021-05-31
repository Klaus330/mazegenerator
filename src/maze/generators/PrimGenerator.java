package maze.generators;

import javafx.scene.canvas.GraphicsContext;
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
        setup();
        while(!generationFinished()) {
            carve();
        }
        play();
    }

    public boolean generationFinished(){
        return (maze.getGrid().parallelStream().allMatch(Cell::isVisited));
    }

    public void carve(){
        this.current.setVisited(true);
        Cell finalCurrent = this.current;
        List<Cell> neighbors = current.getUnvisitedNeighbours();
        frontier.addAll(neighbors);
        int randomNeighbourIndex = (int) (Math.random()*100) % frontier.size();

        this.current = frontier.get(randomNeighbourIndex);

        List<Cell> newNeighbors = current.getVisitedNeighbours();

        if (!newNeighbors.isEmpty()) {
            int randomIndex = (int) (Math.random()*100) % newNeighbors.size();
            Cell finalNext = newNeighbors.get(randomIndex);

            current.removeWalls(finalNext);
            finalNext.setVisited(true);

            showProgress(finalCurrent, finalNext);
        }

        frontier.removeIf(Cell::isVisited);
    }


    public void showProgress(Cell current, Cell next)
    {
        addKeyFrame(highlightKeyFrame(current));

        addKeyFrame(showKeyFrame(current));

        addKeyFrame(showKeyFrame(next));
    }

}
