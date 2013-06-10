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
import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.util.Timer;


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
		 provide(new WalkToDung(),new  Looting(), new Combat(), new WalkToBank(), new Banking() ); }
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
    
    
            
     