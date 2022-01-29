package guru.sfg.beer.inventory.service.services;

import guru.sfg.brewery.model.BeerOrderDto;
import guru.sfg.brewery.model.events.AllocateOrderRequest;
import guru.sfg.brewery.model.events.AllocateOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import static guru.sfg.beer.inventory.service.config.JmsConfig.ALLOCATE_ORDER_QUEUE;
import static guru.sfg.beer.inventory.service.config.JmsConfig.ALLOCATE_ORDER_RESPONSE_QUEUE;

@Component
@Slf4j
@RequiredArgsConstructor
public class AllocationOrderListener {
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = ALLOCATE_ORDER_QUEUE)
    public void onAllocationRequest(AllocateOrderRequest orderRequest) {
        BeerOrderDto beerOrderDto = orderRequest.getBeerOrderDto();

        AllocateOrderResponse orderResponse = AllocateOrderResponse.builder()
                .beerOrderDto(beerOrderDto)
                .build();
        jmsTemplate.convertAndSend(ALLOCATE_ORDER_RESPONSE_QUEUE, orderResponse);
    }
}
