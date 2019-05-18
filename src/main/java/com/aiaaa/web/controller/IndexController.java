package com.aiaaa.web.controller;

import io.swagger.annotations.Api;
import org.junit.runner.RunWith;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.sound.midi.Soundbank;
import java.io.File;
import java.util.Collections;

@Controller
@RequestMapping("/")
public class IndexController {

    private static Logger logger =  LoggerFactory.getLogger(IndexController.class);

    @RequestMapping(value = {"/","/index"})
    public String index(Model model){
        logger.info("this is index");
        model.addAttribute("name","dandan");
        return "index";
    }


    public static void main1(String[] args) {
        //printFile(new File("D:\\java"));

        //System.out.println(Integer.toBinaryString(-5));


    }

    /**
     * 打印指定路径下的文件名
     * @param file
     */
    private static void printFile(File file){
        File[] listFile = file.listFiles();
        if (listFile != null){
            for (File item:listFile) {
                if (item.isDirectory()){
                    printFile(item);
                }
                else{
                    System.out.println(item.getName());
                }
            }
        }
    }

    public static void main(String[] args) {
        Fruit f = new AppleFactory().doCreate(new AppleShow());
        //Fruit apple = new AppleFactory().create();
        //Fruit f = SimpleFactory.getFruit("banana");
        System.out.println(f.name);

    }


}
abstract class Fruit{
    String name;
}
class Apple extends  Fruit{
    public Apple(){
        super();
        this.name = "apple";
    }
}
class Banana extends  Fruit{
    public Banana(){
        super();
        this.name = "banana";
    }
}

interface Inshow{
    void show();
}

class AppleShow implements Inshow{
    @Override
    public void show() {
        System.out.println("开始生产！");
    }
}

/**
 * 抽象工厂
 */
abstract class FruitFactory{

    private Inshow inshow;

    public Fruit doCreate(Inshow inshow){
        inshow.show();
        return this.create();
    }

    abstract Fruit create();
}
class AppleFactory extends  FruitFactory{

    @Override
    Fruit create() {
        return new Apple();
    }
}
class BananaFactory extends  FruitFactory{

    @Override
    Fruit create() {
        return new Banana();
    }
}

/**
 * 简单工厂
 */
class SimpleFactory{

    public static Fruit getFruit(String type){
        Fruit res =null;
        //处理
        System.out.println("开始生产！");
        switch (type){
            case "apple":res = new Apple();break;
            case "banana":res = new Banana();break;
            default:break;
        }
        System.out.println("结束生产！");
        return res;
    }

}



