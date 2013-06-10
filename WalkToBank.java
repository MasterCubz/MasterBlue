import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Camera;

public class WalkToBank extends Node{

	@Override
	public boolean activate() {
		return !Variable.bankTile.isOnScreen() 
				&& !Inventory.contains(Variable.Food) 
				&& !Lodestone.isPlayerTeleporting();
	}

	@Override
	public void execute() {
		
	Variable.paintStatus="trying to teleport";
   	
   	if(!Lodestone.TAVERLY_ARRIVAL_AREA.contains(Players.getLocal().getLocation())
   		&& !Variable.TravleyArea.contains(Players.getLocal().getLocation()) ){
   		Camera.turnTo(Variable.TeleTile);
   		//if (!Variable.TeleTile.isOnScreen()){
   			//Walking.PathToDungEntre(Variable.PathToBank).traverse();
   		//}
   		
   		Variable.TeleTile.interact("Walk here");
   		Methods.sleeps(!Variable.DungArea.contains(Players.getLocal().getLocation()));
   	    
   	 	Lodestone.teleportTo(Lodestone.TAVERLY, Lodestone.TAVERLY_ARRIVAL_AREA); 
   	    Methods.sleeps(Variable.TravleyArea.contains(Players.getLocal().getLocation()));
   	}
   	
    Variable.paintStatus="Walking to bank";
    Walking.newTilePath(Variable.PathToBank).traverse();
    Methods.sleeps(Variable.TravleyArea.contains(Players.getLocal().getLocation()));
     
    Variable.bankTile.interact("Walk here");
    sleep(7300, 8600);
     
    }
	

}
