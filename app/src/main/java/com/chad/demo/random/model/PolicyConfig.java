package com.chad.demo.random.model;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-07-11.
 */
public class PolicyConfig {

    private float[] normalRate;

    private float[] boundaryRate;

    private float[] conerRate;

    public PolicyConfig() {
        normalRate = new float[] {0.075f, 0.075f, 0.05f, 0.8f};
        boundaryRate = new float[] {1 / 3f, 1 / 3f, 1 / 3f};
        conerRate = new float[] { 0.5f, 0.5f};
    }

    public PolicyConfig(float[] normalRate, float[] boundaryRate, float[] conerRate) {
        this.normalRate = normalRate;
        this.boundaryRate = boundaryRate;
        this.conerRate = conerRate;
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

    public float[] getConerRate() {
        return conerRate;
    }

    public void setConerRate(float[] conerRate) {
        this.conerRate = conerRate;
    }
}
