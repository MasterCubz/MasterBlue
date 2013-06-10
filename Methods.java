import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.powerbot.core.script.job.Task;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.wrappers.widget.WidgetChild;


public class Methods {
	
	//Dizzy
	public static int getPrice(int id) {
		  int price = 0;
		  String add = "http://scriptwith.us/api/?return=text&item=";
		  add += id;
		  try {
		   final BufferedReader in = new BufferedReader(new InputStreamReader(
		     new URL(add).openConnection().getInputStream()));
		   final String line = in.readLine();
		   in.close();
		   price= Integer.parseInt(line.split("[:]")[1]);
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
		  return price;
		 }
	
	 
	 private static final WidgetChild CLOSE = Widgets.get(640, 30);
     private static final WidgetChild OPEN = Widgets.get(640, 3);
     private static final WidgetChild ENTERPRESS = Widgets.get(137, 56);
	
    //From resources, with some changes
	public static void  abilities() {
   
  
		if (OPEN.visible()) {
			OPEN.click(true);
		}

		if (Players.getLocal().isInCombat() == true) {
			if (ENTERPRESS.getText() != "[Press Enter to Chat]") {
				if (CLOSE.visible()) {
					Keyboard.sendKey((char) KeyEvent.VK_ENTER);
				} else {
					OPEN.click(true);
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
			if (Players.getLocal().getAdrenalinePercent() == 100
					&& Players.getLocal().getHealthPercent()<60) {
				Keyboard.sendKey('7');
				Task.sleep(1500, 400);
			}
		}
		if (!(Players.getLocal().getInteracting() == null)) {
			if (Players.getLocal().getAdrenalinePercent() == 100) {
				Keyboard.sendKey('6');
				Task.sleep(1500, 400);
			}
		}
	}
}
