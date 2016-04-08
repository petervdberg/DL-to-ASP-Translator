package dl2asp.DefaultLogic.Test;

import dl2asp.DefaultLogic.Formula;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class FormulaTest
{
    Formula formula;
    Formula other;

    @Before
    public void setUp() throws Exception
    {
        formula = new Formula("(a&!b)|c");
        other = new Formula("d&e");
    }

    @Test
    public void toStringTest() throws Exception
    {
        assertEquals(formula.toString(), "((!b & a) | c)");
        assertNotEquals(formula.toString(), other.toString());
        assertNotEquals(formula.toString(), "(!b & a) | c");
        assertNotEquals(formula.toString(), "((!b&a)|c)");
        assertNotEquals(formula.toString(), "(!b&a)|c");
        assertNotEquals(formula.toString(), "(c | (!b & a))");
    }

    @Test
    public void cloneTest() throws Exception
    {
        Formula clone = formula.clone();
        assertEquals(clone.toString(), formula.toString());
        assertNotEquals(clone.toString(), other.toString());
    }

    @Test
    public void equalsTest() throws Exception
    {
        Formula clone = formula.clone();
        assert(clone.equals(formula));
        assert(formula.equals(new Formula("(!b & a) | c")));
        assert(formula.equals(new Formula("((!b&a)|c)")));
        assert(formula.equals(new Formula("(!b&a)|c")));
        assert(formula.equals(new Formula("(c | (!b & a))")));
        assertFalse(formula.equals(other));
    }

    @Test
    public void isFalsumTest() throws Exception
    {
        assert(Formula.FALSUM.isFalsum());
        assertFalse((new Formula("a&!a")).isFalsum());
        assertFalse(formula.isFalsum());
    }

    @Test
    public void isTautologyTest() throws Exception
    {
        assert(Formula.TAUTOLOGY.isTautology());
        assertFalse((new Formula("a|!a")).isTautology());
        assertFalse(formula.isTautology());
    }

    @Test
    public void toCNFTest() throws Exception
    {
        assertEquals(formula.toCNF().toString(), "((!b | c) & (a | c))");
    }

    @Test
    public void concludesTest() throws Exception
    {
        assert(formula.concludes(Formula.TAUTOLOGY));
        assert(formula.concludes(new Formula("a|c")));
        assert(formula.concludes(new Formula("!b|c")));
        assertFalse(formula.concludes(Formula.FALSUM));
        assertFalse(formula.concludes(other));
        assertFalse(formula.concludes(new Formula("a")));
        assertFalse(formula.concludes(new Formula("!a")));
        assertFalse(formula.concludes(new Formula("b")));
        assertFalse(formula.concludes(new Formula("!b")));
        assertFalse(formula.concludes(new Formula("c")));
        assertFalse(formula.concludes(new Formula("!c")));
    }

    @Test
    public void conjunctTest() throws Exception
    {
        assertEquals(formula.conjunct(other).toString(), "(((!b & a) | c) & (d & e))");

    }

    @Test
    public void simplifyTest() throws Exception
    {
        assertEquals(formula.simplify().toString(), "((!b & a) | c)");
        assertEquals((new Formula("a|!a")).simplify().toString(), Formula.TAUTOLOGY.toString());
        assertEquals((new Formula("a&!a")).simplify().toString(), Formula.FALSUM.toString());
    }

    @Test
    public void negateTest() throws Exception
    {
        assertEquals(formula.negate().toString(), "!((!b & a) | c)");
    }

    @Test
    public void hashCodeTest() throws Exception
    {
        Formula clone = formula.clone();
        assertEquals(clone.hashCode(), formula.hashCode());
        assertNotEquals(clone.hashCode(), other.hashCode());
    }
}