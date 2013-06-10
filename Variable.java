import org.powerbot.game.api.methods.tab.Skills;
import org.powerbot.game.api.wrappers.Area;
import org.powerbot.game.api.wrappers.Tile;


public class Variable {
	
	
	 
	 public static final  int Food = 385;
	 public static final  int Foodamount = 4;
	 public static final int[]Loot =  {536, 1751, 217, 2485, 211, 1213};
	 
	 public static String paintStatus;
	 public static int totalGained = 0;
	 public static int monsters[] = {55,4681};
	 
	 public static final int startXpAttack = Skills.getExperience(Skills.ATTACK);
	 public static final int startXpStrength = Skills.getExperience(Skills.STRENGTH);
	 public static final int startXpDefense = Skills.getExperience(Skills.DEFENSE);
	 public static final int startXpHitpoints = Skills.getExperience(Skills.CONSTITUTION);
	 
	 public static int gainedXpAttack;
	 public static int gainedXpStrength;
	 public static int gainedXpDefense;
	 public static int gainedXpHitpoints;
	 public static int gainedExp;	
	 
	 public static Tile bankTile = new Tile(2875, 3417, 0);
	 public static Tile stairTile = new Tile(2885, 3395, 0);
	 public static Tile agliopTile = new Tile(2886,9798,0);
	 public static Tile resourceTile = new Tile(2912,9810,0);
	 public static Tile blueTile = new Tile(1011, 4525,0);
	 public static Tile TeleTile = new Tile(1002,4523,0);
	 public static Tile aglifinTile = new Tile(2892,9799,0);
	 public static Tile crossTile = new Tile(2891,3404,0);
	 
	 public static final Area DungArea = new Area(new Tile(1020, 4537, 0), new Tile(1020, 4505, 0), new Tile(1005, 4505, 0),
			                                      new Tile(1003, 4537, 0));
	 public static final Area PreAgliArea = new Area(new Tile(2887, 9795, 0), new Tile(2887, 9801, 0), new Tile(2885, 9801, 0), 
			                                         new Tile(2885, 9795, 0));
	 public static final Area BlueArea = new Area(new Tile(2892, 9794, 0), new Tile(2923, 9794, 0),new Tile(2923, 9813, 0), 
			 new Tile(2892, 9813, 0));
	 
	 public static final Tile[] PathToResource = new Tile[] { new Tile(2893, 9799, 0), new Tile(2896, 9799, 0), new Tile(2907, 9805, 0) };
	 public static final Tile[] PathInDung = new Tile[] {new Tile(1010,4512,0), new Tile(1012,4522,0), new Tile(1011,4529,0)};
	 public static final Tile[] PathToStairs = new Tile[] {new Tile(2875,3417,0), new Tile(2887,3415,0), new Tile(2898,3414,0), 
	                                                       new Tile(2892,3404,0), new Tile(2885,3395,0)};
	 public static final Tile[] PathToBank = new Tile[] { new Tile(2883,3435,0), new Tile(2883,3429,0), new Tile(2883,3422,0), 
		                                                  new Tile(2881,3418,0), new Tile(2878,3417,0), new Tile(2875,3417,0)};
	 
	 
}
