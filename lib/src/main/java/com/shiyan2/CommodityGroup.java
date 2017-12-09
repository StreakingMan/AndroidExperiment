package com.shiyan2;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by 40344 on 2017/10/11.
 */

public class CommodityGroup {
    private ArrayList<Commodity> commodityArrayList;
    private Scanner input;

    public CommodityGroup(){
        commodityArrayList = new ArrayList<>();
        input = new Scanner(System.in);
    }
    //添加新商品
    public int addCommodity(Commodity commodity){
        int stateCode = 0;
        for(int i=0;i<commodityArrayList.size();i++){
            if(commodity.getId()==queryCommodityId(i)){
                stateCode = 101;//id重复
                break;
            }else if(commodity.getName().equals(queryCommodityName(i))){
                stateCode = 102;//名称重复
                break;
            }
        }
        if(stateCode!=101&&stateCode!=102){
            commodityArrayList.add(commodity);
            stateCode = 103;//成功
        }
        return stateCode;
    }
    //添加已有商品
    public void addCommodity(int position, int num){
        commodityArrayList.get(position).updateNum(num);
    }
    //删除商品
    public void deleteCommodity(int position){
        boolean isNum = false;
        int delNum;
        //输入限制
        do {
            System.out.print("\tThe rest number is:" + queryCommodityNum(position) +
                    "  Please input the number you want to delete:");
            try {
                delNum = Integer.parseInt(input.next());
                //数量判断
                if (queryCommodityNum(position)<delNum){
                    isNum = false;
                }else if(queryCommodityNum(position)==delNum){
                    commodityArrayList.remove(position);
                    System.out.print("\tAll the " + queryCommodityName(position) + " have been deleted!");
                    System.out.println();
                    isNum = true;
                }else if(queryCommodityNum(position)>delNum){
                    commodityArrayList.get(position).updateNum(-delNum);
                    System.out.print("\tDelete " + queryCommodityName(position) + " successful! the rest number:"
                            + queryCommodityNum(position));
                    System.out.println();
                    isNum = true;
                }
            }catch (Exception ex){
                isNum = false;
            }
        }while (!isNum);
    }
    //更新商品
    public void updateCommodity(int position,int id, String name, Float price){
        commodityArrayList.get(position).updateId(id);
        commodityArrayList.get(position).updateName(name);
        commodityArrayList.get(position).updatePrice(price);
    }
    //根据id查找商品
    public int queryCommodityById(int id){
        int position = -1;//为-1时未找到到商品
        for(int i=0;i<commodityArrayList.size();i++){
            if(queryCommodityId(i)==id){
                position = i;
                break;
            }
        }
        return position;
    }
    //根据名称查找商品
    public int queryCommodityByName(String name){
        int position = -1;//为-1时未找到到商品
        for(int i=0;i<commodityArrayList.size();i++){
            if(queryCommodityName(i).equals(name)){
                position = i;
                break;
            }
        }
        return position;
    }
    //查询商品ID
    public int queryCommodityId(int position){
        return commodityArrayList.get(position).getId();
    }
    //查询商品名称
    public String queryCommodityName(int position){
        return commodityArrayList.get(position).getName();
    }
    //查询商品价格
    public Float queryCommodityPrice(int position){
        return commodityArrayList.get(position).getPrice();
    }
    //查询商品数量
    public int queryCommodityNum(int position){
        return commodityArrayList.get(position).getNum();
    }
    //刷新商品列表(除去数量为零商品)
    public void updateCommodityList(){
        for(int i=0;i<commodityArrayList.size();i++){
            if(queryCommodityNum(i)==0){
                commodityArrayList.remove(i);
            }
        }
    }
    //显示商品列表
    public void showCommodityList(int modecode){
        updateCommodityList();
        System.out.println("\n\t----------------------Commodity List------------------------");
        switch (modecode){
            //原始入库顺序
            case 201:
                for(int i=0;i<commodityArrayList.size();i++){
                    showCommodity(i,commodityArrayList);
                }
                break;
            //数量降序
            case 202:
                ArrayList<Commodity> templist_1 = commodityArrayList;
                for(int i=0;i<templist_1.size()-1;i++){
                    for(int j=0;j<templist_1.size()-1-i;j++){
                        if(templist_1.get(j).getNum()<templist_1.get(j+1).getNum()){
                            Commodity commodity = templist_1.get(j);
                            templist_1.set(j, templist_1.get(j+1));
                            templist_1.set(j+1, commodity);
                        }
                    }
                }
                for(int i=0;i<templist_1.size();i++){
                    showCommodity(i, templist_1);
                }
                break;
            //数量升序
            case 203:
                ArrayList<Commodity> templist_2 = commodityArrayList;
                for(int i=0;i<templist_2.size()-1;i++){
                    for(int j=0;j<templist_2.size()-1-i;j++){
                        if(templist_2.get(j).getNum()>templist_2.get(j+1).getNum()){
                            Commodity commodity = templist_2.get(j);
                            templist_2.set(j, templist_2.get(j+1));
                            templist_2.set(j+1, commodity);
                        }
                    }
                }
                for(int i=0;i<templist_2.size();i++){
                    showCommodity(i, templist_2);
                }
                break;
            //价格降序
            case 204:
                ArrayList<Commodity> templist_3 = commodityArrayList;
                for(int i=0;i<templist_3.size()-1;i++){
                    for(int j=0;j<templist_3.size()-1-i;j++){
                        if(templist_3.get(j).getPrice()<templist_3.get(j+1).getPrice()){
                            Commodity commodity = templist_3.get(j);
                            templist_3.set(j, templist_3.get(j+1));
                            templist_3.set(j+1, commodity);
                        }
                    }
                }
                for(int i=0;i<templist_3.size();i++){
                    showCommodity(i, templist_3);
                }
                break;
            //价格升序
            case 205:
                ArrayList<Commodity> templist_4 = commodityArrayList;
                for(int i=0;i<templist_4.size()-1;i++){
                    for(int j=0;j<templist_4.size()-1-i;j++){
                        if(templist_4.get(j).getPrice()>templist_4.get(j+1).getPrice()){
                            Commodity commodity = templist_4.get(j);
                            templist_4.set(j, templist_4.get(j+1));
                            templist_4.set(j+1, commodity);
                        }
                    }
                }
                for(int i=0;i<templist_4.size();i++){
                    showCommodity(i, templist_4);
                }
                break;
            default:
                break;
        }
    }
    //显示商品
    public void showCommodity(int position,ArrayList<Commodity> List){
        System.out.printf("\t");
        System.out.printf("%-12d",List.get(position).getId());
        System.out.printf("%-16s",List.get(position).getName());
        System.out.printf("%-12.2f",List.get(position).getPrice());
        System.out.printf("%-12d",List.get(position).getNum());
        System.out.println();
    }
}
