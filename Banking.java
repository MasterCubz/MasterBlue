import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.node.SceneEntities;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;
import org.powerbot.game.api.wrappers.node.SceneObject;

public class Banking extends Node {
	 
    @Override
    public boolean activate() {
        return !Inventory.contains(Variable.Food) && Calculations.distanceTo(Variable.bankTile) <= 5;
    }

    @Override
    public void execute() {
    	Variable.paintStatus = "Banking";
    	  //NPC bank = NPCs.getNearest(14924);
        SceneObject bank = SceneEntities.getNearest(66666);
		  //bank.interact("click");
		  bank.click(true);
		  //Bank.open();
		 sleep(1900, 2300);

		 
        if (Bank.isOpen()) {
            Bank.depositInventory();
            Bank.withdraw(Variable.Food, Variable.Foodamount);
            }
        }
    }


