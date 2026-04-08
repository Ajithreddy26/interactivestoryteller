package com.yedula.storyteller.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.yedula.storyteller.dto.StoryDTO;
import com.yedula.storyteller.entity.Story;
import com.yedula.storyteller.repository.StoryRepository;

@Service
public class NarratorService {

    private final StoryRepository storyRepository;
    private final ChatModel chatModel;

    public NarratorService(StoryRepository storyRepository, ChatModel chatModel) {
        this.storyRepository = storyRepository;
        this.chatModel = chatModel;
    }

    public String startStory(Long storyId){

        Story story = storyRepository.findById(storyId).orElseThrow(() -> new RuntimeException("Story not found"));   

        String storyContent = story.getContent();

        String prompt = "You are a an interactive story teller narrating this text, you are telling this to a person driving a car, so make it intresting and engaging. Here is the story: " + storyContent;


        return chatModel.call(prompt);
    }

    
    public List<StoryDTO> getAllStories(){
        List<Story> stories = storyRepository.findAll();
        List<StoryDTO> storyDTOs = new ArrayList<>();
        for (Story story : stories) {
            storyDTOs.add(new StoryDTO(story.getId(), story.getTitle(), story.getAuthor(), story.getGenre(), story.getLanguage()));
        }
        return storyDTOs;
    }
    
}
