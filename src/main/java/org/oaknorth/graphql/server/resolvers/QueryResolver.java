package org.oaknorth.graphql.server.resolvers;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.oaknorth.graphql.server.entity.BankDetails;
import org.oaknorth.graphql.server.exception.ValidationFailedException;
import org.oaknorth.graphql.server.service.BankService;
import org.oaknorth.graphql.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QueryResolver implements GraphQLQueryResolver {

    @Autowired
    private BankService bankService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAnyAuthority('USER','ADMIN','BANKER')")
    public List<BankDetails> allBank() {
            return bankService.bankDetailsList();
    }

    @PreAuthorize("!isAnonymous()")
    public BankDetails bank(String accountNumber) {
            return bankService.bankDetails(accountNumber).orElseThrow(()-> new ValidationFailedException("Bank details Not Found"));
        }
}
