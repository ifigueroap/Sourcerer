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
package edu.uci.ics.sourcerer.tools.java.utilization.model.cluster;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import edu.uci.ics.sourcerer.tools.java.utilization.model.jar.Jar;
import edu.uci.ics.sourcerer.tools.java.utilization.model.jar.VersionedFqnNode;
import edu.uci.ics.sourcerer.util.CachedReference;

/**
 * @author Joel Ossher (jossher@uci.edu)
 */
public class ClusterMatcher {
  private final ClusterCollection clusters;
  
  private CachedReference<Map<String, Cluster>> fqnStringsToClusters = new CachedReference<Map<String,Cluster>>() {
    @Override
    protected Map<String, Cluster> create() {
      Map<String, Cluster> map = new HashMap<>();
      for (Cluster cluster : clusters) {
        for (VersionedFqnNode fqn : cluster.getCoreFqns()) {
          map.put(fqn.getFqn(), cluster);
        }
        for (VersionedFqnNode fqn : cluster.getExtraFqns()) {
          map.put(fqn.getFqn(), cluster);
        }
      }
      return map;
    }};
  private CachedReference<Map<VersionedFqnNode, Cluster>> fqnsToClusters = new CachedReference<Map<VersionedFqnNode,Cluster>>() {
    @Override
    protected Map<VersionedFqnNode, Cluster> create() {
      Map<VersionedFqnNode, Cluster> map = new HashMap<>();
      for (Cluster cluster : clusters) {
        for (VersionedFqnNode fqn : cluster.getCoreFqns()) {
          map.put(fqn, cluster);
        }
        for (VersionedFqnNode fqn : cluster.getExtraFqns()) {
          map.put(fqn, cluster);
        }
      }
      return map;
    }};
  private CachedReference<Multimap<Jar, Cluster>> jarsToClusters = new CachedReference<Multimap<Jar,Cluster>>() {
    @Override
    protected Multimap<Jar, Cluster> create() {
      Multimap<Jar, Cluster> map = HashMultimap.create();
      for (Cluster cluster : clusters) {
        for (Jar jar : cluster.getJars()) {
          map.put(jar, cluster);
        }
      }
      return map;
    }};
  
  private ClusterMatcher(ClusterCollection clusters) {
    this.clusters = clusters;
  }
  
  static ClusterMatcher create(ClusterCollection clusters) {
    return new ClusterMatcher(clusters);
  }
  
  public Collection<Cluster> getClusters(Jar jar) {
   return jarsToClusters.get().get(jar);
  }
  
  public Cluster getCluster(VersionedFqnNode fqn) {
    return fqnsToClusters.get().get(fqn);
  }
  
  public Cluster getCluster(String fqn) {
    return fqnStringsToClusters.get().get(fqn);
  }
}
