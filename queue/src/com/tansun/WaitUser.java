package com.tansun;

public class WaitUser implements Runnable{
    //��ǰ������̶߳���
    private Thread thread;
    //��ǰ�û���session����
    //private HttpSession session;
    //�����ж��߳��Ƿ����wait״̬
    private int whetherWait=0;
    //�û���ʵ�����
    //private Test test;

    @Override
    public void run() {
        //���߳�δ�ж�ʱ
        while(!Thread.interrupted()){
            experiment();
        }
    }
    
    /**
     *     ��ʵ����Ϣ�������ݿ�,�û���Ϣ��session��ȡ,��ʹ�õ��豸�Ӷ�����ɾ��,���豸�������session
     */
    public void experiment(){
        try {  
            //ȡ��һ���豸
            Equipment equipment=EquipmentQueue.equipment.take();
            EquipmentQueue.equipment.remove(equipment);
            WaitUser waitUser=EquipmentQueue.waitUsers.take();
            EquipmentQueue.waitUsers.remove(waitUser);
            //���豸���û���,״̬����Ϊ������
            //TestManager.manager.bindUserAndEquipment(equipment,waitUser);
        } catch (InterruptedException e) {  
            System.err.println("---" + e.getMessage());  
        } 
    }
    
    
   

    public int getWhetherWait() {
        return whetherWait;
    }

    public void setWhetherWait(int whetherWait) {
        this.whetherWait = whetherWait;
    }

   
    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

}

