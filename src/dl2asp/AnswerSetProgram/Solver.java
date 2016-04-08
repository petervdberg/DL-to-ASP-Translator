package dl2asp.AnswerSetProgram;

import java.util.HashSet;

public class Solver
{
    //TODO: This is just a brute force algorithm, replace with a smart one.
    public static HashSet<AtomSet> solve(Program program)
    {
        HashSet<AtomSet> interpretations = new HashSet<>();
        HashSet<AtomSet> trash = new HashSet<>();
        solve(interpretations, trash, program, program.getAtoms()); //new AtomSet("P[(!b | !c)]", "P[(c | d)]")); //
        return getMinimal(interpretations);
    }

    private static void solve(HashSet<AtomSet> interpretations, HashSet<AtomSet> trash, Program program, AtomSet atoms)
    {
        if(program.isConcludedBy(atoms))
        {
            interpretations.add(atoms);
        }
        else
        {
            trash.add(atoms);
        }

        for(Atom a : atoms)
        {
            AtomSet clone = atoms.clone();
            clone.remove(a);
            if(!interpretations.contains(clone) && !trash.contains(clone))
            {
                solve(interpretations, trash, program, clone);
            }
        }
    }

    private static HashSet<AtomSet> getMinimal(HashSet<AtomSet> atoms)
    {
        HashSet<AtomSet> result = new HashSet<>(atoms);

        for (AtomSet set1 : atoms)
        {
            for (AtomSet set2 : atoms)
            {
                if (set1.size() > set2.size() && set2.isSubSetOf(set1))
                {
                    result.remove(set1);
                }
            }
        }

        return result;
    }
}
