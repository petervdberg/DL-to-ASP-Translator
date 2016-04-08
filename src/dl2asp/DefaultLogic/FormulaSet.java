package dl2asp.DefaultLogic;

import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.*;

public class FormulaSet implements Iterable<Formula>
{
    private final HashSet<Formula> formulas = new HashSet<>();

    public FormulaSet() { }

    public FormulaSet(String... elements)
    {
        for(String formulaString : elements)
        {
            Formula formula = new Formula(formulaString);
            formulas.add(formula);
        }
    }

    public FormulaSet(FormulaSet elements)
    {
        for(Formula formula : elements)
        {
            formulas.add(formula);
        }
    }

    public FormulaSet(Stream<Formula> elements)
    {
        elements.forEach(f -> formulas.add(f));
    }

    public FormulaSet(HashSet<Formula> elements)
    {
        for(Formula formula : elements)
        {
            formulas.add(formula);
        }
    }

    public FormulaSet negateAll()
    {
        FormulaSet negated = new FormulaSet();
        for(Formula f : formulas)
        {
            negated.add(f.negate());
        }

        return negated;
    }

    public void remove(Formula formula)
    {
        formulas.remove(formula);
    }

    public void addAll(FormulaSet source)
    {
        source.stream().forEach(f -> add(f));
    }

    public void addAll(HashSet<Formula> source)
    {
        source.stream().forEach(f -> add(f));
    }

    public boolean concludesOneOf(FormulaSet conclusions)
    {
        for(Formula f : conclusions)
        {
            if(concludes(f)) return true;
        }

        return false;
    }

    public boolean isSatisfiable()
    {
        Formula premises = Formula.TAUTOLOGY;
        for(Formula f : formulas)
        {
            premises = premises.conjunct(f);
        }

        return premises.isSatisfiable();
    }

    public boolean concludes(FormulaSet conclusions)
    {
        if(formulas.equals(conclusions)) return true;
        Formula conclusion = Formula.TAUTOLOGY;
        for(Formula f : conclusions)
        {
            conclusion = conclusion.conjunct(f);
        }

        return concludes(conclusion);
    }

    public boolean concludes(Formula conclusion)
    {
        if(formulas.contains(conclusion)) return true;
        Formula premises = Formula.TAUTOLOGY;
        for(Formula f : formulas)
        {
            premises = premises.conjunct(f);
        }

        return premises.concludes(conclusion);
    }

    public void add(Formula formula) { formulas.add(formula); }

    public boolean contains(Formula formula)
    {
        return formulas.contains(formula);
    }

    public int size()
    {
        return formulas.size();
    }

    public Iterator<Formula> iterator() { return new FormulaIterator<>(); }

    public Stream<Formula> stream() { return StreamSupport.stream(this.spliterator(), false); }

    public boolean isSubSetOf(FormulaSet other)
    {
        for(Formula f : formulas)
        {
            if(!other.contains(f)) return false;
        }

        return true;
    }

    public FormulaSet clone()
    {
        return new FormulaSet(this);
    }

    @Override
    public String toString()
    {
        String result = "{";
        result += formulas.stream()
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
        if (!(obj instanceof FormulaSet)) return false;

        FormulaSet formulaSet = (FormulaSet) obj; //We know the object is of instance FormulaSet, so we can safely downcast here.
        if(formulas.size() != formulaSet.formulas.size()) return false;
        HashSet<Formula> comparedFormulas = new HashSet<>(formulaSet.formulas);
        comparedFormulas.removeAll(formulas);

        return comparedFormulas.size() == 0;
    }

    @Override
    public int hashCode()
    {
        final int basePrime = 503;
        final int multiplicationPrime = 23;

        return basePrime + formulas.stream()
            .mapToInt(l -> multiplicationPrime * l.hashCode())
            .sum();
    }

    public class FormulaIterator<T> implements Iterator<T>
    {
        private final Iterator<T> iterator;

        public FormulaIterator()
        {
            this.iterator = (Iterator<T>) formulas.iterator();
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
