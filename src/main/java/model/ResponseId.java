package model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AllArgsConstructor;
import lombok.Builder;

//Just a silly class, to return id when POST/CREATE operations
@Builder
@RegisterForReflection
public class ResponseId {
    public Long id;
}
