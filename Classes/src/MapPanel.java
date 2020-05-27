import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;

/**
 * Created by clombardo on 7/31/17.
 */
public class MapPanel extends JPanel implements MouseListener{

    // instance fields
    private VillageSystem villageSystem;
    private DoubleLinkedList<Village> villages;
    private DoubleLinkedList<Road> roads;
    private DoubleLinkedList<Gnome> gnomes;

    // the buttons at the top of the screen
    private JButton clearAllButton;
    private JButton addVillageButton;
    private JButton deleteVillageButton;
    private JButton addRoadButton;
    private JButton addGnomeButton;
    private JButton moveGnomesButton;
    private JButton addMinimumSpanningTree;
    private JButton gnomeSearchButton;

    private ConfirmAddVillageAction villageAdditionConfirmation;

    /**
     * constructs a new MapPanel
     * @param inVillageSystem the village system to put in the panel
     */
    public MapPanel(VillageSystem inVillageSystem) {
        villageSystem = inVillageSystem;
        villages = villageSystem.getAllVillages();
        roads = villageSystem.getAllRoads();
        gnomes = villageSystem.getAllGnomes();
        this.addMouseListener(this);
        villageAdditionConfirmation = new ConfirmAddVillageAction(this);

        this.setLayout(null);

        drawButtons();
    }

    /**
     * paints the different components of the MapPanel
     * @param g the graphics object to use
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        setBackground(Color.lightGray);
        drawButtonMenu(g2);
        drawMap(g2);
    }

    /**
     * draws the map
     * @param g2 the graphics object to use
     */
    private void drawMap(Graphics2D g2) {
        // draw all roads
        Node currentNode = roads.getHead();
        while (currentNode != null) {
            ((Road) currentNode.getData()).draw(g2);
            currentNode = currentNode.getNext();
        }

        // draw all villages
        currentNode = villages.getHead();
        while (currentNode != null) {
            ((Village) currentNode.getData()).draw(g2);
            currentNode = currentNode.getNext();
        }

        // draw all gnomes
        currentNode = gnomes.getHead();
        while (currentNode != null) {
            ((Gnome) currentNode.getData()).draw(g2);
            currentNode = currentNode.getNext();
        }

        // add a message at the bottom about unilateral roads
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.TYPE1_FONT, 14));
        g2.drawString("NOTE: ", 160, Driver.FRAME_HEIGHT - 50);
        g2.drawString("All roads are one-way, going in the direction of the blue dot. Two dots implies two roads.", 207,
                Driver.FRAME_HEIGHT - 50);
        g2.drawString("The number next to the dot on each road represents the toll of traveling on the road.", 207,
                Driver.FRAME_HEIGHT - 34);
    }

    /**
     * draws the background for the button menu
     * @param g2
     */
    private void drawButtonMenu(Graphics2D g2) {
        // draw a rectangle of whatever color to just distinguish between button menu and map
        Rectangle buttonMenuBackGround = new Rectangle(0, 0, Driver.FRAME_WIDTH, Driver.BUTTONMENU_HEIGHT);
        g2.setColor(Color.DARK_GRAY);
        g2.fill(buttonMenuBackGround);

        // draw a line to serve as the border between the button menu and the map
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(5));
        g2.draw(new Line2D.Float(0, Driver.BUTTONMENU_HEIGHT, Driver.FRAME_WIDTH, Driver.BUTTONMENU_HEIGHT));
    }

    /**
     * initializes and draws all the buttons
     */
    private void drawButtons() {
        //add the "clear all" button
        clearAllButton = new JButton("Clear All");
        clearAllButton.setBounds(10, 10, 150, Driver.BUTTONMENU_HEIGHT - 20);
        clearAllButton.addActionListener(new ClearAllAction(this, villageSystem));
        add(clearAllButton);

        // add the "add village" button
        addVillageButton = new JButton("Add a Village");
        addVillageButton.setBounds(170, 10, 135, 52);
        addVillageButton.addActionListener(villageAdditionConfirmation);
        add(addVillageButton);

        // add the "delete village" button
        deleteVillageButton = new JButton("Delete a Village");
        deleteVillageButton.setBounds(170, 68, 135, 52);
        deleteVillageButton.addActionListener(new DeleteVillageAction(this, villageSystem));
        add(deleteVillageButton);

        // add the "add road" button
        addRoadButton = new JButton("Add a Road");
        addRoadButton.setBounds(315, 10, 135, 52);
        addRoadButton.addActionListener(new ConfirmAddRoadAction(this, villageSystem));
        add(addRoadButton);

        // add the "add gnome" button
        addGnomeButton = new JButton("Add a Gnome");
        addGnomeButton.setBounds(315, 68, 135, 52);
        addGnomeButton.addActionListener(new AddGnomeAction(this, villageSystem));
        add(addGnomeButton);

        // add the "move gnomes" button
        moveGnomesButton = new JButton("Move Gnomes");
        moveGnomesButton.setBounds(460, 10, 150, Driver.BUTTONMENU_HEIGHT - 20);
        moveGnomesButton.addActionListener(new MoveGnomesAction(this, villageSystem));
        add(moveGnomesButton);

        // add the "draw minimum spanning tree" button
        addMinimumSpanningTree = new JButton("Minimum Spanning Tree");
        addMinimumSpanningTree.setBounds(620, 10, 200, Driver.BUTTONMENU_HEIGHT - 20);
        addMinimumSpanningTree.addActionListener(new MinumumSpanningTreeAction(this, villageSystem));
        add(addMinimumSpanningTree);

        // add the "gnome search" button
        gnomeSearchButton = new JButton("Gnome Search");
        gnomeSearchButton.setBounds(830, 10, 160, Driver.BUTTONMENU_HEIGHT - 20);
        gnomeSearchButton.addActionListener(new GnomeSearchAction(this, villageSystem));
        add(gnomeSearchButton);
    }

    /**
     * calls when the mouse is pressed
     * if it is looking for a village location, then place a village there
     * @param e the mouse event
     */
    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Hello");
        if (villageAdditionConfirmation.getResponse()) {
            boolean validVillageName;
            String villageName;
            do{
                villageName = JOptionPane.showInputDialog(this, "Enter the name of this village.",
                        "Village Name", JOptionPane.QUESTION_MESSAGE);
                if (villageName == null) validVillageName = false;
                else if (villageName.equals("")) validVillageName = false;
                else validVillageName = true;
            } while (!validVillageName);

            new Village(villageName, villageSystem, e.getX() - 12, e.getY() + 3);
            villageAdditionConfirmation.setResponse(false);
            repaint();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}
}

