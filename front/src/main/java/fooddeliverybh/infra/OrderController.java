package fooddeliverybh.infra;
import fooddeliverybh.domain.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

@RestController
// @RequestMapping(value="/orders")
@Transactional
public class OrderController {
    @Autowired
    OrderRepository orderRepository;





    @RequestMapping(value = "orders/{id}/evaluate",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8")
    public Order evaluate(@PathVariable(value = "id") Long id,
            @RequestBody EvaluateCommand evaluateCommand,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
            System.out.println("##### /order/evaluate  called #####");
            Optional<Order> optionalOrder = orderRepository.findById(id);
            
            optionalOrder.orElseThrow(()-> new Exception("No Entity Found"));
            Order order = optionalOrder.get();
            order.evaluate(evaluateCommand);
            orderRepository.save(order);
            return order;
            
    }
    



}
