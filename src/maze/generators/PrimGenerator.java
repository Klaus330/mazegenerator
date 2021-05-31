package maze.generators;

import javafx.scene.canvas.GraphicsContext;
import maze.Maze;
import utils.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrimGenerator extends MazeGenerator {
    private final List<Cell> boundaries = new ArrayList<>();
    private final Random randomChoice;

    public PrimGenerator(Maze maze) {
        super(maze);
        randomChoice = new Random();
    }
    @Override
    public void generate() {
        setup();
        while(generationNotFinished()) {
            carve();
        }
        play();
    }

    public void carve(){
        this.current.setVisited(true);
        Cell lastCurrent = this.current;

        List<Cell> neighbors = current.getUnvisitedNeighbours();
        boundaries.addAll(neighbors);

        int randomNeighbourIndex = randomChoice.nextInt(boundaries.size());

        this.current = boundaries.get(randomNeighbourIndex);

        List<Cell> newNeighbors = current.getVisitedNeighbours();

        if (!newNeighbors.isEmpty()) {
            int randomIndex = randomChoice.nextInt(newNeighbors.size());
            Cell next = newNeighbors.get(randomIndex);

            current.removeWalls(next);
            next.setVisited(true);

            showProgress(lastCurrent, next);
        }

        boundaries.removeIf(Cell::isVisited);
    }


    public void showProgress(Cell current, Cell next)
    {
        addKeyFrame(highlightKeyFrame(current));

        addKeyFrame(showKeyFrame(current));

        addKeyFrame(showKeyFrame(next));
    }

}
