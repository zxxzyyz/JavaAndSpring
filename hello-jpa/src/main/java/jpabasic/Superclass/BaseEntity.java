package jpabasic.Superclass;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
