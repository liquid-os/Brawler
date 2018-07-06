package code;

import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Spell {
	
	static Spell[] all = new Spell[500];
	int cooldown = 0, baseval = 0, id = 0, minlvl = 1, statType = 0;
	Image sprite = null;
	String name;
	String[] desc = null;
	static Random rand = new Random();
	
	public static final Spell lunarblast = new Spell(0, "Lunar Blast", 6, 550, Bank.spell_cosmiclash).setDesc(new String[]{"A rather powerful blast","with a comparatively","long cooldown for its","type."});
	public static final Spell dropcomet = new Spell(1, "Drop Comet", 14, 1850, Bank.spell_comet).setDesc("Drops a comet from the sky.");
	public static final Spell moonlight = new Spell(2, "Moonlight", 1, 4550, Bank.spell_moonlight).setDesc(new String[]{"Summons a ray of moonlight that","increases damage taken by","enemies inside."});
	public static final Spell jab = new Spell(3, "Focus Punch", 4, 250, Bank.spell_jab).setDesc("A quick, weak punch.");
	public static final Spell chiheal = new Spell(4, "Chi Renewal", 10, 6550, Bank.spell_chiheal).setStatType(1).setDesc("Heals you.");
	public static final Spell greatfirefist = new Spell(5, "Great Fire Fist", 11, 3800, Bank.spell_greatfirefist).setDesc(new String[]{"A high impact and short","lasting fist of fire."});
	public static final Spell ninefuryfist = new Spell(6, "Fist of Nine Furies", 2, 5550, Bank.spell_furyfist).setLevel(4).setDesc("Nine fists launch forward.");
	public static final Spell tempeststrike = new Spell(7, "Wild Tempest Strike", 7, 3550, Bank.spell_tempeststrike).setLevel(7).setDesc(new String[]{"Summons a flying fist that launches","enemies upward."});
	public static final Spell twelvetemplestrike = new Spell(8, "Twelve Temple Strike", 1, 7550, Bank.spell_tempeststrike).setLevel(15).setDesc(new String[]{"Launches a series of punches for the next 3sec."});
	public static final Spell craneleap = new Spell(9, "Leaping Crane", 2, 1200, Bank.spell_jab).setLevel(11);
	public static final Spell tigerleap = new Spell(10, "Pouncing Tiger", 2, 1200, Bank.spell_furyfist).setLevel(11);
	public static final Spell mindleap = new Spell(11, "Mindleap", 2, 1850, Bank.spell_mindleap).setDesc("Teleports you upward.");
	public static final Spell icicle = new Spell(12, "Needle of Frost", 3, 160, Bank.spell_icicle).setDesc(new String[]{"A very weak but","incredibly quick attack."});
	public static final Spell manabomb = new Spell(13, "Mana Bomb", 50, 5000, Bank.spell_manabomb).setDesc(new String[]{"Places a mana bomb that detonates","0.3sec after enemy contact.","The detonation can harm the caster."});
	public static final Spell castigate = new Spell(14, "Castigate", 6, 340, Bank.spell_holystrike).setDesc("A powerful strike.");
	public static final Spell clemency = new Spell(15, "Spiritual Clemency", 2, 10650, Bank.spell_retribution).setDesc(new String[]{"You are healed over","time, with the amount","increasing each tick."}).setStatType(1);
	public static final Spell bulwark = new Spell(16, "Bulwark", 30, 8650, Bank.spell_defend).setStatType(1).setDesc("Absorbs average damage.");
	public static final Spell aegis = new Spell(17, "Aegis", 10, 1200, Bank.spell_defend).setStatType(1).setDesc("Absorbs small damage.");
	public static final Spell sanctuary = new Spell(18, "Divine Sanctuary", 200, 18000, Bank.spell_defend).setStatType(1).setDesc("Absorbs huge damage.");
	public static final Spell stab = new Spell(19, "Thunderblade", 4, 220, Bank.spell_shiv).setDesc(new String[]{"A quick, relatively","strong attack"});
	public static final Spell dash = new Spell(20, "Displacement", 100, 300, Bank.spell_dash).setDesc("You teleport forward.");
	public static final Spell thunder = new Spell(21, "Shockstrike", 7, 2300, Bank.spell_thunderblade).setDesc("A massively powerful strike.");
	public static final Spell electrify = new Spell(22, "Storm Bolt", 9, 200, Bank.spell_stormbolt).setDesc(new String[]{"Launches a storm bolt with","100% chance to critically strike.","The bolt electrifies the target,","slowing them. Storm bolting an","electrified target will hit you also.","Applies 'Feedback' when cast.","Using an ability with feedback will hurt you."});
	public static final Spell shroud = new Spell(23, "Shroud", 60, 3500, Bank.spell_shroud).setDesc(new String[]{"You become invisible, and","your speed is increased by 100%.","Damage of a single attack increased.","Using an ability will cancel this."});
	public static final Spell voidbolt = new Spell(24, "Void Crash", 10, 5500, Bank.spell_voidbolt).setDesc("A powerful blast of chaos.");
	public static final Spell banshee = new Spell(25, "Banshee Curse", 3, 1500, Bank.spell_banshee).setDesc(new String[]{"Deals damage over 4sec.","Health pack spawns on end.","Reaching 4 stacks will incur","[Hellforged Seal]","Duration increased by POWER."});
	public static final Spell submission = new Spell(26, "Submission", 1250, 4750, Bank.spell_submission).setDesc(new String[]{"Stuns the target for 1.25sec.","Duration increased by POWER."});
	public static final Spell bonefire = new Spell(27, "Bonefire", 4, 500, Bank.spell_bonefire).setDesc(new String[]{"Fires a flaming skull."});
	public static final Spell lightflame = new Spell(28, "Lightflame", 5, 3900, Bank.spell_torch).setStatType(1).setLevel(4).setDesc(new String[]{"Burns all enemies and heals you."});
	public static final Spell timeflip = new Spell(29, "Neutralize", 8, 6700, Bank.spell_defend).setDesc(new String[]{"Creates a static field that","manipulates projectiles inside."});
	public static final Spell belcher = new Spell(30, "Summon Plague Spewer", 60, 500, Bank.spell_defend);
	public static final Spell bind = new Spell(31, "Binding Blast", 6, 2250, Bank.spell_bind).setDesc("Resets target's momentum.");
	public static final Spell rebuke = new Spell(32, "Rebuke", 9, 650, Bank.spell_rebuke).setDesc(new String[]{"Deals high damage."}).setLevel(7);
	public static final Spell eclipse = new Spell(33, "Lunar Barrage", 2, 6650, Bank.spell_eclipse).setLevel(20).setDesc(new String[]{"Launches a cluster","of stars ahead,","dealing huge damage."});
	public static final Spell stardust = new Spell(34, "Stardust", 2, 4475, Bank.spell_stardust).setLevel(11).setDesc(new String[]{"Your attacks trigger a stun."});
	public static final Spell cosmiclash = new Spell(35, "Galacticlash", 3, 355, Bank.spell_moonstrike).setLevel(5).setDesc("Fires a quick, weak attack.");
	public static final Spell dart = new Spell(36, "Blowdart", 4, 255, Bank.spell_chiheal);
	public static final Spell maw = new Spell(37, "Maw of Eternity", 4, 7255, Bank.spell_maw).setDesc(new String[]{"Deals damage over 9sec.","Duration increased by POWER."});
	public static final Spell creeper = new Spell(38, "Creeper", 3, 6255, Bank.spell_creeper).setDesc(new String[]{"Damage dealt to afflicted","enemies will spawn a","healthpack for","20% of the amount.","Lasts 4sec.","Duration increased by POWER."});
	public static final Spell cataclysm = new Spell(39, "Cataclysm", 4, 8350, Bank.spell_cataclysm).setDesc(new String[]{"An effect that accumulates","damage before releasing it","after nine seconds."});
	public static final Spell volatility = new Spell(40, "Volatility", 6, 4500, Bank.spell_volatility).setDesc(new String[]{"Places a mark on the enemy,","making the next critical","in 5sec trigger extra","damage and a 3sec stun."});
	public static final Spell oakfist = new Spell(41, "Oakfist", 4, 200, Bank.spell_oakfist).setDesc(new String[]{"Basic attack."});
	public static final Spell tree = new Spell(42, "Tree", 3, 1200, Bank.spell_tree).setStatType(1).setDesc(new String[]{"Summons a tree."});
	public static final Spell spores = new Spell(43, "Life Spores", 2, 3000, Bank.spell_spores).setStatType(1).setDesc(new String[]{"8sec compounding heal."});
	public static final Spell clobber = new Spell(44, "Clobber", 450, 1200, Bank.spell_clobber).setDesc(new String[]{"Stuns an enemy for 0.5sec."});
	public static final Spell spores1 = new Spell(45, "Eternity Spores", 6, 12000, Bank.spell_eternityspores).setStatType(1).setDesc(new String[]{"20sec compounding heal."});
	public static final Spell thorns = new Spell(46, "Bloodthorns", 3000, 6000, Bank.spell_thorns).setDesc(new String[]{"Reflects direct damage for 3sec.","Duration is increased by POWER."});
	public static final Spell reap = new Spell(47, "Reap", 5, 300, Bank.spell_reap).setDesc(new String[]{"Heals for 50% of the damage","dealt."});
	public static final Spell sacrifice = new Spell(48, "Sacrifice", 2, 5500, Bank.spell_sacrifice).setDesc(new String[]{"You sacrifice 50% of your","health, and deal 3x","damage for 5sec.","Cannot be healed for","the duration."});
	public static final Spell bloodbolt = new Spell(49, "Bloodbolt", 10, 700, Bank.spell_bloodbolt).setDesc(new String[]{"You sacrifice 10% of your","health, and fire a bloodbolt."});
	public static final Spell siphon = new Spell(50, "Siphon", 8, 3500, Bank.spell_bloodbeam).setDesc(new String[]{"You siphon life from","neaby enemies."});
	public static final Spell bloodsteed = new Spell(51, "Bloodsteed", 9, 7500, Bank.spell_bloodsteed).setDesc(new String[]{"You turn into a Bloodsteed,","allowing you to charge an enemy","and stun them."});
	public static final Spell joint = new Spell(52, "Joint Toss", 4, 300, Bank.spell_joint).setDesc(new String[]{"You's wastin' it."});
	public static final Spell puff = new Spell(53, "Puff and Pass it on", 14, 7500, Bank.spell_puff).setStatType(1).setDesc(new String[]{"You take a puff.","Who cares man it's like...","a heal or something.","I don't know."});
	public static final Spell dankblast = new Spell(54, "Dankblast", 11, 4500, Bank.spell_kushbolt).setDesc(new String[]{"GET OUT THE WAAAAY FOOL!"});
	public static final Spell quickscope = new Spell(55, "Quickscope", 8, 3500, Bank.spell_quickscope).setDesc(new String[]{"MOOOOOOOOOOOOOM"});
	public static final Spell punch = new Spell(56, "Punch", 4, 200, Bank.spell_jab).setDesc("You punch with your fists.");
	public static final Spell bandage = new Spell(57, "Bandage", 65, 4700, Bank.spell_bandage).setStatType(1).setDesc("Instantly heals you.");
	public static final Spell enrage = new Spell(58, "Enrage", 10, 6000, Bank.spell_sacrifice).setDesc(new String[]{"You enrage yourself,","increasing damage output","by 200% for a short time."});
	public static final Spell slam = new Spell(59, "Slam", 10, 4000, Bank.fist).setDesc(new String[]{"You jump skyward, before","slamming into the earth.","The impact will destroy","the terrain."});
	public static final Spell shamanblast = new Spell(60, "Shaman Blast", 7, 3000, Bank.spell_vitality).setDesc(new String[]{"You summon a powerful","shaman projectile."});
	public static final Spell shamanheal = new Spell(61, "Ritual Chant", 20, 3000, Bank.spell_shamanheal).setDesc(new String[]{"You heal yourself."});
	public static final Spell meteor = new Spell(62, "Earthfall", 13, 2000, Bank.bansheecoil).setDesc(new String[]{"A shamanistic comet","falls from the sky."});
	public static final Spell dragonkick = new Spell(63, "Long-Fire Kick", 8, 1100, Bank.spell_dragonkick).setLevel(9).setDesc(new String[]{"You invoke the power","of an ancient dragon","to kick and burn a foe."});
	public static final Spell meditate = new Spell(64, "Meditate", 4, 5000, Bank.spell_chifury).setLevel(27).setDesc(new String[]{"You are empowered with","zen, increasing speed,","healing recieved and","damage dealt."});
	public static final Spell chislumber = new Spell(65, "Zen Slumber", 1150, 4000, Bank.spell_slumber).setLevel(17).setDesc(new String[]{"You fire a wave","of Chi, putting enemies","to sleep."});
	public static final Spell chislice = new Spell(66, "Chi Slice", 6, 950, Bank.spell_chislice).setLevel(13).setDesc(new String[]{"You strike an enemy","with the side of your hand."});
	public static final Spell zenkick = new Spell(67, "Zen River Kick", 12, 1100, Bank.spell_riverkick).setLevel(20).setDesc(new String[]{"You invoke the serenity","of a Zen River to","deliver a mighty kick."});
	public static final Spell chikick = new Spell(68, "Chi Summer Kick", 10, 1450, Bank.spell_kick).setLevel(23).setDesc(new String[]{"You release a burst","into a kick, dealing","damage and healing you."});
	public static final Spell chill = new Spell(69, "Frost Shock", 3, 300, Bank.spell_chill).setDesc(new String[]{"Deals damage and has","a 20% chance to CHILL","enemies. If the","target is already","chilled, they have a","chance to be frozen."});
	public static final Spell flashfreeze = new Spell(70, "Flash Freeze", 7, 5750, Bank.spell_flashfreeze).setDesc(new String[]{"You instantly freeze","nearby enemies and","deal damage to them."});
	public static final Spell glacialwinds = new Spell(71, "Glacial Winds", 6, 3750, Bank.spell_winds).setDesc(new String[]{"Launches extremely cold","winds, dealing damage","and CHILLING enemies."});
	public static final Spell frostlink = new Spell(72, "Polar Blast", 9, 4250, Bank.spell_frostlink).setLevel(4).setDesc(new String[]{"Launches a bolt of","frost, dealing large","damage. If the enemy","is FROZEN, it will","deal double damage and","shatter the ice,","stunning the enemy also."});
	public static final Spell penguin = new Spell(73, "Invoke Penguin", 4, 4250, Bank.spell_penguin).setLevel(7).setDesc(new String[]{"Summons a penguin","to shoot left","and right","constantly."});
	public static final Spell snowman = new Spell(74, "Jack's Frost", 6, 4250, Bank.spell_snowman).setLevel(12).setDesc(new String[]{"* SPELL UNFINISHED *"});
	public static final Spell coldsnap = new Spell(75, "Cold Snap", 6, 4250, Bank.spell_coldsnap).setLevel(9).setDesc(new String[]{"* SPELL UNFINISHED *"});
	public static final Spell brittle = new Spell(76, "Brittle Bones", 6, 4250, Bank.spell_bones).setLevel(17).setDesc(new String[]{"* SPELL UNFINISHED *"});
	public static final Spell reinforce = new Spell(77, "Reinforce", 6, 5000, Bank.spell_bones).setLevel(3).setDesc(new String[]{"You summon a","guard."});
	public static final Spell arrow = new Spell(78, "Arrow", 4, 350, Bank.spell_arrow).setLevel(1).setDesc(new String[]{"You fire an arrow,","dealing low damage."});
	public static final Spell shockarrow = new Spell(79, "Shock Arrow", 2000, 3000, Bank.spell_shockarrow).setLevel(1).setDesc(new String[]{"You fire a coiling","shock arrow to","stun a foe for 2sec."});
	public static final Spell eagle = new Spell(80, "Eagle Call", 6, 5000, Bank.spell_eagle).setLevel(1).setDesc(new String[]{"You summon an","eagle."});
	public static final Spell firearrow = new Spell(81, "Scorching Arrow", 4, 1200, Bank.spell_firearrow).setLevel(1).setDesc(new String[]{"You fire a flaming","arrow to deal low","damage plus at least 200%","over at least 2sec.","Duration increased by POWER.","Ticks every 0.5sec for","50% of the initial hit."});
	public static final Spell ghostarrow = new Spell(82, "Spectral Arrow", 8, 6000, Bank.spell_ghostarrow).setLevel(1).setDesc(new String[]{"Consumes all effects","on enemy, dealing","25% more damage","for each. Also leaves","the enemy haunted,","reducing healing taken by","50% for 6sec."});
	public static final Spell blackarrow = new Spell(83, "Black Arrow", 8, 2500, Bank.spell_blackarrow).setLevel(1).setDesc(new String[]{"Deals moderate damage.","Critical strikes","heal the caster."});
	public static final Spell executioner = new Spell(84, "The Executioner", 4, 500, Bank.spell_executioner).setLevel(1).setDesc(new String[]{"Deals small damage.","does 500% to targets","below 20% HP."});
	//public static final Spell hunt = new Spell(85, "Hunting", 0, 100, Bank.spell_hunt).setLevel(1).setDesc(new String[]{"Applies the 'HUNTED'","effect to everything","for 4sec."});
	public static final Spell barrage = new Spell(85, "Barrage", 2, 5000, Bank.spell_hunt).setLevel(1).setDesc(new String[]{"You fire an arrow","every 0.2sec for","3 seconds."});
	public static final Spell poisonshot = new Spell(86, "Toxic Shot", 4, 2400, Bank.spell_toxicarrow).setLevel(1).setDesc(new String[]{"You fire a toxic","arrow to deal damage","and increase damage","taken by the enemy","by 50% for 3sec."});
	public static final Spell spidershot = new Spell(87, "Venom Shot", 6, 2200, Bank.spell_venomarrow).setLevel(1).setDesc(new String[]{"You fire a venomous","arrow to deal damage","and slow the enemy for","2 seconds."});
	public static final Spell mindtwist = new Spell(88, "Mind Twist", 3, 350, Bank.spell_mindtwist).setLevel(1).setDesc(new String[]{"A bold of focused","mental energy launches","forward, dealing damage."});
	public static final Spell brainrot = new Spell(89, "Brain Rot", 4, 2400, Bank.spell_brainrot).setLevel(1).setDesc(new String[]{"Deals damage every 700ms","for 2.4 seconds."});
	public static final Spell focus = new Spell(90, "Absolute Focus", 1, 2500, Bank.spell_illusion).setLevel(1).setDesc(new String[]{"You gain a stack of","Mesmeric Charge."});
	public static final Spell deception = new Spell(91, "Summon: Deciever", 10, 4000, Bank.spell_deceit).setLevel(19).setDesc(new String[]{"You summon a","minion that will","find an enemy to kill.","Anyone who hits","the minion will","become the new target."});
	public static final Spell clockshock = new Spell(92, "Clock Shock", 3, 400, Bank.spell_clockshock).setLevel(1).setDesc(new String[]{"An enchanted clock","emerges from your hands,","dealing damage and applying","a small damage over","time effect to an enemy."});
	public static final Spell redirect = new Spell(93, "Chrono-Transfer", 7, 2000, Bank.spell_redirect).setLevel(1).setDesc(new String[]{"You swap effects with","a target."});
	public static final Spell shattertime = new Spell(94, "Shatter Time", 12, 10000, Bank.spell_shattertime).setLevel(1).setDesc(new String[]{"You warp the space-time","continuum to create","an enormous rift,","applying 3 powerful damage","over time effects to","yourself."});
	public static final Spell brilliantsands = new Spell(95, "Brilliant Sands", 6, 3000, Bank.spell_healingsands).setStatType(1).setLevel(3).setDesc(new String[]{"You weave brilliant","magical sands to heal","yourself."});
	public static final Spell eternitysands = new Spell(96, "Veil of Sands", 1, 3500, Bank.spell_sandsoftime).setStatType(1).setLevel(5).setDesc(new String[]{"You cloak yourself in","an aura of magical sand,","healing you every second","for 10sec."});
	public static final Spell psychnova = new Spell(97, "Psych Nova", 6, 2000, Bank.spell_psychnova).setLevel(11).setDesc(new String[]{"You launch a pulse","of psychic energy","outward from your","position, dealing","damage to enemies.",""});
	public static final Spell shadeform = new Spell(98, "Ethereal Form", 6, 8000, Bank.spell_ethereal).setLevel(5).setDesc(new String[]{"You take the form of","a mesmeric cloud to","increase movement speed and","increase damage output by 50%","Causes "+mindtwist.name+" to consume","Mesmeric Charges also.","Reduces damage taken","by 50% and lasts","4 seconds."});
	public static final Spell psychmissile = new Spell(99, "Psychic Missile", 11, 4000, Bank.spell_psychmissile).setLevel(3).setDesc(new String[]{"A powerful psychic","missile crashes into","a foe to deal large","damage."});
	public static final Spell brainfry = new Spell(100, "Brain Fry", 3, 2400, Bank.spell_brainfry).setLevel(8).setDesc(new String[]{"Fries an enemy's brain,","dealing small damage","every second for 4","seconds. Increases","cooldowns by 100%","while active."});
	public static final Spell corruption = new Spell(101, "Corruption", 2, 3500, Bank.spell_corruption).setLevel(16).setDesc(new String[]{"Corrupts an enemy's","mind, dealing small","damage every second","for 8 seconds.","Also attempts to","consume an effect","each second, increasing","damage by 50% if","successful."});
	public static final Spell sandsoftime = new Spell(102, "Sands of Time", 1, 3500, Bank.spell_hourglass).setLevel(9).setDesc(new String[]{"Increases speed and reduces","cooldowns by 75% for 2.5sec."});
	public static final Spell rabeam = new Spell(103, "Burst of Ra", 5, 2500, Bank.spell_rabeam).setLevel(13).setDesc(new String[]{"Fires a beam","to deal damage."});
	public static final Spell radiantsigil = new Spell(104, "Radiant Sigil", 5, 6000, Bank.spell_sandsigil).setLevel(9).setDesc(new String[]{"You summon a","floating sigil that","periodically releases","holy bursts to","deal you and deal","damage. If the","sigil takes damage,","it will become","corrupt, causing it","to harm you and","heal enemies."});
	public static final Spell holynova = new Spell(105, "Anubic Pulse", 3, 575, Bank.spell_holynova).setStatType(1).setLevel(7).setDesc(new String[]{"You release a powerful","burst of holy power,","hitting multiple foes.","Critical strikes will","corrupt the pulse,","causing it to heal","enemies."});
	public static final Spell strikeAxe = new Spell(106, "Hack", 4, 325, Bank.spell_hack).setLevel(1).setDesc(new String[]{""});
	public static final Spell whirlAxe = new Spell(107, "Whirl", 2, 3500, Bank.spell_whirl).setLevel(1).setDesc(new String[]{"You whirl around","constantly, dealing","damage to enemies","either side of you","every 0.1sec for","2 seconds."});
	public static final Spell strikeBlade = new Spell(108, "Slice", 3, 165, Bank.spell_slice).setLevel(1).setDesc(new String[]{"You swiftly","strike with your","sword."});
	public static final Spell superBlade = new Spell(109, "Ravage", 4, 1400, Bank.spell_hemmorage).setLevel(1).setDesc(new String[]{"You ravage enemies,","causing them to","bleed for average","damage and be slowed","every 0.5sec for","2 seconds."});
	public static final Spell strikeMace = new Spell(110, "Slam", 5, 450, Bank.spell_slam).setLevel(1).setDesc(new String[]{"You slam enemies","with your hammer,","dealing large damage."});
	public static final Spell stunMace = new Spell(111, "Pound", 9, 3000, Bank.spell_pound).setLevel(1).setDesc(new String[]{"You pound enemies","into the ground,","concussing them for","2 seconds and dealing","damage. This concussion","increases damage","taken by 100%."});
	public static final Spell mindshield = new Spell(112, "Mindguard", 18, 3575, Bank.spell_brainsurge).setLevel(20).setDesc(new String[]{"Absorbs damage for 5.75sec.","Generates a Mesmeric","charge."});
	public static final Spell equipMace = new Spell(113, "Valhalla", 1, 3575, Bank.icon_axe).setLevel(1).setDesc(new String[]{"You equip the","colossal mace","Valhalla."});
	public static final Spell equipBlade = new Spell(114, "Valkyrie", 1, 3575, Bank.icon_blade).setLevel(1).setDesc(new String[]{"You equip the","mighty blade","Valkyrie."});
	public static final Spell equipAxe = new Spell(115, "Vanguard", 1, 3575, Bank.icon_axe).setLevel(1).setDesc(new String[]{"You equip the","legendary axe","Vanguard and a","shield."});
	public static final Spell unequip = new Spell(116, "Disarm", 4, 4000, Bank.spell_disarm).setLevel(1).setDesc(new String[]{"You drop your weapon."});
	public static final Spell rage = new Spell(117, "Ancient Rage", 5, 3000, Bank.spell_rage).setLevel(1).setDesc(new String[]{"You are filled","with ancient rage,","increasing damage","dealt by 100%","for 5 seconds."});
	public static final Spell totemicflames = new Spell(118, "Totemic Flames", 4, 500, Bank.spell_totemflames).setLevel(1).setDesc(new String[]{"You drop your weapon."});
	public static final Spell shockwave = new Spell(119, "Shockwave", 7, 3500, Bank.slice).setLevel(1).setDesc(new String[]{"Fires a short","lasting shockwave,","concussing enemies for","2 seconds and dealing","damage. This concussion","increases damage","taken by 100%."});
	public static final Spell ancestralhaste = new Spell(120, "Ancestral Haste", 4, 10000, Bank.spell_haste).setLevel(1).setDesc(new String[]{"Reduces cooldowns by","50% for 4.5sec."});
	public static final Spell block = new Spell(121, "Block", 6, 2600, Bank.spell_disarm).setLevel(1).setDesc(new String[]{"You block an attack."});
	public static final Spell bloodoath = new Spell(122, "Blood Oath", 1, 3500, Bank.spell_bleed).setStatType(1).setStatType(1).setLevel(1).setDesc(new String[]{"You are stunned for","3 seconds, and you","will be healed","every second for","the duration."});
	public static final Spell grudge = new Spell(123, "Grudge", 0, 3900, Bank.spell_grudge).setLevel(1).setDesc(new String[]{"You collect 25% of","damage dealt to you,","alowing you to","release it in a","burst of flame."});
	public static final Spell earthbolt = new Spell(124, "Earthen Blast", 5, 1000, Bank.spell_dirtbolt).setLevel(1).setDesc(new String[]{"You collect 25% of","damage dealt to you,","alowing you to","release it in a","burst of flame."});
	public static final Spell grassbolt = new Spell(125, "Earth Missile", 8, 3000, Bank.spell_earthbolt).setLevel(1).setDesc(new String[]{"You collect 25% of","damage dealt to you,","alowing you to","release it in a","burst of flame."});
	public static final Spell rockbolt = new Spell(126, "Stalagmite", 3, 350, Bank.spell_stalagmite).setLevel(1).setDesc(new String[]{"You collect 25% of","damage dealt to you,","alowing you to","release it in a","burst of flame."});
	public static final Spell totemWater = new Spell(127, "Totem of Tides", 2, 3500, Bank.spell_totemWater).setStatType(1).setLevel(1).setDesc(new String[]{"You collect 25% of","damage dealt to you,","alowing you to","release it in a","burst of flame."});
	public static final Spell totemAir = new Spell(128, "Totem of Haste", 6, 3500, Bank.spell_totemAir).setLevel(1).setDesc(new String[]{"You collect 25% of","damage dealt to you,","alowing you to","release it in a","burst of flame."});
	public static final Spell totemFire = new Spell(129, "Incinerator Totem", 4, 3500, Bank.spell_totemFire).setLevel(1).setDesc(new String[]{"You collect 25% of","damage dealt to you,","alowing you to","release it in a","burst of flame."});
	public static final Spell totemEarth = new Spell(130, "Stoneguard Totem", 6, 3500, Bank.spell_totemEarth).setLevel(1).setDesc(new String[]{"You collect 25% of","damage dealt to you,","alowing you to","release it in a","burst of flame."});
	public static final Spell equipHammer = new Spell(131, "Equip Hammer", 6, 4500, Bank.spell_grudge).setLevel(6).setDesc(new String[]{"You equip the Royal","Warhammer."});
	public static final Spell royalstrike = new Spell(132, "Royal Strike", 4, 200, Bank.spell_grudge).setLevel(6).setDesc(new String[]{"You strike with","your hammer."});
	public static final Spell holycharge = new Spell(133, "Holy Charge", 6, 3000, Bank.spell_grudge).setLevel(6).setDesc(new String[]{"You charge forward,","dealing damage to","enemies."});
	public static final Spell astrallens = new Spell(134, "Astral Lens", 6, 4000, Bank.spell_moonlens).setLevel(6).setDesc(new String[]{"You focus a beam","of moonlight into","a point, frying any who","are touched."});
	public static final Spell cosmicblast = new Spell(135, "Cosmic Alignment", 9, 2500, Bank.spell_moonlens).setLevel(6).setDesc(new String[]{"You begin charging a","massive spell that is","cast after 4 seconds.","You are slowed while","charging and take","100% more damge."});
	public static final Spell planet = new Spell(136, "Summon Planet", 6, 5000, Bank.spell_summonplanet).setLevel(6).setDesc(new String[]{"You summon a planet that","grows in size until released.","Deals more damage the","larger it is but will","crush you if too large.","You take double damage","while holding the planet","and taking damage will","reduce size."});
	public static final Spell planetlaunch = new Spell(137, "Planetary Collision", 6, 1500, Bank.spell_shootplanet).setLevel(planet.minlvl).setDesc(new String[]{"Launches the planet,","dealing damage based on","its size."});
	public static final Spell cosmic = new Spell(137, "Cosmic Quickness", 0, 6000, Bank.spell_quickness).setLevel(6).setDesc(new String[]{"Your next spell","triggers no cooldown."});
	public static final Spell powerball = new Spell(138, "", 8, 4000, Bank.spell_grudge).setLevel(6).setDesc(new String[]{"Your next spell","triggers no cooldown."});
	public static final Spell moltensigil = new Spell(139, "Ignite", 4, 6250, Bank.spell_ignite).setLevel(6).setDesc(new String[]{"You burst into flames,","causing you to take","damage each second. The next","spell you cast will transfer","the effect and deal small","additional damage."});
	public static final Spell combustion = new Spell(140, "Combustion", 4, 600, Bank.spell_combust).setLevel(6).setDesc(new String[]{"You fire a molten boulder,","exploding on impact and","dealing average damage.","Critical hits will stun."});
	public static final Spell life = new Spell(141, "Burst of Life", 12, 4300, Bank.spell_shamanheal).setStatType(1).setDesc(new String[]{"You heal yourself."});
	public static final Spell stoneguard = new Spell(142, "Earthguard", 7, 6000, Bank.spell_vitality).setDesc(new String[]{"Absorbs average damage for 6sec."});
	public static final Spell lightguard = new Spell(143, "Lightguard", 7, 8400, Bank.spell_vitality).setDesc(new String[]{"Absorbs average damage for 6sec."});
	public static final Spell totemStorm = new Spell(144, "Lightning Totem", 6, 5500, Bank.spell_totemEarth).setLevel(1).setDesc(new String[]{"You collect 25% of","damage dealt to you,","alowing you to","release it in a","burst of flame."});
	public static final Spell pinestrike = new Spell(145, "Demolish", 7, 1600, Bank.spell_demolish).setDesc(new String[]{"A strong attack."});
	public static final Spell woodwhirl = new Spell(146, "Wood Whirl", 2, 2800, Bank.spell_woodwhirl).setDesc(new String[]{"You whirl around","and strike enemies","either side of you."});
	public static final Spell vilespore = new Spell(147, "Vile Spores", 1, 6000, Bank.spell_vilespores).setDesc(new String[]{"For 4 seconds, your","attacks leave a","DoT effect on enemies."});
	public static final Spell steal = new Spell(148, "Thief's Pouch", 3, 5200, Bank.spell_thiefbag).setDesc(new String[]{"All damage you take for","2.5sec is recorded and","turned into a deadly","projectile."});
	public static final Spell exterminate = new Spell(149, "Exterminate", 6, 5000, Bank.spell_exterminate).setDesc(new String[]{"Deals largely increased","damage when SHROUDED."});
	public static final Spell ichor = new Spell(150, "Ichor Split", 5, 4650, Bank.spell_ichor).setDesc(new String[]{"You turn into an ichor","pool for 3.25sec, increasing","speed and absorbing","all damage."});
	public static final Spell blackout = new Spell(151, "Blackout Punch", 7, 3650, Bank.spell_blackout).setDesc(new String[]{"Stuns the target","for 2sec."});
	public static final Spell crimson = new Spell(152, "Crimson Blade", 5, 950, Bank.spell_bladetoss).setDesc(new String[]{"You throw a knife."});
	public static final Spell withhold = new Spell(153, "Withhold", 9, 1650, Bank.spell_vilespores).setDesc(new String[]{"You grab all effects","from the enemy for","2sec. Deals damage","for each effect expired."});
	public static final Spell cloakanddagger = new Spell(154, "Cloak and Dagger", 2, 3000, Bank.spell_cloakanddagger).setDesc(new String[]{"You jump around slashing","and bleeding enemies","every 0.2sec for","1 second."});
	public static final Spell bladevault = new Spell(155, "Knife Vault", 4, 2250, Bank.spell_knifevault).setDesc(new String[]{"You jump backward","and throw three knives."});
	public static final Spell overpower = new Spell(156, "Hateful Slash", 5, 750, Bank.spell_overpower).setDesc(new String[]{"Deals more damage","for the more health","you have."});
	public static final Spell devour = new Spell(157, "Devour", 7, 2000, Bank.spell_devour).setDesc(new String[]{"Consumes effects and","deals additional damage","for each, also reducing","healing taken by target."});
	public static final Spell smokecloud = new Spell(158, "Smoke Cloud", 7, 4250, Bank.spell_smokecloud).setDesc(new String[]{"Slows and weakens nearby","enemies for 6sec."});
	public static final Spell daggerdance = new Spell(159, "Dagger Dance", 2, 9000, Bank.spell_daggerdance).setDesc(new String[]{"You jump around slashing","and bleeding enemies","every 0.2sec for","3.2 seconds."});
	public static final Spell mutilate = new Spell(160, "Crimson Rend", 7, 1800, Bank.spell_reaper).setDesc(new String[]{"Causes the enemy to bleed."});
	public static final Spell rainofflesh = new Spell(161, "Rain of Flesh", 4, 3000, Bank.spell_rainofflesh).setDesc(new String[]{"The next enemy you","hit will explode into","more explosive piles of","flesh."});
	public static final Spell epidemic = new Spell(162, "Epidemic", 4, 3000, Bank.spell_rainofflesh).setDesc(new String[]{"You fire a fleshbolt","that will multipy","into 2 more upon","hitting a target.","The fleshbolts will heal","the caster and damage","enemies."});
	public static final Spell bloodhawk = new Spell(163, "Scarlet Grasp", 8, 2350, Bank.spell_bloodhawk).setDesc(new String[]{"A demonic hand reaches","for an enemy in front of you,","dealing damage and healing","you."});
	public static final Spell frostwall = new Spell(164, "Glacial Shield", 7, 2800, Bank.spell_frostwall).setDesc(new String[]{"Summons a wall of ice","to absorb attacks."});
	public static final Spell curse = new Spell(165, "Curse of Ereth", 7, 6000, Bank.spell_curse).setDesc(new String[]{"Applies a random effect upon ending, with the same value and duration as your Curse of Ereth."});
	public static final Spell fever = new Spell(166, "Plague Blast", 25, 7500, Bank.spell_fever).setDesc(new String[]{"Deals huge damage, but ressurects the target if they die within 10 seconds."});
	public static final Spell plagueblast = new Spell(167, "Greater Plague Blast", 40, 6000, Bank.spell_fever).setDesc(new String[]{"Deals enormous damage, but grants the target an additional respawn."});

	public Spell(int id, String name, int baseval, int cd, Image img){
		all[id] = this;
		this.id = id;
		this.name = name;
		this.cooldown = cd;
		this.sprite = img;
		this.baseval = baseval;
	}

	private Spell setLevel(int i) {
		this.minlvl = i;
		return this;
	}
	
	private Spell setStatType(int i) {
		this.statType = i;
		return this;
	}

	public int getCooldownMillis() {
		return cooldown;
	}
	
	public static Spell getRandomSpell(){
		int r = rand.nextInt(all.length);
		if(all[r]==null)return getRandomSpell();
		else return all[r];
	}
	
	public Spell setDesc(String[] s){
		this.desc = s;
		return this;
	}
	
	public Spell setDesc(String s){
		this.desc = new String[]{s};
		return this;
	}

	public void cast(Player p, double dam, String notes) {
		int baseval = (int) (this.baseval+(double)(this.baseval*dam));
		boolean crit = false;
		if(p.countEffect(EffectType.cloud) > 0)baseval/=2;
		if(baseval<=0)++baseval;
		if(notes!=null){
			if(notes.contains("crit"))crit = true;
		}
		if(p.countEffect(EffectType.deadlyfocus) > 0){
			int dur = cooldown;
			if(dur<1000)dur=1000;
			p.removeEffect(EffectType.deadlyfocus);
			p.addEffect(new Effect(1, EffectType.resolve, p, dur));
		}
		if(p.countEffect(EffectType.fury) > 0){
			baseval*=(p.countEffect(EffectType.fury)*3);
		}
		if(p.countEffect(EffectType.rage) > 0){
			baseval*=(p.countEffect(EffectType.rage)*2);
		}
		if(p.countEffect(EffectType.shroud) > 0){
			baseval*=(p.countEffect(EffectType.shroud)*(this==exterminate?(3.5d):2));
			p.removeEffect(EffectType.shroud, 1);
		}
		if(p.countEffect(EffectType.mindcharge) > 0 && (this != mindtwist || p.countEffect(EffectType.ethereal) > 0) && this != focus){
			baseval*=(p.countEffect(EffectType.mindcharge)+1);
			p.removeEffect(EffectType.mindcharge, 3);
			p.removeEffect(EffectType.mindcharge, 3);
			p.removeEffect(EffectType.mindcharge, 3);
		}
		if(p.countEffect(EffectType.ethereal) > 0){
			baseval+=(baseval/2*p.countEffect(EffectType.ethereal));
		}
		if(notes.contains("ins")){
			p.addEffect(new Effect(1, EffectType.mindcharge, p, -1));
		}
		if(crit){
			if(p.hasSkill(Skill.ethereality))
			p.addEffect(new Effect(1, EffectType.ethereal, p, 2000));
		}
		if(baseval == 420 && crit){
			Bank.setLockVal("heroes.HB", Hero.snoopdog.id, true);
			if(Bank.setLockVal("achievements.HB", Achievement.kush.id, true)){
				new ClipShell("camera.wav", 3F).start();
				p.addEffect(new Effect(1, EffectType.high, p, 5000));
				Util.persistentGuis.add(new GUIAchievementNotify(Achievement.kush, 0, 0));
			}
		}
		if(this==curse){
			Projectile proj = new Projectile(p, p.posX+p.width/2-30, p.posY+p.height-80, 40, 30, baseval, Bank.voidbolt);
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 150 : -150;
			proj.killYLoss = false;
			proj.effect = 39;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);		
		}
		if(this==fever){
			Projectile proj = new Projectile(p, p.posX+p.width/2-30, p.posY+p.height-80, 40, 30, baseval, Bank.voidbolt);
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 150 : -150;
			proj.killYLoss = false;
			proj.effect = 41;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);		
		}
		if(this==plagueblast){
			Projectile proj = new Projectile(p, p.posX+p.width/2-30, p.posY+p.height-80, 40, 30, baseval, Bank.voidbolt);
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 150 : -150;
			proj.killYLoss = false;
			proj.effect = 42;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);		
		}
		if(this==stoneguard){
			Effect e = new Effect(baseval, EffectType.sanctuary, p, 6000);
			e.dat1 = 2;
			p.addEffect(e);
		}
		if(this==vilespore){
			Effect e = new Effect(baseval, EffectType.sporeholder, p, 4000);
			p.addEffect(e);
		}
		if(this==sandsoftime){
			Effect e = new Effect(baseval, EffectType.sandsoftime, p, 2500);
			p.addEffect(e);
		}
		if(this==cloakanddagger){
			Effect e = new Effect(baseval, EffectType.cloakanddagger, p, 1000);
			p.addEffect(e);
		}
		if(this==daggerdance){
			Effect e = new Effect(baseval, EffectType.cloakanddagger, p, 3200);
			p.addEffect(e);
		}
		if(this==steal){
			Effect e = new Effect(baseval, EffectType.pouch, p, 2500);
			p.addEffect(e);
		}
		if(this==ichor){
			Effect e = new Effect(baseval, EffectType.ichor, p, 3250);
			p.addEffect(e);
		}
		if(this==rainofflesh){
			Effect e = new Effect(baseval, EffectType.rainofflesh, p, 5250);
			p.addEffect(e);
		}
		if(this==bloodhawk){
			Effect e = new Effect(baseval, EffectType.arm, p, 190);
			p.addEffect(e);
		}
		if(this==frostwall){
			if(p.trueDir==1){
				ObjectIcewall wall = new ObjectIcewall(p.posX-50-20, p.posY+p.height-120, 50, 120, 0, baseval*3);
				Display.currentScreen.objects.add(wall);
			}
			if(p.trueDir==0){
				ObjectIcewall wall1 = new ObjectIcewall(p.posX+p.width+20, p.posY+p.height-120, 50, 120, 1, baseval*3);
				Display.currentScreen.objects.add(wall1);
			}
			new ClipShell("wind.wav").start();
			new ClipShell("magicwhip.wav").start();
			for(int j = 0; j < 60; ++j){
				Particle particle = new Particle(p.posX+p.width/2, p.posY+p.height/2, Particle.SPARKLE, Color.CYAN);
				int s = 4+rand.nextInt(8);
				particle.width = s;
				particle.height = s;
				particle.phys.motionX = rand.nextInt(120)-rand.nextInt(120);
				particle.phys.motionY = rand.nextInt(120)-rand.nextInt(120);
				Display.currentScreen.objects.add(particle);
			}
		}
		if(this==volatility){
			Projectile proj = new Projectile(p, p.posX+p.width/2-15, p.posY+p.height/3, 30, 30, baseval, Bank.fireball);
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 225 : -225;
			proj.killYLoss = false;
			proj.effect = 35;
			proj.visEffect = -1;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==combustion){
			new ClipShell("boom.wav").start();
			Projectile proj = new Projectile(p, p.posX+p.width/2-30, p.posY+p.height-80, 60, 60, baseval, Bank.magmaball);
			proj.knockback = true;
			proj.phys.motionX = p.trueDir == 0 ? 150 : -150;
			proj.killYLoss = false;
			proj.effect = 36;
			proj.visEffect = 2;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==crimson){
			Projectile proj = new Projectile(p, p.posX+p.width/2-15, p.posY+p.height/4, 30, 30, baseval, Bank.knifeLeft, Bank.knifeRight);
			proj.knockback = true;
			proj.phys.motionX = p.trueDir == 0 ? 250 : -250;
			proj.killYLoss = false;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==overpower){
			new ClipShell("slice.wav").start();
			Projectile proj = new Projectile(p, p.posX, p.posY, p.width, p.height, baseval+(p.health * 100 / p.healthMax)/10, Bank.slice);
			proj.visEffect = -1;
			proj.knockback = true;
			proj.phys.motionX = p.trueDir == 0 ? 250 : -250;
			proj.maxTime = 100;
			proj.killYLoss = false;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this == devour){
			new ClipShell("slice.wav").start();
			Projectile proj = new Projectile(p, p.posX, p.posY, p.width, p.height, baseval, Bank.slice0);
			proj.knockback = true;
			proj.phys.motionX = p.trueDir == 0 ? 80 : -80;
			proj.killYLoss = false;
			proj.visEffect = -1;
			proj.effect = 19;
			proj.maxTime = 100;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);		
		}
		if(this == smokecloud){
			new ClipShell("swoosh.wav").start();
			ObjectSmokecloud obj = new ObjectSmokecloud(p, p.posX+p.width/2-160, p.posY+p.height/2-160, 320, 320, crit, baseval);
			Display.currentScreen.objects.add(obj);
			for(int j = 0; j < 60; ++j){
				Particle particle = new Particle(p.posX+p.width/2, p.posY+p.height/2, Particle.SPARKLE, Color.BLACK);
				int s = 2+rand.nextInt(8);
				particle.width = s;
				particle.height = s;
				particle.phys.motionX = rand.nextInt(120)-rand.nextInt(120);
				particle.phys.motionY = rand.nextInt(120)-rand.nextInt(120);
				Display.currentScreen.objects.add(particle);
			}
		}
		if(this==bladevault){
			for(int i = 0; i < 45; ++i){
				int s = 2+rand.nextInt(5);
				Particle part = new Particle(p.posX+p.width/2-s/2, p.posY+p.height/2+(rand.nextInt(40)-rand.nextInt(40)), Particle.ZIP, Color.BLACK);
				part.width = s;
				part.height = s;
				part.basePhysics = false;
				part.phys.motionX = (p.trueDir==0?-140:140)+(rand.nextInt(50)-rand.nextInt(50));
				part.phys.motionY = 60+rand.nextInt(120);
				Display.currentScreen.objects.add(part);
			}
			p.posY-=100;
			p.phys.motionX = (p.trueDir==0?-160:160);
			p.phys.motionY = 180;
			for(int i = 0; i < 3; ++i){
				Projectile proj = new Projectile(p, p.posX+p.width/2-15-45+i*30, p.posY+p.height/4+i*25, 30, 30, baseval, Bank.knifeLeft, Bank.knifeRight);
				proj.knockback = false;
				proj.phys.motionX = p.trueDir == 0 ? (220-20+i*20) : (-220+20-i*20);
				proj.phys.motionY = -140-i*30;
				proj.maxTime = 500;
				proj.killYLoss = false;
				proj.destroyOnHit = true;
				proj.crosswall = false;
				proj.solid = true;
				proj.crit = crit;
				Display.currentScreen.objects.add(proj);
			}
		}
		if(this==exterminate){
			new ClipShell("slice.wav").start();
			Projectile proj = new Projectile(p, p.posX, p.posY, p.width, p.height, baseval, Bank.slice);
			proj.visEffect = -1;
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 250 : -250;
			proj.maxTime = 100;
			proj.killYLoss = false;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==moltensigil){
			new ClipShell("firebreath.wav").start();
			p.addEffect(new Effect(this.baseval, EffectType.sigil, p, -1));
			for(int i = 0; i < 45; ++i){
				int s = 3+rand.nextInt(4);
				Particle pa = new Particle(p.posX+p.width/2-s/2, p.posY+p.height/2-s/2, Particle.CHARGED, rand.nextInt(2)==0?Color.YELLOW:Color.RED);
				pa.phys.motionX = (rand.nextInt(100)-rand.nextInt(100));
				pa.phys.motionY = (rand.nextInt(100)-rand.nextInt(100));
				pa.basePhysics = false;
				pa.width = s;
				pa.height = s;
				pa.maxTime = 200;
				Display.currentScreen.objects.add(pa);
			}
		}
		if(this==planet){
			Effect e = new Effect(1, EffectType.planet, p, (p.hasSkill(Skill.bigbang)?250:5000));
			e.dat = rand.nextInt(4);
			p.addEffect(e);
		}
		if(this==cosmic){
			p.addEffect(new Effect(1, EffectType.quickness, p, -1));
		}
		if(this==cosmicblast){
			p.addEffect(new Effect(this.baseval, EffectType.charging, p, 3000));
		}
		if(this==planetlaunch){
			if(p.effects!=null){
				for(int i = 0; i < p.effects.size(); ++i){
					Effect e = p.effects.get(i);
					if(e.type==EffectType.planet){
						Image planet = e.dat == 0 ? Bank.planet : e.dat == 1 ? Bank.brightplanet : e.dat == 2 ? Bank.pinkplanet : e.dat == 3 ? Bank.grayplanet : Bank.planet;
						new ClipShell("rubble1.wav").start();
						float flsize = (int) ((System.currentTimeMillis()-e.start) * 300 / e.durationMax);
						float dmod = flsize/((float)24);
						int size = (int)flsize;
						Projectile proj = new Projectile(p, p.posX+p.width/2-size/2, p.posY-60-size, size, size, (int)(p.hasSkill(Skill.bigbang)?(baseval*dmod)/4:(baseval*dmod)), planet);
						proj.knockback = true;
						proj.phys.motionX = p.trueDir == 0 ? (100+size) : -(100+size);
						proj.killYLoss = false;
						proj.phys.motionY = -(50+size/2);
						proj.destroyOnHit = true;
						proj.crosswall = false;
						proj.solid = true;
						proj.crit = crit;
						Display.currentScreen.objects.add(proj);		
						p.effects.remove(e);
					}
				}
			}
		}
		if(this==earthbolt||this==rockbolt||this==grassbolt){
			Projectile proj = new Projectile(p, p.posX+p.width/2-30, p.posY+p.height/3, 60, 20+(this!=rockbolt?10:0), baseval, this==earthbolt?Bank.dirtbolt:this==grassbolt?Bank.earthbolt:Bank.rockbolt);
			proj.knockback = this!=rockbolt;
			proj.phys.motionX = p.trueDir == 0 ? 225 : -225;
			proj.killYLoss = false;
			if(this!=rockbolt)
			proj.phys.motionY = 60;
			proj.visEffect = 200+(this==earthbolt?GridBlock.dirt.getID():this==grassbolt?GridBlock.grass.getID():GridBlock.stonebrick.getID());
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==totemicflames){
			new ClipShell("dragonbreath.wav").start();
			Projectile proj = new Projectile(p, p.posX+p.width/2-p.width, p.posY+p.height-60, p.width*2, 60, baseval, Bank.totemflames);
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 225 : -225;
			proj.killYLoss = false;
			proj.destroyOnHit = true;
			proj.visEffect = -1;
			proj.effect = 34;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==equipMace){
			new ClipShell("shieldup.wav").start();
			p.unequip.coolstart = System.currentTimeMillis();
			p.addEffect(new Effect(1, EffectType.mace, p, -1));
		}
		if(this==equipBlade){
			new ClipShell("shieldup.wav").start();
			p.unequip.coolstart = System.currentTimeMillis();
			p.addEffect(new Effect(1, EffectType.sword, p, -1));
		}
		if(this==equipAxe){
			new ClipShell("shieldup.wav").start();
			p.unequip.coolstart = System.currentTimeMillis();
			p.addEffect(new Effect(1, EffectType.axe, p, -1));
		}
		if(this==equipHammer){
			new ClipShell("shieldup.wav").start();
			p.unequip.coolstart = System.currentTimeMillis();
			p.addEffect(new Effect(1, EffectType.grandhammer, p, -1));
		}
		if(this==unequip){
			new ClipShell("swoosh.wav").start();
			Image img = (p.countEffect(EffectType.mace) > 0 ? Bank.mace_hit : p.countEffect(EffectType.axe) > 0 ? Bank.axe_stuck : p.countEffect(EffectType.sword) > 0 ? Bank.sword_stuck : p.countEffect(EffectType.grandhammer) > 0 ? Bank.grandhammer_attack : Bank.sword_hit);
			p.removeEffect(EffectType.mace, 1);
			p.removeEffect(EffectType.axe, 1);
			p.removeEffect(EffectType.sword, 1);
			p.removeEffect(EffectType.grandhammer, 1);
			int w = p.height*2, h = p.height+p.width;
			ObjectSnoopgif o = new ObjectSnoopgif(true, p.posX+p.width/2-w/2, p.posY+p.height-h, w, h, img, -1, true);
			Display.currentScreen.objects.add(o);
		}
		if(this==holycharge){
			p.addEffect(new Effect(this.baseval, EffectType.holycharge, p, 3000));
		}
		if(this==royalstrike){
			Projectile proj = new Projectile(p, p.posX, p.posY, p.width, p.height, baseval, Bank.slice0);
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 80 : -80;
			proj.killYLoss = false;
			proj.visEffect = -1;
			proj.effect = 0;
			proj.maxTime = 100;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==mindshield){
			Effect e = new Effect(this.baseval, EffectType.sanctuary, p, 5750);
			e.dat1 = 1;
			p.addEffect(e);
			p.addEffect(new Effect(1, EffectType.mindcharge, p, -1));
		}
		if(this == strikeBlade || this == strikeAxe || this == strikeMace){
			int r = rand.nextInt(3);
			if(this==strikeBlade||this==strikeAxe)new ClipShell(r==0?"slice.wav":r==1?"clang1.wav":"clang.wav").start();
			if(this==strikeMace)new ClipShell("slam.wav", 6F).start();
			//if(this==strikeMace)new ClipShell(r==0?"slam1.wav":r==1?"slam2.wav":"slam3.wav").start();
			Projectile proj = new Projectile(p, p.posX, p.posY, p.width, p.height, baseval, Bank.slice0);
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 80 : -80;
			proj.killYLoss = false;
			proj.visEffect = -1;
			proj.effect = 0;
			proj.maxTime = 100;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);		
		}
		if(this == shockwave){
			new ClipShell("boom.wav", 2.5F).start();
			Projectile proj = new Projectile(p, p.posX+(p.trueDir==0?p.width:-80), p.posY+p.height-60, 80, 60, baseval, Bank.slice0);
			proj.knockback = true;
			proj.phys.motionX = p.trueDir == 0 ? 200 : -200;
			proj.killYLoss = false;
			proj.visEffect = -1;
			proj.effect = 33;
			proj.maxTime = 250;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);		
		}
		if(this == totemWater || this == totemAir || this == totemFire || this == totemEarth){
			ObjectTotem obj = new ObjectTotem(p, (this==totemWater?1:this==totemAir?2:this==totemEarth?3:0), p.posX+p.width/2-30, p.posY+p.height-100, baseval);
			Display.currentScreen.objects.add(obj);
		}
		if(this == bloodoath){
			p.addEffect(new Effect(this.baseval, EffectType.bloodoath, p, 3000));
			p.addEffect(new Effect(this.baseval, EffectType.stun, p, 3000));
		}
		if(this == ancestralhaste){
			p.addEffect(new Effect(this.baseval, EffectType.haste, p, 4500));
		}
		if(this == rage){
			p.addEffect(new Effect(this.baseval, EffectType.rage, p, 5000));
		}
		if(this == whirlAxe || this == woodwhirl){
			Effect e = new Effect(this.baseval, EffectType.whirl, p, 2000);
			e.dat = p.trueDir;
			p.addEffect(e);
		}
		if(this==grudge){
			int v = 0;
			for(Effect e : p.effects){
				if(e.type==EffectType.grudge){
					v+=e.value;
					e.value = 0;
				}
			}
			ObjectPsychicpulse obj = new ObjectPsychicpulse(p, p.posX+p.width/2-150, p.posY+p.height/2-150, 300, 300, crit, v);
			obj.img = Bank.firenova;
			obj.time = 250;
			Display.currentScreen.objects.add(obj);
		}
		if(this == superBlade){
			new ClipShell("slice.wav").start();
			Projectile proj = new Projectile(p, p.posX, p.posY, p.width, p.height, baseval, Bank.slice0);
			proj.knockback = true;
			proj.phys.motionX = p.trueDir == 0 ? 80 : -80;
			proj.killYLoss = false;
			proj.visEffect = -1;
			proj.effect = 32;
			proj.maxTime = 100;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);		
		}
		if(this == stunMace){
			new ClipShell("pound.wav").start();
			Projectile proj = new Projectile(p, p.posX, p.posY, p.width, p.height, baseval, Bank.fist);
			proj.knockback = true;
			proj.phys.motionX = p.trueDir == 0 ? 80 : -80;
			proj.killYLoss = false;
			proj.visEffect = -1;
			proj.effect = 33;
			proj.maxTime = 100;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);		
		}
		if(this==rabeam){
			new ClipShell("dragonbreath.wav").start();
			int w = 500;
			Projectile proj = new Projectile(p, p.posX+(p.trueDir==0?p.width:-w), p.posY+p.height/3, w, 30, baseval, Bank.rabeam);
			proj.knockback = false;
			proj.killYLoss = false;
			proj.killXLoss = false;
			proj.effect = 0;
			proj.destroyOnHit = true;
			proj.crosswall = true;
			proj.maxTime = 100;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
			for(int i = 0; i < 20; ++i){
				int s = rand.nextInt(3)+1;
				Particle pa = new Particle(p.posX+(p.trueDir==0?p.width:-s), p.posY+p.height/3+15-s/2, Particle.CHARGED, Color.RED);
				pa.phys.motionX = (p.trueDir==0?(40+rand.nextInt(40)):-(40+rand.nextInt(40)));
				pa.phys.motionY = (rand.nextInt(2)==0?(40+rand.nextInt(30)):-(40+rand.nextInt(30)));
				pa.maxTime = 250;
				Display.currentScreen.objects.add(pa);
			}
		}
		if(this==shattertime){
			for(int j = 0; j < 3; ++j)p.addEffect(new Effect(this.baseval, EffectType.timerip, p, 4000));
		}
		if(this==arrow||this==firearrow||this==executioner||this==shockarrow||this==blackarrow||this==ghostarrow||this==spidershot||this==poisonshot){
			new ClipShell("arrowhit"+(rand.nextInt(2)==0?"":"1")+".wav").start();
			if(this==shockarrow||this==executioner)new ClipShell("arrowwhistle.wav").start();
			if(this==blackarrow)new ClipShell("chaosblast.wav").start();
			if(this==firearrow)new ClipShell("dragonbreath.wav").start();
			Projectile proj = new Projectile(p, p.posX+p.width/2-20, p.posY+p.height/3, 40, 14, baseval, Bank.arrow);
			proj.knockback = (this==arrow?false:true);
			proj.phys.motionX = p.trueDir == 0 ? 275 : -275;
			proj.killYLoss = false;
			proj.effect = 22;
			if(this==firearrow)proj.effect = 18;
			if(this==ghostarrow)proj.effect = 19;
			if(this==blackarrow)proj.effect = 20;
			if(this==executioner)proj.effect = 21;
			if(this==shockarrow)proj.effect = 8;
			if(this==poisonshot)proj.effect = 24;
			if(this==spidershot)proj.effect = 25;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==mindtwist){
			new ClipShell("shadebuff.wav").start();
			Projectile proj = new Projectile(p, p.posX+p.width/2-15, p.posY+p.height/3, 30, 30, baseval, Bank.psychmissile);
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 225 : -225;
			proj.killYLoss = false;
			proj.effect = 30;
			proj.maxTime = 2200;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==deception){
			CreatureDeciever obj = new CreatureDeciever(p, p.posX+p.width/2-45, p.posY+p.height-60, baseval, baseval/3);
			Display.currentScreen.objects.add(obj);
		}
		if(this==shadeform){
			p.addEffect(new Effect(this.baseval, EffectType.ethereal, p, 3500));
		}
		if(this==reinforce){
			CreatureSoldier obj = new CreatureSoldier(p, p.posX+p.width/2-15, p.posY+p.height-90, baseval, baseval/3);
			Display.currentScreen.objects.add(obj);
		}
		if(this==eternitysands){
			p.addEffect(new Effect(this.baseval, EffectType.sands, p, 10000));
		}
		if(this==brilliantsands){
			for(int i = 0; i < 70; ++i){
				int s = rand.nextInt(4)+1;
				Particle pa = new Particle(p.posX+p.width/2-s/2, p.posY+p.height/3, Particle.DUST, Color.YELLOW);
				pa.phys.motionX = rand.nextInt(90)-rand.nextInt(90);
				pa.phys.motionY = 30+rand.nextInt(80);
				Display.currentScreen.objects.add(pa);
			}
			p.heal(baseval, crit);
		}
		if(this==psychmissile){
			new ClipShell("shadebuff.wav").start();
			Projectile proj = new Projectile(p, p.posX+p.width/2-40, p.posY+p.height/3, 60, 60, baseval, Bank.psychmissile);
			proj.knockback = true;
			proj.phys.motionX = p.trueDir == 0 ? 225 : -225;
			proj.killYLoss = false;
			proj.effect = 31;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==brainrot){
			new ClipShell("shadebuff.wav").start();
			Projectile proj = new Projectile(p, p.posX+p.width/2-15, p.posY+p.height/3, 30, 20, baseval, Bank.brain);
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 175 : -175;
			proj.killYLoss = false;
			proj.effect = 26;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==corruption){
			new ClipShell("shadebuff.wav").start();
			Projectile proj = new Projectile(p, p.posX+p.width/2-15, p.posY+p.height/3, 30, 20, baseval, Bank.brain);
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 215 : -215;
			proj.killYLoss = false;
			proj.effect = 28;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==brainfry){
			new ClipShell("shadebuff.wav").start();
			Projectile proj = new Projectile(p, p.posX+p.width/2-15, p.posY+p.height/3, 30, 20, baseval, Bank.brain);
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 175 : -175;
			proj.killYLoss = false;
			proj.effect = 27;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==focus){
			p.addEffect(new Effect(1, EffectType.mindcharge, p, -1));
		}
		if(this==psychnova){
			ObjectPsychicpulse obj = new ObjectPsychicpulse(p, p.posX+p.width/2-125, p.posY+p.height/2-125, 250, 250, crit, baseval);
			Display.currentScreen.objects.add(obj);
		}
		if(this==holynova){
			ObjectHolypulse obj = new ObjectHolypulse(p, p.posX+p.width/2-10, p.posY+p.height/2-10, 500, crit, baseval);
			Display.currentScreen.objects.add(obj);
		}
		if(this==radiantsigil){
			ObjectSandsigil obj = new ObjectSandsigil(p, p.posX+p.width/2-30, p.posY-200, 60, 120, baseval);
			Display.currentScreen.objects.add(obj);
		}
		if(this==clockshock){
			Projectile proj = new Projectile(p, p.posX+p.width/2-15, p.posY+p.height/3, 30, 30, baseval, Bank.clockproj);
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 200 : -200;
			proj.killYLoss = false;
			proj.effect = 23;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==redirect){
			Projectile proj = new Projectile(p, p.posX+p.width/2-15, p.posY+p.height/3, 30, 30, baseval, Bank.clockproj);
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 200 : -200;
			proj.killYLoss = false;
			proj.effect = 29;
			proj.visEffect = 2;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==barrage){
			p.addEffect(new Effect(this.baseval, EffectType.barrage, p, 3000));
		}
		/*if(this==hunt){
			for(Object o : Display.currentScreen.objects){
				if(o.canHarm)o.addEffect(new Effect(1, EffectType.hunt, p, 4000));
			}
		}*/
		if(this==tree){
			Display.currentScreen.objects.add(new ObjectTree(p, (int) (p.posX+p.walkTick/2-40), p.posY+p.height-160, baseval));
		}
		if(this==enrage){
			p.addEffect(new Effect(20, EffectType.fury, p, 2000));
		}
		if(this==slam){
			p.phys.motionY = 200;
			p.posY-=10;
			p.addEffect(new Effect(this.baseval, EffectType.slam, p, 8000));
		}
		if(this==bandage||this==shamanheal||this==life){
			p.heal(baseval, crit);
		}
		if(this==quickscope){
			for(int i = 0; i < Display.currentScreen.objects.size(); ++i){
				Object pl = Display.currentScreen.objects.get(i);
				if(pl!=null&&pl.canHarm){
					if(pl==p){}
					else{
						pl.hurt(baseval, true);
						pl.renderBlood(80, rand.nextInt(100)-rand.nextInt(100), 40+rand.nextInt(140));
						ObjectSnoopgif obj = new ObjectSnoopgif(false, pl.posX+pl.width/2-130, pl.posY-50, 260, 300);
						obj.img = Bank.scope;
						ObjectSnoopgif obj1 = new ObjectSnoopgif(false, pl.posX+pl.width/2-70, pl.posY+pl.height-220, 140, 220);
						obj1.img = Bank.explosion;
						obj.maxTime = 650;
						obj1.maxTime = obj.maxTime-150;
						Display.currentScreen.objects.add(obj1);
						Display.currentScreen.objects.add(obj);
						new ClipShell("boom.wav", 4F).start();
						new ClipShell("camera.wav", 3F).start();
						Display.currentScreen.shake(200, 11);
					}
				}
			}
		}
		if(this==joint){
			Projectile proj = new Projectile(p, p.posX+p.width/2-28, p.posY+p.height/3, 56, 20, baseval, Bank.joint);
			proj.knockback = true;
			proj.phys.motionX = p.trueDir == 0 ? 175 : -175;
			proj.killYLoss = false;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==shamanblast){
			Projectile proj = new Projectile(p, p.posX+p.width/2-30, p.posY+p.height/3, 60, 50, baseval, Bank.shamanbolt);
			proj.knockback = true;
			proj.phys.motionX = p.trueDir == 0 ? 195 : -195;
			proj.killYLoss = false;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==meteor){
			new ClipShell("lasershot.wav").start();
			Projectile proj = new Projectile(p, p.posX+p.width/2-40, p.posY-500, 80, 80, baseval, Bank.bansheecoil);
			proj.phys.motionY = -200;
			proj.killXLoss = false;
			proj.killYLoss = true;
			proj.effect = 14;
			proj.crit = crit;
			proj.crosswall = true;
			Display.currentScreen.objects.add(proj);
		}
		if(this==dankblast){
			Projectile proj = new Projectile(p, p.posX+p.width/2-28, p.posY+p.height/3, 40, 40, baseval, Bank.kush);
			proj.knockback = true;
			proj.phys.motionX = p.trueDir == 0 ? 275 : -275;
			proj.killYLoss = false;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==puff){
			new ClipShell("smoke.wav").start();
			p.heal(baseval, crit);
			p.addEffect(new Effect(1, EffectType.high, p, 5500+baseval*5));
		}
		if(this == stardust){
			p.addEffect(new Effect(1, EffectType.stardust, p, 2500+baseval*25));
		}
		if(this == bloodsteed){
			p.addEffect(new Effect(this.baseval, EffectType.bloodsteed, p, 3000));
		}
		if(this == sacrifice){
			float h = p.healthMax;
			p.hurtRaw((int) (h * 50F / 100F), crit, null);
			p.atkRenderID = 1;
			p.addEffect(new Effect(1, EffectType.fury, p, 3000));
		}
		if(this==bloodbolt){
			float h = p.healthMax;
			p.hurtRaw((int) (h * 10F / 100F), crit, null);
			p.atkRenderID = 1;
			Projectile proj = new Projectile(p, p.posX+p.width/2-30, p.posY+p.height/3, 60, 30, baseval, Bank.bloodbolt);
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 225 : -225;
			proj.killYLoss = false;
			proj.visEffect = 10;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this == reap){
			new ClipShell(rand.nextInt(2)==0?"clang1.wav":"clang.wav", -10F).start();
			Projectile proj = new Projectile(p, p.posX, p.posY, p.width, p.height, baseval, Bank.slice0);
			proj.knockback = true;
			proj.phys.motionX = p.trueDir == 0 ? 120 : -120;
			proj.killYLoss = false;
			proj.visEffect = -1;
			proj.maxTime = 100;
			proj.destroyOnHit = true;
			proj.effect = 13;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);		
		}
		if(this == spores){
			p.addEffect(new Effect(this.baseval, EffectType.spores, p, 8000));		
		}
		if(this == spores1){
			p.addEffect(new Effect(this.baseval, EffectType.spores, p, 20000));		
		}
		if(this == thorns){
			p.addEffect(new Effect(1, EffectType.thorns, p, baseval));		
		}
		if(this == clobber){
			Projectile proj = new Projectile(p, p.posX, p.posY, p.width, p.height/2, baseval, Bank.fist);
			proj.knockback = true;
			proj.phys.motionX = p.trueDir == 0 ? 40 : -40;
			proj.killYLoss = false;
			proj.visEffect = -1;
			proj.effect = 8;
			proj.maxTime = 100;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);		
		}
		if(this == oakfist || this == pinestrike){
			Projectile proj = new Projectile(p, p.posX, p.posY, p.width, p.height, baseval, Bank.fist);
			proj.knockback = true;
			proj.phys.motionX = p.trueDir == 0 ? 80 : -80;
			proj.killYLoss = false;
			proj.visEffect = -1;
			proj.effect = 0;
			proj.maxTime = 100;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);		
		}
		for(int i = 0; i < p.effects.size(); ++i){
			if(p.effects.get(i).type.equals(EffectType.feedback)){
				new ClipShell("static.wav").start();
				new ClipShell("static.wav").start();
				new ClipShell("shortstatic.wav").start();
				p.hurt(p.effects.get(i).value, false);
				p.effects.remove(i);
			}
		}
		if(this==cosmiclash){
			Projectile proj = new Projectile(p, p.posX+p.width/2-6, p.posY+p.height/3, 12, 12, baseval, Bank.moon);
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 225 : -225;
			//proj.phys.motionY = -50;
			proj.killYLoss = false;
			proj.visEffect = 1;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==dart){
			Projectile proj = new Projectile(p, p.posX+p.width/2-6, p.posY+p.height/3, 12, 4, baseval, Bank.moon);
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 225 : -225;
			//proj.phys.motionY = -50;
			proj.killYLoss = false;
			proj.effect = 9;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==eclipse){
			for(int i = 0; i < 10; ++i){
				Projectile proj = new Projectile(p, p.posX+(p.trueDir==0?(-200-i*6):(200+i*6)), p.posY-p.height/2-i*2, 12, 12, baseval, Bank.moon);
				proj.knockback = false;
				proj.phys.motionX = p.trueDir == 0 ? 165 : -165;
				proj.phys.motionY = -50;
				proj.killYLoss = false;
				proj.visEffect = 1;
				proj.destroyOnHit = true;
				proj.crosswall = true;
				proj.solid = false;
				proj.crit = crit;
				Display.currentScreen.objects.add(proj);
			}
		}
		if(this==dash){
			new ClipShell("swoosh.wav").start();
			for(int i = 0; i < 4+rand.nextInt(5); ++i){
				Particle pa = new Particle(p.posX+rand.nextInt(p.width), p.posY, Particle.ZIP, rand.nextInt(2)==0?Color.WHITE:Color.CYAN);
				pa.phys.motionY=100;
				pa.phys.motionX=(rand.nextInt(50)-rand.nextInt(50));
				Display.currentScreen.objects.add(pa);
			}
			p.posX+=(p.trueDir==0?this.baseval:-this.baseval);
			//p.phys.motionX-=(p.trueDir==0?baseval:-baseval);
			for(int i = 0; i < 4+rand.nextInt(5); ++i){
				Particle pa = new Particle(p.posX+rand.nextInt(p.width), p.posY, Particle.ZIP, rand.nextInt(2)==0?Color.WHITE:Color.CYAN);
				pa.phys.motionY=100;
				pa.phys.motionX=(rand.nextInt(50)-rand.nextInt(50));
				Display.currentScreen.objects.add(pa);
			}
		}
		if(this==lightflame){
			for(int i = 0; i < Display.currentScreen.objects.size(); ++i){
				Object pl = Display.currentScreen.objects.get(i);
				if(pl!=null&&pl.canHarm){
					if(p==pl)
						((Player)pl).heal(baseval, crit);
					else
						pl.hurt(baseval, crit);
					for(int x = 0; x < 40+rand.nextInt(41); ++x){
						Particle pa = new Particle(pl.posX+pl.width/2, pl.posY+pl.height/2, Particle.CHARGED, Color.YELLOW);
						pa.phys.motionX = rand.nextInt(60)-rand.nextInt(60);
						pa.phys.motionY = rand.nextInt(60)-rand.nextInt(60);
						pa.basePhysics = false;
						int s = 14+rand.nextInt(15);
						pa.width = s;
						pa.height = s;
						Display.currentScreen.objects.add(pa);
					}
				}
			}
		}
		if(this==siphon){
			Display.currentScreen.objects.add(new ObjectSiphon(p, p.posX+p.width/2-100, p.posY+p.height/2-100, 200, 200, crit, baseval));
		}
		if(this==castigate||this==rebuke){
			new ClipShell(rand.nextInt(2)==0?"clang1.wav":"clang.wav", -10F).start();
			Projectile proj = new Projectile(p, p.posX, p.posY, p.width, p.height, baseval, Bank.slice0);
			proj.knockback = true;
			proj.phys.motionX = p.trueDir == 0 ? 80 : -80;
			proj.killYLoss = false;
			proj.visEffect = -1;
			proj.effect = 0;
			proj.maxTime = 100;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.solid = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
			Display.currentScreen.objects.add(proj);
		}
		if(this==stab||this==thunder){
			new ClipShell("slice.wav", -10F).start();
			Projectile proj = new Projectile(p, p.posX+(p.trueDir == 0 ? p.width : -40), p.posY+20, 30, p.height/2, baseval, Bank.slice);
			proj.phys.motionX = (p.trueDir==0?80:-80);
			proj.maxTime = 100;
			proj.visEffect = -1;
			proj.effect = 0;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==shroud){
			new ClipShell("shadebuff.wav").start();
			p.addEffect(new Effect(this.baseval, EffectType.shroud, p, 4000));
		}
		if(this==submission){
			Projectile proj = new Projectile(p, p.posX+p.width/2-30, p.posY, 30, 30, baseval, Bank.voidbolt);
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 185 : -185;
			proj.effect = 8;
			proj.killXLoss = true;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.crit = false;
			Display.currentScreen.objects.add(proj);
			new ClipShell("chaosblast.wav").start();
		}
		if(this==timeflip){
			Projectile proj = new Projectile(p, p.posX+p.width/2-250, p.posY+p.height-500, 500, 500, baseval, Bank.slice0);
			proj.knockback = false;
			proj.maxTime = 8500;
			proj.effect = 3;
			proj.visEffect = 5;
			proj.killXLoss = false;
			proj.killYLoss = false;
			proj.destroyOnHit = false;
			proj.crosswall = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==electrify){
			p.addEffect(new Effect(this.baseval, EffectType.feedback, p, 1800));
			new ClipShell("static.wav").start();
			Display.currentScreen.shake(200, 5);
			Projectile proj = new Projectile(p, p.posX+p.width/2-2, p.posY+p.height/2-10, 3, 3, baseval, Bank.slice0);
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 215 : -215;
			proj.effect = 4;
			proj.visEffect = 6;
			proj.killYLoss = false;
			proj.destroyOnHit = true;
			proj.crosswall = true;
			proj.crit = true;
			Display.currentScreen.objects.add(proj);
		}
		if(this==voidbolt){
			Projectile proj = new Projectile(p, p.posX+p.width/2-30, p.posY, 60, 45, baseval, Bank.voidbolt);
			proj.knockback = true;
			proj.phys.motionX = p.trueDir == 0 ? 15 : -15;
			proj.effect = 6;
			proj.killXLoss = true;
			proj.destroyOnHit = true;
			proj.crosswall = true;
			proj.crit = true;
			proj.maxTime = 6000;
			Display.currentScreen.objects.add(proj);
			new ClipShell("chaosblast.wav").start();
		}
		if(this==bonefire){
			Projectile proj = new Projectile(p, p.posX+p.width/2-15, p.posY+p.height/3, 30, 30, baseval, Bank.skull0);
			proj.knockback = true;
			proj.phys.motionX = p.trueDir == 0 ? 165 : -165;
			proj.killYLoss = false;
			proj.visEffect = 8;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
			new ClipShell("chatter.wav").start();
		}
		if(this==banshee){
			Projectile proj = new Projectile(p, p.posX+p.width/2-30, p.posY+p.height/3, 60, 60, baseval, Bank.bansheecoil);
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 265 : -265;
			proj.killYLoss = false;
			proj.effect = 7;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
			new ClipShell("roll.wav").start();
		}
		if(this==maw){
			Projectile proj = new Projectile(p, p.posX+p.width/2-30, p.posY+p.height/3, 60, 60, baseval, Bank.coil);
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 265 : -265;
			proj.killYLoss = false;
			proj.effect = 10;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
			new ClipShell("whisper.wav").start();
		}
		if(this==creeper){
			Projectile proj = new Projectile(p, p.posX+p.width/2-30, p.posY+p.height/3, 30, 30, baseval, Bank.creeper);
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 265 : -265;
			proj.killYLoss = false;
			proj.effect = 11;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
			new ClipShell("roll.wav").start();
		}
		if(this==cataclysm){
			Projectile proj = new Projectile(p, p.posX+p.width/2-30, p.posY+p.height/3, 40, 40, baseval, Bank.fireball);
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 225 : -225;
			proj.killYLoss = false;
			proj.effect = 12;
			proj.visEffect = -1;
			proj.destroyOnHit = true;
			proj.crosswall = false;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==bind){
			Projectile proj = new Projectile(p, p.posX+p.width/2-15, p.posY+p.height/2-10, 30, 30, baseval, Bank.arcane);
			proj.knockback = false;
			proj.phys.motionX = p.trueDir == 0 ? 215 : -215;
			proj.effect = 5;
			proj.visEffect = 7;
			proj.killYLoss = false;
			proj.destroyOnHit = true;
			proj.crosswall = true;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==clemency){
			p.addEffect(new Effect(this.baseval, EffectType.clemency, p, 4000));
		}
		if(this==sanctuary||this==aegis){
			new ClipShell("shieldup.wav").start();
			p.addEffect(new Effect(this.baseval, EffectType.sanctuary, p, 8000));
		}
		if(this==mindleap){
			new ClipShell("laserbub.wav").start();
			p.posY-=250;
		}
		if(this==manabomb){
			Display.currentScreen.objects.add(new ObjectManabomb(p.getUsername(), p.posX+p.width/2-30, p.posY+p.height-60, 60, 60, crit, baseval));
		}
		if(this==icicle){
			new ClipShell("swoosh.wav").start();
			Projectile proj = new Projectile(p, p.posX, p.posY+p.height/3, 26, 5, baseval, Bank.icicle);
			proj.visEffect = 3;
			proj.phys.motionX = p.trueDir == 0 ? 245 : -245;
			proj.crit = crit;
			proj.knockback = false;
			Display.currentScreen.objects.add(proj);
		}
		if(this==lunarblast){
			new ClipShell("lasershot.wav").start();
			Projectile proj = new Projectile(p, p.posX, p.posY, 30, 30, baseval, Bank.moon);
			proj.visEffect = 1;
			proj.phys.motionX = p.trueDir == 0 ? 175 : -175;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==dropcomet){
			new ClipShell("lasershot.wav").start();
			Projectile proj = new Projectile(p, p.posX, p.posY-500, 60, 60, baseval, Bank.moon);
			proj.visEffect = 1;
			proj.phys.motionY = -200;
			proj.killXLoss = false;
			proj.killYLoss = true;
			proj.crit = crit;
			proj.crosswall = true;
			Display.currentScreen.objects.add(proj);
		}
		if(this==moonlight){
			Projectile proj = new Projectile(p, p.posX+p.width/2-100, 0, 200, Properties.height, baseval, Bank.moonlight);
			proj.killXLoss = false;
			proj.killYLoss = false;
			proj.maxTime = 1250;
			proj.effect = 1;
			proj.destroyOnHit = false;
			proj.crit = crit;
			proj.knockback = false;
			Display.currentScreen.objects.add(proj);
		}
		if(this==craneleap){
			p.addEffect(new Effect(0, EffectType.grace, p, 1200));
			p.phys.motionX = p.trueDir == 0 ? 100 : -100;
			p.phys.motionY = 160;
			for(int i = 0; i < 20+rand.nextInt(20); ++i){
				Particle o = new Particle(p.posX+p.width/2+(rand.nextInt(40)-rand.nextInt(40)), p.posY+p.height/3*2, Particle.CHARGED, Color.YELLOW);
				int s = rand.nextInt(5)+6;
				o.basePhysics = false;
				o.width = s;
				o.height = s;
				o.phys.motionX = -(p.phys.motionX/4)+(rand.nextInt(40)-rand.nextInt(40));
				o.phys.motionY = -30+(rand.nextInt(20)-rand.nextInt(20));
				Display.currentScreen.objects.add(o);
			}
		}
		if(this==tigerleap){
			p.addEffect(new Effect(0, EffectType.grace, p, 1200));
			p.phys.motionX = p.trueDir == 0 ? 170 : -170;
			p.phys.motionY = 75;
			for(int i = 0; i < 20+rand.nextInt(20); ++i){
				Particle o = new Particle(p.posX+p.width/2+(rand.nextInt(40)-rand.nextInt(40)), p.posY+p.height/3*2, Particle.CHARGED, Color.YELLOW);
				int s = rand.nextInt(5)+6;
				o.basePhysics = false;
				o.width = s;
				o.height = s;
				o.phys.motionX = -(p.phys.motionX/4)+(rand.nextInt(40)-rand.nextInt(40));
				o.phys.motionY = -30+(rand.nextInt(20)-rand.nextInt(20));
				Display.currentScreen.objects.add(o);
			}
		}
		if(this == chill){
			new ClipShell("frostlaunch.wav").start();
			Projectile proj = new Projectile(p, p.posX+(p.trueDir == 0 ? p.width : -15), p.posY+20, 15, 15, baseval, Bank.snowball);
			proj.phys.motionX = p.trueDir == 0 ? 200 : -200;
			proj.killXLoss = true;
			proj.knockback = false;
			proj.effect = 17;
			proj.crit = crit;
			if(notes.contains("chill")){
				proj.notes = notes;
			}
			Display.currentScreen.objects.add(proj);
		}
		if(this == frostlink){
			new ClipShell("windshort.wav").start();
			Projectile proj = new Projectile(p, p.posX+(p.trueDir == 0 ? p.width : -15), p.posY+20, 15, 15, baseval, Bank.snowball);
			proj.phys.motionX = p.trueDir == 0 ? 150 : -150;
			proj.maxTime = 300;
			proj.killXLoss = true;
			proj.effect = 17;
			proj.visEffect = -1;
			proj.crit = crit;
			if(notes.contains("chill")){
				proj.notes = notes;
			}
			Display.currentScreen.objects.add(proj);
			int maxP = 30;
			for(int i = 0; i < maxP; ++i){
				int s = i+1;
				Particle part = new Particle(p.posX+(p.trueDir==0?p.width+20:-20-s), p.posY+p.height/2-(s/2), Particle.EXPLODE, new Color(0, 200-i*(100/maxP), 255, 255));
				part.width = s;
				part.height = s;
				part.maxTime = 200;
				part.phys.motionX = (p.trueDir==0?(i*4):-(i*4));
				part.basePhysics = false;
				Display.currentScreen.objects.add(part);
			}
		}
		if(this==penguin){
			ObjectPenguin obj = new ObjectPenguin(p, p.posX+p.width/2-20, p.posY+p.height-60, 40, 60, baseval);
			Display.currentScreen.objects.add(obj);
		}
		if(this == flashfreeze){
			new ClipShell("wind.wav").start();
			new ClipShell("magicwhip.wav").start();
			ObjectFreeze obj = new ObjectFreeze(p, p.posX+p.width/2-100, p.posY+p.height/2-100, 200, 200, crit, baseval);
			Display.currentScreen.objects.add(obj);
			for(int j = 0; j < 60; ++j){
				Particle particle = new Particle(p.posX+p.width/2, p.posY+p.height/2, Particle.SPARKLE, Color.CYAN);
				int s = 4+rand.nextInt(8);
				particle.width = s;
				particle.height = s;
				particle.phys.motionX = rand.nextInt(120)-rand.nextInt(120);
				particle.phys.motionY = rand.nextInt(120)-rand.nextInt(120);
				Display.currentScreen.objects.add(particle);
			}
		}
		if(this == glacialwinds){
			new ClipShell("windlong.wav").start();
			Projectile proj = new Projectile(p, p.posX+(p.trueDir == 0 ? p.width : -40), p.posY, 60, p.height, baseval, Bank.bansheecoil);
			proj.phys.motionX = p.trueDir == 0 ? 250 : -250;
			proj.maxTime = 450;
			proj.effect = 16;
			proj.crit = crit;
			proj.visEffect = -1;
			Display.currentScreen.objects.add(proj);
			int maxP = 40;
			for(int x = 0; x < 9; ++x){
				int mx = 40+rand.nextInt(200), my = rand.nextInt(120)-rand.nextInt(120);
				int mp = 1+rand.nextInt(4), mpx = 1+rand.nextInt(4);
				for(int i = 0; i < maxP; ++i){
					int s = i/2+1;
					Particle part = new Particle(p.posX+(p.trueDir==0?p.width+20:-20-s), p.posY+p.height/2-(s/2), Particle.EXPLODE, new Color(200, 200, (int)(200+i*1.3F), 255));
					part.width = s;
					part.height = s;
					part.maxTime = 450;
					part.phys.motionX = (p.trueDir==0?(i*mpx+mx):-(i*mpx+mx));
					part.phys.motionY = (rand.nextInt(2)==0?(i*mp+my):-(i*mp+my));
					part.basePhysics = false;
					Display.currentScreen.objects.add(part);
				}
			}
		}
		if(this==jab||this==punch){
			new ClipShell("swoosh.wav").start();
			Projectile proj = new Projectile(p, p.posX+(p.trueDir == 0 ? p.width : -40), p.posY, 40, 40, baseval, Bank.fist);
			proj.phys.motionX = p.trueDir == 0 ? 50 : -50;
			proj.maxTime = 100;
			proj.effect = 0;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==dragonkick){
			p.atkRenderID = 1;
			int maxP = 30;
			new ClipShell("swoosh.wav").start();
			Projectile proj = new Projectile(p, p.posX+(p.trueDir == 0 ? p.width : -40), p.posY-p.height, maxP+1, p.height*2, baseval, Bank.fist);
			proj.phys.motionX = p.trueDir == 0 ? maxP*2 : -(maxP*2);
			proj.maxTime = 250;
			proj.effect = 15;
			proj.visEffect = -1;
			proj.crit = crit;
			proj.crosswall = true;
			Display.currentScreen.objects.add(proj);
			for(int i = 0; i < maxP; ++i){
				int s = i+1;
				Particle part = new Particle(p.posX+(p.trueDir==0?p.width+20:-20-s), p.posY+p.height-i*2, Particle.EXPLODE, new Color(255, 100-i*(100/maxP), 0, 255));
				part.width = s;
				part.height = s;
				part.maxTime = 100+i*5;
				part.phys.motionX = (p.trueDir==0?i*2:-(i*2));
				part.phys.motionY = 30+i*2;
				part.basePhysics = false;
				Display.currentScreen.objects.add(part);
			}
		}
		if(this==zenkick){
			p.atkRenderID = 1;
			int maxP = 30;
			new ClipShell("swoosh.wav").start();
			Projectile proj = new Projectile(p, p.posX+(p.trueDir == 0 ? p.width : -40), p.posY-p.height, maxP+1, p.height*2, baseval, Bank.fist);
			proj.phys.motionX = p.trueDir == 0 ? maxP*2 : -(maxP*2);
			proj.maxTime = 250;
			proj.effect = 0;
			proj.visEffect = -1;
			proj.crit = crit;
			proj.crosswall = true;
			Display.currentScreen.objects.add(proj);
			for(int i = 0; i < maxP; ++i){
				int s = i+1;
				Particle part = new Particle(p.posX+(p.trueDir==0?p.width+20:-20-s), p.posY+p.height-i*2, Particle.EXPLODE, new Color(0, 100-i*(100/maxP), 255, 255));
				part.width = s;
				part.height = s;
				part.maxTime = 100+i*5;
				part.phys.motionX = (p.trueDir==0?i*2:-(i*2));
				part.phys.motionY = 30+i*2;
				part.basePhysics = false;
				Display.currentScreen.objects.add(part);
			}
		}
		if(this==chikick){
			p.atkRenderID = 1;
			int maxP = 30;
			new ClipShell("swoosh.wav").start();
			Projectile proj = new Projectile(p, p.posX+(p.trueDir == 0 ? p.width : -40), p.posY-p.height, maxP+1, p.height*2, baseval, Bank.fist);
			proj.phys.motionX = p.trueDir == 0 ? maxP*2 : -(maxP*2);
			proj.maxTime = 250;
			proj.effect = 13;
			proj.visEffect = -1;
			proj.crit = crit;
			proj.crosswall = true;
			Display.currentScreen.objects.add(proj);
			for(int i = 0; i < maxP; ++i){
				int s = i+1;
				Particle part = new Particle(p.posX+(p.trueDir==0?p.width+20:-20-s), p.posY+p.height-i*2, Particle.EXPLODE, new Color(100-i*(100/maxP), 255, 0, 255));
				part.width = s;
				part.height = s;
				part.maxTime = 100+i*5;
				part.phys.motionX = (p.trueDir==0?i*2:-(i*2));
				part.phys.motionY = 30+i*2;
				part.basePhysics = false;
				Display.currentScreen.objects.add(part);
			}
		}
		if(this==chislice){
			new ClipShell("swoosh.wav").start();
			new ClipShell("slice.wav").start();
			Projectile proj = new Projectile(p, p.posX+(p.trueDir == 0 ? p.width : -40), p.posY, 40, 40, baseval, Bank.fist);
			proj.phys.motionX = p.trueDir == 0 ? 150 : -150;
			proj.maxTime = 350;
			proj.effect = 0;
			proj.visEffect = -1;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
			int maxP = 80;
			for(int i = 0; i < maxP; ++i){
				int s = rand.nextInt(5)+4;
				Particle part = new Particle(p.posX+p.width/2-s/2, p.posY+p.height/2-s/2, Particle.EXPLODE, Color.GREEN);
				part.width = s;
				part.height = s;
				part.maxTime = 200;
				part.phys.motionX = (p.trueDir==0?140:-140)+(p.trueDir==1?(-(i<maxP/2?i:(maxP-i))):(i<maxP/2?i:(maxP-i)));
				part.phys.motionY = maxP/2-i;
				part.basePhysics = false;
				Display.currentScreen.objects.add(part);
			}
		}
		if(this==chislumber){
			new ClipShell("sleep.wav").start();
			Projectile proj = new Projectile(p, p.posX+p.width/2-60, p.posY, 30, 120, baseval, Bank.creeper);
			proj.phys.motionX = p.trueDir == 0 ? 200 : -200;
			proj.maxTime = 1500;
			proj.effect = 8;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==ninefuryfist){
			new ClipShell("swoosh.wav").start();
			for(int i = 0; i < 9; ++i){
				Projectile proj = new Projectile(p, p.posX+(p.trueDir == 0 ? p.width : -40), p.posY-20+i*10, 30, 30, baseval, Bank.fist);
				proj.killXLoss = true;
				proj.crosswall = true;
				proj.knockback = false;
				proj.phys.motionX = p.trueDir == 0 ? (50+i*15) : -(50+i*15);
				proj.phys.motionY = -50;
				proj.maxTime = 300;
				proj.effect = 0;
				proj.crit = crit;
				Display.currentScreen.objects.add(proj);
			}
		}
		if(this==tempeststrike){
			Projectile proj = new Projectile(p, p.posX+(p.trueDir == 0 ? p.width : -40), p.posY, 60, 60, baseval, Bank.fist);
			//proj.killXLoss = true;
			proj.crosswall = true;
			proj.phys.motionX = p.trueDir == 0 ? (200) : -(200);
			proj.maxTime = 1200;
			proj.crit = crit;
			proj.effect = 2;
			Display.currentScreen.objects.add(proj);
		}
		if(this==greatfirefist){
			new ClipShell("dragonbreath.wav").start();
			Projectile proj = new Projectile(p, p.posX+(p.trueDir == 0 ? p.width : -40), p.posY, 40, 40, baseval, Bank.fist);
			//proj.killXLoss = true;
			proj.phys.motionX = p.trueDir == 0 ? 250 : -250;
			proj.maxTime = 250;
			proj.visEffect = 2;
			proj.crit = crit;
			Display.currentScreen.objects.add(proj);
		}
		if(this==twelvetemplestrike){
			p.addEffect(new Effect(this.baseval, EffectType.twelvetemple, p, 3000));
		}
		if(this==chiheal){
			p.heal(baseval, crit);
		}
	}
}
