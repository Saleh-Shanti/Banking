package com.sshanti.customerservice.control;

import com.sshanti.bank.model.dto.CustomerDto;
import com.sshanti.bank.model.enums.CustomerGender;
import com.sshanti.bank.model.enums.CustomerType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


import static com.sshanti.bank.exceptions.requests.Messages.*;

@Component
public class CustomerValidator {

    public List<String> isValidCustomer(CustomerDto customer) {
        List<String> errorMessages = new ArrayList<>();
        isValidName(customer.getName(), errorMessages);
        isValidType(customer.getCustomerType(), errorMessages);
        isValidAge(customer.getAge(), errorMessages);
        isValidGender(customer.getGender(), errorMessages);
        return errorMessages;
    }

    private void isValidName(String name, List<String> errorMessages) {
        if (name != null && !name.isEmpty()) return;
        errorMessages.add(EMPTY_NAME_MESSAGE.getMessage());
    }

    private void isValidType(String type, List<String> errorMessages) {
        if (type != null && CustomerType.getByName(type.toUpperCase()) != null) return;
        errorMessages.add(INVALID_CUSTOMER_TYPE.getMessage());
    }

    private void isValidAge(int age, List<String> errorMessages) {
        if (age >= 18 && age < 90) return;
        errorMessages.add(AGE_ERROR_MESSAGE.getMessage());
    }


    private void isValidGender(String gender, List<String> errorMessages) {
        if (gender != null && CustomerGender.getByName(gender) != null) return;
        errorMessages.add(INVALID_CUSTOMER_Gender.getMessage());
    }
}
