package com.github.chengheaven.heaven.bean;

import java.io.Serializable;

/**
 * Created by jingbin on 2016/11/29.
 * 轮播图bean
 */

public class FrontpageBean implements Serializable {

    private String randpic;
    private String code;
    private int mo_type;
    private int type;
    private String is_publish;
    private String randpic_iphone6;
    private String randpic_desc;

    public String getRandpic() {
        return randpic;
    }

    public void setRandpic(String randpic) {
        this.randpic = randpic;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMo_type() {
        return mo_type;
    }

    public void setMo_type(int mo_type) {
        this.mo_type = mo_type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIs_publish() {
        return is_publish;
    }

    public void setIs_publish(String is_publish) {
        this.is_publish = is_publish;
    }

    public String getRandpic_iphone6() {
        return randpic_iphone6;
    }

    public void setRandpic_iphone6(String randpic_iphone6) {
        this.randpic_iphone6 = randpic_iphone6;
    }

    public String getRandpic_desc() {
        return randpic_desc;
    }

    public void setRandpic_desc(String randpic_desc) {
        this.randpic_desc = randpic_desc;
    }
}
