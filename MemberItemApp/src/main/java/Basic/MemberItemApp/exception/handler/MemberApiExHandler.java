package Basic.MemberItemApp.exception.handler;

import Basic.MemberItemApp.controller.MemberController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = ResponseBody.class, assignableTypes = MemberController.class)
public class MemberApiExHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> t(MethodArgumentNotValidException exception) {
        return new ResponseEntity<>("shit", HttpStatus.BAD_REQUEST);
    }
}
