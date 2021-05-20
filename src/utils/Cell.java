package utils;

import javafx.scene.canvas.GraphicsContext;

import java.util.List;

public class Cell {
    protected int x;
    protected int y;
    public int size;
    protected GraphicsContext context;
    protected boolean[] walls = {true, true, true, true};
    protected boolean visited = false;

    protected Cell previous;

    public Cell(int x, int y, int size, GraphicsContext context) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.context = context;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }


    public void show(){
        int x = this.getX()*size;
        int y = this.getY()*size;

        if(walls[0]){
            context.strokeLine(x,y,x+size,y); // top
        }

        if(walls[1]){
            context.strokeLine(x+size,y,x+size,y+size); // right
        }

        if(walls[2]){
            context.strokeLine(x+size,y+size,x,y+size); // bottom
        }

        if(walls[3]){
            context.strokeLine(x,y+size,x,y); // left
        }
    }


}
