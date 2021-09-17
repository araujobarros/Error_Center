package br.com.codenation.CentralDeErros.specification;

import br.com.codenation.CentralDeErros.model.ErrorEventLog;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

@And({
        @Spec(params = "level", path = "level", spec = Equal.class),
        @Spec(params = "log", path = "log", spec = Equal.class),
        @Spec(params = "origin", path = "origin", spec = Equal.class),
        @Spec(params = "description", path = "description", spec = LikeIgnoreCase.class),
        @Spec(params = "quantity", path = "quantity", spec = Equal.class),
        @Spec(path="createdAt", params={"registeredAfter","registeredBefore"}, spec= Between.class),
})
public interface ErrorEventLogSpec extends Specification<ErrorEventLog> {
}

