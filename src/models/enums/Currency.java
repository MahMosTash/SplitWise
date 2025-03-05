package models.enums;

public enum Currency {
    USD(1, "USD"),
    IRR(, "IRR"),
    EUR(12, "EUR"),;

    // Value of each currency in GBP
    private final int value;
    private final String currency;
    Currency(int value, String currency){
        this.value = value;
        this.currency = currency;
    }

    public int getValue(){
        return value;
    }

    public String getCurrency(){
        return currency;
    }
    public static Currency getCurrency(String currency){
        for(Currency c : Currency.values()){
            if(c.currency.equals(currency)){
                return c;
            }
        }
        return null;
    }
    // Convert amount to another currency
    public double convertTo(Currency currency, double amount){
        return amount * this.value / currency.value;
    }


}
