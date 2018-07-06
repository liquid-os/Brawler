package code;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Bank {
	
	public static GameServer server;
	public static GameClient client;
	
	static String resPack = null;
	
	static BufferedImage windowbg = (BufferedImage) Filestream.getImageFromPath("windowbg.png");
	static BufferedImage woodbg = (BufferedImage) Filestream.getImageFromPath("woodbg.png");
	static BufferedImage woodboard = (BufferedImage) Filestream.getImageFromPath("woodboard.png");
	static BufferedImage bug = (BufferedImage) Filestream.getImageFromPath("bug.png");
	static BufferedImage eagle = (BufferedImage) Filestream.getImageFromPath("eagle.png");
	static BufferedImage cake = (BufferedImage) Filestream.getImageFromPath("cake.png");
	static BufferedImage cake1 = (BufferedImage) Filestream.getImageFromPath("cake1.png");
	static BufferedImage cake2 = (BufferedImage) Filestream.getImageFromPath("cake2.png");
	static BufferedImage cake3 = (BufferedImage) Filestream.getImageFromPath("cake3.png");
	static BufferedImage hills = (BufferedImage) Filestream.getImageFromPath("hills.png");
	static BufferedImage hillsRoll = (BufferedImage) Filestream.getImageFromPath("hillsRoll.png");
	static BufferedImage hillsSand = (BufferedImage) Filestream.getImageFromPath("hillsSand.png");
	static BufferedImage hillsSnow = (BufferedImage) Filestream.getImageFromPath("hillsSnow.png");
	static BufferedImage hillsMoon = (BufferedImage) Filestream.getImageFromPath("hillsMoon.png");
	static BufferedImage hillsHell = (BufferedImage) Filestream.getImageFromPath("hellspikes.png");
	static BufferedImage hillsCave = (BufferedImage) Filestream.getImageFromPath("hillsCave.png");
	static BufferedImage sky = (BufferedImage) Filestream.getImageFromPath("sky.png");
	static BufferedImage skyDark = (BufferedImage) Filestream.getImageFromPath("skyDark.png");
	static BufferedImage skyRed = (BufferedImage) Filestream.getImageFromPath("skyRed.png");
	static BufferedImage hattext = (BufferedImage) Filestream.getImageFromPath("hattext.png");
	static BufferedImage sidebar = (BufferedImage) Filestream.getImageFromPath("sidebar.png");
	static BufferedImage heroportrait = (BufferedImage) Filestream.getImageFromPath("heroportrait.png");
	static BufferedImage nirvana = (BufferedImage) Filestream.getImageFromPath("nirvana.png");
	static BufferedImage gradient = (BufferedImage) Filestream.getImageFromPath("gradient.png");
	static BufferedImage icewall = (BufferedImage) Filestream.getImageFromPath("icewall.png");
	static BufferedImage icewall_flip = flip(icewall);
	static BufferedImage custbutton = (BufferedImage) Filestream.getImageFromPath("custButton.png");
	static BufferedImage marblebutton = (BufferedImage) Filestream.getImageFromPath("buttonmarble.png");
	static BufferedImage huntarrow = (BufferedImage) Filestream.getImageFromPath("huntarrow.png");
	static BufferedImage orb = (BufferedImage) Filestream.getImageFromPath("orb.png");
	static BufferedImage cannonball = (BufferedImage) Filestream.getImageFromPath("cannonball.png");
	static BufferedImage bag = (BufferedImage) Filestream.getImageFromPath("bag.png");
	static BufferedImage key = (BufferedImage) Filestream.getImageFromPath("key.png");
	static BufferedImage heart = (BufferedImage) Filestream.getImageFromPath("heart.png");
	static BufferedImage grad = (BufferedImage) Filestream.getImageFromPath("grad.png");
	static BufferedImage ice = (BufferedImage) Filestream.getImageFromPath("ice.png");
	static BufferedImage moonblock = (BufferedImage) Filestream.getImageFromPath("blockMoon.png");
	static BufferedImage lunablock = (BufferedImage) Filestream.getImageFromPath("blockLuna.png");
	static BufferedImage roughbrick = (BufferedImage) Filestream.getImageFromPath("roughbrick.png");
	static BufferedImage bloodbrick = (BufferedImage) Filestream.getImageFromPath("bloodblock.png");
	static BufferedImage dirtbolt = (BufferedImage) Filestream.getImageFromPath("dirtbolt.png");
	static BufferedImage earthbolt = (BufferedImage) Filestream.getImageFromPath("earthbolt.png");
	static BufferedImage rockbolt = (BufferedImage) Filestream.getImageFromPath("rock.png");
	static BufferedImage cosblast = (BufferedImage) Filestream.getImageFromPath("cosblast.png");
	static BufferedImage bio = (BufferedImage) Filestream.getImageFromPath("bio.png");
	static BufferedImage bioHover = (BufferedImage) Filestream.getImageFromPath("bioHover.png");
	static BufferedImage planetglow = (BufferedImage) Filestream.getImageFromPath("planetglow.png");
	static Image block_right = Filestream.loadGif("block_right.gif");
	static Image block_left = Filestream.loadGif("block_left.gif");
	static Image totemflames = Filestream.loadGif("totemflames.gif");
	static Image firemissile = Filestream.loadGif("firemissile.gif");
	static Image firemissileRight = Filestream.loadGif("firemissileRight.gif");
	static Image totemFire = Filestream.loadGif("totemFire.gif");
	static Image totemEarth = Filestream.loadGif("totemEarth.gif");
	static Image totemStorm = Filestream.loadGif("totemStorm.gif");
	static Image totemWater = Filestream.loadGif("totemWater.gif");
	static Image cloud = Filestream.loadGif("smoke.gif");
	static Image fire = Filestream.loadGif("fire.gif");
	static Image scales = Filestream.loadGif("scales.png");
	static Image glop = Filestream.loadGif("glop.gif");
	static Image planet = Filestream.loadGif("planet.gif");
	static Image pinkplanet = Filestream.loadGif("pinkplanet.gif");
	static Image brightplanet = Filestream.loadGif("brightplanet.gif");
	static Image grayplanet = Filestream.loadGif("grayplanet.gif");
	static Image firenova = Filestream.loadGif("flamering.gif");
	static Image torchtop = Filestream.loadGif("torchtop.gif");
	static Image flag = Filestream.loadGif("flagfull.gif");
	static Image flagGreen = Filestream.loadGif("flagfullgreen.gif");
	static Image flagGrey= Filestream.loadGif("flagfullgrey.gif");
	static Image dmgbanner = Filestream.getImageFromPath("dmgbanner.png");
	static Image healbanner = Filestream.getImageFromPath("healbanner.png");
	static Image tknbanner = Filestream.getImageFromPath("takenbanner.png");
	static Image icon_axe = Filestream.getImageFromPath("itemdwarvenhatchet.png");
	static Image icon_blade = Filestream.getImageFromPath("itemelfblade.png");
	static BufferedImage level10 = (BufferedImage) Filestream.getImageFromPath("ach10.png");
	static BufferedImage level20 = (BufferedImage) Filestream.getImageFromPath("ach20.png");
	static BufferedImage level50 = (BufferedImage) Filestream.getImageFromPath("ach50.png");
	static BufferedImage level100 = (BufferedImage) Filestream.getImageFromPath("ach100.png");
	static BufferedImage level250 = (BufferedImage) Filestream.getImageFromPath("ach250.png");
	static BufferedImage torchbottom = (BufferedImage) Filestream.getImageFromPath("torchbottom.png");
	static BufferedImage ladder = (BufferedImage) Filestream.getImageFromPath("ladder.png");
	static BufferedImage grass = (BufferedImage) Filestream.getImageFromPath("grass.png");
	static BufferedImage rampright = (BufferedImage) Filestream.getImageFromPath("woodRamp.png");
	static BufferedImage vine = (BufferedImage) Filestream.getImageFromPath("vine.png");
	static BufferedImage sandstone = (BufferedImage) Filestream.getImageFromPath("sandstone.png");
	static BufferedImage sandstonerough = (BufferedImage) Filestream.getImageFromPath("sandstoneRough.png");
	static BufferedImage jungledirt = (BufferedImage) Filestream.getImageFromPath("jungledirt.png");
	static BufferedImage junglegrass = (BufferedImage) Filestream.getImageFromPath("junglegrass.png");
	static BufferedImage junglewood = (BufferedImage) Filestream.getImageFromPath("junglewood.png");
	static BufferedImage jungleleaves = (BufferedImage) Filestream.getImageFromPath("jungleleaf.png");
	static BufferedImage rampleft = Bank.flip(rampright);
	static BufferedImage rampright180 = Bank.rotate(rampright, 180);
	static BufferedImage rampleft180 = Bank.rotate(rampleft, 180);
	static Image hexling_run = Filestream.loadGif("hexling_run.gif");
	static Image hexlingLeft_run = Filestream.loadGif("hexlingLeft_run.gif");
	static BufferedImage hexling_lurch = (BufferedImage) Filestream.getImageFromPath("hexling_lurch.png");
	static BufferedImage hexlingLeft_lurch = flip(hexling_lurch);
	static BufferedImage rope = (BufferedImage) Filestream.getImageFromPath("rope.png");
	static BufferedImage fabric = (BufferedImage) Filestream.getImageFromPath("fabric.png");
	static BufferedImage dirt = (BufferedImage) Filestream.getImageFromPath("dirt.png");
	static BufferedImage dirt1 = (BufferedImage) Filestream.getImageFromPath("dirt1.png");
	static BufferedImage stone = (BufferedImage) Filestream.getImageFromPath("stone.png");
	static BufferedImage brick = (BufferedImage) Filestream.getImageFromPath("bricks.png");
	static BufferedImage brick1 = (BufferedImage) Filestream.getImageFromPath("bricks1.png");
	static BufferedImage log = (BufferedImage) Filestream.getImageFromPath("log.png");
	static BufferedImage planks = (BufferedImage) Filestream.getImageFromPath("wood.png");
	static BufferedImage rocks = (BufferedImage) Filestream.getImageFromPath("rocks.png");
	static BufferedImage planks1 = (BufferedImage) Filestream.getImageFromPath("planks1.png");
	static BufferedImage leaves = (BufferedImage) Filestream.getImageFromPath("leaves.png");
	static BufferedImage sand = (BufferedImage) Filestream.getImageFromPath("sand.png");
	static BufferedImage cactus = (BufferedImage) Filestream.getImageFromPath("cactus.png");
	static BufferedImage snow = (BufferedImage) Filestream.getImageFromPath("snow.png");
	static BufferedImage pillar = (BufferedImage) Filestream.getImageFromPath("woodpillar.png");
	static BufferedImage water = (BufferedImage) Filestream.getImageFromPath("water.png");
	static BufferedImage weeds = (BufferedImage) Filestream.getImageFromPath("jungleweeds.png");
	static BufferedImage mushroom = (BufferedImage) Filestream.getImageFromPath("mushroom.png");
	static BufferedImage logo = (BufferedImage) Filestream.getImageFromPath("logo.png");
	static BufferedImage num2 = (BufferedImage) Filestream.getImageFromPath("2.png");
	static BufferedImage portraitFarmer = (BufferedImage) Filestream.getImageFromPath("farmer.png");
	static BufferedImage portraitLuna = (BufferedImage) Filestream.getImageFromPath("luna.png");
	static BufferedImage portraitNecro = (BufferedImage) Filestream.getImageFromPath("necro.png");
	static BufferedImage portraitDruid = (BufferedImage) Filestream.getImageFromPath("druid.png");
	static BufferedImage portraitMonk = (BufferedImage) Filestream.getImageFromPath("monk.png");
	static BufferedImage portraitMage = (BufferedImage) Filestream.getImageFromPath("magelord.png");
	static BufferedImage portraitMesmer = (BufferedImage) Filestream.getImageFromPath("mesmer.png");
	static BufferedImage wip = (BufferedImage) Filestream.getImageFromPath("WIP.png");
	static BufferedImage back = (BufferedImage) Filestream.getImageFromPath("back.png");
	static BufferedImage backHover = (BufferedImage) Filestream.getImageFromPath("backHover.png");
	static BufferedImage forward = (BufferedImage) Filestream.getImageFromPath("forward.png");
	static BufferedImage forwardHover = (BufferedImage) Filestream.getImageFromPath("forwardHover.png");
	static BufferedImage open = (BufferedImage) Filestream.getImageFromPath("open.png");
	static BufferedImage openHover = (BufferedImage) Filestream.getImageFromPath("openHover.png");
	static BufferedImage block = (BufferedImage) Filestream.getImageFromPath("block.png");
	static BufferedImage blockHover = (BufferedImage) Filestream.getImageFromPath("blockHover.png");
	static BufferedImage save = (BufferedImage) Filestream.getImageFromPath("save.png");
	static BufferedImage saveHover = (BufferedImage) Filestream.getImageFromPath("saveHover.png");
	static BufferedImage tools = (BufferedImage) Filestream.getImageFromPath("tools.png");
	static BufferedImage toolsHover = (BufferedImage) Filestream.getImageFromPath("toolsHover.png");
	static BufferedImage layer1 = (BufferedImage) Filestream.getImageFromPath("layer1.png");
	static BufferedImage layer1Hover = (BufferedImage) Filestream.getImageFromPath("layer1Hover.png");
	static BufferedImage layer0 = (BufferedImage) Filestream.getImageFromPath("layer0.png");
	static BufferedImage layer0Hover = (BufferedImage) Filestream.getImageFromPath("layer0Hover.png");
	static BufferedImage grid0 = (BufferedImage) Filestream.getImageFromPath("grid0.png");
	static BufferedImage grid1 = (BufferedImage) Filestream.getImageFromPath("grid1.png");
	static BufferedImage clock = (BufferedImage) Filestream.getImageFromPath("clock.png");
	static BufferedImage magmaball = (BufferedImage) Filestream.getImageFromPath("magmaball.png");
	static BufferedImage skycloud = (BufferedImage) Filestream.getImageFromPath("cloud.png");
	static BufferedImage parachute = (BufferedImage) Filestream.getImageFromPath("parachute.png");
	static BufferedImage airship = (BufferedImage) Filestream.getImageFromPath("airship.png");
	static Image knifeLeft = Filestream.loadGif("knife_left.gif");
	static Image knifeRight = Filestream.loadGif("knife_right.gif");
	static BufferedImage brain = (BufferedImage) Filestream.getImageFromPath("brain.png");
	static BufferedImage rabeam = (BufferedImage) Filestream.getImageFromPath("rabeam.png");
	static BufferedImage portraitLunaFlip = flip(portraitLuna);
	static BufferedImage portraitNecroFlip = flip(portraitNecro);
	static BufferedImage portraitFarmerFlip = flip(portraitFarmer);
	static BufferedImage portraitDruidFlip = flip(portraitDruid);
	static BufferedImage portraitMonkFlip = flip(portraitMonk);
	static BufferedImage portraitMageFlip = flip(portraitMage);
	static BufferedImage portraitMesmerFlip = flip(portraitMesmer);
	static BufferedImage spell_frostwall = (BufferedImage) Filestream.getImageFromPath("spell_frostwall.png");
	static BufferedImage spell_shattered = (BufferedImage) Filestream.getImageFromPath("spell_shattered.png");
	static BufferedImage spell_resolve = (BufferedImage) Filestream.getImageFromPath("spell_resolve.png");
	static BufferedImage spell_bind = (BufferedImage) Filestream.getImageFromPath("spell_bind.png");
	static BufferedImage spell_combust = (BufferedImage) Filestream.getImageFromPath("spell_combust.png");
	static BufferedImage spell_mindleap = (BufferedImage) Filestream.getImageFromPath("spell_mindleap.png");
	static BufferedImage spell_manabomb = (BufferedImage) Filestream.getImageFromPath("spell_manabomb.png");
	static BufferedImage spell_ignite = (BufferedImage) Filestream.getImageFromPath("spell_ignite.png");
	static BufferedImage spell_volatility = (BufferedImage) Filestream.getImageFromPath("spell_volatility.png");
	static BufferedImage spell_cosmicneedle = (BufferedImage) Filestream.getImageFromPath("spell_cosmicneedle.png");
	static BufferedImage spell_moonlens = (BufferedImage) Filestream.getImageFromPath("spell_astrallens.png");
	static BufferedImage spell_quickness = (BufferedImage) Filestream.getImageFromPath("spell_moonhaste.png");
	static BufferedImage spell_summonplanet = (BufferedImage) Filestream.getImageFromPath("spell_planetshoot.png");
	static BufferedImage spell_shootplanet = (BufferedImage) Filestream.getImageFromPath("spell_summonplanet.png");
	static BufferedImage spell_stalagmite = (BufferedImage) Filestream.getImageFromPath("spell_stalagmite.png");
	static BufferedImage spell_totemflames = (BufferedImage) Filestream.getImageFromPath("spell_totemflames.png");
	static BufferedImage spell_earthbolt = (BufferedImage) Filestream.getImageFromPath("spell_earthbolt.png");
	static BufferedImage spell_dirtbolt = (BufferedImage) Filestream.getImageFromPath("spell_dirtbolt.png");
	static BufferedImage spell_totemEarth = (BufferedImage) Filestream.getImageFromPath("spell_earthtotem.png");
	static BufferedImage spell_totemFire = (BufferedImage) Filestream.getImageFromPath("spell_firetotem.png");
	static BufferedImage spell_totemWater = (BufferedImage) Filestream.getImageFromPath("spell_watertotem.png");
	static BufferedImage spell_totemAir = (BufferedImage) Filestream.getImageFromPath("spell_airtotem.png");
	static BufferedImage spell_sandsigil = (BufferedImage) Filestream.getImageFromPath("spell_sandsigil.png");
	static BufferedImage spell_rabeam = (BufferedImage) Filestream.getImageFromPath("spell_pyra.png");
	static BufferedImage spell_hourglass = (BufferedImage) Filestream.getImageFromPath("spell_hourglass.png");
	static BufferedImage spell_clockshock = (BufferedImage) Filestream.getImageFromPath("spell_clockshock.png");
	static BufferedImage spell_redirect = (BufferedImage) Filestream.getImageFromPath("spell_redirect.png");
	static BufferedImage spell_shattertime = (BufferedImage) Filestream.getImageFromPath("spell_shattertime.png");
	static BufferedImage spell_healingsands = (BufferedImage) Filestream.getImageFromPath("spell_healingsands.png");
	static BufferedImage spell_sandsoftime = (BufferedImage) Filestream.getImageFromPath("spell_sandsoftime.png");
	static BufferedImage spell_mindtwist = (BufferedImage) Filestream.getImageFromPath("spell_mindtwist.png");
	static BufferedImage spell_brainrot = (BufferedImage) Filestream.getImageFromPath("spell_brainrot.png");
	static BufferedImage spell_illusion = (BufferedImage) Filestream.getImageFromPath("spell_illusion.png");
	static BufferedImage spell_deceit = (BufferedImage) Filestream.getImageFromPath("spell_deception.png");
	static BufferedImage spell_arrow = (BufferedImage) Filestream.getImageFromPath("spell_arrow.png");
	static BufferedImage spell_firearrow = (BufferedImage) Filestream.getImageFromPath("spell_firearrow.png");
	static BufferedImage spell_venomarrow = (BufferedImage) Filestream.getImageFromPath("spell_venomshot.png");
	static BufferedImage spell_toxicarrow = (BufferedImage) Filestream.getImageFromPath("spell_toxicshot.png");
	static BufferedImage spell_shockarrow = (BufferedImage) Filestream.getImageFromPath("spell_shockarrow.png");
	static BufferedImage spell_ghostarrow = (BufferedImage) Filestream.getImageFromPath("spell_ghostarrow.png");
	static BufferedImage spell_blackarrow = (BufferedImage) Filestream.getImageFromPath("spell_shadowarrow.png");
	static BufferedImage spell_executioner = (BufferedImage) Filestream.getImageFromPath("spell_executioner.png");
	static BufferedImage spell_eagle = (BufferedImage) Filestream.getImageFromPath("spell_eagle.png");
	static BufferedImage spell_holystrike = (BufferedImage) Filestream.getImageFromPath("spell_holystrike.png");
	static BufferedImage spell_retribution = (BufferedImage) Filestream.getImageFromPath("spell_retribution.png");
	static BufferedImage spell_defend = (BufferedImage) Filestream.getImageFromPath("spell_defend.png");
	static BufferedImage spell_moonstrike = (BufferedImage) Filestream.getImageFromPath("spell_moonblast.png");
	static BufferedImage spell_comet = (BufferedImage) Filestream.getImageFromPath("spell_comet.png");
	static BufferedImage spell_jab = (BufferedImage) Filestream.getImageFromPath("spell_jab.png");
	static BufferedImage spell_holynova = (BufferedImage) Filestream.getImageFromPath("spell_holynova.png");
	static BufferedImage spell_hunt = (BufferedImage) Filestream.getImageFromPath("spell_hunt.png");
	static BufferedImage spell_furyfist = (BufferedImage) Filestream.getImageFromPath("spell_ninefuryfist.png");
	static BufferedImage spell_tempeststrike = (BufferedImage) Filestream.getImageFromPath("spell_tempeststrike.png");
	static BufferedImage spell_greatfirefist = (BufferedImage) Filestream.getImageFromPath("spell_greatfirefist.png");
	static BufferedImage spell_inferno = (BufferedImage) Filestream.getImageFromPath("spell_inferno.png");
	static BufferedImage spell_torch = (BufferedImage) Filestream.getImageFromPath("skill_torch.png");
	static BufferedImage spell_rebuke = (BufferedImage) Filestream.getImageFromPath("spell_rebuke.png");
	static BufferedImage spell_chiheal = (BufferedImage) Filestream.getImageFromPath("spell_chiheal.png");
	static BufferedImage spell_bloodsteed = (BufferedImage) Filestream.getImageFromPath("spell_bloodsteed.png");
	static BufferedImage spell_bloodbolt = (BufferedImage) Filestream.getImageFromPath("spell_bloodbolt.png");
	static BufferedImage spell_reap = (BufferedImage) Filestream.getImageFromPath("spell_bloodreap.png");
	static BufferedImage spell_bandage = (BufferedImage) Filestream.getImageFromPath("spell_bandage.png");
	static BufferedImage spell_bloodbeam = (BufferedImage) Filestream.getImageFromPath("spell_deathbeam.png");
	static BufferedImage spell_sacrifice = (BufferedImage) Filestream.getImageFromPath("spell_sacrifice.png");
	static BufferedImage spell_moonlight = (BufferedImage) Filestream.getImageFromPath("spell_moonlight.png");
	static BufferedImage spell_stardust = (BufferedImage) Filestream.getImageFromPath("spell_stardust.png");
	static BufferedImage spell_cosmiclash = (BufferedImage) Filestream.getImageFromPath("spell_cosmiclash.png");
	static BufferedImage spell_eclipse = (BufferedImage) Filestream.getImageFromPath("spell_eclipse.png");
	static BufferedImage spell_vitality = (BufferedImage) Filestream.getImageFromPath("spell_vitality.png");
	static BufferedImage spell_dragonkick = (BufferedImage) Filestream.getImageFromPath("spell_dragonkick.png");
	static BufferedImage spell_riverkick = (BufferedImage) Filestream.getImageFromPath("spell_riverkick.png");
	static BufferedImage spell_shamanheal = (BufferedImage) Filestream.getImageFromPath("spell_shamanheal.png");
	static BufferedImage spell_chill = (BufferedImage) Filestream.getImageFromPath("spell_chill.png");
	static BufferedImage spell_penguin = (BufferedImage) Filestream.getImageFromPath("spell_penguin.png");
	static BufferedImage spell_snowman = (BufferedImage) Filestream.getImageFromPath("spell_snowman.png");
	static BufferedImage spell_winds = (BufferedImage) Filestream.getImageFromPath("spell_glacialwinds.png");
	static BufferedImage spell_frostlink = (BufferedImage) Filestream.getImageFromPath("spell_frostlink.png");
	static BufferedImage spell_flashfreeze = (BufferedImage) Filestream.getImageFromPath("spell_flashfreeze.png");
	static BufferedImage spell_disarm = (BufferedImage) Filestream.getImageFromPath("spell_unequip.png");
	static BufferedImage spell_bleed = (BufferedImage) Filestream.getImageFromPath("spell_bleed.png");
	static BufferedImage spell_hemmorage = (BufferedImage) Filestream.getImageFromPath("spell_hemmorage.png");
	static BufferedImage spell_hack = (BufferedImage) Filestream.getImageFromPath("spell_hack.png");
	static BufferedImage spell_whirl = (BufferedImage) Filestream.getImageFromPath("spell_whirl.png");
	static BufferedImage spell_slice = (BufferedImage) Filestream.getImageFromPath("spell_slice.png");
	static BufferedImage spell_slam = (BufferedImage) Filestream.getImageFromPath("spell_slam.png");
	static BufferedImage spell_pound = (BufferedImage) Filestream.getImageFromPath("spell_pound.png");
	static BufferedImage spell_rage = (BufferedImage) Filestream.getImageFromPath("spell_rage.png");
	static BufferedImage spell_haste = (BufferedImage) Filestream.getImageFromPath("spell_haste.png");
	static BufferedImage spell_grudge = (BufferedImage) Filestream.getImageFromPath("spell_grudge.png");
	static BufferedImage spell_woodwhirl = (BufferedImage) Filestream.getImageFromPath("spell_woodwhirl.png");
	static BufferedImage spell_demolish = (BufferedImage) Filestream.getImageFromPath("spell_smash.png");
	static BufferedImage spell_knifevault = (BufferedImage) Filestream.getImageFromPath("spell_vault.png");
	static BufferedImage spell_ichor = (BufferedImage) Filestream.getImageFromPath("spell_ichor.png");
	static BufferedImage spell_overpower = (BufferedImage) Filestream.getImageFromPath("spell_overpower.png");
	static BufferedImage spell_smokecloud = (BufferedImage) Filestream.getImageFromPath("spell_smokecloud.png");
	static BufferedImage spell_devour = (BufferedImage) Filestream.getImageFromPath("spell_devour.png");
	static BufferedImage spell_thiefbag = (BufferedImage) Filestream.getImageFromPath("spell_scarletpouch.png");
	static BufferedImage squaregrad = (BufferedImage) Filestream.getImageFromPath("squaregrad.png");
	static BufferedImage squaregrad_gr = (BufferedImage) Filestream.getImageFromPath("squaregrad_green.png");
	static BufferedImage squaregrad_pu = (BufferedImage) Filestream.getImageFromPath("squaregrad_pu.png");
	static BufferedImage guisquare = (BufferedImage) Filestream.getImageFromPath("guisquare.png");
	static BufferedImage paper = (BufferedImage) Filestream.getImageFromPath("paper.png");
	static BufferedImage guileft = (BufferedImage) Filestream.getImageFromPath("guiside.png");
	static BufferedImage moon = (BufferedImage) Filestream.getImageFromPath("moon.png");
	static BufferedImage shamanbolt = (BufferedImage) Filestream.getImageFromPath("shamanbolt.png");
	static BufferedImage fist = (BufferedImage) Filestream.getImageFromPath("fist.png");
	static BufferedImage flesh = (BufferedImage) Filestream.getImageFromPath("flesh.png");
	static BufferedImage icicle = (BufferedImage) Filestream.getImageFromPath("icicle.png");
	static BufferedImage snowball = (BufferedImage) Filestream.getImageFromPath("snowball.png");
	static BufferedImage arcane = (BufferedImage) Filestream.getImageFromPath("arcanebolt.png");
	static BufferedImage clockproj = (BufferedImage) Filestream.getImageFromPath("clockproj.png");
	static BufferedImage psychmissile = (BufferedImage) Filestream.getImageFromPath("psychmissile.png");
	static Image ra = Filestream.loadGif("ra.gif");
	static Image raCorrupt = Filestream.loadGif("raCorrupt.gif");
	static Image occule = Filestream.loadGif("occule.gif");
	static Image occuleLeft = Filestream.loadGif("occuleLeft.gif");
	static Image penguin = Filestream.loadGif("penguin.gif");
	static Image penguinLeft = Filestream.loadGif("penguinLeft.gif");
	static BufferedImage bloodbolt = (BufferedImage) Filestream.getImageFromPath("bloodbolt.png");
	static BufferedImage arrow = (BufferedImage) Filestream.getImageFromPath("arrow.png");
	static BufferedImage arrowLeft = flip(arrow);
	static BufferedImage moonlight = (BufferedImage) Filestream.getImageFromPath("moonlight.png");
	static BufferedImage tree = (BufferedImage) Filestream.getImageFromPath("tree.png");
	static BufferedImage tree_bare = (BufferedImage) Filestream.getImageFromPath("tree_bare.png");
	static BufferedImage treeRight = Bank.rotate(tree, 90);
	static BufferedImage treeLeft = Bank.rotate(tree, 270);
	static Image siphon =  Filestream.loadGif("siphon.gif");
	static Image nova =  Filestream.loadGif("psychNova.gif");
	static Image holyNova =  Filestream.loadGif("holyNova.gif");
	static Image blueNova =  Filestream.loadGif("blueNova.gif");
	static Image winds =  Filestream.loadGif("winds.gif");
	static BufferedImage cursorPencil = (BufferedImage) Filestream.getImageFromPath("cursorPencil.png");
	static BufferedImage cursorDrag = (BufferedImage) Filestream.getImageFromPath("cursorDrag.png");
	static BufferedImage guiright = Bank.flip(guileft);
	static BufferedImage guitop = Bank.rotate(guileft, 90);
	static BufferedImage guibottom = Bank.rotate(guitop, 180);
	static BufferedImage buttondeco = (BufferedImage) Filestream.getImageFromPath("rectThin.png");
	static BufferedImage buttondecoHover = (BufferedImage) Filestream.getImageFromPath("rectThinHover.png");
	static BufferedImage button = (BufferedImage) Filestream.getImageFromPath("button.png");
	static BufferedImage sparkle = (BufferedImage) Filestream.getImageFromPath("sparkle.png");
	static BufferedImage buttonHover = (BufferedImage) Filestream.getImageFromPath("button_h.png");
	static BufferedImage board = (BufferedImage) Filestream.getImageFromPath("board.png");
	static BufferedImage chain = (BufferedImage) Filestream.getImageFromPath("chain.png");
	static BufferedImage confbutton = (BufferedImage) Filestream.getImageFromPath("confbutton.png");
	static BufferedImage conflay1 = (BufferedImage) Filestream.getImageFromPath("conflay1.png");
	static BufferedImage conflay2 = (BufferedImage) Filestream.getImageFromPath("conflay2.png");
	static BufferedImage slice0 = (BufferedImage) Filestream.getImageFromPath("slice0.png");
	static BufferedImage slice1 = (BufferedImage) Filestream.getImageFromPath("slice1.png");
	static BufferedImage slice2 = (BufferedImage) Filestream.getImageFromPath("slice2.png");
	static BufferedImage slice3 = (BufferedImage) Filestream.getImageFromPath("slice3.png");
	static BufferedImage slice4 = (BufferedImage) Filestream.getImageFromPath("slice4.png");
	static BufferedImage sliceflip0 = (BufferedImage) Filestream.getImageFromPath("slice0.png");
	static BufferedImage sliceflip1 = (BufferedImage) Filestream.getImageFromPath("slice1.png");
	static BufferedImage sliceflip2 = (BufferedImage) Filestream.getImageFromPath("slice2.png");
	static BufferedImage sliceflip3 = (BufferedImage) Filestream.getImageFromPath("slice3.png");
	static BufferedImage sliceflip4 = (BufferedImage) Filestream.getImageFromPath("slice4.png");
	static BufferedImage rune = (BufferedImage) Filestream.getImageFromPath("rune.png");
	static Image nexus = Filestream.loadGif("nexus.gif");
	static BufferedImage redbutton = (BufferedImage) Filestream.getImageFromPath("redbutton.png");
	static BufferedImage plus = (BufferedImage) Filestream.getImageFromPath("plus.png");
	static BufferedImage rawplus = (BufferedImage) Filestream.getImageFromPath("rawplus.png");
	static BufferedImage rawplushover = (BufferedImage) Filestream.getImageFromPath("rawplushover.png");
	static BufferedImage minus = (BufferedImage) Filestream.getImageFromPath("minus.png");
	static BufferedImage spark0 = (BufferedImage) Filestream.getImageFromPath("static0.png");
	static BufferedImage spark1 = (BufferedImage) Filestream.getImageFromPath("static1.png");
	static BufferedImage spell_psychmissile = (BufferedImage) Filestream.getImageFromPath("spell_psychicmissile.png");
	static BufferedImage spell_ethereal = (BufferedImage) Filestream.getImageFromPath("spell_ethereal.png");
	static BufferedImage spell_brainsurge = (BufferedImage) Filestream.getImageFromPath("spell_brainblast.png");
	static BufferedImage spell_reaper = (BufferedImage) Filestream.getImageFromPath("spell_reap.png");
	static BufferedImage spell_bloodhawk = (BufferedImage) Filestream.getImageFromPath("spell_bloodhawk.png");
	static BufferedImage spell_rainofflesh = (BufferedImage) Filestream.getImageFromPath("spell_rainofflesh.png");
	static BufferedImage spell_dash = (BufferedImage) Filestream.getImageFromPath("spell_dash.png");
	static BufferedImage spell_shiv = (BufferedImage) Filestream.getImageFromPath("spell_powerblade.png");
	static BufferedImage spell_thunderblade = (BufferedImage) Filestream.getImageFromPath("spell_stormblade.png");
	static BufferedImage spell_shroud = (BufferedImage) Filestream.getImageFromPath("spell_shroud.png");
	static BufferedImage spell_stormbolt = (BufferedImage) Filestream.getImageFromPath("spell_stormbolt.png");
	static BufferedImage spell_feedback = (BufferedImage) Filestream.getImageFromPath("spell_feedback.png");
	static BufferedImage spell_icicle = (BufferedImage) Filestream.getImageFromPath("spell_icicle.png");
	static BufferedImage spell_voidbolt = (BufferedImage) Filestream.getImageFromPath("spell_voidbolt.png");
	static BufferedImage spell_bonefire = (BufferedImage) Filestream.getImageFromPath("spell_flameskull.png");
	static BufferedImage spell_banshee = (BufferedImage) Filestream.getImageFromPath("spell_banshee.png");
	static BufferedImage spell_submission = (BufferedImage) Filestream.getImageFromPath("spell_submission.png");
	static BufferedImage spell_creeper = (BufferedImage) Filestream.getImageFromPath("spell_creeper.png");
	static BufferedImage spell_maw = (BufferedImage) Filestream.getImageFromPath("spell_vice.png");
	static BufferedImage spell_plague = (BufferedImage) Filestream.getImageFromPath("spell_plague.png");
	static BufferedImage spell_cataclysm = (BufferedImage) Filestream.getImageFromPath("spell_cataclysm.png");
	static BufferedImage spell_slumber = (BufferedImage) Filestream.getImageFromPath("spell_slumber.png");
	static BufferedImage spell_chislice = (BufferedImage) Filestream.getImageFromPath("spell_chislice.png");
	static BufferedImage spell_chifury = (BufferedImage) Filestream.getImageFromPath("spell_chifury.png");
	static BufferedImage spell_kick = (BufferedImage) Filestream.getImageFromPath("spell_kick.png");
	static BufferedImage spell_oakfist = (BufferedImage) Filestream.getImageFromPath("spell_oakfist.png");
	static BufferedImage spell_tree = (BufferedImage) Filestream.getImageFromPath("spell_tree.png");
	static BufferedImage spell_clobber = (BufferedImage) Filestream.getImageFromPath("spell_clobber.png");
	static BufferedImage spell_spores = (BufferedImage) Filestream.getImageFromPath("spell_spores0.png");
	static BufferedImage spell_vilespores = (BufferedImage) Filestream.getImageFromPath("spell_spores1.png");
	static BufferedImage spell_eternityspores = (BufferedImage) Filestream.getImageFromPath("spell_bluespore.png");
	static BufferedImage spell_fungus = (BufferedImage) Filestream.getImageFromPath("spell_redspore.png");
	static BufferedImage spell_thorns = (BufferedImage) Filestream.getImageFromPath("spell_thorns.png");
	static BufferedImage spell_coldsnap = (BufferedImage) Filestream.getImageFromPath("spell_coldfront.png");
	static BufferedImage spell_bones = (BufferedImage) Filestream.getImageFromPath("spell_brittlebones.png");
	static BufferedImage spell_psychnova = (BufferedImage) Filestream.getImageFromPath("spell_psychnova.png");
	static BufferedImage spell_brainfry = (BufferedImage) Filestream.getImageFromPath("spell_brainfry.png");
	static BufferedImage spell_corruption = (BufferedImage) Filestream.getImageFromPath("spell_corruption.png");
	static BufferedImage spell_cloakanddagger = (BufferedImage) Filestream.getImageFromPath("spell_cloakanddagger.png");
	static BufferedImage spell_daggerdance = (BufferedImage) Filestream.getImageFromPath("spell_daggerdance.png");
	static BufferedImage spell_bladetoss = (BufferedImage) Filestream.getImageFromPath("spell_bladering.png");
	static BufferedImage spell_blackout = (BufferedImage) Filestream.getImageFromPath("spell_blackout.png");
	static BufferedImage spell_exterminate = (BufferedImage) Filestream.getImageFromPath("spell_exterminate.png");
	static BufferedImage spell_plague1 = (BufferedImage) Filestream.getImageFromPath("spell_plague1.png");
	static BufferedImage spell_fever = (BufferedImage) Filestream.getImageFromPath("spell_fever.png");
	static BufferedImage spell_curse = (BufferedImage) Filestream.getImageFromPath("spell_curse.png");
	static BufferedImage coil = (BufferedImage) Filestream.getImageFromPath("shadowcoil.png");
	static BufferedImage fireball = (BufferedImage) Filestream.getImageFromPath("fireball.png");
	static BufferedImage creeper = (BufferedImage) Filestream.getImageFromPath("creeper.png");
	static BufferedImage pbutton = (BufferedImage) Filestream.getImageFromPath("pbutton.png");
	static BufferedImage skull0 = (BufferedImage) Filestream.getImageFromPath("skull0.png");
	static BufferedImage skull1 = (BufferedImage) Filestream.getImageFromPath("skull1.png");
	static BufferedImage bansheecoil = (BufferedImage) Filestream.getImageFromPath("bansheecoil.png");
	static BufferedImage skull0left = Bank.flip(skull0);
	static BufferedImage skull1left = Bank.flip(skull1);
	static BufferedImage voidbolt = (BufferedImage) Filestream.getImageFromPath("voidbolt.png");
	static BufferedImage bg0 = (BufferedImage) Filestream.getImageFromPath("bg0.png");
	static BufferedImage bg1 = (BufferedImage) Filestream.getImageFromPath("bg1.png");
	static BufferedImage bg2 = (BufferedImage) Filestream.getImageFromPath("bg2.png");
	static BufferedImage bg3 = (BufferedImage) Filestream.getImageFromPath("bg3.png");
	static BufferedImage bg4 = (BufferedImage) Filestream.getImageFromPath("bg4.png");
	static BufferedImage stun = (BufferedImage) Filestream.getImageFromPath("stun.png");
	static BufferedImage bg = (BufferedImage) Filestream.getImageFromPath("bg.png");
	static BufferedImage human_walk0 = (BufferedImage) Filestream.getImageFromPath("human/human_run0.png");
	static BufferedImage human_walk1 = (BufferedImage) Filestream.getImageFromPath("human/human_run1.png");
	static BufferedImage human_walk2 = (BufferedImage) Filestream.getImageFromPath("human/human_run2.png");
	static BufferedImage human_stand = (BufferedImage) Filestream.getImageFromPath("human/human_stand0.png");
	static BufferedImage human_cast = (BufferedImage) Filestream.getImageFromPath("human/human_cast.png");
	static BufferedImage humanLeft_walk0 = flip(human_walk0);
	static BufferedImage humanLeft_walk1 = flip(human_walk1);
	static BufferedImage humanLeft_walk2 = flip(human_walk2);
	static BufferedImage humanLeft_stand = flip(human_stand);
	static BufferedImage humanLeft_cast = flip(human_cast);
	static BufferedImage monk_walk0 = (BufferedImage) Filestream.getImageFromPath("monk/monk_run0.png");
	static BufferedImage monk_walk1 = (BufferedImage) Filestream.getImageFromPath("monk/monk_run1.png");
	static BufferedImage monk_walk2 = (BufferedImage) Filestream.getImageFromPath("monk/monk_run2.png");
	static BufferedImage monk_stand = (BufferedImage) Filestream.getImageFromPath("monk/monk_stand0.png");
	static BufferedImage monk_cast = (BufferedImage) Filestream.getImageFromPath("monk/monk_cast.png");
	static BufferedImage monk_kick = (BufferedImage) Filestream.getImageFromPath("monk/monk_kick.png");
	static BufferedImage monk_fall0 = (BufferedImage) Filestream.getImageFromPath("monk/monk_fall0.png");
	static BufferedImage monk_fall1 = (BufferedImage) Filestream.getImageFromPath("monk/monk_fall1.png");
	static BufferedImage monkLeft_walk0 = flip(monk_walk0);
	static BufferedImage monkLeft_walk1 = flip(monk_walk1);
	static BufferedImage monkLeft_walk2 = flip(monk_walk2);
	static BufferedImage monkLeft_stand = flip(monk_stand);
	static BufferedImage monkLeft_cast = flip(monk_cast);
	static BufferedImage monkLeft_kick = flip(monk_kick);
	static BufferedImage monkLeft_fall0 = flip(monk_fall0);
	static BufferedImage monkLeft_fall1 = flip(monk_fall1);
	static BufferedImage ent_walk0 = (BufferedImage) Filestream.getImageFromPath("ent/ent_run0.png");
	static BufferedImage ent_walk1 = (BufferedImage) Filestream.getImageFromPath("ent/ent_run1.png");
	static BufferedImage ent_walk2 = (BufferedImage) Filestream.getImageFromPath("ent/ent_run2.png");
	static BufferedImage ent_stand = (BufferedImage) Filestream.getImageFromPath("ent/ent_stand0.png");
	static BufferedImage ent_punch = (BufferedImage) Filestream.getImageFromPath("ent/ent_punch.png");
	static BufferedImage ent_swing = (BufferedImage) Filestream.getImageFromPath("ent/ent_swing.png");
	static BufferedImage ent_fall0 = (BufferedImage) Filestream.getImageFromPath("ent/ent_fall0.png");
	static BufferedImage ent_fall1 = (BufferedImage) Filestream.getImageFromPath("ent/ent_fall1.png");
	static BufferedImage entLeft_walk0 = flip(ent_walk0);
	static BufferedImage entLeft_walk1 = flip(ent_walk1);
	static BufferedImage entLeft_walk2 = flip(ent_walk2);
	static BufferedImage entLeft_stand = flip(ent_stand);
	static BufferedImage entLeft_punch = flip(ent_punch);
	static BufferedImage entLeft_swing = flip(ent_swing);
	static BufferedImage entLeft_fall0 = flip(ent_fall0);
	static BufferedImage entLeft_fall1 = flip(ent_fall1);
	static BufferedImage prince_walk0 = (BufferedImage) Filestream.getImageFromPath("prince/prince_run0.png");
	static BufferedImage prince_walk1 = (BufferedImage) Filestream.getImageFromPath("prince/prince_run1.png");
	static BufferedImage prince_walk2 = (BufferedImage) Filestream.getImageFromPath("prince/prince_run2.png");
	static BufferedImage prince_stand = (BufferedImage) Filestream.getImageFromPath("prince/prince_stand0.png");
	static BufferedImage prince_attack = (BufferedImage) Filestream.getImageFromPath("prince/prince_attack.png");
	static BufferedImage prince_swing = (BufferedImage) Filestream.getImageFromPath("prince/prince_swing.png");
	static BufferedImage prince_cast = (BufferedImage) Filestream.getImageFromPath("prince/prince_cast.png");
	static Image princearm_left = Filestream.loadGif("prince/arm_left.gif");
	static Image princearm_right = Filestream.loadGif("prince/arm_right.gif");
	static BufferedImage princearm_aura = (BufferedImage) Filestream.getImageFromPath("prince/armaura.png");
	static Image prince_fall = Filestream.loadGif("prince/prince_fall.gif");
	static Image princeLeft_fall = Filestream.loadGif("prince/princeLeft_fall.gif");
	static BufferedImage princeLeft_walk0 = flip(prince_walk0);
	static BufferedImage princeLeft_walk1 = flip(prince_walk1);
	static BufferedImage princeLeft_walk2 = flip(prince_walk2);
	static BufferedImage princeLeft_stand = flip(prince_stand);
	static BufferedImage princeLeft_attack = flip(prince_attack);
	static BufferedImage princeLeft_swing = flip(prince_swing);
	static BufferedImage princeLeft_cast = flip(prince_cast);
	static BufferedImage human1_walk0 = (BufferedImage) Filestream.getImageFromPath("human/female/lili_run0.png");
	static BufferedImage human1_walk1 = (BufferedImage) Filestream.getImageFromPath("human/female/lili_run1.png");
	static BufferedImage human1_walk2 = (BufferedImage) Filestream.getImageFromPath("human/female/lili_run2.png");
	static BufferedImage human1_stand = (BufferedImage) Filestream.getImageFromPath("human/female/lili_stand0.png");
	static BufferedImage human1_cast = (BufferedImage) Filestream.getImageFromPath("human/female/lili_cast.png");
	static BufferedImage human1Left_walk0 = flip(human1_walk0);
	static BufferedImage human1Left_walk1 = flip(human1_walk1);
	static BufferedImage human1Left_walk2 = flip(human1_walk2);
	static BufferedImage human1Left_stand = flip(human1_stand);
	static BufferedImage human1Left_cast = flip(human1_cast);
	static BufferedImage luna_walk0 = (BufferedImage) Filestream.getImageFromPath("luna/luna_run0.png");
	static BufferedImage luna_walk1 = (BufferedImage) Filestream.getImageFromPath("luna/luna_run1.png");
	static BufferedImage luna_walk2 = (BufferedImage) Filestream.getImageFromPath("luna/luna_run2.png");
	static BufferedImage luna_stand = (BufferedImage) Filestream.getImageFromPath("luna/luna_stand0.png");
	static BufferedImage luna_cast = (BufferedImage) Filestream.getImageFromPath("luna/luna_cast.png");
	static BufferedImage luna_fall0 = (BufferedImage) Filestream.getImageFromPath("luna/luna_fall0.png");
	static BufferedImage luna_fall1 = (BufferedImage) Filestream.getImageFromPath("luna/luna_fall1.png");
	static BufferedImage lunaLeft_walk0 = flip(luna_walk0);
	static BufferedImage lunaLeft_walk1 = flip(luna_walk1);
	static BufferedImage lunaLeft_walk2 = flip(luna_walk2);
	static BufferedImage lunaLeft_stand = flip(luna_stand);
	static BufferedImage lunaLeft_cast = flip(luna_cast);
	static BufferedImage lunaLeft_fall0 = flip(luna_fall0);
	static BufferedImage lunaLeft_fall1 = flip(luna_fall1);
	static Image ranger_stand = Filestream.loadGif("ranger/ranger_stand.gif");
	static Image ranger_barrage = Filestream.loadGif("ranger/ranger_barrage.gif");
	static Image ranger_run = Filestream.loadGif("ranger/ranger_run.gif");
	static BufferedImage ranger_draw = (BufferedImage) Filestream.getImageFromPath("ranger/ranger_draw.png");
	static BufferedImage ranger_release = (BufferedImage) Filestream.getImageFromPath("ranger/ranger_release.png");
	static Image rangerLeft_run = Filestream.loadGif("ranger/rangerLeft_run.gif");
	static Image rangerLeft_barrage = Filestream.loadGif("ranger/rangerLeft_barrage.gif");
	static Image rangerLeft_stand = Filestream.loadGif("ranger/rangerLeft_stand.gif");
	//static BufferedImage ranger_fall0 = (BufferedImage) Filestream.getImageFromPath("ranger/ranger_fall0.png");
	//static BufferedImage ranger_fall1 = (BufferedImage) Filestream.getImageFromPath("ranger/ranger_fall1.png");
	static BufferedImage rangerLeft_draw = flip(ranger_draw);
	static BufferedImage rangerLeft_release = flip(ranger_release);
	//static BufferedImage rangerLeft_fall0 = flip(ranger_fall0);
	//static BufferedImage rangerLeft_fall1 = flip(ranger_fall1);
	static Image buster_walk = Filestream.loadGif("buster/buster_run.gif");
	static BufferedImage buster_stand = (BufferedImage) Filestream.getImageFromPath("buster/buster_stand0.png");
	static BufferedImage buster_cast = (BufferedImage) Filestream.getImageFromPath("buster/buster_cast.png");
	static BufferedImage buster_fall0 = (BufferedImage) Filestream.getImageFromPath("buster/buster_run2.png");
	static BufferedImage buster_fall1 = (BufferedImage) Filestream.getImageFromPath("buster/buster_run2.png");
	static Image busterLeft_walk = Filestream.loadGif("buster/busterLeft_run.gif");
	static BufferedImage busterLeft_stand = flip(buster_stand);
	static BufferedImage busterLeft_cast = flip(buster_cast);
	static BufferedImage busterLeft_fall0 = flip(buster_fall0);
	static BufferedImage busterLeft_fall1 = flip(buster_fall1);
	static Image slice = Filestream.loadGif("slice.gif");
	static Image sliceLeft = Filestream.loadGif("sliceLeft.gif");
	static Image bar_walk = Filestream.loadGif("bar/bar_run.gif");
	static Image barLeft_walk = Filestream.loadGif("bar/barLeft_run.gif");
	static Image bar_whirl = Filestream.loadGif("bar/bar_axewhirl.gif");
	static BufferedImage bar_stand = (BufferedImage) Filestream.getImageFromPath("bar/bar_stand0.png");
	static BufferedImage barLeft_stand = Bank.flip(bar_stand);
	static BufferedImage bar_maceswing = (BufferedImage) Filestream.getImageFromPath("bar/bar_maceswing.png");
	static BufferedImage bar_maceattack = (BufferedImage) Filestream.getImageFromPath("bar/bar_maceattack.png");
	static BufferedImage barLeft_maceswing = flip(bar_maceswing);
	static BufferedImage barLeft_maceattack = flip(bar_maceattack);
	static BufferedImage bar_swordswing = (BufferedImage) Filestream.getImageFromPath("bar/bar_swordswing.png");
	static BufferedImage bar_swordattack = (BufferedImage) Filestream.getImageFromPath("bar/bar_swordattack.png");
	static BufferedImage barLeft_swordswing = flip(bar_swordswing);
	static BufferedImage barLeft_swordattack = flip(bar_swordattack);
	static BufferedImage sword_stuck = (BufferedImage) Filestream.getImageFromPath("sword_stuck.png");
	static BufferedImage axe_stuck = (BufferedImage) Filestream.getImageFromPath("axe_stuck.png");
	static Image mace_run = Filestream.loadGif("bar/mace_run.gif");
	static Image maceLeft_run = Filestream.loadGif("bar/maceLeft_run.gif");
	static BufferedImage mace_swing = (BufferedImage) Filestream.getImageFromPath("bar/mace_swing.png");
	static BufferedImage mace_hit = (BufferedImage) Filestream.getImageFromPath("bar/mace_attack.png");
	static BufferedImage mace_stand = (BufferedImage) Filestream.getImageFromPath("bar/mace_stand.png");
	static BufferedImage maceLeft_swing = flip(mace_swing);
	static BufferedImage maceLeft_hit = flip(mace_hit);
	static BufferedImage maceLeft_stand = flip(mace_stand);
	static Image sword_run = Filestream.loadGif("bar/sword_run.gif");
	static Image swordLeft_run = Filestream.loadGif("bar/swordLeft_run.gif");
	static BufferedImage sword_swing = (BufferedImage) Filestream.getImageFromPath("bar/sword_swing.png");
	static BufferedImage sword_hit = (BufferedImage) Filestream.getImageFromPath("bar/sword_attack.png");
	static BufferedImage sword_stand = (BufferedImage) Filestream.getImageFromPath("bar/sword_stand.png");
	static BufferedImage swordLeft_swing = flip(sword_swing);
	static BufferedImage swordLeft_hit = flip(sword_hit);
	static BufferedImage swordLeft_stand = flip(sword_stand);
	static Image axe_whirl = Filestream.loadGif("bar/axe_whirl.gif");
	static Image axe_run = Filestream.loadGif("bar/axe_run.gif");
	static Image axeLeft_run = Filestream.loadGif("bar/axeLeft_run.gif");
	static BufferedImage axe_swing = (BufferedImage) Filestream.getImageFromPath("bar/axe_swing.png");
	static BufferedImage axe_hit = (BufferedImage) Filestream.getImageFromPath("bar/axe_attack.png");
	static BufferedImage axe_stand = (BufferedImage) Filestream.getImageFromPath("bar/axe_stand.png");
	static BufferedImage axeLeft_swing = flip(axe_swing);
	static BufferedImage axeLeft_hit = flip(axe_hit);
	static BufferedImage axeLeft_stand = flip(axe_stand);
	static Image snowbro_walk = Filestream.loadGif("snowbro/snowbro_run.gif");
	static Image snowbroLeft_walk = Filestream.loadGif("snowbro/snowbroLeft_run.gif");
	static BufferedImage snowbro_stand = (BufferedImage) Filestream.getImageFromPath("snowbro/snowbro_stand0.png");
	static BufferedImage snowbro_sleep = (BufferedImage) Filestream.getImageFromPath("snowbro/snowbro_sleep.png");
	static BufferedImage snowbro_sit = (BufferedImage) Filestream.getImageFromPath("snowbro/snowbro_sit.png");
	static BufferedImage snowbro_cast = (BufferedImage) Filestream.getImageFromPath("snowbro/snowbro_cast.png");
	static BufferedImage snowbroLeft_stand = flip(snowbro_stand);
	static BufferedImage snowbroLeft_sleep = flip(snowbro_stand);
	static BufferedImage snowbroLeft_sit = flip(snowbro_stand);
	static BufferedImage snowbroLeft_cast = flip(snowbro_cast);
	static Image mesmer_walk = Filestream.loadGif("mesmer/mesmer_run.gif");
	static Image mesmerLeft_walk = Filestream.loadGif("mesmer/mesmerLeft_run.gif");
	static BufferedImage mesmer_stand = (BufferedImage) Filestream.getImageFromPath("mesmer/mesmer_stand0.png");
	static BufferedImage mesmer_cast = (BufferedImage) Filestream.getImageFromPath("mesmer/mesmer_cast.png");
	static BufferedImage mesmerLeft_stand = flip(mesmer_stand);
	static BufferedImage mesmerLeft_cast = flip(mesmer_cast);
	static Image frost_right = Filestream.loadGif("mage/frost_right.gif");
	static Image frost_left = Filestream.loadGif("mage/frost_left.gif");
	static Image flame_right = Filestream.loadGif("mage/flame_right.gif");
	static Image flame_left = Filestream.loadGif("mage/flame_left.gif");
	static Image mage_walk = Filestream.loadGif("mage/mage_run.gif");
	static Image mageLeft_walk = Filestream.loadGif("mage/mageLeft_run.gif");
	static BufferedImage mage_stand = (BufferedImage) Filestream.getImageFromPath("mage/mage_stand0.png");
	static BufferedImage mage_cast = (BufferedImage) Filestream.getImageFromPath("mage/mage_cast.png");
	static BufferedImage mageLeft_stand = flip(mage_stand);
	static BufferedImage mageLeft_cast = flip(mage_cast);
	static BufferedImage kera_walk0 = (BufferedImage) Filestream.getImageFromPath("keralyn/kera_run0.png");
	static BufferedImage kera_walk1 = (BufferedImage) Filestream.getImageFromPath("keralyn/kera_run1.png");
	static BufferedImage kera_walk2 = (BufferedImage) Filestream.getImageFromPath("keralyn/kera_run2.png");
	static BufferedImage kera_stand = (BufferedImage) Filestream.getImageFromPath("keralyn/kera_stand0.png");
	static BufferedImage kera_cast = (BufferedImage) Filestream.getImageFromPath("keralyn/kera_cast.png");
	static BufferedImage kera_fall0 = (BufferedImage) Filestream.getImageFromPath("keralyn/kera_fall0.png");
	static BufferedImage kera_fall1 = (BufferedImage) Filestream.getImageFromPath("keralyn/kera_fall1.png");
	static BufferedImage keraLeft_walk0 = flip(kera_walk0);
	static BufferedImage keraLeft_walk1 = flip(kera_walk1);
	static BufferedImage keraLeft_walk2 = flip(kera_walk2);
	static BufferedImage keraLeft_stand = flip(kera_stand);
	static BufferedImage keraLeft_cast = flip(kera_cast);
	static BufferedImage keraLeft_fall0 = flip(kera_fall0);
	static BufferedImage keraLeft_fall1 = flip(kera_fall1);
	static BufferedImage troll_walk0 = (BufferedImage) Filestream.getImageFromPath("troll/troll_walk1.png");
	static BufferedImage troll_walk1 = (BufferedImage) Filestream.getImageFromPath("troll/troll_walk2.png");
	static BufferedImage troll_walk2 = (BufferedImage) Filestream.getImageFromPath("troll/troll_walk3.png");
	static BufferedImage troll_stand = (BufferedImage) Filestream.getImageFromPath("troll/troll_stand.png");
	static BufferedImage troll_punch = (BufferedImage) Filestream.getImageFromPath("troll/troll_punch.png");
	static BufferedImage troll_headbutt = (BufferedImage) Filestream.getImageFromPath("troll/troll_headbutt.png");	
	static BufferedImage troll_slam = (BufferedImage) Filestream.getImageFromPath("troll/troll_slam.png");	
	static BufferedImage trollLeft_walk0 = flip(troll_walk0);
	static BufferedImage trollLeft_walk1 = flip(troll_walk1);
	static BufferedImage trollLeft_walk2 = flip(troll_walk2);
	static BufferedImage trollLeft_stand = flip(troll_stand);
	static BufferedImage trollLeft_punch = flip(troll_punch);
	static BufferedImage trollLeft_headbutt = flip(troll_headbutt);
	static BufferedImage trollLeft_slam = flip(troll_slam);
	static BufferedImage yeti_walk0 = (BufferedImage) Filestream.getImageFromPath("yeti/yetrogg_run0.png");
	static BufferedImage yeti_walk1 = (BufferedImage) Filestream.getImageFromPath("yeti/yetrogg_run1.png");
	static BufferedImage yeti_walk2 = (BufferedImage) Filestream.getImageFromPath("yeti/yetrogg_run2.png");
	static BufferedImage yeti_punch = (BufferedImage) Filestream.getImageFromPath("yeti/yetrogg_attack0.png");
	static BufferedImage yeti_headbutt = (BufferedImage) Filestream.getImageFromPath("yeti/yetrogg_attack1.png");
	static BufferedImage yetiLeft_walk0 = flip(yeti_walk0);
	static BufferedImage yetiLeft_walk1 = flip(yeti_walk1);
	static BufferedImage yetiLeft_walk2 = flip(yeti_walk2);
	static BufferedImage yetiLeft_punch = flip(yeti_punch);
	static BufferedImage yetiLeft_headbutt = flip(yeti_headbutt);
	static Image bandit_run = Filestream.loadGif("bandit/bandit_run.gif");
	static BufferedImage bandit_stand = (BufferedImage) Filestream.getImageFromPath("bandit/bandit_stand0.png");
	static BufferedImage bandit_attack = (BufferedImage) Filestream.getImageFromPath("bandit/bandit_attack.png");
	static Image banditLeft_run = Filestream.loadGif("bandit/banditLeft_run.gif");
	static BufferedImage banditLeft_attack = flip(bandit_attack);
	static BufferedImage banditLeft_stand = flip(bandit_stand);
	static Image priest_walk = Filestream.loadGif("priest/priest_run.gif");
	static BufferedImage priest_stand = (BufferedImage) Filestream.getImageFromPath("priest/priest_stand0.png");
	static BufferedImage priest_cast = (BufferedImage) Filestream.getImageFromPath("priest/priest_cast.png");
	static BufferedImage priest_swing = (BufferedImage) Filestream.getImageFromPath("priest/priest_swing.png");
	static Image priestLeft_walk = Filestream.loadGif("priest/priestLeft_run.gif");
	static BufferedImage priestLeft_stand = flip(priest_stand);
	static BufferedImage priestLeft_cast = flip(priest_cast);
	static BufferedImage priestLeft_swing = flip(priest_swing);
	static Image warden_walk = Filestream.loadGif("warden/warden_run.gif");
	static Image wardenLeft_walk = Filestream.loadGif("warden/wardenLeft_run.gif");
	static Image grandhammer_walk = Filestream.loadGif("warden/mace_run.gif");
	static Image grandhammerLeft_walk = Filestream.loadGif("warden/maceLeft_run.gif");
	static BufferedImage grandhammer_swing = (BufferedImage) Filestream.getImageFromPath("warden/mace_swing.png");
	static BufferedImage grandhammerLeft_swing = Bank.flip(grandhammer_swing);
	static BufferedImage grandhammer_attack = (BufferedImage) Filestream.getImageFromPath("warden/mace_attack.png");
	static BufferedImage grandhammerLeft_attack = Bank.flip(grandhammer_attack);
	static BufferedImage grandhammer_stand = (BufferedImage) Filestream.getImageFromPath("warden/mace_stand.png");
	static BufferedImage grandhammerLeft_stand = Bank.flip(grandhammer_stand);
	static BufferedImage warden_swing = (BufferedImage) Filestream.getImageFromPath("warden/warden_maceswing.png");
	static BufferedImage wardenLeft_swing = Bank.flip(warden_swing);
	static BufferedImage warden_attack = (BufferedImage) Filestream.getImageFromPath("warden/warden_maceattack.png");
	static BufferedImage wardenLeft_attack = Bank.flip(warden_attack);
	static BufferedImage warden_stand = (BufferedImage) Filestream.getImageFromPath("warden/warden_stand0.png");
	static BufferedImage wardenLeft_stand = Bank.flip(warden_stand);
	//static BufferedImage warden_maceswing = (BufferedImage) Filestream.getImageFromPath("warden/warden_maceswing.png");
	//static BufferedImage warden_maceattack = (BufferedImage) Filestream.getImageFromPath("warden/warden_maceattack.png");
	static Image soldier_walk = Filestream.loadGif("soldier/soldier_run.gif");
	static Image soldierLeft_walk = Filestream.loadGif("soldier/soldierLeft_run.gif");
	static Image hammer_walk = Filestream.loadGif("soldier/mace_run.gif");
	static Image hammerLeft_walk = Filestream.loadGif("soldier/maceLeft_run.gif");
	static BufferedImage hammer_swing = (BufferedImage) Filestream.getImageFromPath("soldier/mace_swing.png");
	static BufferedImage hammerLeft_swing = Bank.flip(hammer_swing);
	static BufferedImage hammer_attack = (BufferedImage) Filestream.getImageFromPath("soldier/mace_attack.png");
	static BufferedImage hammerLeft_attack = Bank.flip(hammer_attack);
	static BufferedImage hammer_stand = (BufferedImage) Filestream.getImageFromPath("soldier/mace_stand.png");
	static BufferedImage hammerLeft_stand = Bank.flip(hammer_stand);
	static BufferedImage soldier_stand = (BufferedImage) Filestream.getImageFromPath("soldier/soldier_stand0.png");
	static BufferedImage soldierLeft_stand = Bank.flip(soldier_stand);
	static BufferedImage soldier_attack = (BufferedImage) Filestream.getImageFromPath("soldier/soldier_maceattack.png");
	static BufferedImage soldierLeft_attack = Bank.flip(soldier_attack);
	
	static BufferedImage horse0 = (BufferedImage) Filestream.getImageFromPath("horse_run0.png");
	static BufferedImage horse1 = (BufferedImage) Filestream.getImageFromPath("horse_run1.png");
	static BufferedImage horse0Left = Bank.flip(horse0);
	static BufferedImage horse1Left = Bank.flip(horse1);
	static BufferedImage icon = (BufferedImage) Filestream.getImageFromPath("icon.png");
	static BufferedImage lock = (BufferedImage) Filestream.getImageFromPath("lock.png");
	static BufferedImage snoop = (BufferedImage) Filestream.getImageFromPath("snoop.png");
	static Image puppet = (Image) Filestream.loadGif("pup.gif");
	static Image explosion = (Image) Filestream.loadGif("boom.gif");
	static Image chicken = (Image) Filestream.loadGif("chicken.gif");
	static Image dancing = (Image) Filestream.loadGif("snoop.gif");
	static Image skele = (Image) Filestream.loadGif("skele.gif");
	static Image boogie = (Image) Filestream.loadGif("boogie.gif");
	static Image gif420 = (Image) Filestream.loadGif("420.gif");
	static Image portal = (Image) Filestream.loadGif("portal.gif");
	static Image flash = (Image) Filestream.loadGif("flash.gif");
	static Image shade = (Image) Filestream.loadGif("shade.gif");
	static BufferedImage hitmarker = (BufferedImage) Filestream.getImageFromPath("hitmarker.png");
	static BufferedImage joint = (BufferedImage) Filestream.getImageFromPath("joint.png");
	static BufferedImage kush = (BufferedImage) Filestream.getImageFromPath("kush.png");
	static BufferedImage leaf = (BufferedImage) Filestream.getImageFromPath("leaf.png");
	static BufferedImage scope = (BufferedImage) Filestream.getImageFromPath("scope.png");
	static BufferedImage edgy = (BufferedImage) Filestream.getImageFromPath("edgy.png");
	static BufferedImage spell_joint = (BufferedImage) Filestream.getImageFromPath("spell_jointstrike.png");
	static BufferedImage spell_puff = (BufferedImage) Filestream.getImageFromPath("spell_puff.png");
	static BufferedImage spell_kushbolt = (BufferedImage) Filestream.getImageFromPath("spell_kushblast.png");
	static BufferedImage spell_quickscope = (BufferedImage) Filestream.getImageFromPath("spell_quickscope.png");
	static BufferedImage shield = (BufferedImage) Filestream.getImageFromPath("shield.png");
	static BufferedImage torchLeft = (BufferedImage) Filestream.getImageFromPath("torchLeft.png");
	static BufferedImage torchRight = (BufferedImage) Filestream.getImageFromPath("torchRight.png");
	static BufferedImage fence = (BufferedImage) Filestream.getImageFromPath("fence.png");
	static BufferedImage fenceBottom = (BufferedImage) Filestream.getImageFromPath("fenceBottom.png");
	//static BufferedImage spell_dew = (BufferedImage) Filestream.getImageFromPath("spell_dew.png");
	//static BufferedImage spell_kushquake = (BufferedImage) Filestream.getImageFromPath("spell_kushquake.png");
	public static String path = System.getenv("APPDATA")+"/HB2/";
	
	public static String getLevelData(String lvl){
		InputStream is = Bank.class.getResourceAsStream("levels/"+lvl+".HB"); 
		return convertStreamToString(is);
	}
	
	public static String[] getBio(String str){
		InputStream is = Bank.class.getResourceAsStream(str); 
		String[] s = convertStreamToStringArray(is);
		return s;
	}
	
	public static String getRawLevelData(String lvl){
		return Bank.getRawdirDataLine(lvl);
	}
	
	static String convertStreamToString(java.io.InputStream is) {
	    Scanner s = new Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
	/**
	 * Reads an IS to a 1D string array with 30 or less pieces
	 * @param is
	 * @return
	 */
	static String[] convertStreamToStringArray(java.io.InputStream is) {
	    Scanner s = new Scanner(is).useDelimiter("\\A");
	    int index = 0;
	    String[] ret = new String[30];
	    while(s.hasNextLine()){
	    	ret[index] = s.nextLine();
	    	++index;
	    }
	    return ret;
	}
	
	public static void drawSquare(Graphics g, int x, int y, int w, int h){
		int size = 3;
		g.drawImage(Bank.guileft, x, y, size, h, null);
		g.drawImage(Bank.guiright, x+w-size, y, size, h, null);
		g.drawImage(Bank.guibottom, x, y+h-size, w, size, null);
		g.drawImage(Bank.guitop, x, y, w, size, null);
	}
	
	public static boolean hasLockVal(String f, int index){
		return getRawdirDataLine(path+"data/"+f).split("0")[index].contains("1");
	}
	
	public static boolean[] produceLockvalArray(String f){
		String[] sa = getRawdirDataLine(path+"data/"+f).split("0");
		boolean[] ba = new boolean[sa.length];
		for(int i = 0; i < sa.length; ++i){
			if(sa[i].contains("1"))ba[i]=true;
		}
		return ba;
	}
	
	public static boolean setLockVal(String p, int index, boolean state){
		boolean ret = false;
		StringBuilder sb = new StringBuilder();
		String s = getRawdirDataLine(Bank.path+"data/"+p);
		String[] ns = s.split("0");
		if(ns[index].equals("1"))ret=true;
		if(state){
			ns[index] = "1";
		}else{
			ns[index] = (""+(2+Util.rand.nextInt(8)));
		}
		for(int i = 0; i < ns.length; ++i){
			sb.append(ns[i]+"0");
		}
		setContentsRawdir(Bank.path+"data/"+p, sb.toString());
		return ret;
	}
	
	public static void createLockFile(String s1){
		String s = "";
		for(int i = 0; i < Hat.all.length; ++i){
			s+=((2+Util.rand.nextInt(8))+"0");
		}
		Bank.setContentsRawdir(Bank.path+s1, s);
	}
	
	public static void init(){
		File wFolder = new File(path+"maps");
		File cFolder = new File(path+"chars");
		File dFolder = new File(path+"data");
		File lFolder = new File(path+"loadouts");
		File tFolder = new File(path+"tracks");
		File rFolder = new File(path+"resourcepacks");
		wFolder.mkdirs();
		cFolder.mkdirs();
		dFolder.mkdirs();
		lFolder.mkdirs();
		tFolder.mkdirs();
		rFolder.mkdirs();
		File core = new File(path+"data/Core.HB");
		File keybinds = new File(path+"data/keybinds.HB");
		File settings = new File(path+"data/settings.HB");
		File hats = new File(path+"data/hats.HB");
		File vfx = new File(path+"data/vfx.HB");
		File sfx = new File(path+"data/fx.HB");
		File blocks = new File(path+"data/blocks.HB");
		File spells = new File(path+"data/spells.HB");
		File maps = new File(path+"data/maps.HB");
		File stats = new File(path+"data/stats.HB");
		File achievements = new File(path+"data/achievements.HB");
		File heroes = new File(path+"data/heroes.HB");
		File trial = new File(path+"data/trial.HB");
		File beta = new File(path+"data/lock.HB");
		try {
			if(stats.createNewFile()){}		
			if(keybinds.createNewFile()){}
			if(trial.createNewFile()){}
			if(beta.createNewFile()){Analysis.setKey(beta, "00x0", "0");}
			if(core.createNewFile()){
				Bank.setContentsRawdir(Bank.path+"data/Core.HB", Player.BASE_RUNES+"");
			}
			if(hats.createNewFile()){
				createLockFile("data/hats.HB");
			}
			if(vfx.createNewFile()){
				createLockFile("data/vfx.HB");
			}
			if(sfx.createNewFile()){
				createLockFile("data/fx.HB");
			}
			if(settings.createNewFile()){
				
			}
			if(spells.createNewFile()){
				createLockFile("data/spells.HB");
			}
			if(maps.createNewFile()){
				createLockFile("data/maps.HB");
			}
			if(heroes.createNewFile()){
				createLockFile("data/heroes.HB");
			}
			if(achievements.createNewFile()){
				createLockFile("data/achievements.HB");
			}
			if(blocks.createNewFile()){
				createLockFile("data/blocks.HB");
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		for(int i = 0; i < Hero.all.length; ++i){
			if(Hero.all[i]!=null){
				File f = new File(path+"chars/"+Hero.all[i].classname+".HB");
				try {
					if(f.createNewFile()){
						StringBuilder sb = new StringBuilder();
						for(int x = 0; x < 75; ++x)sb.append("0:");
						Analysis.setKey(f, "sp1", ""+(Hero.all[i].spells.size() > 0 ? Hero.all[i].spells.get(0).id:0));
						Analysis.setKey(f, "sp2", ""+(Hero.all[i].spells.size() > 0 ? Hero.all[i].spells.get(1).id:0));
						Analysis.setKey(f, "sp3", ""+(Hero.all[i].spells.size() > 0 ? Hero.all[i].spells.get(2).id:0));
						Analysis.setKey(f, "power", "0");
						Analysis.setKey(f, "fort", "0");
						Analysis.setKey(f, "resto", "0");
						Analysis.setKey(f, "lvl", "1");
						Analysis.setKey(f, "ccl", "0");
						Analysis.setKey(f, "hat", "-1");
						Analysis.setKey(f, "skills", sb.toString());
						//PlayerAnalysis.setKey(f, "sp4", ""+Hero.all[i].spells.get(3).id);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static BufferedImage rotate(BufferedImage img, double angle)
	{
	    double sin = Math.abs(Math.sin(Math.toRadians(angle))),
	           cos = Math.abs(Math.cos(Math.toRadians(angle)));

	    int w = img.getWidth(null), h = img.getHeight(null);

	    int neww = (int) Math.floor(w*cos + h*sin),
	        newh = (int) Math.floor(h*cos + w*sin);

	    BufferedImage bimg = new BufferedImage(neww, newh, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = bimg.createGraphics();

	    g.translate((neww-w)/2, (newh-h)/2);
	    g.rotate(Math.toRadians(angle), w/2, h/2);
	    g.drawRenderedImage(img, null);
	    g.dispose();

	    return bimg;
	}
	
	 public static BufferedImage flip(BufferedImage bi) {
	        BufferedImage flipped = new BufferedImage(
	                bi.getWidth(),
	                bi.getHeight(),
	                BufferedImage.TYPE_INT_ARGB);
	        AffineTransform tran = AffineTransform.getTranslateInstance(bi.getWidth(), 0);
	        AffineTransform flip = AffineTransform.getScaleInstance(-1d, 1d);
	        tran.concatenate(flip);

	        Graphics2D g = flipped.createGraphics();
	        g.setTransform(tran);
	        g.drawImage(bi, 0, 0, null);
	        g.dispose();
	       return flipped;
	   }
	 
		public static String getRawdirDataLine(String f){
			File file = new File(f);
			try {
				file.createNewFile();
				FileReader w = new FileReader(file);
				BufferedReader bw = new BufferedReader(w);
				return bw.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		public static void setContents(String file, String n){
			File f = new File(path+file);
			try {
				FileWriter w = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(w);
				bw.flush();
				bw.write(n);
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		public static void setContentsRawdir(String file, String n){
			File f = new File(file);
			try {
				f.createNewFile();
				FileWriter w = new FileWriter(f);
				BufferedWriter bw = new BufferedWriter(w);
				bw.flush();
				bw.write(n);
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
}
