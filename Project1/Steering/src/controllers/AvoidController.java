package controllers;

import engine.*;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;

public class AvoidController extends SeekController {
    public AvoidController(Car target) {
        super(target);
    }

    @Override
    public void update(Car subject, Game game, double delta_t, double[] controlVariables){
        controlVariables[VARIABLE_STEERING] = 0;
        controlVariables[VARIABLE_THROTTLE] = 0;
        controlVariables[VARIABLE_BRAKE] = 0;

        double distance = 15;
        double angle = subject.getAngle();

        boolean left = false;
        boolean front = false;
        boolean right = false;

        for (int i = 1; i < distance; i ++) {
            front = this.rayCast(subject, game, angle, i);
            left = this.rayCast(subject, game, angle - Math.PI/4, i);
            right = this.rayCast(subject, game, angle + Math.PI/4, i);

            if(front || left || right) {
                distance = i;
                break;
            }
        }

        if (front || left || right){
            double newX = subject.getX(), newY = subject.getY();
            if (front) {
                newX -= Math.cos(angle) * distance;
                newY -= Math.sin(angle) * distance;
            } else if (left) {
                newX += Math.cos(angle + Math.PI / 4) * distance;
                newY += subject.getY() + Math.sin(angle + Math.PI / 4) * distance;
            } else {
                newX += Math.cos(angle - Math.PI / 4) * distance;
                newY += Math.sin(angle - Math.PI / 4) * distance;
            }
            super.update(subject, controlVariables, this.turn(subject, newX, newY));

        } else {
            super.update(subject, game, delta_t, controlVariables);
        }
    }

    public boolean rayCast(Car subject, Game game, double angle, double distance) {
        double newX = subject.getX() + Math.cos(angle) * distance;
        double newY = subject.getY() + Math.sin(angle) * distance;

        RotatedRectangle r = new RotatedRectangle(newX, newY, subject.m_img.getWidth(), subject.m_img.getHeight(), angle);

        return game.collision(r) instanceof Obstacle;
    }

    public List<Double> turn(Car subject, double x, double y){
        double D_x = x - subject.getX(), D_y = y - subject.getY();
        double D_norm = Math.sqrt(Math.pow(D_x, 2) + Math.pow(D_y, 2));
        double maxAcceleration = 10.0;
        D_x *= maxAcceleration / D_norm;
        D_y *= maxAcceleration / D_norm;
        return Arrays.asList(D_x, D_y);
    }
}
