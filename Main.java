import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.awt.*;
import java.awt.event.MouseEvent;
import javax.imageio.ImageIO;
import java.awt.Image;


import org.powerbot.core.event.listeners.PaintListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.core.script.job.state.Tree;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Random;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Locatable;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.WidgetChild;
import org.powerbot.game.bot.Context;


@Manifest( authors = "Cubz", name = "Blue Dragons", description = "Resource Dung/Shark", version = 1.0)
public class Main extends ActiveScript  implements PaintListener{

	final int food = 385;
	final int foodamount = 4;
	public final int[]lootTier =  {536, 1751, 217, 2485, 211, 1213};
	
	 Tile bankTile = new Tile(2875, 3417, 0);
	 Tile ToBankTile = new Tile(2882,3429,0);
	 Tile stairTile = new Tile(2885, 3395, 0);
	 Tile returnTile = new Tile(2672, 3085, 0);
	 Tile agliopTile = new Tile(2886,9799,0);
	 Tile resourceTile = new Tile(2912,9810,0);
	 Tile blueTile = new Tile(1011, 4525,0);
	 Tile Crossriver = new Tile(2885,3403,0);
	 Tile TeleTile = new Tile(1002,4523,0);
	 Tile prebankTile = new Tile(2881,3420,0);
	 
	 private final Area dungArea = new Area(new Tile(1020, 4537, 0), new Tile(1020, 4511, 0), new Tile(1005, 4511, 0), new Tile(1005, 4537, 0));
	 private static final Tile[] pathToResource = new Tile[] { new Tile(2893, 9799, 0), new Tile(2896, 9799, 0), new Tile(2907, 9805, 0) };
	 public final Tile[] pathToBank = new Tile[] { new Tile(2883,3435,0), new Tile(2883,3429,0), new Tile(2883,3422,0), new Tile(2881,3418,0), new Tile(2878,3417,0), new Tile(2875,3417,0)};
	 
	 public static Map<Integer, Integer> lootPrices; 
	 
	 public static int totalGained = 0;
	 public static String totalGainedString;
	 public static int totalGainedPerHour;
	 public static String totalGainedPerHourString;
	
	 
	 public static final int startXpAttack = Skills.getExperience(Skills.ATTACK);
	 public static final int startXpStrength = Skills.getExperience(Skills.STRENGTH);
	 public static final int startXpDefense = Skills.getExperience(Skills.DEFENSE);
	 public static final int startXpHitpoints = Skills.getExperience(Skills.CONSTITUTION);
	 
	 public static int gainedXpAttack;
	 public static int gainedXpStrength;
	 public static int gainedXpDefense;
	 public static int gainedXpHitpoints;
	
	 public static int gainedExp;	
	 public static String gainedExpString;
	 public static int expPHour;
	 public static String expPHourString;
	 
	 String paintStatus;
	 private Timer runTime = new Timer(0);
	 private final List<Node> jobsCollection = Collections.synchronizedList(new ArrayList<Node>());
	 private Tree jobContainer = null;
	 public final void provide(final Node... jobs) {
	        for (final Node job : jobs) {
	            if (!jobsCollection.contains(job)) {
	                jobsCollection.add(job);
	            }
	        }
	        jobContainer = new Tree(jobsCollection.toArray(new Node[jobsCollection.size()]));
	    }
	 
