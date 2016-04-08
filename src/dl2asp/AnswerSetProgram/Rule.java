package dl2asp.AnswerSetProgram;

import java.util.stream.Collectors;

public class Rule
{
    private final AtomSet head;
    private final AtomSet positive;
    private final AtomSet negative;

    public Rule(AtomSet head, AtomSet positive, AtomSet negative)
    {
        this.head = head;
        this.positive = positive;
        this.negative = negative;
    }

    public Rule clone()
    {
        return new Rule(head.clone(), positive.clone(), negative.clone());
    }

    public Rule cloneOnlyPositive()
    {
        return new Rule(head.clone(), positive.clone(), AtomSet.Empty);
    }

    public AtomSet getAtoms()
    {
        AtomSet result = new AtomSet();
        for(Atom a : head)
        {
            //TODO: remove ugly check with something more pretty
            if(a.toString() != "false")
            {
                result.add(a);
            }
        }
        result.addAll(positive);
        result.addAll(negative);

        return result;
    }

    public boolean isConcludedBy(AtomSet interpretation)
    {
        if(bodyIsDerivedBy(interpretation))
        {
            return !head.isInComplementOf(interpretation);
        }
        else
        {
            return true;
        }
    }

    public boolean bodyIsDerivedBy(AtomSet interpretation)
    {
        return positive.isSubSetOf(interpretation) && negative.isInComplementOf(interpretation);
    }

    @Override
    public String toString() {
        String result = "";
        result += head.stream()
            .map(l -> l.toString())
            .collect(Collectors.joining(","));
        result += "←";
        result += positive.stream()
            .map(l -> l.toString())
            .collect(Collectors.joining(","));
        if(positive.size() > 0 && negative.size() > 0)
        {
            result += ",";
        }
        result += negative.stream()
            .map(l -> "not " + l)
            .collect(Collectors.joining(","));

        return result;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this==obj) return true;
        if (this == null) return false;
        if (!(obj instanceof Rule)) return false;

        Rule rule = (Rule) obj; //We know the object is of instance dl2asp.AnswerSetProgram.Rule, so we can safely downcast here.
        if(!head.equals(rule.head)) return false;
        if(!positive.equals(rule.positive)) return false;
        if(!negative.equals(rule.negative)) return false;

        return true;
    }

    @Override
    public int hashCode()
    {
        final int basePrime = 991;
        final int multiplicationPrime = 37;

        return basePrime + multiplicationPrime * head.hashCode()
            + multiplicationPrime * positive.hashCode()
            + multiplicationPrime * negative.hashCode();
    }
}
