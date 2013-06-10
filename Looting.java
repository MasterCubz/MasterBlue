
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Game;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.input.Mouse;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.node.GroundItems;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;
import org.powerbot.game.api.wrappers.node.GroundItem;
import org.powerbot.game.api.wrappers.widget.WidgetChild;
import org.powerbot.game.bot.Context;

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
				if (!e.validate()){
					Mouse.click(Variable.INV_TAB.getCentralPoint(), true);
				}
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
