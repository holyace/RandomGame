package com.chad.demo.random.model;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-11.
 */
public class PolicyConfig {

    private float[] normalRate;

    private float[] collisionRate;

    private int speed = 3;

    public PolicyConfig() {
        normalRate = new float[] {0.075f, 0.075f, 0.05f, 0.8f};
        collisionRate = new float[] {1 / 3f, 1 / 3f, 1 / 3f};
    }

    public PolicyConfig(float[] normalRate, float[] collisionRate) {
        this.normalRate = normalRate;
        this.collisionRate = collisionRate;
    }

    public float[] getNormalRate() {
        return normalRate;
    }

    public void setNormalRate(float[] normalRate) {
        if (normalRate == null || normalRate.length == 0) {
            return;
        }
        this.normalRate = normalRate;
    }

    public float[] getCollisionRate() {
        return collisionRate;
    }

    public void setCollisionRate(float[] collisionRate) {
        if (collisionRate == null || collisionRate.length == 0) {
            return;
        }
        this.collisionRate = collisionRate;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        if (speed <= 0) {
            return;
        }
        this.speed = speed;
    }
}
