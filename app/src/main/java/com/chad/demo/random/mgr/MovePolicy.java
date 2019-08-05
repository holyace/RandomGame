package com.chad.demo.random.mgr;

import com.chad.demo.random.model.MoveModel;
import com.chad.demo.random.model.PolicyConfig;
import com.chad.demo.random.model.PositionModel;
import com.chad.demo.random.util.ProbabilityUtil;
import com.chad.demo.random.constant.Turn;

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

    private boolean isLeftBottomCorner(int w, int h, PositionModel model) {
        return model.getPosition().x <= 0 && model.getPosition().y >= h;
    }

    private boolean isRightBottomCorner(int w, int h, PositionModel model) {
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

    public MoveModel move(int w, int h, MoveModel mm, long time) {
        if (!Turn.KEEP.equals(mm.getTurn())) {
            mm.setTurn(Turn.KEEP);
            mm.goAhead();
            return mm;
        }
        PositionModel model = mm.getNow();
        if (isLeftTopCorner(w, h, model)) {
            int index = ProbabilityUtil.random(mConfig.getCornerRate());
            if (index == 0) {
                mm.setTurn(Turn.FORCE_TURN);
                mm.goRight(null);
            }
            else {
                mm.setTurn(Turn.FORCE_TURN);
                mm.goDown(null);
            }
        }
        else if (isRightTopCorner(w, h, model)) {
            int index = ProbabilityUtil.random(mConfig.getCornerRate());
            if (index == 0) {
                mm.setTurn(Turn.FORCE_TURN);
                mm.goLeft(null);
            }
            else {
                mm.setTurn(Turn.FORCE_TURN);
                mm.goDown(null);
            }
        }
        else if (isLeftBottomCorner(w, h, model)) {
            int index = ProbabilityUtil.random(mConfig.getCornerRate());
            if (index == 0) {
                mm.setTurn(Turn.FORCE_TURN);
                mm.goUp(null);
            }
            else {
                mm.setTurn(Turn.FORCE_TURN);
                mm.goRight(null);
            }
        }
        else if (isRightBottomCorner(w, h, model)) {
            int index = ProbabilityUtil.random(mConfig.getCornerRate());
            if (index == 0) {
                mm.setTurn(Turn.FORCE_TURN);
                mm.goUp(null);
            }
            else {
                mm.setTurn(Turn.FORCE_TURN);
                mm.goLeft(null);
            }
        }
        else if (isLeftBoundary(w, h, model)) {
            int index = ProbabilityUtil.random(mConfig.getBoundaryRate());
            if (index == 0) {
                mm.setTurn(Turn.FORCE_TURN);
                mm.goDown(null);
            }
            else if (index == 1) {
                mm.setTurn(Turn.FORCE_TURN);
                mm.goUp(null);
            }
            else {
                mm.setTurn(Turn.FORCE_TURN);
                mm.goRight(null);
            }
        }
        else if (isTopBoundary(w, h, model)) {
            int index = ProbabilityUtil.random(mConfig.getBoundaryRate());
            if (index == 0) {
                mm.setTurn(Turn.FORCE_TURN);
                mm.goLeft(null);
            }
            else if (index == 1) {
                mm.setTurn(Turn.FORCE_TURN);
                mm.goRight(null);
            }
            else {
                mm.setTurn(Turn.FORCE_TURN);
                mm.goDown(null);
            }
        }
        else if (isRightBoundary(w, h, model)) {
            int index = ProbabilityUtil.random(mConfig.getBoundaryRate());
            if (index == 0) {
                mm.setTurn(Turn.FORCE_TURN);
                mm.goUp(null);
            }
            else if (index == 1) {
                mm.setTurn(Turn.FORCE_TURN);
                mm.goDown(null);
            }
            else {
                mm.setTurn(Turn.FORCE_TURN);
                mm.goLeft(null);
            }
        }
        else if (isBottomBoundary(w, h, model)) {
            int index = ProbabilityUtil.random(mConfig.getBoundaryRate());
            if (index == 0) {
                mm.setTurn(Turn.FORCE_TURN);
                mm.goLeft(null);
            }
            else if (index == 1) {
                mm.setTurn(Turn.FORCE_TURN);
                mm.goRight(null);
            }
            else {
                mm.setTurn(Turn.FORCE_TURN);
                mm.goUp(null);
            }
        }
        else {
            int index = ProbabilityUtil.random(mConfig.getNormalRate());
            if (index == 0) {
                mm.setTurn(Turn.TURN_LEFT);
                mm.turnLeft();
            }
            else if (index == 1) {
                mm.setTurn(Turn.TURN_RIGHT);
                mm.turnRight();
            }
            else if (index == 2) {
                mm.setTurn(Turn.TURN_BACK);
                mm.turnBack();
            }
            else {
                mm.setTurn(Turn.KEEP);
                mm.goAhead();
            }
        }
        return mm;
    }

}
