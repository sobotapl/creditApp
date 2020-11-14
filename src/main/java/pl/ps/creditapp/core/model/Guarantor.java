package pl.ps.creditapp.core.model;

import java.util.Objects;

public class Guarantor implements Comparable<Guarantor> {
    private final String pesel;
    private final Integer age;

    public Guarantor(String pesel, Integer age) {
        this.pesel = pesel;
        this.age = age;
    }

    public String getPesel() {
        return pesel;
    }

    public Integer getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guarantor guarantor = (Guarantor) o;
        return pesel.equals(guarantor.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pesel);
    }

    @Override
    public int compareTo(Guarantor g) {
        if (g.pesel.compareTo(this.pesel) != 0) {
            return g.pesel.compareTo(this.pesel);
        }
        return this.age.compareTo(g.age);
    }

    public static class Builder {
        private String pesel;
        private Integer age;

        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder withPesel(String pesel) {
            this.pesel = pesel;
            return this;
        }

        public Builder withAge(int age) {
            this.age = age;
            return this;
        }

        public Guarantor build() {
            return new Guarantor(pesel, age);
        }
    }
}
