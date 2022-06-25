package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Data
@Component
public class Room {
    String roomNumber;
    String isReady;
    String password;
}
