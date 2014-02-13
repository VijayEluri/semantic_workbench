package com.monead.semantic.workbench.sparqlservice;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Observable;

import org.apache.log4j.Logger;

import com.hp.hpl.jena.ontology.OntModel;

/**
 * Creates a socket for accepting and responding to SPARQL queries. This is a
 * Runnable and should be run on an independent thread.
 * 
 * A model MUST be set before running.
 * 
 * e.g.
 * 
 * SparqlServer.getInstance().setModel(SOME_MODEL);
 * 
 * new Thread(SparqlServer.getInstance()).start()
 * 
 * @author David Read
 * 
 */
public class SparqlServer extends Observable implements Runnable {
  /**
   * Logger Instance
   */
  private static Logger LOGGER = Logger.getLogger(SparqlServer.class);

  /**
   * Singleton instance
   */
  private static SparqlServer instance;

  /**
   * The ontology model to be used when running SPARQL queries
   */
  private OntModel model;

  /**
   * The server socket being monitored for requests
   */
  private ServerSocket serverSocket;

  /**
   * The number of connections processed by this server
   */
  private long connectionsHandled;

  /**
   * The port used to accept requests
   */
  private int listenerPort = 5162; // Default is 5162

  /**
   * The number of seconds to allow a query to run before killing it
   */
  private int maxRuntimeSeconds = 60; // Default is 60

  /**
   * Constructor - no operation
   */
  private SparqlServer() {

  }

  /**
   * Retrieve the instance
   * 
   * @return The Singleton instance
   */
  public static synchronized SparqlServer getInstance() {
    if (instance == null) {
      instance = new SparqlServer();
    }

    return instance;
  }

  /**
   * Runs the server. A model MUST be set on the instance before running it.
   * 
   * @throws IllegalStateException
   *           If there is no model configured for the server
   */
  public void run() {
    if (model == null) {
      throw new IllegalStateException(
          "No model has been configured for the server");
    }

    connectionsHandled = 0;

    try {
      serverSocket = new ServerSocket(getListenerPort());
    } catch (Throwable throwable) {
      LOGGER.error("Unable to setup SPARQL server endpoint", throwable);
      throw new IllegalStateException("Unable to setup SPARQL server endpoint",
          throwable);
    }

    while (true) {
      try {
        ++connectionsHandled;
        processRequest(serverSocket.accept());
      } catch (SocketException se) {
        LOGGER.info("SparqlServer shutdown", se);
        break;
      } catch (Throwable throwable) {
        LOGGER.error("Error with SparqlServer", throwable);
      }
    }
  }

  /**
   * Handles an incoming request
   * 
   * @param connection
   *          The connection for the request
   */
  private synchronized void processRequest(Socket connection) {
    Thread thread = new Thread(new SparqlRunner(connection, model,
        maxRuntimeSeconds));
    thread.setName("SPARQL Request Handler-" + connectionsHandled);
    thread.start();
    setChanged();
    notifyObservers();
  }

  /**
   * Sets the ontology model to be used for future requests
   * 
   * @param model
   *          The ontology model
   */
  public synchronized void setModel(OntModel model) {
    this.model = model;
  }

  /**
   * Stop the server. This also clears the model.
   */
  public void stop() {
    try {
      if (serverSocket != null) {
        serverSocket.close();
        serverSocket = null;
        setModel(null);
      }
    } catch (Throwable throwable) {
      LOGGER.error("Error closing SparqlServer", throwable);
    }
  }

  /**
   * Checks to see if the server is active.
   * 
   * @return True if the server is active (listening for requests)
   */
  public boolean isActive() {
    return serverSocket != null;
  }

  /**
   * Set the port to be monitored for requests. This may only be called if the
   * server is stopped.
   * 
   * @see #stop()
   * @see #isActive()
   * 
   * @param port
   *          The port to be monitored when the server is started
   */
  public void setListenerPort(int port) {
    if (!isActive()) {
      listenerPort = port;
    } else {
      throw new IllegalStateException(
          "The Sparql Server must be shutdown in order to change the listening port");
    }
  }

  /**
   * Get the number of connection handled since the last time the server was
   * started.
   * 
   * @return The number of connections handled
   */
  public long getConnectionsHandled() {
    return connectionsHandled;
  }

  /**
   * Get the port that this server listens on
   * 
   * @return The port this server listens on
   */
  public int getListenerPort() {
    return listenerPort;
  }

  /**
   * Get the maximum runtime (in seconds) for processing a query before killing
   * it
   * 
   * @return The maximum runtime for a query
   */
  public int getMaxRuntimeSeconds() {
    return maxRuntimeSeconds;
  }

  /**
   * Set the maximum runtime (in seconds) for processing a query before killing
   * it
   * 
   * @param maxRuntimeSeconds
   *          The maximum runtime for a query
   */
  public void setMaxRuntimeSeconds(int maxRuntimeSeconds) {
    this.maxRuntimeSeconds = maxRuntimeSeconds;
  }
}