package dl2asp.DefaultLogic;

import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class DefaultSet implements Iterable<Default>
{
    private final HashSet<Default> defaults = new HashSet<>();

    public void add(Default def)
    {
        defaults.add(def);
    }

    public void remove(Default def)
    {
        defaults.remove(def);
    }

    public boolean contains(Default def)
    {
        return defaults.contains(def);
    }

    public Iterator<Default> iterator()
    {
        return new DefaultIterator<>();
    }

    public Stream<Default> stream()
    {
        return StreamSupport.stream(this.spliterator(), false);
    }

    public FormulaSet getPrerequisites()
    {
        FormulaSet result = new FormulaSet();
        for(Default d : defaults)
        {
            result.add(d.getPrerequisite());
        }

        return result;
    }

    public FormulaSet getJustifications()
    {
        FormulaSet result = new FormulaSet();
        for(Default d : defaults)
        {
            result.addAll(d.getJustifications());
        }

        return result;
    }

    public FormulaSet getConclusions()
    {
        FormulaSet result = new FormulaSet();
        for(Default d : defaults)
        {
            result.add(d.getConclusion());
        }

        return result;
    }

    public DefaultSet clone()
    {
        DefaultSet clone = new DefaultSet();
        for(Default d : defaults)
        {
            clone.add(d.clone());
        }

        return clone;
    }

    @Override
    public String toString()
    {
        String result = "{";
        result += defaults.stream()
            .map(d -> d.toString())
            .collect(Collectors.joining(", "));
        result += "}";

        return result;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this==obj) return true;
        if (this == null) return false;
        if (!(obj instanceof DefaultSet)) return false;

        DefaultSet defaultSet = (DefaultSet) obj; //We know the object is of instance Default, so we can safely downcast here.
        if(defaults.size() != defaultSet.defaults.size()) return false;
        HashSet<Default> comparedDefaults = new HashSet<>(defaultSet.defaults);
        comparedDefaults.removeAll(defaults);

        return comparedDefaults.size() == 0;
    }

    @Override
    public int hashCode()
    {
        final int basePrime = 59;
        final int multiplicationPrime = 149;

        return basePrime + defaults.stream()
            .mapToInt(d -> multiplicationPrime * d.hashCode())
            .sum();
    }

    public class DefaultIterator<T> implements Iterator<T>
    {
        private final Iterator<T> iterator;

        public DefaultIterator() {
            this.iterator = (Iterator<T>) defaults.iterator();
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
