# Chinese Poetry Learning

A Java Spring Boot web application that helps international students study classical Chinese poetry through interactive reading, vocabulary, quizzes, pronunciation, handwriting practice, and private local AI assistance.

## Project Overview

Chinese Poetry Learning was designed as an educational course project for learners who want a simple way to understand classical Chinese poems. The application combines poem content, language-learning activities, user progress, and locally running artificial intelligence in one web interface.

## Main Features

- User registration, login, logout, and role-based access
- Library containing 10 classical Chinese poems
- Search poems by title or author
- Chinese text, pinyin, English translation, literary explanation, and cultural context
- Important vocabulary with example sentences
- Interactive Chinese text:
  - Hover over a word to view its pinyin and English meaning
  - Click a word to hear its pronunciation
  - Listen to the complete poem
- Multiple-choice quizzes with automatic score calculation
- Learning history for previous quiz attempts
- Pronunciation practice with browser speech recognition and similarity scoring
- Character handwriting practice using an HTML canvas
- Local AI poetry assistant and literary analysis
- AI tutor conversation using speech input and speech output
- Administrator dashboard with users, poems, vocabulary, questions, and quiz statistics

## Technologies Used

| Layer | Technology |
|---|---|
| Programming language | Java 21 |
| Application framework | Spring Boot |
| Web framework | Spring MVC |
| User interface templates | Thymeleaf |
| Authentication and authorization | Spring Security |
| Database access | Spring Data JPA and Hibernate |
| Database | H2 Database |
| Build tool | Maven with Maven Wrapper |
| Front end | HTML, CSS, and JavaScript |
| Browser speech functions | Web Speech API |
| Local artificial intelligence | Ollama with a Qwen model |
| Development environment | IntelliJ IDEA |

## System Architecture

```text
Web Browser
    |
    v
Spring MVC Controller
    |
    v
Service Layer
    |
    v
Repository Layer
    |
    v
H2 Database
```

- **Controllers** receive browser requests and select the correct page.
- **Services** contain application and authentication logic.
- **Repositories** read and write data through Spring Data JPA.
- **Models** represent users, poems, vocabulary, quiz questions, and attempts.
- **Thymeleaf templates** produce the HTML pages shown in the browser.
- **JavaScript** handles speech, word tooltips, pronunciation comparison, and handwriting.
- **AiService** sends prompts from Java to Ollama at `localhost:11434`.

## How the Application Works

1. A visitor registers or logs in.
2. Spring Security verifies the user and controls access to protected pages.
3. The user opens the poem library or searches for a poem.
4. A controller requests poem data from a repository and passes it to Thymeleaf.
5. Thymeleaf generates the page containing the poem and learning activities.
6. Browser JavaScript provides interactive speech and handwriting functions.
7. When a quiz is submitted, Java compares the answers, calculates the score, and stores the attempt.
8. The history page reads the saved attempts for the current user.
9. For AI features, Java sends a prompt to the local Ollama server and displays its response.
10. An administrator can view system statistics and registered accounts on the dashboard.

## Project Structure

```text
src/main/java/com/abutaher/poetrylearning/
|-- config/       Security configuration and initial sample data
|-- controller/   Web request handling
|-- model/        Database entities
|-- repository/   Database access interfaces
|-- service/      User and AI application logic

src/main/resources/
|-- templates/    Thymeleaf HTML pages
|-- static/       CSS and browser assets
|-- application.properties
```

## Requirements

Install the following free tools:

- Java Development Kit (JDK) 21
- IntelliJ IDEA Community Edition or another Java IDE
- A modern browser such as Chrome or Edge
- Ollama only if you want to use the local AI features

An internet connection is needed the first time Maven downloads the project dependencies. The main application can run locally afterward.

## Installation

### 1. Clone the repository

```powershell
git clone https://github.com/Md-Abu-Taher-Mazumdar/ChinesePoetryLearning.git
cd ChinesePoetryLearning
```

You can also download the repository as a ZIP file from GitHub and extract it.

### 2. Open the project

1. Open IntelliJ IDEA.
2. Click **Open**.
3. Select the `ChinesePoetryLearning` folder.
4. Wait while Maven downloads the required libraries.

### 3. Run the application

In IntelliJ, open `ChinesePoetryLearningApplication.java` and click the green **Run** triangle.

Alternatively, run this command from PowerShell inside the project folder:

```powershell
.\mvnw.cmd spring-boot:run
```

### 4. Open the Website

Visit:

```text
http://localhost:8080
```

## Optional Local AI Setup

The normal learning features work without Ollama. Ollama is required only for AI chat, AI literary analysis, and the AI tutor response.

1. Install Ollama from its official website.
2. Open PowerShell.
3. Download a suitable local model, for example:

```powershell
ollama pull qwen3.5:2b
```

4. Make sure the model name in `AiService.java` is exactly the same as the downloaded model name.
5. Start the Spring Boot application and use an AI page.

Ollama normally serves its local API at:

```text
http://localhost:11434
```

## Database and Sample Data

The project uses the H2 database for a simple local demonstration. `DataInitializer` and `AdditionalPoemInitializer` insert the demonstration accounts, poems, vocabulary, and quiz questions when the application starts.

If H2 is configured as an in-memory database, its runtime data is cleared when the application stops. A persistent database such as PostgreSQL or MySQL can be added in a future version.

## Security and Privacy

- Spring Security protects authenticated pages.
- User passwords are stored as encoded values, not plain text.
- The administrator dashboard does not display passwords.
- AI prompts are processed by Ollama on the local computer rather than a paid cloud AI service.
- No API key is required for the local AI setup.

## Testing the Project

After starting the application, check these functions:

1. Register a new user and log in.
2. Search for a poem by title or author.
3. Open a poem and test word hover, word audio, and complete-poem audio.
4. Complete a quiz and confirm that the score appears in learning history.
5. Test pronunciation and handwriting practice.
6. Ask the local AI a poetry question.
7. Log in with an administrator account and open the admin dashboard.

## Known Limitations

- This is a local educational demonstration, not a production service.
- Speech recognition and available Chinese voices depend on the browser and operating system.
- Pronunciation scoring compares recognized text; it is not a professional phonetic assessment.
- The handwriting canvas records drawing input but does not perform advanced character recognition.
- The AI tutor uses an illustrated avatar and browser speech; it is not a real video call.
- Local AI speed and answer quality depend on the selected model and computer hardware.
- AI-generated explanations may contain mistakes and should be checked against reliable academic sources.

## Poem Data and Academic Use

The demonstration uses well-known public-domain classical Chinese poems. The pinyin, translations, vocabulary, explanations, and quiz material were prepared for this educational project.

For academic submission, poem text and learning information should be checked against reputable references such as the Chinese Text Project, Wikisource, university resources, or published poetry collections.

## Future Improvements

- Persistent PostgreSQL or MySQL database
- More poems, vocabulary, and quiz questions
- Improved phonetic pronunciation assessment
- Advanced handwriting recognition and stroke-order feedback
- Streaming AI responses for a faster user experience
- More automated unit and integration tests
- Cloud deployment and production configuration
- Expanded administrator content-management tools

## Author

**Md Abu Taher Mazumdar**

This project was created as a Java and software engineering learning project.
