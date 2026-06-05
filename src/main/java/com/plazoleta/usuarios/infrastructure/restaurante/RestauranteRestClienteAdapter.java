package com.plazoleta.usuarios.infrastructure.restaurante;

import com.plazoleta.usuarios.infrastructure.restaurante.dto.AsociarEmpleadoRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestauranteRestClienteAdapter {

    private final RestTemplate restTemplate;
    private final String msRestaurantesUrl;

    public RestauranteRestClienteAdapter(RestTemplate restTemplate,
                                          @Value("${ms-restaurantes.url}") String msRestaurantesUrl) {
        this.restTemplate = restTemplate;
        this.msRestaurantesUrl = msRestaurantesUrl;
    }

    public void asociarEmpleado(Long idEmpleado, Long idCargo, String token) {
        String url = msRestaurantesUrl + "/restaurantes/empleados";
        AsociarEmpleadoRequest body = new AsociarEmpleadoRequest(idEmpleado, idCargo);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<AsociarEmpleadoRequest> request = new HttpEntity<>(body, headers);

        restTemplate.exchange(url, HttpMethod.POST, request, Void.class);
    }
}
