package com.chad.demo.random.model;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-11.
 */
public class PolicyConfig {

    private float[] normalRate;

    private float[] boundaryRate;

    private float[] cornerRate;

    public PolicyConfig() {
        normalRate = new float[] {0.04f, 0.04f, 0.02f, 0.9f};
        boundaryRate = new float[] {1 / 3f, 1 / 3f, 1 / 3f};
        cornerRate = new float[] { 0.5f, 0.5f};
    }

    public PolicyConfig(float[] normalRate, float[] boundaryRate, float[] cornerRate) {
        this.normalRate = normalRate;
        this.boundaryRate = boundaryRate;
        this.cornerRate = cornerRate;
    }

    public float[] getNormalRate() {
        return normalRate;
    }

    public void setNormalRate(float[] normalRate) {
        this.normalRate = normalRate;
    }

    public float[] getBoundaryRate() {
        return boundaryRate;
    }

    public void setBoundaryRate(float[] boundaryRate) {
        this.boundaryRate = boundaryRate;
    }

    public float[] getCornerRate() {
        return cornerRate;
    }

    public void setCornerRate(float[] cornerRate) {
        this.cornerRate = cornerRate;
    }
}
