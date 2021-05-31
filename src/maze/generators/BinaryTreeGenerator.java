package maze.generators;

import javafx.scene.canvas.GraphicsContext;
import maze.Maze;
import utils.Cell;

import java.util.List;
import java.util.Random;

public class BinaryTreeGenerator extends MazeGenerator{
    private final Random randomChoice;
    private int index;

    public BinaryTreeGenerator(Maze maze, GraphicsContext context) {
        super(maze, context);
        randomChoice = new Random();
    }

    @Override
    public void generate() {
        setup();

        index = grid.size()-1;
        this.current = this.grid.get(index);

        //Add the initial stage to the animation
        addKeyFrame(showKeyFrame(this.current));

        while(generationNotFinished())
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
            carveDirection(randomChoice.nextInt(2));
        }else if(top != null){
            carveDirection(0);
        }else{
            carveDirection(1);
        }

        this.current.setVisited(true);
        addKeyFrame(showKeyFrame(this.current));

        if(index-1 >= 0)
        {
            index--;
            this.current = grid.get(index);
        }
    }

    public boolean isTopNeighbour(Cell neighbour)
    {
        return neighbour.getY() + 1 == this.current.getY();
    }

    public boolean isLeftNeighbour(Cell neighbour)
    {
        return neighbour.getX() + 1 == this.current.getX();
    }

    public void carveDirection(int direction)
    {
        List<Cell> neighbours = this.current.getAllNeighbours();
        if(direction == 0)
        {
            for(Cell neighbour:neighbours)
            {
                if(isTopNeighbour(neighbour))
                {
                    this.current.removeWalls(neighbour);
                }
            }
        }else{
            for(Cell neighbour:neighbours)
            {
                if(isLeftNeighbour(neighbour))
                {
                    this.current.removeWalls(neighbour);
                }
            }
        }
    }
}
