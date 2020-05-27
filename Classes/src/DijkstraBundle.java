/**
 * Created by clombardo on 8/1/17.
 */
public class DijkstraBundle {

    // instance fields
    private Village village;
    private int costToReach;
    private Village priorVillage;
    private boolean dealtWith;

    /**
     * constructs a new Dijkstra Bundle
     * @param village the village this bundle represents
     * @param costToReach the current cost to reach the village this bundle represents
     * @param priorVillage the village prior to the village this bundle represents in the current cheapest path
     * @param dealtWith whether or not this bundle has been dealt with
     */
    public DijkstraBundle(Village village, int costToReach, Village priorVillage, boolean dealtWith) {
        this.village = village;
        this.costToReach = costToReach;
        this.priorVillage = priorVillage;
        this.dealtWith = dealtWith;
    }

    /**
     * @return the village this bundle represents
     */
    public Village getVillage() {
        return village;
    }

    /**
     * @return whether or not this bundle is dealt with
     */
    public boolean isDealtWith() {
        return dealtWith;
    }

    /**
     * @param dealtWith whatever the bundle's dealtWith status should be set to
     */
    public void setDealtWith(boolean dealtWith) {
        this.dealtWith = dealtWith;
    }

    /**
     * @return the current cost to reach this bundle's village
     */
    public int getCostToReach() {
        return costToReach;
    }

    /**
     * @param costToReach the current cost to reach this bundle's village
     */
    public void setCostToReach(int costToReach) {
        this.costToReach = costToReach;
    }

    /**
     * @return the village prior to the one this bundle represents
     */
    public Village getPriorVillage() {
        return priorVillage;
    }

    /**
     * @param priorVillage the village to set the prior village to
     */
    public void setPriorVillage(Village priorVillage) {
        this.priorVillage = priorVillage;
    }
}
