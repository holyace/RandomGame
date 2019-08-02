package com.chad.demo.random.event;

import com.chad.demo.random.constant.EventType;

import java.util.Map;

/**
 * No comment for you. yeah, come on, bite me~
 * <p>
 * Created by chad on 2019-08-02.
 */
public interface IEventHandler {

    boolean handleEvent(EventType type, float x, float y, float dx, float dy, float vx, float vy);
}
