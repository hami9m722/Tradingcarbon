package tradingcarbon.my_app.service;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tradingcarbon.my_app.domain.Order;
import tradingcarbon.my_app.domain.OrderStatus;
import tradingcarbon.my_app.domain.Payment;
import tradingcarbon.my_app.domain.Staff;
import tradingcarbon.my_app.domain.User;
import tradingcarbon.my_app.model.OrderDTO;
import tradingcarbon.my_app.repos.UserRepository;
import tradingcarbon.my_app.util.NotFoundException;


@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private final ContractRepository contractRepository;
    private final StaffRepository staffRepository;

    public OrderService(final OrderRepository orderRepository,
            final OrderStatusRepository orderStatusRepository, final UserRepository userRepository,
            final PaymentRepository paymentRepository, final ContractRepository contractRepository,
            final StaffRepository staffRepository) {
        this.orderRepository = orderRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
        this.contractRepository = contractRepository;
        this.staffRepository = staffRepository;
    }

    public List<OrderDTO> findAll() {
        final List<Order> orders = orderRepository.findAll(Sort.by("orderId"));
        return orders.stream()
                .map(order -> mapToDTO(order, new OrderDTO()))
                .toList();
    }

    public OrderDTO get(final Long orderId) {
        return orderRepository.findById(orderId)
                .map(order -> mapToDTO(order, new OrderDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final OrderDTO orderDTO) {
        final Order order = new Order();
        mapToEntity(orderDTO, order);
        return orderRepository.save(order).getOrderId();
    }

    public void update(final Long orderId, final OrderDTO orderDTO) {
        final Order order = orderRepository.findById(orderId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(orderDTO, order);
        orderRepository.save(order);
    }


    public boolean orderStatusIdExists(final Long orderStatusId) {
        return orderRepository.existsByOrderStatusIdOrderStatusId(orderStatusId);
    }

    public boolean paymentIdExists(final UUID paymentId) {
        return orderRepository.existsByPaymentIdPaymentId(paymentId);
    }

    public boolean constractIdExists(final Long constractId) {
        return orderRepository.existsByConstractIdConstractId(constractId);
    }

}
