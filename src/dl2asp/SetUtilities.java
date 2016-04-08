package dl2asp;

import dl2asp.DefaultLogic.Formula;
import dl2asp.DefaultLogic.FormulaSet;

import java.util.HashSet;

public class SetUtilities
{
    //TODO: This is just a brute force algorithm, replace with a smart one.
    public static HashSet<FormulaSet> getMinimalUnsatisfiableSets(FormulaSet variableSet, FormulaSet fixedSet)
    {
        return getMinimal(getUnsatisfiableSets(variableSet, fixedSet));
    }

    private static HashSet<FormulaSet> getUnsatisfiableSets(FormulaSet variableSet, FormulaSet fixedSet)
    {
        HashSet<FormulaSet> result = new HashSet<>();
        for (Formula element : variableSet)
        {
            FormulaSet newVariableSet = new FormulaSet(variableSet);
            newVariableSet.remove(element);
            FormulaSet testSet = new FormulaSet(newVariableSet);
            testSet.addAll(fixedSet);
            if (testSet.isSatisfiable())
            {
                result.add(variableSet);
            } else
            {
                result.addAll(getUnsatisfiableSets(newVariableSet, fixedSet));
            }
        }

        return result;
    }

    private static HashSet<FormulaSet> getMinimal(HashSet<FormulaSet> formulas)
    {
        HashSet<FormulaSet> result = new HashSet<>(formulas);

        for (FormulaSet set1 : formulas)
        {
            for (FormulaSet set2 : formulas)
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