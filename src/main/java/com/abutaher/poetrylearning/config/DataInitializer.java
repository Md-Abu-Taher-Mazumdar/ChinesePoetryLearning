package com.abutaher.poetrylearning.config;

import com.abutaher.poetrylearning.model.AppUser;
import com.abutaher.poetrylearning.model.Poem;
import com.abutaher.poetrylearning.model.QuizQuestion;
import com.abutaher.poetrylearning.model.Vocabulary;
import com.abutaher.poetrylearning.repository.AppUserRepository;
import com.abutaher.poetrylearning.repository.PoemRepository;
import com.abutaher.poetrylearning.repository.QuizQuestionRepository;
import com.abutaher.poetrylearning.repository.VocabularyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AppUserRepository userRepository;
    private final PoemRepository poemRepository;
    private final VocabularyRepository vocabularyRepository;
    private final QuizQuestionRepository questionRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(
            AppUserRepository userRepository,
            PoemRepository poemRepository,
            VocabularyRepository vocabularyRepository,
            QuizQuestionRepository questionRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.poemRepository = poemRepository;
        this.vocabularyRepository = vocabularyRepository;
        this.questionRepository = questionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        createAdministrator();

        if (poemRepository.count() == 0) {
            createQuietNightThought();
            createClimbingStorkTower();
        }
    }

    private void createAdministrator() {
        if (!userRepository.existsByUsername("admin")) {
            AppUser admin = new AppUser();

            admin.setFullName("System Administrator");
            admin.setUsername("admin");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ROLE_ADMIN");

            userRepository.save(admin);
        }
    }

    private void createQuietNightThought() {
        Poem poem = new Poem();

        poem.setTitle("静夜思");
        poem.setAuthor("李白");
        poem.setDynasty("Tang Dynasty");
        poem.setChineseText("""
                床前明月光，
                疑是地上霜。
                举头望明月，
                低头思故乡。
                """);

        poem.setPinyin("""
                Chuáng qián míng yuè guāng,
                yí shì dì shàng shuāng.
                Jǔ tóu wàng míng yuè,
                dī tóu sī gù xiāng.
                """);

        poem.setTranslation("""
                Bright moonlight shines before the bed.
                It looks like frost upon the ground.
                I raise my head to watch the bright moon,
                then lower it and think of my hometown.
                """);

        poem.setExplanation("""
                The poem describes homesickness during a quiet night.
                The moonlight reminds the poet of his distant home.
                The simple movements of raising and lowering his head
                express strong emotion using very few words.
                """);

        poem.setCulturalContext("""
                In traditional Chinese culture, the moon often represents
                reunion, family and homesickness. Li Bai wrote many poems
                about travel, nature and separation.
                """);

        poem = poemRepository.save(poem);

        saveVocabulary(
                poem,
                "明月",
                "míng yuè",
                "bright moon",
                "我们一起看明月。"
        );

        saveVocabulary(
                poem,
                "故乡",
                "gù xiāng",
                "hometown",
                "我非常想念故乡。"
        );

        saveQuestion(
                poem,
                "What does the moon remind the poet of?",
                "His hometown",
                "A mountain",
                "A river",
                "A friend",
                "A"
        );

        saveQuestion(
                poem,
                "Who wrote 静夜思?",
                "杜甫",
                "李白",
                "王维",
                "孟浩然",
                "B"
        );
    }

    private void createClimbingStorkTower() {
        Poem poem = new Poem();

        poem.setTitle("登鹳雀楼");
        poem.setAuthor("王之涣");
        poem.setDynasty("Tang Dynasty");
        poem.setChineseText("""
                白日依山尽，
                黄河入海流。
                欲穷千里目，
                更上一层楼。
                """);

        poem.setPinyin("""
                Bái rì yī shān jìn,
                Huáng Hé rù hǎi liú.
                Yù qióng qiān lǐ mù,
                gèng shàng yì céng lóu.
                """);

        poem.setTranslation("""
                The setting sun disappears behind the mountains.
                The Yellow River flows into the sea.
                To see a thousand miles farther,
                climb one more level of the tower.
                """);

        poem.setExplanation("""
                The poem begins with a wide natural landscape and ends
                with an important idea: to see farther, a person must
                continue moving upward and making greater effort.
                """);

        poem.setCulturalContext("""
                This poem is often used to express ambition and continuous
                self-improvement. Its final two lines are widely remembered
                as encouragement to reach a higher level.
                """);

        poem = poemRepository.save(poem);

        saveVocabulary(
                poem,
                "黄河",
                "Huáng Hé",
                "Yellow River",
                "黄河是中国著名的河流。"
        );

        saveVocabulary(
                poem,
                "一层楼",
                "yì céng lóu",
                "one level of a building",
                "请再上一层楼。"
        );

        saveQuestion(
                poem,
                "Where does the Yellow River flow?",
                "Into the forest",
                "Into the sea",
                "Into the city",
                "Into the mountain",
                "B"
        );

        saveQuestion(
                poem,
                "What should someone do to see farther?",
                "Close their eyes",
                "Return home",
                "Climb one more level",
                "Wait until morning",
                "C"
        );
    }

    private void saveVocabulary(
            Poem poem,
            String word,
            String pinyin,
            String meaning,
            String example
    ) {
        Vocabulary vocabulary = new Vocabulary();

        vocabulary.setPoem(poem);
        vocabulary.setWord(word);
        vocabulary.setPinyin(pinyin);
        vocabulary.setMeaning(meaning);
        vocabulary.setExampleSentence(example);

        vocabularyRepository.save(vocabulary);
    }

    private void saveQuestion(
            Poem poem,
            String questionText,
            String optionA,
            String optionB,
            String optionC,
            String optionD,
            String correctOption
    ) {
        QuizQuestion question = new QuizQuestion();

        question.setPoem(poem);
        question.setQuestion(questionText);
        question.setOptionA(optionA);
        question.setOptionB(optionB);
        question.setOptionC(optionC);
        question.setOptionD(optionD);
        question.setCorrectOption(correctOption);

        questionRepository.save(question);
    }
}