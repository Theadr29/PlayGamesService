# PlayGamesService

# Tutorial de Implementação do Google Play Games Service

Este é um tutorial passo a passo sobre como implementar o Google Play Games Service em seu projeto.

## Referencias
-https://developers.google.com/games/services/v1/android/quickstart?hl=pt-br
-https://developer.android.com/games/pgs/android/android-signin?hl=pt-br

## Pré-requisitos

Antes de começar, certifique-se de ter:

- Uma conta no Google Cloud Platform (GCP)
- Uma conta Google Play Developers

## Passos

Siga os passos abaixo para implementar o Google Play Games Service em seu projeto:

1. **Criar um Projeto no Google Cloud Platform (GCP)**:
- Acesse o Console do GCP em [https://console.cloud.google.com/](https://console.cloud.google.com/) e crie um projeto

![Imagem 1](image/1.png)

2. **Clique em  APIS e serviços**:


![Imagem 2](image/2.png)


3. **Na opção de pesquisa, pesquise por Play Games ou Google Play Games Service e selecione a opção Google Play Games Service**:


![Imagem 3](image/3.png)


4. **Ao chegar na pagina detalhes da API do Google Play Games Service, ative-a**:


![Imagem 4](image/4.png)


5. **Após ativa-la clique em criar as credenciais do Google Play Games Services**:


![Imagem 5](image/5.png)


6. **Na tela de criar as credencias selecione a API do Google Play Games Services e escolha entre dados do usuario ou dados do aplicativo (atualmente eu selecionei a opção dados do usuario) e clique em PROXIMA**:


![Imagem 6](image/6.png)


7. **Na tela dos escopo, eu não inseri nenhum neste projeto e é opcional adicionar algum escopo por isso pode clicar em SALVAR E CONTINUAR**:
 

![Imagem 7](image/7.png)


8. **Na tela de criação das credenciais insira a sua chave SH1, se quiser use o seguinte comando no teminal do Android Studio ./gradlew signingReport**
   copie o SH1 e clique em CRIAR:


![Imagem 8](image/8.png)


9. **Aqui a sua credencial ja foi criada**:


![Imagem 9](image/9.png)


10. **Após configurar o seu projeto no Google Cloud nós vamos para o Google Play Console, na sua pagina inicial crie um app e selecione a opção **Jogo**
    aceite os termos e clique em criar app (É IMPORTANTE SELECIONAR A OPÇÃO JOGO PARA ATIVAR O GOOGLE PLAY GAMES NO SEU PROJETO)**:
 - Acesse o Google Play Console https://play.google.com/console/developers/?pli=1

![Imagem 10](image/10.png)


11. **Do lado esquerdo role o scrool até aparecer as opções do Google Play Games, que são os serviços relacionados a jogos do Google Play,
    clique nesa opção e após isso, clique em configurações, ao lado esquerdo aparecera as opções de configuração, selecione a opção **Criar serviços relacionados a 
    jogos do Google Play**, seleciona o **projeto que você foi criou e configurou com a API do Google Play Games Services**, certamente o Play Console       
    identificará**:


![Imagem 11](image/11.png)


12. **Após ter concluido todas as outras etapas, AGORA SIM podemos copiar o id do app e inserir no projeto, copie-o**:


![Imagem 12](image/12.png)


13. **Agora vamos trabalhar no Android Studio para finalizar, primeiro insira  o implementation a seguir no grandle do seu projeto
     implementation ("com.google.android.gms:play-services-games-v2:+") que é o SDK do Google Play Games Services**:


![Imagem 13](image/13.png)


14. **Crie uma String no seu arquivo de strings que é exatamente o id do seu aplicativo, ja configurado para trabalhar com o Google Play Games Service, exemplo:
    <string name="game_services_project_id">1079374394825</string>**:


![Imagem 14](image/14.png)


15. **Insira o meta-data que define o ID do aplicativo para o Google Play Games no seu Android Manifest
     <meta-data
            android:name="com.google.android.gms.games.APP_ID"
            android:value="@string/game_services_project_id"/>**:

![Imagem 15](image/15.png)


16. **Agora é só copiar o codigo da minha Main Activity e implementar, deixa estrelinha no repositorio se  este tutorial te ajudou.**:


![Imagem 16](image/16.png)



