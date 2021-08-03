package vk.dentttt.instazoo.dtos;

import lombok.Value;

import javax.validation.constraints.NotEmpty;

@Value
public class CommentDto {

    Long id; //TODO логичнее добавить ИД поста сюда, так как комментарий не имеет смысла без поста

    @NotEmpty //TODO добавить сообщения об ошибках
    String content;

    @NotEmpty
    String username;

}
