import java.util.BitSet;
import java.util.Stack;

public class MazeGenerator implements Runnable {

    Maze maze;
    BitSet bitSet;
    Thread thread;
    int height = 10;
    int width = 10;
    int scale = 8;
    int w = width*2+1;
    int h = height*2+1;
    int count = 0;
    int reprint = 0;


    public MazeGenerator(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public void init(){
        maze = new Maze(width, height);
        bitSet = maze.getMaze();
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (!maze.create()){
            count++;
            bitSet = maze.getMaze();
            try {
                thread.sleep(6,0);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
//        System.out.println(maze.toString());
        maze.draw("draw");
    }
}
