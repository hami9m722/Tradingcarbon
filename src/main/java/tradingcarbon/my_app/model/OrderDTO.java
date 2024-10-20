package tradingcarbon.my_app.model;

import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderDTO {

    private Long orderId;

    @Size()
    private String numberCredits;

    @Size()
    private String price;

    @Size()
    private String total;

    @OrderOrderStatusIdUnique
    private Long orderStatusId;

    private UUID sellerId;

    private UUID buyerId;

    @OrderPaymentIdUnique
    private UUID paymentId;

    @OrderConstractIdUnique
    private Long constractId;

    private Long staffId;

}
