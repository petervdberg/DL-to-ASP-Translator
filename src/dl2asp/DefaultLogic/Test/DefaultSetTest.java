package dl2asp.DefaultLogic.Test;

import dl2asp.DefaultLogic.Default;
import dl2asp.DefaultLogic.DefaultSet;
import dl2asp.DefaultLogic.Formula;
import dl2asp.DefaultLogic.FormulaSet;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class DefaultSetTest
{
    Default def1;
    Default def2;
    DefaultSet set;

    @Before
    public void setUp() throws Exception
    {
        set = new DefaultSet();
        def1 = new Default(new Formula("a"), new FormulaSet("b"), new Formula("c"));
        set.add(def1);
        def2 = new Default(new Formula("d"), new FormulaSet("e"), new Formula("f"));
    }

    @Test
    public void containsTest() throws Exception
    {
        assert(set.contains(def1));
        assertFalse(set.contains(def2));
    }

    @Test
    public void toStringTest() throws Exception
    {
        assertEquals(set.toString(), "{a:b/c}");
        assertNotEquals(set.toString(), "{ a :b / c}");
        assertNotEquals(set.toString(), "a:b/c");
        assertNotEquals(set.toString(), "a :b / c");
    }

    @Test
    public void cloneTest() throws Exception
    {
        DefaultSet clone = set.clone();
        assertEquals(clone.toString(), set.toString());
        set.add(def2);
        assertNotEquals(clone.hashCode(), set.hashCode());
    }

    @Test
    public void equalsTest() throws Exception
    {
        DefaultSet clone = set.clone();
        assert(clone.equals(set));
        clone.add(def2);
        assertFalse(clone.equals(set));
    }

    @Test
    public void addTest() throws Exception
    {
        assertFalse(set.contains(def2));
        set.add(def2);
        assert(set.contains(def2));
    }

    @Test
    public void removeTest() throws Exception
    {
        assert(set.contains(def1));
        set.remove(def1);
        assertFalse(set.contains(def1));
    }

    @Test
    public void iteratorTest() throws Exception
    {
        Iterator<Default> it = set.iterator();
        assert(it.hasNext());
        assertEquals(it.next(), def1);
        assertFalse(it.hasNext());
    }

    @Test
    public void streamTest() throws Exception
    {
        assertEquals(set.stream().count(), 1);
        assert(set.stream().allMatch(d -> d.equals(def1)));
    }

    @Test
    public void hashCodeTest() throws Exception
    {
        DefaultSet clone = set.clone();
        assertEquals(clone.hashCode(), set.hashCode());
        clone.add(def2);
        assertNotEquals(clone.hashCode(), set.hashCode());
    }
}