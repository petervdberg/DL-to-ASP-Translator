package dl2asp.DefaultLogic;

import dl2asp.DefaultLogic.ClauseSet.ClauseSet;
import com.bpodgursky.jbool_expressions.And;
import com.bpodgursky.jbool_expressions.Expression;
import com.bpodgursky.jbool_expressions.Not;
import com.bpodgursky.jbool_expressions.parsers.ExprParser;
import com.bpodgursky.jbool_expressions.rules.RuleSet;

public class Formula
{
    private final Expression expression;
    public static final Formula FALSUM = new Formula("false");
    public static final Formula TAUTOLOGY = new Formula("true");

    public Formula(String formulaString)
    {
        expression = ExprParser.parse(formulaString);
    }

    public Formula toCNF()
    {
        return new Formula(RuleSet.toCNF(expression).toString());
    }

    public boolean isSatisfiable()
    {
        ClauseSet clauses = ClauseSet.fromCNFString(RuleSet.toCNF(expression).toString());
        if(clauses.stream().flatMap(c -> c.stream()).allMatch(l -> l.isFalsum())) return false;
        if(clauses.stream().flatMap(c -> c.stream()).allMatch(l -> l.isTautology())) return true;

        return clauses.linearResolution();
    }

    public boolean concludes(Formula formula)
    {
        if(formula.equals(Formula.TAUTOLOGY)) return true;
        if(formula.equals(Formula.FALSUM)) return false;
        if(expression.equals(formula.expression)) return true;

        ClauseSet clauses = ClauseSet.fromCNFString(RuleSet.toCNF(And.of(expression, formula.negate().expression)).toString());

        if(clauses.stream().flatMap(c -> c.stream()).allMatch(l -> l.isFalsum())) return true;
        if(clauses.stream().flatMap(c -> c.stream()).allMatch(l -> l.isTautology())) return false;

        return !clauses.linearResolution();
    }

    public Formula conjunct(Formula formula)
    {
        return new Formula(And.of(expression, formula.expression).toString());
    }

    public Formula simplify()
    {
        return new Formula(RuleSet.simplify(expression).toString());
    }

    public Formula negate()
    {
        return new Formula(RuleSet.simplify(Not.of(expression)).toString());
    }

    public Formula clone()
    {
        return new Formula(expression.toString());
    }

    public boolean isFalsum()
    {
        return equals(Formula.FALSUM);
    }

    public boolean isTautology()
    {
        return equals(Formula.TAUTOLOGY);
    }

    @Override
    public String toString()
    {
        return expression.toString();
    }

    @Override
    public boolean equals (Object obj)
    {
        if (this==obj) return true;
        if (this == null) return false;
        if (!(obj instanceof Formula)) return false;

        Formula formula = (Formula) obj; //We know the object is of instance Formula, so we can safely downcast here.

        return expression.equals(formula.expression);
    }

    @Override
    public int hashCode()
    {
        return expression.hashCode();
    }
}
