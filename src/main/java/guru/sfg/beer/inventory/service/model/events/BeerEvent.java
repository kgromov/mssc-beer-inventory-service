package guru.sfg.beer.inventory.service.model.events;

import guru.sfg.beer.inventory.service.model.BeerDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerEvent implements Serializable {
    static final long serialVersionUID = 8014256150786196269L;

    private BeerDto beerDto;
}