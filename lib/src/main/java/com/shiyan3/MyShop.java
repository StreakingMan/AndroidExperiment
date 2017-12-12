package com.shiyan3;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class MyShop {
    private static CommodityGroup commodityGroup = new CommodityGroup();

    public static void main(String args[]){
        // 显示应用 GUI
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                IndexLayout indexLayout = new IndexLayout();
            }
        });

    }


    public static class IndexLayout extends JFrame{
        private JTable jTable;
        private DefaultTableModel tableModel;
        private int temp_id;
        private String temp_name;
        private float temp_price;
        private int temp_num;
        private boolean isReadSuccess = false;
        private JTextField tf_id;
        private JTextField tf_name;
        private JTextField tf_price;
        private JTextField tf_num;

        public IndexLayout(){
            //添加组件
            //主操作区
            JPanel jPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JButton Add = new JButton("Add commodity");
            JButton Update = new JButton("Update commodity");
            JButton Delete = new JButton("Delete commodity");
            JButton Check = new JButton("Check commodity");
            jPanel1.add(Add);
            jPanel1.add(Update);
            jPanel1.add(Delete);
            jPanel1.add(Check);
            this.add(jPanel1,BorderLayout.NORTH);
            //当前操作项
            JPanel jPanel3 = new JPanel(new GridLayout(2,1));
            JPanel jPanel2 = new JPanel(new GridLayout(2,4));
            JLabel id = new JLabel("Id",JLabel.CENTER);
            JLabel name = new JLabel("Name",JLabel.CENTER);
            JLabel price = new JLabel("Price",JLabel.CENTER);
            JLabel num = new JLabel("Num",JLabel.CENTER);
            tf_id = new JTextField(20);
            tf_name = new JTextField(20);
            tf_price = new JTextField(20);
            tf_num = new JTextField(20);
            jPanel2.add(id);
            jPanel2.add(name);
            jPanel2.add(price);
            jPanel2.add(num);
            jPanel2.add(tf_id);
            jPanel2.add(tf_name);
            jPanel2.add(tf_price);
            jPanel2.add(tf_num);
            jPanel3.add(jPanel2);
            JLabel list = new JLabel("Commodities List",JLabel.CENTER);
            jPanel3.add(list);
            this.add(jPanel3,BorderLayout.CENTER);

            //商品列表
            JPanel jPanel4 = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JPanel jPanel5 = new JPanel(new GridLayout(1,5));
            JButton def = new JButton("Default order");
            JButton num_desc = new JButton("Num-desc order");
            JButton num_asc = new JButton("Num-asc order");
            JButton price_desc = new JButton("Price-desc order");
            JButton price_asc = new JButton("Price-asc order");
            jPanel5.add(def);
            jPanel5.add(num_desc);
            jPanel5.add(num_asc);
            jPanel5.add(price_desc);
            jPanel5.add(price_asc);
            final Object[][] commodityList = null;
            String[] title = { "Id", "Name", "Price", "Num",};
            tableModel = new DefaultTableModel(commodityList,title);
            jTable = new JTable(tableModel);
            jTable.setPreferredScrollableViewportSize(new Dimension(550, 300));
            JScrollPane jScrollPane = new JScrollPane(jTable);
            jPanel4.add(jPanel5);
            jPanel4.add(jScrollPane);
            this.add(jPanel4,BorderLayout.SOUTH);

            //设置窗体属性
            this.setTitle("Easy Shop Management System");
            this.setSize(650,500);
            this.setLocation(600,300);
            this.setVisible(true);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //功能实现
            //点击列表改变操作区
            jTable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent mouseEvent) {
                    super.mouseClicked(mouseEvent);
                    int row = jTable.getSelectedRow();
                    //暂存
                    temp_id = Integer.parseInt((String)jTable.getValueAt(row,0));
                    temp_name = (String)jTable.getValueAt(row,1);
                    temp_price = Float.parseFloat((String)jTable.getValueAt(row,2));
                    temp_num = Integer.parseInt((String)jTable.getValueAt(row,3));
                    tf_id.setText((String)jTable.getValueAt(row,0));
                    tf_name.setText((String)jTable.getValueAt(row,1));
                    tf_price.setText((String)jTable.getValueAt(row,2));
                    tf_num.setText((String)jTable.getValueAt(row,3));
                }
            });

            //添加商品
            Add.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    judgeEdit();
                    if(isReadSuccess){
                        int id = Integer.parseInt(tf_id.getText().toString());
                        String name = tf_name.getText().toString();
                        float price = Float.parseFloat(tf_price.getText().toString());
                        int num = Integer.parseInt(tf_num.getText().toString());
                        Commodity commodity = new Commodity(id,name,price,num);
                        switch (commodityGroup.addCommodity(commodity)){
                            case 101:
                                JOptionPane.showConfirmDialog(null, "ID is exist", "Add Failed", JOptionPane.YES_OPTION);
                                break;
                            case 102:
                                JOptionPane.showConfirmDialog(null, "Name is exist", "Add Failed", JOptionPane.YES_OPTION);
                                break;
                            case 103:
                                break;
                        }
                        showList();
                    }
                }
            });

            //更新商品
            Update.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    judgeEdit();
                    if(isReadSuccess){
                        int position = commodityGroup.queryCommodityById(temp_id);
                        if(position==-1){
                            JOptionPane.showConfirmDialog(null,
                                    "Please select the commodity",
                                    "Update Failed", JOptionPane.YES_OPTION);
                        }else {
                            int id = Integer.parseInt(tf_id.getText().toString());
                            String name = tf_name.getText().toString();
                            float price = Float.parseFloat(tf_price.getText().toString());
                            int num = Integer.parseInt(tf_num.getText().toString());
                            commodityGroup.updateCommodity(position,id,name,price,num);
                            showList();
                        }
                    }
                }
            });

            //删除商品
            Delete.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    int position_id = commodityGroup.queryCommodityById(Integer.parseInt(tf_id.getText().toString()));
                    int position_name = commodityGroup.queryCommodityByName(tf_name.getText().toString());
                    if(position_id==-1){
                        JOptionPane.showConfirmDialog(null,
                                "Please select the commodity",
                                "Delete Failed", JOptionPane.YES_OPTION);
                    }else {
                        if(position_name==-1){
                            JOptionPane.showConfirmDialog(null,
                                    "Please select the commodity",
                                    "Delete Failed", JOptionPane.YES_OPTION);
                        }else {
                            commodityGroup.deleteCommodity(position_id);
                            showList();
                        }
                    }
                }
            });

            //查询商品
            Check.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    ArrayList<Commodity> checkList = new ArrayList<>();
                    //清空
                    DefaultTableModel tableModel = (DefaultTableModel)jTable.getModel();
                    while(tableModel.getRowCount()>0){
                        tableModel.removeRow(tableModel.getRowCount()-1);
                    }
                    if(tf_id.getText().length()>0){
                        int position = commodityGroup.queryCommodityById(Integer.parseInt(tf_id.getText().toString()));
                        if(position!=-1){
                            checkList.add(CommodityGroup.getList().get(position));
                        }else {
                            JOptionPane.showConfirmDialog(null,
                                    "ID is not exist",
                                    "Query Failed", JOptionPane.YES_OPTION);
                        }
                    }
                    if(tf_name.getText().length()>0){
                        int position = commodityGroup.queryCommodityByName(tf_name.getText().toString());
                        if(position!=-1){
                            checkList.add(CommodityGroup.getList().get(position));
                        }else {
                            JOptionPane.showConfirmDialog(null,
                                    "Name is not exist",
                                    "Query Failed", JOptionPane.YES_OPTION);
                        }
                    }
                    if(checkList.size()>0){
                        for(int i=0;i<checkList.size();i++){
                            Object[] o ={"","","",""};
                            for(int j=0;j<4;j++){
                                switch (j){
                                    case 0:
                                        o[0] = Integer.toString(checkList.get(i).getId());
                                        break;
                                    case 1:
                                        o[1] = checkList.get(i).getName();
                                        break;
                                    case 2:
                                        o[2] = Float.toString(checkList.get(i).getPrice());
                                        break;
                                    case 3:
                                        o[3] = Integer.toString(checkList.get(i).getNum());
                                        break;
                                    default:
                                        break;
                                }
                            }
                            tableModel.addRow(o);
                        }
                        tableModel.fireTableDataChanged();
                    }
                }
            });

            //商品排序
            def.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    showList();
                }
            });
            num_desc.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    orderList(202);
                }
            });
            num_asc.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    orderList(203);
                }
            });
            price_desc.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    orderList(204);
                }
            });
            price_asc.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    orderList(205);
                }
            });

        }

        //判断输入框
        private void judgeEdit(){
            if(tf_id.getText().length()!=0){
                if(tf_name.getText().length()!=0){
                    if(tf_price.getText().toString().length()!=0){
                        if( Float.parseFloat(tf_price.getText().toString())>0){
                            if(tf_num.getText().length()!=0){
                                if(Integer.parseInt(tf_num.getText().toString())>0){
                                    isReadSuccess = true;
                                }else {
                                    JOptionPane.showConfirmDialog(null, "Num must bigger than 0", "Add Failed", JOptionPane.YES_OPTION);
                                }
                            }else {
                                JOptionPane.showConfirmDialog(null, "Have no Num", "Add Failed", JOptionPane.YES_OPTION);
                            }
                        }else {
                            JOptionPane.showConfirmDialog(null, "Price must bigger than 0", "Add Failed", JOptionPane.YES_OPTION);
                        }
                    }else {
                        JOptionPane.showConfirmDialog(null, "Have no Price", "Add Failed", JOptionPane.YES_OPTION);
                    }
                }else {
                    JOptionPane.showConfirmDialog(null, "Have no Name", "Add Failed", JOptionPane.YES_OPTION);
                }
            }else {
                JOptionPane.showConfirmDialog(null, "Have no ID", "Add Failed", JOptionPane.YES_OPTION);
            }
        }

        //列表显示
        private void showList(){
            //清空
            DefaultTableModel tableModel = (DefaultTableModel)jTable.getModel();
            while(tableModel.getRowCount()>0){
                tableModel.removeRow(tableModel.getRowCount()-1);
            }
            if(CommodityGroup.getList().size()>0){
                for(int i=0;i<CommodityGroup.getList().size();i++){
                    Object[] o ={"","","",""};
                    for(int j=0;j<4;j++){
                        switch (j){
                            case 0:
                                o[0] = Integer.toString(CommodityGroup.getList().get(i).getId());
                                break;
                            case 1:
                                o[1] = CommodityGroup.getList().get(i).getName();
                                break;
                            case 2:
                                o[2] = Float.toString(CommodityGroup.getList().get(i).getPrice());
                                break;
                            case 3:
                                o[3] = Integer.toString(CommodityGroup.getList().get(i).getNum());
                                break;
                            default:
                                break;
                        }
                    }
                    tableModel.addRow(o);
                }
                tableModel.fireTableDataChanged();
            }
        }

        //列表排序
        private void orderList(int mode){
            ArrayList<Commodity> tempList;
            tempList = commodityGroup.showCommodityList(mode);
            //清空
            DefaultTableModel tableModel = (DefaultTableModel)jTable.getModel();
            while(tableModel.getRowCount()>0){
                tableModel.removeRow(tableModel.getRowCount()-1);
            }
            if(tempList.size()>0){
                for(int i=0;i<tempList.size();i++){
                    Object[] o ={"","","",""};
                    for(int j=0;j<4;j++){
                        switch (j){
                            case 0:
                                o[0] = Integer.toString(tempList.get(i).getId());
                                break;
                            case 1:
                                o[1] = tempList.get(i).getName();
                                break;
                            case 2:
                                o[2] = Float.toString(tempList.get(i).getPrice());
                                break;
                            case 3:
                                o[3] = Integer.toString(tempList.get(i).getNum());
                                break;
                            default:
                                break;
                        }
                    }
                    tableModel.addRow(o);
                }
                tableModel.fireTableDataChanged();
            }
        }
    }
}
