package pl.ps.creditapp.core.bik;

public class ScoringRequest {

    private String pesel;
    private String nip;

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }
}
