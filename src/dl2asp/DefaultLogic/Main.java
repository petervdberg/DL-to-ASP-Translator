package dl2asp.DefaultLogic;

public class Main
{
    public static void main(String[] args)
    {
        /*FormulaSet facts = new FormulaSet();
        DefaultSet defaults = new DefaultSet();
        defaults.add(new Default(Formula.TAUTOLOGY, new FormulaSet("p"), new Formula("!q")));
        defaults.add(new Default(Formula.TAUTOLOGY, new FormulaSet("q"), new Formula("r")));
        DefaultTheory defaultTheory = new DefaultTheory(facts, defaults);
        System.out.println("extensions for " + defaultTheory + ": ");
        defaultTheory.generateExtensions().stream()
            .forEach(e -> System.out.println(e.toString()));
        */
        FormulaSet facts = new FormulaSet("!b|!c", "c|d");
        DefaultSet defaults = new DefaultSet();
        defaults.add(new Default(                   new FormulaSet("!b"),       new Formula("a")));
        defaults.add(new Default(                   new FormulaSet("!a", "!c"), new Formula("b")));
        defaults.add(new Default(                   new FormulaSet("a&!b"),  new Formula("!d")));
        defaults.add(new Default(new Formula("!c"), new FormulaSet("!a"),       new Formula("!a")));
        DefaultTheory defaultTheory = new DefaultTheory(facts, defaults);
        System.out.println("extensions for " + defaultTheory + ": ");
        defaultTheory.generateExtensions().stream()
            .forEach(e -> System.out.println(e.toString()));


    }
}

