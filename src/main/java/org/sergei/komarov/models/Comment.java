package org.sergei.komarov.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//@Entity
//@Table(name = "comments")
@NoArgsConstructor
@Data
public class Comment {
    //@Id
    private int id;
    private String text;
    private LocalDateTime published;
    private Issue issue;
    private User user;
}