	// Code by Manner 
	// http://www.powerbot.org/community/topic/919073-mcamerajava-use-the-mouse-to-turn-the-camera/
	 @SuppressWarnings("deprecation")
public static boolean dragMouse(int x1, int y1, int x2, int y2) {
			final org.powerbot.game.client.input.Mouse MOUSE = Context.client()
					.getMouse();
			final Component TARGET = Context.get().getLoader().getComponent(0);
			Mouse.move(x1, y1);
			MOUSE.sendEvent(new MouseEvent(TARGET, MouseEvent.MOUSE_PRESSED, System
					.currentTimeMillis(), 0, Mouse.getX(), Mouse.getY(), 1, false,
					MouseEvent.BUTTON2));
			Mouse.move(x2, y2);
			MOUSE.sendEvent(new MouseEvent(TARGET, MouseEvent.MOUSE_RELEASED,
					System.currentTimeMillis(), 0, Mouse.getX(), Mouse.getY(), 1,
					false, MouseEvent.BUTTON2));
			return Mouse.getX() == x2 && Mouse.getY() == y2 && !Mouse.isPressed();
		}
public static boolean turnTo(Locatable locatable, int degreesDeviation) {
			final double DEGREES_PER_PIXEL_X = 0.35;
			int degrees = Camera.getMobileAngle(locatable) % 360;
			int angleTo = Camera.getAngleTo(degrees);
			while (Math.abs(angleTo) > degreesDeviation) {
				angleTo = Camera.getAngleTo(degrees);
				int pixelsTo = (int) Math.abs(angleTo / DEGREES_PER_PIXEL_X)
						+ Random.nextInt(
								-(int) (degreesDeviation / DEGREES_PER_PIXEL_X) + 1,
								(int) (degreesDeviation / DEGREES_PER_PIXEL_X) - 1);
				if (pixelsTo > 450)
					pixelsTo = pixelsTo / 450 * 450;
				int startY = Mouse.getY() < 255 && Mouse.getY() > 55 ? Random
						.nextInt(-25, 25) + Mouse.getY() : Random.nextInt(70, 240);
				if (angleTo > degreesDeviation) {//right
					int startX = (500 - pixelsTo)
							- Random.nextInt(0, 500 - pixelsTo - 10);
					dragMouse(startX, startY, startX + pixelsTo,
							startY + Random.nextInt(90, 121));
				} else if (angleTo < -degreesDeviation) {// left
					int startX = (pixelsTo + 10)
							+ Random.nextInt(0, 500 - pixelsTo + 10);
					dragMouse(startX, startY, startX - pixelsTo,
							startY + Random.nextInt(90, 121));
				}
			}
			return Math.abs(Camera.getAngleTo(degrees)) <= degreesDeviation;
		}
    // End Code by Manner
			
public void onStart(){		 
		 provide(new TravelyDung(),new  Looting(), new AttackDragon(), new WalktoBank(), new Banking()); }
@Override
	    public int loop() {
	        if (jobContainer != null) {
	            final Node job = jobContainer.state();
	            if (job != null) {
	                jobContainer.set(job);
	                getContainer().submit(job);
	                job.join();
	            }
	        }
	        return 35;
	    }

private class TravelyDung extends Node {	 
	@Override
	public boolean activate() {
		return !dungArea.contains(Players.getLocal()) && Inventory.contains(food) && Calculations.distanceTo(bankTile) <= 7;
	}
	
	
     public void execute() {
    	 
    	
    	 Camera.setPitch(99);
    	 Camera.setAngle(0);
    	 
    	 Crossriver.clickOnMap();
    	 sleep(13900, 15300);
    	 
    	 paintStatus="Entering Cave";
         stairTile.clickOnMap();
         sleep(3300, 3300);
         SceneObject RopeDown = SceneEntities.getNearest(66991);
				
		 RopeDown.click(true);
		 sleep(1900, 2300);				        
		
		 paintStatus="Agility Shortcut";
		 agliopTile.clickOnMap();
		 sleep(1600, 2100);
		 SceneObject agliop = SceneEntities.getNearest(53134);
		 agliop.click(true);
		 agliop.click(true); // add
		 sleep(6700, 9900);
				
			
		 Walking.newTilePath(pathToResource);
		 sleep(1700, 3500);
			
		 resourceTile.clickOnMap();
		 sleep(1300, 1600);
		 SceneObject dung = SceneEntities.getNearest(52852);
		 dung.click(true);
		 sleep(6900, 7900);
			 		 
 		if(!dungArea.contains(Players.getLocal().getLocation())) {
 		 blueTile.clickOnMap();
 		 sleep(700, 900); 
 		
 		}
     }

    
 		}

public class Looting extends Node{

	@Override
	public boolean activate() {
		GroundItem i = GroundItems.getNearest(lootTier);
		boolean indung = dungArea.contains(Players.getLocal().getLocation());
		boolean full = Inventory.isFull() && !Inventory.contains(food);

		return i != null && indung && dungArea.contains(i) &&  !full;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute() {	
		   GroundItem i = GroundItems.getNearest(lootTier);
		if(dungArea.contains(i)){
			turnTo(i.getLocation(), 6);
			if(Inventory.isFull() && Inventory.contains(food)){
				paintStatus="Eating for room";
				WidgetChild e = Inventory.getItem(food).getWidgetChild();
				e.interact("Eat");
			}else {
				if(i != null){
					if(!i.isOnScreen()){
						Walking.walk(i.getLocation());
					}
					paintStatus="Picking up loot";
					i.interact("Take", i.getGroundItem().getName());
					sleep(650);
					int t = 0;	
					while(Players.getLocal().getInteracting() != null && Players.getLocal().isMoving()){
						sleep(40,10);
						t++;
						if(t==400){
							paintStatus="Something went wrong looting";
							Game.logout(false);
							Context.get().getScriptHandler().stop();
						}
					}
					int id = i.getId();
					int value = lootPrices.get(id);
					int stack = i.getGroundItem().getStackSize();
					if(value == 0) {
						value = lootPrices.get(id - 1);
						if(value != 0) {
							int price = value * stack;
							totalGained += price;	
						}
					} else {
						totalGained += value * stack;
					}
					sleep(600);
				}
			}
		}
	} 

}

public class AttackDragon extends Node {
	 
