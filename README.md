# test-project
Test project - simple search engine using Java


Application consist of two parts: server side app and cli app.

**Server app startup**
 
Server app is a spring boot application that is running on port 4242 by default.

You may use gradle wrapper task to start app 

_{project directory}/gradlew :server:bootRun_

or execute build and run spring boot app jar directly.


**Console app startup**

First you'll need to build app using shadowJar gradle command:

_{project directory}/gradlew :cli:shadowJar_

once you build it, you may run app using following command:

_java -jar {project directory}/build/libs/cli-all.jar -help_

Help command will list all possible options and params
 
 
 

