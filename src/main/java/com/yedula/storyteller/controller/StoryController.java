package com.yedula.storyteller.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedula.storyteller.dto.StoryChunkDTO;
import com.yedula.storyteller.dto.StoryDTO;
import com.yedula.storyteller.entity.Story;
import com.yedula.storyteller.service.NarratorService;

import reactor.core.publisher.Flux;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/stories")
public class StoryController {
    private final NarratorService narratorService;

    public StoryController(NarratorService narratorService){
        this.narratorService = narratorService;
    }

    @GetMapping(value ="/start/{id}", produces = org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<StoryChunkDTO> startStory(@PathVariable Long id){
        return narratorService.startStory(id);
    }

    @GetMapping("/all")
    public List<StoryDTO> getAllStories(){
        return narratorService.getAllStories();
    }

    @GetMapping(value = "/chat/{id}", produces = org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<StoryChunkDTO> chatWithStory(@PathVariable Long id, String userMessage){
        return narratorService.chatWithStory(id, userMessage);
    }

    
}
