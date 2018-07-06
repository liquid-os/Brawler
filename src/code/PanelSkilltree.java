package code;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.util.ArrayList;

public class PanelSkilltree extends PanelBase {
	
	ArrayList<Skill> avail = new ArrayList<Skill>();
	int size = 40, level = 1, runes = 0, scrollX = 0, scrollY = 0;
	int[] ranks = new int[255];
	Rectangle hud = new Rectangle(0, Properties.height-220, Properties.width, 220);
	int maxRanks = 25, spent = 0;
	
	public PanelSkilltree() {
		super();
		String str = Analysis.getKey(new File(Bank.path+"chars/"+Properties.selHero.classname+".HB"), "skills");
		String[] stra = str.split(":");
		for(int i = 0; i < stra.length; ++i){
			ranks[i] = Integer.parseInt(stra[i]);
			spent+=ranks[i];
		}
		level = Integer.parseInt(Analysis.getKey(new File(Bank.path+"chars/"+Properties.selHero.classname+".HB"), "lvl"));
		maxRanks = 20 + level / 4;
		if(maxRanks > 35){maxRanks = 35;}
		runes = Util.getRunes();
		for(Skill s: Skill.all){
			if(s!=null){
				if(s.heroReq==null){
					avail.add(s);
				}
				else{
					if(s.heroReq==Properties.selHero)avail.add(s);
				}
			}
		}
		guis.add(new GUIButton(Bank.custbutton, Bank.custbutton, "Confirm", 1, Properties.width/2-200, hud.y+20, 400, 60));
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent  e){
		int notches = e.getWheelRotation();
		System.out.println(notches+"");
		scrollY -= notches * 20;
	}
	
	public void buttonReact(int id){
		if(id==1){
			this.save();
			Display.currentScreen = new PanelMenu(-1);
		}
	}

	public void onUpdate() {
	}
	
	public void linkPrereq(Skill s, Graphics g){
		for(Skill pre : s.preReqs){
			Rectangle rect = new Rectangle((int) (Properties.width/2-((6*(size+40))/2)+(s.col*(size+80))), (int) (s.row*(size+40)+100) + scrollY, size, size);
			Rectangle rect1 = new Rectangle((int) (Properties.width/2-((6*(size+40))/2)+(pre.col*(size+80))), (int) (pre.row*(size+40)+100) + scrollY, size, size);
			Point p2 = new Point(rect1.x, rect1.y+size/2);
			Point p1 = new Point(rect.x, rect.y+size/2);
			g.setColor(full(pre)?Color.GREEN:Color.RED);
			//for(int i = -1; i < 1; ++i)
			//g.drawLine(p1.x+i, p1.y+i, p2.x+i, p2.y+i);
			g.drawLine(p1.x, p1.y, p2.x, p1.y);
			g.drawLine(p2.x, p2.y, p2.x, p1.y);
			g.drawLine(p1.x-1, p1.y-1, p2.x-1, p1.y-1);
			g.drawLine(p2.x-1, p2.y-1, p2.x-1, p1.y-1);
			g.drawLine(p1.x+1, p1.y+1, p2.x+1, p1.y+1);
			g.drawLine(p2.x+1, p2.y+1, p2.x+1, p1.y+1);
		}
	}
	
	boolean full(Skill s){
		return ranks[s.id] >= s.maxRank;
	}
	
	boolean prereqsfull(Skill s){
		boolean ret = true;
		for(Skill sk : s.preReqs){
			if(!full(sk))ret=false;
		}
		return ret;
	}
	
	String buildPreReqList(Skill s){
		String ret = "";
		for(Skill sk : s.preReqs){
			ret+=(sk.name+", ");
		}
		return ret;
	}
	
	public void click(boolean b){
		for(Skill s : avail){
			Rectangle rect = new Rectangle((int) (Properties.width/2-((6*(size+40))/2)+(s.col*(size+80)))-size/2 + scrollX, (int) (s.row*(size+40)+100) + scrollY, size, size);
			boolean hover = rect.contains(mousePoint);
			if(b){
				if(hover&&spent<maxRanks&&level>=s.getMinLvl(ranks[s.id])&&runes>=s.getCost(ranks[s.id])&&(!s.preReqs.isEmpty()?(prereqsfull(s)):true)&&ranks[s.id]<s.maxRank){
					ranks[s.id]++;
					spent++;
					int newv = Integer.parseInt(Bank.getRawdirDataLine(Bank.path+"data/Core.HB"))-s.getCost(ranks[s.id]);
					Bank.setContentsRawdir(Bank.path+"data/Core.HB", newv+"");
					runes-=s.getCost(ranks[s.id]);
				}
			}else{
				if(hover&&spent>0&&ranks[s.id]>0){
					ranks[s.id]--;
					spent--;
				}
			}
		}
	}

