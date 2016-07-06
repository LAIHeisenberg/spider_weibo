package com.heisenberg.datamodel;

import java.util.List;

/**
 * Created by Heisenberg on 7/6/16.
 */
public class WeiboData implements DataModel{

    private String slogan;
    private String readNum;
    private String discussNum;
    private String fanNum;

    public WeiboData() {
    }

    public WeiboData(String slogan, String readNum, String discussNum, String fanNum) {
        this.slogan = slogan;
        this.readNum = readNum;
        this.discussNum = discussNum;
        this.fanNum = fanNum;
    }

    public WeiboData(String ... args){
        this.slogan = args[0];
        this.readNum = args[1];
        this.discussNum = args[2];
        this.fanNum = args[3];
    }


    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getReadNum() {
        return readNum;
    }

    public void setReadNum(String readNum) {
        this.readNum = readNum;
    }

    public String getDiscussNum() {
        return discussNum;
    }

    public void setDiscussNum(String discussNum) {
        this.discussNum = discussNum;
    }

    public String getFanNum() {
        return fanNum;
    }

    public void setFanNum(String fanNum) {
        this.fanNum = fanNum;
    }

    public String toString(){
        return slogan+","+readNum+","+discussNum+","+fanNum;
    }
}
