### RFID Time Stamping System
This document contains detailed information on the design and deployment of a system for time
stamping RFID tags during a mass shooter simulation by Tarleton State University's Nursing
Department. 

#### Architecture
The systemm shall be comprised of 3 components an android mobile application that runs on Alien
ALR H450 handheld RFID readers and a webservice backend and frontend running on an AWS instance. 
All three components will interact using the client-server architecture.
The following diagram is an overview of the suggested architecture.


#### Functional Requirements
1. Android based handheld
    * The application will have two modes:
        * Add - Writes information to the tag and updates its start time
        * Remove - Reads information to the tag and updates its end time
    * It will generate a time stamp and unique Id for each tag in Add mode
    * It will read and remove the time stamp and unique Id from an RFID tag.
    * It will be able to lock and unlock access to RFID tags.
    * The application must be able to store tag information for later use if there is not access to
      the server.
2. Web Service Backend
    * 
    
3. Web Service Frontend
    *