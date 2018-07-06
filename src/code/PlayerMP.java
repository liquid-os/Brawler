package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.net.InetAddress;

public class PlayerMP extends Player{
	
	InetAddress ip;
	int port = Properties.port, clientScroll = 0, userIcon = rand.nextInt(5);

	public PlayerMP(Hero h, String user, int x, int y, InetAddress ip, int port) {
		super(h, user, x, y);
		this.ip = ip;
		this.port = port;
	}
	
	public Image getUserIcon(){
		return userIcon==0?Bank.dancing:userIcon==1?Bank.chicken:userIcon==2?Bank.boogie:userIcon==3?Bank.puppet:Bank.skele;
	}
	
	public void draw(Graphics g) {	
		if(health>0){
			renderHUD(g, 20+Display.currentScreen.players.indexOf(this)*400, 20, 100);
			Image tex = this.getTexAndDraw(g);
		}
	}
}
