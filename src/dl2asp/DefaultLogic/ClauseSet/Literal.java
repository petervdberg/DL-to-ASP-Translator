package dl2asp.DefaultLogic.ClauseSet;

public class Literal
{
    private final String name;
    private final boolean positive;
    public static final Literal FALSUM = new Literal("false");
    public static final Literal TAUTOLOGY = new Literal("true");

    public Literal(String name, boolean positive)
    {
        this.name = name;
        this.positive = positive;
    }

    public Literal(String name)
    {
        if(name == "false")
        {
            positive = false;
            this.name = name;
        }
        else if(name == "true")
        {
            positive = false;
            this.name = name;
        }
        else
        {
            this.positive = name.charAt(0) != '!';
            if (positive)
            {
                this.name = name;
            } else
            {
                this.name = name.substring(1);
            }
        }
    }

    public boolean isFalsum()
    {
        return equals(Literal.FALSUM);
    }

    public boolean isTautology()
    {
        return equals(Literal.TAUTOLOGY);
    }

    public Literal negate()
    {
        return new Literal(name, !positive);
    }

    public Literal clone()
    {
        return new Literal(name, positive);
    }

    @Override
    public String toString()
    {
        return (positive || isFalsum() ? "" : "!") + name;
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this==obj) return true;
        if (this == null) return false;
        if (!(obj instanceof Literal)) return false;

        Literal literal = (Literal) obj; //We know the object is of instance Literal, so we can safely downcast here.
        boolean result = positive == literal.positive;
        if(name != null)
        {
            result &= name.equals(literal.name);
        }

        return result;
    }

    @Override
    public int hashCode()
    {
        final int basePrime = 673;
        final int multiplicationPrime = 463;
        final int valuationFalsePrime = 509;
        final int valuationTruePrime = 163;
        int valuationHashCode = (positive ? valuationTruePrime : valuationFalsePrime);
        int result = basePrime + multiplicationPrime * valuationHashCode;
        if(name != null)
        {
            result += multiplicationPrime * name.hashCode();
        }

        return result;
    }
}
