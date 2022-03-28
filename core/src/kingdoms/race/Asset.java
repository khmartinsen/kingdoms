package kingdoms.race;

// assets a kingdom (or thing) possesses such as wood, population, gold...
// and describes how that resource grows
public class Asset {
    private final Resource resource;
    private double amount = 0.0;
    private double rate = 0.0;
    private double bonus = 1.0;

    Asset(Asset someAsset) {
        this.resource = someAsset.getResource();
        this.amount = someAsset.getAmount();
        this.rate = someAsset.getRate();
        this.bonus = someAsset.getBonus();
    }

    Asset(Resource resource) {this.resource = resource;}

    Asset(Resource resource, double startAmount, double rate) {
        this.resource = resource;
        amount = startAmount;
        this.rate = rate;
    }

    public void addToAmount(double amount) { this.amount += amount; }
    public void addToRate(double rate) { this.rate += rate;}
    public void addToBonus(double bonus) { this.bonus += bonus;}

    public Resource getResource() {return resource;}
    public double getAmount() {return amount;}
    public double getRate() { return rate; }
    public double getBonus() { return bonus; }

    // grow every tick
    public void grow() {amount += bonus * rate;}
}
