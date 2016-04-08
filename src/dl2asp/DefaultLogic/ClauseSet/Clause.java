package dl2asp.DefaultLogic.ClauseSet;

import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.*;

public class Clause implements Iterable<Literal>
{
    private final HashSet<Literal> literals = new HashSet<>();
    public static final Clause EMPTY =  new Clause();

    public Clause()
    {

    }

    public Clause(String... elements)
    {
        for(String literalString : elements)
        {
            Literal literal = new Literal(literalString.trim());
            literals.add(literal);
        }
    }

    public Clause(Literal... elements)
    {
        for(Literal literal : elements)
        {
            literals.add(literal);
        }
    }

    public boolean contains(Literal literal)
    {
        return literals.contains(literal);
    }

    public int size()
    {
        return literals.size();
    }

    public Iterator<Literal> iterator()
    {
        return new LiteralIterator<Literal>();
    }

    public Stream<Literal> stream()
    {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public Clause clone()
    {
        Clause clone = new Clause();
        for(Literal l : literals)
        {
            clone.literals.add(l.clone());
        }

        return clone;
    }

    @Override
    public String toString()
    {
        String result = "{";
        result += literals.stream()
            .map(a -> a.toString())
            .collect(Collectors.joining(","));
        result += "}";

        return result;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this==obj) return true;
        if (this == null) return false;
        if (!(obj instanceof Clause)) return false;

        Clause clause = (Clause) obj; //We know the object is of instance Clause, so we can safely downcast here.
        if(literals.size() != clause.literals.size()) return false;
        HashSet<Literal> comparedLiterals = new HashSet<>(clause.literals);
        comparedLiterals.removeAll(literals);

        return comparedLiterals.size() == 0;
    }

    @Override
    public int hashCode()
    {
        final int basePrime = 541;
        final int multiplicationPrime = 673;

        return basePrime + literals.stream()
            .mapToInt(l -> multiplicationPrime * l.hashCode())
            .sum();
    }

    public class LiteralIterator<T> implements Iterator<T>
    {
        private final Iterator<T> iterator;

        public LiteralIterator() {
            this.iterator = (Iterator<T>) literals.iterator();
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
