package xd.arkosammy.explosions;

public enum ExplosionHealingMode {

    DEFAULT_MODE("default_mode", "Default healing mode"),
    DAYTIME_HEALING_MODE("daytime_healing_mode", "Daytime healing mode"),
    DIFFICULTY_BASED_HEALING_MODE("difficulty_based_healing_mode", "Difficulty-based healing mode"),
    WEATHER_BASED_HEALING_MODE("weather_based_healing_mode", "Weather-based healing mode"),
    BLAST_RESISTANCE_BASED_HEALING_MODE("blast_resistance_based_healing_mode", "Blast resistance-based healing mode");

    private final String name;
    private final String displayName;

    ExplosionHealingMode(String name, String displayName){
        this.name = name;
        this.displayName = displayName;
    }

    public String getName(){
        return this.name;
    }

    public String getDisplayName(){
        return this.displayName;
    }

    public static ExplosionHealingMode getFromName(String name){
        for(ExplosionHealingMode explosionMode : ExplosionHealingMode.values()){
            if(explosionMode.getName().equals(name)){
                return explosionMode;
            }
        }
        return ExplosionHealingMode.DEFAULT_MODE;
    }
}