	public void drawScreen(Graphics g) {
		g.drawImage(Bank.windowbg, 0, 0, Properties.width, Properties.height, null);
		for(Skill s : avail){
			if(!s.preReqs.isEmpty()){
				linkPrereq(s, g);
			}
		}
		for(Skill s : avail){
			Rectangle rect = new Rectangle((int) (Properties.width/2-((6*(size+40))/2)+(s.col*(size+80)))-size/2, (int) (s.row*(size+40)+100) + scrollY, size, size);
			boolean hover = rect.contains(mousePoint);
			if(hover&!full(s)){
				g.drawImage(Bank.squaregrad, rect.x-8, rect.y-8-(hover&&clicking?1:0), rect.width+16, rect.height+16, null);
			}
			g.drawImage(s.icon, rect.x, rect.y-(hover&&clicking?1:0), rect.width, rect.height, null);
			if(ranks[s.id]>0){
				for(int i = 0; i < ranks[s.id]; ++i){
					g.setColor(new Color(0, 255, 0, 255));
					g.drawRect(rect.x-i, rect.y-(hover&&clicking?1:0)-i, rect.width+i*2, rect.height+i*2);
				}
			}
			if(this.full(s)){
				g.drawImage(Bank.squaregrad_gr, rect.x-5-ranks[s.id], rect.y-5-(hover&&clicking?1:0)-ranks[s.id], rect.width+10+(ranks[s.id]*2), rect.height+10+(ranks[s.id]*2), null);
			}
		}
		for(Skill s : avail){
			int currentRank = ranks[s.id];
			Rectangle rect = new Rectangle((int) (Properties.width/2-((6*(size+40))/2)+(s.col*(size+80)))-size/2, (int) (s.row*(size+40)+100) + scrollY, size, size);
			boolean hover = rect.contains(mousePoint);
			if(hover){
				int baseX = rect.x+rect.width+25, baseY = rect.y+35+s.desc.length*15;
				int h = 130+s.desc.length*15, w = 250;
				g.drawImage(Bank.custbutton, rect.x+rect.width+20, rect.y, w, h, null);
				g.setColor(Color.WHITE);
				g.setFont(Util.descTitleFont);
				g.drawRect(rect.x+rect.width+20, rect.y, w, h);
				g.drawString(s.name+" "+(currentRank==0?"":currentRank==1?"I":currentRank==2?"II":currentRank==3?"III":currentRank==4?"IV":currentRank==5?"V":""), rect.x+rect.width+25, rect.y+20);
				g.setFont(Util.descFont);
				g.setColor(Color.YELLOW);
				for(int i = 0; i < s.desc.length; ++i){
					g.drawString(s.desc[i], rect.x+rect.width+25, rect.y+35+i*15);
				}
				g.setColor(Color.WHITE);
				g.drawString("Prerequisite: "+(s.preReqs.isEmpty()?"None":buildPreReqList(s)), baseX, baseY+5);
				g.setColor(Color.CYAN);
				g.drawString("Current Tier: "+currentRank+"/"+s.maxRank, baseX, baseY+20);
				g.setColor(level>=s.getMinLvl(currentRank+1)?Color.WHITE:Color.RED);
				g.drawString("Next Tier Min Level: "+s.getMinLvl(currentRank+1), baseX, baseY+35);
				g.setColor(runes>=s.getCost(currentRank+1)?Color.WHITE:Color.RED);
				g.drawString("Next Tier Cost: "+s.getCost(currentRank+1), baseX, baseY+60);
				g.setColor(Color.WHITE);
				g.drawImage(Bank.nexus, baseX+g.getFontMetrics().stringWidth("Next Tier Cost: "+s.getCost(currentRank+1))+5, baseY+40, 30, 30, null);
				g.setFont(Util.upgradeFont);
				g.setColor(Color.GREEN);
				g.drawString("Click to Buy Tier "+(currentRank+1), baseX, baseY+85);
			}
		}
		g.drawImage(Bank.gradient, hud.x, hud.y, hud.width, hud.height, null);
		g.setColor(Util.transparent);
		g.fillRect(hud.x, hud.y, hud.width, hud.height);
		Bank.drawSquare(g, hud.x, hud.y, hud.width, hud.height);
		g.setFont(Util.cooldownBold);
		g.setColor(Color.WHITE);
		g.drawString(spent+"/"+maxRanks+" ("+(spent * 100 / maxRanks)+")% full", hud.x+20, hud.y+40);
		g.drawImage(Bank.nexus, 10, 10, 80, 80, null);
		g.drawString(runes+"", 10+40-g.getFontMetrics().stringWidth(runes+"")/2, 60);
		g.setColor(Color.YELLOW);
		g.setFont(Util.spellNameFont);
		g.drawString("Right click to remove rank - Runes not refunded", hud.x+20, hud.y+135);
	}
	
	public void save(){
		StringBuilder sb = new StringBuilder();
		for(int i : ranks){
			sb.append(i+":");
		}
		Analysis.setKey(new File(Bank.path+"chars/"+Properties.selHero.classname+".HB"), "skills", sb.toString());
	}
}
