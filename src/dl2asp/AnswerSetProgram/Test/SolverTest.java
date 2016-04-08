package dl2asp.AnswerSetProgram.Test;

import dl2asp.AnswerSetProgram.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;

import static org.junit.Assert.*;

public class SolverTest
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
    public void solveTest() throws Exception
    {
        HashSet<AtomSet> answerSets = Solver.solve(program);
        Iterator<AtomSet> it = answerSets.iterator();
        assert(it.hasNext());
        assert(it.next().equals(new AtomSet("e", "a", "c")));
        assert(it.hasNext());
        assert(it.next().equals(new AtomSet("d")));
        assertFalse(it.hasNext());
    }
}