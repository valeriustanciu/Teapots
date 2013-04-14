package log;


import org.apache.log4j.*;

import mediator.Mediator;

public class UserLog implements IUserLog {
	private Mediator med;
	private Logger logger;
	private String username;
	private String logFileName;
	
	public UserLog (Mediator med) {
		this.med = med;
	}
	
	public void setLogInfo () {
		this.username = this.med.getCurrentUser();
		this.logFileName = this.username + "_log.txt";
		String conversionPattern = "%d %p - %m%n";

		FileAppender appender = new FileAppender();
		PatternLayout layout = new PatternLayout();
		layout.setConversionPattern(conversionPattern);
		
		appender.setFile(this.logFileName);
		appender.setAppend(false);
		appender.setLayout(layout);
		appender.activateOptions();
		
		this.logger = Logger.getRootLogger();
		this.logger.addAppender(appender);
	}
	
	public Logger getLogger () {
		return this.logger;
	}
	
	public void writeDebug (String message) {
		this.logger.debug(message);
	}
	
	public void writeInfo (String message) {
		this.logger.info(message);
	}
	
	public void writeWarn (String message) {
		this.logger.warn(message);
	}
	
	public void writeError (String message) {
		this.logger.error(message);
	}
	
	public void writeFatal (String message) {
		this.logger.fatal(message);
	}
}
