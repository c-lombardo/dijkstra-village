import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

/**
 * Created by clombardo on 7/30/17.
 */

public class Village implements Drawable {

    // instance fields
    private String name;
    private int id;
    private VillageSystem villageSystem;
    private int centerx;
    private int centery;

    // for displaying to the user
    private int x;
    private int y;

    /**
     * constructs a new village object at 10, 10
     * @param inName the name of the village
     * @param inSystem the system that the village is a member of
     */
    public Village(String inName, VillageSystem inSystem) {
        name = inName;
        villageSystem = inSystem;
        id = villageSystem.newVillageID();
        villageSystem.addVillage(this);
        x = 10;
        y = 10;
        centerx = -1;
        centery = -1;
    }

    /**
     * draws the village
     * @param g the graphics object used to draw the gnome
     */
    public void draw(Graphics2D g) {
        // create a new font
        Font villageFont = new Font("Arial", Font.TYPE1_FONT, 14);
        g.setFont(villageFont);

        // calculate the dimensions of the text with this font, and draw a rectangle
        AffineTransform affinetransform = new AffineTransform();
        FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
        int textwidth = (int)(villageFont.getStringBounds(name, frc).getWidth());
        int textheight = (int)(villageFont.getStringBounds(name, frc).getHeight());
        Rectangle villageSurrounding = new Rectangle(x - 5, y - textheight, textwidth + 12, textheight + 5);
        centerx = (x - 5) + ((textwidth + 12) / 2);
        centery = (y - textheight) + ((textheight + 5) / 2);
        g.setColor(Color.darkGray);
        g.fill(villageSurrounding);

        // draw the name of the village
        g.setColor(Color.white);
        g.drawString(name, x, y);
    }

    /**
     * constructs a new village object
     * @param inName the name of the village
     * @param inSystem the system that the village is a member of
     * @param inX the x value of the location of the village
     * @param inY the y value of the location of the village
     */
    public Village(String inName, VillageSystem inSystem, int inX, int inY) {
        name = inName;
        villageSystem = inSystem;
        id = villageSystem.newVillageID();
        villageSystem.addVillage(this);
        x = inX;
        y = inY;
    }

    /**
     * @return the name of the current village
     */
    public String getName() {
        return name;
    }

    /**
     * @return the x coordinate of this village
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y coordinate of this village
     */
    public int getY() {
        return y;
    }

    /**
     * @return the horizontal center of the village
     */
    public int getCenterx() {
        return centerx;
    }

    /**
     * @return the vertical center of the vilage
     */
    public int getCentery() {
        return centery;
    }
}
