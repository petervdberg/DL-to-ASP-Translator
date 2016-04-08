package dl2asp.AnswerSetProgram;

import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Program implements Iterable<Rule>
{
    private final HashSet<Rule> rules = new HashSet<Rule>();

    public Program() { }

    public void add(Rule rule) {
        rules.add(rule);
    }

    public void remove(Rule rule) {
        rules.remove(rule);
    }

    public boolean contains(Rule rule)
    {
        return rules.contains(rule);
    }

    public Iterator<Rule> iterator()
    {
        return new RuleIterator<Rule>();
    }

    public Stream<Rule> stream()
    {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public AtomSet getAtoms()
    {
        AtomSet result = new AtomSet();
        for(Rule r : rules)
        {
            result.addAll(r.getAtoms());
        }

        return result;
    }

    public boolean isConcludedBy(AtomSet interpretation)
    {
        for(Rule r : rules)
        {
            if(!r.isConcludedBy(interpretation)) return false;
        }

        return true;
    }

    public Program getReduct(AtomSet interpretation)
    {
        Program reduct = new Program();
        rules.stream()
            .filter(r -> r.bodyIsDerivedBy(interpretation))
            .map(r -> r.cloneOnlyPositive())
            .forEach(r -> reduct.add(r));

        return reduct;
    }

    public Program clone()
    {
        Program clone = new Program();
        for(Rule r : rules)
        {
            clone.add(r.clone());
        }

        return clone;
    }

    @Override
    public String toString() {
        String result = "{";
        result += rules.stream()
            .map(r -> r.toString())
            .collect(Collectors.joining(", "));
        result += "}";

        return result;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this==obj) return true;
        if (this == null) return false;
        if (!(obj instanceof Program)) return false;

        Program program = (Program) obj; //We know the object is of instance dl2asp.AnswerSetProgram.Program, so we can safely downcast here.
        if(rules.size() != program.rules.size()) return false;
        HashSet<Rule> tempRules = new HashSet<Rule>(program.rules);
        tempRules.removeAll(rules);

        return tempRules.size() == 0;
    }

    @Override
    public int hashCode()
    {
        final int basePrime = 419;
        final int multiplicationPrime = 947;

        return basePrime + rules.stream()
            .mapToInt(r -> multiplicationPrime * r.hashCode())
            .sum();
    }

    public class RuleIterator <T> implements Iterator<T>
    {
        private final Iterator<T> iterator;

        public RuleIterator()
        {
            this.iterator = (Iterator<T>) rules.iterator();
        }

        public boolean hasNext()
        {

            return iterator.hasNext();
        }

        public T next()
        {
            return iterator.next();
        }

        public void remove()
        {
            iterator.remove();
        }
    }
}
