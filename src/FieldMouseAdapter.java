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
        if (field.isPaused()) return;
        for(ListIterator i = balls.listIterator(balls.size()); i.hasPrevious(); ){
            BouncingBall ball = (BouncingBall)i.previous();
            if (checkPoint(e, ball)){
                pressedEvent = e;
                field.pause();
                pressedTime = System.currentTimeMillis();
                pressedBall = ball;
                break;
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       super.mouseReleased(e);
       if (pressedBall == null) return;

       int timeSpan = (int) (System.currentTimeMillis() - pressedTime);
       pressedBall.setSpeed((e.getX() - pressedEvent.getX()) / timeSpan, (e.getY() - pressedEvent.getY()) / timeSpan);
       pressedBall = null;
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
