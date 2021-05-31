package maze.solvers;

import javafx.scene.canvas.GraphicsContext;
import maze.Maze;
import utils.Cell;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class DijkstraSolver extends Solver{
    private final Queue<Cell> queue;

    public DijkstraSolver(Maze maze, GraphicsContext context) {
        super(maze, context);
        queue = new PriorityQueue<>(new DistanceComparator());
    }

    public void solve()
    {
        setup();
        this.current = this.grid.get(0);
        this.current.setDistance(0);

        queue.offer(this.current);
        while(!solutionFound())
        {
            path();
        }
        getSolution();
        play();
    }

    private void path()
    {
        this.current.setDeadEnd(true);
        addKeyFrame(deadEndFrame(this.current));
        this.current = queue.poll();

        List<Cell> neighbours = this.current.getAccessibleNeighbours();
        for(Cell neighbour:neighbours)
        {
            if(neighbour.getDistance() == Cell.NOT_REACHED)
            {
                neighbour.setDistance(this.current.getDistance() + 1);
                neighbour.setParent(this.current);
                queue.offer(neighbour);
            }
        }
    }

    private void getSolution()
    {
        while(this.current != grid.get(0))
        {
            this.current.setInPath(true);
            addKeyFrame(inPathFrame(this.current));
            this.current = current.getParent();
        }
    }

    private class DistanceComparator implements Comparator<Cell> {
        Cell target = grid.get(grid.size() - 1);

        @Override
        public int compare(Cell first, Cell second) {
            if (getDistanceFromTarget(first) > getDistanceFromTarget(second)) {
                return 1;
            } else {
                return getDistanceFromTarget(first) < getDistanceFromTarget(second) ? -1 : 0;
            }
        }

        private double getDistanceFromTarget(Cell current) {
            return Math.hypot(current.getX() - target.getX(), current.getY() - target.getY());
        }
    }
}
