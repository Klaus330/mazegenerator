package maze.generators;

import javafx.scene.canvas.GraphicsContext;
import maze.Maze;
import utils.Cell;

import java.util.List;
import java.util.Random;

public class BinaryTreeGenerator extends MazeGenerator{
    private Random random;
    private int index;

    public BinaryTreeGenerator(Maze maze, GraphicsContext context) {
        super(maze, context);
        random = new Random();
    }

    @Override
    public void generate() {
        setup();

        index = grid.size()-1;
        this.current = this.grid.get(index);

        //Add the initial stage to the animation
        Cell finalStart = this.current;
        addKeyFrame(showKeyFrame(finalStart));

        while(!grid.parallelStream().allMatch(Cell::isVisited))
        {
            carve();
        }

        play();
    }

    public void carve()
    {
        Cell top = this.current.getNeighbor(current.neighborIndex(this.current.getX(),this.current.getY()-1));
        Cell left = this.current.getNeighbor(current.neighborIndex(this.current.getX()-1,this.current.getY()));

        if(top != null && left != null)
        {
            carveDirection(random.nextInt(2));
        }else if(top != null){
            carveDirection(0);
        }else{
            carveDirection(1);
        }

        this.current.setVisited(true);
        addKeyFrame(showKeyFrame(this.current));

        if(index-1 >= 0)
        {
            this.current = grid.get(--index);
        }
    }

    public void carveDirection(int direction)
    {
        List<Cell> neighbours = this.current.getAllNeighbours();
        if(direction == 0)
        {
            for(Cell neighbour:neighbours)
            {
                if(neighbour.getY() + 1 == this.current.getY())
                {
                    this.current.removeWalls(neighbour);
                }
            }
        }else{
            for(Cell neighbour:neighbours)
            {
                if(neighbour.getX() + 1 == this.current.getX())
                {
                    this.current.removeWalls(neighbour);
                }
            }
        }
    }
}
