package dl2asp.AnswerSetProgram.Test;

import dl2asp.AnswerSetProgram.Atom;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AtomTest
{
    Atom atom;

    @Before
    public void setUp() throws Exception
    {
        atom = new Atom("test");
    }

    @Test
    public void toStringTest() throws Exception
    {
        assertEquals(atom.toString(), "test");
        assertNotEquals(atom.toString(), "somethingElse");
    }

    @Test
    public void equalsTest() throws Exception
    {
        assert(atom.equals(new Atom("test")));
        assertFalse(atom.equals(new Atom("somethingElse")));
    }

    @Test
    public void cloneTest() throws Exception
    {
        assert(atom.equals(atom.clone()));
    }

    @Test
    public void hashCodeTest() throws Exception
    {
        assertEquals(atom.hashCode(), atom.clone().hashCode());
    }
}