package dl2asp.AnswerSetProgram.Test;

import dl2asp.AnswerSetProgram.Atom;
import dl2asp.AnswerSetProgram.AtomSet;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class AtomSetTest
{
    private AtomSet set1;
    private AtomSet set2;
    private AtomSet set3;

    @Before
    public void setUp() throws Exception
    {
        set1 = new AtomSet("a", "!b");
        set2 = new AtomSet("a");
        set3 = new AtomSet("b");
    }

    @Test
    public void toStringTest() throws Exception
    {
        assertEquals(set1.toString(), "{a, !b}");
        assertNotEquals(set1.toString(), "{a,!b}");
        assertNotEquals(set1.toString(), "a, !b");
        assertNotEquals(set1.toString(), "a,!b");
    }

    @Test
    public void equalsTest() throws Exception
    {
        assert(set1.equals(new AtomSet("a", "!b")));
        assertFalse(set1.equals(set2));
    }

    @Test
    public void cloneTest() throws Exception
    {
        assert(set1.equals(set1.clone()));
    }

    @Test
    public void containsTest() throws Exception
    {
        assert(set1.contains(new Atom("a")));
        assert(set1.contains(new Atom("!b")));
        assertFalse(set1.contains(new Atom("!a")));
        assertFalse(set1.contains(new Atom("b")));
        assertFalse(set1.contains(new Atom("c")));
    }

    @Test
    public void addTest() throws Exception
    {
        assertFalse(set1.contains(new Atom("c")));
        set1.add(new Atom("c"));
        assert(set1.contains(new Atom("c")));
    }

    @Test
    public void addAllTest() throws Exception
    {
        assertFalse(set3.contains(new Atom("a")));
        assertFalse(set3.contains(new Atom("!b")));
        set3.addAll(set1);
        assert(set3.contains(new Atom("a")));
        assert(set3.contains(new Atom("!b")));

    }

    @Test
    public void removeTest() throws Exception
    {
        assert(set1.contains(new Atom("a")));
        set1.remove(new Atom("a"));
        assertFalse(set1.contains(new Atom("c")));

    }

    @Test
    public void sizeTest() throws Exception
    {
        assertEquals(set1.size(), 2);
        assertEquals(set2.size(), 1);
        assertEquals(new AtomSet().size(), 0);
    }

    @Test
    public void iteratorTest() throws Exception
    {
        Iterator<Atom> it = set1.iterator();
        assert(it.hasNext());
        assert(it.next().equals(new Atom("a")));
        assert(it.hasNext());
        assert(it.next().equals(new Atom("!b")));
        assertFalse(it.hasNext());
    }

    @Test
    public void streamTest() throws Exception
    {
        assertEquals(set2.stream().count(), 1);
        assert(set2.stream().allMatch(d -> d.equals(new Atom("a"))));
    }

    @Test
    public void isSubSetOfTest() throws Exception
    {
        assert(set2.isSubSetOf(set1));
        assert(set1.isSubSetOf(set1));
        assertFalse(set1.isSubSetOf(set2));
    }

    @Test
    public void isInComplementOfTest() throws Exception
    {
        assert(set3.isInComplementOf(set1));
        assert(set1.isInComplementOf(set3));
        assertFalse(set2.isInComplementOf(set1));
    }

    @Test
    public void hashCodeTest() throws Exception
    {
        assertEquals(set1.hashCode(), set1.clone().hashCode());
        assertNotEquals(set1.hashCode(), set2.hashCode());
    }
}