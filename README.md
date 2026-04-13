# Stardate Converter (Java)

A Java desktop app that converts Earth dates into a Voyager-inspired stardate using a simplified year-and-day scaling formula.

This project started as an early practice build before the Voyager database project. The current version keeps the LCARS-inspired look, but upgrades the codebase into a cleaner Maven app with a more stable Swing layout and two-way conversion support.

## Version 1.0 Highlights

- LCARS-style Swing interface with better alignment and spacing
- Earth date to stardate and stardate back to Earth date conversion
- Dedicated `StardateCalculator` class for reusable conversion logic
- Keyboard-friendly input with Enter-to-convert support
- `Today` and `Clear` actions for quicker testing
- JUnit tests covering date conversion behavior

## Stardate Formula

This app uses a simplified custom formula rather than a canon-accurate Trek formula:

`stardate = 1000 * (year - 2323) + fractional progress through the current year`

Details:

- Base year: `2323`
- Scale: `1000` stardate units per Earth year
- Fractional component: based on day-of-year
- Leap years: handled using `366` days

That makes the output predictable and easy to reuse in other projects, even if it is not meant to exactly match on-screen Trek canon.

## Project Structure

```text
stardate-converter-java
|-- src
|   |-- main
|   |   `-- java
|   |       `-- com
|   |           `-- raven8472
|   |               `-- stardate
|   |                   |-- StarDateConverter.java
|   |                   |-- StarDateConverterApp.java
|   |                   `-- StardateCalculator.java
|   `-- test
|       `-- java
|           `-- com
|               `-- raven8472
|                   `-- stardate
|                       `-- StardateCalculatorTest.java
|-- pom.xml
`-- README.md
```

## Running The App

Requirements:

- Java 21
- Maven 3.9+

Run the desktop app:

```bash
mvn compile
java -cp target/classes com.raven8472.stardate.StarDateConverterApp
```

Build a runnable JAR:

```bash
mvn package
java -jar target/stardate-converter-1.0.0.jar
```

Run the tests:

```bash
mvn test
```

## Future Ideas

- Reverse conversion from stardate to Earth date
- Multiple formula profiles for different Trek eras
- Conversion history panel
- Optional API version for use in other Voyager tools

## Author

Dakota Leahy  
IT student, database enthusiast, and Navy veteran

- GitHub: https://github.com/Raven8472
- LinkedIn: https://www.linkedin.com/in/dakotaleahy/

## Project Context

This converter was built as an early stepping stone toward a larger LCARS-inspired Voyager crew database project for learning, portfolio work, and a bit of Star Trek fun.
