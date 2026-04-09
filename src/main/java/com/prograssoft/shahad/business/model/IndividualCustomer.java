package com.prograssoft.shahad.business.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.prograssoft.shahad.business.model.enums.CustomerType;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.UUID;

public class IndividualCustomer extends Customer {
    @NotBlank
    private String firstName;
    @NotBlank
    private  String lastName;
    @NotBlank
    private long nationalId;
    @NotBlank
    private LocalDate birthDate;

    public IndividualCustomer() {
        super(builder()
                .id(UUID.randomUUID().toString())
                .customerType(CustomerType.INDIVIDUAL)
                .regitrationDate(LocalDate.now())
        );
    }

    @JsonCreator
    public IndividualCustomer(
            @JsonProperty("firstName") String firstName,
            @JsonProperty("lastName") String lastName,
            @JsonProperty("nationalId") Long nationalId,
            @JsonProperty("birthDate") LocalDate birthDate
    ) {
        super(builder()
                .customerType(CustomerType.INDIVIDUAL)
                .regitrationDate(LocalDate.now())
        );
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationalId = nationalId;
        this.birthDate = birthDate;
    }

    public IndividualCustomer(Builder builder) {
        super(builder.customerType(CustomerType.INDIVIDUAL));
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.nationalId = builder.natiinoalId;
        this.birthDate = builder.birthDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getNationalId() {
        return nationalId;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNationalId(long nationalId) {
        this.nationalId = nationalId;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends Customer.Builder<Builder> {
        private String firstName;
        private  String lastName;
        private long natiinoalId;
        private LocalDate birthDate;

        protected Builder() {
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder firstName(String firstName){
            this.firstName=firstName;
            return this;
        }
        public Builder lastName(String lastName){
            this.lastName=lastName;
            return this;
        }

        public Builder nationalId(long natiinoalId){
            this.natiinoalId=natiinoalId;
            return this;
        }
        public Builder birthDate(LocalDate birthDate){
            this.birthDate=birthDate;
            return this;
        }
        public IndividualCustomer build(){
            return new IndividualCustomer(this);
        }
    }
}
