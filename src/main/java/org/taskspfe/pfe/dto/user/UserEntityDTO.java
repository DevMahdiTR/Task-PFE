package org.taskspfe.pfe.dto.user;



import org.taskspfe.pfe.model.role.Role;

import java.util.List;
import java.util.UUID;


public record UserEntityDTO (
            UUID id,
            String firstName,
            String lastName,
            String email,
            boolean isEnabled,
            Role role
        )
{


}
