package fooddeliverybh.infra;
import fooddeliverybh.domain.*;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import org.springframework.hateoas.EntityModel;

@Component
public class StoreOrderHateoasProcessor implements RepresentationModelProcessor<EntityModel<StoreOrder>>  {

    @Override
    public EntityModel<StoreOrder> process(EntityModel<StoreOrder> model) {
        model.add(Link.of(model.getRequiredLink("self").getHref() + "/finish-cook").withRel("finish-cook"));
        model.add(Link.of(model.getRequiredLink("self").getHref() + "/accept").withRel("accept"));
        model.add(Link.of(model.getRequiredLink("self").getHref() + "/reject").withRel("reject"));
        model.add(Link.of(model.getRequiredLink("self").getHref() + "/start-cook").withRel("start-cook"));

        
        return model;
    }
    
}
