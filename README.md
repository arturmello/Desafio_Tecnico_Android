# Desafio Técnico Android
Este é um projeto desenvolvido como parte de um desafio técnico para construção de um aplicativo Android utilizando Kotlin e arquitetura MVVM. O objetivo do projeto é exibir uma lista de voos com funcionalidades de filtro e ordenação, além de testes unitários e de interface.

## Funcionalidades

Exibição de voos de ida e volta em abas separadas.

Filtros por horário do dia (manhã, tarde, noite, madrugada) e por número de escalas.

Ordenação por preço (maior ou menor) e menor tempo de voo.

Persistência do estado dos filtros e ordenações ao navegar entre telas.

Integração com API para obtenção dos dados dos voos.

Testes unitários e de interface automatizados.

## Tecnologias Utilizadas

- Linguagem: Kotlin

- Arquitetura: MVVM (Model-View-ViewModel)

- Bibliotecas:

   - Retrofit para chamadas à API.

   - LiveData para gerenciamento de estados.

   - Espresso para testes de interface.

   - JUnit para testes unitários.

- Ferramentas: Android Studio, Gradle.

## Requisitos para Execução

- Android Studio: versão mais recente.

- JDK: 11 ou superior.

- Dispositivo Android ou Emulador: Android 5.0 (API 21) ou superior.

## Estrutura do Projeto

- view: Contém as telas do aplicativo, como MainActivity, FilterActivity e SortActivity.

- viewmodel: Contém o SharedViewModel, responsável por gerenciar os dados compartilhados entre as telas.

- model: Define as classes de modelo, como Flight, Pricing e OTA.

- network: Contém o cliente Retrofit e a interface para as chamadas à API.

- adapter: Gerencia a exibição de listas com o FlightAdapter.

- test e androidTest: Contêm os testes unitários e de interface.

## Configuração

1 - Clone este repositório:

     git clone https://github.com/seu-usuario/desafio-tecnico-android.git

2 - Abra o projeto no Android Studio.

3 - Sincronize as dependências do Gradle.

4 - Execute o projeto em um dispositivo ou emulador.

## Testes

O projeto possui dois tipos de testes:

#### Testes Unitários

   - Localizados na pasta test.

   - Utilizam JUnit para validar a lógica do ViewModel e outras classes.

   - Para executar, use o comando:

    ./gradlew testDebugUnitTest

#### Testes de Interface

  - Localizados na pasta androidTest.

  - Utilizam Espresso para simular interações do usuário.

  - Para executar, use o comando:

         ./gradlew connectedDebugAndroidTest
