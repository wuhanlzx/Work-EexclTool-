package com.tansun;

public class WaitUser implements Runnable{
    //当前请求的线程对象
    private Thread thread;
    //当前用户的session对象
    //private HttpSession session;
    //用于判断线程是否进入wait状态
    private int whetherWait=0;
    //用户的实验对象
    //private Test test;

    @Override
    public void run() {
        //当线程未中断时
        while(!Thread.interrupted()){
            experiment();
        }
    }
    
    /**
     *     将实验信息存入数据库,用户信息从session获取,将使用的设备从队列中删除,将设备对象存入session
     */
    public void experiment(){
        try {  
            //取出一个设备
            Equipment equipment=EquipmentQueue.equipment.take();
            EquipmentQueue.equipment.remove(equipment);
            WaitUser waitUser=EquipmentQueue.waitUsers.take();
            EquipmentQueue.waitUsers.remove(waitUser);
            //将设备与用户绑定,状态设置为试验中
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

