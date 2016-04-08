package dl2asp.AnswerSetProgram.Test;

import dl2asp.AnswerSetProgram.AtomSet;
import dl2asp.AnswerSetProgram.Program;
import dl2asp.AnswerSetProgram.Rule;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;

import static org.junit.Assert.*;

public class ProgramTest
{
    private Program program;
    AtomSet interpretation;

    @Before
    public void setUp() throws Exception
    {
        program = new Program();
        program.add(new Rule(new AtomSet("a"), new AtomSet("c"), AtomSet.Empty));
        program.add(new Rule(new AtomSet("c"), AtomSet.Empty,    new AtomSet("d")));
        program.add(new Rule(new AtomSet("a"), new AtomSet("b"), new AtomSet("e")));
        program.add(new Rule(new AtomSet("d"), AtomSet.Empty,    new AtomSet("c")));
        program.add(new Rule(new AtomSet("b"), new AtomSet("a"), new AtomSet("e")));
        program.add(new Rule(new AtomSet("e"), AtomSet.Empty,    new AtomSet("d")));

        interpretation = new AtomSet("a", "-b", "c", "-d", "e");
    }

    @Test
    public void toStringTest() throws Exception
    {
        assertEquals(program.toString(), "{a←b,not e, b←a,not e, a←c, c←not d, d←not c, e←not d}");
    }

    @Test
    public void equalsTest() throws Exception
    {
        Program program2 = new Program();
        program2.add(new Rule(new AtomSet("a"), new AtomSet("c"), AtomSet.Empty));
        program2.add(new Rule(new AtomSet("c"), AtomSet.Empty,    new AtomSet("d")));
        program2.add(new Rule(new AtomSet("a"), new AtomSet("b"), new AtomSet("e")));
        program2.add(new Rule(new AtomSet("d"), AtomSet.Empty,    new AtomSet("c")));
        program2.add(new Rule(new AtomSet("b"), new AtomSet("a"), new AtomSet("e")));
        program2.add(new Rule(new AtomSet("e"), AtomSet.Empty,    new AtomSet("d")));
        assert(program.equals(program2));
    }

    @Test
    public void cloneTest() throws Exception
    {
        assert(program.equals(program.clone()));
    }

    @Test
    public void containsTest() throws Exception
    {
        assert(program.contains(new Rule(new AtomSet("a"), new AtomSet("c"), AtomSet.Empty)));
        assert(program.contains(new Rule(new AtomSet("c"), AtomSet.Empty,    new AtomSet("d"))));
        assert(program.contains(new Rule(new AtomSet("a"), new AtomSet("b"), new AtomSet("e"))));
        assert(program.contains(new Rule(new AtomSet("d"), AtomSet.Empty,    new AtomSet("c"))));
        assert(program.contains(new Rule(new AtomSet("b"), new AtomSet("a"), new AtomSet("e"))));
        assert(program.contains(new Rule(new AtomSet("e"), AtomSet.Empty,    new AtomSet("d"))));
        assertFalse(program.contains(new Rule(new AtomSet("x"), new AtomSet("y"), new AtomSet("z"))));
    }

    @Test
    public void addTest() throws Exception
    {
        assertFalse(program.contains(new Rule(new AtomSet("x"), new AtomSet("y"), new AtomSet("z"))));
        program.add(new Rule(new AtomSet("x"), new AtomSet("y"), new AtomSet("z")));
        assert(program.contains(new Rule(new AtomSet("x"), new AtomSet("y"), new AtomSet("z"))));
    }

    @Test
    public void removeTest() throws Exception
    {
        assert(program.contains(new Rule(new AtomSet("a"), new AtomSet("c"), AtomSet.Empty)));
        program.remove(new Rule(new AtomSet("a"), new AtomSet("c"), AtomSet.Empty));
        assertFalse(program.contains(new Rule(new AtomSet("a"), new AtomSet("c"), AtomSet.Empty)));

    }

    @Test
    public void iteratorTest() throws Exception
    {
        Iterator<Rule> it = program.iterator();
        assert(it.hasNext());
        assert(it.next().equals(new Rule(new AtomSet("a"), new AtomSet("b"), new AtomSet("e"))));
        assert(it.hasNext());
        assert(it.next().equals(new Rule(new AtomSet("b"), new AtomSet("a"), new AtomSet("e"))));
        assert(it.hasNext());
        /* TODO: finish test
        assert(it.next().equals(new Rule(new AtomSet("c"), AtomSet.Empty,    new AtomSet("d"))));
        assert(it.hasNext());
        assert(it.next().equals(new Rule(new AtomSet("a"), new AtomSet("c"), AtomSet.Empty)));
        assert(it.hasNext());
        assert(it.next().equals(new Rule(new AtomSet("d"), AtomSet.Empty,    new AtomSet("c"))));
        assert(it.hasNext());
        assert(it.next().equals(new Rule(new AtomSet("e"), AtomSet.Empty,    new AtomSet("d"))));
        assertFalse(it.hasNext());
        */
    }

    @Test
    public void streamTest() throws Exception
    {
        assertEquals(program.stream().count(), 6);
        //assert(set.stream().allMatch(d -> d.equals(def1))); TODO: make test
    }

    @Test
    public void getAtomsTest() throws Exception
    {
        assert(program.getAtoms().equals(new AtomSet("a", "c", "d", "b", "e")));
    }

    @Test
    public void isConcludedByTest() throws Exception
    {
        assert(program.isConcludedBy(interpretation));
        assertFalse(program.isConcludedBy(new AtomSet()));
        assertFalse(program.isConcludedBy(new AtomSet("a", "b")));
    }

    @Test
    public void getReductTest() throws Exception
    {
        assertEquals(program.getReduct(interpretation).toString(), "{c←, a←c, e←}");
    }

    @Test
    public void hashCodeTest() throws Exception
    {
        assertEquals(program.hashCode(), program.clone().hashCode());
    }
}