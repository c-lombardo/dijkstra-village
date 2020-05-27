import java.awt.*;

/**
 * Created by clombardo on 7/30/17.
 */
public class Gnome implements Drawable {

    // instance fields
    private String name;
    private String favColor;
    private int importanceLevel;
    private VillageSystem villageSystem; // the village system this gnome belongs to
    private int idNum;
    private Village currentVillage;
    private Village desiredVillage;
    Color displayColor;

    // importance levels
    public static final int STANDARD = 1;
    public static final int VIP = 2;
    public static final int GOLDSTAR = 3;

    /**
     * initializes a new gnome object
     * @param inName the name of the gnome
     * @param inColor the favortite color of the gnome
     * @param inImportanceLevel the importance level of the gnome
     * @param inVillage the village that this gnome is being initialzed in
     * @param inVillageSystem the village system that this gnome is in
     */
    public Gnome(String inName, String inColor, int inImportanceLevel,
                 Village inVillage, VillageSystem inVillageSystem) {
        name = inName;
        favColor = inColor;
        importanceLevel = inImportanceLevel;
        villageSystem = inVillageSystem;
        idNum = villageSystem.newGnomeID();
        currentVillage = inVillage;
        villageSystem.addGnome(this);
        displayColor = Color.black;
    }

    /**
     * initializes a new gnome object
     * @param inName the name of the gnome
     * @param inColor the favortite color of the gnome
     * @param inImportanceLevel the importance level of the gnome
     * @param inVillage the village that this gnome is being initialzed in
     * @param inDesiredVillage the village that the gnome wants to go to
     * @param inVillageSystem the village system that this gnome is in
     */
    public Gnome(String inName, String inColor, int inImportanceLevel,
                 Village inVillage, Village inDesiredVillage, VillageSystem inVillageSystem) {
        name = inName;
        favColor = inColor;
        importanceLevel = inImportanceLevel;
        villageSystem = inVillageSystem;
        idNum = villageSystem.newGnomeID();
        currentVillage = inVillage;
        desiredVillage = inDesiredVillage;
        villageSystem.addGnome(this);
        displayColor = Color.black;
    }

    /**
     * draws the gnome
     * @param g the graphics object used to draw the gnome
     */
    public void draw(Graphics2D g) {
        int circleSize = 6;
        int circleX = currentVillage.getX();
        int circleY = currentVillage.getY() + 2;

        // shifts any extra gnomes over when drawing them
        Node currentNode = villageSystem.getAllGnomes().getHead();
        while (currentNode != null) {
            if ((currentNode.getData()).equals(this))
                currentNode = null;
            else if (((Gnome) currentNode.getData()).getCurrentVillage().equals(currentVillage)) {
                circleX += circleSize;
                currentNode = currentNode.getNext();
            }
            else { currentNode = currentNode.getNext(); }
        }

        g.setColor(Color.white);
        g.drawOval(circleX, circleY, circleSize, circleSize);

        if (favColor.equals("Red") || favColor.equals("red") || favColor.equals("RED"))
            displayColor = Color.red;
        else if (favColor.equals("Blue") || favColor.equals("blue") || favColor.equals("BLUE"))
            displayColor = Color.blue;
        else if (favColor.equals("Green") || favColor.equals("green") || favColor.equals("GREEN"))
            displayColor = Color.green;
        else if (favColor.equals("White") || favColor.equals("white") || favColor.equals("WHITE"))
            displayColor = Color.white;
        else if (favColor.equals("Gray") || favColor.equals("gray") || favColor.equals("GRAY") ||
                favColor.equals("Grey") || favColor.equals("grey") || favColor.equals("GREY"))
            displayColor = Color.gray;
        else if (favColor.equals("Yellow") || favColor.equals("yellow") || favColor.equals("YELLOW"))
            displayColor = Color.yellow;
        else if (favColor.equals("Orange") || favColor.equals("orange") || favColor.equals("ORANGE"))
            displayColor = Color.orange;
        else if (favColor.equals("Pink") || favColor.equals("pink") || favColor.equals("PINK"))
            displayColor = Color.pink;
        else if (favColor.equals("Cyan") || favColor.equals("cyan") || favColor.equals("CYAN"))
            displayColor = Color.cyan;
        else if (favColor.equals("Magenta") || favColor.equals("magenta") || favColor.equals("MAGENTA"))
            displayColor = Color.magenta;
        g.setColor(displayColor);
        g.fillOval(circleX, circleY, circleSize, circleSize);
    }

    /**
     * @return the name of this gnome
     */
    public String getName() {
        return name;
    }

    /**
     * @return the favorite color as this gnome
     */
    public String getFavColor() {
        return favColor;
    }

    /**
     * changes the desired village of the gnome
     * @param destination the village to change the desired village to
     */
    public void setDesiredVillage(Village destination) {
        desiredVillage = destination;
    }

    /**
     * changes the village that the gnome is in
     * @param destination the village that the gnome is beling changed to
     */
    public void setCurrentVillage(Village destination) {
        currentVillage = destination;
    }

    /**
     * @return the current village the gnome is in
     */
    public Village getCurrentVillage() {
        return currentVillage;
    }

    /**
     * @return the desired village of the gnome
     */
    public Village getDesiredVillage() {
        return desiredVillage;
    }

    /**
     * @return the color that the gnome is displayed in
     */
    public Color getDisplayColor() {
        return displayColor;
    }

    /**
     * @return the id number of this gnome
     */
    public int getIdNum() {
        return idNum;
    }

    /**
     * @return the importance level of this gnome
     */
    public int getImportanceLevel() {
        return importanceLevel;
    }

    /**
     * moves the gnome towards it's desired city
     */
    public void move() {
        if (currentVillage.equals(desiredVillage)) return;

        DoubleLinkedList<DijkstraBundle> theGrid = villageSystem.findCheapestPaths(currentVillage);
        DijkstraBundle targetBundle = null;
        Node currentNode = theGrid.getHead();
        while (currentNode != null) {
            DijkstraBundle currentBundle = (DijkstraBundle) currentNode.getData();
            if (currentBundle.getVillage().equals(desiredVillage))
                targetBundle = currentBundle;
            currentNode = currentNode.getNext();
        }

//        if (targetBundle == null)
//            return;
//        if (targetBundle.getPriorVillage() == null)
//            return;

        boolean done = false;
        DijkstraBundle currentBundle = targetBundle;
        do {
            if (currentBundle == null)
                return;
            else if (currentBundle.getPriorVillage() == null)
                return;
            else if (currentBundle.getPriorVillage().equals(currentVillage)) {
                setCurrentVillage(currentBundle.getVillage());
                done = true;
            }
            else {
                Node currentNode1 = theGrid.getHead();
                while (currentNode1 != null) {
                    if (((DijkstraBundle) currentNode1.getData()).getVillage().equals(currentBundle.getPriorVillage())) {
                        currentBundle = (DijkstraBundle) currentNode1.getData();
                        currentNode1 = null;
                    }
                    else {
                        currentNode1 = currentNode1.getNext();
                    }
                }
            }
        } while (!done);
    }

}
