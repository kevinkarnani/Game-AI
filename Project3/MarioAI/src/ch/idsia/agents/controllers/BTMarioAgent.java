package ch.idsia.agents.controllers;

import ch.idsia.agents.Agent;
import ch.idsia.agents.controllers.behavior.BehaviorHandler;
import ch.idsia.agents.controllers.behavior.Sequence;
import ch.idsia.benchmark.mario.engine.sprites.Mario;
import ch.idsia.benchmark.mario.environments.Environment;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class BTMarioAgent extends BasicMarioAIAgent implements Agent {
    Sequence root;
    int trueJumpCounter = 0;
    int trueSpeedCounter = 0;

    public BTMarioAgent() {
        super("BTMarioAgent");

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            BehaviorHandler b = new BehaviorHandler();
            saxParser.parse(new File("src/ch/idsia/agents/controllers/behavior/behavior.xml"), b);
            this.root = (Sequence) b.tmp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.reset();
    }

    public void simpleMove(int direction, boolean move) {
        this.action[direction] = move;
    }

    public void Jump() {
        this.simpleMove(Mario.KEY_JUMP, isMarioAbleToJump || !isMarioOnGround);
        this.trueJumpCounter++;
    }

    public void MoveRight() {
        this.simpleMove(Mario.KEY_RIGHT, true);
    }

    public void Shoot() {
        this.simpleMove(Mario.KEY_SPEED, this.isMarioAbleToShoot && this.IsEnemyAhead());
        this.trueSpeedCounter++;
    }

    public boolean IsObstacleAhead() {
        int x = this.marioEgoRow;
        int y = this.marioEgoCol;
        if (getReceptiveFieldCellValue(x, y + 1) == 0 || getReceptiveFieldCellValue(x, y + 2) == 0) {
            System.out.println("obstacle ahead");
        }
        return this.getReceptiveFieldCellValue(x, y + 1) != 0 || this.getReceptiveFieldCellValue(x, y + 2) != 0;
    }

    public boolean Danger() {
        return this.IsObstacleAhead() || this.IsPitfallAhead() || this.IsEnemyAhead();
    }

    public boolean IsPitfallAhead() {
        int x = this.marioEgoRow;
        int y = this.marioEgoCol;
        if (getReceptiveFieldCellValue(x + 2, y + 1) == 0 && getReceptiveFieldCellValue(x + 1, y + 1) == 0) {
            System.out.println("pitfall ahead");
        }
        return this.getReceptiveFieldCellValue(x + 2, y + 1) == 0 && this.getReceptiveFieldCellValue(x + 1, y + 1) == 0;
    }

    public boolean IsEnemyAhead() {
        int x = this.marioEgoRow;
        int y = this.marioEgoCol;
        if (this.getEnemiesCellValue(x, y + 1) != 0 || this.getEnemiesCellValue(x, y + 2) != 0) {
            System.out.println("enemy ahead");
        }
        return this.getEnemiesCellValue(x, y + 1) != 0 || this.getEnemiesCellValue(x, y + 2) != 0;
    }

    public boolean IsCoinAbove() {
        int x = this.marioEgoRow;
        int y = this.marioEgoCol;
        return this.Danger() && (this.getReceptiveFieldCellValue(x + 2, y) != 1
                || this.getReceptiveFieldCellValue(x + 1, y) != 1);
    }

    public boolean IsMysteryBoxAbove() {
        int x = this.marioEgoRow;
        int y = this.marioEgoCol;
        int BOX = -24;
        return this.Danger() && (this.getReceptiveFieldCellValue(x + 2, y) == BOX
                || this.getReceptiveFieldCellValue(x + 1, y) == BOX);
    }

    @Override
    public boolean[] getAction() {
        this.root.run(this);
        if (this.trueJumpCounter > 16)
        {
            this.reset();
        }
        return this.action;
    }

    @Override
    public void reset() {
        this.action = new boolean[Environment.numberOfKeys];
        this.trueJumpCounter = 0;
        this.trueSpeedCounter = 0;
    }
}