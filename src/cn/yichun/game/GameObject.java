package cn.yichun.game;

import java.awt.Graphics;
import java.awt.Image;

public class GameObject extends Thread{
	Image img;
	double x, y;
	int speed;
	int width, height;
	public void drawSelf(Graphics g) {
		g.drawImage(img, (int)x, (int)y, null);
	}
	public GameObject(Image img, double x, double y, int speed, int width, int height) {
		super();
		this.img = img;
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.width = width;
		this.height = height;
	}
	public GameObject(Image img, int x, int y, int speed) {
		super();
		this.img = img;
		this.x = x;
		this.y = y;
		this.speed = speed;
	}
	public GameObject(){
		
	}
}
