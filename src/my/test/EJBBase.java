package my.test;

import hk.gov.lad.rm.common.utils.CommonPropertyNames;
import hk.gov.lad.rm.common.utils.PropertiesUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class EJBBase {

	private static final String LOAD_PROPERTIES_MUTEX = new String("LOAD_PROPERTIES_MUTEX");
	private static String m_IPAddress;
	private static volatile Map<String, Properties> m_propertiesMap = new ConcurrentHashMap<String, Properties>();
	private Map<String, Log> m_networkLogs = new HashMap<String, Log>();

	private String m_commonPropertiesFilename = "/config/common_app.properties";

	private String m_appPropertiesFilename = null;

	static {
		try {
			m_IPAddress = InetAddress.getLocalHost().getHostName();
		} catch (Exception e) {
			m_IPAddress = "unknown host";
		}
	}

	/**
	* Get the filename containing the common runtime configurable properties.
	*
	* @return the filename containing the common runtime configurable properties.
	*/
	protected String getCommonPropertiesFilename() {
		return m_commonPropertiesFilename;
	}

	/**
	* Get the filename containing the component specific runtime configurable properties.
	*
	* @return the filename containing the component specific runtime configurable properties.
	*/
	protected String getAppPropertiesFilename() {
		return m_appPropertiesFilename;
	}

	/**
	* Get the runtime configurable properties.
	*
	* @return the runtime configurable properties.
	*/
	protected Properties getRuntimeProperties() throws IOException {
		String commonPropertiesFilename = getCommonPropertiesFilename();
		if (commonPropertiesFilename == null) {
			commonPropertiesFilename = "";
		}

		String appPropertiesFilename = getAppPropertiesFilename();
		if (appPropertiesFilename == null) {
			appPropertiesFilename = "";
		}

		String key = "[" + commonPropertiesFilename + "] [" + appPropertiesFilename + "]";

		Properties properties = m_propertiesMap.get(key);
		if (properties == null) {
			synchronized (LOAD_PROPERTIES_MUTEX) {
				properties = m_propertiesMap.get(key);
				if (properties == null) {
					if (commonPropertiesFilename.equals("")) {
						if (appPropertiesFilename.equals("")) {
							properties = new Properties();
						} else {
							properties = PropertiesUtils
									.loadPropertiesFile(new CommonPropertyNames().PROPERTIES_FILEPATH
											+ appPropertiesFilename);
						}
					} else if (appPropertiesFilename.equals("")) {
						properties = PropertiesUtils.loadPropertiesFile(new CommonPropertyNames().PROPERTIES_FILEPATH
								+ commonPropertiesFilename);
					} else {
						properties = PropertiesUtils.loadPropertiesFile(new CommonPropertyNames().PROPERTIES_FILEPATH
								+ commonPropertiesFilename);
						properties = PropertiesUtils.combineProperties(properties, PropertiesUtils
								.loadPropertiesFile(new CommonPropertyNames().PROPERTIES_FILEPATH
										+ appPropertiesFilename));
					}

					m_propertiesMap.put(key, properties);
				}
			}
		}

		Properties props = new Properties();
		props.putAll(properties);
		return props;
	}

	/**
	* Log a message with FATAL level.
	*
	*	@param message the message to be logged.
	*/
	protected void logFatal(String message) {
		Log log = getLog();

		if (log.isFatalEnabled()) {
			log.fatal(getLogPattern(message));
		}
	}

	/**
	* Log a message with FATAL level including the stack trace of second parameter.
	*
	*	@param message the message to be logged.
	*	@param t the exception, including the stack trace, to be logged.
	*/
	protected void logFatal(String message, Throwable t) {
		Log log = getLog();

		if (log.isFatalEnabled()) {
			log.fatal(getLogPattern(message), t);
		}
	}

	/**
	* Log a message with ERROR level.
	*
	*	@param message the message to be logged.
	*/
	protected void logError(String message) {
		Log log = getLog();

		if (log.isErrorEnabled()) {
			log.error(getLogPattern(message));
		}
	}

	/**
	* Log a message with ERROR level including the stack trace of second parameter.
	*
	*	@param message the message to be logged.
	*	@param t the exception, including the stack trace, to be logged.
	*/
	protected void logError(String message, Throwable t) {
		Log log = getLog();

		if (log.isErrorEnabled()) {
			log.error(getLogPattern(message), t);
		}
	}

	/**
	* Log a message with WARN level.
	*
	*	@param message the message to be logged.
	*/
	protected void logWarn(String message) {
		Log log = getLog();

		if (log.isWarnEnabled()) {
			log.warn(getLogPattern(message));
		}
	}

	/**
	* Log a message with WARN level including the stack trace of second parameter.
	*
	*	@param message the message to be logged.
	*	@param t the exception, including the stack trace, to be logged.
	*/
	protected void logWarn(String message, Throwable t) {
		Log log = getLog();

		if (log.isWarnEnabled()) {
			log.warn(getLogPattern(message), t);
		}
	}

	/**
	* Log a message with INFO level.
	*
	*	@param message the message to be logged.
	*/
	protected void logInfo(String message) {
		Log log = getLog();

		if (log.isInfoEnabled()) {
			log.info(getLogPattern(message));
		}
	}

	/**
	* Log a message with INFO level including the stack trace of second parameter.
	*
	*	@param message the message to be logged.
	*	@param t the exception, including the stack trace, to be logged.
	*/
	protected void logInfo(String message, Throwable t) {
		Log log = getLog();

		if (log.isInfoEnabled()) {
			log.info(getLogPattern(message), t);
		}
	}

	/**
	* Log a message with DEBUG level.
	*
	*	@param message the message to be logged.
	*/
	protected void logDebug(String message) {
		Log log = getLog();

		if (log.isDebugEnabled()) {
			log.debug(getLogPattern(message));
		}
	}

	/**
	* Log a message with DEBUG level including the stack trace of second parameter.
	*
	*	@param message the message to be logged.
	*	@param t the exception, including the stack trace, to be logged.
	*/
	protected void logDebug(String message, Throwable t) {
		Log log = getLog();

		if (log.isDebugEnabled()) {
			log.debug(getLogPattern(message), t);
		}
	}

	/**
	* Check whether message with FATAL level will be logged.
	*
	*	@return true if FATAL level message will be logged, false otherwise.
	*/
	protected boolean isFatalEnabled() {
		return getLog().isFatalEnabled();
	}

	/**
	* Check whether message with ERROR level will be logged.
	*
	*	@return true if ERROR level message will be logged, false otherwise.
	*/
	protected boolean isErrorEnabled() {
		return getLog().isErrorEnabled();
	}

	/**
	* Check whether message with WARN level will be logged.
	*
	*	@return true if WARN level message will be logged, false otherwise.
	*/
	protected boolean isWarnEnabled() {
		return getLog().isWarnEnabled();
	}

	/**
	* Check whether message with INFO level will be logged.
	*
	*	@return true if INFO level message will be logged, false otherwise.
	*/
	protected boolean isInfoEnabled() {
		return getLog().isInfoEnabled();
	}

	/**
	* Check whether message with DEBUG level will be logged.
	*
	*	@return true if DEBUG level message will be logged, false otherwise.
	*/
	protected boolean isDebugEnabled() {
		return getLog().isDebugEnabled();
	}

	//method for logging error that would be captured by network manager
	/**
	* Log a message with FATAL level to the centralized log repository.
	*
	*	@param message the message to be logged.
	*/
	protected void logNetworkFatal(String message) {
		try {
			Log log = getNetworkLog();

			if (log.isFatalEnabled()) {
				log.fatal(getLogPattern(message));
			}
		} catch (Exception e) {
			logError("Failed to log fatal message to network manager.", e);
		}
	}

	/**
	* Log a message with ERROR level to the centralized log repository.
	*
	*	@param message the message to be logged.
	*/
	protected void logNetworkError(String message) {
		try {
			Log log = getNetworkLog();

			if (log.isErrorEnabled()) {
				log.error(getLogPattern(message));
			}
		} catch (Exception e) {
			logError("Failed to log error message to network manager.", e);
		}
	}

	/**
	* Check whether message with FATAL level will be logged to the
	*	centralized log repository.
	*
	*	@return true if FATAL level message will be logged, false otherwise.
	*/
	protected boolean isNetworkLogFatalEnabled() {
		try {
			return getNetworkLog().isFatalEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	/**
	* Check whether message with ERROR level will be logged to the
	*	centralized log repository.
	*
	*	@return true if ERROR level message will be logged, false otherwise.
	*/
	protected boolean isNetworkLogErrorEnabled() {
		try {
			return getNetworkLog().isErrorEnabled();
		} catch (Exception e) {
			return false;
		}
	}

	private String getLogPattern(String message) {
		return "[" + getClass().getName() + "] at [" + m_IPAddress + "] " + message;
	}

	private Log getLog() {
		//Log is always freshly retrieved rather than stored in a member
		//variable because Log is not transient, any stateful ejb keeping
		//Log will not passivate successfully.
		Log log = null;
		try {
			log = LogFactory.getLog(getClass());
		} catch (Exception e) {
			log = null;
		}
		return log;
	}

	private Log getNetworkLog() throws Exception {
		try {
			String name = getClass().getName();

			Log log = m_networkLogs.get(name);
			if (log == null) {
				log = LogFactory.getLog("network." + name);
				m_networkLogs.put(name, log);
			}

			return log;
		} catch (Exception e) {
			logError("Failed to initial the logger for logging message monitored by network manager.", e);
			throw e;
		}
	}
}
