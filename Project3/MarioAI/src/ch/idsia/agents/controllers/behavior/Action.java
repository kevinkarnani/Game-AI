package ch.idsia.agents.controllers.behavior;

import ch.idsia.agents.controllers.BTMarioAgent;

public class Action implements Task {
    public String action;

    public Action(String action) {
        this.action = action;
    }

    @Override
    public State run(BTMarioAgent b) {
        try {
            b.getClass().getDeclaredMethod(this.action).invoke(b);
            return State.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return State.FAILED;
        }
    }
}
