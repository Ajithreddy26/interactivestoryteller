package com.yedula.storyteller.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.yedula.storyteller.dto.StoryChunkDTO;
import com.yedula.storyteller.dto.StoryDTO;
import com.yedula.storyteller.entity.Story;
import com.yedula.storyteller.repository.StoryRepository;

import reactor.core.publisher.Flux;

@Service
public class NarratorService {

    private final StoryRepository storyRepository;
    private final ChatModel chatModel;

    public NarratorService(StoryRepository storyRepository, ChatModel chatModel) {
        this.storyRepository = storyRepository;
        this.chatModel = chatModel;
    }

    public Flux<StoryChunkDTO> startStory(Long storyId){
        Story story = storyRepository.findById(storyId).orElseThrow(() -> new RuntimeException("Story not found"));   
        String promptText = "You are an interactive storyteller narrating this text, " +
            "you are telling this to a person driving a car, so make it interesting and engaging. " +
            "CRITICAL WARNING: Do NOT use stage directions, asterisks, or parentheses. " +
            "You must only output the exact words you intend to speak out loud. " +
            "Here is the story: " + story.getContent();
        // 1. Tell Olamma to start generating the story stream
        Flux<ChatResponse> aiStream = chatModel.stream(new Prompt(promptText));
        // 2. As each token comes out of the AI, grab the text and package it into our DTO
        return aiStream.map(response -> {
            String token = response.getResult().getOutput().getText();
            
            // Protect against empty tokens at the end of the stream
            if (token == null) {
                return new StoryChunkDTO(""); 
            }
            
            return new StoryChunkDTO(token);
        });
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
