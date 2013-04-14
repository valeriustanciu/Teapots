package log;


import org.apache.log4j.*;

import mediator.Mediator;

public interface IUserLog {
	public void setLogInfo ();
	public Logger getLogger ();
	public void writeDebug (String message);
	public void writeInfo (String message);
	public void writeWarn (String message);
	public void writeError (String message);
	public void writeFatal (String message);
}
