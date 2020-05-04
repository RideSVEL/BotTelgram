package telegram.bot.entity;

public class World {

    private int totalConfirmed;
    private int totalDeath;
    private int totalRecovered;
    private int newConfirmed;
    private int newDeath;
    private int numberCountries;
    private int active;

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    @Override
    public String
    toString() {
        return "World{" +
                "totalConfirmed=" + totalConfirmed +
                ", totalDeath=" + totalDeath +
                ", totalRecovered=" + totalRecovered +
                ", newConfirmed=" + newConfirmed +
                ", newDeath=" + newDeath +
                ", numberCountries=" + numberCountries +
                '}';
    }

    public int getNewConfirmed() {
        return newConfirmed;
    }

    public void setNewConfirmed(int newConfirmed) {
        this.newConfirmed = newConfirmed;
    }

    public int getNewDeath() {
        return newDeath;
    }

    public void setNewDeath(int newDeath) {
        this.newDeath = newDeath;
    }

    public int getNumberCountries() {
        return numberCountries;
    }

    public void setNumberCountries(int numberCountries) {
        this.numberCountries = numberCountries;
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
