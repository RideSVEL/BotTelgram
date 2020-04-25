package telegram.bot.entity;

public class Country {

    private String name;
    private int newConfirmed;
    private int totalConfirmed;
    private int newDeath;
    private int totalDeath;
    private int newRecovered;
    private int totalRecovered;
    private String date;
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

    public void setNewDeath(int newDeath) {
        this.newDeath = newDeath;
    }

    public int getTotalDeath() {
        return totalDeath;
    }

    @Override
    public String toString() {
        return "Country{\n" +
                "name='" + name + '\'' +
                ", \nnewConfirmed=" + newConfirmed +
                ", \ntotalConfirmed=" + totalConfirmed +
                ", \nnewDeath=" + newDeath +
                ", \ntotalDeath=" + totalDeath +
                ", \nnewRecovered=" + newRecovered +
                ", \ntotalRecovered=" + totalRecovered +
                ", \ndate='" + date + '\'' +
                ", \nactive=" + active +
                '}';
    }

    public void setTotalDeath(int totalDeath) {
        this.totalDeath = totalDeath;
    }

    public int getNewRecovered() {
        return newRecovered;
    }

    public void setNewRecovered(int newRecovered) {
        this.newRecovered = newRecovered;
    }

    public int getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(int totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
