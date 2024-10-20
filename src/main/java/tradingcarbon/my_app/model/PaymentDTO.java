package tradingcarbon.my_app.model;

import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PaymentDTO {

    private UUID paymentId;

    @Size()
    private String datePayment;

    @Size()
    private String total;

    @Size()
    private String paymentNumber;

}
