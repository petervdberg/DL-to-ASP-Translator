package dl2asp.DefaultLogic;

import java.util.stream.Collectors;

public class Default
{
    private final Formula prerequisite;
    private final FormulaSet justifications;
    private final Formula conclusion;

    public Default(FormulaSet justifications, Formula conclusion)
    {
        this(Formula.TAUTOLOGY, justifications, conclusion);
    }

    public Formula getPrerequisite()
    {
        return prerequisite;
    }

    public FormulaSet getJustifications()
    {
        return justifications;
    }

    public Formula getConclusion()
    {
        return conclusion;
    }

    public Default(Formula prerequisite, FormulaSet justifications, Formula conclusion)
    {
        this.prerequisite = prerequisite;
        this.justifications = justifications;
        this.conclusion = conclusion;
    }

    public Default clone()
    {
        return new Default(prerequisite.clone(), justifications.clone(), conclusion.clone());
    }

    public boolean isConsistentWith(FormulaSet theory)
    {
        return !canBeAppliedTo(theory) || theory.concludes(conclusion);
    }

    public boolean canBeAppliedTo(FormulaSet theory)
    {
        return theory.concludes(prerequisite) && !theory.concludesOneOf(justifications.negateAll());
    }

    public boolean apply(FormulaSet in, FormulaSet out)
    {
        if(!canBeAppliedTo(in)) return false;

        in.add(conclusion);
        out.addAll(justifications.negateAll());

        return !in.concludesOneOf(out);
    }

    @Override
    public String toString() {
        String result = (prerequisite.isTautology() ? "" : prerequisite) + ":";
        result += justifications.stream()
            .map(f -> f.toString())
            .collect(Collectors.joining(","));
        if(justifications.size() > 0)
        {
            result += "/";
        }
        result += conclusion;

        return result;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this==obj) return true;
        if (this == null) return false;
        if (!(obj instanceof Default)) return false;

        Default otherDefault = (Default) obj; //We know the object is of instance Default, so we can safely downcast here.
        if(!prerequisite.equals(otherDefault.prerequisite)) return false;
        if(!justifications.equals(otherDefault.justifications)) return false;
        if(!conclusion.equals(otherDefault.conclusion)) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        final int basePrime = 977;
        final int multiplicationPrime = 419;

        return basePrime + multiplicationPrime * prerequisite.hashCode()
            + multiplicationPrime * justifications.hashCode()
            + multiplicationPrime * conclusion.hashCode();
    }
}
