package com.ysw.fanfou.fan;

import com.ysw.fanfou.demo.txttest;

public class Team {
	
	public static void main(String[] args) {
		Team[] t=getTeam();
		for(int i=0;i<5;i++) {
			System.out.println("\n球队名："+t[i].name);
			for(int j=0;j<10;j++) {
				String name=t[i].players[j].getName();
				int a=t[i].players[j].getA();
				int p=t[i].players[j].getP();
				int d=t[i].players[j].getD();
				System.out.println("球员姓名："+name+"\t基础进攻："+a+"\t转换进攻："+p+"\t防守："+d);
			}
		}
		//成功取到球员信息。
	}
	public String name;
	public Player[] players;
	public int[][] lineUp;
	
	public int[][] getLineUp() {
		return lineUp;
	}

	public void setLineUp(int[][] lineUp) {
		this.lineUp = lineUp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Player[] getPlayers() {
		return players;
	}

	public void setPlayers(Player[] players) {
		this.players = players;
	}

	public static Team getTeams(String tname) {
		Team team=new Team();
		String t1=txttest.basketballfile().split("&")[3];
		String p1=t1.split("\n")[3];
		String pn1=p1.split(" ")[0];
		System.out.println(pn1);
		return team;
	}
	public static Team[] getTeam() {
		String txt=txttest.basketballfile();
		//System.out.println(txt);
		Team[] teams=new Team[30];
		for(int i=1;i<=teams.length;i++) {
			String tx=txt.split("&")[i];//第0个舍去
			//System.out.println(tx);
			String tname=tx.split("\n")[0];//第一个的位置为队名
			//System.out.println(tname);
			teams[i-1]=new Team();
			teams[i-1].setName(tname);//获取队名
			//System.out.println(teams[i-1].name);
			Player[] players=new Player[10];//声明Players数组
			for(int j=1;j<=10;j++) {
				String t=tx.split("\n")[j];
				players[j-1]=new Player();
				players[j-1].setName(t.split(" ")[0]);//0位置为球员名
				players[j-1].setA(Integer.valueOf(t.split(" ")[1]));//1位置为基础攻击
				players[j-1].setP(Integer.valueOf(t.split(" ")[2]));//2位置为转换攻击
				players[j-1].setD(Integer.valueOf(t.split(" ")[3]));//3位置为防守
			}
			
			teams[i-1].setPlayers(players);
			int[][] lineUp=new int[12][5];//声明lineUp数组
			
			
			teams[i-1].setLineUp(lineUp);
		}
		return teams;
	}
}
