/**
 * 
 */
package com.stuartwarren.logit.layout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Stuart Warren 
 * @date 6 Oct 2013
 *
 */
public class Log {
    
    private long                    timestamp;
    private String                  ndc;
    private Map<String, Object>     mdc;
    private ExceptionInformation    exceptionInformation;
    private LocationInformation     locationInformation;
    private String                  level;
    private int                     level_int;
    private String                  loggerName;
    private String                  threadName;
    private String                  message;
    private ArrayList<String>       tags;
    private Map<String,Object>      fields;

    /**
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp
     *            the timestamp to set
     */
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the ndc
     */
    public String getNdc() {
        return ndc;
    }

    /**
     * @param ndc
     *            the ndc to set
     */
    public void setNdc(String ndc) {
        if (null != ndc) {
            this.ndc = ndc;
        }
    }

    /**
     * @return the mdc
     */
    public Map<String, Object> getMdc() {
        return mdc;
    }

    /**
     * @param properties
     *            the mdc to set
     */
    public void setMdc(Map<String, Object> properties) {
        this.mdc = properties;
    }

    /**
     * @return the exceptionInformation
     */
    public ExceptionInformation getExceptionInformation() {
        return exceptionInformation;
    }

    /**
     * @param exceptionInformation
     *            the exceptionInformation to set
     */
    public void setExceptionInformation(
            ExceptionInformation exceptionInformation) {
        this.exceptionInformation = exceptionInformation;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        if (null != message) {
            this.message = message;
        }
    }

    /**
     * @return the locationInformation
     */
    public LocationInformation getLocationInformation() {
        return locationInformation;
    }

    /**
     * @param locationInformation
     *            the locationInformation to set
     */
    public void setLocationInformation(
            LocationInformation locationInformation) {
        this.locationInformation = locationInformation;
    }

    /**
     * @return the level
     */
    public String getLevel() {
        return level;
    }

    /**
     * @param level
     *            the level to set
     */
    public void setLevel(String level) {
        if (null != level) {
            this.level = level;
        }
    }

    /**
     * @return the level_int
     */
    public int getLevel_int() {
        return level_int;
    }

    /**
     * @param level_int the level_int to set
     */
    public void setLevel_int(int level_int) {
        this.level_int = level_int;
    }

    /**
     * @return the loggerNameString
     */
    public String getLoggerName() {
        return loggerName;
    }

    /**
     * @param loggerNameString
     *            the loggerNameString to set
     */
    public void setLoggerName(String loggerName) {
        if (null != loggerName) {
            this.loggerName = loggerName;
        }
    }

    /**
     * @return the threadName
     */
    public String getThreadName() {
        return threadName;
    }

    /**
     * @param threadName
     *            the threadName to set
     */
    public void setThreadName(String threadName) {
        if (null != threadName) {
            this.threadName = threadName;
        }
    }

    // public static String getLocalHostname() {
    // System.setProperty("java.net.preferIPv4Stack" , "true");
    // String hostname;
    // try {
    // hostname = java.net.InetAddress.getLocalHost().getHostName();
    // } catch (UnknownHostException e) {
    // hostname = "unknown";
    // }
    // return hostname;
    // }
    //
    // public static String getUsername() {
    // String username;
    // try {
    // username = System.getProperty("user.name").toLowerCase();
    // } catch (NullPointerException e) {
    // username = "unknown";
    // }
    // return username;
    // }
    
    /**
     * @return the tags
     */
    public ArrayList<String> getTags() {
        return tags;
    }

    /**
     * @param tags 
     *      the tags to set
     */
    public void setTags(String tags) {
        // Split string on commas. Ignore whitespace.
        if (null != tags) {
            this.tags = new ArrayList<String>(Arrays.asList(tags.split("\\s*,\\s*")));
        }
    }
    
    /**
     * @param tag 
     *      the tag to add to the list
     */
    public void appendTag(String tag) {
        if (null != tag) {
            if (null == this.tags) {
                this.tags = new ArrayList<String>();
            }
            this.tags.add(tag);
        }
    }

    /**
     * @return the fields
     */
    public Map<String,Object> getFields() {
        return fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(String fields) {
        // Split string on : and ,
        //eg field1:value,field2:value
        if (null != fields) {
            this.fields = new LinkedHashMap<String, Object>();
                for(String keyValue : fields.split("\\s*,\\s*")) {
                String[] pairs = keyValue.split("\\s*:\\s*", 2);
                this.fields.put(pairs[0], pairs.length == 1 ? "" : pairs[1]);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    public void addField(String key, Object val) {
        if (val instanceof HashMap) {
            if (!((HashMap<String, Object>) val).isEmpty()) {
                fields.put(key, val);
            }
        } else if (null != val) {
            fields.put(key, val);
        }
    }

    public String toString() {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append(getTimestamp());
        strBuf.append(' ');
        strBuf.append(getNdc());
        strBuf.append(' ');
        strBuf.append(getMdc());
        strBuf.append(' ');
        strBuf.append(getExceptionInformation());
        strBuf.append(' ');
        strBuf.append(getMessage());
        strBuf.append(' ');
        strBuf.append(getLocationInformation());
        strBuf.append(' ');
        strBuf.append(getLevel());
        strBuf.append(' ');
        strBuf.append(getLoggerName());
        strBuf.append(' ');
        strBuf.append(getThreadName());
        strBuf.append(' ');
        strBuf.append(getTags());
        strBuf.append(' ');
        strBuf.append(getFields());
        return strBuf.toString();
    }

}