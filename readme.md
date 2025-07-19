# Teste Admissional Apisul - Análise de Elevadores

Este projeto foi desenvolvido como parte do processo seletivo da empresa Apisul. A implementação foi realizada em Java 8, embora possa ser adaptada para versões mais recentes como Java 11 ou Java 17 com possíveis ajustes dependendo da versão escolhida.

## Tecnologias Utilizadas
- Java 8
- Maven (gerenciamento de dependências e build)
- Gson (para parsing do arquivo JSON)

## Sobre o Projeto
O projeto utiliza Maven para gerenciamento de dependências e build, facilitando a execução e manutenção do código. A escolha do Java 8 foi feita para garantir compatibilidade, mas o código pode ser facilmente adaptado para versões mais recentes do Java.

## Descrição do Exercício

Suponha que a administração do prédio 99a da Tecnopuc, com 16 andares e cinco elevadores, denominados A, B, C, D e E, nos convidou a aperfeiçoar o sistema de controle dos elevadores. Depois de realizado um levantamento no qual cada usuário respondia:  
    a.	O elevador que utiliza com mais frequência (A, B, C, D ou E);  
    b.	O andar ao qual se dirigia (0 a 15);  
    c.	O período que utilizava o elevador – M: Matutino; V: Vespertino; N: Noturno.  

Considerando que este possa evoluir para um sistema dinâmico, escreva o código que nos ajude a extrair as seguintes informações:  
    a.	Qual é o andar menos utilizado pelos usuários;  
    b.	Qual é o elevador mais frequentado e o período que se encontra maior fluxo;  
    c.	Qual é o elevador menos frequentado e o período que se encontra menor fluxo;  
    d.	Qual o período de maior utilização do conjunto de elevadores;  
    e.	Qual o percentual de uso de cada elevador com relação a todos os serviços prestados;  

* Deve ser programado em Java ou C#.
* Para a realização do exercício você deve implementar a interface IElevadorService.
* Faça a leitura do arquivo input.json para ter acesso às entradas.