package com.yedula.storyteller.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yedula.storyteller.dto.StoryDTO;
import com.yedula.storyteller.entity.Story;
import com.yedula.storyteller.service.NarratorService;

@RestController
@RequestMapping("/api/stories")
public class StoryController {
    private final NarratorService narratorService;

    public StoryController(NarratorService narratorService){
        this.narratorService = narratorService;
    }

    @GetMapping("/start/{id}")
    public String startStory(@PathVariable Long id){
        return narratorService.startStory(id);
    }

    @GetMapping("/all")
    public List<StoryDTO> getAllStories(){
        return narratorService.getAllStories();
    }

    
}
