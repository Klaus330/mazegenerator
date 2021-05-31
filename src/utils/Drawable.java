package utils;

import controllers.GraphicsController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public abstract class Drawable {
    protected Duration timePoint;
    protected Duration pause;
    protected double drawPause;

    abstract public void play();

    public void setPause(double pause)
    {
        this.drawPause = pause;
    }

    public void setup()
    {
        //Used for stopping another animation that is running
        try {
            if (GraphicsController.timeline.getStatus().equals(Animation.Status.RUNNING)) {
                GraphicsController.timeline.stop();
            }
        }catch(NullPointerException exception)
        {
            throw new NullPointerException();
        }

        GraphicsController.timeline = new Timeline();
        timePoint = Duration.ZERO;
        pause = Duration.seconds(drawPause);
    }

    public KeyFrame showKeyFrame(Cell cell)
    {
        timePoint = timePoint.add(pause);
        return new KeyFrame(timePoint, e -> cell.show());
    }

    public KeyFrame highlightKeyFrame(Cell cell)
    {
        timePoint = timePoint.add(pause);
        return new KeyFrame(timePoint, e -> cell.highlight());
    }

    public KeyFrame inPathFrame(Cell cell)
    {
        timePoint = timePoint.add(pause);
        return new KeyFrame(timePoint, e -> cell.drawInPath());
    }

    public KeyFrame deadEndFrame(Cell cell)
    {
        timePoint = timePoint.add(pause);
        return new KeyFrame(timePoint, e -> cell.deadEnd());
    }

    public void addKeyFrame(KeyFrame frame)
    {
        GraphicsController.timeline.getKeyFrames().add(frame);
    }
}
