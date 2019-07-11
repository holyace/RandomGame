package com.chad.demo.random.mgr;

import android.graphics.PointF;

import com.chad.demo.random.model.MoveModel;
import com.chad.demo.random.model.PolicyConfig;
import com.chad.demo.random.model.PositionModel;
import com.chad.demo.random.util.Direction;
import com.chad.demo.random.util.ProbabilityUtil;
import com.chad.demo.random.util.Turn;

import java.util.Random;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-11.
 */
public class MovePolicy {

    private static float[] RATE_TURN = new float[] { 1 / 3f, 1 / 3f, 1 / 3f};

    private PolicyConfig mConfig;
    private Random mRandom;

    public MovePolicy() {
        this(new PolicyConfig());
    }

    public MovePolicy(PolicyConfig config) {
        mConfig = config;
        mRandom = new Random();
    }

    public MoveModel move(int width, int height, MoveModel curr) {
        PositionModel pos = curr.getNow();

        switch (pos.getDirection()) {
            case GO_RIGHT:
                handleGoRight(width, height, pos, 0);
                break;

            case GO_LEFT:
                handleGoLeft(width, height, pos, 0);
                break;

            case GO_BOTTOM:
                handleGoBottom(width, height, pos, 0);
                break;

            case GO_UP:
                handleGoUp(width, height, pos, 0);
                break;
        }
        return curr;
    }

    private void handleGoRight(int w, int h, PositionModel model, int count) {
        if (count >= 4) {
            return;
        }
        PointF pos = model.getPosition();
        int index = ProbabilityUtil.random(pos.x >= w ? mConfig.getCollisionRate() : mConfig.getNormalRate());
        if (index == 0) {
            model.getDirection().turn(Turn.TURN_LEFT);
            handleGoUp(w, h, model, count++);
        }
        else if (index == 1) {
            model.getDirection().turn(Turn.TURN_RIGHT);
            handleGoBottom(w, h, model, count++);
        }
        else if (index == 2) {
            model.getDirection().turn(Turn.TURN_BACK);
            handleGoLeft(w, h, model, count++);
        }
        else {
            pos.x += mConfig.getSpeed();
        }
    }

    private void handleGoBottom(int w, int h, PositionModel model, int count) {
        if (count >= 4) {
            return;
        }
        PointF pos = model.getPosition();
        int index = ProbabilityUtil.random(pos.y >= h ? mConfig.getCollisionRate() : mConfig.getNormalRate());

    }

    private void handleGoLeft(int w, int h, PositionModel model, int count) {

    }

    private void handleGoUp(int w, int h, PositionModel model, int count) {

    }
}
