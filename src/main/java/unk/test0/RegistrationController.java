package unk.test0;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
public class RegistrationController {

    private boolean usernameAlreadyExists;

    @RequestMapping(method = RequestMethod.POST,
            value = "/register",
            produces = APPLICATION_JSON_VALUE)
    public UserData register(@RequestBody User user) {
        /**/
        if(usernameAlreadyExists) {
            throw new IllegalArgumentException("error.username");
        }
        /**/
        return new UserData();
    }

    @ExceptionHandler
    void handleIllegalArgumentException(
            IllegalArgumentException e,
            HttpServletResponse response) throws IOException {

        response.sendError(HttpStatus.BAD_REQUEST.value());

    }
}