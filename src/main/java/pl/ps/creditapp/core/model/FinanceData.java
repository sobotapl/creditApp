package pl.ps.creditapp.core.model;

public class FinanceData {

    private final SourcesOfIncome[] sourcesOfIncome;

    public FinanceData(SourcesOfIncome... sourcesOfIncome) {
        this.sourcesOfIncome = sourcesOfIncome;
    }

    public SourcesOfIncome[] getSourcesOfIncome() {
        return sourcesOfIncome;
    }
}
