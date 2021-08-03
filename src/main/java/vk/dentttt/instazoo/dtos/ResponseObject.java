package vk.dentttt.instazoo.dtos;

import lombok.Value;

@Value
public class ResponseObject {
    String message;

    public ResponseObject(String message) {
        this.message = message;
    }

}
