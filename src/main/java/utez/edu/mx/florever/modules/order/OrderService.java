package utez.edu.mx.florever.modules.order;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utez.edu.mx.florever.modules.category.Category;
import utez.edu.mx.florever.modules.category.CategoryService;
import utez.edu.mx.florever.modules.flowers.Flowers;
import utez.edu.mx.florever.modules.flowers.FlowersRepository;
import utez.edu.mx.florever.modules.order.dto.DtoOrderPayload;
import utez.edu.mx.florever.modules.orderhasflowers.OrderHasFlowers;
import utez.edu.mx.florever.modules.orderhasflowers.OrderHasFlowersRepository;
import utez.edu.mx.florever.modules.orderhasflowers.OrderHasFlowersService;
import utez.edu.mx.florever.modules.user.BeanUser;
import utez.edu.mx.florever.modules.user.UserService;
import utez.edu.mx.florever.security.jwt.JWTUtils;
import utez.edu.mx.florever.utils.APIResponse;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private OrderHasFlowersRepository orderHasFlowersRepository;
    @Autowired
    private FlowersRepository flowersRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public APIResponse findAllByStatus(String status , HttpServletRequest req) {
        try {


        if (status.equals("OPEN")){
            List<Order> ordersStatus  =  orderRepository.findAllByStatus("OPEN");
            if (ordersStatus.isEmpty())
            {
                return new APIResponse( HttpStatus.BAD_REQUEST, true,"No se encontro el registro");

            }
            return new APIResponse("Operacion exitosa", HttpStatus.CREATED, false,ordersStatus);
        }
        BeanUser beanUser = userService.getUserByMail(jwtUtils.resolveClaims(req, "sub"));

        List<Order> ordersStatus  =  orderRepository.findAllByStatusAndFlorist_Id(status, beanUser.getId());
        if (ordersStatus.isEmpty())
        {
            return new APIResponse( HttpStatus.BAD_REQUEST, true,"No se encontro el registro");

        }
        return new APIResponse("Operacion exitosa", HttpStatus.CREATED, false,ordersStatus);
        }
        catch (Exception e) {
            return new APIResponse( HttpStatus.BAD_REQUEST, false,"Error en la busqueda del registro");

        }
        }

    public Order findById(Long id) {
        return orderRepository.findById(id).get();
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public APIResponse save(DtoOrderPayload payload, HttpServletRequest req) {
        try{


        BeanUser beanUser = userService.getUserByMail(jwtUtils.resolveClaims(req, "sub"));
        Order order = new Order();
        order.setUser(beanUser);
        order.setStatus("OPEN");
        Category category = categoryService.findById(payload.getCategory());
        order.setCategory(category);
        order.setTotalPrice(category.getPrice());
        Double totalPrice = 0.0 ;
            for (int i = 0; i < payload.getFlowers().size(); i++) {

            OrderHasFlowers orderHasFlowers = new OrderHasFlowers();
            Optional<Flowers> flowers = flowersRepository.findById(payload.getFlowers().get(1).getFlowerId());
            if (flowers.isPresent()) {
                orderHasFlowers.setOrder(order);
                orderHasFlowers.setCuantity(payload.getFlowers().get(1).getCuantity());
                orderHasFlowers.setFlowers(flowers.get());
                orderHasFlowers.setPrice(payload.getFlowers().get(1).getCuantity() * flowers.get().getPrecio().doubleValue());
                totalPrice = totalPrice  + orderHasFlowers.getPrice();
                orderHasFlowersRepository.save(orderHasFlowers);
            }
            order.setTotalPrice(totalPrice);
            orderRepository.save(order);

        }


            return new APIResponse("Orden creada" , HttpStatus.CREATED , false ,  order);
        }catch (Exception e){
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Error al guardar la orden");

        }
    }

    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public APIResponse assign(Long id ,  HttpServletRequest req) {
        try {
            BeanUser beanUser = userService.getUserByMail(jwtUtils.resolveClaims(req, "sub"));
            Order order = orderRepository.findById(id).get();
            order.setFlorist(beanUser);
            order.setStatus("PROCESSING");
            orderRepository.save(order);
            return new APIResponse("Orden asignada correctamente" , HttpStatus.CREATED , false ,  order);

    }catch (Exception e){
        return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Error al asignar la orden");

    }

    }
    @Transactional(rollbackFor = {SQLException.class, Exception.class})
    public APIResponse closed(Long id ,  HttpServletRequest req) {
        try {
            BeanUser beanUser = userService.getUserByMail(jwtUtils.resolveClaims(req, "sub"));
            Order order = orderRepository.findByIdAndFlorist(id,beanUser).get(0);
            order.setStatus("CLOSED");
            orderRepository.save(order);
            return new APIResponse("Orden terminada correctamente" , HttpStatus.OK , false ,  order);

        }catch (Exception e){
            return new APIResponse(HttpStatus.INTERNAL_SERVER_ERROR, true, "Error al terminar la orden");

        }

    }

    public APIResponse get(Long id ) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isPresent()) {
           return new APIResponse("Orden terminada correctamente" , HttpStatus.OK , false ,  order);

        }
        return new APIResponse(HttpStatus.BAD_REQUEST, true, "Orden no encontrada");
    }
}
