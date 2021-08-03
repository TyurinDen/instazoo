package vk.dentttt.instazoo.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "images")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Builder(builderMethodName = "imageBuilder")
public class Image extends BaseEntity {

    @Column(nullable = false, length = 200)
    private String title;

    @Column(columnDefinition = "bytea")
    private byte[] imageBytes;

    private Long userId;

    private Long postId;

}
