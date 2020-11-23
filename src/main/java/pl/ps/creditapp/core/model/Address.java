package pl.ps.creditapp.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.ps.creditapp.core.annotation.NotNull;

import java.io.Serializable;
import java.util.Objects;

public class Address implements Serializable {
    public static final long serialVersionUID = 1l;
    @NotNull
    @JsonProperty
    private String street;
    @NotNull
    @JsonProperty
    private String city;
    @NotNull
    @JsonProperty
    private String zipCode;
    @NotNull
    @JsonProperty
    private String state;
    @NotNull
    @JsonProperty
    private String houseNumber;

    public Address(){}
    public Address(String street, String city, String zipCode, String state, String houseNumber) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.state = state;
        this.houseNumber = houseNumber;
    }

    @Override
    public boolean equals(Object o) {
        Address address = (Address) o;
        return street.equalsIgnoreCase(address.street) &&
                city.equalsIgnoreCase(address.city) &&
                zipCode.equalsIgnoreCase(address.zipCode) &&
                state.equalsIgnoreCase(address.state) &&
                houseNumber.equalsIgnoreCase(address.houseNumber);
    }

}
