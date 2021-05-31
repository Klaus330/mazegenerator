package maze.solvers;

import maze.Maze;
import utils.Cell;

public class DFSSolver extends Solver {
    public DFSSolver(Maze maze) {
        super(maze);
    }

    public void solve() {
        setup();

        this.current = grid.get(0);
        while (solutionNotFound()) {
            findPath();
        }

        stack.push(grid.get(grid.size()-1));

        getSolution();
        play();
    }

    protected void findPath() {
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

    protected void getSolution() {
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
