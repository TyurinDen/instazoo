package vk.dentttt.instazoo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import vk.dentttt.instazoo.entities.Image;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByUserId(Long userId);

    Optional<Image> findByPostId(Long postId);

}
