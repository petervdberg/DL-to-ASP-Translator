package dl2asp;

import dl2asp.AnswerSetProgram.Atom;
import dl2asp.AnswerSetProgram.AtomSet;
import dl2asp.AnswerSetProgram.Program;
import dl2asp.AnswerSetProgram.Rule;
import dl2asp.DefaultLogic.Default;
import dl2asp.DefaultLogic.DefaultTheory;
import dl2asp.DefaultLogic.Formula;
import dl2asp.DefaultLogic.FormulaSet;

import java.util.HashSet;
import java.util.stream.Collectors;

public class DL2ASPTranslator
{
    public static HashSet<AtomSet> translateExtensionsToAnswerSets(HashSet<FormulaSet> extensions)
    {
        return extensions.stream()
            .map(e -> translateFormulaSetToAtomSet(e))
            .collect(Collectors.toCollection(HashSet<AtomSet>::new));
    }

    public static HashSet<FormulaSet> translateAnswerSetsToExtensions(HashSet<AtomSet> answerSets)
    {
        return answerSets.stream()
            .map(e -> translateAtomsSetToAnswerSet(e))
            .collect(Collectors.toCollection(HashSet<FormulaSet>::new));
    }

    public static FormulaSet translateAtomsSetToAnswerSet(AtomSet atoms)
    {
        return new FormulaSet(atoms.stream()
            .map(f -> toFormula(f.toString()))
            .toArray(String[]::new));
    }

    public static AtomSet translateFormulaSetToAtomSet(FormulaSet formulas)
    {
        return new AtomSet(formulas.stream()
            .map(f -> toAtom(f.toString()))
            .toArray(String[]::new));
    }

    public static Program translateTheoryToProgram(DefaultTheory input)
    {
        Program result = new Program();
        for (Formula f : input.getFacts())
        {
            AtomSet head = new AtomSet(toAtom(f.toString()));
            AtomSet positive = new AtomSet();
            AtomSet negative = new AtomSet();
            result.add(new Rule(head, positive, negative));
        }

        for (Default d : input.getDefaults())
        {
            AtomSet head = new AtomSet(toAtom(d.getConclusion().toString()));
            AtomSet positive = d.getPrerequisite().isTautology() ? AtomSet.Empty : new AtomSet(toAtom(d.getPrerequisite().toString()));
            AtomSet negative = new AtomSet(d.getJustifications().negateAll().stream().map(j -> toAtom(j.toString())).toArray(String[]::new));
            result.add(new Rule(head, positive, negative));
        }

        createImplicationRules(input).stream().forEach(r -> result.add(r));

        return result;
    }

    private static HashSet<Rule> createImplicationRules(DefaultTheory input)
    {
        HashSet<Rule> implicationRules = new HashSet<>();

        FormulaSet facts = new FormulaSet(input.getFacts());
        FormulaSet conclusions = new FormulaSet();
        input.getDefaults().stream().forEach(c -> conclusions.add(c.getConclusion()));
        implicationRules.addAll(getConstraints(conclusions, facts));

        FormulaSet prereqsAndNegJusts = new FormulaSet();
        input.getDefaults().stream().filter(d -> !d.getPrerequisite().equals(Formula.TAUTOLOGY)).forEach(d -> prereqsAndNegJusts.add(d.getPrerequisite()));
        input.getDefaults().stream().forEach(d -> d.getJustifications().negateAll().stream().forEach(j -> prereqsAndNegJusts.add(j)));
        implicationRules.addAll(getImplicationRules(conclusions, prereqsAndNegJusts, facts));

        return implicationRules;
    }

    private static HashSet<Rule> getConstraints(FormulaSet conclusions, FormulaSet facts)
    {
        HashSet<Rule> result = new HashSet<>();
        HashSet<FormulaSet> MUSes = SetUtilities.getMinimalUnsatisfiableSets(conclusions, facts);
        MUSes.stream()
            .forEach(mus ->
            {
                AtomSet head = new AtomSet(Formula.FALSUM.toString());
                AtomSet positive = new AtomSet(mus.stream().map(f -> toAtom(f.toString())).toArray(String[]::new));
                AtomSet negative = AtomSet.Empty;
                result.add(new Rule(head, positive, negative));
            });

        return result;
    }

    private static HashSet<Rule> getImplicationRules(FormulaSet conclusions, FormulaSet prereqsAndNegJusts, FormulaSet facts)
    {
        HashSet<Rule> result = new HashSet<>();
        for (Formula formula : prereqsAndNegJusts)
        {
            FormulaSet conclusionsClone = new FormulaSet(conclusions);
            conclusionsClone.remove(formula);
            FormulaSet prereqsAndNegJustsClone = new FormulaSet(facts);
            prereqsAndNegJustsClone.add(formula.negate());
            HashSet<FormulaSet> MUSes = SetUtilities.getMinimalUnsatisfiableSets(conclusionsClone, prereqsAndNegJustsClone);
            MUSes.stream()
                .filter(mus ->
                {
                    FormulaSet musClone = new FormulaSet(mus);
                    musClone.addAll(facts);
                    return musClone.isSatisfiable();
                })
                .filter(mus -> !mus.iterator().next().equals(formula) || mus.size() > 1)
                .forEach(mus ->
                {
                    AtomSet head = new AtomSet(toAtom(formula.toString()));
                    AtomSet positive = new AtomSet(mus.stream().map(f -> toAtom(f.toString())).toArray(String[]::new));
                    AtomSet negative = AtomSet.Empty;
                    result.add(new Rule(head, positive, negative));
                });
        }

        return result;
    }

    public static String toAtom(String formula)
    {
        return "P[" + formula + "]";
    }

    public static String toFormula(String atom)
    {
        return atom.substring(2, atom.length() - 1);
    }
}