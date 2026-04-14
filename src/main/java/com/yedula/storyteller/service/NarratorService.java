package com.yedula.storyteller.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
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
    private final ChatClient chatClient;

    public NarratorService(StoryRepository storyRepository, ChatModel chatModel, ChatMemory chatMemory) {
        this.storyRepository = storyRepository;
        
        this.chatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    public Flux<StoryChunkDTO> startStory(Long storyId){
        Story story = storyRepository.findById(storyId).orElseThrow(() -> new RuntimeException("Story not found"));   

        Flux<ChatResponse> aiStream = chatClient.prompt()
                .system("You are an interactive storyteller narrating a text. " +
                        "CRITICAL WARNING: Do NOT use stage directions, asterisks, or parentheses. " +
                        "You must only output the exact words you intend to speak out loud.")
                .user("Here is the story: " + story.getContent())
                .advisors(advisor -> advisor.param("chat_memory_conversation_id", storyId.toString()))
                .stream()
                .chatResponse();

        return aiStream.map(response -> {
            String token = response.getResult().getOutput().getText();
            

            if (token == null) {
                return new StoryChunkDTO(""); 
            }
            
            return new StoryChunkDTO(token);
        });
    }

    public Flux<StoryChunkDTO> chatWithStory(Long storyId, String userMessage){
        return chatClient.prompt()
                        .user(userMessage)
                        .advisors(advisor -> advisor.param("chat_memory_conversation_id", storyId.toString()))
                        .stream()
                        .chatResponse()
                        .map(response -> new StoryChunkDTO(response.getResult().getOutput().getText()));
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
