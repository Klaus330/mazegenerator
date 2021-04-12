import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Stack;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;

public class Maze {
    private final int width;
    private final int height;
    private BitSet maze;
    private Stack<Integer> stack = new Stack<>();
    private char[] hashKey;
    private int finishPoint;
    private int count = 0;

    public Maze(int width, int height) {
        this.width = width;
        this.height = height;

        maze = new BitSet(width * height);

        finishPoint = (int) (Math.random() * (width / 2)) * 2 + 1;
        maze.set(width * height - 1 - finishPoint);
        finishPoint = width * height - 1 - finishPoint - width;

        int startPoint = (int) (Math.random() * (width / 2)) * 2 + 1;
        maze.set(startPoint);
        maze.set(startPoint + width);

        stack.push(startPoint + width);
    }


    // face un singur pas din generarea labirintului
    // returneaza true daca labirintul este gata
    public boolean generate() {
        if (stack.empty()) {
            System.out.println("Maze generated");
            return true;
        }

        int currentLocation = stack.peek();
        int moveDir;


        List<Integer> move = new ArrayList<>();


        if (canGoNorth(currentLocation)) {
            move.add(-1 * width);
        }
        if (canGoSouth(currentLocation)) {
            move.add(1 * width);
        }
        if (canGoEast(currentLocation)) {
            move.add(1);
        }
        if (canGoWest(currentLocation)) {
            move.add(-1);
        }

        if (move.size() > 0) {
            moveDir = move.get((int) (Math.random() * move.size()));
            maze.set(stack.push(currentLocation + 2 * moveDir));
            maze.set(currentLocation + moveDir);
            return false;
        }
        stack.pop();
        return stack.empty();
    }


    public boolean create() {
        if (stack.empty()) {
            System.out.println("Maze generated");
            return true;
        }

        int currentLocation = stack.peek();
        int moveDir;

        List<Integer> move = new ArrayList<>();


        if (canGoNorth(currentLocation)) {
            move.add(-1 * width);
        }
        if (canGoSouth(currentLocation)) {
            move.add(1 * width);
        }
        if (canGoEast(currentLocation)) {
            move.add(1);
        }
        if (canGoWest(currentLocation)) {
            move.add(-1);
        }

        if (move.size() > 0) {
            count++;
            moveDir = move.get((int) (Math.random() * move.size()));
            maze.set(stack.push(currentLocation + 2 * moveDir));
            int pos = currentLocation + moveDir;
            maze.set(pos);


            if (currentLocation + 2 * moveDir == finishPoint) {
                Stack<Integer> temp = new Stack();
                while (!stack.empty())
                    temp.push(stack.pop());
                stack = temp;
            }
            if (count == 1) {
                Stack<Integer> temp = new Stack();
                while (!stack.empty())
                    temp.push(stack.pop());
                stack = temp;
                count = 0;
            }
            return false;
        }
        stack.pop();
        return stack.empty();
    }


    public boolean canGoNorth(int currentLoc) {
        return (currentLoc - 2 * width >= 0 && !maze.get(currentLoc - 2 * width));
    }


    public boolean canGoSouth(int currentLoc) {
        return (currentLoc + 2 * width < width * height && !maze.get(currentLoc + 2 * width));
    }


    public boolean canGoEast(int currentLoc) {
        return (currentLoc + 2 < width * height && (currentLoc + 2) / width == currentLoc / width && !maze.get(currentLoc + 2));
    }


    public boolean canGoWest(int currentLoc) {
        return (currentLoc - 2 >= 0 && (currentLoc - 2) / width == currentLoc / width && !maze.get(currentLoc - 2));
    }

    public BitSet getMaze() {
        return maze;
    }

    //returns a string representation of the maze
    //1 represents path and 0 represents wall
    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (maze.get(i * width + j)) {
                    str += 1;
                } else str += 0;
            }
            str += "\n";
        }
        return str;
    }

    //draws the maze to a png file
    public void draw(String name){
        BufferedImage bi = new BufferedImage(width, height, TYPE_INT_ARGB);
        Graphics g = bi.getGraphics();
        g.setColor(Color.black);

        for (int i = 0; i < height; i++){
            for (int j = 0; j < width; j++){
                if (!maze.get(i * width + j)){
                    g.drawLine(j, i, j, i);
                }
            }
        }

        try {
            File outputfile = new File(name + ".png");
            ImageIO.write(bi, "png", outputfile);
        } catch (IOException e) {}
    }

}
