package cn.yichun.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class MyShell extends Shell{
	static Image SHELL = GameUtil.getImage("images/SHELL.png");
	static Image Shell2 =  GameUtil.getImage("images/Shell2.png");
	static long time;
	int kind;
	int life;
	
	public  MyShell(int x, int y, int k) {
			img = Shell2;
			width = 33;
			height = 100;
			this.x = x+20;
			this.y = y-50;
			speed = 15;
			time = System.currentTimeMillis();
			kind = 2;
			life = 2;
	}
	public MyShell(int x, int y) {
		img = SHELL;
		width = 10;
		height = 10;
		this.x = x+15;
		this.y = y;
		speed = 15;
		time = System.currentTimeMillis();
		kind = 1;
		life = 1;
	}
	public void draw(Graphics g) {//改图片改成大招
		g.drawImage(img, (int)(x-10), (int)y, null);
		y-=speed;
	}
	public Rectangle getRect() {
		return  new Rectangle((int)x, (int)y, width, height);
	}
}
