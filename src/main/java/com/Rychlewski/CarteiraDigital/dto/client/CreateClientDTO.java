package com.Rychlewski.CarteiraDigital.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CreateClientDTO {

    @NotBlank(message = "Nome não pode ser vazio")
    @Size(max = 120)
    private String name;

    @NotBlank(message = "CPF não pode ser vazio")
    @Pattern(
            regexp = "\\d{11}",
            message = "CPF deve conter exatamente 11 dígitos numéricos"
    )
    private String cpf;

    @NotBlank(message = "Endereço não pode ser vazio")
    @Size(max = 200)
    private String endereco;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
