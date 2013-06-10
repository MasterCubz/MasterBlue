import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.SceneObject;


public class WalkToDung extends Node {
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
			 sleep(9700, 11900);
			 }
			 
			 while(Variable.BlueArea.contains(Players.getLocal().getLocation())){ 
			 Variable.paintStatus="In Blues";
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

