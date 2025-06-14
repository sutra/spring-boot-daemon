package org.oxerr.spring.boot.daemon;

import java.util.logging.Logger;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * A daemon implementation based on {@link SpringApplication}.
 */
public abstract class SpringApplicationDaemon implements Daemon {

	private final Logger log = Logger.getLogger(SpringApplicationDaemon.class.getName());

	private SpringApplication application;
	private ConfigurableApplicationContext context;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(DaemonContext context) throws DaemonInitException, Exception {
		log.info("Initializing...");
		this.application = this.initSpringApplication(context);
		log.info("Initialized.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start() throws Exception {
		log.info("Starting...");
		this.context = application.run();
		log.info("Started.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() throws Exception {
		log.info("Stopping...");
		if (context != null && context.isActive()) {
			context.close();
		}
		log.info("Stopped.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void destroy() {
		log.info("Destroying...");
		log.info("Destroyed.");
	}

	/**
	 * Initialize the {@link SpringApplication}.
	 *
	 * @param context the daemon context.
	 * @return the {@link SpringApplication}.
	 */
	protected abstract SpringApplication initSpringApplication(DaemonContext context);

}
