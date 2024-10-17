package tradingcarbon.my_app.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProjectDTO {

    private UUID projectId;

    @Size()
    private String projectName;

    @Size()
    private String projectAddress;

    @Size()
    private String projectSize;

    @Size()
    private String projectTimeStart;

    @Size()
    private String projectTimeEnd;

    @Size()
    private String projectRangeCarbon;

    @Size()
    private String organizationProvide;

    @Size()
    private String numberCarBonCredit;

    @Size()
    private String creditTimeStart;

    @Size()
    private String price;

    @Size()
    private String methodPayment;

    @Size()
    private String projectCredit;

    @Size()
    private String creditDetail;

    @Size()
    private String creditId;

    private List<@Size() String> image;

    @NotNull
    private UUID userId;

    private Long reviewProjectId;

}
