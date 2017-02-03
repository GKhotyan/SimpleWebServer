package com.gkhotyan.simpleserver.configuration;

import java.io.File;
import java.io.IOException;

public interface AppConfigReader {
  AppConfig readConfig(File file) throws IOException;
}
