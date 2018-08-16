package com.ysw.fanfou.demo;
 
import javax.servlet.ServletContext;  
import javax.servlet.ServletContextEvent;  
import javax.servlet.ServletContextListener;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;

import com.ysw.fanfou.fan.PlayBasketball;
   
/** 
 * Web应用监听器 
 */  
public class MyListener implements ServletContextListener {  
   
    // 应用监听器的销毁方法  
    public void contextDestroyed(ServletContextEvent event) {  
        ServletContext sc = event.getServletContext();  
        
        // 在整个web应用销毁之前调用，将所有应用空间所设置的内容清空  
        sc.removeAttribute("dataSource");  
        System.out.println("销毁工作完成...");  
        
    }  
   
    // 应用监听器的初始化方法  
    public void contextInitialized(ServletContextEvent event) {  
        // 通过这个事件可以获取整个应用的空间  
        // 在整个web应用下面启动的时候做一些初始化的内容添加工作  
        ServletContext sc = event.getServletContext();  
        // 设置一些基本的内容；比如一些参数或者是一些固定的对象  
        // 创建DataSource对象，连接池技术 dbcp  
        BasicDataSource bds = new BasicDataSource();  
        bds.setDriverClassName("oracle.jdbc.driver.OracleDriver");  
        bds.setUrl("jdbc:oracle:thin://localhost:1521/orcl");  
        bds.setUsername("ga");  
        bds.setPassword("1000xun");  
        bds.setMaxIdle(5);//最大管理数  
        //bds.setMaxWait(maxWait); 最大等待时间  
        // 把 DataSource 放入ServletContext空间中，  
        // 供整个web应用的使用(获取数据库连接)  
        sc.setAttribute("dataSource", bds);  
        System.out.println("应用监听器初始化工作完成...");  
        System.out.println("已经创建DataSource...");  
        PlayBasketball.main(null);
    }  
}  