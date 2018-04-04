name := """scala-play-react-seed"""

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayJava).settings(
  watchSources ++= (baseDirectory.value / "public/ui" ** "*").get
)

resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.2"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "org.postgresql" % "postgresql" % "9.4-1206-jdbc42"
libraryDependencies += "com.typesafe.slick" %% "slick" % "3.2.3"
libraryDependencies += "com.typesafe.slick" %% "slick-hikaricp" % "3.2.3"
libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.6.4"
libraryDependencies += "com.jason-goodwin" %% "authentikat-jwt" % "0.4.5"
libraryDependencies += "org.mindrot" % "jbcrypt" % "0.4"
libraryDependencies += "be.objectify" %% "deadbolt-scala" % "2.6.0"
libraryDependencies ++= Seq(evolutions, jdbc)
// libraryDependencies += evolutions

