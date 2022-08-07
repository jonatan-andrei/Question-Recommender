package jonatan.andrei.factory;

import jonatan.andrei.model.Word;

public class WordFactory {

    public static Word newWord(String word) {
        return Word.builder()
                .word(word)
                .questionCount(0)
                .build();
    }
}
