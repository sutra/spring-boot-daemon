package org.oxerr.spring.boot.daemon;

import java.util.logging.Logger;

import org.apache.commons.daemon.Daemon;
import org.apache.commons.daemon.DaemonContext;
import org.apache.commons.daemon.DaemonInitException;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public abstract class SpringApplicationDaemon implements Daemon {

	private final Logger log = Logger.getLogger(SpringApplicationDaemon.class.getName());

	private SpringApplication application;
	private ConfigurableApplicationContext context;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init(DaemonContext context) throws DaemonInitException,
			Exception {
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
		context = application.run();
		log.info("Started.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop() throws Exception {
		log.info("Stopping...");
		context.stop();
		log.info("Stopped.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void destroy() {
		log.info("Destroying...");
		context.close();
		log.info("Destroyed.");
	}

	protected abstract SpringApplication initSpringApplication(DaemonContext context);

}
