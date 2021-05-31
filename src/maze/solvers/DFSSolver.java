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

        this.current = grid.get(0);
        while (!solutionFound()) {
            path();
        }
        stack.push(grid.get(grid.size()-1));
        getSolution();
        play();
    }

    private void path() {
        this.current.setDeadEnd(true);
        addKeyFrame(deadEndFrame(this.current));

        Cell nextCell = this.current.getPathNeighbour();

        if (nextCell != null) {
            stack.push(this.current);
            this.current = nextCell;
        } else if (!stack.isEmpty()) {
            try {
                this.current = stack.pop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void getSolution() {
        while (!stack.isEmpty()) {
            try {
                Cell solutionCell = stack.pop();
                solutionCell.setInPathSolve(true);
                addKeyFrame(inPathFrame(solutionCell));
            } catch (Exception ignore) {
            }

        }
    }
}
