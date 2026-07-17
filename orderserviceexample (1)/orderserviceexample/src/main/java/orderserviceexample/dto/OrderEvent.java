package orderserviceexample.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEvent {

    private Long orderId;
    private String email;
    private String mobile;
    private String status;

}
