package com.gkhotyan.simpleserver;

import java.io.File;

import com.gkhotyan.simpleserver.configuration.AppConfig;
import com.gkhotyan.simpleserver.configuration.AppConfigReader;
import com.gkhotyan.simpleserver.configuration.YamlAppConfigReader;

public class App {
  public static void main(String[] args) {
    SimpleServer server = new SimpleServer();
    AppConfigReader configReader = new YamlAppConfigReader();

    try {
      ClassLoader classLoader = App.class.getClassLoader();
      File file = new File(classLoader.getResource("config.yaml").getFile());
      AppConfig config = configReader.readConfig(file);
      server.startServer(config);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
