package code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Util {
	
	public static File stats = new File(Bank.path+"data/stats.HB");
	public static File blocks = new File(Bank.path+"data/blocks.HB");
	public static File achievements = new File(Bank.path+"data/achievements.HB");
	public static File hats = new File(Bank.path+"data/hats.HB");
	public static File heroes = new File(Bank.path+"data/heroes.HB");

	public static ClipShell music = new ClipShell("crit.wav");
	
	public static Color transparent = new Color(0F,0F,0F,0.45F);
	public static Color transparent_dark = new Color(0F,0F,0F,0.85F);
	public static Color transparent_white = new Color(1F,1F,1F,0.45F);
	public static Color transparent_buttonwhite = new Color(1F,1F,1F,0.15F);
	public static Color waterBlue = new Color(0,147,255,101);
	public static Color blood = new Color(255,0,0,100);
	public static Color orange = new Color(200,150,0,255);
	public static Color yellow = new Color(190,240,0,255);
	public static Color purple = new Color(160,0,220,255);
	public static Color lavender = new Color(200,20,255,255);
	public static Color critGreen = new Color(0,255,100,255);
	public static Color bgoverlay = new Color(100,175,230,185);
	public static Font cooldownFont = new Font(Font.MONOSPACED, Font.PLAIN, 34);
	public static Font dialogFont = new Font(Font.MONOSPACED, Font.PLAIN, 28);
	public static Font dialogBold = new Font(Font.MONOSPACED, Font.BOLD, 28);
	public static Font cooldownBold = new Font(Font.MONOSPACED, Font.BOLD, 34);
	public static Font descFont = new Font(Font.MONOSPACED, Font.PLAIN, 14);
	public static Font upgradeFont = new Font(Font.MONOSPACED, Font.PLAIN, 18);
	public static Font smallDescFont = new Font(Font.MONOSPACED, Font.PLAIN, 12);
	public static Font descTitleFont = new Font(Font.MONOSPACED, Font.BOLD, 16);
	public static Font largeNameFont = new Font(Font.MONOSPACED, Font.BOLD, 54);
	public static Font spellNameFont = new Font(Font.MONOSPACED, Font.BOLD, 34);
	public static Font spellDesc = new Font(Font.MONOSPACED, Font.PLAIN, 22);
	public static Random rand = new Random();
	
	public static ArrayList<GUI> persistentGuis = new ArrayList<GUI>();
	public static Color transparent_green = new Color(0,255,0,220);
	
	public static String getSplashText(){
		DateSplash.initSplashes();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date d = new Date();
		String date = (dateFormat.format(d).split("/")[2]+"/"+dateFormat.format(d).split("/")[1]);
		for(int i = 0; i < DateSplash.all.length; ++i){
			DateSplash ds = DateSplash.all[i];
			if(ds!=null){
				String spld = ds.date;
				if(spld.equalsIgnoreCase(date))
					return DateSplash.all[i].txt;
			}
		}
		String[] splashes = new String[]{
			"For your health!",
			"What are a superfood",
			"Makes you thinner.",
			"Based on a True Story",
			"Based on a Fake Story",
			"Not Based on a Story",
			"Based on a Toy Story",
			"Tell me your secrets",
			"Tell me your password",
			"This land is nothing like Azeroth TM",
			"I love you.",
			"I love you for real this time.",
			"Eat fresh.",
			"You think it's time to go to bed?",
			"You've played "+Analysis.getKey(new File(Bank.path+"data/stats.HB"), "played")+" Quick Games. Wow!",
			"You've taken "+Analysis.getKey(new File(Bank.path+"data/stats.HB"), "tkn")+" damage. Wow!",
			"You've dealt "+Analysis.getKey(new File(Bank.path+"data/stats.HB"), "dmg")+" damage. Wow!",
			"You've healed "+Analysis.getKey(new File(Bank.path+"data/stats.HB"), "healed")+" health. Wow!",
			"You're a star.",
			"Not World of Warcraft.",
			"Not Minecraft.",
			"Thanks for the memories!",
			"Have you had your inner health plus today?",
			"You need more coffee.",
			"Watch some Dr Phil.",
			"Watch some Drake and Josh.",
			"Watch some Whose Line is it Anyway.",
			"Watch some Spongebob.",
			"Watch some Seinfeld.",
			"Watch some Everybody Loves Raymond.",
			"Watch some Friends.",
			"Watch some Becker.",
			"Watch some Cheers.",
			"Watch some Frasier.",
			"Watch some Orange is the New Black.",
			"Watch some Game of Thrones.",
			"Watch Titanic.",
			"Listen to a podcast.",
			"Listen to some NIRVANA.",
			"Listen to some John Mayer.",
			"Listen to some Dean Martin.",
			"Listen to some Meat Puppets.",
			"Listen to some James Brown.",
			"About a girl.",
			"What's your favourite animal?",
			"Come as you Are.",
			"What else could I say? Everyone is gay.",
			"As a friend, as an old enemy.",
			"Relax, take it easy.",
			"Drew Carrey is the new king.",
			"All hail Drew Carrey.",
			"All hail John Wayne.",
			"All hail Spy Kids.",
			"Spy Kids.",
			"èƒ–äºº",
			"ä½ æ˜¯æˆ‘çš„å¸Œæœ›",
			"å¿«ä¹�è°¢è°¢è·Ÿ",
			"å¿«ä¹�çš„è°¢è°¢è·Ÿ",
			"å¿«ä¹�çš„è°¢è°¢ç»™",
			"çŽ°åœ¨ï¼Œæˆ‘æ²¡æœ‰æœ‹å�‹ï¼Œæˆ‘å¸¸å¸¸çŽ©ç”µè§†ï¼Œæˆ‘å¾ˆæ— è�Š",
			"Â¿Hablas inglÃ©s?",
			"Â¿Puede ayudarme?",
			"Â¡Es mucho barato!",
			"La cuenta, por favor",
			"Je suis un poobel!",
			"C'est tres grande!",
			"Don't *poop* on the *bus*.",
			"Spingebab Squirepents",
			"Are you feelin' it now Mr Krabs?",
			"With your eyes, burt!",
		};
		return splashes[rand.nextInt(splashes.length)];
	}
	
	public static void createCircle(int x, int y, int w, int h, int res){
		int count = res;
		int quart1 = res/4, quart2 = (res/2), quart3 = res/4*3;
		for(int i = 0; i < res; ++i){
			//int mx = ;
		}
	}
	
	public static ClipShell getMusic(){
		String pth = "ig.wav";
		File[] files = new File(Bank.path+"tracks").listFiles();
		int r = rand.nextInt(files.length+1);
		if(r!=0)pth=files[r-1].getPath();
		return new ClipShell(pth);
	}
	
	public static Image getBadge(){
		try {
			if(new File(Bank.path+"data/stats.HB").createNewFile())return Bank.heart;
		} catch (IOException e) {
			e.printStackTrace();
		}
		int badgeid = Integer.parseInt(Analysis.getKey(new File(Bank.path+"data/stats.HB"), "badge"));
		return (badgeid==-1?Bank.heart:Achievement.all[badgeid].img);
	}
	
	public Achievement getBadgeAchieve(){
		try {
			if(new File(Bank.path+"data/stats.HB").createNewFile())return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		int badgeid = Integer.parseInt(Analysis.getKey(new File(Bank.path+"data/stats.HB"), "badge"));
		return (badgeid==-1?null:Achievement.all[badgeid]);
	}
	
	public static int getRunes(){
		String s = Bank.getRawdirDataLine(Bank.path+"data/Core.HB");
		if(s==null)return 0;
		else{
			if(s.isEmpty())return 0;
			else return Integer.parseInt(s);
		}
	}
	
	public static int distance(Point p1, Point p2){
		int d = p2.x-p1.x;
		int d1 = p2.y-p1.y;
		if(d<0)d=Math.abs(d);
		if(d1<0)d1=Math.abs(d1);
		return d+d1;
	}
	
	public static int xDist(Point p1, Point p2){
		int d = p2.x-p1.x;
		if(d<0)d=Math.abs(d);
		return d;
	}
	
	public static int dif(int n1, int n2){
		int d = n2-n1;
		if(d<0)d=Math.abs(d);
		return d;
	}
	
	public static int yDist(Point p1, Point p2){
		int d = p2.y-p1.y;
		if(d<0)d=Math.abs(d);
		return d;
	}

	public static Color setAlpha(Color c, int a) {
		return new Color(c.getRed(), c.getGreen(), c.getBlue(), a);
	}
}
