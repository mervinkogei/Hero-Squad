import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class HeroTest {
    @Test
    public void createsInstanceOfHero()
    {
        Hero hero = new Hero("Kogei",24,"Strength","Giving up easily");
        assertTrue(hero instanceof Hero);
    }

    @Test
    public void savesName()
    {
        Hero hero = new Hero("Kogei",24,"Strength","Giving up easily");
        assertEquals("Kogei",hero.getName());
    }

    @Test
    public void savesAge()
    {
        Hero hero = new Hero("Kogei",24,"Strength","Giving up easily");
        assertEquals(24,hero.getAge());
    }

    @Test
    public void saveSpecialPowers()
    {
        Hero hero = new Hero("Kogei",24,"Strength","Giving up easily");
        assertEquals("Strength",hero.getSpecial_power());
    }

    @Test
    public void savesWeakness()
    {
        Hero hero = new Hero("Kogei",24,"Strength","Giving up easily");
        assertEquals("Giving up easily",hero.getWeakness());
    }

    @Test
    public void all_returns_all_instancesOfHero()
    {
        Hero hero = new Hero("Kogei Junior",25,"Strength","Giving up easily");
        Hero hero1 = new Hero("Kogei",24,"Strength","Giving up easily");
        assertTrue(Hero.all().contains(hero));
        assertTrue(Hero.all().contains(hero1));
    }

    @Test
    public void clear_emptiesAllCategoriesFromList_0() {
        Hero.clear();
        assertEquals(Hero.all().size(), 0);
    }

    @Test
    public void getId_heroInstantiateWithAnId_1() {
        Hero.clear();
        Hero hero = new Hero("Kogei",40,"Strength","Giving up easily");
        assertEquals(1,hero.getId());
    }

    @Test
    public void find_returnsCategoryWithSameId_secondCategory() {
        Hero.clear();
        Hero hero = new Hero("Kogei",40,"Strength","Giving up easily");
        assertEquals(Hero.find(hero.getId()), hero);
    }

    @Test
    public void find_hero_by_name() {
        Hero.clear();
        Hero hero = new Hero("Kogei Junior",25,"Strength","Giving up easily");
        assertTrue(Hero.findHeroByName("Kogei Junior"));
    }

    @Test
    public void find_returnsNullWhenNoTaskFound_null() {
        assertTrue(Hero.find(1000) == null);
    }
}
