# BDUIX Android

Реализация клиентского BDUIX движка на Android с поддержкой динамической отрисовки экранов, команд навигации и обновлений контента экрана после пользовательских действий.

## Быстрый старт
APK Release:
TODO ссылка на release apk в GitHub

## Запуск (debug)

Требования:
- Android Studio | Narwhal 3 Feature Drop | 2025.1.3 или новее
- Gradle 8.13
- JDK 17
- minSdk 24

Шаги:
1. Создать проект в Firebase со включенной аналитикой
2. Склонировать проект в Android Studio
3. Переместить файл google-services.json из созданного проекта в /app
4. Run 'app'

Альтернатива п.4:
```bash
./gradlew clean assembleDebug
adb install app/build/outputs/apk/debug/app-debug.apk
```