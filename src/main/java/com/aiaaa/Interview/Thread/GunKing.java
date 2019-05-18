package com.aiaaa.Interview.Thread;

import javax.sound.midi.Soundbank;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

/**
 * Gun King
 */
public class GunKing {
    /**
     * max capacity
     */
    private static final Integer MAX_CAPACITY = 20;
    private static final Integer SLEEP_TIME = 1000;
    /**
     * bullet list
     */
    private static LinkedList<Bullet> bullets = new LinkedList<>();

    /**
     * bullet
     */
    static class Bullet {
        //bullet code
        private String code;

        public Bullet() {
            this.code = getCode();
            System.out.println("子弹编号：" + this.code + "产生了！");
        }

        private String getCode() {
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            return "BU" + df.format(new Date()) + (100 + (int) (100 * Math.random()));
        }

        private void Bomb() {
            System.out.println("Bomb!子弹编号：" + this.code + "爆炸了！");
        }
    }

    /**
     * provider
     */
    static class Provider implements Runnable {
        private String name;
        public Provider(String name) {
            this.name = name;
        }

        /**
         * push bullet
         */
        private void push() {
            bullets.add(new Bullet());
            System.out.println("线程："+this.name+"--->弹夹余量：" + bullets.size());
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                }

                synchronized (bullets) {
                    while (bullets.size() >= MAX_CAPACITY) {
                        try {
                            bullets.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    this.push();
                    bullets.notifyAll();
                }
            }
        }
    }

    /**
     * consumer
     */
    static class Consumer implements Runnable {
        private String name;
        public Consumer(String name) {
            this.name = name;
        }

        /**
         * shot
         */
        private void shot() {
            Bullet re = bullets.remove();
            re.Bomb();
            System.out.println("线程："+this.name+"--->弹夹余量：" + bullets.size());
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                }
                synchronized (bullets) {
                    while (bullets.isEmpty()) {
                        try {
                            bullets.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    this.shot();
                    bullets.notifyAll();
                }
            }
        }
    }


    public static void main(String[] args) {
        for (int i = 0; i < 12; i++) {
            Provider p = new Provider("TH-P" + i);
            new Thread(p).start();
        }
        for (int i = 0; i < 10; i++) {
            Consumer c = new Consumer("TH-C" + i);
            new Thread(c).start();
        }
    }
}
