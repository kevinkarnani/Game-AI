package controllers;

import engine.Car;
import engine.Game;
import engine.GameObject;

import java.util.Arrays;
import java.util.List;

public class SeekController extends Controller {
    /**
     * @author Kevin Karnani
     **/
    protected final Car target;
    public SeekController(Car target){
        this.target = target;
    }

    @Override
    public void update(Car subject, Game game, double delta_t, double[] controlVariables){
        this.update(subject, controlVariables, null);
    }

    public void update(Car subject, double[] controlVariables, List<Double> acceleration){
        controlVariables[VARIABLE_STEERING] = 0;
        controlVariables[VARIABLE_THROTTLE] = 0;
        controlVariables[VARIABLE_BRAKE] = 0;

        double maxSpeed = 250.0;

        if (acceleration == null){
            acceleration = this.seek(subject);
        }

        double speed = subject.getSpeed(), angle = subject.getAngle();
        double projection = acceleration.get(0) * Math.cos(angle) + acceleration.get(1) * Math.sin(angle);

        if (projection > 0){
            controlVariables[VARIABLE_THROTTLE] = projection;
        } else {
            controlVariables[VARIABLE_BRAKE] = Math.abs(projection);
        }

        double normalProjection = acceleration.get(0) * -1 * Math.sin(angle) + acceleration.get(1) * Math.cos(angle);
        normalProjection *= (speed/ maxSpeed);
        controlVariables[VARIABLE_STEERING] = normalProjection;
    }

    public List<Double> seek(Car subject) {
        double D_x = this.target.getX() - subject.getX(), D_y = this.target.getY() - subject.getY();
        double D_norm = Math.sqrt(Math.pow(D_x, 2) + Math.pow(D_y, 2));
        double ND_x = D_x / D_norm, ND_y = D_y / D_norm;
        double maxAcceleration = 10.0;
        double A_x = maxAcceleration * ND_x, A_y = maxAcceleration * ND_y;
        return Arrays.asList(A_x, A_y);
    }
}