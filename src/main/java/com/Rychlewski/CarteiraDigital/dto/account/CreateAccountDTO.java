package com.Rychlewski.CarteiraDigital.dto.account;

import com.Rychlewski.CarteiraDigital.enums.AccountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateAccountDTO {

    @NotNull(message = "Cliente é obrigatório")
    private Long clientId;

    @NotBlank(message = "Agência é obrigatória")
    private String agencia;

    @NotBlank(message = "Número da conta é obrigatório")
    private String numero;

    @NotNull(message = "Tipo da conta é obrigatório")
    private AccountType accountType;

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
