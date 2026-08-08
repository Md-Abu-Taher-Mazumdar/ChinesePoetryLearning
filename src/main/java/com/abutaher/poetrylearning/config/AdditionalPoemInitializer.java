package com.abutaher.poetrylearning.config;

import com.abutaher.poetrylearning.model.Poem;
import com.abutaher.poetrylearning.model.QuizQuestion;
import com.abutaher.poetrylearning.model.Vocabulary;
import com.abutaher.poetrylearning.repository.PoemRepository;
import com.abutaher.poetrylearning.repository.QuizQuestionRepository;
import com.abutaher.poetrylearning.repository.VocabularyRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdditionalPoemInitializer implements CommandLineRunner {

    private final PoemRepository poemRepository;
    private final VocabularyRepository vocabularyRepository;
    private final QuizQuestionRepository questionRepository;

    public AdditionalPoemInitializer(
            PoemRepository poemRepository,
            VocabularyRepository vocabularyRepository,
            QuizQuestionRepository questionRepository
    ) {
        this.poemRepository = poemRepository;
        this.vocabularyRepository = vocabularyRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public void run(String... args) {
        for (PoemData data : getPoems()) {

            if (poemRepository.existsByTitle(data.title())) {
                continue;
            }

            Poem poem = new Poem();
            poem.setTitle(data.title());
            poem.setAuthor(data.author());
            poem.setDynasty(data.dynasty());

            setFirstAvailable(
                    poem,
                    data.content(),
                    "setContent",
                    "setChineseText",
                    "setOriginalText"
            );

            setFirstAvailable(
                    poem,
                    data.pinyin(),
                    "setPinyin"
            );

            setFirstAvailable(
                    poem,
                    data.translation(),
                    "setTranslation",
                    "setEnglishTranslation",
                    "setEnglishMeaning"
            );

            poem.setExplanation(data.explanation());
            poem.setCulturalContext(data.culturalContext());

            poem = poemRepository.save(poem);

            VocabularyData vocabularyData = data.vocabulary();

            Vocabulary vocabulary = new Vocabulary();
            vocabulary.setPoem(poem);
            vocabulary.setWord(vocabularyData.word());
            vocabulary.setPinyin(vocabularyData.pinyin());
            vocabulary.setMeaning(vocabularyData.meaning());
            vocabulary.setExampleSentence(vocabularyData.exampleSentence());
            vocabularyRepository.save(vocabulary);

            QuestionData questionData = data.question();

            QuizQuestion question = new QuizQuestion();
            question.setPoem(poem);
            question.setQuestion(questionData.question());
            question.setOptionA(questionData.optionA());
            question.setOptionB(questionData.optionB());
            question.setOptionC(questionData.optionC());
            question.setOptionD(questionData.optionD());
            question.setCorrectOption(questionData.correctOption());
            questionRepository.save(question);
        }
    }

    /*
     * This helper supports the different poem field names that
     * we may have used earlier, such as content or ChineseText.
     */
    private void setFirstAvailable(
            Poem poem,
            String value,
            String... possibleMethodNames
    ) {
        for (String methodName : possibleMethodNames) {
            try {
                Poem.class
                        .getMethod(methodName, String.class)
                        .invoke(poem, value);
                return;
            } catch (NoSuchMethodException ignored) {
                // Try the next possible method name.
            } catch (ReflectiveOperationException exception) {
                throw new IllegalStateException(
                        "Could not add poem information.",
                        exception
                );
            }
        }

        throw new IllegalStateException(
                "A required text field was not found in the Poem class."
        );
    }

    private List<PoemData> getPoems() {
        return List.of(

                new PoemData(
                        "静夜思",
                        "李白",
                        "Tang Dynasty",
                        "床前明月光，\n疑是地上霜。\n举头望明月，\n低头思故乡。",
                        "Chuáng qián míng yuè guāng,\n" +
                                "yí shì dì shàng shuāng.\n" +
                                "Jǔ tóu wàng míng yuè,\n" +
                                "dī tóu sī gù xiāng.",
                        "Moonlight shines before my bed. I think it is frost on the ground. I raise my head to view the moon, then lower it and remember my hometown.",
                        "The poet sees bright moonlight at night. The moon makes him remember and miss his hometown.",
                        "The moon is an important symbol of family, reunion and homesickness in Chinese poetry.",
                        new VocabularyData(
                                "故乡",
                                "gù xiāng",
                                "hometown",
                                "我想念我的故乡。"
                        ),
                        new QuestionData(
                                "What does the poet miss?",
                                "His hometown",
                                "A mountain",
                                "A river",
                                "A school",
                                "A"
                        )
                ),

                new PoemData(
                        "登鹳雀楼",
                        "王之涣",
                        "Tang Dynasty",
                        "白日依山尽，\n黄河入海流。\n欲穷千里目，\n更上一层楼。",
                        "Bái rì yī shān jìn,\n" +
                                "Huáng Hé rù hǎi liú.\n" +
                                "Yù qióng qiān lǐ mù,\n" +
                                "gèng shàng yì céng lóu.",
                        "The sun sets behind the mountains and the Yellow River flows into the sea. To see farther, climb one more floor.",
                        "The poem describes a wide landscape and teaches that greater effort gives us a wider view.",
                        "The Yellow River is one of the most important rivers in Chinese history and culture.",
                        new VocabularyData(
                                "黄河",
                                "Huáng Hé",
                                "Yellow River",
                                "黄河是中国的重要河流。"
                        ),
                        new QuestionData(
                                "What should a person do to see farther?",
                                "Close their eyes",
                                "Climb one more floor",
                                "Go to sleep",
                                "Return home",
                                "B"
                        )
                ),

                new PoemData(
                        "春晓",
                        "孟浩然",
                        "Tang Dynasty",
                        "春眠不觉晓，\n处处闻啼鸟。\n夜来风雨声，\n花落知多少。",
                        "Chūn mián bù jué xiǎo,\n" +
                                "chù chù wén tí niǎo.\n" +
                                "Yè lái fēng yǔ shēng,\n" +
                                "huā luò zhī duō shǎo.",
                        "Sleeping in spring, I did not notice the morning. Birds sing everywhere. Wind and rain came during the night; I wonder how many flowers fell.",
                        "The poet wakes on a spring morning and thinks about the flowers after the night rain.",
                        "Spring poems often express the beauty of nature and the passing of time.",
                        new VocabularyData(
                                "春",
                                "chūn",
                                "spring",
                                "春天来了。"
                        ),
                        new QuestionData(
                                "What sound does the poet hear in the morning?",
                                "Birds",
                                "Cars",
                                "Drums",
                                "Bells",
                                "A"
                        )
                ),

                new PoemData(
                        "鹿柴",
                        "王维",
                        "Tang Dynasty",
                        "空山不见人，\n但闻人语响。\n返景入深林，\n复照青苔上。",
                        "Kōng shān bú jiàn rén,\n" +
                                "dàn wén rén yǔ xiǎng.\n" +
                                "Fǎn yǐng rù shēn lín,\n" +
                                "fù zhào qīng tái shàng.",
                        "No person can be seen in the empty mountain, but human voices can be heard. Evening light enters the deep forest and shines again on the green moss.",
                        "The poem presents a quiet mountain where voices and light make the silence feel deeper.",
                        "Wang Wei was famous for combining poetry, painting and Buddhist ideas about quietness.",
                        new VocabularyData(
                                "空山",
                                "kōng shān",
                                "empty mountain",
                                "空山非常安静。"
                        ),
                        new QuestionData(
                                "What can be heard in the empty mountain?",
                                "Human voices",
                                "Thunder",
                                "Music",
                                "A waterfall",
                                "A"
                        )
                ),

                new PoemData(
                        "相思",
                        "王维",
                        "Tang Dynasty",
                        "红豆生南国，\n春来发几枝。\n愿君多采撷，\n此物最相思。",
                        "Hóng dòu shēng nán guó,\n" +
                                "chūn lái fā jǐ zhī.\n" +
                                "Yuàn jūn duō cǎi xié,\n" +
                                "cǐ wù zuì xiāng sī.",
                        "Red beans grow in the south and form new branches in spring. Please gather some, because they represent deep remembrance.",
                        "The poet uses red beans as a symbol of remembering a distant friend or loved person.",
                        "In Chinese culture, red beans can symbolize remembrance and affection.",
                        new VocabularyData(
                                "相思",
                                "xiāng sī",
                                "to deeply miss someone",
                                "红豆代表相思。"
                        ),
                        new QuestionData(
                                "What object represents remembrance in the poem?",
                                "Red beans",
                                "A boat",
                                "Snow",
                                "A tower",
                                "A"
                        )
                ),

                new PoemData(
                        "江雪",
                        "柳宗元",
                        "Tang Dynasty",
                        "千山鸟飞绝，\n万径人踪灭。\n孤舟蓑笠翁，\n独钓寒江雪。",
                        "Qiān shān niǎo fēi jué,\n" +
                                "wàn jìng rén zōng miè.\n" +
                                "Gū zhōu suō lì wēng,\n" +
                                "dú diào hán jiāng xuě.",
                        "Across many mountains no birds fly, and on all paths no people can be seen. An old man in a small boat fishes alone on the cold snowy river.",
                        "The poem creates a cold and silent winter scene with one fisherman remaining strong and independent.",
                        "The lonely fisherman is often understood as a symbol of endurance during difficult times.",
                        new VocabularyData(
                                "雪",
                                "xuě",
                                "snow",
                                "冬天有雪。"
                        ),
                        new QuestionData(
                                "Who is on the river?",
                                "A fisherman",
                                "A farmer",
                                "A soldier",
                                "A teacher",
                                "A"
                        )
                ),

                new PoemData(
                        "悯农",
                        "李绅",
                        "Tang Dynasty",
                        "锄禾日当午，\n汗滴禾下土。\n谁知盘中餐，\n粒粒皆辛苦。",
                        "Chú hé rì dāng wǔ,\n" +
                                "hàn dī hé xià tǔ.\n" +
                                "Shuí zhī pán zhōng cān,\n" +
                                "lì lì jiē xīn kǔ.",
                        "Farmers work under the hot midday sun and sweat falls into the soil. Every grain of food comes from hard work.",
                        "The poem asks readers to respect farmers and avoid wasting food.",
                        "Chinese children often learn this poem as a lesson about gratitude and saving food.",
                        new VocabularyData(
                                "辛苦",
                                "xīn kǔ",
                                "hard work or hardship",
                                "农民工作很辛苦。"
                        ),
                        new QuestionData(
                                "What lesson does this poem teach?",
                                "Do not waste food",
                                "Buy more clothes",
                                "Travel every day",
                                "Sleep longer",
                                "A"
                        )
                ),

                new PoemData(
                        "望庐山瀑布",
                        "李白",
                        "Tang Dynasty",
                        "日照香炉生紫烟，\n遥看瀑布挂前川。\n飞流直下三千尺，\n疑是银河落九天。",
                        "Rì zhào Xiāng Lú shēng zǐ yān,\n" +
                                "yáo kàn pù bù guà qián chuān.\n" +
                                "Fēi liú zhí xià sān qiān chǐ,\n" +
                                "yí shì Yín Hé luò jiǔ tiān.",
                        "Sunlight creates purple mist over Incense Burner Peak. From far away, the waterfall hangs before the mountain. It falls from a great height like the Milky Way descending from heaven.",
                        "Li Bai uses imagination and exaggeration to describe the great size and beauty of the waterfall.",
                        "Mount Lu is a famous mountain connected with Chinese poetry, religion and landscape culture.",
                        new VocabularyData(
                                "瀑布",
                                "pù bù",
                                "waterfall",
                                "这个瀑布很高。"
                        ),
                        new QuestionData(
                                "What does the waterfall look like to the poet?",
                                "The Milky Way falling from heaven",
                                "A small road",
                                "A quiet house",
                                "A bird",
                                "A"
                        )
                ),

                new PoemData(
                        "绝句",
                        "杜甫",
                        "Tang Dynasty",
                        "两个黄鹂鸣翠柳，\n一行白鹭上青天。\n窗含西岭千秋雪，\n门泊东吴万里船。",
                        "Liǎng gè huáng lí míng cuì liǔ,\n" +
                                "yì háng bái lù shàng qīng tiān.\n" +
                                "Chuāng hán Xī Lǐng qiān qiū xuě,\n" +
                                "mén bó Dōng Wú wàn lǐ chuán.",
                        "Two orioles sing in green willow trees, and a line of white egrets flies into the blue sky. Snowy mountains appear through the window, while distant boats rest outside.",
                        "The poem presents a colourful and peaceful spring landscape using several vivid images.",
                        "Du Fu is one of the most important poets in Chinese history and is sometimes called the Poet Sage.",
                        new VocabularyData(
                                "白鹭",
                                "bái lù",
                                "white egret",
                                "白鹭飞上天空。"
                        ),
                        new QuestionData(
                                "Which birds fly into the blue sky?",
                                "White egrets",
                                "Eagles",
                                "Ducks",
                                "Swans",
                                "A"
                        )
                ),

                new PoemData(
                        "游子吟",
                        "孟郊",
                        "Tang Dynasty",
                        "慈母手中线，\n游子身上衣。\n临行密密缝，\n意恐迟迟归。\n谁言寸草心，\n报得三春晖。",
                        "Cí mǔ shǒu zhōng xiàn,\n" +
                                "yóu zǐ shēn shàng yī.\n" +
                                "Lín xíng mì mì féng,\n" +
                                "yì kǒng chí chí guī.\n" +
                                "Shuí yán cùn cǎo xīn,\n" +
                                "bào dé sān chūn huī.",
                        "A loving mother carefully sews clothes for her travelling child because she worries that the child will return late. A child's gratitude can never fully repay a mother's love.",
                        "The poem expresses deep gratitude for a mother's care and selfless love.",
                        "This poem is widely remembered in China when discussing family love and respect for parents.",
                        new VocabularyData(
                                "慈母",
                                "cí mǔ",
                                "loving mother",
                                "慈母为孩子缝衣服。"
                        ),
                        new QuestionData(
                                "What is the mother doing?",
                                "Sewing clothes",
                                "Cooking rice",
                                "Writing a letter",
                                "Reading a book",
                                "A"
                        )
                )
        );
    }

    private record PoemData(
            String title,
            String author,
            String dynasty,
            String content,
            String pinyin,
            String translation,
            String explanation,
            String culturalContext,
            VocabularyData vocabulary,
            QuestionData question
    ) {
    }

    private record VocabularyData(
            String word,
            String pinyin,
            String meaning,
            String exampleSentence
    ) {
    }

    private record QuestionData(
            String question,
            String optionA,
            String optionB,
            String optionC,
            String optionD,
            String correctOption
    ) {
    }
}