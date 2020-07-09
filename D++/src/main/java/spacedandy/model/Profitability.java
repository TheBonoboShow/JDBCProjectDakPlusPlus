package spacedandy.model;

public class Profitability {
    private int id;
    private double profit;

    @Override
    public String toString() {
        return "{" +
                "ProjectId= " + id +
                ", Profit= " + profit +
                " €}";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }
}
