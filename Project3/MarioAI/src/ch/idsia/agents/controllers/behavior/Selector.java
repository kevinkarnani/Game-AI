package ch.idsia.agents.controllers.behavior;

import ch.idsia.agents.controllers.BTMarioAgent;

import java.util.List;

public class Selector extends Composite {
    public Selector(List<Task> children) {
        super(children);
    }

    @Override
    public State run(BTMarioAgent b) {
        for (Task t: this.tasks) {
            Task.State state = t.run(b);
            if (state == State.SUCCESS) {
                return state;
            }
        }
        return State.FAILED;
    }
}