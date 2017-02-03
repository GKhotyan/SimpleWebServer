import static java.lang.String.format;
import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.HttpServerFilter;
import org.glassfish.grizzly.http.server.HttpServerProbe;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Response;

import com.gkhotyan.simpleserver.resources.HelloEndpointImpl;

public class SimpleWebServiceTest {
  private WebTarget query;
  private static final String     BASE_URL = "http://localhost:8082";
  private static final Logger     log      = LogManager.getLogger(SimpleWebServiceTest.class);
  private static       HttpServer server   = null;

  public SimpleWebServiceTest(){
    Client client = ClientBuilder.newClient();
    query = client.target(BASE_URL + "/query");
  }

  @BeforeClass
  public static void  setUp() throws Exception {
    log.info("Starting Simple Web server: " + BASE_URL);

    final ResourceConfig resourceConfig = new ResourceConfig();
    resourceConfig.register(HelloEndpointImpl.class);
    server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URL), resourceConfig, false);


    HttpServerProbe probe = new HttpServerProbe.Adapter() {
      public void onRequestReceiveEvent(HttpServerFilter filter, Connection connection, Request request) {
        System.out.println(request.getRequestURI());
      }
    };

    server.getServerConfiguration().getMonitoringConfig().getWebServerConfig().addProbes(probe);
    server.start();
    log.info(format("Simple Web Server started.\n url=%s\n", BASE_URL));
  }

  @AfterClass
  public static void tearDown() throws Exception {
    log.info("Shuting down the server: " + BASE_URL);
    server.shutdownNow();
  }

  @Test
  public void testAddAirport() {

    WebTarget target = query.path("/hello").queryParam("name", "mon_cheri");
    Invocation.Builder invocationBuilder = target.request();
    System.out.println(target.getUri());
    Response response = invocationBuilder.get();
    String result = response.readEntity(String.class);
    log.info(target+": " + result + "\n");
    assertEquals("Hello, mon_cheri", result);

  }

}
