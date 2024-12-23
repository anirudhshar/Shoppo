# eCommerce App

This is an eCommerce application built with **Clean Architecture**, **MVVM**, **Ktor**, and **Koin**. The app is modular, scalable, and designed with separation of concerns for maintainability and testability.

## Project Architecture Flow

1. **Presentation Layer (UI):**
   - Contains the UI components and ViewModel classes.
   - Organized by feature (e.g., `cart`, `productDetails`).

2. **Domain Layer:**
   - Includes:
     - `UseCases` to encapsulate business logic.
     - Repository interfaces that define contracts for data operations.
     - Network service interfaces for API interactions.

3. **Data Layer:**
   - Implements repository interfaces and network service interfaces.
   - Handles data fetching through `Ktor` for network calls.
   - Organized into `model` (with `request` and `response`), `network`, and `repository` packages.

## Dependency Injection

- **Koin** is used for Dependency Injection, ensuring loose coupling and modularity.
- Modules are defined to provide dependencies for ViewModels, UseCases, Repositories, and Network Services.

## Network Communication

- **Ktor** is used for making efficient network calls.
- Provides a robust HTTP client to interact with APIs, enabling serialization and error handling.

## Flow of Data

1. **Presentation Layer:**
   - The UI interacts with the `ViewModel`.
   - ViewModel fetches data via `UseCase`.

2. **Domain Layer:**
   - UseCases call methods from the repository interface.
   - Repositories abstract the data-fetching mechanism.

3. **Data Layer:**
   - Repository implementation fetches data from the network service.
   - Network service implementation uses `Ktor` to make API calls.

## Folder Structure

```
- data/
  - di/
  - model/
    - request/
    - response/
  - network/
  - repository/

- domain/
  - di/
  - model/
  - network/
  - repository/
  - useCase/

- presentation/
  - ui/
    - feature/
      - cart/
        - CartViewModel.kt
        - CartScreen.kt
```

## Example Workflow

1. **ViewModel (Presentation Layer):** Calls `UseCase` to fetch product details.

   ```kotlin
   class CartViewModel(private val fetchCartItemsUseCase: FetchCartItemsUseCase) : ViewModel() {
       val cartItems = liveData {
           emit(fetchCartItemsUseCase.execute())
       }
   }
   ```

2. **UseCase (Domain Layer):** Fetches data through repository interface.

   ```kotlin
   class FetchCartItemsUseCase(private val repository: CartRepository) {
       suspend fun execute(): List<CartItem> = repository.getCartItems()
   }
   ```

3. **Repository Interface (Domain Layer):** Defines the contract for data fetching.

   ```kotlin
   interface CartRepository {
       suspend fun getCartItems(): List<CartItem>
   }
   ```

4. **Repository Implementation (Data Layer):** Fetches data using network service.

   ```kotlin
   class CartRepositoryImpl(private val networkService: CartNetworkService) : CartRepository {
       override suspend fun getCartItems(): List<CartItem> =
           networkService.fetchCartItems()
   }
   ```

5. **Network Service Interface (Domain Layer):** Declares network-related methods.

   ```kotlin
   interface CartNetworkService {
       suspend fun fetchCartItems(): List<CartItem>
   }
   ```

6. **Network Service Implementation (Data Layer):** Uses `Ktor` to make API calls.

   ```kotlin
   class CartNetworkServiceImpl(private val ktorClient: HttpClient) : CartNetworkService {
       override suspend fun fetchCartItems(): List<CartItem> {
           return ktorClient.get("/cart/items")
       }
   }
   ```

## Setup Instructions

1. Clone the repository.
2. Add dependencies in the `build.gradle.kts` file:
   ```kotlin
   implementation("io.insert-koin:koin-android:3.1.6")
   implementation("io.ktor:ktor-client-core:2.0.0")
   implementation("io.ktor:ktor-client-android:2.0.0")
   ```
3. Define Koin modules to provide dependencies:
   ```kotlin
   val appModule = module {
       single { HttpClient() }
       factory<CartNetworkService> { CartNetworkServiceImpl(get()) }
       factory<CartRepository> { CartRepositoryImpl(get()) }
       factory { FetchCartItemsUseCase(get()) }
       viewModel { CartViewModel(get()) }
   }
   ```
4. Start Koin in the `Application` class:
   ```kotlin
   class MyApplication : Application() {
       override fun onCreate() {
           super.onCreate()
           startKoin {
               androidContext(this@MyApplication)
               modules(appModule)
           }
       }
   }
   ```

## License

This project is not currently licensed.

