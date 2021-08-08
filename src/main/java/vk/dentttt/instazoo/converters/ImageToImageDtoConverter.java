package vk.dentttt.instazoo.converters;

import org.springframework.stereotype.Component;
import vk.dentttt.instazoo.dtos.ImageDto;
import vk.dentttt.instazoo.entities.Image;

@Component
public class ImageToImageDtoConverter {
    public ImageDto convertImageToImageDto(Image image) {
        return new ImageDto(image.getTitle(), image.getImageBytes(), image.getPostId(), image.getUserId());
    }
}
