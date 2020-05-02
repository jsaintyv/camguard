# Camguard
Camguard is small project allow me to collect images from surveillance camera.

# Goal
A simple project that i can control because i don't trust camera device.
So , i isolate surveillance camera in network area which have no access Internet.
The server run on raspberry pi.

# Architecture.
project is in two parts:
- Server in java spring-boot 
- Frontend in Javascript/Vuejs

# To build
Build chain can be executed by maven.

# To run
From server directory , you can run with :

```
java  -jar target/server-0.0.1-SNAPSHOT.jar configuration.json
```

# Configuration
Configuration is simple JSON. (Actually , i don't need database)

```
{
	"login": "admin",
	"password": "secret",
	"imagedir": "images",
	"webcams": [
		{
			"name": "webcam1",
			"url": "http://172.0.0.10/tmpfs/auto.jpg",
			"login": "admin",
			"password": "admin",
			"minDiff": 1500000,
			"ignoreLeftUpperX": 320,
			"ignoreLeftUpperY": 0,
			"ignoreRightBottomX": 640,
			"ignoreRightBottomY": 10,
			"purgeOlderInSeconds": 259200 		
		}
	]	
}
```

