package ai.abstraction.ruleBasedSystem;

public class Term {
    String functor;
    String[] parameters;

    public Term(String functor, String p1, String p2) {
        this.functor = functor;
        this.parameters = new String[]{p1, p2};
    }

    public String getFunctor() {
        return this.functor;
    }

    public String[] getParameters() {
        return this.parameters;
    }
}
