# Trabalho Prático - Engenharia de Software II

### Nomes:

- Bernando Gomes Nunes
- Fernando Eduardo Pinto Moreira
- Thiago Almeida Campos

### Explicação do Sistema

O sistema criado trata-se de um sistema para marcação de consultas médicas ou testes de covid. Dentre as funcionalidades, o usuário consegue verificar os horários disponíveis, ver as consultas marcadas, marcar uma consulta médica ou um teste de covid, podendo selecionar o horário da consulta a partir de uma lista de horários disponíveis. Além disso, o usuário também pode escolher um médico de sua preferência e verificar as informações da consulta/teste marcado, cancelar uma consultar e até filtrar por dia. Caso o usuário do sistema seja um profissional, ele pode ver as suas consultas, verificar se algum equipamento está disponível, adicionar unidades de um equipamento, dentre outros.

Foram criados uma classe para o médico (Doctor), enfermeiro (Nurse) e o paciente (Patient) em que o usuário consegue marcar uma consulta com um médico (DoctorAppointment) ou um teste de covid (CovidTestAppointment) com um enfermeiro. Ele pode marcar um horário com as funções da classe Calendar. As classes Doctor e Nurse são extendidas da superclasse Professional, as classes DoctorAppointment e CovidTestAppointment extendem de Appointment e foram criadas as classes Equipment e Storage para fazer o controle dos equipamentos.

O sistema não possui interface gráfica, mas é exibido um menu para o usuário onde ele pode fazer as suas escolhas. 

### Explicação das Tecnologias Utilizadas

Para a construção do sistema, utilizou-se a linguagem Java. 

Para realizar a verificação da qualidade do sistema, foi utilizado a ferramenta Lizard (https://github.com/terryyin/lizard), em que notou-se que as 3 funções mais complexas são a handleDoctor, handlePatient e handleNurse. Com isto, foi realizada uma refatoração em tais funções.