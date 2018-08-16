package com.ysw.fanfou.fan;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import com.ysw.fanfou.demo.txttest;

public class PlayBasketball {

	public static void main(String[] args) {
		System.out.println("启动篮球模拟器程序!");
		//playGame(3,0);
		while(true) {
			Date today = new Date();  
			int h=today.getHours();
			int m=today.getMinutes();
			//System.out.println(h+"---"+m);
			if(h==21&&m==0) {
				doSchedule();
				try {
					Thread.sleep(61000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}		
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
		String[] tx=txt.split("\n");
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
		Status.UpdateStatus(fansche0+scheCount+fansche1+fansche2);
		try {
			Thread.sleep(30000);//==========间隔十秒钟
		} catch (InterruptedException e) {
			//
			e.printStackTrace();
		}
		for(int i=0;i<30;i++) {
			if(null!=sche[i]) {
				//System.out.println("比赛"+sche[i]);
				int ke=Integer.parseInt(sche[i].split("vs")[0]);
				int zhu=Integer.parseInt(sche[i].split("vs")[1]);
				
				playGame(ke,zhu);//运行比赛
				
				try {
					Thread.sleep(2*60*1000);//==========间隔两分钟运行下一场比赛
				} catch (InterruptedException e) {
					//
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	/**
	 * 打篮球主方法,输入两队标号，进行比赛，并发饭否。
	 * @param teama/teamb 交战两队
	 */
	public static void playGame(int teama,int teamb){
		int ts=0,ss=0;
		int aquarter[]= {0,0,0,0};int bquarter[]= {0,0,0,0};
		int[] aot=new int[6];int[] bot=new int[6];//每个加时的得分
		int[][] aotScore=new int[6][10];int[][] botScore=new int[6][10];
		int ascore[] = {0,0,0,0,0,0,0,0,0,0};int bscore[]= {0,0,0,0,0,0,0,0,0,0};//双方每个球员得分
		int aquarterScore[][]= {{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0}};//双方每个球员单节得分
		int bquarterScore[][]= {{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0},{0,0,0,0,0,0,0,0,0,0}};
		
		int[][] lineupa=Teams.lineup;//获得阵容，待会儿需要用这个记录每个球员得分
		int[][] lineupb=Teams.lineup;//获得阵容，待会儿需要用这个记录每个球员得分
		
		String[] fanfou={"第一节","第二节","第三节","第四节","本场比赛"};//要发的饭否，四节各一条，最终总结一条。
		Team tma=Team.getTeam()[teama];
		Team tmb=Team.getTeam()[teamb];
		
		int ot=0;//加时数
		/*======以上为整场比赛初始化---以下为12小节循环======*/
		for(int i = 1 ; i <= 12 || ts==ss ; i++) {
			if(ts==ss&&i>=13) {
				ot+=1;
			}
			//int ot=0;//加时数		//这里是之前处理加时的方式，比较草率
			/*if(ts==ss&&i==13) {	//这里是之前处理加时的方式，比较草率
				i--;
				ot+=1;
				fanfou[4]+="第四节结束双方战平，进入加时。";
			}*/
			Teams a=Teams.getTeams(teama, ot==0?i:11);//如果ot不为0说明进加时，保持最后一小节阵容
			Teams b=Teams.getTeams(teamb, ot==0?i:11);
			String aname[]= a.getName();
			String bname[]= b.getName();
			int aa[]= a.getA();//A队基础攻击
			int ap[]= a.getP();//A队转换攻击
			int ad[]= a.getD();//A队防守
			int ba[]= b.getA();//B队基础攻击
			int bp[]= b.getP();//B队转换攻击
			int bd[]= b.getD();//B队防守

			/*======以上为小节内双方数据初始化---以下为小节内双方五人得分计算======*/
			// 
			aot[ot]=0;bot[ot]=0;
			int t=0;
			for(int ia=0;ia<5;ia++) {
				int si=atk(aa[ia],ap[ia],bd[ia]);//单个球员得分
				/*if(ot>1) {
					si=atk(aa[ia],ap[ia],bd[ia]);
				}*/
				//System.out.print(aname[ia]+"得分"+si+"\t");
				if(ot==0) {
					t+=si;//单节球队比分
					ascore[lineupa[i-1][ia]]+=si;//记录球员数据统计
					aquarterScore[(i-1)/3][lineupa[i-1][ia]]+=si;//记录球员单节数据统计
				}else {
					aot[ot-1]+=si;//单节球队比分
					ascore[lineupa[11][ia]]+=si;//记录球员数据统计
					aotScore[ot-1][lineupb[11][ia]]=si;//记录单个球员单节数据统计
					t+=si;
				}
			}
			int s=0;
			for(int ib=0;ib<5;ib++) {
				int si=atk(ba[ib],bp[ib],ad[ib]);//单个球员得分
				
				//System.out.print(bname[ib]+"得分"+si+"\t");
				if(ot==0) {
					s+=si;//单节球队比分
					bscore[lineupb[i-1][ib]]+=si;//记录球员数据统计
					bquarterScore[(i-1)/3][lineupb[i-1][ib]]+=si;//记录球员单节数据统计
				}else {
					bot[ot-1]+=si;//单节球队比分
					bscore[lineupb[11][ib]]+=si;//记录球员数据统计
					botScore[ot-1][lineupb[11][ib]]=si;//记录单个球员单节数据统计
					s+=si;
				}
			}

			/*======以上为小节内双方五人得分数据计算---以下为小节关联大节事项======*/
			if(ot==0) {
				aquarter[(i-1)/3]+=t;
				bquarter[(i-1)/3]+=s;
			}
			//System.out.println(t+"\t"+s);
			/*============以下为大节事项============*/

			if(i%3==0&&ot==0) {
				//fanfou[4]+="第"+(i/3)+"节的比分为："+aquarter[(i-1)/3]+"比"+bquarter[(i-1)/3]+"。";
				fanfou[(i-1)/3]=beStatus((i/3),aquarter,bquarter,aquarterScore,bquarterScore,tma.getName(),tmb.getName(),tma.getPlayers(),tmb.getPlayers(),t,s);
				//System.out.println("第"+(i/3)+"节的比分为："+aquarter[(i-1)/3]+"比"+bquarter[(i-1)/3]+"。");
			}else if(i>=12) {
				/*======以下为加时赛事项======*/
				fanfou[3]+=otbeStatus(ot,aot,bot,aotScore,botScore,tma.getName(),tmb.getName(),tma.getPlayers(),tmb.getPlayers(),ts,ss);
				//System.out.println("第"+ot+"加时的比分为："+t+"比"+s+"。");
			}
			/*======以上为大节事项---以下为小节关联整场比赛事项======*/
			ts+=t;
			ss+=s;
		}
		fanfou[4]+=statbeStatus(ascore,bscore,tma.getPlayers(),tmb.getPlayers(),tma.getName(),tmb.getName());
		System.out.println();
		System.out.println(tma.getName()+"对"+tmb.getName()+"的比赛最终比分为："+ts+"比"+ss+"！四节比分分别为："
				+aquarter[0]+"-"+bquarter[0]+"，"
				+aquarter[1]+"-"+bquarter[1]+"，"
				+aquarter[2]+"-"+bquarter[2]+"，"
				+aquarter[3]+"-"+bquarter[3]+"，");
		for(int f=0;f<5;f++) {
			System.out.println("fanfou"+f+":"+fanfou[f]);
		}
		String fan=fanfou[0]+"。"+fanfou[1]+"。"+fanfou[2]+"。"+fanfou[3]+"。"+fanfou[4];
		fan=fan.replace("第一节，双方比分","首节双方打成");
		fan=fan.replace("第二节，双方战至半场结束", "第二节打完");
		fan=fan.replace("第三节，三节结束","第三节结束");
		fan=fan.replace("第四节常规时间结束","第四节仍难解难分，常规时间结束");
		fan="模拟一场明天"+tma.getName()+"对"+tmb.getName()+"的比赛："+fan;
		if(fan.length()>140) {
			String[] fand=fan.split("本场比赛");
			String fan1=fand[0];
			String fan2="本场比赛"+fand[1];
			/*String fan1=fan.substring(0, 139);
			String fan2=fan.substring(139);*/
			Status.UpdateStatus(fan1);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				//
				e.printStackTrace();
			}
			Status.UpdateStatus(fan2);
		}else {
			System.out.println("fan:"+fan);
			Status.UpdateStatus(fan);
		}
		
		
		
		
		/**
		 * 输出双方球员总得分
		 */
		
		/*for(int ip=0;ip<10;ip++) {
			System.out.print(tma.players[ip].getName()+"总得分" +ascore[ip]+"\t");
			if(ip==4) {
				System.out.println();
			}
		}
		System.out.println();
		for(int ip=0;ip<10;ip++) {
			System.out.print(tmb.players[ip].getName()+"总得分" +bscore[ip]+"\t");
			if(ip==4) {
				System.out.println();
			}
		}
		System.out.println();
		System.out.println();*/
		/*for(int ip=0;ip<10;ip++) {
			for(int jp=0;jp<4;jp++) {
				System.out.print(tma.players[ip].getName()+"第"+(jp+1)+"节得分"+aquarterScore[jp][ip]+"\t");
			}
			if(ip%2==1) {
				System.out.println();
			}
		}
		System.out.println();
		for(int ip=0;ip<10;ip++) {
			for(int jp=0;jp<4;jp++) {
				System.out.print(tmb.players[ip].getName()+"第"+(jp+1)+"节得分"+bquarterScore[jp][ip]+"\t");
			}
			if(ip%2==1) {
				System.out.println();
			}
		}*/
		
		//System.out.println(fanfou[4]);
		//Status.UpdateStatus(fanfou[4]);
	}

	/*
	public static int score(int a[],int p[],int db[],String aname[]) {
		int s=0;
		for(int i=0;i<5;i++) {
			int si=atk(a[i],p[i],db[i]);
			System.out.print(aname[i]+"得分"+si+"\t");
			s+=si;
		}
		return s;
	}*/
	/*public static int basescore(int a[],int p[],int db[],String aname[]) {
		int s=0;
		for(int i=0;i<5;i++) {
			int si=atk(a[i],p[i],db[i]);
			System.out.print(aname[i]+"得分"+si+"\t");
			s+=si;
		}
		return s;
	}*/
	public static int atk(int a,int p,int d) {
		Random r=new Random();
		int s=0;
		s=a;
		int pp=r.nextInt()%(p+1);
		s+=(pp<0?-pp:pp);
		int dd=r.nextInt()%(d+4);
		s-=(dd<0?0:dd);
		//System.out.print("得分"+(s<0?0:s)+"\t");
		return s<0?0:s;
	}
	public static int atk1(int a,int p,int d) {
		Random r=new Random();
		int s=0;
		int pp=r.nextInt()%100;
		s=(p>=5)?8:1;
		return s;//测试用的	
	}
/*
 *	比赛分四节，每节分三个小节，基本上篮球比赛一节12分钟时间分为三个4分钟的小段很常见。
 *	玩家可以为每个小节布置战术及阵容，这是之前考虑好的一些规则，既简单，又模拟了NBA真实情况。
 *	在每个小节里，可能有球员发动自己的技能，如果没有球员发动技能，那就是普通得分，即把每个球员的基础得分相加之类。
 *	普通得分计算公式是球员基础进攻+转换进攻×一定概率比例-对位球员防守×一定概率比例
 *		因为球员基础进攻是球员的基本功，可以稳稳得分。
 *			当然如果某位球员发动了技能，那么其队友的基础进攻当然也就不需要了。
 *			例如汤普森进入了汤神模式，那队友库里肯定会积极给他喂球，自己基本不出手。
 *		转换进攻要看运气，不一定每节都有。
 *			后续可以设定战术，根据球队打法去改变球队的基础得分和转换得分的比例。
 *			比如球队里都是转换进攻高手，则可以加大转换进攻比例，如果运气好，可以得高分，运气不好，不仅没得高分，防守强度降低并且体力消耗大。
 *		防守也需要看状态，比如有时候乐福就是防守漏勺，但在总决赛上的防守却可圈可点。
 *	
 *	一般的球员的技能可能有几种：
 *		XX%概率本小节连得XX分。一般为神经刀或者巨星得分手，如库里、JR史密斯、汤普森。
 *		XX%概率本小节获得阵容中所有队友的快攻得分总和的几倍。一般为擅长打转换进攻的球员如威少、连长、詹姆斯。
 *		XX%概率本小节让阵容中所有队友基础得分各加2分，一般为善于串联队友的球员如隆多、保罗、格林。
 *		XX%概率本小节让阵容中某一队友获得其快攻得分的几倍得分，一般为擅长串联队友打转换进攻的球员如基德、乐福、卢比奥。
 *		XX%概率让对手本小节仅能依靠基础得分进行得分，一般为擅长防守的球员如阿里扎、伦纳德、戈贝尔。
 *	球员能力值大概：
 *		假设五人不考虑体力，能力中游，防守中游，打满一场应该得100-120分左右，按110分算。
 *		那么相当于每人22分，则12个小节平均每人每小节1.83分
 *		对位防守期望假设是1（有一定概率为0，最好可以设计为正态分布）。
 *		则平均基础进攻+转换进攻期望=2.83。这种球队类似黄蜂、掘金、爵士，没有超级巨星，阵容实力平均。
 *		但是如果是勇士、骑士这类巨头球队，替补又好，不可能巨头一多，场均得分就140吧，因此应加入球权机制：
 *			一套阵容平均进攻能力值太高，按比例削弱，如果有了体力机制，球权机制削弱了进攻，可以加强体力，这样多出来的体力可以加大防守的效果。
 *		一期先不做体力、不做球权，只要计算双方得分，勇士这类球队自然要比篮网和湖人赢的概率大，这不太平衡了。没办法，这就是篮球。
 *		体力有一种新想法，每个球员，仅以一个数值表示体力，例如哈登体力为8，他就只能打8小节，如果安排哈登上了超过8小节，到第9小节时候所有数据大幅下滑。
 *		
 *	一期目标：
 *		打满一场应该得平均110分左右，大部分分布在90-125分左右，小概率出现50-160分，其余范围不应出现。
 *		不同队伍拥有不同阵容
 *		12个小节有不同的阵容
 *
 *		上述简版体力/模拟定死轮换
 * */
	public static String beStatus(int quarter,int[] aquarter,int[] bquarter,int[][] aquarterScore, int[][] bquarterScore,String tmaname,String tmbname,Player[] pa,Player[] pb,int ta,int sb) {
		//beStatus((i/3),aquarter,bquarter,aquarterScore,bquarterScore,tma.getName(),tmb.getName(),tma.getPlayers(),tmb.getPlayers());
		String s="";String s1="";String s2="";//三段播报
		int ot=0;
		/*以下为根据数据生成复杂精彩的话术逻辑*/
		switch(quarter) {
		case 1:
			s+="第一节";break;
		case 2:
			s+="第二节";break;
		case 3:
			s+="第三节";break;
		case 4:
			s+="第四节";break;
		default:
			s+="第"+(quarter-4)+"加时";//quarter>4时表示是加时了。5就变成第1加时，以此类推
			ot+=quarter-4;
			break;
		}
		quarter-=1;
		int high=0;//高分方，
		int bc=0;//播报过比分为1，没播报过为0；
		for(int i=0;i<10;i++) {
			//判断单节高分
			if(ot>0) {
				if(ta>12) {
					s+="，"+pa[i].getName()+"单节得到"+aquarterScore[quarter][i]+"分";
					high+=1;
				}
				if(bquarterScore[quarter][i]>12) {
					s+="，"+pb[i].getName()+"单节得到"+bquarterScore[quarter][i]+"分";
					high+=10;
				}
			}
			if(aquarterScore[quarter][i]>12) {
				s+="，"+pa[i].getName()+"单节得到"+aquarterScore[quarter][i]+"分";
				high+=1;
			}
			if(bquarterScore[quarter][i]>12) {
				s+="，"+pb[i].getName()+"单节得到"+bquarterScore[quarter][i]+"分";
				high+=10;
			}
			//判断累积高分
			if(quarter==1||quarter==2) {
				int higha=0;
				int highb=0;
				for(int j=0;j<=quarter;j++) {
					higha+=aquarterScore[j][i];
					highb+=bquarterScore[j][i];
				}
				if(higha>=(quarter*16+8)) {
					s2+="，目前"+pa[i].getName()+"已经得到"+higha+"分";
				}
				if(highb>=(quarter*16+8)) {
					s2+="，目前"+pb[i].getName()+"已经得到"+highb+"分";
				}
			}
		}
		int ab=aquarter[quarter]-bquarter[quarter];
		if(ab>14) {
			s+=(high>0&&high<10)?"，带领":((high>10&&high%10==0)?"，但是":"，");
			if(aquarter[quarter]>42) {
				s+=tmaname+"打出了进攻高潮，单节打出"+aquarter[quarter]+"比"+bquarter[quarter]+"的比分";
				bc=1;
			}else if(bquarter[quarter]<16) {
				s+=tmaname+"打出了窒息防守，单节打出"+aquarter[quarter]+"比"+bquarter[quarter]+"的比分";
				bc=1;
			}else {
				s+=tmaname+"单节打出"+aquarter[quarter]+"比"+bquarter[quarter]+"的比分";
				bc=1;
			}
		}
		if(ab<-14) {
			s+=(high>0&&high<10)?"，但是":((high>10&&high%10==0)?"，带领":"，");
			if(bquarter[quarter]>42) {
				s+=tmbname+"打出了进攻高潮，单节打出"+bquarter[quarter]+"比"+aquarter[quarter]+"的比分";
				bc=1;
			}else if(aquarter[quarter]<16) {
				s+=tmbname+"打出了窒息防守，单节打出"+bquarter[quarter]+"比"+aquarter[quarter]+"的比分";
				bc=1;
			}else {
				s+=tmbname+"单节打出"+bquarter[quarter]+"比"+aquarter[quarter]+"的比分";
				bc=1;
			}
		}
		if(quarter==1||quarter==2) {//系第二节第三节；TODO1 补充第一节和第四节
			int as=aquarter[0]+aquarter[1]+(quarter==2?aquarter[2]:0);
			int bs=bquarter[0]+bquarter[1]+(quarter==2?bquarter[2]:0);//计算目前得分
			int as0=aquarter[0]+(quarter==2?aquarter[1]:0);//计算之前得分
			int bs0=bquarter[0]+(quarter==2?bquarter[1]:0);//计算目前得分
			if(quarter==1) {s1+="，双方战至半场结束，";}else {s1+="，三节结束";}
			if(as>bs) {
				if(bs0-as0>5) {
					s1+=tmaname+"以"+as+"比"+bs+"逆转比分";
				}else if(as0-bs0>13){
					s1+=tmaname+"以"+as+"比"+bs+"继续领先";
				}else {
					s1+=tmaname+"以"+as+"比"+bs+"暂时领先"+tmbname;
				}
			}else if(bs>as) {
				if(as0-bs0>5) {
					s1+=tmbname+"以"+bs+"比"+as+"逆转比分";
				}else if(bs0-as0>13){
					s1+=tmbname+"以"+bs+"比"+as+"继续领先";
				}else {
					s1+=tmbname+"以"+bs+"比"+as+"暂时领先"+tmaname;
				}
			}else {
				s1+="双方打成"+as+"平";
			}
		}else if(quarter==0) {//系第一节
			if(bc==0) {
				s1+="，双方比分"+aquarter[0]+"比"+bquarter[0]+"进入第二节";
			}
		}else if(quarter==3) {//系第四节处理办法
			int as=aquarter[0]+aquarter[1]+aquarter[2]+aquarter[3];
			int bs=bquarter[0]+bquarter[1]+bquarter[2]+bquarter[3];//计算目前得分
			int as3=aquarter[0]+aquarter[1]+aquarter[2];
			int bs3=bquarter[0]+bquarter[1]+bquarter[2];//计算三节得分
			int as2=aquarter[0]+aquarter[1];
			int bs2=bquarter[0]+bquarter[1];//计算二节得分
			int as1=aquarter[0];
			int bs1=bquarter[0];//计算首节得分
			//疯狂逆转，险些逆转，胶着险胜，大胜，小胜
			if(as>bs) {
				if(bs3-as3>20||bs2-as2>20||bs1-as1>20) {
					s2+="，"+tmaname+"以"+as+"比"+bs+"疯狂逆转"+tmbname;
				}else if(bs3-as3>10||bs2-as2>10||bs1-as1>10) {
					s2+="，"+tmaname+"以"+as+"比"+bs+"逆转"+tmbname;
				}else if(as-bs<4) {
					s2+="，"+tmaname+"以"+as+"比"+bs+"险胜"+tmbname;
				}else if(as-bs>17) {
					s2+="，"+tmaname+"以"+as+"比"+bs+"大胜"+tmbname;
				}else {
					s2+="，"+tmaname+"以"+as+"比"+bs+"战胜"+tmbname;
				}
			}else if(bs>as) {
				if(as3-bs3>20||as2-bs2>20||as1-bs1>20) {
					s2+="，"+tmbname+"以"+bs+"比"+as+"疯狂逆转"+tmaname;
				}else if(as3-bs3>10||as2-bs2>10||as1-bs1>10) {
					s2+="，"+tmbname+"以"+bs+"比"+as+"逆转"+tmaname;
				}else if(bs-as<4) {
					s2+="，"+tmbname+"以"+bs+"比"+as+"险胜"+tmaname;
				}else if(bs-as>17) {
					s2+="，"+tmbname+"以"+bs+"比"+as+"大胜"+tmaname;
				}else {
					s2+="，"+tmbname+"以"+bs+"比"+as+"战胜"+tmaname;
				}
			}else {
				s2+="常规时间结束，双方打成"+as+"平，双方进入加时。";
			}
		}
		return s+s1+s2;
	}
	public static String otbeStatus(int ot,int[] aot,int[] bot,int[][] aotScore,int[][] botScore,String tmaname,String tmbname,Player[] pa,Player[] pb,int ta,int sb){
		String s="第"+ot+"加时";
		//加时赛不存在反超等分差问题，不会出现单节高分的情况，只需要播报赢球方最高分的球员即可
		ot--;
		if(aot[ot]==bot[ot]) {
			s+="双方各得"+aot[ot]+"分，比赛进入第"+(ot+2)+"加时";
		}else {
			if(aot[ot]>bot[ot]) {
				int max=0;
				for(int io=1;io<10;io++) {
					if(aotScore[ot][io]>aotScore[ot][max]) {
						max=io;
					}
				}
				s+=pa[max].getName()+"得到"+aotScore[ot][max]+"分，带领"+tmaname+"以"+(ta+aot[ot])+"比"+(sb+bot[ot])+"拿下比赛";
			}else if(bot[ot]>aot[ot]) {
				int max=0;
				for(int io=1;io<10;io++) {
					if(botScore[ot][io]>botScore[ot][max]) {
						max=io;
					}
				}
				s+=pb[max].getName()+"得到"+botScore[ot][max]+"分，带领"+tmbname+"以"+(sb+bot[ot])+"比"+(ta+aot[ot])+"拿下比赛";
			}
		}
		return s;
	}
	public static String statbeStatus(int[] ascore,int[] bscore,Player[] pa,Player[] pb,String tmaname,String tmbname) {
		String s="";
		int max1=ascore[0]>ascore[1]?0:1;
		int max2=max1==0?1:0;
		for(int i=2;i<10;i++) {
			if(ascore[max1]>=ascore[i]&&ascore[i]>ascore[max2]) {
				max2=i;
			}else if(ascore[i]>ascore[max1]){
				max2=max1;
				max1=i;
			}
		}
		s+=tmaname+"队"+pa[max1].getName()+"得到"+ascore[max1]+"分，"+pa[max2].getName()+"得到"+ascore[max2]+"分。";
		max1=bscore[0]>bscore[1]?0:1;
		max2=max1==0?1:0;
		for(int i=2;i<10;i++) {
			if(bscore[max1]>=bscore[i]&&bscore[i]>bscore[max2]) {
				max2=i;
			}else if(bscore[i]>bscore[max1]){
				max2=max1;
				max1=i;
			}
		}
		s+=tmbname+"队"+pb[max1].getName()+"得到"+bscore[max1]+"分，"+pb[max2].getName()+"得到"+bscore[max2]+"分。";	
		return s;
	}
}
