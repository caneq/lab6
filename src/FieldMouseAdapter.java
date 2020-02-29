import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.sql.Time;
import java.util.ArrayList;
import java.util.ListIterator;

public class FieldMouseAdapter extends MouseAdapter {

    private ArrayList<BouncingBall> balls;
    private MouseEvent pressedEvent;
    private BouncingBall pressedBall;
    private long pressedTime;
    private Field field;

    public FieldMouseAdapter(ArrayList<BouncingBall> balls, Field field){
        this.balls = balls;
        this.field = field;
    }


    @Override
    public void mousePressed(MouseEvent e) {
        super.mousePressed(e);
        field.pause();
        pressedEvent = e;
        pressedTime = System.currentTimeMillis();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       super.mouseReleased(e);
        for(ListIterator i = balls.listIterator(); i.hasPrevious(); ){
            BouncingBall ball = (BouncingBall)i.previous();
            if (checkPoint(pressedEvent, ball)){
                ball.setSpeed(pressedEvent.getX() - e.getX(), pressedEvent.getY() - e.getY());
            }
        }
        field.resume();
    }

    private boolean checkPoint(MouseEvent e, BouncingBall ball){
        if (Math.abs(e.getY() - ball.getY()) <  ball.getRadius()
         && Math.abs(e.getX() - ball.getX()) <  ball.getRadius()){
            return true;
        }
        return false;
    }


}
