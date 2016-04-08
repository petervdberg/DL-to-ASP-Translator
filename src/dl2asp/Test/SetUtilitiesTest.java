package dl2asp.Test;

import dl2asp.DefaultLogic.Formula;
import dl2asp.DefaultLogic.FormulaSet;
import dl2asp.SetUtilities;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SetUtilitiesTest
{
    FormulaSet inputSet;
    FormulaSet fixedSet;

    @Before
    public void setUp() throws Exception
    {
        fixedSet = new FormulaSet("a");
        inputSet = new FormulaSet("!a|!b", "b", "c");
    }

    @Test
    public void getMinimalUnsatisfiableSetsTest() throws Exception
    {
        FormulaSet minimalSet = new FormulaSet("!a|!b", "b");
        HashSet<FormulaSet> result = SetUtilities.getMinimalUnsatisfiableSets(inputSet, fixedSet);
        assertEquals(result.size(), 1);
        assertFalse(result.stream().findFirst().get().equals(inputSet));
        assert(result.stream().findFirst().get().equals(minimalSet));
    }
}