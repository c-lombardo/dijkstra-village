import javax.swing.*;

/**
 * Created by clombardo on 7/30/17.
 */
public class Driver {

    public static final int FRAME_WIDTH = 1000;
    public static final int FRAME_HEIGHT = 800;

    public static final int BUTTONMENU_HEIGHT = 130;
    public static final int MAP_HEIGHT = FRAME_HEIGHT - BUTTONMENU_HEIGHT;

    public static void main(String[] args) {

        VillageSystem villageSystem = new VillageSystem();

        Village a = new Village("Atlanta", villageSystem, 50, 320);
        Village b = new Village("Belmont", villageSystem, 150, 200);
        Village c = new Village("Chelsea", villageSystem, 290, 470);
        Village d = new Village("Dallas", villageSystem, 50, 680);
        Village e = new Village("Evansville", villageSystem, 310, 695);
        Village f = new Village("Franklin", villageSystem, 400, 320);
        Village g = new Village("Gary", villageSystem, 630, 450);
        Village h = new Village("Hamburg", villageSystem, 900, 260);
        Village i = new Village("Indianapolis", villageSystem, 880, 400);
        Village j = new Village("Jacksonville", villageSystem, 565, 700);
        Village k = new Village("Kimberly", villageSystem, 490, 160);
        Village l = new Village("Louisville", villageSystem, 850, 650);

        Gnome aGnome = new Gnome("Allan", "black", Gnome.STANDARD, villageSystem.getAllVillages().getRandomItem(),
                villageSystem.getAllVillages().getRandomItem(), villageSystem);
        Gnome bGnome = new Gnome("Ben", "red", Gnome.STANDARD, villageSystem.getAllVillages().getRandomItem(),
                villageSystem.getAllVillages().getRandomItem(), villageSystem);
        Gnome cGnome = new Gnome("Chris", "yellow", Gnome.STANDARD, villageSystem.getAllVillages().getRandomItem(),
                villageSystem.getAllVillages().getRandomItem(), villageSystem);
        Gnome dGnome = new Gnome("Dave", "green", Gnome.STANDARD, villageSystem.getAllVillages().getRandomItem(),
                villageSystem.getAllVillages().getRandomItem(), villageSystem);
        Gnome eGnome = new Gnome("Evan", "white", Gnome.STANDARD, villageSystem.getAllVillages().getRandomItem(),
                villageSystem.getAllVillages().getRandomItem(), villageSystem);
        Gnome fGnome = new Gnome("Frank", "yellow", Gnome.VIP, villageSystem.getAllVillages().getRandomItem(),
                villageSystem.getAllVillages().getRandomItem(), villageSystem);
        Gnome gGnome = new Gnome("Grace", "orange", Gnome.VIP, villageSystem.getAllVillages().getRandomItem(),
                villageSystem.getAllVillages().getRandomItem(), villageSystem);
        Gnome hGnome = new Gnome("Harry", "blue", Gnome.VIP, villageSystem.getAllVillages().getRandomItem(),
                villageSystem.getAllVillages().getRandomItem(), villageSystem);
        Gnome iGnome = new Gnome("Isaac", "cyan", Gnome.VIP, villageSystem.getAllVillages().getRandomItem(),
                villageSystem.getAllVillages().getRandomItem(), villageSystem);
        Gnome jGnome = new Gnome("Jon", "magenta", Gnome.VIP, villageSystem.getAllVillages().getRandomItem(),
                villageSystem.getAllVillages().getRandomItem(), villageSystem);
        Gnome kGnome = new Gnome("Kanye", "pink", Gnome.GOLDSTAR, villageSystem.getAllVillages().getRandomItem(),
                villageSystem.getAllVillages().getRandomItem(), villageSystem);
        Gnome lGnome = new Gnome("Leslie", "red", Gnome.GOLDSTAR, villageSystem.getAllVillages().getRandomItem(),
                villageSystem.getAllVillages().getRandomItem(), villageSystem);
        Gnome mGnome = new Gnome("Megan", "blue", Gnome.GOLDSTAR, villageSystem.getAllVillages().getRandomItem(),
                villageSystem.getAllVillages().getRandomItem(), villageSystem);
        Gnome nGnome = new Gnome("Nancy", "green", Gnome.GOLDSTAR, villageSystem.getAllVillages().getRandomItem(),
                villageSystem.getAllVillages().getRandomItem(), villageSystem);

        villageSystem.addRoad(a, c, 5);
        villageSystem.addRoad(a, b, 8);
        villageSystem.addRoad(a, d, 2);
        villageSystem.addRoad(b, a, 5);
        villageSystem.addRoad(b, c, 2);
        villageSystem.addRoad(b, f, 13);
        villageSystem.addRoad(c, a, 2);
        villageSystem.addRoad(c, b, 2);
        villageSystem.addRoad(c, d, 8);
        villageSystem.addRoad(c, f, 6);
        villageSystem.addRoad(c, g, 1);
        villageSystem.addRoad(c, e, 1);
        villageSystem.addRoad(d, a, 3);
        villageSystem.addRoad(d, c, 2);
        villageSystem.addRoad(d, e, 5);
        villageSystem.addRoad(e, c, 1);
        villageSystem.addRoad(e, d, 11);
        villageSystem.addRoad(e, g, 1);
        villageSystem.addRoad(e, j, 9);
        villageSystem.addRoad(f, b, 6);
        villageSystem.addRoad(f, c, 3);
        villageSystem.addRoad(f, g, 2);
        villageSystem.addRoad(f, h, 3);
        villageSystem.addRoad(f, k, 1);
        villageSystem.addRoad(g, c, 5);
        villageSystem.addRoad(g, e, 3);
        villageSystem.addRoad(g, f, 2);
        villageSystem.addRoad(g, h, 6);
        villageSystem.addRoad(g, i, 7);
        villageSystem.addRoad(g, l, 4);
        villageSystem.addRoad(g, j, 6);
        villageSystem.addRoad(h, f, 3);
        villageSystem.addRoad(h, g, 7);
        villageSystem.addRoad(h, i, 1);
        villageSystem.addRoad(h, k, 1);
        villageSystem.addRoad(i, h, 2);
        villageSystem.addRoad(i, g, 8);
        villageSystem.addRoad(i, l, 2);
        villageSystem.addRoad(j, e, 6);
        villageSystem.addRoad(j, g, 7);
        villageSystem.addRoad(j, l, 2);
        villageSystem.addRoad(k, f, 2);
        villageSystem.addRoad(k, h, 1);
        villageSystem.addRoad(l, g, 2);
        villageSystem.addRoad(l, i, 2);
        villageSystem.addRoad(l, j, 1);

        JFrame frame = new JFrame("Gnomenwald");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        MapPanel mapPanel = new MapPanel(villageSystem);

        frame.add(mapPanel);

        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }
}
