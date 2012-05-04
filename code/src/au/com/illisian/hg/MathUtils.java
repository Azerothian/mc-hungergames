package au.com.illisian.hg;

import org.bukkit.Location;

public class MathUtils {

	public static double GetDistance(Location v1, Location v2)
	{

		int x =  v1.getBlockX() - v2.getBlockX();
		int y = v1.getBlockY() - v2.getBlockY();
		int z = v1.getBlockZ()- v2.getBlockZ();
		
		return Math.sqrt(x*x + y*y + z*z);
		
		
	}
	
}
