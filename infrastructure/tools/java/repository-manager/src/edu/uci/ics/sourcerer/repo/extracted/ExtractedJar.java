/* 
 * Sourcerer: an infrastructure for large-scale source code analysis.
 * Copyright (C) by contributors. See CONTRIBUTORS.txt for full list.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package edu.uci.ics.sourcerer.repo.extracted;

import java.io.File;

import edu.uci.ics.sourcerer.repo.general.AbstractBinaryProperties;
import edu.uci.ics.sourcerer.repo.general.JarProperties;

/**
 * @author Joel Ossher (jossher@uci.edu)
 */
public class ExtractedJar extends ExtractedBinary {
  private JarProperties properties;
  
  public ExtractedJar(File content) {
    super(content);
    properties = JarProperties.load(getPropertiesFile());
  }
  
  public ExtractedJar(File content, File propFile) {
    super(content);
    File exPropFile = getPropertiesFile();
    if (exPropFile.exists()) {
      properties = JarProperties.load(exPropFile);
    } else {
      properties = JarProperties.load(propFile);
    }
  }

  public void reportSuccessfulExtraction(int fromBinary, int binaryExceptions, int fromSource, int sourceExceptions) {
    properties.reportSuccessfulExtraction(getPropertiesFile(), fromBinary, binaryExceptions, fromSource, sourceExceptions);
  }
  
  public void reportMissingTypeExtraction() {
    properties.reportMissingTypeExtraction(getPropertiesFile());
  }
  
  protected AbstractBinaryProperties getBinaryProperties() {
    return properties;
  }
  
  public String getGroup() {
    return properties.getGroup();
  }
  
  public String getVersion() {
    return properties.getVersion();
  }
  
  public String getHash() {
    return properties.getHash();
  }
}
