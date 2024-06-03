This repository contains automated test against [booking.com](https://booking.com/) web-site.

The test selects an attraction, configures ticket options and verified that total price is calculated correctly.

## Tech Stack
- [**Java**](https://www.oracle.com/ca-en/java/): The primary programming language for test automation.
- [**Selenium**](https://www.selenium.dev): Used for browser automation and interaction with web elements.
- [**Maven**](https://maven.apache.org): Dependency management and build automation tool.
- [**TestNG**](https://testng.org): Testing framework for Java programming language.

## Getting Started
To run the automated tests locally, ensure you have the following prerequisites installed:

- [Java Development Kit (JDK)](https://www.oracle.com/ca-en/java/technologies/downloads/)
- [Maven](https://maven.apache.org/install.html)
- [Chrome web browser](https://www.google.com/chrome/)

Clone this repository to your local machine using:

`git clone https://github.com/juliab/booking-assignment.git`

Navigate to the project directory:

`cd contact-list-tests`

Run the tests using Maven:

`mvn clean verify`
