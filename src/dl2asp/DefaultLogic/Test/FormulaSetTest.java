package dl2asp.DefaultLogic.Test;

import dl2asp.DefaultLogic.Formula;
import dl2asp.DefaultLogic.FormulaSet;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class FormulaSetTest
{

    private FormulaSet set1;
    private FormulaSet set2;
    private Formula f1;
    private Formula f2;

    @Before
    public void setUp() throws Exception
    {
        f1 = new Formula("a");
        f2 = new Formula("b|c");
        set1 = new FormulaSet();
        set2 =  new FormulaSet();
        set1.add(f1);
        set1.add(f2);
        set2.add(f2);
    }

    @Test
    public void toStringTest() throws Exception
    {
        assertEquals(set1.toString(), "{(b | c), a}");
    }

    @Test
    public void cloneTest() throws Exception
    {
        FormulaSet clone = set1.clone();
        assertEquals(clone.toString(), set1.toString());
        assertNotEquals(clone.toString(), set2.toString());

    }

    @Test
    public void equalsTest() throws Exception
    {
        FormulaSet clone = set1.clone();
        assert(clone.equals(set1));
        assertFalse(clone.equals(set2));
    }

    @Test
    public void negateAllTest() throws Exception
    {
        FormulaSet negated = set1.negateAll();
        assertEquals(negated.toString(), "{!a, !(b | c)}");
        assertNotEquals(negated.toString(), "{!a, (!b | !c)}");
    }

    @Test
    public void addAllTest() throws Exception
    {
        FormulaSet set3 = new FormulaSet();
        set3.addAll(set1);
        assert(set3.equals(set1));
        assertFalse(set3.equals(set2));
    }

    @Test
    public void concludesOneOfTest() throws Exception
    {
        assert(set1.concludesOneOf(set2));
        assert(set2.concludesOneOf(set1));
        assertFalse(set1.concludesOneOf(new FormulaSet("d", "e")));
        FormulaSet set3 = new FormulaSet("b", "(!b | !c)", "(c | d)");
        assertFalse(set3.concludes(new Formula("!a")));
    }

    @Test
    public void concludesTest() throws Exception
    {
        assert(set1.concludes(f1));
        assert(set1.concludes(f2));
        assert(set1.concludes(new Formula("b|c")));
        assert(set1.concludes(new Formula("a|b|c")));
        assertFalse(set2.concludes(f1));
        assert(set2.concludes(f2));
    }

    @Test
    public void containsTest() throws Exception
    {
        assert(set1.contains(f1));
        assert(set1.contains(f2));
        assertFalse(set2.contains(f1));
        assert(set2.contains(f2));
    }

    @Test
    public void addTest() throws Exception
    {
        assertFalse(set2.contains(f1));
        set2.add(f1);
        assert(set2.contains(f1));
    }

    @Test
    public void sizeTest() throws Exception
    {
        assertEquals(set2.size(), 1);
        set2.add(f1);
        assertEquals(set2.size(), 2);
    }

    @Test
    public void iteratorTest() throws Exception
    {
        Iterator<Formula> it = set1.iterator();
        assert(it.hasNext());
        assertEquals(it.next(), f2);
        assert(it.hasNext());
        assertEquals(it.next(), f1);
        assertFalse(it.hasNext());
    }

    @Test
    public void streamTest() throws Exception
    {
        assertEquals(set2.stream().count(), 1);
        assert(set2.stream().allMatch(f -> f.equals(f2)));
    }

    @Test
    public void isSubSetOfTest() throws Exception
    {
        assert(set2.isSubSetOf(set1));
        assertFalse(set1.isSubSetOf(set2));
    }

    @Test
    public void hashCodeTest() throws Exception
    {
        assertNotEquals(set1.hashCode(), set2.hashCode());
        FormulaSet set3 = set1.clone();
        assertEquals(set3.hashCode(), set1.hashCode());

    }
}