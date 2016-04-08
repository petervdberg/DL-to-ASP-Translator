package dl2asp.AnswerSetProgram;

public class Atom
{
    private final String name;

    public Atom(String name)
    {
        this.name = name;
    }

    public Atom clone()
    {
        return new Atom(name);
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this==obj) return true;
        if (this == null) return false;
        if (!(obj instanceof Atom)) return false;

        Atom atom = (Atom) obj; //We know the object is of instance dl2asp.AnswerSetProgram.Atom, so we can safely downcast here.

        return atom.name.equals(name);
    }

    @Override
    public int hashCode()
    {
        final int basePrime = 347;
        final int multiplicationPrime = 107;

        return basePrime + multiplicationPrime * name.hashCode();
    }
}
