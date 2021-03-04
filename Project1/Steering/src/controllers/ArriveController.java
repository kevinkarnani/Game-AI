package controllers;

import engine.Car;
import engine.Game;
import engine.GameObject;

import java.util.Arrays;
import java.util.List;

public class ArriveController extends Controller {
    private final GameObject target;
    public ArriveController(GameObject target){
        this.target = target;
    }

    @Override
    public void update(Car subject, Game game, double delta_t, double[] controlVariables) {
        controlVariables[VARIABLE_STEERING] = 0;
        controlVariables[VARIABLE_THROTTLE] = 0;
        controlVariables[VARIABLE_BRAKE] = 0;

        double speed = subject.getSpeed(), angle = subject.getAngle();
        List<Double> acceleration = this.arrive(subject, 5, 200, delta_t);
        double projection = acceleration.get(0) * Math.cos(angle) + acceleration.get(1) * Math.sin(angle);
        if (speed < .1 && acceleration.get(0) != 0 && acceleration.get(1) != 0){
            controlVariables[VARIABLE_THROTTLE] = 1;
        } else if (projection >= 0){
            controlVariables[VARIABLE_THROTTLE] = projection;
        } else {
            controlVariables[VARIABLE_BRAKE] = Math.abs(projection);
        }

        double normalProjection = acceleration.get(0) * -1 * Math.sin(angle) + acceleration.get(1) * Math.cos(angle);
        controlVariables[VARIABLE_STEERING] = normalProjection;

        double D_x = this.target.getX() - subject.getX(), D_y = this.target.getY() - subject.getY();
        double D_norm = Math.sqrt(Math.pow(D_x, 2) + Math.pow(D_y, 2));

        double angleDifference = (D_x == 0) ? Math.asin(D_y/D_norm) : (D_x > 0) ? Math.atan(D_y/D_x) : Math.atan(D_y/D_x) + Math.PI;
        angleDifference -= angle;
        angleDifference %= (2 * Math.PI);

        if (angleDifference > Math.PI) {
            angleDifference -= (2 * Math.PI);
        } else if (angleDifference <= -1 * Math.PI){
            angleDifference += (2 * Math.PI);
        }

        if (Math.abs(angleDifference) > 0 && Math.abs(angleDifference) <= Math.PI/2){
            controlVariables[VARIABLE_STEERING] = angleDifference;
        } else if (Math.abs(angleDifference) > Math.PI/2) {
            controlVariables[VARIABLE_THROTTLE] = 1;
            controlVariables[VARIABLE_STEERING] = Math.abs(angleDifference);
        } else {
            controlVariables[VARIABLE_THROTTLE] = 1;
        }
    }

    public List<Double> arrive(Car subject, double targetRadius, double slowRadius, double deltaTime){
        double D_x = this.target.getX() - subject.getX(), D_y = this.target.getY() - subject.getY();
        double curr_vx = subject.getSpeed() * Math.cos(subject.getAngle()), curr_vy = subject.getSpeed() * Math.sin(subject.getAngle());
        double distance = Math.sqrt(Math.pow(D_x, 2) + Math.pow(D_y, 2));
        if (distance < targetRadius){
            return Arrays.asList(0.0, 0.0);
        }
        double maxSpeed = 250.0, maxAcceleration = 10.0;
        double targetSpeed = (distance > slowRadius) ? maxSpeed : maxSpeed * distance/slowRadius;
        double target_vx = D_x/distance * targetSpeed, target_vy = D_y/distance * targetSpeed;
        double A_x = (target_vx - curr_vx)/(deltaTime * 100), A_y = (target_vy - curr_vy)/(deltaTime * 100);
        double A_norm = Math.sqrt(Math.pow(A_x, 2) + Math.pow(A_y, 2));
        if (A_norm > maxAcceleration){
            A_x *= maxAcceleration / A_norm;
            A_y *= maxAcceleration / A_norm;
        }
        return Arrays.asList(A_x, A_y);
    }
}
