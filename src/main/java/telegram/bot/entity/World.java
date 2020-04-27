package telegram.bot.entity;

public class World {

    private int totalConfirmed;
    private int totalDeath;
    private int totalRecovered;

    @Override
    public String toString() {
        return "World{" +
                "totalConfirmed=" + totalConfirmed +
                ", totalDeath=" + totalDeath +
                ", totalRecovered=" + totalRecovered +
                '}';
    }

    public int getTotalConfirmed() {
        return totalConfirmed;
    }

    public void setTotalConfirmed(int totalConfirmed) {
        this.totalConfirmed = totalConfirmed;
    }

    public int getTotalDeath() {
        return totalDeath;
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
