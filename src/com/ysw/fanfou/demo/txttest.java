package com.ysw.fanfou.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Calendar;
import java.util.Date;

import com.ysw.fanfou.fan.Status;
import com.ysw.fanfou.fan.Team;

public class txttest {
    /**
     * 读取txt文件的内容
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
    
    public static void main(String[] args){
    	Status.UpdateStatus("测试消息热火vs骑士，太阳vs公牛，奇才vs森林狼，掘金vs爵士 ，雄鹿vs国王，");
        //File file = new File("/root/tomcat8.5/webapps/Craft/pages/1.txt");
        //System.out.println(txt2String(file));
    	doSchedule();
        
    }
    public static String basketballfile() {
    	File file = new File("/root/tomcat8.5/webapps/Craft/pages/1.txt");
    	//File file = new File("WebContent/pages/1.txt");
    	return txt2String(file);
    }
    public static String schedulefile() {
    	File file = new File("/root/tomcat8.5/webapps/Craft/playbasketball/2.txt");
    	//File file = new File("WebContent/playbasketball/2.txt");
    	return txt2String(file);
    }
    @SuppressWarnings("deprecation")
	public static void doSchedule() {
		
		Date today = new Date();  
        Calendar c = Calendar.getInstance();  
        c.setTime(today);  
        c.add(Calendar.DAY_OF_MONTH, 1);// 今天+1天  
        Date tomorrow = c.getTime();
		int y=tomorrow.getYear()+1900;
		int m=tomorrow.getMonth()+1;
		int d=tomorrow.getDate();
		System.out.println("明天是"+y+"年"+m+"月"+d+"日");
		String fansche0="明天是"+y+"年"+m+"月"+d+"日。NBA有";
		int scheCount=0;
		String fansche1="场比赛，分别是：";
		String fansche2="一场一场来模拟：";
		String txt=txttest.schedulefile();
		String[] tx=txt.split("\r\n");
		//System.out.println(tx[0]);
		//System.out.println(tx[1]);
		//System.out.println(tx[2]);
		String[] sche=new String[30];
		int s=0;
		for(int i=2;i<=1230;i++) {
			if(tx[i].split("\t")[0].equals(y+"/"+m+"/"+d)) {
				sche[s]=tx[i].split("\t")[6]+"vs"+tx[i].split("\t")[7];
				s++;
			}
		}
		for(int i=0;i<30;i++) {
			if(null!=sche[i]) {
				scheCount++;
				int ke=Integer.parseInt(sche[i].split("vs")[0]);
				int zhu=Integer.parseInt(sche[i].split("vs")[1]);
				String tma=Team.getTeam()[ke].getName();
				String tmb=Team.getTeam()[zhu].getName();
				fansche1+=tma+"vs"+tmb+"，";
			}
		}
		String fs=fansche0+scheCount+fansche1+fansche2;
		System.out.println(fs);
		Status.UpdateStatus(fs);
		try {
			Thread.sleep(1000);//==========间隔十秒钟
		} catch (InterruptedException e) {
			//
			e.printStackTrace();
		}
		for(int i=0;i<30;i++) {
			if(null!=sche[i]) {
				//System.out.println("比赛"+sche[i]);
				int ke=Integer.parseInt(sche[i].split("vs")[0]);
				int zhu=Integer.parseInt(sche[i].split("vs")[1]);
				System.out.println(ke+"对"+zhu);
				//playGame(ke,zhu);//运行比赛
				
				try {
					Thread.sleep(1000);//==========间隔两分钟运行下一场比赛
				} catch (InterruptedException e) {
					//
					e.printStackTrace();
				}
			}
		}
		
		
	}
}