package dl2asp.DefaultLogic.ClauseSet.Test;

import dl2asp.DefaultLogic.ClauseSet.Clause;
import dl2asp.DefaultLogic.ClauseSet.ClauseSet;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;


public class ClauseSetTest
{
    private ClauseSet set;

    @Before
    public void setUp() throws Exception
    {
        set = ClauseSet.fromCNFString("a&b&(!c|d)");
    }

    @Test
    public void toStringTest() throws Exception
    {
        assertEquals(set.toString(), "{{a},{!c,d},{b}}");
        assertNotEquals(set.toString(), "{{a},{b},{!c,d}}");
        assertNotEquals(set.toString(), "{{a}, {!c,d}, {b}}");
        assertNotEquals(set.toString(), "{a},{!c,d},{b}");
        assertNotEquals(set.toString(), "{{a,!c,d,b}}");
    }

    @Test
    public void cloneTest() throws Exception
    {
        ClauseSet clone = set.clone();
        assertEquals(clone.toString(), "{{a},{!c,d},{b}}");
    }

    @Test
    public void equalsTest() throws Exception
    {
        ClauseSet clone = set.clone();
        assert(clone.equals(set));
        ClauseSet other = ClauseSet.fromCNFString("a&b&c");
        assertFalse(other.equals(set));
    }

    @Test
    public void fromCNFStringTest() throws Exception
    {
        set = ClauseSet.fromCNFString("(a|b)&c");
        assertEquals(set.toString(), "{{a,b},{c}}");

        set = ClauseSet.fromCNFString("(a&b)|c"); //This is not a cnf string, therefore the behavior is undefined
        assertNotEquals(set.toString(), "{{a,b},{c}}");
    }

    @Test
    public void iteratorTest() throws Exception
    {
        Iterator<Clause> it = set.iterator();
        assert(it.hasNext());
        assertEquals(it.next(), new Clause("a"));
        assert(it.hasNext());
        assertEquals(it.next(), new Clause("!c", "d"));
        assert(it.hasNext());
        assertEquals(it.next(), new Clause("b"));
        assertFalse(it.hasNext());
    }

    @Test
    public void streamTest() throws Exception
    {
        assertEquals(set.stream().count(), 3);
        //assert(set2.stream().allMatch(f -> f.equals(f2))); TODO
    }

    @Test
    public void linearResolutionTest() throws Exception
    {
        ClauseSet pelletierSet = ClauseSet.fromCNFString("((!p|p)&(!p|p|q)&(!p|!q|p)&(p|!p|q|!q)&(q|!q|p)&(q|!q|!p)&(!q|q))");
        assertFalse(pelletierSet.linearResolution());
    }

    @Test
    public void hashCodeTest() throws Exception
    {
        ClauseSet clone = set.clone();
        assertEquals(clone.hashCode(), set.hashCode());
        ClauseSet other = ClauseSet.fromCNFString("a&b&c");
        assertNotEquals(other.hashCode(), set.hashCode());
    }
}