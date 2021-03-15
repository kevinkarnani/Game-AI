package ai.abstraction.ruleBasedSystem;

import java.util.ArrayList;

public class Rule {
    Term[] pattern;
    Term[] effect;
    int[] effectType;

    public Rule(Term[] pattern, Term[] effect, int[] effectType) {
        this.pattern = pattern;
        this.effect = effect;
        this.effectType = effectType;
    }

    public void instantiate(ArrayList<String> bindings) {
        for (Term t: this.effect) {
            if (t.parameters[0].equals(bindings.get(0))) {
                t.parameters[0] = t.parameters[1];
            }
        }
    }

    public Term[] getPattern() {
        return this.pattern;
    }
}
