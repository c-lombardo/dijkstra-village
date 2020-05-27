/**
 * Created by clombardo on 7/30/17.
 */
public class VillageSystem {

    // instance fields
    private int currentGnomeID;
    private int currentVillageID;
    private int numVillages;
    private int numRoads;
    private int numGnomes;
    private DoubleLinkedList<Village> allVillages;
    private DoubleLinkedList<Road> allRoads;
    private DoubleLinkedList<Gnome> allGnomes;
    private DoubleLinkedList<DoubleLinkedList<Village>> adjacencyList;

    /**
     * initializes a new village system
     */
    public VillageSystem() {
        numVillages = 0;
        numRoads = 0;
        numGnomes = 0;
        allVillages = new DoubleLinkedList<>();
        allRoads = new DoubleLinkedList<>();
        allGnomes = new DoubleLinkedList<>();
        adjacencyList = new DoubleLinkedList<>();
        currentGnomeID = 0;
    }

    /**
     * adds a village to the village system
     * @param v the village to add to the system
     */
    public void addVillage(Village v) {
        allVillages.add(v);
        adjacencyList.add(new DoubleLinkedList<>(v));
        numVillages++;
    }

    /**
     * removes a village and all roads connecting to it from the village system
     * @param v
     */
    public void deleteVillage(Village v) {
        allVillages.remove(v); // remove the village
        numVillages--;

        // remove all roads attached to it, in either direction
        Node currentNode = allRoads.getHead();
        while (currentNode != null) {
            Road currentRoad = (Road) currentNode.getData();
            if (currentRoad.getFrom().equals(v) || currentRoad.getTo().equals(v)) {
                allRoads.remove(currentRoad);
                numRoads--;
            }
            currentNode = currentNode.getNext();
        }

        // remove the adjacency list associated with this village
        currentNode = adjacencyList.getHead();
        while (currentNode != null) {
            DoubleLinkedList currentList = (DoubleLinkedList) currentNode.getData();
            if (currentList.getLabel().equals(v))
                currentList.remove(currentList);
            currentNode = currentNode.getNext();
        }

        // remove all gnomes in the village
        currentNode = allGnomes.getHead();
        while (currentNode != null) {
            Gnome currentGnome = (Gnome) currentNode.getData();
            if (currentGnome.getCurrentVillage().equals(v)) {
                allGnomes.remove(currentGnome);
                numGnomes--;
            }
            currentNode = currentNode.getNext();
        }
    }

    /**
     * adds a road to the village system
     * @param from the village the road is from
     * @param to the village the road is to
     * @param toll the toll of the road
     */
    public void addRoad(Village from, Village to, int toll) {
        Road temp = new Road(from, to, toll);
        allRoads.add(temp);
        DoubleLinkedList<Village> found = adjacencyList.graphFind(from);
        found.add(to);
        numRoads++;
    }

    /**
     * adds a road to the village system
     * @param temp the road to add
     */
    public void addRoad(Road temp)  {
        allRoads.add(temp);
        DoubleLinkedList<Village> found = adjacencyList.graphFind(temp.getFrom());
        if (found != null) found.add(temp.getTo());
        numRoads++;
    }

    /**
     * adds a gnome to the village system
     * @param g the gnome to add
     */
    public void addGnome(Gnome g) {
        allGnomes.add(g);
    }

    /**
     * finds all gnomes with a certain name
     * O(n) runtime
     * @param name the name to find gnomes with
     * @return all gnomes with this name
     */
    public DoubleLinkedList<Gnome> findGnomesByName(String name) {
        DoubleLinkedList<Gnome> toReturn = new DoubleLinkedList<>();
        Node currentNode = allGnomes.getHead();
        while (currentNode != null) {
            if (((Gnome) currentNode.getData()).getName().equals(name))
                toReturn.add((Gnome) currentNode.getData());
            currentNode = currentNode.getNext();
        }
        return toReturn;
    }

