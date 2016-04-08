package dl2asp.AnswerSetProgram;

public class Main
{
    public static void main(String[] args)
    {
        Program program = new Program();
        program.add(new Rule(new AtomSet("a"), new AtomSet("c"), AtomSet.Empty));
        program.add(new Rule(new AtomSet("c"), AtomSet.Empty,    new AtomSet("d")));
        program.add(new Rule(new AtomSet("a"), new AtomSet("b"), new AtomSet("e")));
        program.add(new Rule(new AtomSet("d"), AtomSet.Empty,    new AtomSet("c")));
        program.add(new Rule(new AtomSet("b"), new AtomSet("a"), new AtomSet("e")));
        program.add(new Rule(new AtomSet("e"), AtomSet.Empty,    new AtomSet("d")));

        AtomSet interpretation = new AtomSet("a", "-b", "c", "-d", "e");

        System.out.println(interpretation + " |= " + program +  ": " + program.isConcludedBy(interpretation));
        System.out.println(interpretation + " |= " + program.getReduct(interpretation) +  ": " + program.getReduct(interpretation).isConcludedBy(interpretation));
    }
}
