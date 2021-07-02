package com.gk.design.patterns;

import java.util.EventObject;

/**
 * @author guokui
 * @class myCloudProject
 * @date 2021/6/11 14:07
 *
 * 监听器
 *
 * 实现原理利用实现类进行实现，主要有两步，
 *
 * 1、实现实现类，重写对应方法，
 * 2、创建监听器
 *
 */
public class EventPattern {


}
interface  Event {
    void ClickEvent();
}

class ClickEvent implements Event {
    Event event;

    @Override
    public void ClickEvent() {

    }
}