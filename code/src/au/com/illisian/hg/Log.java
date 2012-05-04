package au.com.illisian.hg;

public class Log {

	public static void info(String message)
	{
		Core.getPlugin().getLogger().info(message);
	}
	public static void severe(String message)
	{
		Core.getPlugin().getLogger().severe(message);
	}
}