// a class that gets initialized whenever the user clicks the button to add a village
class ConfirmAddVillageAction implements ActionListener{

    private JPanel panel;
    boolean response;

    public ConfirmAddVillageAction (JPanel panel) {
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int userYesNoChoice = JOptionPane.showConfirmDialog(panel,
                "Click on the location that you would like to place a new village.",
                "Add a Village", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);

        if (userYesNoChoice == JOptionPane.OK_OPTION)
            response = true;
        else response = false;
    }

    public boolean getResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }
}

// a class that gets initialized whenever the user clicks the button to add a road
class ConfirmAddRoadAction implements ActionListener {

    private JPanel panel;
    Village from;
    Village to;
    private VillageSystem villageSystem;

    public ConfirmAddRoadAction (JPanel panel, VillageSystem villageSystem) {
        this.panel = panel;
        this.villageSystem = villageSystem;
        from = null;
        to = null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (villageSystem.getAllVillages().getLength() < 2)
            JOptionPane.showMessageDialog(panel, "There needs to be at least two villages to make a road.",
                    "Not Enough Villages", JOptionPane.ERROR_MESSAGE);
        else {
            boolean validFromVillage = false;
            String userFromInput;
            do {
                userFromInput = JOptionPane.showInputDialog(panel, "What Village will the road be going from?",
                        "Village 1 Selection", JOptionPane.QUESTION_MESSAGE);
                Node currentNode = villageSystem.getAllVillages().getHead();
                while (currentNode != null) {
                    if (((Village) currentNode.getData()).getName().equals(userFromInput)) {
                        from = (Village) currentNode.getData();
                        validFromVillage = true;
                    }
                    currentNode = currentNode.getNext();
                }
            } while (!validFromVillage);

            boolean validToVillage = false;
            String userToInput;
            do {
                userToInput = JOptionPane.showInputDialog(panel, "What Village will the road be going to?",
                        "Village 2 Selection", JOptionPane.QUESTION_MESSAGE);
                Node currentNode = villageSystem.getAllVillages().getHead();
                while (currentNode != null) {
                    if (((Village) currentNode.getData()).getName().equals(userToInput)) {
                        to = (Village) currentNode.getData();
                        validToVillage = true;
                    }
                    currentNode = currentNode.getNext();
                }
            } while (!validToVillage);

            boolean validToll;
            String userTollInput;
            int toll = -1;
            do {
                userTollInput = JOptionPane.showInputDialog(panel, "What will the toll of this road? [integer 1-20]",
                        "Toll Selection", JOptionPane.QUESTION_MESSAGE);
                try {
                    toll = Integer.parseInt(userTollInput);
                    validToll = true;
                } catch(NumberFormatException nfe) {validToll = false;}
                if (!(toll >= 1 && toll <= 20))
                    validToll = false;
            } while (!validToll);

            villageSystem.addRoad(from, to, toll);
            panel.repaint();
        }
    }
}

