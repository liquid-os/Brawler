package code;

import java.awt.Color;
import java.awt.Graphics;

public class ActionShell {
	
	ActionMove action = null;
	
	int chance = 0;
	long coolstart = 0;
	
	boolean cdflag = false;
	
	public ActionShell(ActionMove s){
		action = s;
	}
	
	public ActionShell(ActionMove s, int castChance){
		action = s;
		chance = castChance;
	}
	
	public int getCooldown(Player p){
		int cdm = p.cooldownMod;
		cdm+=100*p.countEffect(EffectType.brainfry);
		cdm+=50*p.countEffect(EffectType.sands);
		cdm-=50*p.countEffect(EffectType.haste);
		cdm-=50*p.countEffect(EffectType.windhaste);
		cdm-=75*p.countEffect(EffectType.sandsoftime);
		int extra = (int) (action.getCooldownMillis() / 100 * cdm);
		return (int) (action.getCooldownMillis()+extra);
	}
	
	public long getCooldownOverlay(Player p){
		return (getCooldown(p)-(System.currentTimeMillis()-coolstart));
	}
	
	public void render(Graphics g, Player p, int x, int y, int scale){
		g.drawImage(action.img, x, y, scale, scale, null);
		g.setColor(Util.transparent);
		int h = (int)((getCooldown(p)+p.cooldownMod)-(System.currentTimeMillis()-coolstart));
		g.fillRect(x, y, scale, scale);
		g.setColor(Color.WHITE);
		g.setFont(Util.cooldownFont);
		g.drawString(h+"", x+scale/2-g.getFontMetrics().stringWidth(h+"")/2, y+scale/3*2);
	}
	
	public void cast(Player p){
		if(!p.stunned()){
			p.renderAtk = true;
			if(System.currentTimeMillis()-coolstart >= getCooldown(p)){
				p.atkRenderID = 0;
				p.lastAttack = System.currentTimeMillis(); 
				double damage = (action.statType==0?p.getDamage():p.getHealpow());		
				coolstart = System.currentTimeMillis()+(action.getCooldownMillis());
				action.onUse(p, damage);
			}
			p.renderAtk = false;
		}
	}
	
	public boolean ready(Player p){
		return System.currentTimeMillis()-coolstart >= getCooldown(p);
	}
}
