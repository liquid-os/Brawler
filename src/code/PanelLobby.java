package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class PanelLobby extends PanelBase {
	
	String world;
	GUIButton startserver, start, cancel, bute;
	
	public PanelLobby() {
		start = new GUIButton("Start", 1, Properties.width/2-250, Properties.height/2+100, 200, 100).setColor(Color.GREEN);
		cancel = new GUIButton("Cancel", 2, Properties.width/2+50, Properties.height/2+100, 200, 100).setColor(Color.RED);
		startserver = new GUIButton("Start Server", 3, Properties.width/2-250, Properties.height/2-50, 500, 100).setColor(Color.BLUE);
		bute = new GUIButton("Distribute World", 4, Properties.width/2-200, Properties.height/2+250, 400, 50).setColor(Color.MAGENTA);
		guis.add(start);
		guis.add(cancel);
		guis.add(startserver);
		guis.add(bute);
		Bank.client = new GameClient("127.0.0.1");
		Properties.username = JOptionPane.showInputDialog("Enter username");
		this.runBaseProfiler = false;
	}
	
	public void buttonReact(int id){
		if(id==1){
			start();
		}
		if(id==2){
			Display.currentScreen = new PanelMenu(-1);
		}
		if(id==3){
			Bank.server = new GameServer();
			Bank.server.start();
			Packet00Login pkt = new Packet00Login(Properties.username, Properties.selHero.id);
			pkt.write(Bank.server);
		}
		if(id==4){
			new ClipShell("click.wav").start();
			JFileChooser fc = new JFileChooser(Bank.path+"maps");
			fc.showOpenDialog(null);
			File f = fc.getSelectedFile();
			world = f.getName().replace(".HB", "");			
			String dat = Bank.getRawdirDataLine(Bank.path+"maps/"+world+".HB");
			Packet04World pkt = new Packet04World(world.replace(".HB", "").replace("MP", "").replace("/", ""), dat);
			pkt.write(Bank.server);
		}
	}
	
	public void start(){
		Packet05Start packet = new Packet05Start();
		packet.write(Bank.server);
	}

	public void onUpdate() {
	}
	
	public void playerConnected(Packet00Login login){
		guis.add(new GUINotify(Bank.rawplus, "Player Joined", login.username, 0, 0));
	}

	public void drawScreen(Graphics g) {
		for(int i = 0; i < players.size(); ++i){
			Rectangle rect = new Rectangle(0,100+i*80,200,80);
			g.drawImage(rect.contains(mousePoint)?Bank.buttonHover:Bank.button, rect.x, rect.y, rect.width, rect.height, null);
			g.drawImage(((PlayerMP)players.get(i)).getUserIcon(), rect.x, rect.y, rect.height, rect.height, null);
			g.setColor(Color.WHITE);
			g.setFont(Util.cooldownBold);
			g.drawString(players.get(i).getUsername(), rect.x+rect.height, rect.y+40);
			Bank.drawSquare(g, rect.x, rect.y, rect.width, rect.height);
		}
		Bank.drawSquare(g, 0, 100, 200, Properties.height-200);
	}
}
