package sg.edu.nus.iss.yunakti.engine.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import com.thoughtworks.xstream.XStream;

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
	
	
	public static void println(String message){
		
		if (outputStream==null){
			outputStream=createConsoleStream();
		}
		outputStream.println(message);
		
	}
	
	

	public static void print(String message){
		
		if (outputStream==null){
			outputStream=createConsoleStream();
		}
		outputStream.print(message);
		
	}
	
	public static void classToString(String metaInfo, Object object){
		if(object == null)	{
			return;
		}
		
		XStream xs = new XStream();
		xs.alias(object.getClass().getName(), object.getClass());
		String xml = xs.toXML(object);
		outputStream.print(metaInfo + "\n" + xml);
	}

	
	public static void print(Exception exception){
		StringWriter stringWriter = new StringWriter(); 
		PrintWriter printWriter = new PrintWriter(stringWriter); 
		printWriter.print(" [ "); 
		printWriter.print(exception.getClass().getName()); 
		printWriter.print(" ] "); 
		printWriter.print(exception.getMessage()); 
		exception.printStackTrace(printWriter); 
		outputStream.print(stringWriter.toString());
	}
}
