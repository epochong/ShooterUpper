package cn.yichun.game;

import java.awt.Graphics;
import java.awt.Image;

/*
 * ±¨’®¿‡
 */
public class MyLight {
	double x, y;
	int count = 0;
	static Image[] imgs2 = new Image[10];
	static {
		for (int i = 0; i < 8; i++) {
			imgs2[i] = GameUtil.getImage("images/explode/L" + (i + 1) + "(“—»•µ◊).jpg");
			imgs2[i].getWidth(null);
		}
	}
	
	public void draw(Graphics g) {
		g.drawImage(imgs2[count], (int) x-40, (int) y+50, null);
		if (count < 8) {
			count++;
		}
	}
	public MyLight(Plane x) {
		this.x = x.x+15;
		this.y = x.y-1000;
	}
}