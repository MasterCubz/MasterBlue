import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.NPCs;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.interactive.NPC;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

public class Combat extends Node {
	 
    @Override
    public boolean activate() {
        return Variable.DungArea.contains(Players.getLocal()) && Inventory.contains(Variable.Food);

    }
    @Override
    public void execute() {
    	if (Players.getLocal().getHealthPercent()<60){
    		Variable.paintStatus="Eating food";
			WidgetChild e = Inventory.getItem(Variable.Food).getWidgetChild();
			e.interact("click");
			sleep(100,200);
		}
    	
    	NPC target = NPCs.getNearest(Variable.monsters);
        out:
        if ( target != null && Variable.DungArea.contains(target)){
        	if (target.isOnScreen()) 
        	{
        		 if (target.getInteracting() == null) {
                     Mouse.setSpeed(Mouse.Speed.FAST);
                     Variable.paintStatus = "Attacking";
                     if (target.interact("Attack"))                     
                    	  do {			
                    		 if(!(Players.getLocal().getAnimation() == -1)){
                    		 Methods.abilities();
                    	 }
                    		 Variable.paintStatus = "Owning Some Dragons";
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
        		Variable.paintStatus = "Let's Find a Dragon";
            target = NPCs.getNearest(Variable.monsters);
            Walking.newTilePath(Variable.PathInDung).reverse().traverse();
                sleep(500);
            if (target.validate())
                break out;
    }
  
}
}
			