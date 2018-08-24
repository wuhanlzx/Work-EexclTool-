package com.tansun;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 初始化队列及线程池
 * @author yangc
 *
 */
public class EquipmentQueue {
    //设备队列
    public static BlockingQueue<Equipment> equipment;
    //请求队列
    public static BlockingQueue<WaitUser> waitUsers;
    //线程池
    public static ExecutorService exec;
    
    /**
     * 初始化设备、请求队列及线程池
     */
    public void initEquipmentAndUsersQueue(){
        exec = Executors.newCachedThreadPool(); 
        equipment=new LinkedBlockingQueue<Equipment>();
        //将空闲的设备放入设备队列中
        setFreeDevices();
        waitUsers=new LinkedBlockingQueue<WaitUser>();
    }
    
    /**
     * 将空闲的设备放入设备队列中
     * @param exec
     */
    private void setFreeDevices() {
        //获取可用的设备
        List<Equipment> equipments=getFreeEquipment();
        for (int i = 0; i < equipments.size(); i++) {
        	Equipment  dc=equipments.get(i);
            try {
                equipment.put(dc);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 获取可用的设备（从数据库中查出可用的设备）
     * @return
     */
    public  List<Equipment> getFreeEquipment(){
        return QuipPartsManager.manager.getFreeEquipment();
    }
}