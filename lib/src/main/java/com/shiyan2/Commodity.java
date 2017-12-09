package com.shiyan2;

/**
 * Created by 40344 on 2017/9/27.
 */

public class Commodity {
    private int id;
    private String name;
    private float price;
    private int num = 1;

    public Commodity(int id, String name, float price){
        //初始化
        this.id = id;
        this.name = name;
        this.price = price;
    }

    //更新商品id
    public void updateId(int id){
        this.id = id;
    }
    //更新商品名称
    public void updateName(String name){
        this.name = name;
    }
    //更新商品价格
    public void updatePrice(float price){
        this.price = price;
    }
    //更新商品数量(单个)
    public void updateNum(){
        num ++;
    }
    //更新商品数量
    public void updateNum(int changeNum){
        num += changeNum;
    }

    //获取商品id
    public int getId(){
        return id;
    }
    //获取商品名称
    public String getName(){
        return name;
    }
    //获取商品加个
    public float getPrice(){
        return price;
    }
    //获取商品数量
    public int getNum(){
        return num;
    }
}
