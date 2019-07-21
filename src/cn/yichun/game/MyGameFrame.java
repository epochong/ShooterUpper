package cn.yichun.game;

import java.awt.Image;
import java.awt.Toolkit;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

public class MyGameFrame extends Frame{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1600380846217097057L;
	//图片
	Image planeImg = GameUtil.getImage("images/plane1.png");
	Image bgImg = GameUtil.getImage("images/SKEY2.jpg");
	Image eplane = GameUtil.getImage("images/eplane.png");
	Image bossImag = GameUtil.getImage("images/boss.png");
	Image bossgun = GameUtil.getImage("images/b3.gif");
	Image stbg = GameUtil.getImage("images/skay.jpg");
	Image lifeIcon = GameUtil.getImage("images/life.png");
	Image gunIcon = GameUtil.getImage("images/gun.png");
	Image gameOver = GameUtil.getImage("images/GameOver.png");
	//vector比数组方便
	public Vector<EnamyPlane> enamy = new Vector<EnamyPlane>();
	public Vector<MyShell> miss = new Vector<MyShell>();
	public Vector<MySuperShell> miss2 = new Vector<MySuperShell>();
	public Vector<Shell> emiss = new Vector<Shell>();
	public Vector<Explode> bao = new Vector<Explode>();
	public Vector<ExplodeLight> light = new Vector<ExplodeLight>();
	public Vector<MyLight> myLight = new Vector<MyLight>();
	//音频
	java.net.URL file1 = getClass().getResource("boom.wav");
	java.net.URL file2 = getClass().getResource("BMG.wav");
	java.net.URL file3 = getClass().getResource("bossbgm.wav");
	java.net.URL file4 = getClass().getResource("ishoot.wav");
	java.net.URL file5 = getClass().getResource("bossStart.wav");
	java.net.URL file6 = getClass().getResource("dei.wav");
	java.net.URL file7 = getClass().getResource("bossShoot.wav");
	java.net.URL file8 = getClass().getResource("hitboss.wav");
	java.net.URL file9 = getClass().getResource("bossDei.wav");
	java.net.URL file10 = getClass().getResource("START.wav");
	java.net.URL file11 = getClass().getResource("liang.wav");
	java.net.URL file12 = getClass().getResource("endMusci.wav");
	java.net.URL file13 = getClass().getResource("LIGHT.wav");
	java.net.URL file14 = getClass().getResource("startBGM.wav");
	AudioClip startBgm = java.applet.Applet.newAudioClip(file14);
	AudioClip iDei = java.applet.Applet.newAudioClip(file6);
	AudioClip lightSound = java.applet.Applet.newAudioClip(file13);
	AudioClip edMusic = java.applet.Applet.newAudioClip(file12);
	AudioClip sound3 = java.applet.Applet.newAudioClip(file3);
	AudioClip liangLiang = java.applet.Applet.newAudioClip(file11);
	AudioClip soundStart = java.applet.Applet.newAudioClip(file10);
	AudioClip sound2 = java.applet.Applet.newAudioClip(file2);
	AudioClip sound1 = java.applet.Applet.newAudioClip(file1);
	AudioClip shoot = java.applet.Applet.newAudioClip(file4);
	AudioClip bossShoot = java.applet.Applet.newAudioClip(file7);
	AudioClip bossHit = java.applet.Applet.newAudioClip(file8);
	AudioClip bossDei = java.applet.Applet.newAudioClip(file9);
	AudioClip bossStart = java.applet.Applet.newAudioClip(file5);
	
	int enamyCount = Constant.ENUM;
	Plane plane = new Plane(400, 500, 10, 50, 50);
	Boss boss1 = new Boss(bossImag, -500, 0, 5, 200, 200);
	
	public static boolean printable = true;//暂停功能键
	boolean bgm1Play, bgm2Play, bossIs, liang, ed;
	int LIFE = 5;
	int maxEnamy;
	int bomb = Constant.LIGHT_NUM;
	int score;
	int bgy = -1000;
	int speed;
	int bgSpeed;
	int STAGE = 1;
	long edTime;
	int fire = 1;
	boolean help = false;//用于h按键的帮助显示

