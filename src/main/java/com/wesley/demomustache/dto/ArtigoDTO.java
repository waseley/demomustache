package com.wesley.demomustache.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ArtigoDTO
{
    private String title;
    private String publishDate;
    private String author;
    private String body;
}
