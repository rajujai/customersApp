package com.customers.app.services.external;

import com.customers.app.entities.AuthUser;
import com.customers.app.entities.Customer;
import com.customers.app.exceptions.EntityNotFoundException;
import com.customers.app.exceptions.SyncCustomerException;
import com.customers.app.repositories.AuthUserRepository;
import com.customers.app.repositories.CustomerRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncCustomerService {
    private final CustomerRepository repository;
    private final AuthUserRepository authUserRepository;
    private final RestClient restClient;

    public void syncCustomers() throws EntityNotFoundException, SyncCustomerException {
        var customers = fetchCustomersFromExternalSource();
        Map<String, Customer> uuidCustomerMap = new HashMap<>();
        customers.forEach(customer -> {
//String str = String.format("%s_%s_%s_%s", customer.getFirstName(), customer.getLastName(), customer.getEmail(), customer.getPhone());
            uuidCustomerMap.put(customer.getUuid(), customer);
        });
//todo: should perform the task in new thread;
//if(emailCustomerMap.keySet().size()>1000){}
        var existingCustomers = repository.findAllByUuidIn(uuidCustomerMap.keySet());
        existingCustomers.forEach(existingCustomer -> {
            var customer = uuidCustomerMap.get(existingCustomer.getUuid());
            updateCustomer(existingCustomer, customer);
            uuidCustomerMap.remove(existingCustomer.getUuid());
        });
        repository.saveAll(existingCustomers);
        repository.saveAll(uuidCustomerMap.values());
        log.info("{} Customers synced, updated: {}, added: {}", customers.size(), existingCustomers.size(), uuidCustomerMap.size());
    }

    private void updateCustomer(Customer existing, Customer toUpdate) {
        existing.setFirstName(update(existing.getFirstName(), toUpdate.getFirstName()));
        existing.setLastName(update(existing.getLastName(), toUpdate.getLastName()));
        existing.setEmail(update(existing.getEmail(), toUpdate.getEmail()));
        existing.setPhone(update(existing.getPhone(), toUpdate.getPhone()));
        existing.setAddress(update(existing.getAddress(), toUpdate.getAddress()));
        existing.setStreet(update(existing.getStreet(), toUpdate.getStreet()));
        existing.setCity(update(existing.getCity(), toUpdate.getCity()));
        existing.setState(update(existing.getState(), toUpdate.getState()));
    }

    private <T> T update(T existing, T toUpdate) {
        return toUpdate == null ? existing : toUpdate;
    }

    private List<Customer> fetchCustomersFromExternalSource() throws EntityNotFoundException, SyncCustomerException {
        final String accessToken = getAccessToken();
        if (Strings.isEmpty(accessToken)) throw new SyncCustomerException("Failed to get access token");
        log.info("Fetching customers using external api");
        final String GET_CUSTOMERS_URI = "/assignment.jsp?cmd=get_customer_list";
        ResponseEntity<Customer[]> responseEntity = restClient.get()
                .uri(GET_CUSTOMERS_URI)
                .header("Authorization", "Bearer " + accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(Customer[].class);
        if (responseEntity.getStatusCode().value() != 200 && ObjectUtils.isEmpty(responseEntity.getBody())) {
            throw new SyncCustomerException("Failed to fetch customers from external source");
        }
        log.info("Customers data fetched successfully. Data size: {}", Objects.requireNonNull(responseEntity.getBody()).length);
        return Arrays.asList(responseEntity.getBody());
    }

    private String getAccessToken() throws SyncCustomerException, EntityNotFoundException {
        final String LOGIN_URI = "/assignment_auth.jsp";
        final AuthUser user = authUserRepository.findById("sunbase-user").orElseThrow(
                () -> new EntityNotFoundException(("Could not find user with id: sunbase-user")));

        ResponseEntity<TokenResponse> responseEntity = restClient.post()
                .uri(LOGIN_URI)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("login_id", user.getEmail(), "password", user.getPassword()))
                .retrieve()
                .toEntity(TokenResponse.class);

        if (responseEntity.getStatusCode().value() != 200 || ObjectUtils.isEmpty(responseEntity.getBody())) {
            throw new SyncCustomerException("Failed to get access token");
        }
        log.info("Access token fetched: {}", responseEntity.getBody());
        return responseEntity.getBody().getAccess_token();
    }

    @Data
    private static class TokenResponse {
        private String access_token;
    }
}
