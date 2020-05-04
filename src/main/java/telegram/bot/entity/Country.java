package telegram.bot.entity;

public class Country {

    private String name;
    private int newConfirmed;
    private int totalConfirmed;
    private int newDeath;
    private int totalDeath;
    private int totalRecovered;
    private int hardState;
    private int rank;
    private int active;

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNewConfirmed() {
        return newConfirmed;
    }

    public void setNewConfirmed(int newConfirmed) {
        this.newConfirmed = newConfirmed;
    }

    public int getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(int totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public int getNewDeath() {
        return newDeath;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                ", newConfirmed=" + newConfirmed +
                ", totalConfirmed=" + totalConfirmed +
                ", newDeath=" + newDeath +
                ", totalDeath=" + totalDeath +
                ", totalRecovered=" + totalRecovered +
                ", hardState=" + hardState +
                ", rank=" + rank +
                ", active=" + active +
                '}';
    }

    public void setNewDeath(int newDeath) {
        this.newDeath = newDeath;
    }

    public int getTotalDeath() {
        return totalDeath;
    }

    public int getHardState() {
        return hardState;
    }

    public void setHardState(int hardState) {
        this.hardState = hardState;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setTotalDeath(int totalDeath) {
        this.totalDeath = totalDeath;
    }


    public int getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(int totalRecovered) {
        this.totalRecovered = totalRecovered;
    }


}
