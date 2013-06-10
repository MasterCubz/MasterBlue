import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;

public class WalkToBank extends Node{

	@Override
	public boolean activate() {
		return !Variable.bankTile.isOnScreen() 
				&& Inventory.contains(Variable.Food) 
				&& !Lodestone.isPlayerTeleporting();
	}

	@Override
	public void execute() {
		
	Variable.paintStatus="trying to teleport";
   	
   	if(!Lodestone.TAVERLY_ARRIVAL_AREA.contains(Players.getLocal().getLocation())){
   		Variable.TeleTile.interact("click");
   	    sleep(4400, 5900);
   	Lodestone.teleportTo(Lodestone.TAVERLY, Lodestone.TAVERLY_ARRIVAL_AREA); 
     sleep(9000, 12000);
   	}
   	Variable.paintStatus="Walking to bank";
     Walking.newTilePath(Variable.PathToBank).traverse();
     sleep(9700, 11500);
     Variable.bankTile.interact("click");
     sleep(7300, 8600);
    }
	

}

