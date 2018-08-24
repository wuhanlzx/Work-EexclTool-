package com.tansun;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * ��ʼ�����м��̳߳�
 * @author yangc
 *
 */
public class EquipmentQueue {
    //�豸����
    public static BlockingQueue<Equipment> equipment;
    //�������
    public static BlockingQueue<WaitUser> waitUsers;
    //�̳߳�
    public static ExecutorService exec;
    
    /**
     * ��ʼ���豸��������м��̳߳�
     */
    public void initEquipmentAndUsersQueue(){
        exec = Executors.newCachedThreadPool(); 
        equipment=new LinkedBlockingQueue<Equipment>();
        //�����е��豸�����豸������
        setFreeDevices();
        waitUsers=new LinkedBlockingQueue<WaitUser>();
    }
    
    /**
     * �����е��豸�����豸������
     * @param exec
     */
    private void setFreeDevices() {
        //��ȡ���õ��豸
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
     * ��ȡ���õ��豸�������ݿ��в�����õ��豸��
     * @return
     */
    public  List<Equipment> getFreeEquipment(){
        return QuipPartsManager.manager.getFreeEquipment();
    }
}