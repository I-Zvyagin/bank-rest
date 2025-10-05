package com.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос регистрации")
public class SignUpRequest {

    @Schema(description = "Имя пользователя", example = "Stan")
    @Size(min = 4, max = 50, message = "Имя пользователя должно содержать от 4 до 50 символов!")
    @NotBlank(message = "Имя пользователя не может быть пустым!")
    private String username;

    @Schema(description = "Адрес электронной почты", example = "stan@gmail.com")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 7 до 255 символов!")
    @NotBlank(message = "Адрес электронной почты не может быть пустым!")
    @Email(message = "Email адрес должен быть в формате \"user@example.com\"!")
    private String email;

    @Schema(description = "Пароль", example = "1234password")
    @Size(min = 8, max = 255, message = "Длина пароля должна быть от 8 до 255 символов!")
    private String password;
}
