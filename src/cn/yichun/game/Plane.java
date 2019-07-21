package cn.yichun.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import cn.yichun.game.MyGameFrame.PaintThread;

public class Plane extends GameObject{
	
	boolean left, up, right, down, shoot, live, shoot2; 
	int shellNum;
	long deiTime;
	long birthTime = 4000;
	static Image[] imgs2 = new Image[2];
	static {
		for (int i = 0; i < 2; i++) {
			imgs2[i] = GameUtil.getImage("images/Hero" + (i + 1) + ".png");
			imgs2[i].getWidth(null);
		}
	}
	
	@Override
	public void drawSelf(Graphics g) {
		if (!live) {
			x = -500;
			y = -500;
			speed = 0;
		}
		Image img = null;
		if (System.currentTimeMillis() % 70 > 35) {
			img = imgs2[0];
		} else{
			if(System.currentTimeMillis() - birthTime <= 2000)//实现无敌时间的飞机闪烁
			{
				img = null;
			} else {
				img = imgs2[1];
			}
		}

		g.drawImage(img, (int)x, (int)y, null);
		
		if(left && x > speed)//附加了边界处理
			x -= speed;
		if(right && x < Constant.GAME_WIDTH-65)//减去飞机宽度65
			x += speed;
		if(up && y > speed+30+25)//边框30+菜单栏25
			y -= speed;
		if(down && y < Constant.GMAE_HEIGTH-70)//飞机长度70
			y += speed;
	}
	
	public Plane(int x, int y, int speed, int width, int height) {
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.speed = 5;
		live = true;
		shellNum = Constant.MY_SHELLNUM; 
	}
	public void drawWid(Graphics g) {
		g.drawRect((int)(x+15), (int)y, width-30, height);
	}
	public Rectangle getRect() {
		return  new Rectangle((int)(x+15), (int)y, width-30, height);
	}
	

	public void changeSpeed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_C) {
			this.speed+=2;
		}
		if(this.speed >= 13) {
			this.speed = 5;
		}
	}

	public void isShoot(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_X) {
			shoot = true;
		}
	}
	public void isShoot2(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_S) {
			shoot2 = true;
			//System.out.println(shoot2);
		}
	}
	public void notShoot(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_X) {
			shoot = false;
		}
	}
	public void notShoot2(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_S) {
			shoot2 = false;
		}
	}
	public void addDirection(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				left = true;
				break;
			case KeyEvent.VK_UP:
				up = true;
				break;
			case KeyEvent.VK_RIGHT:
				right = true;
				break;
			case KeyEvent.VK_DOWN:
				down = true;
				break;
			default:
				break;
		}
	}
	public void minusDirection(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		case KeyEvent.VK_UP:
			up = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		case KeyEvent.VK_DOWN:
			down = false;
			break;
		default:
			break;
		}
	}
}