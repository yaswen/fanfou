package com.ysw.fanfou.fan;

public class Player {
	private String name;
	private int a;
	private int p;
	private int d;
	/*新建球员方法*/
	public Player() {
	}
	public Player(String name) {
		//super();
		this.name=name;
		this.a=0;
		this.p=1;
		this.d=1;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getA() {
		return a;
	}
	public void setA(int a) {
		this.a = a;
	}
	public int getP() {
		return p;
	}
	public void setP(int p) {
		this.p = p;
	}
	public int getD() {
		return d;
	}
	public void setD(int d) {
		this.d = d;
	}
	
}
