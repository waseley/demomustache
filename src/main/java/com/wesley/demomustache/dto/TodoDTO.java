package com.wesley.demomustache.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDTO
{
    private String title;
    private String text;
    private boolean done;
    private Date createdOn;
    private Date completedOn;
}
