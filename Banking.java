import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.tab.Inventory;
import org.powerbot.game.api.methods.widget.Bank;

public class Banking extends Node {
	 
    @Override
    public boolean activate() {
        return !Inventory.contains(Variable.Food) && Calculations.distanceTo(Variable.bankTile) <= 5;
    }

    @Override
    public void execute() {
    	Variable.paintStatus = "Banking";     
		  Bank.open();
		 
        if (Bank.isOpen()) {
            Bank.depositInventory();
            Bank.withdraw(Variable.Food, Variable.Foodamount);
            }
        }
    }


