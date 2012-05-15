package sg.edu.nus.iss.yunakti.engine.util;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class ConsoleStreamUtil {
	
	private static final ConsoleStreamUtil INSTANCE = new ConsoleStreamUtil();
	private ConsoleStreamUtil() {}

	private static MessageConsoleStream outputStream=createConsoleStream();
	
	public static ConsoleStreamUtil getInstance() { 
		
		return INSTANCE; 
		
	}
	
	
	private static MessageConsoleStream createConsoleStream() {

		MessageConsole console=new MessageConsole("Annotations Search Results", null);
		console.activate();
		ConsolePlugin consolePlugin=ConsolePlugin.getDefault();
		consolePlugin.getConsoleManager().addConsoles(new IConsole[]{console});
		MessageConsoleStream messageStream = console.newMessageStream();
		return messageStream;
	}
	
	
	public static void print(String message){
		
		outputStream.println(message);
		
	}

}
