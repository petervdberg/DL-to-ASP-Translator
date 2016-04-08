package dl2asp.AnswerSetProgram.Test;

import dl2asp.AnswerSetProgram.AtomSet;
import dl2asp.AnswerSetProgram.Rule;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RuleTest
{
    private Rule rule;
    private Rule rule2;

    @Before
    public void setUp() throws Exception
    {
        rule = new Rule(new AtomSet("a"), new AtomSet("b"), new AtomSet("c"));
    }

    @Test
    public void toStringTest() throws Exception
    {
        assertEquals(rule.toString(), "a←b,not c");
    }

    @Test
    public void equalsTest() throws Exception
    {
        Rule rule2 = new Rule(new AtomSet("x"), new AtomSet("y"), new AtomSet("z"));
        assert(rule.equals(new Rule(new AtomSet("a"), new AtomSet("b"), new AtomSet("c"))));
        assertFalse(rule.equals(rule2));
    }

    @Test
    public void cloneTest() throws Exception
    {
        assert(rule.equals(rule.clone()));
    }

    @Test
    public void cloneOnlyPositiveTest() throws Exception
    {
        assertFalse(rule.equals(rule.cloneOnlyPositive()));
        assert(rule.cloneOnlyPositive().equals(new Rule(new AtomSet("a"), new AtomSet("b"), AtomSet.Empty)));

    }

    @Test
    public void getAtomsTest() throws Exception
    {
        assert(rule.getAtoms().equals(new AtomSet("a", "b", "c")));
    }

    @Test
    public void isConcludedByTest() throws Exception
    {
        assert(rule.isConcludedBy(new AtomSet("a", "b")));
        assert(rule.isConcludedBy(new AtomSet()));
        assert(rule.isConcludedBy(new AtomSet("a", "b", "!c")));
        assertFalse(rule.isConcludedBy(new AtomSet("b", "!c")));
        assertFalse(rule.isConcludedBy(new AtomSet("b")));
    }

    @Test
    public void bodyIsDerivedByTest() throws Exception
    {
        assert(rule.bodyIsDerivedBy(new AtomSet("b")));
        assert(rule.bodyIsDerivedBy(new AtomSet("b", "!c")));
        assertFalse(rule.bodyIsDerivedBy(new AtomSet("b", "c")));
        assertFalse(rule.bodyIsDerivedBy(new AtomSet("!c")));
        assertFalse(rule.bodyIsDerivedBy(new AtomSet("c")));
    }

    @Test
    public void hashCodeTest() throws Exception
    {
        assertEquals(rule.hashCode(), rule.clone().hashCode());
    }
}