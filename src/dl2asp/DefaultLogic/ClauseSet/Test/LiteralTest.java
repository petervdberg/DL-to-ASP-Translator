package dl2asp.DefaultLogic.ClauseSet.Test;

import dl2asp.DefaultLogic.ClauseSet.Literal;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LiteralTest
{
    private Literal literal;

    @Before
    public void setUp() throws Exception
    {
        literal = new Literal("!a");
    }

    @Test
    public void toStringTest() throws Exception
    {
        assertEquals(literal.toString(), "!a");
        assertNotEquals(literal.toString(), "-a");
        assertNotEquals(literal.toString(), "not a");
        assertNotEquals(literal.toString(), "a");
    }

    @Test
    public void equalsTest() throws Exception
    {
        assert(literal.equals(new Literal("!a")));
        assertFalse(literal.equals(new Literal("a")));
        assertFalse(literal.equals(new Literal("b")));
        assertFalse(literal.equals(Literal.FALSUM));
        assertFalse(literal.equals(Literal.TAUTOLOGY));
    }

    @Test
    public void hashCodeTest() throws Exception
    {
        assertEquals(literal.hashCode(), new Literal("!a").hashCode());
        assertNotEquals(literal.hashCode(), new Literal("a").hashCode());
        assertNotEquals(literal.hashCode(), new Literal("b").hashCode());
    }

    @Test
    public void isFalsumTest() throws Exception
    {
        assert(Literal.FALSUM.isFalsum());
        assertFalse(Literal.TAUTOLOGY.isFalsum());
        assertFalse(literal.isFalsum());
    }

    @Test
    public void isTautologyTest() throws Exception
    {
        assert(Literal.TAUTOLOGY.isTautology());
        assertFalse(Literal.FALSUM.isTautology());
        assertFalse(literal.isTautology());
    }

    @Test
    public void negateTest() throws Exception
    {
        assert(literal.negate().equals(new Literal("a")));
        assertFalse(literal.negate().equals(literal));
    }

    @Test
    public void cloneTest() throws Exception
    {
        assert(literal.equals(literal.clone()));
    }
}