    /**
     * finds all gnomes with a certain favorite color
     * O(n) runtime
     * @param color the favorite color of the gnomes to search for
     * @return all gnomes with this favorite color
     */
    public DoubleLinkedList<Gnome> findGnomesByColor(String color) {
        DoubleLinkedList<Gnome> toReturn = new DoubleLinkedList<>();
        Node currentNode = allGnomes.getHead();
        while (currentNode != null) {
            if (((Gnome) currentNode.getData()).getFavColor().equals(color))
                toReturn.add((Gnome) currentNode.getData());
            currentNode = currentNode.getNext();
        }
        return toReturn;
    }

    /**
     * finds the cheapest path to any node from a certain node using Dijkstra's algorithm
     * @param startVillage the village to find paths from
     * @return a double linked list of dijkstra bundles, representing the all the shortest paths from the startVillage
     */
    public DoubleLinkedList<DijkstraBundle> findCheapestPaths(Village startVillage) {

        DoubleLinkedList<DijkstraBundle> theGrid = new DoubleLinkedList<>();
        DoubleLinkedList<DijkstraBundle> finishedBundles = new DoubleLinkedList<>();

        // add all villages to the grid, in the form of Dijkstra bundles
        Node currentNode = allVillages.getHead();
        while (currentNode != null) {
            theGrid.add(new DijkstraBundle((Village) currentNode.getData(), Integer.MAX_VALUE, null, false));
            currentNode = currentNode.getNext();
        }

        // set up the first bundle of the grid
        DijkstraBundle bundleBeingDealtWith = null;
        currentNode = theGrid.getHead();
        while (currentNode != null) {
            DijkstraBundle currentBundle = (DijkstraBundle) currentNode.getData();
            if (currentBundle.getVillage().equals(startVillage)) {
                currentBundle.setDealtWith(true);
                currentBundle.setCostToReach(0);
                currentBundle.setPriorVillage(null);
                bundleBeingDealtWith = currentBundle;
                currentNode = null;
            }
            else
                currentNode = currentNode.getNext();
        }

        // implement dijkstra's algorithm
        boolean done = false;
        do {
            DoubleLinkedList<DijkstraBundle> connectedBundles = new DoubleLinkedList<>();
            Node currentCompareNode = theGrid.getHead();
            Village currentVillage = bundleBeingDealtWith.getVillage();
            while (currentCompareNode != null) {
                DijkstraBundle currentBundle = (DijkstraBundle) currentCompareNode.getData();
                if (!currentBundle.isDealtWith()) {
                    connectedBundles.add(currentBundle);
                    if (getConnection(currentVillage, currentBundle.getVillage()) != null) {
                        // if the current bundle hasn't been dealt with and connects to the bundle being dealt with
                        if (bundleBeingDealtWith.getCostToReach() +
                                getConnection(currentVillage, currentBundle.getVillage()).getToll() <
                                currentBundle.getCostToReach()) {
                            currentBundle.setPriorVillage(bundleBeingDealtWith.getVillage());
                            currentBundle.setCostToReach(bundleBeingDealtWith.getCostToReach() +
                                    getConnection(currentVillage, currentBundle.getVillage()).getToll());
                        }
                    }
                }
                currentCompareNode = currentCompareNode.getNext();
            }

            // find the closest node to use as the next one to operate on
            currentNode = connectedBundles.getHead();
            if (currentNode == null)
                done = true;
            else {
                DijkstraBundle nearestBundle = (DijkstraBundle) currentNode.getData();
                currentNode = currentNode.getNext();
                while (currentNode != null) {
                    if (((DijkstraBundle) currentNode.getData()).getCostToReach() < nearestBundle.getCostToReach())
                        nearestBundle = (DijkstraBundle) currentNode.getData();
                    currentNode = currentNode.getNext();
                }
                nearestBundle.setDealtWith(true);
                finishedBundles.add(nearestBundle);
                bundleBeingDealtWith = nearestBundle;
            }

        } while(!done);

        return theGrid;
    }

