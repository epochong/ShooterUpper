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
	//ͼƬ
	Image planeImg = GameUtil.getImage("images/plane1.png");
	Image bgImg = GameUtil.getImage("images/SKEY2.jpg");
	Image eplane = GameUtil.getImage("images/eplane.png");
	Image bossImag = GameUtil.getImage("images/boss.png");
	Image bossgun = GameUtil.getImage("images/b3.gif");
	Image stbg = GameUtil.getImage("images/skay.jpg");
	Image lifeIcon = GameUtil.getImage("images/life.png");
	Image gunIcon = GameUtil.getImage("images/gun.png");
	Image gameOver = GameUtil.getImage("images/GameOver.png");
	//vector�����鷽��
	public Vector<EnamyPlane> enamy = new Vector<EnamyPlane>();
	public Vector<MyShell> miss = new Vector<MyShell>();
	public Vector<MySuperShell> miss2 = new Vector<MySuperShell>();
	public Vector<Shell> emiss = new Vector<Shell>();
	public Vector<Explode> bao = new Vector<Explode>();
	public Vector<ExplodeLight> light = new Vector<ExplodeLight>();
	public Vector<MyLight> myLight = new Vector<MyLight>();
	//��Ƶ
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
	
	public static boolean printable = true;//��ͣ���ܼ�
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
	boolean help = false;//����h�����İ�����ʾ

	//��Ϸ�������ý���
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
	//��Ϸǰ����:��ʼ��������ʼ��Ϸ����������
	public void beforGame(Graphics g) {
		g.drawImage(stbg, 0, 0, null);
		if (System.currentTimeMillis() % 1000 >= 500) {
			Font f = new Font("", Font.BOLD, 30);
			g.setFont(f);
			g.drawString("�밴X��ʼ��Ϸ", 300, 700);
		}
		Font f1 = new Font("", Font.BOLD, 20);
		g.setFont(f1);
		g.drawString("��סh����ȡ����", 325, 750);
		//����h
		if(help){
			g.setFont(f1);
			g.drawString("������", 300, 600);
			g.drawString("��������Ʒɻ�", 300, 625);
			g.drawString("x�������c��������s��������", 300, 650);
			g.drawString("�ո�� ��ͣ/����", 300, 675);
		}
	}
	//�����ı�help��ֵ  ����������ʾ���ɿ�����
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
	//��ʼ��Ϸ
	public void enterGame(KeyEvent s){
		if (s.getKeyCode() == KeyEvent.VK_X) {
			//System.out.println("��ʼ");
			STAGE = 2;//�ڶ�ҳ��
			startBgm.stop();
			bgm1Play = bgm2Play = false;
		}
	}
	//��ͣ/������ݼ�  SPACE
	public void pauseAndcontinue(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			printable = !printable;
			if(printable)//printable����������ͣ
				new Thread(new PaintThread()).start();
		}
	}
	public void beforPaint() {
		// ���Լ�����
		if (!plane.live && System.currentTimeMillis() - plane.deiTime > 2000) {
			if (LIFE > 0) {
				plane.x = 300;
				plane.live = true;// ����
				plane.birthTime = System.currentTimeMillis();
				plane.y = 500;
				plane.speed = 5;
				
				if (bomb <  Constant.LIGHT_NUM)//������
				{
					bomb = Constant.LIGHT_NUM;
				}
			} else {
				sound2.stop();
				sound3.stop();
			}
		}
		// �Եл�����
		if (enamyCount <= 0 && enamy.isEmpty() && !bossIs) {
			boss1.live = true;// ����boss
			bossIs = true;
		}
		for (int i = 0; i < enamy.size(); i++) {// �л��ƶ�
			EnamyPlane e = enamy.get(i);
			e.y += e.speed;
			if ((System.currentTimeMillis() - e.birth) % 1000 >= 500)//���һ�
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
			EnamyPlane e = new EnamyPlane( x+150, 0, 3, 40, 40);//�����µĵ���
			enamy.add(e);
		}
		// �����ӵ�
		if (plane.shoot && System.currentTimeMillis() - MyShell.time > 100 && miss.size() < Constant.MY_SHELLNUM*fire) {
			//������������������
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
			//System.out.println("���");
			shoot.play();
		}
		// ���伤��
		if (plane.shoot2 && System.currentTimeMillis() - MySuperShell.time > 1000 && bomb > 0) {
			MySuperShell e = new MySuperShell(plane);
			MyLight l = new MyLight(plane);
			myLight.add(l);
			miss2.add(e);
			lightSound.play();
			bomb--;
		}
		
		for (int j = 0; j < miss2.size(); j++) {// �����Ƿ����е���
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

		for (int i = 0; i < emiss.size(); i++) {//boss�ӵ�
			Shell e = emiss.get(i);
			if (!e.OnSec()) {
				emiss.remove(i);
			}
		}
		// boss����
		if (boss1.live) {
			if (System.currentTimeMillis() - boss1.birth >= 3000) {//����һ��
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
		// �ж��ӵ��͵л�����ײ
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
		for (int i = 0; i < miss2.size(); i++) {// �����ӵ����Ƴ�
			MySuperShell e = miss2.get(i);
			if (System.currentTimeMillis() - e.time > 200) {
				if (e.hit >= 1) {
					bomb += e.hit;
				}
				miss2.remove(i);
			}
		}
		// �жϵл����Լ�����ײ
		for (int i = 0; i < enamy.size(); i++) {
			EnamyPlane e = enamy.get(i);
			//if���ʵ���޵�ʱ��
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
		// �ж�boss���Լ�����ײ
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
		// �ж�boss�ӵ����Լ�����ײ
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
		// �ж�boss���Լ��ӵ�����ײ
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
		//�ж�boss���Լ��������ײ
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
		// �ж�boss�Ƿ�����
		if (boss1.life <= 0 && boss1.live) {
			boss1.live = false;
			bossDei.play();
			sound3.stop();
			boss1.x = -1000;
			boss1.y = -1000;
		}
		// �ж���Щ��ը������Ҫ����
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
			g.drawString("ʣ�����" + enamyCount, 20,90);
		}
		if (boss1.life <= 0) {
			if (ed){
				Font f = new Font("", Font.BOLD, 20);
				g.setFont(f);
				g.drawString("ͨ�����أ�лл�������ǵ���Ϸ", 250, 250);
				g.drawString("             ����epochong����", 350, 300);
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
			if(boss1.life > 0){//�޸�boss bug
				g.drawImage(gameOver, 295, 300, null);
				Font f = new Font("", Font.BOLD, 25);
				//Font f1 = new Font("", Font.BOLD, 40);
				//g.setFont(f1);
				//g.drawString("GAME OVER", 400, 250);
				Color c;
				c = g.getColor();//��ס��ɫ ��
				g.setColor(c.RED);
				g.setFont(f);
				g.drawString("Tips:ʹ�ü����ɱ���˿ɻ�ü������", 190, 500);
				g.drawString("����ͻ��һ����ֵ����ǿ���ӵ�", 190, 530);
				g.drawString("��Ļ������������ɱboss�Ŀ�����", 190, 560);
				g.drawString("��õĳɼ���������һ�Σ�", 190, 590);
				g.setColor(c);//���غ�ɫ ��ֹ�´���
				//g.drawString("aaaa", 200, 300);
				if (!liang) {
					liangLiang.play();
					liang = true;
					edTime = System.currentTimeMillis();
				}
				if (System.currentTimeMillis() - edTime >= 5000) {
					STAGE = 1;
		  //boss1����û������  ���ӻᵼ�µ�һ�δ�bossʧ�ܺ� �ڶ��δ�boss��ʼѪ��������һ�ε�ʣ��Ѫ��
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
	//ÿ��ҳ��Ҫ������
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
	//�߳�  �ڲ���
	class PaintThread extends Thread {
		@Override
		public void run() {
			while (printable) {
				// ���Լ�����
				repaint();
				try {
					Thread.sleep(16);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	//���̼���
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
	//���ڴ���
	public void launchFrame() {
		setTitle("Χ��");
		setVisible(true);
		setSize(Constant.GAME_WIDTH, Constant.GMAE_HEIGTH);
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		setLocation((width-Constant.GAME_WIDTH)/2, 0);
		setAlwaysOnTop(true);
		setResizable(false);
		startBgm.play();
		//�رմ��ڵ�ͬʱ�رս���  ȥ�������޷��ر���Ϸ
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		new PaintThread().start(); // �����ػ��߳�
		addKeyListener(new KeyMonitor());// �����˼��̼���
	}
	//˫���漼�� �����˸����
	private Image offScreenImage = null;
	@Override
	public void update(Graphics g) {
		if (offScreenImage == null)
			offScreenImage = this.createImage(Constant.GAME_WIDTH, Constant.GMAE_HEIGTH);// ������Ϸ���ڵĿ�Ⱥ͸߶�
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