package com.gkhotyan.simpleserver.configuration;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class YamlAppConfigReader implements AppConfigReader {

  public AppConfig readConfig(File file) throws IOException {
    final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
    return mapper.readValue(file, AppConfig.class);
  }
}