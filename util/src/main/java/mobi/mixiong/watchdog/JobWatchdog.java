package mobi.mixiong.watchdog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class JobWatchdog {

	public static void main(String[] argv) {
		if (argv.length == 0) {
			System.out.println("need to specify watched command as arguments");
			System.exit(-1);
		}

		// drop a pid file if these options are set.
		String pid = System.getProperty("pid");
		String pidfile = System.getProperty("pidfile");
		if (pidfile != null && pid != null) {
			File f = new File(pidfile);
			f.deleteOnExit();

			try {
				FileWriter fw = new FileWriter(f);
				fw.write(pid);
				fw.close();
			} catch (IOException e) {
				System.out.println("failed to drop a pid file");
				e.printStackTrace();
				System.exit(-1);
			}
			System.out.println("Dropped a pidfile='" + pidfile + "' with pid=" + pid);
		} else {
			System.out.println("No pid or pidfile system property specified.");
		}

		String interactiveprop = System.getProperty("fwdstdin");
		boolean interactive = (interactiveprop != null);

		String[] args = argv;

//		PropertiesLoader loader = new PropertiesLoader("etl.properties");
//		int maxTriesPerMin = loader.getInteger("watchdog.restarts.max", 5);
		Watchdog watchdog = new Watchdog(args, interactive);
		watchdog.run(5);
	}
}
