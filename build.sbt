name := "MinesweeperActorWeb"

version := "1.0"

lazy val `minesweeperactorweb` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
    jdbc ,
    cache ,
    ws   ,
    specs2 % Test ,
    "org.ektorp" % "org.ektorp" % "1.3.0"
)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"  