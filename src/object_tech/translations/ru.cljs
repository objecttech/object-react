(ns objecttech.translations.ru)

(def translations
  {
   ;common
   :members-title                         "Участники"
   :not-implemented                       "!не реализовано"
   :chat-name                             "Имя чата"
   :notifications-title                   "Уведомления и звуки"
   :offline                               "Оффлайн"
   :cancel                                "Отмена"
   :next                                  "Продолжить"

   ;drawer
   :invite-friends                        "Пригласить друзей"
   :faq                                   "ЧАВО"
   :switch-users                          "Переключить пользователей"

   ;chat
   :is-typing                             "печатает"
   :and-you                               "и вы"
   :search-chat                           "Поиск по чату"
   :members                               {:one   "1 член"
                                           :other "{{count}} члена(ов)"
                                           :zero  "нет членов"}
   :members-active                        {:one   "1 член, 1 активный"
                                           :other "{{count}} члена(ов), {{count}} активных"
                                           :zero  "нет членов"}
   :active-online                         "В сети"
   :active-unknown                        "Неизвестно"
   :available                             "Доступно"
   :no-messages                           "Нет сообщений"
   :suggestions-requests                  "Запросы"
   :suggestions-commands                  "Команды"

   ;sync
   :sync-in-progress                      "Синхронизация..."
   :sync-synced                           "Синхронизируется"

   ;messages
   :object-sending                        "Отправляется"
   :object-pending                        "в ожидании"
   :object-sent                           "Отправлено"
   :object-seen-by-everyone               "Просмотрено всеми"
   :object-seen                           "Просмотрено"
   :object-delivered                      "Доставлено"
   :object-failed                         "Ошибка"

   ;datetime
   :datetime-second                       {:one   "секунда"
                                           :other "секунды"}
   :datetime-minute                       {:one   "минута"
                                           :other "минуты"}
   :datetime-hour                         {:one   "час"
                                           :other "часы"}
   :datetime-day                          {:one   "день"
                                           :other "дни"}
   :datetime-multiple                     "c"
   :datetime-ago                          "назад"
   :datetime-yesterday                    "вчера"
   :datetime-today                        "сегодня"

   ;profile
   :profile                               "Профиль"
   :report-user                           "ПОЖАЛОВАТЬСЯ НА ПОЛЬЗОВАТЕЛЯ"
   :message                               "Сообщение"
   :username                              "Имя пользователя"
   :not-specified                         "Не указано"
   :public-key                            "Публичный ключ"
   :phone-number                          "Номер телефона"
   :email                                 "Электронная почта"
   :profile-no-object                     "Нет статуса"
   :add-to-contacts                       "Добавить в контакты"
   :error-incorrect-name                  "Выберите другое имя"
   :error-incorrect-email                 "Неправильная электронная почта"

   ;;make_photo
   :image-source-title                    "Изображение профиля"
   :image-source-make-photo               "Сфотографировать"
   :image-source-gallery                  "Выбрать из галереи"
   :image-source-cancel                   "Отмена"

   ;;sharing
   :sharing-copy-to-clipboard             "Скопировать"
   :sharing-share                         "Поделиться..."
   :sharing-cancel                        "Отмена"

   ;sign-up
   :contacts-syncronized                  "Ваши контакты синхронизированы"
   :confirmation-code                     (str "Спасибо! Мы отправили вам СМС с кодом подтверждения."
                                               "Введите этот код для подтверждения своего номера телефона")
   :incorrect-code                        (str "Извините, код неправильный, введите еще раз")
   :generate-passphrase                   (str "Я создам для вас парольную фразу, чтобы вы смогли восстановить ваш"
                                               "доступ или войти с другого устройства")
   :phew-here-is-your-passphrase          (str "*Уф*, это было непросто, вот ваша парольная фраза, *запишите ее и сохраните в надежном месте!* "
                                               "Она будет нужна вам для восстановления аккаунта.")
   :here-is-your-passphrase               (str "Вот ваша парольная фраза, *запишите ее и сохраните в надежном месте!* "
                                               "Она будет нужна вам для восстановления аккаунта.")
   :written-down                          "Убедитесь, что вы записали ее в надежном месте"
   :phone-number-required                 "Нажмите сюда для ввода своего номера телефона и поиска своих друзей"
   :intro-object                          "Пообщайтесь со мной в чате, чтобы настроить свой аккаунт и изменить свои настройки!"
   :intro-message1                        "Добро пожаловать в Статус\nНажмите на это сообщение, чтобы установить пароль и начать!"
   :account-generation-message            "Секундочку, мне нужно выполнить безумно сложные расчеты для создания вашего аккаунта!"

   ;chats
   :chats                                 "Чаты"
   :new-chat                              "Новый чат"
   :new-group-chat                        "Новый групповой чат"

   ;discover
   :discover                              "Поиск"
   :none                                  "Нет"
   :search-tags                           "Введите теги для поиска сюда"
   :popular-tags                          "Популярные теги"
   :recent                                "Последние"
   :no-objectes-discovered                "Статусы не обнаружены"

   ;settings
   :settings                              "Настройки"

   ;contacts
   :contacts                              "Контакты"
   :new-contact                           "Новый контакт"
   :edit-contacts                         "Редактирование контактов"
   :show-all                              "ПОКАЗАТЬ ВСЕ"
   :contacts-group-dapps                  "ÐApps"
   :contacts-group-people                 "Люди"
   :contacts-group-new-chat               "Начать новый чат"
   :no-contacts                           "Пока нет контактов"
   :show-qr                               "Показать QR"
   :enter-address                         "Ввести адрес"

   ;group-settings
   :remove                                "Удалить"
   :save                                  "Сохранить"
   :change-color                          "Изменить цвет"
   :clear-history                         "Очистить историю"
   :delete-and-leave                      "Удалить и оставить"
   :chat-settings                         "Настройки чата"
   :edit                                  "Изменить"
   :add-members                           "Добавить членов"
   :blue                                  "Синий"
   :purple                                "Фиолетовый"
   :green                                 "Зеленый"
   :red                                   "Красный"

   ;commands
   :money-command-description             "Отправить деньги"
   :location-command-description          "Отправить местоположение"
   :phone-command-description             "Отправить номер телефона"
   :phone-request-text                    "Запрос номера телефона"
   :confirmation-code-command-description "Отправить код подтверждения"
   :confirmation-code-request-text        "Запрос кода подтверждения"
   :send-command-description              "Отправить местоположение"
   :request-command-description           "Отправить запрос"
   :keypair-password-command-description  ""
   :help-command-description              "Помощь"
   :request                               "Запрос"
   :chat-send-eth                         "{{amount}} ETH"
   :chat-send-eth-to                      "{{amount}} ETH в адрес {{chat-name}}"
   :chat-send-eth-from                    "{{amount}} ETH от {{chat-name}}"

   ;new-group
   :group-chat-name                       "Имя чата"
   :empty-group-chat-name                 "Введите имя"
   :illegal-group-chat-name               "Выберите другое имя"
   :new-group                             "Новая группа"
   :group-name                            "Название группы"
   :reorder-groups                        "Упорядочить группы"

   ;participants
   :add-participants                      "Добавить участников"
   :remove-participants                   "Удалить участников"

   ;protocol
   :received-invitation                   "получил(а) приглашение в чат"
   :removed-from-chat                     "удалил(а) вас из группового чата"
   :left                                  "осталось"
   :invited                               "приглашен(а)"
   :removed                               "удален(а)"
   :You                                   "Вы"

   ;new-contact
   :add-new-contact                       "Добавить новый контакт"
   :import-qr                             "Импорт"
   :scan-qr                               "Сканировать QR"
   :name                                  "Имя"
   :whisper-identity                      "Скрытая личность"
   :address-explication                   "Может быть, здесь должен быть какой-то текст, поясняющий адрес и то, где его искать"
   :enter-valid-address                   "Введите действительный адрес или сканируйте QR-код"
   :enter-valid-public-key                "Введите действительный публичный ключ или сканируйте QR-код"
   :contact-already-added                 "Контакт уже добавлен"
   :can-not-add-yourself                  "Вы не можете добавить себя"
   :unknown-address                       "Неизвестный адрес"


   ;login
   :connect                               "Подключиться"
   :address                               "Адрес"
   :password                              "Пароль"
   :login                                 "Вход"
   :wrong-password                        "Неверный пароль"

   ;recover
   :recover-from-passphrase               "Восстановление с помощью парольной фразы"
   :recover-explain                       "Введите парольную фразу вместо вашего пароля для восстановления доступа"
   :passphrase                            "Парольная фраза"
   :recover                               "Восстановить"
   :enter-valid-passphrase                "Введите парольную фразу"
   :enter-valid-password                  "Введите пароль"

   ;accounts
   :recover-access                        "Восстановить доступ"
   :add-account                           "Добавить аккаунт"

   ;wallet-qr-code
   :done                                  "Готово"
   :main-wallet                           "Основной кошелек"

   ;validation
   :invalid-phone                         "Неверный номер телефона"
   :amount                                "Сумма"
   :not-enough-eth                        (str "Не хватает ETH на балансе "
                                               "({{balance}} ETH)")
   ;transactions
   :confirm-transactions                  {:one   "Подтвердить транзакцию"
                                           :other "Подтвердите {{count}} транзакции(ий)"
                                           :zero  "Нет транзакций"}
   :object                                "Статус"
   :pending-confirmation                  "В ожидании подтверждения"
   :recipient                             "Получатель"
   :one-more-item                         "Еще одна позиция"
   :fee                                   "Комиссия"
   :value                                 "Сумма"

   ;:webview
   :web-view-error                        "ой, ошибка"

   :confirm                               "Подтвердить"
   :phone-national                        "Государственный"
   :transactions-confirmed                {:one   "Транзакция подтверждена"
                                           :other "Подтверждено {{count}} транзакции(й)"
                                           :zero  "Нет подтвержденных транзакций"}
   :public-group-topic                    "Тема"
   :debug-enabled                         "Запущен сервер отладки! Теперь вы можете добавить DApp, выполнив со своего компьютера *object-dev-cli scan*"
   :new-public-group-chat                 "Присоединиться к общему чату"
   :datetime-ago-format                   "{{number}} {{time-intervals}} {{ago}}"
   :share-qr                              "Поделиться QR"
   :feedback                              "Есть отзыв?\nВстряхните телефон!"
   :twelve-words-in-correct-order         "12 слов в нужном порядке"
   :remove-from-contacts                  "Удалить из контактов"
   :delete-chat                           "Удалить чат"
   :edit-chats                            "Изменить чаты"
   :sign-in                               "Вход"
   :create-new-account                    "Создать новый аккаунт"
   :sign-in-to-object                     "Войти в Статус"
   :got-it                                "Понятно"
   :move-to-internal-failure-message      "Нам необходимо перенести некоторые важные файлы из внешнего хранилища во внутреннее. Для этого нам нужно ваше разрешение. В следующих версиях мы не будем использовать внешнее хранилище."
   :edit-group                            "Изменить группу"
   :delete-group                          "Удалить группу"
   :browsing-title                        "Просматривать"
   :browsing-cancel                       "Отмена"
   :faucet-success                        "Заявка на сборщик получена"
   :choose-from-contacts                  "Выбрать из контактов"
   :phone-e164                            "Международная 1"
   :remove-from-group                     "Удалить из группы"
   :search-contacts                       "Поиск контактов"
   :transaction                           "Транзакция"
   :public-group-object                   "Общий"
   :leave-chat                            "Выйти из чата"
   :start-conversation                    "Начать разговор"
   :topic-format                          "Неверный формат [a-z0-9\\-] +"
   :faucet-error                          "Ошибка заявки на сборщик"
   :phone-significant                     "Значительное"
   :search-for                            "Искать..."
   :phone-international                   "Международная 2"
   :send-transaction                      "Отправить транзакцию"
   :delete-contact                        "Удалить контакт"
   :mute-notifications                    "Отключить звук в оповещениях"


   :contact-s                             {:one   "контакт"
                                           :other "контакты"}
   :from                                  "От"
   :search-chats                          "Поиск чатов"
   :in-contacts                           "В контактах"

   :type-a-message                        "Напишите сообщение..."
   :type-a-command                        "Начинайте вводить команду..."
   :shake-your-phone                      "Нашли ошибку или есть предложение? Просто ~потрясите~ телефон!"
   :object-prompt                         "Создайте статус, чтобы люди знали о том, что вы предлагаете. Можно также использовать #хэштеги."
   :add-a-object                          "Добавьте статус..."
   :error                                 "Ошибка..."
   :more                                  "Больше"
   :no-objectes-found                     "Статусы не найдены"
   :swow-qr                               "Показать QR-код"
   :browsing-open-in-web-browser          "Открыть в веб-браузере"
   :delete-group-prompt                   "Это не повлияет на контакты"
   :edit-profile                          "Редактировать профиль"


   :enter-password-transactions           {:one   "Подтвердите транзакцию, указав свой пароль"
                                           :other "Подтвердите транзакции, указав свой пароль"}
   :unsigned-transactions                 "Неподписанные транзакции"
   :empty-topic                           "Пустая тема"
   :to                                    "Кому"
   :group-members                         "Участники группы"
   :estimated-fee                         "Примерная комиссия"
   :data                                  "Дата"})
