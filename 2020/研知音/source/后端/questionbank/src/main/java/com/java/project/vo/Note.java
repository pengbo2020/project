package com.java.project.vo;

import com.java.project.entity.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    String content;
    List<Image> imageList;
}
