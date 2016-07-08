package com.eshore.ecloud.monkey_dog;

import java.io.FileReader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        ScriptEngineManager sem = new ScriptEngineManager();
        ScriptEngine se = sem.getEngineByName("javascript");
        try {
            FileReader reader = new FileReader("E:\\git\\projects\\monkey_dog\\src\\main\\resources\\md5.js");
            se.eval(reader);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            // 调用直接JAVASCRIPT语句
            String tmpstr = "www.newrank.cn/xdnphb/list/day/rank?AppKey=joker&end=2016-06-28&rank_name=时事&rank_name_group=资讯&start=2016-06-28&nonce=d0fddeb72";
            // 调用无参数方法JAVASCRIPT函数
            se.eval("function sayHello() {" + " print('Hello '+strname+'!');return 'my name is '+strname;" + "}");
            Invocable invocableEngine = (Invocable) se;
            se.put("strname", "testname");
            String callbackvalue = (String) invocableEngine.invokeFunction("sayHello");
            System.out.println(callbackvalue);

            // 调用有参数JAVASCRIPT函数
            se.eval("function sayHello2(strname2) {" + " print('Hello '+strname+'!');return 'my name is '+strname2;" + "}");
            callbackvalue = (String) invocableEngine.invokeFunction("MD5", tmpstr);
            System.out.println(callbackvalue);

        } catch (ScriptException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
