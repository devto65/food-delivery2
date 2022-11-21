package fooddeliverybh.infra;
import fooddeliverybh.domain.*;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.EntityModel;

@Component
public class NotificationLogHateoasProcessor implements RepresentationModelProcessor<EntityModel<NotificationLog>>  {

    @Override
    public EntityModel<NotificationLog> process(EntityModel<NotificationLog> model) {

        
        return model;
    }
    
}
