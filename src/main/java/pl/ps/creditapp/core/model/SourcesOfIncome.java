package pl.ps.creditapp.core.model;

public class SourcesOfIncome {

    private final IncomeType incomeType;
    private final double netMonthlyIncome;


    public SourcesOfIncome(IncomeType incomeType, double netMonthlyIncome) {
        this.incomeType = incomeType;
        this.netMonthlyIncome = netMonthlyIncome;
    }

    public IncomeType getIncomeType() {
        return incomeType;
    }

    public double getNetMonthlyIncome() {
        return netMonthlyIncome;
    }
}
