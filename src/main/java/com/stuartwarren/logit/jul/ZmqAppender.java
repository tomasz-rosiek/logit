/**
 * 
 */
package com.stuartwarren.logit.jul;

import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

import com.stuartwarren.logit.utils.LogitLog;
import com.stuartwarren.logit.utils.LayoutLoader;
import com.stuartwarren.logit.zmq.IZmqTransport;
import com.stuartwarren.logit.zmq.ZmqTransport;

/**
 * @author Stuart Warren 
 * @date 6 Oct 2013
 * 
 * Log4j1 ZMQ Appender
 */
public class ZmqAppender extends Handler implements IZmqTransport {
    
    final String prefix = ZmqAppender.class.getName();
    static private final Formatter DEFAULT_FORMATTER = new SimpleFormatter();
    static private final Level DEFAULT_LEVEL = Level.WARNING;
    private LogManager manager = LogManager.getLogManager();
    private ZmqTransport appender;
    
    public ZmqAppender() {
        LogitLog.debug("Jul ZMQ appender in use.");
        ShutdownHook sh = new ShutdownHook();
        sh.attachShutDownHook();
        this.appender = new ZmqTransport();
        configure();
        this.appender.configure();
    }

    /**
     * 
     */
    private void configure() {
        String strLevel = manager.getProperty(prefix + ".level");
        Level lev = DEFAULT_LEVEL;
        if (strLevel == null) {
            try
            {
                lev = Level.parse(strLevel);
            }
            catch (Exception ex){
                LogitLog.error("Error parsing level.", ex);
            }
        }
        setLevel(lev);
        setEndpoints(manager.getProperty(prefix + ".endpoints"));
        setSocketType(manager.getProperty(prefix + ".socketType"));
        setLinger(Integer.parseInt(manager.getProperty(prefix + ".linger")));
        setBindConnect(manager.getProperty(prefix + ".bindConnect"));
        setSendHWM(Integer.parseInt(manager.getProperty(prefix + ".sendHWM")));
        setTheFormatter(manager.getProperty(prefix + ".layout"));
    }

    public void close() {
        this.appender.stop();
        LogitLog.debug("ZMQ context should now be closed!");
    }
    
    /* (non-Javadoc)
     * @see java.util.logging.Handler#publish(java.util.logging.LogRecord)
     */
    @Override
    public void publish(LogRecord record) {
        String log = this.getFormatter().format(record);
        this.appender.appendString(log);
    }

    /* (non-Javadoc)
     * @see java.util.logging.Handler#flush()
     */
    @Override
    public void flush() {
        
    }

    /* (non-Javadoc)
     * @see com.stuartwarren.logit.appender.ITransport#getEndpoints()
     */
    public String getEndpoints() {
        return this.appender.getEndpoints();
    }

    /* (non-Javadoc)
     * @see com.stuartwarren.logit.appender.ITransport#setEndpoints(java.lang.String)
     */
    public void setEndpoints(String endpoints) {
        this.appender.setEndpoints(endpoints);
    }

    /* (non-Javadoc)
     * @see com.stuartwarren.logit.appender.ITransport#getSocketType()
     */
    public String getSocketType() {
        return this.appender.getSocketType();
    }

    /* (non-Javadoc)
     * @see com.stuartwarren.logit.appender.ITransport#setSocketType(java.lang.String)
     */
    public void setSocketType(String socketType) {
        this.appender.setSocketType(socketType);
    }

    /* (non-Javadoc)
     * @see com.stuartwarren.logit.appender.ITransport#getLinger()
     */
    public int getLinger() {
        return this.appender.getLinger();
    }

    /* (non-Javadoc)
     * @see com.stuartwarren.logit.appender.ITransport#setLinger(int)
     */
    public void setLinger(int linger) {
        this.appender.setLinger(linger);
    }

    /* (non-Javadoc)
     * @see com.stuartwarren.logit.appender.ITransport#getBindConnect()
     */
    public String getBindConnect() {
        return this.appender.getBindConnect();
    }

    /* (non-Javadoc)
     * @see com.stuartwarren.logit.appender.ITransport#setBindConnect(java.lang.String)
     */
    public void setBindConnect(String bindConnect) {
        this.appender.setBindConnect(bindConnect);
    }

    /* (non-Javadoc)
     * @see com.stuartwarren.logit.appender.ITransport#getSendHWM()
     */
    public int getSendHWM() {
        return this.appender.getSendHWM();
    }

    /* (non-Javadoc)
     * @see com.stuartwarren.logit.appender.ITransport#setSendHWM(int)
     */
    public void setSendHWM(int sendHWM) {
        this.appender.setSendHWM(sendHWM);
    }
    
    /**
     * 
     * @param formatter
     *          Name of class to use to format the logs.
     */
    private void setTheFormatter(String formatter) {
        if ( null != formatter) {
            Formatter f = (Formatter) LayoutLoader.instantiateByClassName(formatter, DEFAULT_FORMATTER);
            this.setFormatter(f);
        }
    }

}