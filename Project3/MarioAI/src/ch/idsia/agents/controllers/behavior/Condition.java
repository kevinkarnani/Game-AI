package ch.idsia.agents.controllers.behavior;

import ch.idsia.agents.controllers.BTMarioAgent;

public class Condition implements Task {
    public String condition;

    public Condition(String condition) {
        this.condition = condition;
    }

    @Override
    public State run(BTMarioAgent b) {
        try {
            return (boolean)BTMarioAgent.class.getDeclaredMethod(this.condition).invoke(b) ? State.SUCCESS : State.FAILED;
        } catch (Exception e) {
            e.printStackTrace();
            return State.FAILED;
        }
    }
}
