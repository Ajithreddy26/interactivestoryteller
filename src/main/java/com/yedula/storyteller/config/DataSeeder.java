package com.yedula.storyteller.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.yedula.storyteller.entity.Story;
import com.yedula.storyteller.repository.StoryRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    private final StoryRepository storyRepository;
    
    public DataSeeder(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        if (storyRepository.count() == 0) {
            
            // Story 1
            Story mahabharata = new Story();
            mahabharata.setTitle("The Mahabharata");
            mahabharata.setAuthor("Vyasa");
            String epicText = "Long ago, there lived a noble king named Shantanu, who ruled the great kingdom of Hastinapura. " +
                    "He was known for his immense bravery and his deep love for his people. One day, while wandering along the banks of the sacred river Ganga, " +
                    "he saw a lady of unparalleled beauty. Enraptured, the King asked her to be his queen. The lady, who was the river goddess Ganga herself in human form, " +
                    "agreed, but on one strict condition: the King must never question any of her actions, no matter how strange they seemed.";
            mahabharata.setContent(epicText);
            mahabharata.setGenre("Epic/Mythology");
            mahabharata.setLanguage("Sanskrit/English");
            storyRepository.save(mahabharata);

            // Story 2
            Story genesis = new Story();
            genesis.setTitle("The Book of Genesis");
            genesis.setAuthor("Moses");
            String bibleText = "In the beginning God created the heavens and the earth. Now the earth was formless and empty, " +
                    "darkness was over the surface of the deep, and the Spirit of God was hovering over the waters. " +
                    "And God said, 'Let there be light,' and there was light. God saw that the light was good, and he separated the light from the darkness. " +
                    "God called the light 'day,' and the darkness he called 'night.' And there was evening, and there was morning—the first day.";
            genesis.setContent(bibleText);
            genesis.setGenre("Religious/Mythology");
            genesis.setLanguage("Hebrew/English");
            storyRepository.save(genesis);

            System.out.println("Seeded database with both stories!");
        }
    }

}
