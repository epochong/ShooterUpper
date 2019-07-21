package cn.yichun.game;

import java.awt.Rectangle;

public class MySuperShell extends Shell{
	
	static long time;
	int hit;
	public  MySuperShell(Plane p) {
		width = 20;
		height = 800;
		x = p.x;
		y = p.y-800;
		speed = 0;
		hit = 0;
		time = System.currentTimeMillis();
	}
	@Override
	public Rectangle getRect() {
		return  new Rectangle((int)x, (int)y, width, height);
	}
}
