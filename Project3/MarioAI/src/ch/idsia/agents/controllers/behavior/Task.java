package ch.idsia.agents.controllers.behavior;

import ch.idsia.agents.controllers.BTMarioAgent;

public interface Task {
    enum State {SUCCESS, FAILED}

    State run(BTMarioAgent b);
}