    @Override
    public boolean activate() {
        return dungArea.contains(Players.getLocal()) && Inventory.contains(food);

    }
    @Override
    public void execute() {
    	if (Players.getLocal().getHealthPercent()<60){
    		paintStatus="Eating food";
			WidgetChild e = Inventory.getItem(food).getWidgetChild();
			e.click(true);
			sleep(100,200);
		}
    	
    	NPC target = NPCs.getNearest(55);
        out:
        if ( target != null && dungArea.contains(target)){
        	if (target.isOnScreen()) 
        	{
        		 if (target.getInteracting() == null) {
                     Mouse.setSpeed(Mouse.Speed.FAST);
                     paintStatus = "Attacking";
                     if (target.interact("Attack"))                     
                    	  do {			
                    		 if(!(Players.getLocal().getAnimation() == -1)){
                    		 Ability.Abilities();
                    	 }
                             paintStatus = "Owning Some Dragons";
                             sleep(1000, 1200);
                    	    }
                    	 while (Players.getLocal().getInteracting() != null);
                   
                     }else { sleep(1000,2000);
                     }
        	} 
        	else { 
        		Camera.turnTo(target);
        		 } 
        }
        	else {
        	paintStatus = "Let's Find Another Dragon";
            target = NPCs.getNearest(55);
                sleep(500);
            if (target.validate())
                break out;
    }
  
}
}
			
public class WalktoBank extends Node{

	@Override
	public boolean activate() {
		return !bankTile.isOnScreen() 
				&& !Inventory.contains(food) 
				&& !Lodestone.isPlayerTeleporting();
	}

	@Override
	public void execute() {
		
   	paintStatus="trying to teleport";
   	
   	if(!Lodestone.TAVERLY_ARRIVAL_AREA.contains(Players.getLocal().getLocation())){
   	TeleTile.clickOnMap();
   	    sleep(3200, 5900);
   	Lodestone.teleportTo(Lodestone.TAVERLY, Lodestone.TAVERLY_ARRIVAL_AREA); 
     sleep(9000, 12000);
   	}
     paintStatus="Walking to bank";
     ToBankTile.clickOnMap();
	 sleep(4700, 7500);
	 prebankTile.clickOnMap();
	 sleep(3300, 5300);
	 bankTile.clickOnMap();
     sleep(4300, 6300);
     bankTile.clickOnMap();
     sleep(3300, 5300);
    }
	

}

private class Banking extends Node {
	 
    @Override
    public boolean activate() {
        return !Inventory.contains(food) && Calculations.distanceTo(bankTile) <= 5;
    }

    @Override
    public void execute() {
        paintStatus = "Banking";
        SceneObject bank = SceneEntities.getNearest(66666);
		 bank.click(true);
		 sleep(1900, 2300);
		 
        if (Bank.isOpen()) {
            Bank.depositInventory();
            Bank.withdraw(food, foodamount);
            }
        }
    }


//START: Code generated using Enfilade's Easel
private Image getImage(String url) {
    try {
        return ImageIO.read(new URL(url));
    } catch(IOException e) {
        return null;
    }
}

private final Color color1 = new Color(255, 255, 255);
private final Font font1 = new Font("Arial", 0, 9);
private final Image img1 = getImage("http://i39.tinypic.com/ws4mpy.jpg");

public void onRepaint(Graphics g1) {
	
gainedXpAttack = Skills.getExperience(Skills.ATTACK) - startXpAttack;
gainedXpStrength = Skills.getExperience(Skills.STRENGTH) - startXpStrength;
gainedXpDefense = Skills.getExperience(Skills.DEFENSE) - startXpDefense;
gainedXpHitpoints = Skills.getExperience(Skills.CONSTITUTION) - startXpHitpoints;
gainedExp = gainedXpAttack + gainedXpStrength + gainedXpDefense + gainedXpHitpoints;
int expHour = (int)(gainedExp * 10000 / (runTime.getElapsed() / 360));

    Graphics2D g = (Graphics2D)g1;
    g.drawImage(img1, 0, 389, null);
    g.setFont(font1);
    g.setColor(color1);
    g.drawString(paintStatus, 115, 455);
    g.drawString(runTime.toElapsedString(), 115, 473);
    g.drawString("" + totalGained, 116, 491);
    g.drawString( "" + gainedExp + "   (" + expHour + ")" , 116, 509);
} 
//END: Code generated using Enfilade's Easel

}
    
    
            
     