import java.awt.*;
import java.awt.geom.Line2D;

/**
 * Created by clombardo on 7/30/17.
 */
public class Road implements Drawable {

    // instance fields
    private Village from;
    private Village to;
    private int toll;

    /**
     * initializes a new road object
     * @param inFrom
     * @param inTo
     * @param inToll
     */
    public Road(Village inFrom, Village inTo, int inToll) {
        from = inFrom;
        to = inTo;
        toll = inToll;
    }

    /**
     * draws the road
     * @param g the graphics object used to draw the gnome
     */
    public void draw(Graphics2D g) {

        // draw a line representing the road
        g.setColor(Color.blue);
        g.setStroke(new BasicStroke(2));
        g.draw(new Line2D.Float(from.getCenterx(), from.getCentery(), to.getCenterx(), to.getCentery()));

        // draw a dot in the direction of the road
        double angle = - Math.atan((double) (to.getCentery() - from.getCentery()) /
                (double) (to.getCenterx() - from.getCenterx()));

        double centerPointX = (from.getCenterx() + to.getCenterx()) / 2;
        double centerPointY = (from.getCentery() + to.getCentery()) / 2;

        double shiftX = Math.cos(angle);
        double shiftY = Math.sin(angle);

        double distanceBetween = Math.sqrt(Math.pow(to.getCentery() - from.getCentery(), 2.0) +
                Math.pow(to.getCenterx() - from.getCenterx(), 2.0));

        double dotPointX = 0;
        double dotPointY = 0;

        if (from.getX() < to.getX() && from.getY() < to.getY()) {
            dotPointX = centerPointX + (0.17 * distanceBetween * shiftX);
            dotPointY = centerPointY - (0.17 * distanceBetween * shiftY);
        }
        else if (from.getX() > to.getX() && from.getY() > to.getY()) {
            dotPointX = centerPointX - (0.17 * distanceBetween * shiftX);
            dotPointY = centerPointY + (0.17 * distanceBetween * shiftY);
        }
        else if (from.getX() > to.getX() && from.getY() < to.getY()) {
            dotPointX = centerPointX - (0.17 * distanceBetween * shiftX);
            dotPointY = centerPointY + (0.17 * distanceBetween * shiftY);
        }
        else if (from.getX() < to.getX() && from.getY() > to.getY()) {
            dotPointX = centerPointX + (0.17 * distanceBetween * shiftX);
            dotPointY = centerPointY - (0.17 * distanceBetween * shiftY);
        }
        else if (from.getX() == to.getX() && from.getY() > to.getY()) {
            dotPointX = centerPointX + (0.17 * distanceBetween * shiftX);
            dotPointY = centerPointY - (0.17 * distanceBetween * shiftY);
        }
        else if (from.getX() == to.getX() && from.getY() < to.getY()) {
            dotPointX = centerPointX - (0.17 * distanceBetween * shiftX);
            dotPointY = centerPointY + (0.17 * distanceBetween * shiftY);
        }
        else if (from.getY() == to.getY() && from.getX() < to.getX()) {
            dotPointX = centerPointX + (0.17 * distanceBetween * shiftX);
            dotPointY = centerPointY - (0.17 * distanceBetween * shiftY);
        }
        else if (from.getY() == to.getY() && from.getX() > to.getX()) {
            dotPointX = centerPointX - (0.17 * distanceBetween * shiftX);
            dotPointY = centerPointY + (0.17 * distanceBetween * shiftY);
        }

        if (Math.toDegrees(angle) < 90 && Math.toDegrees(angle) > 0) {
            dotPointX -= 3;
            dotPointY -= 3;
        }
        else if (Math.toDegrees(angle) * (-1) < 90 && Math.toDegrees(angle) * (-1) < 180) {
            dotPointX -= 3;
            dotPointY -= 3;
        }
        g.fillOval((int) dotPointX, (int) dotPointY, 7, 7);

        // write the number representing the toll of the road
        double fontPointX = dotPointX;
        double fontPointY = dotPointY;
        if (Math.toDegrees(angle) <= 70 && Math.toDegrees(angle) >= -70) {
            fontPointY -= 2;
        }
        else if (Math.toDegrees(angle) < 0) {
            fontPointX += 3;
            fontPointY -= 3;
        }
        else if (Math.toDegrees(angle) > 0) {
            fontPointX -= 5;
            fontPointY -= 3;
        }

        g.setFont(new Font("Arial", Font.TYPE1_FONT, 14));
        g.drawString(toll + "", (int) fontPointX, (int) fontPointY);

    }

    /**
     * @return the toll of this road
     */
    public int getToll() {
        return toll;
    }

    /**
     * @return the village that this road is from
     */
    public Village getFrom() {
        return from;
    }

    /**
     * @return the village that this road is to
     */
    public Village getTo() {
        return to;
    }
}
