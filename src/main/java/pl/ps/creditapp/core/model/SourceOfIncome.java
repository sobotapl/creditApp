package pl.ps.creditapp.core.model;

import pl.ps.creditapp.core.annotation.NotNull;

import java.io.Serializable;

public class SourceOfIncome implements Serializable {
    public static final long serialVersionUID =1l;
    @NotNull
    private final IncomeType incomeType;
    private final double netMonthlyIncome;


    public SourceOfIncome(IncomeType incomeType, double netMonthlyIncome) {
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
