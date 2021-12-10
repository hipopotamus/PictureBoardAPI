package pictureboard.api.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UsernamePasswordForm {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
