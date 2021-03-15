package ai.abstraction.ruleBasedSystem;

import java.util.ArrayList;

public class KnowledgeBase {
    ArrayList<Term> facts;

    public KnowledgeBase() {
        this.facts = new ArrayList<>();
    }

    void addTerm(Term t) {
        this.facts.add(t);
    }

    void clear() {
        this.facts.clear();
    }

    public ArrayList<Term> getFacts() {
        return this.facts;
    }
}