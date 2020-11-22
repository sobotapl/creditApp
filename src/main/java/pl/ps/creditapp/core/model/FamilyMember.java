package pl.ps.creditapp.core.model;

import pl.ps.creditapp.core.annotation.NotNull;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

public class FamilyMember implements Comparable<FamilyMember>, Serializable {
    public static final long serialVersionUID =1l;
    @NotNull
    private final String name;
    @NotNull
    private final LocalDate birthDate;

    public FamilyMember(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    @Override
    public int compareTo(FamilyMember o) {
        return o.birthDate.compareTo(birthDate);
    }

    @Override
    public String toString() {
        return "FamilyMember{" +
                "name='" + name + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}
