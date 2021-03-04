package ch.idsia.agents.controllers.behavior;

import ch.idsia.agents.controllers.BTMarioAgent;

import java.util.ArrayList;
import java.util.List;

public class Composite implements Task {
    protected List<Task> tasks;

    public Composite(List<Task> children) {
        this.tasks = (children == null) ? new ArrayList<>() : children;
    }

    @Override
    public State run(BTMarioAgent b) {
        return null;
    }

    public void addTask(Task t) {
        this.tasks.add(t);
    }
}
