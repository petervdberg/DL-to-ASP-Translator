package dl2asp.DefaultLogic.Test;

import dl2asp.DefaultLogic.*;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.*;

public class DefaultTheoryTest
{
    FormulaSet facts;
    DefaultSet defaults;
    DefaultTheory defaultTheory;

    @Before
    public void setUp() throws Exception
    {
        facts = new FormulaSet("!b|!c", "c|d");
        defaults = new DefaultSet();
        defaultTheory = new DefaultTheory(facts, defaults);
        defaults.add(new Default(                   new FormulaSet("!b"),       new Formula("a")));
        defaults.add(new Default(                   new FormulaSet("!a", "!c"), new Formula("b")));
        defaults.add(new Default(                   new FormulaSet("a&!b"),     new Formula("!d")));
        defaults.add(new Default(new Formula("!c"), new FormulaSet("!a"),       new Formula("!a")));
    }

    @Test
    public void toStringTest() throws Exception
    {
        assertEquals(defaultTheory.toString(), "<{(!b | !c), (c | d)}, {:!b/a, !c:!a/!a, :(!b & a)/!d, :!a,!c/b}>");
        assertNotEquals(defaultTheory.toString(), "{(!b | !c), (c | d)}, {:!b/a, !c:!a/!a, :(!b & a)/!d, :!a,!c/b}");
        assertNotEquals(defaultTheory.toString(), "{:!b/a, !c:!a/!a, :(!b & a)/!d, :!a,!c/b}");
        assertNotEquals(defaultTheory.toString(), "<{(!b | !c),(c | d)},{:!b/a,!c:!a/!a,:(!b & a)/!d,:!a,!c/b}>");
        assertNotEquals(defaultTheory.toString(), "<{(!b | !c), (c | d)}, {:!b/a, :!a,!c/b, :(a & !b)/!d, !c:!a/!a}>");
    }

    @org.junit.Test
    public void cloneTest() throws Exception
    {
        DefaultTheory clone = defaultTheory.clone();
        assertEquals(clone.toString(), defaultTheory.toString());
    }

    @Test
    public void equalsTest() throws Exception
    {
        DefaultTheory clone = defaultTheory.clone();
        assert(clone.equals(defaultTheory));
    }

    @Test
    public void isExtendedByTest() throws Exception
    {
        FormulaSet theory = facts.clone();
        theory.add(new Formula("a"));
        theory.add(new Formula("!d"));
        assert(defaultTheory.isExtendedBy(theory));

        theory = facts.clone();
        theory.add(new Formula("!a"));
        theory.add(new Formula("b"));
        assert(defaultTheory.isExtendedBy(theory));

        theory = facts.clone();
        theory.add(new Formula("a"));
        assertFalse(defaultTheory.isExtendedBy(theory));

        theory = facts.clone();
        theory.add(new Formula("b"));
        assertFalse(defaultTheory.isExtendedBy(theory));

        theory = facts.clone();
        theory.add(new Formula("d"));
        assertFalse(defaultTheory.isExtendedBy(theory));
    }

    @Test
    public void generateExtensionsTest() throws Exception
    {
        HashSet<FormulaSet> extensions = defaultTheory.generateExtensions();
        FormulaSet[] extensionsArray = extensions.toArray(new FormulaSet[extensions.size()]);
        assertEquals(extensions.size(), 2);

        assertEquals(extensionsArray[0].toString(), "{a, !d, (!b | !c), (c | d)}");
        assertEquals(extensionsArray[1].toString(), "{!a, b, (!b | !c), (c | d)}");

    }

    @Test
    public void hashCodeTest() throws Exception
    {
        DefaultTheory clone = defaultTheory.clone();
        assertEquals(clone.hashCode(), defaultTheory.hashCode());
    }
}