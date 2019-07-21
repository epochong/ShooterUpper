package cn.yichun.game;

import java.awt.Toolkit;

public class Constant {
	public static int GAME_WIDTH = 800;
	public static int GMAE_HEIGTH;
	public static final int MY_SHELLNUM = 4;
	public static final int BOSS_LIFE = 1000;
	public static final int ENUM = 400;
	public static final int LIGHT_NUM = 5;	static {
		GMAE_HEIGTH = Toolkit.getDefaultToolkit().getScreenSize().height-50;
	}
}
