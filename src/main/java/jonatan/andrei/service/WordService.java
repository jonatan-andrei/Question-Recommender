package jonatan.andrei.service;

import jonatan.andrei.factory.WordFactory;
import jonatan.andrei.model.Word;
import jonatan.andrei.repository.WordRepository;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@ApplicationScoped
public class WordService {

    @Inject
    WordRepository wordRepository;

    @ConfigProperty(name = "recommendation.settings.minimum_word_size_considered")
    Integer minimumWordSizeConsidered;

    @Transactional
    public void saveOrUpdate(String question) {
        List<String> questionWords = splitWords(question);
        if (questionWords.isEmpty()) {
            return;
        }

        List<Word> wordsAlreadySaved = wordRepository.findByWord(questionWords);

        List<Word> wordsToSave = questionWords.stream()
                .map(questionWord -> createOrUpdate(questionWord, wordsAlreadySaved))
                .collect(Collectors.toList());

        wordRepository.saveAll(wordsToSave);
    }

    private List<String> splitWords(String phrase) {
        return Stream.of(phrase.split("\\s+"))
                .filter(w -> w.length() < minimumWordSizeConsidered)
                .collect(Collectors.toList());
    }

    private Word createOrUpdate(String word, List<Word> wordsAlreadySaved) {
        Optional<Word> wordAlreadySaved = wordsAlreadySaved.stream().filter(w -> w.getWord().equals(word)).findFirst();
        if (wordAlreadySaved.isPresent()) {
            wordAlreadySaved.get().setQuestionCount(wordAlreadySaved.get().getQuestionCount() + 1);
            return wordAlreadySaved.get();
        } else {
            return WordFactory.newWord(word);
        }
    }
}

