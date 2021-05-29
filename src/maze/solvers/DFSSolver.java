package maze.solvers;

import javafx.scene.canvas.GraphicsContext;
import maze.Maze;
import utils.Cell;

public class DFSSolver extends Solver {
    public DFSSolver(Maze maze, GraphicsContext context) {
        super(maze, context);
    }

    public void solve() {
        setup();
        Cell lastCell = grid.get(grid.size() - 1);

        this.current = grid.get(0);
        while (this.current != grid.get(grid.size()-1)) {
            path();
        }
        getSolution();
        play();
    }

    public void path() {
        this.current.setDeadEnd(true);
        addKeyFrame(deadEndFrame(this.current));

        Cell nextCell = this.current.getPathNeighbour();

        if (nextCell != null) {
            stack.push(nextCell);
            this.current = nextCell;
        } else if (!stack.isEmpty()) {
            try {
                this.current = stack.pop();
            } catch (Exception ignore) {
            }
        }
    }

    private void getSolution() {
        while (!stack.isEmpty()) {
            try {
                Cell solutionCell = stack.pop();
                solutionCell.setInPath(true);
                addKeyFrame(inPathFrame(solutionCell));
            } catch (Exception ignore) {
            }

        }
    }
}
