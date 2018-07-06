package code;

import java.awt.Point;
import java.util.ArrayList;

public class HeroAI {
	
	Player p;
	int range = 30, jumpSpace = 1000;
	int freeX = -1;
	long lastJump = -1, lastAct = -1;
	
	public HeroAI(Player player){
		this.p = player;
	}
	
	public void execute(Object e, double delta){
		Coordinate d = new Coordinate(e.posX, e.posY);
		Grid grid = Display.currentScreen.getGrid();
		if(e.posY < p.posY-90 && p.collideDown()){
			p.jump();
		}else{
			d = new Coordinate(e.posX, e.posY);
		}
		if(lastAct==-1)lastAct = System.currentTimeMillis();
		if(System.currentTimeMillis()>=lastJump+jumpSpace){
			p.jump();
			lastJump = System.currentTimeMillis();
			jumpSpace = 850+Util.rand.nextInt(1351);
		}
		if(p.dialog==Dialog.masterPrince){
			if(lastAct+20000 < System.currentTimeMillis()){
				p.chats.add(new Textbox(new String[]{"I grow weary of chasing","you about!"}, 3250));
				ArrayList<SpellCast> newspells = new ArrayList<SpellCast>();
				newspells.add(new SpellCast(Spell.bonefire));
				newspells.add(new SpellCast(Spell.bloodbolt));
				newspells.add(new SpellCast(Spell.siphon));
				newspells.add(new SpellCast(Spell.aegis));
				p.width = 60;
				p.height = 120;
				p.posY-=30;
				p.heal(p.healthMax/2, false);
				p.spells = newspells;
				lastAct = System.currentTimeMillis();
			}
		}
		if(p.dialog==Dialog.masterMesmer){
			if(lastAct+6500 < System.currentTimeMillis()){
				int sc = p.rand.nextInt(4);
				switch(sc){
				case 0: 
					new SpellCast(Spell.psychnova).cast(p);
					break;
				case 1:
					for(int i = 0; i < 3; ++i){
						p.addEffect(new Effect(1, EffectType.mindcharge, p, -1));
					}
					break;
				case 2:
					int w = 300, h = 180;
					CreatureDeciever dc = new CreatureDeciever(p, p.posX+p.width/2-w/2, p.posY+p.height-h, 50, 8);
					dc.width = w;
					dc.height = h;
					Display.currentScreen.objects.add(dc);
					break;
				case 3:
					for(int i = 0; i < 3+p.rand.nextInt(7); ++i){
						ObjectDummy dm = new ObjectDummy(Bank.brain, p.posX+p.width/2+(p.rand.nextInt(300)-p.rand.nextInt(300)), p.posY+(p.rand.nextInt(300)-p.rand.nextInt(300)), 40, 40);
						Display.currentScreen.objects.add(dm);
					}
					break;
				}
				lastAct = System.currentTimeMillis();
			}
		}
		if(p.dialog==Dialog.masterBarbarian){
			if(lastAct+10000 < System.currentTimeMillis()){
				int type = -1;
				if(p.countEffect(EffectType.sword) > 0){
					p.removeEffect(EffectType.sword, 1);
					type = 0;
				}
				if(p.countEffect(EffectType.axe) > 0){
					p.removeEffect(EffectType.axe, 1);
					type = 1;
				}
				if(p.countEffect(EffectType.mace) > 0){
					p.removeEffect(EffectType.mace, 1);
					type = 2;
				}
				if(type==0){
					p.effects.add(new Effect(0, EffectType.axe, p, -1));
					p.chats.add(new Textbox(new String[]{"Your blows grow","stronger... Perhaps  I will","require this after all."}, 3250));
				}
				if(type==2){
					p.effects.add(new Effect(0, EffectType.sword, p, -1));
					p.chats.add(new Textbox(new String[]{"I will return","to my blade!","Your blood years","for liberation."}, 3800));
				}
				if(type==1){
					p.effects.add(new Effect(0, EffectType.mace, p, -1));
					p.chats.add(new Textbox(new String[]{"This will show","your weakness in","practice."}, 2000));
				}
				p.posX = 830-p.panel.scrollX;
				p.posY = 530-p.panel.scrollY;
				lastAct = System.currentTimeMillis();
			}
		}
		if(d.x-p.width > p.posX+range){p.right = true;p.left = false;p.trueDir = 0;}
		if(d.x < p.posX-p.width-range){p.left = true;p.right = false;p.trueDir = 1;}
		for(int i = 0; i < p.spells.size(); ++i){
			if(p.getSpell(i)!=null){
				boolean doCast = true;
				if(!p.getSpell(i).ready(p)||p.countEffect(EffectType.feedback)>0){
					doCast = false;
				}
				if(p.countEffect(EffectType.shroud) > 0){
					if(Util.distance(new Point(p.posX+p.width/2, p.posY+p.height/2), new Point(e.posX+e.width/2, e.posY+e.height/2)) > 70){
						doCast = false;
					}
				}
				if(p.getSpell(i).spell==Spell.unequip&&p.dialog==Dialog.masterBarbarian)doCast = false;
				if(doCast)p.getSpell(i).cast(p);
			}
		}
	}
}
