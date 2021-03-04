package ch.idsia.agents.controllers.behavior;

import ch.idsia.agents.controllers.BTMarioAgent;

import java.util.List;

public class Sequence extends Composite {
    public Sequence(List<Task> children) {
        super(children);
    }

    @Override
    public State run(BTMarioAgent b) {
        for (Task t: this.tasks) {
            Task.State state = t.run(b);
            if (state == State.FAILED) {
                return state;
            }
        }
        return State.SUCCESS;
    }
}
