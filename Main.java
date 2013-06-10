import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.awt.*;
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
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.node.SceneObject;
import org.powerbot.game.api.wrappers.widget.WidgetChild;
import org.powerbot.game.bot.Context;


@Manifest( authors = "Cubz", name = "Blue Dragons", description = "Resource Dung/Shark", version = 1.0)
public class Main extends ActiveScript  implements PaintListener{
		 	 
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
	 
			
public void onStart(){		
		 provide(new TravelyDung(),new  Looting(), new Combat(), new WalktoBank(), new Banking()); }
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
		return !Variable.DungArea.contains(Players.getLocal()) && Inventory.contains(Variable.Food) && Calculations.distanceTo(Variable.bankTile) <= 5;
	}
	
	
     public void execute() {
    	 
    	
    	 Camera.setPitch(99);
    	 Camera.setAngle(0);
    	 
    	 Walking.newTilePath(Variable.PathToStairs).traverse();
    	 Variable.Crossriver.clickOnMap();
    	 sleep(13900, 15300);
    	 
    	 Variable.paintStatus="Entering Cave";
    	 Variable.stairTile.clickOnMap();
         sleep(3300, 3300);
         SceneObject RopeDown = SceneEntities.getNearest(66991);
				
		 RopeDown.click(true);
		 sleep(1900, 2300);				        
		
		 Variable.paintStatus="In Cave";
		 Variable.agliopTile.clickOnMap();
		 sleep(1600, 2100);
		 
		 while(Variable.PreAgliArea.contains(Players.getLocal().getLocation())){
		 Variable.paintStatus="Agility Shortcut";
		 SceneObject agliop = SceneEntities.getNearest(53134);
		 agliop.click(true);
		 agliop.click(true);
		 sleep(6700, 9900);
		 }
		 
		 while(Variable.BlueArea.contains(Players.getLocal().getLocation())){ 
		 Walking.newTilePath(Variable.PathToResource).traverse();
		 sleep(2700, 3500);	
		 Variable.resourceTile.clickOnMap();
		 sleep(1300, 1600);
		 SceneObject dung = SceneEntities.getNearest(52852);
		 dung.click(true);
		 sleep(4900, 5500);
		 }
		 
 		if(!Variable.DungArea.contains(Players.getLocal().getLocation())) {
 		Variable.blueTile.clickOnMap();
 		 sleep(700, 900); 
 		
 		}
     }

    
 		}

public class Looting extends Node{

	@Override
	public boolean activate() {
		GroundItem i = GroundItems.getNearest(Variable.Loot);
		boolean indung = Variable.DungArea.contains(Players.getLocal().getLocation());
		boolean full = Inventory.isFull() && !Inventory.contains(Variable.Food);

		return i != null && indung && Variable.DungArea.contains(i) &&  !full;
	}
	

	
	@SuppressWarnings("deprecation")
	@Override
	public void execute() {	
		   GroundItem i = GroundItems.getNearest(Variable.Loot);
		if(Variable.DungArea.contains(i)){
			Camera.turnTo(i.getLocation(), 6);
			if(Inventory.isFull() && Inventory.contains(Variable.Food)){
				Variable.paintStatus="Eating for room";
				WidgetChild e = Inventory.getItem(Variable.Food).getWidgetChild();
				e.interact("Eat");
			}else {
				if(i != null){
					if(!i.isOnScreen()){
						Walking.walk(i.getLocation());
					}
					Variable.paintStatus="Picking up loot";
					i.interact("Take", i.getGroundItem().getName());
					sleep(650);
					int t = 0;	
					while(Players.getLocal().getInteracting() != null && Players.getLocal().isMoving()){
						sleep(40,10);
						t++;
						if(t==400){
							Variable.paintStatus="Something went wrong looting";
							Game.logout(false);
							Context.get().getScriptHandler().stop();
						}
					}
					int id = i.getId();
					int value = Methods.getPrice(id);
					int stack = i.getGroundItem().getStackSize();
					if(value != 0) {
					int gain = value * stack;
					Variable.totalGained += gain;	
						}
					}
					sleep(600);
				}
			}		
	} 

}

public class WalktoBank extends Node{

	@Override
	public boolean activate() {
		return !Variable.bankTile.isOnScreen() 
				&& !Inventory.contains(Variable.Food) 
				&& !Lodestone.isPlayerTeleporting();
	}

	@Override
	public void execute() {
		
	Variable.paintStatus="trying to teleport";
   	
   	if(!Lodestone.TAVERLY_ARRIVAL_AREA.contains(Players.getLocal().getLocation())){
   		Variable.TeleTile.clickOnMap();
   	    sleep(4400, 5900);
   	Lodestone.teleportTo(Lodestone.TAVERLY, Lodestone.TAVERLY_ARRIVAL_AREA); 
     sleep(9000, 12000);
   	}
   	Variable.paintStatus="Walking to bank";
     Variable.ToBankTile.clickOnMap();
	 sleep(4700, 7500);
	 Variable.prebankTile.clickOnMap();
	 sleep(3300, 5300);
	 Variable.bankTile.clickOnMap();
     sleep(4300, 6300);
     Variable.bankTile.clickOnMap();
     sleep(3300, 5300);
    }
	

}

private class Banking extends Node {
	 
    @Override
    public boolean activate() {
        return !Inventory.contains(Variable.Food) && Calculations.distanceTo(Variable.bankTile) <= 5;
    }

    @Override
    public void execute() {
    	Variable.paintStatus = "Banking";
        SceneObject bank = SceneEntities.getNearest(66666);
		 bank.click(true);
		 sleep(1900, 2300);
		 
        if (Bank.isOpen()) {
            Bank.depositInventory();
            Bank.withdraw(Variable.Food, Variable.Foodamount);
            }
        }
    }


private Image getImage(String url) {
    try {
        return ImageIO.read(new URL(url));
    } catch(IOException e) {
        return null;
    }
}

private final Color COLOR1 = new Color(255, 255, 255);
private final Font FONT1 = new Font("Arial", 0, 9);
private final Image IMG1 = getImage("http://i39.tinypic.com/ws4mpy.jpg");

public void onRepaint(Graphics g1) {
	
Variable.gainedXpAttack = Skills.getExperience(Skills.ATTACK) - Variable.startXpAttack;
Variable.gainedXpStrength = Skills.getExperience(Skills.STRENGTH) - Variable.startXpStrength;
Variable.gainedXpDefense = Skills.getExperience(Skills.DEFENSE) - Variable.startXpDefense;
Variable.gainedXpHitpoints = Skills.getExperience(Skills.CONSTITUTION) - Variable.startXpHitpoints;
Variable.gainedExp = Variable.gainedXpAttack + Variable.gainedXpStrength + Variable.gainedXpDefense + Variable.gainedXpHitpoints;
int expHour = (int)(Variable.gainedExp * 10000 / (runTime.getElapsed() / 360));
int gpHour = (int)(Variable.totalGained * 10000 / (runTime.getElapsed() / 360));

    Graphics2D g = (Graphics2D)g1;
    g.drawImage(IMG1, 0, 389, null);
    g.setFont(FONT1);
    g.setColor(COLOR1);
    g.drawString(Variable.paintStatus, 115, 455);
    g.drawString(runTime.toElapsedString(), 115, 473);
	g.drawString("" + Variable.totalGained+ "   (" + gpHour + ")" , 116, 491);
    g.drawString( "" + Variable.gainedExp + "   (" + expHour + ")" , 116, 509);
} 

}
    
    
            
     