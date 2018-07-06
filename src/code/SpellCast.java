package code;

import java.awt.Color;
import java.awt.Graphics;

public class SpellCast {
	
	Spell spell = null;
	
	int chance = 0;
	long coolstart = 0;
	
	boolean cdflag = false;
	
	public SpellCast(Spell s){
		spell = s;
	}
	
	public SpellCast(Spell s, int castChance){
		spell = s;
		chance = castChance;
	}
	
	public int getCooldown(Player p){
		int cdm = p.cooldownMod;
		cdm+=100*p.countEffect(EffectType.brainfry);
		cdm-=50*p.countEffect(EffectType.haste);
		cdm-=50*p.countEffect(EffectType.retaliation);
		cdm-=50*p.countEffect(EffectType.windhaste);
		cdm-=75*p.countEffect(EffectType.sandsoftime);
		int extra = (spell.getCooldownMillis() / 100 * cdm);
		return spell.getCooldownMillis()+extra;
	}
	
	public long getCooldownOverlay(Player p){
		return (getCooldown(p)-(System.currentTimeMillis()-coolstart));
	}
	
	public void renderOverlay(Graphics g, Player p, int x, int y, int scale){
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
				double damage = (spell.statType==0?p.getDamage():p.getHealpow());
				String notes = "";
				if(p.crit-Util.rand.nextInt(101)>=0){
					notes = "crit";
					damage*=p.critMultiplier;
				}
				if(spell==Spell.chill){
					if(Util.rand.nextInt(5)==0){
						notes+=":chl";
					}
				}
				if(p.hero.equals(Hero.mesmer)){
					if(p.hasSkill(Skill.insanity)){
						if(Util.rand.nextInt(10)==0)notes+=":ins";
					}
				}
				if(p.countEffect(EffectType.quickness) > 0){
					p.removeEffect(EffectType.quickness);
				}else
				coolstart = System.currentTimeMillis()+(spell.getCooldownMillis());
				spell.cast(p, damage, notes);
				if(spell.getCooldownMillis() >= 3000){
					p.healRaw(p.healthMax / 20 * p.getSkillTier(Skill.recovery), false);
				}
			}
			p.renderAtk = false;
		}
	}
	
	public boolean ready(Player p){
		return System.currentTimeMillis()-coolstart >= getCooldown(p);
	}
}
