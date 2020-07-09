package spacedandy.model;

public class Project {
    private int id;
    private String startDate;
    private String description;
    private double price;
    private String endDate;

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", startDate='" + startDate + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    public String toStringNoId() {
        return "Project{" +
                "startDate='" + startDate + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", endDate='" + endDate + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
