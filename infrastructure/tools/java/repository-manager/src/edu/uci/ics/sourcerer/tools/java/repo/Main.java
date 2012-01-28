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
package edu.uci.ics.sourcerer.tools.java.repo;

import edu.uci.ics.sourcerer.tools.java.repo.jars.JarIdentifier;
import edu.uci.ics.sourcerer.tools.java.repo.maven.MavenImporter;
import edu.uci.ics.sourcerer.tools.java.repo.model.JavaRepositoryFactory;
import edu.uci.ics.sourcerer.tools.java.repo.stats.RepositoryStatisticsCalculator;
import edu.uci.ics.sourcerer.util.io.arguments.Arguments;
import edu.uci.ics.sourcerer.util.io.arguments.Command;

/**
 * @author Joel Ossher (jossher@uci.edu)
 */
public class Main {
  public static final Command AGGREGATE_JAR_FILES =
    new Command("aggregate-jar-files", "Collects all the project jar files into the jars directory.") {
      protected void action() {
        JavaRepositoryFactory.INSTANCE.loadModifiableJavaRepository(JavaRepositoryFactory.INPUT_REPO).aggregateJarFiles();
      }
    }.setProperties(JavaRepositoryFactory.INPUT_REPO);
    
  public static final Command IMPORT_LATEST_MAVEN_TO_REPOSITORY =
    new Command("import-latest-maven-to-repo", "Imports the latest copy of the Maven2 central repository into the Sourcerer repository.") {
      protected void action() {
        MavenImporter.importLatestMavenToRepository();
      }
    }.setProperties(Arguments.INPUT, JavaRepositoryFactory.OUTPUT_REPO);
  
  public static final Command CALCULATE_REPOSITORY_STATISTICS =
    new Command("calculate-repo-stats", "Calculates a variety of statistics about the repository.") {
      protected void action() {
        RepositoryStatisticsCalculator.calculateRepositoryStatistics();
      }
    }.setProperties(JavaRepositoryFactory.INPUT_REPO);
    
  public static final Command TEST_FOR_MEMORY_LEAKS =
    new Command("test-for-memory-leaks", "Tests for memory leaks.") {
      protected void action() {
        RepositoryStatisticsCalculator.testForMemoryLeaks();
      }
    }.setProperties(JavaRepositoryFactory.INPUT_REPO);
    
  public static final Command IDENTIFY_JAR_FILES =
    new Command("identify-jars", "Identify jar files containing a specific FQN.") {
      protected void action() {
        JarIdentifier.identifyJarsContainingFqn();
      }
    }.setProperties(JavaRepositoryFactory.INPUT_REPO, JarIdentifier.CONTAINING_FQN, JarIdentifier.IDENTIFIED_JARS_FILE.asOutput());
    
  public static void main(String[] args) {
    Command.execute(args, Main.class);
  }
}
