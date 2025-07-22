# Kotlin Multiplatform News App

A modern Kotlin Multiplatform application showcasing Clean Architecture principles with a news articles feature. The app demonstrates best practices for building cross-platform applications that run on both Android and iOS.

## Features

-   **Kotlin Multiplatform**: Shared business logic across Android and iOS
-   **Clean Architecture**: Modularized into domain, data, and presentation layers
-   **Jetpack Compose**: Modern declarative UI for both platforms
-   **Ktor**: HTTP client for API communication
-   **Koin**: Dependency injection framework
-   **News API**: Real-time news articles from NewsAPI.org
-   **Decompose**: Navigation and state management

## Architecture Overview

The project follows Clean Architecture principles with the following structure:

```
shared/
├── core/
│   └── domain/
│       └── result/          # Result wrapper for error handling
├── features/
│   └── news/
│       ├── domain/          # Business logic and models
│       ├── data/            # Data layer (API, repositories)
│       └── presentation/    # ViewModels and components
├── di/                      # Dependency injection modules
└── root/                    # Root navigation and components

compose-ui/
├── news/                    # News feature UI screens
├── root/                    # Root navigation UI
└── ...                      # Other UI components
```

## Tech Stack

### Core Technologies

-   **Kotlin Multiplatform**: 2.2.0
-   **Compose Multiplatform**: 1.8.1
-   **Decompose**: 3.4.0-alpha02 (Navigation & State Management)

### Networking

-   **Ktor**: HTTP client for API calls
-   **Kotlin Serialization**: JSON serialization

### Dependency Injection

-   **Koin**: Lightweight DI framework

### UI

-   **Jetpack Compose**: Android UI
-   **Compose Multiplatform**: iOS UI

## Getting Started

### Prerequisites

-   **Android Studio**: Latest stable version
-   **Xcode**: 15.0+ (for iOS development)
-   **Kotlin Multiplatform Mobile Plugin**: Optional but recommended

### API Setup

1. Get a free API key from [NewsAPI.org](https://newsapi.org/)
2. Replace `YOUR_NEWS_API_KEY_HERE` in:
    - `app-android/src/main/kotlin/com/example/myapplication/android/MainActivity.kt`
    - `app-ios-compose/app-ios-compose/iOSApp.swift`

### Running the App

#### Android

1. Open the project in Android Studio
2. Select `app-android` configuration
3. Run the app

#### iOS

1. Open `app-ios-compose/app-ios-compose.xcodeproj` in Xcode
2. Select a simulator or device
3. Run the app

#### Desktop (Optional)

```bash
./gradlew :app-desktop:run
```

## Project Structure

### Shared Module

Contains all shared business logic, models, and components:

-   **Domain Layer**: Pure Kotlin business logic
-   **Data Layer**: API services and repositories
-   **Presentation Layer**: ViewModels and components
-   **DI Configuration**: Koin modules setup

### Compose UI Module

Contains all UI components shared between platforms:

-   **News Screens**: List and detail views
-   **Navigation**: Root navigation setup
-   **Theme**: Material Design 3 theming

### Platform-Specific Modules

-   **app-android**: Android-specific configuration
-   **app-ios-compose**: iOS-specific configuration

## Features Implemented

### News Feature

-   **Top Headlines**: Browse latest news articles
-   **Search**: Search for specific news topics
-   **Article Details**: View full article content
-   **Error Handling**: Proper error states and loading indicators
-   **Image Loading**: Async image loading with Coil

## Clean Architecture Layers

### Domain Layer

-   **Models**: Pure Kotlin data classes
-   **Use Cases**: Business logic operations
-   **Repositories**: Interface definitions

### Data Layer

-   **API Service**: Ktor-based HTTP client
-   **Repository Implementation**: Concrete implementations
-   **DTOs**: Data transfer objects

### Presentation Layer

-   **ViewModels**: State management
-   **Components**: Decompose navigation components
-   **UI**: Compose-based screens

## Testing

While tests are not required for this showcase, the architecture supports:

-   Unit tests for use cases
-   Integration tests for repositories
-   UI tests for Compose screens

## Future Enhancements

-   Offline caching with SQLDelight
-   Push notifications
-   Bookmark/favorite articles
-   Dark theme support
-   Advanced search filters
-   Category-based browsing

## Contributing

This is a showcase project demonstrating Kotlin Multiplatform capabilities. Feel free to use it as a reference for your own projects.

## License

This project is provided as-is for educational and demonstration purposes.
