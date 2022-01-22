package guru.sfg.beer.inventory.service.services;

import guru.sfg.beer.inventory.service.domain.BeerInventory;
import guru.sfg.beer.inventory.service.model.BeerDto;
import guru.sfg.beer.inventory.service.model.events.NewInventoryEvent;
import guru.sfg.beer.inventory.service.repositories.BeerInventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static guru.sfg.beer.inventory.service.config.JmsConfig.NEW_INVENTORY_QUEUE;

@Service
@Slf4j
@RequiredArgsConstructor
public class BeerInventoryListener {
    private final JmsTemplate jmsTemplate;
    private final BeerInventoryRepository beerInventoryRepository;

    @JmsListener(destination = NEW_INVENTORY_QUEUE)
    @Transactional
    public void onNewInventoryEvent(NewInventoryEvent event) {
        BeerDto beerDto = event.getBeerDto();
        BeerInventory beerInventory = BeerInventory.builder()
                .beerId(beerDto.getId())
                .upc(beerDto.getUpc())
                .quantityOnHand(beerDto.getQuantityOnHand())
                .build();
        log.info("Request {} to create new inventory received", beerDto);
        beerInventoryRepository.save(beerInventory);
    }
}
