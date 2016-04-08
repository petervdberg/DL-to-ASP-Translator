package dl2asp.DefaultLogic.ClauseSet.Test;

import dl2asp.DefaultLogic.ClauseSet.Clause;
import dl2asp.DefaultLogic.ClauseSet.Literal;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class ClauseTest
{
    private Clause clause;

    @Before
    public void setUp() throws Exception
    {
        clause = new Clause("a", "!b", "c");
    }

    @Test
    public void toStringTest() throws Exception
    {
        assertEquals(clause.toString(), "{!b,c,a}");
        assertNotEquals(clause.toString(), "{!b, c, a}");
    }

    @Test
    public void cloneTest() throws Exception
    {
        Clause clone = clause.clone();
        assertEquals(clone.toString(), clause.toString());
    }

    @Test
    public void equalsTest() throws Exception
    {
        Clause clone = clause.clone();
        assert(clone.equals(clause));
    }

    @Test
    public void hashCodeTest() throws Exception
    {
        Clause clone = clause.clone();
        assertEquals(clone.hashCode(), clause.hashCode());
        assertEquals(new Clause("!c").hashCode(), new Clause("!c").hashCode());
    }

    @Test
    public void containsTest() throws Exception
    {
        assert(clause.contains(new Literal("a")));
        assert(clause.contains(new Literal("!b")));
        assert(clause.contains(new Literal("c")));
        assertFalse(clause.contains(new Literal("!a")));
        assertFalse(clause.contains(new Literal("b")));
        assertFalse(clause.contains(new Literal("!c")));
        assertFalse(clause.contains(new Literal("d")));
        assertFalse(clause.contains(Literal.FALSUM));
        assertFalse(clause.contains(Literal.TAUTOLOGY));
    }

    @Test
    public void sizeTest() throws Exception
    {
        assertEquals(clause.size(), 3);
    }

    @Test
    public void iteratorTest() throws Exception
    {
        Iterator<Literal> it = clause.iterator();
        assert(it.hasNext());
        assertEquals(it.next(), new Literal("!b"));
        assert(it.hasNext());
        assertEquals(it.next(), new Literal("c"));
        assert(it.hasNext());
        assertEquals(it.next(), new Literal("a"));
        assertFalse(it.hasNext());

    }

    @Test
    public void streamTest() throws Exception
    {
        assertEquals(clause.stream().count(), 3);
        //assert(set2.stream().allMatch(f -> f.equals(f2))); TODO
    }
}