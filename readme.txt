For QA
==============
To build this project, open CMD, then
cd path/to/project
gradlew war

The output is in /libs/project.war

Installation:
1. Create database feedmgr, execute the sql in /src/scripts 
2. Deploy the war to any web server (or extract)
3. Configure the WEB-INF/config/datasource-config.xml to point to the db feedmgr
4. Prepare the CDN storage for feeds with http access
5. Open the web app in browser, configure the settings, sources, project
6. Verify the feeds are generated as defined 

For developer
==============
It's a gradle project, and also can be run as eclipse wtp.
To pull down the dependency, open CMD, then:
cd path/to/project
gradlew eclipse

Then refresh your feed-manager project in eclipse, it should be able to build now.

You can also download the gradle plugin for eclipse and run the tasks from Gradle Tasks view:
https://projects.eclipse.org/projects/tools.buildship/downloads 
