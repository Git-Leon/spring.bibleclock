# Bible Clock Application

A Spring Boot application that displays Bible verses based on the current time. The application matches verses to the current time using chapter and verse numbers, providing a unique way to read through the Bible.

## Features

- Real-time verse display based on current time
- Automatic verse lookup using chapter:verse format
- Fallback to closest verse when exact match not found
- Dark/Light theme toggle
- Responsive design
- CSV-based verse storage
- H2 in-memory database for verse persistence

## Prerequisites

- Java 17 or higher
- Maven 3.6 or higher
- Spring Boot 3.2.3

## Project Structure

```
src/main/
├── java/com/bibleclock/
│   ├── config/
│   │   └── BibleConfig.java           # Application configuration
│   ├── controller/
│   │   └── BibleController.java       # REST endpoints
│   ├── model/
│   │   └── BibleVerse.java           # Verse entity
│   ├── repository/
│   │   └── BibleVerseRepository.java  # JPA repository
│   ├── service/
│   │   ├── BibleService.java         # Business logic
│   │   └── BibleVerseParserService.java # CSV parsing
│   └── utils/
│       └── Loggable.java             # Logging utility
└── resources/
    ├── static/
    │   ├── index.html               # Frontend
    │   ├── script.js               # Frontend logic
    │   └── styles.css             # Styling
    ├── data.csv                   # Bible verses data
    └── application.properties     # Application configuration
```

## Setup and Installation

1. Clone the repository:
   ```bash
   git clone [repository-url]
   cd bible-clock
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The application will be available at `http://localhost:8080`

## API Endpoints

- `GET /book/bible/{timeString}` - Get a Bible verse for the specified time
  - Example: `GET /book/bible/3:16` returns John 3:16
  - Time format: HH:mm (24-hour format)

## Database

The application uses H2 in-memory database for verse storage. You can access the H2 console at:
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:bibledb`
- Username: `sa`
- Password: (empty)

## Data Format

The application expects a CSV file (`data.csv`) with the following format:
```
book,chapter,verse,text
John,3,16,For God so loved the world...
```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 