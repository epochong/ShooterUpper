package cn.yichun.game;

import java.awt.Graphics;
import java.awt.Image;
//±¨’®¿‡
public class Explode {
	double x, y;
	int count = 0;
	static Image[] imgs = new Image[14];
	static {
		for (int i = 0; i < 14; i++) {
			imgs[i] = GameUtil.getImage("images/explode/e" + (i + 1) + ".gif");
			imgs[i].getWidth(null);
		}
	}

	public void draw(Graphics g) {
		g.drawImage(imgs[count], (int) x, (int) y, null);
		if (count < 13)
			count++;
	}
	public Explode(double x, double y) {
		this.x = x;
		this.y = y;
	}
}