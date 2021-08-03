package vk.dentttt.instazoo.entities;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Builder
public class Comment extends BaseEntity {

    @ManyToOne
    private Post post;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, length = 1000)
    private String content;

}