// a class that gets initialized whenever the user clicks the button to clear all
class ClearAllAction implements ActionListener {

    private VillageSystem villageSystem;
    private JPanel panel;

    public ClearAllAction(JPanel panel, VillageSystem villageSystem) {
        this.panel = panel;
        this.villageSystem = villageSystem;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        villageSystem.getAllVillages().clearList();
        villageSystem.getAllGnomes().clearList();
        villageSystem.getAllRoads().clearList();
        panel.repaint();
    }
}

// a class that gets initialized whenever the user clicks the button to delete a village
class DeleteVillageAction implements ActionListener {

    private VillageSystem villageSystem;
    private JPanel panel;

    private JButton salvageButton;
    private JButton deleteButton;

    public  DeleteVillageAction (JPanel panel, VillageSystem villageSystem) {
        this.panel = panel;
        this.villageSystem = villageSystem;
        salvageButton = new JButton("Salvage connecting roads");
        deleteButton = new JButton("Delete all connected roads");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (villageSystem.getAllVillages().getLength() < 1) {
            JOptionPane.showMessageDialog(panel, "No villages to delete.", "Deletion Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean validDeleteVillage = false;
        String userVillageInput;
        Village villageToDelete = null;
        do {
            userVillageInput = JOptionPane.showInputDialog(panel, "What Village should be deleted?",
                    "Village Deletion", JOptionPane.QUESTION_MESSAGE);
            if (userVillageInput == null) return;
            Node currentNode = villageSystem.getAllVillages().getHead();
            while (currentNode != null) {
                if (((Village) currentNode.getData()).getName().equals(userVillageInput)) {
                    villageToDelete = (Village) currentNode.getData();
                    validDeleteVillage = true;
                }
                currentNode = currentNode.getNext();
            }
        } while (!validDeleteVillage);

        deleteButton.addActionListener(new rawDeleteAction(villageToDelete));
        salvageButton.addActionListener(new salvageAction(villageToDelete));

        Object[] msg = {"What should be done with the roads going through this village?", salvageButton, deleteButton};
        JOptionPane.showMessageDialog(panel, msg);

    }

    class salvageAction implements ActionListener {
        Village v;

        public salvageAction(Village v) {
            this.v = v;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Node currentNode = villageSystem.getAllRoads().getHead();
            while (currentNode != null) {
                Road currentRoad = (Road) currentNode.getData();
                if (currentRoad.getTo().equals(v)) {
                    Node currentNodeX = villageSystem.getAllRoads().getHead();
                    while (currentNodeX != null) {
                        Road currentRoadX = (Road) currentNodeX.getData();
                        if (currentRoadX.getFrom().equals(v) && !currentRoadX.getTo().equals(currentRoad.getFrom())) {
                            // check if the villages are already connected
                            boolean alreadyConnected = false;
                            Node currentNodeY = villageSystem.getAllRoads().getHead();
                            while (currentNodeY != null) {
                                Road currentRoadY = (Road) currentNodeY.getData();
                                if (currentRoadY.getFrom().equals(currentRoad.getFrom()) &&
                                        currentRoadY.getTo().equals(currentRoadX.getTo())) {
                                    alreadyConnected = true;
                                }
                                currentNodeY = currentNodeY.getNext();
                            }
                            if (!alreadyConnected)
                                villageSystem.addRoad(currentRoad.getFrom(), currentRoadX.getTo(),
                                        currentRoad.getToll() + currentRoadX.getToll());
                        }
                        currentNodeX = currentNodeX.getNext();
                    }
                }
                currentNode = currentNode.getNext();
            }

            villageSystem.deleteVillage(v);
            panel.repaint();
        }
    }

    class rawDeleteAction implements ActionListener {
        Village v;

        public rawDeleteAction(Village v) {
            this.v = v;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            villageSystem.deleteVillage(v);
            panel.repaint();
        }
    }
}

// a class that gets initialized whenever the user clicks the button to add a gnome
class AddGnomeAction implements ActionListener {

    private JPanel panel;
    private VillageSystem villageSystem;

    public AddGnomeAction(JPanel panel, VillageSystem villageSystem) {
        this.panel = panel;
        this.villageSystem = villageSystem;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String gnomeName;
        boolean validGnomeName;
        do {
            gnomeName = JOptionPane.showInputDialog(panel, "What is the name of this Gnome?",
                    "Gnome Generation", JOptionPane.QUESTION_MESSAGE);
            if (gnomeName == null) validGnomeName = false;
            else if (gnomeName.equals("")) validGnomeName = false;
            else validGnomeName = true;
        } while (!validGnomeName);

        boolean validGnomeVillage = false;
        String userGnomeVillageInput;
        Village gnomeVillage = null;
        do {
            userGnomeVillageInput = JOptionPane.showInputDialog(panel, "What Village should the gnome be placed in?",
                    "Gnome Generation", JOptionPane.QUESTION_MESSAGE);
            if (userGnomeVillageInput == null) validGnomeVillage = false;
            else{
                Node currentNode = villageSystem.getAllVillages().getHead();
                while (currentNode != null) {
                    if (((Village) currentNode.getData()).getName().equals(userGnomeVillageInput)) {
                        gnomeVillage = (Village) currentNode.getData();
                        validGnomeVillage = true;
                    }
                    currentNode = currentNode.getNext();
                }
            }

        } while (!validGnomeVillage);

        String gnomeFavColor;
        boolean validGnomeColor;
        do {
            gnomeFavColor = JOptionPane.showInputDialog(panel, "What is this gnome's favorite color?",
                    "Gnome Generation", JOptionPane.QUESTION_MESSAGE);
            if (gnomeFavColor == null) validGnomeColor = false;
            else if (gnomeFavColor.equals("")) validGnomeColor = false;
            else validGnomeColor = true;
        } while (!validGnomeColor);

        boolean validImpLevel = false;
        String userImpLevelInput;
        int impLevel = -1;
        do {
            userImpLevelInput = JOptionPane.showInputDialog(panel,
                    "Choose an importance level for the gnome [1, 2, or 3].", "Gnome Generation",
                    JOptionPane.QUESTION_MESSAGE);
            if (userImpLevelInput == null) {
                validImpLevel = false;
            }
            else {
                try {
                    impLevel = Integer.parseInt(userImpLevelInput);
                } catch (NumberFormatException nfe) {}

                if (impLevel == 1 || impLevel == 2 || impLevel == 3)
                    validImpLevel = true;
            }
        } while (!validImpLevel);

        boolean validDestinationVillage = false;
        String userDestInput;
        Village destinationVillage = null;
        do {
            userDestInput = JOptionPane.showInputDialog(panel,
                    "What is this gnome's destination village? Leave the box blank for a random destination.",
                    "Gnome Generation", JOptionPane.QUESTION_MESSAGE);
            if (userDestInput == null)
                validDestinationVillage = false;
            else if (userDestInput.equals(""))
                validDestinationVillage = true;
            else {
                Node currentNode = villageSystem.getAllVillages().getHead();
                while (currentNode != null) {
                    if (((Village) currentNode.getData()).getName().equals(userDestInput)) {
                        destinationVillage = (Village) currentNode.getData();
                        validDestinationVillage = true;
                    }
                    currentNode = currentNode.getNext();
                }
            }
        } while (!validDestinationVillage);
        if (destinationVillage == null)
            destinationVillage = villageSystem.getAllVillages().getRandomItem();

        Gnome newGnome = new Gnome(gnomeName, gnomeFavColor, impLevel, gnomeVillage, villageSystem);
        newGnome.setDesiredVillage(destinationVillage);
        panel.repaint();
    }
}

// a class that gets initialized whenever the user clicks the button to move the gnomes
class MoveGnomesAction implements ActionListener {

    private JPanel panel;
    private VillageSystem villageSystem;

    public MoveGnomesAction(JPanel panel, VillageSystem villageSystem) {
        this.panel = panel;
        this.villageSystem = villageSystem;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Node currentNode = villageSystem.getAllGnomes().getHead();
        while (currentNode != null) {
            Gnome currentGnome = (Gnome) currentNode.getData();
            if (!currentGnome.getCurrentVillage().equals(currentGnome.getDesiredVillage())) {
                currentGnome.move();
            }
            currentNode = currentNode.getNext();
        }
        panel.repaint();
    }
}

// a class that gets initialized whenever the user clicks the button to add a minimum spanning tree
class MinumumSpanningTreeAction implements ActionListener {

    private JPanel panel;
    private VillageSystem villageSystem;

    public MinumumSpanningTreeAction(JPanel panel, VillageSystem villageSystem) {
        this.panel = panel;
        this.villageSystem = villageSystem;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (villageSystem.getAllVillages().getLength() < 2) {
            JOptionPane.showMessageDialog(panel,
                    "At least two villages are required to create a minimum spanning tree.", "Not enough villages.",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean validTreeVillage = false;
        String userInputTreeVillage;
        Village treeVillage = null;
        do {
            userInputTreeVillage = JOptionPane.showInputDialog(panel,
                    "What village should the minimum spanning tree start at?", "Tree Start",
                    JOptionPane.QUESTION_MESSAGE);
            if (userInputTreeVillage == null)
                return;
            else {
                Node currentNode = villageSystem.getAllVillages().getHead();
                while (currentNode != null) {
                    Village currentVillage = (Village) currentNode.getData();
                    if (currentVillage.getName().equals(userInputTreeVillage)) {
                        treeVillage = currentVillage;
                        validTreeVillage = true;
                        currentNode = null;
                    }
                    else
                        currentNode = currentNode.getNext();
                }
            }
        } while (!validTreeVillage);

        villageSystem.convertToMinimumSpanningTree(treeVillage);
        panel.repaint();
    }
}

// a class that gets initialized whenever the user clicks the button to search for gnomes
class GnomeSearchAction implements ActionListener {

    private JPanel panel;
    private VillageSystem villageSystem;
    private DoubleLinkedList<Gnome> allGnomes;

    private JButton colorButton;
    private JButton importanceButton;
    private JButton idNumButton;

    public GnomeSearchAction(JPanel panel, VillageSystem villageSystem) {
        this.panel = panel;
        this.villageSystem = villageSystem;
        allGnomes = villageSystem.getAllGnomes();

        colorButton = new JButton("Color");
        colorButton.addActionListener(new colorButtonAction());

        importanceButton = new JButton("Importance");
        importanceButton.addActionListener(new importanceButtonAction());

        idNumButton = new JButton("ID Number");
        idNumButton.addActionListener(new idButtonAction());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object[] msg = {"What would you like to search by?", colorButton, idNumButton, importanceButton};

        JOptionPane.showConfirmDialog(panel, msg, "Gnome Search", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE);
    }

    private void displayGnomeResults(DoubleLinkedList<Gnome> displayGnomes) {
        if (displayGnomes.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "No gnomes found with this criteria.", "Empty Search",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        String displayText = "";
        Node currentNode = displayGnomes.getHead();
        int counter = 1;
        while (currentNode != null) {
            Gnome currentGnome = (Gnome) currentNode.getData();
            displayText += counter + ".     " +
                    "Name: " + currentGnome.getName() +
                    " - ID Number: " + currentGnome.getIdNum() +
                    " - City: " + currentGnome.getCurrentVillage().getName()
                    + "\n";
            counter++;
            currentNode = currentNode.getNext();
        }

        JOptionPane.showMessageDialog(panel, displayText, "Search Results", JOptionPane.PLAIN_MESSAGE);
    }

    class colorButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String userColorInput = JOptionPane.showInputDialog(panel, "What color are you going to search by?",
                    "Color Search", JOptionPane.PLAIN_MESSAGE);

            Color colorBeingSearchedFor = null;
            DoubleLinkedList<Gnome> returnGnomes = new DoubleLinkedList<>();

            if (userColorInput == null)
                return;
            else if (userColorInput.equals("Red") || userColorInput.equals("red") || userColorInput.equals("RED"))
                colorBeingSearchedFor = Color.red;
            else if (userColorInput.equals("Blue") || userColorInput.equals("blue") || userColorInput.equals("BLUE"))
                colorBeingSearchedFor = Color.blue;
            else if (userColorInput.equals("Green") || userColorInput.equals("green") || userColorInput.equals("GREEN"))
                colorBeingSearchedFor = Color.green;
            else if (userColorInput.equals("White") || userColorInput.equals("white") || userColorInput.equals("WHITE"))
                colorBeingSearchedFor = Color.white;
            else if (userColorInput.equals("Gray") || userColorInput.equals("gray") || userColorInput.equals("GRAY") ||
                    userColorInput.equals("Grey") || userColorInput.equals("grey") || userColorInput.equals("GREY"))
                colorBeingSearchedFor = Color.gray;
            else if (userColorInput.equals("Yellow") || userColorInput.equals("yellow") ||
                    userColorInput.equals("YELLOW"))
                colorBeingSearchedFor = Color.yellow;
            else if (userColorInput.equals("Orange") || userColorInput.equals("orange") ||
                    userColorInput.equals("ORANGE"))
                colorBeingSearchedFor = Color.orange;
            else if (userColorInput.equals("Pink") || userColorInput.equals("pink") || userColorInput.equals("PINK"))
                colorBeingSearchedFor = Color.pink;
            else if (userColorInput.equals("Cyan") || userColorInput.equals("cyan") || userColorInput.equals("CYAN"))
                colorBeingSearchedFor = Color.cyan;
            else if (userColorInput.equals("Magenta") || userColorInput.equals("magenta") ||
                    userColorInput.equals("MAGENTA"))
                colorBeingSearchedFor = Color.magenta;

            Node currentNode = allGnomes.getHead();
            while (currentNode != null) {
                Gnome currentGnome = (Gnome) currentNode.getData();
                if (currentGnome.getDisplayColor().equals(colorBeingSearchedFor))
                    returnGnomes.add(currentGnome);
                currentNode = currentNode.getNext();
            }

            displayGnomeResults(returnGnomes);
        }
    }

    class importanceButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String userImpInput = JOptionPane.showInputDialog(panel,
                    "What level of importance should be searched for?", "Importance Search", JOptionPane.PLAIN_MESSAGE);
            if (userImpInput == null)
                return;

            int targetImpLevel;
            try {
                targetImpLevel = Integer.parseInt(userImpInput);
            } catch (NumberFormatException nfe){return;}

            DoubleLinkedList<Gnome> returnGnomes = new DoubleLinkedList<>();

            Node currentNode = allGnomes.getHead();
            while (currentNode != null) {
                Gnome currentGnome = (Gnome) currentNode.getData();
                if (currentGnome.getImportanceLevel() == targetImpLevel)
                    returnGnomes.add(currentGnome);
                currentNode = currentNode.getNext();
            }

            displayGnomeResults(returnGnomes);
        }
    }

    class idButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String userIDInput = JOptionPane.showInputDialog(panel,
                    "What ID number should be searched for?", "ID Number Search", JOptionPane.PLAIN_MESSAGE);
            if (userIDInput == null)
                return;

            DoubleLinkedList<Gnome> returnGnomes = new DoubleLinkedList<>();

            Node currentNode = allGnomes.getHead();
            while (currentNode != null) {
                Gnome currentGnome = (Gnome) currentNode.getData();
                if ((currentGnome.getIdNum() + "").equals(userIDInput))
                    returnGnomes.add(currentGnome);
                currentNode = currentNode.getNext();
            }

            displayGnomeResults(returnGnomes);
        }
    }
}

