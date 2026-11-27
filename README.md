# Orbit Alert

Sistema em Java, rodando no terminal, para cadastrar e acompanhar alertas ambientais (na Terra) e espaciais (captados por satélites e sondas), com regras de severidade próprias para cada tipo.

Global Solution, 1º semestre de 2026, FIAP 1TDSPW. É o back-end em Java do projeto; o front-end do time está em [GRibeiro-developer/OrbitalAlert](https://github.com/GRibeiro-developer/OrbitalAlert).

## Conceitos de POO aplicados

| Conceito | Onde |
| --- | --- |
| Classe abstrata | `Alerta`: base comum de todo alerta |
| Herança e polimorfismo | `AlertaAmbiental` e `AlertaEspacial` sobrescrevem as regras de severidade |
| Interface | `Notificavel`: contrato que todo alerta cumpre |
| Enum | `TipoAlerta` |
| Separação em camadas | `entities` (modelo), `beans` (regras do sistema), `view` (menu no terminal) |

## Estrutura

```
src/br/com/orbitalert/
  entities/  Alerta, AlertaAmbiental, AlertaEspacial, Notificavel, TipoAlerta
  beans/     OrbitAlertSistema: cadastro, listagem e regras
  view/      Main (ponto de entrada) e Terminal (menu)
docs/        OrbitAlert-Documentacao.pdf
```

## Como rodar

Com o JDK 17 ou mais recente:

```
javac -d out $(find src -name "*.java")
java -cp out br.com.orbitalert.view.Main
```

Ou abra a pasta no IntelliJ e rode `Main`.

A documentação completa está em [`docs/OrbitAlert-Documentacao.pdf`](docs/OrbitAlert-Documentacao.pdf).
