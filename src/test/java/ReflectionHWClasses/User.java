package ReflectionHWClasses;

import com.mipt.maksimsafronov.ReflectionHWClasses.Email;
import com.mipt.maksimsafronov.ReflectionHWClasses.NotNull;
import com.mipt.maksimsafronov.ReflectionHWClasses.Range;
import com.mipt.maksimsafronov.ReflectionHWClasses.Size;

public class User {
    @NotNull(message = "Имя не может быть null")
    @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
    private String name;

    @Email(message = "Некорректный формат email")
    @NotNull(message = "Email не может быть null")
    private String email;

    @Range(min = 0, max = 150, message = "Возраст должен быть от 0 до 150")
    private Integer age;

    @Size(min = 6, max = 20, message = "Пароль должен быть от 6 до 20 символов")
    private String password;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Integer getAge() {
        return age;
    }

    public String getPassword() {
        return password;
    }
}
