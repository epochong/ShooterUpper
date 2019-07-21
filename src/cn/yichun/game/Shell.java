package cn.yichun.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
//ÅÚµ¯Àà
public class Shell extends GameObject{
	double degree;
	public Shell() {
		x = 200;
		y = 200;
		width = 10;
		height = 10;
		speed = 1;
		degree = Math.random()*Math.PI*2;
	}
	
	public Shell(Boss boss1, double d, Image i) {
		x = boss1.x+90;
		y = boss1.y+210;
		width = 10;
		height = 10;
		speed = 5;
		degree = d;
		this.img = i;
	}
	
	public boolean OnSec() {
		if (x < 0 || x > Constant.GAME_WIDTH || y < 0 || y > Constant.GMAE_HEIGTH)
			return false;
		return true;
	}
	public void draw(Graphics g) {
		drawSelf(g);
		x+=speed*Math.cos(degree);
		y+=speed*Math.sin(degree);
		
	}

	public Rectangle getRect() {
		return  new Rectangle((int)x, (int)y, width, height);
	}
}
