package edu.iu.c322.test1.repository;

import edu.iu.c322.test1.model.Question;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileRepositoryTest {


    @BeforeAll
    static void setup() throws IOException {
        Files.deleteIfExists(Paths.get("questions-test.txt"));
    }

    @AfterAll
    static void cleanup() throws IOException {
        Files.delete(Paths.get("questions-test.txt"));
    }

    @Test
    void addQuestion1() {
        Question q = new Question(1,
                "Which word matches the image?",
                "elephant",
                new String[]{"tiger", "bear", "elephant"});

        addToDB(q);
    }

    @Test
    void addQuestion2() {
        Question q = new Question(1,
                "Which word matches the image?",
                "leopard",
                new String[]{"tiger", "lion", "leopard"});

        addToDB(q);
    }

    @Test
    void addQuestion3() {
        Question q = new Question(1,
                "Which word matches the image?",
                "lion",
                new String[]{"cheetah", "lion", "leopard"});

        addToDB(q);
    }

    void addToDB(Question question) {
        boolean result = false;
        try {
            Path path = Paths.get("questions-test.txt");
            String data = question.toLine();
            appendToFile(path, data + System.lineSeparator());
            result = true;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            fail();
        }
        assertTrue(result);
    }




    @Test
    void findAll() {
        try {
            List<Question> data = findAllthingy();
            assertEquals(3, data.size());
        } catch (IOException e) {
            fail();
        }
    }
    private static void appendToFile(Path path, String content)
            throws IOException {
        Files.write(path,
                content.getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);
    }
    public List<Question> findAllthingy() throws IOException {
        List<Question> result = new ArrayList<>();
        Path path = Paths.get("questions-test.txt");
        if (Files.exists(path)) {
            List<String> data = Files.readAllLines(path);
            for (String line : data) {
                Question q = Question.fromLine(line);
                result.add(q);
            }
        }
        return result;
    }
}