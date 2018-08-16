package com.ysw.fanfou.demo;

import com.ysw.fanfou.demo.UTFDemo;
public class rand {
	public static void main(String[] args) { 
		quming("杨");
	}
	/**
	 * 随机两个汉字作为名字，与xing拼接成随机姓名
	 * @param xing
	 */
	public static void quming(String xing){
		for(int y=0;y<100;y++){
			System.out.print(xing+randStr()+randStr()+"\t");
			if(y%10==9){
				System.out.println();
				System.out.println();
			}
			}
	}
	public static String quming1(String xing){
		
			return (xing+randStr()+randStr()+"\t");
			
			
	}
	/**
	 * 随机输出1000个常用汉字
	 */
	public static void rand1000(){
		for(int i=0;i<1000;i++){ 
			String ss=randStr();
			
			System.out.print(ss);
			if(i%33==32)
				System.out.println();
		}
	}
	/**
	 * 随机输出一个常用汉字
	 * 由于random的使用，貌似现在还没有涵盖所有汉字。
	 * @return 一个常用汉字
	 */
	public static String randStr(){
		java.util.Random r=new java.util.Random();
		int t=r.nextInt();
		int t2=r.nextInt();
		int a=0xAf;
		int b=0xA0;
		int tt=t%20+20+a;
		int tt2=t2%47+47+b;
		String s=UTFDemo.gbk(tt*0x100+tt2);
		//System.out.println(s+" "+Integer.toHexString(tt)+" "+Integer.toHexString(tt2)+" "+t2%47); 

		return s;
	}
}