	//游戏复活重置界面
	public void reset() {
		enamyCount = Constant.ENUM;
		plane.x = 400;
		plane.y = 500;
		boss1.x = -500;
		boss1.y = 0;
		bossIs = liang = ed = false;
		boss1.live = false;
		LIFE = 5;
		maxEnamy = 0;
		bomb = Constant.LIGHT_NUM;
		score = 0;
		bgy = -1000;
		speed = 0;
		bgSpeed = 0;
		STAGE = 1;
		edTime = 0;
		enamy.removeAllElements();
		emiss.removeAllElements();
	}
	//游戏前处理:开始背景，开始游戏键，帮助键
	public void beforGame(Graphics g) {
		g.drawImage(stbg, 0, 0, null);
		if (System.currentTimeMillis() % 1000 >= 500) {
			Font f = new Font("", Font.BOLD, 30);
			g.setFont(f);
			g.drawString("请按X开始游戏", 300, 700);
		}
		Font f1 = new Font("", Font.BOLD, 20);
		g.setFont(f1);
		g.drawString("按住h键获取帮助", 325, 750);
		//帮助h
		if(help){
			g.setFont(f1);
			g.drawString("帮助：", 300, 600);
			g.drawString("方向键控制飞机", 300, 625);
			g.drawString("x键射击，c键换挡，s键激光炮", 300, 650);
			g.drawString("空格键 暂停/继续", 300, 675);
		}
	}
	//按键改变help的值  做到按下显示，松开隐藏
	public void helpTrue(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_H){
			help = true;
		}
	}
	public void helpFalse(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_H){
			help = false;
		}
	}
	//开始游戏
	public void enterGame(KeyEvent s){
		if (s.getKeyCode() == KeyEvent.VK_X) {
			//System.out.println("开始");
			STAGE = 2;//第二页面
			startBgm.stop();
			bgm1Play = bgm2Play = false;
		}
	}
	//暂停/继续快捷键  SPACE
	public void pauseAndcontinue(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			printable = !printable;
			if(printable)//printable参数用于暂停
				new Thread(new PaintThread()).start();
		}
	}
	public void beforPaint() {
		// 对自己操作
		if (!plane.live && System.currentTimeMillis() - plane.deiTime > 2000) {
			if (LIFE > 0) {
				plane.x = 300;
				plane.live = true;// 复活
				plane.birthTime = System.currentTimeMillis();
				plane.y = 500;
				plane.speed = 5;
				
				if (bomb <  Constant.LIGHT_NUM)//激光数
				{
					bomb = Constant.LIGHT_NUM;
				}
			} else {
				sound2.stop();
				sound3.stop();
			}
		}
		// 对敌机操作
		if (enamyCount <= 0 && enamy.isEmpty() && !bossIs) {
			boss1.live = true;// 启动boss
			bossIs = true;
		}
		for (int i = 0; i < enamy.size(); i++) {// 敌机移动
			EnamyPlane e = enamy.get(i);
			e.y += e.speed;
			if ((System.currentTimeMillis() - e.birth) % 1000 >= 500)//左右晃
			{
				e.x += e.speed;
			} else {
				e.x -= e.speed;
			}
			if (e.y > Constant.GMAE_HEIGTH) {
				enamy.remove(i);
			}
		}
		maxEnamy = 5 + (Constant.ENUM - enamyCount) / 20;
		if (enamy.size() < maxEnamy && enamyCount > 0) {
			int x = (int)(Math.random() * Constant.GAME_WIDTH-200);
			EnamyPlane e = new EnamyPlane( x+150, 0, 3, 40, 40);//创建新的敌人
			enamy.add(e);
		}
		// 发射子弹
		if (plane.shoot && System.currentTimeMillis() - MyShell.time > 100 && miss.size() < Constant.MY_SHELLNUM*fire) {
			//根据能量数决定火力
			if(bomb < 10) {
				fire = 1;
				MyShell e = new MyShell((int)plane.x, (int)plane.y);
				miss.add(e);
			}
			else if (bomb >= 10 && bomb < 30) {
				MyShell e = new MyShell((int)plane.x-10, (int)plane.y);
				miss.add(e);
				MyShell d = new MyShell((int)plane.x+10, (int)plane.y);
				miss.add(d);
				fire = 2;
				
				
			}
			else if (bomb >= 30 && bomb< 50) {
				MyShell e = new MyShell((int)plane.x-20, (int)plane.y+10);
				miss.add(e);
				MyShell k = new MyShell((int)plane.x, (int)plane.y,2);
				miss.add(k);
				MyShell d = new MyShell((int)plane.x+20, (int)plane.y+10);
				miss.add(d);
				fire = 3;
			}
			else if (bomb >= 50) {
				MyShell e = new MyShell((int)plane.x-20, (int)plane.y+10,2);
				miss.add(e);
				MyShell k = new MyShell((int)plane.x, (int)plane.y,2);
				miss.add(k);
				MyShell d = new MyShell((int)plane.x+20, (int)plane.y+10,2);
				miss.add(d);
				fire = 10;
			}
			//System.out.println("射击");
			shoot.play();
		}
		// 发射激光
		if (plane.shoot2 && System.currentTimeMillis() - MySuperShell.time > 1000 && bomb > 0) {
			MySuperShell e = new MySuperShell(plane);
			MyLight l = new MyLight(plane);
			myLight.add(l);
			miss2.add(e);
			lightSound.play();
			bomb--;
		}
		
		for (int j = 0; j < miss2.size(); j++) {// 激光是否命中敌人
			MySuperShell k = miss2.get(j);
			for (int i = 0; i < enamy.size(); i++) {
				EnamyPlane x = enamy.get(i);
				if (x.getRect().intersects(k.getRect())) {
					k.hit++;
					Explode b = new Explode(x.x, x.y);
					bao.add(b);
					sound1.play();
					enamy.remove(i);
					enamyCount--;
					score += 100;
				}
			}
		}
		
		for (int i = 0; i < miss.size(); i++) {
			MyShell e = miss.get(i);
			if (e.y < 0) {
				miss.remove(i);
			}
		}

		for (int i = 0; i < emiss.size(); i++) {//boss子弹
			Shell e = emiss.get(i);
			if (!e.OnSec()) {
				emiss.remove(i);
			}
		}
		// boss攻击
		if (boss1.live) {
			if (System.currentTimeMillis() - boss1.birth >= 3000) {//三秒一炮
				for (double i = 0; i <= 17; i++) {
					Shell e = new Shell(boss1, Math.PI * 2 * (i / 18), bossgun);
					emiss.addElement(e);
				}
				boss1.birth = System.currentTimeMillis();
				ExplodeLight li = new ExplodeLight((int) boss1.x, (int) boss1.y);
				light.add(li);
				bossShoot.play();
			}
		}
		// 判断子弹和敌机的碰撞
		for (int i = 0; i < enamy.size(); i++) {
			EnamyPlane e = enamy.get(i);
			for (int j = 0; j < miss.size(); j++) {
				MyShell k = miss.get(j);
				if (e.getRect().intersects(k.getRect())) {
					Explode b = new Explode(e.x, e.y);
					bao.add(b);
					sound1.play();
					k.life--;
					if (k.life == 0) {
						miss.remove(j);
					}
					enamyCount--;
					score += 100;
					enamy.remove(i);
					break;
				}
			}
		}
		for (int i = 0; i < miss2.size(); i++) {// 激光子弹的移除
			MySuperShell e = miss2.get(i);
			if (System.currentTimeMillis() - e.time > 200) {
				if (e.hit >= 1) {
					bomb += e.hit;
				}
				miss2.remove(i);
			}
		}
		// 判断敌机和自己的碰撞
		for (int i = 0; i < enamy.size(); i++) {
			EnamyPlane e = enamy.get(i);
			//if语句实现无敌时间
			if(e.getRect().intersects(plane.getRect()) && System.currentTimeMillis() - plane.birthTime > 2000) {
				Explode b = new Explode(plane.x, plane.y);
				bao.add(b);
				if (plane.live) {
					plane.live = false;
					//plane.live = true;
					bomb -= 5;
					LIFE--; 
					plane.deiTime = System.currentTimeMillis();
					iDei.play();
				}
			}
		}
		// 判断boss和自己的碰撞
		if (boss1.getRect().intersects(plane.getRect()) && System.currentTimeMillis() - plane.birthTime > 2000) {
			Explode b = new Explode(plane.x, plane.y);
			bao.add(b);
			if (plane.live) {
				plane.live = false;
				LIFE--;
				plane.deiTime = System.currentTimeMillis();
				iDei.play();
			}
		}
		// 判断boss子弹和自己的碰撞
		for (int i = 0; i < emiss.size(); i++) {
			Shell e = emiss.get(i);
			if (e.getRect().intersects(plane.getRect()) && System.currentTimeMillis() - plane.birthTime > 2000) {
				Explode b = new Explode(plane.x, plane.y);
				bao.add(b);
				if (plane.live) {
					plane.live = false;
					bomb -= 5;
					//LIFE--;
					plane.deiTime = System.currentTimeMillis();
					iDei.play();
				}
			}
		}
		// 判断boss和自己子弹的碰撞
		for (int i = 0; i < miss.size(); i++) {
			MyShell e = miss.get(i);
			if (e.getRect().intersects(boss1.getRect())) {
				Explode b = new Explode(e.x - 70, e.y - 100);
				bao.add(b);
				miss.remove(i);
				if (boss1.live) {
					boss1.life--;
					bossHit.play();
				}
			}
		}
		//判断boss和自己激光的碰撞
		for (int i = 0; i < miss2.size(); i++) {
			MySuperShell e = miss2.get(i);
			if (e.getRect().intersects(boss1.getRect())) {
				Explode b = new Explode(boss1.x , boss1.y + 100);
				Explode c = new Explode(boss1.x +70, boss1.y + 100);
				Explode d = new Explode(boss1.x + 140, boss1.y + 100);
				bao.add(b);
				bao.add(c);
				bao.add(d);
				miss2.remove(i);
				if (boss1.live) {
					boss1.life-=10;
					bossHit.play();
					score += 1000;
				}
			}
		}
		// 判断boss是否死亡
		if (boss1.life <= 0 && boss1.live) {
			boss1.live = false;
			bossDei.play();
			sound3.stop();
			boss1.x = -1000;
			boss1.y = -1000;
		}
		// 判断哪些爆炸对象需要消除
		for (int i = 0; i < bao.size(); i++) {
			Explode e = bao.get(i);
			if (e.count == 13) {
				bao.remove(i);
			}
		}
		for (int i = 0; i < light.size(); i++) {
			ExplodeLight e = light.get(i);
			if (e.count == 9) {
				light.remove(i);
			}
		}
		for (int i = 0; i < myLight.size(); i++) {
			MyLight e = myLight.get(i);
			if (e.count == 8) {
				myLight.remove(i);
			}
		}
	}
	public void paintGame(Graphics g) {
		if (bgy >= 0) {
			bgy = -1000;
		}
		if (boss1.live) {
			if (bgSpeed <= 50) {
				bgSpeed++;
			} else {
				bgSpeed = 50;
			}
		}
		else {
			bgSpeed = 10;
		}
		g.drawImage(bgImg, 0, bgy+=bgSpeed, null);
		g.drawString("SCORE:" + score, 20, 50);
		g.drawString("SPEED:" + plane.speed, 20, 70);
		if (enamyCount > 0) {
			g.drawString("剩余敌人" + enamyCount, 20,90);
		}
		if (boss1.life <= 0) {
			if (ed){
				Font f = new Font("", Font.BOLD, 20);
				g.setFont(f);
				g.drawString("通关了呢，谢谢你玩我们的游戏", 250, 250);
				g.drawString("             ——epochong敬上", 350, 300);
				g.drawString("                 	2019/6/22", 350, 350);
			}	
			if (!ed) {
				edMusic.play();
				ed = true;
			}
		}
		if (LIFE > 0) {
			g.drawImage(lifeIcon, 20, 100, null);
			g.drawString("    " + LIFE, 50, 130);
			g.drawImage(gunIcon, 15, 140,null);
			g.drawString("    " + bomb, 50, 170);
			
		} else {
			if(boss1.life > 0){//修复boss bug
				g.drawImage(gameOver, 295, 300, null);
				Font f = new Font("", Font.BOLD, 25);
				//Font f1 = new Font("", Font.BOLD, 40);
				//g.setFont(f1);
				//g.drawString("GAME OVER", 400, 250);
				Color c;
				c = g.getColor();//记住颜色 黑
				g.setColor(c.RED);
				g.setFont(f);
				g.drawString("Tips:使用激光击杀敌人可获得激光层数", 190, 500);
				g.drawString("层数突破一定阀值可以强化子弹", 190, 530);
				g.drawString("你的火力决定了你击杀boss的可能性", 190, 560);
				g.drawString("最好的成绩，就是下一次！", 190, 590);
				g.setColor(c);//调回黑色 防止下次用
				//g.drawString("aaaa", 200, 300);
				if (!liang) {
					liangLiang.play();
					liang = true;
					edTime = System.currentTimeMillis();
				}
				if (System.currentTimeMillis() - edTime >= 5000) {
					STAGE = 1;
		  //boss1对象没有消除  不加会导致第一次打boss失败后 第二次打boss初始血量等于上一次的剩余血量
					boss1.life = Constant.BOSS_LIFE;
					reset();
					startBgm.play();
				}
			}	
		}
		boss1.drawSelf(g);
		// boss1.drawWid(g);
		if (!bgm1Play) {
			soundStart.play();
			bgm1Play = true;
			sound2.loop();
		}
		if (boss1.live) {
			if (!bgm2Play) {
				boss1.x = 300;
				boss1.y = 0;
				sound2.stop();
				bossStart.play();
				sound3.loop();
				bgm2Play = true;
			}
		}
		plane.drawSelf(g);
		// plane.drawWid(g);
		for (int i = 0; i < enamy.size(); i++) {
			EnamyPlane e = enamy.get(i);
			e.drawSelf(g);
			// e.drawWid(g);
		}
		for (int i = 0; i < miss.size(); i++) {
			MyShell e = miss.get(i);
			e.draw(g);
		}
		for (int i = 0; i < myLight.size(); i++) {
			MyLight e = myLight.get(i);
			e.draw(g);
		}
		for (int i = 0; i < bao.size(); i++) {
			Explode e = bao.get(i);
			e.draw(g);
		}
		for (int i = 0; i < light.size(); i++) {
			ExplodeLight e = light.get(i);
			e.draw(g);
		}
		for (int i = 0; i < emiss.size(); i++) {
			Shell e = emiss.get(i);
			e.draw(g);
		}
	}
	//每层页面要做的事
	@Override
	public void paint(Graphics g) {
		if (STAGE == 1) {
			beforGame(g);
		}
		else if (STAGE == 2) {
			beforPaint();
			paintGame(g);
		}
	}
	//线程  内部类
	class PaintThread extends Thread {
		@Override
		public void run() {
			while (printable) {
				// 对自己操作
				repaint();
				try {
					Thread.sleep(16);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	//键盘监听
	class KeyMonitor extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			plane.addDirection(e);
			plane.changeSpeed(e);
			plane.isShoot(e);
			plane.isShoot2(e);
			helpTrue(e);
			pauseAndcontinue(e);
			if (STAGE == 1) {
				enterGame(e);
			}
		}
		@Override
		public void keyReleased(KeyEvent e) {
			plane.minusDirection(e);
			plane.notShoot(e);
			plane.notShoot2(e);
			helpFalse(e);
		}
	}
	//窗口处理
	public void launchFrame() {
		setTitle("围攻");
		setVisible(true);
		setSize(Constant.GAME_WIDTH, Constant.GMAE_HEIGTH);
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		setLocation((width-Constant.GAME_WIDTH)/2, 0);
		setAlwaysOnTop(true);
		setResizable(false);
		startBgm.play();
		//关闭窗口的同时关闭进程  去除导致无法关闭游戏
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		new PaintThread().start(); // 启动重画线程
		addKeyListener(new KeyMonitor());// 增加了键盘监听
	}
	//双缓存技术 解决闪烁问题
	private Image offScreenImage = null;
	@Override
	public void update(Graphics g) {
		if (offScreenImage == null)
			offScreenImage = this.createImage(Constant.GAME_WIDTH, Constant.GMAE_HEIGTH);// 这是游戏窗口的宽度和高度
		Graphics gOff = offScreenImage.getGraphics();
		paint(gOff);
		g.drawImage(offScreenImage, 0, 0, null);
	}
	//main	
	public static void main(String[] args) {
		MyGameFrame f = new MyGameFrame();
		f.launchFrame();
	}
}