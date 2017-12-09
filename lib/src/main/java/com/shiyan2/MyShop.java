package com.shiyan2;

import java.util.Scanner;

public class MyShop {


    public static void main(String args[]){
        CommodityGroup commodityGroup = new CommodityGroup();
        String isFinish = "n";
        do{
            System.out.println("\n\t\t_______________________________________________");
            System.out.println("\t\t|Welcome use MaxStudio inventory manage system|");
            System.out.println("\t\t-----------------------------------------------");
            System.out.println("\t\t\t\t\t1.Add commodity");
            System.out.println("\t\t\t\t\t2.Update commodity");
            System.out.println("\t\t\t\t\t3.Delete commodity");
            System.out.println("\t\t\t\t\t4.Check commodity list");
            System.out.println("\t*********************************************************");
            System.out.print("\tPlease input option number:");

            Scanner input = new Scanner(System.in);
            int cmdNum1 = input.nextInt();
            switch (cmdNum1){
                case 1:
                    System.out.print("\tPlease input Id:");
                    int id_add = input.nextInt();
                    System.out.print("\tPlease input Name:");
                    String name_add = input.next();
                    System.out.print("\tPlease input Price:");
                    float price_add = input.nextFloat();
                    Commodity commodity = new Commodity(id_add,name_add,price_add);
                    switch (commodityGroup.addCommodity(commodity)){
                        case 101:
                            int position_id = commodityGroup.queryCommodityById(id_add);
                            String name = commodityGroup.queryCommodityName(position_id);
                            System.out.print("\t"+id_add+" "+name+" is exist, do you want to add more? y/n ");
                            String answer_1 = input.next();
                            if(answer_1.equals("y")){
                                System.out.print("\tInput the numer you want to add:");
                                int num = input.nextInt();
                                commodityGroup.addCommodity(position_id,num);
                                System.out.print("\tSuccess! "+id_add+" "+name+" now number:"+
                                        commodityGroup.queryCommodityNum(position_id));
                                System.out.println();
                            }
                            break;
                        case 102:
                            int position_name = commodityGroup.queryCommodityByName(name_add);
                            int id = commodityGroup.queryCommodityId(position_name);
                            System.out.print("\t"+id_add+" "+id+" is exist, do you want to add more? y/n ");
                            String answer_2 = input.next();
                            if(answer_2.equals("y")){
                                System.out.print("\tInput the numer you want to add:");
                                int num = input.nextInt();
                                commodityGroup.addCommodity(position_name,num);
                                System.out.print("\tSuccess! "+id_add+" "+id+" now number:"+
                                        commodityGroup.queryCommodityNum(position_name));
                                System.out.println();
                            }
                            break;
                        case 103:
                            System.out.print("\tAdd "+name_add+" success!");
                            System.out.println();
                            break;
                    }
                    System.out.println("\t*********************************************************");
                    System.out.print("\tInput anything to continue:");
                    String cont1 = input.next();
                    break;
                case 2:
                    System.out.print("\tInput the id or name:");
                    String type_update = input.next();
                    boolean isName=true;
                    try{
                        int id = Integer.parseInt(type_update);
                        isName=false;
                    }catch (Exception ex){
                        isName=true;
                    }
                    if(isName){
                        int position_update = commodityGroup.queryCommodityByName(type_update);
                        int stateCode = commodityGroup.queryCommodityByName(type_update);
                        if(stateCode==-1){
                            System.out.print("\tCan't found " + type_update);
                            System.out.println();
                        }else {
                            System.out.print("\tPlease input new Id:");
                            int id_update = input.nextInt();
                            System.out.print("\tPlease input new Name:");
                            String name_update = input.next();
                            System.out.print("\tPlease input new Price:");
                            float price_update = input.nextFloat();
                            if(commodityGroup.queryCommodityById(id_update)==-1
                                    &&commodityGroup.queryCommodityByName(name_update)==-1){
                                commodityGroup.updateCommodity(position_update,id_update,name_update,price_update);
                                System.out.print("\tUpdate commodity successful!");
                                System.out.println();
                            }else {
                                System.out.print("\tId or Name is exited!");
                                System.out.println();
                            }
                        }
                    }else if(!isName){
                        int id_forUpdate = Integer.parseInt(type_update);
                        int position_update = commodityGroup.queryCommodityById(id_forUpdate);
                        int stateCode = commodityGroup.queryCommodityById(id_forUpdate);
                        if(stateCode==-1){
                            System.out.print("\tCan't found " + id_forUpdate);
                            System.out.println();
                        }else {
                            System.out.print("\tPlease input new Id:");
                            int id_update = input.nextInt();
                            System.out.print("\tPlease input new Name:");
                            String name_update = input.next();
                            System.out.print("\tPlease input new Price:");
                            float price_update = input.nextFloat();
                            if(commodityGroup.queryCommodityById(id_update)==-1
                                    &&commodityGroup.queryCommodityByName(name_update)==-1){
                                commodityGroup.updateCommodity(position_update,id_update,name_update,price_update);
                                System.out.print("\tUpdate commodity successful!");
                                System.out.println();
                            }else {
                                System.out.print("\tId or Name is exited!");
                                System.out.println();
                            }
                        }
                    }
                    System.out.println("\t*********************************************************");
                    System.out.print("\tInput anything to continue:");
                    String cont2 = input.next();
                    break;
                case 3:
                    System.out.println("\tInput the id or name:");
                    String type_delete = input.next();
                    boolean isID=false;
                    try{
                        int id = Integer.parseInt(type_delete);
                        isID=true;
                    }catch (Exception ex){
                        isID=false;
                    }
                    if(!isID){
                        int position_delete = commodityGroup.queryCommodityByName(type_delete);
                        int stateCode = commodityGroup.queryCommodityByName(type_delete);
                        if(stateCode==-1){
                            System.out.print("\tCan't found " + type_delete);
                            System.out.println();
                        }else {
                            commodityGroup.deleteCommodity(position_delete);
                        }
                    }else if(isID){
                        int id_Delete = Integer.parseInt(type_delete);
                        int position_fordelete = commodityGroup.queryCommodityById(id_Delete);
                        int stateCode = commodityGroup.queryCommodityById(id_Delete);
                        if(stateCode==-1){
                            System.out.print("\tCan't found " + id_Delete);
                            System.out.println();
                        }else {
                           commodityGroup.deleteCommodity(position_fordelete);
                        }
                    }
                    System.out.println("\t*********************************************************");
                    System.out.print("\tInput anything to continue:");
                    String cont3 = input.next();
                    break;
                case 4:
                    System.out.println("\n\t\t\t\t\t1.Check all commodity");
                    System.out.println("\t\t\t\t\t2.Check commodity by number descending order");
                    System.out.println("\t\t\t\t\t3.Check commodity by number ascending order");
                    System.out.println("\t\t\t\t\t4.Check commodity by price descending order");
                    System.out.println("\t\t\t\t\t5.Check commodity by price ascending order");
                    System.out.println("\t*********************************************************");
                    System.out.print("\tPlease input option number:");
                    int cmd_list = input.nextInt();
                    switch (cmd_list) {
                        case 1:
                            commodityGroup.showCommodityList(201);
                            break;
                        case 2:
                            commodityGroup.showCommodityList(202);
                            break;
                        case 3:
                            commodityGroup.showCommodityList(203);
                            break;
                        case 4:
                            commodityGroup.showCommodityList(204);
                            break;
                        case 5:
                            commodityGroup.showCommodityList(205);
                            break;
                        default:
                            break;
                    }
                    System.out.println("\t*********************************************************");
                    System.out.print("\tInput anything to continue:");
                    String cont4 = input.next();
                    break;
                default:
                    break;
            }
        }while (isFinish == "n");
    }
}
