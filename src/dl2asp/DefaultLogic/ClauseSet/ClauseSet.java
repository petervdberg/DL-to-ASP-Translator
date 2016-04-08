package dl2asp.DefaultLogic.ClauseSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.*;

public class ClauseSet implements Iterable<Clause>
{
    private final HashSet<Clause> clauses = new HashSet<>();

    public ClauseSet() { }

    public static ClauseSet fromCNFString(String cnfString)
    {
        ClauseSet result = new ClauseSet();
        String[] clauseStrings = cnfString.split("&");
        for(String c : clauseStrings)
        {
            if(c == "true") continue;
            String[] literalStrings = c.split("\\|");
            String first = literalStrings[0].trim();
            while(first.charAt(0) == '(')
            {
                first = first.substring(1);
            }
            literalStrings[0] = first;

            String last = literalStrings[literalStrings.length - 1].trim();
            while(last.charAt(last.length() - 1) == ')')
            {
                last = last.substring(0, last.length() - 1);
            }
            literalStrings[literalStrings.length - 1] = last;
            result.clauses.add(new Clause(literalStrings));
        }

        return result;
    }

    public boolean contains(Clause clause)
    {
        return clauses.contains(clause);
    }

    public Iterator<Clause> iterator()
    {
        return new ClauseIterator<>();
    }

    public Stream<Clause> stream()
    {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public boolean linearResolution()
    {
        //TODO: make readable, especially tryResolveClause (this is an UGLY copy from automated reasoning code)!!!
        ClauseSet clauseSet = clone();
        for (Clause clause : clauses)
        {
            while(clause != null)
            {
                clause = clauseSet.tryResolveClause(clause);
                if(!clauseSet.stream().allMatch(c -> c.size() > 0)) return false;
            }
        }

        return true;
    }

    private Clause tryResolveClause(Clause clause1)
    {
        for (Clause clause2 : clauses)
        {
            if (clause1.equals(clause2)) continue;

            for (Literal l : clause1)
            {
                if (clause2.contains(l.negate()))
                {
                    return resolveClauses(clause1, clause2);
                }
            }
        }

        return null;
    }

    private Clause resolveClauses(Clause clause1, Clause clause2)
    {

        ArrayList<Literal> excludedLiterals = clause1.stream()
            .filter(l -> clause2.contains(l.negate()))
            .collect(Collectors.toCollection(ArrayList<Literal>::new));
        ArrayList<Literal> newLiterals = Stream.concat(
            clause1.stream()
                .filter(l -> !excludedLiterals.contains(l)),
            clause2.stream()
                .filter(l -> !excludedLiterals.contains(l.negate())))
            .collect(Collectors.toCollection(ArrayList<Literal>::new));

        Clause resolvent = new Clause(newLiterals.toArray(new Literal[newLiterals.size()]));
        clauses.remove(clause2);
        clauses.add(resolvent);

        return resolvent;
    }

    public ClauseSet clone()
    {
        ClauseSet clone = new ClauseSet();
        for(Clause c : clauses)
        {
            clone.clauses.add(c.clone());
        }

        return clone;
    }

    @Override
    public String toString() {
        String result = "{";
        result += clauses.stream()
            .map(c -> c.toString())
            .collect(Collectors.joining(","));
        result += "}";

        return result;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this==obj) return true;
        if (this == null) return false;
        if (!(obj instanceof ClauseSet)) return false;

        ClauseSet clauseSet = (ClauseSet) obj; //We know the object is of instance ClauseSet, so we can safely downcast here.
        if(clauses.size() != clauseSet.clauses.size()) return false;
        HashSet<Clause> tempClauses = new HashSet<>(clauseSet.clauses);
        tempClauses.removeAll(clauses);

        return tempClauses.size() == 0;
    }

    @Override
    public int hashCode()
    {
        final int basePrime = 773;
        final int multiplicationPrime = 349;

        return basePrime + clauses.stream()
            .mapToInt(c -> multiplicationPrime * c.hashCode())
            .sum();
    }

    public class ClauseIterator<T> implements Iterator<T>
    {
        private final Iterator<T> iterator;

        public ClauseIterator() {
            this.iterator = (Iterator<T>) clauses.iterator();
        }

        public boolean hasNext() {

            return iterator.hasNext();
        }

        public T next() {
            return iterator.next();
        }

        public void remove() {
            iterator.remove();
        }
    }
}
