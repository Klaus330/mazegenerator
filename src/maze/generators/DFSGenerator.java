package maze.generators;

import javafx.scene.canvas.GraphicsContext;
import maze.Maze;
import utils.Cell;


public class DFSGenerator extends MazeGenerator {

    public DFSGenerator(Maze maze, GraphicsContext context) {
        super(maze, context);
    }

    @Override
    public void generate() {
        setup();

        addKeyFrame(showKeyFrame(this.current));

        while (generationNotFinished()) {
            carve();
        }
        play();
    }


    public void carve() {
        this.current.setVisited(true);
        this.current.show();

        // step 1: Choose randomly one of the unvisited neighbours
        Cell next = this.current.checkNeighbors();
        if (next != null) {
            // step 2: Push the current cell to the stack
            stack.push(current);

            // step 3: Remove the wall between the current cell and the chosen cell
            this.current.removeWalls(next);

            showProgress(next);

            next.setVisited(true);
            // step 4: Make the chosen cell the current cell and mark it as visited
            this.current = next;
        } else if (!stack.isEmpty()) {
            current = stack.pop();
        }
    }


    public void showProgress(Cell next)
    {
        addKeyFrame(highlightKeyFrame(next));
        addKeyFrame(showKeyFrame(next));
    }
}
