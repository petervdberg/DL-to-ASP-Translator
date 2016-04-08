package dl2asp.DefaultLogic.Test;

import dl2asp.DefaultLogic.Default;
import dl2asp.DefaultLogic.Formula;
import dl2asp.DefaultLogic.FormulaSet;
import org.junit.Before;

import static org.junit.Assert.*;

public class DefaultTest
{
    private Formula prerequisite;
    private FormulaSet justifications;
    private Formula conclusion;
    private Default def;

    @Before
    public void setUp() throws Exception
    {
        prerequisite = new Formula("a");
        justifications = new FormulaSet("b", "c");
        conclusion = new Formula("d");
        def = new Default(prerequisite, justifications, conclusion);
    }

    @org.junit.Test
    public void toStringTest() throws Exception
    {
        assertEquals(def.toString(), "a:b,c/d");
    }

    @org.junit.Test
    public void cloneTest() throws Exception
    {
        Default clone = def.clone();
        assertEquals(clone.toString(), def.toString());
    }

    @org.junit.Test
    public void equalsTest() throws Exception
    {
        Default clone = def.clone();

        assert(clone.equals(def));

    }

    @org.junit.Test
    public void hashCodeTest() throws Exception
    {
        Default clone = def.clone();

        assertEquals(clone.hashCode(), def.hashCode());
    }

    @org.junit.Test
    public void canBeAppliedToTest() throws Exception
    {
        assertFalse(def.canBeAppliedTo(new FormulaSet()));
        assertFalse(def.canBeAppliedTo(new FormulaSet("!b")));
        assertFalse(def.canBeAppliedTo(new FormulaSet("!c")));
        assertFalse(def.canBeAppliedTo(new FormulaSet("!b", "!c")));
        assertFalse(def.canBeAppliedTo(new FormulaSet("a", "!b")));
        assertFalse(def.canBeAppliedTo(new FormulaSet("a", "!c")));
        assertFalse(def.canBeAppliedTo(new FormulaSet("a", "!b", "!c")));
        assertFalse(def.canBeAppliedTo(new FormulaSet("!b", "d")));
        assertFalse(def.canBeAppliedTo(new FormulaSet("!c", "d")));
        assertFalse(def.canBeAppliedTo(new FormulaSet("!b", "!c", "d")));
        assertFalse(def.canBeAppliedTo(new FormulaSet("a", "!b", "d")));
        assertFalse(def.canBeAppliedTo(new FormulaSet("a", "!c", "d")));
        assertFalse(def.canBeAppliedTo(new FormulaSet("a", "!b", "!c", "d")));
        assert(def.canBeAppliedTo(new FormulaSet("a")));
        assert(def.canBeAppliedTo(new FormulaSet("a", "d")));
    }

    @org.junit.Test
    public void isConsistentWithTest() throws Exception
    {
        assert(def.isConsistentWith(new FormulaSet()));
        assert(def.isConsistentWith(new FormulaSet("!b")));
        assert(def.isConsistentWith(new FormulaSet("!c")));
        assert(def.isConsistentWith(new FormulaSet("!b", "!c")));
        assert(def.isConsistentWith(new FormulaSet("a", "!b")));
        assert(def.isConsistentWith(new FormulaSet("a", "!c")));
        assert(def.isConsistentWith(new FormulaSet("a", "!b", "!c")));
        assert(def.isConsistentWith(new FormulaSet("!b", "d")));
        assert(def.isConsistentWith(new FormulaSet("!c", "d")));
        assert(def.isConsistentWith(new FormulaSet("!b", "!c", "d")));
        assert(def.isConsistentWith(new FormulaSet("a", "!b", "d")));
        assert(def.isConsistentWith(new FormulaSet("a", "!c", "d")));
        assert(def.isConsistentWith(new FormulaSet("a", "!b", "!c", "d")));
        assert(def.isConsistentWith(new FormulaSet("a", "d")));
        assertFalse(def.isConsistentWith(new FormulaSet("a")));
    }

    @org.junit.Test
    public void applyTest() throws Exception
    {
        assertFalse(def.apply(new FormulaSet(), new FormulaSet()));
        assertFalse(def.apply(new FormulaSet("!b"), new FormulaSet()));
        assertFalse(def.apply(new FormulaSet("!c"), new FormulaSet()));
        assertFalse(def.apply(new FormulaSet("!b", "!c"), new FormulaSet()));
        assertFalse(def.apply(new FormulaSet("a", "!b"), new FormulaSet()));
        assertFalse(def.apply(new FormulaSet("a", "!c"), new FormulaSet()));
        assertFalse(def.apply(new FormulaSet("a", "!b", "!c"), new FormulaSet()));
        assertFalse(def.apply(new FormulaSet("!b", "d"), new FormulaSet()));
        assertFalse(def.apply(new FormulaSet("!c", "d"), new FormulaSet()));
        assertFalse(def.apply(new FormulaSet("!b", "!c", "d"), new FormulaSet()));
        assertFalse(def.apply(new FormulaSet("a", "!b", "d"), new FormulaSet()));
        assertFalse(def.apply(new FormulaSet("a", "!c", "d"), new FormulaSet()));
        assertFalse(def.apply(new FormulaSet("a", "!b", "!c", "d"), new FormulaSet()));
        assertFalse(def.apply(new FormulaSet("a"), new FormulaSet("d")));
        assert(def.apply(new FormulaSet("a"), new FormulaSet()));
    }
}