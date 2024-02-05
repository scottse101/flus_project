# flus

flus is a Maven-based JavaFX application developed as part of an assignment that was given to the team through the subject of System Development, at the Department of Computer Technology and Informatics at the Norwegian University of Science and Technology, in the spring of 2023.

The project is designed with the purpose of helping everyday people manage their budgeting and accounting needs. This application is built using JavaFX for the interface, and Maven for handling dependencies and the build process.

**Team:** Ramtin Samavat, Scott Emonanekkul, Markus Dalbakk, Henrik Nilsen and Stian Lyng Stræte.

## Table of Contents

1. [Prerequisites](#Prerequisites)
2. [Installation](#Installation)
3. [Usage](#Usage)
4. [WikiLinks](#wikilinks)

## Wikilinks

- [Requirements](https://gitlab.stud.idi.ntnu.no/ramtinfs/idatt1002/-/wikis/Main-Page/Requirements)
    - [Domain Model](https://gitlab.stud.idi.ntnu.no/ramtinfs/idatt1002/-/wikis/Main-Page/Requirements/Domain-Model)
    - [Sequence Diagrams](https://gitlab.stud.idi.ntnu.no/ramtinfs/idatt1002/-/wikis/Main-Page/Requirements/Sequence-diagram)
    - [Universal Design](https://gitlab.stud.idi.ntnu.no/ramtinfs/idatt1002/-/wikis/Main-Page/Requirements/Universal-Design)
    - [Usability Tests](https://gitlab.stud.idi.ntnu.no/ramtinfs/idatt1002/-/wikis/Main-Page/Requirements/Usability-tests)
    - [Use Case Diagrams](https://gitlab.stud.idi.ntnu.no/ramtinfs/idatt1002/-/wikis/Main-Page/Requirements/Use-Case-diagrams)
        - [Use Case 1 - Account](https://gitlab.stud.idi.ntnu.no/ramtinfs/idatt1002/-/wikis/Main-Page/Requirements/Use-Case-diagrams/Use-Case-1-Account)
        - [Use Case 2 - Budget](https://gitlab.stud.idi.ntnu.no/ramtinfs/idatt1002/-/wikis/Main-Page/Requirements/Use-Case-diagrams/Use-Case-2-Budgeting)
        - [Use Case 3 - Mood](https://gitlab.stud.idi.ntnu.no/ramtinfs/idatt1002/-/wikis/Main-Page/Requirements/Use-Case-diagrams/Use-Case-3-Mood)
    - [Wireframes](https://gitlab.stud.idi.ntnu.no/ramtinfs/idatt1002/-/wikis/Main-Page/Requirements/Wireframes)
- [Software Vision Document](https://gitlab.stud.idi.ntnu.no/ramtinfs/idatt1002/-/wikis/Main-Page/Software-Vision-document)
- [System](https://gitlab.stud.idi.ntnu.no/ramtinfs/idatt1002/-/wikis/Main-Page/System)
    - [Class Diagram](https://gitlab.stud.idi.ntnu.no/ramtinfs/idatt1002/-/wikis/Main-Page/System/Class-diagram)
    - [Installation Manual](https://gitlab.stud.idi.ntnu.no/ramtinfs/idatt1002/-/wikis/Main-Page/System/Installation-manual)
    - [Maven](https://gitlab.stud.idi.ntnu.no/ramtinfs/idatt1002/-/wikis/Main-Page/System/Maven)
    - [Persistance](https://gitlab.stud.idi.ntnu.no/ramtinfs/idatt1002/-/wikis/Main-Page/System/Persistence)
    - [Design Patterns](https://gitlab.stud.idi.ntnu.no/ramtinfs/idatt1002/-/wikis/Main-Page/System/Design-Patterns)
    - [Object Oriented Design](https://gitlab.stud.idi.ntnu.no/ramtinfs/idatt1002/-/wikis/Main-Page/System/Object-Oriented-Design-Principles)
    - [Citation Sources](https://gitlab.stud.idi.ntnu.no/ramtinfs/idatt1002/-/wikis/Main-Page/System/Citation-Sources)
    - [Project Structure](https://gitlab.stud.idi.ntnu.no/ramtinfs/idatt1002/-/wikis/Main-Page/System/Project-Structure)
    - [Source Code](https://gitlab.stud.idi.ntnu.no/ramtinfs/idatt1002/-/tree/master)
    - [Testing](https://gitlab.stud.idi.ntnu.no/ramtinfs/idatt1002/-/wikis/Main-Page/System/Testing)
    - [User Manual](https://gitlab.stud.idi.ntnu.no/ramtinfs/idatt1002/-/wikis/Main-Page/System/User-manual)
    - [Javadoc Documentation](https://ramtinfs.pages.stud.idi.ntnu.no/idatt1002)
    
## Prerequisites

Before you begin, ensure you have met the following requirements:

* You have Java verion 17 installed [Java JDK](https://www.oracle.com/java/technologies/downloads/) 
* You have installed [Maven](https://maven.apache.org/download.cgi)
* You have a basic understanding of Java and JavaFX.

## Installation

Installing the application at this stage requires knowledge on using Git, and running some simple commands in the terminal.

### Download the necessary files

The files needs to be added to a folder on your computer:

Open up your terminal and move to the folder you wish to store the flus app, using the `cd` command

```
cd <your destination folder>
```
Clone the repository from GitLab(You need to be connected to NTNU network or use a VPN)
```
git clone git@gitlab.stud.idi.ntnu.no:ramtinfs/idatt1002.git
```

Move into the directory of the flus app.
```
cd idatt1002

```
## Usage

Ensure a clean build of the application using:
```
mvn clean install
```

Lastly, run the application using:
```
mvn javafx:run
```

