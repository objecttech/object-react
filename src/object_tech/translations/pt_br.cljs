(ns objecttech.translations.pt-br)

(def translations
  {
   ;common
   :members-title                         "Membros"
   :not-implemented                       "não implementado"
   :chat-name                             "Nome do chat"
   :notifications-title                   "Notificações e sons"
   :offline                               "Offline"

   ;drawer
   :invite-friends                        "Convidar amigos"
   :faq                                   "Dúvidas frequentes"
   :switch-users                          "Trocar usuário"

   ;chat
   :is-typing                             "está digitando"
   :and-you                               "e você"
   :search-chat                           "Pesquisar chat"
   :members                               {:one   "1 membro"
                                           :other "{{count}} membros"
                                           :zero  "nenhum membro"}
   :members-active                        {:one   "1 membro, 1 ativo"
                                           :other "{{count}} membros, {{count}} ativo"
                                           :zero  "nenhum membro"}
   :active-online                         "Online"
   :active-unknown                        "Desconhecido"
   :available                             "Disponível"
   :no-messages                           "Nenhuma mensagem"
   :suggestions-requests                  "Solicitações"
   :suggestions-commands                  "Comandos"

   ;sync
   :sync-in-progress                      "Sincronizando..."
   :sync-synced                           "Sincronizado"

   ;messages
   :object-sending                        "Enviando..."
   :object-pending                        "Pendente"
   :object-sent                           "Enviado"
   :object-seen-by-everyone               "Visto por todos"
   :object-seen                           "Visto"
   :object-delivered                      "Entregue"
   :object-failed                         "Malsucedido"

   ;datetime
   :datetime-second                       {:one   "segundo"
                                           :other "segundos"}
   :datetime-minute                       {:one   "minuto"
                                           :other "minutos"}
   :datetime-hour                         {:one   "hora"
                                           :other "horas"}
   :datetime-day                          {:one   "dia"
                                           :other "dias"}
   :datetime-multiple                     "s"
   :datetime-ago                          "atrás"
   :datetime-yesterday                    "ontem"
   :datetime-today                        "hoje"

   ;profile
   :profile                               "Perfil"
   :report-user                           "Denunciar usuário"
   :message                               "Mensagem"
   :username                              "Nome de usuário"
   :not-specified                         "Não especificado"
   :public-key                            "Chave pública"
   :phone-number                          "Número de telefone"
   :email                                 "E-mail"
   :profile-no-object                     "Nenhum object"
   :add-to-contacts                       "Adicionar aos contatos"
   :error-incorrect-name                  "Nome incorreto"
   :error-incorrect-email                 "E-mail incorreto"

   ;;make_photo
   :image-source-title                    "Imagem do perfil"
   :image-source-make-photo               "Tirar foto"
   :image-source-gallery                  "Escolher na galeria"
   :image-source-cancel                   "Cancelar"

   ;sign-up
   :contacts-syncronized                  "Seus contatos foram sincronizados"
   :confirmation-code                     (str "Obrigado! Nós lhe enviamos uma mensagem de texto com um código de "
                                               "confirmação. Por favor, informe esse código para confirmar seu número de telefone")
   :incorrect-code                        (str "Desculpe, o código estava incorreto. Por favor, digite novamente")
   :generate-passphrase                   (str "Vou gerar uma frase secreta para você poder restaurar o seu "
                                               "acesso ou entrar a partir de outro dispositivo")
   :phew-here-is-your-passphrase          "*Ufa* isso foi difícil. Aqui está a sua frase secreta. *Anote-a e guarde-a em segurança!* Você precisará dela para recuperar a sua conta."
   :here-is-your-passphrase               "Aqui está a sua frase secreta. *Anote-a e guarde-a em segurança!* Você precisará dela para recuperar a sua conta."
   :written-down                          "Certifique-se de tê-la anotado em segurança"
   :phone-number-required                 "Toque aqui para inserir seu número de telefone e eu vou encontrar seus amigos"
   :intro-object                          "Converse comigo para configurar a sua conta e alterar suas definições!"
   :intro-message1                        "Bem-vindo ao Object\nToque nesta mensagem para definir sua senha e começar!"
   :account-generation-message            "Mê dê um segundinho. Tenho de fazer uns cálculos malucos para gerar a sua conta!"

   ;chats
   :chats                                 "Chats"
   :new-chat                              "Novo chat"
   :new-group-chat                        "Novo grupo de chat"

   ;discover
   :discover                              "Descobrir"
   :none                                  "Nenhum(a)"
   :search-tags                           "Digite suas tags de pesquisa aqui"
   :popular-tags                          "Tags populares"
   :recent                                "Recentes"
   :no-objectes-discovered                "Nenhum object descoberto"

   ;settings
   :settings                              "Configurações"

   ;contacts
   :contacts                              "Contatos"
   :new-contact                           "Novo contato"
   :show-all                              "Mostrar Todos"
   :contacts-group-dapps                  "DApps"
   :contacts-group-people                 "Pessoas"
   :contacts-group-new-chat               "Iniciar novo chat"
   :no-contacts                           "Você ainda não tem contatos"
   :show-qr                               "Mostrar QR"

   ;group-settings
   :remove                                "Remover"
   :save                                  "Salvar"
   :change-color                          "Alterar cor"
   :clear-history                         "Apagar histórico"
   :delete-and-leave                      "Excluir e sair"
   :chat-settings                         "Configurações do chat"
   :edit                                  "Editar"
   :add-members                           "Adicionar membros"
   :blue                                  "Azul"
   :purple                                "Roxo"
   :green                                 "Verde"
   :red                                   "Vermelho"

   ;commands
   :money-command-description             "Enviar dinheiro"
   :location-command-description          "Enviar localização"
   :phone-command-description             "Enviar número de telefone"
   :phone-request-text                    "Solicitação de número de telefone"
   :confirmation-code-command-description "Enviar código de confirmação"
   :confirmation-code-request-text        "Solicitação de código de confirmação"
   :send-command-description              "Enviar localização"
   :request-command-description           "Enviar solicitação"
   :keypair-password-command-description  ""
   :help-command-description              "Ajuda"
   :request                               "Solicitar"
   :chat-send-eth                         "{{amount}} ETH"
   :chat-send-eth-to                      "{{amount}} ETH para {{chat-name}}"
   :chat-send-eth-from                    "{{amount}} ETH de {{chat-name}}"

   ;new-group
   :group-chat-name                       "Nome do chat"
   :empty-group-chat-name                 "Por favor, informe um nome"
   :illegal-group-chat-name               "Por favor, selecione outro nome"

   ;participants
   :add-participants                      "Adicionar participantes"
   :remove-participants                   "Remover participantes"

   ;protocol
   :received-invitation                   "recebeu o convite para o chat"
   :removed-from-chat                     "removeu você do grupo"
   :left                                  "saiu"
   :invited                               "convidou"
   :removed                               "removeu"
   :You                                   "Você"

   ;new-contact
   :add-new-contact                       "Adicionar novo contato"
   :import-qr                             "Importar"
   :scan-qr                               "Escanear QR"
   :name                                  "Nome"
   :whisper-identity                      "Identidade Whisper"
   :address-explication                   "Talvez aqui deveria haver algum texto explicando o que é um endereço e onde procurá-lo"
   :enter-valid-address                   "Por favor, digite um endereço válido ou escaneie um código QR"
   :contact-already-added                 "O contato já foi adicionado"
   :can-not-add-yourself                  "Não é possível adicionar a si mesmo"
   :unknown-address                       "E-mail desconhecido"


   ;login
   :connect                               "Conectar"
   :address                               "Endereço"
   :password                              "Senha"
   :login                                 "Entrar"
   :wrong-password                        "Senha incorreta"

   ;recover
   :recover-from-passphrase               "Recuperar a partir da frase secreta"
   :recover-explain                       "Por favor, digite a frase secreta da sua senha para recuperar o acesso"
   :passphrase                            "Frase secreta"
   :recover                               "Recuperar"
   :enter-valid-passphrase                "Por favor, digite uma frase secreta"
   :enter-valid-password                  "Por favor, digite uma senha"

   ;accounts
   :recover-access                        "Recuperar o acesso"
   :add-account                           "Adicionar conta"

   ;wallet-qr-code
   :done                                  "Concluído"
   :main-wallet                           "Carteira principal"

   ;validation
   :invalid-phone                         "Número de telefone inválido"
   :amount                                "Quantia"
   :not-enough-eth                        (str "ETH insuficiente no saldo "
                                               "({{balance}} ETH)")
   ;transactions
   :confirm-transactions                  {:one   "Confirmar a transação"
                                           :other "Confirmar {{count}} transações"
                                           :zero  "Nenhuma transação"}
   :object                                "Object"
   :pending-confirmation                  "Confirmação pendente"
   :recipient                             "Destinatário"
   :one-more-item                         "Mais um item"
   :fee                                   "Tarifa"
   :value                                 "Valor"

   ;:webview
   :web-view-error                        "Ops, erro"

   :confirm                               "Confirmar"
   :phone-national                        "Nacional"
   :transactions-confirmed                {:one   "Transação confirmada"
                                           :other "{{count}} transações confirmadas"
                                           :zero  "Nenhuma transação confirmada"}
   :public-group-topic                    "Assunto"
   :debug-enabled                         "O servidor de debug foi inicializado! Você agora pode adicionar seu DApp ao executar *object-dev-cli scan* a partir de seu computador."
   :new-public-group-chat                 "Juntar-se a bate-papo público."
   :datetime-ago-format                   "{{number}} {{time-intervals}} {{ago}}"
   :sharing-cancel                        "Cancelar"
   :share-qr                              "Compartilhar QR"
   :feedback                              "Tem comentários a fazer?\nAgite seu telefone!"
   :twelve-words-in-correct-order         "12 palavras na ordem correta"
   :remove-from-contacts                  "Remover dos contatos"
   :delete-chat                           "Excluir bate-papo"
   :edit-chats                            "Editar bate-papos"
   :sign-in                               "Entrar"
   :create-new-account                    "Criar nova conta"
   :sign-in-to-object                     "Entrar para Object"
   :got-it                                "Entendido"
   :move-to-internal-failure-message      "Precisamos mover alguns arquivos importantes do armazenamento externo para o interno. Para fazer isso, precisamos de sua permissão. Não utilizaremos armazenamento externo em versões futuras."
   :edit-group                            "Editar grupo"
   :delete-group                          "Excluir grupo"
   :browsing-title                        "Buscar"
   :reorder-groups                        "Reorganizar grupos"
   :browsing-cancel                       "Cancelar"
   :faucet-success                        "Requisição faucet foi recebida"
   :choose-from-contacts                  "Selecionar a partir dos contatos"
   :new-group                             "Novo grupo"
   :phone-e164                            "Internacional 1"
   :remove-from-group                     "Remover do grupo"
   :search-contacts                       "Buscar contatos"
   :transaction                           "Transação"
   :public-group-object                   "Público"
   :leave-chat                            "Sair do bate-papo"
   :start-conversation                    "Iniciar conversa"
   :topic-format                          "Formato errado [a-z0-9\\-]+"
   :enter-valid-public-key                "Por favor, insira uma chave pública válida ou capture um código QR"
   :faucet-error                          "Erro na requisição faucet"
   :phone-significant                     "Significante"
   :search-for                            "Buscar por..."
   :sharing-copy-to-clipboard             "Copiar para área de transferência"
   :phone-international                   "Internacional 2"
   :enter-address                         "Inserir endereço"
   :send-transaction                      "Enviar transação"
   :delete-contact                        "Excluir contato"
   :mute-notifications                    "Silenciar notificações"


   :contact-s                             {:one   "contato"
                                           :other "contatos"}
   :group-name                            "Nome do grupo"
   :next                                  "Próximo"
   :from                                  "De"
   :search-chats                          "Buscar bate-papos"
   :in-contacts                           "Em contatos"

   :sharing-share                         "Compartilhar..."
   :type-a-message                        "Digite uma mensagem..."
   :type-a-command                        "Comece a digitar um comando..."
   :shake-your-phone                      "Encontrou um erro ou tem uma sugestão? Basta ~agitar~ seu telefone!"
   :object-prompt                         "Crie um object para ajudar as pessoas a saberem sobre as coisas que você está oferecendo. Você também pode utilizar #hashtags."
   :add-a-object                          "Adicionar um object..."
   :error                                 "Erro"
   :edit-contacts                         "Editar contatos"
   :more                                  "mais"
   :cancel                                "Cancelar"
   :no-objectes-found                     "Nenhum object encontrado"
   :swow-qr                               "Exibir QR"
   :browsing-open-in-web-browser          "Abrir no navegador de internet"
   :delete-group-prompt                   "Isso não afetará os contatos"
   :edit-profile                          "Editar perfil"


   :enter-password-transactions           {:one   "Confirme a transação ao inserir sua senha"
                                           :other "Confirme as transações ao inserir sua senha"}
   :unsigned-transactions                 "Transações não assinadas"
   :empty-topic                           "Assunto vazio"
   :to                                    "Para"
   :group-members                         "Participantes do grupo"
   :estimated-fee                         "Taxa estimada"
   :data                                  "Dados"})
