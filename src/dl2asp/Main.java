package dl2asp;

import dl2asp.AnswerSetProgram.Atom;
import dl2asp.AnswerSetProgram.AtomSet;
import dl2asp.AnswerSetProgram.Program;
import dl2asp.AnswerSetProgram.Solver;
import dl2asp.DefaultLogic.*;

import java.util.HashSet;
import java.util.stream.Collectors;

public class Main
{
    public static void main(String[] args)
    {
        /*
        FormulaSet facts = new FormulaSet("g", "a");
        DefaultSet defaults = new DefaultSet();
        defaults.add(new Default(new Formula("g"), new FormulaSet("!l"), new Formula("!l")));
        defaults.add(new Default(new Formula("a"), new FormulaSet("l"), new Formula("l")));
        */
        //*
        FormulaSet facts = new FormulaSet();
        DefaultSet defaults = new DefaultSet();
        defaults.add(new Default(new FormulaSet("p"), new Formula("!q")));
        defaults.add(new Default(new FormulaSet("q"), new Formula("r")));
        //*/
        /*
        FormulaSet facts = new FormulaSet("!b|!c", "c|d");
        DefaultSet defaults = new DefaultSet();
        defaults.add(new Default(                   new FormulaSet("!b"),       new Formula("a")));
        defaults.add(new Default(                   new FormulaSet("!a", "!c"), new Formula("b")));
        defaults.add(new Default(                   new FormulaSet("a&!b"),  new Formula("!d")));
        defaults.add(new Default(new Formula("!c"), new FormulaSet("!a"),       new Formula("!a")));
        */
        DefaultTheory defaultTheory = new DefaultTheory(facts, defaults);
        System.out.println("Default theory: " + defaultTheory);
        Program program = DL2ASPTranslator.translateTheoryToProgram(defaultTheory);
        System.out.println("Program: " + program);
        System.out.println();
        HashSet<AtomSet> answerSets = Solver.solve(program);
        answerSets.stream().forEach(a -> System.out.println("Atom set " + a.toString() + "\nHas reduct " + program.getReduct(a).toString() + "\nIs Atom set: " + program.getReduct(a).isConcludedBy(a) + "\n"));
        HashSet<FormulaSet> extensions = DL2ASPTranslator.translateAnswerSetsToExtensions(answerSets);
        extensions.stream().forEach(e -> System.out.println("Is " + e.toString() + " a valid extension? " + defaultTheory.isExtendedBy(e)));



        /*
        HashSet<FormulaSet> extensions = input.generateExtensions();
        extensions.stream().forEach(e -> System.out.println("Is " + e.toString() + " a valid extension? " + input.isExtendedBy(e)));
        System.out.println();
        System.out.println("output: " + output);
        HashSet<AtomSet> answerSets = DL2ASPTranslator.translateExtensionsToAnswerSets(extensions);
        answerSets.stream().forEach(a -> System.out.println("\nAnswer set " + a.toString() + "\nHas reduct " + output.getReduct(a).toString() + "\nValid set: " + output.getReduct(a).isConcludedBy(a)));
        System.out.println();
        AtomSet i = new AtomSet("P[(!b | !c)]", "P[(c | d)]", "P[!a]", "P[b]", "P[!c]", "P[!(!b & a)]");
        System.out.println("\nAnswer set " + i.toString() + "\nHas reduct " + output.getReduct(i).toString() + "\nValid set: " + output.getReduct(i).isConcludedBy(i));
        AtomSet i2 = new AtomSet("P[(!b | !c)]", "P[(c | d)]", "P[a]", "P[!d]", "P[c]");
        System.out.println("\nAnswer set " + i2.toString() + "\nHas reduct " + output.getReduct(i2).toString() + "\nValid set: " + output.getReduct(i2).isConcludedBy(i2));
        */
    }
}
