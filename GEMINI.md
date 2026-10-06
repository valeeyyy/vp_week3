# Android, Kotlin, and Jetpack Compose Guidelines

Follow these rules when writing code for this Android project:

## Architecture & State Management
- **MVVM Pattern**: Follow the Model-View-ViewModel architecture. Keep UI logic out of ViewModels and business logic out of UI components.
- **State Hoisting**: Keep Composables as stateless as possible by hoisting state to the caller. Pass state down as parameters and events up as lambdas.
- **Unidirectional Data Flow (UDF)**: State flows down and events flow up. ViewModels should expose UI state as `StateFlow` or `Compose State`.

## Jetpack Compose Best Practices
- **Modifiers**: Every custom Composable should accept a `modifier: Modifier = Modifier` as its first optional parameter to allow the caller to adjust its layout and behavior.
- **Side Effects**: Never perform side effects directly in the Composable body. Use side-effect APIs like `LaunchedEffect`, `DisposableEffect`, and `SideEffect`.
- **Remember**: Use `remember` to store objects across recompositions. Use `rememberSaveable` for state that needs to survive configuration changes.
- **Previews**: Write `@Preview` functions for custom composables to facilitate UI development without running the app.
- **Avoid Heavy Computations**: Do not perform heavy computations directly in a Composable. Move them to a ViewModel or wrap them in `remember` if they are derived state.

## Kotlin Standards
- **Immutability**: Prefer `val` (immutable) over `var` (mutable). Use read-only collections (`List`, `Map`) by default unless mutation is strictly required.
- **Coroutines & Flow**: Use Kotlin Coroutines for asynchronous work. Use `Flow` and `StateFlow` for reactive streams of data. Avoid using RxJava or callbacks.
- **Scope Functions**: Use Kotlin scope functions (`let`, `run`, `with`, `apply`, `also`) idiomatically to keep code concise and readable.
- **Null Safety**: Leverage Kotlin's null safety. Avoid using `!!` (not-null assertion operator); use safe calls (`?.`) and Elvis operators (`?:`) instead.
- **Data Classes**: Use `data class` for models and UI state representations to automatically get `equals()`, `hashCode()`, and `copy()`.

## UI/UX
- **Material Design 3**: Use Material 3 (`androidx.compose.material3.*`) components and typography by default.
- **Theming**: Use the project's custom theme for colors, typography, and shapes rather than hardcoding values.
