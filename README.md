# FakeNews

Fake news identification. Users and their posts credibility on Twitter

# Members 

* Dascalu Ana 
* Glodeanu Irina
* Insuratelu Madalina
* Macariu Camelia
* Rusu Gabriel


# Coordinators
* Conf. dr. Iftene Adrian
* Prof.dr. Tiplea Ferucio Laurentiu

# Documentation:
https://docs.google.com/document/d/1XaCVFFKSZG8TETB7yAAk0Lfo6ztjg3fr0YzwNlv5IC0/edit?usp=sharing

# State-of-art
https://github.com/IrinaGlodeanu/FakeNews/blob/master/Documents/State-of-art.pdf

# Trello board:
https://trello.com/b/568OmHmR/fake-news-taip-project


# Prerequisites

#### We'll use <b>Redis</b> as a cache solution. For this we'll have to do some steps:

How to install Redis:

1) Enable the "Windows Subsystem for Linux (beta)" feature
https://www.wikihow.com/Enable-the-Windows-Subsystem-for-Linux

2) Install and Test Redis
Open the "Bash on Ubuntu on Windows" command line (search that in Windows Search), then execute the following commands:
    
    > sudo apt-get update

    > sudo apt-get upgrade

    > sudo apt-get install redis-server

    > redis-cli -v

  If the last command prints the redis-cli vrsion, then you have your envionment ready!!

3) Use the Redis server:
Restart the Redis server to make sure it is running:
    > sudo service redis-server restart

4) When you've finished with the development for the day, just close the Redis server(take note that all your data form the Redis cache will be lost!):
    > sudo service redis-server stop


#### We'll use <b>Swagger </b> as a API documentation tool for our REST calls:
    
  After you execute the Back-end application, you have access to the Swagger endpoint:   
    http://localhost:8080/swagger-ui.html
    <br></br>
    Here we'll have the endpoints to the exposed functionalities by our app.
   
   
#### The app will need the environment for running Java applications:
 
 * Java : download jdk : https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
 <br></br>
 To test the java version (from cmd): 
    > java -v
 * Maven : downlaod Maven : 
     * Downlaod "Binary zip archive" : https://maven.apache.org/download.cgi
     * Extract the folder into a location of your choice
     * Add the location path to the <b>/bin</b> folder form the previously extracted file to the Path envirnomnet variables
 <br></br>
To test the maven version (from cmd):
    > mvn -v
