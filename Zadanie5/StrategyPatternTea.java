package Zadanie5;

// Interfejs Strategy
interface TeaBrewingStrategy {
    void brew();
}

// Konkretne strategie robienia herbaty
class BlackTeaBrewing implements TeaBrewingStrategy {
    @Override
    public void brew() {
        System.out
                .println("Brewing black tea: Boil water, add black tea leaves, steep for 3-5 minutes, remove leaves.");
    }
}

class GreenTeaBrewing implements TeaBrewingStrategy {
    @Override
    public void brew() {
        System.out.println(
                "Brewing green tea: Heat water to 80°C, add green tea leaves, steep for 2-3 minutes, remove leaves.");
    }
}

class HerbalTeaBrewing implements TeaBrewingStrategy {
    @Override
    public void brew() {
        System.out
                .println("Brewing herbal tea: Boil water, add herbal tea blend, steep for 5-7 minutes, remove blend.");
    }
}

// Klasa kontekstowa
class TeaMaker {
    private TeaBrewingStrategy teaBrewingStrategy;

    public void setTeaBrewingStrategy(TeaBrewingStrategy teaBrewingStrategy) {
        this.teaBrewingStrategy = teaBrewingStrategy;
    }

    public void brewTea() {
        if (teaBrewingStrategy != null) {
            teaBrewingStrategy.brew();
        } else {
            System.out.println("No tea brewing strategy set.");
        }
    }
}

// Klasa testowa
public class StrategyPatternTea {
    public static void main(String[] args) {
        TeaMaker teaMaker = new TeaMaker();

        // Robienie czarnej herbaty
        teaMaker.setTeaBrewingStrategy(new BlackTeaBrewing());
        teaMaker.brewTea();

        // Robienie zielonej herbaty
        teaMaker.setTeaBrewingStrategy(new GreenTeaBrewing());
        teaMaker.brewTea();

        // Robienie herbaty ziołowej
        teaMaker.setTeaBrewingStrategy(new HerbalTeaBrewing());
        teaMaker.brewTea();
    }
}
