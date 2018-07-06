package code;

import java.awt.Toolkit;
import java.util.ArrayList;

public class Properties {
	
	public static Hero selHero = Hero.getRandomHero();
	static int width = Toolkit.getDefaultToolkit().getScreenSize().width, height = Toolkit.getDefaultToolkit().getScreenSize().height-40;
	static float gravity = 9.8F;
	static int framerate = 80, port = 8553;
	static String gameName = "Hero Brawl";
	static double ver = 0.1;
	static String gameTitle = "Beta";
	public static float masterVol = 0F;
	public static String map = null;
	public static boolean legal = true, beta = false;
	public static String username = "username";
	static PanelBase startPanel = new PanelMenu();
}
