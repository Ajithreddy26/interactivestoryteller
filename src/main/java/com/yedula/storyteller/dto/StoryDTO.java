package com.yedula.storyteller.dto;

public record StoryDTO(
        Long id,
        String title,
        String author,
        String genre,
        String language) {

}
