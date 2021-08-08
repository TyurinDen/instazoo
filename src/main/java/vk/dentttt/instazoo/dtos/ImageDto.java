package vk.dentttt.instazoo.dtos;

import lombok.Value;

@Value
public class ImageDto {

    String title;

    byte[] image;

    Long postId;

    Long userId;

}
