package tradingcarbon.my_app.service;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tradingcarbon.my_app.domain.Order;
import tradingcarbon.my_app.domain.Payment;
import tradingcarbon.my_app.model.PaymentDTO;
import tradingcarbon.my_app.repos.OrderRepository;
import tradingcarbon.my_app.repos.PaymentRepository;
import tradingcarbon.my_app.util.NotFoundException;
import tradingcarbon.my_app.util.ReferencedWarning;


@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentService(final PaymentRepository paymentRepository,
            final OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    public List<PaymentDTO> findAll() {
        final List<Payment> payments = paymentRepository.findAll(Sort.by("paymentId"));
        return payments.stream()
                .map(payment -> mapToDTO(payment, new PaymentDTO()))
                .toList();
    }

    public PaymentDTO get(final UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .map(payment -> mapToDTO(payment, new PaymentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public UUID create(final PaymentDTO paymentDTO) {
        final Payment payment = new Payment();
        mapToEntity(paymentDTO, payment);
        return paymentRepository.save(payment).getPaymentId();
    }

    public void delete(final UUID paymentId) {
        paymentRepository.deleteById(paymentId);
    }


    public ReferencedWarning getReferencedWarning(final UUID paymentId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(NotFoundException::new);
        final Order paymentIdOrder = orderRepository.findFirstByPaymentId(payment);
        if (paymentIdOrder != null) {
            referencedWarning.setKey("payment.order.paymentId.referenced");
            referencedWarning.addParam(paymentIdOrder.getOrderId());
            return referencedWarning;
        }
        return null;
    }

}
