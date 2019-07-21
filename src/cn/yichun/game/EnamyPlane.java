package cn.yichun.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

public class EnamyPlane extends GameObject {
	boolean live;
	int life;
	static Image[] imgs2 = new Image[7];
	long birth;
	
	static {
		for (int i = 1; i < 6; i++) {
			imgs2[i] = GameUtil.getImage("images/plane" + (i + 1) + ".png");
			imgs2[i].getWidth(null);
		}
	}
	
	public EnamyPlane(int x, int y, int speed, int width, int height) {
		this.speed = speed;
		this.width = width;
		this.height = height;
		Image img = imgs2[(int) (Math.random()*5)+1];
		this.img = img;
		this.x = x;
		this.y = y;
		birth = System.currentTimeMillis();
		live = true;
	}

	public void drawSelf(Graphics g) {
		if (this.live) {
			super.drawSelf(g);
			//drawWid(g);
		}
	}
	public void drawWid(Graphics g) {
		g.drawRect((int)x, (int)y, width, height);
	}
	
	public Rectangle getRect() {
		return new Rectangle((int)x, (int)y, width, height);
	}
}
