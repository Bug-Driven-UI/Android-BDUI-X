# 📱 BDUIX Android

**BDUIX Android** — клиентская реализация BDUIX-движка на **Android**, которая поддерживает:

- ⚡ динамическую отрисовку экранов
- 🧭 команды навигации
- 🔄 обновление контента экрана после пользовательских действий

---

## ✨ Возможности

- Получение конфигураций экранов с сервера и их моментальная отрисовка
- Поддержка интерактивных UI-компонентов (кнопки, любые кликабельные элементы)
- Навигация между экранами
- Динамическое обновление UI без необходимости обновления приложения в сторе
- Встроенная аналитика пользовательских действий (Firebase Analytics)

---

## 🛠 Технологии

- **Kotlin** — основной язык разработки
- **Jetpack Compose** — декларативный UI
- **Dagger/Hilt** — DI
- **Retrofit + OkHttp** — сетевой слой
- **KotlinX Serialization** — сериализация/десериализация данных
- **Firebase Analytics** — аналитика
- **Coil** — загрузка и кэширование изображений из сети

---

## 🚀 Быстрый старт

### 📦 APK Release
👉 [TODO: ссылка на release APK в GitHub]

---

## ⚙️ Запуск (Debug)

### Требования:
- Android Studio **Narwhal 3 Feature Drop (2025.1.3)** или новее
- Gradle **8.13**
- JDK **17**
- `minSdk` = **24**

### Шаги:
1. Создать проект в **Firebase** и включить аналитику.
2. Склонировать проект в Android Studio:
   ```bash
   git clone https://github.com/Bug-Driven-UI/Android.git
3. Скачать файл google-services.json из Firebase и положить его в /app.
4. Запустить приложение:
   - Через Android Studio (Run app)
   - или через консоль:
   ```bash
    ./gradlew clean assembleDebug
    adb install app/build/outputs/apk/debug/app-debug.apk