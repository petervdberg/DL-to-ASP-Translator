package dl2asp.AnswerSetProgram;

import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.*;

public class AtomSet implements Iterable<Atom>
{
    private final HashSet<Atom> atoms = new HashSet<>();

    public static final AtomSet Empty = new AtomSet();

    public AtomSet() { }

    public AtomSet(String... elements)
    {
        for(String atomString : elements)
        {
            Atom atom = new Atom(atomString);
            atoms.add(atom);
        }
    }

    public void add(Atom atom)
    {
        atoms.add(atom);
    }

    public void addAll(AtomSet atoms)
    {
        for(Atom a : atoms)
        {
            add(a);
        }
    }

    public void remove(Atom atom)
    {
        atoms.remove(atom);
    }

    public boolean contains(Atom atom)
    {
        return atoms.contains(atom);
    }

    public int size()
    {
        return atoms.size();
    }

    public Iterator<Atom> iterator()
    {
        return new AtomIterator<Atom>();
    }

    public Stream<Atom> stream()
    {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public boolean isSubSetOf(AtomSet other)
    {
        for(Atom a : atoms)
        {
            if(!other.contains(a)) return false;
        }

        return true;
    }

    public boolean isInComplementOf(AtomSet other)
    {
        for(Atom a : atoms)
        {
            if(other.contains(a)) return false;
        }

        return true;
    }

    public AtomSet clone()
    {
        AtomSet clone = new AtomSet();
        for(Atom a : atoms)
        {
            clone.add(a.clone());
        }

        return clone;
    }

    @Override
    public String toString()
    {
        String result = "{";
        result += atoms.stream()
            .map(a -> a.toString())
            .collect(Collectors.joining(", "));
        result += "}";

        return result;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this==obj) return true;
        if (this == null) return false;
        if (!(obj instanceof AtomSet)) return false;

        AtomSet atomSet = (AtomSet) obj; //We know the object is of instance dl2asp.AnswerSetProgram.AtomSet, so we can safely downcast here.
        if(atoms.size() != atomSet.atoms.size()) return false;
        HashSet<Atom> comparedAtoms = new HashSet<Atom>(atomSet.atoms);
        comparedAtoms.removeAll(atoms);

        return comparedAtoms.size() == 0;
    }

    @Override
    public int hashCode()
    {
        final int basePrime = 509;
        final int multiplicationPrime = 83;

        return basePrime + atoms.stream()
            .mapToInt(a -> multiplicationPrime * a.hashCode())
            .sum();
    }

    public class AtomIterator<T> implements Iterator<T>
    {
        private final Iterator<T> iterator;

        public AtomIterator() {
            this.iterator = (Iterator<T>) atoms.iterator();
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
