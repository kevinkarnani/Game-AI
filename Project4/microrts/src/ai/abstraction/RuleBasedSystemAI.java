package ai.abstraction;

import ai.abstraction.pathfinding.AStarPathFinding;
import ai.abstraction.pathfinding.PathFinding;
import ai.abstraction.ruleBasedSystem.KnowledgeBase;
import ai.abstraction.ruleBasedSystem.Rule;
import ai.core.AI;
import ai.core.ParameterSpecification;
import rts.GameState;
import rts.PhysicalGameState;
import rts.Player;
import rts.PlayerAction;
import rts.units.UnitTypeTable;

import java.util.ArrayList;
import java.util.List;

public class RuleBasedSystemAI extends AbstractionLayerAI {
    Rule[] rules;
    UnitTypeTable utt;

    public RuleBasedSystemAI(UnitTypeTable utt, PathFinding a_pf, Rule[] rules) {
        super(a_pf);

        this.rules = rules;
        this.utt = utt;

        /*
        TOE = new HashMap<>();

        TOE.put("Base",0);
        TOE.put("Barracks",0);
        TOE.put("Worker",0);
        TOE.put("Light",0);
        TOE.put("Resource", 0);
        TOE.put("Ranged", 0);
        */
    }

    @Override
    public PlayerAction getAction(int player, GameState gs) throws Exception {
        PhysicalGameState pgs = gs.getPhysicalGameState();
        Player p = gs.getPlayer(player);
        KnowledgeBase kb = new KnowledgeBase();

        pgs.getUnits().forEach(unit -> {
            if (unit.getPlayer() == player) {
                // addFact(kb, OWN, unit);
                if (unit.isIdle(gs)) {
                    // addFact(kb, IDLE, unit);
                }
                // addFact(kb, TYPE, unit, unit.getType());
            }
        });


        return translateActions(player, gs);
    }

    @Override
    public AI clone() {
        return new RuleBasedSystemAI(utt, pf, rules);
    }

    // took this from another class
    @Override
    public List<ParameterSpecification> getParameters() {
        List<ParameterSpecification> parameters = new ArrayList<>();

        parameters.add(new ParameterSpecification("PathFinding", PathFinding.class, new AStarPathFinding()));

        return parameters;
    }

    /* public ArrayList<String> unification(Term T) {
        ArrayList<String> bindings = new ArrayList<>();

        KB.getFacts().forEach(fact -> {
            if (T.getFunctor().equals(fact.getFunctor()) && T.getParameters().length == fact.getParameters().length
                    && T.getParameters()[1].equals(fact.getParameters()[1])) {
                bindings.add(T.getParameters()[0]);
                bindings.add(fact.getParameters()[0]);
            }
        });
        return bindings.size() > 0 ? bindings : null;
    }

    public Rule arbitrate(ArrayList<Rule> rules) {
        return rules.get(this.rand.nextInt(rules.size()));
    }

    public void RuleBasedSystemIteration(Player p, PhysicalGameState pgs) {
        ArrayList<Rule> firedRules = new ArrayList<>();
        ArrayList<String> bindings;

        for (Rule r : this.rules) {
            boolean found = true;
            for (Term t: r.getPattern()) {
                bindings = this.unification(t);
                if (bindings == null) {
                    found = false;
                    break;
                }
                r.instantiate(bindings);
            }
            if (!found) {
                continue;
            }
            firedRules.add(r);
        }

        if (!firedRules.isEmpty()) {
            this.arbitrate(firedRules);
        }
    }*/
}
