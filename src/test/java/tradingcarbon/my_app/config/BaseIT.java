package tradingcarbon.my_app.config;

import io.restassured.RestAssured;
import jakarta.annotation.PostConstruct;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.util.StreamUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import tradingcarbon.CarbonShopApplicationTest;
import tradingcarbon.my_app.repos.ChatParticipantsRepository;
import tradingcarbon.my_app.repos.ChatsRepository;
import tradingcarbon.my_app.repos.ContractRepository;
import tradingcarbon.my_app.repos.MessageStatusRepository;
import tradingcarbon.my_app.repos.MessagesRepository;
import tradingcarbon.my_app.repos.OrderRepository;
import tradingcarbon.my_app.repos.OrderStatusRepository;
import tradingcarbon.my_app.repos.PaymentRepository;
import tradingcarbon.my_app.repos.ProjectRepository;
import tradingcarbon.my_app.repos.ReviewCompanyRepository;
import tradingcarbon.my_app.repos.ReviewProjectRepository;
import tradingcarbon.my_app.repos.StaffRepository;
import tradingcarbon.my_app.repos.UserRepository;


@SpringBootTest(
        classes = CarbonShopApplicationTest.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("it")
@Sql("/data/clearAll.sql")
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
public abstract class BaseIT {

    @ServiceConnection
    private static final PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:17.0");

    static {
        postgreSQLContainer.withReuse(true)
                .start();
    }

    @LocalServerPort
    public int serverPort;

    @Autowired
    public ProjectRepository projectRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    public OrderRepository orderRepository;

    @Autowired
    public OrderStatusRepository orderStatusRepository;

    @Autowired
    public PaymentRepository paymentRepository;

    @Autowired
    public ContractRepository contractRepository;

    @Autowired
    public ReviewCompanyRepository reviewCompanyRepository;

    @Autowired
    public ReviewProjectRepository reviewProjectRepository;

    @Autowired
    public StaffRepository staffRepository;

    @Autowired
    public ChatsRepository chatsRepository;

    @Autowired
    public MessagesRepository messagesRepository;

    @Autowired
    public ChatParticipantsRepository chatParticipantsRepository;

    @Autowired
    public MessageStatusRepository messageStatusRepository;

    @PostConstruct
    public void initRestAssured() {
        RestAssured.port = serverPort;
        RestAssured.urlEncodingEnabled = false;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @SneakyThrows
    public String readResource(final String resourceName) throws IOException {
        return StreamUtils.copyToString(getClass().getResourceAsStream(resourceName), StandardCharsets.UTF_8);
    }

}
