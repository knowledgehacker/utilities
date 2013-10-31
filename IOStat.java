import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class IOStat {
    private final int pid;
    private final File procDir;

    public IOStat(int pid) {
        this.pid = pid;
        procDir = new File("/proc/" + Integer.toString(pid) + "/task");
    }

    public void displayIO() {
        try {
            File[] threadDirs = procDir.listFiles();
            for(File threadDir: threadDirs) {
                System.out.println("Thread " + threadDir.getName());
				int threadId = Integer.parseInt(threadDir.getName());
				System.out.println(Integer.toString(threadId, 16));	
			
                File ioFile = new File(threadDir, "io");
                BufferedReader ioReader = new BufferedReader(new FileReader(ioFile));
                String line = ioReader.readLine();
                while(line != null) {
                    System.out.println(line);
                    line = ioReader.readLine();
                }
                ioReader.close();
            }
        }catch(IOException ioe) {
            ioe.printStackTrace();
        }   
    }

    public static void main(String[] args) {
        int argNum = args.length;
        if(argNum != 1) {
            System.err.println("Invalid argument number - " + argNum);
            System.out.println("Usage: java IOStat pid");

            return;
        }

        int pid = Integer.parseInt(args[0]);
        System.out.println(pid);
        IOStat ioStat = new IOStat(pid);
        ioStat.displayIO();
    }
}
