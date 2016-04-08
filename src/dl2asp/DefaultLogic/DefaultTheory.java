package dl2asp.DefaultLogic;

import java.util.HashSet;
import java.util.stream.Collectors;

public class DefaultTheory
{
    private final FormulaSet facts;
    private final DefaultSet defaults;

    public DefaultTheory(FormulaSet facts, DefaultSet defaults)
    {
        this.facts = facts;
        this.defaults = defaults;
    }

    public FormulaSet getFacts()
    {
        return facts;
    }

    public DefaultSet getDefaults()
    {
        return defaults;
    }

    public boolean isExtendedBy(FormulaSet theory)
    {
        if(!facts.isSubSetOf(theory)) return false;

        for(Default d : defaults)
        {
            if(!d.isConsistentWith(theory)) return false;
        }

        return true;
    }

    public HashSet<FormulaSet> generateExtensions()
    {
        return generateExtensions(defaults.clone(), facts.clone(), new FormulaSet());
    }

    private HashSet<FormulaSet> generateExtensions(DefaultSet unappliedDefaults, FormulaSet in, FormulaSet out)
    {
        HashSet<FormulaSet> result = new HashSet<>();

        boolean applied = false;
        for (Default d : unappliedDefaults)
        {
            if (d.canBeAppliedTo(in))
            {
                FormulaSet inClone = in.clone();
                FormulaSet outClone = out.clone();
                if (d.apply(inClone, outClone))
                {
                    DefaultSet unappliedDefaultsClone = unappliedDefaults.clone();
                    unappliedDefaultsClone.remove(d);
                    result.addAll(generateExtensions(unappliedDefaultsClone, inClone, outClone));
                    applied = true;
                }
            }
        }

        if(!applied)
        {
            result.add(in);
        }

        return result;
    }

    public DefaultTheory clone()
    {
        return new DefaultTheory(facts.clone(), defaults.clone());
    }

    @Override
    public String toString()
    {
        String result = "<{";
        result += facts.stream()
            .map(d -> d.toString())
            .collect(Collectors.joining(", "));
        result += "}, {";
        result += defaults.stream()
            .map(d -> d.toString())
            .collect(Collectors.joining(", "));
        result += "}>";

        return result;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this==obj) return true;
        if (this == null) return false;
        if (!(obj instanceof DefaultTheory)) return false;

        DefaultTheory defaultTheory = (DefaultTheory) obj; //We know the object is of instance DefaultTheory, so we can safely downcast here.

        return facts.equals(defaultTheory.facts) && defaults.equals(defaultTheory.defaults);
    }

    @Override
    public int hashCode()
    {
        final int basePrime = 89;
        final int multiplicationPrime = 101;

        return basePrime
            + facts.hashCode() * multiplicationPrime
            + defaults.hashCode() * multiplicationPrime;
    }
}
