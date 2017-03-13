// SBT Reference Manual ........... see: http://www.scala-sbt.org/0.13/docs/index.html
// SBT Best Practices ............. see: http://www.scala-sbt.org/0.13/docs/Best-Practices.html
// Community Plugins .............. see: http://www.scala-sbt.org/0.13/docs/Community-Plugins.html
// Resolvers ...................... see: http://www.scala-sbt.org/0.13/docs/Resolvers.html

// sbt-dependency-graph - sbt plugin to create a dependency graph for your project
//
// task: dependencyTree                   Shows an ASCII tree representation of the project's dependencies
// task: dependencyBrowseGraph            Opens a browser window with a visualization of the dependency graph (courtesy of graphlib-dot + dagre-d3).
// task: dependencyGraph                * Shows an ASCII graph of the project's dependencies on the sbt console
// task: dependencyList                   Shows a flat list of all transitive dependencies on the sbt console (sorted by organization and name)
// task: dependencyLicenseInfo          * Show dependencies grouped by declared license
// task: dependencyStats                * Shows a table with each module a row with (transitive) Jar sizes and number of dependencies
// task: dependencyGraphMl                Generates a .graphml file with the project's dependencies to target/dependencies-<config>.graphml. Use e.g. yEd to format the graph to your needs.
// task: dependencyDot                    Generates a .dot file with the project's dependencies to target/dependencies-<config>.dot. Use graphviz to render it to your preferred graphic format.
// task: whatDependsOn                  * <organization> <module> <revision>: Find out what depends on an artifact. Shows a reverse dependency tree for the selected module.
//
// see: https://github.com/jrudolph/sbt-dependency-graph

addSbtPlugin("net.virtual-void" % "sbt-dependency-graph" % "0.8.2")

// sbt-updates - SBT plugin that can check maven repositories for dependency updates
//
// task: dependencyUpdates              * Shows a list of project dependencies that can be updated.
// task: dependencyUpdatesReport          Writes a list of project dependencies that can be updated to a file.
//
// see: https://github.com/rtimush/sbt-updates

addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.0")

// scalastyle-sbt-plugin - SBT 0.13 plugin support for Scalastyle
//
// task: scalastyle                     * Run scalastyle on your code.
// task: scalastyleGenerateConfig         Generate default configuration files for scalastyle.
//
// see: https://github.com/scalastyle/scalastyle-sbt-plugin

addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "0.8.0")

// sbt-scoverage - plugin for SBT that integrates the scoverage code coverage library
//
// task: coverage
// task: coverageAggregate
// task: coverageReport
//
// see: https://github.com/scoverage/sbt-scoverage

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.0")
