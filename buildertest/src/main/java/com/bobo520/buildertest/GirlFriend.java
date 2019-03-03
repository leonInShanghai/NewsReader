package com.bobo520.buildertest;

/**
 * Created by 求知自学网 on 2019/3/3. Copyright © Leon. All rights reserved.
 * Functions: 女朋友类  建造者模式的举例
 * 一些复杂的对象我们不用创造多个构造方法也能够实现这个时候就要使用建造者模式了
 * 建造者模式：创建时制定特殊的属性如果不指定就使用默认值
 */
public class GirlFriend {

    /**姓名*/
    String name;

    /**年龄*/
    String age;

    /**身高*/
    String height;

    /**体重*/
    String weight;

    /**三维*/
    String sanwei;

    /**是否漂亮*/
    boolean isBeauty;

    public GirlFriend(String name) {
        this.name = name;
    }

    public GirlFriend(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public GirlFriend(String name, String age, String height) {
        this.name = name;
        this.age = age;
        this.height = height;
    }

    public GirlFriend(String name, String age, String height, String weight) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
    }

    public GirlFriend(String name, String age, String height, String weight, String sanwei) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.sanwei = sanwei;
    }

    public GirlFriend(String name, String age, String height, String weight, String sanwei, boolean isBeauty) {
        this.name = name;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.sanwei = sanwei;
        this.isBeauty = isBeauty;
    }

    @Override
    public String toString() {
        return "GirlFriend{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", height='" + height + '\'' +
                ", weight='" + weight + '\'' +
                ", sanwei='" + sanwei + '\'' +
                ", isBeauty=" + isBeauty +
                '}';
    }

    public boolean isBeauty() {
        return isBeauty;
    }

    public void setBeauty(boolean beauty) {
        isBeauty = beauty;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getSanwei() {
        return sanwei;
    }

    public void setSanwei(String sanwei) {
        this.sanwei = sanwei;
    }

    //①建造者模式首先准备一个内部类
    public static class Builder{

        /**默认值-姓名*/
        private String name = "大乔";

        /**默认值-年龄*/
        private String age = "30";

        /**默认值-身高*/
        private String height = "175";

        /**默认值-体重*/
        private String weight = "50kg";

        /**默认值-三维*/
        private String sanwei = "80 60 90";

        /**默认值-是否漂亮*/
        private boolean isBeauty = true;

        //②建造者模式中的setter方法一定要返回this
        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        //②建造者模式中的setter方法一定要返回this
        public Builder setAge(String age) {
            this.age = age;
            return this;
        }

        //②建造者模式中的setter方法一定要返回this
        public Builder setHeight(String height) {
            this.height = height;
            return this;
        }

        //②建造者模式中的setter方法一定要返回this
        public Builder setWeight(String weight) {
            this.weight = weight;
            return this;
        }

        //②建造者模式中的setter方法一定要返回this
        public Builder setSanwei(String sanwei) {
            this.sanwei = sanwei;
            return this;
        }

        //②建造者模式中的setter方法一定要返回this
        public Builder setBeauty(boolean beauty) {
            isBeauty = beauty;
            return this;
        }

        //③建造者模式中的build方法
        public GirlFriend build(){
            GirlFriend girlFriend = new GirlFriend(name,age,height,weight,sanwei,isBeauty);
            return girlFriend;
        }
    }



}
