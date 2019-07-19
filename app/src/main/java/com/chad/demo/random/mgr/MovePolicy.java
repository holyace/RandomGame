package com.chad.demo.random.mgr;

import com.chad.demo.random.model.MoveModel;
import com.chad.demo.random.model.PolicyConfig;
import com.chad.demo.random.model.PositionModel;
import com.chad.demo.random.util.ProbabilityUtil;

import java.util.Random;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-11.
 */
public class MovePolicy {

    private static final String TAG = MovePolicy.class.getSimpleName();

    private PolicyConfig mConfig;

    public MovePolicy() {
        this(new PolicyConfig());
    }

    public MovePolicy(PolicyConfig config) {
        mConfig = config;
    }

    private boolean isLeftTopCorner(int w, int h, PositionModel model) {
        return model.getPosition().x <= 0 && model.getPosition().y <= 0;
    }

    private boolean isRightTopCorner(int w, int h, PositionModel model) {
        return model.getPosition().x >= w && model.getPosition().x <= 0;
    }

    private boolean isBottomLeftCorner(int w, int h, PositionModel model) {
        return model.getPosition().x <= 0 && model.getPosition().y >= h;
    }

    private boolean isBottomRightCorner(int w, int h, PositionModel model) {
        return model.getPosition().x >= w && model.getPosition().y >= h;
    }

    private boolean isTopBoundary(int w, int h, PositionModel model) {
        return model.getPosition().y <= 0;
    }

    private boolean isBottomBoundary(int w, int h, PositionModel model) {
        return model.getPosition().y >= h;
    }

    private boolean isLeftBoundary(int w, int h, PositionModel model) {
        return model.getPosition().x <= 0;
    }

    private boolean isRightBoundary(int w, int h, PositionModel model) {
        return model.getPosition().x >= w;
    }

    public MoveModel move(int w, int h, MoveModel mm) {
        PositionModel model = mm.getNow();
        if (isLeftTopCorner(w, h, model)) {
            int index = ProbabilityUtil.random(mConfig.getConerRate());
            if (index == 0) {
                mm.goRight(null);
            }
            else {
                mm.goDown(null);
            }
        }
        else if (isRightTopCorner(w, h, model)) {
            int index = ProbabilityUtil.random(mConfig.getConerRate());
            if (index == 0) {
                mm.goLeft(null);
            }
            else {
                mm.goDown(null);
            }
        }
        else if (isBottomLeftCorner(w, h, model)) {
            int index = ProbabilityUtil.random(mConfig.getConerRate());
            if (index == 0) {
                mm.goUp(null);
            }
            else {
                mm.goRight(null);
            }
        }
        else if (isBottomRightCorner(w, h, model)) {
            int index = ProbabilityUtil.random(mConfig.getConerRate());
            if (index == 0) {
                mm.goUp(null);
            }
            else {
                mm.goLeft(null);
            }
        }
        else if (isLeftBoundary(w, h, model)) {
            int index = ProbabilityUtil.random(mConfig.getBoundaryRate());
            if (index == 0) {
                mm.goDown(null);
            }
            else if (index == 1) {
                mm.goUp(null);
            }
            else {
                mm.goRight(null);
            }
        }
        else if (isTopBoundary(w, h, model)) {
            int index = ProbabilityUtil.random(mConfig.getBoundaryRate());
            if (index == 0) {
                mm.goLeft(null);
            }
            else if (index == 1) {
                mm.goRight(null);
            }
            else {
                mm.goDown(null);
            }
        }
        else if (isRightBoundary(w, h, model)) {
            int index = ProbabilityUtil.random(mConfig.getBoundaryRate());
            if (index == 0) {
                mm.goUp(null);
            }
            else if (index == 1) {
                mm.goDown(null);
            }
            else {
                mm.goLeft(null);
            }
        }
        else if (isBottomBoundary(w, h, model)) {
            int index = ProbabilityUtil.random(mConfig.getBoundaryRate());
            if (index == 0) {
                mm.goLeft(null);
            }
            else if (index == 1) {
                mm.goRight(null);
            }
            else {
                mm.goUp(null);
            }
        }
        else {
            int index = ProbabilityUtil.random(mConfig.getNormalRate());
            if (index == 0) {
                mm.turnLeft();
            }
            else if (index == 1) {
                mm.turnRight();
            }
            else if (index == 2) {
                mm.turnBack();
            }
            else {
                mm.goAhead();
            }
        }
        return mm;
    }

}
