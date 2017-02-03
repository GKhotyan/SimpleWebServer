package com.gkhotyan.simpleserver;

import static java.lang.String.format;

import java.io.IOException;
import java.net.URI;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.HttpServerFilter;
import org.glassfish.grizzly.http.server.HttpServerProbe;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.gkhotyan.simpleserver.configuration.AppConfig;
import com.gkhotyan.simpleserver.resources.HelloEndpointImpl;

public class SimpleServer {

  private static Logger logger = LogManager.getLogger();

  public  void startServer(AppConfig appConfig) throws IOException, InterruptedException {
    logger.info("Starting Weather App local testing server: " + appConfig.getBaseurl());
    logger.info("Not for production use");

    final ResourceConfig resourceConfig = new ResourceConfig();
    resourceConfig.register(HelloEndpointImpl.class);
    final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(appConfig.getBaseurl()), resourceConfig, false);

    Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
      @Override
      public void run() {
        server.shutdownNow();
      }
    }));

    HttpServerProbe probe = new HttpServerProbe.Adapter() {
      public void onRequestReceiveEvent(HttpServerFilter filter, Connection connection, Request request) {
        System.out.println(request.getRequestURI());
      }
    };

    server.getServerConfiguration().getMonitoringConfig().getWebServerConfig().addProbes(probe);
    server.start();
    logger.info(format("Weather Server started.\n url=%s\n", appConfig.getBaseurl()));

    Thread.currentThread().join();
  }

  public void stopServer(AppConfig appConfig){

    logger.info("Stoping Weather App local testing server: " + appConfig.getBaseurl());
    logger.info("Not for production use");
    final ResourceConfig resourceConfig = new ResourceConfig();
    resourceConfig.register(HelloEndpointImpl.class);
    final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(appConfig.getBaseurl()), resourceConfig, false);
    server.shutdownNow();
  }
}
