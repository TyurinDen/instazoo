package vk.dentttt.instazoo.controllers;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vk.dentttt.instazoo.converters.ImageToImageDtoConverter;
import vk.dentttt.instazoo.dtos.ImageDto;
import vk.dentttt.instazoo.dtos.ResponseObject;
import vk.dentttt.instazoo.entities.Image;
import vk.dentttt.instazoo.services.ImageService;

import java.security.Principal;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;
    private final ImageToImageDtoConverter imageDtoConverter;

    @PostMapping("/upload-profile-image")
    @SneakyThrows // TODO сделать правильную обработку исключений!!
    public ResponseEntity<ResponseObject> uploadProfileImage(@RequestParam MultipartFile image, Principal principal) {
        imageService.uploadProfileImage(image, principal);
        return ResponseEntity.ok(new ResponseObject("Profile image loaded successfully"));
    }

    @PostMapping("/{postId}/upload-post-image")
    @SneakyThrows // TODO сделать правильную обработку исключений!!
    public ResponseEntity<ResponseObject> uploadPostImage(
            @PathVariable("postId") Long postId,
            @RequestParam MultipartFile image,
            Principal principal
    ) {
        imageService.uploadPostImage(image, postId, principal);
        return ResponseEntity.ok(new ResponseObject("Post image loaded successfully"));
    }

    @GetMapping("/profile-image")
    public ResponseEntity<ImageDto> getProfileImage(Principal principal) {
        Image profileImage = imageService.getProfileImage(principal);
        return ResponseEntity.ok(imageDtoConverter.convertImageToImageDto(profileImage));
    }

    @GetMapping("/{postId}/post-image")
    public ResponseEntity<ImageDto> getPostImage(@PathVariable("postId") Long postId) {
        Image postImage = imageService.getPostImage(postId);
        return ResponseEntity.ok(imageDtoConverter.convertImageToImageDto(postImage));
    }

}
