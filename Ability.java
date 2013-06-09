import java.awt.event.KeyEvent;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.widget.WidgetChild;


public class Ability {
	
       public static WidgetChild close = Widgets.get(640, 30);
	   public static WidgetChild open = Widgets.get(640, 3);
	   public static WidgetChild EnterPress = Widgets.get(137, 56);
	   
	public static void  Abilities() {
     
    
		if (open.visible()) {
			open.click(true);
		}

		if (Players.getLocal().isInCombat() == true) {
			if (EnterPress.getText() != "[Press Enter to Chat]") {
				if (close.visible()) {
					Keyboard.sendKey((char) KeyEvent.VK_ENTER);
				} else {
					open.click(true);
					Task.sleep(500, 20);
					Keyboard.sendKey((char) KeyEvent.VK_ENTER);
				}
			}
		}

		if (!(Players.getLocal().getInteracting() == null)) {
			if (Players.getLocal().getAdrenalinePercent() == 100) {
				Keyboard.sendKey('6');
				Task.sleep(1500, 400);
			}
		}

		if (!(Players.getLocal().getInteracting() == null)) {
			Keyboard.sendKey('1');
			Task.sleep(1500, 400);
		}
		if (!(Players.getLocal().getInteracting() == null)) {
			Keyboard.sendKey('2');
			Task.sleep(1500, 400);
		}
		if (!(Players.getLocal().getInteracting() == null)) {
			Keyboard.sendKey('3');
			Task.sleep(1500, 400);
		}
		if (!(Players.getLocal().getInteracting() == null)) {
			Keyboard.sendKey('4');
			Task.sleep(1500, 400);
		}
		if (!(Players.getLocal().getInteracting() == null)) {
			Keyboard.sendKey('5');
			Task.sleep(1500, 400);
		}

		if (!(Players.getLocal().getInteracting() == null)) {
			if (Players.getLocal().getAdrenalinePercent() == 100) {
				Keyboard.sendKey('6');
				Task.sleep(1500, 400);
			}
		}
	}

	
}