    /**
     * converts the graph of nodes to a minimum spanning tree with no cycles
     * @param startVillage the village for the tree to start at
     */
    public void convertToMinimumSpanningTree (Village startVillage) {
        DoubleLinkedList<Road> newRoads = new DoubleLinkedList<>();

        DoubleLinkedList<Village> connectedVillages = new DoubleLinkedList<>();
        connectedVillages.add(startVillage);

        boolean doneAddingVillages = false;
        do {
            // find shortest road to add
            Node currentNode = allVillages.getHead();
            int shortestConnection = Integer.MAX_VALUE;
            Road shortestRoadToMake = null;
            while (currentNode != null) {
                Village currentVillage = (Village) currentNode.getData();

                // check if this village is already connected (if connectedVillages contains it)
                boolean alreadyConnected = false;
                Node currentConnectedNode = connectedVillages.getHead();
                while (currentConnectedNode != null) {
                    Village currentConnectedVillage = (Village) currentConnectedNode.getData();
                    if (currentConnectedVillage.equals(currentVillage)) {
                        alreadyConnected = true;
                        currentConnectedNode = null;
                    }
                    else currentConnectedNode = currentConnectedNode.getNext();
                }

                // if this village hasn't been connected, check if it can be directly reached from a connectedVillage
                if (!alreadyConnected) {
                    currentConnectedNode = connectedVillages.getHead();
                    while (currentConnectedNode != null) {
                        Village currentConnectedVillage = (Village) currentConnectedNode.getData();
                        // if the village can be reached, check how much it costs to reach it
                        if (getConnection(currentConnectedVillage, currentVillage) != null) {
                            if (getConnection(currentConnectedVillage, currentVillage).getToll() < shortestConnection) {
                                shortestConnection = getConnection(currentConnectedVillage, currentVillage).getToll();
                                shortestRoadToMake = getConnection(currentConnectedVillage, currentVillage);
                            }
                        }
                        currentConnectedNode = currentConnectedNode.getNext();
                    }
                }
                currentNode = currentNode.getNext();
            }

            if (shortestRoadToMake != null) {
                newRoads.add(shortestRoadToMake);
                connectedVillages.add(shortestRoadToMake.getTo());
            }
            else doneAddingVillages = true;
        } while (!doneAddingVillages);

        // replace the list of roads with the new list of roads from the tree
        adjacencyList.clearList();
        allRoads.clearList();
        Node currentNode = newRoads.getHead();
        while (currentNode != null) {
            Road currentRoad = (Road) currentNode.getData();
            addRoad(currentRoad);
            currentNode = currentNode.getNext();
        }
    }

    /**
     * @param from the village that the road is from
     * @param to the village that the road is to
     * @return the road between the villages, null if doesn't exist
     */
    private Road getConnection(Village from, Village to) {
        Node currentNode = allRoads.getHead();
        while (currentNode != null) {
            Road currentRoad = (Road) currentNode.getData();
            if (currentRoad.getTo().equals(to) && currentRoad.getFrom().equals(from))
                return currentRoad;
            currentNode = currentNode.getNext();
        }
        return null;
    }

    /**
     * @return the ID to be assigned to the next gnome
     */
    public int newGnomeID() {
        currentGnomeID++;
        return currentGnomeID;
    }

    /**
     * @return the ID to be assigned to the next village
     */
    public int newVillageID() {
        currentVillageID++;
        return currentVillageID;
    }

    /**
     * @return a list of all the villages
     */
    public DoubleLinkedList<Village> getAllVillages() {
        return allVillages;
    }

    /**
     * @return a list of all the roads
     */
    public DoubleLinkedList<Road> getAllRoads() {
        return allRoads;
    }

    /**
     * @return a list of all the gnomes
     */
    public DoubleLinkedList<Gnome> getAllGnomes() {
        return allGnomes;
    }
}
