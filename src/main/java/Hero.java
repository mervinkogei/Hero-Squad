public class Hero {
    private String name;
    private int age;
    private String specialPower;
    private  String weakness;
    private static List<Hero> instances = new ArrayList<Hero>();
    private int Id;

    public Hero(String name, int age, String special_power, String weakness) {
        this.name = name;
        this.age = age;
        this.specialPower = special_power;
        this.weakness = weakness;
        instances.add(this);
        Id = instances.size();
    }
}
