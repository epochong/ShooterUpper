package cn.yichun.game;

import java.awt.Graphics;
import java.awt.Image;

public class ExplodeLight {
	double x, y;
	int count = 0;
	static Image[] imgs2 = new Image[10];
	static {
		for (int i = 0; i < 9; i++) {
			imgs2[i] = GameUtil.getImage("images/b" + (i + 1) + "(ÒÑÈ¥µ×).gif");
			imgs2[i].getWidth(null);
		}
	}
	
	public void draw(Graphics g) {
		g.drawImage(imgs2[count], (int) x+20, (int) y-150, null);
		if (count < 9)
			count++;
	}
	public ExplodeLight(double x, double y) {
		this.x = x;
		this.y = y;
	}
	public ExplodeLight() {
		
	}
}