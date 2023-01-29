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
        FormulaSet facts = new FormulaSet();
        DefaultSet defaults = new DefaultSet();
        defaults.add(new Default(new FormulaSet("p"), new Formula("!q")));
        defaults.add(new Default(new FormulaSet("q"), new Formula("r")));
        DefaultTheory defaultTheory = new DefaultTheory(facts, defaults);
        System.out.println("Default theory: " + defaultTheory);
        Program program = DL2ASPTranslator.translateTheoryToProgram(defaultTheory);
        System.out.println("Program: " + program);
        System.out.println();
        HashSet<AtomSet> answerSets = Solver.solve(program);
        answerSets.stream().forEach(a -> System.out.println("Atom set " + a.toString() + "\nHas reduct " + program.getReduct(a).toString() + "\nIs Atom set: " + program.getReduct(a).isConcludedBy(a) + "\n"));
        HashSet<FormulaSet> extensions = DL2ASPTranslator.translateAnswerSetsToExtensions(answerSets);
        extensions.stream().forEach(e -> System.out.println("Is " + e.toString() + " a valid extension? " + defaultTheory.isExtendedBy(e)));
    }
}
