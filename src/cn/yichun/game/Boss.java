package cn.yichun.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class Boss extends GameObject{
	boolean live;
	int life;
	long birth;
	double degree;
	public Boss(Image img, int x, int y, int speed, int width, int height) {
		super(img, x, y, speed, width, height);
		this.img = img;
		this.x = x;
		this.y = y;
		this.speed = 3;
		live = false;
		this.life = Constant.BOSS_LIFE;
		degree = Math.PI/4;
		this.birth = System.currentTimeMillis();
		life = Constant.BOSS_LIFE;
	}
	
	public void drawWid(Graphics g) {
		g.drawRect((int)x, (int)(y+50), width-30, height-30);
	}
	public Rectangle getRect() {
		return  new Rectangle((int)x, (int)(y+50), width-30, height-30);
	}

	public void drawSelf(Graphics g) {
		// TODO Auto-generated method stub
		if (live) {
			g.drawImage(img, (int)(x-100), (int)y, null);
			//drawWid(g);
			g.drawString("LEFT:"+life, (int)(x-10), (int)(y+50));
			x += Math.cos(degree)*speed;
			y += Math.sin(degree)*speed;
			if (x < 0 || x > Constant.GAME_WIDTH-width) {
				degree = Math.PI-degree;
			}
			if (y < 0 || y > Constant.GMAE_HEIGTH-height) {
				degree = -degree;
			}			
		}
	}
}
