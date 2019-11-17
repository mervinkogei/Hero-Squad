import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class SquadTest {
    @Test
    public void creates_instanceOfSquad()
    {
        Squad squad = new Squad(5,"Squad Team_A","black lives matter");
        assertTrue(squad instanceof Squad);
    }
    @Test
    public void saves_maxSize_5()
    {
        Squad squad = new Squad(5,"Squad Team_A","black lives matter");
        assertEquals(5, squad.getMax_size());
    }
    @Test
    public void saves_squadName_squad_team_A()
    {
        Squad squad = new Squad(5,"Squad Team_A","black lives matter");
        assertEquals("Squad Team_A", squad.getName());
    }
    @Test
    public void saves_cause_black_lives_matterl()
    {
        Squad squad = new Squad(5,"Squad Team_A","black lives matter");
        assertEquals("black lives matter", squad.getCause());
    }

    @Test
    public void all_returns_all_instancesOfSquad()
    {
        Squad squad = new Squad(5,"Squad Team_A","black lives matter");
        Squad squad2 = new Squad(5,"Squad Team_A","black lives matter");
        assertTrue(Squad.all().contains(squad));
        assertTrue(Squad.all().contains(squad2));

    }

    @Test
    public void clear_emptiesAllSquadsFromList_0() {
        Squad.clear();
        assertEquals(Squad.all().size(), 0);
    }

    @Test
    public void getId_SquadInstantiateWithAnId_1() {
        Squad.clear();
        Squad squad = new Squad(5,"Squad Team_A","black lives matter");
        assertEquals(1,squad.getId());
    }

    @Test
    public void find_returnsCategoryWithSameId_secondCategory() {
        Squad.clear();
        Squad squad = new Squad(5,"Squad Team_A","black lives matter");
        assertEquals(Squad.find(squad.getId()), squad);
    }

    @Test
    public void getSquad_initiallyReturnsEmptyList_ArrayList() {
        Squad.clear();
        Squad squad = new Squad(5,"Squad Team_A","black lives matter");
        assertEquals(0, squad.getHeroes().size());
    }

    @Test
    public void addsHeroesToList_true() {
        Squad squad = new Squad(5,"Squad Team_A","black lives matter");
        Hero hero = new Hero("Kogei",30,"Strength","Giving up Easily");
        squad.addHero(hero);
        assertTrue(squad.getHeroes().contains(hero));
    }
}
